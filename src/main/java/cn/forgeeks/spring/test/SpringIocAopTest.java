package cn.forgeeks.spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @desc spring ioc aop 依赖注入演示
 * @author lenovo
 * @desc 依赖注入的三种方式：（1）接口注入（2）Construct注入（3）Setter注入
 *       控制反转（IoC）与依赖注入（DI）是同一个概念，引入IOC的目的：（1）脱开、降低类之间的耦合；（2）倡导面向接口编程、 实施依赖倒换原则；
 *       （3）提高系统可插入、可测试、可修改等特性。
 * @desc具体做法：（1）将bean之间的依赖关系尽可能地抓换为关联关系； （2）将对具体类的关联尽可能地转换为对Java
 *                                       interface的关联，而不是与具体的服务对象相关联；
 *                                       （3）Bean实例具体关联相关Java
 *                                       interface的哪个实现类的实例，在配置信息的元数据中描述；
 *                                       （4）由IoC组件（或称容器）根据配置信息，实例化具体bean类、
 *                                       将bean之间的依赖关系注入进来。
 */
public class SpringIocAopTest {
	ApplicationContext context = new FileSystemXmlApplicationContext("classpath*:configs/spring-test-1.xml");

	public static void main(String[] args) {
		SpringIocAopTest test = new SpringIocAopTest();
		// tdest.fun01();
		// test.fun02();
		// test.fun03();
		test.fun04();
	}

	/**
	 * @desc 演示 构造方法 依赖注入
	 */
	public void fun04() {
		Chinese hashMap = (Chinese) context.getBean("chinese-1");
		System.out.println(hashMap);
	}

	/**
	 * @desc 演示 接口 依赖注入
	 */
	public void fun03() {
		Chinese hashMap = (Chinese) context.getBean("chinese-1");
		System.out.println(hashMap);
	}

	/**
	 * @desc 演示 setter依赖注入 从spring配置文件中取出map
	 */
	public void fun02() {
		Object hashMap = context.getBean("hashMap-1");
		System.out.println(hashMap);
	}

	/**
	 * @desc 演示setter依赖注入从spring配置文件中取出obj
	 */
	public void fun01() {
		Student student = (Student) context.getBean("student-1");
		System.out.println(student);
	}

}
