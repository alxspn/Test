package by.testrest.dao;

import java.util.List;

import by.testrest.domain.Employee;


public interface IEmployeeDao {
	
	Employee getEmployeeById(Long id);
	
	List<Employee> getAllEmployers();
	
	List<Employee> getEmployersByDepartment(Long id);
	
	Long save(Employee employee);
	
	void update(Employee employee);
	
	void deleteById(Long id);
}
