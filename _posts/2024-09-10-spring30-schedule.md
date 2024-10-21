---
layout: single
title: 2024/06
---
## User

#### UserDTO.java
```java 
package xyz.itwill09.dto;

import lombok.Data;

@Data
public class Userinfo {
    private String userid;
    private String password;
    private String name;
    private String email;
    private int auth;
}
```

#### Mapper
#### UserinfoMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill09.mapper.UserinfoMapper">
	<insert id="insertUserinfo">
		insert into userinfo values(#{userid}, #{password}, #{name}, #{email}, #{auth})
	</insert>
	
	<update id="updateUserinfo">
		update userinfo
		<set>
			<if test="password != null and password != ''">
				password=#{password},  
			</if>
			<if test="name != null and name != ''">
				name=#{name},  
			</if>
			<if test="email != null and email != ''">
				email=#{email},  
			</if>
			<if test="auth == 1 or auth == 9">
				auth=#{auth}
			</if>
		</set>
		where userid=#{userid}
	</update>
	
	<delete id="deleteUserinfo">
		delete from userinfo where userid=#{userid}
	</delete>
	
	<select id="selectUserinfo" resultType="Userinfo">
		select userid, password, name, email, auth from userinfo where userid=#{userid}
	</select>
	
	<select id="selectUserinfoList" resultType="Userinfo">
		select userid, password, name, email, auth from userinfo order by userid
	</select>
</mapper>
```
#### UserinfoMapper.java interface 

```java
package xyz.itwill09.mapper;

import java.util.List;

import xyz.itwill09.dto.Userinfo;

public interface UserinfoMapper {
	int insertUserinfo(Userinfo userinfo);
	int updateUserinfo(Userinfo userinfo);
	int deleteUserinfo(String userid);
	Userinfo selectUserinfo(String userid);
	List<Userinfo> selectUserinfoList();
}
```


### `UserinfoDAO` Interface

```java
package xyz.itwill09.dao;

import java.util.List;

import xyz.itwill09.dto.Userinfo;

public interface UserinfoDAO {
	int insertUserinfo(Userinfo userinfo);
	int updateUserinfo(Userinfo userinfo);
	int deleteUserinfo(String userid);
	Userinfo selectUserinfo(String userid);
	List<Userinfo> selectUserinfoList();
}
```

### `UserinfoDAOImpl` Class

```java
package xyz.itwill09.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.Userinfo;
import xyz.itwill09.mapper.UserinfoMapper;

@Repository
@RequiredArgsConstructor
public class UserinfoDAOImpl implements UserinfoDAO {
	private final SqlSession sqlSession;

	@Override
	public int insertUserinfo(Userinfo userinfo) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(UserinfoMapper.class).insertUserinfo(userinfo);
	}

	@Override
	public int updateUserinfo(Userinfo userinfo) {
		return sqlSession.getMapper(UserinfoMapper.class).updateUserinfo(userinfo);	
	}

	@Override
	public int deleteUserinfo(String userid) {
		return sqlSession.getMapper(UserinfoMapper.class).deleteUserinfo(userid);	
	}

	@Override
	public Userinfo selectUserinfo(String userid) {
		return sqlSession.getMapper(UserinfoMapper.class).selectUserinfo(userid);	
	}

	@Override
	public List<Userinfo> selectUserinfoList() {
		return sqlSession.getMapper(UserinfoMapper.class).selectUserinfoList();	
	}
}
```

#### jbcrypt
1. **Add jbcrypt to Your Project**:
   - Include the `jbcrypt` library in your project’s build configuration. For Maven, add it to your `pom.xml` file.
```xml
<!-- https://mvnrepository.com/artifact/org.mindrot/jbcrypt -->
<!-- => A library that provides encryption functionality -->
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```

2. **Encrypt Passwords**:
   - Use the `BCrypt.hashpw(String password, String salt)` method to encrypt a password. 
   - The method takes two parameters: the password to encrypt and a salt string.
   - Different salts will result in different encrypted passwords.

3. **Generate a Salt**:
   - Use `BCrypt.gensalt(int log_rounds)` to generate a salt.
   - The parameter `log_rounds` determines the complexity of the salt. If you don’t provide a value, it defaults to 10.

4. **Verify Passwords**:
   - Use the `BCrypt.checkpw(String plainText, String hashed)` method to compare a plain text password with an encrypted password.
   - It returns `false` if the passwords do not match, and `true` if they do.


### `UserinfoService` Interface

```java
package xyz.itwill09.service;

