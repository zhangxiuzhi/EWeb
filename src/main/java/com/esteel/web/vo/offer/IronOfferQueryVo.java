package com.esteel.web.vo.offer;

import java.math.BigDecimal;

import com.esteel.common.vo.BaseQueryVo;

/**
 * 
 * @ClassName: IronOfferQueryVo
 * @Description: 铁矿报盘搜索VO
 * @author wyf
 * @date 2017年12月19日 下午4:35:01 
 *
 */
public class IronOfferQueryVo extends BaseQueryVo {
	private static final long serialVersionUID = 1L;

	/**
	 * 铁矿报盘ID
	 */
	private long offerId;
	/**
	 * 铁矿报盘编码
	 */
	private String offerCode;
	/**
	 * 铁矿报盘状态 -1:全部, 0:草稿, 100:在售, 200:成交, 300:下架, 999:作废
	 * 扩展状态
	 */
	private int offerStatus = -1;
	/**
	 * 交易方式  -1:全部, 1:现货, 2:点价, 3:远期
	 */
	private int tradeMode = -1;
	/**
	 * 品名ID
	 */
	private long commodityId;
	/**
	 * 港口
	 */
	private long portId;
	/**
	 * 铁品位期间 开始
	 */
	private BigDecimal feMin;
	/**
	 * 铁品位期间 截止
	 */
	private BigDecimal feMax;
	/**
	 * 排序字段
	 */
	private String orderField = "createtime";
	/**
	 * 排序规则
	 */
	private String orderRule = "desc";
	
	public long getOfferId() {
		return offerId;
	}
	public void setOfferId(long offerId) {
		this.offerId = offerId;
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
	public int getTradeMode() {
		return tradeMode;
	}
	public void setTradeMode(int tradeMode) {
		this.tradeMode = tradeMode;
	}
	public long getPortId() {
		return portId;
	}
	public void setPortId(long portId) {
		this.portId = portId;
	}
	public BigDecimal getFeMin() {
		return feMin;
	}
	public void setFeMin(BigDecimal feMin) {
		this.feMin = feMin;
	}
	public BigDecimal getFeMax() {
		return feMax;
	}
	public void setFeMax(BigDecimal feMax) {
		this.feMax = feMax;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderRule() {
		return orderRule;
	}
	public void setOrderRule(String orderRule) {
		this.orderRule = orderRule;
	}
	public Long getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}
}
