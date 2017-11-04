package cn.forggeks.javabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * & & &&
 * 
 * @author hechao &是安位与运算 参与的是数对象 比如1&0=0 &&是逻辑与操作 参与的是boolean类型的对象 只有 true &&
 *         true = true
 */
public class BaseTest02 {

	public long startTime;
	public long endTime;
	public String testName = "##############       & 和 &&        #################";

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

	/**
	 * 证明参与 & 和 && 运算的对象不是一样的
	 * @输出 1&1 1 0 false 1
	 */
	@Test
	public void Test() {
		System.out.println("1&1");
		System.out.println(1 & 1);
		System.out.println(new Integer(1) & new Integer(0));
		char a = 1, b = 0;
		boolean c = false, d = true;
		System.out.println(c && d);
		System.out.println(1 & 1);

	}

}
