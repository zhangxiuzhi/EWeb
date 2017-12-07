package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @ClassName: IronInStockOfferVo
 * @Description: 铁矿港口现货报盘DTO
 * @author wyf
 * @date 2017年12月6日 下午1:28:25 
 *
 */
public class IronInStockOfferVo extends IronOfferBaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 铁矿报盘附表ID
	 */
	private long offerAttachId;
	/**
	 * 化学元素指标 Al2O3
	 */
	private String al2o3;
	/**
	 * 其他化学元素指标 Json数据
	 */
	private String chemicalIndicators;
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
	 * 化学元素指标 H2O
	 */
	private String h2o;
	/**
	 * 指标类型ID
	 */
	private String indicatorTypeId;
	/**
	 * 指标类型
	 */
	private String indicatorTypeName;
	/**
	 * 是否在保税区 0:否, 1:是
	 */
	private String isBondedArea = "1";
	/**
	 * 化学元素指标 LOI
	 */
	private String LOI;
	/**
	 * 起订量
	 */
	private String minQuantity;
	/**
	 * 化学元素指标 Mn
	 */
	private String mn;
	/**
	 * 溢短装
	 */
	private String moreOrLess;
	/**
	 * 铁矿报盘附表编码
	 */
	private String offerAttachCode;
	/**
	 * 报盘重量
	 */
	private String offerQuantity;
	/**
	 *  化学元素指标 P
	 */
	private String p;
	/**
	 * 港口ID
	 */
	private String portId;
	/**
	 * 港口
	 */
	private String portName;
	/**
	 * 价格模式 0:固定价, 1:浮动价
	 */
	private String priceModel = "0";
	/**
	 * 价格单位ID
	 */
	private String priceUnitId;
	/**
	 * 价格数值
	 */
	private String priceValue;
	/**
	 * 重量单位ID
	 */
	private String quantityUnitId;
	/**
	 * 备注
	 */
	private String offerAttachRemarks;
	/**
	 * 化学元素指标 S
	 */
	private String s;
	/**
	 * 化学元素指标 SiO2
	 */
	private String sio2;
	/**
	 * 粒度指标
	 */
	private String sizeIndicators;
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
	public String getAl2o3() {
		return al2o3;
	}
	public void setAl2o3(String al2o3) {
		this.al2o3 = al2o3;
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
	public String getIsBondedArea() {
		return isBondedArea;
	}
	public void setIsBondedArea(String isBondedArea) {
		this.isBondedArea = isBondedArea;
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
	public String getPriceModel() {
		return priceModel;
	}
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
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
	public BigDecimal getSoldQuantity() {
		return soldQuantity;
	}
	public void setSoldQuantity(BigDecimal soldQuantity) {
		this.soldQuantity = soldQuantity;
	}
}
