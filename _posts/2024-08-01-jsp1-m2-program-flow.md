---
layout: single
title: 2024/08/01 JSP-M2-Program-Flow
---

### Overview of Request Handling and Service Design in Model 2 Architecture

1. **Servlet and Action Classes**
   - **ControllerServlet**: This Servlet is responsible for handling all incoming HTTP requests and mapping them to appropriate actions.
   - **Action Interface and Classes**: The `ControllerServlet` forwards requests to classes implementing the `Action` interface (or its specific implementations like `ActionForm`). These action classes handle business logic and decide on the response. They have an `execute(HttpServletRequest request, HttpServletResponse response)` method to process the request and return the result.

2. **Process Flow**
   - **Client Request Handling**: When `ControllerServlet` receives a request, it determines the correct `Action` class based on the URL pattern.
   - **URL Mapping**: URL patterns are mapped to specific `Action` classes using a configuration, typically defined in a configuration file (e.g., `web.xml` or a properties file).
   - **Forwarding**: The `Action` class can set up forwarding to a JSP page if necessary. This is done by setting a forwarding path in the `Action` class (e.g., using `setForward(true)` and `setPath()` methods).
   - **Response Handling**: After execution, the `ControllerServlet` forwards the request to the specified JSP page using the path obtained from the `Action` class. The JSP then generates the final HTML response to be sent back to the client.

3. **Service Class and DAO Integration**
   - **DAO (Data Access Object)**: DAO classes are responsible for direct database operations. They encapsulate the complexity of database interactions.
   - **Service Class (`UserinfoService.java`)**: This class acts as an intermediary between DAOs and the business logic. It contains methods that use DAO methods to perform tasks and handle any necessary business logic. The service class manages transactions and handles exceptions, potentially wrapping DAO exceptions in custom exceptions.

4. **Exception Handling**
   - **Custom Exception Handling**: Define custom exceptions in an `Exception` package to handle specific error scenarios in a controlled manner. These custom exceptions are thrown from the `UserinfoService` class when necessary and managed to ensure smooth error handling.
   - **Throwing and Catching Exceptions**: Custom exceptions are thrown by the `UserinfoService` methods and can be caught and processed either within the service layer or propagated to higher levels (like controllers) to be handled globally.

5. **Login Model Integration**
   - **Login Model (`loginModel.java`)**: This class leverages `UserinfoService` methods for authentication and other login-related operations. It processes user credentials and utilizes service methods to interact with the database.

### Summary

1. **`ControllerServlet`** processes incoming requests and delegates them to the appropriate `Action` classes.
2. **`Action` Classes** encapsulate the business logic for each request and decide on the appropriate JSP page for response rendering.
3. **`UserinfoService`** serves as a service layer that handles business logic and interacts with DAO classes, while also managing exceptions.
4. **Custom Exceptions** are defined in a dedicated package and used for more granular and manageable error handling.


### Program Flow Summary

#### 1. **Client Request Handling**
   - The `ControllerServlet` receives client requests. 
   - The URL pattern of the request determines which model class should handle the request.

#### 2. **URL Mapping**
   - The `ControllerServlet` extracts the request URI and context path to determine the command (e.g., `/loginform.do`, `/login.do`).
   - It maps the command to an appropriate model class that implements the `Action` interface (e.g., `LoginFormModel`, `LoginModel`).

#### 3. **Model Processing**
   - Each model class (e.g., `LoginModel`) implements the `Action` interface and its `execute` method.
   - The `execute` method processes the request:
     - **Login**: Validates credentials using `UserinfoService`. If authentication fails, it forwards to the login page with an error message.
     - **Logout**: Invalidates the session and redirects to the login form.

#### 4. **Forwarding/Redirecting**
   - Based on the `ActionForward` object returned by the model:
     - **Forward**: The `ControllerServlet` forwards the request to a JSP page using `RequestDispatcher`.
     - **Redirect**: The `ControllerServlet` sends a redirect response to the client, causing the client’s browser to request a new URL.

#### 5. **JSP Pages**
   - **`user_login.jsp`**:
     - Displays the login form if the user is not logged in.
     - Shows a welcome message and navigation buttons if the user is logged in.
   - **`user_error.jsp`**:
     - Displays an error message and provides a button to return to the login form.

