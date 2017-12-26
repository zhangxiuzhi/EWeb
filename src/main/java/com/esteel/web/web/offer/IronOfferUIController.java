package com.esteel.web.web.offer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteel.common.controller.WebReturnMessage;
import com.esteel.common.util.EsteelConstant;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.MemberClient;
import com.esteel.web.service.OfferClient;
import com.esteel.web.vo.base.CommodityCategoryEnum;
import com.esteel.web.vo.base.CommodityVo;
import com.esteel.web.vo.base.PortVo;
import com.esteel.web.vo.config.AttributeValueOptionVo;
import com.esteel.web.vo.config.IronAttributeLinkVo;
import com.esteel.web.vo.offer.IronOfferMainVo;
import com.esteel.web.vo.offer.OfferIronAttachVo.IronFuturesOffer;
import com.esteel.web.vo.offer.OfferIronAttachVo.IronInStockOffer;
import com.esteel.web.vo.offer.OfferIronAttachVo.IronPricingOffer;
import com.esteel.web.vo.offer.request.IronFuturesOfferRequest;
import com.esteel.web.vo.offer.request.IronFuturesTransportRequest;
import com.esteel.web.vo.offer.request.IronInStockOfferRequest;
import com.esteel.web.vo.offer.request.IronOfferClauseRequest;
import com.esteel.web.vo.offer.request.IronOfferQueryVo;
import com.esteel.web.vo.offer.request.IronPricingOfferRequest;
import com.taobao.common.tfs.TfsManager;
import com.taobao.tair.json.JSONArray;
import com.taobao.tair.json.JSONObject;

import reactor.core.support.Assert;

/**
 * 
 * @ClassName: OfferController
 * @Description: 铁矿报盘 controller
 * @author wyf
 * @date 2017年12月22日 上午11:31:57 
 *
 */
@RequestMapping("/offer/iron")
@Controller
public class IronOfferUIController {
	@Autowired
	BaseClient baseClient;
	@Autowired
	MemberClient memberClient;
	@Autowired
	OfferClient offerClient;
	@Autowired
    TfsManager tfsManager;
	
	/**
	 * 跳转铁矿报盘新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addIronOfferUI(Model model) {
		loadData(true, true, true, model);
		
		return "/offer/addOffer";
	}
	
	/**
	 * 跳转铁矿报盘编辑页面
	 * @param offerCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{offerCode}", method = RequestMethod.GET)
	public String editIronOfferUI(@PathVariable("offerCode") String offerCode, Model model) {
		Assert.notNull(offerCode, "点击失败！");

		IronOfferQueryVo queryVo = new IronOfferQueryVo();
		queryVo.setOfferCode(offerCode);

		IronOfferMainVo offer = offerClient.getIronOffer(queryVo);
		model.addAttribute("offer", offer);

		if (offer.getTradeMode() == EsteelConstant.TRADE_MODE_INSTOCK) {
			loadData(true, false, false, model);

			return "/offer/edit/inStock";
		} else if (offer.getTradeMode() == EsteelConstant.TRADE_MODE_PRICING) {
			loadData(false, true, false, model);

			return "/offer/edit/pricing";
		} else if (offer.getTradeMode() == EsteelConstant.TRADE_MODE_FUTURES) {
			loadData(false, false, true, model);

			return "/offer/edit/futures";
		}

		return "/offer/edit/inStock";
	}
	
	/**
     * 现货报盘验证
     * @param inStockOfferRequest
     * @param offerResult
     * @param offerClauseVo
     * @param clauseResult
     * @return
     */
    @RequestMapping(value = "/validatedInStockOffer", method = RequestMethod.POST)
    @ResponseBody
    public WebReturnMessage validatedInStockOffer(
    		@Validated(IronInStockOffer.class) IronInStockOfferRequest inStockOfferRequest, BindingResult offerResult, 
    		@Validated IronOfferClauseRequest clauseRequest, BindingResult clauseResult) {
    	WebReturnMessage webRetMesage = new WebReturnMessage(false, "提交失败！");
    	
    	Assert.notNull(inStockOfferRequest, "提交失败！");
    	
    	Assert.notNull(clauseRequest, "提交失败！");
    	
    	StringBuilder msgSB = new StringBuilder();
		if(offerResult.hasErrors()) {
			offerResult.getFieldErrors().forEach(fieldError -> msgSB.append(fieldError.getDefaultMessage()));
		}
		
		if(clauseResult.hasErrors()) {
			clauseResult.getFieldErrors().forEach(fieldError -> msgSB.append(fieldError.getDefaultMessage()));
		}
		
		if (msgSB.length() > 0) {
			webRetMesage.setMsg(msgSB.toString());
			
		    return webRetMesage;
		}
		
		webRetMesage = new WebReturnMessage(true, "success");
		
		return webRetMesage;
    }
    
