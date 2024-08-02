---
layout: single
title: 2024/07/23 AJAX 22-Search ID-PW
---

#### Comment
#### Comment folder - Created

#### (1)comment.jsp
```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that requests and receives the execution result (comment list) from [comment_list.jsp] using Ajax engine --%>
<%-- => If the [comment registration] tag is clicked, requests [comment_add.jsp] using the Ajax engine to insert the comment and receive the execution result for output processing - passing input value (comment) --%>    
<html>
<head>
<meta charset="UTF-8">
<title>AJAX</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<style type="text/css">
h1 {
	text-align: center;
}

.comment_table {
	width: 500px;
	margin: 0 auto;
	border: 2px solid #cccccc;
	border-collapse: collapse;
}

.title {
	width: 100px;
	padding: 5px 10px;
	text-align: center;
	border: 1px solid #cccccc;
}

.input {
	width: 400px;
	padding: 5px 10px;
	border: 1px solid #cccccc;
}

.btn {
	text-align: center;
	border: 1px solid #cccccc;
}

#comment_add {
	margin-bottom: 5px;
}

#comment_modify, #comment_remove {
	margin: 10px;
	display: none;
}

#add_message, #modify_message {
	width: 500px;
	margin: 0 auto;
	margin-bottom: 20px;
	text-align: center;
	color: red;
}

#remove_message {
	padding: 3px;
	text-align: center;
	border: 1px solid #cccccc;
}

.comment {
	width: 550px;
	margin: 0 auto;
	margin-bottom: 5px;
	padding: 3px;
	border: 1px solid #cccccc;
}

.no_comment {
	width: 550px;
	margin: 0 auto;
	margin-bottom: 5px;
	border: 2px solid #cccccc;
	text-align: center;
}
</style>
</head>
<body>
	<h1>AJAX Comments</h1>
	<hr>
	<%-- Comment registration tag --%>
	<div id="comment_add">
		<table class="comment_table">
			<tr>
				<td class="title">Writer</td>
				<td class="input"><input type="text" id="add_writer"></td>
			</tr>
			<tr>
				<td class="title">Content</td>
				<td class="input">
					<textarea rows="3" cols="50" id="add_content"></textarea>
				</td>
			</tr>
			<tr>
				<td class="btn" colspan="2">
					<button type="button" id="add_btn">Add Comment</button>
				</td>
			</tr>
		</table>
		<div id="add_message">&nbsp;</div>
	</div>

	<%-- Comment list tag --%>
	<div id="comment_list"></div>

	<%-- Comment modification tag --%>
	<div id="comment_modify">
		<input type="hidden" id="modify_num">
		<table class="comment_table">
			<tr>
				<td class="title">Writer</td>
				<td class="input"><input type="text" id="modify_writer"></td>
			</tr>
			<tr>
				<td class="title">Content</td>
				<td class="input">
					<textarea rows="3" cols="50" id="modify_content"></textarea>
				</td>
			</tr>
			<tr>
				<td class="btn" colspan="2">
					<button type="button" id="modify_btn">Modify</button>
					<button type="button" id="modify_cancel_btn">Cancel</button>
				</td>
			</tr>
		</table>
		<div id="modify_message">&nbsp;</div>
	</div>

	<%-- Comment removal tag --%>
	<div id="comment_remove">
		<input type="hidden" id="remove_num">
		<div id="remove_message">
			<b>Are you sure you want to delete this comment?</b>
			<button type="button" id="remove_btn">Delete</button>
			<button type="button" id="remove_cancel_btn">Cancel</button>
		</div>
	</div>

	<script type="text/javascript">
	displayComment();
	
	// Requests [comment_list.jsp] document using Ajax engine to receive the execution result (comment list) as JSON and convert it to HTML tags to update the content of the comment list tag function
	function displayComment() {
		$.ajax({
			type: "get",
			url: "<%=request.getContextPath()%>/comment/comment_list.jsp",
			dataType: "json",
			success: function(result) {
				// Deletes child tags (comments) of the comment list tag - deletes existing comments
				$("#comment_list").children().remove();
				
				if(result.code == "success") {// If comment information is found
					// Calls each() member function of Array object for batch processing
					$(result.data).each(function() {
						// Converts elements (comment information - Object object) of Array object to HTML tags
						var html="<div class='comment' id='comment_"+this.num+"'>";// Comment tag
						html+="<b>["+this.writer+"]</b><br>";// Includes writer in comment tag
						html+=this.content.replace(/\n/g,"<br>")+"<br>";// Includes comment content in comment tag
						html+="("+this.regdate+")<br>";// Includes creation date in comment tag
						html+="<button type='button'>Modify Comment</button>&nbsp;";// Includes modify comment button in comment tag
						html+="<button type='button'>Delete Comment</button>&nbsp;";// Includes delete comment button in comment tag
						html+="</div>";
						
						// Adds comment tag to the comment list tag as the last child tag for display processing 
						$("#comment_list").append(html);
					});
				} else {// If no comment information is found
					$("#comment_list").html("<div class='no_comment'>"+result.message+"</div>");
				}
			}, 
			error: function(xhr) {
				alert("Error Code = "+xhr.status);
			}
		});	
	}
	
	// Event handling function registered when [Add Comment] tag is clicked
	// => Requests [comment_add.jsp] document using Ajax engine to insert the comment and receive the execution result as JSON - passes input values (writer and content) when requesting [comment_add.jsp]
	$("#add_btn").click(function() {
		var writer=$("#add_writer").val();
		if(writer == "") {
			$("#add_message").html("Please enter the writer.");
			$("#add_writer").focus();
			return;
		}
		
		var content=$("#add_content").val();
		if(content == "") {
			$("#add_message").html("Please enter the content.");
			$("#add_content").focus();
			return;
		}
		
		$("#add_writer").val("");
		$("#add_content").val("");
		$("#add_message").html("");
		
		$.ajax({
			type: "post",
			url: "<%=request.getContextPath()%>/comment/comment_add.jsp",
			data: {"writer":writer, "content":content},
			dataType: "json",
			success: function(result) {
				if(result.code == "success") {
					displayComment();// Display comment list
				} else {
					alert("Failed to insert comment.");
				}
			},
			error: function(xhr) {
				alert("Error Code = "+xhr.status);
			}
		});
	});
	</script>	
</body>
</html>
```
ajax_Comment table 
```d
create table ajax_comment(num number primary key, writer varchar2(50) , content varchar2(500), regdate date);
create sequence ajax_comment_seq;
```

