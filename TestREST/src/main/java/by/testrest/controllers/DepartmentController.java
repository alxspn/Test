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

import by.test.dto.DepartmentDto;
import by.testrest.service.IDepartmentService;


@Controller
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	IDepartmentService departmentService;
	
	static final Logger logger = Logger.getLogger(DepartmentController.class);
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	DepartmentDto getDepartmentById(@PathVariable("id") long id){
		DepartmentDto department = null;
		try{
			department = departmentService.getDepartmentById(id);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return department;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	List<DepartmentDto> getAllDepartments() {
		List<DepartmentDto> departments = null;
		try {
			departments = departmentService.getAllDepartments();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return departments;
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	DepartmentDto save(@RequestBody DepartmentDto department) {
		Long id = departmentService.save(department);
		department.setId(id);
		return department;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	DepartmentDto update(@RequestBody DepartmentDto department) {
		departmentService.update(department);
		return department;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody
	DepartmentDto deleteDepartment(@PathVariable("id") long id) {
			departmentService.deleteById(id);
			return new DepartmentDto();
	}
}