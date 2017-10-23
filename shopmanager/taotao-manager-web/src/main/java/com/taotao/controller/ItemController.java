package com.taotao.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	//注入servicec
	private ItemService itemService;
	@RequestMapping("item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	//查询商品列表
	@RequestMapping("/item/list")
	@ResponseBody
	//请求的参数名要一样page rows
	public EUDateGridResult getItemList(Integer page,Integer rows){
		EUDateGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	//指定请求方法是post
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult creatItem(TbItem tbItem,String desc,String itemParams){
		TaotaoResult result = itemService.createItem(tbItem, desc,itemParams);
		return result;
	}
	@RequestMapping("/page/item/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model){
		String html = itemService.getItemParamHtml(itemId);
		model.addAttribute("myHtml", html);
		return "itemParam";
	}
}
