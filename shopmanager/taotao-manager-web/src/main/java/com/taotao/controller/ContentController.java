package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.HttpClientUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

/**<p>Description:内容管理controller</p>
 *@author: Li Yinqiang
 *@date 2017年10月5日 下午1:24:00  
 */
 
@Controller
@RequestMapping("/content")
//插入数据
public class ContentController {
	@Autowired
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	private ContentService contentService;
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent tbContent) {
		TaotaoResult result = contentService.insertContentService(tbContent);
		//调用服务同步缓存
		HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+tbContent.getCategoryId());
		return result;
	}
}
