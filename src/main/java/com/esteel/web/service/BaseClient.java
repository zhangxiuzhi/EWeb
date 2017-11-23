package com.esteel.web.service;

import com.esteel.framework.vo.BaseQueryVo;
import com.esteel.web.vo.ProvinceVo;
import com.esteel.web.vo.SimpePageImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 14:55
 *
 */
//,url = "http://127.0.0.1:9000"
@FeignClient(name = "Base",fallbackFactory = BaseClientFallbackFactory.class ,path = "${esteel.language}")
public interface BaseClient {

    @RequestMapping(value = "/port", method = RequestMethod.POST)
    public String getPort(@RequestParam("portId") long portId);

    @RequestMapping(value = "/allProvince", method = RequestMethod.POST)
    public List<ProvinceVo> findAll();

    @RequestMapping(value = "/findProvince", method = RequestMethod.POST)
    public SimpePageImpl<ProvinceVo> findProvince(@RequestBody BaseQueryVo vo);

}

@Component
class BaseClientCallback implements BaseClient {

    @Override
    public String getPort(long portId){
        return "数据丢失";
    }

    @Override
    public List<ProvinceVo> findAll() {
        return null;
    }

    @Override
    public SimpePageImpl<ProvinceVo> findProvince(BaseQueryVo vo) {
        return null;
    }

}
@Component
class BaseClientFallbackFactory implements FallbackFactory<BaseClient>{

    @Override
    public BaseClient create(Throwable cause) {
        return new BaseClient() {
            @Override
            public String getPort(long portId) {
                return null;
            }

            @Override
            public List<ProvinceVo> findAll() {
                return null;
            }

            @Override
            public SimpePageImpl<ProvinceVo> findProvince(BaseQueryVo vo) {
                cause.printStackTrace();
                return null;
            }
        };
    }
}