import java.util.List;

import xyz.itwill09.dto.Userinfo;

public interface UserinfoService {
	void addUserinfo(Userinfo userinfo);
	void modifyUserinfo(Userinfo userinfo);
	void removeUserinfo(String userid);
	Userinfo getUserinfo(String userid);
	List<Userinfo> getUserinfoList();
	Userinfo loginAuth(Userinfo userinfo);
}
```

### `UserinfoServiceImpl` Class

```java
package xyz.itwill09.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dao.UserinfoDAO;
import xyz.itwill09.dto.Userinfo;
import xyz.itwill09.exception.ExistsUserinfoException;
import xyz.itwill09.exception.LoginAuthFailException;
import xyz.itwill09.exception.UserinfoNotFoundException;

// Method to encrypt passwords received from users
// 1. Include the jbcrypt library in your project build - Maven: pom.xml
// 2. Use BCrypt.hashpw(String password, String salt) static method to encrypt the password
// => The method parameters are the string to be encrypted and a salt string
// => Different salts will result in different encrypted passwords
// => BCrypt.gensalt(int log_rounds): A static method that returns a salt based on the length of the salt provided
// => If no value is given, it defaults to 10
// 3. Use BCrypt.checkpw(String plainText, String hashed) static method to compare a plain text string with an encrypted string
// => Returns false if they differ, and true if they match

@Service
@RequiredArgsConstructor
public class UserinfoServiceImpl implements UserinfoService {
	private final UserinfoDAO userinfoDAO;
	
	@Override
	@Transactional
	public void addUserinfo(Userinfo userinfo) {
		if(userinfoDAO.selectUserinfo(userinfo.getUserid()) != null) {
			// Throw a custom exception with a specific message if the user ID already exists
			throw new ExistsUserinfoException("The ID is already in use.", userinfo);
		}
		
		// Encrypt the password of the provided user info and set it
		String hashedPassword = BCrypt.hashpw(userinfo.getPassword(), BCrypt.gensalt());
		userinfo.setPassword(hashedPassword);
		
		userinfoDAO.insertUserinfo(userinfo);	
	}

	@Override
	@Transactional
	public void modifyUserinfo(Userinfo userinfo) {
		if(userinfoDAO.selectUserinfo(userinfo.getUserid()) == null) {
			throw new UserinfoNotFoundException("User info not found for the given ID");
		}
		if(userinfo.getPassword() != null && ! userinfo.getPassword().equals("")) {
			String hashedPassword = BCrypt.hashpw(userinfo.getPassword(), BCrypt.gensalt());
			userinfo.setPassword(hashedPassword);
		}
		userinfoDAO.updateUserinfo(userinfo);
	}
	
	@Transactional
	@Override
	public void removeUserinfo(String userid) {
		if(userinfoDAO.selectUserinfo(userid) == null) {
			throw new UserinfoNotFoundException("User info not found for the given ID");
		}
		userinfoDAO.deleteUserinfo(userid);
	}

	@Override
	public Userinfo getUserinfo(String userid) {
		Userinfo userinfo = userinfoDAO.selectUserinfo(userid);
		if(userinfo == null) {
			throw new UserinfoNotFoundException();
		}
		return userinfo;
	}

	@Override
	public List<Userinfo> getUserinfoList() {
		return userinfoDAO.selectUserinfoList();
	}

	@Override
	public Userinfo loginAuth(Userinfo userinfo) {
		Userinfo authUserinfo = userinfoDAO.selectUserinfo(userinfo.getUserid());
		if(authUserinfo == null) {
			throw new LoginAuthFailException("No such ID", userinfo.getUserid());
		}
		if(!BCrypt.checkpw(userinfo.getPassword(), authUserinfo.getPassword())) {
			throw new LoginAuthFailException("ID does not exist or password is incorrect", userinfo.getUserid());
		}
		return authUserinfo;
	}
}
```

### `UserinfoController` Class


```java
package xyz.itwill09.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.Userinfo;
import xyz.itwill09.exception.BadRequestException;
import xyz.itwill09.service.UserinfoService;

@Controller
@RequestMapping("/userinfo")
@RequiredArgsConstructor
public class UserinfoController {
	private final UserinfoService userinfoService;
	
