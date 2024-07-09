---
layout: single
title: 2024/07/09 JSP 07-Action-Forward
---
## ActionTag

# Forward 

---
## Controller
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that forwards to another JSP document based on received parameters --%>
<%
    // Retrieve and store the received parameter
    String pageName = request.getParameter("pageName");

    if (pageName == null) {
        // Respond to the client with a URL address
        // => Upon receiving the URL address, the client changes the request URL in the browser,
        //    requests and receives the executed result (HTML document) to display - Redirect
        response.sendRedirect("index.jsp");
        return;
    }

    // Store the context path of the JSP document using the received parameter
    String contextPath = "/action/forward/" + pageName + ".jsp";
%>
<%-- forward tag: Tag provided for moving the thread from the requesting JSP document to another JSP document
     to handle response processing with forward movement --%>
<%-- => Provides request and response objects of the requesting JSP document to the thread that moves
     to the destination JSP document for use --%>
<%-- => Since the thread of the JSP document is moved and processed on the server side regardless of the client,
     the request URL address remains unchanged --%>
<%-- Format: <jsp:forward page="context path of the JSP document"></jsp:include> --%>
<%-- Expression (Expression) can be used for the page attribute value of the forward tag --%>
<jsp:forward page="<%=contextPath%>"/>
```
## Index 
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
	<h1>Main Page</h1>
	<hr>
	<a href="<%=request.getContextPath()%>/action/forward/controller.jsp?pageName=login">Login</a>&nbsp;&nbsp;
	<a href="<%=request.getContextPath()%>/action/forward/controller.jsp?pageName=join">Join</a>&nbsp;&nbsp;
	<a href="<%=request.getContextPath()%>/action/forward/controller.jsp?pageName=cart">Cart</a>&nbsp;&nbsp;
	<a href="<%=request.getContextPath()%>/action/forward/controller.jsp?pageName=review">Review</a>&nbsp;&nbsp;
</body>
</html>
```
## Cart Page - Login,Join,Reviw Omitted 