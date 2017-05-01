package com.example.web.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JpaConfig;
import com.example.service.config.ServiceConfig;

/**
 * アプリの設定
 * 
 * @author inouetakumon
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// 設定ファイルをを作成したら登録する
		return new Class<?>[] {
			DataSourceConfig.class,
			JpaConfig.class,
			ServiceConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {MyConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true) };
	}

}
