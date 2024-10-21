---
layout: single
title: 2024-09-19-springsecurity2-sevlet-context
---
#### servlet-context.xml


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- It is recommended to set the default namespace to [security] -->
<!-- => This allows the use of numerous elements provided in the spring-security.xsd file of the [security] namespace -->
<beans:beans xmlns="http://www.springframework.org/schema/security"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:beans="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/security https://www.springframework.org/schema/security/spring-security.xsd
		http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

	<!-- http: Element providing information to apply Spring Security features to a Spring MVC application -->
	<!-- => This element marks the beginning of the Spring Security configuration -->
	<!-- auto-config attribute: Set to false or true (default) -->
	<!-- => If set to [true], it configures the default environment -->
	<!-- use-expressions attribute: Set to false or true (default) -->
	<!-- => If set to [true], allows the use of SpEL expressions to control page access -->
	<!-- SpEL (Spring Expression Language) expressions indicate access permissions -->
	<!-- => hasRole('role'): Only accessible if the user has the specified role -->
	<!-- => hasAnyRole('role1','role2',...): Accessible if the user has any of the listed roles -->
	<!-- => permitAll: Allows access to all users -->
	<!-- => denyAll: Denies access to all users -->
	<!-- => isAnonymous(): Accessible only to anonymous users (not authenticated) -->
	<!-- => isRememberMe(): Accessible only to users authenticated via remember-me functionality -->
	<!-- => isAuthenticated(): Accessible only to authenticated users (including remember-me) -->
	<!-- => isFullyAuthenticated: Accessible only to fully authenticated users (excluding remember-me) -->
 	<http auto-config="true" use-expressions="true">
 		<headers>
 			<!-- frame-options: Element to set whether to allow output processing using embedded browsers like iframe or embed -->
 			<!-- policy attribute: Set to DENY (default), SAMEORIGIN, or ALLOW-FROM -->
 			<frame-options policy="SAMEORIGIN"/>
 		</headers>
 		
 		<!-- If use-expressions is set to [false], SpEL cannot be used for the access attribute -->
 		<!-- intercept-url allows setting access permissions for specific pages -->
 		<!-- 
 		<intercept-url pattern="/user/**" access="ROLE_USER"/>
 		<intercept-url pattern="/manager/**" access="ROLE_MANAGER"/>
 		<intercept-url pattern="/admin/**" access="ROLE_ADMIN"/>
 	 	-->
 	 	
 		<!-- intercept-url: Element to set access permissions for requested pages -->
 		<!-- => It is recommended to set specific page permissions first and overall permissions last -->
 		<!-- pattern attribute: Set the path of the requested page -->
 		<!-- => Use wildcard characters like * or ** for the attribute value -->
 		<!-- access attribute: Set the role that can access the page -->
 		<!-- => If use-expressions is set to [true], you can use SpEL for permission settings -->
 		<!-- => AccessDeniedException occurs if a user without permission requests the page -->
 		<intercept-url pattern="/guest/**" access="hasAnyRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')"/>
 		<intercept-url pattern="/user/**" access="hasRole('ROLE_USER')"/>
 		<intercept-url pattern="/manager/**" access="hasRole('ROLE_MANAGER')"/>
 		<intercept-url pattern="/admin/**" access="hasRole('ROLE_ADMIN')"/>
 		<intercept-url pattern="/**" access="permitAll"/>
 		
 		<!-- form-login: Element to set up a login page using form tags -->
 		<!-- => If not set, the default Spring Security login page (/login) and view are used -->
 		<!-- login-page attribute: Set the path for the page that receives the username and password -->
 		<!-- login-processing-url attribute: Set the path for processing the username and password -->
 		<!-- username-parameter: Set the name (name attribute) used to pass the username to the login processing page - if not set, the username must be set as [username] -->
 		<!-- password-parameter: Set the name (name attribute) used to pass the password - if not set, the password must be set as [password] -->
 		<!-- default-target-url attribute: Set the path to redirect to after successful authentication -->
 		<!-- authentication-failure-forward-url: Set the path to redirect to after authentication failure -->
 		<!-- authentication-failure-handler-ref: Set the Spring Bean identifier (beanName) of the class with the method to be called after authentication failure -->
 		<!-- authentication-success-forward-url: Set the path to redirect to after successful authentication -->
 		<!-- authentication-success-handler-ref: Set the Spring Bean identifier (beanName) of the class with the method to be called after successful authentication -->
 		<!--
 		<form-login login-page="/user_login" login-processing-url="/user_login"
 			username-parameter="userid" password-parameter="passwd"
 			default-target-url="/"
 			authentication-failure-handler-ref="customLoginFailureHandler" 
 			authentication-success-handler-ref="customLoginSuccessHandler"/>
 		-->
 		
 		<form-login login-page="/user_login" login-processing-url="/user_login"
 			username-parameter="userid" password-parameter="passwd"
 			default-target-url="/" 
 			authentication-failure-handler-ref="customLoginFailureHandler"/>
 		
 		<!-- logout: Element to configure logout handling -->
 		<!-- => If not set, the default Spring Security logout page (/logout) and view are used -->
 		<!-- logout-url attribute: Set the path for logout processing -->
 		<!-- logout-success-url attribute: Set the path to redirect to after logout -->
 		<!-- invalidate-session attribute: Set to false or true (default) -->
 		<!-- => If set to [true], the session will be unbound after logout -->
 		<!-- delete-cookies attribute: Set the names of the cookies to delete after logout -->
 		<!-- => If multiple cookies are to be deleted, list them separated by commas -->
 		<logout logout-url="/logout" logout-success-url="/" invalidate-session="true"
 			delete-cookies="JSESSIONID, remember-me"/>
 		
 		<!-- csrf: Element to set whether to use CSRF tokens -->
 		<!-- disabled attribute: Set to false or true (default) -->
 		<!-- => If set to [true], no CSRF tokens are issued, so they will not be checked in form tags -->
 		<!-- <csrf disabled="true"/> -->
 		
 		<!-- access-denied-handler: Element providing functionality to respond with an error page instead of a 403 error code when AccessDeniedException occurs -->
 		<!-- error-page attribute: Set the path for the error message page -->
 		<!-- <access-denied-handler error-page="/access_denied"/> -->
 		<!-- ref attribute: Set the Spring Bean identifier (beanName) of the class with the method to be called when a restricted page is requested -->
 		<access-denied-handler ref="customAccessDeniedHandler"/>
 		
 		<!-- remember-me: Element to provide the remember-me functionality -->
 		<!-- => Before authenticating with username and password, it checks for a cookie named [remember-me] -->
 		<!-- => When authentication is successful, it automatically generates and stores a token in the cookie - cookie duration: 2 weeks -->
 		<!-- token-validity-seconds attribute: Set the duration (in seconds) for the authentication token -->
 		<!-- Storing authentication tokens in cookies is a security risk, so it is recommended to create a persistent_logins table to store tokens -->
 		<!-- => create table persistent_logins(username varchar2(100), series varchar2(100) 
    	primary key, token varchar2(100), last_used timestamp); -->
    	<!-- data-source-ref: Set the Spring Bean identifier for the DataSource class -->
    	<!-- => Upon successful authentication, a token is issued and automatically inserted as a row in the table -->
 		<remember-me token-validity-seconds="604800" data-source-ref="dataSource"/>
 		<session-management>
 			<concurrency-control max-sessions="1" expired-url="/session_error"
 			error-if-maximum-exceeded="true"/>	
 		</session-management>
 	
 	</http>
 	
 	<!-- authentication-manager: Element to register the authentication manager -->
 	<!-- => Provides various forms of authentication -->
 	<!-- authentication-provider: Element to register an authentication provider -->
 	<!-- => Provides the functionality for actual authentication -->
 	<!-- user-service: Element to register user information -->
 	<!-- => Returns authority-related information for users during authentication -->
 	<!-- user: Element to set information for authentication and user authority -->
 	<!-- name attribute: Set the identifier (username) for distinguishing users -->
 	<!-- password attribute: Set the user's password -->
 	<!-- => From Spring Security 5.0 onwards, passwords must be encrypted for comparison -->
 	<!-- => Prefix the password with {noop} to allow comparison without encryption -->
 	<!-- authorities attribute: Set the user's roles -->
 	<!-- => Roles should be set in the format ROLE_XXX -->
 	<!-- => If providing multiple roles, separate them with commas -->
 	<!-- Upon successful authentication, the

 Authentication Manager stores the authentication and authorization information (Authentication object) in the Spring Security session (Security ContextHolder) -->
 	<!-- 
 	<authentication-manager>
 		<authentication-provider>
 			<user-service>
 				<user name="abc123" password="{noop}123456" authorities="ROLE_USER"/>
 				<user name="opq456" password="{noop}123456" authorities="ROLE_USER, ROLE_MANAGER"/>
 				<user name="xyz789" password="{noop}123456" authorities="ROLE_ADMIN"/>
 			</user-service>
 		</authentication-provider>
 	</authentication-manager>
 	-->
 	 
 	<!-- jdbc-user-service: Element for Spring Security to perform authentication using JDBC -->
 	<!-- => Uses JdbcUserDetailsManager class for authentication and authorization -->
 	<!-- => If you create a USERS table for storing user information and an AUTHORITIES table for storing user roles, it will use default SQL commands for authentication -->
 	<!-- data-source-ref: Set the Spring Bean identifier (beanName) for the DataSource class to provide the Connection object -->
 	<!-- password-encoder: Element providing functionality to encrypt passwords or compare encrypted passwords -->
 	<!-- ref attribute: Set the Spring Bean identifier (beanName) of a class that implements the PasswordEncoder interface -->
 	<!-- 
 	<authentication-manager>
 		<authentication-provider>
 			<jdbc-user-service data-source-ref="dataSource"/>
 			<password-encoder ref="customPasswordEncoder"/>
 		</authentication-provider>
 	</authentication-manager> 
 	
 	<beans:bean class="xyz.itwill.auth.CustomPasswordEncoder" id="customPasswordEncoder"/>
 	-->
 
	<!-- To authenticate with user information and role information stored in tables, set users-by-username-query and authorities-by-username-query properties in the jdbc-user-service element -->
	<!-- users-by-username-query property: Set the SELECT statement to search for the id, password, and active status of the matching username - for authentication -->
	<!-- authorities-by-username-query property: Set the SELECT statement to search for the id and role (ROLE) of the matching username - for providing roles to successfully authenticated users -->
	<!-- 
 	<authentication-manager>
 		<authentication-provider>
 			<jdbc-user-service data-source-ref="dataSource"
 				users-by-username-query="select userid, passwd, enabled from security_user where userid=?"
 				authorities-by-username-query="select userid, auth from security_auth where userid=?"/>
 			<password-encoder ref="passwordEncoder"/>
 		</authentication-provider>
 	</authentication-manager>
 	 --> 
 	 
 	<!-- user-service-ref property: Set the Spring Bean identifier (beanName) of the class with the method that retrieves user and role information from the table into a UserDetails object (CustomUserDetails object) -->
 	<authentication-manager>
 		<authentication-provider user-service-ref="customUserDetailsService">
 			<password-encoder ref="passwordEncoder"/>
 		</authentication-provider>
 	</authentication-manager> 
 	
 	<context:component-scan base-package="xyz.itwill.auth"/>
 	<context:component-scan base-package="xyz.itwill.dao"/>
 	
 	<!-- BCryptPasswordEncoder class: Provides functionality to encrypt strings or compare encrypted strings -->
 	<beans:bean class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder" id="passwordEncoder"/>
