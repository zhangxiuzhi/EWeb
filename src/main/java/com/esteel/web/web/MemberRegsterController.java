package com.esteel.web.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.ribbon.proxy.annotation.CacheProvider;
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
	
	@Autowired
    CacheManager cacheManager;

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
	public WebReturnMessage register(String mobile, String code, String password,HttpServletRequest request) {
		logger.info(this.getClass() + "用户注册,参数{}" + mobile + code + password);
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
						//注册成功之后自动登录
						List<GrantedAuthority> authorities = new ArrayList<>();
				        User users = new User(userVo.getMobile(),userVo.getPassword(),authorities);
				        Authentication auth = new UsernamePasswordAuthenticationToken(userVo.getMobile(), "", users.getAuthorities());
				        SecurityContextHolder.getContext().setAuthentication(auth);
				        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
						
						return new WebReturnMessage(true, "注册成功", result);
						// 注册成功，把对象存session作用域来模拟登录状态
					} else {
						// 保存用户注册信息失败验证码状态不处理
						// codeVo.setVerifyStatus(2);
						// logVerityCodeClient.saveLog(codeVo);
						return new WebReturnMessage(true, "注册失败");
					}
				} else {
					// 验证码已过有效期，修改状态为2.失效
					codeVo.setVerifyStatus(2);
					logVerityCodeClient.saveLog(codeVo);
					return new WebReturnMessage(true, "注册失败,验证码已失效");
				}
			} else {
				return new WebReturnMessage(true, "注册失败,验证码错误");
			}
		} catch (Exception e) {
			// 验证成功修改状态为验证通过2
			codeVo.setVerifyStatus(2);
			logVerityCodeClient.saveLog(codeVo);
			logger.error(this.getClass() + "：注册失败，验证未通过" + e);
			return new WebReturnMessage(false, "注册失败,验证未通过");
		}
	}
	/**
	 * 跳转找回密码页面
	 * @return
	 */
	@RequestMapping(value = "/findPwd")
	public String getBackPwd() {
		return "/register/findPwd";
	}
	/**
	 * 找回密码
	 * 
	 * @param mobile
	 * @param code
	 * @return 
	 */
	@RequestMapping(value = "/findPwd", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage findPassword(String mobile, String code,HttpSession session) {
		logger.info("findPassword:找回密码，参数{mobile,code}");
		// 根据验证码确认
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		logger.debug("findPassword:找回密码,验证"+codeVo);
		Assert.notNull(codeVo, "验证码错误");
		//是否过期
		long newTime = new Date().getTime(); // 当前时间
		long start = codeVo.getSendTime().getTime(); // 发送时间
		long end = codeVo.getValidTime().getTime(); // 有效时间
		if (newTime > start && newTime <= end) {
			//放作用域,有效时间一个
			codeVo.setVerifyStatus(1);
			logVerityCodeClient.saveLog(codeVo);
			session.setAttribute("codeVo", codeVo);
			session.setMaxInactiveInterval(30*60);
			return  new WebReturnMessage(true,"1");
		}else {
			//验证码失效
			codeVo.setVerifyStatus(2);
			logVerityCodeClient.saveLog(codeVo);
			return  new WebReturnMessage(true,"验证码已失效");
		}
		
	}
	
	/**
	 * 跳转处重置密码
	 * @return
	 */
	@RequestMapping(value = "/resetPwd")
	public String getBackPwd(HttpSession session) {
		LogVerifyCodeVo codeVo =(LogVerifyCodeVo) session.getAttribute("codeVo");
		if(codeVo!=null) {
			return "/register/resetPwd";
		}else {
			return "/register/findPwd";
		}
	}
	/**
	 * 重置密码
	 * @param resetPwd
	 * @param firmPwd
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage resetPassword(String resetPwd, String firmPwd,HttpSession session) {
		List<Object> list = new ArrayList<>();
		//确认密码正确
		if(!resetPwd.equals(firmPwd)) {
			list.add(1);
			return new WebReturnMessage(true, "1",list);
		}
		//获取验证的信息
		LogVerifyCodeVo codeVo = (LogVerifyCodeVo) session.getAttribute("codeVo");
		if(codeVo!=null){
			String mobile = codeVo.getVerifyTarget();
			//获取用户信息
			MemberUserVo checkNo = memberUserClient.checkNo(mobile);
			Assert.notNull(checkNo, "重置密码失败");
			//修改保存
			checkNo.setPassword(resetPwd);
			MemberUserVo user = memberUserClient.registerUser(checkNo);
			Assert.notNull(user, "重置密码失败");
			//移除验证身份信息
			session.removeAttribute("codeVo");
			return new WebReturnMessage(true, "2");
		}else {
			list.add(2);
			return new WebReturnMessage(true, "1",list);
		}
		
	}
	
	
	
	/**
	 * 验证邮箱
	 * @param uuid
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/check/{uuid}/{id}/html")
	@ResponseBody
	public WebReturnMessage verification(@PathVariable("uuid") String uuid,@PathVariable("id") long id) {
		logger.info("verification:邮箱验证，参数｛uuid,id｝"+uuid+",用户id:"+id);
		//获取Log
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCodeByUuid(uuid);
		Assert.notNull(codeVo, "验证失败");
		//验证是否在有效时间之内
		long newTime = new Date().getTime(); // 当前时间
		long start = codeVo.getSendTime().getTime(); // 发送时间
		long end = codeVo.getValidTime().getTime(); // 有效时间
		if (newTime > start && newTime <= end) {
			//根据id获取用户的信息
			MemberUserVo findUser = memberUserClient.findUser(id);
			logger.debug("verification:邮箱验证,获取用户信息"+findUser);
			Assert.notNull(findUser, "验证失败");
			//从log中获取邮箱设置用户邮箱信息
			findUser.setEmail(codeVo.getVerifyTarget());
			//保存
			MemberUserVo registerUser = memberUserClient.registerUser(findUser);
			logger.debug("verification:邮箱验证,保存用户邮箱信息"+registerUser);
			Assert.notNull(registerUser, "验证失败");
			//验证通过
			codeVo.setVerifyStatus(1);
			logVerityCodeClient.saveLog(codeVo);
			return  new WebReturnMessage(true,"验证成功");
		}else {
			//验证不通过
			codeVo.setVerifyStatus(2);
			logVerityCodeClient.saveLog(codeVo);
			return  new WebReturnMessage(true,"验证失败，已过有效期");
		}
	}
	
	
	
}
