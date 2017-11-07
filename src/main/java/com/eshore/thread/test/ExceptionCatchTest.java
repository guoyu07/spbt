package com.eshore.thread.test;

import java.lang.Thread.UncaughtExceptionHandler;

import org.junit.Test;

/**
 * 线程异常终止及其捕获办法
 * 
 * @author hechao
 *
 * @desc 如果想要在线程抛出uncatchException异常后再次尝试启动线程, 应该提前调用线程的setUncaughtExceptionHandler方法来捕获异常
 *       以此保证总有一个线程执行任务
 */
public class ExceptionCatchTest {

	MyThread task = new MyThread();
	MyThread02 task02 = new MyThread02();

	/**
	 * @输出 Thread-0 : 任务1完成 Thread-0 : 任务2抛出异常 Thread-0抛出的异常被捕获并处理 Thread-1 :
	 *     任务2抛出异常 Thread-1抛出的异常被捕获并处理 Thread-2 : 任务2完成 Thread-2 : 任务3完成
	 *     Thread-2 : 任务4完成 Thread-2 : 任务5完成 Thread-2 : 任务6完成 Thread-2 : 任务7抛出异常
	 *     Thread-2抛出的异常被捕获并处理 Thread-3 : 任务7完成 Thread-3 : 任务8完成 Thread-3 :
	 *     任务9抛出异常 Thread-3抛出的异常被捕获并处理 Thread-4 : 任务9抛出异常 Thread-4抛出的异常被捕获并处理
	 *     Thread-5 : 任务9完成 Thread-5 : 任务10抛出异常 Thread-5抛出的异常被捕获并处理 Thread-6 :
	 *     任务10完成 Thread-6 : 任务11完成 Thread-6 : 任务12完成 Thread-6 : 任务13完成 Thread-6
	 *     : 任务14完成 Thread-6 : 任务15完成 Thread-6 : 任务16完成 Thread-6 : 任务17完成
	 *     Thread-6 : 任务18抛出异常 Thread-6抛出的异常被捕获并处理 Thread-7 : 任务18抛出异常
	 *     Thread-7抛出的异常被捕获并处理 Thread-8 : 任务18完成 Thread-8 : 任务19完成 Thread-8 :
	 *     任务20完成
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void test01() throws InterruptedException {
		Thread thread = new Thread(task);
		 thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(t.getName() + "抛出的异常被捕获并处理");
				try {
					test01();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}); 
		thread.start();
		Thread.sleep(5000);
	}
	
	@Test
	public void test02() throws InterruptedException{
		Thread thread = new Thread(task02);
		thread.start();
		Thread.sleep(5000);
	}

	class MyThread02 implements Runnable {

		public Integer index = 1;

		@Override
		public void run() {
			while (index <= 10) {
				index++;
				if (System.currentTimeMillis() % 3 == 0) {
					System.out.println(Thread.currentThread().getName() + " : 任务" + (index) + "抛出异常");
					try {
						throw new RuntimeException("模拟异常");
					} catch (Exception e) {
						try {
							System.out.println(Thread.currentThread().getName() + " : 任务" + (index) + "异常已捕获");
							test02();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}

		}

	}

	class MyThread implements Runnable {

		public Integer index = 1;

		@Override
		public void run() {
			while (index <= 20) {
				if (System.currentTimeMillis() % 2 == 0) {
					System.out.println(Thread.currentThread().getName() + " : 任务" + (index) + "抛出异常");
					// throw new InterruptedException();
					throw new RuntimeException("模拟异常");
				}
				System.out.println(Thread.currentThread().getName() + " : 任务" + (index++) + "完成");
			}

		}

	}

}