#### 6. **Service Layer**
   - **`UserinfoService`**:
     - Contains methods for handling user information (e.g., add, modify, remove users).
     - Manages interactions with the `UserinfoModelTwoDAO` and throws custom exceptions if errors occur (e.g., `AuthFailException`, `ExistsUserinfoException`).

#### 7. **Exception Handling**
   - **Custom Exceptions**:
     - `AuthFailException`: Thrown when authentication fails.
     - `ExistsUserinfoException`: Thrown when trying to add a user that already exists.
     - `UserinfoNotFoundException`: Thrown when user information cannot be found.

### Detailed Flow

1. **Client Requests**
   - The client requests a URL like `/login.do`.

2. **ControllerServlet Processing**
   - The `ControllerServlet` extracts the command (`/login.do`).
   - Based on the command, it creates an instance of `LoginModel` and calls its `execute` method.

3. **Model Execution**
   - **`LoginModel`**:
     - Retrieves user credentials from the request.
     - Calls `UserinfoService` for authentication.
     - If authentication is successful, it sets the user info in the session and redirects to the login form.
     - If authentication fails, it forwards to the login page with an error message.

4. **ActionForward Handling**
   - **Forwarding**:
     - The `ActionForward` object has `forward` set to `true`, so the request is forwarded to a JSP page (`user_login.jsp`).
   - **Redirecting**:
     - The `ActionForward` object has `forward` set to `false`, so the request is redirected to a new URL (e.g., `/loginform.do`).

5. **JSP Rendering**
   - **`user_login.jsp`**:
     - Shows either the login form or user management options based on session data.
   - **`user_error.jsp`**:
     - Displays an error message with a button to return to the login form.

6. **Service and Exception Handling**
   - **`UserinfoService`**:
     - Handles CRUD operations for user information.
     - Throws exceptions as needed, which are caught and handled by the model.

### Summary of Classes

- **`ControllerServlet`**: Manages request routing and view forwarding.
- **`Action`**: Interface for model classes.
- **`ActionForward`**: Contains information about the navigation (forward/redirect) and the path.
- **Model Classes (`LoginFormModel`, `LoginModel`, etc.)**: Implement the `Action` interface to handle specific requests.
- **`UserinfoService`**: Provides methods for user data processing and authentication.
- **Exception Classes**: Handle specific error conditions related to user operations.

## ControllerServlet.java


```java
package xyz.itwill.mvc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Controller (Servlet) : This class serves as a controller that handles all client requests, calls the request processing methods on model objects (Model - Class),
// and controls the program flow by providing the processing results to the view (View - JSP) for response to the client.

// 1. Implement the function of a single entry point to handle all client requests by setting the URL pattern of the servlet.
// => Front Controller Pattern
// @WebServlet("url") : This annotation registers the class as a servlet and maps the servlet to a URL address that can be requested.
// => URL patterns with wildcards (* : all, ? : one character) can be used to map URL addresses.
// => @WebServlet("*.do") : Executes the servlet when the client requests a URL address in the [XXX.do] format.
// => Instead of the @WebServlet annotation, you can use elements in the [web.xml] file to register the class as a servlet and handle URL pattern mapping.
// @WebServlet("*.do")
public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // This method is automatically called every time the client requests the servlet
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // System.out.println("ControllerServlet class's service() method called");
        
        // 2. Analyze client requests: Use the request URL address - http://localhost:8000/mvc/XXX.do
        // HttpServletRequest.getRequestURI() : Method to return the URI address from the request URL
        String requestURI = request.getRequestURI();
        // System.out.println("requestURI = " + requestURI); // requestURI = /mvc/XXX.do
        
        // HttpServletRequest.getContextPath() : Method to return the top-level path of the context from the request URL
        String contextPath = request.getContextPath();
        // System.out.println("contextPath = " + contextPath); // contextPath = /mvc
        
        String command = requestURI.substring(contextPath.length());
        // System.out.println("command = " + command); // command = /XXX.do
        
        // 3. Process the request by calling request processing methods using a model (Model) object
        // and store the related view (View) information to return.
        // => Set up one model object to handle each request - Command Controller Pattern
        // => Write a model class with request processing methods - implemented by inheriting an interface
        
        // For a user management program, set up model classes (Command Handlers) to handle client requests
        // => [/loginform.do]  - LoginFormModel class
        // => [/login.do]      - LoginModel class
        // => [/logout.do]     - LogoutModel class
        // => [/writeform.do]  - WriteFormModel class
        // => [/write.do]      - WriteModel class
        // => [/list.do]       - ListModel class
        // => [/view.do]       - ViewModel class
        // => [/modifyform.do] - ModifyFormModel class
        // => [/modify.do]     - ModifyModel class
        // => [/remove.do]     - RemoveModel class
        // => [/error.do]      - ErrorModel class
        
        // Create a reference variable of the interface type for model roles
        // => The reference variable can hold objects of any subclasses (models) that implement the interface
        Action action = null;

        // Compare client request information to create and store an object of the model class handling the request
        if (command.equals("/loginform.do")) {
            action = new LoginFormModel();
        } else if (command.equals("/login.do")) {
            action = new LoginModel();
        }
        
        // Call the abstract method with the reference variable of the interface type,
        // which implicitly type-casts to the object stored in the reference variable.
        // The overridden request processing method is called to process the request and  return the view-related information (ActionForward object) for storage.
        ActionForward actionForward = action.execute(request, response);
        
        // 4. Handle the response using the view-related information stored in the object
        if (actionForward.isForward()) { // If the forward field value of the ActionForward object is [true]
            // Forward to the JSP document and respond to the client with the execution result (HTML) of the JSP document
            request.getRequestDispatcher(actionForward.getPath()).forward(request, response);
        } else { // If the forward field value of the ActionForward object is [false]
            // Respond to the client with the URL address (/XXX.do) from the servlet (controller)
            response.sendRedirect(actionForward.getPath());
        }
    }
}
```

