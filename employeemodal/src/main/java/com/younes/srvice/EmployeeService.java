package com.younes.srvice;

import java.util.List;

import com.younes.entity.Employee;

public interface EmployeeService {

	public List<Employee> fetchAllEmployee();
	public void fetchEmployeeByID(Long id);
	public List<Employee> fetchAllEmployeeByID(Long id);
	public Employee saveEmployee(Employee employee);
	public Employee updateEmployee(Employee employee,Long id);
	public void deleteEmployeeById(Long id);
	
}
