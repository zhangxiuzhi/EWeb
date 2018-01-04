package com.esteel.web.web;

import java.util.List;

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
import com.esteel.web.vo.WebUtils;

/**
 * 用户个人信息模块
 * 
 * @author chenshouye ESTeel Description:
 * 
 */
@RequestMapping("/member")
@Controller
public class MemberUserInfoController {

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
		logger.info("userInfo:个人信息页面");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		// 判断用户是否在一家企业，如果存在查询所在企业信息
		Integer companyId = userVo.getCompanyId();
		if (companyId != null) {
			MemberCompanyVo company = memberUserClient.findCompany((long) userVo.getCompanyId());
			logger.debug("userInfo:个人信息页面,获取企业信息MemberCompanyVo："+company);
			//用户申请成为企业子账号，未审核状态下不显示企业信息
			if(userVo.getUserGrade()==0) {
				MemberCompanyVo companyhide = new MemberCompanyVo();
				company.setApprovalStatus(1);
				model.addAttribute("company", companyhide);
			}else {
				model.addAttribute("company", company);
			}
			//如果是企业子账号，则获取管理员姓名
			if(userVo.getUserGrade()==2) {
				List<MemberUserVo> findmembers = memberUserClient.findmembers(companyId);
				//获取企业管理员的名字
				for (MemberUserVo memberUserVo : findmembers) {
					if(memberUserVo.getUserGrade()==1) {
						model.addAttribute("admin", memberUserVo.getUserName());
						break;
					}
				}
			}
		} else {
			MemberCompanyVo company = new MemberCompanyVo();
			company.setApprovalStatus(1);
			model.addAttribute("company", company);
		}
		//设置手机，密码暗文
		userVo.setMobile(WebUtils.MobileHide(userVo.getMobile()));
		userVo.setEmail(WebUtils.EmailHide(userVo.getEmail()));
		model.addAttribute("userVo", userVo);
		return "/member/userInfo";
	}

	/**
	 * 跳转头像设置页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/headSet")
	public String headSet(Model model) {
		return "/member/headSet";
	}
	/**
	 * 跳转企业logo设置
	 * @param model
	 * @return
	 */
	@RequestMapping("/logoSet")
	public String headUpload(Model model) {
		return "/member/logoSet";
	}

	/**
	 * 跳转企业子账号管理页面
	 * 
	 * @param model
	 * @return
	 */
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
		logger.info("修改用户名，参数{userId,memberName}" + userId + "-" + memberName);
		MemberUserVo member = memberUserClient.findByMemberNameId(userId, memberName);
		logger.debug("findMemberName:保存用户信息，member：" + member);
		//Assert.isNull(member, "用户名已被占用");
		if(member==null) {
			return new WebReturnMessage(true,"0");
		}else {
			return new WebReturnMessage(true,"1");
		}
	
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
	@ResponseBody
	public WebReturnMessage updMemberName(Long userId, String memberName, Integer gender) {
		logger.info("修改用户信息，参数{userId,memberName,gender}" + userId + memberName + gender);
		// 先查出用户的信息
		MemberUserVo user = memberUserClient.findUser(userId);
		Assert.notNull(user, "修改失败");
		user.setMemberName(memberName);
		user.setGender(gender);
		// 保存修改后的用户信息
		MemberUserVo registerUser = memberUserClient.registerUser(user);
		logger.debug("applyTrader:保存用户信息，registerUser：" + registerUser);
		Assert.notNull(registerUser, "修改失败");
		return new WebReturnMessage(true,"1");
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
		logger.info("applyTrader,申请成为企业子账号,参数｛companyName，username｝" + companyName + username);
		// 先查询企业信息
		MemberCompanyVo company = memberUserClient.findByCompanyName(companyName);
		logger.debug("applyTrader:申请成为企业子账号，company：" + company );
		Assert.notNull(company, "企业不存在");
		// 获取到登录用户的个人信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("applyTrader:获取登录用户信息，company：" + authentication );
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		logger.debug("applyTrader:获取登录用户信息，userVo：" + userVo );
		Assert.notNull(userVo, "用户登录已失效");
		// 设置用户的名字
		userVo.setUserName(username);
		long companyId = company.getCompanyId();
		// 设置申请企业的id
		userVo.setCompanyId((int) companyId);
		// 设置申请的状态 状态 0正常(默认) 1认证 99销户 2审核
		userVo.setUserStatus(2);
		userVo.setUserGrade(0);
		// 保存
		MemberUserVo registerUser = memberUserClient.registerUser(userVo);
		logger.debug("applyTrader:保存用户信息，registerUser：" + registerUser );
		Assert.notNull(registerUser, "提交审核失败，请重试");
		return new WebReturnMessage(true, "1");
	}

}
