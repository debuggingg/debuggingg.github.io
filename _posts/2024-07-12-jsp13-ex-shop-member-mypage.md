---
layout: single
title: 2024/07/12 JSP 13-EX-Shop-Member-MyPage
---
## Mypage 

## memberMypage - jsp 

- created Error Message method to (login_check.jspf) file and recall when we need. 

```java
<%@page import="xyz.itwill.dao.MemberDAO"%>
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that includes the information of a logged-in user (member information) in the HTML document and responds --%>
<%-- => Only accessible by logged-in users --%>
<%--
	//Retrieve the stored attribute value containing authorization-related information from the session object and save it as an object
	MemberDTO loginMember=(MemberDTO)session.getAttribute("loginMember");

	//Response processing for requests made by non-logged-in users to the JSP document - Abnormal request
	if(loginMember == null) {
		request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=error&work=error_400");
		return;	
	}
--%>
<%@include file="/security/login_check.jspf" %> 
= include directice 는 권한 확인 할때 자주 사용 된다. 

<style type="text/css">
#detail {
	width: 500px;
	margin: 0 auto;
	text-align: left;
}

#link {
	font-size: 1.1em;
}

#link a:hover {
	color: white;
	background: black;
}
</style>
<h1>My Information</h1>
<div id="detail">
	<p>ID = <%=loginMember.getMemberId() %></p>
	<p>Name = <%=loginMember.getMemberName() %></p>
	<p>Email = <%=loginMember.getMemberEmail() %></p>
	<p>Phone Number = <%=loginMember.getMemberMobile() %></p>
	<p>Address = [<%=loginMember.getMemberZipcode() %>]<%=loginMember.getMemberAddress1() %> 
		<%=loginMember.getMemberAddress2() %></p>
	<p>Join Date = <%=loginMember.getMemberRegisterDate() %></p>
	<p>Last Login Date = <%=loginMember.getMemberLastLogin() %></p>
</div>

<div id="link">
	<a href="">[Edit Member Information]</a>
	<a href="">[Delete Account]</a>
</div>
```

### loginCheck -jspf
```jsp
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// Retrieve the object containing authorization-related information stored in the session implicit object
	MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

	// Response handling for non-logged-in users requesting the JSP document - abnormal request
	if (loginMember == null) {
		request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
		return;	
	}
%>
```
---
