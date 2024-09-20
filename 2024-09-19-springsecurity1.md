---
layout: single
title: 2024/06
---
2024-09-18-springsecurity1


---

**Spring Security**: A security framework that provides authentication and authorization features.

**Authentication**: The process of verifying whether a user is allowed to use the program.  
→ To perform authentication successfully, information that can distinguish the user is required—Credential.

**Authorization**: The process of determining whether an authenticated user can access the requested resources.  
→ After authentication, the user can be granted permissions, which are generally assigned in the form of roles.

Spring Security uses a credential-based authentication method, where the Principal object is used as the ID and the Credential object is used as the password.

### Features Provided by Spring Security When Applied to SpringMVC Programs
- Various forms of user authentication methods (form login, token-based authentication, OAuth2-based authentication, LDAP authentication).
- Role-based access control according to user roles.
- Access control to resources provided by the program.
- Data encryption.
- SSL application.
- Blocking of well-known web security attacks.

### How to Apply Spring Security to SpringMVC Programs
1. Include the libraries: `spring-security-web`, `spring-security-core`, `spring-security-config`, and `spring-security-tablibs` in your project build (for Maven: in `pom.xml`).
2. Register the filter classes that provide Spring Security functionality in the `[web.xml]` file, and map the URL patterns for the filter to execute.
3. Provide information for the Spring Security filter in the `[web.xml]` file for the Spring Bean Configuration File, ensuring the file path is accessible to the `ContextLoaderListener` class.
4. Write a Spring Bean Configuration File to implement Spring Security features.  
   → Add the security namespace to provide the necessary elements using the `spring-security.xml` file configuration.

### Types of Spring Security Filters
1. **SecurityContextPersistenceFilter**: A filter that retrieves or creates the SecurityContext from the SecurityContextRepository.
2. **LogoutFilter**: A filter that handles logout requests.
3. **UsernamePasswordAuthenticationFilter**: A filter that processes Form-based user authentication using username and password.  
   → It creates an Authentication object and delegates the authentication processing to the AuthenticationManager.  
   → The AuthenticationManager delegates the actual verification steps to an AuthenticationProvider, typically using a service like UserDetailsService for authentication.
4. **ConcurrentSessionFilter**: A filter related to concurrent sessions to prevent duplicate logins.
5. **RememberMeAuthenticationFilter**: A filter that performs authentication based on stored tokens using cookies or a database, even if the session has expired.
6. **AnonymousAuthenticationFilter**: A filter that returns an anonymous user token if user information has not been authenticated.
7. **SessionManagementFilter**: A filter that handles operations related to sessions after login.
8. **ExceptionTranslationFilter**: A filter that processes exceptions related to authentication and authorization that occur within the filter chain.
9. **FilterSecurityInterceptor**: A filter that delegates access decisions to the AccessDecisionManager for authorization and access control.
10. **HeaderWriterFilter**: A filter that inspects HTTP headers and adds or removes headers from the request.
11. **CorsFilter**: A filter that checks whether requests come from authorized sites or clients.
12. **CsrfFilter**: A filter that provides protection against CSRF attacks using CSRF tokens.

--- 


---

### Spring Security Setup in `pom.xml`

To use Spring Security, you need to register the following four dependencies in your `pom.xml` file. This is the first step in setting up Spring Security for your project.

```xml
<!-- Spring Security Web -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-web</artifactId>
    <version>${spring.security-version}</version>
</dependency>

<!-- Spring Security Core -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-core</artifactId>
    <version>${spring.security-version}</version>
</dependency>

<!-- Spring Security Config -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-config</artifactId>
    <version>${spring.security-version}</version>
</dependency>

<!-- Spring Security Taglibs -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-taglibs</artifactId>
    <version>${spring.security-version}</version>
</dependency>
```

Make sure to replace `${spring.security-version}` with the specific version number you wish to use.

---
---

### `web.xml` Configuration

