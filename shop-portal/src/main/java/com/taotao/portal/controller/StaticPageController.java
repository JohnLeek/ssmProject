package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.pojo.ExceptionUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.service.StaticPageService;

/**<p>Description:生成静态页面</p>
 *@author: Li Yinqiang
 *@date 2017年10月20日 下午6:48:04  
 */
 
@Controller

public class StaticPageController {
	@Autowired
	private StaticPageService staticPageService;
	@RequestMapping("/gen/item/{itemId}")
	public TaotaoResult genItemHtml(@PathVariable Long itemId) {
		try {
			return staticPageService.genItemHtml(itemId);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}

}
