package by.testrest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import by.testrest.dao.IEmployeeDao;
import by.testrest.domain.Employee;


public class EmployeeDaoImpl extends BaseAbstractDao
	implements IEmployeeDao{
	
	@Autowired
	SessionFactory sessionFactory;
	

	@SuppressWarnings("unchecked")
	public Employee getEmployeeById(Long id) {
		String queryString = "from Employee e where e.id = :id";
		List<Employee> employers = sessionFactory.getCurrentSession().createQuery(queryString).setParameter("id", id).list();
		if(employers.isEmpty()){
			return null;
		}else{
			return employers.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployers() {
		return(List<Employee>) super.findAll(sessionFactory, Employee.class, "name");
	}

	@SuppressWarnings("unchecked")
	public List<Employee> getEmployersByDepartment(Long id) {
		String queryString = "from Employee e where e.department.id = :id order by e.name asc";
		List<Employee> employers = sessionFactory.getCurrentSession().createQuery(queryString).setParameter("id", id).list();
		return employers;
	}

	public Long save(Employee employee) {
		return super.saveEntity(sessionFactory, employee);
	}

	public void update(Employee employee) {
		super.updateEntity(sessionFactory, employee);
	}

	public void deleteById(Long id) {
		super.deleteEntityById(sessionFactory, Employee.class, id);
	}

}
