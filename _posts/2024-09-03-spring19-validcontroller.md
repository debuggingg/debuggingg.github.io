---
layout: single
title: 2024-09-03-spring19-validcontroller
---

#### Program Flow 
1. pom.xml ->  validation-api, hibernate-validator
2. Write JSP documents using Spring tags 
	- ex) form tag liberary (<%@taglib prefix="form" ) , and when you use it like - ( <form:form action="${url}") 
3. @Valid annotation on the parameter containing -request handling method to validate the fields of the Command object
4. Use validation-related annotations on the fields of the DTO class containing the Command object to validate the submitted values

---

### ValidController.java


```java
package xyz.itwill09.controller;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.itwill09.dto.Employee;

// Using Spring Data Validation to validate the field values (submitted values) of the Command object
// 1. Include validation-api and hibernate-validator libraries in the project build - Maven usage: pom.xml
// 2. Write JSP documents using Spring tags instead of HTML tags - for handling and displaying error messages
// 3. Use the @Valid annotation on the parameter containing the Command object in the request handling method to validate the fields of the Command object
// 4. Use validation-related annotations on the fields of the DTO class containing the Command object to validate the submitted values

@Controller
//@RequestMapping annotation on the class provides a common URL prefix for all request handling methods in the Controller class
@RequestMapping("/valid")
public class ValidController {

    //@RequestMapping(value = "/valid/html", method = RequestMethod.GET)
    @RequestMapping(value = "/html", method = RequestMethod.GET)
    public String html() {
        return "valid/html_form";        
    }
    
    //@RequestMapping(value = "/valid/html", method = RequestMethod.POST)
    @RequestMapping(value = "/html", method = RequestMethod.POST)
    public String html(@ModelAttribute Employee employee, Model model) {
        if(employee.getId() == null || employee.getId().equals("")) {
            model.addAttribute("idMsg", "Please enter an ID");
            return "valid/html_form";        
        }
        
        String idReg="^[a-zA-Z]\\w{5,19}$";
        if(!Pattern.matches(idReg, employee.getId())) {
            model.addAttribute("idMsg", "Please enter the ID in the correct format");
            return "valid/html_form";        
        }
        
        return "valid/result";        
    }
    
    //@RequestMapping(value = "/valid/spring", method = RequestMethod.GET)
    @RequestMapping(value = "/spring", method = RequestMethod.GET)
    public String spring(@ModelAttribute Employee employee, Model model) {
        //Arrays.asList(Object ... args) : Creates and returns a List object with the passed values (objects) as elements
        //model.addAttribute("genderList", Arrays.asList("Male", "Female"));
        return "valid/spring_form";        
    }
    
    //When the parameter type of the request handling method is Errors, you receive an Errors object from the Front Controller
    // => Errors object: Contains all error-related information that occurs in case of validation failures
    //@RequestMapping(value = "/valid/spring", method = RequestMethod.POST)
    @RequestMapping(value = "/spring", method = RequestMethod.POST)
    public String spring3(@ModelAttribute @Valid Employee employee, Errors errors, Model model) {
        //Errors.hasErrors() : Returns [true] if there are any error-related information in the Errors object
        if(errors.hasErrors()) {
            //model.addAttribute("genderList", Arrays.asList("Male", "Female"));
            return "valid/spring_form";        
        }
        
        return "valid/result";        
    }
    
    //Method return value is provided to all request methods of the Controller class's view
    @ModelAttribute("genderList")
    public List<String> genderList() {
        return Arrays.asList("Male", "Female");
    }
}
```
#### pom.xml
```xml
<!-- https://mvnrepository.com/artifact/javax.validation/validation-api --> <!-- => Provides validation functionality for the values stored in the fields of Command objects --> <dependency> <groupId>javax.validation</groupId> <artifactId>validation-api</artifactId> <version>2.0.1.Final</version> </dependency> <!-- https://mvnrepository.com/artifact/org.hibernate.validator/hibernate-validator --> <!-- => Provides validation functionality for the values stored in the fields of Command objects --> <dependency> <groupId>org.hibernate.validator</groupId> <artifactId>hibernate-validator</artifactId> <version>6.2.5.Final</version> </dependency>
```
### Employee.java

