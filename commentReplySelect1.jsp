<%@page import="xyz.itwill.dto.MyComment1"%>
<%@page import="xyz.itwill.dao.MyCommentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    
    if(request.getParameter("commentNo")==null){
    	response.sendRedirect("commentUserListSelect2.jsp");
    	
    }
    int commentNo=Integer.parseInt(request.getParameter("commentNo"));
    
    MyComment1 comment=MyCommentDAO.getDAO().selectComment(commentNo);
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>