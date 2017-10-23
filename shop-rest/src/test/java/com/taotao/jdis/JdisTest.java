package com.taotao.jdis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.rest.component.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JdisTest {
	// 单机版
	@Test
	public void testJedis() {
		Jedis jedis = new Jedis("192.168.25.133", 6379);
		jedis.set("test", "hello jedis");
		String result = jedis.get("test");
		System.out.println(result);
		jedis.close();
	}

	@Test
	public void testPool() {
		JedisPool jedisPool = new JedisPool("192.168.25.153", 6379);
		// 从连接池中获得一个jedis对象
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get("test");
		System.out.println("连接池" + result);
		jedis.close();
		jedisPool.close();
	}

	@Test
	public void testRedisCluster() {
		// 指定连接的地址端口
		Set<HostAndPort> set = new HashSet<>();
		set.add(new HostAndPort("192.168.25.153", 7001));
		set.add(new HostAndPort("192.168.25.153", 7002));
		set.add(new HostAndPort("192.168.25.153", 7003));
		set.add(new HostAndPort("192.168.25.153", 7004));
		set.add(new HostAndPort("192.168.25.153", 7005));
		set.add(new HostAndPort("192.168.25.153", 7006));
		//
		JedisCluster jedisCluster = new JedisCluster(set);
		jedisCluster.set("name", "zhangsan");
		jedisCluster.set("age", "100");
		String name = jedisCluster.get("name");
		String age = jedisCluster.get("age");
		System.out.println(name + "     " + age);
		jedisCluster.close();
	}

	@Test
	public void testJedisSpring() { 
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisClient jedisClient = context.getBean(JedisClient.class);
		jedisClient.set("client1", "1000");
		String result = jedisClient.get("client1");
		System.out.println(result); 
	}

}
