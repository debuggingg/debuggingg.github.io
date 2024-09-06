package xyz.itwill09.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import xyz.itwill09.dto.Product;

//스프링 메세지(Spring Message - 메세지를 관리하는 기능) 사용 방법
//1.메세지가 저장된 Properties 파일 생성
// => 스프링 컨테이너(WebApplicationContext 객체)가 참조할 수 있도록 [src/main/webapp] 폴더에
//Properties 파일을 작성하는 것을 권장 
// => [src/main/java] 또는 [src/main/resources] 폴더에 작성 가능
//2.Spring Bean Configuration File(servlet-context.xml)에 메세지 관리 기능을 제공하는 클래스를
//Spring Bean으로 등록하고 메세지가 저장된 Properties 파일의 경로를 필드에 저장되도록 의존성 주입

@Controller
@RequestMapping("/message")
public class MessageController {
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String message(@ModelAttribute Product product) {
		return "message/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String message(@ModelAttribute @Valid Product product, Errors errors,HttpSession session) {
		Locale locale=new Locale("en");
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
		if(errors.hasErrors()) {
			return "message/register";
		}
		return "message/success";
	}
}








