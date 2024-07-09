---
layout: single
title: 2024/07/08 JSP 03-DIrective
---
## Directive -지시어
- 1.Page Directive: Directs to provide the necessary information in the JSP document
 <%page attribute="value" attribute="value"%>
- 2.Include Directive: Directs to include the source code of an external file in the JSP document
  <%@ include file="Contextpath" %>
- 3.Taglib Directive: Directs to provide a tag library file in the JSP document to enable the use of custom tags
   <%@ taglib uri="uri" prefix="prefix" %>
--- 
---

## 1.Page Directive 

#### ContentType

- In this case, the page will not be displayed as a web page but will be downloaded as a Word document because the contentType has been changed.

```jsp
<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> --%>
<%@page language="java" contentType="application/msword; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<style type="text/css">
table {
    border: 1px solid black;
    border-collapse: collapse;
}

th, td {
    border: 1px solid black;
    text-align: center;
    width: 200px;
    padding: 2px;
}
</style>
</head>
<body>
    <h1>page Directive - contentType Attribute</h1>
    <hr>
    <p>The contentType attribute of the page Directive sets the type of file (MimeType) and the character encoding 
    (CharacterSet - Encoding) for the document file that can be requested and responded to by the JSP document.</p>
    <p>In this case, the page will not be displayed as a web page but will be downloaded as a Word document 
    because the contentType has been changed.</p>
    <hr>
    <table>
        <tr>
            <th>Student ID</th><th>Name</th><th>Address</th>
        </tr>
        <tr>
            <td>1000</td><td>Hong Gil-dong</td><td>Yeoksam-dong, Gangnam-gu, Seoul</td>
        </tr>
        <tr>
            <td>2000</td><td>Im Kkeok-jeong</td><td>Sangdang-dong, Wolmi-gu, Incheon</td>
        </tr>
        <tr>
            <td>3000</td><td>Jeon Woo-chi</td><td>Gurae-dong, Paldal-gu, Suwol-si</td>
        </tr>
    </table>
</body>
</html>
```

#### ErrorPage

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/error/page_error.jsp"%>
<%-- Using the errorPage attribute of the page Directive, you can configure the JSP document to respond
    with an error page if an exception occurs during execution. However, this leads to the issue of having
    to set the errorPage attribute for each JSP document individually. --%>
<%-- => It is recommended to define the error-page element in the [web.xml] file to handle error responses
    with error pages when error codes (4XX or 5XX) are sent to the client. This approach allows handling
    error responses for all exceptions occurring during the execution of the web application. --%>
<%
    String text="ABCDEFG";
    //String text=null;
    
    int su=10/0;
    
    /*
    if(request.getMethod().equals("GET")) {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return;
    }
    */
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
    <h1>page Directive - errorPage Attribute</h1>
    <hr>
    <p>The errorPage attribute of the page Directive sets the web document's context path that will be
    responded with instead of a 500 status code (error code) when an error (exception) occurs during the
    execution of the JSP document.</p>
    <hr>
    <p>Number of characters in the string = <%=text.length() %></p>
</body>
</html>
```
#### Error Page - errorPage="/error/page_error.jsp"
- there are more error pages ex:400,405,500 ..etc. - Only example for error page 
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
    <h1>Error Page</h1>
    <hr>
    <p>An unexpected error occurred while executing the requested program.
    We will take action to resolve this issue promptly.</p>
</body>
</html>
```
#### web.xml 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd" id="WebApp_ID" version="4.0">
  <display-name>jsp</display-name>
  
  <!-- error-page: Element for setting error pages to be responded instead of error codes -->
  <!-- exception-type: Element for specifying exception classes that may occur during web application execution -->
  <!-- location: Element for specifying the context path of the error page -->
  <!--
  <error-page>
  	<exception-type>java.lang.NullPointerException</exception-type>
	<location>/error/page_error.jsp</location>  
  </error-page>
  -->
  
  <!-- error-code: Element for specifying error codes -->
  <!--  
  <error-page>
  	<error-code>500</error-code>
  	<location>/error/error_500.jsp</location>
  </error-page>
  -->
  
  <error-page>
  	<error-code>400</error-code>
  	<location>/error/error_400.jsp</location>
  </error-page>
  
  <error-page>
  	<error-code>404</error-code>
  	<location>/error/error_404.jsp</location>
  </error-page>
  
  <error-page>
  	<error-code>405</error-code>
  	<location>/error/error_405.jsp</location>
  </error-page>
  
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.jsp</welcome-file>
    <welcome-file>default.htm</welcome-file>
  </welcome-file-list>
