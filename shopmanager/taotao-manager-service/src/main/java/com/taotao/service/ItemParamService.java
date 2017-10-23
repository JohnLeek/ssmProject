package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {
	TaotaoResult getItemParamByCid(Long cid);
	TaotaoResult insertItemParam(Long cid,String paramData);
}
