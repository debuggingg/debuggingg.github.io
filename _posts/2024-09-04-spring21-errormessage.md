---
layout: single
title: 2024-09-04-spring21-errormessage
---
properties path: /WEB-INF/message/error
#### error_en.properties
```
NotEmpty.product.productCode = please provide product code
NotEmpty.product.productName = it's required for product Name 
Min.product.productQuantity = Min 1,it's more than 1 quantity
Max.product.productQuantity = Max 100, these are less than 100 quantities
```
#### error_ko.properties

#### MessageController.java


```java
@RequestMapping(value = "/register", method = RequestMethod.POST)
public String message(@ModelAttribute @Valid Product product, Errors errors, HttpSession session) {
    // Create a Locale object - set the language to use
    Locale locale = new Locale("en");
    
    // Store the Locale object as a Session Scope attribute in the HttpSession object
    // => Ensure the attribute name uses the SessionLocaleResolver class's LOCALE_SESSION_ATTRIBUTE_NAME constant field
    session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
    
    if (errors.hasErrors()) {
        return "message/register";
    }
    return "message/success";
}
```
### register.jsp, success.jsp


```jsp
<%-- Messages can be retrieved from the properties file and used with values --%>
<%-- => In the properties file, messages can include values using the {number} format, where the number starts from 0 and increases by 1 --%>
<%-- arguments attribute: Set values to be passed to the message in the properties file --%>
<%-- => Use [,] to separate multiple values --%>
<h3><spring:message code="product.success" 
    arguments="${product.productName}, ${product.productCode }"/></h3>

<h1>Product Registration</h1>
<hr>
<c:url var="url" value="/message/register"/>
<form:form action="${url }" method="post" modelAttribute="product">
<table>
    <tr>
        <%-- <td>Product Code</td> --%>
        <%-- message tag: Provides messages managed by the MessageSource object --%>
        <%-- code attribute: Set the identifier to distinguish messages in the properties file --%>
        <td><spring:message code="product.code"/></td>
        <td>
            <form:input path="productCode"/>
            <form:errors path="productCode" element="span" cssClass="error"/>
        </td>    
    </tr>
    <tr>
        <%-- <td>Product Name</td> --%>
        <td><spring:message code="product.name"/></td>
        <td>
            <form:input path="productName"/>
            <form:errors path="productName" element="span" cssClass="error"/>
        </td>    
    </tr>
    <tr>
        <%-- <td>Product Quantity</td> --%>
        <td><spring:message code="product.qty"/></td>
        <td>
            <form:input path="productQuantity"/>
            <form:errors path="productQuantity" element="span" cssClass="error"/>
        </td>    
    </tr>
    <tr>
        <td colspan="2"><form:button>Register</form:button></td>
    </tr>
</table>
</form:form>
```


#### sevlet-context.xml

```xml
<!-- If the Properties file is created in the [src/main/webapp] folder, provide the path to the web resource -->
<!-- => If the Properties file is changed, the updated messages will be applied automatically -->
<beans:value>/WEB-INF/message/error</beans:value>

<!-- Register the SessionLocaleResolver class as a Spring Bean -->
<!-- SessionLocaleResolver object: An object that stores a Locale object as a Session Scope attribute to provide messages written in the specified locale -->
<beans:bean class="org.springframework.web.servlet.i18n.SessionLocaleResolver" id="localeResolver">
    <!-- Inject the locale to be stored in the Locale object into the defaultLocale field -->
    <beans:property name="defaultLocale" value="ko"/>
</beans:bean>
```