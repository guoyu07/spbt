package com.eshore.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * threadlocal
 * 
 * @author hechao
 * @desc 适用场景: 并发时多线程需要使用某些具有相同初始值的对象时, 适合用threadlocal
 * @desc 原理: 每一个线程对象都维护有threadlocalmap, key是threadlocal对象, value是想保存的对象,
 *       要使用目标对象时线程就调用 ThreadLocal 的get方法获得一个线程相关的有初始值的对象副本, 由于每个线程都使用自身相关的副本对象,
 *       因此 不会发生并发错误
 * 
 */
public class ThreadlocalTest {

	public long startTime;
	public long endTime;
	public String testName = "#############           threadlocal         ###############";
	public ExecutorService pool = null;

	@Before
	public void fun01() {
		System.out.println(testName + "\n");
		startTime = System.currentTimeMillis();
		pool = Executors.newFixedThreadPool(100);
	}

	@After
	public void fun99() throws InterruptedException {
		Thread.sleep(3000);
		endTime = System.currentTimeMillis();
		System.out.println("\n" + testName);
		System.out.println("OK ! (" + (endTime - startTime) + " ms)");
		pool.shutdown();
	}

	public ConnectionManager cm = new ConnectionManager();

	/**
	 * @desc connection初始化成功 1:
	 *       com.eshore.thread.test.ThreadlocalTest$Conn@5925dae8 2:
	 *       com.eshore.thread.test.ThreadlocalTest$Conn@5925dae8 3:
	 *       com.eshore.thread.test.ThreadlocalTest$Conn@5925dae8
	 *       Thread[pool-1-thread-1,5,main] : connection获取成功, 且自增操作成功 -> 103
	 *       connection初始化成功 1:
	 *       com.eshore.thread.test.ThreadlocalTest$Conn@76f1a348 2:
	 *       com.eshore.thread.test.ThreadlocalTest$Conn@76f1a348 3:
	 *       com.eshore.thread.test.ThreadlocalTest$Conn@76f1a348
	 *       Thread[pool-1-thread-2,5,main] : connection获取成功, 且自增操作成功 -> 103
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void test01() throws InterruptedException {
		Thread t1 = new Thread(new Thread01());
		Thread t2 = new Thread(new Thread01());
		Thread.sleep(1000);

		pool.execute(t1);
		pool.execute(t2);

	}

	class ConnectionManager {
		ThreadLocal<Conn> threadLocal = new ThreadLocal<Conn>();
		Conn conn = null;

		Conn getConnection() {
			if (null == conn) {
				Conn connection = new Conn(100); // 模拟jdbc操作
				System.out.println("connection初始化成功");
				threadLocal.set(connection);
				conn = connection;
			}
			return conn;
		}

	}

	class Thread01 implements Runnable {

		@Override
		public void run() {
			Conn conn = cm.getConnection();
			conn.id++;
			System.out.println("1: " + conn);

			conn = cm.getConnection();
			conn.id++;
			System.out.println("2: " + conn);

			conn = cm.getConnection();
			conn.id++;
			System.out.println("3: " + conn);

			System.out.println(Thread.currentThread() + " : connection获取成功, 且自增操作成功 -> " + conn.id);

		}

	}

	class Conn {
		Integer id;

		public Conn(Integer id) {
			super();
			this.id = id;
		}

	}
}
