---
layout: single
title: 2024-09-05-spring24-mybatis-test
---

#### junit, spring test -pom.xml 

```xml
	
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
		<!-- => SpringTest 기능을 제공하기 위한 라이브러리 -->
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-test</artifactId>
		    <version>${org.springframework-version}</version>
		    <!-- <scope>test</scope> -->
		</dependency>
```




#### DataSourceTest.java


```java
package xyz.itwill.spring;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.slf4j.Slf4j;

// How to write a test program using Spring Test functionality for unit testing
// => Unit tests: DAO classes, Service classes, Controller classes, etc.
// 1. Add the junit library and spring-test library to the project build - Maven: pom.xml
// 2. Modify the configuration file of the log implementation used in the test program
// => Change the content of the [log4j.xml] file in the [src/test/resources] package folder
// 3. Write the test class (test program) in the [src/test/java] package folder
// => Write the class after commenting out the scope attribute of the junit library and spring-test library
// 4. Run the test program

//@RunWith: Annotation to set the class for executing the test program
// => Configures the test class to be instantiated and calls the test methods for execution
// value attribute: Sets the Class object of the class used to execute the test program
// => If there are no other attributes, you can set just the value attribute
// SpringJUnit4ClassRunner creates a Spring container (ApplicationContext object) for the test class
@RunWith(SpringJUnit4ClassRunner.class)

//@WebAppConfiguration: Annotation to provide a WebApplicationContext object instead of an ApplicationContext object
@WebAppConfiguration

//@ContextConfiguration: Annotation to provide the Spring Bean Configuration File to the Spring container
// locations attribute: Sets the path of the Spring Bean Configuration File as the attribute value
// => If there are multiple Spring Bean Configuration Files, provide the attribute value in an array format using {} brackets
// => The path of the Spring Bean Configuration File should be provided using the file prefix for file system paths
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Slf4j
public class DataSourceTest {
    // Field to store the object used in the test class methods
    // => Dependency injection using @Autowired annotation to store the object in the field
    // => Field-level dependency injection is possible - constructor-level dependency injection is not possible
    @Autowired
    private DataSource dataSource;

    //@Test: Annotation to mark a method as a test method
    // => Automatically called after the test class is instantiated by SpringJUnit4ClassRunner
    @Test
    public void testDataSource() throws SQLException {
        log.info("DataSource = " + dataSource);
        Connection connection = dataSource.getConnection();
        log.info("Connection = " + connection);
        connection.close();
    }
}
```

### Summary of Key Points

1. **`@RunWith` Annotation**: Configures the test class to use `SpringJUnit4ClassRunner` to run the tests.
2. **`@WebAppConfiguration` Annotation**: Configures the context to be a `WebApplicationContext`.
3. **`@ContextConfiguration` Annotation**: Provides the path to the Spring Bean configuration file.
4. **`@Autowired` Annotation**: Injects the `DataSource` object automatically.
5. **`@Test` Annotation**: Marks a method as a test method to be executed by the test runner.

This configuration sets up a Spring test environment and tests if a `DataSource` object can be obtained and a database connection can be established.

#### StudentServiceTest.java


```java
package xyz.itwill.spring;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.slf4j.Slf4j;
import xyz.itwill09.dto.Student;
import xyz.itwill09.service.StudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
// @FixMethodOrder: Annotation to set the order of test method calls
// value attribute: Sets the constant field of the MethodSorters Enum as the attribute value
// => MethodSorters.DEFAULT: Test methods are called according to the internal rules of the JUnit program
//    - Test methods are called in the same order every time the test program is executed
// => MethodSorters.JVM: Test methods are called by the JVM program
//    - Test methods are called in an irregular order every time the test program is executed
// => MethodSorters.NAME_ASCENDING: Test methods are called in the order of their names
//    - Test methods are called in the same order every time the test program is executed
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@Slf4j
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    
    @Test
    public void testAddStudent() {
        Student student = Student.builder()
                .no(7000)
                .name("Go Gil-dong")
                .phone("010-5412-2134")
                .address("Gwangjin-gu, Seoul")
                .birthday("1998-07-27")
                .build();
        studentService.addStudent(student);
    }
    
    @Test
    public void testGetStudentList() {
        List<Student> studentList = studentService.getStudentList();
        
        for (Student student : studentList) {
            log.info(student.toString());
        }
    }
}
```

### Summary of Key Points

1. **`@RunWith(SpringJUnit4ClassRunner.class)`**: Configures JUnit to use Spring's test runner to handle Spring components in tests.
2. **`@WebAppConfiguration`**: Indicates that the test will be run in a web application context.
3. **`@ContextConfiguration`**: Specifies the locations of the Spring configuration files to load the application context.
4. **`@FixMethodOrder`**: Specifies the order in which test methods are executed.
   - **`MethodSorters.DEFAULT`**: Uses JUnit's internal rules for method order.
   - **`MethodSorters.JVM`**: Relies on the JVM for method order, which can be irregular.
   - **`MethodSorters.NAME_ASCENDING`**: Executes test methods in alphabetical order by method name.
5. **`@Autowired`**: Injects the `StudentService` dependency into the test class.
6. **`@Test`**: Marks methods as test cases to be run by JUnit.

The test class includes two test methods:
- `testAddStudent()`: Adds a student to the service.
- `testGetStudentList()`: Retrieves and logs the list of students.

#### StudentControllerTest.java


