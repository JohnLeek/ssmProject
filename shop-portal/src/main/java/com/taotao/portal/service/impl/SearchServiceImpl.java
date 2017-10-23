package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;


import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.HttpClientUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService{
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	//搜搜服务的实现方法
	@Override
	public SearchResult search(String keyword, int page, int rows) {
		Map<String, String>param = new HashMap<>();
		param.put("keyword", keyword);
		param.put("page", page+"");
		param.put("rows", rows+"");
		//调用请求得到返回结果
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
		//将返回结果转化成java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
		//取数据
		SearchResult result = (SearchResult) taotaoResult.getData();
		return result;
	}

}