	// Method to return the view name of the JSP document to input user information
	// => Page accessible only by administrators
	/*
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpSession session) {
		Userinfo loginUserinfo=(Userinfo)session.getAttribute("loginUserinfo");
		// Use try~catch to return the view name of the error page if an exception occurs
		// => 500 error code does not occur
		try {
			// If the requesting user is not logged in or not an administrator, manually throw an exception
			if(loginUserinfo == null || loginUserinfo.getAuth() != 9) {
				throw new BadRequestException("The page was requested in an abnormal way.");
			}
		} catch (BadRequestException e) {
			return "userinfo/user_error";
		}
		return "userinfo/user_write";
	}
	*/
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpSession session) {
		Userinfo loginUserinfo=(Userinfo)session.getAttribute("loginUserinfo");

		// If the requesting user is not logged in or not an administrator, manually throw an exception
		if(loginUserinfo == null || loginUserinfo.getAuth() != 9) {
			throw new BadRequestException("The page was requested in an abnormal way.");
		}
		return "userinfo/user_write";
	}
	
	// Method to receive user information and insert it as a row in the USERINFO table, returning a URL to request the login page
	/*
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute Userinfo userinfo, Model model) {
		try {
			// If the ID of the received user information is duplicated, an ExistsUserinfoException is thrown
			userinfoService.addUserinfo(userinfo);
		} catch (ExistsUserinfoException e) {
			model.addAttribute("message", e.getMessage());
			return "userinfo/user_write";
		}
		return "redirect:/userinfo/login";
	}
	*/
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute Userinfo userinfo, Model model) {
		// If the ID of the received user information is duplicated, an ExistsUserinfoException is thrown
		userinfoService.addUserinfo(userinfo);
		return "redirect:/userinfo/login";
	}
	
	// Method to return the view name of the JSP document to input authentication information
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "userinfo/user_login";
	}
	
	// Method to receive authentication information, search for rows stored in the USERINFO table for login processing,
	// and return the view name of the JSP document that displays a welcome message
	// => On successful authentication, store the user's information in the session
	/*
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute Userinfo userinfo, Model model, HttpSession session) {
		try {
			// If authentication with the provided user information fails, a LoginAuthFailException is thrown
			Userinfo authUserinfo=userinfoService.loginAuth(userinfo);
			session.setAttribute("loginUserinfo", authUserinfo);
		} catch (LoginAuthFailException e) {
			model.addAttribute("message", e.getMessage());
			model.addAttribute("userid", userinfo.getUserid());
			return "userinfo/user_login";
		}
		
		return "userinfo/user_login";
	}
	*/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute Userinfo userinfo, Model model, HttpSession session) {
		// If authentication with the provided user information fails, a LoginAuthFailException is thrown
		Userinfo authUserinfo=userinfoService.loginAuth(userinfo);
		session.setAttribute("loginUserinfo", authUserinfo);
		return "userinfo/user_login";
	}
	
	// Method to handle logout and return the URL to request the login page
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//session.removeAttribute("loginUserinfo");
		session.invalidate();
		return "redirect:/userinfo/login";
	}
	
	// Method to search for all rows stored in the USERINFO table, store them as Request Scope attributes,
	// and return the view name of the JSP document displaying the user list
	// => Page accessible only by logged-in users
	/*
	@RequestMapping("/list")
	public String list(Model model, HttpSession session) {
		try {
			if(session.getAttribute("loginUserinfo") == null) {
				throw new BadRequestException("The page was requested in an abnormal way.");
			}
		} catch (BadRequestException e) {
			return "userinfo/user_error";
		}
		model.addAttribute("userinfoList", userinfoService.getUserinfoList());
		return "userinfo/user_list";
	}
	*/
	@RequestMapping("/list")
	public String list(Model model, HttpSession session) {
		if(session.getAttribute("loginUserinfo") == null) {
			throw new BadRequestException("The page was requested in an abnormal way.");
		}
		model.addAttribute("userinfoList", userinfoService.getUserinfoList());
		return "userinfo/user_list";
	}

	// Method to receive an ID, search for the row stored in the USERINFO table, store it as a Request Scope attribute,
	// and return the view name of the JSP document displaying user information
	// => Page accessible only by logged-in users
	/*
	@RequestMapping("/view")
	public String view(@RequestParam String userid, Model model, HttpSession session) {
		try {
			if(session.getAttribute("loginUserinfo") == null) {
				throw new BadRequestException("The page was requested in an abnormal way.");
			}
		} catch (BadRequestException e) {
			return "userinfo/user_error";
		}
		model.addAttribute("userinfo", userinfoService.getUserinfo(userid));
		return "userinfo/user_view";
	}
	*/
	@RequestMapping("/view")
	public String view(@RequestParam String userid, Model model, HttpSession session) {
		if(session.getAttribute("loginUserinfo") == null) {
			throw new BadRequestException("The page was requested in an abnormal way.");
		}
		model.addAttribute("userinfo", userinfoService.getUserinfo(userid));
		return "userinfo/user_view";
	}
	
	// Method to receive an ID, search for the row stored in the USERINFO table, store it as a Request Scope attribute,
	// and return the view name of the JSP document for modifying user information
	// => Page accessible only by administrators
	/*
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(@RequestParam String userid, Model model, HttpSession session) {
		Userinfo loginUserinfo=(Userinfo)session.getAttribute("loginUserinfo");
		try {
			// If the requesting user is not logged in or not an administrator, manually throw an exception
			if(loginUserinfo == null || loginUserinfo.getAuth() != 9) {
				throw new BadRequestException("The page was requested in an abnormal way.");
			}
		} catch (BadRequestException e) {
			return "userinfo/user_error";
		}
		model.addAttribute("userinfo", userinfoService.getUserinfo(userid));
		return "userinfo/user_modify";
	}
	*/
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(@RequestParam String userid, Model model, HttpSession session) {
		Userinfo loginUserinfo=(Userinfo)session.getAttribute("loginUserinfo");
		// If the requesting user is not logged in or not an administrator, manually throw an exception
		if(loginUserinfo == null || loginUserinfo.getAuth() != 9) {
			throw new BadRequestException("The page was requested in an abnormal way.");
		}
		model.addAttribute("userinfo", userinfoService.getUserinfo(userid));
		return "userinfo/user_modify";
	}
	
	// Method to receive updated user information, modify the corresponding row in the USERINFO table,
	// and return the URL to request the page displaying user information
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute Userinfo userinfo, HttpSession session) {
		userinfoService.modifyUserinfo(userinfo);
		
		// If the logged-in user and the modified user are the same, update the session with the modified user's information
		Userinfo loginUserinfo=(Userinfo)session.getAttribute("loginUserinfo");
		if(loginUserinfo.getUserid().equals(userinfo.getUserid())) {
			session.setAttribute("loginUserinfo", userinfoService.getUserinfo(userinfo.getUserid()));
		} 
		
		return "redirect:/userinfo/view?userid="+userinfo.getUserid();
	}
	
	// Method to handle a request to delete a row from the USERINFO table using the provided ID
