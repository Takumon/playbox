package com.example.service;

import java.util.List;

import com.example.persistence.entity.Employee;

/**
 * 従業員サービスのインターフェース
 * 
 * @author inouetakumon
 *
 */
public interface EmployeeService {
	List<Employee> findAll();

	List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

	void insert(Employee employee);
}
