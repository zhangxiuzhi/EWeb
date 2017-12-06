package com.esteel.web.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esteel.common.util.EsteelConstant;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.OfferClient;
import com.esteel.web.vo.base.CommodityCategoryEnum;
import com.esteel.web.vo.base.CommodityVo;
import com.esteel.web.vo.base.PortVo;
import com.esteel.web.vo.config.AttributeValueOptionVo;
import com.esteel.web.vo.config.IronAttributeLinkVo;
import com.esteel.web.vo.offer.IronOfferVo;
import com.taobao.tair.json.JSONObject;

import net.minidev.json.JSONArray;

/**
 * ESTeel
 * Description: 报盘用controller
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 13:49
 */
@RequestMapping("/offer")
@Controller
public class OfferController {
	@Autowired
	BaseClient baseClient;
	@Autowired
	OfferClient offerClient;
	
	@RequestMapping("/myOffer")
    public String myOfferUI(){
        return "/offer/myOffer";
    }

    @RequestMapping(value = "/addOffer", method = RequestMethod.GET)
    public String addOfferUI(Model model) {
    	/* 页面数据组装 开始 */
    	/**
    	 * 品名列表
    	 * 格式:[{"text":"commodityName","value":"commodityId","key":"commodityAlias"},...]
    	 */
    	List<Map<String, String>> ironCommodityJson = new ArrayList<Map<String, String>>();
    	
    	/**
    	 * 铁矿品名属性值联动列表
    	 * 格式:{"commodityName":[{"text":"attributeCode","value":"attributeValue","key":"attributeCode"},...],...}
    	 */
    	Map<String, List<Map<String, String>>> ironAttributeLinkJson = new HashMap<>();
    	
    	/**
    	 * 铁矿品名装货港联动列表
    	 * 格式:{"commodityName":[{"text":"portName","value":"portId","key":"portName,portNameEn"},...],...}
    	 */
    	Map<String, List<Map<String, String>>> ironLoadingPortJson = new HashMap<>();
    	
    	CommodityVo queryVo = new CommodityVo();
    	queryVo.setCategoryId(CommodityCategoryEnum.getInstance().IRON.getId());
    	
    	List<CommodityVo> ironCommodityList = baseClient.findCommodityListByIron();
    	for (CommodityVo commodityVo : ironCommodityList) {
    		Map<String, String> ironCommodityMap = new HashMap<>();
    		ironCommodityJson.add(ironCommodityMap);
    		ironCommodityMap.put("text", commodityVo.getCommodityName());
    		ironCommodityMap.put("value", commodityVo.getCommodityId() + "");
    		ironCommodityMap.put("key", commodityVo.getCommodityAlias());
    		
    		/**
        	 * 某品名属性值集合
        	 * 格式:[{"text":"attributeCode","value":"attributeValue","key":"attributeCode"},...]
        	 */
    		List<Map<String, String>> ironAttributes = new ArrayList<Map<String, String>>();
    		ironAttributeLinkJson.put(commodityVo.getCommodityName(), ironAttributes);
    		
    		queryVo.setCommodityCode(commodityVo.getCommodityCode());
    		
    		List<IronAttributeLinkVo> ironAttributeList = baseClient.findAttributeListByIron(queryVo);
    		ironAttributeList.forEach(attribute ->{
    			Map<String, String> ironAttributeMap = new HashMap<>();
    			ironAttributes.add(ironAttributeMap);
    			ironAttributeMap.put("text", attribute.getAttributeCode());
    			ironAttributeMap.put("value", attribute.getAttributeValue());
    			ironAttributeMap.put("key", attribute.getAttributeCode());
    		});
    		
    		/**
        	 * 某品名装货港集合
        	 * 格式:[{"text":"portName","value":"portId","key":"portName,portNameEn"},...]
        	 */
    		List<Map<String, String>> loadingPorts = new ArrayList<Map<String, String>>();
    		ironLoadingPortJson.put(commodityVo.getCommodityName(), loadingPorts);
    		
    		List<PortVo> loadingPortList = baseClient.findLoadingPortListForOffer(queryVo);
    		loadingPortList.forEach(port -> {
    			Map<String, String> loadingPortMap = new HashMap<>();
    			loadingPorts.add(loadingPortMap);
    			loadingPortMap.put("text", port.getPortName());
    			loadingPortMap.put("value", port.getPortId() + "");
    			loadingPortMap.put("key", port.getPortName() + "," + port.getPortNameEn());
        	});
    	}
    	
    	/**
    	 * 港口列表
    	 * 格式:[{"text":"portName","value":"portId","key":"portName,portNameEn"},...]
    	 */
		List<Map<String, String>> portJson = new ArrayList<Map<String, String>>();
		
    	List<PortVo> portList = baseClient.findPortListForOffer();
    	portList.forEach(port -> {
    		Map<String, String> portMap = new HashMap<>();
    		portJson.add(portMap);
    		portMap.put("text", port.getPortName());
    		portMap.put("value", port.getPortId() + "");
    		portMap.put("key", port.getPortName() + "," + port.getPortNameEn());
    	});
    	
    	/**
    	 * 保税区港口列表
    	 * 格式:[{"text":"portName","value":"portId","key":"portName,portNameEn"},...]
    	 */
    	List<Map<String, String>> bondedAreaPortJson = new ArrayList<Map<String, String>>();
		
    	List<PortVo> bondedAreaPortList = baseClient.findBondedAreaPortListForOffer();
    	bondedAreaPortList.forEach(port -> {
    		Map<String, String> bondedAreaPortMap = new HashMap<>();
    		bondedAreaPortJson.add(bondedAreaPortMap);
    		bondedAreaPortMap.put("text", port.getPortName());
    		bondedAreaPortMap.put("value", port.getPortId() + "");
    		bondedAreaPortMap.put("key", port.getPortName() + "," + port.getPortNameEn());
    	});
    	
    	/**
    	 * 指标类型列表
    	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
    	 */
    	List<Map<String, String>> indicatorTypeJson = new ArrayList<Map<String, String>>();
    	
    	AttributeValueOptionVo attributeValueOptionVo = new AttributeValueOptionVo();
    	attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_IRON_INDICATOR_TYPE);
    	
