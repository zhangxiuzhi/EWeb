package com.esteel.web.web;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@RequestMapping("/register")
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
	@RequestMapping(value = "/checkNo", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage checkNo(String mobile) {
		MemberUserVo checkNo = memberUserClient.checkNo(mobile);
		WebReturnMessage webRetMesage = null;
		if (checkNo != null) {
			webRetMesage = new WebReturnMessage(true, "该号码已注册");
		} else {
			webRetMesage = new WebReturnMessage(false, "该号码可注册");
		}
		return webRetMesage;

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
		code.setValidTime(new Timestamp(time + 3 * 60 * 1000));// 有效时间3分钟
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
				webRetMesage = new WebReturnMessage(false, "验证码发送失败");
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
	public WebReturnMessage register(String mobile, String code, String password, HttpSession session, Model model) {
		System.out.println(mobile+"///"+code+"///"+password+"///");
		WebReturnMessage webRetMsg = null;
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		System.out.println("MemberUserController.register()"+"*****"+codeVo);
		try {
			if (codeVo != null) {
				long newTime = new Date().getTime(); // 当前时间
				long start = codeVo.getSendTime().getTime(); // 发送时间
				long end = codeVo.getValidTime().getTime(); // 有效时间
				System.out.println("MemberUserController.register()"+newTime);
				System.out.println("MemberUserController.register()"+start);
				System.out.println("MemberUserController.register()"+end);
				if (newTime > start && newTime < end) {
					String pwd = Encrypt.EncoderByMd5(password); // 密码加密
					MemberUserVo user = new MemberUserVo();
					user.setAccount("E"); // 帐号
					user.setMobile(mobile); // 手机
					user.setPassword(pwd); // 密码
					user.setUserStatus(0); // 状态，初始为0
					user.setRegisteredTime(new Timestamp(System.currentTimeMillis())); // 注册时间
					user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
					// 调用保存
					MemberUserVo userVo = memberUserClient.registerUser(user);
					System.out.println("MemberUserController.register()" +"//////////////"+ userVo);
					if (userVo != null) {
						// 验证成功修改状态为验证通过1
						codeVo.setVerifyStatus(1);
						logVerityCodeClient.saveLog(codeVo);
						webRetMsg = new WebReturnMessage(true, "注册成功");
						// 注册成功，把对象存session作用域来模拟登录状态
						session.setAttribute("userVo", userVo);
					} else {
						// 验证成功修改状态为验证通过2,验证未通过
						codeVo.setVerifyStatus(2);
						logVerityCodeClient.saveLog(codeVo);
						webRetMsg = new WebReturnMessage(false, "注册失败");
					}
				} else {
					// 验证成功修改状态为验证通过2,验证未通过
					codeVo.setVerifyStatus(2);
					logVerityCodeClient.saveLog(codeVo);
					webRetMsg = new WebReturnMessage(false, "验证码已失效");
				}
			} else {
				webRetMsg = new WebReturnMessage(false, "验证码错误");
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
	public String findPassword(String mobile, String code, Model model, HttpSession session) {
		// 根据验证码确认
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		// 根据手机号码确认是否有这个用户
		MemberUserVo checkNo = memberUserClient.checkNo(mobile);
		if (codeVo != null && checkNo != null) {
			// 验证成功把对象信息放入session,初期模拟登录状态
			session.setAttribute("userVo", checkNo);
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
	@RequestMapping(value = "/resetPwd", method = RequestMethod.GET)
	public String updatePwd(String pwd, HttpSession session) {
		MemberUserVo user = new MemberUserVo();
		String password;
		try {
			password = Encrypt.EncoderByMd5(pwd);
			user.setPassword(password);
			MemberUserVo userVo = memberUserClient.registerUser(user);
			if (userVo != null) {
				session.setAttribute("userVo", userVo);
				return "/";
			} else {
				return "/register/resetPed"; // 返回重置页面
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/500页面";
		}
	}

	/**
	 * 登录用户身份密码验证
	 * 
	 * @param moblle
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/updtPwd", method = RequestMethod.GET)
	@ResponseBody
	public WebReturnMessage testMail(String mobile, String code, String password, HttpSession session) {
		WebReturnMessage webRetMesage = null;
		// 根据验证码获取
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		MemberUserVo userVo = memberUserClient.checkNo(mobile);
		if (userVo != null && codeVo != null) {
			boolean flag;
			try {
				// 密码是否一致
				flag = Encrypt.EncoderByMd5(userVo.getPassword()).equals(Encrypt.EncoderByMd5(password));
				if (flag) {
					webRetMesage = new WebReturnMessage(true, "验证通过");
					session.setAttribute("userVo", userVo);
				} else {
					webRetMesage = new WebReturnMessage(false, "密码错误");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("错误位置：WebReturnMessage.testMail" + e);
				webRetMesage = new WebReturnMessage(false, "密码错误");
			}
		} else {
			webRetMesage = new WebReturnMessage(false, "验证失败");
		}
		return webRetMesage;
	}

	/**
	 * 登录用户身份手机验证码验证
	 * 
	 * @param mobile
	 * @param code
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/checkMail", method = RequestMethod.GET)
	@ResponseBody
	public WebReturnMessage checkMail(String mobile, String code, HttpSession session) {
		WebReturnMessage webRetMesage = null;
		// 根据验证码获取
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		MemberUserVo userVo = memberUserClient.checkNo(mobile);
		if (userVo != null && codeVo != null) {
			long newTime = new Date().getTime(); // 当前时间
			long start = codeVo.getSendTime().getTime(); // 发送时间
			long end = codeVo.getValidTime().getTime(); // 有效时间
			if (newTime > start && newTime < end) {
				webRetMesage = new WebReturnMessage(true, "验证通过");
			} else {
				webRetMesage = new WebReturnMessage(true, "验证码已失效");
			}
		} else {
			webRetMesage = new WebReturnMessage(false, "验证失败");
		}
		return webRetMesage;
	}

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 * @return
	 */
	@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	@ResponseBody
	public WebReturnMessage sendMail(String mail) {
		// 设置验证信息
		LogVerifyCodeVo code = new LogVerifyCodeVo();
		code.setVerifyType(1); // 验证为邮箱
		code.setVerifyTarget(mail);
		Integer miMa = (int) ((Math.random() * 9 + 1) * 100000); // 随机生成六位验证码
		code.setVerifyContent("【点钢网】  邮箱验证码为：" + miMa + ",请勿转发他人,如非本人操作请忽略。"); // 发送手机信息
		code.setVerifyCode(miMa.toString()); // 验证码
		code.setVerifyStatus(0); // 初始为0，未验证
		long time = new Date().getTime();
		code.setSendTime(new Timestamp(time)); // 发送时间
		code.setValidTime(new Timestamp(time + 24 * 60 * 60 * 1000));

		// 保存返回
		LogVerifyCodeVo saveLog = logVerityCodeClient.saveLog(code);
		WebReturnMessage webRetMesage = null;
		if (saveLog != null) {
			String str = "您在点钢的账号已创建，请激活";
			// 发送邮件
			boolean sendMail = contactClient.sendMail(mail, str, code.getVerifyTarget());
			if (sendMail) {

				webRetMesage = new WebReturnMessage(true, "已发送邮件" + mail + "\r\n" + "验证邮件24小时内有效，请尽快登录您的邮箱点击验证链接完成验证");
			} else {
				webRetMesage = new WebReturnMessage(false, "发送失败");
			}
		} else {
			webRetMesage = new WebReturnMessage(false, "发送失败");
		}
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
	 * @param phone
	 * @param code
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/updMobile", method = RequestMethod.GET)
	@ResponseBody
	public WebReturnMessage modifyMobile(String phone, String code, String mobile) {
		WebReturnMessage webRetMesage = null;
		// 获取当前用户
		MemberUserVo user = memberUserClient.checkNo(phone);
		// 根据验证码获取
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		MemberUserVo userVo = memberUserClient.checkNo(mobile);
		if (userVo != null && codeVo != null) {
			long newTime = new Date().getTime(); // 当前时间
			long start = codeVo.getSendTime().getTime(); // 发送时间
			long end = codeVo.getValidTime().getTime(); // 有效时间
			if (newTime > start && newTime < end) {
				user.setMobile(mobile);
				MemberUserVo registerUser = memberUserClient.registerUser(user);
				if (registerUser != null) {
					webRetMesage = new WebReturnMessage(true,
							"\r\n" + "变更成功\r\n" + "您的手机号已经更改为" + mobile + "，需重新登录网站。\r\n" + "立即登录 ");
				} else {
					webRetMesage = new WebReturnMessage(false, "修改失败");
				}

			} else {
				webRetMesage = new WebReturnMessage(false, "验证码已失效");
			}
		} else {
			webRetMesage = new WebReturnMessage(false, "验证失败");
		}
		return webRetMesage;
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
		WebReturnMessage webRetMesage = null;
		if (file != null && !file.isEmpty()) {
			// 获取文件后缀
			String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
			try {
				// 文件ID
				String filepath = tfsManager.saveFile(file.getBytes(), null, type);
				if (filepath != null && filepath != "") {
					webRetMesage = new WebReturnMessage(true, filepath);// 成功返回文件id
				} else {
					webRetMesage = new WebReturnMessage(false, "文件上传失败");
				}
			} catch (Exception e) {
				logger.debug("错误位置：" + this.getClass() + ".uploadFile" + e);
				webRetMesage = new WebReturnMessage(false, "文件上传失败");
			}
		} else {
			webRetMesage = new WebReturnMessage(false, "文件不能为空");
		}
		return webRetMesage;

	}

	/**
	 * 文件下载
	 * 
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	@ResponseBody
	public void uploadFile(String fileId, String req) {
		tfsManager.fetchFile(fileId, "jpg", "e:\\1111.jpg");
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
