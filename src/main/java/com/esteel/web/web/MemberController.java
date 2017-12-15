package com.esteel.web.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteel.common.controller.WebReturnMessage;
import com.esteel.web.service.MemberClient;
import com.esteel.web.vo.MemberCompanyVo;
import com.esteel.web.vo.MemberUserVo;

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
	
	@Autowired
	MemberClient memberUserClient;
	
	/**
	 * 跳转个人信息页面，传递用户个人信息，如果用户在一个企业则传递所在企业的信息
	 * @param model
	 * @return
	 */
    @RequestMapping("/userInfo")
    public String userInfo(Model model){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
        //判断用户是否在一家企业，如果存在查询所在企业信息
        Integer companyId =  userVo.getCompanyId();
        if(companyId!=null) {
        	MemberCompanyVo company = memberUserClient.findCompany(userVo.getCompanyId());
        	 model.addAttribute("company", company);
        }else {
        	 model.addAttribute("company", new MemberCompanyVo());
        }
        model.addAttribute("userVo", userVo);
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
    /**
     * 根据输入的用户名来确定是否被占用，用户名唯一，初始为手机号
     * @param memberName
     * @return
     */
    @RequestMapping("/membername")
    @ResponseBody
    public WebReturnMessage findMemberName(int userId,String memberName) {
    	MemberUserVo member = memberUserClient.findByMemberName(userId,memberName);
    	Assert.isNull(member, "用户名已被占用");
		return new WebReturnMessage(true);
    }
    /**
     * 用户修改用户名，先根据用户id来获取到用户对象，然后设置修改属性在保存
     * @param userId
     * @param memberName
     * @param gender
     * @return
     */
    @RequestMapping(value="/updMemberName",method=RequestMethod.POST)
    public String updMemberName(Integer userId ,String memberName,Integer gender) {
    	System.out.println(userId+"**********"+memberName+"***********"+gender);
    	MemberUserVo user = memberUserClient.findUser(userId);
    	Assert.notNull(user, "修改失败");
    	user.setMemberName(memberName);
    	user.setGender(gender);
    	MemberUserVo registerUser = memberUserClient.registerUser(user);
    	Assert.notNull(registerUser, "修改失败");
		return "/member/user";
    }
    
    
}
