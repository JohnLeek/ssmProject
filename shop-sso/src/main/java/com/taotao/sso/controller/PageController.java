package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * Description:展示登录注册页面
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年10月22日 下午2:16:21
 */
@Controller
public class PageController {
	// 登录界面
	@RequestMapping("/page/login")
	public String showLogin(String redirectURL, Model model) {
		model.addAttribute("redirect", redirectURL);
		return "login";
	}

	// 注册
	@RequestMapping("/page/register")
	public String showRegister() {
		return "register";
	}
}
