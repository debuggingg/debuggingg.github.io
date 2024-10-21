---
layout: single
title: 2024/06
---


### 1. SecurityUserService.java
```java
package xyz.itwill.service;

import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUser;

public interface SecurityUserService {
    void addSecurityUser(SecurityUser user);
    void addSecurityAuth(SecurityAuth auth);
    SecurityUser getSecurityUserByUserid(String userid);
}
```

### 2. SecurityUserServiceImpl.java
```java
package xyz.itwill.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import xyz.itwill.dao.SecurityUserDAO;
import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUser;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {
    private final SecurityUserDAO securityUserDAO;

    @Override
    public void addSecurityUser(SecurityUser user) {
        securityUserDAO.insertSecurityUser(user);
    }

    @Override
    public void addSecurityAuth(SecurityAuth auth) {
        securityUserDAO.insertSecurityAuth(auth);
    }

    @Override
    public SecurityUser getSecurityUserByUserid(String userid) {
        return securityUserDAO.selectSecurityUserByUserid(userid);
    }
}
```

### 3. SecurityUserController.java
```java
package xyz.itwill.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUser;
import xyz.itwill.service.SecurityUserService;

@Controller
@RequiredArgsConstructor
public class SecurityUserController {
    private final SecurityUserService securityUserService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/member/add", method = RequestMethod.GET)
    @ResponseBody
    public String addUser() {
        SecurityUser user1 = new SecurityUser("abc123", passwordEncoder.encode("123456"), "홍길동", "abc@itwill.xyz", "1", null);
        SecurityUser user2 = new SecurityUser("opq456", passwordEncoder.encode("123456"), "임꺽정", "opq@itwill.xyz", "1", null);
        SecurityUser user3 = new SecurityUser("xyz789", passwordEncoder.encode("123456"), "전우치", "xyz@itwill.xyz", "1", null);

        securityUserService.addSecurityUser(user1);
        securityUserService.addSecurityUser(user2);
        securityUserService.addSecurityUser(user3);

        SecurityAuth auth1 = new SecurityAuth("abc123", "ROLE_USER");
        SecurityAuth auth2 = new SecurityAuth("opq456", "ROLE_USER");
        SecurityAuth auth3 = new SecurityAuth("opq456", "ROLE_MANAGER");
        SecurityAuth auth4 = new SecurityAuth("xyz789", "ROLE_ADMIN");

        securityUserService.addSecurityAuth(auth1);
        securityUserService.addSecurityAuth(auth2);
        securityUserService.addSecurityAuth(auth3);
        securityUserService.addSecurityAuth(auth4);

        return "success";
    }
}
```

### 4. LoginController.java
```java
package xyz.itwill.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
create table users(username varchar2(100) primary key, password varchar2(100), enabled varchar2(1));
insert into users values('abc123', '123456', '1');
insert into users values('opq456', '123456', '1');
insert into users values('xyz789', '123456', '1');
insert into users values('test000', '123456', '0');
commit;

create table authorities(username varchar2(100), authority varchar2(50)
    , CONSTRAINT authorities_username_fk foreign key(username) REFERENCES users(username));
create unique index authorities_username_index on authorities(username, authority);    
insert into authorities values('abc123', 'ROLE_USER');
insert into authorities values('opq456', 'ROLE_USER');
insert into authorities values('opq456', 'ROLE_MANAGER');
insert into authorities values('xyz789', 'ROLE_ADMIN');
insert into authorities values('test000', 'ROLE_USER');
commit;
*/

@Controller
public class LoginController {
    @RequestMapping(value = "/csrf", method = RequestMethod.GET)
    public String form() {
        return "csrf";
    }

    @RequestMapping(value = "/csrf", method = RequestMethod.POST)
    @ResponseBody
    public String form(@RequestParam String name) {
        System.out.println("name = " + name);
        return "ok";
    }

    @RequestMapping(value = "/user_login", method = RequestMethod.GET)
    public String userLogin() {
        return "form_login";
    }

    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public String accessDenied() {
        return "/access_denied";
    }

    @RequestMapping(value = "/session_error", method = RequestMethod.GET)
    public String sessionError() {
        return "/session_error";
    }
}
```

# JSP
#### csrf.jsp


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SECURITY</title>
</head>
<body>
    <h1>Input Page</h1>
    <hr>
    <form method="post" enctype="multipart/form-data">
        Name: <input type="text" name="name">
        <%-- To prevent CSRF attacks, the CSRF Token issued by Spring Security is passed as hidden --%>
        <%-- => The CSRF Token is issued to verify that the request sent to the server is an actual request allowed by the server --%>
        <%-- => The server generates a random token each time it creates a view page, stores it in the session, and when the user requests a server page, the token is passed as hidden type --%>
        <%-- => The server compares it with the token stored in the session to confirm the client --%>
        <%-- => The matched token is deleted, and a new token is generated for the next view --%>
        <%-- CSRF (Cross-Site Request Forgery) attack: a method of forging requests to a site --%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit">Submit</button>
    </form>
