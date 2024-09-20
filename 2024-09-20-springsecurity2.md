---
layout: single
title: 2024-09-19-springsecurity2
---
servlet-context.xml
여기에 context:component-scan 를 설정하여 우선 annotation 쓸수있게 하고, 

SecurityUser DTO, Mapper, DAO, Service, Controller 만들고, 그렇게 DATA 에 인증 정보가 들어가면, 그걸 Select로 불러 오기위해 security-context.xml 에 authentication-manager 안에 	users-by-username-query 를 이용하여 사용 할수있지만 mapper 에 그냥 새롭게 sql select 명령어를 사용하여 sql 명령어를 만들어서 불러왔다. 
이렇게 인증된 사용자 정보와 권한을 클래스에 저장 하여 사용할수있는 CustomUserDetail class 를 UserDetails  interface 를  상속 받아서 만들고 그안에 인증된 사용자 정보가 저장될 필드를 만들어 그안에 정보를 저장 한다. 
CustomUserDetails.java 안에 저장 된 정보및 권한 정보를 검색하여 인증처리후 저장된 UserDetails 객체를 반환하는 메소드가 작성된 클래스로 CustomUserDetailsService.java 를 만든다. 

CustomUserDetailsService.java 는 UserDetailsService interface 를 상속 받아 만든다
