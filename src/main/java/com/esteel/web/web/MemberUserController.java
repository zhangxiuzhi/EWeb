package com.esteel.web.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esteel.common.controller.WebReturnMessage;
import com.esteel.web.service.ContactClient;
import com.esteel.web.service.LogVerityCodeClient;
import com.esteel.web.service.MemberClient;
import com.esteel.web.vo.Encrypt;
import com.esteel.web.vo.LogVerifyCodeVo;
import com.esteel.web.vo.MemberUserVo;
import com.taobao.common.tfs.TfsManager;

@Controller
@RequestMapping("/user")
public class MemberUserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static List<Object>  list;
	
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
	 * @param model
	 * @return
	 */
	@RequestMapping("/registerPage")
	public String addUI(Model model) {
		return "/register/register";
	}

	/**
	 * 注册成功页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/success")
	public String success(Model model) {
		return "/register/success";
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
		list = new ArrayList<>();
		MemberUserVo checkNo = memberUserClient.checkNo(mobile);
		Assert.isNull(checkNo,"该号码已注册");
		list.add(1);
		return new WebReturnMessage(true, "", list);

	}
	/**
	 * 获取手机号码
	 * @return
	 */
	@RequestMapping(value = "/getMobile")
	@ResponseBody
	public String  getUserMboile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
        String mobile = userVo.getMobile();
		return mobile;
	}
	/**
	 * 保存并发送验证码
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sendSms", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage sendSms(String mobile) {
		LogVerifyCodeVo code = new LogVerifyCodeVo();
		code.setVerifyType(0); // 验收为手机
		Integer miMa = (int) ((Math.random() * 9 + 1) * 100000); // 随机生成六位验证码
		code.setVerifyTarget(mobile); // 手机号码
		code.setVerifyContent("【点钢网】验证码为:" + miMa + "（请勿将验证码告知他人），请在页面中完成验证，如有问题请联系点钢网客服400-0072-323");
		code.setVerifyCode(miMa.toString()); // 验证码
		code.setVerifyStatus(0); // 初始为0，未验证
		long time = new Date().getTime();
		code.setSendTime(new Timestamp(time)); // 时间
		code.setValidTime(new Timestamp(time + 5*60 * 60 * 1000));// 有效时间3分钟
		// 返回结果
		WebReturnMessage webRetMesage = null;
		try {
			// 保存
			logVerityCodeClient.saveLog(code);
			// 发送短信
			boolean sendSms = contactClient.sendSms(mobile, code.getVerifyContent());
			if (sendSms) {
				webRetMesage = new WebReturnMessage(true, "验证码已发送");
			} else {
				webRetMesage = new WebReturnMessage(true, "验证码发送失败");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.debug("错误位置：MemberUserController.sendSms.错误：保存验证码对象失败抛出异常" + e);
			webRetMesage = new WebReturnMessage(false, "验证码发送失败,");
		}
		return webRetMesage;
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
		WebReturnMessage webRetMsg = null;
		List<Object> result = new ArrayList<>();
		//验证码验证
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		try {
			if (codeVo != null) {
				long newTime = new Date().getTime(); // 当前时间
				long start = codeVo.getSendTime().getTime(); // 发送时间
				long end = codeVo.getValidTime().getTime(); // 有效时间
				if (newTime > start && newTime < end) {
					String pwd = Encrypt.EncoderByMd5(password); // 密码加密
					MemberUserVo user = new MemberUserVo();
					user.setAccount("E"); // 帐号
					user.setMemberName(mobile);; // 帐号
					user.setMobile(mobile); // 手机
					user.setPassword(pwd); // 密码
					user.setUserStatus(0); // 状态，初始为0
					user.setUserGrade(0); //用户权级，默认普通会员 0
					user.setRegisteredTime(new Timestamp(System.currentTimeMillis())); // 注册时间
					user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
					// 调用保存
					MemberUserVo userVo = memberUserClient.registerUser(user);
					if (userVo != null) {
						// 验证成功修改状态为验证通过1
						codeVo.setVerifyStatus(1);
						//logVerityCodeClient.saveLog(codeVo);
						result.add(0);
						webRetMsg = new WebReturnMessage(true, "注册成功",result);
						// 注册成功，把对象存session作用域来模拟登录状态
					} else {
						// 验证成功修改状态为验证通过2,验证未通过
						 codeVo.setVerifyStatus(2);
						 logVerityCodeClient.saveLog(codeVo);
						 result.add(1);
						webRetMsg = new WebReturnMessage(true, "注册失败",result);
					}
				} else {
					// 验证成功修改状态为验证通过2,验证未通过
					//codeVo.setVerifyStatus(2);
					//logVerityCodeClient.saveLog(codeVo);
					result.add(1);
					webRetMsg = new WebReturnMessage(true, "注册失败,验证码已失效",result);
				}
			} else {
				result.add(1);
				webRetMsg = new WebReturnMessage(true, "注册失败,验证码错误",result);
			}
		} catch (Exception e) {
			// 验证成功修改状态为验证通过2
			codeVo.setVerifyStatus(2);
			logVerityCodeClient.saveLog(codeVo);
			webRetMsg = new WebReturnMessage(false, "注册失败,验证未通过");
			logger.debug(this.getClass() + "：注册失败，验证未通过" + e);
		}
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

	/**
	 * 修改密码
	 * 
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage updatePwd(String passwordA, String passwordB) {
		WebReturnMessage webRetMesage = null;
		list = new ArrayList<>();
		//比较两次密码输入是否一致
		if(!passwordA.equals(passwordB)) {
			list.add(0);
			 webRetMesage = new WebReturnMessage(true, "两次密码输入不一致",list);
			return webRetMesage;
		}
		//获取登录用户
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
		Assert.notNull(userVo, "用户登录已失效");
		try {
			//加密保存
			String password = Encrypt.EncoderByMd5(passwordB);
			userVo.setPassword(password);
			MemberUserVo userupd = memberUserClient.registerUser(userVo);
			Assert.notNull(userupd, "修改失败");
			return WebReturnMessage.sucess;
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug(this.getClass()+"修改密码失败"+e);
			list.add(0);
			return new WebReturnMessage(true, "修改密码失败",list);
		}
		
	}

	/**
	 * 登录用户身份---验证码+密码验证
	 * 
	 * @param moblle
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/checkIdentityPwd", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage testMail(String mobile, String code, String password) {
		WebReturnMessage webRetMesage = null;
		list = new ArrayList<>();
		//获取登录用户
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
		Assert.notNull(userVo, "未登录");
		// 密码是否一致
		boolean flag = userVo.getPassword().equals(Encrypt.EncoderByMd5(password));
		if(flag) {
			list.add(0);
			return new WebReturnMessage(true,"密码错误",list);
		}	
		// 根据验证码获取
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		Assert.notNull(codeVo, "验证码错误");
		long newTime = new Date().getTime(); // 当前时间
		long start = codeVo.getSendTime().getTime(); // 发送时间
		long end = codeVo.getValidTime().getTime(); // 有效时间
		//判断是否在有效时间之内
		if (newTime > start && newTime <= end) {
			//验证成功修改验证状态
			codeVo.setVerifyStatus(1);
			//logVerityCodeClient.saveLog(codeVo);
			logger.info(this.getClass()+"参数："+mobile+","+code+","+password+",返回结果："+new WebReturnMessage(false,"验证码已失效"));
			return webRetMesage.sucess;
		}else {
			list.add(1);
			return new WebReturnMessage(true, "验证码失效",list);
		}
	}

	/**
	 * 登录用户身份----手机验证码验证
	 * 
	 * @param mobile
	 * @param code
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/checkIdentity")
	@ResponseBody
	public WebReturnMessage checkMail(String mobile, String code) {
		//获取用户登录信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
		Assert.notNull(userVo, "未登录");
		//获取邮箱
		String email = userVo.getEmail();
		Assert.notNull(email, "您还未进行邮箱验证，请先验证");
		// 根据验证码获取
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		Assert.notNull(codeVo, "验证码错误");
		long newTime = new Date().getTime(); // 当前时间
		long start = codeVo.getSendTime().getTime(); // 发送时间
		long end = codeVo.getValidTime().getTime(); // 有效时间
		//判断是否在有效时间之内
		if (newTime > start && newTime <= end) {
			//验证成功修改验证状态
			codeVo.setVerifyStatus(1);
			//logVerityCodeClient.saveLog(codeVo);
			list = new ArrayList<>();
			list.add(userVo.getEmail()+"");
			list.add(userVo.getMobile());
			logger.info(this.getClass()+"参数："+mobile+","+code+",返回结果："+new WebReturnMessage(true));
			return new WebReturnMessage(true,"",list);
		} else {
			System.out.println("验证码失效了");
			codeVo.setVerifyStatus(2);
			//logVerityCodeClient.saveLog(codeVo);
			logger.info(this.getClass()+"参数："+mobile+","+code+",返回结果："+new WebReturnMessage(false,"验证码已失效"));
			return  new WebReturnMessage(true,"验证码已失效");
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 * @return
	 */
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage sendMail(String mail) {
		WebReturnMessage webRetMesage = null;
		// 设置验证信息
		LogVerifyCodeVo code = new LogVerifyCodeVo();
		code.setVerifyType(1); // 验证为邮箱
		code.setVerifyTarget(mail);
		code.setVerifyContent("【点钢网】  邮箱验证码为： ,请勿转发他人,如非本人操作请忽略。"); //邮件信息
		code.setVerifyCode(""); // 验证码
		code.setVerifyStatus(0); // 初始为0，未验证
		long time = new Date().getTime();
		code.setSendTime(new Timestamp(time)); // 发送时间
		code.setValidTime(new Timestamp(time + 30 * 60 * 1000));

		// 保存返回
		LogVerifyCodeVo saveLog = logVerityCodeClient.saveLog(code);
		Assert.notNull(saveLog, "发送失败，请重试");
		String str = "您在点钢的账号已创建，请激活";
		// 发送邮件
		boolean sendMail = contactClient.sendMail(mail,str,"\"【点钢网】  邮箱验证码为：\" + miMa + \",请勿转发他人,如非本人操作请忽略。\"");
		if (sendMail) {
			list = new ArrayList<>();
			list.add("验证邮件30分钟内有效，请尽快登录您的邮箱点击验证链接完成验证");
			String msg = "已发送邮件至"+mail;
			webRetMesage = new WebReturnMessage(true,msg,list);
		} else {
			webRetMesage = new WebReturnMessage(false, "邮件发送失败");
		}
		logger.info(this.getClass()+"参数："+mail+",返回结果："+webRetMesage);
		return webRetMesage;
		
	}

	/**
	 * 修改邮箱
	 * 
	 * @param email
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/updMail", method = RequestMethod.GET)
	@ResponseBody
	public WebReturnMessage modifyMail(String email, String mobile) {
		WebReturnMessage webRetMesage = null;
		// 获取登录用户
		MemberUserVo user = memberUserClient.checkNo(mobile);
		// 更新邮箱
		user.setEmail(email);
		MemberUserVo registerUser = memberUserClient.registerUser(user);
		if (registerUser != null) {
			webRetMesage = new WebReturnMessage(true, "\r\n" + "恭喜，邮箱更改成功\r\n" + "您还可以前往账户安全继续完善您的账户信息哦！");
		} else {
			webRetMesage = new WebReturnMessage(false, "修改失败");
		}
		return webRetMesage;

	}

	/**
	 * 修改手机号
	 * 
	 * @param phone
	 * @param code
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/updMobile", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage modifyMobile(String code, String mobile) {
		list = new ArrayList<>();
		//获取登录用户
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.findByAccount(authentication.getName());
		// 根据验证码获取
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		Assert.notNull(codeVo, "验证码错误");
		//判断验证码是否过期
		long newTime = new Date().getTime(); // 当前时间
		long start = codeVo.getSendTime().getTime(); // 发送时间
		long end = codeVo.getValidTime().getTime(); // 有效时间
		if (newTime > start && newTime <= end) {
			//保存
			userVo.setMobile(mobile);
			MemberUserVo registerUser = memberUserClient.registerUser(userVo);
			Assert.notNull(registerUser, "修改失败");
			list.add("变更成功\r\n" + "您的手机号已经更改为" + mobile + "，需重新登录网站。\r\n" + "立即登录 ");
			return new WebReturnMessage(true,"",list);
		}else {
			return  new WebReturnMessage(true,"验证码已失效");
		}
	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage uploadFile(MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			// 获取文件后缀
			String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
			try {
				// 文件ID
				String filepath = tfsManager.saveFile(file.getBytes(), null, type);
				if (filepath != null && filepath != "") {
					list = new ArrayList<>();
					list.add(filepath);
					list.add(type);
					return new WebReturnMessage(true, "",list);// 成功返回文件id
				} else {
					return new WebReturnMessage(true, "文件上传失败");
				}
			} catch (Exception e) {
				logger.debug("错误位置：" + this.getClass() + ".uploadFile" + e);
				return new WebReturnMessage(true, "文件上传失败");
			}
		} else {
			return new WebReturnMessage(true, "文件不能为空");
		}
	}
	/**
	 * 图片回显
	 * @param fileId  
	 * @param type  文件类型
	 * @return
	 */
	@RequestMapping(value="/export/{fileId}/{type}/html")
	public ResponseEntity<byte[]> export(@PathVariable("fileId") String fileId,@PathVariable("type") String type){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		//生成一个uuid做文件名
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		headers.setContentDispositionFormData("attachment", uuid+type);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		boolean fetchFile = tfsManager.fetchFile(fileId,type,out);
		return new ResponseEntity<byte[]>( out.toByteArray(),headers, HttpStatus.CREATED);
	}

	/**
	 * 普通会员头像编辑
	 * 
	 * @param fileId
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/memberIgm", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage userImg(String fileId, String mobile) {
		WebReturnMessage webRetMesage = null;
		// 获取当前用户
		MemberUserVo user = memberUserClient.checkNo(mobile);
		user.setUserPicture(fileId);
		// 保存
		MemberUserVo registerUser = memberUserClient.registerUser(user);
		if (registerUser != null) {
			webRetMesage = new WebReturnMessage(true, "保存成功");
		} else {
			webRetMesage = new WebReturnMessage(false, "保存失败");
		}
		return webRetMesage;
	}
}
