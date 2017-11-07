package com.eshore.thread.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * jvm内存模型
 * 
 * @author hechao
 * 
 * @desc jvm内存模型中包含包含5个重要部分, jvm栈, 程序计数器, 本地方法栈, jvm堆, 方法区
 * @desc 通过改变程序计数器的值来完成对字节码指令的基础操作
 * @desc jvm栈表示java方法执行的内存区域, 每个java方法执行都会创建一个栈帧用于存放局部变量等信息
 * @desc 本地方法栈与jvm栈类似, 它表示native方法执行的内存区域
 * @desc jvm堆存放几乎所有对象, 除了栈上分配,标量替换, 是jvm管理的最大的一块内存, 也是垃圾收集器的主要活动区域,
 *       通常采用分代收集算法来gc, 同时从gc的角度jvm堆还可以分为新生代和老生代,从内存分配角度可以分为私有分配缓冲区
 * @desc 方法区存放类信息, 常量, 静态变量, 编译过的代码等
 */
public class JVMModelTest {

	public long startTime;
	public long endTime;
	public String testName = "#############           jvm内存模型          ###############";
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

	List<JVMModelTest> list = new ArrayList<JVMModelTest>();

	@Test
	public void test01() {
		while (true) {
			pool.execute(new MyThread());
		}
	}

	class MyThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				list.add(new JVMModelTest());
			}
		}

	}

}
