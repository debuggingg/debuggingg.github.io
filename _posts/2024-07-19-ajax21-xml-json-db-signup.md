---
layout: single
title: 2024/07/19 AJAX 21-XML-Json-Signup/DB
---
## AJAX 

#### xml_one.jsp(XML)

```html
<%-- JSP document that uses an Ajax engine to request [xml_two.jsp], receiving XML-formatted data as a result and responding with HTML tags --%>
<%-- XML (eXtensible Markup Language): Structured data representation format using elements (tags) to provide values --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
<style type="text/css">
#newsList {
	width: 50%;
	margin: 0 auto;
}	

#newsHeader {
	padding: 5px;
	text-align: center;
	font-size: x-large;
	border: 2px solid black;
}

#newsContents {
	padding: 5px;
	font-size: medium;
	border: 2px dashed black;
	display: none;
}
</style>
</head>
<body>
	<h1>Headline News</h1>
	<hr>
	<div id="newsList">
		<div id="newsHeader">Today's News</div>
		<div id="newsContents"></div>
	</div>
	
	<script type="text/javascript">
	document.getElementById("newsList").onmouseover=function() {
		var xhr=new XMLHttpRequest();
		
		xhr.onreadystatechange=function() {
			if(xhr.readyState == 4) {
				if(xhr.status == 200) {
					// XMLHttpRequest.responseXML: Member variable storing the response (XML) of the web program request
					var xmlDoc=xhr.responseXML;
					// Document.getElementsByTagName(tagName): Member function of Document object (Element object), searches for tags by tagName and returns a NodeList object (HTMLCollection object)
					var newsList=xmlDoc.getElementsByTagName("news");
					
					var html="<ol>";
					for(i=0;i<newsList.length;i++) {
						// NodeList.item(index): Member function returning the element value (Element object) at the index position passed as a parameter from NodeList object
						var news=newsList.item(i);
						
						var title=news.getElementsByTagName("title").item(0).firstChild.nodeValue;
						var publisher=news.getElementsByTagName("publisher").item(0).firstChild.nodeValue;
						
						html+="<li>"+title+"["+publisher+"]</li>";
					}
					html+="</ol>";
					
					document.getElementById("newsContents").innerHTML=html;
					document.getElementById("newsContents").style.display="block";
				} else {
					alert("Error code = "+xhr.status);
				}
			}
		}
		
		xhr.open("get", "xml_two.jsp");
		xhr.send(null);
	}
	
	document.getElementById("newsList").onmouseout=function() {
		document.getElementById("newsContents").style.display="none";
	}
	</script>	
</body>
</html>
```
xml_two.jsp(xml)
```xml
%-- JSP document responding with the execution result of the web program in XML format --%>
<newsList>
	<news>
		<title>Park Narae Shows Off 5.5 Billion Won Mansion... "Many Unauthorized Entries, Asking for Money"</title>
		<publisher>TenAsia</publisher>
	</news>
	<news>
		<title>Park Han Byul, Without Shame Over 'Burning Sun Husband Controversy'... Flawless Skin Even in Her 40s</title>
		<publisher>Sports Chosun</publisher>
	</news>
	<news>
		<title>53-Year-Old Ko Hyun Jung, What About Her 'Weight'?... Jenny Also Weeping Over 'Right-Angle Shoulders'</title>
		<publisher>TV Report</publisher>
	</news>
	<news>
		<title>Lim Ji Kyu, Mourns Father's Death... Guarding His Deathbed Amidst Grief</title>
		<publisher>Xports News</publisher>
	</news>
	<news>
		<title>Sending Younger Sister First... Actor Lim Ji Kyu, Receives News of Father's Death Today</title>
		<publisher>TV Report</publisher>
	</news>
</newsList>
```
---
#### Json_one.jsp(Json)

