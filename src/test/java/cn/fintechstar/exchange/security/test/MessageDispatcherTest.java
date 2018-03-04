
package cn.fintechstar.exchange.security.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fintechstar.exchange.security.SpringBootApp;
import cn.fintechstar.exchange.security.rabbitmq.MessageDispatcher;


/**
* @auther: Water
* @time: 25 Dec 2017 11:39:17
* 
*/

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringBootApp.class)
public class MessageDispatcherTest {
	
	@Autowired
	private MessageDispatcher messageDispatcher;
	
	
	@Test
	public void testMessageDispatcher(){
		messageDispatcher.responseEmailReq("123".getBytes());
		System.out.println("testMessageDispatcher result:");
	}
	
	@Test
	public void testEmailReqDispatcher(){
		messageDispatcher.sendEmailReq();
		
		try {
			Thread.sleep(1000*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("testMessageDispatcher result:");
	}

}
