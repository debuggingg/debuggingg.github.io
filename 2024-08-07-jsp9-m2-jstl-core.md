---
layout: single
title: 2024/08/07 JSP9 - M2-Jstl-Core2
---
## Core
#### core_if


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - Program Flow Control Tags</h1>
	<hr>
	<%-- if tag: Tag used to determine whether to include the tag body based on a condition --%>
	<%-- test attribute: Set to either false or true --%>
	<%-- => If the test attribute is [false], the tag body will not be included in the response. If [true], the tag body will be included and output --%>
	<c:if test="true">
		<p>The tag body is output because the test attribute is [true] - 1</p>
	</c:if>
	<c:if test="false">
		<p>The tag body is output because the test attribute is [true] - 2</p>
	</c:if>
	
	<c:set var="result" value="true"/>
	<%-- The test attribute can also use EL to provide a Boolean object from the Scope attribute --%>
	<c:if test="${result }">
		<p>The tag body is output because the test attribute is [true] - 3</p>
	</c:if>
	<hr>
	
	<c:set var="num" value="10"/>
	<%-- When using EL for the test attribute, logical values can be provided using EL operators in the EL expression --%>
	<c:if test="${num % 2 != 0}">${num } = Odd</c:if>
	<c:if test="${num % 2 == 0}">${num } = Even</c:if>
	<br>
	<c:set var="num" value="11"/>
	<c:if test="${num mod 2 ne 0}">${num } = Odd</c:if>
	<c:if test="${num mod 2 eq 0}">${num } = Even</c:if>
	<hr>
	
	<c:set var="score" value="80"/>
	<c:if test="${score <= 100 && score >= 0}">${score } is a valid score.</c:if>
	<c:if test="${score > 100 || score < 0}">${score } is an invalid score.</c:if>
	<br>
	<c:set var="score" value="800"/>
	<c:if test="${score le 100 and score ge 0}">${score } is a valid score.</c:if>
	<c:if test="${score gt 100 or score lt 0}">${score } is an invalid score.</c:if>
	<hr>
	
	<c:set var="name" value="홍길동"/>
	<c:if test="${empty(name) }">
		<p>The Scope attribute value stored with the name attribute is either non-existent or empty.</p>
	</c:if>
	<c:if test="${!empty(name) }">
		<p>The Scope attribute value stored with the name attribute is [${name }] </p>
	</c:if>
</body>
</html>
```
---

#### core_choose


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - Program Flow Control Tags</h1>
	<hr>
	<c:set var="choice" value="4"/>
	
	<%-- choose tag: Tag used to determine whether to include the tag body based on conditions --%>
	<%-- => Sub-tags: when tags (one or more), otherwise tag (zero or one) --%>
	<%-- => The choose tag must not contain any code other than the sub-tags, excluding JSP comments --%>
	<c:choose>
		<%-- when tag: Tag used to determine whether to include the tag body based on a condition --%>
		<%-- test attribute: Set to either false or true, EL can be used for the attribute value --%>
		<%-- => If the test attribute is [false], the tag body will not be included in the response. If [true], the tag body will be included and processing will end --%>		
		<c:when test="${choice == 1 }">
			<p>Moving to Mercury.</p>
		</c:when>
		<c:when test="${choice == 2 }">
			<p>Moving to Venus.</p>
		</c:when>
		<c:when test="${choice == 3 }">
			<p>Moving to Mars.</p>
		</c:when>
		<%-- otherwise tag: Tag used to include the tag body if all when tag conditions are false --%>
		<c:otherwise>
			<p>Moving to Earth.</p>
		</c:otherwise>
	</c:choose>
	
	<c:set var="num" value="10"/>
	<c:choose>
		<c:when test="${num % 2 != 0 }">${num } = Odd</c:when>
		<c:otherwise>${num } = Even</c:otherwise>
	</c:choose>
</body>
</html>
```
#### core_choose_from , core_choose_action

