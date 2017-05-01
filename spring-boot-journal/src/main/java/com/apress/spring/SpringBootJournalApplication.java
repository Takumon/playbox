package com.apress.spring;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.apress.spring.persistence.entity.Journal;
import com.apress.spring.persistence.repository.JournalRepository;

@SpringBootApplication(exclude = { ActiveMQAutoConfiguration.class })
public class SpringBootJournalApplication{
	
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootJournalApplication.class);
	
	@Bean
	String info() {
		return "Just a simple String Bean";
	}
	
	@Autowired
	String info;
	
	@Bean
	InitializingBean saveDate(JournalRepository repo) {
		return () -> {
			repo.save(new Journal("Getto know Spring Boot", "Today I will learn SpringBoot", "01/01/2016"));
			repo.save(new Journal("Simple Spring Boot Project", "I will do my first SpringBoot", "01/02/2016"));
			repo.save(new Journal("Spring Boot Reading", "Read more about SpringBoot", "02/01/2016"));
			repo.save(new Journal("Sprinag Boot in the Cloud", "Spring Boot using Cloud Foundry", "03/01/2016"));
		};
	}
	
	
	@Autowired
	MyAppProperties props;
	
	@Component
	@ConfigurationProperties(prefix="spring")
	public static class MyAppProperties {
		private String my;

		public String getMy() {
			return my;
		}

		public void setMy(String my) {
			this.my = my;
		}
	}
	


	@Bean
	CommandLineRunner myMethod() {
		return args -> {
			log.info("##> ApplicationRunner Implementation...");
			log.info("Accession the Info bean:" + info);
			log.info("spring-my:" + props.getMy());
			Stream.of(args).forEach(log::info);
		};
	}
	
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(SpringBootJournalApplication.class);
		
		SpringApplication app = new SpringApplicationBuilder()
				.bannerMode(Banner.Mode.OFF)
				.sources(SpringBootJournalApplication.class)
				.listeners(new ApplicationListener<ApplicationEvent>() {
					
					@Override
					public void onApplicationEvent(ApplicationEvent event) {
						logger.info("####>" + event.getClass().getCanonicalName());
					}
					
				})
				.build();
		app.run(args);
	}
}
