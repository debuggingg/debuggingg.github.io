---
layout: single
title: 2024/07/11 JSP 10-EX-Shop-Member SignUp
---
# Shopping Mall 

## Member SignUp Pages 

## MemberDTO 

create table member(member_num number primary key, member_id varchar2(30) unique, member_passwd varchar2(200)
    , member_name varchar2(30), member_email varchar2(50), member_mobile varchar2(20), member_zipcode varchar2(10)
    , member_address1 varchar2(200), member_address2 varchar2(100), member_register_date date
    , member_update_date date, member_last_login date, member_auth number(1));
    
create sequence member_seq;

name                   null?       type           
-------------------- -------- ------------- 
MEMBER_NUM           NOT NULL NUMBER        - Member number (PK)
MEMBER_ID                     VARCHAR2(30)  - ID (UNIQUE)
MEMBER_PASSWD                 VARCHAR2(200) - Password (encrypted)
MEMBER_NAME                   VARCHAR2(30)  - Name
MEMBER_EMAIL                  VARCHAR2(50)  - Email
MEMBER_MOBILE                 VARCHAR2(20)  - Phone number
MEMBER_ZIPCODE                VARCHAR2(10)  - Zip code
MEMBER_ADDRESS1               VARCHAR2(200) - Primary address
MEMBER_ADDRESS2               VARCHAR2(100) - Detailed address
MEMBER_REGISTER_DATE          DATE          - Date of registration
MEMBER_UPDATE_DATE            DATE          - Date of last update
MEMBER_LAST_LOGIN             DATE          - Date of last login
MEMBER_AUTH                   NUMBER(1)     - Member authorization: 0 (withdrawn member), 1 (regular member), 9 (administrator)


## MemberDAO -omitted JdbcDAO,context,web


```java
public class MemberDAO extends JdbcDAO {
	private static MemberDAO _dao;
	
	private MemberDAO() {
	
	}
	
	static {
		_dao = new MemberDAO();		
	}
	
	public static MemberDAO getDAO() {
		return _dao;
	}
	
	// Method to insert member information (MemberDTO object) into the MEMBER table and return the number of rows inserted (int)
	public int insertMember(MemberDTO member) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			con = getConnection();
			
			String sql = "INSERT INTO member VALUES (member_seq.nextval,?,?,?,?,?,?,?,?,SYSDATE,NULL,NULL,1)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPasswd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberEmail());
			pstmt.setString(5, member.getMemberMobile());
			pstmt.setString(6, member.getMemberZipcode());
			pstmt.setString(7, member.getMemberAddress1());
			pstmt.setString(8, member.getMemberAddress2());
			
			rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in insertMember() method: " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	// Method to retrieve member information (MemberDTO) by ID (String object) from the MEMBER table
	public MemberDTO selectMemberById(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDTO member = null;
		try {
			con = getConnection();
			
			String sql = "SELECT member_num, member_id, member_passwd, member_name, member_email"
				+ ", member_mobile, member_zipcode, member_address1, member_address2"
				+ ", member_register_date, member_update_date, member_last_login, member_auth"
				+ " FROM member WHERE member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();			
			
			if (rs.next()) {
				member = new MemberDTO();
				member.setMemberNum(rs.getInt("member_num"));
				member.setMemberId(rs.getString("member_id"));
				member.setMemberPasswd(rs.getString("member_passwd"));
				member.setMemberName(rs.getString("member_name"));
				member.setMemberEmail(rs.getString("member_email"));
				member.setMemberMobile(rs.getString("member_mobile"));
				member.setMemberZipcode(rs.getString("member_zipcode"));
				member.setMemberAddress1(rs.getString("member_address1"));
				member.setMemberAddress2(rs.getString("member_address2"));
				member.setMemberRegisterDate(rs.getString("member_register_date"));
				member.setMemberUpdateDate(rs.getString("member_update_date"));
				member.setMemberLastLogin(rs.getString("member_last_login"));
				member.setMemberAuth(rs.getInt("member_auth"));
			}
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in selectMemberById() method: " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return member;
	}
}
``` 
---

