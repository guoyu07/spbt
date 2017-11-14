package cn.forgeeks.mybatis.test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.forgeeks.mybatis.StartApplication;
import cn.forgeeks.mybatis.test.dao.CourseMapper;
import cn.forgeeks.mybatis.test.dao.ScoreMapper;
import cn.forgeeks.mybatis.test.dao.StudentMapper;
import cn.forgeeks.mybatis.test.dao.TeacherMapper;
import cn.forgeeks.mybatis.test.pojo.Course;
import cn.forgeeks.mybatis.test.pojo.Score;
import cn.forgeeks.mybatis.test.pojo.Student;
import cn.forgeeks.mybatis.test.pojo.Teacher;

/**
 * 测试mybatis基本用法, 以及数据初始化
 * 
 * @author hechao
 *
 */
@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
// @SpringBootTest
public class SpringBootTest01 {

	@Autowired
	StudentMapper studentMapper;
	@Autowired
	TeacherMapper teacherMapper;
	@Autowired
	ScoreMapper scoreMapper;
	@Autowired
	CourseMapper courseMapper;

	public long startTime;
	public long endTime;
	public String testName = "#############         测试           ###############";
	public ExecutorService pool = null;

	@Before
	public void fun01() {
		System.out.println(testName + "\n");
		startTime = System.currentTimeMillis();
		pool = Executors.newFixedThreadPool(20);
	}

	@After
	public void fun99() throws InterruptedException {
		Thread.sleep(3000);
		endTime = System.currentTimeMillis();
		System.out.println("\n" + testName);
		System.out.println("OK ! (" + (endTime - startTime) + " ms)");
		pool.shutdown();
	}

