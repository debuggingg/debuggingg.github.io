---
layout: single
title: 2024-09-04-spring23-mybatis-ex-Student
---

1. **Table → DTO Class → DAO Class (MyBatis) → Service Class → Controller Class**
2. **JUnit Test Program**: Test whether the JSP documents function correctly and run them combined with JSP (HTML) documents.
---
#### Student.java DTO 
```java
package xyz.itwill09.dto;

import lombok.Builder;
import lombok.Data;

// DTO Class: A class that represents a row in a table as a Java object
// => A class written to be used as a parameter for methods in data handling classes (DAO classes or Service classes)
// or to return values from methods
// => Used as a Command object to hold the values received in the request handling method of a Controller class
// - Written so that the names of the values and fields match

@Data
@Builder
public class Student {
    // When using the MyBatis framework, fields can be named the same as column names
    // for automatic mapping of column values in search results
    private int no;
    private String name;
    private String phone;
    private String address;
    private String birthday;
}
```




### StudentDAO.java
### StudentDAOImpl.java


```java
package xyz.itwill09.dao;

import java.util.List;

import xyz.itwill09.dto.Student;

public interface StudentDAO {
    int insertStudent(Student student);
    List<Student> selectStudentList();
}
```

```java
package xyz.itwill09.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.Student;
import xyz.itwill09.mapper.StudentMapper;

// DAO Class (Repository Class): A class that provides functionality to insert rows into, update, delete,
// and query from a storage medium
// => It is recommended to implement the class by inheriting an interface to minimize the impact on
// the Service class when the DAO class is replaced - Increases maintainability

// Uses a DBMS server as a storage medium to handle SQL commands for inserting, updating, deleting,
// and querying rows
// => Methods in the DAO class connect to the DBMS server to execute a single SQL command and return
// the execution result as a Java object (value) - JDBC

// When writing web programs with SpringMVC using the MyBatis framework to create a DAO class:
// 1. Build DataSource-related libraries and MyBatis-related libraries into the project - Maven: pom.xml
// => ojdbc, spring-jdbc(spring-tx), mybatis, mybatis-spring
// 2. Write the MyBatis framework configuration file (mybatis-config.xml - settings element)
// => The configuration file should be written in the [src/main/webapp] folder so that the Spring container
// (WebApplicationContext object) can access it to create the SqlSessionFactory object
// 3. Register DataSource-related classes, SqlSessionFactory-related classes, SqlSession-related classes,
// and TransactionManager-related classes as Spring Beans in the Spring Bean Configuration File (root-context.xml)
// 4. Write the DAO class using a mapper file

// The DAO class should be registered as a Spring Bean so that it can be provided as an object in the Service class
// => The DAO class is registered as a Spring Bean using the @Repository annotation
// => To ensure the Spring container processes the @Repository annotation, the package where the class is written
// must be configured to be scanned in the component-scan element of the Spring Bean Configuration File (servlet-context.xml)
@Repository
// Annotation that creates a constructor initializing only fields with final modifiers
@RequiredArgsConstructor
public class StudentDAOImpl implements StudentDAO {
    // The DAO class methods need an SqlSession object to receive SQL commands registered in the mapper,
    // send them to the DBMS server for execution, and return the results as Java objects
    // => A field is created to store the SqlSession object and dependency injection is used to provide the object
    // from the Spring container
    
    // Dependency injection using @Autowired annotation at the field level
    // @Autowired
    // private SqlSession sqlSession;

    // Dependency injection using a constructor with @Autowired annotation
    // => Constructor-level dependency injection: Prevents circular references
    // => If there is only one constructor, the @Autowired annotation can be omitted
    private final SqlSession sqlSession;

    @Override
    public int insertStudent(Student student) {
        return sqlSession.getMapper(StudentMapper.class).insertStudent(student);
    }

    @Override
    public List<Student> selectStudentList() {
        return sqlSession.getMapper(StudentMapper.class).selectStudentList();
    }
}
```


#### StudentService.java
#### StudentServiceImpl.java


```java
package xyz.itwill09.service;

import java.util.List;

import xyz.itwill09.dto.Student;

public interface StudentService {
    void addStudent(Student student);
    List<Student> getStudentList();
}
```

