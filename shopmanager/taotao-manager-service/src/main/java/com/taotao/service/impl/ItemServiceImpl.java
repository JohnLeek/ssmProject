package com.taotao.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	// 注入mapper
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItemById(long itemId) {
		// TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample tbItemExample = new TbItemExample();
		// 添加查询条件
		Criteria criteria = tbItemExample.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(tbItemExample);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	// 商品列表查询
	@Override
	public EUDateGridResult getItemList(int page, int rows) {
		TbItemExample tbItemExample = new TbItemExample();
		// 没有查询条件直接分页
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(tbItemExample);
		EUDateGridResult euDateGridResult = new EUDateGridResult();
		// 取显示的商品集合
		euDateGridResult.setRows(list);
		// 设置总数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		euDateGridResult.setTotal(pageInfo.getTotal());
		return euDateGridResult;
	}

	@Override
	public TaotaoResult createItem(TbItem tbItem, String desc, String itemParam) {
		// 商品id
		Long itemId = IDUtils.genItemId();
		tbItem.setId(itemId);
		// 补全item的信息
		// 商品状态商品状态，1-正常，2-下架，3-删除',
		tbItem.setStatus((byte) 1);
		// 创建时间 更新时间
		java.util.Date date = new java.util.Date();
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		// 插入数据库
		itemMapper.insert(tbItem);
		// 商品描述表
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDesc.setItemId(itemId);
		// 插入数据库
		itemDescMapper.insert(itemDesc);
		// 添加商品规格参数
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		// 插入数据库
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}

	@Override
	public String getItemParamHtml(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list == null && list.isEmpty()) {
			return "";
		}
		// 取规格参数信息
		TbItemParamItem itemParamItem = list.get(0);
		// 取json数据
		String paramData = itemParamItem.getParamData();
		// 转化成java对象
		List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
		// 遍历list生成html
		StringBuffer sb = new StringBuffer();
		sb.append("<table>\n");
		sb.append("  <tbody>\n");
		for (Map map : mapList) {
			sb.append("     <tr>\n");
			sb.append("    <th>"+map.get("group")+"</th>\n");
			sb.append("  </tr> \n");
			//取规格项
			List<Map>mapList2 = (List<Map>) map.get("params");
			for(Map map2:mapList2){
				sb.append("  <tr>\n");
				sb.append("    <td>"+map2.get("k")+"</td>\n");
				sb.append("    <td>"+map2.get("v")+"</td>\n");
				sb.append("  </tr>\n");
			}
		}
		sb.append("  </tbody>\n");
		sb.append("</table>");
		return sb.toString();
	}

}
