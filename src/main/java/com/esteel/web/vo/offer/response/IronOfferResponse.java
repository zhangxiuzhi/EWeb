package com.esteel.web.vo.offer.response;

import java.io.Serializable;

import com.esteel.web.vo.offer.IronOfferMainVo;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 
 * @ClassName: IronOfferPageVo
 * @Description: 铁矿报盘页面DTO
 * @author wyf
 * @date 2017年12月20日 下午2:59:16 
 *
 */
public class IronOfferResponse extends IronOfferMainVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 报盘方
	 */
	private long companyName;
	/**
	 * 铁矿报盘状态 文本
	 * 扩展字段
	 */
	private String offerStatusText;
	/**
	 * 是否匿名文本
	 */
	private String isAnonymousText;
	/**
	 * 发布时间 文本
	 * 扩展字段
	 */
	private String publishTimeText;
	/**
	 * 交易方式 文本
	 * 扩展字段
	 */
	private int tradeModeText;
	/**
	 * 有效日期  文本
	 * 扩展字段
	 */
	private String validTimeText;
	/**
	 * 品名名称
	 * 扩展字段
	 */
	private String commodityName;
	/**
	 * 化学元素指标 Fe
	 * 扩展字段
	 */
	private String fe;
	/**
	 * 港口ID
	 * 扩展字段
	 */
	private long portId;
	/**
	 * 港口
	 * 扩展字段
	 */
	private String portName = "";
	/**
	 * 价格数值
	 * 扩展字段
	 */
	private String priceValue;
	/**
	 * 价格描述
	 * 扩展字段
	 */
	private String priceDescription;
	/**
	 * 价格 文本
	 * 扩展字段
	 */
	private String priceText;
	/**
	 * 可交易重量
	 * 扩展字段
	 */
	private String tradableQuantity;
	/**
	 * 运输状态 Json数据
	 * 扩展字段
	 */
	private String transportDescription;
	/**
	 * 点价期
	 * 扩展字段
	 */
	private String pricingPeriod;
	/**
	 * 交货期
	 * 扩展字段
	 */
	private String deliveryPeriod;
	
	@JsonCreator
	public IronOfferResponse() {
		super();
	}
	
	public long getCompanyName() {
		return companyName;
	}
	public void setCompanyName(long companyName) {
		this.companyName = companyName;
	}
	public String getOfferStatusText() {
		return offerStatusText;
	}
	public void setOfferStatusText(String offerStatusText) {
		this.offerStatusText = offerStatusText;
	}
	public String getIsAnonymousText() {
		return isAnonymousText;
	}
	public void setIsAnonymousText(String isAnonymousText) {
		this.isAnonymousText = isAnonymousText;
	}
	public String getPublishTimeText() {
		return publishTimeText;
	}
	public void setPublishTimeText(String publishTimeText) {
		this.publishTimeText = publishTimeText;
	}
	public int getTradeModeText() {
		return tradeModeText;
	}
	public void setTradeModeText(int tradeModeText) {
		this.tradeModeText = tradeModeText;
	}
	public String getValidTimeText() {
		return validTimeText;
	}
	public void setValidTimeText(String validTimeText) {
		this.validTimeText = validTimeText;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getFe() {
		return fe;
	}
	public void setFe(String fe) {
		this.fe = fe;
	}
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public String getPriceValue() {
		return priceValue;
	}
	public void setPriceValue(String priceValue) {
		this.priceValue = priceValue;
	}
	public String getPriceDescription() {
		return priceDescription;
	}
	public void setPriceDescription(String priceDescription) {
		this.priceDescription = priceDescription;
	}
	public String getPriceText() {
		return priceText;
	}
	public void setPriceText(String priceText) {
		this.priceText = priceText;
	}
	public String getTradableQuantity() {
		return tradableQuantity;
	}
	public void setTradableQuantity(String tradableQuantity) {
		this.tradableQuantity = tradableQuantity;
	}
	public String getTransportDescription() {
		return transportDescription;
	}
	public void setTransportDescription(String transportDescription) {
		this.transportDescription = transportDescription;
	}
	public String getPricingPeriod() {
		return pricingPeriod;
	}
	public void setPricingPeriod(String pricingPeriod) {
		this.pricingPeriod = pricingPeriod;
	}
	public String getDeliveryPeriod() {
		return deliveryPeriod;
	}
	public void setDeliveryPeriod(String deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}
}
