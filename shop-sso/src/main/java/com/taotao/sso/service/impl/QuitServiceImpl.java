package com.taotao.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.sso.component.JedisClient;
import com.taotao.sso.service.QuitService;
@Service
public class QuitServiceImpl implements QuitService{
	 @Autowired
	 private JedisClient jedisClient;
	 @Value("${REDIS_SESSION_KEY}")
	 private String REDIS_SESSION_KEY;
	@Override
	public TaotaoResult quit(String token) {
		jedisClient.del(REDIS_SESSION_KEY+":"+token);
		return TaotaoResult.ok();
	}

}
