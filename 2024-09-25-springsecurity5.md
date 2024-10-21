---
layout: single
title: 2024/06
---
#### **SecurityBoard.java DTO**

```java
package xyz.itwill.dto;

import lombok.Data;

/*
create table security_board(
    num number primary key,
    writer varchar2(100),
    subject varchar2(550),
    content varchar2(4000),
    regdate date
);
create sequence security_board_seq;
*/
@Data
public class SecurityBoard {
    private int num;        // Number
    private String writer;  // Writer
    private String subject; // Subject
    private String content; // Content
    private String regdate; // Registration date
    
    private String name;    // Name
}
```

#### SecurityBoardMapper.xml Mapper


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill.mapper.SecurityBoardMapper">
 
 <insert id="insertSecurityBoard">
     <selectKey resultType="int" keyProperty="num" order="BEFORE">
         select security_board_seq.nextval from dual
     </selectKey>
     insert into security_board values(#{num}, #{writer}, #{subject}, #{content}, sysdate)
 </insert>

 <update id="updateSecurityBoard">
     update security_board set subject=#{subject}, content=#{content} where num=#{num}
 </update>

 <delete id="deleteSecurityBoard">
     delete from security_board where num=#{num}
 </delete>

 <select id="selectSecurityBoardByNum" resultType="SecurityBoard">
     select num, writer, subject, content, regdate as name from security_board join security_user on writer=userid
 </select>

 <select id="selectSecurityBoardCount" resultType="int">
     select count(*) from security_board join security_user on writer=userid
     <if test="keyword != null and keyword != ''">
         <bind name="word" value="'%'+keyword+'%'"/>
         where ${column} like #{word}
     </if>
 </select>

 <select id="selectSecurityBoardList" resultType="SecurityBoard">
     select * from (
         select rownum rn, board.* from (
             select num, writer, subject, content, regdate as name from security_board 
             join security_user on writer=userid 
             <if test="keyword != null and keyword != ''">
                 <bind name="word" value="'%'+keyword+'%'"/>
                 where ${column} like #{word}
             </if>
             order by num desc
         ) board
     ) where rn between #{startRow} and #{endRow}
 </select>

</mapper>
```


### 1. SecurityBoardMapper.java
```java
package xyz.itwill.mapper;

import java.util.List;
import java.util.Map;

import xyz.itwill.dto.SecurityBoard;

public interface SecurityBoardMapper {
    int insertSecurityBoard(SecurityBoard board);
    int updateSecurityBoard(SecurityBoard board);
    int deleteSecurityBoard(int num);
    SecurityBoard selectSecurityBoardByNum(int num);
    int selectSecurityBoardCount(Map<String, Object> map);
    List<SecurityBoard> selectSecurityBoardList(Map<String, Object> map);
}
```

### 2. SecurityBoardDAO.java
```java
package xyz.itwill.dao;

import java.util.List;
import java.util.Map;

import xyz.itwill.dto.SecurityBoard;

public interface SecurityBoardDAO {
    int insertSecurityBoard(SecurityBoard board);
    int updateSecurityBoard(SecurityBoard board);
    int deleteSecurityBoard(int num);
    SecurityBoard selectSecurityBoardByNum(int num);
    int selectSecurityBoardCount(Map<String, Object> map);
    List<SecurityBoard> selectSecurityBoardList(Map<String, Object> map);
}
```

### 3. SecurityBoardDAOImpl.java
```java
package xyz.itwill.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dto.SecurityBoard;
import xyz.itwill.mapper.SecurityBoardMapper;

@Repository
@RequiredArgsConstructor
public class SecurityBoardDAOImpl implements SecurityBoardDAO {
    private final SqlSession sqlSession;

    @Override
    public int insertSecurityBoard(SecurityBoard board) {
        // Insert a new security board entry
        return sqlSession.getMapper(SecurityBoardMapper.class).insertSecurityBoard(board);
    }

    @Override
    public int updateSecurityBoard(SecurityBoard board) {
        // Update an existing security board entry
        return sqlSession.getMapper(SecurityBoardMapper.class).updateSecurityBoard(board);
    }

    @Override
    public int deleteSecurityBoard(int num) {
        // Delete a security board entry by its number
        return sqlSession.getMapper(SecurityBoardMapper.class).deleteSecurityBoard(num);
    }

    @Override
    public SecurityBoard selectSecurityBoardByNum(int num) {
        // Select a security board entry by its number
        return sqlSession.getMapper(SecurityBoardMapper.class).selectSecurityBoardByNum(num);
    }

    @Override
    public int selectSecurityBoardCount(Map<String, Object> map) {
        // Count the number of security board entries
        return sqlSession.getMapper(SecurityBoardMapper.class).selectSecurityBoardCount(map);
    }

    @Override
    public List<SecurityBoard> selectSecurityBoardList(Map<String, Object> map) {
        // Get a list of security board entries
        return sqlSession.getMapper(SecurityBoardMapper.class).selectSecurityBoardList(map);
    }
}
```

### 4. SecurityBoardService.java
```java
package xyz.itwill.service;

import java.util.Map;

import xyz.itwill.dto.SecurityBoard;

public interface SecurityBoardService {
    void addSecurityBoard(SecurityBoard board);
    void modifySecurityBoard(SecurityBoard board);
    void removeSecurityBoard(int num);
    SecurityBoard getSecurityBoardByNum(int num);
    Map<String, Object> getSecurityBoardList(Map<String, Object> map);
}
```

### 5. SecurityBoardServiceImpl.java
```java
package xyz.itwill.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import xyz.itwill.dao.SecurityBoardDAO;
import xyz.itwill.dao.SecurityUserDAO;
import xyz.itwill.dto.SecurityBoard;
import xyz.itwill.util.Pager;

@Service
@RequiredArgsConstructor
public class SecurityBoardServiceImpl implements SecurityBoardService {
    private final SecurityBoardDAO securityBoardDAO;
    private final SecurityUserDAO securityUserDAO;

    @Override
    @Transactional
    public void addSecurityBoard(SecurityBoard board) {
        // Check if the writer exists
        if (securityUserDAO.selectSecurityUserByUserid(board.getWriter()) == null) {
            throw new RuntimeException("no writer");
        }
        securityBoardDAO.insertSecurityBoard(board);
    }

    @Override
    public void modifySecurityBoard(SecurityBoard board) {
        // Check if the writer exists
        if (securityUserDAO.selectSecurityUserByUserid(board.getWriter()) == null) {
            throw new RuntimeException("no writer");
        }
        // Check if the subject exists
        if (securityBoardDAO.selectSecurityBoardByNum(board.getNum()) == null) {
            throw new RuntimeException("no subject");
        }
        securityBoardDAO.updateSecurityBoard(board);
    }

    @Transactional
    @Override
    public void removeSecurityBoard(int num) {
        // Check if the subject exists
        if (securityBoardDAO.selectSecurityBoardByNum(num) == null) {
            throw new RuntimeException("no subject");
        }
        securityBoardDAO.deleteSecurityBoard(num);
    }

    @Override
    public SecurityBoard getSecurityBoardByNum(int num) {
        SecurityBoard board = securityBoardDAO.selectSecurityBoardByNum(num);
        // Check if the subject exists
        if (board == null) {
            throw new RuntimeException("no subject");
        }
        return board;
    }

    @Override
    public Map<String, Object> getSecurityBoardList(Map<String, Object> map) {
        int pageNum = 1;
        // Get the current page number
        if (map.get("pageNum") != null && map.get("pageNum").equals("")) {
            pageNum = Integer.parseInt((String) map.get("pageNum"));
        }
        int pageSize = 5;
        // Get the page size
        if (map.get("pageSize") != null && map.get("pageSize").equals("")) {
            pageSize = Integer.parseInt((String) map.get("pageSize"));
        }
        int totalBoard = securityBoardDAO.selectSecurityBoardCount(map);
        int blockSize = 5;
        Pager pager = new Pager(pageNum, pageSize, totalBoard, blockSize);
        map.put("startRow", pager.getStartRow());
        map.put("endRow", pager.getEndRow());
        List<SecurityBoard> boardList = securityBoardDAO.selectSecurityBoardList(map);
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("pager", pager);
        result.put("securityBoardList", boardList);

        return result;
    }
}
```

