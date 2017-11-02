package cn.forggeks.javabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * static & final &finally & finalize
 * 
 * @author hechao
 * @desc static修饰变量表示该变量只允许被初始化一次 , static修饰方法时表示该方法只能被所属类访问,不允许通过实例访问
 * static修饰代码块时,会在该类创建实例或者调用该类静态方法时自动执行,static修饰内部类时,表示...??? 
 *@desc  final修饰变量时表示该变量不允许被修改, final修饰方法时表示该方法不会被子类继承  final修饰类时表示改类不允许被继承 
 *@desc finally与trycatch配合使用,finally代码块始终会执行,....
 *@desc finalize  ....
 *Object
 * 
 */
public class StaticFinalTest {

	public long startTime;
	public long endTime;
	public String testName = "##############       == & equals         #################";

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
	public void Test() {

	}

}