```html
<%-- JSP document that uses an Ajax engine to request [json_two.jsp], receiving data in JSON format as a result and responding with HTML tags --%>
<%-- JSON (JavaScript Object Notation): Structured data representation format using JavaScript objects to provide values --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
<style type="text/css">
#newsList {
	width: 50%;
	margin: 0 auto;
}	

#newsHeader {
	padding: 5px;
	text-align: center;
	font-size: x-large;
	border: 2px solid black;
}

#newsContents {
	padding: 5px;
	font-size: medium;
	border: 2px dashed black;
	display: none;
}
</style>
</head>
<body>
	<h1>Headline News</h1>
	<hr>
	<div id="newsList">
		<div id="newsHeader">Today's News</div>
		<div id="newsContents"></div>
	</div>
	
	<script type="text/javascript">
	document.getElementById("newsList").onmouseover=function() {
		var xhr=new XMLHttpRequest();
		
		xhr.onreadystatechange=function() {
			if(xhr.readyState == 4) {
				if(xhr.status == 200) {
					// JSON.parse(json): Member function that converts JSON formatted string received as parameter into JavaScript object and returns it
					var result=JSON.parse(xhr.responseText);
					
					var html="<ol>";
					// Iterates through elements of Array object (Object objects)
					for(i=0;i<result.length;i++) {
						// Stores member variable values from Object object
						var title=result[i].title;
						var publisher=result[i].publisher;
						
						html+="<li>"+title+"["+publisher+"]</li>";
					}
					html+="</ol>";
					
					document.getElementById("newsContents").innerHTML=html;
					document.getElementById("newsContents").style.display="block";
				} else {
					alert("Error code = "+xhr.status);
				}
			}
		}
		
		xhr.open("get", "json_two.jsp");
		xhr.send(null);
	}
	
	document.getElementById("newsList").onmouseout=function() {
		document.getElementById("newsContents").style.display="none";
	}
	</script>	
</body>
</html>
```

json_two.jsp(json)
```json
[
  {"title": "Park Narae Shows Off 5.5 Billion Won Mansion... \"Many Unauthorized Entries, Asking for Money\"", "publisher": "TenAsia"},
  {"title": "Park Han Byul, Without Shame Over 'Burning Sun Husband Controversy'... Flawless Skin Even in Her 40s", "publisher": "Sports Chosun"},
  {"title": "53-Year-Old Ko Hyun Jung, What About Her 'Weight'?... Jenny Also Weeping Over 'Right-Angle Shoulders'", "publisher": "TV Report"},
  {"title": "Lim Ji Kyu, Mourns Father's Death... Guarding His Deathbed Amidst Grief", "publisher": "Xports News"},
  {"title": "Sending Younger Sister First... Actor Lim Ji Kyu, Receives News of Father's Death Today", "publisher": "TV Report"}
]
```

---

#### Welcom_one.jsp(jquery)
```html
<%-- JSP document that uses an Ajax engine to request [welcome_two.jsp], receiving the execution result as HTML (TEXT) and responding with modified tags --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>jQuery AJAX</h1>
	<hr>
	<label for="name">Name: </label>
	<input type="text" id="name">
	<button type="button" id="btn">Submit</button>
	<hr>
	<div id="message"></div>
	
	<script type="text/javascript">
	$("#btn").click(function() {
		var name=$("#name").val();
		if(name == "") {
			$("#message").text("Please enter a name.");
			return;
		}		
		$("#name").val("");
		
		// $.ajax(settings): Member function using Ajax engine to request web program and receive response for processing
		// => Parameters are provided as elements of an Object object to set necessary values or functions for Ajax functionality
		// => $.get() or $.post() can be called to implement Ajax functionality
		/*
		$.ajax({
			type: "post", // Request method setting - defaults to GET if omitted
			url: "welcome_two.jsp", // URL address of the requested web program
			data: "name="+name, // Values to be passed to the requested web program
			dataType: "text", // Document type of the response - values: text, html, script, xml, json, etc.
			// Function to execute when receiving a successful response - status code: 200
			// => Automatically receives the response as a parameter
			success: function(result) {
				$("#message").text(result);
			},
			// Function to handle abnormal response - status code: 4XX or 5XX
			// => Automatically receives XMLHttpRequest object as a parameter
			error: function(xhr) {
				alert("Error code = "+xhr.status);
			}
		});
		*/
		
		// $.post(url[, data][, success][, dataType]): Member function using Ajax engine to request web program via POST method and receive response for processing
		$.post("welcome_two.jsp","name="+name, function(result) {
			$("#message").text(result);
		}, "text"); 
	});
	</script>
</body>
</html>
```
Welcom_two.jsp
```jsp
<%
request.setCharacterEncoding("utf-8");
String name=request.getParameter("name");
%>
welcom, <%=name%>
```
---

