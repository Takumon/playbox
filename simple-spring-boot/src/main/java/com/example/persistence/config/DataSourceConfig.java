package com.example.persistence.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * データソースの設定
 * 
 * @author inouetakumon
 */
@Configuration
public class DataSourceConfig {
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setName("jsug")
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:/schema.sql")
				.addScript("classpath:/data.sql")
				.build();
	}
}
