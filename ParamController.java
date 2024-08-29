package xyz.itwill09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.CharacterEncodingFilter;
@Controller
public class ParamController {
	@RequestMapping(value="/param",method=RequestMethod.GET)
	public String form() {
		return "param_form";
	}
/*
	@RequestMapping(value="/param",method=RequestMethod.POST)
	public String action(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		String food=request.getParameter("food");
		request.setAttribute("food", food);
		
		return "param_display";
	}
	*/
	@RequestMapping(value="/param",method=RequestMethod.POST)
	public String action(String food,Model model) {
	
		model.addAttribute("food",food);
		return "param_display";
	}
}
