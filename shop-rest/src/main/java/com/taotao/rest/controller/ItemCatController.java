package com.taotao.rest.controller;

import javax.print.attribute.standard.Media;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * <p>
 * Description:商品分类查询服务
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年10月1日 下午8:31:37
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	//解决乱码
	@RequestMapping(value = "/list",produces= MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public Object getItemCatList(String callback) {
		//第一种方法
//		ItemCatResult result = itemCatService.getItemCatList();
//		if (StringUtils.isBlank(callback)) {
//			//把result变成字符串
//			String json = JsonUtils.objectToJson(result);
//			System.out.println("空");
//			return json;
//		}else{
//			//不为空支持jsonp调用
//			String json = JsonUtils.objectToJson(result);
//			System.out.println("不为空");
//			return callback+"("+json+");";
//		}
		//第二种方法
		ItemCatResult result = itemCatService.getItemCatList();
		if (StringUtils.isBlank(callback)) {
			//把result变成字符串
			String json = JsonUtils.objectToJson(result);
			return json;
		}else{
			//不为空支持jsonp调用
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
		}

	}
}
