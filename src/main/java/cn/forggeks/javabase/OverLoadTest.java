package cn.forggeks.javabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * overload & override
 * 
 * @author hechao
 * @desc overload是重载, 方法名称一样, 而且参数个数类型顺序必须完全一致, override重写, 方法名称一样
 *       ,但是参数个数或类型或顺序一定要有变化
 */
public class OverLoadTest {

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

	}

	public void sayHello(String name, Integer num, Boolean boolean1) {

	}

	public void sayHello1(String name, Integer num, Boolean boolean1) {

	}

	public void sayHello(String name, Boolean boolean1) {

	}

	public void sayHello(Integer num, String name, Boolean boolean1) {

	}

	class TestClass01 extends OverLoadTest {
		@Override
		public void sayHello(String name, Integer num, Boolean boolean1) {

		}
	}

}
