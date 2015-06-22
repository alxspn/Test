package by.testrest.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Department implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8061583284910517448L;
	
	
	private Long id;
	private String name;
	private Double averageWage;

	private Set<Employee> employers = new HashSet<Employee>();
	
	public Department(){}

	public Department(Long id, String name, Double averageWage) {
		super();
		this.id = id;
		this.name = name;
		this.averageWage = averageWage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Employee> getEmployers() {
		return employers;
	}

	public void setEmployers(Set<Employee> employers) {
		this.employers = employers;
	}

	public Double getAverageWage() {
		return averageWage;
	}

	public void setAverageWage(Double averageWage) {
		this.averageWage = averageWage;
	}
	
}