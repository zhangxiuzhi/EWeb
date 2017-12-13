package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @ClassName: IronFuturesOfferRequest
 * @Description: 铁矿远期现货报盘Request
 * @author wyf
 * @date 2017年12月6日 下午1:28:25 
 *
 */
public class IronFuturesOfferRequest extends OfferIronAttachVo implements Serializable {
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
	/**
	 * 铁矿报盘附表ID 数组
	 * 扩展字段
	 */
	private String[] offerAttachIdArr;
	/**
	 * 化学元素指标 Al2O3 数组
	 * 扩展字段
	 */
	private String[] al2o3Arr;
	/**
	 * 其他化学元素指标 Json数据 数组
	 * 扩展字段
	 */
	private String[] chemicalIndicatorsArr;
	/**
	 * 品名ID 数组
	 * 扩展字段
	 */
	@NotEmpty(message = "请选择品名", groups = {IronFuturesOffer.class})
	private String[] commodityIdArr;
	/**
	 * 品名名称 数组
	 * 扩展字段
	 */
	private String[] commodityNameArr;
	/**
	 * 化学元素指标 Fe 数组
	 * 扩展字段
	 */
	@NotEmpty(message = "请填写Fe指标", groups = {IronFuturesOffer.class})
	private String[] feArr;
	/**
	 * 化学元素指标 H2O 数组
	 * 扩展字段
	 */
	private String[] h2oArr;
	/**
	 * 指标类型ID 数组
	 * 扩展字段
	 */
	private String[] indicatorTypeIdArr;
	/**
	 * 指标类型 数组
	 * 扩展字段
	 */
	private String[] indicatorTypeNameArr;
	/**
	 * 化学元素指标 LOI 数组
	 * 扩展字段
	 */
	private String[] LOIArr;
	/**
	 * 化学元素指标 Mn 数组
	 * 扩展字段
	 */
	private String[] mnArr;
	/**
	 * 溢短装 数组
	 * 扩展字段
	 */
	@NotEmpty(message = "请填写溢短装", groups = {IronFuturesOffer.class})
	private String[] moreOrLessArr;
	/**
	 * 铁矿报盘附表编码 数组
	 * 扩展字段
	 */
	private String[] offerAttachCodeArr;
	/**
	 * 报盘重量 数组
	 * 扩展字段
	 */
	@NotEmpty(message = "请填写数量", groups = {IronFuturesOffer.class})
	private String[] offerQuantityArr;
	/**
	 *  化学元素指标 P 数组
	 *  扩展字段
	 */
	private String[] pArr;
	/**
	 * 价格基数 铁 数组
	 * 扩展字段
	 */
//	private String[] priceBasisFeArr;
	/**
	 * 价格描述 数组
	 * 扩展字段
	 */
//	private String []priceDescriptionArr;
	/**
	 * 价格模式  数组 0:固定价, 1:浮动价
	 * 扩展字段
	 */
//	private String[] priceModelArr = new String[]{"1", "1"};
	/**
	 * 价格数值 数组
	 * 扩展字段
	 */
//	private String[] priceValueArr;
	/**
	 * 化学元素指标 S 数组
	 * 扩展字段
	 */
	private String[] sArr;
	/**
	 * 化学元素指标 SiO2 数组
	 * 扩展字段
	 */
	private String[] sio2Arr;
	/**
	 * 粒度指标 数组
	 * 扩展字段
	 */
	private String[] sizeIndicatorsArr;
	
	/**
	 * 运输信息
	 * 装船期
	 */
	private String transport_load;
	/**
	 * 运输信息
	 * 提单日
	 */
	private String transport_bill;
	/**
	 * 运输信息
	 * 到港月
	 */
	private String transport_arrive;
	/**
	 * 运输信息
	 * ETA新加坡
	 */
	private String transport_etaxjb;
	/**
	 * 运输信息
	 * ETA青岛港
	 */
	private String transport_etaqdg;
	/**
	 * 运输信息
	 * 其他
	 */
	private String transport_remark;
	
