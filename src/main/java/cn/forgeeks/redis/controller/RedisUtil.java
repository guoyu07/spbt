package cn.forgeeks.redis.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {

	// Redis服务器IP
	private static String ADDR = "192.168.239.128";

	// Redis的端口号
	private static int PORT = 6379;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
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
	public static void returnResource() {
		if (jedisPool != null) {
			jedisPool.close();
		}
	}

	/**
	 * 序列化方法
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] object2Bytes(Object value) {
		if (value == null)
			return null;
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream;
		try {
			outputStream = new ObjectOutputStream(arrayOutputStream);
			outputStream.writeObject(value);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				arrayOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arrayOutputStream.toByteArray();
	}

	/**
	 * 反序列化方法
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object byte2Object(byte[] bytes) {
		if (bytes == null || bytes.length == 0)
			return null;
		try {
			ObjectInputStream inputStream;
			inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object obj = inputStream.readObject();
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存对象
	 * 
	 * @param redis
	 * @param key
	 */
	public static void setObj(Jedis redis, String key, Object obj) {
		redis.set(key.getBytes(), object2Bytes(obj));
	}

	/**
	 * 获取对象
	 * 
	 * @param redis
	 * @param key
	 * @return
	 */
	public static Object getObj(Jedis redis, String key) {
		Object obj = byte2Object(redis.get(key.getBytes()));
		return obj;
	}

	/**
	 * 保存文件方法
	 * 
	 * @param redis
	 * @param key
	 * @param path
	 */
	public static void setFile(Jedis redis, String key, String path) {
		File fr = new File(path);
		redis.set(key.getBytes(), object2Bytes(fr));
	}

	/**
	 * 读取文件对象方法
	 * 
	 * @param redis
	 * @param key
	 * @return
	 */
	public static File getFile(Jedis redis, String key) {
		File file = (File) byte2Object(redis.get(key.getBytes()));
		return file;
	}

	public static void flushAll(Jedis redis) {
		redis.flushAll();
	}

}