package xyz.itwill07.dao;

import lombok.Builder;
import lombok.Data;

/*
이름       널?       유형            
-------- -------- ------------- 
NO       NOT NULL NUMBER(4)     
NAME              VARCHAR2(50)  
PHONE             VARCHAR2(20)  
ADDRESS           VARCHAR2(100) 
BIRTHDAY          DATE      
*/

//STUDENT 테이블의 하나의 행을 Student 클래스(DTO 클래스)로 작성해 표현
@Data
@Builder
public class Student {
	private int no;
	private String name;
	private String phone;
	private String address;
	private String birthday;
}