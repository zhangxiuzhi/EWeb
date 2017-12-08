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
@RequestMapping("/member")
@Controller
public class MemberController {

    @RequestMapping("/userInfo")
    public String userInfo(Model model){
        return "/member/userInfo";
    }
    
    @RequestMapping("/headSet")
    public String headSet(Model model){
        return "/member/headSet";
    }
    
    @RequestMapping("/headUpload")
    public String headUpload(Model model){
        return "/member/headUpload";
    }
    
    @RequestMapping("/subAccount")
    public String subAccount(Model model){
        return "/member/subAccount";
    }

}
