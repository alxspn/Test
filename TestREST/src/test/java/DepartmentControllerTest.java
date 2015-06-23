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

import by.test.dto.DepartmentDto;
import by.testrest.controllers.DepartmentController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:jtest-context.xml")
@Transactional
public class DepartmentControllerTest {

    MockHttpServletRequest requestMock;
    MockHttpServletResponse responseMock;
    AnnotationMethodHandlerAdapter handlerAdapter;
    ObjectMapper mapper;
    
    @Autowired
    DepartmentController departmentController;
    
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
	public void testGetDepartmentById() throws Exception {
	    requestMock.setMethod("GET");
	    requestMock.setRequestURI("/departments/1");
	    handlerAdapter.handle(requestMock, responseMock, departmentController);
	    assertEquals(responseMock.getStatus(), 200);
	    DepartmentDto department = mapper.readValue(responseMock.getContentAsString(), DepartmentDto.class);
	    assertEquals(department.getId(), new Long(1));
	}
	
	@Test
	public void testGetAllDepartments() throws Exception {
	    requestMock.setMethod("GET");
	    requestMock.setRequestURI("/departments/list");
	    handlerAdapter.handle(requestMock, responseMock, departmentController);
	    assertEquals(responseMock.getStatus(), 200);
	    List<DepartmentDto> departments = Arrays.asList(mapper.readValue(responseMock.getContentAsString(), DepartmentDto[].class));
	    assertEquals(10, departments.size());
	}
	
	@Test
	public void testSave() throws Exception {
	    requestMock.setMethod("POST");
	    requestMock.setRequestURI("/departments/save");
	    DepartmentDto department = new DepartmentDto();
	    String departmentAsString=mapper.writeValueAsString(department);
	    requestMock.setContent(departmentAsString.getBytes());
	    handlerAdapter.handle(requestMock, responseMock, departmentController);
	    Long id = mapper.readValue(responseMock.getContentAsString(), DepartmentDto.class).getId();
	    assertFalse(id==null);
	}
	
	@Test
	public void testUpdate() throws Exception{
		DepartmentDto department = new DepartmentDto();
		department.setId(new Long(5));
		department.setName("testDepartment");
		String departmentAsString=mapper.writeValueAsString(department);
		requestMock.setMethod("POST");
	    requestMock.setRequestURI("/departments/update");
	    requestMock.setContent(departmentAsString.getBytes("UTF-8"));
	    handlerAdapter.handle(requestMock, responseMock, departmentController);
		String name = mapper.readValue(responseMock.getContentAsString(), DepartmentDto.class).getName();
		
		assertEquals("testDepartment", name);
	}
	
	@Test
	public void testRemoveDepartment() throws Exception{
	    requestMock.setMethod("GET");
	    requestMock.setRequestURI("/departments/delete/1");
	    handlerAdapter.handle(requestMock, responseMock, departmentController);

	    requestMock.setMethod("GET");
	    requestMock.setRequestURI("/departments/1");
	    handlerAdapter.handle(requestMock, responseMock, departmentController);

	    DepartmentDto department = mapper.readValue(responseMock.getContentAsString(), DepartmentDto.class);
	    assertEquals(department.getId(), null);

	}
   
}
