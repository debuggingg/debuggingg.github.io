package xyz.itwill.spring;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//[*] 기호를 사용해 Spring Bean Configuration File 제공 가능
// => [**] 기호를 사용하면 하위 폴더의 작성된 Spring Bean Configuration File 제공
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@Slf4j
public class StudentControllerTest {
	//WebApplicationContext 객체(스프링 컨테이너)를 제공받아 필드에 저장되도록 의존성 주입
	@Autowired
	private WebApplicationContext context;
	
	//MockMvc 객체를 저장하기 위한 필드
	// => MockMvc 객체 : 요청과 응답을 가상으로 제공하기 위한 객체 - Front Controller 기능 제공
	private MockMvc mvc;
	
	//@Before : 테스트 메소드 호출 전에 실행될 명령이 작성된 메소드를 호출하여 초기화 처리하는
	//기능을 제공하는 어노테이션
	@Before
	public void setup() {
		//MockMvcBuilders.webAppContextSetup(WebApplicationContext context)
		// => MockMvcBuilder 객체를 생성하여 반환하는 메소드
		// => Front Controller 기능을 제공하는 MockMvc 객체에 스프링 컨테이너를 제공하기 위해
		//매개변수에 WebApplicationContext 객체 전달
		//MockMvcBuilder.build() : MockMvc 객체를 반환하는 메소드
		mvc=MockMvcBuilders.webAppContextSetup(context).build();
		log.info("MockMvc 객체 생성");
	}
	
	@Test
	public void testStudentDisplay() throws Exception {
		//MockMvcRequestBuilders.get(String url) :  요청 URL 주소의 페이지를 GET 방식으로 
		//요청하기 위한 RequestBuilder 객체를 반환하는 정적메소드
		//MockMvc.perform(RequestBuilder requestBuilder) : RequestBuilder 객체에 저장된 정보를
		//사용해 페이지를 요청하는 메소드 - 요청 처리 메소드 호출
		// => 요청 처리 메소드의 실행결과가 저장된 ResultActions 객체 반환
		//ResultActions.andReturn() :  요청 처리 메소드의 반환값이 저장된 MvcResult 객체를 반환하는 메소드
		MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/student/display")).andReturn();
		
		log.info(result.getModelAndView().getViewName());
		log.info(result.getModelAndView().getModel().toString());
	}
}









