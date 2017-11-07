package com.eshore.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 在循环中检查等待的原因
 * 
 * @author hechao 循环等待和wait/notify都是等待, 但是循环等待是忙等, 也就是一直占用这cpu在循环机检测等待, 而后者是休眠等待
 */
public class CyclicWaitTest {

	public long startTime;
	public long endTime;
	public String testName = "#############         在循环中检查等待的原因       ###############";
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

	Product p = null;

	/**
	 * @输出 宝贝已发货 收到请回复 宝贝已收到 谢谢 交易完成
	 */
	@Test
	public void test() {
		p = new Product("profuct");
		Thread thread = new Thread(new Producer());
		Thread thread2 = new Thread(new Consumer());
		thread.start();
		thread2.start();

	}
	/**
	 * @输出 宝贝已发货 收到请回复 宝贝已收到 谢谢 交易完成
	 */
	@Test
	public void tes2t() {
		p = new Product("profuct");
		Thread thread = new Thread(new Producer2());
		Thread thread2 = new Thread(new Consumer2());
		thread.start();
		thread2.start();
		
	}

	class Producer implements Runnable {

		@Override
		public void run() {
			synchronized (p) {
				try {
					System.out.println("宝贝已发货 收到请回复");
					p.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("交易完成");
		}

	}

	class Consumer implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (p) {
				p.notifyAll();
				System.out.println("宝贝已收到 谢谢");
			}
		}

	}
	
	class Producer2 implements Runnable {
		
		@Override
		public void run() {
			System.out.println("宝贝已发货 收到请回复");
			while(!p.flag){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("交易完成");
		}
		
	}
	
	class Consumer2 implements Runnable {
		
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.flag=true;
			System.out.println("宝贝已收到 谢谢");
		}
		
	}

	class Product {
		String name;
		Boolean flag=false;
		public Product(String name) {
			super();
			this.name = name;
		}

	}

}
