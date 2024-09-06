package xyz.itwill.spring;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.slf4j.Slf4j;

//SpringTest 기능을 사용해 단위 프로그램을 검사하는 테스트 프로그램을 작성하는 방법
// => 단위 프로그램 : DAO 클래스, Service 클래스, Controller 클래스 등
//1.junit 라이브러리와 spring-test 라이브러리를 프로젝트에 빌드 처리 - 메이븐 : pom.xml
//2.테스트 프로그램에서 사용할 로그 구현체의 환경설정파일 변경
// => [src/test/resources] 패키지 폴더의 [log4j.xml] 파일의 내용 변경
//3.[src/test/java] 패키지 폴더에 테스트 클래스(테스트 프로그램) 작성
// => junit 라이브러리와 spring-test 라이브러리의 scope 속성을 주석 처리한 후 클래스 작성
//4.테스트 프로그램 실행

//@RunWith : 테스트 프로그램을 실행하기 위한 클래스를 설정하는 어노테이션
// => 테스트 클래스를 객체로 생성하여 테스트 메소드를 호출해 테스트 명령 실행
//value 속성 : 테스트 프로그램을 실행하기 위한 클래스의 Class 객체를 속성값으로 설정
// => value 속성외에 다른 속성이 없는 경우 속성값만 설정 가능
//SpringJUnit4ClassRunner 클래스는 스프링 컨테이너(ApplicationContext 객체)를 생성하여 테스트
//클래스에게 객체 제공
@RunWith(SpringJUnit4ClassRunner.class)

//@WebAppConfiguration : SpringJUnit4ClassRunner 클래스에서 생성되는 스프링 컨테이너를  
//ApplicationContext 객체가 아닌 WebApplicationContext 객체로 제공하기 위한 어노테이션
@WebAppConfiguration

//@ContextConfiguration : 스프링 컨테이너에게 Spring Bean Configuration File을 제공하는 어노테이션
//locations 속성 : Spring Bean Configuration File 경로를 속성값으로 설정
// => Spring Bean Configuration File이 여러개인 경우 {} 기호를 사용해 배열 형식으로 속성값 제공
// => Spring Bean Configuration File의 경로는 file 접두사를 사용해 파일 시스템 경로로 작성해 제공 
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Slf4j
public class DataSourceTest {
	//테스트 클래스의 메소드에서 사용될 객체를 저장하기 위한 필드 작성
	// => @Autowired 어노테이션을 사용하여 필드에 객체가 저장된 의존성 주입
	// => 필드 레벨의 의존성 주입 가능 - 생성자 레벨의 의존성 주입 불가능
	@Autowired
	private DataSource dataSource;

	//@Test : 테스트 메소드로 설정하기 위한 어노테이션
	// => SpringJUnit4ClassRunner 클래스에 의해 테스트 클래스가 객체로 생성된 후 자동 호출되는 메소드
	@Test
	public void testDataSource() throws SQLException {
		log.info("DataSource = "+dataSource);
		Connection connection=dataSource.getConnection();
		log.info("Connection = "+connection);
		connection.close();
	}
}