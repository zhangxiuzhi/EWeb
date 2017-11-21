package com.esteel.web.config;

import com.esteel.framework.util.EsteelCacheKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 16:24
 */
@Configuration
public class WebConfig {

    @Bean
    public EsteelCacheKeyGenerator esteelCacheKeyGenerator(){
        return new EsteelCacheKeyGenerator();
    }
}