</body>
</html>
```

#### form_login.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SECURITY</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <h1>Login</h1>
    <hr>
    <form action="<c:url value="/user_login"/>" method="post" id="loginForm">
    <table>
        <tr>
            <td>Username</td>
            <%-- The name attribute for passing the username to the authentication processing page must be set to [username] --%>
            <%-- <td><input type="text" name="username" id="userid"></td> --%>
            <td><input type="text" name="userid" id="userid" value="${userid }"></td>
            <c:remove var="userid"/>
        </tr>
        <tr>
            <td>Password</td>
            <%-- The name attribute for passing the password to the authentication processing page must be set to [password] --%>
            <%-- <td><input type="password" name="password" id="passwd"></td> --%>
            <td><input type="password" name="passwd" id="passwd"></td>
        </tr>
        <tr>
            <td colspan="2"><button type="submit">Login</button></td>
        </tr>
    </table>
    
    <%-- Tag to provide functionality to keep the login even after the browser is closed --%>
    <%-- => Set the type attribute of the input tag to [checkbox] and the name attribute to [remember-me] --%>
    <input type="checkbox" name="remember-me">Remember Me
    
    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    </form>
    <%-- SPRING_SECURITY_LAST_EXCEPTION: The name of the property stored in the session scope that contains the last exception (Exception object) thrown by Spring Security --%>
    <%-- => Process output including this tag if an exception occurred due to Spring Security --%>
    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION }">
        <hr>
        <%-- <h3 style="color: red;">Invalid username or password.</h3> --%>
        <h3 style="color: red;">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }</h3>
        <%-- Remove the exception stored in the session scope property --%>
        <c:remove var="SPRING_SECURITY_LAST_EXCEPTION"/>
    </c:if>
    
    <hr>
    <h3><a href="<c:url value="/"/>">Main Page</a></h3>
    
    <script type="text/javascript">
    $("#loginForm").submit(function() {
        if($("#userid").val() == "") {
            alert("Please enter your username.");
            return false;
        }        
        
        if($("#passwd").val() == "") {
            alert("Please enter your password.");
            return false;
        }
    });
    </script>
</body>
</html>
```

#### main_page.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Include security tag library to use Spring Security related tags in the JSP document --%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SECURITY</title>
</head>
<body>
    <h1>Main Page</h1>
    <hr>
    <h3><a href="<c:url value="/guest/"/>">Guest</a></h3>
    <h3><a href="<c:url value="/user/"/>">User</a></h3>
    <h3><a href="<c:url value="/manager/"/>">Manager</a></h3>
    <h3><a href="<c:url value="/admin/"/>">Admin</a></h3>
    
    <%-- No need to create a method and view to handle requests for the [/login] page; Spring Security provides a login page --%>
    <%-- <h3><a href="<c:url value="/login"/>">Login</a></h3> --%>

    <%-- authorize tag: Tag to set the inclusion based on permission comparison --%>
    <%-- access attribute: Set the permission (Role) as an attribute value - SpEL can be used --%>
    <%-- Set this tag to be included for unauthenticated users --%>
    <sec:authorize access="isAnonymous()">
        <%-- <h3><a href="<c:url value="/login"/>">Login</a></h3> --%>
        <h3><a href="<c:url value="/user_login"/>">Login</a></h3>
    </sec:authorize>
    
    <%-- Store the logged-in user information (Principal field of Authentication object) as a scope attribute --%>
    <sec:authentication property="principal" var="loginUser"/>
    
    <%-- Set this tag to be included for authenticated users --%>
    <sec:authorize access="isAuthenticated()">
        <%-- authentication tag: Tag to provide authenticated user information (UserDetails object) --%>
        <%-- => Tag available only to authenticated users --%>
        <%-- => Tag to provide the Authentication object to the JSP document --%>
        <%-- property attribute: Set the field name where the authenticated user's value is stored as an attribute value --%>
        <%-- <h3><sec:authentication property="principal.username"/> Welcome!</h3> --%>
        <%-- If authenticated user information is stored in a class that implements the UserDetails interface, the field values can be accessed --%>
        <%-- <h3><sec:authentication property="principal.userid"/> Welcome!</h3> --%>
        <%-- <h3><sec:authentication property="principal.name"/> Welcome!</h3> --%>
        <h3>${loginUser.name}, welcome!</h3>
        
        <%-- The page that provides logout functionality must use a form tag for the request --%>
        <%-- => Use a form tag to pass the CSRF token --%>
        <form action="<c:url value="/logout"/>" method="post">
            <%-- <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"> --%>
            <%-- csrfInput tag: Tag to pass the CSRF token --%>
            <sec:csrfInput/>            
            <button type="submit">Logout</button>    
        </form>
    </sec:authorize>
    
    <%-- embed tag: Tag to output a file using an embedded browser --%>
    <%-- src attribute: Set the URL of the file to be displayed in the embedded browser as an attribute value --%>
    <embed src="<c:url value="/resources/eclipse_cheatsheet.pdf"/>" width="1000" height="1500">
