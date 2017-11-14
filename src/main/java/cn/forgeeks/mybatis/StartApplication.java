package cn.forgeeks.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class StartApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StartApplication.class);
	}

	public static void main(String[] args) {
		System.out.println("Springboot 项目正常启动 \n######  mybatis测试   ######\n");
		SpringApplication.run(StartApplication.class, args);
	}
}