### Action.java(interface)



```java
package xyz.itwill.mvc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// An interface that all model classes must implement
// => Provides a guideline for request processing methods in model classes
// => Allows the controller servlet to easily call request processing methods on model objects and improves maintenance efficiency

// The request processing method is defined as an abstract method in the interface
// => All subclasses that inherit from the interface must override the abstract method - provides a method implementation guideline
// => The request processing method's parameters are designed to accept HttpServletRequest and HttpServletResponse objects
// and should return view-related information (ActionForward object)
public interface Action {
    ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException;
}
```

This translation keeps the meaning of the original comments and explains the purpose and structure of the `Action` interface in the context of an MVC web application.

#### ActionForward.java(class)

```java
package xyz.itwill.mvc;

// Class to store view-related information
public class ActionForward {
    // Field to store information about the page navigation method
    // => false: Redirect, true: Forward
    // Redirect: Handles the response by sending the URL address (/XXX.do) to the client for re-request
    // => Changes the request URL address in the client browser
    // Forward: Handles the response by moving the servlet (controller) thread to a JSP (view)
    // => The request URL address in the client browser remains unchanged
    private boolean forward;
    
    // Field to store the URL address or context path for page navigation
    // => Redirect: /XXX.do, Forward: /XXX.jsp
    private String path;
    
    public ActionForward() {
        // TODO Auto-generated constructor stub
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
```

This translation retains the functionality descriptions and field explanations of the `ActionForward` class.

#### LoginFormModel.java(class)

```java
package xyz.itwill.mvc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Model class that will be instantiated when the client requests the page with [/loginform.do]
// => Returns an ActionForward object containing information to forward to the [user_login.jsp] page
public class LoginFormModel implements Action {
    // Request processing method: Method to process the client's request - returns view-related information
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActionForward actionForward = new ActionForward();
        actionForward.setForward(true); // Set to true for forward navigation
        actionForward.setPath("/model_two/user_login.jsp"); // Set the path to the JSP page
        return actionForward;
    }
}
```

This translation maintains the functionality and purpose of the `LoginFormModel` class in the context of handling client requests and forwarding to a specific JSP page.

### user_login.jsp