```java
package xyz.itwill.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
// The [*] symbol can be used to provide Spring Bean Configuration Files
// => Using [**] includes Spring Bean Configuration Files in subfolders
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@Slf4j
public class StudentControllerTest {
    // Inject the WebApplicationContext object (Spring container) into the field
    @Autowired
    private WebApplicationContext context;
    
    // Field to store the MockMvc object
    // => MockMvc object: Provides virtual requests and responses - simulates Front Controller functionality
    private MockMvc mvc;
    
    //@Before: Annotation that provides functionality to call methods to initialize before running test methods
    @Before
    public void setup() {
        // MockMvcBuilders.webAppContextSetup(WebApplicationContext context)
        // => Method that creates and returns a MockMvcBuilder object
        // => Passes the WebApplicationContext object to provide the MockMvc object with Spring container
        // MockMvcBuilder.build() : Method that returns a MockMvc object
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        log.info("MockMvc object created");
    }
    
    @Test
    public void testStudentDisplay() throws Exception {
        // MockMvcRequestBuilders.get(String url) : Static method that returns a RequestBuilder object to request a page via GET method
        // MockMvc.perform(RequestBuilder requestBuilder) : Method that uses the RequestBuilder object to request the page - calls the request handling method
        // => Returns a ResultActions object with the results of the request handling method
        // ResultActions.andReturn() : Method that returns an MvcResult object with the return value of the request handling method
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/student/display")).andReturn();
        
        log.info(result.getModelAndView().getViewName());
        log.info(result.getModelAndView().getModel().toString());
    }
}
```

### Summary of Key Points

1. **`@RunWith(SpringJUnit4ClassRunner.class)`**: Configures JUnit to use Spring's test runner to handle Spring components in tests.
2. **`@WebAppConfiguration`**: Indicates that the test will be run in a web application context.
3. **`@ContextConfiguration`**: Specifies the locations of the Spring configuration files to load the application context.
   - Using `[**]` includes configuration files from subfolders.
4. **`@Autowired`**: Injects the `WebApplicationContext` dependency into the test class.
5. **`MockMvc`**: Provides a way to perform HTTP requests and verify results without starting a real server.
6. **`@Before`**: Initializes the `MockMvc` object before running the test methods.
7. **`@Test`**: Marks methods as test cases to be run by JUnit.
   - **`testStudentDisplay()`**: Sends a GET request to `/student/display` and logs the view name and model attributes from the result.

tx-TransactionManager 설치- pom.xml 에는 이미 spring-jdbc 있어서 따로 빌트 안해도되고
root-context.xml 에 설정 
```xml
	<!-- TransactionManager 관련 클래스(DataSourceTransactionManager 클래스)를 Spring Bean으로 등록 -->
	<!-- => Spring Bean의 식별자(beanName)를 반드시 [transactionManager]로 설정 -->
	<!-- => dataSource 필드에 TransactionManager 객체에 의해 트렌젝션이 관리될 DataSource 객체가 
	저장 되도록 의존성 주입 - Setter Injection -->
	<bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
		<property name="dataSource" ref="dataSource"/>
	</bean>
```
sevlet-context.xml 에 namespace 에서 우선 tx 설정하고, 다시 소스로 와서 
tx:advice 를 설정 하면됨
```xml
<!-- TransactionManager 객체를 사용해 트렌젝션를 관리할 수 있도록 설정하기 위해 tx 네임스페이스에
	spring-tx.xsd 파일을 제공받아 엘리먼트를 사용할 수 있도록 설정 -->
	<!-- advice : TransactionManager 객체로 Advisor를 생성하기 위한 엘리먼트 -->
	<!-- Advisor : 삽입위치(JoinPoint)가 정해져 있는 횡단관심코드가 작성된 Advice 클래스 -->
	<!-- id 속성 : advice 엘리먼트를 구분하기 위한 식별자를 속성값으로 설정 -->
	<!-- transaction-manager 속성 : TransactionManager 기능을 제공하는 Spring Bean의 식별자
	(beanName)를 속성값으로 설정 -->
	<!-- => TransactionManager 객체를 사용해 예외가 발생되지 않은 경우 커밋 처리하고
	예외가 발생될 경우 롤백 처리 -->
	<!-- attributes : TransactionManager 객체에 의해 커밋 또는 롤백 처리될 메소드의 목록을
	설정하기 위한 엘리먼트 - 하위 엘리먼트 : method 엘리먼트 -->
	<!-- method : TransactionManager 객체에 관리될 메소드의 이름과 트렌젝션 처리 방식을 설정하기 위한 엘리먼트 -->
	<!-- => 메소드의 이름에는 [*] 기호를 사용해 설정 가능 -->
	<!-- name 속성 : TransactionManager 객체에 관리될 메소드의 이름을 속성값으로 설정 -->
	<!-- rollback-for 속성 : 롤백 처리하기 위한 예외를 속성값으로 설정 -->
	<!-- read-only 속성 : false(기본) 또는 true(롤백처리 불필요) 중 하나를 속성값으로 설정 -->
	<tx:advice id="txAdvisor" transaction-manager="transactionManager">
		<tx:attributes>
			<tx:method name="add*" rollback-for="Exception"/>
			<tx:method name="modify*" rollback-for="Exception"/>
			<tx:method name="remove*" rollback-for="Exception"/>
			<tx:method name="get*" read-only="true"/>
		</tx:attributes>
	</tx:advice>
	
	<!-- Spring AOP 기능을 사용하기 위해 aop 네임스페이스에 spring-aop.xsd 파일을 제공받아 
	엘리먼트를 사용할 수 있도록 설정 -->
	<!-- advisor : Advisor를 제공받아 사용하기 위한 엘리먼트 -->
	<!-- advice-ref 속성 : Advisor를 생성한 advice 엘리먼트의 식별자를 속성값으로 설정 -->
	<aop:config>
		<aop:advisor advice-ref="txAdvisor" pointcut="execution(* xyz.itwill09.service..*(..))"/>
	</aop:config>
```