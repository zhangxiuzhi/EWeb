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

import com.esteel.web.service.MemberClient;
import com.esteel.web.vo.MemberUserVo;

/**
 * ESTeel Description: 用户帐号安全模块
 * 
 * @author chenshouye
 * 
 */
@RequestMapping("/safe")
@Controller
public class MemberSafeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	MemberClient memberUserClient;

	/**
	 * 跳转用户账号安全主页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/accountSafe")
	public String accountSafe(Model model) {
		logger.info("accountSafe:跳转用户账号安全主页面");
		// 获取到登录用户信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("accountSafe:跳转用户账号安全主页面,获取登录状态"+authentication);
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		logger.debug("accountSafe:跳转用户账号安全主页面,获取登录用户信息"+userVo);
		Assert.notNull(userVo, "无法获取用户信息");
		// 去掉密码信息.substring(4, 8);
		/*userVo.getMobile();
		userVo.setPassword("XXXXXXXXX");*/
		model.addAttribute("userVo", userVo);
		logger.info("accountSafe:跳转用户账号安全主页面,/safe/accountSafe");
		return "/safe/accountSafe";
	}

	/**
	 * 修改密碼--获取用户信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePassword")
	public String updatePassword(Model model) {
		logger.info("updatePassword:修改密碼--获取用户信息");
		// 获取到登录用户信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("updatePassword:登陆状态-"+authentication);
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		logger.debug("updatePassword:登陆状态--获取用户信息"+userVo);
		Assert.notNull(userVo, "无法获取用户信息");
		model.addAttribute("userVo", userVo);
		return "/safe/updatePassword";
	}

	/**
	 * 修改邮箱
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateMail")
	public String updateMail(Model model) {
		logger.info("updateMail:修改邮箱");
		return "/safe/updateMail";
	}

	/**
	 * 修改手机号
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateMobile")
	public String updateMobile(Model model) {
		logger.info("updateMobile:修改手机号");
		return "/safe/updateMobile";
	}

	/**
	 * 验证邮箱
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/verifyMail")
	public String verifyMail(Model model) {
		logger.info("verifyMail:验证邮箱");
		return "/safe/verifyMail";
	}
}
