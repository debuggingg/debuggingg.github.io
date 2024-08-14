---
layout: single
title: 2024/08/08 MyBatis-EX-Student
---
## mybatis 
In order => 
- **`log4j.xml`**: A log configuration file used to output MyBatis SQL logs.
- **`mybatis-config.xml`**: The configuration file for MyBatis, used to set up database connection details and specify the paths to mapper files.
- **Mapper XML File**: Defines SQL queries and the mapping of results.
- **DTO**: Defines data transfer objects used to encapsulate data.
- **DAO**: Uses `SqlSessionFactory` to execute SQL commands defined in the mapper files.
#### Download
https://blog.mybatis.org/ ->product->MyBatis 3->mybatis-3.5.16.zip
mybatis.org->logging-> 

Download files -> 
--> form professor 
under SRC: log4j.xml-> this is for finding error -
 under web-lnf->lib
--> from Zip 
mybatis-3.5.16 1.jar
reload4j-1.2.25.jar
asm-7.1.jar
cglib-3.3.0.jar
commons-logging-1.3.1.jar
javassist-3.30.2-GA.jar
log4j-api-2.23.1.jar
ognl-3.4.2.jar
--> from Professor 
slf4j-api-1.7.21.jar
slf4j-log4j12-1.7.21.jar


#### setting eclipse
window->preference ->xml(wild Web developer) -> click downlaod external resoures list referenced DTD 


--- 

#### Program Flow 
### MyBatis Components and Their Interactions

1. **`SqlSessionFactory` and `mybatis-config.xml`**
   - **`SqlSessionFactory`**: This is a factory class in MyBatis responsible for creating `SqlSession` objects. It needs to be created using configuration details defined in `mybatis-config.xml`.
   - **`mybatis-config.xml`**: This is the configuration file for MyBatis. It contains information about database connections, transaction management, type aliases, and mapper file locations. `SqlSessionFactory` is created based on this configuration file.

2. **`SqlSession` and Mapper Files**
   - **`SqlSession`**: This object is used to execute SQL commands mapped in the mapper files. It acts as a bridge between the application and the database.
   - **Mapper Files**: These XML files contain SQL commands and their mappings to Java objects. They define how SQL queries and updates are executed and how the results are mapped to objects.

3. **DAO (Data Access Object)**
   - **DAO**: In a DAO class, you create an instance of `SqlSession` using `SqlSessionFactory`. This is essential for performing database operations as defined in the mapper files.

4. **Interactions**
   - To perform any database operation, you first need to create a `SqlSessionFactory` object. This is done by reading the `mybatis-config.xml` configuration file.
   - Once you have the `SqlSessionFactory`, you can create a `SqlSession` instance from it. This `SqlSession` can then be used to execute SQL commands defined in the mapper files.
   - The mapper files contain SQL commands and are used by the `SqlSession` to interact with the database. 

5. **Logging**
   - **`log4j.xml`**: This configuration file is used for logging. It helps in capturing and displaying error messages and other log information, which is useful for debugging and monitoring the application.

In summary, the `mybatis-config.xml` file provides the necessary configuration to create a `SqlSessionFactory`, which in turn is used to create `SqlSession` objects. These `SqlSession` objects execute SQL commands defined in mapper files. Logging, often configured through `log4j.xml`, helps in tracking and debugging the application's execution.


---


