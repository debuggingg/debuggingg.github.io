---
layout: single
title: 2024/07/10 JSP 08-Action-UseBean
---
# Non useBean 


#### Non_useBean- Form -  jsp-BasicForm 

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document for receiving member information from the user --%>
<%-- => When the [Register Member] button is clicked, the [non_useBean_action.jsp] document is requested to navigate the page and pass the input values --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
	<h1>Member Information Registration</h1>
	<hr>
	<form action="<%=request.getContextPath()%>/action/useBean/non_useBean_action.jsp" method="post">
	<table>
		<tr>
			<td>Name</td>
			<td><input type="text" name="name"></td>
		</tr>
		<tr>
			<td>Phone Number</td>
			<td><input type="text" name="phone"></td>
		</tr>
		<tr>
			<td>Address</td>
			<td><input type="text" name="address"></td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit">Register Member</button></td>
		</tr>
	</table>
	</form>
</body>
</html>
```

### non_useBean -Action - jsp

This JSP script receives parameters for member information, stores them as attributes in the implicit object, and forwards the request to another JSP document for further processing or display. 

1. **Method Check and Error Handling**:
    - The script first checks if the request method is `GET`. If so, it sends an error code `405 Method Not Allowed` back to the client and terminates the script.

2. **Character Encoding**:
    - For `POST` requests, the character encoding is set to `UTF-8` to ensure that the request parameters are read correctly.

3. **Retrieve Parameters**:
    - The script retrieves the `name`, `phone`, and `address` parameters from the request object.

4. **Create and Populate Bean**:
    - A `HewonBean` object is created, and the retrieved parameters are set as properties of this bean.

5. **Request Attributes and Forwarding**:
    - The `HewonBean` object is set as an attribute in the request object.
    - The request is then forwarded to another JSP document (`non_useBean_display.jsp`) for further processing using `RequestDispatcher`.

```jsp
<%@page import="xyz.itwill.bean.HewonBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- A JSP document that receives member information as parameters, stores it as attributes of an implicit object, 
and forwards to [non_useBean_display.jsp] document - JSP document that only provides data processing for client requests --%>    
<%
    // If the JSP document is requested via GET method, send an error code to the client for response
    if(request.getMethod().equals("GET")) {// In case the JSP document is requested abnormally
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return;
    }

    // Change the character encoding to read the values passed via POST method
    request.setCharacterEncoding("utf-8");
    
    // Retrieve and store the passed values
    String name=request.getParameter("name");
    String phone=request.getParameter("phone");
    String address=request.getParameter("address");
    
    /*
    session.setAttribute("name", name);
    session.setAttribute("phone", phone);
    session.setAttribute("address", address);
    */
    
    // Create an object of HewonBean class and store the passed values in its fields
    HewonBean hewon=new HewonBean();
    hewon.setName(name);
    hewon.setPhone(phone);
    hewon.setAddress(address);
    
    /*
    // Store the HewonBean object as an attribute of the session object
    // => The attribute value can be retrieved as an object in all JSP documents bound to the same session
    session.setAttribute("hewon", hewon);
    
    // Redirect: Send the URL to the client, so that the client requests the JSP document at that URL 
    // and outputs the execution result after receiving the response
    // => Changes the request URL in the client's browser - moves using the client
    // => Store the attribute value in the session object and use the attribute value in the redirected JSP document
    // => Delete the attribute value from the session object in the redirected JSP document after retrieving it
    response.sendRedirect(request.getContextPath()+"/action/useBean/non_useBean_display.jsp");
    */
    
    // Store the HewonBean object as an attribute of the request object
    // => The attribute value can only be retrieved as an object in the JSP document to which the thread is forwarded
    request.setAttribute("hewon", hewon);

    // Forward: Move the thread from the requesting JSP document to the responding JSP document for response processing
    // => Does not change the request URL in the client's browser - moves within the server's JSP document
    // => Store the attribute value in the request object and use the attribute value in the forwarded JSP document
    // => No need to delete the attribute value from the request object in the forwarded JSP document after retrieving it
    
    // Instead of using the forward tag, you can call the method with the request object for forwarding
    // request.getRequestDispatcher(String contextPath): Returns a RequestDispatcher object with the web resource context path passed as a parameter
    // => RequestDispatcher object: Provides the function to move the thread to another web program
    // RequestDispatcher.forward(ServletRequest request, ServletResponse response): Moves the thread to the web program with the context path stored in the RequestDispatcher object for response processing - forward move
    // => Pass the request object and response object as parameters to set the web program to use the request object and response object together
    request.getRequestDispatcher("/action/useBean/non_useBean_display.jsp").forward(request, response);
