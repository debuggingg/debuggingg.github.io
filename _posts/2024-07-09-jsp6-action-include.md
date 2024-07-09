---
layout: single
title: 2024/07/09 JSP 06-Action-Include
---
# ActionTag 

- To distinguish them from HTML tags, use the [jsp] namespace: JSP tags

1. **include Tag**: Includes the result (web document) of executing another JSP document by moving the thread of the JSP document to the other JSP document, at the location where the tag is used.
2. **forward Tag**: Moves the thread of the JSP document to another JSP document and responds to the client with the result (web document) of executing the other JSP document.
3. **param Tag**: Used to pass values to the JSP document to which the thread has moved. 
   - Executed as a subtag of the include and forward tags.
4. **useBean Tag**: Provides an object that can be used in the JSP document.
   - Returns an attribute value stored in an implicit object or creates a new object to provide.
5. **setProperty Tag**: Changes the field value of the object provided by the useBean tag (calls the Setter method).
   - A dependent tag of the useBean tag.
6. **getProperty Tag**: Returns the field value of the object provided by the useBean tag (calls the Getter method).
   - A dependent tag of the useBean tag.
---
# Include 
## Index(main)

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document responding to all client requests with the execution result (HTML document) - Template page --%>    
<%
    request.setCharacterEncoding("utf-8");

    //String headerFilePath="/action/include/header.jspf";
    String headerFilePath="/action/include/header.jsp";
    
    String worker=request.getParameter("worker");
    if(worker == null) {
        worker="main";
    }
    
    // Create and store the context path of the JSP document to be included in the body part
    String contentFilePath="/action/include/"+worker+".jsp";
    //System.out.println("contentFilePath = "+contentFilePath);
    
    String webMaster="";
    if(worker.equals("main")) {
        webMaster="Hong Gil-dong (abc@itwill.xyz)";
    } else if(worker.equals("login") || worker.equals("join")) {
        webMaster="Lim Kkeok-jeong (opq@itwill.xyz)";
    } else if(worker.equals("cart") || worker.equals("review")) {
        webMaster="Jeon Woo-chi (xyz@itwill.xyz)";
    }
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
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
</head>
<body>
    <%-- Header: Logo, Menu, etc. --%>
    <div id="header">
        <%-- 
        <h1><a href="<%=request.getContextPath()%>/action/include/index.jsp">Shopping Mall</a></h1>
        <div id="menu">
            <a href="<%=request.getContextPath()%>/action/include/index.jsp?worker=login">Login</a>&nbsp;&nbsp;
            <a href="<%=request.getContextPath()%>/action/include/index.jsp?worker=join">Sign Up</a>&nbsp;&nbsp;
            <a href="<%=request.getContextPath()%>/action/include/index.jsp?worker=cart">Cart</a>&nbsp;&nbsp;
            <a href="<%=request.getContextPath()%>/action/include/index.jsp?worker=review">Reviews</a>&nbsp;&nbsp;
        </div>
        --%>
        
        <%-- include Directive: Directive to include the source code of a document file (JSPF) in a JSP document --%>
        <%-- => Used to include CSL (HTML, CSS, JavaScript) and SSL (Java - Script Element) codes in a JSP document --%>
        <%-- => Includes the source code of the document file as it is in the JSP document - static inclusion --%>
        <%-- When a JSP document is requested, the code of the document file set as the file attribute value 
        of the include Directive is included and executed in the JSP document, then the execution result is 
        generated as an HTML document and responded to the client --%>
        <%-- If the document file set as the file attribute value of the include Directive is changed, it 
        is considered the same as if the JSP document is changed, so the servlet class is newly converted 
        and executed when the JSP document is requested --%>
        <%-- <%@include file="/action/include/header.jspf" %> --%>
        <%-- Expressions (Expression) cannot be used as file attribute values for include Directive - causes an error --%>
        <%-- <%@include file="<%=headerFilePath %>" %> --%>
        
        <%-- include Tag: A JSP tag that moves the thread of the requested JSP document to another JSP document, 
        executes it, and includes the execution result (HTML tags) in the JSP document --%>
        <%-- Format) <jsp:include page="Context path of the JSP document"></jsp:include> --%>
        <%-- => Provides the request object and response object of the requested JSP document to the JSP document 
        to which the thread is moved so that they can be used --%>
        <%-- => Includes the execution result (CSL - HTML, CSS, JavaScript) of the moved JSP document 
        in the requested JSP document - dynamic inclusion --%>
        <%-- => Changing the JSP document set as the page attribute value of the include tag does not affect 
        the requested JSP document --%>
        <%-- <jsp:include page="/action/include/header.jsp"></jsp:include> --%>
        <%-- <jsp:include page="/action/include/header.jsp"/> --%>
        <%-- Expressions (Expression) can be used as file attribute values for include tags --%>
        <jsp:include page="<%=headerFilePath %>"/>
    </div>
    
    <%-- Body: Execution result for the request --%>
    <div id="content">
        <%-- <h2>Main Page - Product List</h2> --%>
        <jsp:include page="<%=contentFilePath %>"/>
    </div>
    
    <%-- Footer: Copyright, Terms, Privacy Policy, etc. --%>
    <div id="footer">
        <%-- 
        <p>Copyright ⓒ Itwill Corp. All rights reserved</p>
        <p>Administrator: Hong Gil-dong (abc@itwill.xyz)</p>
        --%>
        <%-- param Tag: A tag for passing values to the JSP document to which the thread is moved --%>
        <%-- => Stores values in the body of the request message (request object) and passes them --%>
        <%-- => Can only be used as a child tag of include tags and forward tags --%>
        <%-- => Can only be used as a child tag of include tags and forward tags --%>
        <%-- Including other codes (including comments) in include tags and forward tags results in an error --%>
        <jsp:include page="/action/include/footer.jsp">
            <jsp:param value="<%=webMaster %>" name="master"/>
        </jsp:include>        
    </div>
    <%
        if(request.getAttribute("errorCode") != null) {
            // Sends the error code to the client for response handling
            response.sendError((Integer)request.getAttribute("errorCode"));
            return;
        }
    %>
