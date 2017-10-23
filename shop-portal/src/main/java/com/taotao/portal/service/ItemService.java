package com.taotao.portal.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	TbItem getTbItemById(Long itemId);
	String getTbDescById(Long itemId);
	String getTbItemParamItemById(Long itemId);
}
