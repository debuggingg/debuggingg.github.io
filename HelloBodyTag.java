package xyz.itwill.custom;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class HelloBodyTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private boolean test;
	public HelloBodyTag() {
		// TODO Auto-generated constructor stub
	}
	public boolean isTest() {
		return test;
	}
	public void setTest(boolean test) {
		this.test = test;
	}
	@Override
	public int doStartTag() throws JspException {
	try {
		if(test) {
			pageContext.getOut().println("<h3>");
		}else {
			pageContext.getOut().println("<p>");
			
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		try {
			if(test) {
				pageContext.getOut().println("님, 안녕하세요</h3>");
			}else {
				pageContext.getOut().println("님,안녕하세요</p>");
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		return EVAL_PAGE;
	}
}