---
#### books_one.jsp(jquery and xml)
```html
<%-- JSP document that uses an Ajax engine to request [books_two.jsp], receiving the execution result as XML and responding with modified tags --%>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>jQuery AJAX</h1>
	<hr>
	<div id="bookList"></div>
	
	<script type="text/javascript">
	$.ajax({
		type: "get",
		url: "books_two.jsp",
		// If the dataType attribute value does not match the response document type of the requested web program, a 200 error code will occur
		dataType: "xml",
		success: function(xmlDoc) {
			//alert(xmlDoc);//[object XMLDocument]
			
			var count=$(xmlDoc).find("book").length;
			if(count == 0) {
				$("#bookList").html("<p>No books found.</p>");
				return;
			}
			
			var html="<p>There are "+count+" books found.<p>";
			html+="<ol>";
			$(xmlDoc).find("book").each(function() {
				var title=$(this).find("title").text();
				var author=$(this).find("author").text();
				html+="<li><b>"+title+"</b>["+author+"]</li>";
			});
			html+="</ol>";
			
			$("#bookList").html(html);
		},
		error: function(xhr) {
			alert("Error code = "+xhr.status);
		}
	});
	</script>
</body>
</html>
```
books_two.jsp
```jsp
<%-- title of books data responed with  XML form --%>
<books>
<book>
<title>Java</title>
<author>Paul</author>
</book>
<book>
<title>JSP web programming </title>
<author>Laura Kim</author>
</book>
<book>
<title>Spring boot </title>
<author>BBNS</author>
</book>
</books>
```
---

#### songs_one.jsp (jquery and json)
```html
<%-- JSP document that uses an Ajax engine to request [songs_two.jsp], receiving the execution result as JSON and responding with modified tags --%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>jQuery AJAX</h1>
	<hr>
	<h1>Music Chart (as of <span id="now"></span>)</h1>
	<div id="songList"></div>
	
	<script type="text/javascript">
	$.ajax({
		type: "get",
		url: "songs_two.jsp",
		dataType: "json",
		success: function(result) {
			//alert(result);//[object Object]
			
			$("#now").html(result.now);
			
			var html="<ol>";
			$(result.songs).each(function() {
				html+="<li><b>"+this.title+"</b>["+this.singer+"]</li>";
			});
			html+="</ol>";
			
			$("#songList").html(html);
		},
		error: function(xhr) {
			alert("Error code = "+xhr.status);
		} 
	});
	</script>
</body>
</html>
```

songs_two.jsp
```jsp
<%-- JSP document that responds with server platform's date and time along with a list of songs in JSON format --%>
<%
    String now = new SimpleDateFormat("yyyy년 MM월 dd일 HH시").format(new Date());
%>
{
	"now": "<%=now %>",
	"songs": [
		{"title": "Supernova", "singer": "aespa"},
		{"title": "Small girl (feat. D.O.)", "singer": "이영지"},
		{"title": "How Sweet", "singer": "NewJeans"},
		{"title": "Supernatural", "singer": "NewJeans"},
		{"title": "소나기", "singer": "이클립스 (ECLIPSE)"}
	]
}
```






