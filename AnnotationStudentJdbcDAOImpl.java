package xyz.itwill05.di;

import java.util.List;

import org.springframework.stereotype.Repository;

//@Component : 클래스를 스프링 컨테이너가 관리할 수 있는 Spring Bean으로 등록하기 위한 어노테이션
// => 클래스의 이름이 자동으로 식별자(beanName)으로 설정 - 클래스명의 첫문자는 소문자로 변환
// => @Component 어노테이션의 value 속성을 사용하여 beanName 변경 가능
//@Component
//@Primary : 의존성 주입을 위한 어노테이션 - 다수의 클래스 중 의존성 주입을 위한 클래스에 설정
//@Primary

//@Repository : DAO 클래스(Repository 클래스)를 스프링 컨테이너가 관리할 수 있는 Spring Bean으로 등록하기 위한 어노테이션
// => 클래스의 이름을 자동으로 식별자(beanName)으로 사용하지만 value 속성을 사용해 변경 가능
// => 트렌젝션 관리자에 의해 클래스의 메소드에 문제가 발생될 경우 롤백 처리 가능
@Repository
public class AnnotationStudentJdbcDAOImpl implements StudentDAO {
	public AnnotationStudentJdbcDAOImpl() {
		System.out.println("### AnnotationStudentJdbcDAOImpl 클래스의 기본 생성자 호출 ###");
	}
	
	@Override
	public int insertStudent(Student student) {
		System.out.println("*** AnnotationStudentJdbcDAOImpl 클래스의 insertStudent(Student student) 메소드 호출 ***");
		return 0;
	}

	@Override
	public int updateStudent(Student student) {
		System.out.println("*** AnnotationStudentJdbcDAOImpl 클래스의 updateStudent(Student student) 메소드 호출 ***");
		return 0;
	}

	@Override
	public int deleteStudent(int num) {
		System.out.println("*** AnnotationStudentJdbcDAOImpl 클래스의 deleteStudent(int num) 메소드 호출 ***");
		return 0;
	}

	@Override
	public Student selectStudent(int num) {
		System.out.println("*** AnnotationStudentJdbcDAOImpl 클래스의 selectStudent(int num) 메소드 호출 ***");
		return null;
	}

	@Override
	public List<Student> selectStudentList() {
		System.out.println("*** AnnotationStudentJdbcDAOImpl 클래스의 selectStudentList() 메소드 호출 ***");
		return null;
	}

}