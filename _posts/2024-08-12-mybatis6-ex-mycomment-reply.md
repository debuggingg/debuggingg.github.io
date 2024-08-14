---
layout: single
title: 2024/08/12 MyBatis6-EX-MyComment-Reply
---

Will create a program where replies can be added. Among the components, I will exclude the JSP files. I will use SQL with joins for this, so first, I will create a `comment` XML and interface, and then I will create a `reply` XML and interface.

#### MyCommentMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill.mapper.MyCommentMapper">
    <insert id="insertComment1" parameterType="MyComment1">
        insert into mycomment values(mycomment_seq.nextval, #{commentId}, #{commentContent}, sysdate)
    </insert>
    
    <insert id="insertComment2" parameterType="MyComment1">
        <!-- selectKey: An element used to provide a value from the result of a SELECT statement
        to be stored as a field in the object specified by the parameterType attribute of the insert element -->
        <!-- => A dependent element of the insert element -->
        <!-- => Written to use an auto-incremented value or a random value retrieved via a SELECT statement
        as a column value for the insert command by storing it in the object's field -->
        <!-- resultType attribute: Specifies the Java data type to receive the result value from the SELECT statement -->
        <!-- => Can use an alias specified by the typeAlias element instead of a Java data type -->
        <!-- keyProperty attribute: Specifies the field name of the class set by the parameterType attribute of the insert element
        to store the result value from the SELECT command -->
        <!-- order attribute: Set to [BEFORE] or [AFTER] -->
        <selectKey resultType="int" keyProperty="commentNo" order="BEFORE">
            select mycomment_seq.nextval from dual
        </selectKey>
        insert into mycomment values(#{commentNo}, #{commentId}, #{commentContent}, sysdate)
    </insert>
    
    <select id="selectCommentList1" resultType="MyComment1">
        select comment_no, comment_id, comment_content, comment_date from mycomment 
            order by comment_no desc
    </select>
    
    <!-- When column names in the search result differ from field names in the resultType class, 
    instead of creating an object of the resultType class, NULL will be provided -->
    <!--  
    <select id="selectCommentList2" resultType="MyComment2">
        select comment_no, comment_id, comment_content, comment_date from mycomment 
            order by comment_no desc
    </select>
    -->
    
    <!-- Use the ColumnAlias feature to match column names in the search result with field names in the resultType class
    - Use "" to express inappropriate words as identifiers -->
    <!-- 
    <select id="selectCommentList2" resultType="MyComment2">
        select comment_no no, comment_id id, comment_content content, comment_date "date" 
            from mycomment order by comment_no desc
    </select>
    -->
     
    <!-- Use resultMap element to manually map the search result to a Java object
    and use sub-elements to store column values in the object's fields -->
    <!-- => Creates an object using the default constructor of the class specified by the type attribute
    and calls the setter methods for the object's fields using id or result elements to set column values -->
    <resultMap type="MyComment2" id="myComment2ResultMap">
        <id column="comment_no" property="no"/>
        <result column="comment_id" property="id"/>
        <result column="comment_content" property="content"/>
        <result column="comment_date" property="date"/>
    </resultMap> 
    
    <!-- Use resultMap attribute of the select element to manually map the search result to a Java object -->
    <!-- 
    <select id="selectCommentList2" resultMap="myComment2ResultMap">
        select comment_no, comment_id, comment_content, comment_date from mycomment 
            order by comment_no desc
    </select>
    -->
    
    <!-- Use a constructor of the class specified by the type attribute in resultMap
    to create an object and set column values to the object's fields via constructor parameters -->
    <resultMap type="MyComment2" id="myComment2ConstructorResultMap">
        <!-- constructor: An element to provide information for mapping using a constructor of the class specified by the type attribute -->
        <!-- => Sub-elements: idArg or arg elements to be used -->
        <!-- => Number of sub-elements must match and have the same type as constructor parameters -->
        <constructor>
            <!-- idArg: An element to pass column values to constructor parameters -->
            <!-- => Provides PK constrained column values to constructor parameters -->
            <!-- column attribute: Specifies the column name of the search result -->
            <!-- javaType attribute: Specifies the data type of the constructor parameter -->
            <!-- => Can use an alias specified by the typeAlias element instead of a Java data type -->
            <idArg column="comment_no" javaType="_int"/>
            <!-- arg: An element to pass column values to constructor parameters -->
            <arg column="comment_id" javaType="string"/>
            <!-- 
            <arg column="comment_content" javaType="string"/>
            <arg column="comment_date" javaType="string"/>
            -->
        </constructor>
        <!-- Constructor and id (or result) elements can be used together to set column values to the object's fields -->
        <!-- => Write id (or result) elements after the constructor element -->
        <result column="comment_content" property="content"/>
        <result column="comment_date" property="date"/>
    </resultMap>
    
    <select id="selectCommentList2" resultMap="myComment2ConstructorResultMap">
        select comment_no, comment_id, comment_content, comment_date from mycomment 
            order by comment_no desc
    </select>
    
    <!-- 
    <select id="selectCommentList3" resultType="MyComment3">
        select comment_no "no", comment_id "id", comment_content "content", comment_date "date"
            , user_name "name" from mycomment join myuser on comment_id=user_id 
            order by comment_no desc
    </select>
    -->
     
    <resultMap type="MyComment3" id="myComment3ResultMap">
        <id column="comment_no" property="no"/>
        <result column="comment_id" property="id"/>
        <result column="comment_content" property="content"/>
        <result column="comment_date" property="date"/>
        <result column="user_name" property="name"/>
    </resultMap>
    
    <select id="selectCommentList3" resultMap="myComment3ResultMap">
        select comment_no, comment_id, comment_content, comment_date, user_name
            from mycomment join myuser on comment_id=user_id order by comment_no desc
    </select>
    
    <select id="selectCommentUserList1" resultType="MyCommentUser1">
        select comment_no, comment_id, comment_content, comment_date, user_id, user_name
            from mycomment join myuser on comment_id=user_id order by comment_no desc
    </select>
    
    <resultMap type="MyCommentUser2" id="myCommentUserResultMap">
        <!-- association: In a 1:1 relationship join, an element to create an object with column values
        and store it in a field of the class specified by the type attribute of resultMap -->
        <!-- => Use id or result elements in sub-elements to store column values in the created object -->
        <!-- property attribute: Specifies the field name to store the association object -->
        <!-- javaType attribute: Specifies the Java type of the association object -->
        <!-- => Can use an alias specified by the typeAlias element instead of a Java data type -->
        <association property="comment" javaType="MyComment1">
            <id column="comment_no" property="commentNo"/>
            <result column="comment_id" property="commentId"/>
            <result column="comment_content" property="commentContent"/>
            <result column="comment_date" property="commentDate"/>
        </association>
        <association property="user" javaType="MyUser">
            <id column="user_id" property="userId"/>
            <result column="user_name" property="userName"/>
        </association>
    </resultMap>
    
    <!-- Search and join rows from MYCOMMENT and MYUSER tables in a 1:1 relationship,
    and use resultMap to manually map the result to a Java object (MyCommentUser object) -->
    <select id="selectCommentUserList2" resultMap="myCommentUserResultMap">
        select comment_no, comment_id, comment_content, comment_date, user_id, user_name
            from mycomment join myuser on comment_id=user_id order by comment_no desc
    </select>
    
    <!-- Provide a row from MYCOMMENT table as a MyComment1 object
    by passing a post number - Single row search -->
    <select id="selectComment" parameterType="int" resultType="MyComment1">
        select comment_no, comment_id, comment_content, comment_date 
            from mycomment where comment_no=#{commentNo}
    </select>    
    
    <resultMap type="MyCommentReply" id="myCommentReplyResultMap">
        <association property="comment" javaType="MyComment1">
            <id column="comment_no" property="commentNo"/>
            <result column="comment_id" property="commentId"/>
            <result column="comment_content" property="commentContent"/>
            <result column="comment_date" property="commentDate"/>
        </association>
        
        <!-- collection: In a 1:N relationship join, an element to create objects with column values
        and add them as elements to a List object, which is then stored in a
```
#### MyCommentMapper.java
```java 
package xyz.itwill.mapper;

public interface MyCommentMapper {
	int insertComment1(MyComment1 comment);
	int insertComment2(MyComment1 comment);
	List<MyComment1> selectCommentList1();
	List<MyComment2> selectCommentList2();
	List<MyComment3> selectCommentList3();
	List<MyCommentUser1> selectCommentUserList1();
	List<MyCommentUser2> selectCommentUserList2();
	MyComment1 selectComment(int commentNo);
	MyCommentReply selectCommentReply(int commentNO);
	}
```
---

#### MyReplyMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill.mapper.MyReplyMapper">
    <insert id="insertReply" parameterType="MyReply">
        <selectKey resultType="int" keyProperty="replyNo" order="BEFORE">
            select myreply_seq.nextval from dual
        </selectKey>
        insert into myreply values(#{replyNo}, #{replyId}, #{replyContent}, sysdate, #{replyCommentNo})
    </insert>

    <select id="selectReplyList" resultType="MyReply">
        select reply_no, reply_id, reply_content, reply_date, reply_comment_no 
            from myreply order by reply_no desc
    </select>

    <!-- Retrieve the most recent 5 rows from the MYREPLY table -->
    <!-- Issue: In XML-based mapper files, comparison operators such as > or < can be interpreted as element symbols, causing errors -->
    <!-- Solution-1: Use entity references (escape characters) to represent > or < operators in SQL commands -->
    <!--  
    <select id="selectCountReplyList" resultType="MyReply">
        select rownum, reply.* from (select reply_no, reply_id, reply_content, reply_date 
            , reply_comment_no from myreply order by reply_no desc) reply where rownum &lt; 6
    </select>
    -->

    <!-- Solution-2: Use CDATA sections to include SQL commands -->
    <!-- CDATA Section: An area in XML that allows the content to be used as-is without parsing -->
    <select id="selectCountReplyList" resultType="MyReply">
        <![CDATA[
        select rownum, reply.* from (select reply_no, reply_id, reply_content, reply_date 
            , reply_comment_no from myreply order by reply_no desc) reply where rownum < 6
        ]]>
    </select>

    <resultMap type="MyReplyUser" id="myReplyUserResultMap1">
        <association property="reply" javaType="MyReply">
            <id column="reply_no" property="replyNo"/>
            <result column="reply_id" property="replyId"/>
            <result column="reply_content" property="replyContent"/>
            <result column="reply_date" property="replyDate"/>
            <result column="reply_comment_no" property="replyCommentNo"/>
        </association>
        <association property="user" javaType="MyUser">
            <id column="user_id" property="userId"/>
            <result column="user_name" property="userName"/>
        </association>
    </resultMap>

    <select id="selectReplyUserList1" resultMap="myReplyUserResultMap1">
        select reply_no, reply_id, reply_content, reply_date, reply_comment_no, user_id
            , user_name from myreply join myuser on reply_id=user_id order by reply_no desc
    </select>

    <resultMap type="MyReply" id="myReplyResultMap">
        <id column="reply_no" property="replyNo"/>
        <result column="reply_id" property="replyId"/>
        <result column="reply_content" property="replyContent"/>
        <result column="reply_date" property="replyDate"/>
        <result column="reply_comment_no" property="replyCommentNo"/>
    </resultMap>

    <resultMap type="MyReplyUser" id="myReplyUserResultMap2">
        <!-- resultMap: Reuse the mapping information of the resultMap element identified by the id attribute -->
        <!-- => association element uses the resultMap element to provide objects and store in fields -->
        <association property="reply" resultMap="myReplyResultMap"/>
        <!-- select attribute: Identifies the select element to use -->
        <!-- => association element uses the select element to provide objects and store in fields -->
        <!-- column attribute: Defines the column names for the SELECT command -->
        <!-- => Provides the column values for the SELECT command specified in the select attribute -->
        <association property="user" select="selectUser" column="reply_id"/>
    </resultMap>

    <!-- Select element for use in association elements, not directly in DAO methods -->
    <!-- => The abstract method for the select element is not written in the interface-based mapper file -->
    <!-- => The SELECT command is written to return a single row based on the parameterType attribute -->
    <select id="selectUser" resultType="MyUser" parameterType="string">
        select user_id, user_name from myuser where user_id=#{replyId}
    </select>

    <select id="selectReplyUserList2" resultMap="myReplyUserResultMap2">
        select reply_no, reply_id, reply_content, reply_date, reply_comment_no 
            from myreply order by reply_no desc
    </select>

    <!-- Retrieve rows from the MYREPLY table based on the post number provided -->
    <!-- Rows are returned as MyReply objects -->
    <!-- Search result: 0 or more rows -->
    <select id="selectCommentNoReplyList" parameterType="int" resultType="MyReply">
        select reply_no, reply_id, reply_content, reply_date, reply_comment_no 
            from myreply where reply_comment_no=#{commentNo} order by reply_no desc
    </select>
</mapper>
```
#### MyReplyMapper.java
```
package xyz.itwill.mapper;

public interface MyReplyMapper {
	int insertReply(MyReply reply);
	List<MyReply> selectReplyList();
	List<MyReply> selectCountReplyList();
	List<MyReplyUser> selectReplyUserList1();
	List<MyReplyUser> selectReplyUserList2();
	List<MyReply> selectCommentNoReplyList(int commentNo);
}
```

```sql
/*
// MYCOMMENT table: A table for storing posts
create table mycomment(comment_no number primary key, comment_id varchar2(50)
    , comment_content varchar2(100), comment_date date);

// MYCOMMENT_SEQ sequence: A sequence to provide automatically incremented values for the COMMENT_NO column in the MYCOMMENT table
create sequence mycomment_seq;

Column Name        Nullable?  Type            
------------------ --------- ------------- 
COMMENT_NO         NOT NULL  NUMBER        - Post number 
COMMENT_ID                  VARCHAR2(50)  - Post author (member's ID)
COMMENT_CONTENT             VARCHAR2(100) - Post content
COMMENT_DATE                DATE          - Post date  
*/

/*
// MYREPLY table: A table for storing comments on posts
create table myreply(reply_no number primary key, reply_id varchar2(50)
	, reply_content varchar2(100), reply_date date, reply_comment_no number);

// MYREPLY_SEQ sequence: A sequence to provide automatically incremented values for the REPLY_NO column in the MYREPLY table
create sequence myreply_seq;  

Column Name        Nullable?  Type            
------------------ --------- ------------- 
REPLY_NO          NOT NULL  NUMBER        - Comment number 
REPLY_ID                   VARCHAR2(50)  - Comment author (member's ID)
REPLY_CONTENT              VARCHAR2(100) - Comment content
REPLY_DATE                 DATE          - Comment date  
REPLY_COMMENT_NO           NUMBER        - Post number  
*/

/*
MYUSER table: A table for storing user information 
 => SQL commands are case-insensitive, so identifiers are written using snake case notation
 => Snake case notation: A method for writing identifiers by using underscores to separate words  
create table myuser(user_id varchar2(50) primary key, user_name varchar2(50));

Column Name        Nullable?  Type           
------------------ --------- ------------ 
USER_ID           NOT NULL  VARCHAR2(50) - ID 
USER_NAME                 VARCHAR2(50) - Name
*/
```



MyComment3.java DTO join comment and user - change all mapper, dao 
create list3.jsp

MyCommentUser1.java  join comment and user - change all mapper, dao 
create list3.jsp same as above 

MyCommentUser2.java DTO 

MyReply MyReplyUser - reply

comment 목록에서 들어가서 보고 

본 글을 안에 뎃글이 달려 있기때문에 reply 다시 변경한다 



commentReplySelect1.jsp


commentinsert2.jsp
commentinsert1 1.jsp
commentReplySelect2.jsp
commentListSelect2.jsp
commentListSelect3.jsp
commentReplySelect1 1.jsp
commentListSelect1.jsp
commentUserListSelect1.jsp
commentUserListSelect2.jsp

replyListSelect.jsp
replyListSelect1.jsp
replyListSelect2.jsp
replyCountListSelect.jsp
replyInsert.jsp
