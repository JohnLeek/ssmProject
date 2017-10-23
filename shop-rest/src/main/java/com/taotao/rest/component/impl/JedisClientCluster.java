package com.taotao.rest.component.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.rest.component.JedisClient;

import redis.clients.jedis.JedisCluster;

/**<p>Description:redis集群版</p>
 *@author: Li Yinqiang
 *@date 2017年10月11日 下午2:41:27  
 */
 
public class JedisClientCluster implements JedisClient{

	@Autowired
	private JedisCluster jedisCluster;
	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Long hset(String key, String item, String value) {
		return jedisCluster.hset(key, item, value);
	}

	@Override
	public String hget(String key, String item) {
		return jedisCluster.hget(key, item);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public Long decr(String key) {
		return jedisCluster.decr(key);
	}

	@Override
	public Long expire(String key, int second) {
		return jedisCluster.expire(key,second);
	}

	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public Long hdel(String key, String item) {
		return jedisCluster.hdel(key, item);
	}

}
