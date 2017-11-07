package com.eshore.thread.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Runnable & Callable & Executor & Future & FutureTask
 * 
 * @author hechao
 * @desc runnable执行任务后没有返回结果, 只有一个run方法, 无法在run中抛出异常,
 * @desc callable执行任务后有Future返回结果, 只有一个call方法 , 可以在call中抛出异常
 * @desc Future保存异步计算结果, cancel可以中断任务, get可以阻塞式等待任务结果, isDone判断任务是否完成
 * @desc FutureTask实现了Future和Runnable接口, 这样它就可以通过封装Future或Runnable对象来初始化线程
 */
public class RunnableTest {
	public long startTime;
	public long endTime;
	public String testName = "#############           runnable & callable         ###############";
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
	 * 证明callable 和 runnable 有无返回任务的区别, 以及callable与Future配合的过程
	 */
	@Test
	public void test05() {

		Callable<Integer> callable = new CallableTest();
		Callable<Integer> callable2 = new CallableTest();
		Callable<Integer> callable3 = new CallableTest();
		Future<Integer> future = pool.submit(callable);
		Future<Integer> future2 = pool.submit(callable2);
		Future<Integer> future3 = pool.submit(callable3);
		try {
			Integer rs = future.get();
			Integer rs2 = future2.get();
			Integer rs3 = future3.get();
			future.get(10, TimeUnit.MILLISECONDS);
			future.cancel(true);
			future.isDone();
			System.out.println(rs + rs2 + rs3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

	}

	/**
	 * FutureTask用法
	 */
	@Test
	public void test06() {
		Callable<Integer> callable = new CallableTest();
		FutureTask<Integer> task = new FutureTask<Integer>(callable);
		Thread thread = new Thread(task);
		thread.start();
	}

	class CallableTest implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			return 1;
		}
	}

}
