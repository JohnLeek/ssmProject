package com.taotao.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService userService;
	@Value("${SSO_USER_TOKEN_SERVICE}")
	private String SSO_USER_TOKEN_SERVICE;
	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		TbUser user = userService.getUserByTbToken(request, response);
		//如果user为空直接跳转到登录界面
		if(user == null){
			response.sendRedirect(SSO_LOGIN_URL+"?redirectURL="+request.getRequestURL());
			return false;
		}
		//放行 下列两个方法需要返回值为true时才执行
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
 
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
