---
layout: single
title: 2024/08/14 MyBatis9-Search
---

#### Search 


```xml
<!-- Element to search rows in the MYHEWON table where the search column contains the search keyword,
     and provide the result as a MyHewon object -->
<!-- #{variableName|fieldName|mapKey} : The provided value is used as a literal in the SQL command - 'provided value' -->
<!-- ${variableName|fieldName|mapKey} : The provided value is used to construct the SQL command - provided value -->
<!-- => In the iBATIS framework, use $variableName|fieldName|mapKey$ format -->
<!-- => The $ expression is vulnerable to SQL injection attacks, so its use is not recommended -->


    <select id="selectSearchHewonList" parameterType="map" resultMap="myHewonResultMap">
        SELECT hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope 
        FROM myhewon WHERE ${searchColumn} = #{searchKeyword} ORDER BY hewon_id
    </select>


<!-- Element to search rows in the MYHEWON table where the search column contains the search keyword,
     and provide the result as a MyHewon object -->

    <select id="selectSearchHewonList" parameterType="map" resultMap="myHewonResultMap">
        SELECT hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope 
        FROM myhewon WHERE ${searchColumn} LIKE '%' || #{searchKeyword} || '%' ORDER BY hewon_id
    </select>

```

### Explanation:

- **Element to search rows**: Describes that the SQL element is used to find rows in the `MYHEWON` table where the specified column contains the provided search keyword.
- **#{variableName|fieldName|mapKey}**: Indicates that this syntax is used to insert the value directly into the SQL command, treating it as a literal.
- **${variableName|fieldName|mapKey}**: Indicates that this syntax is used to construct part of the SQL command dynamically, which includes the actual value.
- **$ expression vulnerability**: Notes that using `${}` can expose the application to SQL injection attacks and is generally discouraged.

This translation maintains the structure and intent of the original comments, providing clear guidance on SQL injection risks and the proper usage of parameter placeholders in SQL queries.


#### java (interface)
```java

List<MyHewon> selectSearchHewonList(Map<String, Object> map);

```

### jsp file


```jsp
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="xyz.itwill.dao.MyHewonDAO"%>
<%@page import="xyz.itwill.dto.MyHewon"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String searchColumn = request.getParameter("searchColumn");
	String searchKeyword = request.getParameter("searchKeyword");
	
	List<MyHewon> hewonList = null;
	if (searchKeyword == null || searchKeyword.equals("")) { // If no search is used
		hewonList = MyHewonDAO.getDAO().selectHewonList(); // Full search
	} else { // If search is used
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchColumn", searchColumn);
		map.put("searchKeyword", searchKeyword);

		hewonList = MyHewonDAO.getDAO().selectSearchHewonList(map); // Conditional search
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYBATIS</title>
<style type="text/css">
table {
	border: 1px solid black;
	border-collapse: collapse;
}

td {
	border: 1px solid black;
	text-align: center;
	padding: 3px;	
}

.id { width: 150px; }
.name { width: 150px; }
.phone { width: 200px; }
.email { width: 200px; }
.scope { width: 100px; }
</style>
</head>
<body>
	<h1>Member List</h1>
	<hr>
	<table>
		<tr>
			<td class="id">ID</td>
			<td class="name">Name</td>
			<td class="phone">Phone Number</td>
			<td class="email">Email</td>
			<td class="scope">Scope</td>
		</tr>
		<% if (hewonList.isEmpty()) { %>
		<tr>
			<td colspan="5">No member information found.</td>
		</tr>
		<% } else { %>
			<% for (MyHewon hewon : hewonList) { %>
			<tr>
				<td class="id"><%= hewon.getId() %></td>
				<td class="name"><%= hewon.getName() %></td>
				<td class="phone"><%= hewon.getPhone() %></td>
				<td class="email"><%= hewon.getEmail() %></td>
				<td class="scope"><%= hewon.getScope() %></td>
			</tr>	
			<% } %>
		<% } %>
	</table>
	<br>
	
	<form method="post">
		<%-- Select the search column and pass it to the request --%>
		<%-- => The value must match the column names in the table --%>
		<select name="searchColumn">
			<option value="hewon_id" selected="selected">ID</option>
			<option value="hewon_name">Name</option>
			<option value="hewon_phone">Phone Number</option>
			<option value="hewon_email">Email</option>
		</select>
		<%-- Enter the search keyword and pass it to the request --%>
		<input type="text" name="searchKeyword">
		<button type="submit">Search</button>
	</form>
</body>
</html>
```