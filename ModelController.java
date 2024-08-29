package xyz.itwill09.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ModelController {
	@RequestMapping("/display1")
	public String display1(Model model) {
		model.addAttribute("displayName1", "홍길동");
		//model.addAttribute("now", new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date()));
		return "model_display1";
	}
	
	@RequestMapping("/display2")
	public String display2(Model model) {
		model.addAttribute("displayName2", "임꺽정");
		//model.addAttribute("now", new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date()));
		return "model_display2";
	}
	
	@RequestMapping("/display3")
	public String display3(Model model) {
		model.addAttribute("displayName3", "전우치");
		//model.addAttribute("now", new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date()));
		return "model_display3";
	}

	//@ModelAttribute : 메소드의 반환값을 현재 Controller 클래스에 작성된 모든 요청 처리 
	//메소드의 뷰에게 속성값으로 제공하기 위한 어노테이션
	// => @ModelAttribute 어노테이션을 메소드에 작성한 경우에만 적용
	//value 속성 : 메소드의 반환값을 뷰에서 사용하기 위한 속성명을 속성값으로 설정
	// => value 속성외에 다른 속성이 없는 경우 속성값만 설정 가능
	@ModelAttribute("now")
	public String getNow() {
		return new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date());
	}
}