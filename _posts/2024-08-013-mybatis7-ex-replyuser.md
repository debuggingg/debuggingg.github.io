---
layout: single
title: 2024/08/12 MyBatis7-EX-MyComment-Reply-ResultMap
---
#### MyCommentUserReplyUser.java DTO


```java
package xyz.itwill.dto;

import java.util.List;

// Class to store the result of combining rows from the MYCOMMENT (MYUSER) and MYREPLY (MYUSER) tables 
// in a 1:N relationship
public class MyCommentUserReplyUser {
    // Fields to store column values from the MYCOMMENT table (post information)
    private int commentNo;
    private String commentId;
    private String commentContent;
    private String commentDate;
    
    // Field to store a single MyUser object representing the user information for the post
    private MyUser user;
    
    // Field to store a list of MyReplyUser objects, which combine comment and user information
    // from the MYREPLY and MYUSER tables in a 1:1 relationship
    private List<MyReplyUser> replyUserList;

    // Default constructor
    public MyCommentUserReplyUser() {
        // TODO Auto-generated constructor stub
    }

    // Getter and setter for commentNo
    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }

    // Getter and setter for commentId
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    // Getter and setter for commentContent
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    // Getter and setter for commentDate
    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    // Getter and setter for user
    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    // Getter and setter for replyUserList
    public List<MyReplyUser> getReplyUserList() {
        return replyUserList;
    }

    public void setReplyUserList(List<MyReplyUser> replyUserList) {
        this.replyUserList = replyUserList;
    }
}
```



#### MyCommentMapper 2.xml


```xml
<!--
<resultMap type="MyCommentUserReplyUser" id="myCommentUserReplyUserResultMap">
    <id column="comment_no" property="commentNo"/>
    <result column="comment_id" property="commentId"/>
    <result column="comment_content" property="commentContent"/>
    <result column="comment_date" property="commentDate"/>
    <association property="user" javaType="MyUser">
        <id column="user_id" property="userId"/>
        <result column="user_name" property="userName"/>
    </association>
    <collection property="replyUserList" ofType="MyReplyUser">
        <association property="reply" javaType="MyReply">
            <id column="reply_no" property="replyNo"/>
            <result column="reply_id" property="replyId"/>
            <result column="reply_content" property="replyContent"/>
            <result column="reply_date" property="replyDate"/>
            <result column="reply_comment_no" property="replyCommentNo"/>
        </association>
        <association property="user" javaType="MyUser">
            <id column="reply_user_id" property="userId"/>
            <result column="reply_user_name" property="userName"/>
        </association>
    </collection>
</resultMap>
-->

<!-- Element to retrieve and combine rows from the MYCOMMENT (MYUSER) and MYREPLY (MYUSER) tables 
in a 1:N relationship, providing a MyCommentUserReplyUser object -->
<!--
<select id="selectCommentUserReplyUser" parameterType="int" resultMap="myCommentUserReplyUserResultMap">
    select comment_no, comment_id, comment_content, comment_date, user_id, user_name
        , reply_no, reply_id, reply_content, reply_date, reply_comment_no, reply_user_id
        , reply_user_name from mycomment join myuser on comment_id=user_id left join
        (select reply_no, reply_id, reply_content, reply_date, reply_comment_no
        , user_id reply_user_id, user_name reply_user_name from myreply join myuser 
        on reply_id=user_id) on comment_no=reply_comment_no where comment_no=#{commentNo}
        order by reply_no desc
</select>
-->

<!-- autoMapping attribute: set to either false (default) or true -->
<!-- => If autoMapping is set to [true], fields with the same name as column names in the result set will be 
automatically populated with the column values -->
<resultMap type="MyCommentUserReplyUser" id="myCommentUserReplyUserResultMap" autoMapping="true">
    <id column="comment_no" property="commentNo"/>
    <association property="user" javaType="MyUser" autoMapping="true"/>
    <collection property="replyUserList" select="selectReplyUser" column="comment_no"/>
</resultMap>

<resultMap type="MyReplyUser" id="myReplyUserResultMap">
    <association property="reply" javaType="MyReply" autoMapping="true"/>
    <association property="user" javaType="MyUser" autoMapping="true"/>
</resultMap>

<!-- Element to retrieve and combine rows from the MYREPLY and MYUSER tables 
in a 1:1 relationship to provide a MyReplyUser object -->
<select id="selectReplyUser" parameterType="int" resultMap="myReplyUserResultMap">
    select reply_no, reply_id, reply_content, reply_date, reply_comment_no
        , user_id, user_name from myreply join myuser on reply_id=user_id
        where reply_comment_no=#{commentNo} order by reply_no desc
</select>

<!-- Element to retrieve and combine rows from the MYCOMMENT and MYUSER tables 
in a 1:1 relationship to provide a MyCommentUserReplyUser object -->
<select id="selectCommentUserReplyUser" parameterType="int" resultMap="myCommentUserReplyUserResultMap">
    select comment_no, comment_id, comment_content, comment_date, user_id, user_name
        from mycomment join myuser on comment_id=user_id where comment_no=#{commentNo} 
</select>
```

This configuration defines the result mappings and SQL queries for retrieving comments, replies, and user information, handling the relationships between these entities using MyBatis. The `autoMapping` attribute is set to `true` to enable automatic mapping of result columns to properties.