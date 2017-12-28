package com.esteel.web.vo.offer.request;

import com.esteel.web.vo.offer.IronOfferClauseVo;
import com.esteel.web.vo.offer.validator.Array;
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
public class IronOfferClauseRequest extends IronOfferClauseVo {
	private static final long serialVersionUID = 1L;

	/* 交货结算条款模版中参数 */
	/**
	 * 剩余货款在几个工作日内结清
	 */
	@Array(min=1, message = "请填完 结算方式。")
	@PatternHasEmpty(regexp = "^\\d{1,3}$", message = "付款期限： 必须为整数。")
	private String[] clear_within_several_working_daysArr;

	public String[] getClear_within_several_working_daysArr() {
		return clear_within_several_working_daysArr;
	}

	public void setClear_within_several_working_daysArr(String[] clear_within_several_working_daysArr) {
		this.clear_within_several_working_daysArr = clear_within_several_working_daysArr;
	}
}
