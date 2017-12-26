package com.esteel.web.vo.offer.request;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.esteel.web.vo.offer.OfferIronAttachVo.IronInStockOffer;
import com.esteel.web.vo.offer.OfferIronAttachVo.IronPricingOffer;
import com.esteel.web.vo.offer.validator.DigitsHasEmpty;
import com.esteel.web.vo.offer.validator.IronFuturesTransport;

/**
 * 
 * @ClassName: IronFuturesTransportDescription
 * @Description: 铁矿远期现货报盘 运输信息
 * @author wyf
 * @date 2017年12月14日 下午2:03:44 
 *
 */
@IronFuturesTransport()
public class IronFuturesTransportRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 运输信息
	 * 装船期 截止
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date transport_load_end;
	/**
	 * 运输信息
	 * 装船期 开始
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date transport_load_start;
	/**
	 * 运输信息
	 * 提单日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String transport_bill;
	/**
	 * 运输信息
	 * 到港月
	 */
	@DateTimeFormat(pattern = "yyyy-MM")
	private String transport_arrive_month;
	/**
	 * 运输信息
	 * 0:上半月(First half) 1:下半月(Second half)
	 */
	private String transport_half_month;
	/**
	 * 运输信息
	 * ETA新加坡
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String transport_etaxjb;
	/**
	 * 运输信息
	 * ETA青岛港
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String transport_etaqdg;
	/**
	 * 运输信息
	 * 其他
	 */
	@Length(min=0, max=1, message="运输备注:128字符以内") 
	/**
	 * 是否在保税区 0:否, 1:是
	 */
	private String isBondedArea;
	/**
	 * 保税区港口ID
	 */
	@DigitsHasEmpty(groups = {IronInStockOffer.class, IronPricingOffer.class}, integer = 10, fraction = 0, message = "请重新选择保税区港口")
	private String bondedAreaPortId;
	
	private String transport_remark;
	
	public Date getTransport_load_end() {
		return transport_load_end;
	}
	public void setTransport_load_end(Date transport_load_end) {
		this.transport_load_end = transport_load_end;
	}
	public Date getTransport_load_start() {
		return transport_load_start;
	}
	public void setTransport_load_start(Date transport_load_start) {
		this.transport_load_start = transport_load_start;
	}
	public String getTransport_bill() {
		return transport_bill;
	}
	public void setTransport_bill(String transport_bill) {
		this.transport_bill = transport_bill;
	}
	public String getTransport_arrive_month() {
		return transport_arrive_month;
	}
	public void setTransport_arrive_month(String transport_arrive_month) {
		this.transport_arrive_month = transport_arrive_month;
	}
	public String getTransport_half_month() {
		return transport_half_month;
	}
	public void setTransport_half_month(String transport_half_month) {
		this.transport_half_month = transport_half_month;
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
	public String getIsBondedArea() {
		return isBondedArea;
	}
	public void setIsBondedArea(String isBondedArea) {
		this.isBondedArea = isBondedArea;
	}
	public String getBondedAreaPortId() {
		return bondedAreaPortId;
	}
	public void setBondedAreaPortId(String bondedAreaPortId) {
		this.bondedAreaPortId = bondedAreaPortId;
	}
}
