package cn.forggeks.javabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * static & final &finally & finalize
 * 
 * @author hechao
 * @desc static修饰成员变量和成员方法时表示他们被类的所有实例共享, 可以通过类名直接调用 ,
 *       static修饰代码块时,会在jvm加载该类时自动执行,且只执行一次
 * @desc final修饰变量时表示该变量不允许被修改, 而且必须在使用时给定初值, final修饰方法时表示该方法不会被覆盖 ,
 *       final修饰类时表示该类不允许被继承 , 因此一个类不可能同时被adbstract和final同时修饰
 * @desc finally与trycatch配合使用,finally代码块始终会执行
 * @desc finalize 垃圾回收器执行时,被回收对象会调用finalize方法
 * 
 */
public class StaticFinalTest {

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

	public void sayHello() {
		System.out.println("hello ");
	}

	public static void sayHello02() {
		System.out.println("hello ");
	}

	public static String field01 = "string01";

	static {
		System.out.println("this is static block");
	}

	/**
	 * 证明static块会在类方法被调用时自动运行, static修饰的变量只能初始化一次
	 * 
	 * @输出 hello hello 第一次初始化:string01 初始值已改变 , 此时new出来的对象初始值:string02
	 * 
	 */
	@Test
	public void Test() {
		StaticFinalTest test = new StaticFinalTest();
		test.sayHello();
		StaticFinalTest.sayHello02();

		System.out.println("第一次初始化:" + test.field01);
		test.field01 = new String("string02");

		StaticFinalTest test02 = new StaticFinalTest();
		System.out.println("初始值已改变 , 此时new出来的对象初始值:" + test02.field01);

		// TestClass testClass=new TestClass("testname");

	}

	public final String finalFiels = "asd";

	/**
	 * @desc 此处报错 证明final修饰变量时表示该变量不允许被修改
	 */
	@Test
	public void test02() {
		// finalFiels=new String("dddd");
	}

	/*
	 * @desc 此处报错说明 final修饰方法时表示该方法不会被覆盖
	 * 
	 * @desc public final void finaMethod() { } class TestClass02 extends
	 * StaticFinalTest { public void finaMethod() { } }
	 */

	/*
	 * @desc final修饰类时表示该类不允许被继承 , 因此一个类不可能同时被adbstract和final同时修饰
	 * 
	 * @desc final class FinalClass{ } class Child extends FinalClass{ }
	 */

}
