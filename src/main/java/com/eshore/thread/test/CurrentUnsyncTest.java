package com.eshore.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 同步异步使用场景
 * 
 * @author hechao
 * @desc 同步和异步关注的是消息通信机制, 同步指线程中有的调用执行后需要有反馈结果才能进行下一步,
 *       异步是指线程中所有调用在执行后不需要反馈结果就能一直执行下去
 */
public class CurrentUnsyncTest {

	public long startTime;
	public long endTime;
	public String testName = "#############          同步异步使用场景         ###############";
	public ExecutorService pool = null;

	@Before
	public void fun01() {
		System.out.println(testName + "\n");
		startTime = System.currentTimeMillis();
		pool = Executors.newFixedThreadPool(10);
	}

	@After
	public void fun99() {
		endTime = System.currentTimeMillis();
		System.out.println("\n" + testName);
		System.out.println("OK ! (" + (endTime - startTime) + " ms)");
		pool.shutdown();
	}

	/**
	 * @输出 异步结果-> thread - 1 : work result : Info [name=hechao, cash=0] thread -
	 *     2 : work result : Info [name=hechao, cash=-100] thread - 3 : work
	 * @desc result : Info [name=hechao, cash=-200] 证明了 同步和异步关注的是消息通信机制,
	 *       同步指线程中有的调用执行后需要有反馈结果才能进行下一步, 异步是指线程中所有调用在执行后不需要反馈结果就能一直执行下去
	 * @throws InterruptedException
	 */
	@Test
	public void test() throws InterruptedException {
		Info info = new Info("hechao", 100);

		Thread t1 = new Thread(new MyThread02(1, new Info("hechao", 100)));
		Thread t2 = new Thread(new MyThread02(2, new Info("hechao", 100)));
		Thread t3 = new Thread(new MyThread02(3, new Info("hechao", 100)));

		t1.start();
		t2.start();
		t3.start();

	}

	/**
	 * @输出 同步结果-> first wait begin thread - 1 : work result : Info [name=hechao,
	 *     cash=200] first changed : Info [name=hechao, cash=200] second wait
	 *     begin thread - 3 : work result : Info [name=hechao, cash=300] thread
	 *     - 2 : work result : Info [name=hechao, cash=400] second changed :
	 *     Info [name=hechao, cash=400] all done result
	 * 
	 * @desc 证明了 同步和异步关注的是消息通信机制, 同步指线程中有的调用执行后需要有反馈结果才能进行下一步,
	 *       异步是指线程中所有调用在执行后不需要反馈结果就能一直执行下去
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void test1() throws InterruptedException {
		Info info = new Info("hechao", 100);

		Thread t1 = new Thread(new MyThread(1, info));
		Thread t2 = new Thread(new MyThread(2, info));
		Thread t3 = new Thread(new MyThread(3, info));
		t1.start();
		t2.start();
		t3.start();

		synchronized (info) {
			System.out.println(" first wait begin");
			info.wait();
			System.out.println("first changed : " + info);
			System.out.println("second wait begin");
			info.wait();
			System.out.println("second changed : " + info);
			System.out.println("all done");
		}

	}

	/**
	 * 
	 * @author hechao
	 *
	 */
	class MyThread implements Runnable {
		public Integer index;
		public Info info;

		public MyThread(Integer index, Info info) {
			super();
			this.index = index;
			this.info = info;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(100);
				doWork04();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void doWork04() throws InterruptedException {
			synchronized (info) {
				info.cash += 100;
				Thread.sleep(100);
				System.out.println("thread - " + index + " : work result :  " + info.toString());
				info.notify();
			}
		}

	}

	/**
	 * 
	 * @author hechao
	 *
	 */
	class MyThread02 implements Runnable {
		public Integer index;
		public Info info;

		public MyThread02(Integer index, Info info) {
			super();
			this.index = index;
			this.info = info;
		}

		@Override
		public void run() {
			info.cash -= 100;
			System.out.println("thread - " + index + " : work result :  " + info.toString());
		}

	}

	/**
	 * 
	 * @author hechao
	 *
	 */
	class Info {
		String name;
		Integer cash;

		public Info(String name, Integer cash) {
			super();
			this.name = name;
			this.cash = cash;
		}

		@Override
		public String toString() {
			return "Info [name=" + name + ", cash=" + cash + "]";
		}

	}

}
