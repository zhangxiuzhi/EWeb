package com.esteel.web;

import javax.servlet.MultipartConfigElement;

import com.esteel.web.config.EsteelFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableFeignClients
//@EnableHystrix
//@EnableOAuth2Sso
//@EnableOAuth2Client
public class WebApplication extends WebSecurityConfigurerAdapter {

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

        http.headers().frameOptions().disable()
                .and().formLogin().and()
                .antMatcher("/**").authorizeRequests().antMatchers("/", "/trade/**","/login**", "/webjars/**").permitAll().anyRequest()
                .authenticated()
                .and().addFilterBefore(oauth2ClientAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
        ;
//        http.headers().frameOptions().disable()
//                .and().formLogin().defaultSuccessUrl("/sss")
//                .and().antMatcher("/trade/**").authorizeRequests().anyRequest().permitAll()
//                .and().antMatcher("/**").authorizeRequests().anyRequest().authenticated()
////                .and().antMatcher("/trade/**").authorizeRequests().anyRequest().authenticated()
////                .anyRequest().authenticated()
//        .and().addFilterBefore(oauth2ClientAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)

//        .(new EsteelFilter())
        ;
    }

}
