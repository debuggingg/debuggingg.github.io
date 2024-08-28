package xyz.itwill07.dao;

import java.util.List;

import lombok.Setter;

//STUDENT 테이블을 사용한 데이타 처리 기능을 제공하기 위한 Service 클래스
public class StudentServiceImpl implements StudentService {
	//StudentDAO 인터페이스를 상속받은 자식클래스의 객체를 저장하기 위한 필드
	// => Spring Bean Configuration File에서 StudentServiceImpl 클래스를 Spring Bean으로 등록할
	//때 스프링 컨테이너로부터 StudentDAO 인터페이스를 상속받은 자식클래스의 객체를 제공받아 필드에
	//저장되도록 의존성 주입
	@Setter
	private StudentDAO studentDAO;
	
	@Override
	public void addStudnent(Student student) {
		studentDAO.insertStudent(student);
	}

	@Override
	public void modifyStudnent(Student student) {
		studentDAO.updateStudent(student);
	}

	@Override
	public void removeStudnent(int no) {
		studentDAO.deleteStudent(no);
	}

	@Override
	public Student getStudnent(int no) {
		return studentDAO.selectStudent(no);
	}

	@Override
	public List<Student> getStudnentList() {
		return studentDAO.selectStudentList();
	}

}