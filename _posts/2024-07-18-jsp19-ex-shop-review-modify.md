---
layout: single
title: 2024/07/18 JSP 19-EX-Shop-Review-modify-remove
---

## modify 

## review_modify.jsp
```jsp
<%@page import="xyz.itwill.dao.ReviewDAO"%>
<%@page import="xyz.itwill.dto.ReviewDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document to input changes to a post --%>
<%-- => Retrieves a row from the REVIEW table based on the post number to initialize input tag values --%>
<%-- => Accessible only to logged-in users who are either the post author or an administrator --%>
<%-- => Clicking [Modify Post] redirects to [/review/review_modify_action.jsp] with input values --%>

<%@include file="/security/login_check.jspf" %>
<%
	// Handles abnormal requests to this JSP document
	if(request.getParameter("reviewNum") == null) { // If parameter is missing
		request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
		return;
	}

	// Retrieves and stores passed parameters
	int reviewNum = Integer.parseInt(request.getParameter("reviewNum"));
	String pageNum = request.getParameter("pageNum");
	String pageSize = request.getParameter("pageSize");
	String search = request.getParameter("search");
	String keyword = request.getParameter("keyword");
	
	// Retrieves a row from the REVIEW table based on the post number and returns it as a ReviewDTO object
	ReviewDTO review = ReviewDAO.getDAO().selectReviewByNum(reviewNum);
	
	// Handles abnormal requests to this JSP document
	if(review == null) { // If the retrieved post does not exist
		request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
		return;
	}
	
	// Handles abnormal requests to this JSP document
	// => If the logged-in user is neither the post author nor an administrator
	if(loginMember.getMemberNum() != review.getReviewMemberNum() && loginMember.getMemberAuth() != 9) {
		request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
		return;
	}
%>
<style type="text/css">
table {
	margin: 0 auto;
}

th {
	width: 100px;
}

td {
	text-align: left;
}
</style>
<h1>Modify Post</h1>

<%-- Sets enctype attribute to [multipart/form-data] to handle file inputs --%>
<form action="<%=request.getContextPath()%>/index.jsp?workgroup=review&work=review_modify_action"
	method="post" enctype="multipart/form-data" id="reviewForm">
	<input type="hidden" name="reviewNum" value="<%=reviewNum%>">	
	<input type="hidden" name="pageNum" value="<%=pageNum%>">	
	<input type="hidden" name="pageSize" value="<%=pageSize%>">	
	<input type="hidden" name="search" value="<%=search%>">	
	<input type="hidden" name="keyword" value="<%=keyword%>">	
	<table>
		<tr>
			<th>Title</th>
			<td>
				<input type="text" name="reviewSubject" 
					id="reviewSubject" size="40" value="<%=review.getReviewSubject()%>">
				<input type="checkbox" name="reviewStatus" 
					value="2" <% if(review.getReviewStatus() == 2) { %>checked<% } %>>Private
			</td>	
		</tr>
		<tr>
			<th>Content</th>
			<td>
				<textarea rows="7" cols="60" name="reviewContent" 
					id="reviewContent"><%=review.getReviewContent() %></textarea>
			</td>
		</tr>
		<tr>
			<th>Image File</th>
			<td>
				<input type="file" name="reviewImage"><br>
				<% if(review.getReviewImage() != null) { %>
					<div style="color: red;">Please upload a new file only if you wish to change the image.</div>
					<img src="<%=request.getContextPath()%>/review_images/<%=review.getReviewImage()%>" width="200">
				<% } %>
		
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<button type="submit">Modify Post</button>
				<button type="reset" id="resetBtn">Reset</button>
			</th>
		</tr>
	</table>
</form>
<div id="message" style="color: red;"></div>

<script type="text/javascript">
$("#reviewSubject").focus();

$("#reviewForm").submit(function() {
	if($("#reviewSubject").val() == "") {
		$("#message").text("Please enter a title.");
		$("#reviewSubject").focus();
		return false;
	}
	
	if($("#reviewContent").val() == "") {
		$("#message").text("Please enter content.");
		$("#reviewContent").focus();
		return false;
	}
});

$("#resetBtn").click(function() {
	$("#reviewSubject").focus();
	$("#message").text("");
});
</script>
```

