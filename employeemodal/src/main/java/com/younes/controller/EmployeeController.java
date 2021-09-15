package com.younes.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.younes.entity.Employee;
import com.younes.srvice.EmployeeService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value={"/employee","/"})
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	public String home(Model model) {
		model.addAttribute("title", "List-Employees");
		List<Employee> listEmployees=employeeService.fetchAllEmployee();
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}
	
	@GetMapping("/list-employees")
	public String listEmployees(Model model) {
		model.addAttribute("title", "List-Employees");
		List<Employee> listEmployees=employeeService.fetchAllEmployee();
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}
	
	@PostMapping("/save-employees")
	public String saveEmployees(@ModelAttribute Employee employee) {
		
		employeeService.saveEmployee(employee);
		return "redirect:/employee/list-employees";
	}
	
	@PostMapping("/update-employees")
	public String updateEmployees(@RequestParam("id")Long id ,@ModelAttribute Employee employee) {
		employeeService.updateEmployee(employee, id);
		return "redirect:/employee/list-employees";
	}
	
	@PostMapping("/delete-employees")
	public String deleteEmployees(@RequestParam("id")Long id) {
		employeeService.deleteEmployeeById(id);
		return "redirect:/employee/list-employees";
	}
	
	@GetMapping("/info")
	public ResponseEntity<byte[]> generatePDF() throws FileNotFoundException, JRException {
		List<Employee> list=this.employeeService.fetchAllEmployee();
		File file=ResourceUtils.getFile("classpath:list_employees.jrxml");
		JRBeanCollectionDataSource datasource=new JRBeanCollectionDataSource(list);//new FileInputStream("src/main/resources/list_employees.jrxml")
		JasperReport compilereport = JasperCompileManager.compileReport(file.getAbsolutePath());
		HashMap<String,Object> map=new HashMap<>();
		
		JasperPrint print = JasperFillManager.fillReport(compilereport,null,datasource);
		//JasperExportManager.exportReportToPdfFile(print,"info.pdf");
		byte[] data=JasperExportManager.exportReportToPdf(print);	
		HttpHeaders httph=new HttpHeaders();
		httph.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=list_employees.pdf");
		return ResponseEntity.ok().headers(httph)
				.contentType(MediaType.APPLICATION_PDF)
				.contentLength(data.length)
				.body(data) ;
		
	}
	
	@GetMapping("/report/{id}")
	public ResponseEntity<byte[]> generateReportPDF(@PathVariable("id")Long id) 
			throws FileNotFoundException, JRException {
		
		List<Employee> list= employeeService.fetchAllEmployeeByID(id);
		
		File file=ResourceUtils.getFile("classpath:employee.jrxml");
		JRBeanCollectionDataSource datasource=new JRBeanCollectionDataSource(list);//new FileInputStream("src/main/resources/list_employees.jrxml")
		JasperReport compilereport = JasperCompileManager.compileReport(file.getAbsolutePath());
		HashMap<String,Object> map=new HashMap<>();
		map.put("title", "Employee");
		JasperPrint print = JasperFillManager.fillReport(compilereport,map,datasource);
		//JasperExportManager.exportReportToPdfFile(print,"info.pdf");
		byte[] data=JasperExportManager.exportReportToPdf(print);	
		HttpHeaders httph=new HttpHeaders();
		httph.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=employee.pdf");
		return ResponseEntity.ok().headers(httph)
				.contentType(MediaType.APPLICATION_PDF)
				.contentLength(data.length)
				.body(data) ;
		
	}
	
}
