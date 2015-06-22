package by.testweb.web.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import by.test.dto.DepartmentDto;

public class DepartmentValidator implements Validator {
	
	final Log logger = LogFactory.getLog(getClass());
	
	public boolean supports(Class<?> clazz) {
		return DepartmentDto.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.department.name.empty");
	}

}
