package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	List<EUTreeNode> getContentCatList(Long parentId);
	TaotaoResult insertCategory(Long patentId,String name);
	void updateContenCatgory(Long id,String name);
	//void deleteContenCategory(Long parentId,Long id);
}
