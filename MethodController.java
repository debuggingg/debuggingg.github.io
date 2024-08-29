package xyz.itwill09.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MethodController {
	//사용자로부터 값을 입력받기 위한 JSP 문서의 뷰이름을 반환하는 요청 처리 메소드
	@RequestMapping("/method_input")
	public String inputOne() {
		return "method_input1";
	}
	
	//전달값을 반환받아 Request Scope 속성값으로 저장하고 속성값을 제공받아 출력할 수 있는
	//JSP 문서의 뷰이름을 반환하는 메소드 
	// => 요청 처리 메소드의 명령으로 POST 방식으로 요청한 경우에만 명령이 실행되도록 설정
	@RequestMapping("/method_output")
	public String outputOne(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		//클라이언트가 페이지를 GET 방식으로 요청한 경우 - 비정상적인 요청
		if(request.getMethod().equals("GET")) {
			//response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			response.sendRedirect("method_input");
			return null;
		}
		request.setCharacterEncoding("utf-8");
		String name=request.getParameter("name");
		request.setAttribute("name", name);
		return "method_output";
	}	
	
	//사용자로부터 값을 입력받기 위한 JSP 문서의 뷰이름을 반환하는 요청 처리 메소드
	// => 클라이언트가 페이지를 GET 방식으로 요청한 경우에만 요청 처리 메소드를 호출할 있도록 설정 가능
	//method 속성 : RequestMethod(Enum 자료형)의 상수필드 중 하나를 속성값으로 설정
	// => RequestMethod(Enum 자료형)에는 클라이언트의 요청방식을 상수필드로 제공
	// => method 속성값과 다른 요청방식으로 페이지를 요청한 경우 405 에러코드로 응답 처리
	//@RequestMapping 어노테이션 대신 @GetMapping 어노테이션을 사용해 GET 방식으로 요청한
	//경우에만 요청 처리 메소드가 호출되도록 설정 가능
	@RequestMapping(value = "/method", method = RequestMethod.GET)
	public String inputTwo() {
		return "method_input2";
	}
	
	//전달값을 반환받아 Request Scope 속성값으로 저장하고 속성값을 제공받아 출력할 수 있는
	//JSP 문서의 뷰이름을 반환하는 메소드 
	// => @RequestMapping 어노테이션으로 POST 방식으로 요청한 경우에만 명령이 실행되도록 설정
	// => @RequestMapping 어노테이션의 value 속성값이 중복돼도 method 속성값이 다르면 에러 미발생
	//@RequestMapping 어노테이션 대신 @PostMapping 어노테이션을 사용해 GET 방식으로 요청한
	//경우에만 요청 처리 메소드가 호출되도록 설정 가능
	@RequestMapping(value = "/method", method = RequestMethod.POST)
	public String outputTwo(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		request.setCharacterEncoding("utf-8");
		String name=request.getParameter("name");
		request.setAttribute("name", name);
		return "method_output";
	}	
}