	public IronFuturesOfferRequest() {
		// 默认:不在保税区
		super.setIsBondedArea("0");
		// 默认:浮动价模式 
		super.setPriceModel("1");
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
	public String[] getOfferAttachIdArr() {
		return offerAttachIdArr;
	}
	public void setOfferAttachIdArr(String[] offerAttachIdArr) {
		this.offerAttachIdArr = offerAttachIdArr;
	}
	public String[] getAl2o3Arr() {
		return al2o3Arr;
	}
	public void setAl2o3Arr(String[] al2o3Arr) {
		this.al2o3Arr = al2o3Arr;
	}
	public String[] getChemicalIndicatorsArr() {
		return chemicalIndicatorsArr;
	}
	public void setChemicalIndicatorsArr(String[] chemicalIndicatorsArr) {
		this.chemicalIndicatorsArr = chemicalIndicatorsArr;
	}
	public String[] getCommodityIdArr() {
		return commodityIdArr;
	}
	public void setCommodityIdArr(String[] commodityIdArr) {
		this.commodityIdArr = commodityIdArr;
	}
	public String[] getCommodityNameArr() {
		return commodityNameArr;
	}
	public void setCommodityNameArr(String[] commodityNameArr) {
		this.commodityNameArr = commodityNameArr;
	}
	public String[] getFeArr() {
		return feArr;
	}
	public void setFeArr(String[] feArr) {
		this.feArr = feArr;
	}
	public String[] getH2oArr() {
		return h2oArr;
	}
	public void setH2oArr(String[] h2oArr) {
		this.h2oArr = h2oArr;
	}
	public String[] getIndicatorTypeIdArr() {
		return indicatorTypeIdArr;
	}
	public void setIndicatorTypeIdArr(String[] indicatorTypeIdArr) {
		this.indicatorTypeIdArr = indicatorTypeIdArr;
	}
	public String[] getIndicatorTypeNameArr() {
		return indicatorTypeNameArr;
	}
	public void setIndicatorTypeNameArr(String[] indicatorTypeNameArr) {
		this.indicatorTypeNameArr = indicatorTypeNameArr;
	}
	public String[] getLOIArr() {
		return LOIArr;
	}
	public void setLOIArr(String[] lOIArr) {
		LOIArr = lOIArr;
	}
	public String[] getMnArr() {
		return mnArr;
	}
	public void setMnArr(String[] mnArr) {
		this.mnArr = mnArr;
	}
	public String[] getMoreOrLessArr() {
		return moreOrLessArr;
	}
	public void setMoreOrLessArr(String[] moreOrLessArr) {
		this.moreOrLessArr = moreOrLessArr;
	}
	public String[] getOfferAttachCodeArr() {
		return offerAttachCodeArr;
	}
	public void setOfferAttachCodeArr(String[] offerAttachCodeArr) {
		this.offerAttachCodeArr = offerAttachCodeArr;
	}
	public String[] getOfferQuantityArr() {
		return offerQuantityArr;
	}
	public void setOfferQuantityArr(String[] offerQuantityArr) {
		this.offerQuantityArr = offerQuantityArr;
	}
	public String[] getpArr() {
		return pArr;
	}
	public void setpArr(String[] pArr) {
		this.pArr = pArr;
	}
	public String[] getsArr() {
		return sArr;
	}
	public void setsArr(String[] sArr) {
		this.sArr = sArr;
	}
	public String[] getSio2Arr() {
		return sio2Arr;
	}
	public void setSio2Arr(String[] sio2Arr) {
		this.sio2Arr = sio2Arr;
	}
	public String[] getSizeIndicatorsArr() {
		return sizeIndicatorsArr;
	}
	public void setSizeIndicatorsArr(String[] sizeIndicatorsArr) {
		this.sizeIndicatorsArr = sizeIndicatorsArr;
	}

	public String getCounterpartyIdMulti() {
		return counterpartyIdMulti;
	}

	public void setCounterpartyIdMulti(String counterpartyIdMulti) {
		this.counterpartyIdMulti = counterpartyIdMulti;
	}

	public String getTransport_load() {
		return transport_load;
	}

	public void setTransport_load(String transport_load) {
		this.transport_load = transport_load;
	}

	public String getTransport_bill() {
		return transport_bill;
	}

	public void setTransport_bill(String transport_bill) {
		this.transport_bill = transport_bill;
	}

	public String getTransport_arrive() {
		return transport_arrive;
	}

	public void setTransport_arrive(String transport_arrive) {
		this.transport_arrive = transport_arrive;
	}

	public String getTransport_etaxjb() {
		return transport_etaxjb;
	}

	public void setTransport_etaxjb(String transport_etaxjb) {
		this.transport_etaxjb = transport_etaxjb;
	}

	public String getTransport_etaqdg() {
		return transport_etaqdg;
	}

	public void setTransport_etaqdg(String transport_etaqdg) {
		this.transport_etaqdg = transport_etaqdg;
	}

	public String getTransport_remark() {
		return transport_remark;
	}

	public void setTransport_remark(String transport_remark) {
		this.transport_remark = transport_remark;
	}
}