```java
package xyz.itwill09.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dao.StudentDAO;
import xyz.itwill09.dto.Student;

// Service Class: A class that provides data processing functionality in response to client requests
// => The methods in the Service class are written to call methods from the DAO object with commands required
// for data processing
// => It is recommended to implement the class by inheriting an interface to minimize the impact on
// the Controller class when the Service class is replaced - Increases maintainability

// The Service class should be registered as a Spring Bean so that it can be provided as an object in the Controller class
// => The Service class is registered as a Spring Bean using the @Service annotation
// => To ensure the Spring container processes the @Service annotation, the package where the class is written
// must be configured to be scanned in the component-scan element of the Spring Bean Configuration File (servlet-context.xml)
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    // The methods in the Service class need a DAO object to call methods from the DAO class
    // => A field is created to store the DAO object and dependency injection is used to provide the object
    // from the Spring container - Constructor-level dependency injection
    private final StudentDAO studentDAO;

    @Override
    public void addStudent(Student student) {
        studentDAO.insertStudent(student);        
    }

    @Override
    public List<Student> getStudentList() {
        return studentDAO.selectStudentList();
    }
}
```
#### StudentMapper.xml


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill09.mapper.StudentMapper">
    <insert id="insertStudent">
        insert into student values(#{no}, #{name}, #{phone}, #{address}, #{birthday})
    </insert>
    
    <select id="selectStudentList" resultType="Student">
        select no, name, phone, address, birthday from student order by no
    </select>
</mapper>
```
#### StudentMapper.java interface
```java
package xyz.itwill09.mapper;

import java.util.List;

import xyz.itwill09.dto.Student;

public interface StudentMapper {
	int insertStudent(Student student);
	List<Student> selectStudentList();
}
```
#### student_add.jsp, student_display.jsp

#### StudentController.java
Here is the translated `StudentController` class:

```java
package xyz.itwill09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.Student;
import xyz.itwill09.service.StudentService;

// How to use SpringMVC features to create a web program
// => Table >> DTO Class >> DAO Class (MyBatis) >> Service Class >> Controller Class
// >> Unit Program Testing - Testing Program (JUnit) >> Execute with JSP Document (HTML Document) (Integration Test)

// How to record log events generated by MyBatis framework's Log Factory into Spring framework's log implementation
// 1. Build the log4jdbc-log4j2-jdbc4 library into the project - Maven: pom.xml 
// 2. Modify the driverClassName and url fields in the DataSource related class bean element in the Spring Bean Configuration File (root-context.xml)
// 3. Create a [log4jdbc.log4j2.properties] file in the [src/main/resources] folder
// => File to configure the SpyLogDelegator class for providing MyBatis framework's log events to Spring framework's log implementation
// 4. Modify the environment configuration file (log4j.xml) to record log events generated by SpyLogDelegator object to Spring framework's log implementation - Add logger elements  

// Controller Class: Provides functionality to handle client requests
// => The request handling methods of the Controller class call Service object methods with the necessary commands for data processing

// The Controller class is registered as a Spring Bean so that it can be provided and used by the Front Controller
// => The Controller class is registered as a Spring Bean using the @Controller annotation
// => To process the @Controller annotation, the package where the class is written must be specified in the component-scan element of the Spring Bean Configuration File (servlet-context.xml)
@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    // Service object is needed to call Service class methods in the Controller class's methods
    // => Create a field to store the Service object and inject it from the Spring container - Constructor-based dependency injection
    private final StudentService studentService;
    
    // Request handling method that returns the view name of the JSP document for entering student information
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "student/student_add";
    }
    
    // Request handling method that receives student information, inserts it as a row into the STUDENT table,
    // and responds to a URL address that requests the student list output page
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Student student, Model model) {
        try {
            studentService.addStudent(student);
        } catch (Exception e) {
            model.addAttribute("message", "Student number is duplicated or there is an issue with the input value.");
            return "student/student_add";
        }
        return "redirect:/student/display"; // Redirect
    }
    
    // Request handling method that searches all rows stored in the STUDENT table, stores the returned student list as a Request Scope attribute,
    // and returns the view name of the JSP document that displays the student list
    @RequestMapping("/display")
    public String display(Model model) {
        model.addAttribute("studentList", studentService.getStudentList());
        return "student/student_display";
    }
}
```