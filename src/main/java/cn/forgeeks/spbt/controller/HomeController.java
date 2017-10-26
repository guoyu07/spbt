package cn.forgeeks.spbt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.forgeeks.activeMQ.test.Consumer;
import cn.forgeeks.activeMQ.test.Producer;

/**
 * SpringBoot 测试类
 * 
 * @author hechao
 */
@Controller
@RequestMapping("/home")
public class HomeController {

	
	
	@RequestMapping("/index")
	public String index() {
		System.out.println("springboot项目启动成功!");
		return "index";
	}
	
	@RequestMapping("/portal")
	@ResponseBody
	public String home() {
		System.out.println("springboot项目启动成功!");
		return "springboot项目启动成功!";
	}

	

	
}
