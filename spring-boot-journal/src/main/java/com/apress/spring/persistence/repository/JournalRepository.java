package com.apress.spring.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apress.spring.persistence.entity.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long> {

}
