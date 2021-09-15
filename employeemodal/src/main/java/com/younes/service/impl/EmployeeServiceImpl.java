package com.younes.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.younes.entity.Employee;
import com.younes.repository.EmployeeRepository;
import com.younes.srvice.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
   private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> fetchAllEmployee() {
		
		return this.employeeRepository.findAll();
	}

	@Override
	public void fetchEmployeeByID(Long id) {
		
		this.employeeRepository.findById(id).get();
		
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		
		return this.employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee, Long id) {
		if(employee!=null) {
			employee.setId(id);
			return this.employeeRepository.save(employee);
		}else {
			return null;
		}
	}

	@Override
	public void deleteEmployeeById(Long id) {
		this.employeeRepository.deleteById(id);
		
	}

	@Override
	public List<Employee> fetchAllEmployeeByID(Long id) {
		 Employee emp = employeeRepository.findById(id).get();
		 List<Employee> list=new ArrayList<>();
		 list.add(emp);
		return list;
	}
}
