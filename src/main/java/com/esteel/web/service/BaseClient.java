package com.esteel.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.esteel.common.vo.BaseQueryVo;
import com.esteel.common.vo.SimpePageImpl;
import com.esteel.web.vo.ProvinceVo;
import com.esteel.web.vo.base.CommodityQueryVo;
import com.esteel.web.vo.base.CommodityVo;
import com.esteel.web.vo.base.PortVo;
import com.esteel.web.vo.config.AttributeValueOptionVo;
import com.esteel.web.vo.config.IronAttributeLinkVo;

import feign.hystrix.FallbackFactory;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 14:55
 *
 */
//,url = "http://127.0.0.1:9000"
@FeignClient(name = "Base",fallback = BaseClientCallback.class ,path = "offer")
public interface BaseClient {

    @RequestMapping(value = "/port", method = RequestMethod.POST)
    public String getPort(@RequestParam("portId") long portId);

    @RequestMapping(value = "/province", method = RequestMethod.POST)
    public List<ProvinceVo> findAll();

    @RequestMapping(value = "/findProvince", method = RequestMethod.POST)
    public SimpePageImpl<ProvinceVo> findProvince(@RequestBody BaseQueryVo vo);
    
    /**
     * 报盘模块 港口列表
     * @return
     */
    @RequestMapping(value = "/portListForOffer", method = RequestMethod.POST)
    public List<PortVo> findPortListForOffer();

    /**
     * 报盘模块 保税区装货港列表
     * @return
     */
    @RequestMapping(value = "/bondedAreaPortListForOffer", method = RequestMethod.POST)
    public List<PortVo> findBondedAreaPortListForOffer();
    
    /**
     * 报盘模块 装货港查询
     * @param commodityQueryVo
     * @return
     */
    @RequestMapping(value = "/bondedAreaPortListForOffer", method = RequestMethod.POST)
    public List<PortVo> findLoadingPortListForOffer(@RequestBody CommodityQueryVo commodityQueryVo);
    
    /**
	 * 品名查询:根据名称模糊查询
	 * @param name
	 * @return
	 */
    @RequestMapping(value = "/commodityListByName", method = RequestMethod.POST)
    public List<CommodityVo> findCommodityListByName(@RequestParam("name") String CommodityName);
    
    /**
     * 铁矿属性查询:根据品名查询
     * @param baseCommodityQueryVo
     * @return
     */
    @RequestMapping(value = "/attributeList", method = RequestMethod.POST)
    public List<IronAttributeLinkVo> findAttributeListByIron(@RequestBody CommodityQueryVo commodityQueryVo);
    
    /**
     * 属性值选项查询:根据属性编码查询
     * @param attributeCode
     * @return
     */
    @RequestMapping("/attributeValueOptionListByCode")
    public List<AttributeValueOptionVo> findByAttributeCode(@RequestParam("attributeCode") String attributeCode);
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
	public List<PortVo> findPortListForOffer() {
		ArrayList<PortVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public List<PortVo> findBondedAreaPortListForOffer() {
		ArrayList<PortVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public List<PortVo> findLoadingPortListForOffer(CommodityQueryVo commodityQueryVo) {
		ArrayList<PortVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public List<CommodityVo> findCommodityListByName(String CommodityName) {
		ArrayList<CommodityVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public List<IronAttributeLinkVo> findAttributeListByIron(CommodityQueryVo commodityQueryVo) {
		ArrayList<IronAttributeLinkVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public List<AttributeValueOptionVo> findByAttributeCode(String attributeCode) {
		ArrayList<AttributeValueOptionVo> vos = new ArrayList<>();
		return vos;
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
			public List<PortVo> findPortListForOffer() {
				return null;
			}

			@Override
			public List<PortVo> findBondedAreaPortListForOffer() {
				return null;
			}

			@Override
			public List<PortVo> findLoadingPortListForOffer(CommodityQueryVo commodityQueryVo) {
				return null;
			}

			@Override
			public List<CommodityVo> findCommodityListByName(String CommodityName) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<IronAttributeLinkVo> findAttributeListByIron(CommodityQueryVo commodityQueryVo) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<AttributeValueOptionVo> findByAttributeCode(String attributeCode) {
				// TODO Auto-generated method stub
				return null;
			}
        };
    }
}