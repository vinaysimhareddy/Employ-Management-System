package com.employee.management.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.entity.Employee;
import com.employee.management.exceptions.EmployeeNotFoundException;
import com.employee.management.repositories.EmployeeRepository;

@RestController
@CrossOrigin (origins ="http://localhost:4200")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{empId}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int empId){
		Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee with Employee Id"+empId+"does not exist"));
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/employees/{empId}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable int empId, @RequestBody Employee employeeDetails) {
		Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee with Employee Id"+empId+"does not exist"));
		employee.setEmpName(employeeDetails.getEmpName());
		employee.setDesignation(employeeDetails.getDesignation());
		employee.setEmpSalary(employeeDetails.getEmpSalary());
		employeeRepository.save(employee);
		return ResponseEntity.ok(employee);
	}
	
	@DeleteMapping("/employees/{empId}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable int empId){
		Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee with Employee Id"+empId+"does not exist"));
		employeeRepository.delete(employee);
		Map<String,Boolean> response = new HashMap<String,Boolean>();
		response.put("Deleted ", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
