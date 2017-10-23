package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

public interface ItemService {
	TbItem getItemById(Long id);
	TbItemDesc getItemDescById(Long id);
	TbItemParamItem getItemParamItem(Long id);
}