#### AjaxCommentDTO.java AjaxCommentDAO.java
```java
public class AjaxCommentDAO extends JdbcDAO {
	private static AjaxCommentDAO _dao;
	
	private AjaxCommentDAO() {
		// TODO: Auto-generated constructor stub
	}
	
	static {
		_dao = new AjaxCommentDAO();
	}
	
	public static AjaxCommentDAO getDAO() {
		return _dao;
	}
	
	// Method that receives AjaxCommentDTO object (comment information) and inserts it into the AJAX_COMMENT table, returning the number of inserted rows (int)
	public int insertAjaxComment(AjaxCommentDTO ajaxComment) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			con = getConnection();
			
			String sql = "insert into ajax_comment values(ajax_comment_seq.nextval,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ajaxComment.getWriter());
			pstmt.setString(2, ajaxComment.getContent());
			
			rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in insertAjaxComment() method = " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	// Method that receives AjaxCommentDTO object (comment information) and updates the row in the AJAX_COMMENT table, returning the number of updated rows (int)
	public int updateAjaxComment(AjaxCommentDTO ajaxComment) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			con = getConnection();
			
			String sql = "update ajax_comment set writer=?,content=? where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ajaxComment.getWriter());
			pstmt.setString(2, ajaxComment.getContent());
			pstmt.setInt(3, ajaxComment.getNum());
			
			rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in updateAjaxComment() method = " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	// Method that receives an integer comment number (num), deletes the corresponding row from the AJAX_COMMENT table, and returns the number of deleted rows (int)
	public int deleteAjaxComment(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			con = getConnection();
			
			String sql = "delete from ajax_comment where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in deleteAjaxComment() method = " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	// Method that receives an integer comment number (num), searches for the corresponding row in the AJAX_COMMENT table, and returns the comment information (AjaxCommentDTO object)
	public AjaxCommentDTO selectAjaxComment(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AjaxCommentDTO ajaxComment = null;
		try {
			con = getConnection();
			
			String sql = "select num,writer,content,regdate from ajax_comment where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				ajaxComment = new AjaxCommentDTO();
				ajaxComment.setNum(rs.getInt("num"));
				ajaxComment.setWriter(rs.getString("writer"));
				ajaxComment.setContent(rs.getString("content"));
				ajaxComment.setRegdate(rs.getString("regdate"));
			}
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in selectAjaxComment() method = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return ajaxComment;
	}
	
	// Method that retrieves all rows from the AJAX_COMMENT table, returning a list of comments (List<AjaxCommentDTO> object)
	public List<AjaxCommentDTO> selectAjaxCommentList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AjaxCommentDTO> ajaxCommentList = new ArrayList<AjaxCommentDTO>();
		try {
			con = getConnection();
			
			String sql = "select num,writer,content,regdate from ajax_comment order by num desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				AjaxCommentDTO ajaxComment = new AjaxCommentDTO();
				ajaxComment.setNum(rs.getInt("num"));
				ajaxComment.setWriter(rs.getString("writer"));
				ajaxComment.setContent(rs.getString("content"));
				ajaxComment.setRegdate(rs.getString("regdate"));
				ajaxCommentList.add(ajaxComment);
			}
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in selectAjaxCommentList() method = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return ajaxCommentList;
	}
}
```

