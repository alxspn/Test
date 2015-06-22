package by.testweb.service;

import java.util.List;

import by.test.dto.DepartmentDto;

public interface IDepartmentService {

	DepartmentDto getDepartmentById(Long id);
	
	List<DepartmentDto> getAllDepartments();
	
	Long save(DepartmentDto departmentDto);
	
	void update(DepartmentDto departmentDto);
	
	void deleteById(Long id);
	
}