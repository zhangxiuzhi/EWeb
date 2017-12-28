package com.esteel.web.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.esteel.common.controller.WebReturnMessage;
import com.esteel.web.service.ContactClient;
import com.esteel.web.service.LogVerityCodeClient;
import com.esteel.web.service.MemberClient;
import com.esteel.web.vo.Encrypt;
import com.esteel.web.vo.LogVerifyCodeVo;
import com.esteel.web.vo.MemberUserVo;
import com.taobao.common.tfs.TfsManager;

/**
 * ESTeel Description:  web无登录状态验证入口
 * @author chenshouye
 */
@RequestMapping("/register")
@Controller
public class MemberRegsterController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MemberClient memberUserClient;

	@Autowired
	ContactClient contactClient;

	@Autowired
	LogVerityCodeClient logVerityCodeClient;

	@Autowired
	TfsManager tfsManager;

	/**
	 * 注册页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/registerPage")
	public String addUI(Model model) {
		logger.info("addUI：跳转注册页面");
		return "/register/register";
	}

	/**
	 * 检查手机号码是否注册
	 * 
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/checkNo")
	@ResponseBody
	public WebReturnMessage checkNo(String mobile) {
		logger.info("验证手机号码是否注册+参数{mobile}" + mobile);
		List<Object> list = new ArrayList<>();
		MemberUserVo checkNo = memberUserClient.checkNo(mobile);
		// Assert.isNull(checkNo,"该号码已注册");
		if (checkNo == null) {
			return new WebReturnMessage(true, "");
		} else {
			list.add(1);
			logger.info("验证手机号码是否注册,返回结果：WebReturnMessage" + new WebReturnMessage(true, "该号码已被注册", list));
			return new WebReturnMessage(true, "", list);
		}
	}

	/**
	 * 保存并发送验证码
	 * 验证码有效时间三分钟
	 * @param mobile
	 * @return WebReturnMessage
	 */
	@RequestMapping(value = "/sendSms", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage sendSms(String mobile) {
		logger.info("根据手机号码发送验证码+参数{mobile}" + mobile);
		LogVerifyCodeVo code = new LogVerifyCodeVo();
		code.setVerifyType(0); // 验收为手机
		Integer miMa = (int) ((Math.random() * 9 + 1) * 100000); // 随机生成六位验证码
		code.setVerifyTarget(mobile); // 手机号码
		code.setVerifyContent("验证码为:" + miMa + "（请勿将验证码告知他人），请在页面中完成验证，如有问题请联系点钢网客服400-0072-323");
		code.setVerifyCode(miMa.toString()); // 验证码
		code.setVerifyStatus(0); // 初始为0，未验证
		long time = new Date().getTime();
		code.setSendTime(new Timestamp(time)); // 时间
		code.setValidTime(new Timestamp(time + 3 * 60 * 1000));// 有效时间3分钟
		// 返回结果
		try {
			// 保存
			logVerityCodeClient.saveLog(code);
			// 发送短信
			boolean sendSms = contactClient.sendSms(mobile, code.getVerifyContent());
			if (sendSms) {
				logger.debug("sendSms:发送验证码成功返回");
				return new WebReturnMessage(true, "验证码已发送");
			} else {
				logger.debug("sendSms:验证码发送失败");
				return new WebReturnMessage(true, "验证码发送失败");
			}
		} catch (Exception e) {
			logger.error("错误位置：MemberUserController.sendSms.错误：保存验证码对象失败抛出异常" + e);
			return new WebReturnMessage(false, "验证码发送失败,");
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param mobile
	 * @param code
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage register(String mobile, String code, String password, Model model) {
		logger.info(this.getClass() + "用户注册,参数{}" + mobile + code + password);
		WebReturnMessage webRetMsg = null;
		List<Object> result = new ArrayList<>();
		// 验证码验证
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		try {
			if (codeVo != null) {
				long newTime = new Date().getTime(); // 当前时间
				long start = codeVo.getSendTime().getTime(); // 发送时间
				long end = codeVo.getValidTime().getTime(); // 有效时间
				if (newTime > start && newTime < end) {
					// 设置用户信息
					String pwd = Encrypt.EncoderByMd5(password); // 密码加密
					MemberUserVo user = new MemberUserVo();
					user.setAccount(mobile); // 帐号
					user.setUserName(mobile);
					user.setMemberName(mobile); // 帐号
					user.setMobile(mobile); // 手机
					user.setPassword(pwd); // 密码
					user.setUserStatus(0); // 状态，初始为0
					user.setUserGrade(0); // 用户权级，默认普通会员 0
					user.setRegisteredTime(new Timestamp(System.currentTimeMillis())); // 注册时间
					user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
					// 调用保存
					MemberUserVo userVo = memberUserClient.registerUser(user);
					logger.debug("保存用户注册信息" + userVo);
					if (userVo != null) {
						// 验证通过，修改状态为验证通过 1
						codeVo.setVerifyStatus(1);
						logVerityCodeClient.saveLog(codeVo);
						result.add(0);
						webRetMsg = new WebReturnMessage(true, "注册成功", result);
						// 注册成功，把对象存session作用域来模拟登录状态
					} else {
						// 保存用户注册信息失败验证码状态不处理
						// codeVo.setVerifyStatus(2);
						// logVerityCodeClient.saveLog(codeVo);
						webRetMsg = new WebReturnMessage(true, "注册失败");
					}
				} else {
					// 验证码已过有效期，修改状态为2.失效
					codeVo.setVerifyStatus(2);
					logVerityCodeClient.saveLog(codeVo);
					webRetMsg = new WebReturnMessage(true, "注册失败,验证码已失效");
				}
			} else {
				webRetMsg = new WebReturnMessage(true, "注册失败,验证码错误");
			}
		} catch (Exception e) {
			// 验证成功修改状态为验证通过2
			codeVo.setVerifyStatus(2);
			logVerityCodeClient.saveLog(codeVo);
			webRetMsg = new WebReturnMessage(false, "注册失败,验证未通过");
			logger.error(this.getClass() + "：注册失败，验证未通过" + e);
		}
		logger.info(this.getClass() + "用户注册返回结果" + webRetMsg);
		return webRetMsg;
	}

	/**
	 * 找回密码
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/findPwd", method = RequestMethod.POST)
	public String findPassword(String mobile, String code, Model model) {
		// 根据验证码确认
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		// 根据手机号码确认是否有这个用户
		MemberUserVo checkNo = memberUserClient.checkNo(mobile);
		if (codeVo != null && checkNo != null) {
			// 验证成功把对象信息放入session,初期模拟登录状态
			return "/register/recover";
		} else {
			model.addAttribute("false", new WebReturnMessage(false, "系统无此用户或验证码错误"));
			model.addAttribute("mobile", mobile);
			return "/register/findPwd";

		}
	}
}
