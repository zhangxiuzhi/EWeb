package com.esteel.web.vo.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: PortVo
 * @Description: 港口DTO
 * @author wyf
 * @date 2017年11月30日 下午2:48:18 
 *
 */
public class PortVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 港口ID
	 */
	private long portId;
	/**
	 * 港口编码
	 */
	private String portCode;
	/**
	 * 港口名称
	 */
	private String portName;
	/**
	 * 港口英文名称
	 */
	private String portNameEn;
	/**
	 * 港口归类(位权) 1：港口/目的港; 2：保税区港口; 4：装货港
	 */
	private int portClassify;
	/**
	 * 是否保税区 0:否, 1:是
	 */
	private int isBondedArea;
	/**
	 * 是否海港 0:否, 1:是
	 */
	private int isSeaport;
	/**
	 * 国家ID
	 */
	private Long countryId;
	/**
	 * 省ID
	 */
	private Long provinceId;
	/**
	 * 市ID
	 */
	private Long cityId;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUser;

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getPortNameEn() {
		return portNameEn;
	}

	public void setPortNameEn(String portNameEn) {
		this.portNameEn = portNameEn;
	}

	public int getPortClassify() {
		return portClassify;
	}

	public void setPortClassify(int portClassify) {
		this.portClassify = portClassify;
	}

	public int getIsBondedArea() {
		return isBondedArea;
	}

	public void setIsBondedArea(int isBondedArea) {
		this.isBondedArea = isBondedArea;
	}

	public int getIsSeaport() {
		return isSeaport;
	}

	public void setIsSeaport(int isSeaport) {
		this.isSeaport = isSeaport;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
