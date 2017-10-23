package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;

/**<p>Description: 商品规格参数管理模板</p>
 *@author: Li Yinqiang
 *@date 2017年9月24日 下午8:18:31  
 */
 
@Controller
@RequestMapping("/item/param")
public class ItamParamController {
	@Autowired
	private ItemParamService itemParamService;
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long cid){
		TaotaoResult result = itemParamService.getItemParamByCid(cid);
		return result;
	}
	//插入数据
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable Long cid,String paramData){
		TaotaoResult result = itemParamService.insertItemParam(cid, paramData);
		return result;
	}
}
