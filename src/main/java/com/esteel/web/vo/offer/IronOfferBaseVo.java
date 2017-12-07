package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.util.Date;

import com.esteel.common.vo.StatusMSGVo;

/**
 * 
 * @ClassName: IronOfferVo
 * @Description: 铁矿报盘主表DTO
 * @author wyf
 * @date 2017年12月4日 下午3:46:41 
 *
 */
public class IronOfferBaseVo extends StatusMSGVo implements Serializable {
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
	private Date createTime;
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
	private Date publishTime;
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
	private int tradeDirection = 0;
	/**
	 * 交易方式 1:现货, 2:点价, 3:远期
	 */
	private int tradeMode;
	/**
	 * 更新时间
	 */
	private Date updateTime;
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
	private Date validTime;
	/**
	 * 版本号
	 */
	private int version;
	/**
	 * 是否拆分 0:否, 1:是
	 * 扩展字段
	 */
	private String isSplitStr = "0";
	/**
	 * 是否一船多货 0:否, 1:是
	 * 扩展字段
	 */
	private String isMultiCargoStr = "0";
	/**
	 * 是否匿名 0:否, 1:是
	 * 扩展字段
	 */
	private String isAnonymousStr = "1";
	/**
	 * 有效日期
	 * 扩展字段
	 */
	private String validTimeStr;
	/**
	 * 指定对手
	 * 扩展字段
	 */
	private String counterpartyIdStr;
	/**
	 * 报盘附件
	 * 扩展字段
	 */
	private String offerAffixPath;
	/**
	 * 合同附件
	 * 扩展字段
	 */
	private String contractAffixPath;
	/**
	 * 品名ID
	 * 扩展字段
	 */
	private String commodityIdStr;
	/**
	 * 港口ID
	 * 扩展字段
	 */
	private String portIdStr;
	/**
	 * 指标类型ID
	 * 扩展字段
	 */
	private String indicatorTypeIdStr;
	private String[] arr;
	
	public String[] getArr() {
		return arr;
	}
	public void setArr(String[] arr) {
		this.arr = arr;
	}
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
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
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
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
	public Date getValidTime() {
		return validTime;
	}
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getIsSplitStr() {
		return isSplitStr;
	}
	public void setIsSplitStr(String isSplitStr) {
		this.isSplitStr = isSplitStr;
	}
	public String getIsMultiCargoStr() {
		return isMultiCargoStr;
	}
	public void setIsMultiCargoStr(String isMultiCargoStr) {
		this.isMultiCargoStr = isMultiCargoStr;
	}
	public String getIsAnonymousStr() {
		return isAnonymousStr;
	}
	public void setIsAnonymousStr(String isAnonymousStr) {
		this.isAnonymousStr = isAnonymousStr;
	}
	public String getValidTimeStr() {
		return validTimeStr;
	}
	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}
	public String getCounterpartyIdStr() {
		return counterpartyIdStr;
	}
	public void setCounterpartyIdStr(String counterpartyIdStr) {
		this.counterpartyIdStr = counterpartyIdStr;
	}
	public String getOfferAffixPath() {
		return offerAffixPath;
	}
	public void setOfferAffixPath(String offerAffixPath) {
		this.offerAffixPath = offerAffixPath;
	}
	public String getContractAffixPath() {
		return contractAffixPath;
	}
	public void setContractAffixPath(String contractAffixPath) {
		this.contractAffixPath = contractAffixPath;
	}
	public String getCommodityIdStr() {
		return commodityIdStr;
	}
	public void setCommodityIdStr(String commodityIdStr) {
		this.commodityIdStr = commodityIdStr;
	}
	public String getPortIdStr() {
		return portIdStr;
	}
	public void setPortIdStr(String portIdStr) {
		this.portIdStr = portIdStr;
	}
	public String getIndicatorTypeIdStr() {
		return indicatorTypeIdStr;
	}
	public void setIndicatorTypeIdStr(String indicatorTypeIdStr) {
		this.indicatorTypeIdStr = indicatorTypeIdStr;
	}
}
