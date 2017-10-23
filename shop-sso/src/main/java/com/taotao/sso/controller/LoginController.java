package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.ExceptionUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.sso.service.LoginService;

/**
 * <p>
 * Description:登录
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年10月21日 下午7:18:30
 */

@Controller

public class LoginController {
	@Autowired
	private LoginService loginService;

	// 用户登录
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(@RequestParam String username, @RequestParam String password,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try {
			return loginService.login(username, password, httpServletRequest, httpServletResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	// 根据token取用户信息 支持jsop调用
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		try {
			TaotaoResult result = loginService.getUserByToken(token);
			if (StringUtils.isNotBlank(callback)) {
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

}