    	List<AttributeValueOptionVo> indicatorTypeList = 
    			baseClient.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
    	indicatorTypeList.forEach(indicatorType -> {
    		Map<String, String> indicatorTypeMap = new HashMap<>();
    		indicatorTypeJson.add(indicatorTypeMap);
    		indicatorTypeMap.put("text", indicatorType.getOptionValue());
    		indicatorTypeMap.put("value", indicatorType.getOptionId() + "");
    		indicatorTypeMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
    	});
    	
    	/**
    	 * 价格术语列表
    	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
    	 */
		List<Map<String, String>> priceTermJson = new ArrayList<Map<String, String>>();
    	
    	attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_PRICE_TERM);
    	List<AttributeValueOptionVo> priceTermList = 
    			baseClient.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
    	priceTermList.forEach(indicatorType -> {
    		Map<String, String> priceTermMap = new HashMap<>();
    		priceTermJson.add(priceTermMap);
    		priceTermMap.put("text", indicatorType.getOptionValue());
    		priceTermMap.put("value", indicatorType.getOptionId() + "");
    		priceTermMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
    	});
    	
    	/**
    	 * 计量方式列表
    	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
    	 */
		List<Map<String, String>> measureMethodJson = new ArrayList<Map<String, String>>();
    	
    	attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_IRON_MEASURE_METHOD);
    	List<AttributeValueOptionVo> measureMethodList = 
    			baseClient.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
    	measureMethodList.forEach(indicatorType -> {
    		Map<String, String> measureMethodMap = new HashMap<>();
    		measureMethodJson.add(measureMethodMap);
    		measureMethodMap.put("text", indicatorType.getOptionValue());
    		measureMethodMap.put("value", indicatorType.getOptionId() + "");
    		measureMethodMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
    	});
    	
    	/**
    	 * 计价方式列表
    	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
    	 */
		List<Map<String, String>> pricingMethodJson = new ArrayList<Map<String, String>>();
    	
    	attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_IRON_PRICING_METHOD);
    	List<AttributeValueOptionVo> pricingMethodList = 
    			baseClient.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
    	pricingMethodList.forEach(indicatorType -> {
    		Map<String, String> pricingMethodMap = new HashMap<>();
    		pricingMethodJson.add(pricingMethodMap);
    		pricingMethodMap.put("text", indicatorType.getOptionValue());
    		pricingMethodMap.put("value", indicatorType.getOptionId() + "");
    		pricingMethodMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
    	});
    	/* 页面数据组装 结束 */
    	
    	/* 页面数据传输 开始 */
    	// 品名列表
    	model.addAttribute("ironCommodityJson", JSONArray.toJSONString(ironCommodityJson));
    	// 铁矿品名属性值联动列表
    	model.addAttribute("ironAttributeLinkJson", JSONObject.toJSONString(ironAttributeLinkJson));
    	// 铁矿品名装货港联动列表
    	model.addAttribute("ironLoadingPortJson", JSONObject.toJSONString(ironLoadingPortJson));
    	// 港口列表
    	model.addAttribute("portJson", JSONArray.toJSONString(portJson));
    	// 保税区港口列表
    	model.addAttribute("bondedAreaPortJson", JSONArray.toJSONString(bondedAreaPortJson));
    	// 指标类型列表
    	model.addAttribute("indicatorTypeJson", JSONArray.toJSONString(indicatorTypeJson));
    	// 价格术语列表
    	model.addAttribute("priceTermJson", JSONArray.toJSONString(priceTermJson));
    	// 计量方式列表
    	model.addAttribute("measureMethodJson", JSONArray.toJSONString(measureMethodJson));
    	// 计价方式列表
    	model.addAttribute("pricingMethodJson", JSONArray.toJSONString(pricingMethodJson));
    	/* 页面数据传输 结束 */
    	
        return "/offer/addOffer";
    }
    
    @RequestMapping(value = "/saveOffer", method = RequestMethod.POST)
    public String saveOffer(IronOfferVo offerVo, Model model){
    	try {
    		offerClient.saveOffer(offerVo);
    	} catch (Exception e) {
			e.printStackTrace();
			
			model.addAttribute("msg", "新增失败");
			
			model.addAttribute("offerVo", offerVo);
		    
		    return "/offer/addOffer";
		}
    	
    	model.addAttribute("msg", "新增成功");
    	
        return "redirect:/offer/myOffer";
    }
}
