package com.taotao.service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(long itemId);
	EUDateGridResult getItemList(int page,int rows);
	TaotaoResult createItem(TbItem tbItem,String desc,String itemParam);
	String getItemParamHtml(Long itemId);
}
