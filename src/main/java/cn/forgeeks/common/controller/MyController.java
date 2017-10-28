package cn.forgeeks.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc Spring Boot应用中@RestController的Controller带有默认基于Jackson2的对象转JSON功能
 * @author hechao
 *
 */
@RestController
public class MyController {

	@RequestMapping("/thing")
	public MyThing thing() {
		return new MyThing("hechao", 19);
	}

	class MyThing {
		String name;
		Integer age;
		public MyThing(String name, Integer age) {
			super();
			this.name = name;
			this.age = age;
		}
	}
}