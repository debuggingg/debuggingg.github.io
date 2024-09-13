---
layout: single
title: 2024-09-11-spring32-RESTful
---
##  REST (Representational State Transfer)
#### pom.xml
 A library providing functionality to convert Java objects to JSON format strings

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<!-- => A library providing functionality to convert Java objects to JSON format strings -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.2</version>
</dependency>
```

#### RestfulController.java 
```java
package xyz.itwill09.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.itwill09.dto.RestMember;

// REST (Representational State Transfer): It means transferring the state by the representation of the resource
// => Responding to client requests in XML or JSON format documents
// Restful API: A program written to securely exchange values between two computer systems using REST functionalities
// => Used to deliver the information needed for smart device programs (apps) or provide the execution results

@Controller
@RequestMapping("/rest")
public class RestfulController {
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "rest/input";
	}
	
	/*
	// On page requests, the request handling method is called, which processes the request and stores the result in a Model object
	// => The model's attribute values are stored with the request scope and the view name is returned to respond with the view
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@RequestParam String id, @RequestParam String name, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		return "rest/output";
	}
	*/
	
	//@ResponseBody: An annotation that saves the return value of the request handling method into the response message body and delivers it to the client as a plain document
	// => Instead of the Front Controller converting the return value of the request handling method into a view, it directly responds with the return value of the request handling method
	// => Used to respond to page requests in JSON format string
	//@RequestBody: An annotation used to provide all the transmitted values stored in the request message body as a string
	// => When a page is requested with POST, PUT, PATCH, DELETE methods, it receives all transmitted values in the request message body as a string
	// => @RequestBody annotation cannot be used with GET requests as the request message body is empty
	// => Used to provide values transmitted in JSON format in the request message body to the method parameters
	// => Alternatively, RequestEntity class can be used as the parameter type to receive and store the transmitted values
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@ResponseBody
	public String join(@RequestBody String input) {
		return input;
	}
	
	/*
	//@ResponseBody annotation is used to respond with the return value of the request handling method (RestMember object) as a string in the response message body
	//Problem: Java objects cannot be stored in the response message body directly, resulting in a 406 error code
	//Solution: Convert the Java object returned by the request handling method to a string (XML or JSON) and store it in the response message body
	// => If the jackson-databind library is included in the project, Java objects can be converted to JSON format string and stored in the response message body
	@RequestMapping("/member")
	@ResponseBody
	public RestMember restMember() {
		// Convert RestMember object to JavaScript Object for response
		return RestMember.builder().id("abc123").name("홍길동").email("abc@itwill.xyz").build();
	}
	*/
	
	//@ResponseBody annotation can be replaced by setting the return type of the request handling method to ResponseEntity class
	// => The ResponseEntity object can be returned as a string response
	// => The generic of the ResponseEntity class specifies the data type of the Java object to be processed in the response
	@RequestMapping("/member")
	public ResponseEntity<RestMember> restMember() {
		try {
			RestMember member = RestMember.builder().id("abc123").name("홍길동").email("abc@itwill.xyz").build();
			return new ResponseEntity<RestMember>(member, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RestMember>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/member_list")
	@ResponseBody
	public List<RestMember> restMemberList() {
		List<RestMember> list = new ArrayList<RestMember>();
		list.add(RestMember.builder().id("abc123").name("홍길동").email("abc@itwill.xyz").build());
		list.add(RestMember.builder().id("opq456").name("임꺽정").email("opq@itwill.xyz").build());
		list.add(RestMember.builder().id("xyz789").name("전우치").email("xyz@itwill.xyz").build());
		// Convert List object to JavaScript Array for response
		// => Elements of the List (RestMember objects) are converted to JavaScript Object
		return list;
	}
	
	@RequestMapping("/member_map")
	@ResponseBody
	public Map<String, Object> restMemberMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "abc123");		
		map.put("name", "홍길동");		
		map.put("email", "abc@itwill.xyz");		
		// Convert Map object to JavaScript Object for response
		return map;
	}
	
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String restBoard() {
		return "rest/board";
	}
}
```