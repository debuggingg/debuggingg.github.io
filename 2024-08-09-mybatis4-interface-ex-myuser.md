---
layout: single
title: 2024/08/08 MyBatis4-Interface-Mapper -EX-user
---
#### Flow
1. **Create the SQL Table**:
   - Define the SQL table structure in your database.

2. **Create the DTO (Data Transfer Object)**:
   - Define a Java class with fields that correspond to the columns of the SQL table. This class is used to transfer data between the database and the application.

3. **Define SQL Commands in an XML Mapper**:
   - Create an XML file (mapper XML) to include SQL statements for CRUD operations (INSERT, UPDATE, DELETE, SELECT) that will be executed against the SQL table.

4. **Create a Mapper Interface**:
   - Define a Java interface with methods that correspond to the SQL commands in the XML mapper. This interface is used to interact with the SQL database using MyBatis.

5. **Create the DAO (Data Access Object)**:
   - Implement a DAO class that uses MyBatis to perform database operations. This class will:
     - Use `SqlSessionFactory` to obtain a `SqlSession` object.
     - Call the appropriate methods on the mapper interface to execute SQL commands.

6. **Create an Abstract Session Class**:
   - Optionally, create an abstract class (e.g., `AbstractSession.java`) that encapsulates common functionality for creating `SqlSession` objects and interacting with the `SqlSessionFactory`. This class can be extended by specific DAO classes.

7. **Develop JSP Pages**:
   - Create JSP (JavaServer Pages) files to display SQL data on the web. Use the DAO methods to fetch data and render it in the web interface.

### Summary Flow:
1. **SQL Table Creation** → 2. **Create DTO** → 3. **Define SQL Commands in XML Mapper** → 4. **Create Mapper Interface** → 5. **Implement DAO** → 6. **Create Abstract Session Class (Optional)** → 7. **Develop JSP Pages for Web Display**
 
 
### DTO MyUser


```java
package xyz.itwill.dto;

/*
MYUSER TABLE: A table to store user information
=> SQL commands are case-insensitive, so use snake_case for identifiers
=> Snake Case: A method of writing identifiers using underscores to separate words
create table myuser(user_id varchar2(50) primary key, user_name varchar2(50));

Name        Nullable       Type           
---------  --------  ------------ 
USER_ID   NOT NULL VARCHAR2(50) - ID 
USER_NAME          VARCHAR2(50) - Name
*/

// Java is case-sensitive, so PascalCase is used for Java identifiers (Class, Interface, Enum, etc.)
// => Pascal Case: A method of writing identifiers where the first letter of each word is capitalized
public class MyUser {
	// Java uses camelCase for identifiers other than Java types and constants
	// => Camel Case: A method of writing identifiers where the first letter of the first word is lowercase, and the first letter of each subsequent word is uppercase
	private String userId;
	private String userName;
	
	public MyUser() {
		// TODO Auto-generated constructor stub
	}

	public MyUser(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
```

### Translation Summary:
- **Table Description**: The `MYUSER` table stores user information. SQL commands are case-insensitive, so identifiers are written in snake_case.
- **Java Conventions**:
  - **PascalCase**: Used for class names, where each word starts with a capital letter.
  - **CamelCase**: Used for field names and methods, where the first letter of the first word is lowercase, and the first letter of each subsequent word is uppercase.

