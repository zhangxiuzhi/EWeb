package com.esteel.web.vo;

import java.io.Serializable;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-23
 * Time: 9:46
 */
public class ProvinceVo implements Serializable {

    private long provinceId;
    private String provinceCode;
    private String provinceName;

    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProvinceVo that = (ProvinceVo) o;

        if (provinceId != that.provinceId) return false;
        if (provinceCode != null ? !provinceCode.equals(that.provinceCode) : that.provinceCode != null) return false;
        return provinceName != null ? provinceName.equals(that.provinceName) : that.provinceName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (provinceId ^ (provinceId >>> 32));
        result = 31 * result + (provinceCode != null ? provinceCode.hashCode() : 0);
        result = 31 * result + (provinceName != null ? provinceName.hashCode() : 0);
        return result;
    }
}
