package com.esteel.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.esteel.web.vo.LogVerifyCodeVo;

@FeignClient(name = "User", url = "http://127.0.0.1:9930/", fallback = LogVerityCodeClientCallback.class)
public interface LogVerityCodeClient {
	/**
	 * 保存验证码
	 * @param logCode
	 */
	@RequestMapping(value = "/saveCode", method = RequestMethod.POST)
	public LogVerifyCodeVo saveLog(@RequestBody LogVerifyCodeVo logCode);
	
	/**
	 * 检查验证码是否匹配
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.POST)
	public LogVerifyCodeVo checkCode(@RequestParam("code") String code,@RequestParam("target") String target); 
	
}

@Component
class LogVerityCodeClientCallback implements LogVerityCodeClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 调用保存验证码错误
	 */
	@Override
	public LogVerifyCodeVo saveLog(@RequestBody LogVerifyCodeVo logCode) {
		System.out.println("服务调用失败");
		logger.debug("错误位置：LogVerityCodeClientCallback,错误内容:服务调用失败");
		return null;
	}
	/**
	 * 调用失败返回空
	 */
	@Override
	public LogVerifyCodeVo checkCode(String code, String mobile) {
		return null;
	}
	
}