After setting up the dependencies in `pom.xml`, the next step is to configure the `web.xml` file for your Spring Security application.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.5" xmlns="http://java.sun.com/xml/ns/javaee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee https://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">

	<!-- The definition of the Root Spring Container shared by all Servlets and Filters -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>
			/WEB-INF/spring/root-context.xml
			<!-- Create a Spring Bean Configuration File for registering classes that provide Spring Security features -->
			/WEB-INF/spring/security-context.xml
		</param-value>
	</context-param>
	
	<!-- Creates the Spring Container shared by all Servlets and Filters -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<filter>
		<filter-name>multipartFilter</filter-name>
		<filter-class>org.springframework.web.multipart.support.MultipartFilter</filter-class>
	</filter>

	<filter-mapping>
		<filter-name>multipartFilter</filter-name>
		<url-pattern>/*</url-pattern>	
	</filter-mapping>

	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>utf-8</param-value>
		</init-param>
	</filter>
	
	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>	
	</filter-mapping>
	
	<!-- Register the DelegatingFilterProxy class as a filter -->
	<!-- => Make sure to name the filter [springSecurityFilterChain] -->
	<!-- DelegatingFilterProxy: A class that sets the position in the main Filter Chain -->
	<!-- => Acts as a starting point for using Spring Security filters and connects the filters registered as Spring Beans in the ApplicationContext (Spring Container) to the filters of the servlet container (WAS) -->
	<!-- => When a user requests the web application, the DelegatingFilterProxy filter receives the request and delegates it to the FilterChainProxy filter, executing the necessary filters in order -->
	<filter>
		<filter-name>springSecurityFilterChain</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	</filter>

	<filter-mapping>
		<filter-name>springSecurityFilterChain</filter-name>
		<url-pattern>/*</url-pattern>	
	</filter-mapping>

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
		<url-pattern>/</url-pattern>
	</servlet-mapping>

</web-app>
```

### Configuration Steps Summary
After setting up the dependencies in `pom.xml`, the next step is to configure the `web.xml` file to properly initialize Spring Security and the application context.

---

---
#### mybatis-config 1.xml

### `root-context.xml` Configuration

After configuring the `web.xml` file, you can set up MyBatis, DataSource, and JDBC settings through the `root-context.xml`.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/tx https://www.springframework.org/schema/tx/spring-tx.xsd">

	<bean class="org.springframework.jdbc.datasource.DriverManagerDataSource" id="dataSource">
		<property name="driverClassName" value="net.sf.log4jdbc.sql.jdbcapi.DriverSpy"/>
		<property name="url" value="jdbc:log4jdbc:oracle:thin:@localhost:1521:xe"/>
		<property name="username" value="scott"/>
		<property name="password" value="tiger"/>
	</bean>
	
	<bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactoryBean">
		<property name="configLocation" value="/WEB-INF/spring/mybatis-config.xml"/> 
		<property name="dataSource" ref="dataSource"/>
		<property name="typeAliasesPackage" value="xyz.itwill.dto"/>
		<property name="mapperLocations">
			<list>
				<value>classpath:xyz/itwill/mapper/*.xml</value>
			</list>
		</property>
	</bean>	
	
	<bean class="org.mybatis.spring.SqlSessionTemplate" id="sqlSession" destroy-method="clearCache">
		<constructor-arg ref="sqlSessionFactoryBean"/>
	</bean>

	<bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
		<property name="dataSource" ref="dataSource"/>
	</bean>
	
	<tx:annotation-driven/>
	
	<bean class="org.springframework.web.multipart.commons.CommonsMultipartResolver" id="filterMultipartResolver">
		<property name="maxUploadSize" value="20971520"/>
		<property name="defaultEncoding" value="utf-8"/>
	</bean>
	
	<!-- Configuration for message files (Properties files) to provide error messages upon authentication failure -->
	<bean class="org.springframework.context.support.ReloadableResourceBundleMessageSource" id="messageSource">
		<property name="basenames">
			<list>
				<value>/WEB-INF/message/security_message</value>
			</list>
		</property>
		<property name="cacheSeconds" value="60"/>
		<property name="defaultEncoding" value="utf-8"/>
	</bean>
</beans>
```

### Summary
After configuring the `web.xml` file, you can use the `root-context.xml` to set up MyBatis, DataSource, and JDBC settings for your application.

---


---

### Spring Security Configuration XML

This XML is necessary for storing the `dataSource` and for defining user roles (ROLE) and permissions within your application.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/security"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:beans="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/security https://www.springframework.org/schema/security/spring-security.xsd
		http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

	<!-- The <http> element provides information to apply Spring Security features to the SpringMVC application -->
	<http auto-config="true" use-expressions="true">
 		<intercept-url pattern="/guest/**" access="hasAnyRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')"/>
 		<intercept-url pattern="/user/**" access="hasRole('ROLE_USER')"/>
 		<intercept-url pattern="/manager/**" access="hasRole('ROLE_MANAGER')"/>
 		<intercept-url pattern="/admin/**" access="hasRole('ROLE_ADMIN')"/>
 		<intercept-url pattern="/**" access="permitAll"/>
 	</http>
 	
 	<!-- <authentication-manager> is used to register the authentication manager -->
 	<authentication-manager>
 		<authentication-provider>
 			<jdbc-user-service data-source-ref="dataSource"/>
 			<password-encoder ref="customPasswordEncoder"/>
 		</authentication-provider>
 	</authentication-manager> 

 	<beans:bean class="xyz.itwill.security.CustomPasswordEncoder" id="customPasswordEncoder"/>
</beans:beans>
```

### Summary
This XML configuration sets up the `dataSource` and defines user roles (ROLE) necessary for access control within your application.

---

Got it! Here’s a grammatically corrected version in English:

---

**USERS and AUTHORITIES tables should be created to retrieve data from the Oracle SQL database that has access authority. Additionally, a LoginController should be created.**

### SQL Script
- **SQL Script**: The SQL statements are organized and separated into sections for creating tables, inserting data, and committing changes. Each table has a brief comment explaining its purpose.

```sql
-- Create USERS table
CREATE TABLE users (
    username VARCHAR2(100) PRIMARY KEY,
    password VARCHAR2(100),
    enabled VARCHAR2(1)
);

-- Insert sample data into USERS table
INSERT INTO users VALUES ('abc123', '123456', '1');
INSERT INTO users VALUES ('opq456', '123456', '1');
INSERT INTO users VALUES ('xyz789', '123456', '1');
INSERT INTO users VALUES ('test000', '123456', '0');
COMMIT;

-- Create AUTHORITIES table
CREATE TABLE authorities (
    username VARCHAR2(100),
    authority VARCHAR2(50),
    CONSTRAINT authorities_username_fk FOREIGN KEY (username) REFERENCES users(username)
);

-- Create unique index for AUTHORITIES table
CREATE UNIQUE INDEX authorities_username_index ON authorities(username, authority);

-- Insert sample data into AUTHORITIES table
INSERT INTO authorities VALUES ('abc123', 'ROLE_USER');
INSERT INTO authorities VALUES ('opq456', 'ROLE_USER');
INSERT INTO authorities VALUES ('opq456', 'ROLE_MANAGER');
INSERT INTO authorities VALUES ('xyz789', 'ROLE_ADMIN');
INSERT INTO authorities VALUES ('test000', 'ROLE_USER');
COMMIT;
```

### LoginController Java Class

- **LoginController**: The Java class includes Javadoc comments for better documentation and understanding of the method's purpose. The method `loginPage` is clearly defined to return the name of the login page view.

```java
package xyz.itwill.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for handling login-related requests.
 */
