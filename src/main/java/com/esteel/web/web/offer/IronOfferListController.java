package com.esteel.web.web.offer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteel.common.controller.WebReturnMessage;
import com.esteel.common.util.EsteelConstant;
import com.esteel.common.util.JsonUtils;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.MemberClient;
import com.esteel.web.service.OfferClient;
import com.esteel.web.vo.MemberCompanyVo;
import com.esteel.web.vo.MemberUserVo;
import com.esteel.web.vo.base.CommodityVo;
import com.esteel.web.vo.base.PortVo;
import com.esteel.web.vo.config.AttributeValueOptionVo;
import com.esteel.web.vo.offer.IronFuturesTransportVo;
import com.esteel.web.vo.offer.IronOfferClauseVo;
import com.esteel.web.vo.offer.IronOfferMainVo;
import com.esteel.web.vo.offer.IronOfferPage;
import com.esteel.web.vo.offer.OfferIronAttachVo;
import com.esteel.web.vo.offer.request.IronOfferQueryVo;
import com.esteel.web.vo.offer.response.IronFuturesTransportResponse;
import com.esteel.web.vo.offer.response.IronOfferAttachResponse;
import com.esteel.web.vo.offer.response.IronOfferClauseResponse;
import com.esteel.web.vo.offer.response.IronOfferResponse;
import com.taobao.common.tfs.TfsManager;
import com.taobao.tair.json.JSONArray;

import reactor.core.support.Assert;

/**
 * 
 * @ClassName: OfferListController
 * @Description:  铁矿报盘 controller
 * @author wyf
 * @date 2017年12月22日 上午11:32:47 
 *
 */
