package cn.forgeeks.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis客户端
 * 
 * @author hechao
 *
 */
@Component
public class RedisClient {

	@Autowired
	private JedisPool jedisPool;

	public Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public  void returnResource() {
		if (jedisPool != null) {
			jedisPool.close();
		}
	}
	
}
