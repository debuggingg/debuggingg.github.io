package xyz.itwill07.dao;

import java.util.List;

public interface StudentService {
	void addStudnent(Student student);
	void modifyStudnent(Student student);
	void removeStudnent(int no);
	Student getStudnent(int no);
	List<Student> getStudnentList();
}