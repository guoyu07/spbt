package cn.forgeeks.redis.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.forgeeks.util.StringUtils;

/**
 * 测试redis
 * 
 * @author hechao
 */
@Controller
@RequestMapping("/redis")
public class JredisController {

	@Autowired
	RedisClient redisCLient;

	@RequestMapping("/index")
	@ResponseBody
	public String index() {
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
		redisCLient.getJedis().hmset(RedisUtil.object2Bytes(key), map);
		// 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		// 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
		List<byte[]> rsmap = redisCLient.getJedis().hmget(RedisUtil.object2Bytes(key),
				RedisUtil.object2Bytes("manager-01"), RedisUtil.object2Bytes("root-01"),
				RedisUtil.object2Bytes("visitor-01"));

		for (byte[] bs : rsmap) {
			SysUser user4 = (SysUser) RedisUtil.byte2Object(bs);
			System.out.println(user4);
		}
		
		redisCLient.returnResource();
		return "success";
	}
}
