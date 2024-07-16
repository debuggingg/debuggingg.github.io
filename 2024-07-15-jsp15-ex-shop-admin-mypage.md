---
layout: single
title: 2024/07/15 JSP 15-EX-Shop-Admin-<yAccount
---

## Admin 



#### change indext.jsp pages 

- Put the code below in the Index.jsp 
```js

String headerPath="/header.jsp";
if(workgroup.equals("admin")) {
headerPath="/header_admin.jsp";
}
```

- change the code below 
--> <jsp:include page="header.jsp"/> 
=> <jsp:include page="<%=headerPath %>"/>

#### Create Admin pages - jsp
admin_main.jsp

```html
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
    // Retrieve the attribute storing authorization-related information from the session object and store it as an object
    MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
    // Handle response for unauthorized requests from non-logged-in users or regular users requesting the JSP document - abnormal request
    if (loginMember == null || loginMember.getMemberAuth() != 9) {
        request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
        return;         
    }
--%>
<%@include file="/security/admin_check.jspf" %>
<h1>Admin Main Page</h1>
```


Header_admin.jsp
```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="profile">
    <span style="font-weight: bold;">Welcome, Administrator.</span>&nbsp;&nbsp;
    <a href="index.jsp?workgroup=member&work=member_logout_action">Logout</a>&nbsp;&nbsp;
    <a href="index.jsp?workgroup=main&work=main_page">Shopping Mall</a>&nbsp;&nbsp;
</div>
<div id="logo"><a href="index.jsp?workgroup=admin&work=admin_main">Administrator</a></div>
<div id="menu">
    <a href="#">Member Management</a>
    <a href="#">Post Management</a>
    <a href="#">Product Management</a>
    <a href="#">Order Management</a>
</div>
```


admin_check.jsp - 앞으로 사용하기 위해 미리 만들어서 호출하여 사용 
```html
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// Retrieve the attribute storing authorization-related information from the session object and store it as an object
	MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

	// Handling response for unauthorized requests from non-logged-in users or regular users requesting the JSP document - abnormal request
	if (loginMember == null || loginMember.getMemberAuth() != 9) {
		request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
		return;			
	}
%>
```



---

my page 관리 9d447ee
#### My page Changes(member_mypage.jsp)

- change a tag all  the way to bottom 
- create the password_confirm.jsp  and move to this 
When clicking the tag [Modify Member Information], request the document [/member/password_confirm.jsp] for page redirection - Passing values related to page redirection 
 When clicking the tag [Withdraw Membership], request the document [/member/password_confirm.jsp] for page redirection - Passing values related to page redirection 
```html

<a href="<%=request.getContextPath()%>/index.jsp?workgroup=member&work=password_confirm&action=modify">[Modify Member Information]</a>&nbsp;&nbsp;
<a href="<%=request.getContextPath()%>/index.jsp?workgroup=member&work=password_confirm&action=remove">[Withdraw Membership]</a>
```

#### password_confirm.jsp (created)

- Create a JSP page that prompts the user to enter their password when they click on "Modify Information" or "Withdraw Membership" on their profile page.

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document to receive password input from the user --%>
<%-- => Only accessible to logged-in users --%>
<%-- => When the [Submit] tag is clicked, request a different JSP document based on the transmitted value for page redirection - Transmitting input values (password) --%>
<%@include file="/security/login_check.jspf" %>
<%
    // Retrieve and store the transmitted value
    String action = request.getParameter("action");

    // Handling response for requests with no or abnormal transmitted values - Abnormal request
    if (action == null || (!action.equals("modify") && !action.equals("remove"))) {
        request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
        return;
    }
    // After creating member_modify, additional code added
    String message = (String) session.getAttribute("message");
    if (message == null) {
        message = "";
    } else {
        session.removeAttribute("message");
    }
%>
<% if (action.equals("modify")) { %>
    <p>Please enter your password to modify member information.</p>
<% } else { %>
    <p>Please enter your password to withdraw membership.</p>
<% } %>
<form method="post" id="passwordForm">
    <input type="password" name="passwd" id="passwd">
    <button type="button" id="submitBtn">Submit</button>
</form>
<p id="message" style="color: red;"><%= message %></p><%-- <%= message %> added after creating member_modify --%>

<script type="text/javascript">
$("#passwd").focus();

