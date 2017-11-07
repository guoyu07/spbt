package com.eshore.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * sleep & notify & wait & yield & join
 * 
 * @author hechao
 * @desc sleep是线程类方法 wait是所有Object类都有的方法
 * @desc sleep让该线程在不放弃所持有对象锁的前提下进入阻塞状态, 而一定时间后该线程会被唤醒进入就绪状态, wait和notify是配套使用,
 *       且出现在同步块或同步方法中, 表示线程释放该对象的锁, 提供给其他线程使用, 使用完毕后等待着该对象notify方法的调用,
 *       由此准备重新占有该对象的锁, join是指当前线程会因为等待指定线程运行结束而进入阻塞状态, 指定线程运行结束后当前线程会被唤醒并继续执行,
 *       yield是停止当前线程, 让同等优先权的线程运行, 若没有同等优先线程, 该方法不起作用
 */
public class SleepTest {

	public long startTime;
	public long endTime;
	public String testName = "    #############          sleep & wait         #############    ";
	public ExecutorService pool = null;

	@Before
	public void fun01() {
		System.out.println(testName + " started ...\n");
		startTime = System.currentTimeMillis();
		pool = Executors.newFixedThreadPool(10);
	}

	@After
	public void fun99() {
		endTime = System.currentTimeMillis();
		System.out.println("\n " + testName + "ended by taking " + (endTime - startTime) + " ms");
		pool.shutdown();
	}

	/**
	 * @输出 begin finght for lock of info thread - 1 : work begin Info
	 *     [name=hechao, cash=110] 1 :work over
	 * @desc 证明wait和notify是配套使用, 且出现在同步块或同步方法中, 表示线程释放该对象的锁, 提供给其他线程使用,
	 *       使用完毕后等待着该对象notify方法的调用, 由此准备重新占有该对象的锁
	 */
	@Test
	public void test() throws InterruptedException {
		Info info = new Info("hechao", 100);

		Thread thread01 = new Thread(new MyThread(1, info));
		thread01.start();
		synchronized (info) {
			System.out.println("begin finght for lock of info  ");
			info.wait();
		}
	}

	@Test
	public void test02() throws InterruptedException {
		Info info = new Info("hechao", 100);
		Thread thread01 = new Thread(new MyThread02(1, info));
		Thread thread02 = new Thread(new MyThread02(2, info));
		Thread thread03 = new Thread(new MyThread02(3, info));
		thread01.start();
		thread02.start();
		thread03.start();
		// thread01.yield();
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
				Thread.sleep(200);
				doWork04();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void doWork04() throws InterruptedException {
			System.out.println("thread - " + index + " : work begin ");
			synchronized (info) {
				info.cash += 10;
				Thread.sleep(1000);
				System.out.println(info.toString());
				info.notify();
			}
			System.out.println("thread - " + index + " :work over ");
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
			try {
				Thread.sleep(200);
				doWork04();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void doWork04() throws InterruptedException {
			System.out.println("thread - " + index + " : work begin ");
			info.cash += 10;
			Thread.sleep(1000);
			System.out.println(info.toString());
			System.out.println("thread - " + index + " :work over ");
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
