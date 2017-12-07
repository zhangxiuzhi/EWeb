package com.esteel.web.service;

import com.esteel.common.vo.BaseQueryVo;
import com.esteel.common.vo.SimpePageImpl;
import com.esteel.web.vo.CityVo;
import com.esteel.web.vo.DistrictVo;
import com.esteel.web.vo.ProvinceVo;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
@FeignClient(name = "Base",fallback = BaseClientCallback.class ,path = "cn")
public interface BaseClient {

    @RequestMapping(value = "/port", method = RequestMethod.POST)
    public String getPort(@RequestParam("portId") long portId);
    
    @RequestMapping(value = "/allProvince", method = RequestMethod.POST)
    public List<ProvinceVo> findAll();

    @RequestMapping(value = "/findProvince", method = RequestMethod.POST)
    public SimpePageImpl<ProvinceVo> findProvince(@RequestBody BaseQueryVo vo);
    /**
     * 获取所有的城市
     * @param provinceId
     * @return
     */
    @RequestMapping(value = "/findCity", method = RequestMethod.POST)
    public List<CityVo> findAllCity(int provinceId);
    /**
     * 获取所有的区县
     * @param cityId
     * @return
     */
    @RequestMapping(value = "/findDistrict", method = RequestMethod.POST)
    public List<DistrictVo> findAllDistrict(int cityId);

}

@Component
class BaseClientCallback implements BaseClient {

    @Override
    public String getPort(long portId){
        return "数据丢失";
    }

    @Override
    public List<ProvinceVo> findAll() {
        ArrayList<ProvinceVo> vos = new ArrayList<>();
        return vos;
    }

    @Override
    public SimpePageImpl<ProvinceVo> findProvince(BaseQueryVo vo) {
        return null;
    }

	@Override
	public List<CityVo> findAllCity(int provinceId) {
		return null;
	}

	@Override
	public List<DistrictVo> findAllDistrict(int cityId) {
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

			@Override
			public List<CityVo> findAllCity(int provinceId) {
				return null;
			}

			@Override
			public List<DistrictVo> findAllDistrict(int cityId) {
				return null;
			}
        };
    }
}