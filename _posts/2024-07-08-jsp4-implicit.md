---
layout: single
title: 2024/07/08 JSP 03-Implict
---
# Implict 

1. Implicit Objects: Java objects provided by the WAS program for use in JSP documents
2. page (HttpJspPage object): An object that provides information about the JSP document -not much use
3. config (ServletConfig object): An object that provides configuration information registered in the WAS program - not much use
4. out (JspWriter object): An object that creates the document file to be responded to the client - output stream -not much use bc better use expression than out. 
5. **request (HttpServletRequest object): An object that provides the client's request information** 
 6. **response (HttpServletResponse object): An object that provides the client's response information**
7.  **session (HttpSession object): An object that provides the persistence of the connection between the server and the client** 
8. application (ServletContext object): An object that provides information to manage web resources (WebContext) - WAS
9. pageContext (PageContext object): An object that provides necessary objects for writing web programs- Used on TagClass
10. exception (Exception object): An object that provides exceptions occurred during web program execution Provided only when the isErrorPage attribute in the page directive is set to [true]: Used on error pages


### Ex) LoginForm-jsp Pages 

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that responds with different results based on whether the user is logged in or not --%>

<%-- For non-logged-in users, respond with an HTML document to input authentication information (username and password) --%>
<%-- => When the [Login] tag is clicked, request the [login_action.jsp] document to navigate the page - pass the input values (authentication information) --%>

<%-- For logged-in users, respond with an HTML document displaying a welcome message --%>
<%-- => When the [Logout] tag is clicked, request the [logout_action.jsp] document to navigate the page --%>
<%-- => When the [My Info] tag is clicked, request the [login_user.jsp] document to navigate the page --%>

<%
    // Retrieve the attribute value with authority-related information stored in the session as an object
    String loginId = (String)session.getAttribute("loginId");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
    <% if(loginId == null) { // If the user requesting the JSP document is not logged in %>

        <%--
            // request.getParameter(String name): A method that returns the value passed with the given name
            // => Returns null if no value is passed with the given name
            String msg = request.getParameter("msg");
            if(msg == null) { // No value passed
                // Prevent the expression from printing [null] as a string
                msg = "";
            }
        --%>
        <%
            // session.getAttribute(String attributeName): A method that retrieves the attribute value (object) stored in the client's session
            // (HttpSession object) bound to the JSP document with the given name
            // => Returns the attribute value stored in the session as an Object, so explicit type casting is required
            // => Returns [null] if no attribute value with the given name is stored in the session
            String msg = (String)session.getAttribute("msg");
            if(msg == null) { // No attribute value stored in the session
                msg = "";
            } else {
                // session.removeAttribute(String attributeName): A method that deletes the attribute value (object) stored in the client's session
                // (HttpSession object) bound to the JSP document with the given name
                // => Deletes the attribute value so it cannot be used in other JSP documents except the current JSP document
                session.removeAttribute("msg");
            }
            
            String id = (String)session.getAttribute("id");
            if(id == null) {
                id = "";
            } else {
                session.removeAttribute("id");
            }
        %>
        <h1>Login</h1>
        <hr>
        <form action="login_action.jsp" method="post" name="loginForm">
        <table>
            <tr>
                <td>Username</td>
                <td><input type="text" name="id" value="<%=id%>"></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="passwd"></td>
            </tr>
            <tr>
                <td colspan="2"><button type="submit">Login</button></td>
            </tr>
        </table>    
        </form>
        <p id="message" style="color: red;"><%=msg %></p>
        
        <script type="text/javascript">
        loginForm.id.focus();
        
        loginForm.onsubmit=function() {
            if(loginForm.id.value == "") {
                document.getElementById("message").innerHTML="Please enter your username.";
                loginForm.id.focus();
                return false;
            }
            
            if(loginForm.passwd.value == "") {
                document.getElementById("message").innerHTML="Please enter your password.";
                loginForm.passwd.focus();
                return false;
            }
        }
        </script>
    <% } else { // If the user requesting the JSP document is logged in %>
        <h1>Main Page</h1>
        <hr>
        <p>
            Welcome, <%=loginId %>.&nbsp;&nbsp;
            <a href="logout_action.jsp">[Logout]</a>
            <a href="login_user.jsp">[My Info]</a>
        </p>
    <% } %>    
