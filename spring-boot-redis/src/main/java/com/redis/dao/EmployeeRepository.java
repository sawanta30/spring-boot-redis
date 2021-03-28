package com.redis.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.redis.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String>{
	
	
	
	
}
