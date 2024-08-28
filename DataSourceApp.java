package xyz.itwill07.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//DataSource 객체 : 다수의 Connection 객체를 미리 생성하여 저장하고 있는 객체 - DBCP(DataBase Connection Pool)
// => Spring Bean Configuration File에서 DataSource 인터페이스를 상속받은 자식클래스를 Spring
//Bean으로 등록해 스프링 컨테이너에 의해 관리되도록 설정
//DataSource 인터페이스를 상속받은 자식클래스는 spring-jdbc 라이브러리를 빌처 처리하면 사용
//가능 - 메이븐 사용 : pom.xml
// => spring-jdbc 라이브러리외에 Driver 클래스(ojdbc 라이브러리)를 제공하는 라이브러리도 
//프로젝트에 빌드 처리

//스프링 컨테이너로부터 DataSource 객체를 제공받아 DataSource 객체로 Connection 객체를 반환받아
//Connection 객체의 정보를 출력하는 프로그램
public class DataSourceApp {
	public static void main(String[] args) throws SQLException {
		ApplicationContext context=new ClassPathXmlApplicationContext("07_dao.xml");
		System.out.println("=============================================================");
		DataSource dataSource=context.getBean("dataSource", DataSource.class);
		System.out.println("dataSource = "+dataSource);
		Connection connection=dataSource.getConnection();
		System.out.println("connection = "+connection);
		connection.close();
		System.out.println("=============================================================");
		((ClassPathXmlApplicationContext)context).close();					
	}
}