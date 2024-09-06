---
layout: single
title: 2024-08-29-spring16-mvc-annotation
---
## First Path (auto created )
#### src/main/webapp/spring/appServlet/servlet-context.xml

```xml
	<!-- Processes application requests -->
	<servlet>
		<servlet-name>appServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
		
	<servlet-mapping>
		<servlet-name>appServlet</servlet-name>
	<!-- If the value of the url-pattern element is set to [/], the servlet (Front Controller) will handle all client web resource requests -->
		<url-pattern>/</url-pattern>
	</servlet-mapping>
```
#### src/main/webapp/WEB-INF/view (For JSP files )
**EX**
**Input** 
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>
</head>
<body>
<h1>input Page</h1>
<hr>
<form action="param" method="post">
	Food Name : <input type="text" name="food"/>
	<button type="submit">submit</button>
</form>
</body>
</html>
```

**output**
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring</title>
</head>
<body>
<h1>output Page</h1>
<hr>
<h2>tomorrow,  will get ${food}.</h2>
</body>
</html>
```

#### HelloController.java


```java
package xyz.itwill09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

// @Controller: Annotation to register the request handling class (Controller class) as a Spring Bean
// => The class name is used as the identifier (beanName) for the Spring Bean - the first letter is converted to lowercase
// => The identifier (beanName) can be changed using the value attribute
// By using the @Controller annotation, the class can be used as a request handling class without inheriting from the Controller interface
// => The @RequestMapping annotation is used to specify request handling methods
// => Multiple request handling methods can be registered using the @RequestMapping annotation
@Controller
@Slf4j
public class HelloController {
    // Request handling methods must provide a view name to the Front Controller
    // => The Front Controller uses the provided view name to handle the response with the ViewResolver object
    // 1. If the return type of the request handling method is [void], the value of the @RequestMapping annotation
    //    is used as the view name
    // 2. If the return type of the request handling method is [String], the return value (string) of the method is used as the view name
    // 3. If the return type of the request handling method is [ModelAndView], the view name is stored in the ModelAndView object and provided

    //@RequestMapping: Annotation to set a method of the Controller class as a request handling method
    // => Used when creating a request handling method called by all client request methods (GET, POST, PUT, PATCH, DELETE, etc.)
    // => For distinguishing client request methods, annotations such as @GetMapping, @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping can be used
    // value attribute: Sets the client's request information (Command - URL address) as the attribute value
    // => If there are no other attributes besides the value attribute, only the attribute value can be set
    // => When a request is made to the URL address set as the value attribute, the request handling method is called to execute the command
    // => If the value attribute of the @RequestMapping annotation is duplicated, an error occurs when the WAS program is run
    @RequestMapping(value = "/hello")
    public void hello() {
        log.info("[/hello] Page request: HelloController class's hello() method called");
    }
    
    @RequestMapping("/helloViewName")
    public String helloViewName() {
        log.info("[/helloViewName] Page request: HelloController class's helloViewName() method called");
        return "hello";
    }
    
    @RequestMapping("/helloMav")
    public ModelAndView helloModelAndView() {
        log.info("[/helloMav] Page request: HelloController class's helloModelAndView() method called");

        /*
        // ModelAndView object: An object used to store the processing result and view name
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello"); // Store the view name using the setter method
        return modelAndView;
        */
        
        ModelAndView modelAndView = new ModelAndView("hello"); // Store the view name using the constructor
        return modelAndView;
    }
}
```
---
#### ResultController.java


```java
package xyz.itwill09.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// Methods for providing the result of a request handling method to the view (JSP):
// 1. Use the ModelAndView object to call the addObject() method and provide the result as an attribute value
// 2. Use the HttpServletRequest object to call the setAttribute() method and provide the result as an attribute value
// 3. Use the Model object to call the addAttribute() method and provide the result as an attribute value

@Controller
public class ResultController {
    /*
    @RequestMapping("/resultMav")
    public ModelAndView modelAndViewResult() {
        ModelAndView modelAndView = new ModelAndView("result_display");
        // ModelAndView.addObject(String attributeName, Object attributeValue): Method to store the result of the request handling method
        // as an attribute value in the ModelAndView object - Request Scope
        // => The view (JSP) receives the attribute value as an object using EL (Expression Language) for response processing
        modelAndView.addObject("mavName", "Hong Gil-dong");
        return modelAndView;        
    }
    */
    
    // When parameters are written in a request handling method, the Front Controller provides objects from the Spring container (WebApplicationContext
    // object) to be used and stored in the parameters
    @RequestMapping("/resultMav")
    public ModelAndView modelAndViewResult(ModelAndView modelAndView) {
        modelAndView.setViewName("result_display");
        modelAndView.addObject("mavName", "Hong Gil-dong");
        return modelAndView;        
    }
    
    @RequestMapping("/resultRequest")
    public String requestResult(HttpServletRequest request) {
        // HttpServletRequest.setAttribute(String attributeName, Object attributeValue): Method to store the result of the request handling method
        // as an attribute value in the HttpServletRequest object - Request Scope
        request.setAttribute("requestName", "Im Geok-jeong");
        return "result_display";
    }
    
    @RequestMapping("/resultModel")
    public String modelResult(Model model) {
        // Model object: An object used to provide the result of the request handling method to the view
        // Model.addAttribute(String attributeName, Object attributeValue): Method to store the result of the request handling method
        // as an attribute value in the Model object - Request Scope
        model.addAttribute("modelName", "Jeon Woo-chi");
        return "result_display";
    }
}
```
---
#### ModelController.java

