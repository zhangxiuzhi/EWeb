package com.esteel.web.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esteel.common.controller.WebReturnMessage;
import com.esteel.web.service.BaseClient;
import com.esteel.web.service.MemberClient;
import com.esteel.web.vo.CityVo;
import com.esteel.web.vo.DistrictVo;
import com.esteel.web.vo.Encrypt;
import com.esteel.web.vo.MemberCompanyAttachVo;
import com.esteel.web.vo.MemberCompanyVo;
import com.esteel.web.vo.MemberUserVo;
import com.esteel.web.vo.ProvinceVo;
import com.esteel.web.vo.ResultJson;
import com.google.gson.Gson;

@Controller
@RequestMapping("/company")
public class MemberCompanyController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	MemberClient memberClient;

	@Autowired
	BaseClient baseClient;

	/**
	 * 跳转企业logo编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/editLogo")
	public String editLogo() {
		return "/";
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
		List<CityVo> Cities = baseClient.findAllCity(provinceId);
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
		List<DistrictVo> dists = baseClient.findAllDistrict(cityId);
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
		return new WebReturnMessage(true, "", data);
	}

	/**
	 * 企业认证
	 * 
	 * @return
	 */
	@RequestMapping(value="/attest")
	@ResponseBody
	public String companyAttest(

			String companyName, // 企业名称
			String regProvince, // 注册省份
			String regCity, // 注册城市
			String regDistinct, // 注册区县
			String regAddress, // 注册详细地址
			String conAddress, // 联系地址
			// 待确认
			String agentName, // 代理人姓名
			String agentCardId, // 代理人身份证号
			String positive, // 代理人身份证正面
			String opposite, // 代理人身份证号反面
			// ----------------
			String legalName, // 法人姓名 ---
			String legalCardId, // 法人身份证号
			String legalPositive, // 法人身份证正面 --
			String legalOpposite, // 法人身份证号反面

			String authorize, // 主联系人授权书
			Integer certificate, // 是否三证合一
			String regCode, // 营业执照注册号
			String socialCode, // 社会信用代码
			String organizationCode, // 组织结构代码
			String taxationCode, // 税务登记号

			String licenseImg, // 营业执照
			String organizationImg, // 组织机构
			String taxationImg // 税务登记证

	) {
		System.out.println("*********************************************************");
		System.out.println(companyName);
		// 设置企业基本信息
		MemberCompanyVo company = new MemberCompanyVo();
		company.setCountryCode("China"); // 国家编码
		company.setCompanyName(companyName);// 企业名称
		company.setContactAddress(conAddress);// 企业联系地址
		company.setCompanyNameEn(""); // --
		company.setSeats(5); // 企业子账号数
		company.setIsForeignTrade(0); // 是否有外贸资质
		company.setValidTime(new Timestamp(new Date().getTime() + 10 * 365 * 24 * 60 * 60 * 1000)); // 有效时间10年
		company.setRegisteredTime(new Timestamp(new Date().getTime())); // 注册时间
		company.setRegisteredUserId(1);// --
		company.setRegisteredUser(""); // --
		company.setApprovalStatus(0);// 审核状态
		company.setCompanyStatus(0);// 企业状态
		// 设置企业附件信息
		MemberCompanyAttachVo companyAtt = new MemberCompanyAttachVo();
		companyAtt.setRegisteredProvince(regProvince);// 省
		companyAtt.setLegalIdCard(legalCardId);// 法人身份证号
		companyAtt.setLegalIdPath(opposite);// 身份证号图片
		companyAtt.setRegisteredCity(regCity);// 市
		companyAtt.setRegisteredDistrict(regDistinct);// 区县
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
		// 保存企业信息，同时返回保存后的对象信息
		MemberCompanyVo saveComp = memberClient.saveComp(company);
		WebReturnMessage webRetMesage = null;
		if (saveComp != null) {
			companyAtt.setCompanyId((int) saveComp.getCompanyId());
			// 保存企业信息，同时返回保存后的对象信息
			MemberCompanyAttachVo saveComAtt = memberClient.saveComAtt(companyAtt);
			if (saveComAtt != null) {
				webRetMesage = new WebReturnMessage(true, "提交成功");
			} else {
				webRetMesage = new WebReturnMessage(false, "提交失败");
			}
		} else {
			webRetMesage = new WebReturnMessage(false, "提交失败");
		}
		return "";

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
	@RequestMapping(value = "/updtPwd", method = RequestMethod.GET)
	@ResponseBody
	public WebReturnMessage trade(String license, String englishName, HttpSession session) {
		WebReturnMessage webRetMesage = null;
		// 根据登录用户获取到企业id查询企业信息
		MemberUserVo user = (MemberUserVo) session.getAttribute("userVo");
		Integer companyId = user.getCompanyId();
		MemberCompanyVo company = memberClient.findCompany(companyId);
		if (company != null) {
			company.setCompanyNameEn(englishName);// 设置英文名
			company.setLogo(license); // 外贸资质文件id
			company.setApprovalStatus(0);// 修改审核状态
			MemberCompanyVo saveComp = memberClient.saveComp(company);
			if (saveComp != null) {
				webRetMesage = new WebReturnMessage(true, "已提交");
			} else {
				webRetMesage = new WebReturnMessage(false, "提交审核失败");
			}
		} else {
			webRetMesage = new WebReturnMessage(false, "提交审核失败");
		}
		return webRetMesage;
	}

	/**
	 * 根据企业id获取企业下属子账号
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findMembers", method = RequestMethod.GET)
	public List<MemberUserVo> memebers(HttpSession session) {
		// 根据登录用户获取到企业id查询企业信息
		MemberUserVo user = (MemberUserVo) session.getAttribute("userVo");
		Integer companyId = user.getCompanyId();
		List<MemberUserVo> members = memberClient.findmembers(companyId);
		return members;
	}

	/**
	 * 添加企业子账号
	 * 
	 * @param mobile
	 * @param username
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addMember", method = RequestMethod.GET)
	@ResponseBody
	public WebReturnMessage addMember(String mobile, String username, HttpSession session) {
		WebReturnMessage webRetMesage = null;
		// 获取企业id
		MemberUserVo userVo = (MemberUserVo) session.getAttribute("userVo");
		Integer companyId = userVo.getCompanyId();
		// 创建保存信息
		MemberUserVo user = new MemberUserVo();
		user.setAccount("E0000062");// 设置帐号
		user.setMobile(mobile);// 手机号
		String str = Encrypt.EncoderByMd5("123456");
		user.setPassword(str); // 设置密码
		user.setUserStatus(1); // 认证
		user.setCompanyId(companyId);// 企业id
		user.setUserGrade(2);// 设置子账号权级 2 普通企业会员
		// 保存子账号信息
		MemberUserVo registerUser = memberClient.registerUser(user);
		if (registerUser != null) {
			webRetMesage = new WebReturnMessage(true, "添加成功");
		} else {
			webRetMesage = new WebReturnMessage(false, "添加失败");
		}
		return webRetMesage;
	}

	/**
	 * 移除企业子账号
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/romoveMember", method = RequestMethod.GET)
	@ResponseBody
	public WebReturnMessage removeMember(Long userId) {
		WebReturnMessage webRetMesage = null;
		MemberUserVo member = memberClient.findUser(userId);
		if (member != null) {
			member.setCompanyId(null);// 设置企业号为null
			MemberUserVo memuser = memberClient.registerUser(member);
			if (memuser != null) {
				webRetMesage = new WebReturnMessage(true, "移除成功");
			} else {
				webRetMesage = new WebReturnMessage(false, "保存失败");
			}
		} else {
			webRetMesage = new WebReturnMessage(false, "获取子账号信息失败");
		}
		return webRetMesage;
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
	@RequestMapping(value = "/Member", method = RequestMethod.GET)
	@ResponseBody
	public WebReturnMessage editMember(Long userId, String mobile, String userName, String dept, String position,
			String email) {
		WebReturnMessage webRetMesage = null;
		// 获取子账号对象
		MemberUserVo member = memberClient.findUser(userId);
		if (member != null) {
			// 更改信息
			member.setMobile(mobile);
			member.setUserName(userName);
			member.setEmail(email);
			// 更新用户信息
			MemberUserVo user = memberClient.registerUser(member);
			if (user != null) {
				webRetMesage = new WebReturnMessage(true, "更改成功");
			} else {
				webRetMesage = new WebReturnMessage(false, "更改失败");
			}
		} else {
			webRetMesage = new WebReturnMessage(false, "无此帐号");
		}
		return webRetMesage;
	}
}