%>
```

#### non_useBean - Display -jsp

```jsp
<%@page import="xyz.itwill.bean.HewonBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that retrieves attribute values stored in the implicit object and includes them in the HTML document for the response --%>
<%--
	// If there is no attribute value stored in the session implicit object, send an error code to the client
	if(session.getAttribute("hewon") == null) {// If the JSP document is requested abnormally
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}

	// Retrieve the attribute value stored in the session implicit object as an object and save it
	HewonBean hewon=(HewonBean)session.getAttribute("hewon");
	
	// Delete the attribute value in the session implicit object so that it cannot be used in other JSP documents
	session.removeAttribute("hewon");
--%>
<%
	// If there is no attribute value stored in the request implicit object, send an error code to the client
	if(request.getAttribute("hewon") == null) {// If the JSP document is requested abnormally
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	// Retrieve the attribute value stored in the request implicit object as an object and save it
	HewonBean hewon=(HewonBean)request.getAttribute("hewon");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
	<h1>Member Information Confirmation</h1>
	<hr>
	<p>Name = <%=hewon.getName() %></p>
	<p>Phone Number = <%=hewon.getPhone() %></p>
	<p>Address = <%=hewon.getAddress() %></p>
</body>
</html>
```

---
---
# UseBean

#### useBean - Java

```java
// JavaBean: A Java object created by the WAS (Web Application Server) program when using the useBean tag
// => An object to store the values passed when a JSP document is requested
// => The values are automatically stored in fields with the same name as the passed values
//getProperty or setProperty tag is requested same name as getter and setter name 
// A class to store member information - JavaBean >> DTO
public class HewonBean {
    private String name;
    private String phone;
    private String address;
    
    public HewonBean() {
        // TODO Auto-generated constructor stub
    }

    public HewonBean(String name, String phone, String address) {
        super();
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
```


#### useBean- Form -  jsp-BasicForm 

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document for receiving member information from the user --%>
<%-- => When the [Register Member] button is clicked, the [non_useBean_action.jsp] document is requested to navigate the page and pass the input values --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
	<h1>Member Information Registration</h1>
	<hr>
	<form action="<%=request.getContextPath()%>/action/useBean/non_useBean_action.jsp" method="post">
	<table>
		<tr>
			<td>Name</td>
			<td><input type="text" name="name"></td>
		</tr>
		<tr>
			<td>Phone Number</td>
			<td><input type="text" name="phone"></td>
		</tr>
		<tr>
			<td>Address</td>
			<td><input type="text" name="address"></td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit">Register Member</button></td>
		</tr>
	</table>
	</form>
</body>
</html>
```

### useBean-Action - jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that retrieves the submitted member information and stores it as an attribute in an implicit object, then forwards to [useBean_display.jsp] --%>    
<%
	// If the JSP document is requested using the GET method, send an error code to the client
	if(request.getMethod().equals("GET")) { // If the JSP document is requested abnormally
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}

	// Change the character encoding to read the submitted values when requested using the POST method
	request.setCharacterEncoding("utf-8");
%>
<%-- useBean Tag: A tag used to provide a Java object (JavaBean) in the JSP document --%>
<%-- Format: <jsp:useBean id="identifier" class="class" scope="scope"></jsp:useBean> --%>
<%-- => Returns the attribute value stored in the implicit object as an object or creates an object if there is no attribute value, then stores it as an attribute in the implicit object and provides it --%>
<%-- id Attribute: Sets an identifier to distinguish the object provided by the useBean tag --%>
<%-- => Used as the attribute name for the attribute value stored in the implicit object --%>
<%-- class Attribute: Sets the data type (class) of the object provided by the useBean tag --%>
<%-- scope Attribute: Sets the scope of use for the object provided by the useBean tag --%>
<%-- => Sets one of page, request, session, or application as the attribute value --%>
<%-- => If the scope attribute is omitted, the default value is [page] --%>
<jsp:useBean id="hewon" class="xyz.itwill.bean.HewonBean" scope="request"/>
<%--
	// Using the useBean tag is equivalent to the command that creates a HewonBean object and stores it as an attribute in the request object
	HewonBean hewon=new HewonBean();
	request.setAttribute("hewon", hewon);
--%>

<%-- setProperty Tag: A tag used to change the field value of the object provided by the useBean tag --%>
<%-- => A dependent tag of the useBean tag --%>
<%-- Format: <jsp:setProperty name="identifier" property="field" value="new value"></jsp:setProperty> --%>
<%-- => Equivalent to calling the Setter method to change the field value --%>
<%-- name Attribute: Sets the identifier (id attribute value) of the object provided by the useBean tag as the attribute value --%>
<%-- property Attribute: Sets the field name to change the field value as the attribute value --%>
<%-- => Calls the Setter method of the field set as the attribute value to change the field value - an error occurs if the Setter method does not exist --%>
<%-- value Attribute: Sets the new field value as the attribute value --%>
<%-- <jsp:setProperty name="hewon" property="name" value="John Doe"/> --%>
<%-- hewon.setName("John Doe"); --%>

<%-- If the value attribute of the setProperty tag is omitted, the submitted value will be retrieved and used to change the field value when the JSP document is requested --%>
<%-- => The field name must match the name of the submitted value to change the field value using the retrieved value --%>
<%-- 
<jsp:setProperty name="hewon" property="name"/>
<jsp:setProperty name="hewon" property="phone"/>
<jsp:setProperty name="hewon" property="address"/>
--%>
<%-- If the property attribute is set to [*], all submitted values will be retrieved, and the field values with names matching the submitted values will be changed --%>
<jsp:setProperty name="hewon" property="*"/>
<%--
	hewon.setName(request.getParameter("name"));
	hewon.setPhone(request.getParameter("phone"));
	hewon.setAddress(request.getParameter("address"));
--%>

<jsp:forward page="/action/useBean/useBean_display.jsp"/>
<%-- request.getRequestDispatcher("/action/useBean/useBean_display.jsp").forward(request, response); --%>
```

#### useBean -  Display- jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that retrieves attribute values stored in an implicit object and includes them in the HTML document for response --%>
<%
	// If there are no attribute values stored in the request implicit object, send an error code to the client
	if(request.getAttribute("hewon") == null) { // If the JSP document is requested abnormally
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
	<h1>Member Information Confirmation</h1>
	<%-- Use the useBean tag to retrieve the attribute value stored in the request implicit object and provide it to the JSP document --%>
	<jsp:useBean id="hewon" class="xyz.itwill.bean.HewonBean" scope="request"/>
	<%-- HewonBean hewon = (HewonBean)request.getAttribute("hewon"); --%>
	<hr>

	<%-- getProperty Tag: A tag that returns the field value of the object provided by the useBean tag --%>
	<%-- => A dependent tag of the useBean tag --%>
	<%-- Format: <jsp:getProperty name="identifier" property="field"></jsp:getProperty> --%>
	<%-- => Equivalent to calling the Getter method to return the field value --%>
	<%-- name Attribute: Sets the identifier (id attribute value) of the object provided by the useBean tag as the attribute value --%>
	<%-- property Attribute: Sets the field name to return the field value as the attribute value --%>
	<%-- => Calls the Getter method of the field set as the attribute value to return the field value - an error occurs if the Getter method does not exist --%>		
	<p>Name = <jsp:getProperty property="name" name="hewon"/></p>
	<p>Phone Number = <jsp:getProperty property="phone" name="hewon"/></p>
	<p>Address = <jsp:getProperty property="address" name="hewon"/></p>
	<%--
		<p>Name = <%=hewon.getName() %></p>
		<p>Phone Number = <%=hewon.getPhone() %></p>
		<p>Address = <%=hewon.getAddress() %></p>	
	--%>
</body>
</html>
```