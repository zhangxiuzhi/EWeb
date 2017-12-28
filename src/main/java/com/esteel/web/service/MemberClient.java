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
import com.esteel.web.vo.QueryPageVo;

/**
 * 服务调用
 * 
 * @author chenshouye
 *
 */
@FeignClient(name = "User", url = "http://127.0.0.1:9930/",fallback = MemberUserClientCallback.class)
//@FeignClient(name = "User",url = "http://10.0.1.234:9930",fallback = MemberUserClientCallback.class)
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
	 * 注册用户和保存用户信息共用
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
	public MemberUserVo checkUserByNo(@RequestBody MemberUserVo user);
	
	/**
	 * 保存企业认证信息
	 * @param company
	 * @param comAtt
	 * @return int  0:保存失败，1:保存成功
	 */
	@RequestMapping(value = "/saveCompanyInfo", method = RequestMethod.POST)
	public int saveCompanyInfo(@RequestBody MemberCompanyVo company);
	/**
	 * 保存企业主表信息
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
	public MemberCompanyVo findCompany(@RequestParam("companyId") Long companyId);
	/**
	 * 根据企业id获取企业信息---分页
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/findMembersPage", method = RequestMethod.POST)
	public QueryPageVo findmember(
			@RequestParam("companyId") Integer companyId,
			@RequestParam("userId") Long userId,
			@RequestParam("page") Integer page,
			@RequestParam("size") Integer size);
	/**
	 * 根据企业id获取子账号
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/findMembers", method = RequestMethod.POST)
	public List<MemberUserVo> findmembers(@RequestParam("companyId") int companyId);
	/**
	 * 根据用户的id获取用户对象
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/findUserById", method = RequestMethod.POST)
	public MemberUserVo findUser(@RequestParam("userId") Long userId);
	/**
	 * 根据用户的帐号获取用户对象
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/findByAccount", method = RequestMethod.POST)
	public MemberUserVo findByAccount(@RequestParam("account") String account);
	
	/**
	 * 根据用户的帐号获取用户对象
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/findByUserName", method = RequestMethod.POST)
	public MemberUserVo findByUserName(@RequestParam("userName") String userName);
	/**
	 * 根据用户名和id查找对象
	 * @param memberName
	 * @return
	 */
	@RequestMapping(value = "/findUserByIdName", method = RequestMethod.POST)
	public MemberUserVo findByMemberNameId(@RequestParam("userId") int userId,@RequestParam("memberName") String memberName);
	/**
	 * 根据用户名查找对象
	 * @param memberName
	 * @return
	 */
	@RequestMapping(value = "/findByMemberName", method = RequestMethod.POST)
	public MemberUserVo findByMemberName(@RequestParam("memberName") String memberName);
	
	/**
	 * 根据企业名称获取企业对象
	 * @param companyName
	 * @return
	 */
	@RequestMapping(value = "/findByComName", method = RequestMethod.POST)
	public MemberCompanyVo findByCompanyName(@RequestParam("companyName") String companyName);
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
		logger.warn("checkNo:验收手机号验证失败");
		return null;
	}
	/**
	 * 用户注册调用失败
	 */
	@Override
	public MemberUserVo registerUser(MemberUserVo user) {
		logger.warn(this.getClass()+":用户注册调用失败");
		return null;
	}
	/**
	 * 保存企业用户失败返回null
	 */
	@Override
	public MemberCompanyVo saveComp(MemberCompanyVo user) {
		logger.warn("saveComp:保存企业主表信息调用失败");
		return null;
	}
	/**
	 * 失败返回null
	 */
	@Override
	public MemberCompanyAttachVo saveComAtt(MemberCompanyAttachVo comAtt) {
		logger.warn("saveComAtt:保存企业信息调用失败");
		return null;
	}
	/*
	 * 失败返回null
	 * @see com.esteel.web.service.MemberClient#findCompany(long)
	 */
	@Override
	public MemberCompanyVo findCompany(Long comanyId) {
		logger.warn("findCompany:根据企业id信息调用失败");
		return null;
	}
	/*
	 * 调用失败了返回空
	 * @see com.esteel.web.service.MemberClient#findUser(long)
	 */
	@Override
	public MemberUserVo findUser(Long userId) {
		logger.warn("findUser:根据用户id用户信息调用失败");
		return null;
	}
	/**
	 * 根据用户帐号获取用户信息失败返回null
	 */
	@Override
	public MemberUserVo findByAccount(String account) {
		logger.warn("findByAccount：根据用户帐号获取用户信息失败");
		return null;
	}
	/**
	 * 失败返回执行
	 */
	@Override
	public MemberUserVo findByUserName(String userName) {
		logger.warn("findByUserName：根据用户信息查询失败");
		return null;
	}
	/**
	 * 根据企业名称查询企业信息失败返回null
	 */
	@Override
	public MemberCompanyVo findByCompanyName(String companyName) {
		logger.warn("findByCompanyName：企业认证信息保存失败");
		return null;
	}
	/**
	 * 企业认证保存信息
	 *  0:保存失败，1:保存成功
	 *  调用失败返回
	 */
	@Override
	public int saveCompanyInfo(@RequestBody MemberCompanyVo company) {
		logger.warn("saveCompanyInfo：企业认证信息保存失败");
		return 0;
	}
	/**
	 * 子账号
	 */
	@Override
	public List<MemberUserVo> findmembers(int companyId) {
		logger.warn("findmembers：获取企业下属子账号");
		return null;
	}
	/**
	 * 子账号分页
	 */
	@Override
	public QueryPageVo findmember(Integer companyId, Long userId, Integer page, Integer size) {
		logger.warn("findmember：获取企业下属子账号分页失败");
		return null;
	}
	/**
	 * 根据id和用户名查询用户信息
	 */
	@Override
	public MemberUserVo findByMemberNameId(int userId, String memberName) {
		logger.warn("findByMemberNameId：根据id和用户名查询用户信息失败");
		return null;
	}
	@Override
	public MemberUserVo findByMemberName(String memberName) {
		logger.warn("findByMemberName：根据用户名查询用户信息失败");
		return null;
	}
	/**
	 * 手机验证
	 */
	@Override
	public MemberUserVo checkUserByNo(MemberUserVo user) {
		logger.warn("checkUserByNo：手机验证调用失败");
		return null;
	}
	
	
	
	

}