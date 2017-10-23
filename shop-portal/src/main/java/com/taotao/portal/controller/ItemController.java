package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;

/**<p>Description:商品详情页面展示</p>
 *@author: Li Yinqiang
 *@date 2017年10月18日 下午3:26:36  
 */
 
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/item/{itemId}")
	public String getTbItemById(@PathVariable Long itemId,Model model){
		TbItem tbItem = itemService.getTbItemById(itemId);
		model.addAttribute("item", tbItem);
		return "item";
	}
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getTbItemDescById(@PathVariable Long itemId){
		return itemService.getTbDescById(itemId);
	}
	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getTbItemParamItemById(@PathVariable Long itemId){
		return itemService.getTbItemParamItemById(itemId);
	}
}
