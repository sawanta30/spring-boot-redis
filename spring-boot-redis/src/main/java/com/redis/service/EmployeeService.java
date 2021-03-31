package com.redis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redis.dao.EmployeeRepository;
import com.redis.model.Employee;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	public Employee getEmployeeById(String id) {
		return employeeRepository.findById(id).orElseThrow(()->new IllegalStateException("Employee not found : "+id));
	}
	
	public Employee UpdateEmployee(String id, Employee employee) {
		Optional<Employee> emp = employeeRepository.findById(id);
		employee.setId(id);
		return employeeRepository.save(employee);
		
	}
	
	public List<Employee> getAllEmployees(){
		return (List<Employee>) employeeRepository.findAll();
	}
	
	public void deleteEmployeeById(String id) {
		employeeRepository.deleteById(id);
	}
}
