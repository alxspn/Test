package by.testweb.web.formcontrollers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import by.test.dto.DepartmentDto;
import by.test.dto.EmployeeDto;
import by.testweb.service.IDepartmentService;
import by.testweb.service.IEmployeeService;
import by.testweb.web.validators.EmployeeValidator;

@Controller
@RequestMapping("/Employers.html")
public class EmployeeFormController{
	
	EmployeeValidator employeeValidator;
	@Autowired
	public EmployeeFormController(EmployeeValidator employeeValidator){
		this.employeeValidator = employeeValidator;
	}
	@Autowired	
	IDepartmentService departmentManagerService;
	@Autowired
	IEmployeeService employeeManagerService;
	
	
	final Log logger = LogFactory.getLog(getClass());

 	@RequestMapping(method = RequestMethod.POST)
 	public String processSubmit(@ModelAttribute ("employee") EmployeeDto employee,
			BindingResult result, SessionStatus status, 
			HttpServletRequest request, ModelMap model) {
		
		if(isSave(request)){
			employeeValidator.validate(employee, result);
			if(!result.hasErrors()){			
				if(employee.getId()!=null){
					employeeManagerService.update(employee);
					logger.info("Update employee, name - "+employee.getName()+"; wage - "+employee.getWage()+";  id- "+employee.getId()+"; for " +request.getRemoteAddr());
				}else{
					Long id = employeeManagerService.save(employee);
					employee.setId(id);
					logger.info("Save new employee,  name - "+employee.getName()+"; wage - "+employee.getWage()+";  id- "+employee.getId()+"; for " +request.getRemoteAddr());
				}
			}
		}	
		if(isNew(request)){
			forceBind(employee, new EmployeeDto());
		}
		if(isDelete(request)){
			logger.info("Delete employee, name - "+employee.getName()+"; wage - "+employee.getWage()+";  id- "+employee.getId()+"; for " +request.getRemoteAddr());
			employeeManagerService.deleteById(employee.getId());
			forceBind(employee, new EmployeeDto());
		}
		if(isReselect(request)){
			EmployeeDto newEmployee = employeeManagerService.getEmployeeById(employee.getId());
			forceBind(employee, newEmployee);
			logger.info("Select employee, name - "+employee.getName()+"; wage - "+employee.getWage()+";  id- "+employee.getId()+"; for " +request.getRemoteAddr());
		}
		if(isDepartment(request)){
			request.getSession().setAttribute("departmentId", employee.getDepartment());
			return "redirect:Departments.html";
		}
		if(isReturn(request)){
			request.getSession().setAttribute("employeeId", null);
			return "redirect:Departments.html";
		}
		model.addAttribute("isNew", isNew(employee));
		model.addAttribute("employee", employee);
		model.addAttribute("departments", getDepartments());
		model.addAttribute("employers", getEmployers());
		return "Employers";
	}	
 	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model, HttpServletRequest request) {
		if(request.getSession().getAttribute("employeeId")!=null){
			Long id = (Long)request.getSession().getAttribute("employeeId");
			request.getSession().setAttribute("employeeId", null);
			model.addAttribute("isNew", false);
			model.addAttribute("employee", employeeManagerService.getEmployeeById(id)); 
		}else{
			model.addAttribute("employee", new EmployeeDto());
		}	
		return "Employers";
	}

	@ModelAttribute("isNew")
	public boolean isNew(@ModelAttribute ("employee") EmployeeDto employee){
		return employee.getId()==null;
	}
	
	@ModelAttribute("departments")
	public List<DepartmentDto> getDepartments(){
		return departmentManagerService.getAllDepartments();
	}
	
	@ModelAttribute("employers")
	public List<EmployeeDto> getEmployers(){
			return employeeManagerService.getAllEmployers();
	}
	
	private boolean isSave(HttpServletRequest request){
    	return request.getParameter("save")!=null;
	}
	
	private boolean isNew(HttpServletRequest request){
    	return request.getParameter("new")!=null;
	}
	
	private boolean isDelete(HttpServletRequest request){
    	return request.getParameter("delete")!=null;
	}
	
	private boolean isReturn(HttpServletRequest request){
    	return request.getParameter("return")!=null;
	}
	
	private boolean isDepartment(HttpServletRequest request){
    	return request.getParameter("getDepartment")!=null;
	}
	
    private boolean isReselect(HttpServletRequest request){
    	return (!isSave(request) & !isNew(request) & !isDelete(request) & 
    			!isReturn(request) & !isDepartment(request) & request.getParameter("id")!=null);
    }
	
    private void forceBind(EmployeeDto command, EmployeeDto employee){
    	command.setId(employee.getId());
    	command.setName(employee.getName());
    	command.setDepartment(employee.getDepartment());
    	command.setWage(employee.getWage());
    }
}