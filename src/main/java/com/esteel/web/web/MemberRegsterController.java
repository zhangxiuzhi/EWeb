package com.esteel.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ESTeel Description: 报盘用controller User: zhangxiuzhi Date: 2017-11-21 Time:
 * 13:49
 */
@RequestMapping("/register")
@Controller
public class MemberRegsterController {

	@RequestMapping("/")
	public String addUI(Model model) {
		return "/register/register";
	}

	@RequestMapping("/success")
	public String success(Model model) {
		return "/register/success";
	}

}