## MemberSignup Form-jsp 

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document for user input of member information --%>
<%-- => When clicking on the [Register] tag, request [/member/member_join_action.jsp] for page transition - passing input values --%>
<%-- => When clicking on the [Check ID duplication] tag, open a new browser (popup window) to request [/member/id_check.jsp] - passing the ID --%>
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
<form id="join" action="<%=request.getContextPath() %>/index.jsp?workgroup=member&work=member_join_action" method="post">
<%-- Input tag to verify whether the [Check ID duplication] function is executed or not --%>
<%-- => 0 : Not executed - ID duplicated, 1 : Executed - ID not duplicated --%>
<input type="hidden" id="idCheckResult" value="0">
<fieldset>
	<legend>Member Registration Information</legend>
	<ul>
		<li>
			<label for="id">Username</label>
			<input type="text" name="id" id="id"><span id="idCheck">Check ID Duplication</span>
			<div id="idMsg" class="error">Please enter your username.</div>
			<div id="idRegMsg" class="error">Username must be between 6 to 20 characters and start with an alphabet, followed by alphanumeric characters or underscores.</div>
			<div id="idCheckMsg" class="error">Please perform the ID duplication check.</div>
		</li>
		<li>
			<label for="passwd">Password</label>
			<input type="password" name="passwd" id="passwd">
			<div id="passwdMsg" class="error">Please enter your password.</div>
			<div id="passwdRegMsg" class="error">Password must be between 6 to 20 characters and include at least one alphabet, one number, and one special character.</div>
		</li>
		<li>
			<label for="passwd">Confirm Password</label>
			<input type="password" name="repasswd" id="repasswd">
			<div id="repasswdMsg" class="error">Please confirm your password.</div>
			<div id="repasswdMatchMsg" class="error">Password and confirmation do not match.</div>
		</li>
		<li>
			<label for="name">Name</label>
			<input type="text" name="name" id="name">
			<div id="nameMsg" class="error">Please enter your name.</div>
		</li>
		<li>
			<label for="email">Email</label>
			<input type="text" name="email" id="email">
			<div id="emailMsg" class="error">Please enter your email.</div>
			<div id="emailRegMsg" class="error">The entered email is not in the correct format.</div>
		</li>
		<li>
			<label for="mobile2">Phone Number</label>
			<select name="mobile1">
				<option value="010" selected>&nbsp;010&nbsp;</option>
				<option value="011">&nbsp;011&nbsp;</option>
				<option value="016">&nbsp;016&nbsp;</option>
				<option value="017">&nbsp;017&nbsp;</option>
				<option value="018">&nbsp;018&nbsp;</option>
				<option value="019">&nbsp;019&nbsp;</option>
			</select>
			- <input type="text" name="mobile2" id="mobile2" size="4" maxlength="4">
			- <input type="text" name="mobile3" id="mobile3" size="4" maxlength="4">
			<div id="mobileMsg" class="error">Please enter your phone number.</div>
			<div id="mobileRegMsg" class="error">Phone number should consist of 3 to 4 digits only.</div>
		</li>
		<li>
			<label>Zip Code</label>
			<input type="text" name="zipcode" id="zipcode" size="7">
			<div id="zipcodeMsg" class="error">Please enter your zip code.</div>
		</li>
		<li>
			<label for="address1">Primary Address</label>
			<input type="text" name="address1" id="address1" size="50">
			<div id="address1Msg" class="error">Please enter your primary address.</div>
		</li>
		<li>
			<label for="address2">Detail Address</label>
			<input type="text" name="address2" id="address2" size="50">
			<div id="address2Msg" class="error">Please enter your detail address.</div>
		</li>
	</ul>
</fieldset>
<div id="fs">
	<button type="submit">Register</button>
	<button type="reset">Reset</button>
</div>
</form>
<script type="text/javascript">
$("#id").focus();

$("#join").submit(function() {
	var submitResult=true;
	
	$(".error").css("display","none");

	var idReg=/^[a-zA-Z]\w{5,19}$/g;
	if($("#id").val()=="") {
		$("#idMsg").css("display","block");
		submitResult=false;
	} else if(!idReg.test($("#id").val())) {
		$("#idRegMsg").css("display","block");
		submitResult=false;
	} else if($("#idCheckResult").val()=="0") {
		$("#idCheckMsg").css("display","block");
		submitResult=false;
	}
		
	var passwdReg=/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*_-]).{6,20}$/g;
	if($("#passwd").val()=="") {
		$("#passwdMsg").css("display","block");
		submitResult=false;
	} else if(!passwdReg.test($("#passwd").val())) {
		$("#passwdRegMsg").css("display","block");
		submitResult=false;
	} 
	
	if($("#repasswd").val()=="") {
		$("#repasswdMsg").css("display","block");
		submitResult=false;
	} else if($("#passwd").val()!=$("#repasswd").val()) {
		$("#repasswdMatchMsg").css("display","block");
		submitResult=false;
	}
	
	if($("#name").val()=="") {
		$("#nameMsg").css("display","block");
		submitResult=false;
	}
	
	var emailReg=/^([a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+(\.[-a-zA-Z0-9]+)+)*$/g;
	if($("#email").val()=="") {
		$("#emailMsg").css("display","block");
		submitResult=false;
	} else if(!emailReg.test($("#email").val())) {
		$("#emailRegMsg").css("display","block");
		submitResult=false;
	}

	var mobile2Reg=/\d{3,4}/;
	var mobile3Reg=/\d{4}/;
	if($("#mobile2").val()=="" || $("#mobile3").val()=="") {
		$("#mobileMsg").css("display","block");
		submitResult=false;
	} else if(!mobile2Reg.test($("#mobile2").val()) || !mobile3Reg.test($("#mobile3").val())) {
		$("#mobileRegMsg").css("display","block");
		submitResult=false;
	}
	
	if($("#zipcode").val()=="") {
		$("#zipcodeMsg").css("display","block");
		submitResult=false;
	}
	
	if($("#address1").val()=="") {
		$("#address1Msg").css("display","block");
		submitResult=false;
	}
	
	if($("#address2").val()=="") {
		$("#address2Msg").css("display","block");
		submitResult=false;
	}
	
	return submitResult;
});

