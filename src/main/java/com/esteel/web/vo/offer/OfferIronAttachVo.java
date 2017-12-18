package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.esteel.common.vo.StatusMSGVo;
import com.esteel.web.vo.offer.validator.DigitsHasEmpty;

/**
 * 
 * @ClassName: IronInStockOfferVo
 * @Description: 铁矿港口现货报盘DTO
 * @author wyf
 * @date 2017年12月6日 下午1:28:25 
 *
 */
public class OfferIronAttachVo extends StatusMSGVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 验证组 */
	public interface IronInStockOffer {  
	}  
	  
	public interface IronFuturesOffer {  
	} 
	
	public interface IronPricingOffer {  
	} 
	
	/**
	 * 铁矿报盘附表ID
	 */
	private String offerAttachId;
	/**
	 * 化学元素指标 Al2O3
	 */
	@DigitsHasEmpty(groups = {IronInStockOffer.class}, integer = 2, fraction = 3, message = "Al2O3指标：请填写有效数字。小数位支持1-3位。")
	private String al2o3;
	/**
	 * 基差
	 */
	@NotBlank(groups = {IronPricingOffer.class}, message = "请填写基差")
	@Pattern(regexp = "^[+-]((\\d{0,2}\\.\\d{1,2})|(\\d{1,2}(\\.\\d{0,2})?))$", message = "基差：请填写有效数字。首位必须为正负号，小数位支持1-2位。")
	private String baseDifference;
	/**
	 * 其他化学元素指标 Json数据
	 */
	private String chemicalIndicators;
	/**
	 * 品名ID
	 */
	@NotBlank(groups = {IronInStockOffer.class, IronPricingOffer.class}, message = "请选择品名")
	@Pattern(groups = {IronInStockOffer.class, IronPricingOffer.class}, regexp = "^\\d+$", message = "请选择品名")
	private String commodityId;
	/**
	 * 品名名称
	 */
	private String commodityName;
	/**
	 * 交货期截止
	 */
	@NotNull(groups = {IronPricingOffer.class }, message = "请填写交货期截止时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryTimeEnd;
	/**
	 * 交货期起始
	 */
	@NotNull(groups = {IronPricingOffer.class }, message = "请填写交货期起始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryTimeStart;
	/**
	 * 目的港ID
	 */
	@Pattern(groups = {IronFuturesOffer.class}, regexp = "^\\d+$", message = "请重新选择目的港")
	private String dischargePortId;
	/**
	 * 目的港
	 */
	private String dischargePortName;
	/**
	 * 化学元素指标 Fe
	 */
	@NotBlank(groups = {IronInStockOffer.class}, message = "请填写Fe指标")
	@Digits(groups = {IronInStockOffer.class}, integer = 2, fraction = 3, message = "Fe指标：请填写有效数字。小数位支持1-3位。")
	private String fe;
	/**
	 * 化学元素指标 H2O
	 */
	@DigitsHasEmpty(groups = {IronInStockOffer.class}, integer = 2, fraction = 3, message = "H2O指标：请填写有效数字。小数位支持1-3位。")
	private String h2o;
	/**
	 * 指标类型ID
	 */
	@NotBlank(groups = {IronInStockOffer.class}, message = "请选择指标")
	@Pattern(groups = {IronInStockOffer.class}, regexp = "^\\d+$", message = "请重新选择指标")
	private String indicatorTypeId;
	/**
	 * 指标类型
	 */
	private String indicatorTypeName;
	/**
	 * 连铁合约
	 */
	@NotBlank(groups = {IronPricingOffer.class}, message = "请选择连铁合约")
	private String ironContract;
	/**
	 * 是否在保税区 0:否, 1:是
	 */
	private String isBondedArea;
	/**
	 * 信用证交单期
	 */
	@NotNull(groups = {IronFuturesOffer.class}, message = "请填写信用证交单期")
	@Pattern(groups = {IronFuturesOffer.class}, regexp = "^\\d{1,3}$", message = "信用证交单期：必须为整数。")
	private String lcPresentationPeriod;
	/**
	 * 装货港ID
	 */
	@NotBlank(groups = {IronFuturesOffer.class}, message = "请选择装货港")
	@Pattern(groups = {IronFuturesOffer.class}, regexp = "^\\d+$", message = "请重新选择装货港")
	private String loadingPortId;
	/**
	 * 装货港
	 */
	private String loadingPortName;
	/**
	 * 化学元素指标 LOI
	 */
	@DigitsHasEmpty(groups = {IronInStockOffer.class}, integer = 2, fraction = 3, message = "LOI指标：请填写有效数字。小数位支持1-3位。")
	private String LOI;
	/**
	 * 起订量
	 */
	@Pattern(groups = {IronInStockOffer.class, IronPricingOffer.class}, regexp = "^\\d{1,7}$", message = "起订量：必须为整数。")
	private String minQuantity;
	/**
	 * 化学元素指标 Mn
	 */
	@DigitsHasEmpty(groups = {IronInStockOffer.class}, integer = 2, fraction = 3, message = "Mn指标：请填写有效数字。小数位支持1-3位。")
	private String mn;
	/**
	 * 溢短装
	 */
	@NotBlank(groups = {IronInStockOffer.class, IronPricingOffer.class}, message = "请填写溢短装")
	@Digits(groups = {IronInStockOffer.class, IronPricingOffer.class}, integer = 2, fraction = 1, message = "溢短装：请填写有效数字。小数位支持1位。")
	private String moreOrLess;
	/**
	 * 铁矿报盘附表编码
	 */
	private String offerAttachCode;
	/**
	 * 备注
	 */
	@Length(groups = {IronInStockOffer.class, IronPricingOffer.class, IronFuturesOffer.class}, min=0, max=128, message="商品备注:128字符以内") 
	private String offerAttachRemarks;
	/**
	 * 铁矿报盘ID
	 */
	private String offerId;
	/**
	 * 报盘重量
	 */
	@NotBlank(groups = {IronInStockOffer.class, IronPricingOffer.class}, message = "请填写数量")
	@Pattern(groups = {IronInStockOffer.class, IronPricingOffer.class}, regexp = "^\\d{1,6}00$", message = "数量：必须为100的正整数倍。")
	private String offerQuantity;
	/**
	 * 化学元素指标 P
	 */
	@DigitsHasEmpty(groups = {IronInStockOffer.class}, integer = 2, fraction = 3, message = "P指标：请填写有效数字。小数位支持1-3位。")
	private String p;
	/**
	 * 点价期截止
	 */
	@NotNull(groups = {IronPricingOffer.class }, message = "请填写点价期截止时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pricingPeriodEnd;
	/**
	 * 点价期起始
	 */
	@NotNull(groups = {IronPricingOffer.class }, message = "请填写点价期起始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pricingPeriodStart;
	/**
	 * 港口ID
	 */
	@NotBlank(groups = {IronInStockOffer.class, IronPricingOffer.class}, message = "请选择港口")
	@Digits(groups = {IronInStockOffer.class, IronPricingOffer.class}, integer = 10, fraction = 0, message = "请重新选择港口")
	private String portId;
	/**
	 * 港口
	 */
	private String portName;
	/**
	 * 价格基数 铁
	 */
	private String priceBasisFe;
	/**
	 * 价格描述
	 */
	@Length(groups = {IronFuturesOffer.class}, min=0, max=128, message="价格描述:128字符以内") 
	private String priceDescription;
	/**
	 * 价格模式 0:固定价, 1:浮动价
	 */
	private String priceModel = "1";
	/**
	 * 价格术语
	 */
	@NotBlank(groups = {IronFuturesOffer.class}, message = "请填写价格术语")
	private String priceTerm;
	/**
	 * 价格术语基于港ID
	 */
	@NotBlank(groups = {IronFuturesOffer.class}, message = "请选择价格术语 港口")
	@Digits(groups = {IronFuturesOffer.class}, integer = 10, fraction = 0, message = "请重新选择价格术语 港口")
	private String priceTermPortId;
	/**
	 * 价格术语基于港
	 */
	private String priceTermPortName;
	/**
	 * 价格单位ID
	 */
	private String priceUnitId;
	/**
	 * 价格数值
	 */
	@NotBlank(groups = {IronInStockOffer.class}, message = "请填写价格")
	@Digits(groups = {IronInStockOffer.class}, integer = 4, fraction = 1, message = "价格：请填写有效数字。小数位支持1位。")
	private String priceValue;
	/**
	 * 重量单位ID
	 */
	private String quantityUnitId;
	/**
	 * 化学元素指标 S
	 */
	@DigitsHasEmpty(groups = {IronInStockOffer.class}, integer = 2, fraction = 3, message = "S指标：请填写有效数字。小数位支持1-3位。")
	private String s;
	/**
	 * 化学元素指标 SiO2
	 */
	@DigitsHasEmpty(groups = {IronInStockOffer.class}, integer = 2, fraction = 3, message = "SiO2指标：请填写有效数字。小数位支持1-3位。")
	private String sio2;
	/**
	 * 粒度指标
	 */
	@Length(groups = {IronInStockOffer.class}, min=0, max=32, message="粒度指标:32字符以内")
	private String sizeIndicators;
	/**
	 * 已售重量
	 */
	private String soldQuantity;
	/**
	 * 运输状态 Json数据
	 */
	private String transportDescription;
	
	public String getOfferAttachId() {
		return offerAttachId;
	}
	public void setOfferAttachId(String offerAttachId) {
		this.offerAttachId = offerAttachId;
	}
	public String getAl2o3() {
		return al2o3;
	}
	public void setAl2o3(String al2o3) {
		this.al2o3 = al2o3;
	}
	public String getBaseDifference() {
		return baseDifference;
	}
	public void setBaseDifference(String baseDifference) {
		this.baseDifference = baseDifference;
	}
	public String getChemicalIndicators() {
		return chemicalIndicators;
	}
	public void setChemicalIndicators(String chemicalIndicators) {
		this.chemicalIndicators = chemicalIndicators;
	}
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public Date getDeliveryTimeEnd() {
		return deliveryTimeEnd;
	}
	public void setDeliveryTimeEnd(Date deliveryTimeEnd) {
		this.deliveryTimeEnd = deliveryTimeEnd;
	}
	public Date getDeliveryTimeStart() {
		return deliveryTimeStart;
	}
	public void setDeliveryTimeStart(Date deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}
	public String getDischargePortId() {
		return dischargePortId;
	}
	public void setDischargePortId(String dischargePortId) {
		this.dischargePortId = dischargePortId;
	}
	public String getDischargePortName() {
		return dischargePortName;
	}
	public void setDischargePortName(String dischargePortName) {
		this.dischargePortName = dischargePortName;
	}
	public String getFe() {
		return fe;
	}
	public void setFe(String fe) {
		this.fe = fe;
	}
	public String getH2o() {
		return h2o;
	}
	public void setH2o(String h2o) {
		this.h2o = h2o;
	}
	public String getIndicatorTypeId() {
		return indicatorTypeId;
	}
	public void setIndicatorTypeId(String indicatorTypeId) {
		this.indicatorTypeId = indicatorTypeId;
	}
	public String getIndicatorTypeName() {
		return indicatorTypeName;
	}
	public void setIndicatorTypeName(String indicatorTypeName) {
		this.indicatorTypeName = indicatorTypeName;
	}
	public String getIronContract() {
		return ironContract;
	}
	public void setIronContract(String ironContract) {
		this.ironContract = ironContract;
	}
	public String getIsBondedArea() {
		return isBondedArea;
	}
	public void setIsBondedArea(String isBondedArea) {
		this.isBondedArea = isBondedArea;
	}
	public String getLcPresentationPeriod() {
		return lcPresentationPeriod;
	}
	public void setLcPresentationPeriod(String lcPresentationPeriod) {
		this.lcPresentationPeriod = lcPresentationPeriod;
	}
	public String getLoadingPortId() {
		return loadingPortId;
	}
	public void setLoadingPortId(String loadingPortId) {
		this.loadingPortId = loadingPortId;
	}
	public String getLoadingPortName() {
		return loadingPortName;
	}
	public void setLoadingPortName(String loadingPortName) {
		this.loadingPortName = loadingPortName;
	}
	public String getLOI() {
		return LOI;
	}
	public void setLOI(String lOI) {
		LOI = lOI;
	}
	public String getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(String minQuantity) {
		this.minQuantity = minQuantity;
	}
	public String getMn() {
		return mn;
	}
	public void setMn(String mn) {
		this.mn = mn;
	}
	public String getMoreOrLess() {
		return moreOrLess;
	}
	public void setMoreOrLess(String moreOrLess) {
		this.moreOrLess = moreOrLess;
	}
	public String getOfferAttachCode() {
		return offerAttachCode;
	}
	public void setOfferAttachCode(String offerAttachCode) {
		this.offerAttachCode = offerAttachCode;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getOfferQuantity() {
		return offerQuantity;
	}
	public void setOfferQuantity(String offerQuantity) {
		this.offerQuantity = offerQuantity;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public Date getPricingPeriodEnd() {
		return pricingPeriodEnd;
	}
	public void setPricingPeriodEnd(Date pricingPeriodEnd) {
		this.pricingPeriodEnd = pricingPeriodEnd;
	}
	public Date getPricingPeriodStart() {
		return pricingPeriodStart;
	}
	public void setPricingPeriodStart(Date pricingPeriodStart) {
		this.pricingPeriodStart = pricingPeriodStart;
	}
	public String getPortId() {
		return portId;
	}
	public void setPortId(String portId) {
		this.portId = portId;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public String getPriceDescription() {
		return priceDescription;
	}
	public void setPriceDescription(String priceDescription) {
		this.priceDescription = priceDescription;
	}
	public String getPriceModel() {
		return priceModel;
	}
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	public String getPriceTerm() {
		return priceTerm;
	}
	public void setPriceTerm(String priceTerm) {
		this.priceTerm = priceTerm;
	}
	public String getPriceTermPortId() {
		return priceTermPortId;
	}
	public void setPriceTermPortId(String priceTermPortId) {
		this.priceTermPortId = priceTermPortId;
	}
	public String getPriceTermPortName() {
		return priceTermPortName;
	}
	public void setPriceTermPortName(String priceTermPortName) {
		this.priceTermPortName = priceTermPortName;
	}
	public String getPriceUnitId() {
		return priceUnitId;
	}
	public void setPriceUnitId(String priceUnitId) {
		this.priceUnitId = priceUnitId;
	}
	public String getPriceValue() {
		return priceValue;
	}
	public void setPriceValue(String priceValue) {
		this.priceValue = priceValue;
	}
	public String getQuantityUnitId() {
		return quantityUnitId;
	}
	public void setQuantityUnitId(String quantityUnitId) {
		this.quantityUnitId = quantityUnitId;
	}
	public String getOfferAttachRemarks() {
		return offerAttachRemarks;
	}
	public void setOfferAttachRemarks(String offerAttachRemarks) {
		this.offerAttachRemarks = offerAttachRemarks;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public String getSio2() {
		return sio2;
	}
	public void setSio2(String sio2) {
		this.sio2 = sio2;
	}
	public String getSizeIndicators() {
		return sizeIndicators;
	}
	public void setSizeIndicators(String sizeIndicators) {
		this.sizeIndicators = sizeIndicators;
	}
	public String getSoldQuantity() {
		return soldQuantity;
	}
	public void setSoldQuantity(String soldQuantity) {
		this.soldQuantity = soldQuantity;
	}
	public String getTransportDescription() {
		return transportDescription;
	}
	public void setTransportDescription(String transportDescription) {
		this.transportDescription = transportDescription;
	}
	public String getPriceBasisFe() {
		return priceBasisFe;
	}
	public void setPriceBasisFe(String priceBasisFe) {
		this.priceBasisFe = priceBasisFe;
	}
}
