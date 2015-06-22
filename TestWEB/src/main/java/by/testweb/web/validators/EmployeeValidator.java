package by.testweb.web.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import by.test.dto.EmployeeDto;

public class EmployeeValidator implements Validator {
	
 
	final Log logger = LogFactory.getLog(getClass());
	
	public boolean supports(Class<?> clazz) {
		return EmployeeDto.class.isAssignableFrom(clazz);
	}
	
	
	public void validate(Object target, Errors errors) {
		
		EmployeeDto employee = (EmployeeDto)target;

	   	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.employee.name.empty");
	   	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "department", "error.employee.department.empty");

	   	try{
	   		Integer wage = Integer.valueOf(employee.getWage());
	   		if(wage<=0){
	   			errors.rejectValue("wage", "error.employee.wage.negative");
	   		}
	   	}catch(Exception e){
	   		errors.rejectValue("wage", "error.employee.wage.negative");
	   	}
	}

}
