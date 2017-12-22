package com.esteel.web.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteel.common.controller.WebReturnMessage;
import com.esteel.common.util.EsteelConstant;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.MemberClient;
import com.esteel.web.service.OfferClient;
import com.esteel.web.vo.MemberUserVo;
import com.esteel.web.vo.base.CommodityVo;
import com.esteel.web.vo.base.PortVo;
import com.esteel.web.vo.offer.IronOfferMainVo;
import com.esteel.web.vo.offer.IronOfferPage;
import com.esteel.web.vo.offer.IronOfferQueryVo;
import com.esteel.web.vo.offer.IronOfferResponse;
import com.esteel.web.vo.offer.OfferIronAttachVo;
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
	@Autowired
	BaseClient baseClient;
	@Autowired
	MemberClient memberClient;
	@Autowired
	OfferClient offerClient;
	@Autowired
    TfsManager tfsManager;
	
	@RequestMapping(value = "/myList", method = RequestMethod.GET)
    public String myOfferUI(Model model){
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
    	List<CommodityVo> ironCommoditys = baseClient.findCommodityListByIron();
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
		
    	List<PortVo> ports = baseClient.findPortListForOffer();
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
    	offerStatusMap.put("value", EsteelConstant.OFFER_STATUS_DRAFT + "");
    	offerStatusMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_IN_SALE));
    	
    	offerStatusMap = new HashMap<>();
    	offerStatusList.add(offerStatusMap);
    	offerStatusMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_SOLD_OUT));
    	offerStatusMap.put("value", EsteelConstant.OFFER_STATUS_DRAFT + "");
    	offerStatusMap.put("key", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_SOLD_OUT));
    	
    	offerStatusMap = new HashMap<>();
    	offerStatusList.add(offerStatusMap);
    	offerStatusMap.put("text", EsteelConstant.OFFER_STATUS_NAME(EsteelConstant.OFFER_STATUS_OFF_SHELVES));
    	offerStatusMap.put("value", EsteelConstant.OFFER_STATUS_DRAFT + "");
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
    	
    	List<MemberUserVo> users = memberClient.findmembers(1);
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
    	
        return "/offer/myOffer";
    }
	
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	@ResponseBody
    public IronOfferPage ironOfferPage(IronOfferQueryVo queryVo){
		Assert.notNull(queryVo, "查询失败！");
		
		if (queryVo.getSizePerPage() == 0) {
			queryVo.setSizePerPage(10);
		}

    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	DecimalFormat priceFormat = new DecimalFormat("###,##0.0");
    	DecimalFormat qtyFormat = new DecimalFormat("###,###,###,##0");
    	
    	IronOfferPage page = offerClient.query(queryVo);
    	
    	List<IronOfferResponse> offerList = page.getData();
    	for (IronOfferResponse pageVo: offerList) {
    		if (NumberUtils.toInt(pageVo.getIsAnonymous()) == EsteelConstant.NO) {
    			pageVo.setIsAnonymousText("公开");
    		} else if (NumberUtils.toInt(pageVo.getIsAnonymous()) == EsteelConstant.YES) {
    			pageVo.setIsAnonymousText("匿名");
    		}
    		pageVo.setPublishTimeText(pageVo.getPublishTime() == null ? null : dateFormat.format(pageVo.getPublishTime()));
    		pageVo.setValidTimeText(pageVo.getValidTime() == null ? null : timestampFormat.format(pageVo.getValidTime()));
    		
    		pageVo.setOfferStatusText(EsteelConstant.OFFER_STATUS_NAME(NumberUtils.toInt(pageVo.getOfferStatus())));
    		
    		List<OfferIronAttachVo> offerAttachEntityList = pageVo.getOfferAttachList();
    		if (offerAttachEntityList != null && offerAttachEntityList.size() > 0){
    			// 交易方式 1:现货, 2:点价, 3:远期
    			OfferIronAttachVo firstAttach = offerAttachEntityList.get(0);
    			OfferIronAttachVo secondAttach = null;
    			if (pageVo.getTradeMode() == EsteelConstant.TRADE_MODE_FUTURES 
    					&& offerAttachEntityList.size() > 1) {
    				secondAttach = offerAttachEntityList.get(1);
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
    			if (secondAttach != null && secondAttach.getDeliveryPeriodEnd() != null) {
    				pricingPeriod += 
    						firstAttach.getDeliveryPeriodEnd() == null ? "" : dateFormat.format(firstAttach.getDeliveryPeriodEnd());
    			}
    			pageVo.setPricingPeriod(pricingPeriod);
    			// 交货期
    			String deliveryPeriod = 
    					firstAttach.getDeliveryPeriodStart() == null ? "" : dateFormat.format(firstAttach.getDeliveryPeriodStart());
    			deliveryPeriod += "-";
    			if (secondAttach != null && secondAttach.getDeliveryPeriodEnd() != null) {
    				deliveryPeriod += 
    						firstAttach.getDeliveryPeriodEnd() == null ? "" : dateFormat.format(firstAttach.getDeliveryPeriodEnd());
    			}
    			pageVo.setDeliveryPeriod(deliveryPeriod);
    		}
    	}
    	
        return page;
    }
	
    @RequestMapping(value = "/detailBySelf/{offerCode}", method = RequestMethod.GET)
    public String getIronOffer(@PathVariable("offerCode") String offerCode, Model model){
    	Assert.notNull(offerCode, "点击失败！");
    	
    	IronOfferQueryVo queryVo = new IronOfferQueryVo();
    	queryVo.setOfferCode(offerCode);
    	
    	IronOfferMainVo offer = offerClient.getIronOffer(queryVo);
    	model.addAttribute("offer", offer);
    	
    	if (offer.getTradeMode() == EsteelConstant.TRADE_MODE_INSTOCK) {
    		 return "/myOffer/detail/inStock";
		} else if (offer.getTradeMode() == EsteelConstant.TRADE_MODE_PRICING) {
			 return "/myOffer/detail/pricing";
		}  else if (offer.getTradeMode() == EsteelConstant.TRADE_MODE_FUTURES) {
			 return "/myOffer/detail/futures";
		} 
    	
        return "/myOffer/detail/inStock";
    }
    
    /**
     * 上架
     * @param offerCode
     * @param model
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
}

