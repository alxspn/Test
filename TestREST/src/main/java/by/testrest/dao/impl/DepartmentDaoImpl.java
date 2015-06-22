package by.testrest.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import by.testrest.dao.IDepartmentDao;
import by.testrest.domain.Department;


public class DepartmentDaoImpl extends BaseAbstractDao
	implements IDepartmentDao{
	
	@Autowired
	SessionFactory sessionFactory;
	

	@SuppressWarnings("unchecked")
	public Department getDepartmentById(Long id) {
		String queryString = "from Department d where d.id = :id";
		List<Department> departments = sessionFactory.getCurrentSession().createQuery(queryString).setParameter("id", id).list();
		if(departments.isEmpty()){
			return null;
		}else{
			return departments.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Department> getAllDepartments() {
		String sql = "select d.*, e.averageWage from departments as d left join"
				+ " (select avg(ifnull(wage,0)) as averageWage, "
				+ "department from employers group by "
				+ "department) e on d.id = e.department order by d.name asc";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List<Object> result = query.list();
		Iterator<Object> iterator = result.iterator();
		List<Department> departments = new ArrayList<Department>();
		while (iterator.hasNext()) {
		    Object[] row = (Object[])iterator.next();
		    if(row[2]!=null){
		    	if(row[2].getClass().getName().equals("java.math.BigDecimal")){
		    		departments.add(new Department(((BigInteger)row[0]).longValue(), (String)row[1], ((BigDecimal)row[2]).doubleValue()));
		    	}else{
		    		departments.add(new Department(((BigInteger)row[0]).longValue(), (String)row[1], ((Integer)row[2]).doubleValue()));
		    	}
		    }else{
		    	departments.add(new Department(((BigInteger)row[0]).longValue(), (String)row[1], null));
		    }
		}
		return departments;
	}

	public Long save(Department department) {
		return super.saveEntity(sessionFactory, department);
	}

	public void update(Department department) {
		super.updateEntity(sessionFactory, department);
	}

	public void deleteById(Long id) {
		super.deleteEntityById(sessionFactory, Department.class, id);
	}

}