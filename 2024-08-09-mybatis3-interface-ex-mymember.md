---
layout: single
title: 2024/08/08 MyBatis-Interface-Mapper -EX-MyMember
---
#### Package Elements
**Important** - By using package elements, you can automatically provide aliases for all Java data types. With this approach, you can avoid using type aliases and mapper elements and still be able to use Java data types directly for execution.
Here is the translation of the XML document into English:

```xml
<typeAliases>
    <!-- typeAlias: An element used to define an alias for a Java data type (DTO class) in XML-based mapper files -->
    <!-- type attribute: Specifies the Java data type -->
    <!-- alias attribute: Specifies the alias to be used instead of the Java data type -->
    <!-- => An alias for the Java data type is provided internally and can be used -->
    <!-- 
    <typeAlias type="xyz.itwill.dto.Student" alias="Student"/>
    <typeAlias type="xyz.itwill.dto.MyMember" alias="MyMember"/>
    -->
     
    <!-- package: An element that automatically provides aliases for all Java data types in a package -->
    <!-- => The names of Java data types (DTO classes) in the package are used as aliases -->
    <!-- name attribute: Specifies the package containing the Java data types -->
    <package name="xyz.itwill.dto"/>
</typeAliases>

<!-- environments: An element used to register one or more environment elements -->
<!-- default attribute: Specifies the identifier (id attribute value) of the environment element -->
<environments default="development">
    <!-- environment: An element used to set the information required to access the DBMS server -->
    <!-- => Provides information for creating Connection objects -->
    <!-- id attribute: Specifies the identifier for the environment element -->
    <environment id="development">
        <!-- transactionManager: An element used to configure transaction management (commit or rollback) -->
        <!-- type attribute: Specifies one of [JDBC] or [MANAGED] -->
        <!-- => JDBC attribute value: Uses JDBC functionality for transaction management -->
        <!-- => MANAGED attribute value: Uses a transaction management program for transaction management -->
        <transactionManager type="JDBC"/>
        <!-- dataSource: An element that provides the information required to create Connection objects -->
        <!-- type attribute: Specifies one of [UNPOOLED], [POOLED], or [JNDI] -->
        <!-- => The sub-elements are configured differently based on the type attribute value -->
        <!-- => UNPOOLED attribute value: Creates and provides Connection objects at runtime -->
        <!-- => POOLED attribute value: Creates and provides Connection objects in advance - DBCP -->
        <!-- => JNDI attribute value: Uses resources (objects) registered in the WAS program to provide Connection objects at runtime -->
        <dataSource type="POOLED">
            <!-- property: An element used to provide values required for creating Connection objects -->
            <!-- name attribute: Specifies a name to distinguish the value -->
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

<!-- mappers: An element used to register one or more mapper elements -->
<!-- => The package element can be used instead of the mapper element -->
<mappers>
    <!-- mapper: An element used to register a mapper file -->
    <!-- Mapper: Provides the information to register SQL commands and map the results to Java objects -->
    <!-- => The MyBatis framework supports registering mappers using XML-based mapper files or Interface-based mapper files -->
    <!-- => The iBATIS framework supports registering mappers only using XML-based mapper files -->
    <!-- resource attribute: Specifies the file system path for XML-based mapper files -->
    <!-- => Advantage: Easier to implement manual mapping and dynamic SQL features -->
    <!-- => Disadvantage: Creating DAO classes using SqlSession objects is cumbersome -->
    <mapper resource="xyz/itwill/mapper/StudentMapper.xml"/>
    <mapper resource="xyz/itwill/mapper/MyMemberXMLMapper.xml"/>
    
    <!-- class attribute: Specifies the Java data type (interface) for Interface-based mapper files -->
    <!-- => Advantage: Easier to create DAO classes using SqlSession objects -->
    <!-- => Disadvantage: Implementing manual mapping and dynamic SQL features is cumbersome -->
    <!-- <mapper class="xyz.itwill.mapper.MyMemberInterfaceMapper"/> -->
    
    <!-- Mapper Binding: Allows registering XML-based mapper files and Interface-based mapper files as a single mapper -->
    <!-- => SQL commands are registered in XML-based mapper files, while DAO classes use Interface-based mapper files -->
    <!-- => The mapper element can register either XML-based or Interface-based mapper files, but not both -->
    <!-- <mapper resource="xyz/itwill/mapper/MyMemberMapper.xml"/> -->
    
    <!-- mapper: An element that automatically registers all Interface-based mapper files in a package -->
    <!-- => XML-based mapper files cannot be registered using the package element -->
    <!-- name attribute: Specifies the package containing Interface-based mapper files -->
    <package name="xyz.itwill.mapper"/>
</mappers>
```