#### review_modify_action
```jsp
<%@page import="xyz.itwill.dto.ReviewDTO"%>
<%@page import="java.io.File"%>
<%@page import="xyz.itwill.dao.ReviewDAO"%>
<%@page import="xyz.itwill.util.Utility"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document to receive and update a post (modified post) in the REVIEW table and respond with the URL for [/review/review_detail.jsp] --%>
<%-- => Passes post number and paging-related values for query processing --%>    
<%-- => Accessible only to logged-in users who are either the post author or an administrator --%>
<%@include file="/security/login_check.jspf" %>
<%
	// Handles abnormal requests to this JSP document
	if(request.getMethod().equals("GET")) { // If JSP document is requested with GET method
		request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
		return;
	}

	// Retrieves the server filesystem path to store files
	String saveDirectory = request.getServletContext().getRealPath("/review_images");
	
	// Creates MultipartRequest object - Automatically handles all uploaded files to the server directory
	MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory
			, 20*1024*1024, "utf-8", new DefaultFileRenamePolicy());
	
	
	// Retrieves and stores passed parameters
	int reviewNum = Integer.parseInt(multipartRequest.getParameter("reviewNum"));
	String pageNum = multipartRequest.getParameter("pageNum");
	String pageSize = multipartRequest.getParameter("pageSize");
	String search = multipartRequest.getParameter("search");
	String keyword = multipartRequest.getParameter("keyword");

	String reviewSubject = Utility.escapeTag(multipartRequest.getParameter("reviewSubject"));
	int reviewStatus = 1; // Default to normal post if parameter is missing
	if(multipartRequest.getParameter("reviewStatus") != null) { // If parameter exists, set to private post
		reviewStatus = Integer.parseInt(multipartRequest.getParameter("reviewStatus"));
	}
	String reviewContent = Utility.escapeTag(multipartRequest.getParameter("reviewContent"));
	
	// Retrieves the filename of the uploaded file - Returns null if no file is uploaded
	String reviewImage = multipartRequest.getFilesystemName("reviewImage");
	
	// Creates a ReviewDTO object and sets its fields with received values (parameters)
	ReviewDTO review = new ReviewDTO();
	review.setReviewNum(reviewNum);
	review.setReviewSubject(reviewSubject);
	review.setReviewContent(reviewContent);
	
	// If there's an uploaded file, set the reviewImage field and delete the old image file
	String removeReviewImage = ReviewDAO.getDAO().selectReviewByNum(reviewNum).getReviewImage();
	if(reviewImage != null) {
		review.setReviewImage(reviewImage);
		// Retrieves the name of the old image file from the REVIEW table based on the post number
		// Deletes the file using the delete() method on a File object representing the image file in the server directory
		new File(saveDirectory, removeReviewImage).delete();
	} else {
		review.setReviewImage(removeReviewImage);
	}
	review.setReviewStatus(reviewStatus);

	// Updates the row in the REVIEW table with the received ReviewDTO object
	ReviewDAO.getDAO().updateReview(review);
	
	// Redirects to a new page - Passes post number and paging-related values for query processing
	request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=review&work=review_detail"
		+ "&reviewNum=" + reviewNum + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&search=" + search + "&keyword=" + keyword);
%>
```
#### updateReview DAO 생성 
```java
	public int updateReview(ReviewDTO review) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int rows=0;
		try {
			con=getConnection();
			
			String sql="update review set review_subject=?,review_content=?,review_image=?"
					+ ",review_status=?,review_update_date=sysdate where review_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, review.getReviewSubject());
			pstmt.setString(2, review.getReviewContent());
			pstmt.setString(3, review.getReviewImage());
			pstmt.setInt(4, review.getReviewStatus());
			pstmt.setInt(5, review.getReviewNum());
			
			rows=pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[에러]updateReview() 메소드의 SQL 오류 = "+e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
```
#### review_remove_action.jsp - delete


```jsp
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.File"%>
<%@page import="xyz.itwill.dao.ReviewDAO"%>
<%@page import="xyz.itwill.dto.ReviewDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document to change the status of a post in the REVIEW table to [0] and delete it, responding with the URL for [/review/review_list.jsp] --%>
<%-- => Passes paging-related values for query processing --%>    
<%-- => Accessible only to logged-in users who are either the post author or an administrator --%>
<%@include file="/security/login_check.jspf" %>
<%
	// Handles abnormal requests to this JSP document
	if(request.getParameter("reviewNum") == null) { // If parameter is missing
		request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
		return;
	}
	
	// Retrieves and stores passed parameters
	int reviewNum = Integer.parseInt(request.getParameter("reviewNum"));
	String pageNum = request.getParameter("pageNum");
	String pageSize = request.getParameter("pageSize");
	String search = request.getParameter("search");
	String keyword = URLEncoder.encode(request.getParameter("keyword"),"utf-8");
	
	// Retrieves a row from the REVIEW table based on the post number and converts it to a ReviewDTO object
	ReviewDTO review = ReviewDAO.getDAO().selectReviewByNum(reviewNum);
	
	// Handles abnormal requests to this JSP document
	if(review == null) { // If no matching post is found
		request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
		return;
	}
	
	// Handles abnormal requests to this JSP document
	// => Accessible only to logged-in users who are not the post author or an administrator
	if(loginMember.getMemberNum() != review.getReviewMemberNum() && loginMember.getMemberAuth() != 9) {
		request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=error&work=error_400");
		return;
	}	
	
	// Sets the review status of the found post (ReviewDTO object) to [0] for deletion
	review.setReviewStatus(0);
	
	// Updates the row in the REVIEW table with the received ReviewDTO object and retrieves the number of updated rows
	ReviewDAO.getDAO().updateReview(review);
	
	// If there is an image file associated with the found post, deletes it
	if(review.getReviewImage() != null) {
		String saveDirectory = request.getServletContext().getRealPath("/review_images");
		new File(saveDirectory, review.getReviewImage()).delete();
	}
	
	// Redirects to a new page - Passes paging-related values for query processing
	request.setAttribute("returnUrl", request.getContextPath() + "/index.jsp?workgroup=review&work=review_list"
		+ "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&search=" + search + "&keyword=" + keyword);
%>
```