### Page 1: `core_choose_form.jsp`
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - Program Flow Control Tags</h1>
	<hr>
	<form action="core_choose_action.jsp" method="post">
		Score Input: <input type="text" name="score">
		<button type="submit">Submit</button>
	</form>
</body>
</html>
```

### Page 2: `core_choose_action.jsp`
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - Program Flow Control Tags</h1>
	<hr>
	<c:choose>
		<c:when test="${!empty(param.score) }">
			<p>Entered Score = ${param.score } points</p>
			<c:choose>
				<c:when test="${param.score le 100 and param.score ge 90}">
					<c:set var="grade" value="A"/>
				</c:when>
				<c:when test="${param.score le 89 and param.score ge 80}">
					<c:set var="grade" value="B"/>
				</c:when>
				<c:when test="${param.score le 79 and param.score ge 70}">
					<c:set var="grade" value="C"/>
				</c:when>
				<c:when test="${param.score le 69 and param.score ge 60}">
					<c:set var="grade" value="D"/>
				</c:when>
				<c:otherwise>
					<c:set var="grade" value="F"/>
				</c:otherwise>
			</c:choose>
			<p>${param.score } points corresponds to a grade of ${grade }.</p>
		</c:when>
		<c:otherwise>
			<p style="color: red;">Please go back to the input page and enter a score.</p>
			<a href="core_choose_form.jsp">Go to Input Page</a>
		</c:otherwise>
	</c:choose>
</body>
</html>
```
#### core_forEach


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - Program Flow Control Tags</h1>
	<hr>
	<%-- forEach tag: A tag used to include content repeatedly --%>
	<%-- var attribute: Name (attribute name) for distinguishing Scope attribute values --%>
	<%-- begin attribute: Starting value (Integer object) for Scope attribute value --%>
	<%-- end attribute: Ending value (Integer object) for Scope attribute value --%>
	<%-- step attribute: Increment value (Integer object) for Scope attribute value --%>
	<c:forEach var="i" begin="1" end="5" step="1">
		<p>${i }st output content.</p>
	</c:forEach>
	<hr>
	
	<%-- Calculate and display the sum of integers from 1 to 100 --%>
	<c:forEach var="i" begin="1" end="100" step="1">
		<c:set var="tot" value="${tot + i }"/>
	</c:forEach>
	<p>Sum of integers from 1 to 100 = ${tot }</p>
	<hr>
	
	<%-- Display the multiplication table (Times Table) in a table format --%>
	<table>
		<c:forEach var="i" begin="1" end="9" step="1">
			<tr>
				<c:forEach var="j" begin="2" end="9" step="1">
					<td width="100">${j } * ${i } = ${i*j }</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
```
---

#### core_forEach_one, core_forEach_two



```jsp
<%@page import="java.util.ArrayList"%>
<%@page import="xyz.itwill.jstl.Student"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String[] nameArray = {"hong", "jun", "lim", "il", "jang"};
request.setAttribute("nameArray", nameArray);
List<Student> studentList = new ArrayList<Student>();
studentList.add(new Student(1000, "hong"));
studentList.add(new Student(2000, "jun"));
studentList.add(new Student(3000, "lim"));
studentList.add(new Student(4000, "il"));
studentList.add(new Student(5000, "jang"));

request.setAttribute("studentList", studentList);

request.getRequestDispatcher("core_forEach_two.jsp").forward(request, response);
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - Program Flow Control Tags</h1>
	<hr>
	<ul>
		<li>${nameArray[0]}</li>
		<li>${nameArray[1]}</li>
		<li>${nameArray[2]}</li>
		<li>${nameArray[3]}</li>
		<li>${nameArray[4]}</li>
	</ul>
	<hr>
	
	<ul>
		<c:forEach var="i" begin="0" end="4" step="1">
			<li>${nameArray[i]}</li>	
		</c:forEach>
	</ul>
	<hr>
	
	<%-- The forEach tag is used to process elements of an array or collection in order --%>
	<%-- var attribute: Name (attribute name) for storing the element values of the array or collection --%>
	<%-- items attribute: Array or collection object to be processed in bulk - can be set using EL --%>
	<ul>
		<c:forEach var="name" items="${nameArray}">
			<li>${name}</li>
		</c:forEach>
	</ul>
	<hr>
	
	<c:forEach var="student" items="${studentList}">
		<div>Student ID = ${student.num}, Name = ${student.name}</div>
	</c:forEach>
