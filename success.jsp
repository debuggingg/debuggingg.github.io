<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
</head>
<body>
	<h1>제품확인</h1>
	<hr>
	<%-- 
	<p>제품코드 = ${product.productCode }</p>
	<p>제품이름 = ${product.productName }</p>
	<p>제품수량 = ${product.productQuantity }</p>
	--%>
	
	<p><spring:message code="product.code"/> = ${product.productCode }</p>
	<p><spring:message code="product.name"/> = ${product.productName }</p>
	<p><spring:message code="product.qty"/> = ${product.productQuantity }</p>
	<hr>
	
	<h3><spring:message code="product.success" arguments="${product.productName },${product.productCode }"/></h3> 
</body>
</html>