</beans:beans>
``` 



### SecurityUser.java

```java
package xyz.itwill.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Create table security_user(userid varchar2(100) primary key, passwd varchar2(100),
// name varchar2(50), email varchar2(100), enabled varchar2(1));
// Create table security_auth(userid varchar2(100), auth varchar2(50),
// constraint auth_userid_fk foreign key(userid) references security_user(userid));
// Create unique index auth_userid_index on security_auth(userid, auth);

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser {
    private String userid;
    private String passwd;
    private String name;
    private String email;
    private String enabled;
    private List<SecurityAuth> securityAuthList;
}
```

### SecurityAuth.java

```java
package xyz.itwill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityAuth {
    private String userid;
    private String auth;
}
```


### SecurityMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill.mapper.SecurityUserMapper">
    <insert id="insertSecurityUser">
        insert into security_user values(#{userid}, #{passwd}, #{name}, #{email}, #{enabled})
    </insert>
    
    <insert id="insertSecurityAuth">
        insert into security_auth values(#{userid}, #{auth})
    </insert>
    
    <!--  
    <resultMap type="SecurityUser" id="securityUserResultMap">
        <id column="userid" property="userid"/>
        <result column="passwd" property="passwd"/>
        <result column="name" property="name"/>
        <result column="email" property="email"/>
        <result column="enabled" property="enabled"/>
        <collection property="securityAuthList" ofType="SecurityAuth">
            <result column="userid" property="userid"/>
            <result column="auth" property="auth"/>
        </collection>
    </resultMap>
    
    <select id="selectSecurityUserByUserid" resultMap="securityUserResultMap">
        select security_user.userid, passwd, name, email, enabled, auth from 
             security_user left join security_auth on security_user.userid=security_auth.userid
             where security_user.userid=#{userid}
    </select>
    -->
    
    <resultMap type="SecurityUser" id="securityUserResultMap">
        <id column="userid" property="userid"/>
        <result column="passwd" property="passwd"/>
        <result column="name" property="name"/>
        <result column="email" property="email"/>
        <result column="enabled" property="enabled"/>
        <collection property="securityAuthList" select="selectSecurityAuthByUserid" column="userid"/>
    </resultMap>
    
    <select id="selectSecurityAuthByUserid" resultType="SecurityAuth">
        select userid, auth from security_auth where userid=#{userid}
    </select>
    
    <select id="selectSecurityUserByUserid" resultMap="securityUserResultMap">
        select security_user.userid, passwd, name, email, enabled from 
             security_user where userid=#{userid}
    </select>    
</mapper>
```



### 1. SecurityUserMapper.java
```java
package xyz.itwill.mapper;

import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUser;

public interface SecurityUserMapper {
    int insertSecurityUser(SecurityUser user);
    int insertSecurityAuth(SecurityAuth auth);
    SecurityUser selectSecurityUserByUserid(String userid);
}
```

### 2. SecurityUserDAO.java
```java
package xyz.itwill.dao;

import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUser;

public interface SecurityUserDAO {
    int insertSecurityUser(SecurityUser user);
    int insertSecurityAuth(SecurityAuth auth);
    SecurityUser selectSecurityUserByUserid(String userid);
}
```

### 3. SecurityUserDAOImpl.java
```java
package xyz.itwill.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUser;
import xyz.itwill.mapper.SecurityUserMapper;

@Repository
@RequiredArgsConstructor
public class SecurityUserDAOImpl implements SecurityUserDAO {
    private final SqlSession sqlSession;

    @Override
    public int insertSecurityUser(SecurityUser user) {
        return sqlSession.getMapper(SecurityUserMapper.class).insertSecurityUser(user);
    }

    @Override
    public int insertSecurityAuth(SecurityAuth auth) {
        return sqlSession.getMapper(SecurityUserMapper.class).insertSecurityAuth(auth);
    }

    @Override
    public SecurityUser selectSecurityUserByUserid(String userid) {
        return sqlSession.getMapper(SecurityUserMapper.class).selectSecurityUserByUserid(userid);
    }
}
```


여기에 context:component-scan 를 설정하여 우선 annotation 쓸수있게 하고, 

SecurityUser DTO, Mapper, DAO, Service, Controller 만들고, 그렇게 DATA 에 인증 정보가 들어가면, 그걸 Select로 불러 오기위해 security-context.xml 에 authentication-manager 안에 	users-by-username-query 를 이용하여 사용 할수있지만 mapper 에 그냥 새롭게 sql select 명령어를 사용하여 sql 명령어를 만들어서 불러왔다. 
이렇게 인증된 사용자 정보와 권한을 클래스에 저장 하여 사용할수있는 CustomUserDetail class 를 UserDetails  interface 를  상속 받아서 만들고 그안에 인증된 사용자 정보가 저장될 필드를 만들어 그안에 정보를 저장 한다. 
CustomUserDetails.java 안에 저장 된 정보및 권한 정보를 검색하여 인증처리후 저장된 UserDetails 객체를 반환하는 메소드가 작성된 클래스로 CustomUserDetailsService.java 를 만든다. 

CustomUserDetailsService.java 는 UserDetailsService interface 를 상속 받아 만든다
