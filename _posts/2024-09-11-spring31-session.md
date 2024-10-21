---
layout: single
title: 2024-09-11-spring31-session
---
#### Login without Session 
#### Setup Interceptor 
#### sevlet-context.xml

```xml
<!-- interceptors: Element used to register interceptor elements -->
<interceptors>
    <!-- interceptor: Element providing rules to call the methods of the Interceptor object through the Front Controller -->
    <interceptor>
        <!-- mapping: Element providing the request page paths where the interceptor will be active -->
        <!-- path attribute: Sets the request page path as an attribute value -->
        <mapping path="/userinfo/write"/>
        <mapping path="/userinfo/modify"/>
        <mapping path="/userinfo/remove"/>
        <!-- ref: Element providing the Interceptor object used by the Front Controller -->
        <!-- bean attribute: Sets the Spring Bean identifier (beanName) of the Interceptor class as an attribute value -->
        <beans:ref bean="adminAuthInterceptor"/>
    </interceptor>  
    
    <interceptor>
        <!--
        <mapping path="/userinfo/list"/>
        <mapping path="/userinfo/view"/>
        <beans:ref bean="loginAuthInterceptor"/>
        -->
        
        <!-- 
        The path attribute of the mapping element can use the [*] symbol to set request pages -->
        <!-- [*]: Requests for all pages in the folder -->
        <!-- <mapping path="/*"/> -->
        <!-- [**]: Requests for all pages in the folder and its subfolders -->
        <!-- <mapping path="/**"/> -->
        <mapping path="/userinfo/*"/>
        <!-- exclude-mapping: Element providing the request page paths where the interceptor will not be active -->
        <exclude-mapping path="/userinfo/login"/> 
        <beans:ref bean="loginAuthInterceptor"/>
    </interceptor>  
</interceptors>
```


#### Interceptor Class 
#### AdminAuthInterceptor.java

```java
package xyz.itwill09.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import xyz.itwill09.dto.Userinfo;
import xyz.itwill09.exception.BadRequestException;

// Interceptor: Provides functionality to execute commands before or after the request handling method
// => Implement the Interceptor class by inheriting the HandlerInterceptor interface and register it as a Spring Bean in
// the Spring Bean Configuration File (servlet-context.xml) for use as an interceptor
// => Override only the necessary methods from the default methods of the HandlerInterceptor interface
// => Filters are executed before the Front Controller, whereas interceptors are executed after the Front Controller
// - Filters are managed by the WAS program, while interceptors are managed by the Front Controller

// An Interceptor class created for managing admin-related permissions
// => Throws a manual exception if a non-logged-in user or a user who is not an admin requests a page
public class AdminAuthInterceptor implements HandlerInterceptor {
	// Method to write commands to be executed before the request handling method is called
	// => Returns false: the request handling method is not called, true: the request handling method is called
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();

		Userinfo loginUserinfo = (Userinfo) session.getAttribute("loginUserinfo");

		if (loginUserinfo == null || loginUserinfo.getAuth() != 9) {
			// Forwarding and redirecting are possible
			// response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			// return false;

			throw new BadRequestException("The page was requested in an abnormal manner.");
		}

		return true;
	}

	// Method to write commands to be executed after the request handling method is called
	// => Used to write commands to be executed before creating the view from the return value (ViewName) of the request handling method
	// => Used to modify information stored in the ModelAndView object
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}


	// Method to write commands to be executed after the request handling method is called
	// => Used to write commands to be executed after creating the view from the return value (ViewName) of the request handling method
	// => Used to modify view-related information
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
```



#### LoginAuthInterceptor.java


```java
package xyz.itwill09.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import xyz.itwill09.dto.Userinfo;
import xyz.itwill09.exception.BadRequestException;

// Interceptor class created for managing login user-related permissions
// => Throws a manual exception if a non-logged-in user requests a page before the request handling method is called
public class LoginAuthInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();

		Userinfo loginUserinfo = (Userinfo) session.getAttribute("loginUserinfo");

		if (loginUserinfo == null) {
			throw new BadRequestException("The page was requested in an abnormal manner.");
		}

		return true;
	}
}
```

#### How to Use
In the `servlet-context.xml`, methods that map to `/userinfo` are pre-configured to handle requests with a session containing login information. This setup ensures that only requests with user information in the session are processed automatically. Requests without user information are intercepted and handled by an exception class, which redirects such requests to an error page.

```java
public class UserinfoController {
	private final UserinfoService userinfoService;

@RequestMapping(value = "/write", method = RequestMethod.GET)
public String write() {
//    public String write(HttpSession session) {
//    Userinfo loginUserinfo = (Userinfo) session.getAttribute("loginUserinfo");
//
//    // Throws a manual exception if the user requesting the page is not logged in or is not an admin
//    if (loginUserinfo == null || loginUserinfo.getAuth() != 9) {
//        throw new BadRequestException("The page was requested in an abnormal manner.");
//    }
    return "userinfo/user_write";
}
}
```