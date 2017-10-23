package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.HttpClientUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService{
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	@Value("${REST_CONTENT_AD1_CID}")
	private String REST_CONTENT_AD1_CID;
	//大广告位
	@Override
	public String getAd1List() {
		//调用rest服务
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_URL+REST_CONTENT_AD1_CID);
		//将json转化成java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToList(json, TbContent.class);
		//取data属性内容列表
		List<TbContent>contentList = (List<TbContent>) taotaoResult.getData();
		//把内容转化成AdNode
		List<AdNode>resultList = new ArrayList<>();
		for(TbContent tbContent:contentList){
			AdNode node = new AdNode();
			node.setHight(240);
			node.setWidth(680);
			node.setSrc(tbContent.getPic());
			
			node.setHightB(240);
			node.setWidthB(550);
			node.setSrc(tbContent.getPic2());
			
			node.setAlt(tbContent.getSubTitle());
			node.setHerf(tbContent.getUrl());
			resultList.add(node);
		}
		//将resultList转化成json对象
		String resultJson = JsonUtils.objectToJson(resultList);
		return resultJson;
	}

}
