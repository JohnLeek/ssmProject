package com.taotao.sso.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.component.JedisClient;
import com.taotao.sso.service.LoginService;

/**<p>Description:用户登录</p>
 *@author: Li Yinqiang
 *@date 2017年10月21日 下午5:57:10  
 */
 @Service
public class LoginServiceImpl implements LoginService{
	 @Autowired
	 private TbUserMapper userMapper;
	 @Autowired
	 private JedisClient jedisClient;
	 @Value("${REDIS_SESSION_KEY}")
	 private String REDIS_SESSION_KEY;
	 @Value("${SESSION_EXPIRE}")
	 private Integer SESSION_EXPIRE;
	@Override
	public TaotaoResult login(String username, String password, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		//验证用户
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		TbUser user = list.get(0);
		if(list == null || list.isEmpty()){
			return TaotaoResult.build(400, "账号或密码错误");
		}
		if(!list.get(0).getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return TaotaoResult.build(400, "账号或密码错误");
		}
		//登录成功生成token存redis和cookie
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		//存入redis
		jedisClient.set(REDIS_SESSION_KEY+":"+token, JsonUtils.objectToJson(user));
		//设置过期时间
		jedisClient.expire(REDIS_SESSION_KEY+":"+token, SESSION_EXPIRE);
		//放入cookie
		CookieUtils.setCookie(httpServletRequest, httpServletResponse, "TT_TOKEN", token);
		return TaotaoResult.ok(token);
	}
	//根据token获得用户
	@Override
	public TaotaoResult getUserByToken(String token) {
		String json = jedisClient.get(REDIS_SESSION_KEY+":"+token);
		//判断session是否过期
		if(StringUtils.isBlank(json)){
			return TaotaoResult.build(500, "用户session已经过期");
		}
		//没有过期 将session时间延长
		jedisClient.expire(token, SESSION_EXPIRE);
		TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
		return TaotaoResult.ok(tbUser);
	}

}
