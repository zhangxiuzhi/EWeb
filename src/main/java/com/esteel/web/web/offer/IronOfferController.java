package com.esteel.web.web.offer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.esteel.common.util.EsteelConstant;
import com.esteel.common.util.JsonUtils;
import com.esteel.common.vo.StatusMSGVo;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.MemberClient;
import com.esteel.web.service.OfferClient;
import com.esteel.web.vo.base.CommodityVo;
import com.esteel.web.vo.base.PortVo;
import com.esteel.web.vo.config.AttributeValueOptionEnum;
import com.esteel.web.vo.offer.IronFuturesOfferRequest;
import com.esteel.web.vo.offer.IronFuturesTransportVo;
import com.esteel.web.vo.offer.IronInStockOfferRequest;
import com.esteel.web.vo.offer.IronOfferClauseVo;
import com.esteel.web.vo.offer.IronOfferMainVo;
import com.esteel.web.vo.offer.IronPricingOfferRequest;
import com.esteel.web.vo.offer.OfferAffixVo;
import com.esteel.web.vo.offer.OfferIronAttachVo;
import com.esteel.web.vo.offer.OfferIronAttachVo.IronPricingOffer;
import com.taobao.common.tfs.TfsManager;

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
public class IronOfferController {
	@Autowired
	BaseClient baseClient;
	@Autowired
	MemberClient memberClient;
	@Autowired
	OfferClient offerClient;
	@Autowired
    TfsManager tfsManager;
	