---

#### xsl_one.jsp(XSL )
```html
<%-- JSP document that requests and processes [xsl_two.jsp] document using an Ajax engine, transforming the resulting XML into HTML tags --%>
<%-- => Processes XML data into HTML tags using XSL --%>
<%-- => Requests XSL document (books.xsl) using Ajax engine, receiving XSLT response for use --%>
<%-- XSL (eXtensible Stylesheet Language): Language based on XML for creating programs (Parsers) to transform XML data --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>List of Books</h1>
	<hr>
	<div id="bookList"></div>
	
	<script type="text/javascript">
	var xmlDoc=null; // Global variable to store XML data
	var xslDoc=null; // Global variable to store XSLT data
	
	$.ajax({
		type: "get",
		url: "xsl_two.jsp",
		dataType: "xml",
	//asyns : Attribute to distinguish synchronous or asynchronous communication
	// => false : synchronous communication, true : asynchronous communication - default
		async: false,
		success: function(result) {
			xmlDoc=result;
			//doXSLT();
		}, 
		error: function(xhr) {
			alert("Error code = "+xhr.status);
		}
	});
	
	$.ajax({
		type: "get",
		url: "books.xsl",
		dataType: "xml",
		success: function(result) {
			xslDoc=result;
			doXSLT();
		}, 
		error: function(xhr) {
			alert("Error code = "+xhr.status);
		}
	});
	
	// Function to transform XML data into HTML tags using XSLT data
	function doXSLT() {
		//if(xmlDoc == null || xslDoc == null) return;
		
		// Creating XSLTProcessor object capable of transforming XML data using XSLT data
		var xsltProcessor=new XSLTProcessor();
		
		// XSLTProcessor.importStylesheet(xslt): Method to pass XSLT data to XSLTProcessor object for storage
		xsltProcessor.importStylesheet(xslDoc);
		
		// XSLTProcessor.transformToFragment(xml, document): Method to receive XML data as parameter, transforming it into Element object using XSLTProcessor object and returning it
		var fragment=xsltProcessor.transformToFragment(xmlDoc, document);
		
		$("#bookList").append(fragment);
	}
	</script>
</body>
</html>
```

xsl_two.jsp
```jsp
<%-- title of books data responed with  XML form --%>
<books>
<book>
<title>Java</title>
<author>Paul</author>
</book>
<book>
<title>JSP web programming </title>
<author>Laura Kim</author>
</book>
<book>
<title>Spring boot </title>
<author>BBNS</author>
</book>
</books>
```
#### Books.xsl (XSL)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- XSL document: XML document storing XSLT data -->
<!-- XSLT (eXtensible Stylesheet Language Template): Parser providing transformation information using XSL -->
<!-- => Transforms XML data into XML or HTML documents -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!-- output: Element specifying the document format for transforming XML data -->
	<!-- method attribute: Specifies the file format for transformation - default: xml -->
	<!-- encoding attribute: Specifies the character encoding used in the transformation document - default: iso-8859-1 -->
	<xsl:output method="html" encoding="utf-8"/>
	<!-- template: Element providing transformation information -->
	<!-- match attribute: Sets the name of the top-level element in the XML document as the attribute value -->
	<xsl:template match="books">
		<ol>
			<!-- for-each: Element for iteration processing -->
			<!-- select attribute: Sets the name of the element to be iteratively processed as the attribute value -->
			<xsl:for-each select="book">
				<!-- value-of: Element for providing tag content -->
				<!-- select attribute: Sets the name of the element whose content is to be provided as the attribute value -->
				<li><b><xsl:value-of select="title"/></b>[<xsl:value-of select="author"/>]</li>
			</xsl:for-each>
		</ol>
	</xsl:template>
