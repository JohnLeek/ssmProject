package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;

public interface StaticPageService {
	TaotaoResult genItemHtml(Long itemId) throws Exception;
}
