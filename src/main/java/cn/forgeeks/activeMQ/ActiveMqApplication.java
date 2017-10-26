package cn.forgeeks.activeMQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * /**
 * 
 * @desc springboot启动app 注意该app要放在最外面的包
 * @author hechao
 * @desc 另外配置方法 @Configuration @EnableAutoConfiguration @ComponentScan
 *       当有classes文件被改动时，系统会重新加载类文件，不用重启启动服务。 注：使用application
 *       run(非debug模式下)，热部署功能会失效。
 */
@SpringBootApplication
public class ActiveMqApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ActiveMqApplication.class);
	}

	public static void main(String[] args) {
		System.out.println("进入 Springboot ActiveMqApplication 入口 ");
		SpringApplication.run(ActiveMqApplication.class, args);
	}
}
