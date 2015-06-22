package by.testrest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import by.test.dto.DepartmentDto;
import by.testrest.dao.IDepartmentDao;
import by.testrest.domain.Department;
import by.testrest.service.IDepartmentService;

public class DepartmentServiceImpl implements IDepartmentService {
	
	@Autowired
	IDepartmentDao departmentDao;
	
	private DepartmentDto getDtoFromEntity(Department department){
		DepartmentDto departmentDto = new DepartmentDto();
		if(department!=null){
			departmentDto.setId(department.getId());
			departmentDto.setName(department.getName());
			departmentDto.setAverageWage(department.getAverageWage());
			if(departmentDto.getAverageWage()!=null){
				departmentDto.setFullName(departmentDto.getName()+" - "+departmentDto.getAverageWage());
			}else{
				departmentDto.setFullName(departmentDto.getName());
			}
			return departmentDto;
		}else{
			return null;
		}
		
	}

	private Department getEntityFromDto(Department department, DepartmentDto departmentDto){
		department.setId(departmentDto.getId());
		department.setName(departmentDto.getName());

		return department;
	}
	
	private List<DepartmentDto> getDtosFromEntity(List<Department> departments){
		if(departments!=null && !departments.isEmpty()){
			List<DepartmentDto> departmentDtos = new ArrayList<DepartmentDto>();
			Department department;
			for (Object departments1 : departments) {
				department = (Department) departments1;
				departmentDtos.add(getDtoFromEntity(department));
			}
			return departmentDtos;
		}else{
			return null;
		}
	}
	
	public DepartmentDto getDepartmentById(Long id) {
		return getDtoFromEntity(departmentDao.getDepartmentById(id));
	}

	public List<DepartmentDto> getAllDepartments() {
		return getDtosFromEntity(departmentDao.getAllDepartments());
	}

	public Long save(DepartmentDto departmentDto) {
		return departmentDao.save(getEntityFromDto(new Department(), departmentDto));
	}

	public void update(DepartmentDto departmentDto) {
		Department department=departmentDao.getDepartmentById(departmentDto.getId());
		departmentDao.update(getEntityFromDto(department, departmentDto));
	}

	public void deleteById(Long id) {
		departmentDao.deleteById(id);
	}

}