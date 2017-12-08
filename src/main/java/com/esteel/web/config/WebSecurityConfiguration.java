package com.esteel.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-12-08
 * Time: 下午2:27
 */
//@Configuration
//@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("");

        http
                .authorizeRequests()
                .antMatchers("/trade/**")
                .hasAnyRole("USER")
                .antMatchers("/**")
                .permitAll();
    }
}