$("#submitBtn").click(function() {
    if ($("#passwd").val() == "") {
        $("#message").text("Please enter your password.");
        return;
    }
    
    // Set the action attribute of the form tag differently based on the transmitted value
    <% if (action.equals("modify")) { %>
        $("#passwordForm").attr("action", "<%= request.getContextPath() %>/index.jsp?workgroup=member&work=member_modify")
    <% } else { %>
        $("#passwordForm").attr("action", "<%= request.getContextPath() %>/index.jsp?workgroup=member&work=member_remove_action")
    <% } %>
    
    $("#passwordForm").submit(); // Execute the form tag
});
</script>

```

####  member_modify .jsp

```jsp
<%@page import="xyz.itwill.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document to receive member information for modification --%>
<%-- => Only accessible to logged-in users --%>
<%-- => Compare the received password with the password stored in the MEMBER table and display the input tags for member information if they match - Use member information stored in session --%>
<%-- => If the [Postal Code Search] tag is clicked, change the input values of the input tags (zip code and basic address) using the Daum postal service --%>
<%-- => If the [Modify Member] tag is clicked, request [/member/member_modify_action.jsp] to redirect to another page - Pass input values --%>
<%@include file="/security/login_check.jspf" %>
<%
	// Response handling for abnormal JSP document requests
	if(request.getMethod().equals("GET")) {
		request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=error&work=error_400");
		return;
	}

	// Retrieve and store received parameters
	String passwd = Utility.encrypt(request.getParameter("passwd"));
	
	// Response handling for cases where the logged-in user's password and received password do not match
	if(!loginMember.getMemberPasswd().equals(passwd)) {
		session.setAttribute("message", "The password you entered is incorrect. Please enter the correct password.");
		request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=member&work=password_confirm&action=modify");
		return;		
	}
%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>    
<style type="text/css">
fieldset {
	text-align: left;
	margin: 10px auto;
	width: 1100px;
}

legend {
	font-size: 1.2em;
}

#join label {
	width: 150px;
	text-align: right;
	float: left;
	margin-right: 10px;
}

#join ul li {
	list-style-type: none;
	margin: 15px 0;
}

#fs {
	text-align: center;
}

.error {
	color: red;
	position: relative;
	left: 160px;
	display: none;
}

#idCheck, #postSearch {
	font-size: 12px;
	font-weight: bold;
	cursor: pointer;
	margin-left: 10px;
	padding: 2px 10px;
	border: 1px solid black;
}