</web-app>
```

#### Import
- The import attribute and its values in the page directive can be automatically generated using Eclipse's autocomplete feature.
```jsp
<%-- <%@page import="java.util.List, java.util.ArrayList" %> --%>
<%-- The import attribute and its values in the page directive can be automatically generated using Eclipse's autocomplete feature. --%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%-- If the same attribute is used multiple times in the page directive, an error may occur. --%>
<%-- => Except for the import attribute. --%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    //java.util.List<java.lang.String> nameList=new java.util.ArrayList<java.lang.String>();
    //List<String> nameList=new ArrayList<String>(); 
    List<String> nameList=new ArrayList<>();// Generic type can be omitted when creating objects
    nameList.add("Hong Gil-dong");
    nameList.add("Im Kkeok-jeong");
    nameList.add("Jeon Woo-chi");
    nameList.add("Ilji-mae");
    nameList.add("Jang Gil-san");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
    <h1>page Directive - import Attribute</h1>
    <hr>
    <p>The import attribute of the page Directive sets Java data types (classes or interfaces) to be used in JSP documents as attribute values. It is used to clearly express Java data types using packages.</p>
    <hr>
    <ul>
    <% for(String name : nameList) { %>
        <li><%=name %></li>
    <% } %>
    </ul>    
</body>
</html>
```
---
---

## 2. Include Directive  - jspf
#### Main - will bring 'p' message from Sub
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
    <h1>include Directive</h1>
    <hr>
    <p>A directive that includes the code from a file into a JSP document - static inclusion.</p>
    <hr>
    <%-- 
    <p>This is a very important content to be responded to the client.</p>
    <p>This is a very important content to be responded to the client.</p>
    <p>This is a very important content to be responded to the client.</p>
    <p>This is a very important content to be responded to the client.</p>
    <p>This is a very important content to be responded to the client.</p>
    <p>This is a very important content to be responded to the client.</p>
    <p>This is a very important content to be responded to the client.</p>
    <p>This is a very important content to be responded to the client.</p>
    <p>This is a very important content to be responded to the client.</p>
    <p>This is a very important content to be responded to the client.</p>
    --%>
    
    <%-- 
    Use the file attribute of the include Directive to specify the context path of the file as the attribute value,
    so that the source code of the specified file is included at the location of the include Directive. 
    --%>
    <%-- => If the file specified in the file attribute does not exist, an error occurs. --%>
    <%@include file="/directive/include_sub.jspf" %>
</body>
</html>
```

#### Sub
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSPF File: A file intended to provide code for JSP documents - read by the include Directive. --%>
<%-- => Not converted to servlet by client request. --%>
<%-- Note: The contentType attribute value in the page Directive of both JSP and JSPF files must be identical. --%>
<p>This is a very important content to be responded to the client.</p>
<p>This is a very important content to be responded to the client.</p>
<p>This is a very important content to be responded to the client.</p>
<p>This is a very important content to be responded to the client.</p>
<p>This is a very important content to be responded to the client.</p>
<p>This is a very important content to be responded to the client.</p>
<p>This is a very important content to be responded to the client.</p>
<p>This is a very important content to be responded to the client.</p>
<p>This is a very important content to be responded to the client.</p>
<p>This is a very important content to be responded to the client.</p>
```
## EX) Shopping Mall 
#### Header- jspf
```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="header">
    <h1><a href="index.jsp">Shopping Mall</a></h1>
    <div id="menu">
        <a href="login.jsp">Login</a>&nbsp;&nbsp;
        <a href="join.jsp">Join</a>&nbsp;&nbsp;
        <a href="cart.jsp">Cart</a>&nbsp;&nbsp;
        <a href="review.jsp">Review Board</a>&nbsp;&nbsp;
    </div>
</div>
```

#### Footer-jspf
```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="header">
    <h1><a href="index.jsp">Shopping Mall</a></h1>
    <div id="menu">
        <a href="login.jsp">Login</a>&nbsp;&nbsp;
        <a href="join.jsp">Join</a>&nbsp;&nbsp;
        <a href="cart.jsp">Cart</a>&nbsp;&nbsp;
        <a href="review.jsp">Review Board</a>&nbsp;&nbsp;
    </div>
