package com.taotao.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.service.CartService;

/**
 * <p>
 * Description:购物车
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年10月23日 上午9:38:51
 */
@Controller
public class CartController {
	@Autowired
	private CartService cartService;

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		TaotaoResult result = cartService.addCart(itemId, num, request, response);
		return "addSuccess";
	}

}
