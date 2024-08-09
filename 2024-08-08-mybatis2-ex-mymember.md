---
layout: single
title: 2024/08/08 MyBatis-EX-Mymember
---
## EX for MyMember(Ommit Jsp)

#### XML (mybatis-config.xml)


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

#### MyMemberXMLMapper.jsp

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill.mapper.MyMemberXMLMapper">
    <!-- insert: Element for registering an INSERT command -->
    <!-- id attribute: Identifies the element -->
    <!-- parameterType attribute: Specifies the Java type needed to provide values for the SQL command -->
    <!-- => Instead of Java types, you can use typeAlias aliases specified in the typeAlias element -->
    <!-- => If no value is needed for the SQL command, the parameterType attribute can be omitted -->
    <!-- DML commands (INSERT, UPDATE, DELETE) always map the number of affected rows to an integer, so resultType is omitted -->
    <insert id="insertMyMember" parameterType="MyMember">
        <!-- The value provided via parameterType is referenced in the SQL command using #{variableName|fieldName|mapKey} -->
        <!-- => When a Java object (DTO object) is passed as parameterType, field values are provided using #{fieldName} -->
        <!-- => In iBatis, values are provided using #variableName|fieldName|mapKey# -->
        insert into mymember values(#{id}, #{name}, #{phone}, #{email})
    </insert>
    
    <!-- update: Element for registering an UPDATE command -->
    <update id="updateMyMember" parameterType="MyMember">
        update mymember set name=#{name}, phone=#{phone}, email=#{email} where id=#{id}
    </update>
    
    <!-- delete: Element for registering a DELETE command -->
    <!-- => When parameterType is set to String, a single string is passed, and values are referenced in the SQL command using #{variableName} -->
    <delete id="deleteMyMember" parameterType="string">
        delete from mymember where id=#{id}
    </delete>
    
    <!-- select: Element for registering a SELECT command -->
    <!-- => The result of the SELECT query must be specified using resultType (or resultMap) -->
    <!-- resultType specifies the DTO class, which maps the column values to the fields of the class with the same name -->
    <!-- => Field names in the DTO class should match the column names in the result set -->
    <select id="selectMyMember" parameterType="string" resultType="MyMember">
        select id, name, phone, email from mymember where id=#{id}
    </select>
    
    <select id="selectMyMemberList" resultType="MyMember">
        select id, name, phone, email from mymember order by id
    </select>
</mapper>
```

#### DTO (MyMember.java)


```java
package xyz.itwill.dto;

// Create table mymember(id varchar2(50) primary key, name varchar2(50),
//     phone varchar2(20), email varchar2(100));

public class MyMember {
    private String id;
    private String name;
    private String phone;
    private String email;
    
    public MyMember() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

#### DAO(MyMemberXMLDAO.java)
Here is the English translation of the `MyMemberXMLDAO` class:

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

public class MyMemberXMLDAO {
    private static MyMemberXMLDAO _dao;
    
    private MyMemberXMLDAO() {
        // TODO Auto-generated constructor stub
    }
    
    static {
        _dao = new MyMemberXMLDAO();
    }
    
    public static MyMemberXMLDAO getDAO() {
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
        SqlSession sqlSession = getSqlSessionFactory().openSession();
        try {
            // SqlSession.insert(String elementId[, Object parameterValue]) : Provides the SQL command (INSERT) registered in the mapper
            // and executes it on the DBMS server, returning the number of inserted rows
            // => elementId : String based on the mapper identifier and element identifier
            // => parameterValue : Value (object) needed for SQL command creation
            int rows = sqlSession.insert("xyz.itwill.mapper.MyMemberXMLMapper.insertMyMember", member);
            
            // SqlSession object has AutoCommit functionality disabled, so commit or rollback is required after executing DML commands
            if (rows > 0) {
                // SqlSession.commit() : Commits the transaction
                sqlSession.commit();
            } else {
                // SqlSession.rollback() : Rolls back the transaction
                sqlSession.rollback();
            }
            
            return rows;
        } finally {
            sqlSession.close();
        }
    }

    // Method to update member information in the MYMEMBER table and return the number of updated rows
    public int updateMyMember(MyMember member) {
        // SqlSessionFactory.openSession(boolean autoCommit) : Returns a SqlSession object
        // => Passing [true] activates AutoCommit functionality
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            // SqlSession.update(String elementId[, Object parameterValue]) : Provides the SQL command (UPDATE) registered in the mapper
            // and executes it on the DBMS server, returning the number of updated rows
            return sqlSession.update("xyz.itwill.mapper.MyMemberXMLMapper.updateMyMember", member);
        } finally {
            sqlSession.close();
        }
    }

    // Method to delete a row from the MYMEMBER table by ID and return the number of deleted rows
    public int deleteMyMember(String id) {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            // SqlSession.delete(String elementId[, Object parameterValue]) : Provides the SQL command (DELETE) registered in the mapper
            // and executes it on the DBMS server, returning the number of deleted rows
            return sqlSession.delete("xyz.itwill.mapper.MyMemberXMLMapper.deleteMyMember", id);
        } finally {
            sqlSession.close();
        }
    }
    
    // Method to retrieve a member by ID from the MYMEMBER table and return the member information
    public MyMember selectMyMember(String id) {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            // SqlSession.selectOne(String elementId[, Object parameterValue]) : Provides the SQL command (SELECT) registered in the mapper
            // and executes it on the DBMS server, returning the Java object containing the search result (one row)
            return sqlSession.selectOne("xyz.itwill.mapper.MyMemberXMLMapper.selectMyMember", id);
        } finally {
            sqlSession.close();
        }
    }
    
    // Method to retrieve all rows from the MYMEMBER table and return the list of members
    public List<MyMember> selectMyMemberList() {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            // SqlSession.selectList(String elementId[, Object parameterValue]) : Provides the SQL command (SELECT) registered in the mapper
            // and executes it on the DBMS server, returning the List object containing the search results (multiple rows)
            return sqlSession.selectList("xyz.itwill.mapper.MyMemberXMLMapper.selectMyMemberList");
        } finally {
            sqlSession.close();
        }
    }
}
```


SqlSessionFactory - mybatis.xml  둘이 서로 필요하고
SqlSesstion - mapper files  둘이 서로 필요하고
DAO 안에 sql session 을 만들어야하는데 만들기 위해서는 sessionfactory객채를 만들어야 하고
그러기 위해서 mybatis.xml 환경설정 파일잉 있어야 한다. 이것을 읽어야만 sessionfactory 객채를 만들수있다. mybatis 안에는 connection 객채를 만들수있는정보들, environment, mapper 등등을 만드는것이다.  mapper 를 사용할수있는 mapper files 을 만들어서 그안에 sql 명령어를 만들면 sqlsession 이 mapper files 안에 있는 sql 명령을 가져다가 쓸수있다. 
log4j.xml 이라고 log 구현 채는 구현채가 에러 나 상태를 모두 보여주게 하는 기능을 한다. 