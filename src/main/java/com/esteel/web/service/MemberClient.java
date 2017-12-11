package com.esteel.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.esteel.web.vo.MemberCompanyAttachVo;
import com.esteel.web.vo.MemberCompanyVo;
import com.esteel.web.vo.MemberUserVo;

/**
 * 服务调用
 * 
 * @author chenshouye
 *
 */
// ,url = "http://127.0.0.1:9000"
@FeignClient(name = "User", url = "http://127.0.0.1:9930/", fallback = MemberUserClientCallback.class)
public interface MemberClient {
	/**
	 * 验证手机号
	 * 
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/checkNo", method = RequestMethod.POST)
	public MemberUserVo checkNo(@RequestParam("mobile") String mobile);
	/**
	 * 注册用户
	 * @param user
	 */
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public MemberUserVo registerUser(@RequestBody MemberUserVo user);
	/**
	 * 手机验证
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
	public MemberUserVo checkNo(@RequestBody MemberUserVo user);
	
	/**
	 * 保存企业信息
	 * @param company
	 * @return
	 */
	@RequestMapping(value = "/saveCompany", method = RequestMethod.POST)
	public MemberCompanyVo saveComp(@RequestBody MemberCompanyVo company);
	
	/**
	 * 保存企业附表信息
	 */
	@RequestMapping(value = "/saveCompanyAtt", method = RequestMethod.POST)
	public MemberCompanyAttachVo saveComAtt(@RequestBody MemberCompanyAttachVo comAtt);
	/**
	 * 根据企业id获取企业信息
	 * @param comanyId
	 * @return
	 */
	@RequestMapping(value = "/findCompanyById", method = RequestMethod.POST)
	public MemberCompanyVo findCompany(long comanyId);
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/findMembers", method = RequestMethod.POST)
	public List<MemberUserVo> findmembers(int companyId);
	
	@RequestMapping(value = "/findUser", method = RequestMethod.POST)
	public MemberUserVo findUser(long userId);
}

/**
 * 调用失败处理
 * 
 * @author chenshouye
 *
 */
@Component
class MemberUserClientCallback implements MemberClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 验收手机号验证失败
	 */
	@Override
	public MemberUserVo checkNo(String mobile) {
		return null;
	}
	/**
	 * 用户注册调用失败
	 */
	@Override
	public MemberUserVo registerUser(MemberUserVo user) {
		logger.debug(this.getClass()+":用户注册调用失败");
		return null;
	}
	/**
	 * 验证用户
	 */
	@Override
	public MemberUserVo checkNo(MemberUserVo user) {
		return null;
	}
	/**
	 * 保存企业用户失败返回null
	 */
	@Override
	public MemberCompanyVo saveComp(MemberCompanyVo user) {
		return null;
	}
	/**
	 * 失败返回null
	 */
	@Override
	public MemberCompanyAttachVo saveComAtt(MemberCompanyAttachVo comAtt) {
		return null;
	}
	/*
	 * 失败返回null
	 * @see com.esteel.web.service.MemberClient#findCompany(long)
	 */
	@Override
	public MemberCompanyVo findCompany(long comanyId) {
		return null;
	}
	/**
	 * 调用失败
	 */
	@Override
	public List<MemberUserVo> findmembers(int companyId) {
		return null;
	}
	/*
	 * 调用失败了返回空
	 * @see com.esteel.web.service.MemberClient#findUser(long)
	 */
	@Override
	public MemberUserVo findUser(long userId) {
		return null;
	}

}