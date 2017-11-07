package com.eshore.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 死锁 & 活锁 & 饥饿
 * 
 * @author hechao
 * @desc 死锁指竞态环境下线程a占有资源a请求资源b, 线程b占有资源b请求资源a, 由此产生互相等待的死循环的局面, 若无外力作用,
 *       任务不会执行下去, 死锁条件有四, 1保持并请求 2资源互斥 3环路等待 4非剥夺
 * @desc 活锁指线程a和线程b同时请求一个资源却互相谦让然后都重新发起资源请求, 然而由于重试算法不合理导致两个线程陷入重试失败的循环中,
 *       和死锁不同的是, 活锁是活的, 处于活锁的实体状态一直在改变且可能自行解开, 而处于死锁的实体一直在阻塞等待 ,不会自行解开
 * @desc 饥饿指竞态环境下由于不公平锁而导致的某些线程一直处于等待的状态
 */
public class DeadLock {

	public long startTime;
	public long endTime;
	public String testName = "#############              死锁 & 活锁 & 饥饿           ###############";
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

	MyResource a = new MyResource("re", 0);
	MyResource b = new MyResource("re", 0);

	/**
	 * 证明死锁生成条件
	 * 
	 * @输出 Thread[Thread-1,5,main] : 我已经占有资源b, 正在请求资源a Thread[Thread-0,5,main] :
	 *     我已经占有资源a, 正在请求资源b
	 */
	@Test
	public void test01() {
		Thread thread01 = new Thread(new Thread01());
		Thread thread02 = new Thread(new Thread02());
		thread01.start();
		thread02.start();

	}

	class Thread01 implements Runnable {

		@Override
		public void run() {
			synchronized (a) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread() + " : 我已经占有资源a, 正在请求资源b");
				synchronized (b) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println(Thread.currentThread() + " : 我已经占有资源a和资源b");
				}

			}
		}

	}

	class Thread02 implements Runnable {

		@Override
		public void run() {
			synchronized (b) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread() + " : 我已经占有资源b, 正在请求资源a");
				synchronized (a) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread() + " : 我已经占有资源a和资源b");
				}

			}
		}

	}

	/**
	 * 证明 活锁 的
	 * 
	 * @输出 Thread[Thread-0,5,main] : 我想使用这个资源, 没人跟我抢吧, 嘤嘤嘤
	 *     Thread[Thread-0,5,main] : 有人也想用这个资源, 那让他先用吧, 我等会再次发起资源请求
	 *     Thread[Thread-1,5,main] : 我想使用这个资源, 没人跟我抢吧, 嘤嘤嘤
	 *     Thread[Thread-1,5,main] : 有人也想用这个资源, 那让他先用吧, 我等会再次发起资源请求
	 *     Thread[Thread-1,5,main] : 有人也想用这个资源, 那让他先用吧, 我等会再次发起资源请求
	 *     Thread[Thread-0,5,main] : 有人也想用这个资源, 那让他先用吧, 我等会再次发起资源请求
	 *     Thread[Thread-0,5,main] : 我占有了资源, 嘤嘤嘤 Thread[Thread-1,5,main] :
	 *     我占有了资源, 嘤嘤嘤
	 * 
	 */
	@Test
	public void test06() {
		Thread thread = new Thread(new Thread05());
		Thread thread2 = new Thread(new Thread05());
		alone = false;

		thread.start();
		thread2.start();

	}

	Boolean alone = null;

	class Thread05 implements Runnable {

		Boolean isRich = false;

		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " : 我想使用这个资源, 没人跟我抢吧, 嘤嘤嘤");
			while (!isRich) {
				if (System.currentTimeMillis() % 3 == 0) {
					alone = true;
				}
				if (alone) {
					System.out.println(Thread.currentThread() + " : 我占有了资源, 嘤嘤嘤");
					isRich = true;
					alone = true;
				} else {
					System.out.println(Thread.currentThread() + " : 有人也想用这个资源, 那让他先用吧, 我等会再次发起资源请求");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}

	}

	class MyResource {
		String name;
		Integer index;

		public MyResource(String name, Integer index) {
			super();
			this.name = name;
			this.index = index;
		}

	}

}
