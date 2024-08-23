package xyz.itwill05.lombok;

public class MemberBuilderApp {
	public static void main(String[] args) {
		Member member1=new Member("abc123", "홍길동", "abc@itwill.xyz");
		
		System.out.println("아이디 = "+member1.getId());
		System.out.println("이름 = "+member1.getName());
		System.out.println("이메일 = "+member1.getEmail());
		System.out.println("=============================================================");
		//Member 객체가 저장된 참조변수를 출력할 경우 자동으로 Member 클래스의 toString()
		//메소드가 호출되어 문자열을 반환받아 출력 처리
		// => Lombok 라이브러리에 의해 생성된 toString() 메소드는 객체의 모든 필드에 저장된
		//값을 문자열로 결합하여 반환
		System.out.println(member1);
		System.out.println("=============================================================");
		//클래스.builder() : 클래스에 선언된 Builder 클래스(내부 정적 클래스)로 Builder 객체를
		//생성하여 반환하는 정적 메소드
		// => Builder 객체를 참조해 클래스의 필드에 필요한 값을 저장할 수 있는 메소드 호출 가능
		//Builder.build() : Builder 클래스가 선언된 클래스(외부 클래스)로 객체를 생성하여 반환하는 메소드
		// => Builder 객체에 저장된 값을 사용해 객체 필드를 초기화 처리하여 반환
		Member member2=Member.builder()
				.id("xyz789")
				.name("임꺽정")
				.email("xyz@itwill.xyz")
				.build();
		
		System.out.println(member2);
		System.out.println("=============================================================");
	}
}