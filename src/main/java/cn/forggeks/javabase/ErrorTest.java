package cn.forggeks.javabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * error & exception
 * 
 * @author hechao
 * @desc error指jvm内存不足 , 系统崩溃, 虚拟机错误, 方法调用栈溢出等, exception指程序可以处理的异常, 可以被捕获和恢复,
 *       它分为运行时异常和受检查异常, 运行时异常可以通过编译, 但是运行时会报错 , 受检查异常不能通过编译,
 *       除非用trycatcatch捕获或者声明抛出异常, 常见的运行时异常包括数组越界, 空指针 , 算数异常, 参数不合法异常
 */
public class ErrorTest {
	public long startTime;
	public long endTime;
	public String testName = "##############          error & exception       #################";

	@Before
	public void fun01() {
		System.out.println(testName + "  \n");
		startTime = System.currentTimeMillis();
	}

	@After
	public void fun99() {
		endTime = System.currentTimeMillis();
		System.out.println("\n " + testName + " \n  OK ! " + (endTime - startTime) + " ms");
	}

	@Test
	public void test() {

		int i = 12 / 0;

	}
}
