package cn.forgeeks.activeMQ.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.tomcat.jni.Pool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/acmq")
@Controller
public class ActiveMQController {

	@Autowired
	Producer producer;
	ExecutorService pool = Executors.newFixedThreadPool(20);

	@ResponseBody
	@RequestMapping("/test")
	public String testSendAndReceive() {
		for (int i = 1; i <= 20; i++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					producer.send("# " + Thread.currentThread().getName() + " 条消息 ");
				}
			});
		}
		pool.shutdown();
		return " 测试成功 ";
	}
	
}
