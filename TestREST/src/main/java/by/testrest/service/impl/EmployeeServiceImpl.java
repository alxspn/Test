package by.testrest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import by.test.dto.EmployeeDto;
import by.testrest.dao.IDepartmentDao;
import by.testrest.dao.IEmployeeDao;
import by.testrest.domain.Department;
import by.testrest.domain.Employee;
import by.testrest.service.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService{
	
	@Autowired
	IEmployeeDao employeeDao;
	@Autowired
	IDepartmentDao departmentDao;
	
	private EmployeeDto getDtoFromEntity(Employee employee){
		if(employee!=null){
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setId(employee.getId());
			employeeDto.setDepartment(employee.getDepartment().getId());
			employeeDto.setDepartmentName(employee.getDepartment().getName());
			employeeDto.setName(employee.getName());
			employeeDto.setWage(employee.getWage().toString());
			employeeDto.setFullName(employee.getName()+" " + employee.getWage()+" ("+employee.getDepartment().getName()+")");
			return employeeDto;
		}else{
			return null;
		}
	}

	private Employee getEntityFromDto(Employee employee, EmployeeDto employeeDto){
		Department department = departmentDao.getDepartmentById(employeeDto.getDepartment());
		employee.setId(employeeDto.getId());
		employee.setDepartment(department);
		employee.setName(employeeDto.getName());
		employee.setWage(Integer.valueOf(employeeDto.getWage()));
		
		return employee;
		
	}
	
	private List<EmployeeDto> getDtosFromEntity(List<Employee> employers){
		if(employers!=null && !employers.isEmpty()){
			List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
			Employee employee;
			for (Object employers1 : employers) {
				employee = (Employee) employers1;
				employeeDtos.add(getDtoFromEntity(employee));
			}
			return employeeDtos;
		}else{
			return null;
		}
	}
	
	public EmployeeDto getEmployeeById(Long id) {
		return getDtoFromEntity(employeeDao.getEmployeeById(id));
	}

	public List<EmployeeDto> getAllEmployers() {
		return getDtosFromEntity(employeeDao.getAllEmployers());
	}

	public List<EmployeeDto> getEmployersByDepartment(Long id) {
		return getDtosFromEntity(employeeDao.getEmployersByDepartment(id));
	}

	public Long save(EmployeeDto employeeDto) {
		return employeeDao.save(getEntityFromDto(new Employee(), employeeDto));
	}

	public void update(EmployeeDto employeeDto) {
		Employee employee = employeeDao.getEmployeeById(employeeDto.getId());
		employeeDao.update(getEntityFromDto(employee, employeeDto));
	}

	public void deleteById(Long id) {
		employeeDao.deleteById(id);
	}

}