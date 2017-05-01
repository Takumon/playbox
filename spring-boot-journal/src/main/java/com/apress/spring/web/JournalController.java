package com.apress.spring.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apress.spring.persistence.entity.Journal;
import com.apress.spring.persistence.repository.JournalRepository;


@Controller
public class JournalController {
	@Autowired
	JournalRepository repo;
	
	private static final Logger log = LoggerFactory.getLogger(JournalController.class);
	
	@Value("${spring.my}")
	private String my;
	
	@RequestMapping(value="/journal", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	public @ResponseBody List<Journal> getJournal() {
		return repo.findAll();
	}

	@RequestMapping("/")
	public String index(Model model) {
		log.info("自分で定義したプロパティ：" + my);
		model.addAttribute("journal", repo.findAll());
		return "index";
	}
}
