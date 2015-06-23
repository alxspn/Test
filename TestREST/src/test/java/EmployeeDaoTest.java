import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.testrest.dao.IEmployeeDao;
import by.testrest.domain.Department;
import by.testrest.domain.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:jtest-context.xml")
@Transactional
public class EmployeeDaoTest {

	@Autowired
	IEmployeeDao employeeDao;
	
	
	@Test
	public void getEmployeeById(){
		assertFalse(employeeDao.getEmployeeById(new Long(100))==null);
	}
	
	@Test
	public void getAllEmployers(){
		List<Employee> employers = employeeDao.getAllEmployers();
		assertEquals(299, employers.size());
	}
	
	@Test
	public void getEmployersByDepartment(){
		List<Employee> employers = employeeDao.getEmployersByDepartment(new Long(5));
		assertEquals(33, employers.size());
	}
	
	@Test
	public void save(){
		Employee employee = new Employee();
		employee.setName("testEmployee");
		employee.setWage(100000);
		employee.setDepartment(new Department(new Long(10), "test", new Double(100000)));
		Long id = employeeDao.save(employee);
		assertFalse(id==null);
	}
	
	@Test
	public void update(){
		Employee employee = new Employee();
		employee.setId(new Long(5));
		employee.setName("testEmployee");
		employee.setWage(100000);
		employee.setDepartment(new Department(new Long(10), "test", new Double(100000)));
		employeeDao.update(employee);
		assertEquals("testEmployee", employeeDao.getEmployeeById(new Long(5)).getName());
	}
	
	@Test
	public void delete(){
		employeeDao.deleteById(new Long(1));
		assertEquals(employeeDao.getEmployeeById(new Long(1)), null);
	}
}
