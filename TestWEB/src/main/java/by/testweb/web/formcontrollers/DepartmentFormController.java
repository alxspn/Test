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
import by.testweb.web.validators.DepartmentValidator;

@Controller
@RequestMapping("/Departments.html")
public class DepartmentFormController {
	
	DepartmentValidator departmentValidator;
	
	@Autowired
	public DepartmentFormController(DepartmentValidator departmentValidator){
		this.departmentValidator = departmentValidator;
	}
	@Autowired
	IDepartmentService departmentManagerService;
	@Autowired
	IEmployeeService employeeManagerService;
		
	final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute ("department") DepartmentDto department,
			BindingResult result, SessionStatus status, 
			HttpServletRequest request, ModelMap model) {
		
		if(isSave(request)){
			departmentValidator.validate(department, result);
			if(!result.hasErrors()){			
				if(department.getId()!=null){
					departmentManagerService.update(department);
					logger.info("Update department, new name - "+department.getName()+";  id- "+department.getId()+"; for " +request.getRemoteAddr());
				}else{
					Long id = departmentManagerService.save(department);
					department.setId(id);
					logger.info("Save new department - "+department.getName()+";  id- "+id+"; for " +request.getRemoteAddr());
				}
			}
		}
		if(isNew(request)){
			forceBind(department, new DepartmentDto());
		}
		if(isDelete(request)){
			logger.info("Delete department - "+department.getName()+";  id- "+department.getId()+"; for " +request.getRemoteAddr());
			departmentManagerService.deleteById(department.getId());
			forceBind(department, new DepartmentDto());
		}
		if(isReselect(request)){
			DepartmentDto newDepartment = departmentManagerService.getDepartmentById(department.getId());
			forceBind(department, newDepartment);
			logger.info("Select department - "+newDepartment.getName()+";  id- "+newDepartment.getId()+"; for " +request.getRemoteAddr());
		}
		if(isEmployee(request)){
			request.getSession().setAttribute("employeeId", getEmployeeId(request));
			return "redirect:Employers.html";
		}
		model.addAttribute("isNew", isNew(department));
		model.addAttribute("department", department);
		model.addAttribute("departments", getDepartments());
		model.addAttribute("employers", getEmployers(department));
		return "Departments";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model, HttpServletRequest request) {
		if(request.getSession().getAttribute("departmentId")!=null){
			Long id = (Long)request.getSession().getAttribute("departmentId");
			request.getSession().setAttribute("departmentId", null);
			model.addAttribute("isNew", false);
			model.addAttribute("employers", getEmployersByDepartmentId(id));
			model.addAttribute("department", departmentManagerService.getDepartmentById(id)); 
		}else{
			model.addAttribute("department", new DepartmentDto());
		}	
		
		return "Departments";
	}
	
	
	@ModelAttribute("departments")
	public List<DepartmentDto> getDepartments(){
		return departmentManagerService.getAllDepartments();
	}
	
	@ModelAttribute("employers")
	public List<EmployeeDto> getEmployers(@ModelAttribute ("department") DepartmentDto department){
		if(department.getId()!=null){
			return employeeManagerService.getEmployersByDepartment(department.getId());
		}else{
			return null;
		}
	}
	
	public List<EmployeeDto> getEmployersByDepartmentId(Long id){
			return employeeManagerService.getEmployersByDepartment(id);
	}
	
	@ModelAttribute("isNew")
	public boolean isNew(@ModelAttribute ("department") DepartmentDto department){
		return department.getId()==null;
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
	
	private boolean isEmployee(HttpServletRequest request){
    	return request.getParameter("employee")!=null;
	}
	
    private boolean isReselect(HttpServletRequest request){
    	return (!isSave(request) & !isNew(request) & !isDelete(request) & 
    			!isEmployee(request) & request.getParameter("id")!=null);
    }
	
    private Long getEmployeeId(HttpServletRequest request){
    	if(request.getParameter("employers")!=null && !request.getParameter("employers").equals("")){
    		return Long.valueOf(request.getParameter("employers").toString());
    	}else{
    		return null;
    	}
    }
    
    private void forceBind(DepartmentDto command, DepartmentDto department){
    	command.setId(department.getId());
    	command.setName(department.getName());
    }

}