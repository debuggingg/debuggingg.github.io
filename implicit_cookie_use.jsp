<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Cookie[] cookies=request.getCookies();
String userName="";
for(Cookie cookie:cookies){
if(cookie.getName().equals("userName")){
	userName=cookie.getValue();
	
	}
}
%>
<p>cookie name=<%=userName %></p>
<p>cookie name=${cookie.userName.value }
</p>


</body>
</html>