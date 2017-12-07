package com.esteel.web.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ESTeel
 * Description: 报盘用controller
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 13:49
 */
@RequestMapping("/safe")
@Controller
public class AccountSafeController {

	@RequestMapping("/accountSafe")
    public String accountSafe(Model model){
        return "/safe/accountSafe";
    }

    @RequestMapping("/updatePassword")
    public String updatePassword(Model model){
        return "/safe/updatePassword";
    }
    
    @RequestMapping("/updateMail")
    public String updateMail(Model model){
        return "/safe/updateMail";
    }
    
    @RequestMapping("/updateMobile")
    public String updateMobile(Model model){
        return "/safe/updateMobile";
    }
    
    @RequestMapping("/verifyMail")
    public String verifyMail(Model model){
        return "/safe/verifyMail";
    }
}
