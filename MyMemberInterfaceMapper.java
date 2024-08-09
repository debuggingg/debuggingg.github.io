package xyz.itwill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import xyz.itwill.dto.MyMember;

public interface MyMemberInterfaceMapper {
	@Insert(value="insert into mymember values(#{id},#{name},#{phone},#{email})")
	int insertMyMember(MyMember member);
	@Update("update mymember set name=#{name}, phone=#{phone}, email=#{email} where id=#{id}")
	int updateMyMember(MyMember member);
	@Delete("delete from mymember where id=#{id}")
	int deleteMyMember(String id);
	@Select("select id, name, phone, email from mymember where id=#{id}")
	MyMember selectMyMember(String id);
	@Select("select id, name, phone, email from mymember order by id")
	List<MyMember> selectMyMemberList();
}
