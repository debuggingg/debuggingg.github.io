---
layout: single
title: 2024/08/06 JSP7-M2-EL-Function-Operation
---

## Custom Tag 
custom folder-> hello_tag.jsp
package- > xyz.itwill.custom->HelloTag.java


class -> HelloAttributeTag.java->add tag to custom.tld->hello_attribute_tag.jsp

HelloBodyTag.java->add tag to custom.tld->hello_body_tag.jsp

#### custom.tld


```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.0" xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xml="http://www.w3.org/XML/1998/namespace" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd ">
	<description>Implementation of simple custom tags</description>
	<tlib-version>1.0</tlib-version>
	<short-name>simple</short-name>
	<uri>https://www.itwill.xyz/mvc/custom</uri>
	
	<!-- tag : Element to register a custom tag -->
	<tag>
		<!-- name : Element to set the name of the custom tag -->
		<name>hello</name>
		<!-- tag-class : Element to set the class that will be instantiated for the custom tag -->
		<tag-class>xyz.itwill.custom.HelloTag</tag-class>
		<!-- body-content : Element to set what kind of content the custom tag can have -->
		<!-- => Set to [empty] for custom tags that do not have body content -->
		<body-content>empty</body-content>		
	</tag>
	
	<tag>
		<name>helloAttribute</name>
		<tag-class>xyz.itwill.custom.HelloAttributeTag</tag-class>
		<body-content>empty</body-content>
		<!-- attribute : Element to register attributes for the custom tag -->
		<attribute>
			<!-- name : Element to set the name of the custom tag attribute -->
			<!-- => Attribute name should match the field name in the tag class -->
			<name>name</name>
			
			<!-- required : Element to specify whether the custom tag attribute is optional or required -->
			<!-- => false : Optional (default), true : Required -->
			<required>true</required>
		</attribute>
	</tag>
	
	<tag>
		<name>helloBody</name>
		<tag-class>xyz.itwill.custom.HelloBodyTag</tag-class>
		<!-- Set the body-content element to [JSP] to allow the tag body to be a JSP -->
		<body-content>JSP</body-content>
		<attribute>
			<name>test</name>
			<required>true</required>
			<!-- rtexprvalue : Element to specify whether the custom tag attribute can use JSP expressions or EL -->
			<!-- => false : Not allowed (default), true : Allowed -->
			<rtexprvalue>true</rtexprvalue>
		</attribute>
	</tag>
</taglib>
```

### Summary of the Translation:

- **Tag Library Descriptor (TLD)**: A file used to register custom tags and their attributes for use in JSP documents.
- **Description**: Provides a description of the custom tag library, which in this case is for implementing simple custom tags.
- **Tag Definitions**:
  - **`hello`**: A custom tag with no body content. The class associated with this tag is `HelloTag`.
  - **`helloAttribute`**: A custom tag with a required attribute `name`. The class associated with this tag is `HelloAttributeTag`.
  - **`helloBody`**: A custom tag that allows JSP content in the body and has a required attribute `test`. The class associated with this tag is `HelloBodyTag`. The attribute `test` can use JSP expressions or EL.

---


### Custom Tag Class: `HelloTag.java`


```java
package xyz.itwill.custom;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

// Tag class: The class that is instantiated when a custom tag is used in a JSP document.
// => Inherits from one of the TagSupport, BodyTagSupport, or SimpleTagSupport classes.
// => Methods called when using the custom tag should be overridden to handle requests or responses.

public class HelloTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    // Called only once to create the tag class object when the custom tag is first used in the JSP document
    // => Initialization work is done here
    public HelloTag() {
        //System.out.println("HelloTag class default constructor called - Object created");
    }

    // Automatically called each time the start tag of the custom tag is used in the JSP document
    @Override
    public int doStartTag() throws JspException {
        //System.out.println("HelloTag class doStartTag() method called");

        try {
            // Retrieve the PageContext object from the parent class to get related objects and perform operations
            // pageContext.getOut(): Method to get the output stream (JspWriter object) to create the response document
            pageContext.getOut().println("<div>Hello.</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return value from doStartTag() method: Use one of the constant fields (integer values) defined in the parent class
        // => Return either SKIP_BODY or EVAL_BODY_INCLUDE
        // SKIP_BODY: Use this constant if the tag body should not be sent to the client (default value)
        // EVAL_BODY_INCLUDE: Use this constant if the tag body should be sent to the client
        return SKIP_BODY;
    }

    // Automatically called each time the body of the custom tag is used in the JSP document
    @Override
    public int doAfterBody() throws JspException {
        //System.out.println("HelloTag class doAfterBody() method called");

        // Return value from doAfterBody() method: Use one of the constant fields (integer values) defined in the parent class
        // => Return either SKIP_BODY or EVAL_BODY_AGAIN
        // SKIP_BODY: Use this constant if the tag body should not be sent to the client again (default value)
        // EVAL_BODY_AGAIN: Use this constant if the tag body should be sent to the client again (repeated output)
        return SKIP_BODY;
    }

    // Automatically called each time the end tag of the custom tag is used in the JSP document
    @Override
    public int doEndTag() throws JspException {
        //System.out.println("HelloTag class doEndTag() method called");

        // Return value from doEndTag() method: Use one of the constant fields (integer values) defined in the parent class
        // => Return either SKIP_PAGE or EVAL_PAGE
        // SKIP_PAGE: Use this constant if the JSP document should be terminated forcibly after using the custom tag
        // EVAL_PAGE: Use this constant if the JSP document should continue executing after using the custom tag (default value)
        return SKIP_PAGE;
    }
}
```

