package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @ClassName: IronInStockOfferRequest
 * @Description: 铁矿港口现货报盘Request
 * @author wyf
 * @date 2017年12月6日 下午1:28:25 
 *
 */
public class IronInStockOfferRequest extends OfferIronAttachVo implements Serializable {
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
	 * 合同模版ID
	 */
	private String contractTemplateId;
	/**
	 * 是否匿名 0:否, 1:是
	 */
//	@NotBlank
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
//	@NotBlank
	private String isMultiCargo = "0";
	/**
	 * 是否拆分 0:否, 1:是
	 */
//	@NotBlank
	private String isSplit = "0";
	/**
	 * 铁矿报盘状态 0:草稿, 100:在售, 200:成交, 300:下架, 999:作废
	 */
	private String offerStatus = "0";
	/**
	 * 报盘类型 0:普通报盘,1:保证金报盘,2:信誉报盘
	 */
	private String offerType = "0";
	/**
	 * 备注
	 */
	private String offerRemarks;
	/**
	 * 交易方向 0:销售, 1:采购
	 */
	private String tradeDirection = "0";
	/**
	 * 交易方式 1:现货, 2:点价, 3:远期
	 */
	private int tradeMode = 1;
	/**
	 * 有效日期
	 */
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date validTime;
	/**
	 * 有效日期(yyyy-MM-dd HH:mm:ss)
	 * 扩展字段
	 */
	private String validTimestamp;
	/**
	 * 指定对手(多选值)
	 * 报盘扩展字段
	 */
	private String counterpartyIdMulti;
	
	public IronInStockOfferRequest() {
		// 默认:在保税区
		super.setIsBondedArea("1");
		// 默认:固定价模式 
		super.setPriceModel("0");
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

	public String getContractTemplateId() {
		return contractTemplateId;
	}

	public void setContractTemplateId(String contractTemplateId) {
		this.contractTemplateId = contractTemplateId;
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

	public int getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(int tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public String getValidTimestamp() {
		return validTimestamp;
	}

	public void setValidTimestamp(String validTimestamp) {
		this.validTimestamp = validTimestamp;
	}

	public String getCounterpartyIdMulti() {
		return counterpartyIdMulti;
	}

	public void setCounterpartyIdMulti(String counterpartyIdMulti) {
		this.counterpartyIdMulti = counterpartyIdMulti;
	}
}
