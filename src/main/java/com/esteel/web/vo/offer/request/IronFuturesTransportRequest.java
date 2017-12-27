package com.esteel.web.vo.offer.request;

import com.esteel.web.vo.offer.IronFuturesTransportVo;
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
public class IronFuturesTransportRequest extends IronFuturesTransportVo {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否在保税区 0:否, 1:是
	 */
	private String isBondedArea;
	/**
	 * 保税区港口ID
	 */
	@DigitsHasEmpty(groups = {IronInStockOffer.class, IronPricingOffer.class}, integer = 10, fraction = 0, message = "请重新选择保税区港口")
	private String bondedAreaPortId;
	
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
