package com.esteel.web.vo.offer;

import java.io.Serializable;

/**
 * 
 * @ClassName: IronOfferPageVo
 * @Description: 铁矿报盘页面DTO
 * @author wyf
 * @date 2017年12月20日 下午2:59:16 
 *
 */
public class IronOfferPageVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 铁矿报盘ID
	 */
	private long offerId;
	/**
	 * 交货结算条款模版ID
	 */
	private long clauseTemplateId;
	/**
	 * 交货结算条款Json
	 */
	private String clauseTemplateJson;
	/**
	 * 报盘方ID
	 */
	private long companyId;
	/**
	 * 合同模版ID
	 */
	private long contractTemplateId;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 创建人ID
	 */
	private long createUserId;
	/**
	 * 是否匿名 0:否, 1:是
	 */
	private int isAnonymous = 1;
	/**
	 * 是否指定 0:否, 1:是
	 */
	private int isDesignation = 0;
	/**
	 * 是否议价 0:否, 1:是
	 */
	private int isDiscussPrice = 0;
	/**
	 * 是否一船多货 0:否, 1:是
	 */
	private int isMultiCargo = 0;
	/**
	 * 是否拆分 0:否, 1:是
	 */
	private int isSplit = 0;
	/**
	 * 铁矿报盘编码
	 */
	private String offerCode;
	/**
	 * 铁矿报盘状态 0:草稿, 100:在售, 200:成交, 300:下架, 999:作废
	 */
	private int offerStatus = 0;
	/**
	 * 报盘类型 0:普通报盘,1:保证金报盘,2:信誉报盘
	 */
	private int offerType = 0;
	/**
	 * 发布时间
	 */
	private String publishTime;
	/**
	 * 发布人
	 */
	private String publishUser;
	/**
	 * 发布人ID
	 */
	private long publishUserId;
	/**
	 * 备注
	 */
	private String offerRemarks;
	/**
	 * 交易方向 0:销售, 1:采购
	 */
	private int tradeDirection;
	/**
	 * 交易方式 1:现货, 2:点价, 3:远期
	 */
	private int tradeMode;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;
	/**
	 * 更新人ID
	 */
	private long updateUserId;
	/**
	 * 有效日期
	 */
	private String validTime;
	/**
	 * 版本号
	 */
	private int versionId;
	/**
	 * 品名ID
	 */
	private String commodityId;
	/**
	 * 品名名称
	 */
	private String commodityName;
	/**
	 * 化学元素指标 Fe
	 */
	private String fe;
	/**
	 * 港口ID
	 */
	private long portId;
	/**
	 * 港口
	 */
	private String portName = "";
	/**
	 * 价格数值
	 */
	private String priceValue;
	/**
	 * 价格描述
	 */
	private String priceDescription;
	/**
	 * 可交易重量
	 */
	private String tradableQuantity;
	/**
	 * 运输状态 Json数据
	 */
	private String transportDescription;
	/**
	 * 点价期
	 */
	private String pricingPeriod;
	/**
	 * 交货期
	 */
	private String deliveryPeriod;
	
	public long getOfferId() {
		return offerId;
	}
	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}
	public long getClauseTemplateId() {
		return clauseTemplateId;
	}
	public void setClauseTemplateId(long clauseTemplateId) {
		this.clauseTemplateId = clauseTemplateId;
	}
	public String getClauseTemplateJson() {
		return clauseTemplateJson;
	}
	public void setClauseTemplateJson(String clauseTemplateJson) {
		this.clauseTemplateJson = clauseTemplateJson;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public long getContractTemplateId() {
		return contractTemplateId;
	}
	public void setContractTemplateId(long contractTemplateId) {
		this.contractTemplateId = contractTemplateId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
	public int getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(int isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public int getIsDesignation() {
		return isDesignation;
	}
	public void setIsDesignation(int isDesignation) {
		this.isDesignation = isDesignation;
	}
	public int getIsDiscussPrice() {
		return isDiscussPrice;
	}
	public void setIsDiscussPrice(int isDiscussPrice) {
		this.isDiscussPrice = isDiscussPrice;
	}
	public int getIsMultiCargo() {
		return isMultiCargo;
	}
	public void setIsMultiCargo(int isMultiCargo) {
		this.isMultiCargo = isMultiCargo;
	}
	public int getIsSplit() {
		return isSplit;
	}
	public void setIsSplit(int isSplit) {
		this.isSplit = isSplit;
	}
	public String getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
	public int getOfferStatus() {
		return offerStatus;
	}
	public void setOfferStatus(int offerStatus) {
		this.offerStatus = offerStatus;
	}
	public int getOfferType() {
		return offerType;
	}
	public void setOfferType(int offerType) {
		this.offerType = offerType;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getPublishUser() {
		return publishUser;
	}
	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}
	public long getPublishUserId() {
		return publishUserId;
	}
	public void setPublishUserId(long publishUserId) {
		this.publishUserId = publishUserId;
	}
	public String getOfferRemarks() {
		return offerRemarks;
	}
	public void setOfferRemarks(String offerRemarks) {
		this.offerRemarks = offerRemarks;
	}
	public int getTradeDirection() {
		return tradeDirection;
	}
	public void setTradeDirection(int tradeDirection) {
		this.tradeDirection = tradeDirection;
	}
	public int getTradeMode() {
		return tradeMode;
	}
	public void setTradeMode(int tradeMode) {
		this.tradeMode = tradeMode;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getValidTime() {
		return validTime;
	}
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
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
