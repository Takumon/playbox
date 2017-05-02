package com.apress.spring.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apress.spring.domain.Journal;
import com.apress.spring.service.JournalService;

@Controller
public class JournalController {
	@Autowired
	JournalService journalService;

	@RequestMapping(value = "/journal", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody List<Journal> getJournal() {
		return journalService.findAll();
	}

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("journal", journalService.findAll());
		return "index";
	}
}
