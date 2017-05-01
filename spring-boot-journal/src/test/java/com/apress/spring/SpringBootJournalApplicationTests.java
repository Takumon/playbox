package com.apress.spring;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.apress.spring.domain.JournalEntry;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringBootJournalApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration
public class SpringBootJournalApplicationTests {
	/******************************
	 * 定数
	 ******************************/
	private final String SPRING_BOOT_MATCH = "Spring Boot";

	private final String CLOUD_MATCH = "Cloud";

	private MediaType contentType = new MediaType(//
			MediaType.APPLICATION_JSON.getType(), //
			MediaType.APPLICATION_JSON.getSubtype(), //
			Charset.forName("utf8"));

	/******************************
	 * MockMvcの設定
	 ******************************/
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(wac).build();
	}

	/******************************
	 * JSON <-> Object変換
	 ******************************/
	@Autowired
	private ObjectMapper mapper;

	/******************************
	 * テスト
	 ******************************/
	@Test
	public void getAll() throws Exception {
		mockMvc.perform(get("/journal/all")) //
				.andExpect(status().isOk()) //
				.andExpect(content().contentType(contentType)) //
				.andExpect(jsonPath("$", iterableWithSize(4))) //
				.andExpect(jsonPath("$[0]['title']", containsString(SPRING_BOOT_MATCH)));
	}

	@Test
	public void findByTitle() throws Exception {
		mockMvc.perform(get("/journal/findBy/title/" + CLOUD_MATCH)) //
				.andExpect(status().isOk()) //
				.andExpect(content().contentType(contentType)) //
				.andExpect(jsonPath("$", iterableWithSize(1))) //
				.andExpect(jsonPath("$[0]['title']", containsString(CLOUD_MATCH)));
	}

	@Test
	public void add() throws Exception {
		JournalEntry entry = new JournalEntry(//
				"Spring Boot Testing", //
				"Create Spring Boot Tests", //
				"05/09/2016");

		String requestBody = mapper.writeValueAsString(entry);
		mockMvc.perform(post("/journal").content(requestBody).contentType(contentType)) //
				.andExpect(status().isOk());
	}

}
