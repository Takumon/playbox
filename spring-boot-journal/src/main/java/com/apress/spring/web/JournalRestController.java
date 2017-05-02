package com.apress.spring.web;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.apress.spring.domain.JournalEntry;
import com.apress.spring.domain.JournalEntry;
import com.apress.spring.repository.JournalRepository;
import com.apress.spring.service.JournalService;

@RestController
public class JournalRestController {
	
	private static final String VIEW_INDEX = "index";

	//@Autowired
	private JournalService journalService;
	
	@Autowired
	private JournalRepository repo;

	//@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public String greeting() {
		return "Hello BookRocks";
	}

	//@RequestMapping("/journal/all")
	public List<JournalEntry> getAll() throws ParseException {
		return journalService.findAll();
	}

	//@RequestMapping("/journal/findBy/title/{title}")
	public List<JournalEntry> findByTitleContains(@PathVariable String title) throws ParseException {
		return journalService.findByTitleContains(title);
	}
	
	//@RequestMapping(value = "/journal", method = RequestMethod.POST)
	public JournalEntry add(@RequestBody JournalEntry entry) {
		return journalService.add(entry);
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName(VIEW_INDEX);
		modelAndView.addObject("journal", repo.findAll());
		return modelAndView;
	}
	

}
