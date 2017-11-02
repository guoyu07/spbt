package com.eshore.thread.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @desc 测试Phaser的另一种功能,将任务分阶段依次并发完成
 * @author hechao
 * @desc Phaser 一种可重用的同步屏障，功能上类似于CyclicBarrier和CountDownLatch，但使用上更为灵活。
 *       非常适用于在多线程环境下同步协调分阶段计算任务（Fork/Join框架中的子任务之间需同步时，优先使用Phaser）
 * @desc Exchanger 允许两个线程在某个汇合点交换对象，在某些管道设计时比较有用。
 * @desc http://blog.csdn.net/u010504064/article/details/45485133
 *
 */
public class MyPhaseTest {

	private static final int MAX_THREAD = 100;
	ExecutorService pool = Executors.newFixedThreadPool(MAX_THREAD);

	public static void main(String[] args) {
		MyPhaseTest test = new MyPhaseTest();
		test.fun01();
	}

	/**
	 * @desc 测试方法
	 */
	public void fun01() {
		long startTime = System.currentTimeMillis();
		MyPhaser phaser = new MyPhaser();
		List<StudentThread> list = new ArrayList<StudentThread>();
		CountDownLatch countDownLatch = new CountDownLatch(MAX_THREAD);
		for (int i = 1; i <= MAX_THREAD; i++) {
			StudentThread studentThread = new StudentThread(phaser, i, countDownLatch);
			phaser.register();
			list.add(studentThread);
		}
		for (StudentThread studentThread : list) {
			pool.execute(studentThread);
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("all exams take time " + (endTime - startTime) / 1000 + " ms ");
		pool.shutdown();
	}

	/**
	 * @desc 自定义学生线程
	 * @author hechao
	 */
	class StudentThread implements Runnable {
		MyPhaser phaser;
		Integer index;
		CountDownLatch countDownLatch;

		public StudentThread(MyPhaser phaser, Integer index, CountDownLatch countDownLatch) {
			super();
			this.phaser = phaser;
			this.index = index;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			phaser.arriveAndAwaitAdvance();
			firstExam();
			phaser.arriveAndAwaitAdvance();
			secondStep();
			phaser.arriveAndAwaitAdvance();
			thirdExam();
			phaser.arriveAndAwaitAdvance();
			countDownLatch.countDown();
		}

		private void thirdExam() {
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(5));
				System.out.println("student-" + index + " submit  third exam ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void firstExam() {
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(5));
				System.out.println("student-" + index + " submit  first exam ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void secondStep() {
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(5));
				System.out.println("student-" + index + " submit  second exam ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @desc 自定义辅助类
	 * @author hechao
	 *
	 */
	class MyPhaser extends Phaser {

		@Override
		protected boolean onAdvance(int phase, int registeredParties) {
			switch (phase) {
			case 0:
				return startStep();
			case 1:
				return firstStep();
			case 2:
				return secondStep();
			case 3:
				return finish();
			default:
				return true;
			}
		}

		private boolean startStep() {
			System.out.println("first exam start ...");
			return false;
		}

		private boolean firstStep() {
			System.out.println("second exam  start...  ");
			return false;
		}

		private boolean secondStep() {
			System.out.println("third exam start... ");
			return false;
		}

		private boolean finish() {
			System.out.println("all exam finish .");
			return true;
		}

	}

}
