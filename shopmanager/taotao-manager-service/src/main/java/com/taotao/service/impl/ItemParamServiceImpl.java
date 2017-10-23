package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

/**
 * <p>
 * Description: 商品规格参数模板管理service
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年9月24日 下午7:30:02
 */

@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		TaotaoResult result = new TaotaoResult();
		// 根据cid查询规格参数
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if (list.size() > 0 && list != null) {
			TbItemParam itemParam = list.get(0);
			return TaotaoResult.ok(itemParam);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertItemParam(Long cid, String paramData) {
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(paramData);
		tbItemParam.setCreated(new Date());
		tbItemParam.setUpdated(new Date());
		//插入记录
		itemParamMapper.insert(tbItemParam);
		return TaotaoResult.ok();
	}

}
