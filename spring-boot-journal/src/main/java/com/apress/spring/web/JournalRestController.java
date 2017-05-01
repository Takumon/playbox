package com.apress.spring.web;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apress.spring.domain.JournalEntry;
import com.apress.spring.persistence.entity.Journal;
import com.apress.spring.persistence.repository.JournalRepository;

@RestController
public class JournalRestController {

	@Autowired
	private JournalRepository journalRepository;

	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public String greeting() {
		return "Hello BookRocks";
	}

	@RequestMapping("/journal/all")
	public List<Journal> getAll() throws ParseException {
		return journalRepository.findAll();
	}

	@RequestMapping("/journal/findBy/title/{title}")
	public List<Journal> findByTitleContains(@PathVariable String title) throws ParseException {
		return journalRepository.findAll() //
				.stream() //
				.filter(entry -> entry.getTitle().toLowerCase().contains(title.toLowerCase())) //
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/journal", method = RequestMethod.POST)
	public Journal add(@RequestBody JournalEntry entry) {
		Journal journal = new Journal();
		journal.setTitle(entry.getTitle());
		journal.setSummary(entry.getSummary());
		journal.setCreated(entry.getCreated());

		journalRepository.save(journal);
		return journal;
	}

}
