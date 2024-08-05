---
layout: single
title: 2024/08/02 SP5-M2-EL-implicit
---

#### implicit_scope

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// JSP internal objects allow you to set attribute values with different scopes.
	// => Scopes: Page Scope, Request Scope, Session Scope, Application Scope
	pageContext.setAttribute("pageName", "Hong Gil-dong"); // Page Scope
	request.setAttribute("requestName", "Im Geok-jeong"); // Request Scope
	session.setAttribute("sessionName", "Jeon Woo-chi"); // Session Scope
	application.setAttribute("applicationName", "Il Ji-mae"); // Application Scope
	
	// It's possible to store attribute values with the same name in different JSP internal objects.
	// => If you store attribute values with the same name in different JSP internal objects, 
	// the new value will replace the existing value for that scope.
	pageContext.setAttribute("name", "Hong Gil-dong"); // Page Scope
	request.setAttribute("name", "Im Geok-jeong"); // Request Scope
	session.setAttribute("name", "Jeon Woo-chi"); // Session Scope
	application.setAttribute("name", "Il Ji-mae"); // Application Scope
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>EL Internal Objects - Scope Attributes</h1>
	<hr>
	<%-- EL Internal Objects: Basic objects provided for use in EL expressions --%>
	<%-- => All internal objects except pageContext are provided as Map objects --%>
	<%-- EL expressions sequentially search for attribute values using Scope attribute names and print them --%>
	<%-- => Page Scope >> Request Scope >> Session Scope >> Application Scope --%>
	<ul>
		<li>pageName attribute value (Page Scope) = ${pageName }</li>
		<li>requestName attribute value (Request Scope) = ${requestName }</li>
		<li>sessionName attribute value (Session Scope) = ${sessionName }</li>
		<li>applicationName attribute value (Application Scope) = ${applicationName }</li>
	</ul>
	<hr>
	<%-- When attribute values are stored with the same name in different JSP internal objects, 
	EL will search for Scope attribute values sequentially and may output unexpected values --%>
	<%-- => It is not recommended to use the same attribute names across different JSP internal objects --%>
	<%-- 
	<ul>
		<li>name attribute value (Page Scope) = ${name }</li>
		<li>name attribute value (Request Scope) = ${name }</li>
		<li>name attribute value (Session Scope) = ${name }</li>
		<li>name attribute value (Application Scope) = ${name }</li>
	</ul>
	--%>

	<%-- When attribute values with the same name are stored in different JSP internal objects, 
	EL expressions can differentiate Scope attribute values using EL internal objects --%>
	<%-- => pageScope, requestScope, sessionScope, applicationScope --%>
	<%-- => Scope-related EL internal objects are Map objects, where attribute names are used as map keys --%>
	<ul>
		<li>name attribute value (Page Scope) = ${pageScope.name }</li>
		<li>name attribute value (Request Scope) = ${requestScope.name }</li>
		<li>name attribute value (Session Scope) = ${sessionScope.name }</li>
		<li>name attribute value (Application Scope) = ${applicationScope.name }</li>
	</ul>

</body>
</html>
```

### Summary of Translation:
- **Comments Translation**: Explained the usage of JSP internal objects and their scopes, how to set attributes in different scopes, and how to handle attributes with the same name in different scopes using EL expressions.
- **Content Translation**: Translated the names and explanations related to JSP scopes into English, highlighting how to access and differentiate attributes across different scopes.


#### implicit_param_form.jsp ,implicit_param_action.jsp



```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL Internal Objects - Request Parameter</h1>
	<hr>
	<form action="implicit_param_action.jsp" method="post">
		<div><b>Personal Information</b></div>
		<div>Name: <input type="text" name="name"></div>
		<div>Address: <input type="text" name="address"></div>
		<br>
		<div><b>Favorite Foods</b></div>
		<div>Food-1: <input type="text" name="food"></div>
		<div>Food-2: <input type="text" name="food"></div>
		<br>
		<button type="submit">Submit</button>
	</form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that retrieves and includes the transmitted values in HTML tags for response --%>
