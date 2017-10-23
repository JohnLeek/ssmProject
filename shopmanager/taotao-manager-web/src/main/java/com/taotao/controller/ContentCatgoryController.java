package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**<p>Description:内容分类管理</p>
 *@author: Li Yinqiang
 *@date 2017年10月2日 下午4:54:44  
 */
 
@Controller
@RequestMapping("/content/category")
public class ContentCatgoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping("/list")
	@ResponseBody
	//查询分类的内容
	public List<EUTreeNode> getContentCatList(@RequestParam(value = "id",defaultValue="0")Long parentId){
		List<EUTreeNode> list = contentCategoryService.getContentCatList(parentId);
		return list;
	}
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult inserContentCat(Long parentId,String name){
		TaotaoResult result = contentCategoryService.insertCategory(parentId, name);
		return result;
		
	}
	//重命名的方法
	@RequestMapping("/update")
	public void updateContenCatgory(@RequestParam Long id,String name){
		contentCategoryService.updateContenCatgory(id, name);
	}
	
	
	
}