#### MyUserMapper

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill.mapper.MyUserMapper">
	<insert id="insertUser" parameterType="MyUser">
		insert into myuser values(#{userId}, #{userName})
	</insert>
	
	<!-- Using the resultType attribute allows the creation of objects of the class specified by resultType,
	automatically mapping column values from the result set to fields with the same names in the class - automatic mapping -->
	<!-- Issue: If the column names in the result set differ from the field names in the class specified by resultType,
	the object is not created and NULL is returned -->
	<!-- => This will cause a NullPointerException when the web program runs -->
	<!-- 
	<select id="selectUserList" resultType="MyUser">
		select user_id, user_name from myuser order by user_id
	</select>
	 -->
	 
	<!-- Solution-1: Make the column names in the SELECT statement match the field names in the class specified by resultType -->
	<!-- => Use column aliases in the SELECT statement to match the field names -->
	<!--   
	<select id="selectUserList" resultType="MyUser">
		select user_id userId, user_name userName from myuser order by user_id
	</select>
	-->
	
	<!-- sql: Element for registering part of an SQL statement -->
	<!-- 
	<sql id="myUserColumnAlias">
		user_id userId, user_name userName
	</sql> 
	-->
	 
	<!-- include: Element for including SQL statements defined in sql elements into an SQL command -->
	<!-- refid attribute: Sets the identifier (id attribute value) of the sql element to be included -->
	<!--
	<select id="selectUserList" resultType="MyUser">
		select <include refid="myUserColumnAlias"/> from myuser order by user_id
	</select>
	-->
	
	<!-- Solution-2: Use the setting element in the MyBatis configuration file (mybatis-config.xml) to activate
	the option (mapUnderscoreToCamelCase) to automatically convert snake_case column names to camelCase -->
	<!-- 
	<select id="selectUserList" resultType="MyUser">
		select user_id, user_name from myuser order by user_id
	</select>
	-->
	
	<!-- resultMap: Element that provides information for creating a Java object from the result set - mapping information -->
	<!-- => Configures how column values from the result set are stored in object fields using sub-elements -->
	<!-- => Sub-elements: constructor, id, result, association, collection, discriminator -->
	<!-- type attribute: Sets the Java type for the object created by the resultMap element -->
	<!-- => You can also use aliases defined in typeAlias elements instead of Java types -->
	<resultMap type="MyUser" id="myUserResultMap">
		<!-- id: Element for storing primary key column values in the object field - calls setter methods -->
		<!-- => Element for storing column values in fields with primary key constraints -->
		<!-- column attribute: Sets the column name from the result set -->
		<!-- property attribute: Sets the field name in the object -->
		<id column="user_id" property="userId"/>
		<!-- result: Element for storing column values in the object field - calls setter methods -->
		<result column="user_name" property="userName"/>
	</resultMap>
	
	<!-- Solution-3: Use the resultMap element to manually map column values to object fields -->
	<!-- resultMap attribute: Sets the identifier (id attribute value) of the resultMap element -->
	<select id="selectUserList" resultMap="myUserResultMap">
		select user_id, user_name from myuser order by user_id
	</select>
</mapper>
```

### Translation Summary:
- **Insert Statement**: Insert a new record into the `myuser` table using the `MyUser` object.
- **Result Mapping**:
  - **Automatic Mapping**: If column names in the result set do not match field names in the `resultType` class, a `NullPointerException` may occur.
  - **Solution-1**: Match column names with field names using column aliases in the `SELECT` statement or SQL fragments.
  - **Solution-2**: Activate `mapUnderscoreToCamelCase` option in MyBatis configuration to automatically convert column names from snake_case to camelCase.
  - **Solution-3**: Use `resultMap` for manual mapping of result set columns to object fields to avoid discrepancies between column names and field names.

#### Interface MyUserMapper

```java
package xyz.itwill.mapper;

import java.util.List;

import xyz.itwill.dto.MyUser;

public interface MyUserMapper {
    // Method to insert a user into the database
    int insertUser(MyUser user);
    
    // Method to select a list of users from the database
    List<MyUser> selectUserList();
}
```

#### DAO MyUserDAO


```java
package xyz.itwill.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import xyz.itwill.dto.MyUser;
import xyz.itwill.mapper.MyUserMapper;

public class MyUserDAO extends AbstractSession {
    private static MyUserDAO _dao;

    // Private constructor for singleton pattern
    private MyUserDAO() {
        // TODO Auto-generated constructor stub
    }

    // Static block to initialize the singleton instance
    static {
        _dao = new MyUserDAO(); 
    }

    // Public method to get the singleton instance of MyUserDAO
    public static MyUserDAO getDAO() {
        return _dao;
    }

    // Method to insert a user into the database
    public int insertUser(MyUser user) {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            return sqlSession.getMapper(MyUserMapper.class).insertUser(user);
        } finally {
            sqlSession.close();
        }
    }

    // Method to select a list of users from the database
    public List<MyUser> selectUserList() {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            return sqlSession.getMapper(MyUserMapper.class).selectUserList();
        } finally {
            sqlSession.close();
        }
    }
}
```



#### AbstractSession.java

Here's the translated Java class into English:

```java
package xyz.itwill.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

// Class responsible for creating and returning the SqlSessionFactory object
// => Parent class for all DAO classes that use mappers
// => It is recommended to declare this class as abstract since it is intended for inheritance
public abstract class AbstractSession {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "mybatis-config.xml";

        InputStream in = null;
        try {
            in = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }

    protected SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
```

### Explanation:

- **`AbstractSession` Class**:
  - Responsible for creating and managing the `SqlSessionFactory` object.
  - Intended to be a parent class for DAO classes that use MyBatis mappers.
  - Declared as `abstract` to indicate that it is not meant to be instantiated directly but rather extended by other classes.

- **Static Block**:
  - Loads the MyBatis configuration from the `mybatis-config.xml` file.
  - Creates a `SqlSessionFactory` using `SqlSessionFactoryBuilder` and assigns it to the static `sqlSessionFactory` variable.
  - Handles potential `IOException` during the configuration file loading.

- **`getSqlSessionFactory()` Method**:
  - Provides access to the `SqlSessionFactory` instance for subclasses.
