package com.taotao.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
/*code is far away from bug with the animal protecting 
 *  ┏┓　　　┏┓ 
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　 
 *┃　　　━　　　┃ 
 *┃　┳┛　┗┳　┃ 
 *┃　　　　　　　┃ 
 *┃　　　┻　　　┃ 
 *┃　　　　　　　┃ 
 *┗━┓　　　┏━┛ 
 *　　┃　　　┃神兽保佑 
 *　　┃　　　┃代码无BUG！ 
 *　　┃　　　┗━━━┓ 
 *　　┃　　　　　　　┣┓ 
 *　　┃　　　　　　　┏┛ 
 *　　┗┓┓┏━┳┓┏┛ 
 *　　　┃┫┫　┃┫┫ 
 *　　　┗┻┛　┗┻┛  
 *　　　 
 */  

@Service
public class ItemCatServiceImpl implements ItemCatService {

	//根据parentId查询节点的方法
	//注入mapper
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public List<EUTreeNode> getItemCatList(long parentId) {
		//根据parentId查询列表
		TbItemCatExample example = new TbItemCatExample();
		//查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//转化成EUTreeNode 的类型
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

}
