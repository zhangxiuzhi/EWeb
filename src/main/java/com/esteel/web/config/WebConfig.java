package com.esteel.web.config;

import com.esteel.framework.util.EsteelCacheKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


    @Bean
    public ExecutorService executorService(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        return executorService;
    }

}
