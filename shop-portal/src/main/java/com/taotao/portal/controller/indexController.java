package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.service.ContentService;

/**
 * <p>
 * Description:访问首页的controller
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年9月25日 下午7:58:49
 */
@Controller
public class indexController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		String json = contentService.getAd1List();
		model.addAttribute("ad1", json);
		return "index";
	}

	@RequestMapping(value = "/posttest", method = RequestMethod.POST)
	@ResponseBody
	public String postTest(String name, String pass) {
		System.out.println(name);
		System.out.println(pass);
		return "ok";
	}
}
