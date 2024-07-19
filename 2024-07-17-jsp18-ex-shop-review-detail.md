---
layout: single
title: 2024/07/17 JSP 17-EX-Shop-Review-Detail(Contents)
---
## Reviw

#### review_detail.jsp -> reviewContent 
#### program flow 

Retrieves a single row from the REVIEW table based on the received post number and responds with HTML tags.
 => Passes paging and search-related values when requesting the JSP document.
-  When clicking the [Edit Post] tag, requests [/review/review_modify.jsp] for page navigation:
	- Passes post number, page number, number of posts, search target, and search keyword. 
 -  When clicking the [Delete Post] tag, requests [/review/review_remove_action.jsp] for page navigation:
	- Passes post number, page number, number of posts, search target, and search keyword.
 -  When clicking the [Reply] tag, requests [/review/review_write.jsp] for page navigation:
	- Passes post group, post order, post depth, page number, number of posts, search target, and search keyword.
-  When clicking the [Post List] tag, requests [/review/review_list.jsp] for page navigation:
	- Passes page number, number of posts, search target, and search keyword.
 => The [Edit Post] and [Delete Post] tags are displayed only to the post author or administrators,
while the [Reply] tag is displayed only to logged-in users. 




#### review_detail.jsp

```jsp
<%@page import="xyz.itwill.dto.MemberDTO"%>
<%@page import="xyz.itwill.dao.ReviewDAO"%>
<%@page import="xyz.itwill.dto.ReviewDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Retrieves a row from the REVIEW table based on the post number and responds with HTML tags in a JSP document --%>
<%-- => Passes paging and search-related values when requesting the JSP document --%>
<%-- => Clicking [Modify] redirects to [/review/review_modify.jsp] with post number, page number, post count, search target, and keyword --%>
<%-- => Clicking [Delete] redirects to [/review/review_remove_action.jsp] with post number, page number, post count, search target, and keyword --%>
<%-- => Clicking [Reply] redirects to [/review/review_write.jsp] with post group, post sequence, post depth, page number, post count, search target, and keyword --%>
<%-- => Clicking [List] redirects to [/review/review_list.jsp] with page number, post count, search target, and keyword --%>
<%-- => [Modify] and [Delete] tags are displayed only to the post author or administrator --%>
<%-- => [Reply] tag is displayed only to logged-in users --%>
<%
	// Responds to abnormal requests to the JSP document
	if(request.getParameter("reviewNum") == null) {
		request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=error&work=error_400");
		return;
	}

	// Retrieves and stores passed values
	int reviewNum = Integer.parseInt(request.getParameter("reviewNum"));
	String pageNum = request.getParameter("pageNum");
	String pageSize = request.getParameter("pageSize");
	String search = request.getParameter("search");
	String keyword = request.getParameter("keyword");
	
	// Calls the method of ReviewDAO class that retrieves a row from the REVIEW table based on the post number and returns it as a ReviewDTO object
	ReviewDTO review = ReviewDAO.getDAO().selectReviewByNum(reviewNum);
	
	// Responds to abnormal requests to the JSP document
	if(review == null) {
		request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=error&work=error_400");
		return;
	}
	
	// Retrieves authorization-related information stored in the session attributes as an object
	// => Provides authorization only if the post is private and the logged-in user is the post author or an administrator
	// => [Modify] and [Delete] functionalities are provided only to the post author or administrator
	MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
	
	// Responds to abnormal requests to the JSP document
	if(review.getReviewStatus() == 2) {
		// Displays IP address of the client who wrote the post if the post is private and the logged-in user is neither the post author nor an administrator
		if(loginMember == null || loginMember.getMemberNum() != review.getReviewMemberNum()
			&& loginMember.getMemberAuth() != 9) {
			request.setAttribute("returnUrl", request.getContextPath()+"/index.jsp?workgroup=error&work=error_400");
			return;
		}
	}
	
	// Increases the view count of the post stored in the REVIEW table based on the post number and returns the number of affected rows
	ReviewDAO.getDAO().updateReviewCount(reviewNum);
%>
<style type="text/css">
#review_detail {
	width: 500px;
	margin: 0 auto;
}

table {
	border: 1px solid black;
	border-collapse: collapse;
}

th, td {
	border: 1px solid black;
	padding: 5px;	
}

th {
	width: 100px;
	background: black;
	color: white;
}

td {
	width: 400px;
}

.subject, .content {
	text-align: left;
}

.content {
	height: 300px;
	vertical-align: middle;
}

#review_menu {
	text-align: right;
	margin: 5px;
}
</style>

<div id="review_detail">
	<h1>Product Review</h1>
	
	<%-- Displays the retrieved post --%>
	<table>
		<tr>
			<th>Author</th>
			<td>
				<%=review.getMemberName() %>
				<%-- Displays the IP address of the client who wrote the post if the logged-in user is an administrator --%>	
				<% if(loginMember != null && loginMember.getMemberAuth() == 9) { %>
					[<%=review.getReviewIp() %>]
				<% } %>
			</td>
		</tr>
		<tr>
			<th>Date</th>
			<td><%=review.getReviewRegisterDate() %></td>
		</tr>
		<tr>
			<th>Views</th>
			<td><%=review.getReviewCount()+1 %></td>
		</tr>
		<tr>
			<th>Title</th>
			<td>
				<%-- Displays "[Private]" if the post is private --%>			
				<% if(review.getReviewStatus() == 2) { %>[Private]<% } %>
				<%=review.getReviewSubject() %>
			</td>
		</tr>
		<tr>
			<th>Content</th>
			<td>
				<%=review.getReviewContent().replace("\n", "<br>") %>
				<br>
				<% if(review.getReviewImage() != null) { %>
					<img src="<%=request.getContextPath()%>/review_images/<%=review.getReviewImage()%>" width="200">
				<% } %>
			</td>
		</tr>
	</table>
	
	<%-- Displays links --%>
	<div id="review_menu">
		<%-- Displays [Modify] and [Delete] tags if the logged-in user is the post author or administrator --%>
		<% if(loginMember !=null && (loginMember.getMemberNum() == review.getReviewMemberNum()
			|| loginMember.getMemberAuth() == 9)) { %>
			<button type="button" id="modifyBtn">Modify</button>
			<button type="button" id="removeBtn">Delete</button>
		<% } %>
			
		<%-- Displays [Reply] tag if the user is logged in --%>
		<% if(loginMember !=null) { %>
			<button type="button" id="replyBtn">Reply</button>
		<% } %>
		
		<button type="button" id="listBtn">List</button>
	</div>
</div>

<script type="text/javascript">
$("#modifyBtn").click(function() {
	location.href="<%=request.getContextPath()%>/index.jsp?workgroup=review&work=review_modify"
		+"&reviewNum=<%=review.getReviewNum()%>&pageNum=<%=pageNum%>&pageSize=<%=pageSize%>"
		+"&search=<%=search%>&keyword=<%=keyword%>";
});

$("#removeBtn").click(function() {
	if(confirm("Are you sure you want to delete this post?")) {
		location.href="<%=request.getContextPath()%>/index.jsp?workgroup=review&work=review_remove_action"
			+"&reviewNum=<%=review.getReviewNum()%>&pageNum=<%=pageNum%>&pageSize=<%=pageSize%>"
			+"&search=<%=search%>&keyword=<%=keyword%>";
	}
});

$("#replyBtn").click(function() {
	location.href="<%=request.getContextPath()%>/index.jsp?workgroup=review&work=review_write"
		+"&ref=<%=review.getReviewRef()%>&restep=<%=review.getReviewRestep()%>&relevel=<%=review.getReviewRelevel()%>"
		+"&pageNum=<%=pageNum%>&pageSize=<%=pageSize%>&search=<%=search%>&keyword=<%=keyword%>";
});

$("#listBtn").click(function() {
	location.href="<%=request.getContextPath()%>/index.jsp?workgroup=review&work=review_list"
		+"&pageNum=<%=pageNum%>&pageSize=<%=pageSize%>&search=<%=search%>&keyword=<%=keyword%>";
});
</script>
```
