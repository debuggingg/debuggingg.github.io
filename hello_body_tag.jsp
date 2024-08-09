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
	<h1>Custom Tag - AnyAttribute And AnyBody</h1>
	<hr>
	<simple:helloBody test="true">Mr,Hong</simple:helloBody>
	<simple:helloBody test="false">Mr,Lim</simple:helloBody>
	<%
	String name="Mr,Jun";
	request.setAttribute("name","Mr,Il");
	%>
	<simple:helloBody test="true"><%=name %></simple:helloBody>
	<simple:helloBody test="false">${name }</simple:helloBody>
	<hr>
	<%
	boolean result=true;
	request.setAttribute("result", false);
	%>
	<simple:helloBody test="<%=result %>">Mr,Jang</simple:helloBody>
	<simple:helloBody test="${result }">Mr,Hong2</simple:helloBody>
</body>
</html>