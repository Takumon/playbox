package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.persistence.entity.Employee;
import com.example.persistence.repository.EmployeeRepository;
import com.example.service.EmployeeService;

/**
 * 従業員サービスの実装クラス
 * 
 * @author inouetakumon
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Employee> findByFirstNameContainingIgnoreCase(String firstName) {
		return employeeRepository.findByFirstNameContainingIgnoreCase(firstName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Employee employee) {
		employeeRepository.save(employee);
	}
}