<%
	request.setCharacterEncoding("utf-8");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL Internal Objects - Request Parameter</h1>
	<hr>
	<h2>Without EL</h2>
	<ul>
		<li>Name = <%=request.getParameter("name") %></li>
		<li>Address = <%=request.getParameter("address") %></li>
		<%-- When multiple values are transmitted with the same name, calling getParameter() 
		on the request object will return only the first value --%>
		<%-- 
		<li>Favorite Food-1 = <%=request.getParameter("food") %></li>
		<li>Favorite Food-2 = <%=request.getParameter("food") %></li>
		--%>
		<%-- When multiple values are transmitted with the same name, use getParameterValues() 
		on the request object to get an array of all values --%>
		<%-- => Store multiple values transmitted with the same name in the array elements 
		and use the index to access the values --%>
		<li>Favorite Food-1 = <%=request.getParameterValues("food")[0] %></li>
		<li>Favorite Food-2 = <%=request.getParameterValues("food")[1] %></li>
	</ul>
	<hr>
	<h2>Using EL</h2>
	<ul>
		<%-- Using the param or paramValues implicit objects in EL expressions retrieves and outputs 
		the transmitted values --%>
		<%-- => param and paramValues are Map objects where attribute names are used to get the values --%>
		<li>Name = ${param.name }</li>
		<li>Address = ${param.address }</li>
		<li>Favorite Food-1 = ${paramValues.food[0] }</li>
		<li>Favorite Food-2 = ${paramValues.food[1] }</li>
	</ul>
</body>
</html>
```

### Summary of Translation:
- **Comments Translation**: Described the usage of JSP internal objects for handling request parameters and how to retrieve and display those parameters using both JSP scriptlets and EL expressions.
- **Content Translation**: Translated the form elements and parameter handling into English, highlighting how to process and display multiple values using JSP and EL.
#### implicit_header
Sure! Here is the English translation of the provided JSP code and comments:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL Internal Objects - Request Header</h1>
	<hr>
	<h2>Without EL</h2>
	<ul>
		<%-- request.getHeader(String name): This method returns the value of the specified header name 
		from the request message's header --%>
		<li>Current server = <%=request.getHeader("host") %></li>
		<li>Client browser type = <%=request.getHeader("user-Agent") %></li>
		<li>Document types acceptable in the body = <%=request.getHeaders("accept").nextElement() %></li>
	</ul>
	<hr>
	<h2>Using EL</h2>
	<ul>
		<%-- Using the header or headerValues implicit objects in EL expressions retrieves and displays 
		the values stored in the request header --%>
		<%-- => header and headerValues are Map objects where the names of the headers are used to get the values --%>
		<li>Current server = ${header.host}</li>
		<li>Client browser type = ${header["user-Agent"]}</li>
		<li>Document types acceptable in the body = ${headerValues.accept[0]}</li>
	</ul>

</body>
</html>
```

### Summary of Translation:
- **Comments Translation**: Explained how to use `request.getHeader` and `request.getHeaders` methods to retrieve header values without EL, and how to use EL expressions to access header values through the `header` and `headerValues` implicit objects.
- **Content Translation**: Translated the labels and method descriptions into English, focusing on how to retrieve and display HTTP header information using JSP scriptlets and EL expressions.


#### implicit_cookie_add.jsp, implicit_cookie_use.jsp


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // To deliver cookies to the client for storage, create a Cookie object
    // => The name and value of the cookie stored in the Cookie object should be strings composed of English letters, numbers, and some special characters
    // => If using characters other than English letters, numbers, and some special characters for the cookie name and value, they should be encoded
    Cookie cookie = new Cookie("userName", "HongGilDong");
    cookie.setMaxAge(60 * 60); // Change the cookie's lifetime (in seconds)
    // Use the response object's addCookie() method to send the Cookie object to the client for storage
    response.addCookie(cookie);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
    <h1>EL Internal Objects - Cookie</h1>
    <hr>
    <p>The cookie has been delivered to the client for storage.</p>
    <p><a href="implicit_cookie_use.jsp">Print Cookie Value</a></p>
