---
layout: single
title: 2024/07/12 JSP 12-EX-Shop-Member-Login-Logout
---

## MemberDAO -code add

```java

public int updateLastLogin(int num) {
    Connection con = null;
    PreparedStatement pstmt = null;
    int rows = 0;
    try {
        con = getConnection();
        
        String sql = "update member set member_last_login = sysdate where member_num = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, num);
        
        rows = pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("[Error] SQL error in updateLastLogin() method = " + e.getMessage());
    } finally {
        close(con, pstmt);
    }
    return rows;
}
```

### Header - Change code -jsp
### before 


```java
<div id="profile">

<a href="index.jsp?workgroup=member&work=member_login">login</a>&nbsp;&nbsp;

<a href="index.jsp?workgroup=member&work=member_join">Sign Up</a>&nbsp;&nbsp;

</div> 
```
### After

```jsp

<%-- Retrieve and store the attribute containing authorization-related information from the session object 
 => Returns null: when there is no attribute - Unauthenticated user 
 => Returns MemberDTO object: when there is an attribute - Authenticated user --%>
<%
MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
%>

<div id="profile">

<% if (loginMember == null) { %>

<a href="index.jsp?workgroup=member&work=member_login">Login</a>&nbsp;&nbsp;

<a href="index.jsp?workgroup=member&work=member_join">Sign Up</a>&nbsp;&nbsp;

<% } else { %>

<span style="font-weight: bold;">[<%= loginMember.getMemberName() %>] Welcome.</span>&nbsp;&nbsp;

<a href="index.jsp?workgroup=member&work=member_logout_action">Logout</a>&nbsp;&nbsp;

<a href="index.jsp?workgroup=member&work=member_mypage">My Page</a>&nbsp;&nbsp;

<% if (loginMember.getMemberAuth() == 9) { // If the authenticated user is an admin %>

<a href="index.jsp?workgroup=admin&work=admin_main">Admin</a>&nbsp;&nbsp;

<% } %>

<% } %>
```


### LoginForm(memberLogin) - jsp


- **JSP Page Directive**: Specifies the page language and character encoding.
- **Session Attributes**: Retrieves `message` and `id` from the session, setting them to an empty string if they are not present, and then removes them from the session.
- **CSS Styles**: Defines styles for the login form, including spacing, input focus, and button appearance.
- **HTML Form**: Creates a form for user login, including input fields for ID and password, and a login button.
- **JavaScript**: Handles form validation and submission. If the ID or password is not entered, an appropriate message is displayed and focus is set to the missing field.

- Encode the URL address - will explain Cart section. 
	- url :  Path of the JSP document from the requested URL address +the query string from the requested URL address (if  queryString url there is.)
		


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document for receiving authentication information (ID and password) from the user --%>
<%-- => When the [Login] tag is clicked, request the [/member/member_login_action.jsp] document to navigate to the page - passing the input values --%>
<%
	
	String url=request.getParameter("url");
	if(url == null) {
		url="";
		
    String message=(String)session.getAttribute("message");
    if(message == null) {
        message="";
    } else {
        session.removeAttribute("message");
    }
    
    String id=(String)session.getAttribute("id");
    if(id == null) {
        id="";
    } else {
        session.removeAttribute("id");
    }
%>
<style type="text/css">
#space {
    height: 50px;
}    
.login_tag {
    margin: 5px auto;
    width: 300px;
}    

#login label {
    text-align: right;
    width: 100px;
    float: left;
}

#login ul li {
    list-style-type: none;
    margin-bottom: 10px;
}

#login input:focus {
    border: 2px solid aqua;
}

#login_btn {
    margin: 0 auto;
    padding: 5px;
    width: 300px;
    background-color: aqua;
    font-size: 1.2em;
    cursor: pointer;
    letter-spacing: 20px;
    font-weight: bold;     
}

#search {
    margin-top: 10px;    
    margin-bottom: 20px;    
}

#message {
    color: red;    
    font-weight: bold;
}

a:hover {
    background-color: orange;
}
</style>
<div id="space"></div>
<form id="login" name="loginForm"  method="post"
    action="<%=request.getContextPath() %>/index.jsp?workgroup=member&work=member_login_action">
    <ul class="login_tag">
        <li>
            <label for="id">ID</label>
            <input type="text" name="id" id="id" value="<%=id%>">
        </li>
        <li>
            <label for="passwd">Password</label>
            <input type="password" name="passwd" id="passwd">
        </li>
    </ul>
    <div id="login_btn">Login</div>
    <div id="search">
        <a href="#">Find ID</a> |
        <a href="#">Find Password</a> 
    </div>
    <div id="message"><%=message %></div>
