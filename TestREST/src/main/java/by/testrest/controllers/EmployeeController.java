package by.testrest.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import by.test.dto.EmployeeDto;
import by.testrest.service.IEmployeeService;

@Controller
@RequestMapping("/employers")
public class EmployeeController {

		@Autowired
		IEmployeeService employeeService;
		
		static final Logger logger = Logger.getLogger(EmployeeController.class);
		
		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
		public @ResponseBody
		EmployeeDto getEmployeeById(@PathVariable("id") long id){
			EmployeeDto employee = null;
			try{
				employee = employeeService.getEmployeeById(id);
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			return employee;
		}
		
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public @ResponseBody
		List<EmployeeDto> getAllEmployers() {
			List<EmployeeDto> employers = null;
			try {
				employers = employeeService.getAllEmployers();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return employers;
		}
		
		@RequestMapping(value = "/listByDepartment/{id}", method = RequestMethod.GET)
		public @ResponseBody
		List<EmployeeDto> getEmployersByDepartment(@PathVariable("id") long id) {
			List<EmployeeDto> employers = null;
			try {
				employers = employeeService.getEmployersByDepartment(id);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return employers;
		}
		
		@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody
		EmployeeDto save(@RequestBody EmployeeDto employee) {
				Long id = employeeService.save(employee);
				employee.setId(id);
				return employee;
		}
		
		@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody
		EmployeeDto update(@RequestBody EmployeeDto employee) {
				employeeService.update(employee);
				return employee;
		}

		@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
		public @ResponseBody
		EmployeeDto deleteEmployee(@PathVariable("id") long id) {
				employeeService.deleteById(id);
				return new EmployeeDto();
		}
	}