</body>
</html>
```

# Custom
### CustomPasswordEncoder.java
```java
package xyz.itwill.auth;

import org.springframework.security.crypto.password.PasswordEncoder;

// A class that provides methods to encrypt a password and compare an encrypted password with a plain password.
// => This class implements the PasswordEncoder interface.
public class CustomPasswordEncoder implements PasswordEncoder {
    // Method that encrypts the given string and returns it.
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    // Method that compares the given encrypted string with a plain string and returns the comparison result as a boolean.
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
```

### CustomUserDetails.java
```java
package xyz.itwill.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUser;

// A class to store authenticated user information and authority information.
// => After successful authentication, the UserDetails object containing user and authority information 
// can be provided by Spring Security, but this class is written to provide user and authority information 
// in a desired format.
// => This class implements the UserDetails interface.
@Data
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    
    // Fields to store authenticated user information
    private String userid;
    private String passwd;
    private String name;
    private String email;
    private String enabled;
    
    // Field to store authenticated user's authority information
    private List<GrantedAuthority> secuthryAuthList;

    // Constructor that saves the fields of the passed SecurityUser object into the CustomUserDetails object's fields
    public CustomUserDetails(SecurityUser user) {
        this.userid = user.getUserid();
        this.passwd = user.getPasswd();
        this.name = user.getName();
        this.email = user.getEmail();
        this.enabled = user.getEnabled();
        
        this.secuthryAuthList = new ArrayList<GrantedAuthority>();
        // Create GrantedAuthority objects for the found user's authority information and save them in the List
        for (SecurityAuth auth : user.getSecurityAuthList()) {
            secuthryAuthList.add(new SimpleGrantedAuthority(auth.getAuth()));
        }
    }
    
    // Method that returns the authenticated user's authority information
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return secuthryAuthList;
    }

    // Method that returns the authenticated user's password
    @Override
    public String getPassword() {
        return passwd;
    }

    // Method that returns the authenticated user's identifier (username)
    @Override
    public String getUsername() {
        return userid;
    }

    // Method that returns the validity state of the authenticated user's account
    // => false: user account has expired, true: user account is still valid
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Method that returns the locked state of the authenticated user's account
    // => false: account is locked, true: account is unlocked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Method that returns the validity state of the authenticated user's password
    // => false: user password has expired, true: user password is still valid
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Method that returns the active state of the authenticated user
    // => false: user is inactive, true: user is active
    @Override
    public boolean isEnabled() {
        return !enabled.equals("0");
    }
}
```

### CustomUserDetailsService.java
```java
package xyz.itwill.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dao.SecurityUserDAO;
import xyz.itwill.dto.SecurityUser;

// A class that retrieves user information and authority information stored in the table 
// to authenticate and return a UserDetails object containing user and authority information.
// => This class implements the UserDetailsService interface.
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SecurityUserDAO securityUserDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = securityUserDAO.selectSecurityUserByUserid(username);
        
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        return new CustomUserDetails(user);
    }
}
```

### CustomLoginFailureHandler.java
```java
package xyz.itwill.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

// A class that provides functionality to be executed after authentication failure.
// => Implements features such as accumulating login failure counts, account deactivation, etc.
// => This class extends SimpleUrlAuthenticationFailureHandler.
@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    // Method automatically called on authentication failure
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        request.getSession().setAttribute("userid", request.getParameter("userid"));
        setDefaultFailureUrl("/user_login");
        super.onAuthenticationFailure(request, response, exception);
    }
}
```

### CustomLoginSuccessHandler.java
```java
package xyz.itwill.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

// A class that provides functionality to be executed after successful authentication.
// => Implements features such as updating the last login date or resetting the login failure count.
// => This class implements AuthenticationSuccessHandler.
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    // Method automatically called on authentication success
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        // Create a List object to store the roles of the logged-in user
        List<String> roleNames = new ArrayList<String>();
        
        // Authentication.getAuthorities(): Returns a List of all authority information (GrantedAuthority objects)
        // related to the authenticated user.
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            // GrantedAuthority.getAuthority(): Returns the authority information stored in the GrantedAuthority object as a string.
            roleNames.add(authority.getAuthority());
        }
        
        // Check if the role list contains specific roles and redirect accordingly
        if (roleNames.contains("ROLE_ADMIN")) {
            // Send response by redirecting to the admin URL
            response.sendRedirect(request.getContextPath() + "/admin/");
        } else if (roleNames.contains("ROLE_MANAGER")) {
            response.sendRedirect(request.getContextPath() + "/manager/");
        } else if (roleNames.contains("ROLE_USER")) {
            response.sendRedirect(request.getContextPath() + "/user/");
        } else {
            response.sendRedirect(request.getContextPath() + "/guest/");
        }
    }
}
```

### CustomAccessDeniedHandler.java
```java
package xyz.itwill.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

// A class that contains methods called when a restricted page is requested.
// => Implements commands such as activating account lock functionality.
// => This class implements AccessDeniedHandler.
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    // Method automatically called when a restricted page is requested
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/access_denied");
    }
}
```