#### create xml file-> name (can be anything ) (mybatis-config.xml) under SRC in order to create SqlSessionFactory 
Building SqlSessionFactory from XML
The configuration XML file contains settings for the core of the MyBatis system, including a DataSource for acquiring database Connection instances, as well as a TransactionManager for determining how transactions should be scoped and controlled. The full details of the XML configuration file can be found later in this document, but here is a simple example:
Here’s the English translation of your MyBatis configuration file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- MyBatis Configuration File: Provides the necessary information to create the SqlSessionFactory object -->
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- properties: Element to register one or more property elements -->
    <!-- resource attribute: Sets the file system path of the Properties file -->
    <!-- => Uses values stored in the Properties file instead of individual property elements -->
    <properties resource="oracle_url.properties">
        <!-- property: Element to provide values for the MyBatis configuration file -->
        <!-- => Values can be used in the configuration file in the format ${name} -->
        <!-- => Provides common values for multiple elements, increasing maintenance efficiency -->
        <property name="oracleDriver" value="oracle.jdbc.driver.OracleDriver"/>
    </properties>
    
    <!-- settings: Element to register one or more setting elements -->
    <settings>
        <!-- setting: Element to provide necessary information for the SqlSession object -->
        <!-- => The default values are used if settings elements are not specified -->
        <!-- => Only specify settings elements if the default values are not sufficient -->
        <!-- The mapUnderscoreToCamelCase option, when set to [true], provides automatic conversion of column names from snake case to camel case -->
        <!-- <setting name="mapUnderscoreToCamelCase" value="true"/> -->
        <!-- The jdbcTypeForNull option, when set to [NULL], treats object field values as [NULL] if they are [NULL] -->
        <setting name="jdbcTypeForNull" value="NULL"/>
        <!-- The callSettersOnNulls option, when set to [true], allows setting column values to [NULL] during UPDATE commands -->
        <setting name="callSettersOnNulls" value="true"/>
    </settings>
    
    <!-- typeAliases: Element to register one or more typeAlias elements -->
    <typeAliases>
        <!-- typeAlias: Element to set aliases for Java data types (DTO classes) to be used in XML-based mapper files -->
        <!-- type attribute: Specifies the Java data type -->
        <!-- alias attribute: Specifies the alias to be used instead of the Java data type -->
        <!-- => Provides an internal alias for Java data types -->
        <typeAlias type="xyz.itwill.dto.Student" alias="Student"/>
        <typeAlias type="xyz.itwill.dto.MyMember" alias="MyMember"/>
    </typeAliases>

    <!-- environments: Element to register one or more environment elements -->
    <!-- default attribute: Specifies the identifier (id attribute value) of the default environment -->
    <environments default="development">
        <!-- environment: Element to set up the information necessary for connecting to the DBMS server -->
        <!-- => Provides the information to create a Connection object -->
        <!-- id attribute: Specifies the identifier to distinguish environment elements -->
        <environment id="development">
            <!-- transactionManager: Element to configure transaction management (commit or rollback) -->
            <!-- type attribute: Specifies either [JDBC] or [MANAGED] -->
            <!-- => JDBC: Manages transactions using JDBC features -->
            <!-- => MANAGED: Manages transactions using a transaction management program -->
            <transactionManager type="JDBC"/>
            <!-- dataSource: Element to provide the information necessary for creating Connection objects -->
            <!-- type attribute: Specifies either [UNPOOLED], [POOLED], or [JNDI] -->
            <!-- => UNPOOLED: Creates Connection objects at runtime without pre-creating them -->
            <!-- => POOLED: Pre-creates Connection objects and provides them at runtime - DBCP -->
            <!-- => JNDI: Uses resources registered in the WAS program to provide Connection objects at runtime -->
            <dataSource type="POOLED">
                <!-- property: Element to provide values necessary for creating Connection objects -->
                <!-- name attribute: Specifies the name to distinguish values -->
                <!-- value attribute: Specifies the value -->
                <!-- <property name="driver" value="oracle.jdbc.driver.OracleDriver"/> -->
                <property name="driver" value="${oracleDriver}"/>
                <!-- <property name="url" value="jdbc:oracle:thin:@localhost:1521:xe"/> -->
                <property name="url" value="${local}"/>
                <property name="username" value="scott"/>
                <property name="password" value="tiger"/>
            </dataSource>
        </environment>
    </environments>
    
    <!-- mappers: Element to register one or more mapper elements -->
    <mappers>
        <!-- mapper: Element to register mapper files -->
        <!-- Mapper: Provides the information for registering SQL commands and mapping the results to Java objects -->
        <!-- => MyBatis framework supports both XML-based and Interface-based mapper files -->
        <!-- => iBatis framework supports only XML-based mapper files -->
        <!-- resource attribute: Specifies the file system path of XML-based mapper files -->
        <mapper resource="xyz/itwill/mapper/StudentMapper.xml"/>
        <mapper resource="xyz/itwill/mapper/MyMemberXMLMapper.xml"/>
    </mappers>
</configuration>
```



#### logging 
- MyBatis provides logging information through the use of an internal log factory. The internal log factory will delegate logging information to one of the following log implementations
-  SLF4J
- Apache Commons Logging
- Log4j 2
- Log4j (deprecated since 3.5.9)
- JDK logging
---
#### EX
#### Mapper Files(StudentMapper.xml) ( before creates DTO class) 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- XML-based Mapper File: Used to register SQL commands using elements -->
<!-- => The mapper must be registered in the MyBatis configuration file using the mapper element for the SqlSession object to use the SQL commands in the mapper file -->
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- mapper: The top-level element of the XML-based mapper file -->
<!-- namespace attribute: Sets the identifier to distinguish XML-based mapper files -->
<!-- => Used to distinguish mapper files that the SqlSession object will use -->
<!-- => It is recommended to set the namespace attribute as a Java data type -->
<!-- => In the iBatis framework, the namespace attribute can be omitted -->
<mapper namespace="xyz.itwill.mapper.StudentMapper">
    <!-- select: Element to register a SELECT command -->
    <!-- id attribute: Sets the identifier to distinguish the SQL command registration element -->
    <!-- resultType attribute: Specifies the data type (class) to convert the SQL command (SELECT) result into a Java object -->
    <!-- => Maps a single row returned by the SELECT command to a single Java object -->
    <select id="selectStudentList" resultType="Student">
        select no, name, phone, address, birthday from student order by no
    </select>
</mapper>
```