Here's the English translation for the `Employee` class with its annotations and comments:

```java
package xyz.itwill09.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Employee {
	//@NotNull: Annotation that triggers an error if the provided value is [null]
	//@NotBlank: Annotation that triggers an error if the provided value is [null] or contains whitespace
	//@NotEmpty: Annotation that triggers an error if the provided value is [null] or empty (NullString)
	//message attribute: Sets the error message that will be provided to the view
	// => If the message attribute is omitted, the default error message is provided to the view for display
	@NotEmpty(message = "Please enter an ID.")
	//@Size: Annotation that triggers an error based on the size (number of characters) of the provided value
	//min attribute: Sets the minimum size of the provided value
	//max attribute: Sets the maximum size of the provided value
	//@Size(min = 6, max = 20, message = "ID must be between 6 and 20 characters long.")
	//@Pattern: Annotation that triggers an error based on the character pattern of the provided value
	//regexp attribute: Sets the regular expression to compare the provided value
	@Pattern(regexp = "^[a-zA-Z]\\w{5,19}$", message = "Please enter an ID that matches the format.")
	private String id;
	//@NotEmpty(message = "Please enter a password.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*_-]).{6,20}$"
			, message = "Please enter a password that matches the format.")
	private String passwd;
	@NotEmpty(message = "Please enter a name.")
	private String name;
	@NotEmpty(message = "Please enter an email.")
	//@Email: Annotation that triggers an error based on email format
	@Email(message = "Please enter an email that matches the format.")
	private String email;
	@NotEmpty(message = "Please enter a gender.")
	private String gender;
}
```
### Comments Translation:
- `@NotNull`: An annotation that triggers an error if the submitted value is `null`.
- `@NotBlank`: An annotation that triggers an error if the submitted value is `null` or contains whitespace.
- `@NotEmpty`: An annotation that triggers an error if the submitted value is `null` or empty (null string).
- `message attribute`: Specifies the error message to be provided to the view.
  - If omitted, a default error message is used.

The `@Pattern` annotation is used to specify that the `id` field must match a given regular expression, with a custom error message if the validation fails. The `@NotEmpty` annotations are used to ensure that other fields are not empty, each with a specific error message if validation fails.

