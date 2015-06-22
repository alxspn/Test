package by.testweb.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import by.test.dto.DepartmentDto;
import by.testweb.service.IDepartmentService;

public class DepartmentServiceImpl implements IDepartmentService {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public DepartmentDto getDepartmentById(Long id) {
		return restTemplate.getForObject(
				"http://localhost:8080/TestREST/departments/"+id, DepartmentDto.class);
	}

	public List<DepartmentDto> getAllDepartments() {
		DepartmentDto[] departments = restTemplate.getForObject(
				"http://localhost:8080/TestREST/departments/list", DepartmentDto[].class);
		if(departments!=null){
			return Arrays.asList(departments);
		}else{
			return null;
		}
	}

	public Long save(DepartmentDto departmentDto) {
		ResponseEntity<DepartmentDto> response = restTemplate.postForEntity(
				"http://localhost:8080/TestREST/departments/save", departmentDto, DepartmentDto.class);
		DepartmentDto department = response.getBody();

		return department.getId();
	}

	public void update(DepartmentDto departmentDto) {
		restTemplate.postForEntity(
				"http://localhost:8080/TestREST/departments/update", departmentDto, DepartmentDto.class);
	}

	public void deleteById(Long id) {
		restTemplate.getForObject(
				"http://localhost:8080/TestREST/departments/delete/"+id, DepartmentDto.class);
	}

}