</body>
</html>
```

## Login Action-jsp
```jsp
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document for processing authentication by receiving authentication information (username and password) --%>
<%-- => JSP document requested with the form tag from [login_form.jsp] using the POST method --%>
<%-- Authentication failure: If the received authentication information does not match the information stored in the member table --%>
<%-- => Store the username and error message as session attributes and provide them to the [login_form.jsp] document --%>
<%-- => Respond by sending the URL of the [login_form.jsp] document to the client --%>    
<%-- Authentication success: If the received authentication information matches the information stored in the member table --%>
<%-- => Store an object containing authority-related information as a session attribute - provide authority to the client: login --%>    
<%-- => Respond by sending the URL of the [login_form.jsp] document to the client --%>    
<%
    // request.getMethod(): Method that returns the request method (GET or POST) used to request the JSP document
    if(request.getMethod().equals("GET")) { // If the JSP document is requested using the GET method - abnormal request
        /*
        out.println("<script type='text/javascript'>");
        out.println("alert('You have requested the page in an abnormal way.');");
        out.println("location.href='login_form.jsp';");
        out.println("</script>");
        */
        
        /*
        // response.sendError(int sc): Method that responds to the client by sending an error code (4XX or 5XX)
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return;
        */
        
        /*
        // response.sendRedirect(String url): Method that responds to the client by sending a URL
        // => The client that receives the URL changes the request URL in the browser and requests the JSP document
        // and receives and prints the execution result - redirect
        // When responding by sending the URL of the JSP document, it is possible to pass values using a query string
        // => Request URL should be composed of only English letters, numbers, and some special characters
        // Characters other than English letters, numbers, and some special characters cannot be used
        // If characters other than English letters, numbers, and some special characters are included
        // in the query string of the URL, an IllegalArgumentException exception occurs making it impossible to respond with the URL
        // => Characters other than English letters, numbers, and some special characters can be encoded and used in the URL
        // String message = "You have requested the page in an abnormal way.";
        
        // URLEncoder.encode(String s, String enc): Static method that encodes the given string
        // into the desired character set and returns it
        // => Provides the same functionality as the JavaScript function encodeURIComponent()
        String message = URLEncoder.encode("You have requested the page in an abnormal way.", "utf-8");
        System.out.println("message = "+message);
        response.sendRedirect("login_form.jsp?msg="+message);
        return;
        */
        
        // session.setAttribute(String attributeName, Object attributeValue): Method that stores the attribute name (string) and attribute value (object)
        // passed as parameters in the client's session (HttpSession object) bound to the JSP document
        // => Provides the attribute value stored in the session to all JSP documents - object sharing
        session.setAttribute("msg", "You have requested the page in an abnormal way.");
        response.sendRedirect("login_form.jsp");
        return;
    }

    // request.setCharacterEncoding(String encoding): Method that changes the encoding to receive the values passed by POST method in the desired character set
    // => Can be omitted if the JSP document is requested using the GET method or if the passed values are composed of only English letters, numbers, and special characters
    // request.setCharacterEncoding("utf-8");

    // Retrieve the passed values and store them
    String id = request.getParameter("id");
    String passwd = request.getParameter("passwd");
    
    // Compare the received authentication information with the stored authentication information in the table - authentication process
    if(!id.equals("abc123") || !passwd.equals("123456")) { // Authentication failure
        session.setAttribute("id", id);
        session.setAttribute("msg", "The username or password is incorrect.");
        response.sendRedirect("login_form.jsp");
        return;
    }
    
    // Authentication success - store the object containing authority information as a session attribute
    session.setAttribute("loginId", id);
    response.sendRedirect("login_form.jsp");
%>
```

#### LoginUser -jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that responds with the logged-in user information included in the HTML document --%>
<%-- => If a non-logged-in user requests the JSP document, respond by sending the URL address of the [login_form.jsp] document --%>
<%-- => If the [Logout] tag is clicked, move to the [logout_action.jsp] document --%>
<%-- => If the [Main] tag is clicked, move to the [login_form.jsp] document --%>
<%
    // Retrieve the attribute value containing authority-related information stored in the session
    String loginId = (String) session.getAttribute("loginId");

    // If the retrieved attribute value is null - the user is not logged in and the JSP document is requested abnormally
    if (loginId == null) {
        session.setAttribute("msg", "Only logged-in users can request this page.");
        response.sendRedirect("login_form.jsp");
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
    <h1>My Information</h1>
    <hr>
    <a href="logout_action.jsp">[Logout]</a>
    <a href="login_form.jsp">[Main]</a>
    <hr>
    <p>ID = <%=loginId %>&nbsp;&nbsp;</p>
    <p>This information is only available to logged-in users.</p>
    <p>This information is only available to logged-in users.</p>
    <p>This information is only available to logged-in users.</p>
    <p>This information is only available to logged-in users.</p>
    <p>This information is only available to logged-in users.</p>
    <hr>
    <%-- Use web resources with a relative path --%>
    <%-- => Relative path: The path of the web resource is expressed based on the folder of the requested JSP document --%>
    <img alt="Koala" src="../images/Koala.jpg" width="200">
    
    <%-- Use web resources with an absolute path - recommended --%>
    <%-- => Absolute path: The path of the web resource is expressed based on the root folder (server directory) --%>
    <%-- Issue: If the name of the context root folder is changed, the path of the web resource is changed, causing a 404 error --%>
    <img alt="Koala" src="/jsp/images/Koala.jpg" width="200">
    
    <%-- Solution: Write the URL address by calling the method that returns the path of the context root folder --%>
    <%-- request.getContextPath(): Method that returns the path of the context root folder --%>
    <img alt="Koala" src="<%=request.getContextPath() %>/images/Koala.jpg" width="200">
</body>
</html>
```

#### Logout-jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- After processing logout, respond by sending the URL address to request the [login_form.jsp] document to the client --%>
<%-- => Logout: Delete the attribute value containing the authority-related information stored in the session --%>    
<%
    //session.removeAttribute("loginId");

    //session.invalidate(): Method to unbind and delete the session
    session.invalidate();

    response.sendRedirect("login_form.jsp");
%>
```