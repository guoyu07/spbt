package cn.forggeks.javabase;

import java.awt.List;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * == & equals
 * 
 * @author hechao
 * @desc Object对象equals方法比较的就是==所比较的堆内存地址, 但是很多类为了实现方便的比较, 都覆盖了eqauls方法 比如
 *       String ArrayList , 这种情况下equals是值比较 , ==是堆内存地址比较 , equals为true的情况下 ,
 *       不一定==为true , 但是hashcode一定相等 , ==为true的情况下, eqauls一定为true ,
 *       但hashcode不一定相等 . 因此 ,
 *       集合对象添加元素时首先会判断判断又没有与它equals为true的元素,如果没有就说明没有同样hashcode的元素,可以安全插入,
 *       如果有元素与它equals为true, 那么继续比较它们的hashcode是否一样, 不一样才可以插入
 *
 */
public class EqualsTest01 {

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
	public void Test(){
		
	}

	/**
	 * 证明 == 和 equals 是有区别的
	 * 
	 * @输出 403501971 2051753018 a1==a2 : false a1 equals a2 : false 97 97 97
	 *     s1==s2 : false s1 equals s2 : true s2 equals s3 : true 'a' == 'a' :
	 *     true 128 128 list1.equals(list2)true
	 */
	@Test
	public void test01() {

		EqualsTest01 a1 = new EqualsTest01();
		EqualsTest01 a2 = new EqualsTest01();
		System.out.println(a1.hashCode() + "  " + a2.hashCode());
		System.out.print("a1==a2 : ");
		System.out.println(a1 == a2);
		System.out.print("a1 equals a2 : ");
		System.out.println(a1.equals(a2));

		String s1 = "a";
		String s2 = new String("a");
		String s3 = new String();
		s3 = "a";
		System.out.println(s1.hashCode() + " " + s2.hashCode() + "  " + s3.hashCode());
		System.out.println("s1==s2 :");
		System.out.println(s1 == s2);
		System.out.println("s1 equals s2 :");
		System.out.println(s1.equals(s2));
		System.out.println("s2 equals s3 :");
		System.out.println(s2.equals(s3));
		System.out.println(" 'a' == 'a' :");
		System.out.println("a" == "a");

		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();

		list1.add("a");
		list2.add("a");

		System.out.println(list1.hashCode() + " " + list2.hashCode());
		System.out.print("list1.equals(list2)");
		System.out.println(list1.equals(list2));

	}

}
