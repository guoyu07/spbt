package com.eshore.thread.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @desc 测试Phaser辅助类是如何并发执行多个任务步骤的
 * @author hechao
 * @desc Phaser 一种可重用的同步屏障，功能上类似于CyclicBarrier和CountDownLatch，但使用上更为灵活。
 *       非常适用于在多线程环境下同步协调分阶段计算任务（Fork/Join框架中的子任务之间需同步时，优先使用Phaser） Exchanger
 *       允许两个线程在某个汇合点交换对象，在某些管道设计时比较有用。
 * @desc http://blog.csdn.net/u010504064/article/details/45485133
 */
public class PhaserTest {

	public static Integer MAX_THREAD_NUM = 10;
	public ArrayList<String> resultList = new ArrayList<String>();
	public ExecutorService pool = Executors.newFixedThreadPool(MAX_THREAD_NUM);

	public static void main(String[] args) {
		PhaserTest test = new PhaserTest();
		test.fun01("D:\\doc\\msp", "jsp", "D:\\doc\\msp", "jar", "D:\\doc\\msp", "html");
		test.pool.shutdown();
	}

	/**
	 * @desc 测试任务
	 */
	public void fun01(String str1, String str2, String str3, String str4, String str5, String str6) {
		Phaser phaser = new Phaser(3);
		pool.execute(new PhaserThread(str1, str2, phaser));
		pool.execute(new PhaserThread(str3, str4, phaser));
		pool.execute(new PhaserThread(str5, str6, phaser));
	}

	/**
	 * @desc 由phaser控制的线程
	 */
	class PhaserThread implements Runnable {

		public String filePath; // 目录
		public String keyword; // 后缀
		public Phaser phaser; // 辅助类
		public List<String> list;

		public PhaserThread(String filePath, String keyword, Phaser phaser) {
			super();
			this.filePath = filePath;
			this.keyword = keyword;
			this.phaser = phaser;
			this.list = new ArrayList<String>();
		}

		/**
		 * @desc 第一个步骤:遍历文件夹
		 */
		public void step_1_findDir(File file) {
			File list[] = file.listFiles();
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					if (list[i].isDirectory()) {
						step_1_findDir(list[i]);
					} else {
						this.list.add(list[i].getAbsolutePath());
					}
				}
			}
		}

		/**
		 * @desc 第二个步骤:筛选文件
		 */
		public void step_2_compareFile() {
			System.out.println("step-2  comparing Files ...");
			List<String> newResult = new ArrayList<String>();
			// long actualDate = new Date().getTime();
			for (int i = 0; i < list.size(); i++) {
				File file = new File(list.get(i));

				/*
				 * 修改时间 long fileDate = file.lastModified(); if (actualDate -
				 * fileDate < TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) {
				 * newResult.add(list.get(i)); }
				 */
				/* 后缀 */
				if (file.getName().endsWith(keyword)) {
					System.out.println(file.getAbsolutePath());
					list.add(file.getAbsolutePath());
				}

			}
			list = newResult;
		}

		/**
		 * @desc 展示匹配的文件
		 */
		public void step_3_showInfo() {
			for (int i = 0; i < list.size(); i++) {
				File file = new File(list.get(i));
				System.out.printf("step-3 %s: %s\n.", Thread.currentThread().getName(), file.getAbsolutePath());
			}
		}

		public boolean checkBlank() {
			if (list.isEmpty()) {
				System.out.printf("blank  %s: phase %d \n", Thread.currentThread().getName(), phaser.getPhase());
				phaser.arriveAndDeregister();
				return false;
			} else {
				System.out.printf("no-blank %s:Phase %d: %d results.\n", Thread.currentThread().getName(),
						phaser.getPhase(), list.size());
				/* 通知phaser对象当前线程已经完成当前阶段，需要被阻塞直到其他线程也都完成当前阶段 */
				phaser.arriveAndAwaitAdvance();
				return true;
			}
		}

		@Override
		public void run() {
			phaser.arriveAndAwaitAdvance();
			File file = new File(filePath);
			if (file.isDirectory()) {
				step_1_findDir(file);
			}
			checkBlank();
			step_2_compareFile();
			checkBlank();
			step_3_showInfo();
			phaser.arriveAndDeregister();

		}

	}

}