    /**
     * 远期报盘验证
     * @param futuresOfferRequest
     * @param offerResult
     * @param transportDescription
     * @param transportResult
     * @return
     */
    @RequestMapping(value = "/validatedFuturesOffer", method = RequestMethod.POST)
    @ResponseBody
    public WebReturnMessage validatedFuturesOffer(
    		@Validated(IronFuturesOffer.class) IronFuturesOfferRequest futuresOfferRequest, BindingResult offerResult, 
    		@Validated IronFuturesTransportRequest transportRequest, BindingResult transportResult) {
    	WebReturnMessage webRetMesage = new WebReturnMessage(false, "提交失败！");
    	
    	Assert.notNull(futuresOfferRequest, "提交失败！");
    	
    	Assert.notNull(transportRequest, "提交失败！");
    	
    	StringBuilder msgSB = new StringBuilder();
		if(offerResult.hasErrors()) {
			offerResult.getFieldErrors().forEach(fieldError -> msgSB.append(fieldError.getDefaultMessage()));
		}
		
		if(transportResult.hasErrors()) {
			transportResult.getFieldErrors().forEach(fieldError -> msgSB.append(fieldError.getDefaultMessage()));
		}
		
		if (msgSB.length() > 0) {
			webRetMesage.setMsg(msgSB.toString());
			
		    return webRetMesage;
		}
		
		webRetMesage = new WebReturnMessage(true, "success");
		
		return webRetMesage;
    }
    
    /**
     * 点价报盘验证
     * @param inStockOfferRequest
     * @param offerResult
     * @param offerClauseVo
     * @param clauseResult
     * @return
     */
    @RequestMapping(value = "/validatedPricingOffer", method = RequestMethod.POST)
    @ResponseBody
    public WebReturnMessage validatedPricingOffer(
    		@Validated(IronPricingOffer.class) IronPricingOfferRequest pricingOfferRequest, BindingResult offerResult, 
    		@Validated IronOfferClauseRequest clauseRequest, BindingResult clauseResult) {
    	WebReturnMessage webRetMesage = new WebReturnMessage(false, "提交失败！");
    	
    	Assert.notNull(pricingOfferRequest, "提交失败！");
    	
    	Assert.notNull(clauseRequest, "提交失败！");
    	
    	StringBuilder msgSB = new StringBuilder();
		if(offerResult.hasErrors()) {
			offerResult.getFieldErrors().forEach(fieldError -> msgSB.append(fieldError.getDefaultMessage()));
		}
		
		if(clauseResult.hasErrors()) {
			clauseResult.getFieldErrors().forEach(fieldError -> msgSB.append(fieldError.getDefaultMessage()));
		}
		
		if (msgSB.length() > 0) {
			webRetMesage.setMsg(msgSB.toString());
			
		    return webRetMesage;
		}
		
		webRetMesage = new WebReturnMessage(true, "success");
		
		return webRetMesage;
    }
    
