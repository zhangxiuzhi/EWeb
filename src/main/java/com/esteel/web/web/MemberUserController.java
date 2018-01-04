package com.esteel.web.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
import com.esteel.web.vo.MemberCompanyVo;
import com.esteel.web.vo.MemberUserVo;
import com.esteel.web.vo.WebUtils;
import com.taobao.common.tfs.TfsManager;

/**
 * WEB-用户相关模块
 * 
 * @author chenshouye
 *
 */
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
	 * 注册成功页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/success")
	public String success(Model model) {
		logger.info("注册成功，跳转成功页面");
		return "/register/success";
	}

	/**
	 * 获取手机号码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMobile")
	@ResponseBody
	public WebReturnMessage getUserMboile() {
		logger.info("获取登录用户的手机验证码");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("getUserMboile:获取手机号码,获取登陆用户信息" + authentication);
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		String mobile = userVo.getMobile();
		logger.info("获取手机号码+返回结果{mobile}" + mobile);
		return new WebReturnMessage(true, mobile);
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
		logger.info("用户修改密码，参数{passwordA,passwordB}" + passwordA + passwordB);
		WebReturnMessage webRetMesage = null;
		List<Object> list = new ArrayList<>();
		// 比较两次密码输入是否一致
		if (!passwordA.equals(passwordB)) {
			list.add(0);
			webRetMesage = new WebReturnMessage(true, "两次密码输入不一致", list);
			logger.debug("修改密码，两次密码输入不一致");
			return webRetMesage;
		}
		// 获取登录用户
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		Assert.notNull(userVo, "用户登录已失效");
		try {
			// 加密保存
			String password = Encrypt.EncoderByMd5(passwordB);
			userVo.setPassword(password);
			MemberUserVo userupd = memberUserClient.registerUser(userVo);
			logger.debug("修改密码" + userupd);
			Assert.notNull(userupd, "修改失败");
			return WebReturnMessage.sucess;
		} catch (Exception e) {
			// e.printStackTrace();
			logger.debug(this.getClass() + "修改密码失败" + e);
			list.add(0);
			return new WebReturnMessage(true, "修改密码失败", list);
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
		logger.info("验证码+密码验证登录身份，参数{mobile,code,password}" + mobile + code + password);
		List<Object> list = new ArrayList<>();
		// 获取登录用户
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		Assert.notNull(userVo, "未登录");
		// 密码是否一致
		boolean flag = userVo.getPassword().equals(Encrypt.EncoderByMd5(password));
		if (flag) {
			list.add(0);
			return new WebReturnMessage(true, "密码错误", list);
		}
		// 根据验证码获取
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		Assert.notNull(codeVo, "验证码错误");
		long newTime = new Date().getTime(); // 当前时间
		long start = codeVo.getSendTime().getTime(); // 发送时间
		long end = codeVo.getValidTime().getTime(); // 有效时间
		// 判断是否在有效时间之内
		if (newTime > start && newTime <= end) {
			// 验证成功修改验证状态
			codeVo.setVerifyStatus(1);
			// logVerityCodeClient.saveLog(codeVo);
			logger.info(this.getClass() + "参数：" + mobile + "," + code + "," + password + ",返回结果：true");
			return new WebReturnMessage(true);
		} else {
			list.add(1);
			logger.info("验证码失效");
			return new WebReturnMessage(true, "验证码失效", list);
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
	@RequestMapping(value = "/checkIdentity", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage checkMail(String mobile, String code, int isNull) {
		logger.info("手机验证码验证用户身份，参数{mobile,code}" + mobile + "验证码：" + code);
		// 获取用户登录信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		Assert.notNull(userVo, "未登录");
		// 获取邮箱
		String email = userVo.getEmail();
		if (isNull == 1) {
			Assert.isNull(email, "您已绑定邮箱，无须在验证  ");
		} else if (isNull == 2) {
			Assert.notNull(email, "您还未验证邮箱，请先验证 ");
		}
		// 根据验证码获取
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		logger.debug("checkMail:通过手机验证码验证身份	LogVerifyCodeVo " + codeVo);
		Assert.notNull(codeVo, "验证码错误");
		long newTime = new Date().getTime(); // 当前时间
		long start = codeVo.getSendTime().getTime(); // 发送时间
		long end = codeVo.getValidTime().getTime(); // 有效时间
		// 判断是否在有效时间之内
		if (newTime > start && newTime <= end) {
			// 验证成功修改验证状态
			codeVo.setVerifyStatus(1);
			// logVerityCodeClient.saveLog(codeVo);
			List<Object> list = new ArrayList<>();
			list.add(userVo.getEmail() + "");
			list.add(userVo.getMobile());
			logger.info(this.getClass() + "参数：" + mobile + "," + code + ",返回结果：" + new WebReturnMessage(true));
			return new WebReturnMessage(true, "", list);
		} else {
			System.out.println("验证码失效了");
			codeVo.setVerifyStatus(2);
			// logVerityCodeClient.saveLog(codeVo);
			logger.info(
					this.getClass() + "参数：" + mobile + "," + code + ",返回结果：" + new WebReturnMessage(false, "验证码已失效"));
			return new WebReturnMessage(true, "验证码已失效");
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
		logger.info("发送邮件验证，参数{mail}" + mail);
		// 获取用户登录信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		logger.debug("sendMail:发送邮件，获取登录状态 MemberUserVo" + userVo);
		Assert.notNull(userVo, "请先登录");
		WebReturnMessage webRetMesage = null;
		// 设置验证信息
		LogVerifyCodeVo code = new LogVerifyCodeVo();
		code.setVerifyType(1); // 验证为邮箱
		code.setVerifyTarget(mail);
		// uuid
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		long id = userVo.getUserId();
		String url = "http://10.0.1.214:8888/register/check/" + uuid + "/" + id + "/html";
		String content = "你需要点击以下链接来激活你的邮箱:\n" + "<a href=\"" + url + "\" target=\"_blank\">" + url + "</a>";
		code.setVerifyCode(uuid); // 验证码
		code.setVerifyStatus(0); // 初始为0，未验证
		long time = new Date().getTime();
		code.setSendTime(new Timestamp(time)); // 发送时间
		code.setValidTime(new Timestamp(time + 30 * 60 * 1000));
		code.setVerifyContent(content); // 邮件信息
		// 保存返回
		LogVerifyCodeVo saveLog = logVerityCodeClient.saveLog(code);
		Assert.notNull(saveLog, "发送失败，请重试");
		String str = "您在点钢的账号已创建，请激活";

		// 发送邮件
		boolean sendMail = contactClient.sendMail(mail, str, content);
		logger.debug("发送邮件状态" + sendMail);
		if (sendMail) {
			List<Object> list = new ArrayList<>();
			list.add("验证邮件30分钟内有效，请尽快登录您的邮箱点击验证链接完成验证");
			String msg = "已发送邮件至" + mail;
			webRetMesage = new WebReturnMessage(true, msg, list);
		} else {
			webRetMesage = new WebReturnMessage(false, "邮件发送失败");
		}
		logger.info(this.getClass() + "参数：" + mail + ",返回结果：" + webRetMesage);
		return webRetMesage;

	}

	/**
	 * 修改邮箱
	 * 
	 * @param email
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/updMail", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage modifyMail(String email, String mobile) {
		logger.info("修改邮箱，参数{}", email, mobile);
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
		logger.info("修改邮箱返回结果：" + webRetMesage);
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
		logger.info("修改手机号，参数{mobile,code}" + mobile);
		List<Object> list = new ArrayList<>();
		// 获取登录用户
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		// 根据验证码获取
		LogVerifyCodeVo codeVo = logVerityCodeClient.checkCode(code, mobile);
		Assert.notNull(codeVo, "验证码错误");
		// 判断验证码是否过期
		long newTime = new Date().getTime(); // 当前时间
		long start = codeVo.getSendTime().getTime(); // 发送时间
		long end = codeVo.getValidTime().getTime(); // 有效时间
		if (newTime > start && newTime <= end) {
			// 保存
			userVo.setMobile(mobile);
			MemberUserVo registerUser = memberUserClient.registerUser(userVo);
			Assert.notNull(registerUser, "修改失败");
			list.add("变更成功\r\n" + "您的手机号已经更改为" + mobile + "，需重新登录网站。\r\n" + "立即登录 ");
			logger.debug("修改手机号成功");
			return new WebReturnMessage(true, "", list);
		} else {
			logger.debug("修改手机号，验证码失效");
			return new WebReturnMessage(true, "验证码已失效");
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
		logger.info("uploadFile:文件上传");
		if (file != null && !file.isEmpty()) {
			// 获取文件后缀
			String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
			try {
				// 文件ID
				String filepath = tfsManager.saveFile(file.getBytes(), null, type);
				if (filepath != null && filepath != "") {
					List<Object> list = new ArrayList<>();
					list.add(filepath);
					list.add(type);
					logger.debug("文件上传成功");
					return new WebReturnMessage(true, "", list);// 成功返回文件id
				} else {
					logger.debug("文件上传失败");
					return new WebReturnMessage(true, "文件上传失败");
				}
			} catch (Exception e) {
				logger.error("错误位置：" + this.getClass() + ".uploadFile" + e);
				return new WebReturnMessage(true, "文件上传失败");
			}
		} else {
			logger.warn("上传文件为空");
			return new WebReturnMessage(true, "请重新上传附件");
		}
	}

	/**
	 * 图片回显
	 * 
	 * @param fileId
	 * @param type
	 *            文件类型
	 * @return
	 */
	@RequestMapping(value = "/export/{fileId}/{type}/html")
	public ResponseEntity<byte[]> export(@PathVariable("fileId") String fileId, @PathVariable("type") String type) {
		logger.info("图片回显下载，参数{文件id+文件类型}" + fileId + "--" + type);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		// 生成一个uuid做文件名
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		headers.setContentDispositionFormData("attachment", uuid + type);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		boolean fetchFile = tfsManager.fetchFile(fileId, type, out);
		logger.info(
				"图片回显下载，返回结果" + fetchFile + new ResponseEntity<byte[]>(out.toByteArray(), headers, HttpStatus.CREATED));
		return new ResponseEntity<byte[]>(out.toByteArray(), headers, HttpStatus.CREATED);
	}

	/**
	 * 图片裁剪
	 * 
	 * @param rank
	 *            操作的会员等级 1 普通会员 2 企业
	 * @param fileId
	 *            文件id
	 * @param imgType
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/imageCut", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage uploadFileCut(int rank, String fileId, String imgType, int x, int y, int width,
			int height) {
		logger.info("uploadFileCut:图片裁剪,参数{}", fileId, imgType, x, y, width, height);
		// 获取登录用户
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("uploadFileCut:图片裁剪-用户登录信息", authentication);
		MemberUserVo userVo = memberUserClient.checkNo(authentication.getName());
		logger.debug("uploadFileCut:图片裁剪-用户信息", userVo);

		try {
			// 获取图像流文件
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			boolean fetchFile = tfsManager.fetchFile(fileId, imgType, out);
			if (fetchFile) {
				// 图片处理
				byte[] imgCut = WebUtils.imgCut(out, imgType, x, y, width, height);
				out.close();
				Assert.notNull(imgCut, "保存失败");
				// 保存处理后的图片得到fileid
				String fileid = tfsManager.saveFile(imgCut, null, ".JPEG");
				logger.debug("uploadFileCut:图片裁剪-保存结果", fileid);
				Assert.notNull(fileid, "保存失败");
				// 根据前端传递的rank参数来判断头像设置的是普通会员还是企业
				if (rank == 1) {
					// 设置用户信息
					userVo.setUserPicture(fileid + ".jpeg");
					MemberUserVo saveUser = memberUserClient.registerUser(userVo);
					Assert.notNull(saveUser, "保存失败");
					return new WebReturnMessage(true, "1");
				} else {
					// 设置企业信息
					MemberCompanyVo company = memberUserClient.findCompany((long) userVo.getCompanyId());
					Assert.notNull(company, "保存失败");
					company.setLogo(fileid + ".jpeg");
					userVo.getCompanyId();
					MemberCompanyVo saveComp = memberUserClient.saveComp(company);
					Assert.notNull(saveComp, "保存失败");
					return new WebReturnMessage(true, "1");
				}

			} else {
				// 获取图片失败
				return new WebReturnMessage(true, "0");
			}
		} catch (Exception e) {
			logger.error("uploadFileCut:图片裁剪-图片流处理异常");
			return new WebReturnMessage(true, "0");
		}
	}
}
