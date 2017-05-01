package com.example.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.persistence.entity.Employee;
import com.example.service.EmployeeService;
import com.example.web.form.EmployeeForm;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@ModelAttribute
	public EmployeeForm setUpForm() {
		return new EmployeeForm();
	}

	@GetMapping("/index")
	public String index(Model model) {
		List<Employee> employees = employeeService.findAll();
		model.addAttribute("employees", employees);
		return "employee/index";
	}
	
	@GetMapping("findByFirstName")
	public String findByFirstName(
			@RequestParam(value="firstName", defaultValue="")
			String firstName,
			Model model) {
		List<Employee> employees = employeeService.findByFirstNameContainingIgnoreCase(firstName);
		model.addAttribute("employees", employees);
		return "employee/index";
	}
	
	@GetMapping("/insertMain")
	public String insertMain() {
		return "employee/insertMain";
	}
	
	@PostMapping("/insertComplete")
	public String insertComplete(
			@Validated EmployeeForm form,
			BindingResult bindingResult
			) {
		if(bindingResult.hasErrors()) {
			return "employee/insertMain";
		}
		
		Employee employee = form.convertToEntity();
		employeeService.insert(employee);
		return "redirect:index";
	}

}