    /**
     * 新增 编辑页面数据 加载
     * @param isInStock
     * @param isPricing
     * @param isFutures
     * @param model
     */
    private void loadData(boolean isInStock, boolean isPricing, boolean isFutures, Model model) {
    	/* 查询 开始 */
    	// 品名列表
    	List<CommodityVo> ironCommoditys = baseClient.findCommodityListByIron();
    	// 铁矿品名属性值联动列表
    	CommodityVo queryVo = new CommodityVo();
    	queryVo.setCategoryId(CommodityCategoryEnum.getInstance().IRON.getId());
    	List<IronAttributeLinkVo> ironAttributeList = baseClient.findAttributeListByIron(queryVo);
    	// 装货港列表
    	List<PortVo> loadingPorts = baseClient.findLoadingPortListForOffer();
    	// 港口列表
    	List<PortVo> ports = baseClient.findPortListForOffer();
    	// 点价交易港口列表
    	List<PortVo> pricingPorts = baseClient.findPortListForPricingOffer();
    	// 保税区港口列表
    	List<PortVo> bondedAreaPorts = baseClient.findBondedAreaPortListForOffer();
    	// 指标类型列表
    	AttributeValueOptionVo attributeValueOptionVo = new AttributeValueOptionVo();
    	attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_IRON_INDICATOR_TYPE);
		List<AttributeValueOptionVo> indicatorTypes = baseClient
				.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
		// 价格术语列表
		attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_PRICE_TERM);
		List<AttributeValueOptionVo> priceTerms = baseClient
				.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
		// 计量方式列表
		attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_IRON_MEASURE_METHOD);
		List<AttributeValueOptionVo> measureMethods = baseClient
				.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
		// 计价方式列表
		attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_IRON_PRICING_METHOD);
		List<AttributeValueOptionVo> pricingMethods = baseClient
				.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
		// 交易者类型列表
		attributeValueOptionVo.setAttributeCode(EsteelConstant.ATTRIBUTE_CODE_TRADER_TYPE);
		List<AttributeValueOptionVo> traderTypes = baseClient
				.findAttributeValueOptionListByAttributeCode(attributeValueOptionVo);
		/* 查询 结束 */
		
    	/* 页面数据组装 开始 */
		/**
		 * 会员白名单列表
		 */
		List<Map<String, String>> counterpartyList = new ArrayList<Map<String, String>>();
    	
    	Map<String, String> counterpartyMap = new HashMap<>();
    	counterpartyList.add(counterpartyMap);
    	counterpartyMap.put("text", "测试1");
    	counterpartyMap.put("value", "1");
    	counterpartyMap.put("key", "测试1,one");
    	
    	counterpartyMap = new HashMap<>();
     	counterpartyList.add(counterpartyMap);
     	counterpartyMap.put("text", "测试2");
     	counterpartyMap.put("value", "2");
     	counterpartyMap.put("key", "测试2,two");
     	
     	counterpartyMap = new HashMap<>();
     	counterpartyList.add(counterpartyMap);
     	counterpartyMap.put("text", "测试3");
     	counterpartyMap.put("value", "3");
     	counterpartyMap.put("key", "测试3,three");
     	
    	/**
    	 * 品名列表
    	 * 格式:[{"text":"commodityName","value":"commodityId","key":"commodityAlias"},...]
    	 */
    	List<Map<String, String>> ironCommodityList = new ArrayList<Map<String, String>>();
    	ironCommoditys.forEach(commodityVo -> {
    		Map<String, String> ironCommodityMap = new HashMap<>();
    		ironCommodityList.add(ironCommodityMap);
    		ironCommodityMap.put("text", commodityVo.getCommodityName());
    		ironCommodityMap.put("value", commodityVo.getCommodityId() + "");
    		ironCommodityMap.put("key", commodityVo.getCommodityAlias());
    	});
    	// 页面数据传输
    	model.addAttribute("ironCommodityJson", JSONArray.toJSONString(ironCommodityList));
    	
    	/**
    	 * 港口列表
    	 * 格式:[{"text":"portName","value":"portId","key":"portName,portNameEn"},...]
    	 */
		List<Map<String, String>> portList = new ArrayList<Map<String, String>>();
    	ports.forEach(port -> {
    		Map<String, String> portMap = new HashMap<>();
    		portList.add(portMap);
    		portMap.put("text", port.getPortName());
    		portMap.put("value", port.getPortId() + "");
    		portMap.put("key", port.getPortName() + "," + port.getPortNameEn());
    	});
    	// 页面数据传输
    	model.addAttribute("portJson", JSONArray.toJSONString(portList));
    	
    	if (isInStock || isFutures) {
    		/**
        	 * 指标类型列表
        	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
        	 */
        	List<Map<String, String>> indicatorTypeList = new ArrayList<Map<String, String>>();
        	indicatorTypes.forEach(indicatorType -> {
        		Map<String, String> indicatorTypeMap = new HashMap<>();
        		indicatorTypeList.add(indicatorTypeMap);
        		indicatorTypeMap.put("text", indicatorType.getOptionValue());
        		indicatorTypeMap.put("value", indicatorType.getOptionId() + "");
        		indicatorTypeMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
        	});
        	// 页面数据传输
        	model.addAttribute("indicatorTypeJson", JSONArray.toJSONString(indicatorTypeList));
        	
        	/**
        	 * 铁矿品名属性值联动列表
        	 * 格式:{"commodityName":[{"text":"attributeCode","value":"attributeValue","key":"attributeCode"},...],...}
        	 */
        	Map<String, List<Map<String, String>>> ironAttributeLinkMap = new HashMap<>();
        	for (CommodityVo commodityVo : ironCommoditys) {
        		/**
            	 * 某品名属性值集合
            	 * 格式:[{"text":"attributeCode","value":"attributeValue","key":"attributeCode"},...]
            	 */
        		List<Map<String, String>> ironAttributes = new ArrayList<Map<String, String>>();
        		ironAttributeLinkMap.put(commodityVo.getCommodityName(), ironAttributes);
        		
        		List  list = ironAttributeList.stream().filter(attribute -> attribute.getCommodityCode() != null && attribute.getCommodityCode().equals(commodityVo.getCommodityCode()))
            			.collect(Collectors.toList());
        		ironAttributeList.stream().filter(attribute -> attribute.getCommodityCode() != null && attribute.getCommodityCode().equals(commodityVo.getCommodityCode()))
        			.collect(Collectors.toList()).forEach(attribute ->{
        			Map<String, String> ironAttributeMap = new HashMap<>();
        			ironAttributes.add(ironAttributeMap);
        			ironAttributeMap.put("text", attribute.getAttributeCode());
        			ironAttributeMap.put("value", attribute.getAttributeValue());
        			ironAttributeMap.put("key", attribute.getAttributeCode());
        		});
        	}
        	// 页面数据传输
        	model.addAttribute("ironAttributeLinkJson", JSONObject.toJSONString(ironAttributeLinkMap));
    	}
    	
    	
    	if (isPricing) {
    		/**
        	 * 点价交易港口列表
        	 * 格式:[{"text":"portName","value":"portId","key":"portName,portNameEn"},...]
        	 */
        	List<Map<String, String>> pricingPortList = new ArrayList<Map<String, String>>();
        	pricingPorts.forEach(port -> {
        		Map<String, String> pricingPortMap = new HashMap<>();
        		pricingPortList.add(pricingPortMap);
        		pricingPortMap.put("text", port.getPortName());
        		pricingPortMap.put("value", port.getPortId() + "");
        		pricingPortMap.put("key", port.getPortName() + "," + port.getPortNameEn());
        	});
        	// 页面数据传输
        	model.addAttribute("pricingPortJson", JSONArray.toJSONString(pricingPortList));
    	}
    	
    	if (isFutures) {
    		/**
        	 * 装货港列表
        	 * 格式:[{"text":"portName","value":"portId","key":"portName,portNameEn"},...]
        	 */
        	List<Map<String, String>> loadingPortList = new ArrayList<Map<String, String>>();
    		loadingPorts.forEach(port -> {
    			Map<String, String> loadingPortMap = new HashMap<>();
    			loadingPortList.add(loadingPortMap);
    			loadingPortMap.put("text", port.getPortName());
    			loadingPortMap.put("value", port.getPortId() + "");
    			loadingPortMap.put("key", port.getPortName() + "," + port.getPortNameEn());
        	});
    		// 页面数据传输
    		model.addAttribute("loadingPortJson", JSONArray.toJSONString(loadingPortList));
    		
        	/**
        	 * 保税区港口列表
        	 * 格式:[{"text":"portName","value":"portId","key":"portName,portNameEn"},...]
        	 */
        	List<Map<String, String>> bondedAreaPortList = new ArrayList<Map<String, String>>();
        	bondedAreaPorts.forEach(port -> {
        		Map<String, String> bondedAreaPortMap = new HashMap<>();
        		bondedAreaPortList.add(bondedAreaPortMap);
        		bondedAreaPortMap.put("text", port.getPortName());
        		bondedAreaPortMap.put("value", port.getPortId() + "");
        		bondedAreaPortMap.put("key", port.getPortName() + "," + port.getPortNameEn());
        	});
        	// 页面数据传输
        	model.addAttribute("bondedAreaPortJson", JSONArray.toJSONString(bondedAreaPortList));
    	}
    	
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
    	 * 是否拆分
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> isSplitList = new ArrayList<Map<String, String>>();
    	isSplitList.add(yesMap);
    	isSplitList.add(noMap);
    	
    	if (isFutures) {
    		/**
        	 * 是否一船多货
        	 * 格式:[{"text":"","value":"","key":""},...]
        	 */
        	List<Map<String, String>> isMultiCargoList = new ArrayList<Map<String, String>>();
        	isMultiCargoList.add(yesMap);
        	isMultiCargoList.add(noMap);
        	// 页面数据传输
        	model.addAttribute("isMultiCargoJson", JSONArray.toJSONString(isMultiCargoList));
        	
        	/**
        	 * 是否在保税区
        	 * 格式:[{"text":"","value":"","key":""},...]
        	 */
        	List<Map<String, String>> isBondedAreaList = new ArrayList<Map<String, String>>();
        	isBondedAreaList.add(yesMap);
        	isBondedAreaList.add(noMap);
        	// 页面数据传输
        	model.addAttribute("isBondedAreaJson", JSONArray.toJSONString(isBondedAreaList));
    	}
    	
    	Map<String, String> map = new HashMap<>();
    	
    	if (isFutures) {
    		/**
        	 * 价格模式
        	 * 格式:[{"text":"","value":"","key":""},...]
        	 */
        	List<Map<String, String>> priceModelList = new ArrayList<Map<String, String>>();
        	
        	priceModelList.add(map);
        	map.put("text", "固定价");
        	map.put("value", EsteelConstant.PRICE_MODEL_FIX + "");
        	map.put("key", "Fix");
        	
        	map = new HashMap<>();
        	priceModelList.add(map);
        	map.put("text", "浮动价");
        	map.put("value", EsteelConstant.PRICE_MODEL_FLOAT + "");
        	map.put("key", "Float");
        	
        	// 页面数据传输
        	model.addAttribute("priceModelJson", JSONArray.toJSONString(priceModelList));
        	
        	/**
        	 * 价格术语列表
        	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
        	 */
    		List<Map<String, String>> priceTermList = new ArrayList<Map<String, String>>();
        	priceTerms.forEach(indicatorType -> {
        		Map<String, String> priceTermMap = new HashMap<>();
        		priceTermList.add(priceTermMap);
        		priceTermMap.put("text", indicatorType.getOptionValue());
        		priceTermMap.put("value", indicatorType.getOptionValue() + "");
        		priceTermMap.put("key", indicatorType.getOptionValue());
        	});
        	// 页面数据传输
        	model.addAttribute("priceTermJson", JSONArray.toJSONString(priceTermList));
    	}
    	
    	if (isPricing) {
    		/**
        	 * 连铁合约
        	 * 格式:[{"text":"","value":"","key":""},...]
        	 */
        	List<Map<String, String>> ironContractList = new ArrayList<Map<String, String>>();
        	
        	Calendar calendar = Calendar.getInstance();
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        	for (int i = 0; i < 12; i ++) {
        		map = new HashMap<>();
        		ironContractList.add(map);
            	map.put("text", "i" + dateFormat.format(calendar.getTime()));
            	map.put("value", "i" + dateFormat.format(calendar.getTime()));
            	map.put("key", "i" + dateFormat.format(calendar.getTime()));
            	
            	calendar.add(Calendar.MONTH, 1);
        	}
        	// 页面数据传输
        	model.addAttribute("ironContractJson", JSONArray.toJSONString(ironContractList));
    	}
    	
    	/**
    	 * 是否匿名
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> isAnonymousList = new ArrayList<Map<String, String>>();
    	isAnonymousList.add(yesMap);
    	isAnonymousList.add(noMap);
    	
    	if (isInStock || isPricing) {
    		/**
        	 * 计量方式列表
        	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
        	 */
    		List<Map<String, String>> measureMethodList = new ArrayList<Map<String, String>>();
        	measureMethods.forEach(indicatorType -> {
        		Map<String, String> measureMethodMap = new HashMap<>();
        		measureMethodList.add(measureMethodMap);
        		measureMethodMap.put("text", indicatorType.getOptionValue());
        		measureMethodMap.put("value", indicatorType.getOptionId() + "");
        		measureMethodMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
        	});
        	// 页面数据传输
        	model.addAttribute("measureMethodJson", JSONArray.toJSONString(measureMethodList));
        	
        	/**
        	 * 计价方式列表
        	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
        	 */
    		List<Map<String, String>> pricingMethodList = new ArrayList<Map<String, String>>();
        	pricingMethods.forEach(indicatorType -> {
        		Map<String, String> pricingMethodMap = new HashMap<>();
        		pricingMethodList.add(pricingMethodMap);
        		pricingMethodMap.put("text", indicatorType.getOptionValue());
        		pricingMethodMap.put("value", indicatorType.getOptionId() + "");
        		pricingMethodMap.put("key", indicatorType.getOptionValue() + "," + indicatorType.getOptionValueEn());
        	});
        	// 页面数据传输
        	model.addAttribute("pricingMethodJson", JSONArray.toJSONString(pricingMethodList));
        	
        	/**
        	 * 交易者类型列表
        	 * 格式:[{"text":"optionValue","value":"optionId","key":"optionValue,optionValueEn"},...]
        	 */
    		List<Map<String, String>> traderTypeList = new ArrayList<Map<String, String>>();
    		traderTypes.forEach(traderType -> {
        		Map<String, String> traderTypeMap = new HashMap<>();
        		traderTypeList.add(traderTypeMap);
        		traderTypeMap.put("text", traderType.getOptionValue());
        		traderTypeMap.put("value", traderType.getOptionId() + "");
        		traderTypeMap.put("key", traderType.getOptionValue() + "," + traderType.getOptionValueEn());
        	});
        	// 页面数据传输
        	model.addAttribute("traderTypeJson", JSONArray.toJSONString(traderTypeList));
    	}
    	/* 页面数据组装 结束 */
    	
    	/* 页面数据传输 开始 */
    	// 交易方式Json
    	model.addAttribute("tradeModeJson", JSONArray.toJSONString(tradeModeList));
     	// 公司白名单列表Json
     	model.addAttribute("companyWhitelistJson", JSONArray.toJSONString(counterpartyList));
    	// 是否拆分Json
    	model.addAttribute("isSplitJson", JSONArray.toJSONString(isSplitList));
    	// 是否匿名Json
    	model.addAttribute("isAnonymousJson", JSONArray.toJSONString(isAnonymousList));
    	/* 页面数据传输 结束 */
    }
}

