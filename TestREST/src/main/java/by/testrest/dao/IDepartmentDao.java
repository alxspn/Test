package by.testrest.dao;

import java.util.List;

import by.testrest.domain.Department;

public interface IDepartmentDao {
	
	Department getDepartmentById(Long id);
	
	List<Department> getAllDepartments();
	
	Long save(Department department);
	
	void update(Department department);
	
	void deleteById(Long id);

}