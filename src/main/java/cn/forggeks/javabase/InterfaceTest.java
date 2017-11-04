package cn.forggeks.javabase;

import org.junit.After;
import org.junit.Before;

/**
 * interface & abstract
 * 
 * @author hechao
 * @desc interface 不能有私有方法, 默认是public abstract , 成员变量只允许定义static final类型 ,
 *       一个类可以实现多个接口
 * @desc abstract 可以有自己的数据成员, 私有方法, 非抽象方法, 抽象类也可以被继承, 但是不可以被实现, 一个类可以继承多个抽象类
 * @desc 从设计思想来看, abstract强调的是is-a的关系 , 而interface强调的是like-a的关系, 比如
 *       ,如果要设计一个类称为防盗门, 它同时具有门的属性开门和关门, 而且具有报警器的报警功能,
 *       那么我们根据abstract和interface的特性这样来设计抽象关系, 首先防盗门本质就是一扇门,所以防盗门和门是is-a的关系,
 *       其次防盗门还具有和报警器相似的报警功能, 所以防盗门和报警器应该是like-a的关系, 由此我们可以设计出一个门的抽象类和一个报警器的接口,
 *       防盗门来继承门这个抽象类同时实现报警器接口,然后重载三个方法就可以了.
 */
public class InterfaceTest {

	public long startTime;
	public long endTime;
	public String testName = "##############          interface & abstract       #################";

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

	interface MyInterface01 {

		public static final Integer NUM = 1;
		public String string = "asd";
		// private String s1="sd";

		public void fun01();
		// public static void fun02(); // Illegal modifier for the interface
		// method fun02; only public & abstract are permitted
		// public final void fun02(); // Illegal modifier for the interface
		// method fun02; only public & abstract are permitted
		// public void fun03(){
		// } //Abstract methods do not specify a body
	}

	interface MyInterface02 extends MyInterface01 {
		@Override
		public void fun01();

		public void fun03();
	}

	class MyImplenments01 implements MyInterface01 {
		@Override
		public void fun01() {
		}
	}

	class MyImplements02 implements MyInterface02 {
		@Override
		public void fun01() {
		}

		@Override
		public void fun03() {
		}

		public void print() {
			System.out.println(NUM + string);
		}
	}

	abstract class MyAbstract01 {

		public final String s1 = "12";
		private String s2 = "1we";
		public String s3 = "12e";

		public abstract void method01();

		// public void method03();
		public void method02() {
			System.out.println("abstract 01  method 02");
		}

	}

	abstract class MyAbstract02 extends MyAbstract01 {
		public abstract void method04();

		public void methhod05() {
			System.out.println("abstract 02  method 05");
		}
	}

	abstract class MyAbs03 {
		abstract void method06();
	}

	class MyImple05 extends MyAbstract02 {

		@Override
		public void method01() {
		}

		@Override
		public void method04() {
		}

	}

	class MyIm06 extends MyAbstract02 implements MyInterface02 {

		@Override
		public void fun01() {
		}

		@Override
		public void fun03() {
		}

		@Override
		public void method04() {
		}

		@Override
		public void method01() {
		}

	}

	interface Alerm {
		public void aler();
	}

	abstract class Door {
		public abstract void open();

		public abstract void close();

	}

	class AlermDoor extends Door implements Alerm {

		@Override
		public void aler() {
		}

		@Override
		public void open() {
		}

		@Override
		public void close() {
		}

	}

}