#### Mapper - Interface Mapper 
go to Xml file (mybatis-config.xml)
mapper class="xyz.itwill.mapper.MyMemberInterfaceMapper" -> MyMemberInterfaceMapper.java->MyMemberInterfaceDAO.java


MyMemberInterfaceMapper.java-
---

```java
package xyz.itwill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import xyz.itwill.dto.MyMember;

// The MyBatis framework allows the creation of mapper files using interfaces
// => SQL commands can be defined using mapper annotations on abstract methods
public interface MyMemberInterfaceMapper {
    //@Insert: Annotation to register an INSERT command on an abstract method
    // value attribute: Sets the SQL command for the abstract method
    // => If there are no other attributes, only the value attribute is used
    @Insert(value = "insert into mymember values(#{id}, #{name}, #{phone}, #{email})")
    // The parameter of the abstract method uses Java data types to receive the necessary objects (values) for the SQL command
    // The return type uses Java data types to provide the result of the SQL command
    int insertMyMember(MyMember member);
    
    //@Update: Annotation to register an UPDATE command on an abstract method
    @Update("update mymember set name=#{name}, phone=#{phone}, email=#{email} where id=#{id}")
    int updateMyMember(MyMember member);
    
    //@Delete: Annotation to register a DELETE command on an abstract method
    @Delete("delete from mymember where id=#{id}")
    int deleteMyMember(String id);
    
    //@Select: Annotation to register a SELECT command on an abstract method
    @Select("select id, name, phone, email from mymember where id=#{id}")
    MyMember selectMyMember(String id);
    
    @Select("select id, name, phone, email from mymember order by id")
    List<MyMember> selectMyMemberList();
}
```

MyMemberInterfaceDAO.java
```java
package xyz.itwill.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import xyz.itwill.dto.MyMember;
import xyz.itwill.mapper.MyMemberInterfaceMapper;

public class MyMemberInterfaceDAO {
	private static MyMemberInterfaceDAO _dao;
	
	private MyMemberInterfaceDAO() {
		// TODO Auto-generated constructor stub
	}
	
	static {
		_dao = new MyMemberInterfaceDAO();
	}
	
	public static MyMemberInterfaceDAO getDAO() {
		return _dao;
	}
	
	private SqlSessionFactory getSqlSessionFactory() {
		String resource = "mybatis-config.xml";
		
		InputStream in = null;
		try {
			in = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		
		return new SqlSessionFactoryBuilder().build(in);
	}

	// Method to insert member information into the MYMEMBER table and return the number of inserted rows
	public int insertMyMember(MyMember member) {
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			// SqlSession.getMapper(Class<T> class): Method to get a Mapper object from the SqlSession
			// => Provide the Class object of the interface mapper to get a Mapper instance
			// Mapper object: Allows calling methods where SQL commands are registered
			// => Calling an abstract method on the Mapper object executes the SQL command and returns the result as a Java object
			return sqlSession.getMapper(MyMemberInterfaceMapper.class).insertMyMember(member);
		} finally {
			sqlSession.close();
		}
	}
	
	// Method to update member information in the MYMEMBER table and return the number of updated rows
	public int updateMyMember(MyMember member) {
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			return sqlSession.getMapper(MyMemberInterfaceMapper.class).updateMyMember(member);
		} finally {
			sqlSession.close();
		}
	}

	// Method to delete a member from the MYMEMBER table by ID and return the number of deleted rows
	public int deleteMyMember(String id) {
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			return sqlSession.getMapper(MyMemberInterfaceMapper.class).deleteMyMember(id);
		} finally {
			sqlSession.close();
		}
	}
	
	// Method to retrieve a member's information from the MYMEMBER table by ID
	public MyMember selectMyMember(String id) {
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			return sqlSession.getMapper(MyMemberInterfaceMapper.class).selectMyMember(id);
		} finally {
			sqlSession.close();
		}
	}
	
	// Method to retrieve all member information from the MYMEMBER table and return a list of members
	public List<MyMember> selectMyMemberList() {
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			return sqlSession.getMapper(MyMemberInterfaceMapper.class).selectMyMemberList();
		} finally {
			sqlSession.close();
		}
	}
}
```

