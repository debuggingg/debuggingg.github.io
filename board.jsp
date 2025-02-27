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
		<button type="button" id="writeBtn">글쓰기</button>
	</div>
	
	<%-- 게시글 목록을 출력하는 태그 --%>
	<div id="restBoardListDiv"></div>

	<%-- 페이지 번호를 출력하는 태그 --%>
	<div id="pageNumDiv"></div>
	
	<%-- 신규 게시글을 입력받기 위한 태그 --%>
	<div id="insertDiv" class="inputDiv">
		<table>
			<tr>
				<td>작성자</td>
				<td><input type="text" id="insertWriter" class="insert"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><input type="text" id="insertContent" class="insert"></td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="button" id="insertBtn">저장</button>
					<button type="button" id="cancelInsertBtn">취소</button>
				</td>
			</tr>
		</table>
	</div>
	
	<%-- 변경 게시글을 입력받기 위한 태그 --%>
	<div id="updateDiv" class="inputDiv">
		<input type="hidden" id="updateIdx">
		<table>
			<tr>
				<td>작성자</td>
				<td><input type="text" id="updateWriter" class="update"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><input type="text" id="updateContent" class="update"></td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="button" id="updateBtn">변경</button>
					<button type="button" id="cancelUpdateBtn">취소</button>
				</td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript">
	//요청 페이지 번호를 저장하기 위한 전역변수
	// => 프로그램 종료시까지 값을 유지해 모든 자바스트립트 함수에서 사용할 수 있도록 전역변수로 선언 
	var page=1;
	
	//페이지를 요청해 게시글 목록을 제공받아 출력하는 함수 호출
	boardListDisplay(page, 5);
	
	//요청 페이지 번호를 전달받아 페이지 번호에 해당하는 게시글 목록을 출력하는 함수
	// => 비동기식 방식으로 페이지를 요청해 JSON 형식의 문자열로 응답받아 HTML 태그로 변환하여 출력 처리
	function boardListDisplay(pageNum, pageSize) {
		page=pageNum;
		$.ajax({
			type: "get",
			url: "<c:url value="/rest/board_list"/>",
			data: {"pageNum":pageNum, "pageSize":pageSize},
			dataType: "json",
			success: function(result) {
				//alert(result);
				
				//응답받은 자바스트립트 객체를 HTML 태그로 변환하여 출력 처리
				if(result.restBoardList.length == 0) {//검색된 게시글이 없는 경우
					var html="<table id='restBoardTable'>";
					html+="<tr>";
					html+="<th width='800'>검색된 게시글이 없습니다.</th>";
					html+="</tr>";
					html+="</table>";
					$("#restBoardListDiv").html(html);
					return;
				}
				
				var html="<table id='restBoardTable'>";
				html+="<tr>";
				html+="<th width='50'>번호</th>";
				html+="<th width='100'>작성자</th>";
				html+="<th width='350'>내용</th>";
				html+="<th width='200'>작성일</th>";
				html+="<th width='50'>변경</th>";
				html+="<th width='50'>삭제</th>";
				html+="</tr>";
				$(result.restBoardList).each(function() {
					html+="<tr>";
					html+="<td align='center'>"+this.idx+"</td>";
					html+="<td align='center'>"+this.writer+"</td>";
					html+="<td>"+this.content+"</td>";
					html+="<td align='center'>"+this.regdate+"</td>";
					html+="<td align='center'><button type='button' onclick='modify("+this.idx+");'>변경</button></td>";
					html+="<td align='center'><button type='button' onclick='remove("+this.idx+");'>삭제</button></td>";
					html+="</tr>";
				});
				html+="</table>";
				$("#restBoardListDiv").html(html);
				//페이지 번호를 출력하는 함수 호출
				pageNumberDisplay(result.pager);
			},
			error: function(xhr) {
				alert("에러코드(게시글 목록 검색) = "+xhr.status);
			}
		});
	}
	
	//페이징 처리 관련 Object 객체를 전달받아 HTML 태그로 변환해 페이지 번호를 출력하는 함수
	function pageNumberDisplay(pager) {
		var html="";
		
		if(pager.startPage > pager.blockSize) {
			html+="<a href='javascript:boardListDisplay("+pager.prevPage+");'>[이전]</a>";
		} else {
			html+"[이전]";
		}
		
		for(i = pager.startPage ; i <= pager.endPage ; i++) {
			if(pager.pageNum != i) {
				html+="<a href='javascript:boardListDisplay("+i+");'>["+i+"]</a>";
			} else {
				html+="["+i+"]";	
			}
		}
		
		if(pager.endPage != pager.totalPage) {
			html+="<a href='javascript:boardListDisplay("+pager.nextPage+");'>[다음]</a>";
		} else {
			html+"[다음]";
		}
		
		$("#pageNumDiv").html(html);
	}
	
	//모든 입력태그 초기화 및 태그 숨김 처리하는 함수
	function init() {
		$(".insert").val("");
		$(".update").val("");
		$(".inputDiv").hide();
	}
	
	//[글쓰기] 태그를 클릭한 경우 호출될 이벤트 처리 함수 등록
	$("#writeBtn").click(function() {
		init();
		//신규 게시글을 입력받기 위한 태그 출력 처리
		$("#insertDiv").show();
	});
	
	//신규 게시글을 입력받기 위한 태그에서 [저장] 태그를 클릭한 경우 호출될 이벤트 처리 함수 등록
	// => 입력값을 삽입 처리하는 Restful API를 비동기식으로 요청하여 실행결과를 제공받아 출력 처리
	$("#insertBtn").click(function() {
		var writer=$("#insertWriter").val();
		var content=$("#insertContent").val();
		
		if(writer == "") {
			alert("작성자를 입력해 주세요.");
			return;
		}
		
		if(content == "") {
			alert("내용을 입력해 주세요.");
			return;
		}
		
		$.ajax({
			type: "post",
			url: "<c:url value="/rest/board_add"/>",
			//headers : 리퀘스트 메세지 머릿부에 저장된 정보를 변경하기 위한 속성
			// =>  리퀘스트 메세지 머릿부의 contentType 속성으로 리퀘스트 메세지 몸체부에 
			//저장되어 전달될 값의 파일형식(MimeType)을 변경
			//headers: {"contentType":"application/json"},
			//contentType : 리퀘스트 메세지 몸체부에 저장되어 전달될 값의 파일형식(MimeType)을 
			//변경하기 위한 속성
			// => 리퀘스트 메세지 몸체부에 JSON 형식의 문자열로 값이 전달되도록 설정
			// => 요청 처리 메소드의 매개변수에서는 @RequestBody 어노테이션을 사용하여 JSON 형식의  
			//문자열을 전달받아 Java 객체로 저장하여 사용 - 속성명과 같은 이름의 필드에 전달값 저장
			contentType: "application/json",
			//JSON.stringify(object) : JavaScript 객체를 JSON 형식의 문자열로 변환하여 반환하는 메소드
			data: JSON.stringify({"writer":writer, "content":content}),
			dataType: "text",
			success: function(result) {
				if(result == "success") {
					init();
					boardListDisplay(page, 5);
				}
			},
			error: function(xhr) {
				alert("에러코드(게시글 삽입) = "+xhr.status);
			}
		});
	});
	
	//신규 게시글을 입력받기 위한 태그에서 [취소] 태그를 클릭한 경우 호출될 이벤트 처리 함수 등록
	$("#cancelInsertBtn").click(init);
	
	//게시글의 [변경] 태그를 클릭한 경우 호출되는 이벤트 처리 함수
	// => 매개변수로 전달받은 글번호의 게시글을 검색하여 JSON 형식의 문자열로 제공하는 
	//Restful API를 비동기식 방식으로 요청하여 출력 처리
	function modify(idx) {
		//alert(idx);
		
		init();
		$("#updateDiv").show();
		
		$.ajax({
			type: "get",
			//페이지의 요청 URL 주소를 사용해 값 전달 
			// => @PathVariable 어노테이션을 사용해 전달값을 매개변수에 저장
			url: "<c:url value="/rest/board_view"/>/"+idx,
			dataType: "json",
			success: function(result) {
				$("#updateIdx").val(result.idx);
				$("#updateWriter").val(result.writer);
				$("#updateContent").val(result.content);
			},
			error: function(xhr) {
				alert("에러코드(게시글 검색) = "+xhr.status);
			}
		});
	}
	
	
	//변경 게시글을 입력받기 위한 태그에서 [변경] 태그를 클릭한 경우 호출될 이벤트 처리 함수 등록
	// => 입력값으로 변경 처리하는 Restful API를 비동기식으로 요청하여 실행결과를 제공받아 출력 처리
	$("#updateBtn").click(function() {
		var idx=$("#updateIdx").val();
		var writer=$("#updateWriter").val();
		var content=$("#updateContent").val();
		
		if(writer == "") {
			alert("작성자를 입력해 주세요.");
			return;
		}
		
		if(content == "") {
			alert("내용을 입력해 주세요.");
			return;
		}
		
		$.ajax({
			type: "put",
			url: "<c:url value="/rest/board_modify"/>",
			contentType: "application/json",
			data: JSON.stringify({"idx":idx, "writer":writer, "content":content}),
			dataType: "text",
			success: function(result) {
				if(result == "success") {
					init();
					boardListDisplay(page, 5);
				}
			},
			error: function(xhr) {
				alert("에러코드(게시글 변경) = "+xhr.status);
			}
		});
	});
	
	//변경 게시글을 입력받기 위한 태그에서 [취소] 태그를 클릭한 경우 호출될 이벤트 처리 함수 등록
	$("#cancelUpdateBtn").click(init);
	
	//게시글의 [삭제] 태그를 클릭한 경우 호출되는 이벤트 처리 함수
	// => 게시글을 삭제 처리하는 Restful API를 비동기식으로 요청하여 실행결과를 제공받아 출력 처리
	function remove(idx) {
		if(confirm("게시글을 삭제 하시겠습니까?")) {
			$.ajax({
				type: "delete",
				url: "<c:url value="/rest/board_remove"/>/"+idx,
				dataType: "text",
				success: function(result) {
					if(result == "success") {
						init();
						boardListDisplay(page, 5);
					}
				},
				error: function(xhr) {
					alert("에러코드(게시글 삭제) = "+xhr.status);
				}
			});
		}
	}
	</script>
</body>
</html>















