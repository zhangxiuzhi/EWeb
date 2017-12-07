package com.esteel.web.vo.config;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: IronAttributeLinkVo
 * @Description: 铁矿品名属性值联动DTO
 * @author wyf
 * @date 2017年12月1日 上午10:36:57 
 *
 */
public class IronAttributeLinkVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 联动ID
	 */
	private long ironAttrLinkId;
	/**
	 * 属性编码
	 */
	private String attributeCode;
	/**
	 * 属性值
	 */
	private String attributeValue;
	/**
	 * 品名编码
	 */
	private String commodityCode;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;

	public long getIronAttrLinkId() {
		return ironAttrLinkId;
	}

	public void setIronAttrLinkId(long ironAttrLinkId) {
		this.ironAttrLinkId = ironAttrLinkId;
	}

	public String getAttributeCode() {
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
