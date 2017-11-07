package com.eshore.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * synchronized & lock
 * 
 * @author hechao
 *
 * @desc synchronized隐式控制同步过程, 不需要手动释放锁, 当同步块或同步方法执行结束时或者执行过程抛出异常时会自动释放锁,
 *       而且它是java关键字, 而lock显式控制同步过程, 需要在finally中释放锁, 它是一个类
 * @desc lock具有比synchronized更丰富的功能, lock可以知道锁的状态, 可以中断正在等待锁的线程, 可以提高读操作的效率,
 * @desc (非)可重入锁, 锁是基于线程的分配而不是基于同步方法或同步块,synchronized和lock都是可重入锁
 * @desc 读写锁, 将一个对象的访问分成读和写, 不允许同时写但是允许同时读,和一个写的同时多个读, 这样并发读的时候效率更高
 * @desc (非)公平锁, 公平是指等待时间越长, 那么执行顺序就越靠前, 非公平的就是不能保证这种规则, synchronized就是非公平锁,
 *       ReentranLock和ReentrantReadWriteLock默认是非公平锁, 
 * @desc (非)中断锁, 可以相应中断等待的线程的锁, synchronizd不可中断, lock可中断
 */
public class LockTest {

	public long startTime;
	public long endTime;
	public String testName = "#############           synchronized & lock         ###############";
	public ExecutorService pool = null;

	@Before
	public void fun01() {
		lock01 = new ReentrantLock();
		lock02 = new ReentrantLock();
		lock03 = new ReentrantReadWriteLock();
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

	@Test
	public void test05() {

	}

	/**
	 * 证明了lock工作的基本过程: lock显式控制同步过程, 需要在finally中释放锁
	 * 
	 * @输出 线程名pool-1-thread-1获得了锁 线程名pool-1-thread-1释放了锁 线程名pool-1-thread-2获得了锁
	 *     线程名pool-1-thread-2释放了锁
	 * 
	 */
	@Test
	public void test() {
		pool.execute(new MyThread());
		pool.execute(new MyThread());
	}

	/**
	 * 证明: lock 可以中断正在等待锁的线程, 可以提高读操作的效率
	 * 
	 * @desc 输出 t2 interrupted java.lang.InterruptedException at
	 *       java.util.concurrent.locks.AbstractQueuedSynchronizer.
	 *       doAcquireInterruptibly(Unknown Source) at
	 *       java.util.concurrent.locks.AbstractQueuedSynchronizer.
	 *       acquireInterruptibly(Unknown Source) at
	 *       java.util.concurrent.locks.ReentrantLock.lockInterruptibly(Unknown
	 *       Source) at
	 *       com.eshore.thread.test.LockTest$MyThread02.run(LockTest.java:97) at
	 *       java.lang.Thread.run(Unknown Source) Thread[Thread-0,5,main] : lock
	 *       is hold Thread[Thread-0,5,main] : lock is unlocked
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void test02() throws InterruptedException {
		Thread t1 = new Thread(new MyThread02());
		Thread t2 = new Thread(new MyThread02());
		t1.start();
		Thread.sleep(10);
		t2.start();
		Thread.sleep(10);
		t2.interrupt();
		System.out.println("t2 interrupted");
		Thread.sleep(100);
	}

	/**
	 * 证明 : 读写lock可以提高读操作效率 输出 read msg . read msg . read msg .
	 * 
	 */
	@Test
	public void test03() {
		pool.execute(new MyThread03());
		pool.execute(new MyThread03());
		pool.execute(new MyThread03());
	}

	ReadWriteLock lock03 = new ReentrantReadWriteLock();

	class MyThread03 implements Runnable {
		@Override
		public void run() {
			lock03.readLock().lock();
			try {
				System.out.println("read msg .");
			} finally {
				lock03.readLock().unlock();
			}
		}

	}

	Lock lock02 = new ReentrantLock();

	class MyThread02 implements Runnable {
		@Override
		public void run() {
			try {
				lock02.lockInterruptibly();
				Thread.sleep(50);
				System.out.println(Thread.currentThread() + " : lock is hold");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock02.unlock();
				System.out.println(Thread.currentThread() + " : lock is unlocked ");
			}
		}

	}

	public Lock lock01 = new ReentrantLock();

	class MyThread implements Runnable {
		@Override
		public void run() {
			method(Thread.currentThread());
			// method02(Thread.currentThread());
		}

		// 需要参与同步的方法
		public void method(Thread thread) {
			lock01.lock();
			try {
				System.out.println("线程名" + thread.getName() + "获得了锁");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("线程名" + thread.getName() + "释放了锁");
				lock01.unlock();
			}
		}

		public void method02(Thread thread) {
			if (lock01.tryLock()) {
				try {
					System.out.println("线程名" + thread.getName() + "获得了锁");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					System.out.println("线程名" + thread.getName() + "释放了锁");
					lock01.unlock();
				}
			} else {
				System.out.println("我是" + Thread.currentThread().getName() + "有人占着锁，我就不要啦");
			}
		}

	}
}
