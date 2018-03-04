package cn.fintechstar.exchange.security.spring;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;


import cn.fintechstar.exchange.security.rabbitmq.EmailRecv;
import cn.fintechstar.exchange.security.rabbitmq.RabbitmqConstants;


@Configuration
public class RabbitConfig {

	@Autowired 
	private Environment environment;
	
	@Autowired
	private EmailRecv emailRecv;
	

	@Bean
	public ConnectionFactory connectionFactory(){
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(environment.getProperty("mq.host"));
		connectionFactory.setPort(Integer.parseInt(environment.getProperty("mq.port")));
		connectionFactory.setVirtualHost(environment.getProperty("mq.vhost"));
		connectionFactory.setUsername(environment.getProperty("mq.user"));
		connectionFactory.setPassword(environment.getProperty("mq.password"));
		connectionFactory.setPublisherConfirms(false);
		connectionFactory.setPublisherReturns(false);
		return connectionFactory;
	}

	@Bean
	public RabbitAdmin rabbitAdmin() {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
		return rabbitAdmin;
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = rabbitAdmin().getRabbitTemplate();
		rabbitTemplate.setExchange(environment.getProperty("mq.exchange"));
		return rabbitTemplate;
	}
	
	@Bean
	public Queue emailReciveQueue(){
		return new Queue(RabbitmqConstants.MQ_QUEUE_SECURITY_EMAIL_REQ, false, false, true);
	}

	@Bean
	public TopicExchange topicExchange(){
		return new TopicExchange(environment.getProperty("mq.exchange"), false, false);
	}
	
	@Bean
	public Binding emailReciveBinding(){
		return BindingBuilder.bind(emailReciveQueue()).to(topicExchange()).with(RabbitmqConstants.MQ_KEY_SECURITY_EMAIL_REQ);
	}		
	
	
	@Bean
	public SimpleMessageListenerContainer userinfoMessageContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
		container.setQueues(emailReciveQueue());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.NONE); 
		container.setMessageListener(emailRecv);		
		return container;
	}
	

}