</div>
```

#### Style.css -css
```css
@charset "UTF-8";
div {
    margin: 5px;
    padding: 5px;
}
#header {height: 200px;border: 1px solid red;}   
#header h1 {text-align: center;}
#menu {font-size: 1.5em;text-align: right;}
#menu a, #menu a:visited {text-decoration: none;color: black;}
#menu a:hover {color: orange;}
#content {min-height: 500px;border: 1px solid green;text-align: center;}
#footer {height: 150px;border: 1px solid blue;text-align: center;font-size: 1.2em;}
```

### Main Page -Jsp 
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<%-- 
<style type="text/css">
div {
    margin: 5px;
    padding: 5px;
}

#header {
    height: 200px;
    border: 1px solid red;
}   

#header h1 {
    text-align: center;
}

#menu {
    font-size: 1.5em;
    text-align: right;
}

#menu a, #menu a:visited {
    text-decoration: none;
    color: black;
}

#menu a:hover {
    color: orange;
}

#content {
    min-height: 500px;
    border: 1px solid green;
    text-align: center;
}

#footer {
    height: 150px;
    border: 1px solid blue;
    text-align: center;
    font-size: 1.2em;
}
</style>
--%>
<link href="style.css" type="text/css" rel="stylesheet">
</head>
<body>
    <%-- Header Section: Logo, Menu --%>
    <div id="header">
        <%-- 
        <h1><a href="index.jsp">Shopping Mall</a></h1>
        <div id="menu">
            <a href="login.jsp">Login</a>&nbsp;&nbsp;
            <a href="join.jsp">Join</a>&nbsp;&nbsp;
            <a href="cart.jsp">Cart</a>&nbsp;&nbsp;
            <a href="review.jsp">Review Board</a>&nbsp;&nbsp;
        </div>
        --%>
        <%@include file="header.jspf"%>
    </div>
    
    <%-- Content Section: Execution result for request --%>
    <div id="content">
        <h2>Main Page - Displaying Product List</h2>
    </div>
    
    <%-- Footer Section: Copyright, Terms, Privacy Policy --%>
    <div id="footer">
        <%-- <p>Copyright ⓒ Itwill Corp. All rights reserved</p> --%>
        <%@include file="footer.jspf" %>
    </div>
</body>
</html>
```
#### Cart- jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<link href="style.css" type="text/css" rel="stylesheet">
</head>
<body>
    <%-- Header Section: Logo, Menu --%>
    <div id="header">
        <%@include file="header.jspf"%>
    </div>
    
    <%-- Content Section: Execution result for request --%>
    <div id="content">
        <h2>Cart Page - Displaying Products in the Cart</h2>
    </div>
    
    <%-- Footer Section: Copyright, Terms, Privacy Policy --%>
    <div id="footer">
        <%@include file="footer.jspf" %>
    </div>
</body>
</html>
```
#### signup- jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<link href="style.css" type="text/css" rel="stylesheet">
</head>
<body>
    <%-- Header Section: Logo, Menu --%>
    <%@ include file="header.jspf" %>
    
    <%-- Content Section: Execution result for request --%>
    <div id="content">
        <h2>signup</h2>
    </div>
    
    <%-- Footer Section: Copyright, Terms, Privacy Policy --%>
    <%@ include file="footer.jspf" %>
</body>
</html>
```
#### Login-jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<link href="style.css" type="text/css" rel="stylesheet">
</head>
<body>
    <%-- Header Section: Logo, Menu --%>
    <div id="header">
        <%@include file="header.jspf"%>
    </div>
    
    <%-- Content Section: Execution result for request --%>
    <div id="content">
        <h2>Login Page - Enter your username and password</h2>
    </div>
    
    <%-- Footer Section: Copyright, Terms, Privacy Policy --%>
    <div id="footer">
        <%@include file="footer.jspf" %>
    </div>
</body>
</html>
```
#### Review- jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<link href="style.css" type="text/css" rel="stylesheet">
</head>
<body>
    <%-- Header Section: Logo, Menu --%>
    <div id="header">
        <%@include file="header.jspf"%>
    </div>
    
    <%-- Content Section: Execution result for request --%>
    <div id="content">
        <h2>Product Review Page - Displaying product reviews</h2>
    </div>
    
    <%-- Footer Section: Copyright, Terms, Privacy Policy --%>
    <div id="footer">
        <%@include file="footer.jspf" %>
    </div>
</body>
</html>
```
---
---
## 3.Taglib Directive
1. create java( package:xyz.itwill.custom - CustomTag.java)taglib
2. taglib custom.tld 
3. taglib


