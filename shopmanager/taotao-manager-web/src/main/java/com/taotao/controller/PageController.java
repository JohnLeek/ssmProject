package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//页面跳转
@Controller
public class PageController {
	// 标签的意义是放问根路径就打开首页
	@RequestMapping("/")
	public String showIndex() {

		return "index";
	}

	// 展示其他页面
	@RequestMapping("/{page}")
	public String showpage(@PathVariable String page) {
		return page;
	}

}
