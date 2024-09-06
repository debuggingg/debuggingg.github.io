package xyz.itwill09.dto;

import lombok.Builder;
import lombok.Data;

//DTO 클래스 : 테이블의 행을 Java 클래스로 표현해 객체로 생성하기 위한 클래스 
// => 데이타 처리 클래스(DAO 클래스 또는 Service 클래스)의 메소드에서 매개변수로 값을 전달받거나
//값을 반환하기 위해 작성하는 클래스
// => Controller 클래스의 요청 처리 메소드의 매개변수로 전달값이 저장된 Command 객체로 제공받기 위해 
//사용 - 전달값의 이름과 필드의 이름이 같도록 작성

@Data
@Builder
public class Student {
	//Mybatis 프레임워크를 사용할 경우 검색행의 컬럼값을 저장하기 위해 컬럼명과 같은 이름으로
	//필드를 작성해 자동 매핑되도록 설정 가능
	private int no;
	private String name;
	private String phone;
	private String address;
	private String birthday;
}