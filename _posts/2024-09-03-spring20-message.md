---
layout: single
title: 2024-09-03-spring20-message
---
#### Program Flow

Create a `Product` class, create a JSP file to load it, and create a properties file for labels so that they can be loaded. To make the properties file visible, place it in:

=> `src/main/resources -> label.properties`

Or to hide it, place it in:

=> `WEB-INF/message/label.properties`

After creating the properties file, you need to register `ReloadableResourceBundleMessageSource` as a bean in the `servlet-context` file.

---
#### MessageController.java


```java
package xyz.itwill09.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.itwill09.dto.Product;

// How to use Spring Messages
// 1. Create a Properties file to store messages
// => It is recommended to create the Properties file in the [src/main/webapp] folder
// so that it can be referenced by the Spring container (WebApplicationContext object)
// => Alternatively, you can create it in the [src/main/java] or [src/main/resources] folder
// 2. Register a class that provides message management functionality as a Spring Bean
// in the Spring Bean Configuration File (servlet-context.xml) and inject the path
// of the Properties file into its field

@Controller
@RequestMapping("/message")
public class MessageController {
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String message(@ModelAttribute Product product) {
        return "message/register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String message(@ModelAttribute @Valid Product product, Errors errors) {
        if(errors.hasErrors()) {
            return "message/register";
        }
        return "message/success";
    }
}
```
#### Product.java

```java
package xyz.itwill09.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Product {
    @NotEmpty // Ensures that the productCode field is not empty
    private String productCode;

    @NotEmpty // Ensures that the productName field is not empty
    private String productName;

    @Min(value = 1) // Annotation to validate the minimum value for the productQuantity field
    @Max(value = 100) // Annotation to validate the maximum value for the productQuantity field
    private int productQuantity;
}
```

#### register.jsp 


```jsp
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
    <h1>Product Registration</h1>
    <hr>
    <c:url var="url" value="/message/register"/>
    <form:form action="${url }" method="post" modelAttribute="product">
    <table>
        <tr>
            <%-- <td>Product Code</td> --%>
            <%-- message tag: Tag that outputs messages managed by the MessageSource object --%>
            <%-- code attribute: Identifier set as a property value to distinguish messages stored in the Properties file --%>
            <td><spring:message code="product.code"/></td>
            <td>
                <form:input path="productCode"/>
                <form:errors path="productCode" element="span"/>
            </td>    
        </tr>
        <tr>
            <%-- <td>Product Name</td> --%>
            <td><spring:message code="product.name"/></td>
            <td>
                <form:input path="productName"/>
                <form:errors path="productName" element="span"/>
            </td>    
        </tr>
        <tr>
            <%-- <td>Product Quantity</td> --%>
            <td><spring:message code="product.qty"/></td>
            <td>
                <form:input path="productQuantity"/>
                <form:errors path="productQuantity" element="span"/>
            </td>    
        </tr>
        <tr>
            <td colspan="2"><form:button>Register</form:button></td>
        </tr>
    </table>
    </form:form>
</body>
</html>
```

#### sevlet-context.xml


---

```xml
<!-- Register a class that implements the MessageSource interface as a Spring Bean -->
<!-- => Provide message management functionality using the ReloadableResourceBundleMessageSource class -->
<!-- => The identifier (beanName) of the Spring Bean must be set to [messageSource] -->
<beans:bean class="org.springframework.context.support.ReloadableResourceBundleMessageSource" id="messageSource">
    <!-- Inject a List object into the basenames field -->
    <!-- => The elements of the List object should contain the paths to the Properties files where messages are stored -->
    <beans:property name="basenames">
        <beans:list>
            <!-- If the Properties file is created in the [src/main/webapp] folder, provide the path as a web resource -->
            <!-- => Messages will be automatically updated when the Properties file changes -->
            <!-- <beans:value>/WEB-INF/message/label</beans:value> -->
            
            <!-- If the Properties file is created in the [src/main/java] or [src/main/resources] folder, provide the path using the classpath prefix -->
            <!-- => Messages will be updated only after restarting the application server if the Properties file changes -->
            <beans:value>classpath:message/label</beans:value>
        </beans:list>
    </beans:property>
    <!-- Inject the time (in seconds) for reading and loading the Properties file into memory into the cacheSeconds field -->
    <beans:property name="cacheSeconds" value="60"/>
    <!-- Inject the character set of the Properties file into the defaultEncoding field -->
    <beans:property name="defaultEncoding" value="utf-8"/>
</beans:bean>
```
#### label.properties
```
product.code = \uC81C\uD488\uCF54\uB4DC
product.name = \uC81C\uD488\uC774\uB984
product.qty = \uC81C\uD488\uC218\uB7C9
```