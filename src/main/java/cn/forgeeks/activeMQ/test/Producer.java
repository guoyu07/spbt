package cn.forgeeks.activeMQ.test;

import javax.jms.Queue;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定义生产者
 * 
 * @author lenovo
 */
@Component
public class Producer {
	private static Logger LOGGER = LoggerFactory.getLogger(Producer.class);
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private Queue queue;
	@Autowired
	private Topic topic;

	/**
	 * 每5秒执行一次
	 */
	@Scheduled(fixedRate = 5000, initialDelay = 3000)
	public void send(String msg) {
		LOGGER.info("生产一条点对点消息");
		this.jmsMessagingTemplate.convertAndSend(this.queue, "生产者: "+msg);
		LOGGER.info("生产一条订阅消息");
		this.jmsMessagingTemplate.convertAndSend(this.topic, "生产者: "+msg);
	}

}
