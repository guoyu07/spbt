package cn.forgeeks.mybatis.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mb")
public class MyBatisTest01Controller {

	@Resource
	StudentMapper mapper;
	
	@RequestMapping("/test01")
	@ResponseBody
	public Student getCustList(String phone) {
		Student student = new Student();
		student = mapper.sleectStudent01(null);
		System.out.println(student);
		return student;
	}
}
