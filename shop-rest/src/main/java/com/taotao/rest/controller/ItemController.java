package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.ExceptionUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.service.ItemService;

/**<p>Description:商品详细信息查询</p>
 *@author: Li Yinqiang
 *@date 2017年10月18日 下午2:33:06  
 */
 
@Controller
@RequestMapping("item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/base/{itemid}")
	@ResponseBody
	//商品详细信息查询  查询商品
	public TaotaoResult getItemById(@PathVariable Long itemid){
		try {
			TbItem item = itemService.getItemById(itemid);
			return TaotaoResult.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping("/desc/{itemid}")
	@ResponseBody
	//商品详细信息查询  商品描述
	public TaotaoResult getItemDescById(@PathVariable Long itemid){
		try {
			TbItemDesc itemDesc = itemService.getItemDescById(itemid);
			return TaotaoResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	@RequestMapping("/param/{itemid}")
	@ResponseBody
	//商品详细信息查询  商品规格参数
	public TaotaoResult getItemParamItem(@PathVariable Long itemid){
		try {
			TbItemParamItem itemParamItem = itemService.getItemParamItem(itemid);
			return TaotaoResult.ok(itemParamItem);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