#### DTO( Student.java)


```java
package xyz.itwill.dto;
/*
Name       Null?     Type             
--------  --------  ------------- 
NO        NOT NULL NUMBER(4)     
NAME              VARCHAR2(50)  
PHONE             VARCHAR2(20)  
ADDRESS           VARCHAR2(100) 
BIRTHDAY          DATE   
*/

// It is recommended to declare fields with the same names as the table columns
// => The SqlSession object automatically stores column values into fields with the same names
// and creates the DTO object.
public class Student {
	private int no;
	private String name;
	private String phone;
	private String address;
	private String birthday;

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
```

### DAO(StudentDAO)



```java
package xyz.itwill.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import xyz.itwill.dto.Student;

public class StudentDAO {
    private static StudentDAO _dao;
    
    private StudentDAO() {
        // TODO Auto-generated constructor stub
    }
    
    static {
        _dao = new StudentDAO();
    }
    
    public static StudentDAO getDAO() {
        return _dao;
    }
    
    // Method to create and return a SqlSessionFactory object
    // => SqlSessionFactory object: Provides SqlSession objects
    // => SqlSessionFactory object requires a MyBatis configuration file (mybatis-config.xml) for creation
    private SqlSessionFactory getSqlSessionFactory() {
        // If the MyBatis configuration file is written in the package, use the file system path
        // String resource = "xyz/itwii/config/mybatis-config.xml";
        String resource = "mybatis-config.xml";
        
        InputStream in = null;
        try {
            // Resources.getResourceAsStream(String resource): Static method that creates and returns an input stream for the specified MyBatis configuration file
            // => Throws IOException if the MyBatis configuration file is not found
            in = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        
        // SqlSessionFactoryBuilder object: Provides functionality to create SqlSessionFactory objects
        // SqlSessionFactoryBuilder.build(InputStream in): Creates and returns a SqlSessionFactory object using the provided input stream for the MyBatis configuration file
        return new SqlSessionFactoryBuilder().build(in);
    }
    
    // Method to search and return all rows stored in the STUDENT table as a List object
    // => Calls methods using SqlSession object to obtain and execute SQL commands registered in the mapper, and returns the execution results as Java objects
    public List<Student> selectStudentList() {
        // SqlSessionFactory.openSession(): Method that returns a SqlSession object
        // => SqlSession object: Executes SQL commands provided by the mapper and returns the execution results as Java objects
        SqlSession sqlSession = getSqlSessionFactory().openSession();
        try {
            // SqlSession.selectList(String elementId): Method that obtains the registered SELECT command from the mapper, executes it on the DBMS server, and returns the execution results as a List object
            // => elementId parameter: A string using the mapper identifier (namespace attribute value of the mapper element) and select element identifier (id attribute value) is provided to obtain and execute SQL commands
            return sqlSession.selectList("xyz.itwill.mapper.StudentMapper.selectStudentList");
        } finally {
            // SqlSession.close(): Method that deletes the SqlSession object
            sqlSession.close();
        }
    }
}
```

#### Student.jsp


```jsp
<%@page import="xyz.itwill.dao.StudentDAO"%>
<%@page import="xyz.itwill.dto.Student"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<Student> studentList = StudentDAO.getDAO().selectStudentList();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYBATIS</title>
<style type="text/css">
table {
    border: 1px solid black;
    border-collapse: collapse;
}

td {
    border: 1px solid black;
    text-align: center;
    padding: 10px;
}
</style>
</head>
<body>
    <h1>Student List</h1>
    <hr>
    <table>
        <tr>
            <td>Student ID</td>
            <td>Name</td>
            <td>Phone</td>
            <td>Address</td>
            <td>Date of Birth</td>
        </tr>
        
        <%
        for (Student student : studentList) {
        %>
        <tr>
            <td><%= student.getNo() %></td>
            <td><%= student.getName() %></td>
            <td><%= student.getPhone() %></td>
            <td><%= student.getAddress() %></td>
            <td><%= student.getBirthday().substring(0, 10) %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
```