// and return a URL to the page displaying the list of users
// => Accessible only by administrators
/*
@RequestMapping("/remove")
public String remove(@RequestParam String userid, HttpSession session) {
    Userinfo loginUserinfo = (Userinfo) session.getAttribute("loginUserinfo");
    try {
        // If the requesting user is either not logged in or not an administrator, manually throw an exception
        if (loginUserinfo == null || loginUserinfo.getAuth() != 9) {
            throw new BadRequestException("The page has been requested in an invalid manner.");
        }
    } catch (BadRequestException e) {
        return "userinfo/user_error";
    }
    
    userinfoService.removeUserinfo(userid);
    
    // If the logged-in user and the user whose information is being deleted have the same ID, redirect to the logout page
    if (loginUserinfo.getUserid().equals(userid)) {
        return "redirect:/userinfo/logout";
    }
    
    return "redirect:/userinfo/list";
}
*/
@RequestMapping("/remove")
public String remove(@RequestParam String userid, HttpSession session) {
    Userinfo loginUserinfo = (Userinfo) session.getAttribute("loginUserinfo");
    // If the requesting user is either not logged in or not an administrator, manually throw an exception
    if (loginUserinfo == null || loginUserinfo.getAuth() != 9) {
        throw new BadRequestException("The page has been requested in an invalid manner.");
    }
    
    userinfoService.removeUserinfo(userid);
    
    // If the logged-in user and the user whose information is being deleted have the same ID, redirect to the logout page
    if (loginUserinfo.getUserid().equals(userid)) {
        return "redirect:/userinfo/logout";
    }
    
    return "redirect:/userinfo/list";
}

