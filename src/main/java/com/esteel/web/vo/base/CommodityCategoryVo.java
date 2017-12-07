package com.esteel.web.vo.base;

import java.io.Serializable;
import java.util.Date;

import com.esteel.common.vo.StatusMSGVo;

/**
 * 
 * @ClassName: CommodityCategoryVo
 * @Description: 品名大类DTO
 * @author wyf
 * @date 2017年12月5日 下午1:53:25 
 *
 */
public class CommodityCategoryVo extends StatusMSGVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品名大类ID
	 */
	private long categoryId;
	/**
	 * 品名大类编码
	 */
	private String categoryCode;
	/**
	 * 品名大类名称
	 */
	private String categoryName;
	/**
	 * 品名大类英文名称
	 */
	private String categoryNameEn;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;
	
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryNameEn() {
		return categoryNameEn;
	}
	public void setCategoryNameEn(String categoryNameEn) {
		this.categoryNameEn = categoryNameEn;
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
