package com.esteel.web.vo.offer;

import java.io.Serializable;


/**
 * 
 * @ClassName: OfferAffixVo
 * @Description: 报盘附件DTO
 * @author wyf
 * @date 2017年12月7日 下午4:44:48 
 *
 */
public class OfferAffixVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 报盘附件ID
	 */
	private long affixId;
	/**
	 * ftp返回ID
	 */
	private String affixPath;
	/**
	 * 附件类型 0:报盘备注附件, 1:报盘合同附件
	 */
	private int affixType;
	/**
	 * 报盘编码
	 */
	private String offerCode;
	
	public long getAffixId() {
		return affixId;
	}
	public void setAffixId(long affixId) {
		this.affixId = affixId;
	}
	public String getAffixPath() {
		return affixPath;
	}
	public void setAffixPath(String affixPath) {
		this.affixPath = affixPath;
	}
	public int getAffixType() {
		return affixType;
	}
	public void setAffixType(int affixType) {
		this.affixType = affixType;
	}
	public String getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
}