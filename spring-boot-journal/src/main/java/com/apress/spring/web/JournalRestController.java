package com.apress.spring.web;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apress.spring.domain.Journal;
import com.apress.spring.domain.JournalEntry;
import com.apress.spring.service.JournalService;

@RestController
public class JournalRestController {

	@Autowired
	private JournalService journalService;

	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public String greeting() {
		return "Hello BookRocks";
	}

	@RequestMapping("/journal/all")
	public List<Journal> getAll() throws ParseException {
		return journalService.findAll();
	}

	@RequestMapping("/journal/findBy/title/{title}")
	public List<Journal> findByTitleContains(@PathVariable String title) throws ParseException {
		return journalService.findByTitleContains(title);
	}
	
	@RequestMapping(value = "/journal", method = RequestMethod.POST)
	public Journal add(@RequestBody JournalEntry entry) {
		return journalService.add(entry);
	}

}
