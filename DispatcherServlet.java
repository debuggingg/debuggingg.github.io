package xyz.itwill08.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//컨트롤러(Controller) : 클라이언트의 모든 요청을 받아 URL 주소를 분석하여 필요한 요청 처리 
//클래스(Model)의 메소드를 호출해 클라이언트의 요청을 처리하고 JSP 문서(View)로 스레드를 이동
//하여 응답 처리되도록 프로그램의 흐름을 제어하는 기능 제공 - 서블릿(Servlet)으로 구현

//컨트롤러 기능을 제공하기 위한 서블릿 클래스
// => 클라이언트의 모든 요청을 받아 처리하는 단일진입점의 역활을 수행하도록 설정 - Front Controller Pattern
// => [web.xml] 파일에서 클래스를 서블릿으로 등록하고 클라이언트의 모든 요청을 받아 처리할 수
//있도록 URL 패턴을 등록 처리
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//클라이언트가 서블릿을 요청할 때만다 자동으로 호출되는 메소드
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트의 요청 URL 주소를 분석하여 요청정보를 반환받아 저장
		String requestURI=request.getRequestURI();
		String contextPath=request.getContextPath();
		String command=requestURI.substring(contextPath.length());
		
		//클라이언트 요청정보(Command)를 이용하여 요청 처리 클래스(Model)의 객체를 제공받아 
		//메소드를 호출하여 클라이언트 요청을 처리하고 응답 관련 정보(ViewName)를 반환받아 저장
		/*
		Controller controller=null;
		if(command.equals("/list.itwill")) {
			controller=new ListController();
		}else if(command.equals("/view.itwill")) {
			controller=new ViewController();
			
		}
		String viewName=controller.handleRequest(request, response);
	*/
		
		HandlerMapping mapping=new HandlerMapping();
		Controller controller=mapping.getController(command);
		String viewName=controller.handleRequest(request,response);
		
		ViewResolver viewResolver=new ViewResolver();
		String view=viewResolver.getView(viewName);
		request.getRequestDispatcher(view).forward(request, response);
		
	}
}