```jsp
<%@page import="xyz.itwill.dto.UserinfoDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- If the user is not logged in - JSP document for receiving authentication information from the user --%>
<%-- => When the [Login] button is clicked, it requests the [/login.do] address to move the page and pass input values --%>
<%-- If the user is logged in - JSP document for responding with a welcome message --%>
<%-- => When the [Member List] button is clicked, it requests the [/list.do] address to move the page --%>
<%-- => When the [Logout] button is clicked, it requests the [/logout.do] address to move the page --%>
<%-- => When the [Register Member] button is clicked, it requests the [/writeform.do] address to move the page - Provided only to administrators --%>
<%
    UserinfoDTO loginUserinfo = (UserinfoDTO) session.getAttribute("loginUserinfo");

    String message = (String) session.getAttribute("message");
    if (message == null) {
        message = "";
    } else {
        session.removeAttribute("message");
    }

    String userid = (String) session.getAttribute("userid");
    if (userid == null) {
        userid = "";
    } else {
        session.removeAttribute("userid");
    }
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>MVC</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="<%=request.getContextPath() %>/model_two/css/user.css" type="text/css">
<script language="JavaScript">
function userLogin() {
    if (f.userid.value == "") {
        alert("Please enter your user ID.");
        f.userid.focus();
        return;
    } 
    if (f.password.value == "") {
        alert("Please enter your password.");
        f.password.focus();
        return;
    }    
    
    f.action = "<%=request.getContextPath()%>/login.do";
    f.submit();
}
</script>
</head>
<body bgcolor=#FFFFFF text=#000000 leftmargin=0 topmargin=0 marginwidth=0 marginheight=0>
<br>
<table width=780 border=0 cellpadding=0 cellspacing=0>
    <tr>
        <td width="20"></td>
        <td style="color: red;"><%=message %></td>
    </tr>

    <tr>
      <td width="20"></td>
      <td>
      <!--contents-->
      <table width=590 border=0 cellpadding=0 cellspacing=0>
          <tr>
            <td bgcolor="f4f4f4" height="22">&nbsp;&nbsp;<b>User Management - Login</b></td>
          </tr>
      </table>  
      <br>
      
      <% if (loginUserinfo == null) { %>
          <!-- Login Form -->
          <form name="f" method="post">
          <table border="0" cellpadding="0" cellspacing="1" width="590" bgcolor="BBBBBB">
              <tr>
                <td width=100 align=center bgcolor="E6ECDE" height="22">User ID</td>
                <td width=490 bgcolor="ffffff" style="padding-left:10px;">
                    <input type="text" style="width:150" name="userid" value="<%=userid%>">
                </td>
              </tr>
              <tr>
                <td width=100 align=center bgcolor="E6ECDE" height="22">Password</td>
                <td width=490 bgcolor="ffffff" style="padding-left:10px;">
                    <input type="password" style="width:150" name="password">
                </td>
              </tr>
          </table>
          </form>
          <br>
          
          <table width=590 border=0 cellpadding=0 cellspacing=0>
              <tr>
                <td align=center>
                    <input type="button" value="Login" onClick="userLogin();"> &nbsp;
                </td>
              </tr>
          </table>
    <% } else { %>
          <table border="0" cellpadding="0" cellspacing="1" width="590" bgcolor="BBBBBB">
              <tr>
                <td width=100 align=center bgcolor="E6ECDE" height="22">
                    Welcome, <%=loginUserinfo.getName() %>!
                </td>
              </tr>
          </table>
          <br>
          
          <table width=590 border=0 cellpadding=0 cellspacing=0>
              <tr>
                <td align=center>
                    <button type="button" onclick="location.href='<%=request.getContextPath()%>/list.do';">Member List</button>
                    <button type="button" onclick="location.href='<%=request.getContextPath()%>/logout.do';">Logout</button>
                    <% if (loginUserinfo.getAuth() == 9) { %>
                    <button type="button" onclick="location.href='<%=request.getContextPath()%>/writeform.do';">Register Member</button>
                    <% } %>
                </td>
              </tr>
          </table>
    <% } %>
      </td>
    </tr>
</table>  

</body>
</html>
```

This translation maintains the purpose of the JSP page, which handles both login and user management functionalities, showing different content based on whether the user is logged in or not.

### user_error.jsp 


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document for responding with an error message --%>
<%-- => When the [Go to Main] button is clicked, it requests the [/loginform.do] address to navigate to the page --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
<style type="text/css">
body {
    text-align: center;
}