@RequestMapping("/offer/iron")
@Controller
public class IronOfferListController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BaseClient baseClient;
	@Autowired
	MemberClient memberClient;
	@Autowired
	OfferClient offerClient;
	@Autowired
    TfsManager tfsManager;
	
	/**
	 * 跳转我的铁矿报盘新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myList", method = RequestMethod.GET)
    public String myOfferUI(Model model){
		logger.info("myOfferUI 页面数据 加载开始");
		// 品名列表
		logger.info("myOfferUI 品名列表");
		List<CommodityVo> ironCommoditys = baseClient.findCommodityListByIron();
		
		// 港口列表
		logger.info("myOfferUI 港口列表");
		List<PortVo> ports = baseClient.findPortListForOffer();
		
		// 账号
		logger.info("myOfferUI 账号列表");
		List<MemberUserVo> users = memberClient.findmembers(1);
		
		logger.info("myOfferUI 数据组装/传输 开始");
		/* 页面数据组装 开始 */
		/**
    	 * 交易方式
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> tradeModeList = new ArrayList<Map<String, String>>();
    	
    	Map<String, String> tradeModeMap = new HashMap<>();
    	tradeModeList.add(tradeModeMap);
    	tradeModeMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.TRADE_MODE_INSTOCK));
    	tradeModeMap.put("value", EsteelConstant.TRADE_MODE_INSTOCK + "");
    	tradeModeMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.TRADE_MODE_INSTOCK));
    	
    	tradeModeMap = new HashMap<>();
    	tradeModeList.add(tradeModeMap);
    	tradeModeMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.TRADE_MODE_PRICING));
    	tradeModeMap.put("value", EsteelConstant.TRADE_MODE_PRICING + "");
    	tradeModeMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.TRADE_MODE_PRICING));
    	
    	tradeModeMap = new HashMap<>();
    	tradeModeList.add(tradeModeMap);
    	tradeModeMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.TRADE_MODE_FUTURES));
    	tradeModeMap.put("value", EsteelConstant.TRADE_MODE_FUTURES + "");
    	tradeModeMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.TRADE_MODE_FUTURES));
    	
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
    	
    	/**
    	 * 报盘状态
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> offerStatusList = new ArrayList<Map<String, String>>();
    	
    	Map<String, String> offerStatusMap = new HashMap<>();
    	offerStatusList.add(offerStatusMap);
    	offerStatusMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.ALL));
    	offerStatusMap.put("value", EsteelConstant.ALL + "");
    	offerStatusMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.ALL));
    	
    	offerStatusMap = new HashMap<>();
    	offerStatusList.add(offerStatusMap);
    	offerStatusMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_IN_SALE));
    	offerStatusMap.put("value", EsteelConstant.OFFER_STATUS_IN_SALE + "");
    	offerStatusMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_IN_SALE));
    	
    	offerStatusMap = new HashMap<>();
    	offerStatusList.add(offerStatusMap);
    	offerStatusMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_SOLD_OUT));
    	offerStatusMap.put("value", EsteelConstant.OFFER_STATUS_SOLD_OUT + "");
    	offerStatusMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_SOLD_OUT));
    	
    	offerStatusMap = new HashMap<>();
    	offerStatusList.add(offerStatusMap);
    	offerStatusMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_OFF_SHELVES));
    	offerStatusMap.put("value", EsteelConstant.OFFER_STATUS_OFF_SHELVES + "");
    	offerStatusMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_OFF_SHELVES));
    	
    	offerStatusMap = new HashMap<>();
    	offerStatusList.add(offerStatusMap);
    	offerStatusMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_DRAFT));
    	offerStatusMap.put("value", EsteelConstant.OFFER_STATUS_DRAFT + "");
    	offerStatusMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_DRAFT));
    	
    	/**
    	 * 账号
    	 * 格式:[{"text":"","value":"","key":""},...]
    	 */
    	List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
    	if (users != null) {
    		users.forEach(user -> {
        		Map<String, String> userMap = new HashMap<>();
        		userList.add(userMap);
        		userMap.put("text", user.getAccount());
        		userMap.put("value", user.getUserId() + "");
        		userMap.put("key", user.getAccount() + "," + user.getUserName());
         	});
    	}
     	
    	/* 页面数据组装 结束 */
    	
    	/* 页面数据传输 开始 */
    	// 交易方式列表Json
    	model.addAttribute("tradeModeJson", JSONArray.toJSONString(tradeModeList));
    	// 品名列表Json
    	model.addAttribute("ironCommodityJson", JSONArray.toJSONString(ironCommodityList));
    	// 港口列表Json
    	model.addAttribute("portJson", JSONArray.toJSONString(portList));
    	// 状态列表Json
    	model.addAttribute("offerStatusJson", JSONArray.toJSONString(offerStatusList));
    	// 账号列表Json
    	model.addAttribute("userJson", JSONArray.toJSONString(userList));
    	/* 页面数据传输 结束 */
    	logger.info("myOfferUI 数据组装/传输 结束");
    	
    	logger.info("myOfferUI 页面数据 加载结束");
        return "/offer/myOffer";
    }
	
	/**
	 * 我的铁矿列表查询
	 * @param queryVo
	 * @return
	 */
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	@ResponseBody
    public IronOfferPage ironOfferPage(IronOfferQueryVo queryVo){
		logger.info("ironOfferPage 查询 开始");
		Assert.notNull(queryVo, "查询失败！");
		logger.info("ironOfferPage，参数{IronOfferQueryVo}" + JsonUtils.toJsonString(queryVo));
		
		if (queryVo.getPageNum() == null) {
			queryVo.setPage(0);
		} else {
			int page = NumberUtils.toInt(queryVo.getPageNum());
			if (page > 0) {
				page = page - 1;
			}
			queryVo.setPage(page);
		}
		
		if (queryVo.getPageSize() == null) {
			queryVo.setSizePerPage(10);
		} else {
			queryVo.setSizePerPage(NumberUtils.toInt(queryVo.getPageSize()));
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");    	DecimalFormat priceFormat = new DecimalFormat("###,##0.0");
    	DecimalFormat qtyFormat = new DecimalFormat("###,###,###,##0");
    	
    	logger.info("ironOfferPage，根据参数{IronOfferQueryVo}查询铁矿报盘");
    	IronOfferPage page = offerClient.query(queryVo);
    	
    	List<IronOfferMainVo> offerList = page.getData();
    	for (IronOfferMainVo offerVo: offerList) {
    		IronOfferResponse pageVo = getIronOfferResponse(offerVo);
    		
    		List<OfferIronAttachVo> offerAttachEntityList = pageVo.getOfferAttachList();
    		if (offerAttachEntityList != null && offerAttachEntityList.size() > 0){
    			// 交易方式 1:现货, 2:点价, 3:远期
    			IronOfferAttachResponse firstAttach = getIronOfferAttachResponse(offerAttachEntityList.get(0));
    			IronOfferAttachResponse secondAttach = null;
    			if (pageVo.getTradeMode() == EsteelConstant.TRADE_MODE_FUTURES 
    					&& NumberUtils.toInt(pageVo.getIsMultiCargo()) == EsteelConstant.YES
    					&& offerAttachEntityList.size() > 1) {
    				secondAttach = getIronOfferAttachResponse(offerAttachEntityList.get(1));
    			}
    			
    			// 可交易量
    			BigDecimal firstQty = 
    					firstAttach.getOfferQuantity() == null ? new BigDecimal(0) : new BigDecimal(firstAttach.getOfferQuantity());
    			if (firstAttach.getSoldQuantity() != null) {
    				firstQty = firstQty.subtract(new BigDecimal(firstAttach.getSoldQuantity()));
    				if (firstQty.compareTo(new BigDecimal(0)) < 0) {
    					firstQty = new BigDecimal(0);
    				}
    			}
    			
    			BigDecimal secondQty = new BigDecimal(0);
    			if (secondAttach != null) {
    				secondQty = 
    						secondAttach.getOfferQuantity() == null ? new BigDecimal(0) : new BigDecimal(secondAttach.getOfferQuantity());
    				if (secondAttach.getSoldQuantity() != null) {
    					secondQty = secondQty.subtract(new BigDecimal(secondAttach.getSoldQuantity()));
        				if (secondQty.compareTo(new BigDecimal(0)) < 0) {
        					secondQty = new BigDecimal(0);
        				}
        			}
    			}
    			
    			if (secondQty.compareTo(new BigDecimal(0)) == 0) {
    				secondAttach = null;
    			}
    			
    			// 品名名称
    			String commodityName = firstAttach.getCommodityName();
    			if (secondAttach != null && secondAttach.getCommodityName() != null)
    			{
    				commodityName += "+" + firstAttach.getCommodityName();
    			}
    			pageVo.setCommodityName(commodityName);
    			// 铁品位
    			String fe = firstAttach.getFe() == null ? "" : firstAttach.getFe().toString();
    			if (secondAttach != null && secondAttach.getFe() != null) {
    				fe += "+" + (secondAttach.getFe() == null ? "" : secondAttach.getFe().toString());
    			}
    			pageVo.setFe(fe);
    			// 港口ID
    			long portId = firstAttach.getPortId() == null ? 0 : NumberUtils.toInt(firstAttach.getPortId());
    			if (pageVo.getTradeMode() == EsteelConstant.TRADE_MODE_FUTURES
    					&& NumberUtils.toInt(firstAttach.getIsBondedArea()) == EsteelConstant.NO)
    			{
    				portId = firstAttach.getDischargePortId() == null ? 0 : NumberUtils.toInt(firstAttach.getDischargePortId());
    			}
    			pageVo.setPortId(portId);
    			// 港口
    			String portName = firstAttach.getPortName();
    			if (pageVo.getTradeMode() == EsteelConstant.TRADE_MODE_FUTURES
    					&& NumberUtils.toInt(firstAttach.getIsBondedArea()) == EsteelConstant.NO)
    			{
    				portName = firstAttach.getDischargePortName();
    			}
    			pageVo.setPortName(portName);
    			// 价格数值
    			String priceValue = 
    					firstAttach.getPriceValue() == null ? "0.0" : priceFormat.format(new BigDecimal(firstAttach.getPriceValue()));
    			if (secondAttach != null && secondAttach.getPriceValue() != null) {
    				priceValue += "+" + priceFormat.format(new BigDecimal(secondAttach.getPriceValue()));
    			}
    			pageVo.setPriceValue(priceValue);
    			// 价格描述
    			pageVo.setPriceDescription(firstAttach.getPriceDescription());
    			//价格 文本
    			String priceText = priceValue;
    			if (pageVo.getTradeMode() == EsteelConstant.TRADE_MODE_FUTURES
    					&& NumberUtils.toInt(firstAttach.getPriceModel()) == EsteelConstant.PRICE_MODEL_FLOAT) {
    				priceText = pageVo.getPriceDescription();
    			}
    			pageVo.setPriceText(priceText);
    			// 可交易重量
    			String tradableQuantity = qtyFormat.format(firstQty);
    			if (secondAttach != null) {
    				tradableQuantity += "+" + qtyFormat.format(secondQty);
    			}
    			pageVo.setTradableQuantity(tradableQuantity);
    			// 运输状态 Json数据
    			pageVo.setTransportDescription(firstAttach.getTransportDescription());
    			// 点价期
    			String pricingPeriod = 
    					firstAttach.getPricingPeriodStart() == null ? "" : dateFormat.format(firstAttach.getPricingPeriodStart());
    			pricingPeriod += "-";
    			if (firstAttach.getDeliveryPeriodEnd() != null) {
    				pricingPeriod += 
    						firstAttach.getDeliveryPeriodEnd() == null ? "" : dateFormat.format(firstAttach.getDeliveryPeriodEnd());
    			}
    			pageVo.setPricingPeriod(pricingPeriod);
    			// 交货期
    			String deliveryPeriod = 
    					firstAttach.getDeliveryPeriodStart() == null ? "" : dateFormat.format(firstAttach.getDeliveryPeriodStart());
    			deliveryPeriod += "-";
    			if (firstAttach.getDeliveryPeriodEnd() != null) {
    				deliveryPeriod += 
    						firstAttach.getDeliveryPeriodEnd() == null ? "" : dateFormat.format(firstAttach.getDeliveryPeriodEnd());
    			}
    			pageVo.setDeliveryPeriod(deliveryPeriod);
    		}
    	}
    	
        return page;
    }

	/**
	 * 跳转铁矿报盘详情页面
	 * @param offerCode
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/detailBySelf/{offerCode}", method = RequestMethod.GET)
    public String detailBySelf(@PathVariable("offerCode") String offerCode, Model model){
		logger.info("detailBySelf，参数{offerCode}" + offerCode);
		Assert.notNull(offerCode, "点击失败！");

		IronOfferQueryVo queryVo = new IronOfferQueryVo();
		queryVo.setOfferCode(offerCode);

		logger.info("detailBySelf，根据参数{offerCode}获取铁矿报盘");
		IronOfferMainVo offerVo = offerClient.getIronOffer(queryVo);
		Assert.notNull(offerVo, "点击失败！");
		
		IronOfferResponse offer = getIronOfferResponse(offerVo);
		model.addAttribute("offer", offer);
		
		/**
		 * 指定交易对手
		 */
		List<Long> counterpartyIdList = offer.getCounterpartyIdList();
		List<Map<String, String>> counterpartyList = new ArrayList<Map<String, String>>();
		if (counterpartyIdList != null && counterpartyIdList.size() > 0) {
			logger.info("detailBySelf 指定交易对手");
			for (long counterpartyId : counterpartyIdList) {
				MemberCompanyVo counterparty = memberClient.findCompany(counterpartyId);
				if (counterparty != null) {
					Map<String, String> counterpartyMap = new HashMap<>();
					counterpartyList.add(counterpartyMap);
					counterpartyMap.put("text", counterparty.getCompanyName());
					counterpartyMap.put("value", counterparty.getCompanyId() + "");
					counterpartyMap.put("key", counterparty.getCompanyName() + "," + counterparty.getCompanyNameEn());
				}
			}
		}
		
		model.addAttribute("counterpartyJson", JSONArray.toJSONString(counterpartyList));
		
		IronOfferAttachResponse offerAttachResponse = new IronOfferAttachResponse();
		if (offer.getOfferAttachList() != null && offer.getOfferAttachList().size() > 0) {
			offerAttachResponse = getIronOfferAttachResponse(offer.getOfferAttachList().get(0));
		}
		
		model.addAttribute("offerAttach", offerAttachResponse);

		if (offer.getTradeMode() == EsteelConstant.TRADE_MODE_INSTOCK) {
			IronOfferClauseResponse offerClause  = new IronOfferClauseResponse();
			if (offer.getClauseTemplateJson() != null && !offer.getClauseTemplateJson().trim().equals("")) {
				offerClause = getIronOfferClauseResponse(offer.getClauseTemplateJson());
			}
			
			model.addAttribute("offerClause", offerClause);
			
			return "/myOffer/detail/inStock";
		} else if (offer.getTradeMode() == EsteelConstant.TRADE_MODE_FUTURES) {
			List<IronOfferAttachResponse> offerAttachList = new ArrayList<>();
			offerAttachList.add(offerAttachResponse);
			
			if (NumberUtils.toInt(offer.getIsMultiCargo()) == EsteelConstant.YES) {
				IronOfferAttachResponse sencondResponse = new IronOfferAttachResponse();
				if (offer.getOfferAttachList() != null && offer.getOfferAttachList().size() > 1) {
					sencondResponse = getIronOfferAttachResponse(offer.getOfferAttachList().get(1));
				}
				
				offerAttachList.add(sencondResponse);
			}

			model.addAttribute("offerAttachList", offer.getOfferAttachList());
			
			IronFuturesTransportResponse offerTransport  = new IronFuturesTransportResponse();
			if (offerAttachResponse.getTransportDescription() != null && !offerAttachResponse.getTransportDescription().trim().equals("")) {
				IronFuturesTransportVo offerTransportVo = 
						JsonUtils.toObject(offerAttachResponse.getTransportDescription(), IronFuturesTransportVo.class);
				
				BeanUtils.copyProperties(offerTransportVo, offerTransport);
				
				if (offerTransportVo.getTransport_arrive_month() == null 
						|| !offerTransportVo.getTransport_arrive_month().trim().equals("")) {
					offerTransport.setTransport_half_month("上半月");
					if (NumberUtils.toInt(offerTransportVo.getTransport_half_month()) == 1) {
						offerTransport.setTransport_half_month("下半月");
					}
				}
			}
			
			model.addAttribute("offerTransport", offerTransport);
			
			return "/myOffer/detail/futures";
		} else if (offer.getTradeMode() == EsteelConstant.TRADE_MODE_PRICING) {
			IronOfferClauseResponse offerClause  = new IronOfferClauseResponse();
			if (offer.getClauseTemplateJson() != null && !offer.getClauseTemplateJson().trim().equals("")) {
				offerClause = getIronOfferClauseResponse(offer.getClauseTemplateJson());
			}
			
			model.addAttribute("offerClause", offerClause);
			
			return "/myOffer/detail/pricing";
		}

		return "/myOffer/detail/nStock";
	}
	
	/**
     * 上架
     * @param offerCode
     * @return
     */
    @RequestMapping(value = "/putShelves/{offerCode}", method = RequestMethod.POST)
    @ResponseBody
    public WebReturnMessage putShelvesIronOffer(@PathVariable("offerCode") String offerCode){
    	WebReturnMessage webRetMesage = new WebReturnMessage(false, "上架失败！");
    	
    	IronOfferQueryVo queryVo = new IronOfferQueryVo();
    	queryVo.setOfferCode(offerCode);
    	
    	IronOfferMainVo offerMainVo = offerClient.getIronOffer(queryVo);
    	
    	offerMainVo.setUpdateUser("王雁飞测试");
    	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_IN_SALE + "");
    	
    	// 删除
    	IronOfferMainVo offer = offerClient.updateIronOfferMain(offerMainVo);
    	Assert.notNull(offer, "上架失败！");
    	
    	webRetMesage = new WebReturnMessage(true, "上架成功！");
    	
        return webRetMesage;
    }
    
    /**
     * 下架
     * @param offerCode
     * @return
     */
    @RequestMapping(value = "/offShelves/{offerCode}", method = RequestMethod.POST)
    @ResponseBody
    public WebReturnMessage offShelvesIronOffer(@PathVariable("offerCode") String offerCode){
    	WebReturnMessage webRetMesage = new WebReturnMessage(false, "下架失败！");
    	
    	IronOfferQueryVo queryVo = new IronOfferQueryVo();
    	queryVo.setOfferCode(offerCode);
    	
    	IronOfferMainVo offerMainVo = offerClient.getIronOffer(queryVo);
    	
    	offerMainVo.setUpdateUser("王雁飞测试");
    	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_OFF_SHELVES + "");
    	
    	// 删除
    	IronOfferMainVo offer = offerClient.updateIronOfferMain(offerMainVo);
    	Assert.notNull(offer, "下架失败！");
    	
    	webRetMesage = new WebReturnMessage(true, "下架成功！");
    	
        return webRetMesage;
    }
    
    /**
     * 删除
     * @param offerCode
     * @return
     */
    @RequestMapping(value = "/delete/{offerCode}", method = RequestMethod.POST)
    @ResponseBody
    public WebReturnMessage deleteIronOffer(@PathVariable("offerCode") String offerCode){
    	WebReturnMessage webRetMesage = new WebReturnMessage(false, "删除失败！");
    	
    	IronOfferQueryVo queryVo = new IronOfferQueryVo();
    	queryVo.setOfferCode(offerCode);
    	
    	IronOfferMainVo offerMainVo = offerClient.getIronOffer(queryVo);
    	
    	offerMainVo.setUpdateUser("王雁飞测试");
    	offerMainVo.setOfferStatus(EsteelConstant.OFFER_STATUS_SCRAP + "");
    	
    	// 删除
    	IronOfferMainVo offer = offerClient.updateIronOfferMain(offerMainVo);
    	Assert.notNull(offer, "删除失败！");
    	
    	webRetMesage = new WebReturnMessage(true, "删除成功！");
    	
        return webRetMesage;
    }
    
    /**
     * IronOfferMainVo 转 IronOfferResponse
     * @param offer
     * @return
     */
    private IronOfferResponse getIronOfferResponse(IronOfferMainVo offer) {
    	logger.info("getIronOfferResponse 开始");
    	IronOfferResponse offerResponse = new IronOfferResponse();
    	
    	if (offer == null) {
    		return offerResponse;
    	}
    	
    	logger.info("getIronOfferResponse IronOfferMainVo 复制到 IronOfferResponse");
    	BeanUtils.copyProperties(offer, offerResponse);
    	
    	offerResponse.setIsAnonymousText("匿名");
    	if (NumberUtils.toInt(offerResponse.getIsAnonymous()) == EsteelConstant.NO) {
    		offerResponse.setIsAnonymousText("公开");
    	}
    	
    	offerResponse.setIsDiscussPriceText("公开");
    	if (NumberUtils.toInt(offerResponse.getIsDesignation()) == EsteelConstant.YES) {
    		offerResponse.setIsDesignationText("定向交易");
    	}
    	
    	offerResponse.setIsDesignationText("一口价");
    	if (NumberUtils.toInt(offerResponse.getIsDiscussPrice()) == EsteelConstant.YES) {
    		offerResponse.setIsDiscussPriceText("允许议价");
    	}
    	
    	offerResponse.setIsMultiCargoText("一船货");
    	if (NumberUtils.toInt(offerResponse.getIsMultiCargo()) == EsteelConstant.YES) {
    		offerResponse.setIsMultiCargoText("一船多货");
    	}
    	
    	offerResponse.setIsSplitText("不拆分");
    	if (NumberUtils.toInt(offerResponse.getIsSplit()) == EsteelConstant.YES) {
    		offerResponse.setIsSplitText("拆分");
    	}
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	offerResponse.setPublishTimeText(offerResponse.getPublishTime() == null ? null : dateFormat.format(offerResponse.getPublishTime()));
    	
    	SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	offerResponse.setValidTimeText(offerResponse.getValidTime() == null ? null : timestampFormat.format(offerResponse.getValidTime()));
		
    	offerResponse.setOfferStatusText(EsteelConstant.OFFER_STATUS_NAME(NumberUtils.toInt(offerResponse.getOfferStatus())));
    	
    	logger.info("getIronOfferResponse 结束");
    	return offerResponse;
    }
    
    /**
     * OfferIronAttachVo 转 IronOfferAttachResponse
     * @param offerAttach
     * @return
     */
    private IronOfferAttachResponse getIronOfferAttachResponse(OfferIronAttachVo offerAttach) {
    	logger.info("getIronOfferAttachResponse 开始");
    	IronOfferAttachResponse offerAttachResponse = new IronOfferAttachResponse();
		
    	if (offerAttach == null) {
    		return offerAttachResponse;
    	}
    	
    	logger.info("getIronOfferAttachResponse OfferIronAttachVo 复制到 IronOfferAttachResponse");
		BeanUtils.copyProperties(offerAttach, offerAttachResponse);
		
		// 品名
		logger.info("getIronOfferAttachResponse 品名");
		if (offerAttach.getCommodityName() == null || !offerAttach.getCommodityName().trim().equals("")) {
	    	CommodityVo commodity = new CommodityVo();
	    	commodity.setCommodityId(NumberUtils.toLong(offerAttach.getCommodityId()));
	    	
	    	logger.info("getIronOfferAttachResponse baseClient.getCommodity("+ JsonUtils.toJsonString(commodity) +")");
	    	CommodityVo commodityVo = baseClient.getCommodity(commodity);
	    	if (commodityVo != null) {
	    		offerAttachResponse.setCommodityName(commodityVo.getCommodityName());
	    	}
		}
		
		// 港口
		logger.info("getIronOfferAttachResponse 港口");
		if (offerAttach.getPortName() == null || !offerAttach.getPortName().trim().equals("")) {
	    	PortVo port = new PortVo();
	    	port.setPortId(NumberUtils.toLong(offerAttach.getPortId()));
	    	
	    	logger.info("getIronOfferAttachResponse baseClient.getPort("+ JsonUtils.toJsonString(port) +")");
	    	PortVo portVo = baseClient.getPort(port);
	    	if (portVo != null) {
	    		offerAttachResponse.setPortName(portVo.getPortName());
	    	}
		}
		
		// 目的港
		logger.info("getIronOfferAttachResponse 目的港");
		if (offerAttach.getDischargePortName() == null || !offerAttach.getDischargePortName().trim().equals("")) {
	    	PortVo port = new PortVo();
	    	port.setPortId(NumberUtils.toLong(offerAttach.getDischargePortId()));
	    	
	    	logger.info("getIronOfferAttachResponse baseClient.getPort("+ JsonUtils.toJsonString(port) +")");
	    	PortVo portVo = baseClient.getPort(port);
	    	if (portVo != null) {
	    		offerAttachResponse.setDischargePortName(portVo.getPortName());
	    	}
		}
		
		// 指标类型
		logger.info("getIronOfferAttachResponse 指标类型");
		if (offerAttach.getIndicatorTypeName() == null || !offerAttach.getIndicatorTypeName().trim().equals("")) {
			AttributeValueOptionVo attributeValueOption= new AttributeValueOptionVo();
			attributeValueOption.setOptionId(NumberUtils.toLong(offerAttach.getIndicatorTypeId()));
	    	
			logger.info("getIronOfferAttachResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
			AttributeValueOptionVo attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
	    	if (attributeValueOptionVo != null) {
	    		offerAttachResponse.setIndicatorTypeName(attributeValueOptionVo.getOptionValue());
	    	}
		}
		
		// 是否在保税区 0:否, 1:是
		offerAttachResponse.setIsBondedAreaText("否");
		if (offerAttach.getIsBondedArea() == null 
				|| NumberUtils.toInt(offerAttach.getIsBondedArea()) == EsteelConstant.YES) {
			offerAttachResponse.setIsBondedAreaText("是");
		}
		
		// 装货港
		logger.info("getIronOfferAttachResponse 装货港");
		if (offerAttach.getLoadingPortName() == null || !offerAttach.getLoadingPortName().trim().equals("")) {
	    	PortVo port = new PortVo();
	    	port.setPortId(NumberUtils.toLong(offerAttach.getLoadingPortId()));
	    	
	    	logger.info("getIronOfferAttachResponse baseClient.getPort("+ JsonUtils.toJsonString(port) +")");
	    	PortVo portVo = baseClient.getPort(port);
	    	if (portVo != null) {
	    		offerAttachResponse.setLoadingPortName(portVo.getPortName());
	    	}
		}
		
		// 价格模式 0:固定价, 1:浮动价
		offerAttachResponse.setPriceModelText("固定价");
		if (offerAttach.getPriceModel() == null 
				|| NumberUtils.toInt(offerAttach.getPriceModel()) == EsteelConstant.PRICE_MODEL_FLOAT) {
			offerAttachResponse.setPriceModelText("浮动价");
		}
		
		// 价格术语基数 港
		logger.info("getIronOfferAttachResponse 价格术语基数 港");
		if (offerAttach.getPriceTermPortName() == null || !offerAttach.getPriceTermPortName().trim().equals("")) {
	    	PortVo port = new PortVo();
	    	port.setPortId(NumberUtils.toLong(offerAttach.getPriceTermPortId()));
	    	
	    	logger.info("getIronOfferAttachResponse baseClient.getPort("+ JsonUtils.toJsonString(port) +")");
	    	PortVo portVo = baseClient.getPort(port);
	    	if (portVo != null) {
	    		offerAttachResponse.setPriceTermPortName(portVo.getPortName());
	    	}
		}
		
		// 价格单位
		logger.info("getIronOfferAttachResponse 价格单位");
		offerAttachResponse.setPriceUnit("");
		if (offerAttach.getPriceUnitId() == null || NumberUtils.toLong(offerAttach.getPriceUnitId()) > 0) {
			AttributeValueOptionVo attributeValueOption= new AttributeValueOptionVo();
			attributeValueOption.setOptionId(NumberUtils.toLong(offerAttach.getPriceUnitId()));
	    	
			logger.info("getIronOfferAttachResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
			AttributeValueOptionVo attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
	    	if (attributeValueOptionVo != null) {
	    		offerAttachResponse.setPriceUnit(attributeValueOptionVo.getOptionValue());
	    	}
		}
		
		// 重量单位
		logger.info("getIronOfferAttachResponse 重量单位");
		offerAttachResponse.setQuantityUnit("");
		if (offerAttach.getQuantityUnitId() == null || !offerAttach.getPriceUnitId().trim().equals("")) {
			AttributeValueOptionVo attributeValueOption= new AttributeValueOptionVo();
			attributeValueOption.setOptionId(NumberUtils.toLong(offerAttach.getQuantityUnitId()));
	    	
			logger.info("getIronOfferAttachResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
			AttributeValueOptionVo attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
	    	if (attributeValueOptionVo != null) {
	    		offerAttachResponse.setQuantityUnit(attributeValueOptionVo.getOptionValue());
	    	}
		}
		
		logger.info("getIronOfferAttachResponse 结束");
    	return offerAttachResponse;
    }
    
    /**
     * clauseTemplateJson 转 IronOfferClauseResponse
     * @param clauseTemplateJson
     * @return
     */
    private IronOfferClauseResponse getIronOfferClauseResponse(String clauseTemplateJson) {
    	logger.info("getIronOfferClauseResponse 开始");
    	IronOfferClauseResponse offerClauseResponse = new IronOfferClauseResponse();
    	
    	if (clauseTemplateJson == null || clauseTemplateJson.trim().equals("")) {
    		return offerClauseResponse;
    	}
    	
    	logger.info("getIronOfferClauseResponse clauseTemplateJson 转 IronOfferClauseVo");
    	IronOfferClauseVo offerClauseVo = JsonUtils.toObject(clauseTemplateJson, IronOfferClauseVo.class);
    	
    	logger.info("getIronOfferClauseResponse IronOfferClauseVo 复制到 IronOfferClauseResponse");
    	BeanUtils.copyProperties(offerClauseVo, offerClauseResponse);
    	
    	PortVo port = new PortVo();
    	AttributeValueOptionVo attributeValueOption= new AttributeValueOptionVo();
    	
    	// 交货港口
    	logger.info("getIronOfferClauseResponse 交货港口");
    	port.setPortId(NumberUtils.toLong(offerClauseVo.getDelivery_Method_port()));
    	
    	logger.info("getIronOfferClauseResponse baseClient.getPort("+ JsonUtils.toJsonString(port) +")");
    	PortVo portVo = baseClient.getPort(port);
    	if (portVo != null) {
    		offerClauseResponse.setDelivery_Method_port(portVo.getPortName());
    	} else {
    		offerClauseResponse.setDelivery_Method_port("");
    	}
    	
    	// 计价方式
    	logger.info("getIronOfferClauseResponse 计价方式");
		attributeValueOption.setOptionId(NumberUtils.toLong(offerClauseVo.getPricing_method()));
    	
		logger.info("getIronOfferClauseResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
		AttributeValueOptionVo attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
    	if (attributeValueOptionVo != null) {
    		offerClauseResponse.setPricing_method(attributeValueOptionVo.getOptionValue());
    	} else {
    		offerClauseResponse.setPricing_method("");
    	}
    	
    	// 交货数量标准港口
    	logger.info("getIronOfferClauseResponse 交货数量标准港口");
    	port.setPortId(NumberUtils.toLong(offerClauseVo.getDelivery_quantity_port()));
    	
    	logger.info("getIronOfferClauseResponse baseClient.getPort("+ JsonUtils.toJsonString(port) +")");
		portVo = baseClient.getPort(port);
    	if (portVo != null) {
    		offerClauseResponse.setDelivery_quantity_port(portVo.getPortName());
    	} else {
    		offerClauseResponse.setDelivery_quantity_port("");
    	}
    	
    	// 计量方式
    	logger.info("getIronOfferClauseResponse 计量方式");
		attributeValueOption.setOptionId(NumberUtils.toLong(offerClauseVo.getMeasure_method()));
    	
		logger.info("getIronOfferClauseResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
		attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
    	if (attributeValueOptionVo != null) {
    		offerClauseResponse.setMeasure_method(attributeValueOptionVo.getOptionValue());
    	} else {
    		offerClauseResponse.setMeasure_method("");
    	}
    	
    	// 交易者类型
    	logger.info("getIronOfferClauseResponse 交易者类型");
    	attributeValueOption.setOptionId(NumberUtils.toLong(offerClauseVo.getTrader_type()));
    	
    	logger.info("getIronOfferClauseResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
		attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
    	if (attributeValueOptionVo != null) {
    		offerClauseResponse.setTrader_type(attributeValueOptionVo.getOptionValue());
    	} else {
    		offerClauseResponse.setTrader_type("");
    	}
    	
    	// 运输费承担方
    	logger.info("getIronOfferClauseResponse 运输费承担方");
		attributeValueOption.setOptionId(NumberUtils.toLong(offerClauseVo.getTransport_costs_bearer()));
    	
		logger.info("getIronOfferClauseResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
		attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
    	if (attributeValueOptionVo != null) {
    		offerClauseResponse.setTransport_costs_bearer(attributeValueOptionVo.getOptionValue());
    	} else {
    		offerClauseResponse.setTransport_costs_bearer("");
    	}

    	// 代理费承担方
    	logger.info("getIronOfferClauseResponse 代理费承担方");
		attributeValueOption.setOptionId(NumberUtils.toLong(offerClauseVo.getAgency_fee_bearer()));
    	
		logger.info("getIronOfferClauseResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
		attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
    	if (attributeValueOptionVo != null) {
    		offerClauseResponse.setAgency_fee_bearer(attributeValueOptionVo.getOptionValue());
    	} else {
    		offerClauseResponse.setAgency_fee_bearer("");
    	}

    	// 内贸港口建设费承担方
    	logger.info("getIronOfferClauseResponse 内贸港口建设费承担方");
		attributeValueOption.setOptionId(NumberUtils.toLong(offerClauseVo.getPort_construction_fee_bearer()));
    	
		logger.info("getIronOfferClauseResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
		attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
    	if (attributeValueOptionVo != null) {
    		offerClauseResponse.setPort_construction_fee_bearer(attributeValueOptionVo.getOptionValue());
    	} else {
    		offerClauseResponse.setPort_construction_fee_bearer("");
    	}

    	// 二程船运费承担方
    	logger.info("getIronOfferClauseResponse 二程船运费承担方");
		attributeValueOption.setOptionId(NumberUtils.toLong(offerClauseVo.getSecond_vessel_fee_bearer()));
    	
		logger.info("getIronOfferClauseResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
		attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
    	if (attributeValueOptionVo != null) {
    		offerClauseResponse.setSecond_vessel_fee_bearer(attributeValueOptionVo.getOptionValue());
    	} else {
    		offerClauseResponse.setSecond_vessel_fee_bearer("");
    	}

    	// 过磅或水尺费承担方
    	logger.info("getIronOfferClauseResponse 过磅或水尺费承担方");
		attributeValueOption.setOptionId(NumberUtils.toLong(offerClauseVo.getWeighing_fee_bearer()));
    	
		logger.info("getIronOfferClauseResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
		attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
    	if (attributeValueOptionVo != null) {
    		offerClauseResponse.setWeighing_fee_bearer(attributeValueOptionVo.getOptionValue());
    	} else {
    		offerClauseResponse.setWeighing_fee_bearer("");
    	}

    	// 超期产生的堆存费承担方
    	logger.info("getIronOfferClauseResponse 超期产生的堆存费承担方");
		attributeValueOption.setOptionId(NumberUtils.toLong(offerClauseVo.getOverdue_storage_fee_bearer()));
    	
		logger.info("getIronOfferClauseResponse baseClient.getAttributeValueOption("+ JsonUtils.toJsonString(attributeValueOption) +")");
		attributeValueOptionVo = baseClient.getAttributeValueOption(attributeValueOption);
    	if (attributeValueOptionVo != null) {
    		offerClauseResponse.setOverdue_storage_fee_bearer(attributeValueOptionVo.getOptionValue());
    	} else {
    		offerClauseResponse.setOverdue_storage_fee_bearer("");
    	}

    	logger.info("getIronOfferClauseResponse 结束");
    	return offerClauseResponse;
    }
}