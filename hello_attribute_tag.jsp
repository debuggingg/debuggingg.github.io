<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="simple" uri="https://www.itwill.xyz/mvc/custom"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>Custom Tag - AnyAttribute And NoBody</h1>
	<hr>
	<%-- 커스텀 태그의 속성이 필수 속성이 아닌 경우 커스텀 태그의 속성 생략 가능 --%>
	<%-- => 커스텀 태그의 속성을 생략한 경우 태그 클래스의 생성자에서 설정한 기본값을 
	커스텀 태그의 속성값 사용 --%>
	<%-- 커스텀 태그의 속성이 필수 속성인 태그 속성을 생략하면 에러 발생 --%>
	<%-- <simple:helloAttribute/> --%>
	<simple:helloAttribute name="홍길동"/>
	
	<%-- 커스텀 태그의 속성을 사용하여 속성값을 설정한 경우 태그 클래스의 필드에 대한 Setter 
	메소드를 자동 호출하여 설정된 속성값으로 필드값 변경 --%>
	<simple:helloAttribute name="임꺽정"/>
</body>
</html>