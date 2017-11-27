package com.esteel.web.service;

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

    @Override
    public boolean sendSms(String mobile, String sms) {
        return false;
    }

    @Override
    public boolean sendMail(String sendTo, String subject, String htmlMessage) {
        return false;
    }
}