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
	private BigDecimal al2o3;
	/**
	 * 其他化学元素指标 Json数据
	 */
	private String chemicalIndicators;
	/**
	 * 品名ID
	 */
	private long commodityId;
	/**
	 * 品名名称
	 */
	private String commodityName;
	/**
	 * 化学元素指标 Fe
	 */
	private BigDecimal fe;
	/**
	 * 化学元素指标 H2O
	 */
	private BigDecimal h2o;
	/**
	 * 指标类型ID
	 */
	private long indicatorTypeId;
	/**
	 * 指标类型
	 */
	private String indicatorTypeName;
	/**
	 * 是否在保税区 0:否, 1:是
	 */
	private int isBondedArea = 1;
	/**
	 * 化学元素指标 LOI
	 */
	private BigDecimal LOI;
	/**
	 * 起订量
	 */
	private BigDecimal minQuantity;
	/**
	 * 化学元素指标 Mn
	 */
	private BigDecimal mn;
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
	 *  化学元素指标 P
	 */
	private BigDecimal p;
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
	 * 化学元素指标 S
	 */
	private BigDecimal s;
	/**
	 * 化学元素指标 SiO2
	 */
	private BigDecimal sio2;
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
	public BigDecimal getAl2o3() {
		return al2o3;
	}
	public void setAl2o3(BigDecimal al2o3) {
		this.al2o3 = al2o3;
	}
	public String getChemicalIndicators() {
		return chemicalIndicators;
	}
	public void setChemicalIndicators(String chemicalIndicators) {
		this.chemicalIndicators = chemicalIndicators;
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
	public BigDecimal getFe() {
		return fe;
	}
	public void setFe(BigDecimal fe) {
		this.fe = fe;
	}
	public BigDecimal getH2o() {
		return h2o;
	}
	public void setH2o(BigDecimal h2o) {
		this.h2o = h2o;
	}
	public long getIndicatorTypeId() {
		return indicatorTypeId;
	}
	public void setIndicatorTypeId(long indicatorTypeId) {
		this.indicatorTypeId = indicatorTypeId;
	}
	public String getIndicatorTypeName() {
		return indicatorTypeName;
	}
	public void setIndicatorTypeName(String indicatorTypeName) {
		this.indicatorTypeName = indicatorTypeName;
	}
	public int getIsBondedArea() {
		return isBondedArea;
	}
	public void setIsBondedArea(int isBondedArea) {
		this.isBondedArea = isBondedArea;
	}
	public BigDecimal getLOI() {
		return LOI;
	}
	public void setLOI(BigDecimal lOI) {
		LOI = lOI;
	}
	public BigDecimal getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(BigDecimal minQuantity) {
		this.minQuantity = minQuantity;
	}
	public BigDecimal getMn() {
		return mn;
	}
	public void setMn(BigDecimal mn) {
		this.mn = mn;
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
	public long getOfferId() {
		return offerId;
	}
	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}
	public BigDecimal getOfferQuantity() {
		return offerQuantity;
	}
	public void setOfferQuantity(BigDecimal offerQuantity) {
		this.offerQuantity = offerQuantity;
	}
	public BigDecimal getP() {
		return p;
	}
	public void setP(BigDecimal p) {
		this.p = p;
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
	public BigDecimal getS() {
		return s;
	}
	public void setS(BigDecimal s) {
		this.s = s;
	}
	public BigDecimal getSio2() {
		return sio2;
	}
	public void setSio2(BigDecimal sio2) {
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
