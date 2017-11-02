package cn.forggeks.javabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * jvm生成class过程 , 以及类加载机制
 * 
 * @author hechao
 * @desc 首先编译器 将java文件编译成字节码组成的class文件,
 *       然后jvm就可以执行class文件,并根据当前操作系统来生成可执行的汇编指令,由此实现jvm跨平台特性 最后操作系统执行
 * @desc jvm首先通过类名来查找相应class文件的过程就是类加载过程, 其中运用了双亲委派加载机制,
 *       具体是应用加载器通过classname检验是否被它加载过, 若没有则向委派给父加载器,
 *       也就是拓展类加载器检测,若没有加载过,委派给启动类加载器加载,
 *       只有父加载器无法找到相应class时候才会让子类加载器加载,直到找到为止,找不到就抛异常classnotfound
 */
public class JvmTest01 {
	public long startTime;
	public long endTime;
	public String testName = " jvm生成class过程 , 以及类加载机制";

	@Before
	public void fun01() {
		System.out.println(testName + " started ...\n");
		startTime = System.currentTimeMillis();
	}

	@After
	public void fun99() {
		endTime = System.currentTimeMillis();
		System.out.println("\n " + testName + "ended by taking " + (endTime - startTime) + " ms");
	}

	/**
	 * 证明类加载机制应用了双亲委派模型
	 * 
	 * @输出 sun.misc.Launcher$AppClassLoader@5552bb15
	 *     sun.misc.Launcher$ExtClassLoader@2a788b76
	 */
	@Test
	public void test01() {
		ClassLoader classLoader = JvmTest01.class.getClassLoader();
		while (classLoader != null) {
			System.out.println(classLoader.toString());
			classLoader = classLoader.getParent();
		}
	}

}