</body>
</html>
```
---
#### core_forTokens


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - Program Flow Control Tags</h1>
	<hr>
	<c:set var="phone" value="010-1234-5678"/>
	<p>Phone Number = ${phone}</p>
	<hr>
	
	<%-- forTokens Tag: Used to split a string and process each part in sequence --%>
	<%-- items attribute: Specifies the string to be split - can be set using EL --%>
	<%-- delims attribute: Specifies the delimiter (string) to split the string --%>
	<%-- var attribute: Specifies the name (attribute name) for storing the split string parts --%>
	<c:forTokens items="${phone}" delims="-" var="num">
		<div>${num}</div>
	</c:forTokens>
</body>
</html>
```
---
#### core_url


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - URL Management Tags</h1>
	<hr>
	<%-- Providing URL addresses for web resources using relative paths --%>
	<%-- Relative Path: Expressing the path of web resources relative to the requesting web application's path --%>
	<%-- Problem: In a JSP Model-2 web application using the MVC design pattern, if the paths of the requesting web application (Controller - Servlet) and the responding web application (View - JSP) are different, a 404 error occurs --%>
	<%-- Solution: Use absolute paths to express the path of web resources --%>
	<img alt="Koala" src="images/Koala.jpg" width="200">

	<%-- Providing URL addresses for web resources using absolute paths --%>
	<%-- Absolute Path: Expressing the path of web resources relative to the top-level directory --%>
	<%-- Problem: If the name of the top-level directory in the context is changed, the path to the web resource changes, causing a 404 error --%>
	<%-- Solution: Use the path of the top-level directory in the context to express the path of the web resource --%>
	<img alt="Koala" src="/mvc/jstl/images/Koala.jpg" width="200">

	<%-- Expressing the path of web resources using the context path provided by calling the request.getContextPath() method --%>
	<img alt="Koala" src="<%=request.getContextPath()%>/jstl/images/Koala.jpg" width="200">
	
	<%-- Using the pageContext object in EL expressions to express the path of web resources using the context path --%>
	<img alt="Koala" src="${pageContext.request.contextPath}/jstl/images/Koala.jpg" width="200">
	
	<%-- url Tag: Providing the path of web resources as an absolute path --%>
	<%-- value attribute: Sets the context path of the web resource --%>
	<%-- => Provides the path of the web resource as an absolute URL address by obtaining the path of the top-level directory in the context --%>
	<img alt="Koala" src="<c:url value="/jstl/images/Koala.jpg"/>" width="200">
</body>
</html>
```
---

#### core_import_source, core_import_target


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - URL Management Tags</h1>
	<hr>
	<p>This is the result of executing the core_import_source.jsp document.</p>
	
	<%-- import tag: A tag used to request a web program and include the result in the response --%>
	<%-- => Provides similar functionality to JSP's include tag --%>
	<%-- url attribute: Sets the URL address of the web program to be requested and responded to as a property value --%>
	<%-- <c:import url="core_import_target.jsp"/> --%>
	
	<%-- JSP's include tag moves the thread to the currently accessed server's web program to execute and include the result. In contrast, the import tag can request and include results from a web program on a different server --%>
	<%-- The result received from requesting a web program can be stored as a scope attribute --%>
	<%-- <c:import url="https://www.yonhapnewstv.co.kr/browse/feed/" />  --%>

	<%-- The import tag can store the result received from requesting a web program as a scope attribute --%>
	<%-- 
	<c:import url="https://www.yonhapnewstv.co.kr/browse/feed/" var="xml"/>
	<p>${xml }</p>
	--%>
	
	<%-- Use the param tag as a child tag of the import tag to pass values to the requested web program --%>
	<%-- => An error will occur if any other code besides the param tag is present within the import tag --%>
	<c:import url="core_import_target.jsp">
		<%-- param tag: A tag used to pass values to the requested web program --%>
		<%-- => Can only be used as a child tag of URL management tags --%>
		<c:param name="name" value="Hong Gil-dong"/>
	</c:import>
</body>
</html>
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
	<h1>EL - URL Management Tags</h1>
	<hr>
	<p>This is the result of executing the core_import_target.jsp document.</p>
	<p>Hello, ${param.name }.</p>
</body>
</html>
```

