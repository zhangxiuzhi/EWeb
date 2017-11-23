package com.esteel.web.service;

import com.esteel.web.vo.ProvinceVo;
import org.springframework.data.domain.Page;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 15:07
 */
public interface IndexService {

    public Page<ProvinceVo> getPort(long portId);

}
