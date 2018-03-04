package cn.fintechstar.exchange.security.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageDispatcher {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public void responseEmailReq(byte[] body){		
		String req = new String(body);
		
		System.out.println("responseEmailReq:" + req);
		
		String res = "yes";
		MessageProperties properties = new MessageProperties();
		properties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
		Message resMessage = new Message(res.getBytes(), properties);
		rabbitTemplate.send(RabbitmqConstants.MQ_KEY_SECURITY_EMAIL_RES, resMessage);
	}
	
	
	// below is used for test
	public void sendEmailReq(){
		String req = "req yes";
		MessageProperties properties = new MessageProperties();
		properties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
		Message resMessage = new Message(req.getBytes(), properties);
		rabbitTemplate.send(RabbitmqConstants.MQ_KEY_SECURITY_EMAIL_REQ, resMessage);
	}
	
}
