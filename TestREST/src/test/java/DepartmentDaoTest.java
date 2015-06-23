import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.testrest.dao.IDepartmentDao;
import by.testrest.domain.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:jtest-context.xml")
@Transactional
public class DepartmentDaoTest {

	@Autowired
	IDepartmentDao departmentDao;
	
	@Test
	public void testSave(){
		Department department = new Department();
		Long id = departmentDao.save(department);
		assertFalse(id==null);
	}
	
	@Test
	public void testUpdate(){
		Department department = new Department();
		department.setId(new Long(5));
		department.setName("testDepartment");
		departmentDao.update(department);
		assertEquals("testDepartment", departmentDao.getDepartmentById(department.getId()).getName());
	}
	
	@Test
	public void testGetDepartmentById(){
		assertFalse(departmentDao.getDepartmentById(new Long(5))==null);
	}
	
	@Test
	public void testRemoveDepartment(){
		departmentDao.deleteById(new Long(1));
		assertEquals(departmentDao.getDepartmentById(new Long(1)), null);
	}
	
	@Test 
	public void testAllDepartments(){
		List<Department> departments = departmentDao.getAllDepartments();
		assertEquals(10, departments.size());
	}
}
