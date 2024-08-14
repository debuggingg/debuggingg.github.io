<%@page import="xyz.itwill.dao.MyHewonDAO"%>
<%@page import="xyz.itwill.dto.MyHewon"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int scope=0;
	if(request.getParameter("scope") != null) {
		scope=Integer.parseInt(request.getParameter("scope"));
	}

	List<MyHewon> hewonList=null;
	if(scope == 0) {	
		hewonList=MyHewonDAO.getDAO().selectHewonList();//전체 검색
	} else {
		hewonList=MyHewonDAO.getDAO().selectScopeHewonList(scope);//조건 검색
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYBATIS</title>
<style type="text/css">
table {
	border: 1px solid black;
	border-collapse: collapse;
}

td {
	border: 1px solid black;
	text-align: center;
	padding: 3px;	
}

.id { width: 150px; }
.name { width: 150px; }
.phone { width: 200px; }
.email { width: 200px; }
.scope { width: 100px; }
</style>
</head>
<body>
	<h1>회원목록</h1>
	<hr>
	<table>
		<tr>
			<td class="id">아이디</td>
			<td class="name">이름</td>
			<td class="phone">전화번호</td>
			<td class="email">이메일</td>
			<td class="scope">공개범위</td>
		</tr>
		<% if(hewonList.isEmpty()) { %>
		<tr>
			<td colspan="5">검색된 회원정보가 없습니다.</td>
		</tr>
		<% } else { %>
			<% for(MyHewon hewon : hewonList) { %>
			<tr>
				<td class="id"><%=hewon.getId() %></td>
				<td class="name"><%=hewon.getName() %></td>
				<td class="phone"><%=hewon.getPhone() %></td>
				<td class="email"><%=hewon.getEmail() %></td>
				<td class="scope"><%=hewon.getScope() %></td>
			</tr>	
			<% } %>
		<% } %>
	</table>
	<br>
	
	<form method="post">
		회원검색(공개범위) : 
		<select name="scope">
			<option value="0" selected="selected">전체</option>
			<option value="1">&nbsp;1&nbsp;</option>
			<option value="2">&nbsp;2&nbsp;</option>
			<option value="3">&nbsp;3&nbsp;</option>
			<option value="4">&nbsp;4&nbsp;</option>
		</select>
		<button type="submit">검색</button>
	</form>
</body>
</html>









