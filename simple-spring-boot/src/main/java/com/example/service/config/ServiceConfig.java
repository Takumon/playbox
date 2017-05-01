package com.example.service.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * サービスの設定
 * 
 * @author inouetakumon
 */
@Configuration
@ComponentScan(basePackages = "com.example.service.impl")
@EnableTransactionManagement
public class ServiceConfig {

	/**
	 * トランザクションの設定
	 * 
	 * @param emf
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(emf);
		return transactionManager;
	}

}
