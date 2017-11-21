package com.esteel.web.service.impl;

import com.esteel.web.service.BaseClient;
import com.esteel.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 15:06
 */
@Service
//@CacheConfig(cacheNames = "base",keyGenerator = "esteelCacheKeyGenerator")
public class IndexServiceImpl implements IndexService {

    @Autowired
    BaseClient baseClient;

    @Override
//    @Cacheable
    public String getPort(long portId) {
        return baseClient.getPort(portId);
    }
}
