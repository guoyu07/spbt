package cn.forgeeks.redis.controller;

import redis.clients.jedis.Jedis;

public interface RedisService {
	public Jedis getJedis();
}
