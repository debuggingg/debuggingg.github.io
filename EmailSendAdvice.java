package xyz.itwill06.aop;

import org.aspectj.lang.JoinPoint;

public class EmailSendAdvice {
	//메일 전송 전에 삽입되어 실행될 명령이 작성된 메소드 - Before Advice Method
	// => 받는 사람의 이메일 주소와 메일 제목을 타겟메소드의 매개변수로 제공받아 기록하는 명령 작성
	public void accessLog(JoinPoint joinPoint) {
		//타겟메소드의 매개변수에 저장된 받은 사람의 이메일 주소를 반환받아 저장
		String email=(String)joinPoint.getArgs()[0];
		//타겟메소드의 매개변수에 저장된 메일 제목을 반환받아 저장
		String subject=(String)joinPoint.getArgs()[1];
		System.out.println(email+"님에게 <"+subject+"> 제목의 메일을 전송합니다.");		
	}
	
	//메일 전송 성공 후 삽입되어 실행될 명령이 작성된 메소드 - After Returning Advice Method
	// => 받는 사람의 이메일 주소를 타겟메소드의 반환값을 매개변수로 전달받아 기록하는 명령
	public void successLog(String email) {
		System.out.println(email+"님에게 메일을 성공적으로 전송 하였습니다.");
	}
	
	//메일 전송 실패 후 삽입되어 실행될 명령이 작성된 메소드 - After Throwing Advice Method
	// => 메일 전송 실패에 대한 타겟메소드의 예외 정보를 매개변수로 전달받아 기록하는 명령
	public void errorLog(Exception exception) {
		System.out.println("메일 전송 실패 = "+exception.getMessage());
	}
}