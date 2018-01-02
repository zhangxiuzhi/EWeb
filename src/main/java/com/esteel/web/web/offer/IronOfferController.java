package com.esteel.web.web.offer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteel.common.controller.WebReturnMessage;
import com.esteel.common.util.EsteelConstant;
import com.esteel.common.util.JsonUtils;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.MemberClient;
import com.esteel.web.service.OfferClient;
import com.esteel.web.vo.base.CommodityVo;
import com.esteel.web.vo.base.PortVo;
import com.esteel.web.vo.config.AttributeValueOptionEnum;
import com.esteel.web.vo.offer.IronFuturesTransportVo;
import com.esteel.web.vo.offer.IronOfferClauseVo;
import com.esteel.web.vo.offer.IronOfferMainVo;
import com.esteel.web.vo.offer.OfferAffixVo;
import com.esteel.web.vo.offer.OfferIronAttachVo;
import com.esteel.web.vo.offer.request.IronFuturesOfferRequest;
import com.esteel.web.vo.offer.request.IronFuturesTransportRequest;
import com.esteel.web.vo.offer.request.IronInStockOfferRequest;
import com.esteel.web.vo.offer.request.IronOfferClauseRequest;
import com.esteel.web.vo.offer.request.IronPricingOfferRequest;

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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BaseClient baseClient;
	@Autowired
	MemberClient memberClient;
	@Autowired
	OfferClient offerClient;
	
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
    @ResponseBody
    public WebReturnMessage saveInStockOffer(IronInStockOfferRequest inStockOfferRequest, 
    		IronOfferClauseRequest clauseRequest,  Model model) {
    	logger.info("saveInStockOffer 开始");
    	Assert.notNull(inStockOfferRequest, "提交失败！");
    	Assert.notNull(clauseRequest, "提交失败！");
		
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
    	
    	// 保存附件
    	// tfs 报盘附件
    	if (inStockOfferRequest.getOfferAffixPath() != null && !inStockOfferRequest.getOfferAffixPath().trim().equals("")) {
    		OfferAffixVo offerAffix = new OfferAffixVo();
    		
    		offerAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_REMARKS);
    		offerAffix.setAffixPath(inStockOfferRequest.getOfferAffixPath());
    		
    		offerMainVo.addOfferAffix(offerAffix);
    	}
    	
    	// tfs 合同附件
    	if (inStockOfferRequest.getContractAffixPath() != null && !inStockOfferRequest.getContractAffixPath().trim().equals("")) {
    		OfferAffixVo oferAffix = new OfferAffixVo();
    		
    		oferAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_CONTRACT);
    		oferAffix.setAffixPath(inStockOfferRequest.getContractAffixPath());
    		
    		offerMainVo.addOfferAffix(oferAffix);
    	}
    	
    	OfferIronAttachVo offerAttachVo = new OfferIronAttachVo();
    	// 将request 复制到 offerAttachVo
    	logger.info("saveInStockOffer 将IronInStockOfferRequest 复制到 OfferIronAttachVo");
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
    	
    	// 交货结算条款Json
    	IronOfferClauseVo offerClauseVo = new IronOfferClauseVo();
    	logger.info("saveInStockOffer 将IronOfferClauseRequest 复制到 IronOfferClauseVo");
    	BeanUtils.copyProperties(clauseRequest, offerClauseVo);
    	offerClauseVo.setClear_within_several_working_days(
    			clauseRequest.getClear_within_several_working_daysArr()[NumberUtils.toInt(clauseRequest.getSettlement_method())]);
    	
    	logger.info("saveInStockOffer 将IronOfferClauseVo 转成Json");
    	offerMainVo.setClauseTemplateJson(JsonUtils.toJsonString(offerClauseVo));

		// 保存
    	logger.info("saveInStockOffer saveIronOffer(" + JsonUtils.toJsonString(offerMainVo) + ")");
		IronOfferMainVo offer = offerClient.saveIronOffer(offerMainVo);
    	
		if (inStockOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "新增失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
		logger.info("saveInStockOffer 结束");
        return new WebReturnMessage(true, "success");
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
    @ResponseBody
    public WebReturnMessage saveFuturesOffer(IronFuturesOfferRequest futuresOfferRequest, 
    		IronFuturesTransportRequest transportRequest, Model model){
    	logger.info("saveFuturesOffer 开始");
    	Assert.notNull(futuresOfferRequest, "提交失败！");
    	
    	Assert.notNull(transportRequest, "提交失败！");
    	
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
    	
    	// 保存附件
    	// tfs 报盘附件
    	if (futuresOfferRequest.getOfferAffixPath() != null && !futuresOfferRequest.getOfferAffixPath().trim().equals("")) {
    		OfferAffixVo offerAffix = new OfferAffixVo();
    		
    		offerAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_REMARKS);
    		offerAffix.setAffixPath(futuresOfferRequest.getOfferAffixPath().trim());
    		
    		offerMainVo.addOfferAffix(offerAffix);
    	}
    	
    	// 重量单位 湿吨
    	futuresOfferRequest.setQuantityUnitId(AttributeValueOptionEnum.getInstance().WMT.getId() + "");
    	// 价格单位 人民币/湿吨
    	futuresOfferRequest.setPriceUnitId(AttributeValueOptionEnum.getInstance().CNY_WMT.getId() + "");
    	
    	// 是否在保税区 0:否, 1:是
    	boolean isBondedArea = NumberUtils.toInt(futuresOfferRequest.getIsBondedArea()) == EsteelConstant.YES;
    	
    	PortVo queryport = new PortVo();
    	PortVo port = null;
    	
    	// 交货结算条款Json
    	IronFuturesTransportVo transportVo = new IronFuturesTransportVo();
    	BeanUtils.copyProperties(transportRequest, transportVo);
    	
    	// 在保税区 没有这些数据: 价格术语, 目的港, 运输状态
    	if (isBondedArea) {
    		// 保税区港口
        	long bondedAreaPortId = Long.parseLong(transportRequest.getBondedAreaPortId());
    		futuresOfferRequest.setPortId(bondedAreaPortId + "");
    		
    		queryport.setPortId(bondedAreaPortId);
    		port = baseClient.getPort(queryport);
        	if (port != null) {
        		futuresOfferRequest.setPortName(port.getPortName());
        	}
        	
        	// 价格术语, 目的港, 运输状态
        	futuresOfferRequest.setPriceTerm(null);
    		futuresOfferRequest.setPriceTermPortId(null);
    		futuresOfferRequest.setDischargePortId(null);
    		futuresOfferRequest.setTransportDescription(null);
    	} else {
    		queryport.setPortId(NumberUtils.toLong(futuresOfferRequest.getPriceTermPortId()));
    		port = baseClient.getPort(port);
        	if (port != null) {
        		futuresOfferRequest.setPriceTermPortName(port.getPortName());
        	}
        	
        	queryport.setPortId(NumberUtils.toLong(futuresOfferRequest.getDischargePortId()));
    		port = baseClient.getPort(port);
        	if (port != null) {
        		futuresOfferRequest.setDischargePortName(port.getPortName());
        	}
        	
        	logger.info("saveFuturesOffer 将IronFuturesTransportVo 转成Json");
    		futuresOfferRequest.setTransportDescription(JsonUtils.toJsonString(transportVo));
    	}
    	
    	// 第一个货物报盘
    	logger.info("saveFuturesOffer getOne(IronFuturesOfferRequest, 0)");
    	OfferIronAttachVo firstCargo = getOne(futuresOfferRequest, 0);
    	
    	offerMainVo.addOfferIronAttach(firstCargo);
    	
    	// 品名
    	CommodityVo commodity = new CommodityVo();
    	commodity.setCommodityId(Long.parseLong(firstCargo.getCommodityId()));
    	CommodityVo commodityVo = baseClient.getCommodity(commodity);
    	if (commodityVo != null) {
    		firstCargo.setCommodityName(commodityVo.getCommodityName());
    	}
    	
    	// 一船两货
    	if (isBondedArea) {
    		// 第二个货物报盘
    		logger.info("saveFuturesOffer getOne(IronFuturesOfferRequest, 1)");
    		OfferIronAttachVo secondCargo = getOne(futuresOfferRequest, 1);
    		
    		offerMainVo.addOfferIronAttach(secondCargo);
    		
    		// 品名
        	commodity = new CommodityVo();
        	commodity.setCommodityId(Long.parseLong(secondCargo.getCommodityId()));
        	commodityVo = baseClient.getCommodity(commodity);
        	if (commodityVo != null) {
        		secondCargo.setCommodityName(commodityVo.getCommodityName());
        	}
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
    	
    	// 保存
    	logger.info("saveFuturesOffer saveIronOffer(" + JsonUtils.toJsonString(offerMainVo) + ")");
		IronOfferMainVo offer = offerClient.saveIronOffer(offerMainVo);
    	
		if (futuresOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "新增失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
		logger.info("saveFuturesOffer 结束");
        return new WebReturnMessage(true, "success");
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
    @ResponseBody
    public WebReturnMessage savePricingOffer(IronPricingOfferRequest pricingOfferRequest, 
    		IronOfferClauseRequest clauseRequest, Model model){
    	logger.info("savePricingOffer 开始");
    	Assert.notNull(pricingOfferRequest, "提交失败！");
    	Assert.notNull(clauseRequest, "提交失败！");
    	
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
    	
    	// 保存附件
    	// tfs 报盘附件
    	if (pricingOfferRequest.getOfferAffixPath() != null && !pricingOfferRequest.getOfferAffixPath().trim().equals("")) {
    		OfferAffixVo offerAffix = new OfferAffixVo();
    		
    		offerAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_REMARKS);
    		offerAffix.setAffixPath(pricingOfferRequest.getOfferAffixPath());
    		
    		offerMainVo.addOfferAffix(offerAffix);
    	}
    	
    	// tfs 合同附件
    	if (pricingOfferRequest.getContractAffixPath() != null && !pricingOfferRequest.getContractAffixPath().trim().equals("")) {
    		OfferAffixVo oferAffix = new OfferAffixVo();
    		
    		oferAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_CONTRACT);
    		oferAffix.setAffixPath(pricingOfferRequest.getContractAffixPath());
    		
    		offerMainVo.addOfferAffix(oferAffix);
    	}
    	
    	OfferIronAttachVo offerAttachVo = new OfferIronAttachVo();
    	// 将request 复制到 offerAttachVo
    	logger.info("savePricingOffer 将IronPricingOfferRequest 复制到 OfferIronAttachVo");
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
    	
    	// 交货结算条款Json
    	IronOfferClauseVo offerClauseVo = new IronOfferClauseVo();
    	logger.info("savePricingOffer 将IronOfferClauseRequest 复制到 IronOfferClauseVo");
    	BeanUtils.copyProperties(clauseRequest, offerClauseVo);
    	offerClauseVo.setClear_within_several_working_days(
    			clauseRequest.getClear_within_several_working_daysArr()[NumberUtils.toInt(clauseRequest.getSettlement_method())]);
    	
    	logger.info("savePricingOffer 将IronOfferClauseVo 转成Json");
    	offerMainVo.setClauseTemplateJson(JsonUtils.toJsonString(offerClauseVo));
    	
    	// 保存
    	logger.info("savePricingOffer saveIronOffer(" + JsonUtils.toJsonString(offerMainVo) + ")");
		IronOfferMainVo offer = offerClient.saveIronOffer(offerMainVo);
    	
		if (pricingOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "新增失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
		logger.info("savePricingOffer 结束");
        return new WebReturnMessage(true, "success");
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
    @ResponseBody
    public WebReturnMessage updateInStockOffer(IronInStockOfferRequest inStockOfferRequest, 
    		IronOfferClauseRequest clauseRequest,  Model model) {
    	logger.info("updateInStockOffer 开始");
    	
    	Assert.notNull(inStockOfferRequest, "提交失败！");
    	Assert.notNull(clauseRequest, "提交失败！");

    	IronOfferMainVo offerMainVo = new IronOfferMainVo();
    	// 将request 复制到 offerMainVo
    	logger.info("updateInStockOffer 将IronInStockOfferRequest 复制到 OfferIronAttachVo");
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
    	
    	// 保存附件
    	// tfs 报盘附件
    	if (inStockOfferRequest.getOfferAffixPath() != null && !inStockOfferRequest.getOfferAffixPath().trim().equals("")) {
    		OfferAffixVo offerAffix = new OfferAffixVo();
    		
    		offerAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_REMARKS);
    		offerAffix.setAffixPath(inStockOfferRequest.getOfferAffixPath());
    		
    		offerMainVo.addOfferAffix(offerAffix);
    	}
    	
    	// tfs 合同附件
    	if (inStockOfferRequest.getContractAffixPath() != null && !inStockOfferRequest.getContractAffixPath().trim().equals("")) {
    		OfferAffixVo oferAffix = new OfferAffixVo();
    		
    		oferAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_CONTRACT);
    		oferAffix.setAffixPath(inStockOfferRequest.getContractAffixPath());
    		
    		offerMainVo.addOfferAffix(oferAffix);
    	}
    	
    	OfferIronAttachVo offerAttachVo = new OfferIronAttachVo();
    	// 将request 复制到 offerAttachVo
    	logger.info("updateInStockOffer 将IronInStockOfferRequest 复制到 OfferIronAttachVo");
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
    	
    	if (inStockOfferRequest.getOfferStatus().equals("publish")) {
    		// 铁矿报盘状态 :发布
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_IN_SALE + "");
        	offerMainVo.setPublishUser("王雁飞测试");
    	} else {
    		// 铁矿报盘状态 :草稿
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_DRAFT + "");
    	}
    	
    	offerMainVo.setUpdateUser("王雁飞测试");
    	
    	// 交货结算条款Json
    	IronOfferClauseVo offerClauseVo = new IronOfferClauseVo();
    	logger.info("updateInStockOffer 将IronOfferClauseRequest 复制到 IronOfferClauseVo");
    	BeanUtils.copyProperties(clauseRequest, offerClauseVo);
    	offerClauseVo.setClear_within_several_working_days(
    			clauseRequest.getClear_within_several_working_daysArr()[NumberUtils.toInt(clauseRequest.getSettlement_method())]);

    	logger.info("updateInStockOffer 将IronOfferClauseVo 转成Json");
    	offerMainVo.setClauseTemplateJson(JsonUtils.toJsonString(offerClauseVo));

		// 保存
    	logger.info("updateInStockOffer saveIronOffer(" + JsonUtils.toJsonString(offerMainVo) + ")");
		IronOfferMainVo offer = offerClient.updateIronOffer(offerMainVo);
    	
		if (inStockOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "更新失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
		logger.info("updateInStockOffer 结束");
        return new WebReturnMessage(true, "success");
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
    @ResponseBody
    public WebReturnMessage updateFuturesOffer(IronFuturesOfferRequest futuresOfferRequest, 
    		IronFuturesTransportRequest transportRequest, Model model){
    	logger.info("updateFuturesOffer 开始");
    	
    	Assert.notNull(futuresOfferRequest, "提交失败！");
    	Assert.notNull(transportRequest, "提交失败！");
    	
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
    	
    	// 保存附件
    	// tfs 报盘附件
    	if (futuresOfferRequest.getOfferAffixPath() != null && !futuresOfferRequest.getOfferAffixPath().trim().equals("")) {
    		OfferAffixVo offerAffix = new OfferAffixVo();
    		
    		offerAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_REMARKS);
    		offerAffix.setAffixPath(futuresOfferRequest.getOfferAffixPath().trim());
    		
    		offerMainVo.addOfferAffix(offerAffix);
    	}
    	
    	// 是否在保税区 0:否, 1:是
    	boolean isBondedArea = NumberUtils.toInt(futuresOfferRequest.getIsBondedArea()) == EsteelConstant.YES;
    	
    	PortVo queryport = new PortVo();
    	PortVo port = null;
    	
    	// 交货结算条款Json
    	IronFuturesTransportVo transportVo = new IronFuturesTransportVo();
    	BeanUtils.copyProperties(transportRequest, transportVo);
    	
    	// 在保税区 没有这些数据: 价格术语, 目的港, 运输状态
    	if (isBondedArea) {
    		// 保税区港口
        	long bondedAreaPortId = Long.parseLong(transportRequest.getBondedAreaPortId());
    		futuresOfferRequest.setPortId(bondedAreaPortId + "");
    		
    		queryport.setPortId(bondedAreaPortId);
    		port = baseClient.getPort(queryport);
        	if (port != null) {
        		futuresOfferRequest.setPortName(port.getPortName());
        	}
        	
        	// 价格术语, 目的港, 运输状态
        	futuresOfferRequest.setPriceTerm(null);
    		futuresOfferRequest.setPriceTermPortId(null);
    		futuresOfferRequest.setDischargePortId(null);
    		futuresOfferRequest.setTransportDescription(null);
    	} else {
    		queryport.setPortId(NumberUtils.toLong(futuresOfferRequest.getPriceTermPortId()));
    		port = baseClient.getPort(port);
        	if (port != null) {
        		futuresOfferRequest.setPriceTermPortName(port.getPortName());
        	}
        	
        	queryport.setPortId(NumberUtils.toLong(futuresOfferRequest.getDischargePortId()));
    		port = baseClient.getPort(port);
        	if (port != null) {
        		futuresOfferRequest.setDischargePortName(port.getPortName());
        	}
    		
        	logger.info("updateFuturesOffer 将IronFuturesTransportVo 转成Json");
    		futuresOfferRequest.setTransportDescription(JsonUtils.toJsonString(transportVo));
    	}
    	
    	// 第一个货物报盘
    	logger.info("updateFuturesOffer getOne(IronFuturesOfferRequest, 0)");
    	OfferIronAttachVo firstCargo = getOne(futuresOfferRequest, 0);
    	
    	offerMainVo.addOfferIronAttach(firstCargo);
    	
    	// 品名
    	CommodityVo commodity = new CommodityVo();
    	commodity.setCommodityId(Long.parseLong(firstCargo.getCommodityId()));
    	CommodityVo commodityVo = baseClient.getCommodity(commodity);
    	if (commodityVo != null) {
    		firstCargo.setCommodityName(commodityVo.getCommodityName());
    	}
    	
    	// 一船两货
    	if (isBondedArea) {
    		// 第二个货物报盘
    		logger.info("updateFuturesOffer getOne(IronFuturesOfferRequest, 1)");
    		OfferIronAttachVo secondCargo = getOne(futuresOfferRequest, 1);
    		
    		offerMainVo.addOfferIronAttach(secondCargo);
    		
    		// 品名
        	commodity = new CommodityVo();
        	commodity.setCommodityId(Long.parseLong(secondCargo.getCommodityId()));
        	commodityVo = baseClient.getCommodity(commodity);
        	if (commodityVo != null) {
        		secondCargo.setCommodityName(commodityVo.getCommodityName());
        	}
    	}
    	
    	if (futuresOfferRequest.getOfferStatus().equals("publish")) {
    		// 铁矿报盘状态 :发布
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_IN_SALE + "");
        	offerMainVo.setPublishUser("王雁飞测试");
    	} else {
    		// 铁矿报盘状态 :草稿
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_DRAFT + "");
    	}
    	
    	offerMainVo.setUpdateUser("王雁飞测试");
    	
    	// 保存
    	logger.info("updateFuturesOffer saveIronOffer(" + JsonUtils.toJsonString(offerMainVo) + ")");
		IronOfferMainVo offer = offerClient.updateIronOffer(offerMainVo);
    	
		if (futuresOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "更新失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
		logger.info("updateFuturesOffer 结束");
        return new WebReturnMessage(true, "success");
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
    @ResponseBody
    public WebReturnMessage updatePricingOffer(IronPricingOfferRequest pricingOfferRequest, 
    		IronOfferClauseRequest clauseRequest, Model model){
    	logger.info("updatePricingOffer 开始");
    	
    	Assert.notNull(pricingOfferRequest, "提交失败！");
    	Assert.notNull(clauseRequest, "提交失败！");
    	
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
    	
    	// 保存附件
    	// tfs 报盘附件
    	if (pricingOfferRequest.getOfferAffixPath() != null && !pricingOfferRequest.getOfferAffixPath().trim().equals("")) {
    		OfferAffixVo offerAffix = new OfferAffixVo();
    		
    		offerAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_REMARKS);
    		offerAffix.setAffixPath(pricingOfferRequest.getOfferAffixPath());
    		
    		offerMainVo.addOfferAffix(offerAffix);
    	}
    	
    	// tfs 合同附件
    	if (pricingOfferRequest.getContractAffixPath() != null && !pricingOfferRequest.getContractAffixPath().trim().equals("")) {
    		OfferAffixVo oferAffix = new OfferAffixVo();
    		
    		oferAffix.setAffixType(EsteelConstant.AFFIX_TYPE_OFFER_CONTRACT);
    		oferAffix.setAffixPath(pricingOfferRequest.getContractAffixPath());
    		
    		offerMainVo.addOfferAffix(oferAffix);
    	}
    	
    	OfferIronAttachVo offerAttachVo = new OfferIronAttachVo();
    	// 将request 复制到 offerAttachVo
    	logger.info("updatePricingOffer 将IronPricingOfferRequest 复制到 OfferIronAttachVo");
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
    	
    	if (pricingOfferRequest.getOfferStatus().equals("publish")) {
    		// 铁矿报盘状态 :发布
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_IN_SALE + "");
        	offerMainVo.setPublishUser("王雁飞测试");
    	} else {
    		// 铁矿报盘状态 :草稿
        	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_DRAFT + "");
    	}

    	offerMainVo.setUpdateUser("王雁飞测试");
    	
    	// 交货结算条款Json
    	IronOfferClauseVo offerClauseVo = new IronOfferClauseVo();
    	logger.info("updatePricingOffer 将IronOfferClauseRequest 复制到 IronOfferClauseVo");
    	BeanUtils.copyProperties(clauseRequest, offerClauseVo);
    	offerClauseVo.setClear_within_several_working_days(
    			clauseRequest.getClear_within_several_working_daysArr()[NumberUtils.toInt(clauseRequest.getSettlement_method())]);
    	
    	logger.info("updatePricingOffer 将IronOfferClauseVo 转成Json");
    	offerMainVo.setClauseTemplateJson(JsonUtils.toJsonString(offerClauseVo));

		// 保存
    	logger.info("updatePricingOffer saveIronOffer(" + JsonUtils.toJsonString(offerMainVo) + ")");
		IronOfferMainVo offer = offerClient.updateIronOffer(offerMainVo);
    	
		if (pricingOfferRequest.getOfferStatus().equals("draft")) {
    		// 铁矿报盘状态 :草稿
			Assert.notNull(offer, "更新失败！");
    	} else {
    		// 铁矿报盘状态 :草稿
    		Assert.notNull(offer, "发布失败！");
    	}
    	
		logger.info("updatePricingOffer 结束");
        return new WebReturnMessage(true, "success");
    }
    
    /**
	 * 根据下标获取货物
	 * @param index
	 * @return
	 */
	private OfferIronAttachVo getOne(IronFuturesOfferRequest futuresOfferRequest, int index) {
		logger.info("getOne 开始");
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
		
		logger.info("getOne IronFuturesOfferRequest.getClass().getDeclaredFields()");
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
				logger.error("getOne 错误位置：" + e);
				continue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.error("getOne 错误位置：" + e);
				continue;
			}
			
			if (name.matches("^[a-zA-Z0-9_]+Arr$") 
					&& value != null && value instanceof String[]) {
				valueMap.put(name.substring(0, name.length() - 3), (String[]) value);
			}
		}
		
		OfferIronAttachVo attach = new OfferIronAttachVo();
		logger.info("getOne 将IronFuturesOfferRequest 复制到 OfferIronAttachVo");
		BeanUtils.copyProperties(futuresOfferRequest, attach);
		
		logger.info("getOne OfferIronAttachVo.getClass().getDeclaredFields()");
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
					logger.error("getOne 错误位置：" + e);
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error("getOne 错误位置：" + e);
					continue;
				}
			}
		}
		
		logger.info("getOne 结束");
		return attach;
	}
}

