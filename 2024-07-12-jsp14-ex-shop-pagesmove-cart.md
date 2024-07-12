---
layout: single
title: 2024/07/12 JSP 14-EX-Shop-PageMove - ex/cart
---
## Pages Movement

#### URLpage( loginUrl) -jspf

Encode the URL address 
	- url :  Path of the JSP document from the requested URL address +the query string from the requested URL address (if  queryString url there is.)

```jsp
<%@page import="java.net.URLEncoder"%>
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// Retrieve the authorization-related information stored in the session object as an attribute
	MemberDTO loginMember=(MemberDTO)session.getAttribute("loginMember");

	// Response handling for non-logged-in users requesting the JSP document
	if(loginMember == null) {
		// request.getRequestURI(): Method to return the path of the JSP document from the request URL address
		String requestURI=request.getRequestURI();
		// System.out.println("requestURI = "+requestURI);//requestURI = /shop/index.jsp
				
		// request.getQueryString(): Method to return the query string from the request URL address
		String queryString=request.getQueryString();		
		// System.out.println("queryString = "+queryString);//queryString = workgroup=cart&work=cart_list
		
		String url=requestURI;
		if(queryString != null) {
			url+="?"+queryString;
		}
		
		// Encode the URL address for storage
		url=URLEncoder.encode(url, "utf-8");
		System.out.println("url = "+url);//queryString = workgroup=cart&work=cart_list
		
		request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=member&work=member_login&url="+url);
		return;	
	}
%> 
```


---
### PageMovment Flower 
**On the Cart Page:**

When the user clicks on "CartList":(Use: login_url.jspf)

```jsp
<%-- Check if the user is logged in --%>

   <%-- MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

    // If user is not logged in, prepare the URL for redirection
    if (loginMember == null) {
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        String url = requestURI;
        if (queryString != null) {
            url += "?" + queryString;
        }

        url = URLEncoder.encode(url, "utf-8");
        request.setAttribute("returnUrl", request.getContextPath() + "/login.jsp?url=" + url);
        return;
    }--%>
   
<%@include file="/security/login_url.jspf" %>

<h1>CartList</h1>
```

**In the `login.jsp` Page:**

Retrieve the URL parameter and set it in a hidden input field within a form:

```jsp
<%-- Retrieve the URL parameter --%>
<%
    String url = request.getParameter("url");
    if (url == null) {
        url = "";
    }
%>

<%-- Form with hidden input to send the URL parameter --%>
<form action="<%=request.getContextPath() %>/index.jsp?workgroup=member&work=member_login_action" method="post">
    <input type="hidden" name="url" value="<%=url%>">
    <%-- Other form elements for login --%>
    ...
</form>
```

**In the `member_login_action` Page:**

Retrieve the URL parameter and determine the redirection based on its presence:

```jsp
<%-- Retrieve the URL parameter --%>
<%
    String url = request.getParameter("url");
    if (url == null || url.equals("")) {
        // If no URL parameter, redirect to the main page
        request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=main&work=main_page");
    } else {
        // Redirect to the specified URL
        request.setAttribute("returnUrl", url);
    }
%>
```

---

This sequence ensures that users are redirected back to the cart page after logging in, maintaining a seamless user experience.

