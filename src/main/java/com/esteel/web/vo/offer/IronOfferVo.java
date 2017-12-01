package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: IronOfferVo
 * @Description: TODO
 * @author wyf
 * @date 2017年12月4日 下午3:46:41 
 *
 */
public class IronOfferVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long offerId;
	private long clauseTemplateId;
	private String clauseTemplateJson;
	private long companyId;
	private long contractTemplateId;
	private Date createTime;
	private String createUser;
	private long createUserId;
	private int isAnonymous;
	private int isDesignation;
	private int isDiscussPrice;
	private int isMultiCargo;
	private int isSplit;
	private String offerCode;
	private int offerStatus;
	private int offerType;
	private Date publishTime;
	private String publishUser;
	private long publishUserId;
	private String remarks;
	private int tradeDirection;
	private int tradeMode;
	private Date updateTime;
	private String updateUser;
	private long updateUserId;
	private Date validTime;
	private int version;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
}
