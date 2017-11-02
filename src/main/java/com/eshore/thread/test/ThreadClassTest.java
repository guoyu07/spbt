package com.eshore.thread.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @desc 多线程工具类测试
 * @desc CountDownLatch & CyclicBarrier & Semaphore & Phaser & Exchanger
 * @author hechao
 * @desc Semaphore 信号量是一类经典的同步工具。信号量通常用来限制线程可以同时访问的（物理或逻辑）资源数量。
 * @desc CountDownLatch 一种非常简单、但很常用的同步辅助类。其作用是在完成一组正在其他线程中执行的操作之前,允许一个或多个线程一直阻塞。
 * @desc CyclicBarrier 一种可重置的多路同步点，在某些并发编程场景很有用。它允许一组线程互相等待，直到到达某个公共的屏障点 (common
 *       barrier point)。在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该
 *       barrier在释放等待线程后可以重用，所以称它为循环的barrier。
 * @desc Phaser 一种可重用的同步屏障，功能上类似于CyclicBarrier和CountDownLatch，但使用上更为灵活。
 *       非常适用于在多线程环境下同步协调分阶段计算任务（Fork/Join框架中的子任务之间需同步时，优先使用Phaser） Exchanger
 *       允许两个线程在某个汇合点交换对象，在某些管道设计时比较有用。
 * @desc Exchanger提供了一个同步点，在这个同步点，一对线程可以交换数据。
 *       每个线程通过exchange()方法的入口提供数据给他的伙伴线程，并接收他的伙伴线程提供的数据并返回。
 *       当两个线程通过Exchanger交换了对象，这个交换对于两个线程来说都是安全的。
 * @desc Exchanger 允许两个线程在某个汇合点交换对象，在某些管道设计时比较有用。
 */
public class ThreadClassTest {
	public static Integer MAX_SIZE = 100;
	public static Integer MAX_PLAYER = 6;
	public static Integer MAX_CONNECTED_NUMBER = 3;

	ExecutorService pool = Executors.newFixedThreadPool(MAX_SIZE);

	
	public void startRun() {

		ThreadClassTest test = new ThreadClassTest();

		CountDownLatch countDownLatch = new CountDownLatch(MAX_PLAYER);
		for (int i = 0; i <= MAX_PLAYER + 1; i++) {
			pool.execute(new CountDownLatchThread(i, countDownLatch, new HashMap<>()));
		}
		try {
			countDownLatch.await();
			System.out.println("finished");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		test.pool.shutdown();
	}

	public static void main(String[] args) {
		ThreadClassTest test = new ThreadClassTest();

		// test.fun01();
		// test.fun02();
		test.fun03();

		test.pool.shutdown();
	}

	/**
	 * @desc 测试countDownLatch基本功能
	 */
	public void fun03() {
		CountDownLatch countDownLatch = new CountDownLatch(MAX_PLAYER);
		for (int i = 0; i <= MAX_PLAYER + 1; i++) {
			pool.execute(new CountDownLatchThread(i, countDownLatch, new HashMap<>()));
		}
		try {
			countDownLatch.await();
			System.out.println("finished");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @desc 测试CyclicBarrier基本功能
	 */
	public void fun01() {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(MAX_PLAYER);
		for (int i = 0; i <= MAX_PLAYER; i++) {
			pool.execute(new MyThread(i, cyclicBarrier));
		}
	}

	/**
	 * @desc 测试Semaphore基本功能
	 */
	public void fun02() {
		Semaphore semaphore = new Semaphore(MAX_CONNECTED_NUMBER);
		for (int i = 0; i < 20; i++) {
			pool.execute(new SemaphoreThread(i, semaphore));
		}
	}

	/**
	 * @desc CountDownLatch辅助控制的线程
	 */
	@SuppressWarnings("rawtypes")
	class CountDownLatchThread implements Runnable {
		Integer tIndex;
		CountDownLatch finishSinal;
		Map map;

		public CountDownLatchThread(Integer tIndex, CountDownLatch finishSinal, Map map) {
			this.tIndex = tIndex;
			this.finishSinal = finishSinal;
			this.map = map;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				map.put("key-" + tIndex + "-index-" + i, tIndex + "-value");
				map.containsKey("index");
			}
			System.out.println("thread-" + tIndex + " runs nice");
			finishSinal.countDown();
		}
	}

	/**
	 * @desc CyclicBarrier辅助控制的线程
	 */
	class MyThread implements Runnable {
		Integer tIndex;
		CyclicBarrier cyclicBarrier;

		public MyThread(Integer tIndex, CyclicBarrier cyclicBarrier) {
			super();
			this.tIndex = tIndex;
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			try {
				int timer = new Random().nextInt(5);
				TimeUnit.SECONDS.sleep(timer);
				System.out.printf("%d号选手准备完毕,准备时间%d\n", tIndex, timer);
				cyclicBarrier.await();
				System.out.printf("%d号选手于%s时起跑!\n", tIndex, new Date().toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * @desc Semaphore辅助控制的线程,
	 */
	class SemaphoreThread implements Runnable {
		Integer tIndex;
		Semaphore semaphore;

		public SemaphoreThread(Integer tIndex, Semaphore semaphore) {
			super();
			this.tIndex = tIndex;
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println("thread-" + tIndex + " acquired ");
				int timer = new Random().nextInt(2);
				TimeUnit.SECONDS.sleep(timer);
				semaphore.release();
				System.out.println("thread-" + tIndex + " released ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
