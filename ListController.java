package xyz.itwill08.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//모델(Model) : 클라이언트의 요청을 처리하기 기능의 클래스 - 요청 처리 클래스 
// => 클라이언트의 요청을 하나의 클래스로 처리되도록 작성 - Command Controller Pattern 

//클라이언트가 [/list.itwill]의 URL 주소로 요청한 경우 컨트롤러에 의해 클래스의 요청 처리
//메소드를 호출하여 클라이언트의 요청 처리
public class ListController implements Controller {
	//요청 처리 메소드 : 클라이언트의 요청을 처리하기 위한 명령 작성
	// => 응답 처리할 뷰(View)의 이름(ViewName) 반환
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//데이타를 처리하는 명령 작성 - Service 클래스의 메소드 호출
		List<Member> memberList=new ArrayList<Member>();
		memberList.add(new Member("abc123", "홍길동", "서울시 강남구"));
		memberList.add(new Member("opq456", "임꺼정", "인천시 월미구"));
		memberList.add(new Member("xyz789", "전우치", "수원시 팔달구"));
		
		//요청에 대한 처리결과를 뷰(View)에게 제공하기 위한 request 객체의 속성값으로 저장
		request.setAttribute("memberList", memberList);
		
		return "member_list";
	}

}