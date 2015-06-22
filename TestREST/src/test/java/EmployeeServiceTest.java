import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.test.dto.EmployeeDto;
import by.testrest.service.IEmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:jtest-context.xml")
@Transactional
public class EmployeeServiceTest {
	
	@Autowired
	IEmployeeService employeeService;
	
	
	@Test
	public void getEmployeeById(){
		assertFalse(employeeService.getEmployeeById(new Long(100))==null);
	}
	
	@Test
	public void getAllEmployers(){
		List<EmployeeDto> employers = employeeService.getAllEmployers();
		assertEquals(299, employers.size());
	}
	
	@Test
	public void getEmployersByDepartment(){
		List<EmployeeDto> employers = employeeService.getEmployersByDepartment(new Long(5));
		assertEquals(33, employers.size());
	}
	
	@Test
	public void save(){
		EmployeeDto employee = new EmployeeDto();
		employee.setName("testEmployee");
		employee.setWage("100000");
		employee.setDepartment(new Long(1));
		Long id = employeeService.save(employee);
		assertFalse(id==null);
	}
	
	@Test
	public void update(){
		EmployeeDto employee = new EmployeeDto();
		employee.setId(new Long(5));
		employee.setName("testEmployee");
		employee.setWage("100000");
		employee.setDepartment(new Long(1));
		employeeService.update(employee);
		assertEquals("testEmployee", employeeService.getEmployeeById(new Long(5)).getName());
	}
	
	@Test
	public void delete(){
		employeeService.deleteById(new Long(1));
		assertEquals(employeeService.getEmployeeById(new Long(1)), null);
	}
}