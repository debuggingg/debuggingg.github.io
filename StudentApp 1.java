package xyz.itwill07.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentApp {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("07_dao.xml");
		System.out.println("=============================================================");
		StudentService service=context.getBean("studentService", StudentService.class);
		
		/*
		Student newStudent=Student.builder()
				.no(6000)
				.name("홍경래")
				.phone("010-3437-3431")
				.address("서울시 동작구")
				.birthday("1999-05-05")
				.build();
		
		service.addStudnent(newStudent);
		*/
		
		/*
		Student searchStudent=service.getStudnent(6000);
		//System.out.println(searchStudent);
		searchStudent.setName("고길동");
		searchStudent.setBirthday("2000-01-13");
		service.modifyStudnent(searchStudent);
		*/
		
		service.removeStudnent(6000);
		
		List<Student> studentList=service.getStudnentList();
		for(Student student : studentList) {
			System.out.println(student);
		}
		System.out.println("=============================================================");
		((ClassPathXmlApplicationContext)context).close();				
	}
}





