package xyz.itwill.spring;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.slf4j.Slf4j;
import xyz.itwill09.dto.Student;
import xyz.itwill09.service.StudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"
		, "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
//@FixMethodOrder : 테스트 메소드의 호출순서를 설정하기 위한 어노테이션
//value 속성 : MethodSorters Enum 자료형의 상수필드를 속성값으로 설정
// => MethodSorters.DEFAULT : JUnit 프로그램의 내부 규칙에 의해 테스트 메소드 호출
//    - 테스트 프로그램을 실행할 때마다 동일한 순서로 테스트 메소드 호출
// => MethodSorters.JVM : JVM 프로그램에 의해 테스트 메소드 호출
//    - 테스트 프로그램을 실행할 때마다 불규칙한 순서로 테스트 메소드 호출
// => MethodSorters.NAME_ASCENDING : 테스트 메소드의 이름으로 테스트 메소드 호출
//    - 테스트 프로그램을 실행할 때마다 동일한 순서로 테스트 메소드 호출
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@Slf4j
public class StudentServiceTest {
	@Autowired
	private StudentService studentService;
	
	@Test
	public void testAddStudent() {
		Student student=Student.builder().no(7000).name("고길동").phone("010-5412-2134")
				.address("서울시 광진구").birthday("1998-07-27").build();
		studentService.addStudent(student);
	}
	
	@Test
	public void testGetStudentList() {
		List<Student> studentList=studentService.getStudentList();
		
		for(Student student : studentList) {
			log.info(student.toString());
		}
	}
}