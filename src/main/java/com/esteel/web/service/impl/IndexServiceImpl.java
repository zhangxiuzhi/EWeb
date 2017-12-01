package com.esteel.web.service.impl;

import com.esteel.common.util.EsteelConstant;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.ContactClient;
import com.esteel.web.service.IndexService;
import com.esteel.web.vo.ProvinceVo;
import com.taobao.common.tfs.TfsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 15:06
 */
@Service
@CacheConfig(cacheNames = "base",keyGenerator = "esteelCacheKeyGenerator")
public class IndexServiceImpl implements IndexService {

    @Autowired
    BaseClient baseClient;


    @Autowired
    CacheManager cacheManager;

    @Autowired
    ContactClient contactClient;

    @Autowired
    TfsManager tfsManager;


    @Override
//    @Cacheable(key="'com.esteel.base.service.BaseProvinceService.findAll:00'",unless = " !(#result != null&&#result.size()>0) ")
    public List<ProvinceVo> getPort(long portId) {

        int companyStatusCancel = EsteelConstant.COMPANY_STATUS_CANCEL;

        List<ProvinceVo> all = baseClient.findAll();

//        String key = "com.esteel.base.service.BaseProvinceService.findAll:10";
//
//        Cache baseCache = cacheManager.getCache("base");
//
//
//        if (baseCache.get(key)==null){
//            baseCache.put(key,all);
//        }else{
//            return baseCache.get(key, all.getClass());
//        }




//        String port = null;
//        try {
//            long t1 = System.currentTimeMillis();
//            Future<String> submit = executorService.submit((Callable<String>) () -> baseClient.getPort(portId));
//            Future<String> submit1 = executorService.submit((Callable<String>) () -> baseClient.getPort(portId + 1));
//
//            port = submit.get();
//            String port1 = submit1.get();
//            System.out.println(port);
//            System.out.println(port1);
//            System.out.println("666666666666666666666666");
//            System.out.println(System.currentTimeMillis()-t1);
//            System.out.println("6666666666666666666666666");
//        } catch (InterruptedException | ExecutionException e) {
//
//        }

//        long t2 = System.currentTimeMillis();
//        baseClient.getPort(portId);
//        baseClient.getPort(portId+1);

//        System.out.println("7777777777777777777777777777");
//        System.out.println(System.currentTimeMillis()-t2);
//        System.out.println("7777777777777777777777777777");

//        List<ProvinceVo> provinceVos = baseClient.findAll();


//        System.out.println(provinceVos);



//        BaseQueryVo vo = new BaseQueryVo();
//        vo.setPage(2);
//        SimpePageImpl<ProvinceVo> province = baseClient.findProvince(vo);



//        发送邮件的样例代码
//        contactClient.sendMail("zhangxiuzhi@mysteelsoft.com.cn","新的测试","张修志");

//        发送短信的样例代码
//        contactClient.sendSms("13916048081","新的测试111");

        return all;

//        System.out.println(provinceVos);
//        System.out.println(vos);
//        return province;
    }

    @Override
    public void tfsTest(){

        System.out.println(tfsManager.isEnable());



        tfsManager.fetchFile("T13RZTB5xT1RXrhCrK","jpg","e:\\1111.jpg");

    }
}
