package com.esteel.web;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esteel.web.config.EsteelRedirectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

import java.io.IOException;

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
                .and().formLogin().defaultSuccessUrl("/sss")
                .and().antMatcher("/**").authorizeRequests().anyRequest().authenticated()
        .and().addFilterBefore(oauth2ClientAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)

        ;
    }

}