    /**
     * 现货报盘保存
     * @param inStockOfferRequest
     * @param offerAffix
     * @param offerClauseVo
     * @param contractAffix
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveInStockOffer", method = RequestMethod.POST)
    public String saveInStockOffer(IronInStockOfferRequest inStockOfferRequest, 
    		@RequestParam("offerAffix") MultipartFile offerAffix,  IronOfferClauseVo offerClauseVo, 
    		@RequestParam("contractAffix") MultipartFile contractAffix, Model model) {
    	Assert.notNull(inStockOfferRequest, "提交失败！");
    	
    	Assert.notNull(offerClauseVo, "提交失败！");

    	IronOfferMainVo offerMainVo = new IronOfferMainVo();
    	// 将request 复制到 offerMainVo
    	BeanUtils.copyProperties(inStockOfferRequest, offerMainVo);
    	
    	// 指定对手(多选值)
    	List<Long> counterpartyIdList = null;
    	if (inStockOfferRequest.getCounterpartyIdMulti() != null && !inStockOfferRequest.getCounterpartyIdMulti().trim().equals("")) {
    		String[] counterpartyIdArr = inStockOfferRequest.getCounterpartyIdMulti().split(",");
    		
    		counterpartyIdList = 
    				Arrays.asList(counterpartyIdArr).stream()
    				.filter(counterpartyId -> counterpartyId != null && counterpartyId.trim().matches("^\\d+$"))
    				.map(counterpartyId -> Long.parseLong(counterpartyId.trim())).collect(Collectors.toList()); 
    	}
    	
    	if (counterpartyIdList != null && !counterpartyIdList.isEmpty()) {
    		// 是否指定 0:否, 1:是
    		offerMainVo.setIsDesignation("1");
    		offerMainVo.setCounterpartyIdList(counterpartyIdList);
    	}
    	
    	OfferIronAttachVo offerAttachVo = new OfferIronAttachVo();
    	// 将request 复制到 offerAttachVo
    	BeanUtils.copyProperties(inStockOfferRequest, offerAttachVo);
    	
    	offerMainVo.addOfferIronAttach(offerAttachVo);
    	
    	// 品名
    	CommodityVo commodity = new CommodityVo();
    	commodity.setCommodityId(Long.parseLong(inStockOfferRequest.getCommodityId()));
    	
    	CommodityVo commodityVo = baseClient.getCommodity(commodity);
    	if (commodityVo != null) {
    		offerAttachVo.setCommodityName(commodityVo.getCommodityName());
    	}
    	
    	// 港口
    	PortVo port = new PortVo();
    	port.setPortId(Long.parseLong(inStockOfferRequest.getPortId()));
    	
    	PortVo portVo = baseClient.getPort(port);
    	if (portVo != null) {
    		offerAttachVo.setPortName(portVo.getPortName());
    	}
    	
    	// 重量单位 湿吨
    	offerAttachVo.setQuantityUnitId(AttributeValueOptionEnum.getInstance().WMT.getId() + "");
    	// 价格单位 人民币/湿吨
    	offerAttachVo.setPriceUnitId(AttributeValueOptionEnum.getInstance().CNY_WMT.getId() + "");
    	
    	// 初始化
    	// 交易方式 1:现货
    	offerMainVo.setTradeMode(EsteelConstant.TRADE_MODE_INSTOCK);
    	
    	if (inStockOfferRequest.getOfferStatus().equals("publish")) {
    		// 铁矿报盘状态 :发布
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_IN_SALE + "");
        	offerMainVo.setPublishUser("王雁飞测试");
    	} else {
    		// 铁矿报盘状态 :草稿
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_DRAFT + "");
    	}
   	
    	offerMainVo.setCompanyId(1L);
    	offerMainVo.setCreateUser("王雁飞测试");
    	offerMainVo.setUpdateUser("王雁飞测试");
    	
    	// 保存附件
    	// tfs 报盘附件
    	StatusMSGVo msg = getTfsFileName(offerAffix, "报盘备注附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_REMARKS, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	// tfs 合同附件
    	msg = getTfsFileName(contractAffix, "报盘合同附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_CONTRACT, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	// 交货结算条款Json
    	offerMainVo.setClauseTemplateJson(JsonUtils.toJsonString(offerClauseVo));

    	System.out.println(JsonUtils.toJsonString(offerMainVo));
		
		// 保存
		IronOfferMainVo offer = offerClient.saveIronOffer(offerMainVo);
    	
		if (inStockOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "新增失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
        return "redirect:/offer/myOffer";
    }
    
    /**
     * 远期报盘保存
     * @param futuresOfferRequest
     * @param offerAffix
     * @param offerClauseVo
     * @param contractAffix
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveFuturesOffer", method = RequestMethod.POST)
    public String saveFuturesOffer(IronFuturesOfferRequest futuresOfferRequest, 
    		IronFuturesTransportVo transportDescription, 
    		@RequestParam("offerAffix") MultipartFile offerAffix, Model model){
    	Assert.notNull(futuresOfferRequest, "提交失败！");
    	
    	Assert.notNull(transportDescription, "提交失败！");
    	
    	IronOfferMainVo offerMainVo = new IronOfferMainVo();
    	// 将request 复制到 offerMainVo
    	BeanUtils.copyProperties(futuresOfferRequest, offerMainVo);
    	
    	// 指定对手(多选值)
    	List<Long> counterpartyIdList = null;
    	if (futuresOfferRequest.getCounterpartyIdMulti() != null && !futuresOfferRequest.getCounterpartyIdMulti().trim().equals("")) {
    		String[] counterpartyIdArr = futuresOfferRequest.getCounterpartyIdMulti().split(",");
    		
    		counterpartyIdList = 
    				Arrays.asList(counterpartyIdArr).stream()
    				.filter(counterpartyId -> counterpartyId != null && counterpartyId.trim().matches("^\\d+$"))
    				.map(counterpartyId -> Long.parseLong(counterpartyId.trim())).collect(Collectors.toList()); 
    	}
    	
    	if (counterpartyIdList != null && !counterpartyIdList.isEmpty()) {
    		// 是否指定 0:否, 1:是
    		offerMainVo.setIsDesignation("1");
    		offerMainVo.setCounterpartyIdList(counterpartyIdList);
    	}
    	
    	// 第一个货物报盘
    	OfferIronAttachVo firstCargo = getOne(futuresOfferRequest, 0);
    	
    	offerMainVo.addOfferIronAttach(firstCargo);
    	
    	// 品名
    	CommodityVo commodity = new CommodityVo();
    	commodity.setCommodityId(Long.parseLong(firstCargo.getCommodityId()));
    	CommodityVo commodityVo = baseClient.getCommodity(commodity);
    	if (commodityVo != null) {
    		firstCargo.setCommodityName(commodityVo.getCommodityName());
    	}
    	
    	// 港口
    	PortVo port = new PortVo();
    	port.setPortId(Long.parseLong(firstCargo.getPortId()));
    	
    	PortVo portVo = baseClient.getPort(port);
    	if (portVo != null) {
    		firstCargo.setPortName(portVo.getPortName());
    	}
    	
    	// 重量单位 湿吨
    	firstCargo.setQuantityUnitId(AttributeValueOptionEnum.getInstance().WMT.getId() + "");
    	// 价格单位 人民币/湿吨
    	firstCargo.setPriceUnitId(AttributeValueOptionEnum.getInstance().CNY_WMT.getId() + "");
    	
    	firstCargo.setTransportDescription(JsonUtils.toJsonString(transportDescription));
    	
    	// 一船两货
    	if (futuresOfferRequest.getIsMultiCargo().equals(EsteelConstant.YES + "")) {
    		// 第二个货物报盘
    		OfferIronAttachVo secondCargo = getOne(futuresOfferRequest, 1);
    		
    		offerMainVo.addOfferIronAttach(secondCargo);
    		
    		// 品名
        	commodity = new CommodityVo();
        	commodity.setCommodityId(Long.parseLong(secondCargo.getCommodityId()));
        	commodityVo = baseClient.getCommodity(commodity);
        	if (commodityVo != null) {
        		secondCargo.setCommodityName(commodityVo.getCommodityName());
        	}
        	
        	// 港口
        	port = new PortVo();
        	port.setPortId(Long.parseLong(secondCargo.getPortId()));
        	
        	portVo = baseClient.getPort(port);
        	if (portVo != null) {
        		secondCargo.setPortName(portVo.getPortName());
        	}
        	
        	// 重量单位 湿吨
        	secondCargo.setQuantityUnitId(AttributeValueOptionEnum.getInstance().WMT.getId() + "");
        	// 价格单位 人民币/湿吨
        	secondCargo.setPriceUnitId(AttributeValueOptionEnum.getInstance().CNY_WMT.getId() + "");
        	
    		secondCargo.setTransportDescription(JsonUtils.toJsonString(transportDescription));
    	}
    	
    	// 初始化
    	// 交易方式 3:远期
    	offerMainVo.setTradeMode(EsteelConstant.TRADE_MODE_FUTURES);
    	
    	if (futuresOfferRequest.getOfferStatus().equals("publish")) {
    		// 铁矿报盘状态 :发布
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_IN_SALE + "");
        	offerMainVo.setPublishUser("王雁飞测试");
    	} else {
    		// 铁矿报盘状态 :草稿
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_DRAFT + "");
    	}
   	
    	offerMainVo.setCompanyId(1L);
    	offerMainVo.setCreateUser("王雁飞测试");
    	offerMainVo.setUpdateUser("王雁飞测试");
    	
    	// 报盘附件保存 tfs
    	StatusMSGVo msg = getTfsFileName(offerAffix, "报盘备注附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_REMARKS, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	System.out.println(JsonUtils.toJsonString(offerMainVo));
    	
    	// 保存
		IronOfferMainVo offer = offerClient.saveIronOffer(offerMainVo);
    	
		if (futuresOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "新增失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
        return "redirect:/offer/myOffer";
    }
    
    /**
     * 点价报盘保存
     * @param pricingOfferRequest
     * @param offerAffix
     * @param offerClauseVo
     * @param contractAffix
     * @param model
     * @return
     */
    @RequestMapping(value = "/savePricingOffer", method = RequestMethod.POST)
    public String savePricingOffer(@Validated(IronPricingOffer.class) IronPricingOfferRequest pricingOfferRequest, BindingResult offerResult, 
    		@RequestParam("offerAffix") MultipartFile offerAffix, IronOfferClauseVo offerClauseVo, 
    		@RequestParam("contractAffix") MultipartFile contractAffix, Model model){
    	Assert.notNull(pricingOfferRequest, "提交失败！");
    	
    	Assert.notNull(offerClauseVo, "提交失败！");
    	
    	IronOfferMainVo offerMainVo = new IronOfferMainVo();
    	// 将request 复制到 offerMainVo
    	BeanUtils.copyProperties(pricingOfferRequest, offerMainVo);
    	
    	// 指定对手(多选值)
    	List<Long> counterpartyIdList = null;
    	if (pricingOfferRequest.getCounterpartyIdMulti() != null && !pricingOfferRequest.getCounterpartyIdMulti().trim().equals("")) {
    		String[] counterpartyIdArr = pricingOfferRequest.getCounterpartyIdMulti().split(",");
    		
    		counterpartyIdList = 
    				Arrays.asList(counterpartyIdArr).stream()
    				.filter(counterpartyId -> counterpartyId != null && counterpartyId.trim().matches("^\\d+$"))
    				.map(counterpartyId -> Long.parseLong(counterpartyId.trim())).collect(Collectors.toList()); 
    	}
    	
    	if (counterpartyIdList != null && !counterpartyIdList.isEmpty()) {
    		// 是否指定 0:否, 1:是
    		offerMainVo.setIsDesignation("1");
    		offerMainVo.setCounterpartyIdList(counterpartyIdList);
    	}
    	
    	OfferIronAttachVo offerAttachVo = new OfferIronAttachVo();
    	// 将request 复制到 offerAttachVo
    	BeanUtils.copyProperties(pricingOfferRequest, offerAttachVo);
    	
    	offerMainVo.addOfferIronAttach(offerAttachVo);
    	
    	// 品名
    	CommodityVo commodity = new CommodityVo();
    	commodity.setCommodityId(Long.parseLong(pricingOfferRequest.getCommodityId()));
    	CommodityVo commodityVo = baseClient.getCommodity(commodity);
    	if (commodityVo != null) {
    		offerAttachVo.setCommodityName(commodityVo.getCommodityName());
    	}
    	
    	// 港口
    	PortVo port = new PortVo();
    	port.setPortId(Long.parseLong(pricingOfferRequest.getPortId()));
    	
    	PortVo portVo = baseClient.getPort(port);
    	if (portVo != null) {
    		offerAttachVo.setPortName(portVo.getPortName());
    	}
    	
    	// 重量单位 湿吨
    	offerAttachVo.setQuantityUnitId(AttributeValueOptionEnum.getInstance().WMT.getId() + "");
    	// 价格单位 人民币/湿吨
    	offerAttachVo.setPriceUnitId(AttributeValueOptionEnum.getInstance().CNY_WMT.getId() + "");
    	
    	// 初始化
    	// 交易方式 2:点价
    	offerMainVo.setTradeMode(EsteelConstant.TRADE_MODE_PRICING);
    	
    	if (pricingOfferRequest.getOfferStatus().equals("publish")) {
    		// 铁矿报盘状态 :发布
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_IN_SALE + "");
        	offerMainVo.setPublishUser("王雁飞测试");
    	} else {
    		// 铁矿报盘状态 :草稿
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_DRAFT + "");
    	}
   	
    	offerMainVo.setCompanyId(1L);
    	offerMainVo.setCreateUser("王雁飞测试");
    	offerMainVo.setUpdateUser("王雁飞测试");
    	
    	// 保存附件
    	// tfs 报盘附件
    	StatusMSGVo msg = getTfsFileName(offerAffix, "报盘备注附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_REMARKS, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	// tfs 合同附件
    	msg = getTfsFileName(contractAffix, "报盘合同附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_CONTRACT, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	// 交货结算条款Json
    	offerMainVo.setClauseTemplateJson(JsonUtils.toJsonString(offerClauseVo));
    	
    	System.out.println(JsonUtils.toJsonString(offerMainVo));
    	
    	// 保存
		IronOfferMainVo offer = offerClient.saveIronOffer(offerMainVo);
    	
		if (pricingOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "新增失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
        return "redirect:/offer/myOffer";
    }
    
    /**
     * 现货报盘更新
     * @param inStockOfferRequest
     * @param offerAffix
     * @param offerClauseVo
     * @param contractAffix
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateInStockOffer", method = RequestMethod.POST)
    public String updateInStockOffer(IronInStockOfferRequest inStockOfferRequest, 
    		@RequestParam("offerAffix") MultipartFile offerAffix,  IronOfferClauseVo offerClauseVo, 
    		@RequestParam("contractAffix") MultipartFile contractAffix, Model model) {
    	Assert.notNull(inStockOfferRequest, "提交失败！");
    	
    	Assert.notNull(offerClauseVo, "提交失败！");

    	IronOfferMainVo offerMainVo = new IronOfferMainVo();
    	// 将request 复制到 offerMainVo
    	BeanUtils.copyProperties(inStockOfferRequest, offerMainVo);
    	
    	// 指定对手(多选值)
    	List<Long> counterpartyIdList = null;
    	if (inStockOfferRequest.getCounterpartyIdMulti() != null && !inStockOfferRequest.getCounterpartyIdMulti().trim().equals("")) {
    		String[] counterpartyIdArr = inStockOfferRequest.getCounterpartyIdMulti().split(",");
    		
    		counterpartyIdList = 
    				Arrays.asList(counterpartyIdArr).stream()
    				.filter(counterpartyId -> counterpartyId != null && counterpartyId.trim().matches("^\\d+$"))
    				.map(counterpartyId -> Long.parseLong(counterpartyId.trim())).collect(Collectors.toList()); 
    	}
    	
    	if (counterpartyIdList != null && !counterpartyIdList.isEmpty()) {
    		// 是否指定 0:否, 1:是
    		offerMainVo.setIsDesignation("1");
    		offerMainVo.setCounterpartyIdList(counterpartyIdList);
    	}
    	
    	OfferIronAttachVo offerAttachVo = new OfferIronAttachVo();
    	// 将request 复制到 offerAttachVo
    	BeanUtils.copyProperties(inStockOfferRequest, offerAttachVo);
    	
    	offerMainVo.addOfferIronAttach(offerAttachVo);
    	
    	// 品名
    	CommodityVo commodity = new CommodityVo();
    	commodity.setCommodityId(Long.parseLong(inStockOfferRequest.getCommodityId()));
    	
    	CommodityVo commodityVo = baseClient.getCommodity(commodity);
    	if (commodityVo != null) {
    		offerAttachVo.setCommodityName(commodityVo.getCommodityName());
    	}
    	
    	// 港口
    	PortVo port = new PortVo();
    	port.setPortId(Long.parseLong(inStockOfferRequest.getPortId()));
    	
    	PortVo portVo = baseClient.getPort(port);
    	if (portVo != null) {
    		offerAttachVo.setPortName(portVo.getPortName());
    	}
    	
    	offerMainVo.setUpdateUser("王雁飞测试");
    	
    	// 保存附件
    	// tfs 报盘附件
    	StatusMSGVo msg = getTfsFileName(offerAffix, "报盘备注附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_REMARKS, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	// tfs 合同附件
    	msg = getTfsFileName(contractAffix, "报盘合同附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_CONTRACT, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	// 交货结算条款Json
    	offerMainVo.setClauseTemplateJson(JsonUtils.toJsonString(offerClauseVo));

    	System.out.println(JsonUtils.toJsonString(offerMainVo));
		
		// 保存
		IronOfferMainVo offer = offerClient.updateIronOffer(offerMainVo);
    	
		if (inStockOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "新增失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
        return "redirect:/offer/myOffer";
    }
    
    /**
     * 远期报盘保存
     * @param futuresOfferRequest
     * @param offerAffix
     * @param offerClauseVo
     * @param contractAffix
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateFuturesOffer", method = RequestMethod.POST)
    public String updateFuturesOffer(IronFuturesOfferRequest futuresOfferRequest, 
    		IronFuturesTransportVo transportDescription, 
    		@RequestParam("offerAffix") MultipartFile offerAffix, Model model){
    	Assert.notNull(futuresOfferRequest, "提交失败！");
    	
    	Assert.notNull(transportDescription, "提交失败！");
    	
    	IronOfferMainVo offerMainVo = new IronOfferMainVo();
    	// 将request 复制到 offerMainVo
    	BeanUtils.copyProperties(futuresOfferRequest, offerMainVo);
    	
    	// 指定对手(多选值)
    	List<Long> counterpartyIdList = null;
    	if (futuresOfferRequest.getCounterpartyIdMulti() != null && !futuresOfferRequest.getCounterpartyIdMulti().trim().equals("")) {
    		String[] counterpartyIdArr = futuresOfferRequest.getCounterpartyIdMulti().split(",");
    		
    		counterpartyIdList = 
    				Arrays.asList(counterpartyIdArr).stream()
    				.filter(counterpartyId -> counterpartyId != null && counterpartyId.trim().matches("^\\d+$"))
    				.map(counterpartyId -> Long.parseLong(counterpartyId.trim())).collect(Collectors.toList()); 
    	}
    	
    	if (counterpartyIdList != null && !counterpartyIdList.isEmpty()) {
    		// 是否指定 0:否, 1:是
    		offerMainVo.setIsDesignation("1");
    		offerMainVo.setCounterpartyIdList(counterpartyIdList);
    	}
    	
    	// 第一个货物报盘
    	OfferIronAttachVo firstCargo = getOne(futuresOfferRequest, 0);
    	
    	offerMainVo.addOfferIronAttach(firstCargo);
    	
    	// 品名
    	CommodityVo commodity = new CommodityVo();
    	commodity.setCommodityId(Long.parseLong(firstCargo.getCommodityId()));
    	CommodityVo commodityVo = baseClient.getCommodity(commodity);
    	if (commodityVo != null) {
    		firstCargo.setCommodityName(commodityVo.getCommodityName());
    	}
    	
    	// 港口
    	PortVo port = new PortVo();
    	port.setPortId(Long.parseLong(firstCargo.getPortId()));
    	
    	PortVo portVo = baseClient.getPort(port);
    	if (portVo != null) {
    		firstCargo.setPortName(portVo.getPortName());
    	}
    	
    	firstCargo.setTransportDescription(JsonUtils.toJsonString(transportDescription));
    	
    	// 一船两货
    	if (futuresOfferRequest.getIsMultiCargo().equals(EsteelConstant.YES + "")) {
    		// 第二个货物报盘
    		OfferIronAttachVo secondCargo = getOne(futuresOfferRequest, 1);
    		
    		offerMainVo.addOfferIronAttach(secondCargo);
    		
    		// 品名
        	commodity = new CommodityVo();
        	commodity.setCommodityId(Long.parseLong(secondCargo.getCommodityId()));
        	commodityVo = baseClient.getCommodity(commodity);
        	if (commodityVo != null) {
        		secondCargo.setCommodityName(commodityVo.getCommodityName());
        	}
        	
        	// 港口
        	port = new PortVo();
        	port.setPortId(Long.parseLong(secondCargo.getPortId()));
        	
        	portVo = baseClient.getPort(port);
        	if (portVo != null) {
        		secondCargo.setPortName(portVo.getPortName());
        	}
        	
    		secondCargo.setTransportDescription(JsonUtils.toJsonString(transportDescription));
    	}
    	
    	offerMainVo.setUpdateUser("王雁飞测试");
    	
    	// 报盘附件保存 tfs
    	StatusMSGVo msg = getTfsFileName(offerAffix, "报盘备注附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_REMARKS, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	System.out.println(JsonUtils.toJsonString(offerMainVo));
    	
    	// 保存
		IronOfferMainVo offer = offerClient.saveIronOffer(offerMainVo);
    	
		if (futuresOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "新增失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
        return "redirect:/offer/myOffer";
    }
    
    /**
     * 点价报盘保存
     * @param pricingOfferRequest
     * @param offerAffix
     * @param offerClauseVo
     * @param contractAffix
     * @param model
     * @return
     */
    @RequestMapping(value = "/updatePricingOffer", method = RequestMethod.POST)
    public String updatePricingOffer(@Validated(IronPricingOffer.class) IronPricingOfferRequest pricingOfferRequest, BindingResult offerResult, 
    		@RequestParam("offerAffix") MultipartFile offerAffix, IronOfferClauseVo offerClauseVo, 
    		@RequestParam("contractAffix") MultipartFile contractAffix, Model model){
    	Assert.notNull(pricingOfferRequest, "提交失败！");
    	
    	Assert.notNull(offerClauseVo, "提交失败！");
    	
    	// 页面验证
		if(offerResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = offerResult.getAllErrors();
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage()+";");
			}
			
			model.addAttribute("msg", sb.toString());
			System.out.println(sb.toString());
		    
		    return "/offer/addOffer";
		}
    	
    	IronOfferMainVo offerMainVo = new IronOfferMainVo();
    	// 将request 复制到 offerMainVo
    	BeanUtils.copyProperties(pricingOfferRequest, offerMainVo);
    	
    	// 指定对手(多选值)
    	List<Long> counterpartyIdList = null;
    	if (pricingOfferRequest.getCounterpartyIdMulti() != null && !pricingOfferRequest.getCounterpartyIdMulti().trim().equals("")) {
    		String[] counterpartyIdArr = pricingOfferRequest.getCounterpartyIdMulti().split(",");
    		
    		counterpartyIdList = 
    				Arrays.asList(counterpartyIdArr).stream()
    				.filter(counterpartyId -> counterpartyId != null && counterpartyId.trim().matches("^\\d+$"))
    				.map(counterpartyId -> Long.parseLong(counterpartyId.trim())).collect(Collectors.toList()); 
    	}
    	
    	if (counterpartyIdList != null && !counterpartyIdList.isEmpty()) {
    		// 是否指定 0:否, 1:是
    		offerMainVo.setIsDesignation("1");
    		offerMainVo.setCounterpartyIdList(counterpartyIdList);
    	}
    	
    	OfferIronAttachVo offerAttachVo = new OfferIronAttachVo();
    	// 将request 复制到 offerAttachVo
    	BeanUtils.copyProperties(pricingOfferRequest, offerAttachVo);
    	
    	offerMainVo.addOfferIronAttach(offerAttachVo);
    	
    	// 品名
    	CommodityVo commodity = new CommodityVo();
    	commodity.setCommodityId(Long.parseLong(pricingOfferRequest.getCommodityId()));
    	CommodityVo commodityVo = baseClient.getCommodity(commodity);
    	if (commodityVo != null) {
    		offerAttachVo.setCommodityName(commodityVo.getCommodityName());
    	}
    	
    	// 港口
    	PortVo port = new PortVo();
    	port.setPortId(Long.parseLong(pricingOfferRequest.getPortId()));
    	
    	PortVo portVo = baseClient.getPort(port);
    	if (portVo != null) {
    		offerAttachVo.setPortName(portVo.getPortName());
    	}

    	offerMainVo.setUpdateUser("王雁飞测试");
    	
    	// 保存附件
    	// tfs 报盘附件
    	StatusMSGVo msg = getTfsFileName(offerAffix, "报盘备注附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_REMARKS, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	// tfs 合同附件
    	msg = getTfsFileName(contractAffix, "报盘合同附件", 
    			EsteelConstant.AFFIX_TYPE_OFFER_CONTRACT, offerMainVo);
    	if (msg != null && msg.getStatus() != 0) {
    		model.addAttribute("msg", msg.getMsg());
    		
    		return "/offer/addOffer";
    	}
    	
    	// 交货结算条款Json
    	offerMainVo.setClauseTemplateJson(JsonUtils.toJsonString(offerClauseVo));
    	
    	System.out.println(JsonUtils.toJsonString(offerMainVo));
    	
    	// 保存
		IronOfferMainVo offer = offerClient.saveIronOffer(offerMainVo);
    	
		if (pricingOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "新增失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
        return "redirect:/offer/myOffer";
    }
    
    /**
     * 报盘页面上传附件保存 tfs
     * @param file
     * @param multipartFile
     * @param futuresOfferVo
     * @param affixType
     * @return
     */
    private StatusMSGVo getTfsFileName(MultipartFile file, String affixName, 
    		int affixType, IronOfferMainVo  offerMainVo) {
    	if (file == null) {
    		return null;
    	}
    	
    	StatusMSGVo vo = new StatusMSGVo();

		String fileName = file.getOriginalFilename();// 获取上传文件名,包括路径
	    long size = file.getSize();
	    if ((fileName == null || fileName.equals("")) && size == 0) {
	    	vo.setStatus(1);
	    	vo.setMsg(affixName + "上传失败：请上传有效文件！");
	    	
	    	return vo;
	    }
	    
	    String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	    
		// 支持拓展名:doc,docx,xls,xlsx,pdf,zip,rar,7z
	    if (!Pattern.matches("^((docx?)|(xlsx?)|(pdf)|(zip)|(rar)|(7z))$", fileType)) {
	    	vo.setStatus(1);
	    	vo.setMsg(affixName + "上传失败：请上传扩展名为：doc,docx,xls,xlsx,pdf,zip,rar,7z的文件！");
	    	
	    	return vo;
	    }
	    
	    if (size > 1024 * 1024) {
	    	vo.setStatus(1);
	    	vo.setMsg(affixName + "上传失败：请上传小于1MB的文件！");
	    	
	    	return vo;
	    }
	    
	    try {
	    	String tfsFileName = tfsManager.saveFile(file.getBytes(), null, fileType);
	    	
	    	vo.setStatus(0);
	    	vo.setMsg(affixName + "保存成功！");
	    	
	    	// 记录 tfs返回ID
	    	OfferAffixVo offerAffix = new OfferAffixVo();
	    	offerAffix.setAffixType(affixType);
	    	offerAffix.setAffixPath(tfsFileName);
	    	
	    	offerMainVo.addOfferAffix(offerAffix);
		} catch (IOException e) {
			e.printStackTrace();
			
			vo.setStatus(1);
	    	vo.setMsg(affixName + "保存失败：请稍后再操作！");
		}
	    
    	return vo;
    }

    /**
	 * 根据下标获取货物
	 * @param index
	 * @return
	 */
	private OfferIronAttachVo getOne(IronFuturesOfferRequest futuresOfferRequest, int index) {
		if (futuresOfferRequest == null) {
			return null;
		}
		
		if (index < 0) {
			index = 0;
		}
		
		if (index > 1) {
			index = 1;
		}
		
		Map<String, String[]> valueMap = new HashMap<>();
		
		Field[] fields = futuresOfferRequest.getClass().getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			Field f = fields[j];
			f.setAccessible(true);// 设置些属性是可以访问的
			
			String name = f.getName();
			Object value;
			try {
				value = f.get(futuresOfferRequest);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				
				continue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				
				continue;
			}
			
			if (name.matches("^[a-zA-Z0-9_]+Arr$") 
					&& value != null && value instanceof String[]) {
				valueMap.put(name.substring(0, name.length() - 3), (String[]) value);
			}
		}
		
		OfferIronAttachVo attach = new OfferIronAttachVo();
		BeanUtils.copyProperties(futuresOfferRequest, attach);
		
		fields = attach.getClass().getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			Field f = fields[j];
			f.setAccessible(true);// 设置些属性是可以访问的
			
			String[] valueArr = valueMap.get(f.getName());
			if (valueArr != null && valueArr.length > index) {
				try {
					f.set(attach, valueArr[index]);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					
					continue;
				}
			}
		}
		
		return attach;
	}
}

