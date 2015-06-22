package by.testweb.service;

import java.util.List;

import by.test.dto.EmployeeDto;

public interface IEmployeeService {

	EmployeeDto getEmployeeById(Long id);
	
	List<EmployeeDto> getAllEmployers();
	
	List<EmployeeDto> getEmployersByDepartment(Long id);
	
	Long save(EmployeeDto employeeDto);
	
	void update(EmployeeDto employeeDto);
	
	void deleteById(Long id);
	
}