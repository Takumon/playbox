package com.example.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOGE")
	@SequenceGenerator(name = "HOGE", sequenceName = "hibernate_sequence", initialValue = 5, allocationSize = 1) // このアノテーションがないとBootではIDが自動生成されない
	public Integer id;

	public String firstName;

	public String lastName;

	public LocalDate joinedDate;
}
