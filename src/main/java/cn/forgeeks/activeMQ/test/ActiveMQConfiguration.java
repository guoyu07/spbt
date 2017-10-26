package cn.forgeeks.activeMQ.test;

import javax.jms.Queue;
import javax.jms.Topic;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

/**
 * 定义队列||主题
 * 
 * @author lenovo
 *这里对 ActiveMQ 的端口做一个简短说明，61616为消息代理接口 ，8161 为管理界面
 */
@Configuration
@EnableJms
public class ActiveMQConfiguration {

	/**
	 * 定义点对点队列
	 * 
	 * @return
	 */
	@Bean
	public Queue queue() {
		return  new ActiveMQQueue("sample.queue");
	}

	/**
	 * 定义主题
	 * @return
	 */
	@Bean
	public Topic topic() {
		return new ActiveMQTopic("sample.topic");
	}

	
}
