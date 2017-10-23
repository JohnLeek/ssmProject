package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * <p>
 * Description:商品分类列表查询
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年10月1日 下午8:28:21
 */

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public ItemCatResult getItemCatList() {

		List list = getItemCatList(0l);
		ItemCatResult result = new ItemCatResult();
		result.setData(list);
		return result;
	}

	private List getItemCatList(Long parentId) {
		// 查询
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List resultList = new ArrayList<>();
		int index = 0;
		for (TbItemCat tbItemCat : list) {
			//
			if (index >= 14) {
				break;
			}
			// 如果是父节点
			if (tbItemCat.getIsParent()) {
				CatNode node = new CatNode();
				node.setUrl("/products/" + tbItemCat.getId() + ".html");
				// 一级节点
				if (tbItemCat.getParentId() == 0) {
					node.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
					// 一级节点不能超过14个
					index++;
				} else {
					node.setName(tbItemCat.getName());
				}
				node.setItems(getItemCatList(tbItemCat.getId()));
				// 把node添加到resultList
				resultList.add(node);
			} else {
				// 叶子节点
				String item = "/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
				resultList.add(item);
			}
		}
		return resultList;

	}
}
