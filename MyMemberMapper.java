package xyz.itwill.mapper;

import java.util.List;

import xyz.itwill.dto.MyMember;

public interface MyMemberMapper {
	int insertMyMember(MyMember member);
	int updateMyMember(MyMember member);
	int deleteMyMember(String id);
	MyMember selectMyMember(String id);
	List<MyMember> selectMyMemberList();
}