#### comment_list.jsp 

```jsp
<%@page import="xyz.itwill.util.Utility"%>
<%@page import="xyz.itwill.dao.AjaxCommentDAO"%>
<%@page import="xyz.itwill.dto.AjaxCommentDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that searches all rows in the AJAX_COMMENT table and responds with JSON --%>    
<%
	List<AjaxCommentDTO> ajaxCommentList = AjaxCommentDAO.getDAO().selectAjaxCommentList();
%>
<% if(ajaxCommentList.isEmpty()) { %>
{"code":"empty", "message":"Please enter your first comment."}
<% } else { %>
{
	"code":"success",
	"data":[
	<% for(int i=0; i<ajaxCommentList.size(); i++) { %>
		<% if(i > 0 ) { %>,<% } %>
		{"num":<%=ajaxCommentList.get(i).getNum()%>,
		 "writer":"<%=Utility.toJSON(ajaxCommentList.get(i).getWriter()) %>",
		 "content":"<%=Utility.toJSON(ajaxCommentList.get(i).getContent()) %>",
		 "regdate":"<%=ajaxCommentList.get(i).getRegdate() %>"}
	<% } %>	
	]
}
<% } %>
```
#### comment_add.jsp
```jsp
<%@page import="xyz.itwill.dao.AjaxCommentDAO"%>
<%@page import="xyz.itwill.dto.AjaxCommentDTO"%>
<%@page import="xyz.itwill.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that inserts comment information (writer and content) into the AJAX_COMMENT table
     and responds with the execution result in JSON format --%>    
<%
	if(request.getMethod().equals("GET")) {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}

	request.setCharacterEncoding("utf-8");
	
	String writer = Utility.escapeTag(request.getParameter("writer"));
	String content = Utility.escapeTag(request.getParameter("content"));

	AjaxCommentDTO ajaxComment = new AjaxCommentDTO();
	ajaxComment.setWriter(writer);
	ajaxComment.setContent(content);
	
	int rows = AjaxCommentDAO.getDAO().insertAjaxComment(ajaxComment);
%>
<% if(rows > 0) { %>
{"code":"success"}
<% } else { %>
{"code":"error"}
<% } %>
```
#### Utility.java - add. public static String toJSON(String source){}
```java
package xyz.itwill.util;

// Class providing functionalities for web program development
public class Utility {
	// Static method that receives a string and replaces tag-related characters (< or >) with escape characters, then returns the result
	public static String escapeTag(String source) {
		return source.replace("<", "&lt;").replace(">", "&gt;");
	}
	
	// Static method that receives a string and replaces characters that cannot be represented in JSON format with escape characters, then returns the result
	public static String toJSON(String source) {
		return source.replace("\\", "\\\\").replace("\"", "\\\"")
					.replace("\n", "\\n").replace("\r\n", "\\n");
	}
}
```



## add for comment modify 

#### add comment.jsp -  add button tag (click) and function()
```
html+="<button type='button'click="modifyComment("+this.num+")">reply modify </button>&nbsp;"; 
```

## create comment_get.jsp 
= 여기까진 글을 modify 를 누르면 글을 쓸수 있는 화면이 나오는기능 

comment.jsp이리로 와서 다시 click modify 버튼을 눌렀을때 삽인 하는 기능을 위한 코드 추가
#### create comment_modify.jsp => very similar as comment_add.jsp

