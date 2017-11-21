package com.esteel.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 13:24
 */
@Controller
public class IndexController {

    /**
     * web系统首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
