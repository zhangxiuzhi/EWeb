package com.esteel.web.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 17:32
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CacheManager cacheManager;

    @RequestMapping("/clearCache")
    public String clearCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);

        if (cache!=null){
            cache.clear();
        }
        return "clear";
    }

    @RequestMapping("/caches")
    public String[] cacheNames(){

       return  cacheManager.getCacheNames().toArray(new String[0]);

    }
}
