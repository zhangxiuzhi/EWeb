package com.esteel.web.vo.config;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * @ClassName: AttributeValueOptionVo
 * @Description: 属性值选项DTO
 * @author wyf
 * @date 2017年12月4日 下午3:29:01 
 *
 */
public class AttributeValueOptionVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 属性值选项ID
	 */
	private long optionId;
	/**
	 * 属性值选项编码
	 */
	private String attributeCode;
	/**
	 * 属性编码
	 */
	private String optionCode;
	/**
	 * 属性值
	 */
	private String optionValue;
	/**
	 * 属性值英文
	 */
	private String optionValueEn;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;
	
	public long getOptionId() {
		return optionId;
	}
	public void setOptionId(long optionId) {
		this.optionId = optionId;
	}
	public String getAttributeCode() {
		return attributeCode;
	}
	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}
	public String getOptionCode() {
		return optionCode;
	}
	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public String getOptionValueEn() {
		return optionValueEn;
	}
	public void setOptionValueEn(String optionValueEn) {
		this.optionValueEn = optionValueEn;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
