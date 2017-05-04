package com.apress.spring;

import java.time.LocalDateTime;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.apress.spring.rabbitmq.Producer;

@EnableScheduling
@SpringBootApplication
public class SpringBootRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRabbitmqApplication.class, args);
	}

	@Value("${myqueue}")
	String queueName;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Autowired
	Producer producer;

	@Scheduled(fixedDelay = 500L)
	public void sendMessage() {
		producer.sentTo(queueName, "Hello World at " + LocalDateTime.now());
	}
}
