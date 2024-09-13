---
layout: single
title: 2024-09-12-spring32-restful
---
####  Table SQL
```
create table rest_board(idx  number primary key,writer varchar2(50),content varchar2(100), regdate date);
create sequence rest_board_seq;
```
#### RestBoard.java DTO


```java
package xyz.itwill09.dto;

import lombok.Data;

@Data
public class RestBoard {
    private int idx;          // Index of the board entry
    private String writer;    // Author of the board entry
    private String content;   // Content of the board entry
    private String regdate;   // Registration date of the board entry
}
```
#### RestBoardMapper.xml


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill09.mapper.RestBoardMapper">
	<insert id="insertRestBoard">
		<selectKey resultType="int" keyProperty="idx" order="BEFORE">
			select rest_board_seq.nextval from dual
		</selectKey>
		insert into rest_board values(#{idx}, #{writer}, #{content}, sysdate)
	</insert>
	
	<update id="updateRestBoard">
		update rest_board set writer=#{writer}, content=#{content} where idx=#{idx}
	</update>
	
	<delete id="deleteRestBoard">
		delete from rest_board where idx=#{idx} 
	</delete>
	
	<select id="selectRestBoard" resultType="RestBoard">
		select idx, writer, content, regdate from rest_board where idx=#{idx}
	</select>
	
	<select id="selectRestBoardCount" resultType="int">
		select count(*) from rest_board
	</select>
	
	<select id="selectRestBoardList" resultType="RestBoard">
		select * from (select rownum rn, board.* from (select idx, writer, content, regdate 
			from rest_board order by idx desc) board) where rn between #{startRow} and #{endRow} 
	</select>
</mapper>
```

#### RestBoardMapper.java


```java
package xyz.itwill09.mapper;

import java.util.List;
import java.util.Map;

import xyz.itwill09.dto.RestBoard;

public interface RestBoardMapper {
    int insertRestBoard(RestBoard restboard); // Method to insert a new board entry
    int updateRestBoard(RestBoard restboard); // Method to update an existing board entry
    int deleteRestBoard(int idx);             // Method to delete a board entry by its index
    RestBoard selectRestBoard(int idx);       // Method to select a board entry by its index
    int selectRestBoardCount();               // Method to count the total number of board entries
    List<RestBoard> selectRestBoardList(Map<String, Object> map); // Method to select a list of board entries based on criteria
}
```


#### RestBoardDAO.java interface
```java
package xyz.itwill09.dao;

import java.util.List;
import java.util.Map;

import xyz.itwill09.dto.RestBoard;

public interface RestBoardDAO {
    int insertRestBoard(RestBoard restboard);             // Method to insert a new board entry
    int updateRestBoard(RestBoard restboard);             // Method to update an existing board entry
    int deleteRestBoard(int idx);                         // Method to delete a board entry by index
    int selectRestBoardCount();                           // Method to count the total number of board entries
    RestBoard selectRestBoard(int idx);                   // Method to select a board entry by index
    List<RestBoard> selectRestBoardList(Map<String, Object> map); // Method to select a list of board entries based on criteria
}
```
#### RestBoardDAOImpl.java 
```java
package xyz.itwill09.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.RestBoard;
import xyz.itwill09.mapper.RestBoardMapper;

@Repository
@RequiredArgsConstructor
public class RestBoardDAOImpl implements RestBoardDAO {
    private final SqlSession sqlSession;

    @Override
    public int insertRestBoard(RestBoard restboard) {
        return sqlSession.getMapper(RestBoardMapper.class).insertRestBoard(restboard);
    }

    @Override
    public int updateRestBoard(RestBoard restboard) {
        return sqlSession.getMapper(RestBoardMapper.class).updateRestBoard(restboard);
    }

    @Override
    public int deleteRestBoard(int idx) {
        return sqlSession.getMapper(RestBoardMapper.class).deleteRestBoard(idx);
    }

    @Override
    public int selectRestBoardCount() {
        return sqlSession.getMapper(RestBoardMapper.class).selectRestBoardCount();
    }

    @Override
    public RestBoard selectRestBoard(int idx) {
        return sqlSession.getMapper(RestBoardMapper.class).selectRestBoard(idx);
    }

    @Override
    public List<RestBoard> selectRestBoardList(Map<String, Object> map) {
        return sqlSession.getMapper(RestBoardMapper.class).selectRestBoardList(map);
    }
}
```

#### RestBoardService.java interface


```java
package xyz.itwill09.service;

import java.util.Map;

import xyz.itwill09.dto.RestBoard;

public interface RestBoardService {
    void addRestBoard(RestBoard restBoard);                     // Method to add a new board entry
    void modifyRestBoard(RestBoard restBoard);                  // Method to modify an existing board entry
    void removeRestBoard(int idx);                              // Method to remove a board entry by its index
    RestBoard getRestBoard(int idx);                            // Method to get a board entry by its index
    Map<String, Object> getRestBoardList(int pageNum, int pageSize); // Method to get a list of board entries with pagination
}
```
#### RestBoardServiceImpl.java
```java
package xyz.itwill09.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dao.RestBoardDAO;
import xyz.itwill09.dto.RestBoard;
import xyz.itwill09.util.Pager;

@Service
@RequiredArgsConstructor
public class RestBoardServiceImpl implements RestBoardService {
    private final RestBoardDAO restBoardDAO;
    
    @Override
    public void addRestBoard(RestBoard restBoard) {
        restBoardDAO.insertRestBoard(restBoard); // Inserts a new board entry
    }

    @Override
    public void modifyRestBoard(RestBoard restBoard) {
        restBoardDAO.updateRestBoard(restBoard); // Updates an existing board entry
    }

    @Override
    public void removeRestBoard(int idx) {
        restBoardDAO.deleteRestBoard(idx); // Deletes a board entry by its index
    }

    @Override
    public RestBoard getRestBoard(int idx) {
        return restBoardDAO.selectRestBoard(idx); // Retrieves a board entry by its index
    }

    @Override
    public Map<String, Object> getRestBoardList(int pageNum, int pageSize) {
        int totalSize = restBoardDAO.selectRestBoardCount(); // Gets the total number of board entries
        int blockSize = 5; // Number of entries per page
        Pager pager = new Pager(pageNum, pageSize, totalSize, blockSize);
        
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("startRow", pager.getStartRow()); // Start row for pagination
        pageMap.put("endRow", pager.getEndRow()); // End row for pagination
        List<RestBoard> restBoardList = restBoardDAO.selectRestBoardList(pageMap); // Retrieves a list of board entries
        
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("pager", pager); // Adds pager information
        resultMap.put("restBoardList", restBoardList); // Adds the list of board entries
        
        return resultMap; // Returns the result map with pager and board list
    }
}
```
#### RestBoardController.java
```java
package xyz.itwill09.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.service.RestBoardService;

// @RestController: Annotation used to declare a Spring Bean for a controller class
// that provides REST functionality (RESTful API).
// It allows the request handler methods to return data directly, which will be converted
// to JSON (or other formats) without needing @ResponseBody on each method.
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class RestBoardController {
    private final RestBoardService restBoardService;
    
    // For REST functionality, it is recommended to use annotations such as @GetMapping,
    // @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping instead of @RequestMapping.
    // These annotations map the request path to the handler methods and specify the HTTP method.
    // GET (search), POST (insert), PUT (update), PATCH (partial update), DELETE (remove)

    // @RequestMapping(value = "/board_list", method = RequestMethod.GET)
    @GetMapping("/board_list")
    // Since @RestController is used on the class, the @ResponseBody annotation is not needed
    // on the method. The return value will be directly converted to JSON and sent to the client.
    //@ResponseBody
    public Map<String, Object> restBoardList(@RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "5") int pageSize) {
        return restBoardService.getRestBoardList(pageNum, pageSize);
    }
}
```

### Summary:
- **`RestBoardController` Class**: A REST controller class annotated with `@RestController`, which means it handles RESTful requests and automatically converts return values to JSON.
- **`restBoardList` Method**: Handles GET requests to `/rest/board_list` and returns a paginated list of board entries by using `RestBoardService`. Default values for `pageNum` and `pageSize` are provided if not specified in the request.