### Summary of the Translation:

- **Tag Class**: `HelloTag` is a custom tag class used in JSP documents. It extends `TagSupport` and overrides methods to handle the custom tag's lifecycle.
- **Constructor**: Initializes the tag class object when the tag is first used.
- **`doStartTag()` Method**: Called when the start tag of the custom tag is encountered. It outputs "Hello." to the response and decides whether to process the tag body or skip it.
- **`doAfterBody()` Method**: Called after the tag body has been processed. It decides whether to process the body again or skip it.
- **`doEndTag()` Method**: Called when the end tag of the custom tag is encountered. It determines whether to continue processing the JSP page or terminate it.
custom folder-> hello_tag.jsp
package- > xyz.itwill.custom->HelloTag.java

### hello_tag.java


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="simple" uri="https://www.itwill.xyz/mvc/custom"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>Custom Tag - No Attribute And No Body</h1>
	<hr>
	<%-- Custom Tag: A tag created by developers for use in JSP documents --%>
	<%-- => Write a tag class and register the tag class as a custom tag in the TLD file, then use
	taglib directive in the JSP document to include the TLD file and use the custom tag in the JSP --%>
	<%-- => When using a custom tag in a JSP document, the tag class is instantiated, and the tag's 
	related methods are automatically called to execute commands - managed by the WAS program --%>
	<simple:hello></simple:hello>
	<%-- Custom tags without body content can be implemented with both start and end tags simultaneously --%>
	<simple:hello/>
	<simple:hello/>
</body>
</html>
```

#### HelloAttributeTag.java


```java
package xyz.itwill.custom;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

// Class to create a custom tag with attributes and no body content
public class HelloAttributeTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	// Field to store the attribute value of the custom tag
	// => Field name should match the attribute name of the custom tag
	private String name;
	
	// Initialize the field with default value when creating the tag class object
	public HelloAttributeTag() {
		// Set the default value to the field when the custom tag attribute is omitted
		// => If the custom tag attribute is set as mandatory, initializing the field with default value is optional
		name = "Hong Gil-dong";
	}

	public String getName() {
		return name;
	}

	// Automatically called Setter method when setting the attribute value of the custom tag
	public void setName(String name) {
		this.name = name;
	}
	
	// Override the method to write the commands to be executed when the custom tag is used
	// => Methods not overridden will automatically call the parent class's methods with no commands
	@Override
	public int doStartTag() throws JspException {
		try {
			if (name.equals("Hong Gil-dong")) {
				pageContext.getOut().println("<h3>Hello, Administrator.</h3>");
			} else {
				pageContext.getOut().println("<h3>Hello, " + name + ".</h3>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
```



#### hello_attribute_tag.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="simple" uri="https://www.itwill.xyz/mvc/custom"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
	<h1>Custom Tag - Any Attribute And No Body</h1>
	<hr>
	<%-- If a custom tag's attribute is not mandatory, the attribute can be omitted --%>
	<%-- => If the attribute is omitted, the default value set in the tag class constructor will be used --%>
	<%-- Omitting a mandatory attribute will result in an error --%>
	<%-- <simple:helloAttribute/> --%>
	<simple:helloAttribute name="Hong Gil-dong"/>
	
	<%-- When using the custom tag's attribute and setting its value, the tag class's Setter method 
	is automatically called to change the field value to the set attribute value --%>
	<simple:helloAttribute name="Im Geok-jeong"/>
</body>
</html>
```

class -> HelloAttributeTag.java->add tag to custom.tld->hello_attribute_tag.jsp

---
#### HelloBodyTag.java

Here's the translated version of the provided Java code into English:

```java
package xyz.itwill.custom;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

// Class to create a custom tag with attributes and body content
public class HelloBodyTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private boolean test;

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			if (test) {
				pageContext.getOut().println("<h3>");
			} else {
				pageContext.getOut().println("<p>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE; // Pass the body content to the client for response processing
	}
	
	@Override
	public int doEndTag() throws JspException {
		try {
			if (test) {
				pageContext.getOut().println("Hello.</h3>");
			} else {
				pageContext.getOut().println("Hello.</p>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
```

### Explanation:
- **Class Purpose**: This class creates a custom tag with both attributes and body content.
- **Attributes**:
  - `test`: A boolean attribute used to determine the HTML tag to be used.
- **Methods**:
  - `doStartTag()`: Outputs either an `<h3>` or `<p>` tag at the start based on the value of `test`, and includes the body content in the response.
  - `doEndTag()`: Completes the HTML tag started by `doStartTag()` with a closing tag and outputs a greeting message.

The comments in the code help explain the functionality of each part in the context of custom tag creation in JSP.
#### hello_body_tag.jsp
```xml
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="simple" uri="https://www.itwill.xyz/mvc/custom"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
<h1>Custom Tag - AnyAttribute And AnyBody</h1>
<hr>
<simple:helloBody test="true">Mr,Hong</simple:helloBody>
<simple:helloBody test="false">Mr,Lim</simple:helloBody>
<%
String name="Mr,Jun";
request.setAttribute("name","Mr,Il");
%>
<simple:helloBody test="true"><%=name %></simple:helloBody>
<simple:helloBody test="false">${name }</simple:helloBody>
<hr>
<%
boolean result=true;
request.setAttribute("result", false);
%>
<simple:helloBody test="<%=result %>">Mr,Jang</simple:helloBody>
<simple:helloBody test="${result }">Mr,Hong2</simple:helloBody>
</body>
</html>
```
HelloBodyTag.java->add tag to custom.tld->hello_body_tag.jsp