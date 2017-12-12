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
import com.esteel.web.vo.CityVo;
import com.esteel.web.vo.DistrictVo;
import com.esteel.web.vo.ProvinceVo;
import com.esteel.web.vo.base.CommodityCategoryVo;
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
//@FeignClient(name = "Base",url = "http://127.0.0.1:9920",fallback = BaseClientCallback.class)
@FeignClient(name = "Base",url = "http://10.0.1.214:9920",fallback = BaseClientCallback.class ,path = "cn")
public interface BaseClient {

    @RequestMapping(value = "/port", method = RequestMethod.POST)
    public String getPort(@RequestParam("portId") long portId);
    
    @RequestMapping(value = "/cn/allProvince", method = RequestMethod.POST)
    public List<ProvinceVo> findAllPro();
    
    @RequestMapping(value = "/province", method = RequestMethod.POST)
    public List<ProvinceVo> findAll();

    @RequestMapping(value = "/findProvince", method = RequestMethod.POST)
    public SimpePageImpl<ProvinceVo> findProvince(@RequestBody BaseQueryVo vo);
    /**
     * 获取所有的城市
     * @param provinceId
     * @return
     */
    @RequestMapping(value = "/cn/findAllCity",method= RequestMethod.POST)
    public List<CityVo> findAllCity(@RequestParam("provinceId") int provinceId);
    /**
     * 获取所有的区县
     * @param cityId
     * @return
     */
    @RequestMapping(value = "/cn/findAllDistrict", method = RequestMethod.POST)
    public List<DistrictVo> findAllDistrict(@RequestParam("cityId") int cityId);
    /**
     * 港口
     * @param port
     * @return
     */
    @RequestMapping(value = "/port", method = RequestMethod.POST)
    public PortVo getPort(@RequestBody PortVo vo);
    
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
     * @param commodityVo
     * @return
     */
    @RequestMapping(value = "/loadingPortListForOffer", method = RequestMethod.POST)
    public List<PortVo> findLoadingPortListForOffer(@RequestBody CommodityVo commodityVo);
    
    /**
	 * 品名大类
	 * @param commodityCategoryVo
	 * @return
	 */
	@RequestMapping("/commodityCategory")
	public CommodityCategoryVo getCommodityCategory(@RequestBody CommodityCategoryVo commodityCategoryVo);
    
    /**
	 * 品名查询:根据名称模糊查询
	 * @param commodityName
	 * @return
	 */
    @RequestMapping(value = "/commodityListByName", method = RequestMethod.POST)
    public List<CommodityVo> findCommodityListByName(@RequestBody CommodityVo commodityVo);
    
    /**
     * 铁矿属性查询:根据品名查询
     * @param commodityVo
     * @return
     */
    @RequestMapping(value = "/ironAttributeList", method = RequestMethod.POST)
    public List<IronAttributeLinkVo> findAttributeListByIron(@RequestBody CommodityVo commodityVo);
    
    /**
	 * 铁矿品名列表
	 * @return
	 */
    @RequestMapping(value = "/ironCommodityList", method = RequestMethod.POST)
	public List<CommodityVo> findCommodityListByIron();
    
    /**
     * 属性值选项查询:根据属性编码查询
     * @param attributeValueOptionVo
     * @return
     */
    @RequestMapping(value = "/attributeValueOptionListByAttributeCode", method = RequestMethod.POST)
    public List<AttributeValueOptionVo> findAttributeValueOptionListByAttributeCode(@RequestBody AttributeValueOptionVo attributeValueOptionVo);
    
    /**
     * 属性值选项
     * @param attributeValueOptionVo
     * @return
     */
    @RequestMapping(value = "/attributeValueOption", method = RequestMethod.POST)
    public AttributeValueOptionVo getAttributeValueOption(@RequestBody AttributeValueOptionVo attributeValueOptionVo);
    
}

@Component
class BaseClientCallback implements BaseClient {

    @Override
    public PortVo getPort(PortVo vo){
    	if (vo == null) {
    		vo = new PortVo();
    	}
		
		vo.setPortId(-1);;
		
		vo.setStatus(99);
		vo.setMsg("根据属性编码查询失败！");
		vo.setMsg("");
		
		return vo;
    }

    @Override
	public List<ProvinceVo> findAll() {
		ArrayList<ProvinceVo> vos = new ArrayList<>();
		return vos;
	}

    @Override
    public SimpePageImpl<ProvinceVo> findProvince(BaseQueryVo vo) {
    	if (vo == null) {
    		vo = new BaseQueryVo();
    	}
    	
		return new SimpePageImpl<>(new ArrayList<>(), vo.getPageable(), 0);
    }

	@Override
	public List<CityVo> findAllCity(int provinceId) {
		return null;
	}

	@Override
	public List<DistrictVo> findAllDistrict(int cityId) {
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
	public List<PortVo> findLoadingPortListForOffer(CommodityVo commodityVo) {
		ArrayList<PortVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public List<CommodityVo> findCommodityListByName(CommodityVo commodityVo) {
		ArrayList<CommodityVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public List<IronAttributeLinkVo> findAttributeListByIron(CommodityVo commodityVo) {
		ArrayList<IronAttributeLinkVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public List<AttributeValueOptionVo> findAttributeValueOptionListByAttributeCode(AttributeValueOptionVo attributeValueOptionVo) {
		ArrayList<AttributeValueOptionVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public AttributeValueOptionVo getAttributeValueOption(AttributeValueOptionVo vo) {
		if (vo == null) {
			vo = new AttributeValueOptionVo();
		}
		
		vo.setOptionId(-1);
		
		vo.setStatus(99);
		vo.setMsg("根据属性编码查询失败！");
		vo.setMsg("");
		
		return vo;
	}

	@Override
	public CommodityCategoryVo getCommodityCategory(CommodityCategoryVo vo) {
		if (vo == null) {
			vo = new CommodityCategoryVo();
		}
		
		vo.setCategoryId(-1);
		
		vo.setStatus(99);
		vo.setMsg("");
		vo.setMsg("");
		
		return vo;
	}

	@Override
	public List<CommodityVo> findCommodityListByIron() {
		ArrayList<CommodityVo> vos = new ArrayList<>();
		return vos;
	}

	@Override
	public String getPort(long portId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProvinceVo> findAllPro() {
		// TODO Auto-generated method stub
		return null;
	}

}
@Component
class BaseClientFallbackFactory implements FallbackFactory<BaseClient>{

    @Override
    public BaseClient create(Throwable cause) {
        return new BaseClient() {

			@Override
			public List<ProvinceVo> findAll() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public SimpePageImpl<ProvinceVo> findProvince(BaseQueryVo vo) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PortVo getPort(PortVo vo) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<PortVo> findPortListForOffer() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<PortVo> findBondedAreaPortListForOffer() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<PortVo> findLoadingPortListForOffer(CommodityVo commodityVo) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CommodityCategoryVo getCommodityCategory(CommodityCategoryVo commodityCategoryVo) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<CommodityVo> findCommodityListByName(CommodityVo commodityVo) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<IronAttributeLinkVo> findAttributeListByIron(CommodityVo commodityVo) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<CommodityVo> findCommodityListByIron() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<AttributeValueOptionVo> findAttributeValueOptionListByAttributeCode(AttributeValueOptionVo attributeValueOptionVo) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public AttributeValueOptionVo getAttributeValueOption(AttributeValueOptionVo queryVo) {
				// TODO Auto-generated method stub
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

			@Override
			public String getPort(long portId) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<ProvinceVo> findAllPro() {
				// TODO Auto-generated method stub
				return null;
			}
        };
    }
}