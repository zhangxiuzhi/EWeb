package com.esteel.web.vo;

import java.sql.Timestamp;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-23
 * Time: 9:21
 */
public class DistrictVo {
    private long districtId;
    private String districtCode;
    private String districtName;
    private String districtNameEn;
    private long cityId;
    private Timestamp updateTime;
    private String updateUser;

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    public String getDistrictNameEn() {
        return districtNameEn;
    }

    public void setDistrictNameEn(String districtNameEn) {
        this.districtNameEn = districtNameEn;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
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

        DistrictVo that = (DistrictVo) o;

        if (districtId != that.districtId) return false;
        if (cityId != that.cityId) return false;
        if (districtCode != null ? !districtCode.equals(that.districtCode) : that.districtCode != null) return false;
        if (districtName != null ? !districtName.equals(that.districtName) : that.districtName != null) return false;
        if (districtNameEn != null ? !districtNameEn.equals(that.districtNameEn) : that.districtNameEn != null)
            return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (districtId ^ (districtId >>> 32));
        result = 31 * result + (districtCode != null ? districtCode.hashCode() : 0);
        result = 31 * result + (districtName != null ? districtName.hashCode() : 0);
        result = 31 * result + (districtNameEn != null ? districtNameEn.hashCode() : 0);
        result = 31 * result + (int) (cityId ^ (cityId >>> 32));
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        return result;
    }
}