### spring_form.jsp


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
.error { color: red; }
</style>
</head>
<body>
    <h1>Employee Registration</h1>
    <hr>
    <c:url value="/valid/spring" var="url"/>
    <%-- Spring form tag: A tag for requesting the page and passing input values --%>
    <%-- modelAttribute attribute (required): Sets the attribute name of the Command object stored in the request handling method as its value --%>
    <%-- => If validation fails, the Command object with the submitted values is provided for using the input tag's values --%>
    <form:form action="${url}" method="post" id="registerForm" modelAttribute="employee">
    <table>
        <tr>
            <td>ID</td>
            <td>
                <%-- Spring input tag: A tag for receiving and passing string values --%>
                <%-- => Equivalent to an HTML input tag with type attribute set to [text] --%>
                <%-- path attribute: Sets the name for passing the input value --%>
                <%-- => Equivalent to setting the name and id attributes of an HTML input tag --%>
                <%-- => In case of validation failure, the Command object's field value is provided as the value attribute --%>
                <form:input path="id"/>
                <%-- Spring errors tag: A tag for displaying error messages provided by the request handling method --%>
                <%-- path attribute: Sets the identifier (name of the submitted value) to receive and display the error message --%>
                <%-- cssClass attribute: Sets the CSS style class selector, equivalent to the class attribute in HTML --%>
                <%-- element attribute: Sets the tag name for displaying the error message --%>
                <%-- delimiter attribute: Sets the character for separating multiple error messages --%>
                <form:errors path="id" cssClass="error" element="span" delimiter=" "/>
                <span id="idMsg" class="error"></span>
            </td>
        </tr>
        <tr>
            <td>Password</td>
            <td>
                <%-- Spring password tag: A tag for receiving and passing string values --%>
                <%-- => Equivalent to an HTML input tag with type attribute set to [password] --%>
                <form:password path="passwd"/>
                <form:errors path="passwd" cssClass="error" element="span" delimiter=" "/>
                <span id="passwdMsg" class="error"></span>
            </td>
        </tr>
        <tr>
            <td>Name</td>
            <td>
                <form:input path="name"/>
                <form:errors path="name" cssClass="error" element="span" delimiter=" "/>
                <span id="nameMsg" class="error"></span>
            </td>
        </tr>
        <tr>
            <td>Email</td>
            <td>
                <form:input path="email"/>
                <form:errors path="email" cssClass="error" element="span" delimiter=" "/>
                <span id="emailMsg" class="error"></span>
            </td>
        </tr>
        <tr>
            <td>Gender</td>
            <td>
                <%-- Spring radiobutton tag: A tag for selecting one value from a list --%>
                <%-- => Equivalent to an HTML input tag with type attribute set to [radio] --%>
                <%-- 
                Male<form:radiobutton path="gender" cssClass="gender" value="Male"/>&nbsp;&nbsp;
                Female<form:radiobutton path="gender" cssClass="gender" value="Female"/>
                --%>
                
                <%-- Spring radiobuttons tag: A tag for selecting one value from a list provided by the request handling method --%>
                <%-- => Uses the list of items provided by the request handling method as the options for input --%>
                <%-- items attribute: Sets the list object containing the options as its value --%>
                <form:radiobuttons path="gender" items="${genderList}"/>
                <form:errors path="gender" cssClass="error" element="span" delimiter=" "/>
                <span id="genderMsg" class="error"></span>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <%-- Spring button tag: A tag for triggering a submit event --%>
                <%-- => Equivalent to an HTML button tag with type attribute set to [submit] --%>
                <form:button>Register</form:button>            
            </td>
        </tr>
    </table>
    </form:form>
    
    <%-- 
    <script type="text/javascript">
    $("#registerForm").submit(function() {
        var validResult = true;
        
        $(".error").html("").hide();
        
        var idReg = /^[a-zA-Z]\w{5,19}$/g;
        if ($("#id").val() == "") {
            $("#idMsg").html("Please enter an ID.");
            validResult = false;
        } else if (!idReg.test($("#id").val())) {
            $("#idMsg").html("Please enter the ID in the correct format.");
            validResult = false;
        }

        var passwdReg = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*_-]).{6,20}$/g;
        if ($("#passwd").val() == "") {
            $("#passwdMsg").html("Please enter a password.");
            validResult = false;
        } else if (!passwdReg.test($("#passwd").val())) {
            $("#passwdMsg").html("Please enter the password in the correct format.");
            validResult = false;
        } 
        
        if ($("#name").val() == "") {
            $("#nameMsg").html("Please enter a name.");
            validResult = false;
        }
        
        var emailReg = /^([a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+(\.[-a-zA-Z0-9]+)+)*$/g;
        if ($("#email").val() == "") {
            $("#emailMsg").html("Please enter an email.");
            validResult = false;
        } else if (!emailReg.test($("#email").val())) {
            $("#emailMsg").html("Please enter the email in the correct format.");
            validResult = false;
        }

        if ($(".gender").filter(":checked").length == 0) {
            $("#genderMsg").html("Please select a gender.");
            validResult = false;
        }
        
        $(".error").show();
        
        return validResult;
    });
    </script>
    --%>
</body>
</html>
```