#idCheck:hover, #postSearch:hover {
	background: aqua;
}
</style>
<form id="join" action="<%=request.getContextPath() %>/index.jsp?workgroup=member&work=member_modify_action" method="post">
<input type="hidden" name="num" value="<%=loginMember.getMemberNum()%>">
<fieldset>
	<legend>Member Information</legend>
	<ul>
		<li>
			<label for="id">ID</label>
			<input type="text" name="id" id="id" value="<%=loginMember.getMemberId()%>" readonly="readonly">
		</li>
		<li>
			<label for="passwd">Password</label>
			<input type="password" name="passwd" id="passwd">
			<span style="color: red;">Leave blank if you do not wish to change your password.</span>
			<div id="passwdMsg" class="error">Please enter your password.</div>
			<div id="passwdRegMsg" class="error">Password must contain at least one letter, one number, and one special character (~!@#$%^&*_-), and be between 6 to 20 characters.</div>
		</li>
		<li>
			<label for="passwd">Confirm Password</label>
			<input type="password" name="repasswd" id="repasswd">
			<div id="repasswdMsg" class="error">Please confirm your password.</div>
			<div id="repasswdMatchMsg" class="error">Passwords do not match.</div>
		</li>
		<li>
			<label for="name">Name</label>
			<input type="text" name="name" id="name" value="<%=loginMember.getMemberName()%>">
			<div id="nameMsg" class="error">Please enter your name.</div>
		</li>
		<li>
			<label for="email">Email</label>
			<input type="text" name="email" id="email" value="<%=loginMember.getMemberEmail()%>">
			<div id="emailMsg" class="error">Please enter your email.</div>
			<div id="emailRegMsg" class="error">The email entered is not in correct format.</div>
		</li>
		<li>
			<% String[] mobile=loginMember.getMemberMobile().split("-"); %>
			<label for="mobile2">Phone Number</label>
			<select name="mobile1">
				<option value="010" <% if(mobile[0].equals("010")) { %> selected <% } %>>&nbsp;010&nbsp;</option>
				<option value="011" <% if(mobile[0].equals("011")) { %> selected <% } %>>&nbsp;011&nbsp;</option>
				<option value="016" <% if(mobile[0].equals("016")) { %> selected <% } %>>&nbsp;016&nbsp;</option>
				<option value="017" <% if(mobile[0].equals("017")) { %> selected <% } %>>&nbsp;017&nbsp;</option>
				<option value="018" <% if(mobile[0].equals("018")) { %> selected <% } %>>&nbsp;018&nbsp;</option>
				<option value="019" <% if(mobile[0].equals("019")) { %> selected <% } %>>&nbsp;019&nbsp;</option>
			</select>
			- <input type="text" name="mobile2" id="mobile2" size="4" maxlength="4" value="<%=mobile[1]%>">
			- <input type="text" name="mobile3" id="mobile3" size="4" maxlength="4" value="<%=mobile[2]%>">
			<div id="mobileMsg" class="error">Please enter your phone number.</div>
			<div id="mobileRegMsg" class="error">Phone number should be 3 to 4 digits only.</div>
		</li>
		<li>
			<label>Zip Code</label>
			<input type="text" name="zipcode" id="zipcode" size="7" readonly="readonly" value="<%=loginMember.getMemberZipcode()%>">
			<span id="postSearch">Search Postal Code</span>
			<div id="zipcodeMsg" class="error">Please enter your zip code.</div>
		</li>
		<li>
			<label for="address1">Basic Address</label>
			<input type="text" name="address1" id="address1" size="50" readonly="readonly" value="<%=loginMember.getMemberAddress1()%>">
			<div id="address1Msg" class="error">Please enter your basic address.</div>
		</li>
		<li>
			<label for="address2">Detail Address</label>
			<input type="text" name="address2" id="address2" size="50" value="<%=loginMember.getMemberAddress2()%>">
			<div id="address2Msg" class="error">Please enter your detail address.</div>
		</li>
	</ul>
</fieldset>
<div id="fs">
	<button type="submit">Modify Member</button>
	<button type="reset">Reset</button>
</div>
</form>
<script type="text/javascript">
$("#id").focus();

$("#join").submit(function() {
	var submitResult = true;
	
	$(".error").css("display","none");
		
	var passwdReg = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*_-]).{6,20}$/g;
	if($("#passwd").val() != "" && !passwdReg.test($("#passwd").val())) {
		$("#passwdRegMsg").css("display","block");
		submitResult = false;
	} 
	
	if($("#passwd").val() != $("#repasswd").val()) {
		$("#repasswdMatchMsg").css("display","block");
		submitResult = false;
	}
	
	if($("#name").val() == "") {
		$("#nameMsg").css("display","block");
		submitResult = false;
	}
	
	var emailReg = /^([a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+(\.[-a-zA-Z0-9]+)+)*$/g;
	if($("#email").val() == "") {
		$("#emailMsg").css("display","block");
		submitResult = false;
	} else if(!emailReg.test($("#email").val())) {
		$("#emailRegMsg").css("display","block");
		submitResult = false;
	}

	var mobile2Reg = /\d{3,4}/;
	var mobile3Reg = /\d{4}/;
	if($("#mobile2").val() == "" || $("#mobile3").val() == "") {
		$("#mobileMsg").css("display","block");
		submitResult = false;
	} else if(!mobile2Reg.test($("#mobile2").val



-> member_modify_action.jsp 

```jsp
<%@page import="xyz.itwill.util.Utility"%>
<%@page import="xyz.itwill.dao.MemberDAO"%>
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that receives member information, updates a row in the MEMBER table, and responds with a URL to request [/member/member_mypage.jsp] --%>
<%-- => Only accessible to logged-in users --%>
<%@include file="/security/login_check.jspf" %>
<%
    // Handling response for abnormal requests to JSP documents
    if (request.getMethod().equals("GET")) { // Requested the JSP document using GET method
        request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
        return;
    }
    
    // Retrieve and store transmitted values
    int num = Integer.parseInt(request.getParameter("num"));
    String passwd = request.getParameter("passwd");
    if (passwd != null) {
        passwd = Utility.encrypt(passwd); // Encrypt the password
    }
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String mobile = request.getParameter("mobile1") + "-" + request.getParameter("mobile2") + "-" + request.getParameter("mobile3");
    String zipcode = request.getParameter("zipcode");
    String address1 = request.getParameter("address1");
    String address2 = request.getParameter("address2");
    
    // Create a MemberDTO object and set field values based on transmitted values
    MemberDTO member = new MemberDTO();
    member.setMemberNum(num);
    member.setMemberPasswd(passwd);
    member.setMemberName(name);
    member.setMemberEmail(email);
    member.setMemberMobile(mobile);
    member.setMemberZipcode(zipcode);
    member.setMemberAddress1(address1);
    member.setMemberAddress2(address2);
    
    // Call the method of MemberDAO class to update a row in the MEMBER table with received member information (MemberDTO object) and return the number of updated rows
    MemberDAO.getDAO().updateMember(member);
    
    if (passwd != null) { // If there is a transmitted value (password)
        // Call the method of MemberDAO class to update the password of a row in the MEMBER table with received member information (MemberDTO object) and return the number of updated rows
        MemberDAO.getDAO().updatePassword(member);
    }
    
    // Change the attribute storing login-related information in the session
    // => Retrieve a row from the MEMBER table based on member number, return as MemberDTO object, and change the attribute value in the session
    session.setAttribute("loginMember", MemberDAO.getDAO().selectMemberByNum(num));
    
    // Store the URL address in the attribute of the request object to provide to the requesting JSP document (index.jsp)
    // => Redirect to the returned URL address from the requesting JSP document (index.jsp)
    request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=member&work=member_mypage");
%>
```


#### add new Method (updateMember, updatePassword) MemberDAO 

```java
// Method that receives member information (MemberDTO object), updates a row in the MEMBER table, and returns the number of updated rows
public int updateMember(MemberDTO member) {
    Connection con = null;
    PreparedStatement pstmt = null;
    int rows = 0;
    try {
        con = getConnection();

        String sql = "update member set member_name=?, member_email=?, member_mobile=?, "
                   + "member_zipcode=?, member_address1=?, member_address2=? where member_num=?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, member.getMemberName());
        pstmt.setString(2, member.getMemberEmail());
        pstmt.setString(3, member.getMemberMobile());
        pstmt.setString(4, member.getMemberZipcode());
        pstmt.setString(5, member.getMemberAddress1());
        pstmt.setString(6, member.getMemberAddress2());
        pstmt.setInt(7, member.getMemberNum());

        rows = pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("[Error] SQL error in updateMember() method: " + e.getMessage());
    } finally {
        close(con, pstmt);
    }
    return rows;
}

// Method that receives member information (MemberDTO object), updates the password of a row in the MEMBER table, and returns the number of updated rows
public int updatePassword(MemberDTO member) {
    Connection con = null;
    PreparedStatement pstmt = null;
    int rows = 0;
    try {
        con = getConnection();

        String sql = "update member set member_passwd=? where member_num=?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, member.getMemberPasswd());
        pstmt.setInt(2, member.getMemberNum());

        rows = pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("[Error] SQL error in updatePassword() method: " + e.getMessage());
    } finally {
        close(con, pstmt);
    }
    return rows;
}
```

#### member_remove_action.jsp

```jsp
<%@page import="xyz.itwill.dao.MemberDAO"%>
<%@page import="xyz.itwill.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that compares the received password with the password stored in the MEMBER table, 
     and if they match, updates the authority of the corresponding member row in the table and 
     responds with the URL address to request [/member/member_logout_action.jsp]. 
     It utilizes session-stored member information. --%>
<%@include file="/security/login_check.jspf" %>
<%
    // Response handling for abnormal requests to the JSP document
    if (request.getMethod().equals("GET")) {
        request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
        return;
    }

    // Retrieve and store the received parameter values
    String passwd = Utility.encrypt(request.getParameter("passwd"));
    
    // Handling for cases where the received password does not match the logged-in member's password
    if (!loginMember.getMemberPasswd().equals(passwd)) {
        session.setAttribute("message", "The password you entered is incorrect. Please enter the correct password.");
        request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=member&work=password_confirm&action=remove");
        return;
    }
    
    // Create a MemberDTO object and set the member number and authority to update
    MemberDTO member = new MemberDTO();
    member.setMemberNum(loginMember.getMemberNum());
    member.setMemberAuth(0);
    
    // Call the updateAuth method of the MemberDAO class to update the authority of the member row in the MEMBER table
    int rowsUpdated = MemberDAO.getDAO().updateAuth(member);
    
    // Set the URL address for page redirection
    request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=member&work=member_logout_action");
%>
```
-> add new Method (updateAuth) MemberDAO

```java
// Method that receives a MemberDTO object and updates the member authority in the MEMBER table, returning the number of rows affected.
public int updateAuth(MemberDTO member) {
    Connection con = null;
    PreparedStatement pstmt = null;
    int rows = 0;
    try {
        con = getConnection();

        String sql = "update member set member_auth=? where member_num=?";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, member.getMemberAuth());
        pstmt.setInt(2, member.getMemberNum());

        rows = pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("[Error] SQL error in updateAuth() method: " + e.getMessage());
    } finally {
        close(con, pstmt);
    }
    return rows;
}
```


