package cn.forgeeks.mybatis.test;

import java.util.ArrayList;
import java.util.HashMap;
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
import cn.forgeeks.mybatis.test.mapper.TestMapper01;
import cn.forgeeks.mybatis.test.pojo.Score;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
public class SpringBootTest02 {

	@Autowired
	TestMapper01 mapper;

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
		endTime = System.currentTimeMillis();
		System.out.println("\n" + testName);
		System.out.println("OK ! (" + (endTime - startTime) + " ms)");
		pool.shutdown();
	}
 
	@Test
	public void test01() {
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) mapper.select01();
		for (HashMap<String, Object> map : list) {
			System.out.println(map.get("snumber"));

		}
	}

 

}
