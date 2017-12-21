package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.esteel.common.vo.StatusMSGVo;

/**
 * 
 * @ClassName: IronOfferMainVo
 * @Description: 铁矿报盘主表DTO
 * @author wyf
 * @date 2017年12月4日 下午3:46:41 
 *
 */
public class IronOfferMainVo extends StatusMSGVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 铁矿报盘ID
	 */
	private String offerId;
	/**
	 * 交货结算条款模版ID
	 */
	private String clauseTemplateId;
	/**
	 * 交货结算条款Json
	 */
	private String clauseTemplateJson;
	/**
	 * 报盘方ID
	 */
	private Long companyId;
	/**
	 * 合同模版ID
	 */
	private String contractTemplateId;
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
	private Long createUserId;
	/**
	 * 是否匿名 0:否, 1:是
	 */
	private String isAnonymous = "1";
	/**
	 * 是否指定 0:否, 1:是
	 */
	private String isDesignation = "0";
	/**
	 * 是否议价 0:否, 1:是
	 */
	private String isDiscussPrice = "0";
	/**
	 * 是否一船多货 0:否, 1:是
	 */
	private String isMultiCargo = "0";
	/**
	 * 是否拆分 0:否, 1:是
	 */
	private String isSplit = "0";
	/**
	 * 铁矿报盘编码
	 */
	private String offerCode;
	/**
	 * 铁矿报盘状态 0:草稿, 100:在售, 200:成交, 300:下架, 999:作废
	 */
	private String offerStatus;
	/**
	 * 报盘类型 0:普通报盘,1:保证金报盘,2:信誉报盘
	 */
	private String offerType;
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
	private Long publishUserId;
	/**
	 * 备注
	 */
	private String offerRemarks;
	/**
	 * 交易方向 0:销售, 1:采购
	 */
	private String tradeDirection;
	/**
	 * 交易方式 1:现货, 2:点价, 3:远期
	 */
	private Integer tradeMode;
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
	private Long updateUserId;
	/**
	 * 有效日期
	 */
	private Date validTime;
	/**
	 * 版本号
	 */
	private int versionId = 1;
	/**
	 * 指定对手IDList
	 * 报盘扩展字段
	 */
	private List<Long> counterpartyIdList;
	/**
	 * 附件List
	 * 扩展属性
	 */
	private List<OfferAffixVo> offerAffixList = new ArrayList<>();
	/**
	 * 报盘附表List 
	 * 报盘扩展属性
	 */
	private List<OfferIronAttachVo> offerAttachList = new ArrayList<>();
	
	public void addOfferAffix(OfferAffixVo offerAffix) {
		if (offerAffix == null) {
			return ;
		}
		
		String affixPath = offerAffix.getAffixPath() == null ? "" : offerAffix.getAffixPath().trim();
		
		if (offerAffix.getAffixId() > 0 || !affixPath.equals("")) {
			offerAffix.setAffixPath(affixPath);
			
			this.offerAffixList.add(offerAffix);
		}
	}
	
	public void removeOfferAffix(OfferAffixVo offerAffix) {
		for (int i = 0; i < offerAffixList.size(); i ++) {
			OfferAffixVo tmp = offerAffixList.get(i);
			
			if (offerAffix.getAffixId() ==  tmp.getAffixId()) {
				offerAffixList.remove(i);
				break;
			}
		}
	}
	
	public void clearOfferAffixList() {
		this.offerAffixList.clear();
	}
	
	public void addOfferIronAttach(OfferIronAttachVo offerAttach) {
		this.offerAttachList.add(offerAttach);
	}
	
	public void removeOfferAttach(OfferIronAttachVo offerAttach) {
		for (int i = 0; i < offerAttachList.size(); i ++) {
			OfferIronAttachVo tmp = offerAttachList.get(i);
			
			if (offerAttach.getOfferAttachId() ==  tmp.getOfferAttachId()) {
				offerAttachList.remove(i);
				break;
			}
		}
	}
	
	public void clearOfferAttachList() {
		this.offerAttachList.clear();
	}
	
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getClauseTemplateId() {
		return clauseTemplateId;
	}
	public void setClauseTemplateId(String clauseTemplateId) {
		this.clauseTemplateId = clauseTemplateId;
	}
	public String getClauseTemplateJson() {
		return clauseTemplateJson;
	}
	public void setClauseTemplateJson(String clauseTemplateJson) {
		this.clauseTemplateJson = clauseTemplateJson;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getContractTemplateId() {
		return contractTemplateId;
	}
	public void setContractTemplateId(String contractTemplateId) {
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
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public String getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public String getIsDesignation() {
		return isDesignation;
	}
	public void setIsDesignation(String isDesignation) {
		this.isDesignation = isDesignation;
	}
	public String getIsDiscussPrice() {
		return isDiscussPrice;
	}
	public void setIsDiscussPrice(String isDiscussPrice) {
		this.isDiscussPrice = isDiscussPrice;
	}
	public String getIsMultiCargo() {
		return isMultiCargo;
	}
	public void setIsMultiCargo(String isMultiCargo) {
		this.isMultiCargo = isMultiCargo;
	}
	public String getIsSplit() {
		return isSplit;
	}
	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}
	public String getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
	public String getOfferStatus() {
		return offerStatus;
	}
	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}
	public String getOfferType() {
		return offerType;
	}
	public void setOfferType(String offerType) {
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
	public Long getPublishUserId() {
		return publishUserId;
	}
	public void setPublishUserId(Long publishUserId) {
		this.publishUserId = publishUserId;
	}
	public String getOfferRemarks() {
		return offerRemarks;
	}
	public void setOfferRemarks(String offerRemarks) {
		this.offerRemarks = offerRemarks;
	}
	public String getTradeDirection() {
		return tradeDirection;
	}
	public void setTradeDirection(String tradeDirection) {
		this.tradeDirection = tradeDirection;
	}
	public Integer getTradeMode() {
		return tradeMode;
	}
	public void setTradeMode(Integer tradeMode) {
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
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getValidTime() {
		return validTime;
	}
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}
	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public List<Long> getCounterpartyIdList() {
		return counterpartyIdList;
	}
	public void setCounterpartyIdList(List<Long> counterpartyIdList) {
		this.counterpartyIdList = counterpartyIdList;
	}
	public List<OfferAffixVo> getOfferAffixList() {
		return offerAffixList;
	}
	public void setOfferAffixList(List<OfferAffixVo> offerAffixList) {
		this.offerAffixList = offerAffixList;
	}
	public List<OfferIronAttachVo> getOfferAttachList() {
		return offerAttachList;
	}
	public void setOfferAttachList(List<OfferIronAttachVo> offerAttachList) {
		this.offerAttachList = offerAttachList;
	}
}