---
#### core_redirect_form, core_redirect_action



```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Configures the custom tags of the fmt (Formatter) tag library for use in JSP documents --%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- request.setCharacterEncoding("utf-8"); --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>EL - URL Management Tags</h1>
	<hr>
	<%-- requestEncoding: Tag used to change the character encoding for reading values sent via POST method --%>
	<%-- value attribute: Sets the encoding method for the character set --%>
	<%-- <fmt:requestEncoding value="utf-8"/> --%>
	
	<c:choose>
		<c:when test="${!empty(param.name) }">
			<p>Hello, ${param.name}.</p>
		</c:when>
		<c:otherwise>
			<%-- redirect tag: Tag used to set a URL address to redirect the client request --%>
			<%-- url attribute: Sets the URL address to be provided to the client --%>
			<c:redirect url="core_redirect_form.jsp"/>
		</c:otherwise>
	</c:choose>
</body>
</html>
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
	<h1>EL - URL Management Tags</h1>
	<hr>
	<form action="core_redirect_action.jsp" method="post">
		Name: <input type="text" name="name">
		<button type="submit">Submit</button>
	</form>
</body>
</html>
```

#### xyz.itwill.filter -> EncodingFilter.java->xml bulid- no longer needed endcoding 
#### java


```java
package xyz.itwill.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// Filter: A component that provides commands to be executed either before or after the execution of a web program in response to client requests.
// => Managed by the Web Application Server (WAS) and can be used for tasks such as XSS protection, encoding conversion, request authentication, and permission checks.

// Filter Class: A class that is instantiated before the execution of the request web program
// => Must extend the Filter interface and override the doFilter() method
// => Filters can be registered using the @WebFilter annotation or the [web.xml] file, specifying the URL patterns for which they apply

// This filter class provides functionality for changing the character set (CharacterSet) to read values stored in the request message body before any web program is executed - used to create an encoding filter.
public class EncodingFilter implements Filter {
    // Field to store encoding information for the character set
    private String encoding;

    // Method called once when the filter class is instantiated - initialization
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = "utf-8";
    }

    // Method automatically called each time the filter is invoked
    // => Commands to be executed before or after the execution of the web program are written here
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Commands to be executed before the web program's command
        if (request.getCharacterEncoding() == null || 
            !request.getCharacterEncoding().equalsIgnoreCase(encoding)) {
            request.setCharacterEncoding(encoding);
        }

        // FilterChain.doFilter(ServletRequest request, ServletResponse response)
        // => Method to execute commands of other filters connected to the current filter
        // => If there are no other filters, it executes the web program's command
        // FilterChain object: An object that holds information about filters that can be connected
        chain.doFilter(request, response);

        // Commands to be executed after the web program's command
    }
}
```

#### xml


```xml
<!-- filter: Element for registering a class as a filter -->
<!-- => Must be written before the servlet element -->
<filter>
    <filter-name>Encoding Filter</filter-name>
    <filter-class>xyz.itwill.filter.EncodingFilter</filter-class>
</filter>

<!-- filter-mapping: Element for registering URL patterns for which the filter should be applied -->
<filter-mapping>
    <filter-name>Encoding Filter</filter-name>
    <!-- Configure the filter to be applied for all web program requests -->
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

---