.message {
    color: red;
    font-size: 1.5em;
}
</style>
</head>
<body>
    <h1>Error Page</h1>
    <hr>
    <p class="message">An unexpected error occurred during program execution or an error occurred due to an abnormal request.</p>
    <button type="button" onclick="location.href='<%=request.getContextPath()%>/loginform.do';">Go to Main</button>
</body>
</html>
```

This translation preserves the structure and intent of the original JSP, which is to display an error message and provide a button to navigate back to the main login form.

### LoginModel.java(class)


```java
package xyz.itwill.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xyz.itwill.exception.AuthFailException;
import xyz.itwill.service.UserinfoService;

// Model class that will be instantiated when the client requests the [/login.do] page
// => Authenticates the provided credentials against the USERINFO table and returns an ActionForward object
// containing information to redirect to [/loginform.do]
public class LoginModel implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActionForward actionForward = new ActionForward();
        try {
            if (request.getMethod().equals("GET")) { // For abnormal requests
                throw new Exception("Abnormal request - GET method request"); // Artificially throw an exception
            }
            
            // Retrieve the values passed with the servlet (controller) request
            // => Can use the HttpServletRequest object passed as a parameter to the request handling method
            String userid = request.getParameter("userid");
            String password = request.getParameter("password");
            
            // Call a method from the Service class to process the data
            // => Auth() method of UserinfoService class for authentication
            // => AuthFailException is thrown if authentication fails
            UserinfoService.getService().auth(userid, password);
            
            // HttpServletRequest.getSession() : Returns the bound session (HttpSession object)
            HttpSession session = request.getSession();
            
            // Session Scope: Attributes bound to the session can be used across all web programs with the same session
            // => The session is automatically deleted when the browser is closed
            session.setAttribute("loginUserinfo", UserinfoService.getService().getUserinfo(userid));
            
            actionForward.setForward(false);
            actionForward.setPath(request.getContextPath() + "/loginform.do");
        } catch (AuthFailException e) {
            // Request Scope: Attributes are accessible only within the web program (JSP) where the thread is forwarded
            request.setAttribute("message", e.getMessage());
            request.setAttribute("userid", request.getParameter("userid"));
            // Store information in the ActionForward object to forward to the [/model_two/user_login.jsp] page
            actionForward.setForward(true);
            actionForward.setPath("/model_two/user_login.jsp");
        } catch (Exception e) { // Handle all other exceptions
            e.printStackTrace(); // Print the exception information to the console
            // Store information in the ActionForward object to redirect to the [/error.do] page
            actionForward.setForward(false);
            actionForward.setPath(request.getContextPath() + "/error.do");
        }
        
        return actionForward;
    }
}
```

This translation captures the purpose and functionality of the `LoginModel` class, which handles authentication and determines the appropriate response based on the outcome of the authentication process.
### UserinfoService.java(class)


```java
package xyz.itwill.service;

import java.sql.SQLException;
import java.util.List;

import xyz.itwill.dao.UserinfoModelTwoDAO;
import xyz.itwill.dto.UserinfoDTO;
import xyz.itwill.exception.AuthFailException;
import xyz.itwill.exception.ExistsUserinfoException;
import xyz.itwill.exception.UserinfoNotFoundException;

// Service class: Provides data processing functionality for model class request handling methods
// => Modular component: Handles various DAO methods for data processing - DAO modularization
// => Throws custom exceptions for any issues encountered during data processing
public class UserinfoService {
    private static UserinfoService _service;

    private UserinfoService() {
        // TODO Auto-generated constructor stub
    }

    static {
        _service = new UserinfoService();
    }

    public static UserinfoService getService() {
        return _service;
    }

    // Method to insert a row into the USERINFO table with the provided user information
    // => Throws a custom exception if the user ID already exists in the USERINFO table
    public void addUserinfo(UserinfoDTO userinfo) throws SQLException, ExistsUserinfoException {
        if (UserinfoModelTwoDAO.getDAO().selectUserinfo(userinfo.getUserid()) != null) {
            throw new ExistsUserinfoException("The user ID is already in use.");
        }

        UserinfoModelTwoDAO.getDAO().insertUserinfo(userinfo);
    }

