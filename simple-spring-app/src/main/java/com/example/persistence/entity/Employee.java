package com.example.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Employee {
	
	@Id
	@GeneratedValue
	public Integer id;
	
	public String firstName;
	
	public String lastName;
	
	public LocalDate joinedDate;
}
