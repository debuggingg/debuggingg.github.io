package xyz.itwill08.mvc;

public class ViewResolver {
	public String getView(String viewName) {
		return "/WEB-INF/mvc/"+viewName+".jsp";
	}
}
