package com.eshore.thread.test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * volatile & atomic
 * 
 * @author hechao
 * @desc volatile保证变量的线程可见性, 不保证其原子性, 它适合在只有get和set操作的场景,
 *       一旦有getAndOperate例如自增等不具有原子性的操作, 那么就不能保证变量的线程安全, jvm中写操作之后会放一个内存屏障,
 *       表示之后所有读都将是最新的, 屏障之前的指令先执行, 执行完之后强制更新cpu缓存, 那么一旦有b线程在a线程执行到一半时更新了cpu缓存,
 *       然后a线程执行完之后也将错误的值更新到cpu缓存
 * @desc atomic既能保证变量线程可见性又能保证原子性, 这是由于atomic即用到了volatile保证线程可见性,
 *       又用了cas无锁同步算法保证原子性, 具体过程是cpu想改内存中某个值的时候, 会先与备份的值比较下, 若发现被更改则操作失败,
 *       没有更改则cas操作就成功了
 */
public class VolatileTest {

	public long startTime;
	public long endTime;
	public String testName = "#############           volatile & atomic         ###############";
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
		// System.out.println(num);
		System.out.println(a.get());
	}

	public volatile Integer num = 0;

	/**
	 * 证明volatile不具有原子操作性
	 * 
	 * @输出 Thread[pool-1-thread-1,5,main] before : 0
	 *     Thread[pool-1-thread-1,5,main] after : 100
	 *     Thread[pool-1-thread-2,5,main] before : 100
	 *     Thread[pool-1-thread-2,5,main] after : 200
	 *     Thread[pool-1-thread-4,5,main] before : 200
	 *     Thread[pool-1-thread-4,5,main] after : 300
	 *     Thread[pool-1-thread-5,5,main] before : 300
	 *     Thread[pool-1-thread-5,5,main] after : 400
	 *     Thread[pool-1-thread-8,5,main] before : 400
	 *     Thread[pool-1-thread-8,5,main] after : 500
	 *     Thread[pool-1-thread-9,5,main] before : 500
	 *     Thread[pool-1-thread-3,5,main] before : 500
	 *     Thread[pool-1-thread-9,5,main] after : 633
	 *     Thread[pool-1-thread-3,5,main] after : 646
	 *     Thread[pool-1-thread-6,5,main] before : 646
	 *     Thread[pool-1-thread-6,5,main] after : 746
	 *     Thread[pool-1-thread-7,5,main] before : 746
	 *     Thread[pool-1-thread-7,5,main] after : 846
	 *     Thread[pool-1-thread-10,5,main] before : 846
	 *     Thread[pool-1-thread-10,5,main] after : 946 OK ! (3010 ms) 946
	 */
	@Test
	public void test01() {
		for (int i = 1; i <= 10; i++) {
			pool.execute(new VolatileThread());
		}

	}

	AtomicInteger a = new AtomicInteger(0);

	/**
	 * @输出 Thread[pool-1-thread-1,5,main] before : 0
	 *     Thread[pool-1-thread-1,5,main] after : 100
	 *     Thread[pool-1-thread-5,5,main] before : 100
	 *     Thread[pool-1-thread-5,5,main] after : 200
	 *     Thread[pool-1-thread-3,5,main] before : 200
	 *     Thread[pool-1-thread-3,5,main] after : 300
	 *     Thread[pool-1-thread-4,5,main] before : 300
	 *     Thread[pool-1-thread-7,5,main] before : 300
	 *     Thread[pool-1-thread-4,5,main] after : 400
	 *     Thread[pool-1-thread-7,5,main] after : 500
	 *     Thread[pool-1-thread-8,5,main] before : 500
	 *     Thread[pool-1-thread-8,5,main] after : 600
	 *     Thread[pool-1-thread-2,5,main] before : 600
	 *     Thread[pool-1-thread-2,5,main] after : 700
	 *     Thread[pool-1-thread-9,5,main] before : 700
	 *     Thread[pool-1-thread-6,5,main] before : 747
	 *     Thread[pool-1-thread-9,5,main] after : 800
	 *     Thread[pool-1-thread-6,5,main] after : 900
	 *     Thread[pool-1-thread-10,5,main] before : 900
	 *     Thread[pool-1-thread-10,5,main] after : 1000
	 * 
	 * 
	 */
	@Test
	public void test02() {
		for (int i = 1; i <= 10; i++) {
			pool.execute(new AtomicThread());
		}
	}

	class VolatileThread implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " before : " + num);
			for (int i = 1; i <= 100; i++) {
				num++;
			}
			System.out.println(Thread.currentThread() + " after : " + num);

		}

	}

	class AtomicThread implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " before : " + a.get());
			for (int i = 1; i <= 100; i++) {
				a.incrementAndGet();
			}
			System.out.println(Thread.currentThread() + " after : " + a.get());
		}
	}

}