$("#idCheck").click(function() {
	// Hide error messages related to ID
	$("#idMsg").css("display", "none");
	$("#idRegMsg").css("display", "none");
	
	// Validate input value (ID)
	var idReg=/^[a-zA-Z]\w{5,19}$/g;
	if($("#id").val()=="") {
		$("#idMsg").css("display","block");
		return;
	} else if(!idReg.test($("#id").val())) {
		$("#idRegMsg").css("

display","block");
		return;
	}
	
	// Open a new browser to request [/member/id_check.jsp]
	window.open("<%=request.getContextPath()%>/member/id_check.jsp?id="+$("#id").val()
		,"idCheck","width=450, height=130, left=700, top=400");
});

// Event handler for when input value (ID) changes
$("#id").change(function() {
	// Reset the ID duplication check status input tag
	$("#idCheckResult").val("0");
});
</script>
```

## MemberSignup Action-jsp 

```html
<%@page import="xyz.itwill.dao.MemberDAO"%>
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that receives member information, inserts a row into the MEMBER table, 
     and responds with a URL address to request [/member/member_login.jsp] --%>    
<%
	// Handling response for cases where the JSP document is requested abnormally
	if(request.getMethod().equals("GET")) {// Requested the JSP document using GET method
		// Provide the URL address to request the error message page (/error/error_400.jsp) in response
		// => Not directly providing the URL address to the client for redirection,
		// but passing it to the requesting JSP document (index.jsp), so direct page redirection is not possible
		// response.sendRedirect(request.getContextPath()+"/error/error_400.jsp");
		// return;
		
		// Storing the URL address of the error message page as an attribute in the request object
		request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=error&work=error_400");
		return;
	}

	// Converting and retrieving the POSTed values requested by the JSP document
	// => Changing the character encoding for the POSTed values as per the requested JSP document (index.jsp)
	// request.setCharacterEncoding("utf-8");
	
	// Retrieving and storing the POSTed values
	String id=request.getParameter("id");
	String passwd=request.getParameter("passwd");
	String name=request.getParameter("name");
	String email=request.getParameter("email");
	String mobile=request.getParameter("mobile1")+"-"+request.getParameter("mobile2")+"-"+request.getParameter("mobile3");
	String zipcode=request.getParameter("zipcode");
	String address1=request.getParameter("address1");
	String address2=request.getParameter("address2");

	// Creating a MemberDTO object and setting its fields with the retrieved values
	MemberDTO member=new MemberDTO();
	member.setMemberId(id);
	member.setMemberPasswd(passwd);
	member.setMemberName(name);
	member.setMemberEmail(email);
	member.setMemberMobile(mobile);
	member.setMemberZipcode(zipcode);
	member.setMemberAddress1(address1);
	member.setMemberAddress2(address2);
	
	// Invoking the method of MemberDAO class to insert the member information (MemberDTO object) into the MEMBER table
	MemberDAO.getDAO().insertMember(member);
	
	// Storing the URL address as an attribute in the request object to provide to the requesting JSP document (index.jsp)
	// => The requesting JSP document (index.jsp) will retrieve the URL address and perform a redirect
	request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=member&work=member_login");
%>
```
## ID duplicationCheck -jsp 

```html
<%@page import="xyz.itwill.dao.MemberDAO"%>
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that receives an ID, checks for its existence in the MEMBER table,
     and responds with duplicate status in HTML format --%>
<%-- => Non-duplicate ID: Displays a message indicating the ID is available for use,
     and clicking on [Use ID] tag will fill the parent browser's ID input tag with the ID and close the browser --%>
<%-- => Duplicate ID: Displays a message indicating the ID is unavailable for use,
     prompts for a new ID input, and requests the current executing JSP document (id_check.jsp) again with the new ID input --%>    
<%
	// Retrieving and storing the posted value
	String id=request.getParameter("id");

	if(id == null) {// If no value is received - abnormal request
		response.sendRedirect(request.getContextPath()+"/error/error_400.jsp");
		return;
	}
	
	// Invoking the method of MemberDAO class to search for a row in the MEMBER table based on the received ID
	// => Returns null: No member information found - Non-duplicate ID (ID is available for use)
	// => Returns MemberDTO object: Member information found - Duplicate ID (ID is unavailable for use)
	MemberDTO member=MemberDAO.getDAO().selectMemberById(id);
%>
```

