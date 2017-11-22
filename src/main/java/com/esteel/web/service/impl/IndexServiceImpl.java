package com.esteel.web.service.impl;

import com.esteel.web.service.BaseClient;
import com.esteel.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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

    @Autowired
    ExecutorService executorService;

    @Override
//    @Cacheable
    public String getPort(long portId) {

        long t1 = System.currentTimeMillis();
        Future<String> submit = executorService.submit((Callable<String>) () -> baseClient.getPort(portId));
        Future<String> submit1 = executorService.submit((Callable<String>) () -> baseClient.getPort(portId+1));
        Future<String> future = executorService.submit((Callable<String>) () -> "ah");


        String port = null;
        try {
            port = submit.get();
            String port1 = submit1.get();
//            System.out.println(port);
//            System.out.println(port1);
            System.out.println("666666666666666666666666");
            System.out.println(System.currentTimeMillis()-t1);
            System.out.println("6666666666666666666666666");
        } catch (InterruptedException|ExecutionException e) {

        }

        long t2 = System.currentTimeMillis();
        baseClient.getPort(portId);
        baseClient.getPort(portId+1);

        System.out.println("7777777777777777777777777777");
        System.out.println(System.currentTimeMillis()-t2);
        System.out.println("7777777777777777777777777777");

        return port;
    }
}