</body>
</html>
```
### Include Tag - ex ) Header-jsp (not jspf)

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1><a href="<%=request.getContextPath()%>/action/include/index.jsp">Shopping Mall</a></h1>
<div id="menu">
    <a href="<%=request.getContextPath()%>/action/include/index.jsp?worker=login">Login</a>&nbsp;&nbsp;
    <a href="<%=request.getContextPath()%>/action/include/index.jsp?worker=join">Sign Up</a>&nbsp;&nbsp;
    <a href="<%=request.getContextPath()%>/action/include/index.jsp?worker=cart">Cart</a>&nbsp;&nbsp;
    <a href="<%=request.getContextPath()%>/action/include/index.jsp?worker=review">Reviews</a>&nbsp;&nbsp;
</div>
```

### Include Tag - ex ) footer-jsp (not jspf)

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // Issue: The encoding of the request body cannot be changed in the JSP document where the thread has moved.
    // Solution: Call the method to change the character encoding of the request body in the initial JSP document.
    //request.setCharacterEncoding("utf-8");

    String master = request.getParameter("master");

    // Issue: In the JSP document where the thread has moved, methods like sendError() or sendRedirect() cannot
    // be called on the response object to send error codes or URLs to the client.
    // Solution: Store the error code or URL as an attribute in the request object and provide it to the initial JSP document.
    if(master.equals("홍길동(abc@itwill.xyz)")) {
        // Send the error code to the initial JSP document instead of the client.
        //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        request.setAttribute("errorCode", HttpServletResponse.SC_BAD_REQUEST);
        return; 
    }
%>    
<p>Copyright ⓒ Itwill Corp. All rights reserved</p>
<%-- <p>Admin: 홍길동(abc@itwill.xyz)</p> --%>
<p>Admin: <%=master %></p>
```

### Main Page

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2>Main Page - Product List</h2>
```

### LogIn Page - Join,Cart,Reviw Omitted 
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2>Login Page - Enter Username and Password</h2>
```
