package xyz.itwill07.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StudentRowMapper implements RowMapper<Student> {

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

}