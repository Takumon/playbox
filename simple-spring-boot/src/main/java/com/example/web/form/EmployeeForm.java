package com.example.web.form;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.persistence.entity.Employee;

/**
 * 従業員入力フォームのBeanクラス
 * 
 * @author inouetakumon
 */
public class EmployeeForm {

	@NotBlank
	@Length(min = 1, max = 40)
	private String firstName;

	@NotBlank
	@Length(min = 1, max = 40)
	private String lastName;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate joinDate;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}
	
	public Employee convertToEntity() {
		Employee result = new Employee();
		result.firstName = firstName;
		result.lastName = lastName;
		result.joinedDate = joinDate;
		return result;
	}

}
