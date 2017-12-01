package com.esteel.web.vo.base;

import com.esteel.common.vo.BaseQueryVo;

/**
 * 
 * @ClassName: CommodityQueryVo
 * @Description: 品名搜索VO
 * @author wyf
 * @date 2017年11月30日 上午10:02:05 
 *
 */
public class CommodityQueryVo extends BaseQueryVo {
	private static final long serialVersionUID = 1L;

	/**
	 *  品名ID
	 */
	private long commodityId;
	/**
	 *  品名编码
	 */
	private String commodityCode;
	/**
	 * 品名名称
	 */
	private String commodityName;
	/**
	 * 品名别称
	 */
	private String commodityAlias;
	/**
	 * 品名大类ID
	 */
	private long categoryId;
	/**
	 * 品名种类ID
	 */
	private long speciesId;
	
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
	public String getCommodityAlias() {
		return commodityAlias;
	}
	public void setCommodityAlias(String commodityAlias) {
		this.commodityAlias = commodityAlias;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public long getSpeciesId() {
		return speciesId;
	}
	public void setSpeciesId(long speciesId) {
		this.speciesId = speciesId;
	}
}