</xsl:stylesheet>
```

---
#### (1)member_join_input.jsp(JDBC)- Ajax type connects DB

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
.title {
	width: 100px;
	padding-right: 10px;
	text-align: center;
	font-weight: bold;
}

.input {
	width: 200px;
}

.message {
	width: 500px;
	color: red;
}

.msg {
	display: none;
}

#btn {
	text-align: center;
}
</style>
</head>
<body>
	<h1>Sign Up</h1>
	<hr>
	<form action="<%=request.getContextPath()%>/jdbc/member_join_action.jsp" method="post" id="joinForm">
	<table>
		<tr>
			<td class="title">ID</td>
			<td class="input"><input type="text" name="id" id="id"></td>
			<td class="message">
				<span id="idNullMsg" class="msg idMsg">Please enter your ID.</span>
				<span id="idValidMsg" class="msg idMsg">Please enter a valid ID format.</span>
				<span id="idDuplMsg" class="msg idMsg">This ID is already taken.</span>
			</td>
		</tr>
		<tr>
			<td class="title">Password</td>
			<td class="input"><input type="password" name="passwd" id="passwd"></td>
			<td class="message">
				<span id="passwdNullMsg" class="msg">Please enter your password.</span>
				<span id="passwdValidMsg" class="msg">Please enter a valid password format.</span>
			</td>
		</tr>
		<tr>
			<td class="title">Name</td>
			<td class="input"><input type="text" name="name" id="name"></td>
			<td class="message">
				<span id="nameNullMsg" class="msg">Please enter your name.</span>
				<span id="nameValidMsg" class="msg">Please enter a valid name format.</span>
			</td>
		</tr>
		<tr>
			<td class="title">Email</td>
			<td class="input"><input type="text" name="email" id="email"></td>
			<td class="message">
				<span id="emailNullMsg" class="msg">Please enter your email.</span>
				<span id="emailValidMsg" class="msg">Please enter a valid email format.</span>
			</td>
		</tr>
		<tr>
			<td colspan="2" id="btn"><button type="submit">Sign Up</button></td> 
		</tr>
	</table>	
	</form>

	<script type="text/javascript">
	$("#id").focus();
	
	$("#joinForm").submit(function() {
		$(".msg").hide();
		
		var validResult=true;
		
		var id=$("#id").val();
		var idReg=/^[a-zA-Z]\w{5,19}$/g;
		if(id=="") {
			$("#idNullMsg").show();
			validResult=false;
		} else if(!idReg.test(id)) {
			$("#idValidMsg").show();
			validResult=false;
		}
		
		var passwd=$("#passwd").val();
		var passwdReg=/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*_-]).{6,20}$/g;
		if(passwd=="") {
			$("#passwdNullMsg").show();
			validResult=false;
		} else if(!passwdReg.test(passwd)) {
			$("#passwdValidMsg").show();
			validResult=false;
		}
		
		var name=$("#name").val();
		var nameReg=/^[가-힣]{2,10}$/g;
		if(name=="") {
			$("#nameNullMsg").show();
			validResult=false;
		} else if(!nameReg.test(name)) {
			$("#nameValidMsg").show();
			validResult=false;
		}
		
		var email=$("#email").val();
		var emailReg=/^([a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+(\.[-a-zA-Z0-9]+)+)*$/g;
		if(email=="") {
			$("#emailNullMsg").show();
			validResult=false;
		} else if(!emailReg.test(email)) {
			$("#emailValidMsg").show();
			validResult=false;
		}
		
		return validResult;		
	});
	
	</script>
</body>
</html>
```

#### (2)member_id_check.jsp 

