package cn.forgeeks.redis.controller;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisServiceImpl implements RedisService {

	@Override
	public Jedis getJedis() {
		return RedisUtil.getJedis();
	}
 
}