/*
 //@ExceptionHandler: Annotation used to define methods for handling exceptions
 // => Methods annotated with @ExceptionHandler are automatically called by the Front Controller
 // when exceptions occur in request-handling methods of the Controller class (using Spring AOP)
 // => Exception-handling methods can have parameters to receive exception objects from the Front Controller
 // and return a view name for the client response - redirects are also possible
 // value attribute: Sets the Class object of the exception class to be handled
 // => If no other attributes are present, only the value attribute can be set
 @ExceptionHandler(value = BadRequestException.class)
 public String badRequestException() {
     return "userinfo/user_error";
 }
 
 @ExceptionHandler(ExistsUserinfoException.class)
 public String existsUserinfoException(ExistsUserinfoException exception, Model model) {
     model.addAttribute("message", exception.getMessage());
     model.addAttribute("userinfo", exception.getUserinfo());
     return "userinfo/user_write";
 }
 
 @ExceptionHandler(LoginAuthFailException.class)
 public String loginAuthFailException(LoginAuthFailException exception, Model model) {
     model.addAttribute("message", exception.getMessage());
     model.addAttribute("userid", exception.getUserid());
     return "userinfo/user_login";
 }
 
 @ExceptionHandler(UserinfoNotFoundException.class)
 public String userinfoNotFoundException() {
     return "userinfo/user_error";
 }
 
 @ExceptionHandler(Exception.class)
 public String exception() {
     return "userinfo/user_error";
 }
 */

```

#### Exception Class

**BadRequestException.java**

```java
package xyz.itwill09.exception;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public BadRequestException() {
        // TODO Auto-generated constructor stub
    }
    
    public BadRequestException(String message) {
        super(message);
    }
}
```

**ExistsUserinfoException.java**

```java
package xyz.itwill09.exception;

import lombok.Getter;
import xyz.itwill09.dto.Userinfo;

// Exception class created to be thrown when the ID of the user information being registered
// is duplicated with an existing user ID in the USERINFO table.
// => Inherits from the Exception class (RuntimeException class)
public class ExistsUserinfoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    // Field to store values needed for exception handling
    // => Field to store user information received from user input
    @Getter
    private Userinfo userinfo;
    
    public ExistsUserinfoException() {
        // TODO Auto-generated constructor stub
    }
    
    public ExistsUserinfoException(String message, Userinfo userinfo) {
        super(message);
        this.userinfo = userinfo;
    }
}
```

**LoginAuthFailException.java**

```java
package xyz.itwill09.exception;

import lombok.Getter;

// Exception class created to be thrown when authentication fails for the ID and password
// provided during login processing
public class LoginAuthFailException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    private String userid;
    
    public LoginAuthFailException() {
        // TODO Auto-generated constructor stub
    }

    public LoginAuthFailException(String message, String userid) {
        super(message);
        this.userid = userid;
    }
}
```

**UserinfoNotFoundException.java**

```java
package xyz.itwill09.exception;

// Exception class created to be thrown when user information with the provided ID cannot be found
// during operations such as modification, deletion, or search
public class UserinfoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserinfoNotFoundException() {
        // TODO Auto-generated constructor stub
    }
    
    public UserinfoNotFoundException(String message) {
        super(message);
    }
}
```

#### ExceptionController.java 

```java
package xyz.itwill09.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import xyz.itwill09.exception.BadRequestException;
import xyz.itwill09.exception.ExistsUserinfoException;
import xyz.itwill09.exception.LoginAuthFailException;
import xyz.itwill09.exception.UserinfoNotFoundException;

//@ControllerAdvice: Annotation to register a Controller class with only exception handling methods as a Spring Bean
// => Allows handling exceptions thrown from request processing methods in all Controller classes
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = BadRequestException.class)
    public String badRequestException() {
        return "userinfo/user_error";
    }
    
    @ExceptionHandler(ExistsUserinfoException.class)
    public String existsUserinfoException(ExistsUserinfoException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("userinfo", exception.getUserinfo());
        return "userinfo/user_write";
    }
    
    @ExceptionHandler(LoginAuthFailException.class)
    public String loginAuthFailException(LoginAuthFailException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("userid", exception.getUserid());
        return "userinfo/user_login";
    }
    
    @ExceptionHandler(UserinfoNotFoundException.class)
    public String userinfoNotFoundException() {
        return "userinfo/user_error";
    }
    
    @ExceptionHandler(Exception.class)
    public String exception() {
        return "userinfo/user_error";
    }
}
```