</form>
<script type="text/javascript">
$("#id").focus();

$("#login_btn").click(function() {
    if($("#id").val()=="") {
        $("#message").text("Please enter your ID.");
        $("#id").focus();
        return;
    }
    
    if($("#passwd").val()=="") {
        $("#message").text("Please enter your password.");
        $("#passwd").focus();
        return;
    }
    
    $("#login").submit();
});
</script>
```

## LoginAction (memberLoginAction) - jsp



```java
<%@page import="xyz.itwill.dao.MemberDAO"%>
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@page import="xyz.itwill.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document for processing login by comparing authentication information received with member information stored in the MEMBER table
     and responding with the URL address to request the [/member/main_page.jsp] document 
 => Login processing: Save authorization-related information upon successful authentication 
 => If a URL address is also received, respond with the received URL address instead of the [/member/main_page.jsp] document 
 => If authentication fails, respond with the URL address to request the [/member/main_login.jsp] document --%>
<%
    // Handle the response for abnormal requests for the JSP document
    if(request.getMethod().equals("GET")) {// If the JSP document is requested via GET method
        // Save the URL address of the page that displays the error message as an attribute of the request object
        // and provide it to the requesting JSP document (index.jsp) - The requesting JSP document (index.jsp) handles the redirect
        request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
        return;
    }
    
    // Retrieve and store the values received
    String id = request.getParameter("id");
    String passwd = Utility.encrypt(request.getParameter("passwd"));
    String url=request.getParameter("url");
	if(url == null) {
	url="";

}
    
    // Call the method of the MemberDAO class to search the MEMBER table for a row with the received ID and return the retrieved member information
    MemberDTO member = MemberDAO.getDAO().selectMemberById(id);
    
    // Handle authentication failure if no member information is retrieved, the password of the retrieved member information does not match the received password,
    // or the retrieved member information is for a withdrawn member
    if(member == null || !member.getMemberPasswd().equals(passwd) || member.getMemberAuth() == 0) {
        session.setAttribute("message", "Incorrect ID or password. Please enter your ID and password correctly.");
        session.setAttribute("id", id);
        request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=member&work=member_login");
        return;
    }

    // Authentication success - Save the object containing authorization-related information as an attribute of the session object
    // => Save the logged-in user's information (MemberDTO object) as an attribute of the session object
    // session.setAttribute("loginMemberNum", member.getMemberNum());
    session.setAttribute("loginMember", member);
    
    // Call the method of the MemberDAO class to update the last login date and time to the current date and time for the row in the MEMBER table
    // identified by the member number, and return the number of rows updated (int)
    MemberDAO.getDAO().updateLastLogin(member.getMemberNum());

    // Handle page navigation
    if( url.equals("")){// URL is null  then moves to main_page
   request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=main&work=main_page");
    }else {//else then moves to url address
   request.setAttribute("returnUrl", url);
   }
%>
```
## LogoutForm - memberLogout- jsp


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that responds with a URL address to request [/main/main_page.jsp] after logout --%>
<%-- => Logout process: Remove authorization-related attributes stored in the session --%>
<%
	//session.removeAttribute("loginMember");
	session.invalidate();

	request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=main&work=main_page");
%>
```


#### Header Page
- Click Logout -> works memberLogout.jsp-> session out.-> back to login pages.
- Logout:  **a href="index.jsp?workgroup=member&work=member_logout_action">Log Out</a**
```html
<div id="profile">
	<% if(loginMember == null) { %>
		<a href="index.jsp?workgroup=member&work=member_login">Log In</a>&nbsp;&nbsp;
		<a href="index.jsp?workgroup=member&work=member_join">Join Us</a>&nbsp;&nbsp;
	<% } else { %>
		<span style="font-weight: bold;">[<%=loginMember.getMemberName() %>] Welcome.</span>&nbsp;&nbsp;
		<a href="index.jsp?workgroup=member&work=member_logout_action">Log Out</a>&nbsp;&nbsp;
		<a href="index.jsp?workgroup=member&work=member_mypage">My Profile</a>&nbsp;&nbsp;
		<% if(loginMember.getMemberAuth() == 9) {// If the logged-in user is an admin %>
			<a href="index.jsp?workgroup=admin&work=admin_main">Admin</a>&nbsp;&nbsp;
		<% } %>
	<% } %>
</div>
```