	/**
	 * 测试插入100学生, 5老师
	 */
	@Test
	public void test03() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(100);
		pool.execute(new MyThread(countDownLatch));
		countDownLatch.await();
		System.out.println("任务执行完毕");
		pool.shutdown();
	}

	/**
	 * 插入9门课程
	 */
	@Test
	public void test04() throws InterruptedException {
		// ArrayList<Student> list = (ArrayList<Student>)
		// studentMapper.selectAll();

		pool.execute(new Runnable() {
			@Override
			public void run() {
				Course course = new Course(UUID.randomUUID().toString().replace("-", ""), "大学语文",
						"21d6da016e8b4c79b9bd39f24d2aa27e", "上课了", new Date(),
						new Random().nextInt(20) + "栋" + new Random().nextInt(100) + "教室");
				Course course2 = new Course(UUID.randomUUID().toString().replace("-", ""), "高等数学上",
						"43cffb79da6f4a2d88f6efa6b0013bc9", "上课了", new Date(),
						new Random().nextInt(20) + "栋" + new Random().nextInt(100) + "教室");
				Course course3 = new Course(UUID.randomUUID().toString().replace("-", ""), "高等数学下",
						"6fdebeeaf9f141d0b9341702e72330bb", "上课了", new Date(),
						new Random().nextInt(20) + "栋" + new Random().nextInt(100) + "教室");
				Course course4 = new Course(UUID.randomUUID().toString().replace("-", ""), "数据库原理",
						"ddc160ac20404674bb4a351635d9c07b", "上课了", new Date(),
						new Random().nextInt(20) + "栋" + new Random().nextInt(100) + "教室");
				Course course5 = new Course(UUID.randomUUID().toString().replace("-", ""), "编译原理",
						"fabbf0e6a35b43e5bd7ec3866700eb6e", "上课了", new Date(),
						new Random().nextInt(20) + "栋" + new Random().nextInt(100) + "教室");

				courseMapper.insert(course5);
				courseMapper.insert(course4);
				courseMapper.insert(course3);
				courseMapper.insert(course2);
				courseMapper.insert(course);

			}
		});
		pool.wait();
		System.out.println("任务执行完毕");
		pool.shutdown();
	}

	@Test
	public void test07() throws InterruptedException {
		// courseMapper.selectAll();
		ArrayList<Student> studentList = (ArrayList<Student>) studentMapper.selectAll();
		ArrayList<Course> coursesList = (ArrayList<Course>) courseMapper.selectAll();
		Integer size = studentList.size() * coursesList.size();
		CountDownLatch countDownLatch = new CountDownLatch(size);
		pool.execute(new MyThread02(countDownLatch,  studentList, coursesList));
		countDownLatch.await();
		System.out.println("任务执行完毕");
		pool.shutdown();

	}

	/**
	 * 插入分数
	 * 
	 * @author hechao  
	 */
	class MyThread02 implements Runnable {
		CountDownLatch countDownLatch;
		ArrayList<Student> studentList;
		ArrayList<Course> coursesList;

		@Override
		public void run() {
			dowork();

		}

		public MyThread02(CountDownLatch countDownLatch , ArrayList<Student> studentList,
				ArrayList<Course> coursesList) {
			super();
			this.countDownLatch = countDownLatch;
			this.studentList = studentList;
			this.coursesList = coursesList;
		}

		private void dowork() {
			for (int i = 0; i < studentList.size(); i++) {
				for (int j = 0; j < coursesList.size(); j++) {
					Student student = studentList.get(i);
					Course course = coursesList.get(j);
					Score score = new Score(UUID.randomUUID().toString().replace("-", ""), student.getSid(),
							course.getCid(), new BigDecimal(40 + new Random().nextInt(60)), new Date());
					scoreMapper.insert(score);
					countDownLatch.countDown();
					System.out.println(student.getSname() + " - " + course.getCname() + " - "
							+ score.getScore().intValue() + "  - " + countDownLatch.getCount() + " left ");
				}
			}
		}

	}

	/**
	 * 插入课程
	 * 
	 * @author hechao
	 *
	 */
	class MyThread03 implements Runnable {
		CountDownLatch countDownLatch;

		@Override
		public void run() {
			dowork();

		}

		public MyThread03(CountDownLatch countDownLatch) {
			super();
			this.countDownLatch = countDownLatch;
		}

		private void dowork() {
			countDownLatch.countDown();
		}

	}

	/**
	 * 插入学生教室
	 * 
	 * @author hechao
	 *
	 */
	class MyThread implements Runnable {
		CountDownLatch countDownLatch;

		@Override
		public void run() {
			dowork();

		}

		public MyThread(CountDownLatch countDownLatch) {
			super();
			this.countDownLatch = countDownLatch;
		}

		private void dowork() {
			for (int i = 0; i < 100; i++) {
				Student student = new Student(UUID.randomUUID().toString().replace("-", ""), getRandomCN(),
						15 + new Random().nextInt(10), i % 3 == 0 ? "女" : "男", "201" + getRandomNumber(10),
						"13" + getRandomNumber(11), new Date());
				Teacher teacher = new Teacher(UUID.randomUUID().toString().replace("-", ""), getRandomCN(),
						"13" + getRandomNumber(11));
				studentMapper.insert(student);
				if (i % 20 == 0) {
					teacherMapper.insert(teacher);
				}
				countDownLatch.countDown();
				System.out.println(countDownLatch.getCount() + " left ");
			}
		}

	}

	/*
	 * 返回长度为【strLength】的随机数，在前面补0
	 */
	public String getRandomNumber(int strLength) {
		Random rm = new Random();
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
		String fixLenthString = String.valueOf(pross);
		return fixLenthString.substring(2, strLength + 2);
	}

	public String getRandomCN() {
		return getRandomChar() + getRandomChar() + getRandomChar();
	}

	// 随机生成常见汉字
	public static String getRandomChar() {
		String str = "";
		int highCode;
		int lowCode;

		Random random = new Random();

		highCode = (176 + Math.abs(random.nextInt(39))); // B0 + 0~39(16~55)
															// 一级汉字所占区
		lowCode = (161 + Math.abs(random.nextInt(93))); // A1 + 0~93 每区有94个汉字

		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(highCode)).byteValue();
		b[1] = (Integer.valueOf(lowCode)).byteValue();

		try {
			str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	@Test
	public void test02() {

	}

}
