package com.esteel.web.vo.base;

import java.io.Serializable;
import java.util.Date;

import com.esteel.common.vo.StatusMSGVo;

/**
 * 
 * @ClassName: CommodityVo
 * @Description: 品名DTO
 * @author wyf
 * @date 2017年11月30日 下午2:48:18 
 *
 */
public class CommodityVo extends StatusMSGVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品名ID
	 */
	private long commodityId;
	/**
	 * 品名编码
	 */
	private String commodityCode;
	/**
	 * 品名名称
	 */
	private String commodityName;
	/**
	 * 品名英文名称
	 */
	private String commodityNameEn;
	/**
	 * 产地(国家)ID
	 */
	private long countryId;
	/**
	 * 品名种类ID
	 */
	private long speciesId;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;
	/**
	 * 品名大类ID
	 * 扩展字段
	 */
	private long categoryId;
	/**
	 * 品名别名
	 * 扩展字段
	 */
	private String commodityAlias;

	public long getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(long commodityId) {
		this.commodityId = commodityId;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityNameEn() {
		return commodityNameEn;
	}

	public void setCommodityNameEn(String commodityNameEn) {
		this.commodityNameEn = commodityNameEn;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public long getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(long speciesId) {
		this.speciesId = speciesId;
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

	public String getCommodityAlias() {
		return commodityAlias;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public void setCommodityAlias(String commodityAlias) {
		this.commodityAlias = commodityAlias;
	}
}
