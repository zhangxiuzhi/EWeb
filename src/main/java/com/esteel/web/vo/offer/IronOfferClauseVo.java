package com.esteel.web.vo.offer;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.esteel.web.vo.offer.validator.DigitsHasEmpty;
import com.esteel.web.vo.offer.validator.IronOfferClause;
import com.esteel.web.vo.offer.validator.PatternHasEmpty;

/**
 * 
 * @ClassName: IronOfferClauseVo
 * @Description: 铁矿报盘条约DTO
 * @author wyf
 * @date 2017年12月4日 下午3:46:41 
 *
 */
@IronOfferClause()
public class IronOfferClauseVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 交货结算条款模版中参数 */
	/**
	 * 结算方式 数据范围:0, 1, 2, ...
	 */
	private String settlement_method;
	/**
	 * 签订合同后几个工作日 数据范围:0, 1, 2, ...
	 */
	@PatternHasEmpty(regexp = "^\\d{1,3}$", message = "工作日：必须为整数。")
	private String after_sign_several_working_days;
	/**
	 * 合同款的百分比 数据范围:0 - 100
	 */
	@DigitsHasEmpty(integer = 2, fraction = 2, message = "合同款的百分比：请填写有效数字。小数位支持1-2位。")
	private String contract_funds_percentage;
	/**
	 * 剩余货款在几个工作日内结清
	 */
	@PatternHasEmpty(regexp = "^\\d{1,3}$", message = "付款期限： 必须为整数。")
	private String clear_within_several_working_days;
	/**
	 * 付全款后几个工作日
	 */
	@NotBlank(message = "请填完 交收期。")
	@Pattern(regexp = "^\\d{1,3}$", message = "工作日：必须为整数。")
	private String after_pay_off_several_working_days;
	/**
	 * 交货港口 数据范围:port
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 交货方式。")
	private String delivery_Method_port;
	/**
	 * 计价方式 数据范围:option
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 交货方式。")
	private String pricing_method;
	/**
	 * 交货数量标准港口 数据范围:port
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 交货数量标准。")
	private String delivery_quantity_port;
	/**
	 * 计量方式 数据范围:option
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 交货数量标准。")
	private String measure_method;
	/**
	 * 交易者类型 0:卖方, 1:买方
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 货物规格标准。")
	private String trader_type;
	/**
	 * 运输费承担方
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 费用条款。")
	private String transport_costs_bearer;
	/**
	 * 代理费承担方
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 费用条款。")
	private String agency_fee_bearer;
	/**
	 * 内贸港口建设费承担方
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 费用条款。")
	private String port_construction_fee_bearer;
	/**
	 * 二程船运费承担方
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 费用条款。")
	private String second_vessel_fee_bearer;
	/**
	 * 过磅或水尺费承担方
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 费用条款。")
	private String weighing_fee_bearer;
	/**
	 * 免堆期 数据范围:0, 1, 2, ...
	 */
	@NotBlank(message = "请填完 费用条款。")
	@Pattern(regexp = "^\\d{1,3}$", message = "免堆期：必须为整数。")
	private String free_storage_several_days;
	/**
	 * 超期产生的堆存费承担方
	 */
	@Digits(integer = 10, fraction = 0, message = "请填完 费用条款。")
	private String overdue_storage_fee_bearer;
	/**
	 * 发票条款
	 */
	@Length(min=0, max=200, message="发票条款：200字符以内。") 
	private String invoice_terms;
	
	public String getSettlement_method() {
		return settlement_method;
	}
	public void setSettlement_method(String settlement_method) {
		this.settlement_method = settlement_method;
	}
	public String getAfter_sign_several_working_days() {
		return after_sign_several_working_days;
	}
	public void setAfter_sign_several_working_days(String after_sign_several_working_days) {
		this.after_sign_several_working_days = after_sign_several_working_days;
	}
	public String getContract_funds_percentage() {
		return contract_funds_percentage;
	}
	public void setContract_funds_percentage(String contract_funds_percentage) {
		this.contract_funds_percentage = contract_funds_percentage;
	}
	public String getClear_within_several_working_days() {
		return clear_within_several_working_days;
	}
	public void setClear_within_several_working_days(String clear_within_several_working_days) {
		this.clear_within_several_working_days = clear_within_several_working_days;
	}
	public String getAfter_pay_off_several_working_days() {
		return after_pay_off_several_working_days;
	}
	public void setAfter_pay_off_several_working_days(String after_pay_off_several_working_days) {
		this.after_pay_off_several_working_days = after_pay_off_several_working_days;
	}
	public String getDelivery_Method_port() {
		return delivery_Method_port;
	}
	public void setDelivery_Method_port(String delivery_Method_port) {
		this.delivery_Method_port = delivery_Method_port;
	}
	public String getPricing_method() {
		return pricing_method;
	}
	public void setPricing_method(String pricing_method) {
		this.pricing_method = pricing_method;
	}
	public String getDelivery_quantity_port() {
		return delivery_quantity_port;
	}
	public void setDelivery_quantity_port(String delivery_quantity_port) {
		this.delivery_quantity_port = delivery_quantity_port;
	}
	public String getMeasure_method() {
		return measure_method;
	}
	public void setMeasure_method(String measure_method) {
		this.measure_method = measure_method;
	}
	public String getTrader_type() {
		return trader_type;
	}
	public void setTrader_type(String trader_type) {
		this.trader_type = trader_type;
	}
	public String getTransport_costs_bearer() {
		return transport_costs_bearer;
	}
	public void setTransport_costs_bearer(String transport_costs_bearer) {
		this.transport_costs_bearer = transport_costs_bearer;
	}
	public String getAgency_fee_bearer() {
		return agency_fee_bearer;
	}
	public void setAgency_fee_bearer(String agency_fee_bearer) {
		this.agency_fee_bearer = agency_fee_bearer;
	}
	public String getPort_construction_fee_bearer() {
		return port_construction_fee_bearer;
	}
	public void setPort_construction_fee_bearer(String port_construction_fee_bearer) {
		this.port_construction_fee_bearer = port_construction_fee_bearer;
	}
	public String getSecond_vessel_fee_bearer() {
		return second_vessel_fee_bearer;
	}
	public void setSecond_vessel_fee_bearer(String second_vessel_fee_bearer) {
		this.second_vessel_fee_bearer = second_vessel_fee_bearer;
	}
	public String getWeighing_fee_bearer() {
		return weighing_fee_bearer;
	}
	public void setWeighing_fee_bearer(String weighing_fee_bearer) {
		this.weighing_fee_bearer = weighing_fee_bearer;
	}
	public String getFree_storage_several_days() {
		return free_storage_several_days;
	}
	public void setFree_storage_several_days(String free_storage_several_days) {
		this.free_storage_several_days = free_storage_several_days;
	}
	public String getOverdue_storage_fee_bearer() {
		return overdue_storage_fee_bearer;
	}
	public void setOverdue_storage_fee_bearer(String overdue_storage_fee_bearer) {
		this.overdue_storage_fee_bearer = overdue_storage_fee_bearer;
	}
	public String getInvoice_terms() {
		return invoice_terms;
	}
	public void setInvoice_terms(String invoice_terms) {
		this.invoice_terms = invoice_terms;
	}
}
