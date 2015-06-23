import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import by.test.dto.EmployeeDto;
import by.testrest.controllers.EmployeeController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:jtest-context.xml")
@Transactional
public class EmployeeControllerTest {
	
	MockHttpServletRequest requestMock;
	MockHttpServletResponse responseMock;
	AnnotationMethodHandlerAdapter handlerAdapter;
	ObjectMapper mapper;
	
    @Autowired
    EmployeeController employeeController;
    
	@Before
	public void setUp() {
	    requestMock = new MockHttpServletRequest();
	    requestMock.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    requestMock.addHeader("ACCEPT", MediaType.APPLICATION_JSON_VALUE);
	    responseMock = new MockHttpServletResponse();
	    handlerAdapter = new AnnotationMethodHandlerAdapter();
	    HttpMessageConverter[] messageConverters = {new MappingJacksonHttpMessageConverter()};
	    handlerAdapter.setMessageConverters(messageConverters);
	    mapper = new ObjectMapper();
    }
	
	@Test
	public void testGetEmployeeById() throws Exception{
	    requestMock.setMethod("GET");
	    requestMock.setRequestURI("/employers/1");
	    handlerAdapter.handle(requestMock, responseMock, employeeController);
	    assertEquals(responseMock.getStatus(), 200);
	    EmployeeDto employee = mapper.readValue(responseMock.getContentAsString(), EmployeeDto.class);
	    assertEquals(employee.getId(), new Long(1));
	}
	
	@Test
	public void testGetAllEmployers() throws Exception{
		requestMock.setMethod("GET");
	    requestMock.setRequestURI("/employers/list");
	    handlerAdapter.handle(requestMock, responseMock, employeeController);
	    assertEquals(responseMock.getStatus(), 200);
	    List<EmployeeDto> employers  = Arrays.asList(mapper.readValue(responseMock.getContentAsString(), EmployeeDto[].class));
	    assertEquals(299, employers.size());
	}
	
	@Test
	public void testGetEmployersByDepartment() throws Exception{
		requestMock.setMethod("GET");
	    requestMock.setRequestURI("/employers/listByDepartment/5");
	    handlerAdapter.handle(requestMock, responseMock, employeeController);
	    assertEquals(responseMock.getStatus(), 200);
	    List<EmployeeDto> employers  = Arrays.asList(mapper.readValue(responseMock.getContentAsString(), EmployeeDto[].class));
	    assertEquals(33, employers.size());
	}
	
	@Test
	public void testSave() throws Exception {
	    requestMock.setMethod("POST");
	    requestMock.setRequestURI("/employers/save");
	    EmployeeDto employee = new EmployeeDto();
		employee.setName("testEmployee");
		employee.setWage("100000");
		employee.setDepartment(new Long(10));
	    String employeeAsString=mapper.writeValueAsString(employee);
	    requestMock.setContent(employeeAsString.getBytes());
	    handlerAdapter.handle(requestMock, responseMock, employeeController);
	    Long id = mapper.readValue(responseMock.getContentAsString(), EmployeeDto.class).getId();
	    assertFalse(id==null);
	}
	
	@Test
	public void testUpdate() throws Exception{
		EmployeeDto employee = new EmployeeDto();
		employee.setId(new Long(100));
		employee.setName("testEmployee");
		employee.setWage("1000000");
		String employeeAsString=mapper.writeValueAsString(employee);
		requestMock.setMethod("POST");
	    requestMock.setRequestURI("/employers/update");
	    requestMock.setContent(employeeAsString.getBytes("UTF-8"));
	    handlerAdapter.handle(requestMock, responseMock, employeeController);
		String name = mapper.readValue(responseMock.getContentAsString(), EmployeeDto.class).getName();
		
		assertEquals("testEmployee", name);
	}
	
	@Test
	public void testRemoveEmployee() throws Exception{
	    requestMock.setMethod("GET");
	    requestMock.setRequestURI("/employers/delete/20");
	    handlerAdapter.handle(requestMock, responseMock, employeeController);

	    requestMock.setMethod("GET");
	    requestMock.setRequestURI("/employers/20");
	    handlerAdapter.handle(requestMock, responseMock, employeeController);

	    EmployeeDto employee = mapper.readValue(responseMock.getContentAsString(), EmployeeDto.class);
	    assertEquals(employee.getId(), null);

	}
}
