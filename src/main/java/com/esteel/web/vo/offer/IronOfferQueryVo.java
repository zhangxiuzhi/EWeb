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
	 * 创建人
	 */
	private String createUser;
	/**
	 * 铁矿报盘ID
	 */
	private String offerId;
	/**
	 * 铁矿报盘编码
	 */
	private String offerCode;
	/**
	 * 铁矿报盘状态 -1:全部, 0:草稿, 100:在售, 200:成交, 300:下架, 999:作废
	 * 扩展状态
	 */
	private String offerStatus = "-1";
	/**
	 * 交易方式  -1:全部, 1:现货, 2:点价, 3:远期
	 */
	private String tradeMode = "-1";
	/**
	 * 品名ID
	 */
	private String commodityId;
	/**
	 * 港口
	 */
	private String portId;
	/**
	 * 铁品位期间 开始
	 */
	private String feMin;
	/**
	 * 铁品位期间 截止
	 */
	private String feMax;
	/**
	 * 排序字段
	 */
	private String orderField = "createtime";
	/**
	 * 排序规则
	 */
	private String orderRule = "desc";
	
	public String getCreateUser() {
		if (createUser == null || createUser.trim().equals("")) {
			return "-1";
		}
		
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
	public String getOfferStatus() {
		if (offerStatus == null || offerStatus.trim().equals("")) {
			return "-1";
		}
		
		return offerStatus;
	}
	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}
	public String getTradeMode() {
		if (tradeMode == null || tradeMode.trim().equals("")) {
			return "-1";
		}
		
		return tradeMode;
	}
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}
	public String getCommodityId() {
		if (commodityId == null || commodityId.trim().equals("")) {
			return "-1";
		}
		
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public String getPortId() {
		if (portId == null || portId.trim().equals("")) {
			return "-1";
		}
		
		return portId;
	}
	public void setPortId(String portId) {
		this.portId = portId;
	}
	public String getFeMin() {
		return feMin;
	}
	public void setFeMin(String feMin) {
		this.feMin = feMin;
	}
	public String getFeMax() {
		return feMax;
	}
	public void setFeMax(String feMax) {
		this.feMax = feMax;
	}
	public String getOrderField() {
		if (orderField == null || orderField.trim().equals("")) {
			return "createtime";
		}
		
		return orderField;
	}
	public void setOrderField(String orderField) {
		
		this.orderField = orderField;
	}
	public String getOrderRule() {
		if (orderRule == null || orderRule.trim().equals("")) {
			return "desc";
		}
		
		return orderRule;
	}
	public void setOrderRule(String orderRule) {
		this.orderRule = orderRule;
	}
}
