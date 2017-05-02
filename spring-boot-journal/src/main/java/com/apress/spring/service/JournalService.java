package com.apress.spring.service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apress.spring.domain.JournalEntry;
import com.apress.spring.domain.JournalEntry;
import com.apress.spring.repository.JournalRepository;

//@Service
public class JournalService {
	Logger log = LoggerFactory.getLogger(JournalService.class);

	@Autowired
	JournalRepository repo;

	public List<JournalEntry> findAll() {
		//return repo.findAll();
		return null;
	}

	public JournalEntry add(JournalEntry entry) {
		JournalEntry journal = new JournalEntry();
		journal.setTitle(entry.getTitle());
		journal.setSummary(entry.getSummary());
		journal.setCreated(entry.getCreated());

		repo.save(journal);
		return journal;
	}

	public List<JournalEntry> findByTitleContains(String title) {
		//return repo.findByCustomQuery(title);
		return null;
	}
}
