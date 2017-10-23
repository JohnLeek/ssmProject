package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentCategoryService;
/**<p>Description:内容分类管理service</p>
 *@author: Li Yinqiang
 *@date 2017年10月2日 下午3:56:55  
 */
 
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EUTreeNode> getContentCatList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory>list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for(TbContentCategory tbContentCategory:list){
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}
	@Override
	public TaotaoResult insertCategory(Long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		tbContentCategory.setStatus(1);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setSortOrder(1);
		//插入数据
		contentCategoryMapper.insert(tbContentCategory);
		//取返回的主键
		Long id = tbContentCategory.getId();
		//判断父节点的isParent属性
		//查询父节点
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()){
			parentNode.setIsParent(true);
			//更新
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		//返回主键
		return TaotaoResult.ok(id);
	}
	//重命名的方法
	@Override
	public void updateContenCatgory(Long id, String name) {
		//先查询
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		//更新
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
	}
	//删除的方法
//	@Override
//	public void deleteContenCategory(Long parentId, Long id) {
//		//先查询
//		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
//		//删除 判断是否是父节点，不是直接删除，如果是父节点再判断下边节点是否也有父节点再全部删除
//		if(!contentCategory.getIsParent()){
//			contentCategoryMapper.deleteByPrimaryKey(id);
//		}else{
//			
//		}
		
		
	

}
