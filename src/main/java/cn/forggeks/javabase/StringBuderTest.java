package cn.forggeks.javabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试 String & StringBuffer $StringBuilder
 * 
 * @author hechao
 * @desc string是不可变对象,stringbuffer是可变对象 , stringbuffer是线程安全, stringbuilder不是线程安全
 */
public class StringBuderTest {

	public long startTime;
	public long endTime;

	@Before
	public void fun01() {
		System.out.println("[ 测试 String & StringBuffer $StringBuilder ] started ...");
		startTime = System.currentTimeMillis();
	}

	@After
	public void fun99() {
		endTime = System.currentTimeMillis();
		System.out.println(
				"[ 测试 String & StringBuffer $StringBuilder ] ended by taking " + (endTime - startTime) + " ms");
	}

	/**
	 * 证明 string是不可变对象,stringbuffer是可变对象
	 * 
	 * @output 96354 -1424388898 403501971 403501971
	 */
	@Test
	public void fun02() {
		String str1 = new String("abc");
		System.out.println(str1.hashCode());
		str1 += "acb";
		System.out.println(str1.hashCode());

		StringBuffer sb1 = new StringBuffer("ddd");
		System.out.println(sb1.hashCode());
		sb1.append("asd");
		System.out.println(sb1.hashCode());
	}

	/**
	 * stringbuffer是线程安全, stringbuilder不是线程安全
	 */
	@Test
	public void fun03() {
		final String stri = new String("123");
		final StringBuffer sBuffer = new StringBuffer("123");
		final StringBuilder stringBuilder = new StringBuilder("123");
		System.out.println(sBuffer.toString());
		System.out.println(stringBuilder.toString());
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 50; i++) {
			pool.execute(new Runnable() {
				public void run() {
					sBuffer.append("a");
					stringBuilder.append("a");
				}
			});
		}
		System.out.println(sBuffer.length());
		System.out.println(stringBuilder.length());

		pool.shutdown();
	}

}
