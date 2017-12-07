package com.esteel.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-23
 * Time: 9:21
 */public class CityVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long cityId;
    private String cityCode;
    private String cityName;
    private String cityNameEn;
    private long provinceId;
    private Timestamp updateTime;
    private String updateUser;

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }
    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityVo that = (CityVo) o;

        if (cityId != that.cityId) return false;
        if (provinceId != that.provinceId) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;
        if (cityNameEn != null ? !cityNameEn.equals(that.cityNameEn) : that.cityNameEn != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (cityId ^ (cityId >>> 32));
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (cityNameEn != null ? cityNameEn.hashCode() : 0);
        result = 31 * result + (int) (provinceId ^ (provinceId >>> 32));
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        return result;
    }
}