#### xyz.itwill.custom - Class CustomTag

```java
// Tag class: A class used to create objects when custom tags are used in a JSP document
// => Extends the TagSupport class

public class CustomTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	// Method automatically called by the tag class object when a custom tag is used in a JSP document
	// => Write the commands to be executed when the custom tag is used
	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().println("<h3>Using Custom Tag</h3>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}
```


---

#### Taglib.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Including a Tag Library Descriptor (TLD) file with the taglib Directive enables the usage of custom tags in JSP documents. --%>
<%-- prefix attribute: Sets the namespace for using custom tags. --%>
<!-- => Namespace: A name used in JSP documents to differentiate custom tags declared in the tag library file. -->
<!-- uri attribute: Sets an identifier for distinguishing the Tag Library Descriptor (TLD) file. -->    
<%@taglib prefix="custom" uri="http://www.itwill.xyz/custom"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
    <h1>taglib Directive</h1>
    <hr>
    <p>The taglib Directive allows the usage of custom tags in JSP documents by including a Tag Library Descriptor (TLD) file.</p>
    <p>Tag Library Descriptor (TLD) file: An XML file used to register tag classes as custom tags.</p>
    <p>Custom tags: Java tags created using tag classes.</p>
    <hr>
    <custom:display/>
    <custom:display/>
    <custom:display/>
</body>
</html>
```
#### Custom.tld
how to create the file. 
1. ![스크린샷 2024-07-08 161524](https://github.com/debuggingg/project/assets/167505419/f2a72b2e-ef43-4a1d-94df-c99e19273323)
2. ![스크린샷 2024-07-08 161643](https://github.com/debuggingg/project/assets/167505419/ec5bdcb3-91e7-4aae-b03b-06dde8aeb192)
3. ![스크린샷 2024-07-08 161804](https://github.com/debuggingg/project/assets/167505419/3718ed08-8359-4679-8dec-e15c55deb618)
4. ![스크린샷 2024-07-08 162005](https://github.com/debuggingg/project/assets/167505419/25014024-9623-43de-8015-e67b995bc5ba)
5.  ![스크린샷 2024-07-08 162224](https://github.com/debuggingg/project/assets/167505419/f2e758ed-b74e-4471-af79-a12a6822d7ec)
6. ![스크린샷 2024-07-08 162307](https://github.com/debuggingg/project/assets/167505419/6f459d24-ff12-4f34-b05e-62cfc9fd6b2a)
7. ![스크린샷 2024-07-08 162327](https://github.com/debuggingg/project/assets/167505419/cc98379a-395f-42db-9ad1-4e87351b19f4)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xml="http://www.w3.org/XML/1998/namespace" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
  <tlib-version>1.0</tlib-version>
  <short-name>custom</short-name>
  <uri>http://www.itwill.xyz/custom</uri>
  <tag>
    <name>display</name>
    <tag-class>xyz.itwill.custom.CustomTag</tag-class>
    <body-content>empty</body-content>
  </tag>
</taglib>
```
his XML code defines a tag library descriptor (TLD) file used to declare custom tags in JSP. Here is an explanation of the elements:

- `<?xml version="1.0" encoding="UTF-8"?>`: Specifies the XML version and encoding.
- `<taglib>`: Root element of the tag library descriptor.
    - `version="2.0"`: Specifies the version of the JSP tag library descriptor.
    - `xmlns="http://java.sun.com/xml/ns/j2ee"`: Namespace for the JSP tag library descriptor.
    - `xmlns:xml="http://www.w3.org/XML/1998/namespace"`: XML namespace declaration.
    - `xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"`: XML Schema instance namespace.
    - `xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd"`: Specifies the location of the XML schema.
- `<tlib-version>1.0</tlib-version>`: Version of the tag library.
- `<short-name>custom</short-name>`: Short name for the tag library.
- `<uri>http://www.itwill.xyz/custom</uri>`: URI used to reference the tag library in a JSP file.
- `<tag>`: Element that defines a custom tag.
    - `<name>display</name>`: Name of the custom tag.
    - `<tag-class>xyz.itwill.custom.CustomTag</tag-class>`: Fully qualified name of the tag handler class.
    - `<body-content>empty</body-content>`: Specifies that the tag has no body content.

