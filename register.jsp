<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
</head>
<body>
	<h1>제품등록</h1>
	<hr>
	<c:url var="url" value="/message/register"/>
	<form:form action="${url }" method="post" modelAttribute="product">
	<table>
		<tr>
			<%-- <td>제품코드</td> --%>
			<%-- message 태그 : MessageSource 객체에 의해 관리되는 메세지를 제공받아 출력하는 태그 --%>
			<%-- code 속성 : Properties 파일에 저장된 메시지를 구분하기 위한 식별자를 속성값으로 설정 --%>
			<td><spring:message code="product.code"/></td>
			<td>
				<form:input path="productCode"/>
				<form:errors path="productCode" element="span"/>
			</td>	
		</tr>
		<tr>
			<%-- <td>제품이름</td> --%>
			<td><spring:message code="product.name"/></td>
			<td>
				<form:input path="productName"/>
				<form:errors path="productName" element="span"/>
			</td>	
		</tr>
		<tr>
			<%-- <td>제품수량</td> --%>
			<td><spring:message code="product.qty"/></td>
			<td>
				<form:input path="productQuantity"/>
				<form:errors path="productQuantity" element="span"/>
			</td>	
		</tr>
		<tr>
			<td colspan="2"><form:button>등록</form:button></td>
		</tr>
	</table>
	</form:form>
</body>
</html>