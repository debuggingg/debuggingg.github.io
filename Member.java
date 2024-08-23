package xyz.itwill05.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//클래스의 생성자 또는 메소드를 자동으로 만들어주는 Lombok 라이브러리를 사용하는 방법 
//1.Lombok 라이브러리를 프로젝트에 빌드 처리 - 메이븐 사용 : pom.xml
//2.이클립스(STS3)를 종료하고 콘솔(Console - cmd)을 관리자 권한으로 실행한 후 Lombok 라이브러리
//파일이 저장된 로컬 저장소(Local Repository - 사용자 폴더의 .m2 폴더)의 폴더로 이동
//3.콘솔에서 Jar 프로그램을 실행하여 Lombok 라이브러리를 이클립스(STS3)에서 사용할 수 있도록 설정
// => Jar 프로그램 실행 방법 : java -jar lombok-1.18.34.jar
// => 탐색기에서 로컬 저장소의 Lombok 라이브러리 파일이 저장된 폴더로 이동하여 라이브러리 파일을
//더블클릭하여 실행 가능
//4.Jar 프로그램을 실행해 제공된 설치창(Installer)에서 Lombok 라이브러리를 사용할 이클립스(STS3)를
//선택해 이클립스(STS3)에 Lombok 라이브러리 설치
// => 이클립스(STS3)가 설치된 폴더로 이동하여 eclipse.ini(STS.ini) 파일을 편집하여 저장하고 
//이클립스(STS3) 실행
// => eclipse.ini(STS.ini) 파일에서 [-javaagent:lombok.jar] 변경 - lombok.jar 파일의 절대경로를 상대경로로 변경
//5.이클립스(STS3)에서 Lombok 라이브러리가 제공하는 어노테이션을 사용하여 클래스 작성
// => @NoArgsConstructor, @AllArgsConstructor, @RequiredArgsConstructor, @Setter, @Getter, @ToString 등

//@NoArgsConstructor : 매개변수가 없는 기본 생성자를 제공하기 위한 어노테이션
//@AllArgsConstructor : 모든 필드를 초기화 처리하기 위한 매개변수가 작성된 생성자를 제공하기 위한 어노테이션
//@RequiredArgsConstructor : final 제한자를 사용한 필드만 초기화 처리하기 위한 매개변수가 
//작성된 생성자를 제공하기 위한 어노테이션
// => final 제한자를 사용한 필드를 작성한 경우 @NoArgsConstructor 어노테이션를 사용하면 에러 발생 
//@Setter : 클래스에 작성된 모든 필드에 대한 Setter 메소드를 제공하기 위한 어노테이션
// => 필드에 @Setter 어노테이션을 사용하면 해당 필드에 대한 Setter 메소드 제공
//@Getter : 클래스에 작성된 모든 필드에 대한 Getter 메소드를 제공하기 위한 어노테이션
// => 필드에 @Getter 어노테이션을 사용하면 해당 필드에 대한 Getter 메소드 제공
//@ToString : 클래스에 toString() 메소드를 오버라이드 선언하기 위한 어노테이션
// => 클래스에 작성된 모든 필드값을 결합해 문자열로 반환하는 기능 제공 - 필드값 확인
//@Data : 클래스에 Setter 메소드, Getter 메소드, toString() 메소드, equals() 메소드, hashCode() 
//메소드를 제공하기 위한 어노테이션
// => VO 클래스(객체를 값처리 비교할 수 있도록 작성된 클래스)를 선언할 때 사용
//@Builder : 클래스에 Builder 클래스와 builder() 메소드를 제공하기 위한 어노테이션
// => Builder 클래스 : 객체 생성시 객체 필드에 필요한 값을 저장하기 위한 메소드를 제공하는 클래스
// => Builder 클래스의 메소드를 호출하여 객체 필드의 필요한 값이 저장되도록 설정
// => 원하는 필드만 초기화 처리할 있어 생성자보다 가족성이 좋으며 필드 순서와 상관없이 초기화
//처리할 수 있으므로 객체 생성 편리 - 테스트 프로그램 작성시 사용
//@Slf4j : 로그 이벤트를 발생할 수 있는 Logger 객체가 저장된 log 필드를 제공하기 위한 어노테이션

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Member {
	private String id;
	private String name;
	private String email;
}