```java
package xyz.itwill09.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ModelController {
    @RequestMapping("/display1")
    public String display1(Model model) {
        model.addAttribute("displayName1", "Hong Gil-dong");
        // model.addAttribute("now", new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date()));
        return "model_display1";
    }
    
    @RequestMapping("/display2")
    public String display2(Model model) {
        model.addAttribute("displayName2", "Im Geok-jeong");
        // model.addAttribute("now", new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date()));
        return "model_display2";
    }
    
    @RequestMapping("/display3")
    public String display3(Model model) {
        model.addAttribute("displayName3", "Jeon Woo-chi");
        // model.addAttribute("now", new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date()));
        return "model_display3";
    }

    //@ModelAttribute: Annotation used to provide the return value of the method as an attribute to all request handling methods in the current Controller class
    // => This annotation is only applied when used on a method
    // value attribute: Sets the attribute name for the return value of the method to be used in the view
    // => If there are no other attributes besides the value attribute, only the attribute value can be set
    @ModelAttribute("now")
    public String getNow() {
        return new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date());
    }
}
```
#### MethodController.java

```java
package xyz.itwill09.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MethodController {
    // Request handling method that returns the view name of the JSP document for input from the user
    @RequestMapping("/method_input")
    public String inputOne() {
        return "method_input1";
    }
    
    // Method that returns the view name of the JSP document where the result, stored as a Request Scope attribute, can be provided and displayed
    // => Configured to execute the command only when the request is made with the POST method
    @RequestMapping("/method_output")
    public String outputOne(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        // When the client requests the page with the GET method - abnormal request
        if(request.getMethod().equals("GET")) {
            // response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            response.sendRedirect("method_input");
            return null;
        }
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        request.setAttribute("name", name);
        return "method_output";
    }   
    
    // Request handling method that returns the view name of the JSP document for input from the user
    // => Can be configured to call the request handling method only when the client requests the page with the GET method
    // method attribute: Sets one of the constant fields of RequestMethod (Enum type) as the attribute value
    // => RequestMethod (Enum type) provides constant fields for client request methods
    // => If the page is requested with a request method different from the method attribute value, a 405 error code will be used for response handling
    // Can use @GetMapping annotation instead of @RequestMapping annotation to configure the request handling method to be called only for GET requests
    @RequestMapping(value = "/method", method = RequestMethod.GET)
    public String inputTwo() {
        return "method_input2";
    }
    
    // Method that returns the view name of the JSP document where the result, stored as a Request Scope attribute, can be provided and displayed
    // => Configured to execute the command only when the request is made with the POST method using @RequestMapping annotation
    // => No error occurs even if the value attribute of @RequestMapping annotation is duplicated, as long as the method attribute value is different
    // Can use @PostMapping annotation instead of @RequestMapping annotation to configure the request handling method to be called only for POST requests
    @RequestMapping(value = "/method", method = RequestMethod.POST)
    public String outputTwo(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        request.setAttribute("name", name);
        return "method_output";
    }   
}
```
---
#### ParamController.java

```java
package xyz.itwill09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ParamController {
    // Request handling method that returns the view name of the JSP document for the form input
    @RequestMapping(value = "/param", method = RequestMethod.GET)
    public String form() {
        return "param_form";
    }
    
    /*
    // When a page request is made, the method is called with the HttpServletRequest object to retrieve and use the passed values
    @RequestMapping(value = "/param", method = RequestMethod.POST)
    public String action(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String food = request.getParameter("food");
        request.setAttribute("food", food);
        return "param_display";
    }
    */

    // When a page request is made, if the parameter name matches the name of the passed value,
    // the Front Controller will provide the passed value and store it in the parameter for use
    // => If the name of the passed value and the parameter name do not match, the parameter will be stored as [null]
    // => If the parameter type is not String, or if the passed value does not match the parameter name or type,
    // a 400 error code will occur
    // To provide the passed value to the parameter, ensure that character encoding is set correctly
    // Use an encoding filter
    // Filter: A web program that provides necessary functionality before or after the execution of the Front Controller
    // => Write a subclass that inherits from the Filter interface and register the Filter class in the [web.xml] file
    // and configure the URL address (pattern) for which the filter can be executed
    @RequestMapping(value = "/param", method = RequestMethod.POST)
    public String action(String food, Model model) {
        model.addAttribute("food", food);
        return "param_display";
    }
}
```
#### web.XML Setteing  for UTF-8 Setting (encoding)


```xml
<!-- filter: Element to register a class as a filter -->
<filter>
    <!-- filter-name: Element to set an identifier for the filter -->
    <filter-name>encodingFilter</filter-name>
    <!-- filter-class: Element to set the filter class to be instantiated for servlet requests -->
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <!-- init-param: Element to provide necessary values to the filter class -->
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
</filter>

<!-- filter-mapping: Element to register the servlet request URL address for which the filter will be executed -->
<filter-mapping>
    <!-- filter-name: Element to set the identifier of the filter to be executed by the servlet request -->
    <filter-name>encodingFilter</filter-name>
    <!-- url-pattern: Element to set the URL address (pattern) of the servlet requests for which the filter will be executed -->
    <!-- => Configures the filter to be executed for all client requests -->
    <url-pattern>/*</url-pattern>
</filter-mapping>
```