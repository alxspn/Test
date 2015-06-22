package by.testweb.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import by.test.dto.EmployeeDto;
import by.testweb.service.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService{
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public EmployeeDto getEmployeeById(Long id) {
		return restTemplate.getForObject(
				"http://localhost:8080/TestREST/employers/"+id, EmployeeDto.class);
	}

	public List<EmployeeDto> getAllEmployers() {
		EmployeeDto[] employers = restTemplate.getForObject(
				"http://localhost:8080/TestREST/employers/list", EmployeeDto[].class);
		if(employers!=null){
			return Arrays.asList(employers);
		}else{
			return null;
		}
	}

	public List<EmployeeDto> getEmployersByDepartment(Long id) {
		EmployeeDto[] employers = restTemplate.getForObject(
				"http://localhost:8080/TestREST/employers/listByDepartment/"+id, EmployeeDto[].class);
		if(employers!=null){
			return Arrays.asList(employers);
		}else{
			return null;
		}
	}

	public Long save(EmployeeDto employeeDto) {
		ResponseEntity<EmployeeDto> response = restTemplate.postForEntity(
				"http://localhost:8080/TestREST/employers/save", employeeDto, EmployeeDto.class);
		
		EmployeeDto employee = response.getBody();
		return employee.getId();
	}

	public void update(EmployeeDto employeeDto) {
		restTemplate.postForEntity(
				"http://localhost:8080/TestREST/employers/update", employeeDto, EmployeeDto.class);
	}

	public void deleteById(Long id) {
		restTemplate.getForObject(
				"http://localhost:8080/TestREST/employers/delete/"+id, EmployeeDto.class);
	}

}