package xyz.itwill09.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//요청 처리 메소드의 명령으로 생성된 결과값을 뷰(JSP)에게 제공하는 방법
//1.ModelAndView 객체로 addObject() 메소드를 호출해 결과값을 속성값으로 저장하여 제공
//2.HttpServletRequest 객체로 setAttribute() 메소드를 호출해 결과값을 속성값으로 저장하여 제공
//3.Model 객체로 addAttribute() 메소드를 호출해 결과값을 속성값으로 저장하여 제공

@Controller
public class ResultController {
	/*
	@RequestMapping("/resultMav")
	public ModelAndView modelAndViewResult() {
		ModelAndView modelAndView=new ModelAndView("result_display");
		//ModelAndView.addObject(String attributeName, Object attributeValue) : ModelAndView
		//객체에 요청 처리 메소드의 실행결과를 속성값으로 저장하기 위한 메소드 - Request Scope
		// => 뷰(JSP)에서는 EL을 사용해 속성명으로 속성값을 객체로 제공받아 응답 처리 
		modelAndView.addObject("mavName", "홍길동");
		return modelAndView;		
	}
	*/
	
	//요청 처리 메소드에 매개변수를 작성하면 Front Controller가 스프링 컨테이너(WebApplicationContext 
	//객체)로부터 객체를 제공받아 매개변수에 저장해 사용되도록 제공
	@RequestMapping("/resultMav")
	public ModelAndView modelAndViewResult(ModelAndView modelAndView) {
		modelAndView.setViewName("result_display");
		modelAndView.addObject("mavName", "홍길동");
		return modelAndView;		
	}
	
	@RequestMapping("/resultRequest")
	public String requestResult(HttpServletRequest request) {
		//HttpServletRequest.setAttribute(String attributeName, Object attributeValue) : HttpServletRequest
		//객체에 요청 처리 메소드의 실행결과를 속성값으로 저장하기 위한 메소드 - Request Scope
		request.setAttribute("requestName", "임꺽정");
		return "result_display";
	}
	
	@RequestMapping("/resultModel")
	public String modelResult(Model model) {
		//Model 객체 : 요청 처리 메소드의 처리 결과값을 뷰에게 제공하기 위한 객체
		//Model.addAttribute(String attributeName, Object attributeValue) : Model 객체에 
		//요청 처리 메소드의 실행결과를 속성값으로 저장하기 위한 메소드 - Request Scope
		model.addAttribute("modelName", "전우치");
		return "result_display";
	}
}




