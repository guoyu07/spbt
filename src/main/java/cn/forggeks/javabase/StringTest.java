package cn.forggeks.javabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试 String & StringBuffer $StringBuilder
 *  @author hechao
 *	string是不可变对象,stringbuffer是可变对象
 *  stringbuffer不是线程安全  stringbuilder是线程安全
 */
public class StringTest {

	public long startTime;
	public long endTime;

	@Before
	public void fun01() {
		System.out.println(  "[ 测试 String & StringBuffer $StringBuilder ] started ...");
		startTime = System.currentTimeMillis();
	}
	
	@After
	public void fun99() {
		endTime = System.currentTimeMillis();
		System.out.println( "[ 测试 String & StringBuffer $StringBuilder ] ended by taking " + (endTime - startTime) + " ms");
	}

	/**
	 * 证明 	string是不可变对象,stringbuffer是可变对象
	 */
	@Test
	public void fun02() {
		String str1=new String("abc");
		System.out.println(str1.hashCode());
		str1+="acb";
		System.out.println(str1.hashCode());

		StringBuffer sb1=new StringBuffer("ddd");
		System.out.println(sb1.hashCode());
		sb1.append("asd");
		System.out.println(sb1.hashCode());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
