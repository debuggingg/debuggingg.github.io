package xyz.itwill09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import xyz.itwill09.dto.Hewon;

@Controller
@SessionAttributes(value="hewon")
public class SessionController {
	//아이디를 전달받아 회원정보를 검색해 반환하는 메소드 - Service 클래스의 메소드
	public Hewon getHewon(String id) {
		return Hewon.builder().id("abc123").name("홍길동").email("abc@itwill.xyz").build();
	}
	
	//아이디를 전달받아 아이디의 회원정보를 검색해 Model 객체의 속성값으로 저장하여 뷰에게 
	//제공하기 위한 요청 처리 메소드
	@RequestMapping("/hewon_view")
	public String view(@RequestParam(defaultValue = "abc123") String id, Model model) {
		//Service 클래스의 메소드를 호출하여 매개변수로 전달한 아이디의 회원정보를 반환받아 저장 
		Hewon hewon=getHewon(id);
		
		//반환받은 회원정보를 Model 객체의 속성값으로 저장
		// => 요청 처리 메소드와 뷰에서만 속성값을 제공받아 사용 가능 - Request Scope
		//model.addAttribute("hewon", hewon);
		//Model 객체에 속성명을 생략하여 속성값 저장 가능 
		// => 속성값에 대한 자료형을 속성명으로 사용 - 자료형의 첫문자는 소문자로 변환
		// => 속성값의 자료형이 String 클래스 또는 원시형(Wrapper 클래스)인 경우 속성명 생략 불가능
		model.addAttribute(hewon);
		
		return "hewon_view";
	}
	@RequestMapping(value="/hewon_update",method=RequestMethod.GET)
	public String update(@RequestParam(defaultValue = "abc123") String id, Model model) {
		Hewon hewon=getHewon(id);
		model.addAttribute(hewon);
		return "hewon_update";
	}
	@RequestMapping(value="/hewon_update",method=RequestMethod.POST)
	public String update(@ModelAttribute Hewon hewon) {
		
		return "hewon_result";
	}
}











