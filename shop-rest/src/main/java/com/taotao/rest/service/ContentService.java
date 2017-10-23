package com.taotao.rest.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	List<TbContent> getContentList(Long cid);
	TaotaoResult syncContent(Long cid);
}