@Controller
public class LoginController {

    /**
     * Displays the login page.
     * 
     * @return the name of the login page view
     */
    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String loginPage() {
        return "login_page";
    }
}
```

---
#### CustomPasswordEncoder.JAVA FOR PASSWORD ENCODER

```java
package xyz.itwill.security;

import org.springframework.security.crypto.password.PasswordEncoder;

// A class that provides methods to encrypt a password and to compare an encrypted password with a raw password.
// This class implements the PasswordEncoder interface.
public class CustomPasswordEncoder implements PasswordEncoder {
    // Method that takes a raw password as input and returns the encrypted version.
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    // Method that compares an encrypted password with a raw password and returns the result as a boolean.
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
```

#### security-context.xml 
```xml 
	<beans:bean class="xyz.itwill.security.CustomPasswordEncoder" id="customPasswordEncoder"/>
</beans:beans>
```


---

---

#### customize error messages, create a file and use it as follows:

1. Create a `message` folder inside `WEB-INF`.
2. Inside the `message` folder, create a properties file.

**Configuration:**
- For non-Spring Security applications, you create the `ReloadableResourceBundleMessageSource` in `WEB-INF/spring/appServlet/servlet-context.xml`.
- For Spring Security, it should be created in `WEB-INF/spring/root-context.xml`.

```xml
<!-- Configuration for the message file (Properties file) to provide error messages that occur during authentication failure -->
<bean class="org.springframework.context.support.ReloadableResourceBundleMessageSource" id="messageSource">
    <property name="basenames">
        <list>
            <value>/WEB-INF/message/security_message</value>
        </list>
    </property>
    <property name="cacheSeconds" value="60"/>
    <property name="defaultEncoding" value="utf-8"/>
</bean>
```

---

---

#### CSRF Attacks
When passing values, there is a risk of CSRF attacks. To prevent this, you can use the following method:

```java
@RequestMapping(value = "/csrf/", method = RequestMethod.GET)
public String form() {
    return "csrf";
}

@RequestMapping(value = "/csrf/", method = RequestMethod.POST)
@ResponseBody
public String form(@RequestParam String name) {
    System.out.println("name=" + name);
    return "ok";
}
```

However, to properly issue a token in Spring Security, you need to configure it as follows:

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
        <%-- To defend against CSRF attacks, pass the CSRF Token issued by Spring Security as a hidden input --%>
        <%-- This verifies whether the request sent to the server is actually allowed --%>
        <%-- The server issues a random token each time a view page is generated, storing it in the session. --%>
        <%-- When the user requests a server page, the hidden token is passed and compared with the token stored in the session --%>
        <%-- If they match, the token is removed and a new token is issued for the new view --%>
        <%-- CSRF (Cross-Site Request Forgery) attack: A method of forging requests on a site --%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit">Submit</button>
    </form>
</body>
</html>
```

In `security-context.xml`, you could also disable CSRF protection with:

```xml
<csrf disable="true"/>
```

However, doing this leaves your application vulnerable to CSRF attacks as it does not generate a CSRF token.

---


---
#### Multipart Form Data
When using `multipart/form-data`, it may not properly compare with the CSRF token. Therefore, you need to register a `multipartFilter` in `web.xml` and configure a `multipartResolver` as a bean in `root-context.xml`. This aspect is similar to general Spring configurations, which means that the environment settings differ when using both Spring Security and standard Spring.

Security-related configurations should be made in `security-context.xml`, while other general settings should be configured in `root-context.xml`.
```xml
	<bean class="org.springframework.web.multipart.commons.CommonsMultipartResolver" id="filterMultipartResolver">
		<property name="maxUploadSize" value="20971520"/>
		<property name="defaultEncoding" value="utf-8"/>
	</bean>
```