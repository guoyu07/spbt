package cn.forggeks.javabase;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 设计百亿计算器思路
 * 
 * @author hechao
 * @desc 大整数类型:BigInteger 带精度的大浮点数类型:BigDecimal
 */
public class ComputeTest {
	public long startTime;
	public long endTime;
	public String testName = "##############         static & final &finally & finalize        #################";

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
		double number = Math.pow(2, 64);
		double number2 = Math.pow(2, 65);
		BigInteger bigInteger01 = BigInteger.valueOf((long) number);
		BigInteger bigInteger02 = BigInteger.valueOf((long) number2);
		System.out.println(bigInteger01.add(bigInteger02));
		System.out.println(bigInteger01.subtract(bigInteger02));
		System.out.println(bigInteger01.multiply(bigInteger02));
		System.out.println(bigInteger01.divide(bigInteger02));

	}

}
