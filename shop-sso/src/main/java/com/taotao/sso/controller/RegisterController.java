package com.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.ExceptionUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.RegisterService;

/**<p>Description:用户校验</p>
 *@author: Li Yinqiang
 *@date 2017年10月21日 下午2:07:16  
 */
 @Controller
 @RequestMapping("/user")
public class RegisterController {
	 @Autowired
	 private RegisterService registerService;
	 @RequestMapping("/check/{param}/{type}")
	 @ResponseBody
	 public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback){
		 try {
			TaotaoResult result = registerService.checkData(param, type);
			//jsonp调用
			if(StringUtils.isNotBlank(callback)){
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	 }
	 //用户注册的方法
	 @RequestMapping(value = "/register",method=RequestMethod.POST)
	 @ResponseBody
	 public TaotaoResult register(TbUser tbUser){
		try {
			return registerService.register(tbUser);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		} 
	 }
	 
}
