---
layout: single
title: 2024/08/08 MyBatis-EX-MyComment
---
#### DTO
Here is the translated Java class into English:

```java
package xyz.itwill.dto;

/*
MYCOMMENT TABLE: Table for storing comments
create table mycomment(
    comment_no number primary key,
    comment_id varchar2(50),
    comment_content varchar2(100),
    comment_date date
);

//MYCOMMENT_SEQ SEQUENCE: Sequence to provide auto-increment values for the COMMENT_NO column in the MYCOMMENT table
create sequence mycomment_seq;

Column Name       Nullable?   Type
----------------- ----------- -------------
COMMENT_NO        NOT NULL    NUMBER      - Comment Number
COMMENT_ID                   VARCHAR2(50) - Comment Author (User ID)
COMMENT_CONTENT              VARCHAR2(100) - Comment Content
COMMENT_DATE                 DATE         - Comment Date
*/

// Define class fields with names matching the table columns - automatic mapping
// => Column names written in snake case are automatically converted to camel case, so field names should be written in camel case
public class MyComment1 {
    private int commentNo;
    private String commentId;
    private String commentContent;
    private String commentDate;

    public MyComment1() {
        // TODO Auto-generated constructor stub
    }

    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
```

#### XML 
Here is the translated MyBatis XML mapper file in English:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill.mapper.MyCommentMapper">
    <insert id="insertComment1" parameterType="MyComment1">
        insert into mycomment values(mycomment_seq.nextval, #{commentId}, #{commentContent}, sysdate)
    </insert>
    
    <insert id="insertComment2" parameterType="MyComment1">
        <!-- selectKey: An element to get a value from a SELECT command and store it in the field of the object specified by the parameterType attribute of the insert element -->
        <!-- => A dependent element of the insert element -->
        <!-- => Used to get an auto-increment value or random value from a SELECT command and store it in the object field to use as a column value in the INSERT command -->
        <!-- resultType attribute: Specifies the Java type for the result of the SELECT command -->
        <!-- => An alias set by the typeAlias element can also be used -->
        <!-- keyProperty attribute: Specifies the field name of the class set by the parameterType attribute of the insert element -->
        <!-- order attribute: Set to either [BEFORE] or [AFTER] -->
        <selectKey resultType="int" keyProperty="commentNo" order="BEFORE">
            select mycomment_seq.nextval from dual
        </selectKey>
        insert into mycomment values(#{commentNo}, #{commentId}, #{commentContent}, sysdate)
    </insert>
    
    <select id="selectCommentList1" resultType="MyComment1">
        select comment_no, comment_id, comment_content, comment_date from mycomment 
            order by comment_no desc
    </select>
    
    <!-- If the column names in the search result and the field names of the class set by the resultType attribute are different, NULL is returned instead of creating an object of the class set by resultType -->
    <!--  
    <select id="selectCommentList2" resultType="MyComment2">
        select comment_no, comment_id, comment_content, comment_date from mycomment 
            order by comment_no desc
    </select>
    -->
    
    <!-- Solution: Use the ColumnAlias feature to match column names with field names of the class set by resultType -->
    <!-- => Use double quotes for names that are inappropriate for identifiers -->
    <!-- 
    <select id="selectCommentList2" resultType="MyComment2">
        select comment_no no, comment_id id, comment_content content, comment_date "date" 
            from mycomment order by comment_no desc
    </select>
    -->
     
    <!-- Use resultMap to create a Java object from search rows and set the values of object fields using sub-elements -->
    <!-- => The type attribute specifies the Java type for the object created by the resultMap element -->
    <!-- => Use the default constructor to create the object and use id or result elements to set the values of the fields -->
    <resultMap type="MyComment2" id="myComment2ResultMap">
        <id column="comment_no" property="no"/>
        <result column="comment_id" property="id"/>
        <result column="comment_content" property="content"/>
        <result column="comment_date" property="date"/>
    </resultMap> 
    
    <!-- Use the resultMap attribute of the select element to manually map the result to a Java object -->
    <!-- 
    <select id="selectCommentList2" resultMap="myComment2ResultMap">
        select comment_no, comment_id, comment_content, comment_date from mycomment 
            order by comment_no desc
    </select>
    -->
    
    <!-- Use the constructor of the class set by the type attribute of the resultMap element to create the object -->
    <!-- => Use the constructor to set the values of object fields from the search row columns -->
    <resultMap type="MyComment2" id="myComment2ConstructorResultMap">
        <!-- constructor: Provides information to map using the constructor of the class set by the type attribute of the resultMap element -->
        <!-- => Use idArg or arg elements as sub-elements -->
        <!-- => Ensure the number and type of sub-elements match the number and type of constructor parameters -->
        <constructor>
            <!-- idArg: Element to pass the column value to a constructor parameter -->
            <!-- => Used to provide the primary key value to the constructor parameter -->
            <!-- column attribute: Specifies the column name -->
            <!-- javaType attribute: Specifies the data type of the parameter -->
            <!-- => An alias set by the typeAlias element can also be used -->
            <idArg column="comment_no" javaType="int"/>
            <!-- arg: Element to pass the column value to a constructor parameter -->
            <arg column="comment_id" javaType="string"/>
            <!-- 
            <arg column="comment_content" javaType="string"/>
            <arg column="comment_date" javaType="string"/>
            -->
        </constructor>
        <!-- Constructor element and id/result elements can be used together to set object fields -->
        <!-- => Place id/result elements after the constructor element -->
        <result column="comment_content" property="content"/>
        <result column="comment_date" property="date"/>
    </resultMap>
    
    <select id="selectCommentList2" resultMap="myComment2ConstructorResultMap">
        select comment_no, comment_id, comment_content, comment_date from mycomment 
            order by comment_no desc
    </select>
</mapper>
```

### Explanation:

- **`<insert>` Elements**:
  - `insertComment1`: Inserts a new comment using a sequence to generate `comment_no`.
  - `insertComment2`: Uses a `selectKey` to get a new `comment_no` before inserting, then performs the insert.

- **`<select>` Elements**:
  - `selectCommentList1`: Retrieves a list of comments ordered by `comment_no` in descending order.
  - `selectCommentList2`: Demonstrates various solutions for mapping columns to class fields, including using column aliases and `resultMap`.

- **`<resultMap>` Elements**:
  - `myComment2ResultMap`: Maps query results to fields of `MyComment2` class.
  - `myComment2ConstructorResultMap`: Uses the constructor of `MyComment2` to create objects and set field values from query results.

### interface
Here is the Java interface for the `MyCommentMapper` with explanations:

```java
package xyz.itwill.mapper;

import java.util.List;

import xyz.itwill.dto.MyComment1;
import xyz.itwill.dto.MyComment2;

public interface MyCommentMapper {
    // Method to insert a comment using the first insert method defined in the XML mapper
    int insertComment1(MyComment1 comment);

    // Method to insert a comment using the second insert method with sequence key generation
    int insertComment2(MyComment1 comment);

    // Method to retrieve a list of comments as instances of MyComment1
    List<MyComment1> selectCommentList1();

    // Method to retrieve a list of comments as instances of MyComment2
    List<MyComment2> selectCommentList2();
}
```

### DAO
Here's the translation of your Java DAO class for managing `MyMember` objects using MyBatis. This class provides methods for CRUD operations on the `MYMEMBER` table.

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
import xyz.itwill.mapper.MyMemberMapper;

public class MyMemberDAO {
    private static MyMemberDAO _dao;

    private MyMemberDAO() {
        // Private constructor to prevent instantiation
    }

    static {
        _dao = new MyMemberDAO(); // Initialize the singleton instance
    }

    public static MyMemberDAO getDAO() {
        return _dao; // Provide access to the singleton instance
    }

    private SqlSessionFactory getSqlSessionFactory() {
        String resource = "mybatis-config.xml";
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return new SqlSessionFactoryBuilder().build(in); // Create and return SqlSessionFactory
    }

    // Method to insert a new member into the MYMEMBER table
    public int insertMyMember(MyMember member) {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            // Use the MyMemberMapper to execute the SQL command for insertion
            return sqlSession.getMapper(MyMemberMapper.class).insertMyMember(member);
        } finally {
            sqlSession.close(); // Close the session to release resources
        }
    }

    // Method to update an existing member in the MYMEMBER table
    public int updateMyMember(MyMember member) {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            return sqlSession.getMapper(MyMemberMapper.class).updateMyMember(member);
        } finally {
            sqlSession.close(); // Close the session to release resources
        }
    }

    // Method to delete a member from the MYMEMBER table by ID
    public int deleteMyMember(String id) {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            return sqlSession.getMapper(MyMemberMapper.class).deleteMyMember(id);
        } finally {
            sqlSession.close(); // Close the session to release resources
        }
    }

    // Method to retrieve a member from the MYMEMBER table by ID
    public MyMember selectMyMember(String id) {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            return sqlSession.getMapper(MyMemberMapper.class).selectMyMember(id);
        } finally {
            sqlSession.close(); // Close the session to release resources
        }
    }

    // Method to retrieve a list of all members from the MYMEMBER table
    public List<MyMember> selectMyMemberList() {
        SqlSession sqlSession = getSqlSessionFactory().openSession(true);
        try {
            return sqlSession.getMapper(MyMemberMapper.class).selectMyMemberList();
        } finally {
            sqlSession.close(); // Close the session to release resources
        }
    }
}
```

#### DTO - different field name from SQL column name 

- To handle the case where field names in Java classes differ from column names in SQL, `resultMap` is used to map each column name to the corresponding field name.

```java
package xyz.itwill.dto;

public class MyComment2 {
    private int no;           // Comment number
    private String id;       // Comment author (member's ID)
    private String content;  // Comment content
    private String date;     // Comment date

    // Default constructor
    public MyComment2() {
        // TODO Auto-generated constructor stub
    }

    // Constructor with all fields
    public MyComment2(int no, String id, String content, String date) {
        super();
        this.no = no;
        this.id = id;
        this.content = content;
        this.date = date;
    }

    // Constructor with no and id fields
    public MyComment2(int no, String id) {
        super();
        this.no = no;
        this.id = id;
    }

    // Getter for no
    public int getNo() {
        return no;
    }

    // Setter for no
    public void setNo(int no) {
        this.no = no;
    }

    // Getter for id
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
    }

    // Getter for date
    public String getDate() {
        return date;
    }

    // Setter for date
    public void setDate(String date) {
        this.date = date;
    }
}
```


```xml
<!-- Use the constructor of the class set by the type attribute of the resultMap element to create the object -->
    <!-- => Use the constructor to set the values of object fields from the search row columns -->
    <resultMap type="MyComment2" id="myComment2ConstructorResultMap">
        <!-- constructor: Provides information to map using the constructor of the class set by the type attribute of the resultMap element -->
        <!-- => Use idArg or arg elements as sub-elements -->
        <!-- => Ensure the number and type of sub-elements match the number and type of constructor parameters -->
        <constructor>
            <!-- idArg: Element to pass the column value to a constructor parameter -->
            <!-- => Used to provide the primary key value to the constructor parameter -->
            <!-- column attribute: Specifies the column name -->
            <!-- javaType attribute: Specifies the data type of the parameter -->
            <!-- => An alias set by the typeAlias element can also be used -->
            <idArg column="comment_no" javaType="int"/>
            <!-- arg: Element to pass the column value to a constructor parameter -->
            <arg column="comment_id" javaType="string"/>
            <!-- 
            <arg column="comment_content" javaType="string"/>
            <arg column="comment_date" javaType="string"/>
            -->
        </constructor>
        <!-- Constructor element and id/result elements can be used together to set object fields -->
        <!-- => Place id/result elements after the constructor element -->
        <result column="comment_content" property="content"/>
        <result column="comment_date" property="date"/>
    </resultMap>
```