    // Method to update a row in the USERINFO table with the provided user information
    // => Throws a custom exception if the user ID does not exist in the USERINFO table
    public void modifyUserinfo(UserinfoDTO userinfo) throws SQLException, UserinfoNotFoundException {
        if (UserinfoModelTwoDAO.getDAO().selectUserinfo(userinfo.getUserid()) == null) {
            throw new UserinfoNotFoundException("User information does not exist.");
        }

        UserinfoModelTwoDAO.getDAO().updateUserinfo(userinfo);
    }

    // Method to delete a row from the USERINFO table based on the provided user ID
    // => Throws a custom exception if the user ID does not exist in the USERINFO table
    public void removeUserinfo(String userid) throws SQLException, UserinfoNotFoundException {
        if (UserinfoModelTwoDAO.getDAO().selectUserinfo(userid) == null) {
            throw new UserinfoNotFoundException("User information does not exist.");
        }

        UserinfoModelTwoDAO.getDAO().deleteUserinfo(userid);
    }

    // Method to retrieve a user’s information from the USERINFO table based on the provided user ID
    // => Throws a custom exception if the user ID does not exist in the USERINFO table
    public UserinfoDTO getUserinfo(String userid) throws SQLException, UserinfoNotFoundException {
        UserinfoDTO userinfo = UserinfoModelTwoDAO.getDAO().selectUserinfo(userid);

        if (userinfo == null) {
            throw new UserinfoNotFoundException("User information does not exist.");
        }

        return userinfo;
    }

    // Method to retrieve all rows from the USERINFO table as a list of user information
    public List<UserinfoDTO> getUserinfoList() throws SQLException {
        return UserinfoModelTwoDAO.getDAO().selectUserinfoList();
    }

    // Method to authenticate user credentials
    // => Throws a custom exception if authentication fails; if no exception is thrown, authentication is successful
    public void auth(String userid, String password) throws SQLException, AuthFailException {
        UserinfoDTO userinfo = UserinfoModelTwoDAO.getDAO().selectUserinfo(userid);

        if (userinfo == null || !userinfo.getPassword().equals(password)) {
            throw new AuthFailException("The user ID is incorrect or the password does not match.");
        }
    }
}
```

This translation maintains the technical detail of the class while ensuring that the functionality and purpose are clearly conveyed in English.

### AuthFailException.java(class) ,ExistsUserinfoException.java(class), UserinfoNotFoundException.java(class)

Here are the translations for the exception classes in English:

```java
package xyz.itwill.exception;

// Class for storing exception information when user information is not found
public class UserinfoNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public UserinfoNotFoundException() {
        // Default constructor
    }
    
    public UserinfoNotFoundException(String message) {
        super(message);
    }
}
```

```java
package xyz.itwill.exception;

// Class for storing exception information when an ID is duplicated
// => Exception classes must extend the Exception class (or RuntimeException class)
public class ExistsUserinfoException extends Exception {
    private static final long serialVersionUID = 1L;

    public ExistsUserinfoException() {
        // Default constructor
    }
    
    public ExistsUserinfoException(String message) {
        super(message);
    }
}
```

```java
package xyz.itwill.exception;

// Class for storing exception information when authentication fails
public class AuthFailException extends Exception {
    private static final long serialVersionUID = 1L;

    public AuthFailException() {
        // Default constructor
    }
    
    public AuthFailException(String message) {
        super(message);
    }
}
```

### Summary

- **`UserinfoNotFoundException`**: Used when user information cannot be found.
- **`ExistsUserinfoException`**: Used when there is a duplicate ID.
- **`AuthFailException`**: Used when authentication fails.

Each class extends `Exception` and provides a default constructor as well as a parameterized constructor for specifying an error message.

### LogoutModel.java(class)
Here's the translation of the `LogoutModel` class into English:

```java
package xyz.itwill.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// The model class that will be instantiated when a client requests the [/login.do] page
// => Logs out the user and returns an ActionForward object with information to redirect to the [/loginform.do] address
public class LogoutModel implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        
        ActionForward actionForward = new ActionForward();
        actionForward.setForward(false);
        actionForward.setPath(request.getContextPath() + "/loginform.do");
        return actionForward;
    }
}
```

### Summary

- **Class Purpose**: The `LogoutModel` class handles the logout process for the application.
- **Functionality**:
  - It invalidates the user's session to log them out.
  - It then creates an `ActionForward` object to redirect the client to the `/loginform.do` page.

This class implements the `Action` interface and overrides the `execute` method to perform its functionality.