</body>
</html>    
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
    <h1>EL Internal Objects - Cookie</h1>
    <hr>
    <h2>Without EL</h2>
    <%
        // Retrieve all cookies sent from the client as an array
        Cookie[] cookies = request.getCookies();
        String userName = ""; // Variable to store the cookie value
        // Iterate through the array of cookies to process them
        for (Cookie cookie : cookies) {
            // Compare the name of the cookie with the specified name
            if (cookie.getName().equals("userName")) {
                // Store the value of the matching cookie in a variable
                userName = cookie.getValue();
            }
        }
    %>
    <p>Name stored in cookie = <%=userName %></p>
    <hr>
    <%-- Using the cookie implicit object in an EL expression retrieves and displays the cookie values sent from the client --%>
    <%-- => The cookie implicit object is a Map object where the cookie names are used to get the Cookie object 
    and the value name is used to output the cookie value --%>
    <%-- <p>Name stored in cookie = ${cookie.userName}</p> --%>    
    <p>Name stored in cookie = ${cookie.userName.value}</p>    
</body>
</html>
```

### Summary of Translation:
- **Comments Translation**: Explained how to create and set cookies in JSP, how to retrieve and display cookie values using both scriptlets and EL (Expression Language), and how to handle cookie values.
- **Content Translation**: Translated the labels and descriptions of methods used for cookie handling and displaying cookie values, including the use of the `cookie` implicit object in EL expressions.

#### implicit_init.jsp,  some method added to web.xml
#### XML first 

```xml
<context-param>

<param-name>name</param-name>

<param-value>홍길동</param-value>

</context-param>
```


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
    <h1>EL Internal Objects - Context Init Parameter</h1>
    <hr>
    <h2>Without EL</h2>
    <%-- application.getInitParameter(String name) : Method to retrieve values provided by the context-param element in the web.xml file --%>
    <p>Name = <%=application.getInitParameter("name") %></p>
    <%-- The application JSP implicit object stores the ServletContext object --%>
    <p>Name = <%=request.getServletContext().getInitParameter("name") %></p>
    <hr>
    <h2>Using EL</h2>    
    <%-- Using the initParam implicit object in an EL expression retrieves and displays values provided by the context-param element in the web.xml file --%>
    <%-- => The initParam implicit object is a Map object where the param-name element's name is used as the key 
    and the param-value element's value is retrieved and displayed --%>
    <p>Name = ${initParam.name }</p>
</body>
</html>
```

### Summary of Translation:
- **Comments Translation**: Describes how to retrieve context initialization parameters using both scriptlets and EL (Expression Language) in JSP. 
- **Content Translation**: Explains how to get values from the `web.xml` file's `context-param` element using the `application` implicit object and `initParam` EL object.
#### implicit_page.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
    <h1>EL Internal Objects - PageContext</h1>
    <hr>
    <h2>Without EL</h2>
    <%-- request implicit object: An object storing request information as an HttpServletRequest object --%>
    <p>Context Root Directory Path = <%=request.getContextPath() %></p>
    <p>Request URI Address = <%=request.getRequestURI() %></p>
    <hr>
    <%-- pageContext implicit object: A PageContext object storing all information needed for web program development --%>
    <%-- pageContext.getRequest(): Method that returns the ServletRequest object with request information --%>
    <p>Context Root Directory Path = <%=((HttpServletRequest)pageContext.getRequest()).getContextPath() %></p>
    <p>Request URI Address = <%=((HttpServletRequest)pageContext.getRequest()).getRequestURI() %></p>
    <hr>
    <h2>Using EL</h2>
    <%-- Using the pageContext implicit object in EL expressions provides access to the PageContext object --%>
    <%-- => EL expressions allow calling methods of the PageContext object using the object name without the "get" prefix --%>
    <p>Context Root Directory Path = ${pageContext.request.contextPath }</p>
    <p>Request URI Address = ${pageContext.request.requestURI }</p>
</body>
</html>
```

### Summary of Translation:
- **Comments Translation**: Explains how to access request information and PageContext properties using both scriptlets and Expression Language (EL) in JSP.
- **Content Translation**: Describes how to obtain and display context root directory paths and request URI addresses using JSP scriptlets and EL.