package com.esteel.web.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.esteel.common.util.EsteelConstant;
import com.esteel.common.vo.StatusMSGVo;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.OfferClient;
import com.esteel.web.vo.base.CommodityCategoryEnum;
import com.esteel.web.vo.base.CommodityVo;
import com.esteel.web.vo.base.PortVo;
import com.esteel.web.vo.config.AttributeValueOptionVo;
import com.esteel.web.vo.config.IronAttributeLinkVo;
import com.esteel.web.vo.offer.IronFuturesOfferVo;
import com.esteel.web.vo.offer.IronInStockOfferVo;
import com.esteel.web.vo.offer.IronOfferBaseVo;
import com.esteel.web.vo.offer.IronOfferClauseVo;
import com.taobao.common.tfs.TfsManager;
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
	@Autowired
    TfsManager tfsManager;
	
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
    	List<Map<String, String>> ironCommodityList = new ArrayList<Map<String, String>>();
    	
    	/**
    	 * 铁矿品名属性值联动列表
    	 * 格式:{"commodityName":[{"text":"attributeCode","value":"attributeValue","key":"attributeCode"},...],...}
    	 */
    	Map<String, List<Map<String, String>>> ironAttributeLinkMap = new HashMap<>();
    	
    	/**
    	 * 铁矿品名装货港联动列表
    	 * 格式:{"commodityName":[{"text":"portName","value":"portId","key":"portName,portNameEn"},...],...}
    	 */
    	Map<String, List<Map<String, String>>> ironLoadingPortMap = new HashMap<>();
    	
    	CommodityVo queryVo = new CommodityVo();
    	queryVo.setCategoryId(CommodityCategoryEnum.getInstance().IRON.getId());
    	
    	List<CommodityVo> ironCommoditys = baseClient.findCommodityListByIron();
    	for (CommodityVo commodityVo : ironCommoditys) {
    		Map<String, String> ironCommodityMap = new HashMap<>();
    		ironCommodityList.add(ironCommodityMap);
    		ironCommodityMap.put("text", commodityVo.getCommodityName());
    		ironCommodityMap.put("value", commodityVo.getCommodityId() + "");
    		ironCommodityMap.put("key", commodityVo.getCommodityAlias());
    		
    		/**
        	 * 某品名属性值集合
        	 * 格式:[{"text":"attributeCode","value":"attributeValue","key":"attributeCode"},...]
        	 */
    		List<Map<String, String>> ironAttributes = new ArrayList<Map<String, String>>();
    		ironAttributeLinkMap.put(commodityVo.getCommodityName(), ironAttributes);
    		
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
    		ironLoadingPortMap.put(commodityVo.getCommodityName(), loadingPorts);
    		
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
		List<Map<String, String>> portList = new ArrayList<Map<String, String>>();
		
    	List<PortVo> ports = baseClient.findPortListForOffer();
    	ports.forEach(port -> {
    		Map<String, String> portMap = new HashMap<>();
    		portList.add(portMap);
    		portMap.put("text", port.getPortName());
    		portMap.put("value", port.getPortId() + "");
    		portMap.put("key", port.getPortName() + "," + port.getPortNameEn());
    	});
    	
    	/**
    	 * 保税区港口列表
    	 * 格式:[{"text":"portName","value":"portId","key":"portName,portNameEn"},...]
    	 */
    	List<Map<String, String>> bondedAreaPortList = new ArrayList<Map<String, String>>();
		
    	List<PortVo> bondedAreaPorts = baseClient.findBondedAreaPortListForOffer();
    	bondedAreaPorts.forEach(port -> {
    		Map<String, String> bondedAreaPortMap = new HashMap<>();
    		bondedAreaPortList.add(bondedAreaPortMap);
    		bondedAreaPortMap.put("text", port.getPortName());
    		bondedAreaPortMap.put("value", port.getPortId() + "");
    		bondedAreaPortMap.put("key", port.getPortName() + "," + port.getPortNameEn());
    	});
    	
    	/**
    	 * 指标类型列表
    	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
    	 */
    	List<Map<String, String>> indicatorTypeList = new ArrayList<Map<String, String>>();
    	
    	AttributeValueOptionVo attributeValueOptionVo = new AttributeValueOptionVo();
    	attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_IRON_INDICATOR_TYPE);
    	
    	List<AttributeValueOptionVo> indicatorTypes = 
    			baseClient.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
    	indicatorTypes.forEach(indicatorType -> {
    		Map<String, String> indicatorTypeMap = new HashMap<>();
    		indicatorTypeList.add(indicatorTypeMap);
    		indicatorTypeMap.put("text", indicatorType.getOptionValue());
    		indicatorTypeMap.put("value", indicatorType.getOptionId() + "");
    		indicatorTypeMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
    	});
    	
    	/**
    	 * 价格术语列表
    	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
    	 */
		List<Map<String, String>> priceTermList = new ArrayList<Map<String, String>>();
    	
    	attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_PRICE_TERM);
    	List<AttributeValueOptionVo> priceTerms = 
    			baseClient.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
    	priceTerms.forEach(indicatorType -> {
    		Map<String, String> priceTermMap = new HashMap<>();
    		priceTermList.add(priceTermMap);
    		priceTermMap.put("text", indicatorType.getOptionValue());
    		priceTermMap.put("value", indicatorType.getOptionId() + "");
    		priceTermMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
    	});
    	
    	/**
    	 * 计量方式列表
    	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
    	 */
		List<Map<String, String>> measureMethodList = new ArrayList<Map<String, String>>();
    	
    	attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_IRON_MEASURE_METHOD);
    	List<AttributeValueOptionVo> measureMethods = 
    			baseClient.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
    	measureMethods.forEach(indicatorType -> {
    		Map<String, String> measureMethodMap = new HashMap<>();
    		measureMethodList.add(measureMethodMap);
    		measureMethodMap.put("text", indicatorType.getOptionValue());
    		measureMethodMap.put("value", indicatorType.getOptionId() + "");
    		measureMethodMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
    	});
    	
    	/**
    	 * 计价方式列表
    	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
    	 */
		List<Map<String, String>> pricingMethodList = new ArrayList<Map<String, String>>();
    	
    	attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_IRON_PRICING_METHOD);
    	List<AttributeValueOptionVo> pricingMethods = 
    			baseClient.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
    	pricingMethods.forEach(indicatorType -> {
    		Map<String, String> pricingMethodMap = new HashMap<>();
    		pricingMethodList.add(pricingMethodMap);
    		pricingMethodMap.put("text", indicatorType.getOptionValue());
    		pricingMethodMap.put("value", indicatorType.getOptionId() + "");
    		pricingMethodMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
    	});
    	
    	/**
    	 * 交易方式
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> tradeModeList = new ArrayList<Map<String, String>>();
    	
    	Map<String, String> tradeModeMap = new HashMap<>();
    	tradeModeList.add(tradeModeMap);
    	tradeModeMap.put("text", "港口现货");
    	tradeModeMap.put("value", EsteelConstant.TRADE_MODE_INSTOCK + "");
    	tradeModeMap.put("key", "InStock");
    	
    	tradeModeMap = new HashMap<>();
    	tradeModeList.add(tradeModeMap);
    	tradeModeMap.put("text", "远期现货");
    	tradeModeMap.put("value", EsteelConstant.TRADE_MODE_FUTURES + "");
    	tradeModeMap.put("key", "Futures");
    	
    	tradeModeMap = new HashMap<>();
    	tradeModeList.add(tradeModeMap);
    	tradeModeMap.put("text", "点价");
    	tradeModeMap.put("value", EsteelConstant.TRADE_MODE_PRICING + "");
    	tradeModeMap.put("key", "Pricing");
    	
    	Map<String, String> yesMap = new HashMap<>();
    	yesMap.put("text", "否");
    	yesMap.put("value", EsteelConstant.NO + "");
    	yesMap.put("key", "Yes");
    	Map<String, String> noMap = new HashMap<>();
    	noMap.put("text", "是");
    	noMap.put("value", EsteelConstant.YES + "");
    	noMap.put("key", "No");
    	
    	/**
    	 * 是否一船多货
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> isMultiCargoList = new ArrayList<Map<String, String>>();
    	isMultiCargoList.add(yesMap);
    	isMultiCargoList.add(noMap);
    	
    	/**
    	 * 是否拆分
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> isSplitList = new ArrayList<Map<String, String>>();
    	isSplitList.add(yesMap);
    	isSplitList.add(noMap);
    	
    	/**
    	 * 是否在保税区
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> isBondedAreaList = new ArrayList<Map<String, String>>();
    	isBondedAreaList.add(yesMap);
    	isBondedAreaList.add(noMap);
    	
    	/**
    	 * 价格模式
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> priceModelList = new ArrayList<Map<String, String>>();
    	
    	Map<String, String> map = new HashMap<>();
    	priceModelList.add(map);
    	map.put("text", "固定价");
    	map.put("value", EsteelConstant.PRICE_MODEL_FIX + "");
    	map.put("key", "Fix");
    	
    	map = new HashMap<>();
    	priceModelList.add(map);
    	map.put("text", "浮动价");
    	map.put("value", EsteelConstant.PRICE_MODEL_FLOAT + "");
    	map.put("key", "Float");
    	
    	/**
    	 * 是否匿名
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> isAnonymousList = new ArrayList<Map<String, String>>();
    	isAnonymousList.add(yesMap);
    	isAnonymousList.add(noMap);
    	/* 页面数据组装 结束 */
    	
    	/* 页面数据传输 开始 */
    	// 计量方式列表
    	model.addAttribute("measureMethodList", measureMethods);
    	// 计价方式列表
    	model.addAttribute("pricingMethodList", pricingMethods);
    	model.addAttribute("pricingMethodListJson", JSONArray.toJSONString(pricingMethods));
    	
    	// 品名列表Json
    	model.addAttribute("ironCommodityJson", JSONArray.toJSONString(ironCommodityList));
    	// 铁矿品名属性值联动列表Json
    	model.addAttribute("ironAttributeLinkJson", JSONObject.toJSONString(ironAttributeLinkMap));
    	// 铁矿品名装货港联动列表Json
    	model.addAttribute("ironLoadingPortJson", JSONObject.toJSONString(ironLoadingPortMap));
    	// 港口列表Json
    	model.addAttribute("portJson", JSONArray.toJSONString(portList));
    	// 保税区港口列表Json
    	model.addAttribute("bondedAreaPortJson", JSONArray.toJSONString(bondedAreaPortList));
    	// 指标类型列表Json
    	model.addAttribute("indicatorTypeJson", JSONArray.toJSONString(indicatorTypeList));
    	// 价格术语列表Json
    	model.addAttribute("priceTermJson", JSONArray.toJSONString(priceTermList));
    	// 计量方式列表Json
    	model.addAttribute("measureMethodJson", JSONArray.toJSONString(measureMethodList));
    	// 计价方式列表Json
    	model.addAttribute("pricingMethodJson", JSONArray.toJSONString(pricingMethodList));
    	// 交易方式Json
    	model.addAttribute("tradeModeJson", JSONArray.toJSONString(tradeModeList));
    	// 是否一船多货Json
    	model.addAttribute("isMultiCargoJson", JSONArray.toJSONString(isMultiCargoList));
    	// 是否拆分Json
    	model.addAttribute("isSplitJson", JSONArray.toJSONString(isSplitList));
    	// 是否在保税区Json
    	model.addAttribute("isBondedAreaJson", JSONArray.toJSONString(isBondedAreaList));
    	// 价格模式Json
    	model.addAttribute("priceModelJson", JSONArray.toJSONString(priceModelList));
    	// 是否匿名Json
    	model.addAttribute("isAnonymousJson", JSONArray.toJSONString(isAnonymousList));
    	/* 页面数据传输 结束 */
    	
        return "/offer/addOffer";
    }
    
    @RequestMapping(value = "/saveOffer", method = RequestMethod.POST)
    public String saveOffer(IronOfferBaseVo offerVo, Model model){
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
    
    @RequestMapping(value = "/saveInStockOffer", method = RequestMethod.POST)
    public String saveInStockOffer(IronInStockOfferVo inStockOfferVo, @RequestParam("offerAffix") MultipartFile offerAffix, 
    		IronOfferClauseVo offerClauseVo, @RequestParam("contractAffix") MultipartFile contractAffix, Model model){
    	if (inStockOfferVo == null) {
    		model.addAttribute("msg", "提交失败！");
			
			model.addAttribute("offerClauseVo", offerClauseVo);
		    
		    return "/offer/addOffer";
    	}
    	
    	if (offerClauseVo == null) {
    		model.addAttribute("msg", "提交失败！");
			
			model.addAttribute("offerVo", inStockOfferVo);
		    
		    return "/offer/addOffer";
    	}
    	
    	// 报盘附件保存 tfs
    	StatusMSGVo msg = getTfsFileName(offerAffix, "报盘附件");
    	
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		model.addAttribute("offerVo", inStockOfferVo);
    		model.addAttribute("offerClauseVo", offerClauseVo);
    		
    		return "/offer/addOffer";
    	}
    	
    	// tfs返回ID
    	inStockOfferVo.setOfferAffixPath(msg.getMsg());
    	
    	// 合同附件保存 tfs
    	msg = getTfsFileName(contractAffix, "合同附件");
    	
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		model.addAttribute("offerVo", inStockOfferVo);
    		model.addAttribute("offerClauseVo", offerClauseVo);
    		
    		return "/offer/addOffer";
    	}
    	
    	// tfs返回ID
    	inStockOfferVo.setContractAffixPath(msg.getMsg());
    	
    	inStockOfferVo.setCompanyId(1);
    	
    	try {
//    		offerClient.saveOffer(offerVo);
    	} catch (Exception e) {
			e.printStackTrace();
			
			model.addAttribute("msg", "新增失败");
			
			model.addAttribute("offerVo", inStockOfferVo);
		    
		    return "/offer/addOffer";
		}
    	
    	model.addAttribute("msg", "新增成功");
    	
        return "redirect:/offer/myOffer";
    }
    
    @RequestMapping(value = "/saveFuturesOffer", method = RequestMethod.POST)
    public String saveFuturesOffer(IronFuturesOfferVo futuresOfferVo, @RequestParam("offerAffix") MultipartFile offerAffix, 
    		IronOfferClauseVo offerClauseVo, @RequestParam("contractAffix") MultipartFile contractAffix, Model model){
    	if (futuresOfferVo == null) {
    		model.addAttribute("msg", "提交失败！");
			
			model.addAttribute("offerClauseVo", offerClauseVo);
		    
		    return "/offer/addOffer";
    	}
    	
    	if (offerClauseVo == null) {
    		model.addAttribute("msg", "提交失败！");
			
			model.addAttribute("offerVo", futuresOfferVo);
		    
		    return "/offer/addOffer";
    	}
    	
    	// 报盘附件保存 tfs
    	StatusMSGVo msg = getTfsFileName(offerAffix, "报盘附件");
    	
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		model.addAttribute("offerVo", futuresOfferVo);
    		model.addAttribute("offerClauseVo", offerClauseVo);
    		
    		return "/offer/addOffer";
    	}
    	
    	// tfs返回ID
    	futuresOfferVo.setOfferAffixPath(msg.getMsg());
    	
    	// 合同附件保存 tfs
    	msg = getTfsFileName(contractAffix, "合同附件");
    	
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		model.addAttribute("offerVo", futuresOfferVo);
    		model.addAttribute("offerClauseVo", offerClauseVo);
    		
    		return "/offer/addOffer";
    	}
    	
    	// tfs返回ID
    	futuresOfferVo.setContractAffixPath(msg.getMsg());
    	
    	futuresOfferVo.setCompanyId(1);
    	
    	try {
//    		offerClient.saveOffer(offerVo);
    	} catch (Exception e) {
			e.printStackTrace();
			
			model.addAttribute("msg", "新增失败");
			
			model.addAttribute("offerVo", futuresOfferVo);
		    
		    return "/offer/addOffer";
		}
    	
    	model.addAttribute("msg", "新增成功");
    	
        return "redirect:/offer/myOffer";
    }
    
    /**
     * 报盘页面上传附件保存 tfs
     * @param multipartFile
     * @return
     */
    private StatusMSGVo getTfsFileName(MultipartFile file, String affixName) {
    	if (file == null) {
    		return null;
    	}
    	
    	StatusMSGVo vo = new StatusMSGVo();

		String fileName = file.getOriginalFilename();// 获取上传文件名,包括路径
	    long size = file.getSize();
	    if ((fileName == null || fileName.equals("")) && size == 0) {
	    	vo.setStatus(1);
	    	vo.setMsg(affixName + "上传失败：请上传有效文件！");
	    }
	    
	    String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	    
		// 支持拓展名:doc,docx,xls,xlsx,pdf,zip,rar,7z
	    if (!Pattern.matches("^((docx?)|(xlsx?)|(pdf)|(zip)|(rar)|(7z))$", fileType)) {
	    	vo.setStatus(1);
	    	vo.setMsg(affixName + "上传失败：请上传扩展名为：doc,docx,xls,xlsx,pdf,zip,rar,7z的文件！");
	    }
	    
	    if (size > 1024 * 1024) {
	    	vo.setStatus(1);
	    	vo.setMsg(affixName + "上传失败：请上传小于1MB的文件！");
	    }
	    
	    try {
	    	String tfsFileName = tfsManager.saveFile(file.getBytes(), null, fileType);
			
			vo.setStatus(0);
	    	vo.setMsg(tfsFileName);
	    	
		} catch (IOException e) {
			e.printStackTrace();
			
			vo.setStatus(1);
	    	vo.setMsg(affixName + "保存失败：请稍后再操作！");
		}
	    
    	return vo;
    }
}
