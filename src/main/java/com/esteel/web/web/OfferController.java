package com.esteel.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ESTeel
 * Description: 报盘用controller
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 13:49
 */
@RequestMapping("/offer")
@Controller
public class OfferController {

    @RequestMapping("/add")
    public String addUI(){
        return "/offer/add";
    }

}