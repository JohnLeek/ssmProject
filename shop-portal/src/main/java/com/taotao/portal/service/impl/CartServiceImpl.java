package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;
@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private ItemService itemService;
	@Value("{COOKIE_EXPIRE")
	private Integer COOKIE_EXPIRE;
	@Override
	public TaotaoResult addCart(Long itemId, Integer num,HttpServletRequest request, HttpServletResponse response) {
		//从cookie中取商品
		List<CartItem> itemList = getCartItemList(request);
		//判断是否存在要添加的商品商品 有数量增加 没有 调用服务通过itemId获得
		boolean flag = false;
		for(CartItem cartItem : itemList){
			//存在
			if(itemId == cartItem.getId().longValue()){
				flag = true;
				cartItem.setNum(cartItem.getNum()+num);
				break;
			}
		}
		//不存在 调用服务
		if(flag == false){
			TbItem tbItem = itemService.getTbItemById(itemId);
			//转化成CartItem对象
			CartItem cartItem = new CartItem();
			cartItem.setId(tbItem.getId());
			cartItem.setNum(num);
			cartItem.setPrice(tbItem.getPrice());
			cartItem.setTitle(tbItem.getTitle());
			if(StringUtils.isNotBlank(tbItem.getImage())){
				String[] strings  = tbItem.getImage().split(",");
				cartItem.setImage(strings[0]);
			}
			itemList.add(cartItem);
			//写入cookie
		}
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), COOKIE_EXPIRE, true);
		return TaotaoResult.ok();
	}
	//取购物车商品列表
	private List<CartItem> getCartItemList(HttpServletRequest request){
		try {
			//从cookie中取商品列表
			String json = CookieUtils.getCookieValue(request, "TT_CART",true);
			//将json转化为CartItem对象
			List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
			return list==null?new ArrayList<CartItem>():list;
			
		} catch (Exception e) {
			return new ArrayList<CartItem>();
		}
	}
}