```xml
<?xml version="1.0" encoding="utf-8"?>
<%@page import="xyz.itwill.dao.AjaxMemberDAO"%>
<%@page import="xyz.itwill.dto.AjaxMemberDTO"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- This JSP document checks the availability of an ID by searching for a row in the AJAX_MEMBER table and responds with XML data. --%>    
<%
	String id=request.getParameter("id");

	if(id == null || id.equals("")) {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	AjaxMemberDTO ajaxMember=AjaxMemberDAO.getDAO().selectAjaxMember(id);
%>
<result>
	<% if(ajaxMember != null) {// If member information is found for the ID, it indicates the ID is already taken (ID unavailable). %>
	<code>impossible</code>
	<% } else {// If no member information is found for the ID, it indicates the ID is available. %>
	<code>possible</code>
	<% } %>
</result>
```

#### AjaxMemberDTO (DTO)
```java
//create table ajax_member(id varchar2(30) primary key, passwd varchar2(100)
// , name varchar2(50), email varchar2(100));
public class AjaxMemberDTO {
private String id;
private String passwd;
private String name;
private String email;
public AjaxMemberDTO() {
}
public String getId() {
return id;
}
public void setId(String id) {
this.id = id;
}
public String getPasswd() {
return passwd;
}
public void setPasswd(String passwd) {
this.passwd = passwd;
}
public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}
public String getEmail() {
return email;
}
public void setEmail(String email) {
this.email = email;
}
```

#### AjaxMemberDAO
```java
public class AjaxMemberDAO extends JdbcDAO {
	private static AjaxMemberDAO _dao;
	
	private AjaxMemberDAO() {
		// TODO Auto-generated constructor stub
	}
	
	static {
		_dao = new AjaxMemberDAO();		
	}
	
	public static AjaxMemberDAO getDAO() {
		return _dao;
	}
	
	// Method to insert a member information (AjaxMemberDTO object) into the AJAX_MEMBER table
	// and return the number of rows inserted (int).
	public int insertAjaxMember(AjaxMemberDTO ajaxMember) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			con = getConnection();
			
			String sql = "insert into ajax_member values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ajaxMember.getId());
			pstmt.setString(2, ajaxMember.getPasswd());
			pstmt.setString(3, ajaxMember.getName());
			pstmt.setString(4, ajaxMember.getEmail());
			
			rows = pstmt.executeUpdate();			
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in insertAjaxMember() method: " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	// Method to search for a row in the AJAX_MEMBER table based on the ID (String object)
	// and return member information (AjaxMemberDTO object).
	public AjaxMemberDTO selectAjaxMember(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AjaxMemberDTO ajaxMember = null;
		try {
			con = getConnection();
			
			String sql = "select id, passwd, name, email from ajax_member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				ajaxMember = new AjaxMemberDTO();
				ajaxMember.setId(rs.getString("id"));
				ajaxMember.setPasswd(rs.getString("passwd"));
				ajaxMember.setName(rs.getString("name"));
				ajaxMember.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in selectAjaxMember() method: " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return ajaxMember;
	}
}
```

#### (3) member_join_action.jsp
```jsp
<%@page import="xyz.itwill.dao.AjaxMemberDAO"%>
<%@page import="xyz.itwill.dto.AjaxMemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- This JSP document receives member information and inserts a row into the AJAX_MEMBER table. It responds with the URL address to request [member_join_result.jsp]. --%>    
<%
	if(request.getMethod().equals("GET")) {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}

	request.setCharacterEncoding("utf-8");
	
	String id=request.getParameter("id");
	String passwd=request.getParameter("passwd");
	String name=request.getParameter("name");
	String email=request.getParameter("email");
	
	AjaxMemberDTO ajaxMember=new AjaxMemberDTO();
	ajaxMember.setId(id);
	ajaxMember.setPasswd(passwd);
	ajaxMember.setName(name);
	ajaxMember.setEmail(email);
	
	AjaxMemberDAO.getDAO().insertAjaxMember(ajaxMember);
	
	response.sendRedirect(request.getContextPath()+"/jdbc/member_join_result.jsp");
%>
```
#### (4)member_join_result.jsp
```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
</head>
<body>
<h1> Singup success</h1>
<hr>
<p>welcom .</p>
</body>
</html>
```
