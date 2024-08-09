---
layout: single
title: 2024/08/06 JSP46-M2-EL-Function-Operation
---
#### EL Function 


```
- how to create XML file 
file type xml-> next->file name.tld ->next->click (create file using a DTO or XML Schema file)-> next ->click (Select XML Catalog entry)-> click (Plugin Secified Entries)->find (http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd) ->next-> click j2ee -> click edit-> make empty (prefix) delete-> ok -> change j2ee to <no prefix> ->  finish
```

#### el_function.jsp -> class HelloEL-> under WEB-INF create folder (tld) -> xml file name(el.tld)


### JSP Code
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- taglib Directive: Includes the TLD file in the JSP document, allowing the use of EL functions or custom tags registered in the TLD file --%>
<%-- prefix attribute: Sets a prefix (NameSpace) to use EL functions or custom tags --%>
<%-- uri attribute: Sets an identifier (URL element value) for distinguishing the TLD file - for auto-completion functionality --%>    
<%@taglib prefix="elfun" uri="https://www.itwill.xyz/mvc/el"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
    <h1>EL Function</h1>
    <hr>
    <p>EL Function: A function used in EL expressions</p>
    <p>Function: Provides functionality that processes values passed as parameters and returns the result</p>
    <p>When an EL function is called in an EL expression, it automatically calls the static method of the EL function class, executes the command, and returns the result for output.</p>
    <p>Write the EL function class, register the static method of the class as an EL function in the TLD file, and then use the taglib directive in the JSP document to provide the TLD file for calling the EL function in the EL expression.</p>
    <hr>
    <p>${elfun:hello("Hong Gil Dong") }</p>
    <p>${elfun:hello("Im Geok Jeong") }</p>
    <p>${elfun:hello("Jeon Woo Chi") }</p>
</body>
</html>
```

### TLD File (el.tld)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- TLD (Tag Library Descriptor) file: XML file for registering EL functions or custom tags -->
<!-- => XML document uses only elements provided by the XML schema file (web-jsptaglibrary_2_0.xsd) -->
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xml="http://www.w3.org/XML/1998/namespace" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd ">
    <!-- description: Provides a description of the TLD file -->
    <description>EL Function</description>    
    <!-- tlib-version: Sets the version of the TLD file -->
    <tlib-version>1.0</tlib-version>
    <!-- short-name: Registers the name of the TLD file -->
    <short-name>elfun</short-name>
    <!-- uri: Sets the URI address as an identifier for distinguishing the TLD file -->
    <!-- => Identifier used to load the TLD file in the JSP document -->
    <uri>https://www.itwill.xyz/mvc/el</uri>
    
    <!-- function: Registers an EL function -->
    <function>
        <!-- name: Sets the name of the EL function -->
        <name>hello</name>
        <!-- function-class: Sets the class where the static method for the EL function is written -->
        <function-class>xyz.itwill.el.HelloEL</function-class>
        <!-- function-signature: Sets the signature of the static method to be called as an EL function -->
        <function-signature>java.lang.String hello(java.lang.String)</function-signature>
    </function>
</taglib>
```



### Java Class (HelloEL.java)
```java
package xyz.itwill.el;

// Class that contains the static method to be called when using the EL function in EL expressions
public class HelloEL {
    // Static method called when using the EL function in EL expressions
    // => Processes the value passed as a parameter and returns a result
    public static String hello(String name) {
        return name + "님, 안녕하세요."; // Translates to "Hello, Mr./Ms. [name]."
    }
}
```

### Summary of Translation:
- **JSP Code**: Explains how to use EL functions by including a TLD file with the `taglib` directive. It demonstrates how to call an EL function and get output.
- **TLD File**: Defines the EL function and its properties, including the function name, class, and signature.
- **Java Class**: Implements the static method for the EL function that returns a greeting message.


---


####  el_operation

```jsp
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
    <h1>EL Operator</h1>
    <hr>
    <%-- EL Operators: Operators used in EL expressions - arithmetic operators, comparison operators, logical operators, etc. --%>
    <% request.setAttribute("num1", 20); %>
    <% request.setAttribute("num2", 10); %>
    <div>num1 = ${num1 }</div>
    <div>num2 = ${num2 }</div>
    <hr>
    <div>num1 * num2 = ${num1 * num2 }</div>
    <div>num1 / num2 = ${num1 / num2 }</div>
    <div>num1 div num2 = ${num1 div num2 }</div>
    <div>num1 % num2 = ${num1 % num2 }</div>
    <div>num1 mod num2 = ${num1 mod num2 }</div>
    <div>num1 + num2 = ${num1 + num2 }</div>
    <div>num1 - num2 = ${num1 - num2 }</div>
    <hr>
    <div>num1 &gt; num2 = ${num1 > num2 }</div>
    <div>num1 gt num2 = ${num1 gt num2 }</div>
    <div>num1 &lt; num2 = ${num1 < num2 }</div>
    <div>num1 lt num2 = ${num1 lt num2 }</div>
    <div>num1 &gt;= num2 = ${num1 >= num2 }</div>
    <div>num1 ge num2 = ${num1 ge num2 }</div>
    <div>num1 &lt;= num2 = ${num1 <= num2 }</div>
    <div>num1 le num2 = ${num1 le num2 }</div>
    <div>num1 == num2 = ${num1 == num2 }</div>
    <div>num1 eq num2 = ${num1 eq num2 }</div>
    <div>num1 != num2 = ${num1 != num2 }</div>
    <div>num1 ne num2 = ${num1 ne num2 }</div>
    <hr>
    <% request.setAttribute("su", 15); %>
    <div>su = ${su }</div>
    <hr>
    <div>num1 &gt; su &amp;&amp; num2 &lt; su = ${num1 > su && num2 < su }</div>
    <div>num1 gt su and num2 lt su = ${num1 gt su and num2 lt su }</div>
    <div>num1 &lt; su || num2 &gt; su = ${num1 < su || num2 > su }</div>
    <div>num1 lt su or num2 gt su = ${num1 lt su or num2 gt su }</div>
    <hr>
    <%
        Object object = null;
        request.setAttribute("object", object);
        
        String string = "";
        request.setAttribute("string", string);
        
        String[] array = {};
        request.setAttribute("array", array);
        
        List<String> list = new ArrayList<String>();
        request.setAttribute("list", list);
    %>
    <%-- empty operator: In EL expressions, returns [true] if the attribute value is missing or empty, and [false] if the attribute value is present --%>
    <div>object Empty = ${empty(object) }</div>
    <div>string Empty = ${empty(string) }</div>
    <div>array Empty = ${empty(array) }</div>
    <div>list Empty = ${empty(list) }</div>
</body>
</html>
```

### Summary of Translation:
- **EL Operators**: This section demonstrates the use of various operators in EL expressions, including arithmetic, comparison, and logical operators.
- **Arithmetic Operations**: Examples show how to use basic arithmetic operators such as `*`, `/`, `%`, `+`, and `-`.
- **Comparison Operations**: Examples demonstrate comparison operators such as `>`, `<`, `>=`, `<=`, `==`, and `!=`, as well as their equivalent keywords (`gt`, `lt`, `ge`, `le`, `eq`, `ne`).
- **Logical Operations**: Examples show logical operators `&&` (and), `||` (or), and their keyword equivalents (`and`, `or`).
- **Empty Operator**: Demonstrates the use of the `empty` operator to check if an attribute value is empty or not.



