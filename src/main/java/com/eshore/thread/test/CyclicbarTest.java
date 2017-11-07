package com.eshore.thread.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * CountDownLatch & CyclicBarrier & Semaphore & Phaser & Exchanger
 * 
 * @author hechao
 *
 * @desc CountdownLatch适用场景: 需要阻塞式等待任务完成, 如考试完成后才能阅卷 常用方法: await, countDown
 * @desc CycllicBarrier使用场景: 需要阻塞式等待任务开始, 如考生就绪后才开始发卷考试, 百米赛跑等待运动员就绪 常用方法 :
 *       await
 * @desc Semaphore适用场景: 需要控制连接池连接数量或并发数量, 如考试时一个考场人数是有限制的, 景区人数限制 常用方法 :
 * @desc Phaser适用场景: 需要并发执行多个任务步骤, 如上午考试考语文考完了才能开始下午的数学, 高考过程 常用方法:
 * @desc Exchanger适用场景: 成对出现的线程交换数据, 如考试结束两位老师分别核验所有学生分数, 若核验结果是同一个分数就作为最终分数,
 *       否则重新核验那个考生的试卷, 流水线产品校验, 常用方法:
 */
public class CyclicbarTest {

	public long startTime;
	public long endTime;
	public String testName = "#############           CountDownLatch & CyclicBarrier & Semaphore & Phaser & Exchanger         ###############";
	public ExecutorService pool = null;

	/**
	 * @输出 Thread[pool-1-thread-1,5,main] : before -> value-4 value-3 value-2
	 *     Thread[pool-1-thread-2,5,main] : before -> value-3 value-1 value-0
	 *     Thread[pool-1-thread-1,5,main] : after -> value-3 value-1 value-0
	 *     Thread[pool-1-thread-2,5,main] : after -> value-4 value-3 value-2
	 */
	@Test
	public void test10() {
		Exchanger<List<String>> exchanger = new Exchanger<List<String>>();
		List<String> list = new ArrayList<String>();
		pool.execute(new ExchangerThread(exchanger));
		pool.execute(new ExchangerThread(exchanger));

	}

	@Before
	public void fun01() {
		System.out.println(testName + "\n");
		startTime = System.currentTimeMillis();
		pool = Executors.newFixedThreadPool(100);
	}

	@After
	public void fun99() {
		endTime = System.currentTimeMillis();
		System.out.println("\n" + testName);
		System.out.println("OK ! (" + (endTime - startTime) + " ms)");
		pool.shutdown();
	}

	/**
	 * @输出 语文考试开始 Thread[pool-1-thread-1,5,main] 完成语文考试
	 *     Thread[pool-1-thread-4,5,main] 完成语文考试 Thread[pool-1-thread-2,5,main]
	 *     完成语文考试 Thread[pool-1-thread-5,5,main] 完成语文考试
	 *     Thread[pool-1-thread-3,5,main] 完成语文考试 数学考试开始
	 *     Thread[pool-1-thread-3,5,main] 完成数学考试 Thread[pool-1-thread-2,5,main]
	 *     完成数学考试 Thread[pool-1-thread-4,5,main] 完成数学考试
	 *     Thread[pool-1-thread-1,5,main] 完成数学考试 Thread[pool-1-thread-5,5,main]
	 *     完成数学考试 英语考试开始 Thread[pool-1-thread-5,5,main] 完成英语考试
	 *     Thread[pool-1-thread-2,5,main] 完成英语考试 Thread[pool-1-thread-1,5,main]
	 *     完成英语考试 Thread[pool-1-thread-4,5,main] 完成英语考试
	 *     Thread[pool-1-thread-3,5,main] 完成英语考试 所有考试结束
	 * @throws Exception
	 */
	@Test
	public void test07() throws Exception {
		MyPhaser phaser = new MyPhaser();
		for (int i = 1; i <= 5; i++) {
			phaser.register();
			pool.execute(new PhaserThread(phaser));
		}
		Thread.sleep(20000);
	}

