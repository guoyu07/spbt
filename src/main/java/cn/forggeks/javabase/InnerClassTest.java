package cn.forggeks.javabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * 内部类
 * 
 * @author hechao
 * @desc 内部类分为成员内部类, 局部内部类, 匿名内部类 , 嵌套内部类, 成员内部类不允许static变量和static方法,
 *       生成对象的方式是先new一个外部类, 然后通过Outer.Inter obj=outer.new Inner()的方式new一个内部类,
 *       局部内部类只能在作用域内使用 , 匿名内部类new的时候这个类必须事先定义
 * @内部类+接口实现闭包
 */
public class InnerClassTest {

	public long startTime;
	public long endTime;
	public String testName = "##############        内部类        #################";

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
		InnerClassTest outer = new InnerClassTest();
		InnerClassTest.InnerClass inner = outer.new InnerClass("inner02");
		inner.getLogger("").info("asd");
	}

	class InnerClass implements ILoggerFactory {

		String name;

		public InnerClass(String name) {
			super();
			this.name = name;
		}

		@Override
		public Logger getLogger(String arg0) {
			return new Logger() {
				
				@Override
				public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {
				}
				
				@Override
				public void warn(Marker arg0, String arg1, Throwable arg2) {
				}
				
				@Override
				public void warn(Marker arg0, String arg1, Object... arg2) {
				}
				
				@Override
				public void warn(Marker arg0, String arg1, Object arg2) {
				}
				
				@Override
				public void warn(String arg0, Object arg1, Object arg2) {
				}
				
				@Override
				public void warn(Marker arg0, String arg1) {
				}
				
				@Override
				public void warn(String arg0, Throwable arg1) {
				}
				
				@Override
				public void warn(String arg0, Object... arg1) {
				}
				
				@Override
				public void warn(String arg0, Object arg1) {
				}
				
				@Override
				public void warn(String arg0) {
				}
				
				@Override
				public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {
				}
				
				@Override
				public void trace(Marker arg0, String arg1, Throwable arg2) {
				}
				
				@Override
				public void trace(Marker arg0, String arg1, Object... arg2) {
				}
				
				@Override
				public void trace(Marker arg0, String arg1, Object arg2) {
				}
				
				@Override
				public void trace(String arg0, Object arg1, Object arg2) {
				}
				
				@Override
				public void trace(Marker arg0, String arg1) {
				}
				
				@Override
				public void trace(String arg0, Throwable arg1) {
				}
				
				@Override
				public void trace(String arg0, Object... arg1) {
				}
				
				@Override
				public void trace(String arg0, Object arg1) {
				}
				
				@Override
				public void trace(String arg0) {
				}
				
				@Override
				public boolean isWarnEnabled(Marker arg0) {
					return false;
				}
				
				@Override
				public boolean isWarnEnabled() {
					return false;
				}
				
				@Override
				public boolean isTraceEnabled(Marker arg0) {
					return false;
				}
				
				@Override
				public boolean isTraceEnabled() {
					return false;
				}
				
				@Override
				public boolean isInfoEnabled(Marker arg0) {
					return false;
				}
				
				@Override
				public boolean isInfoEnabled() {
					return false;
				}
				
				@Override
				public boolean isErrorEnabled(Marker arg0) {
					return false;
				}
				
				@Override
				public boolean isErrorEnabled() {
					return false;
				}
				
				@Override
				public boolean isDebugEnabled(Marker arg0) {
					return false;
				}
				
				@Override
				public boolean isDebugEnabled() {
					return false;
				}
				
				@Override
				public void info(Marker arg0, String arg1, Object arg2, Object arg3) {
				}
				
				@Override
				public void info(Marker arg0, String arg1, Throwable arg2) {
				}
				
				@Override
				public void info(Marker arg0, String arg1, Object... arg2) {
				}
				
				@Override
				public void info(Marker arg0, String arg1, Object arg2) {
				}
				
				@Override
				public void info(String arg0, Object arg1, Object arg2) {
				}
				
				@Override
				public void info(Marker arg0, String arg1) {
				}
				
				@Override
				public void info(String arg0, Throwable arg1) {
				}
				
				@Override
				public void info(String arg0, Object... arg1) {
				}
				
				@Override
				public void info(String arg0, Object arg1) {
				}
				
				@Override
				public void info(String arg0) {
				}
				
				@Override
				public String getName() {
					return null;
				}
				
				@Override
				public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
				}
				
				@Override
				public void error(Marker arg0, String arg1, Throwable arg2) {
				}
				
				@Override
				public void error(Marker arg0, String arg1, Object... arg2) {
				}
				
				@Override
				public void error(Marker arg0, String arg1, Object arg2) {
				}
				
				@Override
				public void error(String arg0, Object arg1, Object arg2) {
				}
				
				@Override
				public void error(Marker arg0, String arg1) {
				}
				
				@Override
				public void error(String arg0, Throwable arg1) {
				}
				
				@Override
				public void error(String arg0, Object... arg1) {
				}
				
				@Override
				public void error(String arg0, Object arg1) {
				}
				
				@Override
				public void error(String arg0) {
				}
				
				@Override
				public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
				}
				
				@Override
				public void debug(Marker arg0, String arg1, Throwable arg2) {
				}
				
				@Override
				public void debug(Marker arg0, String arg1, Object... arg2) {
				}
				
				@Override
				public void debug(Marker arg0, String arg1, Object arg2) {
				}
				
				@Override
				public void debug(String arg0, Object arg1, Object arg2) {
				}
				
				@Override
				public void debug(Marker arg0, String arg1) {
				}
				
				@Override
				public void debug(String arg0, Throwable arg1) {
				}
				
				@Override
				public void debug(String arg0, Object... arg1) {
				}
				
				@Override
				public void debug(String arg0, Object arg1) {
				}
				
				@Override
				public void debug(String arg0) {
				}
			};
		}

	}

	public ILoggerFactory getFactory(String name) {
		return new InnerClass(name);
	}

}
