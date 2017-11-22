package com.esteel.web.web;

import com.esteel.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    IndexService indexService;

    /**
     * web系统首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index(){
        String port = indexService.getPort(1);
//        System.out.println("0000000000000000000");
//        System.out.println(port);
//        System.out.println("0000000000000000000");
        return "index";
    }

}
