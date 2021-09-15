package com.younes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.younes.entity.Employee;
import com.younes.repository.EmployeeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)

public class EmployeeTest {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Test
	void employeeTest() {
		Employee employee=new Employee();
		employee.setName("John Doe");
		employee.setEmail("john@gmail.com");
		employee.setAddress("Ichemoul boulevard Ben Boulaid Mostapha");
		employee.setPhone("0600000000");
		employeeRepository.save(employee);
		assertThat(employee.getName()).isNotNull();
		
	}
}
