
package cn.fintechstar.exchange.security.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
* @auther: Water
* @time: 27 Feb 2018 18:20:31
* 
*/

@Component
public class EmailRecv implements MessageListener{

	@Autowired
	private MessageDispatcher messageDispatcher;
	
	@Override
	public void onMessage(Message message) {		
		byte[] body = message.getBody();
		messageDispatcher.responseEmailReq(body);
	}
	
}

