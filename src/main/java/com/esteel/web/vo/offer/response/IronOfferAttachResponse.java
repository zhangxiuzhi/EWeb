package com.esteel.web.vo.offer.response;

import com.esteel.web.vo.offer.OfferIronAttachVo;

public class IronOfferAttachResponse extends OfferIronAttachVo {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否在保税区 文本
	 * 扩展字段
	 */
	private String isBondedAreaText;
	/**
	 * 价格模式  文本
	 * 扩展字段
	 */
	private String priceModelText;
	/**
	 * 价格单位
	 * 扩展字段
	 */
	private String priceUnit;
	/**
	 * 重量单位
	 * 扩展字段
	 */
	private String quantityUnit;
	public String getIsBondedAreaText() {
		return isBondedAreaText;
	}
	public void setIsBondedAreaText(String isBondedAreaText) {
		this.isBondedAreaText = isBondedAreaText;
	}
	public String getPriceModelText() {
		return priceModelText;
	}
	public void setPriceModelText(String priceModelText) {
		this.priceModelText = priceModelText;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public String getQuantityUnit() {
		return quantityUnit;
	}
	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
}
