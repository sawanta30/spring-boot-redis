package com.redis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.model.Employee;
import com.redis.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@PostMapping()
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeService.addEmployee(employee);
		
	}
	
	@GetMapping("{id}")
	public Employee getEmployeeById(@PathVariable("id") String id) {
		Optional<Employee> result = employeeService.getEmployeeById(id);
		if(result.isPresent())
			return result.get();
		else
			return null;
	}
	
	@PutMapping("{id}")
	public Employee updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) {
		return employeeService.UpdateEmployee(id, employee);
	}
	
	@GetMapping
	public List<Employee> getAllEmployees(){
		return employeeService.getAllEmployees();
	}
	
	@DeleteMapping("{id}")
	public void deleteEmployeeById(@PathVariable("id") String id) {
		employeeService.deleteEmployeeById(id);
	}
}
