import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.test.dto.DepartmentDto;
import by.testrest.service.IDepartmentService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:jtest-context.xml")
@Transactional
public class DepartmentServiceTest {
	
	@Autowired
	IDepartmentService departmentService;
	
	@Test
	public void testSave(){
		DepartmentDto department = new DepartmentDto();
		Long id = departmentService.save(department);
		assertFalse(id==null);
	}
	
	@Test
	public void testUpdate(){
		DepartmentDto department = new DepartmentDto();
		department.setId(new Long(5));
		department.setName("testDepartment");
		departmentService.update(department);
		assertEquals("testDepartment", departmentService.getDepartmentById(department.getId()).getName());
	}
	
	@Test
	public void testGetDepartmentById(){
		assertFalse(departmentService.getDepartmentById(new Long(5))==null);
	}
	
	@Test
	public void testRemoveDepartment(){
		departmentService.deleteById(new Long(1));
		assertEquals(departmentService.getDepartmentById(new Long(1)), null);
	}
	
	@Test 
	public void testAllDepartments(){
		List<DepartmentDto> departments = departmentService.getAllDepartments();
		assertEquals(10, departments.size());
	}
	
}