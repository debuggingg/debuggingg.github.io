---
layout: single
title: 2024-09-12-spring33-restful
---
#### board.jsp


```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">
#btnDiv {
	margin: 10px;
}

#restBoardTable {
	border: 1px solid black;
	border-collapse: collapse; 
}

#restBoardTable td, #restBoardTable th {
	border: 1px solid black;
	padding: 3px;
}

.inputDiv {
	width: 240px;
	height: 80px;
	border: 2px solid black;
	background-color: gray;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -40px;
	margin-left: -120px;
	padding: 5px;
	z-index: 100;
	display: none;
}
</style>
</head>
<body>
	<h1>RestBoard</h1>
	<hr>
	<div id="btnDiv">
		<button type="button" id="writeBtn">Write Post</button>
	</div>
	
	<%-- Tag to display the list of posts --%>
	<div id="restBoardListDiv"></div>

	<%-- Tag to display the page numbers --%>
	<div id="pageNumDiv"></div>
	
	<%-- Tag to input new posts --%>
	<div id="insertDiv" class="inputDiv">
		<table>
			<tr>
				<td>Author</td>
				<td><input type="text" id="insertWriter" class="insert"></td>
			</tr>
			<tr>
				<td>Content</td>
				<td><input type="text" id="insertContent" class="insert"></td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="button" id="insertBtn">Save</button>
					<button type="button" id="cancelInsertBtn">Cancel</button>
				</td>
			</tr>
		</table>
	</div>
	
	<%-- Tag to input updated posts --%>
	<div id="updateDiv" class="inputDiv">
		<input type="hidden" id="updateIdx">
		<table>
			<tr>
				<td>Author</td>
				<td><input type="text" id="updateWriter" class="update"></td>
			</tr>
			<tr>
				<td>Content</td>
				<td><input type="text" id="updateContent" class="update"></td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="button" id="updateBtn">Update</button>
					<button type="button" id="cancelUpdateBtn">Cancel</button>
				</td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript">
	// Global variable to store the requested page number
	// => Declared as a global variable to retain the value until the program ends, so it can be used in all JavaScript functions
	var page=1;
	
	// Call the function to request the page and display the list of posts
	boardListDisplay(page, 5);
	
	// Function to display the list of posts for the requested page number
	// => Requests the page asynchronously and receives the response as a JSON string, which is then converted into HTML tags and displayed
	function boardListDisplay(pageNum, pageSize) {
		page=pageNum;
		$.ajax({
			type: "get",
			url: "<c:url value="/rest/board_list"/>",
			data: {"pageNum":pageNum, "pageSize":pageSize},
			dataType: "json",
			success: function(result) {
				//alert(result);
				
				// Convert the received JavaScript object into HTML tags for display
				if(result.restBoardList.length == 0) {// If no posts are found
					var html="<table id='restBoardTable'>";
					html+="<tr>";
					html+="<th width='800'>No posts found.</th>";
					html+="</tr>";
					html+="</table>";
					$("#restBoardListDiv").html(html);
					return;
				}
				
				var html="<table id='restBoardTable'>";
				html+="<tr>";
				html+="<th width='50'>No</th>";
				html+="<th width='100'>Author</th>";
				html+="<th width='350'>Content</th>";
				html+="<th width='200'>Date</th>";
				html+="<th width='50'>Edit</th>";
				html+="<th width='50'>Delete</th>";
				html+="</tr>";
				$(result.restBoardList).each(function() {
					html+="<tr>";
					html+="<td align='center'>"+this.idx+"</td>";
					html+="<td align='center'>"+this.writer+"</td>";
					html+="<td>"+this.content+"</td>";
					html+="<td align='center'>"+this.regdate+"</td>";
					html+="<td align='center'><button type='button' onclick='modify("+this.idx+");'>Edit</button></td>";
					html+="<td align='center'><button type='button' onclick='remove("+this.idx+");'>Delete</button></td>";
					html+="</tr>";
				});
				html+="</table>";
				$("#restBoardListDiv").html(html);
				// Call the function to display the page numbers
				pageNumberDisplay(result.pager);
			},
			error: function(xhr) {
				alert("Error code (post list search) = "+xhr.status);
			}
		});
	}
	
	// Function to display page numbers by converting the paging-related Object into HTML tags
	function pageNumberDisplay(pager) {
		var html="";
		
		if(pager.startPage > pager.blockSize) {
			html+="<a href='javascript:boardListDisplay("+pager.prevPage+");'>[Previous]</a>";
		} else {
			html+"[Previous]";
		}
		
		for(i = pager.startPage ; i <= pager.endPage ; i++) {
			if(pager.pageNum != i) {
				html+="<a href='javascript:boardListDisplay("+i+");'>["+i+"]</a>";
			} else {
				html+="["+i+"]";	
			}
		}
		
		if(pager.endPage != pager.totalPage) {
			html+="<a href='javascript:boardListDisplay("+pager.nextPage+");'>[Next]</a>";
		} else {
			html+"[Next]";
		}
		
		$("#pageNumDiv").html(html);
	}
	
	// Function to initialize and hide all input tags
	function init() {
		$(".insert").val("");
		$(".update").val("");
		$(".inputDiv").hide();
	}
	
	// Event handler function for clicking the [Write Post] button
	$("#writeBtn").click(function() {
		init();
		// Show the tag for inputting new posts
		$("#insertDiv").show();
	});
	
	// Event handler function for clicking the [Save] button in the new post input tag
	// => Asynchronously request the RESTful API to insert the input values and display the result
	$("#insertBtn").click(function() {
		var writer=$("#insertWriter").val();
		var content=$("#insertContent").val();
		
		if(writer == "") {
			alert("Please enter the author.");
			return;
		}
		
		if(content == "") {
			alert("Please enter the content.");
			return;
		}
		
		$.ajax({
			type: "post",
			url: "<c:url value="/rest/board_add"/>",
			//headers : Properties for modifying the information stored in the request message header
			// => Changes the contentType property in the request message header to set the MIME type of the request message body
			//headers: {"contentType":"application/json"},
			//contentType : Property to set the MIME type of the request message body
			// => Configures the request message body to be sent as JSON format
			// => The @RequestBody annotation is used in the request handling method to receive the JSON string as a Java object
			contentType: "application/json",
			//JSON.stringify(object) : Method to convert a JavaScript object to a JSON format string
			data: JSON.stringify({"writer":writer, "content":content}),
			dataType: "text",
			success: function(result) {
				if(result == "success") {
					init();
					boardListDisplay(page, 5);
				}
			},
			error: function(xhr) {
				alert("Error code (post insertion) = "+xhr.status);
			}
		});
	});
	
	// Event handler function for clicking the [Cancel] button in the new post input tag
	$("#cancelInsertBtn").click(init);
	


// Function called when the [Modify] tag of a post is clicked
// => Asynchronously requests a JSON string from the Restful API to search for the post with the given ID and process the output
function modify(idx) {
    //alert(idx);
    
    init();
    $("#updateDiv").show();
    
    $.ajax({
        type: "get",
        // Use the URL address of the page to pass values
        // => Use the @PathVariable annotation to store the passed values in the method parameter
        url: "<c:url value='/rest/board_view'/>/" + idx,
        dataType: "json",
        success: function(result) {
            $("#updateIdx").val(result.idx);
            $("#updateWriter").val(result.writer);
            $("#updateContent").val(result.content);
        },
        error: function(xhr) {
            alert("Error code (Post search) = " + xhr.status);
        }
    });
}

// Register event handler for the [Update] tag in the update post input form
// => Asynchronously requests the Restful API to process the update and provides the execution result
$("#updateBtn").click(function() {
    var idx = $("#updateIdx").val();
    var writer = $("#updateWriter").val();
    var content = $("#updateContent").val();
    
    if (writer == "") {
        alert("Please enter the author's name.");
        return;
    }
    
    if (content == "") {
        alert("Please enter the content.");
        return;
    }
    
    $.ajax({
        type: "put",
        url: "<c:url value='/rest/board_modify'/>",
        contentType: "application/json",
        data: JSON.stringify({"idx": idx, "writer": writer, "content": content}),
        dataType: "text",
        success: function(result) {
            if (result == "success") {
                init();
                boardListDisplay(page, 5);
            }
        },
        error: function(xhr) {
            alert("Error code (Post update) = " + xhr.status);
        }
    });
});

// Register event handler for the [Cancel] tag in the update post input form
$("#cancelUpdateBtn").click(init);

// Function called when the [Delete] tag of a post is clicked
// => Asynchronously requests the Restful API to delete the post and provides the execution result
function remove(idx) {
    if (confirm("Are you sure you want to delete this post?")) {
        $.ajax({
            type: "delete",
            url: "<c:url value='/rest/board_remove'/>/" + idx,
            dataType: "text",
            success: function(result) {
                if (result == "success") {
                    init();
                    boardListDisplay(page, 5);
                }
            },
            error: function(xhr) {
                alert("Error code (Post delete) = " + xhr.status);
            }
        });
    }
}
</script>
</body>
</html>
```

