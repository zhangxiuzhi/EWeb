package com.esteel.web.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 14:55
 */
@FeignClient(name = "Base",fallback = BaseClientCallback.class,path = "${esteel.language}")
public interface BaseClient {

    @RequestMapping(value = "/port", method = RequestMethod.POST)
    public String getPort(@RequestParam("portId") long portId);

}

@Component
class BaseClientCallback implements BaseClient {

    @Override
    public String getPort(long portId){
        return "数据丢失";
    }
}