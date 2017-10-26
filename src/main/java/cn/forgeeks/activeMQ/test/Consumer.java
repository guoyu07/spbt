package cn.forgeeks.activeMQ.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 定义消费者
 * 
 * @author lenovo
 *
 */
@Component
public class Consumer {
	private static Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

	@JmsListener(destination = "sample.queue")
	public void receriveQueue(String text) {
		LOGGER.info(  text + "  -> 该点对点消息已被消费 ");
	}

	@JmsListener(destination = "sample.topic")
	public void receriveTopic(String text) {
		LOGGER.info( text + "   -> 该订阅消息已被消费  ");
	}

}
