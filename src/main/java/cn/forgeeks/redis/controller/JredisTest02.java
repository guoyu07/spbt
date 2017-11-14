package cn.forgeeks.redis.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.forgeeks.util.StringUtils;
import redis.clients.jedis.Jedis;

public class JredisTest02 {

	private Jedis jedis;

	@Before
	public void setup() {
		// 连接redis服务器，连接池方式
		jedis = RedisUtil.getJedis();
	}

	@After
	public void After() {
		RedisUtil.returnResource();
	}

	/**
	 * 存取object
	 */
	@Test
	public void test01() {
		for (int i = 1; i <= 1000; i++) {
			SysUser user = new SysUser(StringUtils.getUUID36(), StringUtils.getRandomCN(2), "湖北武汉软件园",
					"13" + StringUtils.getRandomNumber(11), new Date());
			user.setDetail(new DetaiInfo(StringUtils.getUUID36(), user.getId(), "此用户还没有填写详细信息"));
			RedisUtil.setObj(jedis, user.getId(), user);
			System.out.println(i + "条已存入redis : " + user.getId());
			user = (SysUser) RedisUtil.getObj(jedis, user.getId());
			System.out.println("从redis中查到 : " + user);
		}
	}

	@Test
	public void test0211() {
		Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
		SysUser user = new SysUser(StringUtils.getUUID36(), StringUtils.getRandomCN(2), "湖北武汉软件园",
				"13" + StringUtils.getRandomNumber(11), new Date());
		user.setDetail(new DetaiInfo(StringUtils.getUUID36(), user.getId(), "管理员"));
		map.put(RedisUtil.object2Bytes("manager-01"), RedisUtil.object2Bytes(user));

		SysUser user2 = new SysUser(StringUtils.getUUID36(), StringUtils.getRandomCN(2), "湖北武汉软件园",
				"13" + StringUtils.getRandomNumber(11), new Date());
		user2.setDetail(new DetaiInfo(StringUtils.getUUID36(), user.getId(), "超级管理员"));
		map.put(RedisUtil.object2Bytes("root-01"), RedisUtil.object2Bytes(user2));

		SysUser user3 = new SysUser(StringUtils.getUUID36(), StringUtils.getRandomCN(2), "湖北武汉软件园",
				"13" + StringUtils.getRandomNumber(11), new Date());
		user3.setDetail(new DetaiInfo(StringUtils.getUUID36(), user.getId(), "访客"));
		map.put(RedisUtil.object2Bytes("visitor-01"), RedisUtil.object2Bytes(user3));
		String key = "map-user-group";

		jedis.hmset(RedisUtil.object2Bytes(key), map);
		// 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		// 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
		/*
		 * List<byte[]> rsmap = jedis.hmget(RedisUtil.object2Bytes(key),
		 * RedisUtil.object2Bytes("manager-01"),
		 * RedisUtil.object2Bytes("root-01"),
		 * RedisUtil.object2Bytes("visitor-01"));
		 * 
		 * for(byte[] bs : rsmap){ SysUser user4=(SysUser)
		 * RedisUtil.byte2Object(bs); System.out.println(user4); }
		 */

	}

	@Test
	public void test02w11() {
		System.out.println(jedis.hlen(RedisUtil.object2Bytes("map-user-group"))); // 返回key为user的键中存放的值的个数2
		System.out.println(jedis.exists(RedisUtil.object2Bytes("map-user-group")));// 是否存在key为user的记录
																					// 返回true

		System.out.println(jedis.hkeys(RedisUtil.object2Bytes("map-user-group")));// 返回map对象中的所有key
		Set<byte[]> set = jedis.hkeys(RedisUtil.object2Bytes("map-user-group"));
		for (byte[] bs : set) {
			System.out.println((String) RedisUtil.byte2Object(bs));
		}

		System.out.println(jedis.hvals(RedisUtil.object2Bytes("map-user-group")));// 返回map对象中的所有value

		List<byte[]> list = jedis.hvals(RedisUtil.object2Bytes("map-user-group"));
		for (byte[] bs : list) {
			System.out.println((SysUser) RedisUtil.byte2Object(bs));
		}
		/*
		 * // 删除map中的某个键值 Iterator<String> iter =
		 * jedis.hkeys("user").iterator(); while (iter.hasNext()) { String key =
		 * iter.next(); System.out.println(key + ":" + jedis.hmget("user",
		 * key)); }
		 */
	}

	@Test
	public void test0112() {
		jedis.hdel(RedisUtil.object2Bytes("map-user-group"), RedisUtil.object2Bytes("visitor-01"));
		List<byte[]> list = jedis.hmget(RedisUtil.object2Bytes("map-user-group"), RedisUtil.object2Bytes("manager-01")); 
		// 因为删除了，所以返回的是null
		for (byte[] bs : list) {
			System.out.println((SysUser)RedisUtil.byte2Object(bs));
		}
	}

	@Test
	public void test2202() {
		
		
	}

	@Test
	public void test234202() {

	}

	@Test
	public void test220452() {

	}

}
