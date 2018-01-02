package com.esteel.web.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.esteel.common.controller.WebReturnMessage;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.ContactClient;
import com.esteel.web.service.MemberClient;
import com.esteel.web.vo.CityVo;
import com.esteel.web.vo.DistrictVo;
import com.esteel.web.vo.Encrypt;
import com.esteel.web.vo.MemberCompanyAttachVo;
import com.esteel.web.vo.MemberCompanyVo;
import com.esteel.web.vo.MemberUserVo;
import com.esteel.web.vo.ProvinceVo;
import com.esteel.web.vo.QueryPageVo;
import com.esteel.web.vo.ResultJson;
import com.google.gson.Gson;

/**
 * 企业相关模块
 * 
 * @author chenshouye
 *
 */
@Controller
@RequestMapping("/company")
public class MemberCompanyController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MemberClient memberClient;

	@Autowired
	BaseClient baseClient;

	@Autowired
	ContactClient contactClient;

	/**
	 * 跳转企业logo编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/editLogo")
	public String editLogo() {
		return "/";
	}

	@RequestMapping("/attest")
	public String sttest() {
		return "/member/attestation";
	}

	/**
	 * 跳转企业认证页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/company")
	public String findAddress(Model model) {
		// 所有身份信息
		List<ProvinceVo> province = baseClient.findAllPro();
		Assert.notNull(province, "获取所有省份失败");
		// 存放封装数据
		List<ResultJson> result = new ArrayList<>();
		ResultJson state = null;
		for (ProvinceVo provinceVo : province) {
			state = new ResultJson();
			// 取出省份的名称和id
			state.setText(provinceVo.getProvinceName());
			state.setValue(provinceVo.getProvinceId());
			state.setKey(provinceVo.getProvinceName());
			result.add(state);
		}
		Gson gson = new Gson();
		String jsonProvince = gson.toJson(result);
		model.addAttribute("provinces", jsonProvince);
		logger.info("findAddress:企业认证获取省份,结果+" + jsonProvince);
		return "/member/approve";
	}

	/**
	 * 获取省份下属所有城市
	 * 
	 * @param provinceId
	 * @return
	 */
	@RequestMapping("/findCity")
	@ResponseBody
	public WebReturnMessage findCity(int provinceId) {
		logger.info("findDistrict:获取省下属所有的市区，参数cityId" + provinceId);
		List<CityVo> Cities = baseClient.findAllCity(provinceId);
		Assert.notNull(Cities, "获取市下属所有的区县失败");
		// 存放封装数据
		List<ResultJson> city = new ArrayList<>();
		List<String> data = new ArrayList<>();
		ResultJson state = null;
		for (CityVo cityVo : Cities) {
			state = new ResultJson();
			state.setText(cityVo.getCityName());
			state.setValue(cityVo.getCityId());
			state.setKey(cityVo.getCityName());
			city.add(state);
		}
		data.add(new Gson().toJson(city));
		logger.info("findDistrict:获取省下属所有的市区,结果+" + data);
		return new WebReturnMessage(true, "", data);
	}

	/**
	 * 获取所有的区县
	 * 
	 * @param cityId
	 * @return
	 */
	@RequestMapping("/findDistrict")
	@ResponseBody
	public WebReturnMessage findDistrict(int cityId) {
		logger.info("findDistrict:获取市下属所有的区县，参数cityId" + cityId);
		List<DistrictVo> dists = baseClient.findAllDistrict(cityId);
		Assert.notNull(dists, "获取市下属所有的区县失败");
		// 存放封装数据
		List<ResultJson> city = new ArrayList<>();
		List<String> data = new ArrayList<>();
		ResultJson state = null;
		for (DistrictVo districtVo : dists) {
			state = new ResultJson();
			state.setText(districtVo.getDistrictName());
			state.setValue(districtVo.getCityId());
			state.setKey(districtVo.getDistrictName());
			city.add(state);
		}
		data.add(new Gson().toJson(city));
		logger.info("findDistrict:获取市下属所有的区县,结果+" + data);
		return new WebReturnMessage(true, "", data);
	}

	/**
	 * 企业认证
	 * 
	 * @return
	 */
	@RequestMapping(value = "/attest", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage companyAttest(

			String companyName, // 企业名称
			String companyshort, // 企业简称
			String provincesr, // 注册省份
			String cityr, // 注册城市
			String dictristor, // 注册区县
			String regAddress, // 注册详细地址
			String conAddress, // 联系地址
			// 待确认
			String agentName, // 代理人姓名**
			String agentCardId, // 代理人身份证号**
			String positive, // 代理人身份证正面**
			String opposite, // 代理人身份证号反面
			// ----------------
			String legalName, // 法人姓名 **

			String legalCardId, // 法人身份证号
			String legalPositive, // 法人身份证正面 **
			String legalOpposite, // 法人身份证号反面**

			String authorize, // 主联系人授权书
			Integer certificate, // 是否三证合一
			String regCode, // 营业执照注册号
			String organizationCode, // 组织结构代码
			String taxationCode, // 税务登记号

			String socialCode, // 社会信用代码

			String licenseImg, // 营业执照
			String organizationImg, // 组织机构
			String taxationImg // 税务登记证

	) {

		logger.info(this.getClass() + "企业认证入口,参数{}" + companyName + agentName);
		// 获取用户登录信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("companyAttest:登录用户信息Authentication " + authentication);
		MemberUserVo userVo = memberClient.checkNo(authentication.getName());
		Assert.notNull(userVo, "获取登录信息失败");
		// 设置企业基本信息
		MemberCompanyVo company = new MemberCompanyVo();
		company.setCountryCode("China"); // 国家编码
		company.setCompanyName(companyName);// 企业名称
		company.setCompanyAlias(companyshort);
		company.setContactAddress(conAddress);// 企业联系地址
		company.setCompanyNameEn(""); // --
		company.setSeats(5); // 企业子账号数
		company.setIsForeignTrade(0); // 是否有外贸资质
		company.setValidTime(new Timestamp(new Date().getTime() + 10 * 365 * 24 * 60 * 60 * 1000)); // 有效时间10年
		company.setRegisteredTime(new Timestamp(new Date().getTime())); // 注册时间
		company.setRegisteredUserId((int) userVo.getUserId());// --
		company.setRegisteredUser(userVo.getUserName()); // --
		company.setApprovalStatus(0);// 审核状态 0待审核 1驳回 2通过
		company.setCompanyStatus(1);// 状态 0正常(默认) 1审核 99锁定
		// 设置企业附件信息
		MemberCompanyAttachVo companyAtt = new MemberCompanyAttachVo();
		companyAtt.setRegisteredProvince(provincesr);// 省
		companyAtt.setRegisteredCity(cityr);// 市
		companyAtt.setRegisteredDistrict(dictristor);// 区县
		companyAtt.setRegisteredAddress(regAddress);// 联系地址
		companyAtt.setAuthorizationPath(authorize);// 主联系人授权书
		companyAtt.setIsThreeCertificate(certificate);// 三证合一
		companyAtt.setBusinessLicenseCode(regCode);// 营业执照号
		companyAtt.setSocialCreditCode(socialCode);// 统一社会信用号
		companyAtt.setOrganizationCode(organizationCode);// 组织结构代码
		companyAtt.setTaxRegistrationCode(taxationCode);// 税务登记证号
		companyAtt.setBusinessLicensePath(licenseImg);// 营业执照图片
		companyAtt.setOrganizationPath(organizationImg);// 组织结构图片
		companyAtt.setTaxRegistrationPath(taxationImg);// 税务登记证图片
		companyAtt.setUpdateTime(new Timestamp(new Date().getTime()));// 更新时间
		// 法人
		companyAtt.setLegalName(legalName); // 企业法人姓名
		companyAtt.setLegalIdCard(legalCardId);// 法人身份证号
		companyAtt.setLegalIdPath(legalPositive);// 业法人身份证附件正面
		companyAtt.setLegalIdPathO(legalOpposite); // 企业法人身份证附件反面
		// 代理人
		companyAtt.setAgentName(agentName);
		companyAtt.setAgentIdCard(agentCardId);
		companyAtt.setAgentIdPathP(positive);
		companyAtt.setAgentIdPathO(opposite);

		company.setComAtt(companyAtt);
		// 保存企业信息，同时返回保存后的对象信息
		int status = memberClient.saveCompanyInfo(company);
		if (status == 1) {
			List<Object> list = new ArrayList<>();
			list.add(1);
			return new WebReturnMessage(true, "", list);
		} else {
			return new WebReturnMessage(true, "数据保存失败");
		}
	}

	/**
	 * 企业外贸资质认证
	 * 
	 * @param license
	 *            外贸资质文件id
	 * @param englishName
	 *            英文名
	 * @return
	 */
	@RequestMapping(value = "/tradeAttest", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage tradeAttest(String license, String englishName) {
		logger.info("tradeAttest:企业外贸资质认证,参数{license,englishName}"+license+englishName);
		// 获取登录用户
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("tradeAttest:企业外贸资质认证,获取登录状态"+authentication);
		System.out.println(authentication.getName());
		MemberUserVo userVo = memberClient.checkNo(authentication.getName());
		Assert.notNull(userVo, "获取用户失败");
		//获取企业信息
		Integer companyId = userVo.getCompanyId();
		MemberCompanyVo company = memberClient.findCompany((long) companyId);
		logger.debug("tradeAttest:企业外贸资质认证,获取企业信息"+company);
		Assert.notNull(company, "获取企业失败");
		//设置企业信息
		company.setCompanyNameEn(englishName);// 设置英文名
		company.setLogo(license); // 外贸资质文件id
		company.setApprovalStatus(0);// 修改审核状态
		company.setIsForeignTrade(2); //审核状态
		//保存
		MemberCompanyVo saveComp = memberClient.saveComp(company);
		logger.debug("tradeAttest:企业外贸资质认证,保存企业信息"+saveComp);
		Assert.notNull(saveComp, "提交审核失败");
		logger.info("tradeAttest:企业外贸资质认证，结果返回new WebReturnMessage(true, \"1\");");
		return new WebReturnMessage(true, "1");
	}

	/**
	 * 根据企业id获取企业下属子账号
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findMembers", method = RequestMethod.POST)
	@ResponseBody
	public QueryPageVo memebers(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		logger.info("memebers:子账号列表，参数{pageNum,pageSize}" + pageNum);
		// 获取用户登录信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("addMember:登录用户信息Authentication " + authentication);
		MemberUserVo userVo = memberClient.checkNo(authentication.getName());
		Assert.notNull(userVo, "获取登录用户信息失败");
		// 获取企业id
		Integer companyId = userVo.getCompanyId();
		Long userId = userVo.getUserId();
		// 获取到企业子账号分页
		QueryPageVo findmember = memberClient.findmember(companyId, userId, pageNum - 1, pageSize);
		if (findmember == null) {
			return new QueryPageVo();
		}
		return findmember;
	}

	/**
	 * 添加企业子账号
	 * 
	 * @param mobile
	 * @param username
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addMember", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage addMember(String mobile, String userName) {
		logger.info("addMember:添加子账号,参数{}" + mobile + userName);
		// 获取用户登录信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("addMember:登录用户信息Authentication " + authentication);
		MemberUserVo userVo = memberClient.checkNo(authentication.getName());
		// 获取企业id
		Integer companyId = userVo.getCompanyId();
		// 创建保存信息
		MemberUserVo user = new MemberUserVo();
		user.setAccount(mobile);// 设置帐号
		user.setMobile(mobile);// 手机号
		// 设置密码
		Integer miMa = (int) ((Math.random() * 9 + 1) * 10000000); // 随机生成8位验证码
		String str = Encrypt.EncoderByMd5(miMa.toString());
		user.setPassword(str); // 设置密码
		user.setUserName(userName);
		user.setRegisteredTime(new Timestamp(System.currentTimeMillis())); // 注册时间
		user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
		user.setUserStatus(3); // 激活中
		user.setCompanyId(companyId);// 企业id
		user.setMemberName(mobile);
		user.setUserGrade(2);// 设置子账号权级 2 普通企业会员
		long time = new Date().getTime();
		user.setActivationTime(new Timestamp(time + 60 * 60 * 1000)); // 有效时间
		// 保存子账号信息
		MemberUserVo registerUser = memberClient.registerUser(user);
		Assert.notNull(registerUser, "添加子账号失败");
		// 发送短信
		MemberCompanyVo company = memberClient.findCompany((long) companyId);
		String msg = company.getCompanyName() + "申请添加您为该企业的子账号，请于1小时内使用密码" + miMa + "登录激活账号，1小时后该密码失效。";
		boolean sendSms = contactClient.sendSms(mobile, msg);
		logger.info("removeMember:添加子账号，发送短信状态" + sendSms);
		if (sendSms) {
			return new WebReturnMessage(true);
		} else {
			return new WebReturnMessage(true, "短信发送失败");
		}
	}

	/**
	 * 移除企业子账号
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/romoveMember", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage removeMember(Long userId) {
		logger.info("removeMember:移除子账号，参数{}" + userId);
		// 根据用户id获取帐号信息
		MemberUserVo member = memberClient.findUser(userId);
		logger.debug("removeMember:移除子账号" + member);
		Assert.notNull(member, "无此帐号");
		// 设置用户信息
		member.setCompanyId(null);// 设置企业号为null
		member.setUserStatus(0);
		member.setUserGrade(0);
		member.setMemberName(" ");
		member.setPositon(" ");
		member.setDept(" ");
		// 保存
		MemberUserVo memuser = memberClient.registerUser(member);
		logger.debug("removeMember:去除子账号企业信息" + member);
		Assert.notNull(memuser, "移除帐号失败");
		logger.info("removeMember:移除子账号" + new WebReturnMessage(true, "1"));
		return new WebReturnMessage(true, "1");
	}

	/**
	 * 修改子账号信息
	 * 
	 * @param userId
	 *            子账号id
	 * @param mobile
	 *            手机
	 * @param userName
	 *            姓名
	 * @param dept
	 *            部门
	 * @param position
	 *            职位
	 * @param email
	 *            邮箱
	 * @return
	 */
	@RequestMapping(value = "/updMember", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage editMember(Long userId, String userName, String dept, String position) {
		logger.info("editMember:修改子账号信息，参数{}" + userId);
		// 获取子账号对象
		MemberUserVo member = memberClient.findUser(userId);
		logger.debug("editMember:获取子账号信息，MemberUserVo：" + member);
		Assert.notNull(member, "无此帐号");
		member.setUserName(userName);
		member.setDept(dept);
		member.setPositon(position);
		// 更新用户信息
		MemberUserVo registerUser = memberClient.registerUser(member);
		logger.debug("editMember:保存子账号信息，user：" + registerUser);
		Assert.notNull(registerUser, "修改失败");
		logger.info("removeMember:修改子账号子账号" + new WebReturnMessage(true, "1"));
		return new WebReturnMessage(true, "1");
	}

	/**
	 * 重新添加子账号，重新发送密码短信
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/reAddUser", method = RequestMethod.POST)
	@ResponseBody
	public WebReturnMessage reSendMsg(Long userId) {
		logger.info("reSendMsg:重新发送激活短信，参数{}" + userId);
		// 获取子账号对象
		MemberUserVo member = memberClient.findUser(userId);
		logger.debug("reSendMsg:获取子账号信息，MemberUserVo：" + member);
		Assert.notNull(member, "无此帐号");
		// 设置用户信息
		long times = new Date().getTime();
		Integer miMa = (int) ((Math.random() * 9 + 1) * 10000000); // 随机生成8位验证码
		String str = Encrypt.EncoderByMd5(miMa.toString());
		member.setPassword(str); // 设置密码
		member.setActivationTime(new Timestamp(times + 60 * 60 * 1000));// 1小时有效
		// 保存子账号信息
		MemberUserVo registerUser = memberClient.registerUser(member);
		logger.debug("reSendMsg:保存子账号信息，registerUser：" + registerUser);
		Assert.notNull(registerUser, "重新添加失败");
		// 根据用户获取企业信息
		long companyId = member.getCompanyId();
		MemberCompanyVo company = memberClient.findCompany(companyId);
		logger.debug("reSendMsg:获取用户所在企业信息，company：" + company);
		String msg = company.getCompanyName() + "申请添加您为该企业的子账号，请于1小时内使用密码" + miMa + "登录激活账号，1小时后该密码失效。";
		// 发送短信
		boolean sendSms = contactClient.sendSms(member.getMobile(), msg);
		logger.info("removeMember:添加子账号，发送短信状态" + sendSms);
		if (sendSms) {
			return new WebReturnMessage(true, "1");
		} else {
			return new WebReturnMessage(true, "重新添加失败，请重试");
		}

	}

}
