package com.esteel.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ESTeel
 * <p>
 * 登录控制文件
 * <p>
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-12-06
 * Time: 下午2:16
 */
@Configuration
public class LoginConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    public void init(WebSecurity web) {
        web.ignoring().antMatchers("/")
                .and().ignoring().antMatchers("/trade/**")
                .and().ignoring().antMatchers("/offer/**")
                .and().ignoring().antMatchers("/fragments/**")
                .and().ignoring().antMatchers("/bootstrap/**")
                .and().ignoring().antMatchers("/css/**")
                .and().ignoring().antMatchers("/fonts/**")
                .and().ignoring().antMatchers("/images/**")
                .and().ignoring().antMatchers("/jq/**")
                .and().ignoring().antMatchers("/js/**")
                .and().ignoring().antMatchers("/react/**")
                .and().ignoring().antMatchers("/view/**")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated()
                .and().headers().frameOptions().sameOrigin()
        ;
    }

}
