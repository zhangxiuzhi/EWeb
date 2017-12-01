package com.esteel.web.service;

import com.esteel.web.vo.ProvinceVo;

import java.util.List;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 15:07
 */
public interface IndexService {

    public List<ProvinceVo> getPort(long portId);

    void tfsTest();
}
