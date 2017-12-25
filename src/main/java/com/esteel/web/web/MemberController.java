package com.esteel.web.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteel.common.controller.WebReturnMessage;
import com.esteel.web.service.MemberClient;
import com.esteel.web.vo.MemberCompanyVo;
import com.esteel.web.vo.MemberUserVo;

/**
 * ESTeel Description: 登录用户个人信息模块
 * 13:49
 */
@RequestMapping("/member")
@Controller
public class MemberController {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	MemberClient memberUserClient;

	/**
	 * 跳转个人信息页面，传递用户个人信息，如果用户在一个企业则传递所在企业的信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userInfo")
	public String userInfo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
		// 判断用户是否在一家企业，如果存在查询所在企业信息
		Integer companyId = userVo.getCompanyId();
		if (companyId != null) {
			MemberCompanyVo company = memberUserClient.findCompany(userVo.getCompanyId());
			model.addAttribute("company", company);
		} else {
			MemberCompanyVo company = new MemberCompanyVo();
			company.setApprovalStatus(1);
			model.addAttribute("company", company);
		}
		model.addAttribute("userVo", userVo);
		return "/member/userInfo";
	}
	/**
	 * 跳转头像设置页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/headSet")
	public String headSet(Model model) {
		return "/member/headSet";
	}

	@RequestMapping("/headUpload")
	public String headUpload(Model model) {
		return "/member/headUpload";
	}

	@RequestMapping("/subAccount")
	public String subAccount(Model model) {
		return "/member/subAccount";
	}

	/**
	 * 根据输入的用户名来确定是否被占用，用户名唯一，初始为手机号
	 * 
	 * @param memberName
	 * @return
	 */
	@RequestMapping(value = "/membername", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage findMemberName(int userId, String memberName) {
		logger.info("修改用户名，参数{userId,memberName}"+userId+"-"+memberName);
		MemberUserVo member = memberUserClient.findByMemberName(userId, memberName);
		Assert.isNull(member, "用户名已被占用");
		return new WebReturnMessage(true);
	}

	/**
	 * 用户修改用户名，先根据用户id来获取到用户对象，然后设置修改属性在保存
	 * 
	 * @param userId
	 * @param memberName
	 * @param gender
	 * @return
	 */
	@RequestMapping(value = "/updMemberName", method = RequestMethod.POST)
	public String updMemberName(Integer userId, String memberName, Integer gender) {
		logger.info("修改用户信息，参数{userId,memberName,gender}"+userId+memberName+gender);
		// 先查出用户的信息
		MemberUserVo user = memberUserClient.findUser(userId);
		Assert.notNull(user, "修改失败");
		user.setMemberName(memberName);
		user.setGender(gender);
		// 保存修改后的用户信息
		MemberUserVo registerUser = memberUserClient.registerUser(user);
		Assert.notNull(registerUser, "修改失败");
		return "/member/user";
	}

	/**
	 * 申请成为企业子账号
	 * 
	 * @param companyName
	 * @param username
	 * @return
	 */
	@RequestMapping("/apply")
	@ResponseBody
	public WebReturnMessage applyTrader(String companyName, String username) {
		// 先查询企业信息
		MemberCompanyVo company = memberUserClient.findByCompanyName(companyName);
		Assert.notNull(company, "企业不存在");
		// 获取到登录用户的个人信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
		Assert.notNull(userVo, "用户登录已失效");
		// 设置用户的名字
		userVo.setUserName(username);
		long companyId = company.getCompanyId();
		// 设置申请企业的id
		userVo.setCompanyId((int) companyId);
		// 设置申请的状态 状态 0正常(默认) 1认证 99销户 2审核
		userVo.setUserStatus(2);
		// 保存
		MemberUserVo registerUser = memberUserClient.registerUser(userVo);
		Assert.notNull(registerUser, "提交审核失败，请重试");
		return new WebReturnMessage(true, "已提交审核");
	}

}
