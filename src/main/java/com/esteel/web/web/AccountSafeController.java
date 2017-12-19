package com.esteel.web.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esteel.web.service.MemberClient;
import com.esteel.web.vo.MemberUserVo;

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
	
	
	@Autowired
	MemberClient memberUserClient;
	/**
	 * 跳转用户账号安全主页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/accountSafe")
    public String accountSafe(Model model){
		//获取到登录用户信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
        Assert.notNull(userVo, "无法获取用户信息");
        model.addAttribute("userVo", userVo);
        return "/safe/accountSafe";
    }
	/**
	 * 修改密碼
	 * @param model
	 * @return
	 */
    @RequestMapping("/updatePassword")
    public String updatePassword(Model model){
    	//获取到登录用户信息
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
        Assert.notNull(userVo, "无法获取用户信息");
        model.addAttribute("userVo", userVo);
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
