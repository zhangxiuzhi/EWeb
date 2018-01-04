package com.esteel.web;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esteel.web.config.EsteelFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableFeignClients
//@EnableHystrix
//@EnableOAuth2Sso
//@EnableOAuth2Client
public class WebApplication extends WebSecurityConfigurerAdapter {

    @Value("${ssourl}")
    private String ssourl;

    @Autowired
    OAuth2ClientAuthenticationProcessingFilter oauth2ClientAuthenticationProcessingFilter;

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
	/**
     * 文件上传配置
     * 
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("10240KB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        String logoutUrl = "/logout";

        RequestMatcher logoutMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher(logoutUrl, "GET"),
                new AntPathRequestMatcher(logoutUrl, "POST")
        );

        LogoutSuccessHandler logoutSuccessHandler = new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                String target = request.getParameter("_target");

                if (!StringUtils.isEmpty(target)){
                    //退出sso服务器
                    if (!target.toUpperCase().startsWith("HTTP://")){
                        String requestUrl = request.getRequestURL().toString();
                        String requestUri = request.getRequestURI();
                        int p = requestUrl.indexOf(requestUri);
                        String serverUrl = requestUrl.substring(0, p);
                        target = serverUrl+target;

                    }
                    response.sendRedirect(ssourl+"/logout?_target="+target);
//                    response.sendRedirect(target);
                }
            }
        };

        http.headers().frameOptions().disable()
                .and().csrf().ignoringAntMatchers("/logout**","/trade/**")
                .and()
                .formLogin().and()
                .antMatcher("/**").authorizeRequests().antMatchers("/", "/trade/**","/login**", "/webjars/**").permitAll().anyRequest()
                .authenticated()
                .and().logout().logoutRequestMatcher(logoutMatcher).logoutSuccessHandler(logoutSuccessHandler)
                .and().addFilterBefore(oauth2ClientAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        ;
    }

}
