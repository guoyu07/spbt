package com.eshore.collection.test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @desc 集合工具类测试 ConcurrentHashMap & HashMap & HashTable
 * @desc Created by hechao at 2017-10-22 17:14
 * @desc Hashtable和ConcurrentHashMap有什么分别呢?
 *       它们都可以用于多线程的环境，但是当Hashtable的大小增加到一定的时候
 *       ，性能会急剧下降，因为迭代时需要被锁定很长的时间。因为ConcurrentHashMap引入了分割(segmentation)，
 *       不论它变得多么大，仅仅需要锁定map的某个部分，而其它的线程不需要等到迭代完成才能访问map。简而言之，在迭代的过程中，
 *       ConcurrentHashMap仅仅锁定map的某个部分，而Hashtable则会锁定整个map。
 * @desc 你知道HashMap的get()方法的工作原理吗 ? HashMap是基于hashing的原理，我们使用put(key,
 *       value)存储对象到HashMap中，使用get(key)从HashMap中获取对象。当我们给put()方法传递键和值时，
 *       我们先对键调用hashCode()方法，返回的hashCode用于找到bucket位置来储存Entry对象
 * @desc HashMap和Hashtable的区别 ?
 *       HashMap几乎可以等价于Hashtable，除了HashMap是非synchronized的，并可以接受null(
 *       HashMap可以接受为null的键值(key)和值(value)，而Hashtable则不行)。
 *       HashMap是非synchronized，而Hashtable是synchronized，这意味着Hashtable是线程安全的，
 *       多个线程可以共享一个Hashtable；而如果没有正确的同步的话，多个线程是不能共享HashMap的。Java
 *       5提供了ConcurrentHashMap，它是HashTable的替代，比HashTable的扩展性更好。
 *       另一个区别是HashMap的迭代器(Iterator)是fail-fast迭代器，而Hashtable的enumerator迭代器不是fail
 *       -fast的。所以当有其它线程改变了HashMap的结构（增加或者移除元素），
 *       将会抛出ConcurrentModificationException，但迭代器本身的remove()
 *       方法移除元素则不会抛出ConcurrentModificationException异常。但这并不是一个一定发生的行为，要看JVM。
 *       这条同样也是Enumeration和Iterator的区别。
 *       由于Hashtable是线程安全的也是synchronized，所以在单线程环境下它比HashMap要慢。如果你不需要同步，只需要单一线程，
 *       那么使用HashMap性能要好过Hashtable。 HashMap不能保证随着时间的推移Map中的元素次序是不变的。
 * @desc http://blog.csdn.net/basycia/article/details/51890699
 */
public class ConcurrentHashMapTest01 {
	public static Integer MAX_CURRENT_THREAD = 1000; // 当前最大并发线程
	CountDownLatch finishSinal = null;
	ExecutorService threadPool = Executors.newFixedThreadPool(MAX_CURRENT_THREAD);

	public static void main(String[] args) {
		ConcurrentHashMapTest01 test01 = new ConcurrentHashMapTest01();
		test01.beginThreaPool();

		test01.fun01();
		test01.fun02();
		test01.fun03();

	}

	/**
	 * @desc 测试HashTable性能
	 * @desc 万级并发用时 71 ms 并发数据安全
	 */
	@SuppressWarnings("rawtypes")
	public void fun03() {
		finishSinal = new CountDownLatch(MAX_CURRENT_THREAD);
		long startTime = System.currentTimeMillis();
		Hashtable concurrentHashMap = new Hashtable();
		for (int i = 1; i <= MAX_CURRENT_THREAD; i++) {
			threadPool.execute(new MyThread02(i, finishSinal, concurrentHashMap));
		}
		try {
			finishSinal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		long period = (endTime - startTime);
		System.out.println("线程池并发测试Hashtable性能   " + period + " ms");
		System.out.println("Hashtable大小 " + concurrentHashMap.size());
	}

	/**
	 * @desc 测试HashMap性能
	 * @desc 万级并发用时 81 ms 并发数据不安全
	 * 
	 */
	@SuppressWarnings({ "rawtypes" })
	public void fun02() {
		finishSinal = new CountDownLatch(MAX_CURRENT_THREAD);
		long startTime = System.currentTimeMillis();
		HashMap hashMap = new HashMap();
		for (int i = 1; i <= MAX_CURRENT_THREAD; i++) {
			threadPool.execute(new MyThread(i, finishSinal, hashMap));
		}
		try {
			finishSinal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		long period = (endTime - startTime);
		System.out.println("线程池并发测试hashMap性能   " + period + " ms");
		System.out.println("hashMap大小 " + hashMap.size());
	}

	/**
	 * @desc 测试ConcurrentHashMap性能
	 * @desc 万级并发用时 165 ms 并发数据安全
	 */
	@SuppressWarnings({ "rawtypes" })
	public void fun01() {
		finishSinal = new CountDownLatch(MAX_CURRENT_THREAD);
		long startTime = System.currentTimeMillis();
		ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
		for (int i = 1; i <= MAX_CURRENT_THREAD; i++) {
			threadPool.execute(new MyThread(i, finishSinal, concurrentHashMap));
		}
		try {
			finishSinal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		long period = (endTime - startTime);
		System.out.println("线程池并发测试ConcurrentHashMap性能   " + period + " ms");
		System.out.println("ConcurrentHashMap大小 " + concurrentHashMap.size());
	}

	/**
	 * @desc 先打开连接池
	 */
	public void beginThreaPool() {
		threadPool.execute(new Runnable() {
			public void run() {
				System.out.println("begin...");
			}
		});
	}

	/**
	 * @desc 自定义线程
	 */
	@SuppressWarnings("rawtypes")
	class MyThread implements Runnable {
		Integer tIndex;
		CountDownLatch finishSinal;
		Map map;

		public MyThread(Integer tIndex, CountDownLatch finishSinal, Map map) {
			this.tIndex = tIndex;
			this.finishSinal = finishSinal;
			this.map = map;
		}

		@SuppressWarnings("unchecked")
		public void run() {
			for (int i = 0; i < 100; i++) {
				map.put("key-" + tIndex + "-index-" + i, tIndex + "-value");
				map.containsKey("index");
			}
			finishSinal.countDown();
		}
	}

	/**
	 * @desc 自定义线程2
	 */
	@SuppressWarnings("rawtypes")
	class MyThread02 implements Runnable {
		Integer tIndex;
		CountDownLatch finishSinal;
		Hashtable map;

		public MyThread02(Integer tIndex, CountDownLatch finishSinal, Hashtable map) {
			this.tIndex = tIndex;
			this.finishSinal = finishSinal;
			this.map = map;
		}

		@SuppressWarnings("unchecked")
		public void run() {
			// System.out.println("thread-" + tIndex + "");
			for (int i = 0; i < 100; i++) {
				map.put("key-" + tIndex + "-index-" + i, tIndex + "-value");
				map.containsKey("index");
			}
			finishSinal.countDown();
		}
	}
}
