---
layout: single
title: 2024-09-06-spring27-mybatis-file-paging
---
#### FileBoardMapper.xml


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill09.mapper.FileBoardMapper">
    <!-- Insert a new file board entry -->
    <insert id="insertFileBoard">
        <selectKey resultType="int" keyProperty="num" order="BEFORE">
            select file_board_seq.nextval from dual
        </selectKey>  
        insert into file_board values(#{num}, #{writer}, #{subject}, #{filename})
    </insert>
    
    <!-- Delete a file board entry by its number -->
    <delete id="deleteFileBoard">
        delete from file_board where num=#{num}
    </delete>
    
    <!-- Select a file board entry by its number -->
    <select id="selectFileBoard" resultType="FileBoard">
        select num, writer, subject, filename from file_board where num=#{num} 
    </select>
    
    <!-- Count the total number of file board entries -->
    <select id="selectFileBoardCount" resultType="int">
        select count(*) from file_board    
    </select>
    
    <!-- Select a paginated list of file board entries -->
    <select id="selectFileBoardList" resultType="FileBoard">
        select * from (select rownum rn, board.* from (select num, writer, subject, filename 
            from file_board order by num desc) board) where rn between #{startRow} and #{endRow}
    </select>
</mapper>
```

### Explanation:
- **`insertFileBoard`**: Inserts a new entry into the `file_board` table. The `selectKey` element retrieves the next sequence value for the primary key before the insert operation.
- **`deleteFileBoard`**: Deletes an entry from the `file_board` table based on its `num`.
- **`selectFileBoard`**: Retrieves a single entry from the `file_board` table by its `num`.
- **`selectFileBoardCount`**: Returns the total number of entries in the `file_board` table.
- **`selectFileBoardList`**: Retrieves a paginated list of entries from the `file_board` table, ordered by `num` in descending order. It uses row numbers to handle pagination.

#### FileBoardMapper.java interface for Mapper

```java
package xyz.itwill09.mapper;

import java.util.List;
import java.util.Map;

import xyz.itwill09.dto.FileBoard;

public interface FileBoardMapper {
    // Insert a new file board entry
    int insertFileBoard(FileBoard board);
    
    // Delete a file board entry by its number
    int deleteFileBoard(int num);
    
    // Select a file board entry by its number
    FileBoard selectFileBoard(int num);
    
    // Get the count of all file board entries
    int selectFileBoardCount();
    
    // Select a list of file board entries with pagination
    List<FileBoard> selectFileBoardList(Map<String, Object> map);
}
```


### `FileBoardDAO` Interface

```java
package xyz.itwill09.dao;

import java.util.List;
import java.util.Map;

import xyz.itwill09.dto.FileBoard;

public interface FileBoardDAO {
    // Insert a new file board entry
    int insertFileBoard(FileBoard board);
    
    // Delete a file board entry by its number
    int deleteFileBoard(int num);
    
    // Select a file board entry by its number
    FileBoard selectFileBoard(int num);
    
    // Get the count of all file board entries
    int selectFileBoardCount();
    
    // Select a list of file board entries with pagination
    List<FileBoard> selectFileBoardList(Map<String, Object> map);
}
```

### `FileBoardDAOImpl` Implementation

```java
package xyz.itwill09.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.FileBoard;
import xyz.itwill09.mapper.FileBoardMapper;

@Repository
@RequiredArgsConstructor
public class FileBoardDAOImpl implements FileBoardDAO {
    private final SqlSession sqlSession;

    @Override
    public int insertFileBoard(FileBoard board) {
        return sqlSession.getMapper(FileBoardMapper.class).insertFileBoard(board);
    }

    @Override
    public int deleteFileBoard(int num) {
        return sqlSession.getMapper(FileBoardMapper.class).deleteFileBoard(num);
    }

    @Override
    public FileBoard selectFileBoard(int num) {
        return sqlSession.getMapper(FileBoardMapper.class).selectFileBoard(num);
    }

    @Override
    public int selectFileBoardCount() {
        return sqlSession.getMapper(FileBoardMapper.class).selectFileBoardCount();
    }

    @Override
    public List<FileBoard> selectFileBoardList(Map<String, Object> map) {
        return sqlSession.getMapper(FileBoardMapper.class).selectFileBoardList(map);
    }
}
```

- **`insertFileBoard(FileBoard board)`**: Uses MyBatis to insert a new `FileBoard` entry into the database.
- **`deleteFileBoard(int num)`**: Uses MyBatis to delete a `FileBoard` entry by its `num`.
- **`selectFileBoard(int num)`**: Uses MyBatis to retrieve a single `FileBoard` entry by its `num`.
- **`selectFileBoardCount()`**: Uses MyBatis to get the total number of `FileBoard` entries.
- **`selectFileBoardList(Map<String, Object> map)`**: Uses MyBatis to retrieve a list of `FileBoard` entries based on the parameters in the `map`, typically for pagination.

Here is the English translation for the `FileBoardService` interface and its implementation `FileBoardServiceImpl`:

### `FileBoardService` Interface

```java
package xyz.itwill09.service;

import java.util.Map;

import xyz.itwill09.dto.FileBoard;

public interface FileBoardService {
    // Add a new file board entry
    void addFileBoard(FileBoard fileBoard);

    // Remove a file board entry by its number
    void removeFileBoard(int num);

    // Retrieve a file board entry by its number
    FileBoard getFileBoard(int num);

    // Get a list of file board entries with pagination
    Map<String, Object> getFileBoardList(int pageNum, int pageSize);
}
```

### `FileBoardServiceImpl` Implementation

```java
package xyz.itwill09.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dao.FileBoardDAO;
import xyz.itwill09.dto.FileBoard;
import xyz.itwill09.util.Pager;

@Service
@RequiredArgsConstructor
public class FileBoardServiceImpl implements FileBoardService {
    private final FileBoardDAO fileBoardDAO;

    @Transactional
    @Override
    public void addFileBoard(FileBoard fileBoard) {
        fileBoardDAO.insertFileBoard(fileBoard);
    }

    @Transactional
    @Override
    public void removeFileBoard(int num) {
        if (fileBoardDAO.selectFileBoard(num) == null) {
            throw new RuntimeException("Cannot find the file board entry to delete.");
        }
        
        fileBoardDAO.deleteFileBoard(num);
    }

    @Override
    public FileBoard getFileBoard(int num) {
        FileBoard fileBoard = fileBoardDAO.selectFileBoard(num);
        
        if (fileBoard == null) {
            throw new RuntimeException("Cannot find the file board entry.");
        }
        
        return fileBoard;
    }

    // This method receives the page number and page size as parameters and retrieves
    // the list of file board entries for the corresponding page number from the FILE_BOARD table.
    // It returns a Map containing the file board list and paging-related information.
    @Override
    public Map<String, Object> getFileBoardList(int pageNum, int pageSize) {
        // Get the total number of rows in the FILE_BOARD table
        int totalSize = fileBoardDAO.selectFileBoardCount();
        
        // Set the number of page numbers displayed in one block to a specific value
        int blockSize = 5;
        
        Pager pager = new Pager(pageNum, pageSize, totalSize, blockSize);
        
        // Create a Map to be used as a parameter for the selectFileBoardList() method
        // The Map contains the starting and ending row numbers
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("startRow", pager.getStartRow());
        pageMap.put("endRow", pager.getEndRow());
        List<FileBoard> fileBoardList = fileBoardDAO.selectFileBoardList(pageMap);
        
        // Create a result Map to be returned from the method
        // This Map will be stored as a Request Scope attribute to be provided to the view
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("pager", pager);
        resultMap.put("fileBoardList", fileBoardList);
        
        return resultMap;
    }
}
```

### Explanation:
- **`addFileBoard(FileBoard fileBoard)`**: Uses `FileBoardDAO` to insert a new `FileBoard` entry into the database.
- **`removeFileBoard(int num)`**: Checks if a `FileBoard` entry exists by its `num` and deletes it if found.
- **`getFileBoard(int num)`**: Retrieves a single `FileBoard` entry by its `num`, throwing an exception if not found.
- **`getFileBoardList(int pageNum, int pageSize)`**: Retrieves a paginated list of `FileBoard` entries, using the `Pager` utility to handle pagination and returns a map with the results and pagination details.