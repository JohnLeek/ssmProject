package com.taotao.controller;

import java.util.List;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;
/*
 * 商品分类管理controller
 */
//查询商品列表的方法
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	//注入service
	@Autowired
	private ItemCatService itemCatService;
	@RequestMapping("list")
	@ResponseBody
	public List<EUTreeNode> getItemCate(@RequestParam(value="id",defaultValue="0") long parentId){
		List<EUTreeNode> list = itemCatService.getItemCatList(parentId);
		return list;
	}
}