	/**
	 * 
	 */
	@Test
	public void test05() {
		Semaphore semaphore = new Semaphore(10);
		for (int i = 1; i <= 20; i++) {
			pool.execute(new SemaphoreThread(semaphore));
		}

		System.out.println("到期开始考试");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 证明countDownLatch的适用场景
	 * 
	 * @输出 Thread[pool-1-thread-2,5,main] : 准备好了 Thread[pool-1-thread-3,5,main]
	 *     : 准备好了 Thread[pool-1-thread-4,5,main] : 准备好了
	 *     Thread[pool-1-thread-6,5,main] : 准备好了 Thread[pool-1-thread-1,5,main]
	 *     : 准备好了 Thread[pool-1-thread-5,5,main] : 准备好了
	 *     Thread[pool-1-thread-7,5,main] : 准备好了 Thread[pool-1-thread-9,5,main]
	 *     : 准备好了 Thread[pool-1-thread-8,5,main] : 准备好了
	 *     Thread[pool-1-thread-10,5,main] : 准备好了 等待线程:0 任务完成!
	 */
	@Test
	public void test01() {
		CountDownLatch countDownLatch = new CountDownLatch(10);
		for (int i = 1; i <= 10; i++) {
			pool.execute(new CountDownThread(countDownLatch, i));
		}
		try {
			countDownLatch.await();
			Thread.sleep(200);
			System.out.println("等待线程:" + countDownLatch.getCount());
			System.out.println("任务完成!");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 证明 CyclicBarrier适用场景和基本用法
	 * 
	 * @输出 Thread[pool-1-thread-1,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-2,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-5,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-6,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-8,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-9,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-4,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-3,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-7,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-10,5,main] : 正在等待其他考生准备就绪
	 *     Thread[pool-1-thread-1,5,main] : 我准备就绪 Thread[pool-1-thread-2,5,main]
	 *     : 我准备就绪 Thread[pool-1-thread-5,5,main] : 我准备就绪
	 *     Thread[pool-1-thread-6,5,main] : 我准备就绪
	 *     Thread[pool-1-thread-10,5,main] : 我准备就绪
	 *     Thread[pool-1-thread-8,5,main] : 我准备就绪 Thread[pool-1-thread-9,5,main]
	 *     : 我准备就绪 Thread[pool-1-thread-3,5,main] : 我准备就绪
	 *     Thread[pool-1-thread-7,5,main] : 我准备就绪 Thread[pool-1-thread-4,5,main]
	 *     : 我准备就绪
	 * 
	 * @throws InterruptedException
	 * @throws BrokenBarrierException
	 */
	@Test
	public void test02() throws InterruptedException, BrokenBarrierException {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
		for (int i = 1; i <= 10; i++) {
			pool.execute(new CyclicBarrierThread(cyclicBarrier));
		}
		Thread.sleep(2000);
	}

	class ExchangerThread implements Runnable {
		Exchanger<List<String>> exchanger = null;
		List<String> list = new ArrayList<String>();

		public ExchangerThread(Exchanger<List<String>> exchanger) {
			super();
			this.exchanger = exchanger;

		}

		@Override
		public void run() {
			try {
				list.add("value-" + new Random().nextInt(5));
				list.add("value-" + new Random().nextInt(5));
				list.add("value-" + new Random().nextInt(5));
				System.out.println("\n" + Thread.currentThread() + " : before -> ");
				print(list);
				List<String> list02 = exchanger.exchange(list);
				System.out.println("\n" + Thread.currentThread() + " : after -> ");
				print(list02);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void print(List<String> list) {
			for (String str : list) {
				System.out.print(str + " ");
			}
		}
	}

	class PhaserThread implements Runnable {
		Phaser phaser = null;

		public PhaserThread(Phaser phaser) {
			super();
			this.phaser = phaser;
		}

		@Override
		public void run() {
			phaser.arriveAndAwaitAdvance();
			System.out.println(Thread.currentThread() + " 完成语文考试");
			phaser.arriveAndAwaitAdvance();
			System.out.println(Thread.currentThread() + " 完成数学考试");
			phaser.arriveAndAwaitAdvance();
			System.out.println(Thread.currentThread() + " 完成英语考试");
			phaser.arriveAndAwaitAdvance();
		}
	}

	class SemaphoreThread implements Runnable {

		Semaphore semaphore = null;

		public SemaphoreThread(Semaphore semaphore) {
			super();
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				Thread.sleep(1000);
				System.out.println(
						Thread.currentThread() + " : 我已到达考场教室准备考试, 还剩" + semaphore.availablePermits() + "人没到考场");
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	class CountDownThread implements Runnable {
		CountDownLatch countDownLatch = null;
		Integer index = null;

		public CountDownThread(CountDownLatch countDownLatch, Integer index) {
			super();
			this.countDownLatch = countDownLatch;
			this.index = index;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(10);
				System.out.println(Thread.currentThread() + " : 准备好了");
				countDownLatch.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	class CyclicBarrierThread implements Runnable {

		CyclicBarrier cyclicBarrier = null;

		public CyclicBarrierThread(CyclicBarrier cyclicBarrier) {
			super();
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " : 正在等待其他考生准备就绪  ");
			try {
				cyclicBarrier.await();
				System.out.println(Thread.currentThread() + " : 我准备就绪");

			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

		}

	}

	class MyPhaser extends Phaser {

		@Override
		protected boolean onAdvance(int phase, int registeredParties) {
			switch (phase) {
			case 0:
				return startStep();
			case 1:
				return firstStep();
			case 2:
				return secondStep();
			case 3:
				return finish();
			default:
				return true;
			}
		}

		private boolean startStep() {
			System.out.println("语文考试开始");
			return false;
		}

		private boolean firstStep() {
			System.out.println("数学考试开始");
			return false;
		}

		private boolean secondStep() {
			System.out.println("英语考试开始");
			return false;
		}

		private boolean finish() {
			System.out.println("所有考试结束");
			return true;
		}

	}

}