---

#### mapper xml and interface together ->
mapper resource="xyz/itwill/mapper/MyMemberMapper.xml"/ -> MyMemberMapper.xml->
MyMemberMapper.java->MyMemberDAO.java

MyMemberMapper.java
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill.mapper.MyMemberXMLMapper">
    <!-- insert: An element used to register an INSERT command -->
    <!-- id attribute: Specifies the identifier for distinguishing the element -->
    <!-- parameterType attribute: Specifies the Java data type for providing values required for the SQL command -->
    <!-- => You can use an alias defined by the typeAlias element instead of a Java data type -->
    <!-- => If no value is required for the SQL command, the parameterType attribute can be omitted -->
    <!-- For DML commands (INSERT, UPDATE, DELETE), the registered SQL command is always mapped to an integer (int) representing the number of affected rows, so the resultType attribute is omitted -->
    <insert id="insertMyMember" parameterType="MyMember">
        <!-- Values passed with the parameterType attribute are provided to the SQL command using #{variableName|fieldName|mapKey} format -->
        <!-- => When a Java object (DTO object) is passed with the parameterType attribute, field values are provided using #{fieldName} format, which calls the getter method for the field -->
        <!-- => In the iBATIS framework, values are provided using #variableName|fieldName|mapKey# format -->
        insert into mymember values(#{id}, #{name}, #{phone}, #{email})
    </insert>
    
    <!-- update: An element used to register an UPDATE command -->
    <update id="updateMyMember" parameterType="MyMember">
        update mymember set name=#{name}, phone=#{phone}, email=#{email} where id=#{id}
    </update>
    
    <!-- delete: An element used to register a DELETE command -->
    <!-- => The parameterType attribute is set to the String class to receive a single string and provide the value to the SQL command using #{variableName} format - the variable name can be any name -->
    <delete id="deleteMyMember" parameterType="string">
        delete from mymember where id=#{id}
    </delete>    
    
    <!-- select: An element used to register a SELECT command -->
    <!-- => To provide the result of a SELECT command as a Java object, the resultType attribute (or resultMap attribute) must be specified -->
    <!-- When resultType is set to a DTO class, an object is created with the class, and column values from the result set are stored in fields with names matching the column names - automatic mapping -->
    <!-- => Ensure that field names in the DTO class match the column names in the result set -->
    <select id="selectMyMember" parameterType="string" resultType="MyMember">
        select id, name, phone, email from mymember where id=#{id}
    </select>
    
    <select id="selectMyMemberList" resultType="MyMember">
        select id, name, phone, email from mymember order by id
    </select>
</mapper>
```
MyMemberDAO.java
Here's a description of the `MyMemberMapper` interface in English:

```java
package xyz.itwill.mapper;

import java.util.List;
import xyz.itwill.dto.MyMember;

public interface MyMemberMapper {
    // Method to insert a new member into the database
    // Accepts a MyMember object and returns the number of rows affected
    int insertMyMember(MyMember member);
    
    // Method to update an existing member's information in the database
    // Accepts a MyMember object and returns the number of rows affected
    int updateMyMember(MyMember member);
    
    // Method to delete a member from the database by their ID
    // Accepts a String representing the member's ID and returns the number of rows affected
    int deleteMyMember(String id);
    
    // Method to retrieve a member's information from the database by their ID
    // Accepts a String representing the member's ID and returns a MyMember object
    MyMember selectMyMember(String id);
    
    // Method to retrieve a list of all members from the database
    // Returns a List of MyMember objects
    List<MyMember> selectMyMemberList();
}
```

### Explanation:
- **`insertMyMember(MyMember member)`**: Inserts a `MyMember` object into the database and returns the number of rows affected.
- **`updateMyMember(MyMember member)`**: Updates the details of an existing `MyMember` object in the database and returns the number of rows affected.
- **`deleteMyMember(String id)`**: Deletes a member from the database based on their ID and returns the number of rows affected.
- **`selectMyMember(String id)`**: Retrieves a `MyMember` object from the database based on the member's ID.
- **`selectMyMemberList()`**: Retrieves a list of all `MyMember` objects from the database.



