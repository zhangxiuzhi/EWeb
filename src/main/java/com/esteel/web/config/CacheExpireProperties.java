package com.esteel.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * ESTeel
 * Description: 短暂时间内的缓存配置
 * User: zhangxiuzhi
 * Date: 2017-12-04
 * Time: 11:28
 */

@ConfigurationProperties(prefix = CacheExpireProperties.PREFIX)
public class CacheExpireProperties {


    public static final String PREFIX="esteel.cache";


    //
    private Map<String,Long> expires = new HashMap<>();

    public Map<String, Long> getExpires() {
        return expires;
    }

    public void setExpires(Map<String, Long> expires) {
        this.expires = expires;
    }
}
