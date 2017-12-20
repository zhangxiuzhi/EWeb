package com.esteel.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-24
 * Time: 18:18
 */
@FeignClient(name = "Contact",fallback = ContactClientFallback.class)
public interface ContactClient {

    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    public boolean sendSms(@RequestParam("mobile")String mobile, @RequestParam("sms") String sms);

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public boolean sendMail(@RequestParam("sendTo")String sendTo,@RequestParam("subject")String subject,@RequestParam("htmlMessage")String htmlMessage);
}

@Component
class ContactClientFallback implements ContactClient{

	Logger logger = LoggerFactory.getLogger(ContactClientFallback.class);
	
    @Override
    public boolean sendSms(String mobile, String sms) {
    	logger.info("sendSms Error  mobile:{} sms:{}",mobile,sms);
        return false;
    }

    @Override
    public boolean sendMail(String sendTo, String subject, String htmlMessage) {
    	System.out.println("失败调用了");
    	logger.info("sendMail Error  mobile:{} sms:{}",sendTo,subject,htmlMessage);
        return false;
    }
}