package com.esteel.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-12-13
 * Time: 16:39
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class MethodSecurity extends GlobalMethodSecurityConfiguration {

}