@ResponseBody = java=>json
@RequestBody = json=>java
#### RestBoardController.java

```java
package xyz.itwill09.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.RestBoard;
import xyz.itwill09.service.RestBoardService;

// The Controller class providing REST functionality. The request handling methods can be verified using 
// RESTful API testing tools like Postman or ARC.

// @RestController: Annotation to register the Controller class as a Spring Bean that provides REST functionality
// => Request handling methods return data as JSON strings to the client without needing the @ResponseBody annotation.
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class RestBoardController {
    private final RestBoardService restBoardService;
    
    // Request handling method to search for a row corresponding to the requested page number
    // in the REST_BOARD table and respond with a JSON string representing the result.
    // Use @GetMapping instead of @RequestMapping for RESTful requests.
    @GetMapping("/board_list")
    // No need to use @ResponseBody since @RestController handles this automatically.
    public Map<String, Object> restBoardList(@RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "5") int pageSize) {
        return restBoardService.getRestBoardList(pageNum, pageSize);
    }
    
    // Request handling method to insert a new row into the REST_BOARD table using
    // the provided post data and respond with a success message.
    // => Use @RequestBody annotation to bind the JSON data to a RestBoard object(java).
    @PostMapping("/board_add")
    public String restBoardAdd(@RequestBody RestBoard restBoard) {
        // HtmlUtils.htmlEscape(String str): Converts HTML special characters to escape sequences
        // to prevent XSS attacks.
        restBoard.setContent(HtmlUtils.htmlEscape(restBoard.getContent()));
        restBoardService.addRestBoard(restBoard);
        return "success";
    }

    /*
    // Request handling method to search for a row in the REST_BOARD table
    // using a query string parameter and respond with a JSON string.
    @GetMapping("/board_view")
    public RestBoard restBoardView(@RequestParam int idx) {
        return restBoardService.getRestBoard(idx);
    }
    */

    // Request handling method to search for a row in the REST_BOARD table
    // using a path variable for the row number and respond with a JSON string.
    // => Use @PathVariable to bind the URL path variable to the method parameter.
    @GetMapping("/board_view/{idx}")
    public RestBoard restBoardView(@PathVariable int idx) {
        return restBoardService.getRestBoard(idx);
    }

    // Request handling method to update an existing row in the REST_BOARD table
    // using the provided post data and respond with a success message.
    @PutMapping("/board_modify")
    public String restBoardModify(@RequestBody RestBoard restBoard) {
        restBoard.setContent(HtmlUtils.htmlEscape(restBoard.getContent()));
        restBoardService.modifyRestBoard(restBoard);
        return "success";
    }

    // Request handling method to delete a row from the REST_BOARD table
    // using the provided row number and respond with a success message.
    @DeleteMapping("/board_remove/{idx}")
    public String restBoardRemove(@PathVariable int idx) {
        restBoardService.removeRestBoard(idx);
        return "success";
    }
}
```
