package cn.forgeeks.mybatis.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.forgeeks.mybatis.test.pojo.Student;

@Controller
@RequestMapping("/mb")
public class MyBatisTest01Controller {

	@RequestMapping("/test01")
	@ResponseBody
	public Student getCustList(String phone) {
		return null;
	}
}
