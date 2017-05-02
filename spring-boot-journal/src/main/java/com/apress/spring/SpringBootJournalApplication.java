package com.apress.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.apress.spring.service.JournalService;

@SpringBootApplication(exclude = { ActiveMQAutoConfiguration.class })
public class SpringBootJournalApplication {

	private Logger log = LoggerFactory.getLogger(SpringBootJournalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJournalApplication.class, args);
	}

	@Bean
	CommandLineRunner start(JournalService service) {
		return args -> {
			log.info("@@ findAll call");
			service.findAll().forEach(entry -> log.info(entry.toString()));
		};
	}
}
