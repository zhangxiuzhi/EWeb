package com.esteel.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-12-01
 * Time: 22:28
 */
@Configuration
@EnableConfigurationProperties(CacheExpireProperties.class)
public class CustomerConfiguration {

        /**
     * 临时数据存储
     * @param redisTemplate
     * @return
     */
    @Bean("tmpCacheManager")
    @ConfigurationProperties(CacheExpireProperties.PREFIX)

    public CacheManager tmpCacheManager(RedisTemplate<Object, Object> redisTemplate,CacheExpireProperties cacheExpireProperties){
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);

        Map<String,Long> expires = cacheExpireProperties.getExpires();

        Collection<String> cacheNames = cacheExpireProperties.getExpires().keySet();
        cacheManager.setCacheNames(cacheNames);
        cacheManager.setExpires(expires);

        return cacheManager;
    }

}
