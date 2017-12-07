package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @ClassName: IronPricingOfferVo
 * @Description: 铁矿点价报盘DTO
 * @author wyf
 * @date 2017年12月6日 下午1:28:25 
 *
 */
public class IronPricingOfferVo extends IronOfferBaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 铁矿报盘附表ID
	 */
	private long offerAttachId;
	/**
	 * 基差
	 */
	private BigDecimal baseDifference;
	/**
	 * 品名ID
	 */
	private long commodityId;
	/**
	 * 品名名称
	 */
	private String commodityName;
	/**
	 * 点价期截止
	 */
	private String deliveryTimeEnd;
	/**
	 * 交货期起始
	 */
	private String deliveryTimeStart;
	/**
	 * 连铁合约
	 */
	private String ironContract;
	/**
	 * 是否在保税区 0:否, 1:是
	 */
	private int isBondedArea = 1;
	/**
	 * 起订量
	 */
	private BigDecimal minQuantity;
	/**
	 * 溢短装
	 */
	private BigDecimal moreOrLess;
	/**
	 * 铁矿报盘附表编码
	 */
	private String offerAttachCode;
	/**
	 * 铁矿报盘ID
	 */
	private long offerId;
	/**
	 * 报盘重量
	 */
	private BigDecimal offerQuantity;
	/**
	 * 点价期截止
	 */
	private String pricingPeriodEnd;
	/**
	 * 点价期起始
	 */
	private String pricingPeriodStart;
	/**
	 * 港口ID
	 */
	private long portId;
	/**
	 * 港口
	 */
	private String portName;
	/**
	 * 价格模式 0:固定价, 1:浮动价
	 */
	private int priceModel = 0;
	/**
	 * 价格单位ID
	 */
	private long priceUnitId;
	/**
	 * 价格数值
	 */
	private BigDecimal priceValue;
	/**
	 * 重量单位ID
	 */
	private long quantityUnitId;
	/**
	 * 备注
	 */
	private String offerAttachRemarks;
	/**
	 * 已售重量
	 */
	private BigDecimal soldQuantity;
	
	public long getOfferAttachId() {
		return offerAttachId;
	}
	public void setOfferAttachId(long offerAttachId) {
		this.offerAttachId = offerAttachId;
	}
	public BigDecimal getBaseDifference() {
		return baseDifference;
	}
	public void setBaseDifference(BigDecimal baseDifference) {
		this.baseDifference = baseDifference;
	}
	public long getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(long commodityId) {
		this.commodityId = commodityId;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getDeliveryTimeEnd() {
		return deliveryTimeEnd;
	}
	public void setDeliveryTimeEnd(String deliveryTimeEnd) {
		this.deliveryTimeEnd = deliveryTimeEnd;
	}
	public String getDeliveryTimeStart() {
		return deliveryTimeStart;
	}
	public void setDeliveryTimeStart(String deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}
	public String getIronContract() {
		return ironContract;
	}
	public void setIronContract(String ironContract) {
		this.ironContract = ironContract;
	}
	public int getIsBondedArea() {
		return isBondedArea;
	}
	public void setIsBondedArea(int isBondedArea) {
		this.isBondedArea = isBondedArea;
	}
	public BigDecimal getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(BigDecimal minQuantity) {
		this.minQuantity = minQuantity;
	}
	public BigDecimal getMoreOrLess() {
		return moreOrLess;
	}
	public void setMoreOrLess(BigDecimal moreOrLess) {
		this.moreOrLess = moreOrLess;
	}
	public String getOfferAttachCode() {
		return offerAttachCode;
	}
	public void setOfferAttachCode(String offerAttachCode) {
		this.offerAttachCode = offerAttachCode;
	}
	public BigDecimal getOfferQuantity() {
		return offerQuantity;
	}
	public void setOfferQuantity(BigDecimal offerQuantity) {
		this.offerQuantity = offerQuantity;
	}
	public String getPricingPeriodEnd() {
		return pricingPeriodEnd;
	}
	public void setPricingPeriodEnd(String pricingPeriodEnd) {
		this.pricingPeriodEnd = pricingPeriodEnd;
	}
	public String getPricingPeriodStart() {
		return pricingPeriodStart;
	}
	public void setPricingPeriodStart(String pricingPeriodStart) {
		this.pricingPeriodStart = pricingPeriodStart;
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
	public int getPriceModel() {
		return priceModel;
	}
	public void setPriceModel(int priceModel) {
		this.priceModel = priceModel;
	}
	public long getPriceUnitId() {
		return priceUnitId;
	}
	public void setPriceUnitId(long priceUnitId) {
		this.priceUnitId = priceUnitId;
	}
	public BigDecimal getPriceValue() {
		return priceValue;
	}
	public void setPriceValue(BigDecimal priceValue) {
		this.priceValue = priceValue;
	}
	public long getQuantityUnitId() {
		return quantityUnitId;
	}
	public void setQuantityUnitId(long quantityUnitId) {
		this.quantityUnitId = quantityUnitId;
	}
	public String getOfferAttachRemarks() {
		return offerAttachRemarks;
	}
	public void setOfferAttachRemarks(String offerAttachRemarks) {
		this.offerAttachRemarks = offerAttachRemarks;
	}
	public BigDecimal getSoldQuantity() {
		return soldQuantity;
	}
	public void setSoldQuantity(BigDecimal soldQuantity) {
		this.soldQuantity = soldQuantity;
	}
}
