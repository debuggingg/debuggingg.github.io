package xyz.itwill07.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.Setter;

//SpringDAO 기능을 사용해 DAO 클래스 작성 - spring-jdbc 라이브러리의 클래스 사용
// => Template Method Pattern이 적용된 JdbcTemplate 객체의 메소드의 호출하여 DAO 클래스의 메소드 작성
//Template Method Pattern : 기능 구현을 쉽게 제공하기 위해 메소드에 필요한 명령이 미리 작성된 디자인 패턴
// => 템플릿 메소드의 매개변수에 필요한 값(객체)를 전달하여 원하는 기능 구현 가능

//STUDENT 테이블에 행을 삽입하거나 저장된 행을 변경, 삭제, 검색하기 위한 기능을 제공하는 DAO 클래스
public class StudentDAOImpl implements StudentDAO {
	//JdbcTemplate 객체를 저장하기 위한 필드 작성
	// => Spring Bean Configuration File에서 StudentDAOImpl 클래스를 Spring Bean으로 등록할
	//때 스프링 컨테이너로부터 JdbcTemplate 객체를 제공받아 필드에 저장되도록 의존성 주입
	@Setter
	private JdbcTemplate jdbcTemplate;
	
	//학생정보를 전달받아 STUDENT 테이블의 행으로 삽입하고 삽입행의 갯수를 반환하는 메소드
	@Override
	public int insertStudent(Student student) {
		String sql="insert into student values(?,?,?,?,?)";
		//JdbcTemplate.update(String sql, Object ... args) : SQL 명령(INSERT, UPDATE, DELETE)을
		//DBMS 서버에 전달하여 실행하는 메소드 - 조작행의 갯수(int) 반환
		// => 매개변수에는 SQL 명령과 SQL 명령에 필요한 값을 차례대로 전달하여 제공
		// => SQL 명령에 필요한 값은 InParameter(?)의 갯수와 순서에 맞게 차례대로 전달
		return jdbcTemplate.update(sql, student.getNo(), student.getName()
				, student.getPhone(), student.getAddress(), student.getBirthday());
	}

	//학생정보를 전달받아 STUDENT 테이블에 저장된 행을 변경하고 변경행의 갯수를 반환하는 메소드 
	@Override
	public int updateStudent(Student student) {
		String sql="update student set name=?, phone=?, address=?, birthday=? where no=?";
		return jdbcTemplate.update(sql, student.getName(), student.getPhone()
				, student.getAddress(), student.getBirthday(), student.getNo());
	}

	//학번을 전달받아 STUDENT 테이블에 저장된 행을 삭제하고 삭제행의 갯수를 반환하는 메소드 
	@Override
	public int deleteStudent(int no) {
		return jdbcTemplate.update("delete from student where no=?", no);
	}

	//학번을 전달받아 STUDENT 테이블에 저장된 행을 검색하여 학생정보를 반환하는 메소드 
	@Override
	public Student selectStudent(int no) {
		try {
			String sql="select no, name, phone, address, birthday from student where no=?";
			//JdbcTemplate.queryForObject(String sql, RowMapper<T> rowMapper, Object ... object)
			// => SQL 명령(SELECT)을 DBMS 서버에 전달하여 실행하는 메소드 
			// => 단일행의 검색결과를 DTO 객체(Wrapper 또는 String)로 반환하기 위해 사용하는 메소드
			// => 매개변수에는 SQL 명령과 검색행을 Java 객체로 변환하는 정보를 제공하는 RowMapper 객체
			//, SQL 명령에 필요한 값을 차례대로 전달하여 제공
			//RowMapper 객체 : 검색행을 Java 객체로 변환하기 위한 정보를 저장한 객체
			// => RowMapper 인터페이스의 추상메소드를 오버라이드 선언하여 메소드에 검색행의 컬럼값을
			//객체 필드에 저장하기 위한 매핑정보를 제공하는 명령 작성
			// => RowMapper 인터페이스의 제네릭은 검색행이 변환될 Java 객체의 자료형(클래스) 설정
			/*
			return jdbcTemplate.queryForObject(sql, new RowMapper<Student>() {
				@Override
				public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
					//검색행을 Java 객체로 변환하여 반환하는 명령 작성
					// => 검색행의 컬럼값이 Java 객체 필드에 저장되도록 설정
					return Student.builder()
							.no(rs.getInt("no"))
							.name(rs.getString("name"))
							.phone(rs.getString("phone"))
							.address(rs.getString("address"))
							.birthday(rs.getString("birthday"))
							.build();
				}
			}, no);
			*/
			
			return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), no);
		} catch (EmptyResultDataAccessException e) {
			//EmptyResultDataAccessException : queryForObject() 메소드로 전달되어 실행된 
			//SELECT 명령에 대한 검색행이 없는 경우 발생되는 예외
			return null;
		}
	}

	@Override
	public List<Student> selectStudentList() {
		String sql="select no, name, phone, address, birthday from student order by no";
		//JdbcTemplate.query(String sql, RowMapper<T> rowMapper, Object ... object)
		// => SQL 명령(SELECT)을 DBMS 서버에 전달하여 실행하는 메소드 
		// => 다수행의 검색결과를 List 객체로 반환하기 위해 사용하는 메소드
		// => 매개변수에는 SQL 명령과 검색행을 Java 객체로 변환하는 정보를 제공하는 RowMapper 객체
		//, SQL 명령에 필요한 값을 차례대로 전달하여 제공
		/*
		return jdbcTemplate.query(sql, new RowMapper<Student>() {
			@Override
			public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
				return Student.builder()
						.no(rs.getInt("no"))
						.name(rs.getString("name"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.birthday(rs.getString("birthday"))
						.build();
			}
		});
		*/
		
		return jdbcTemplate.query(sql, new StudentRowMapper());
	}
}





