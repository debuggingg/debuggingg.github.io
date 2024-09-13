---
layout: single
title: 2024-09-05-spring25-mybatis-ex-point
---
#### pom.xml
- Install TransactionManager: If spring-jdbc is already included in pom.xml, no additional build is required
#### root-context.xm

```xml

<!-- Configuration in root-context.xml -->

<!-- Register the DataSourceTransactionManager class as a Spring Bean -->
<!-- => The bean's identifier (beanName) must be set to [transactionManager] -->
<!-- => Inject the DataSource object into the transactionManager bean using setter injection -->
<bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>
```

#### sevlet-context.xml

-  In servlet-context.xml, first configure the tx namespace, then set up tx:advice

```xml


<!-- To manage transactions using the TransactionManager, configure the tx namespace by providing the spring-tx.xsd file -->
<!-- advice: Element to create an Advisor using the TransactionManager -->
<!-- Advisor: A class with cross-cutting concerns (Advice) that is applied at specific join points -->
<!-- id attribute: Identifier for the advice element -->
<!-- transaction-manager attribute: The identifier (beanName) of the Spring Bean providing TransactionManager functionality -->
<!-- => The TransactionManager commits on success and rolls back on failure -->
<!-- attributes: Element to define the methods managed by the TransactionManager for commit or rollback -->
<!-- method: Element to set the method names and transaction management policies -->
<!-- => Method names can use the [*] symbol -->
<!-- name attribute: Name of the methods to be managed by the TransactionManager -->
<!-- rollback-for attribute: Exceptions that should trigger a rollback -->
<!-- read-only attribute: Set to either false (default) or true (no rollback needed) -->
<tx:advice id="txAdvisor" transaction-manager="transactionManager">
    <tx:attributes>
        <tx:method name="add*" rollback-for="Exception"/>
        <tx:method name="modify*" rollback-for="Exception"/>
        <tx:method name="remove*" rollback-for="Exception"/>
        <tx:method name="get*" read-only="true"/>
    </tx:attributes>
</tx:advice>

<!-- To use Spring AOP features, configure the aop namespace by providing the spring-aop.xsd file -->
<!-- advisor: Element to use the Advisor -->
<!-- advice-ref attribute: Identifier of the advice element that created the Advisor -->
<aop:config>
    <aop:advisor advice-ref="txAdvisor" pointcut="execution(* xyz.itwill09.service..*(..))"/>
</aop:config>
```

### Summary of Key Points

1. **TransactionManager Configuration**:
   - Registers `DataSourceTransactionManager` as a Spring Bean.
   - The bean is identified by `transactionManager`, and the `dataSource` property is injected.

2. **`tx` Namespace Configuration**:
   - Configures transaction management by setting up `tx:advice`.
   - Specifies methods to be managed by the `TransactionManager` using attributes like `name`, `rollback-for`, and `read-only`.

3. **`aop` Namespace Configuration**:
   - Configures AOP to apply transaction advice to methods in the specified package using `aop:advisor` and `pointcut`.


---
### PointUser.java DTO 
```java
package xyz.itwill09.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointUser {
    private String id;
    private String name;
    private int point;
}
```
### PointUserDAO.java
```java
package xyz.itwill09.dao;

import xyz.itwill09.dto.PointUser;

public interface PointUserDAO {
    int insertPointUser(PointUser user);
    int updatePlusPointUser(String id);
    int updateMinusPointUser(String id);
    PointUser selectPointUser(String id);
}
```
### PointUserDAOImpl.java
```java
package xyz.itwill09.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.PointUser;
import xyz.itwill09.mapper.PointUserMapper;

@Repository
@RequiredArgsConstructor
public class PointUserDAOImpl implements PointUserDAO {
    
    private final SqlSession sqlSession;
    
    @Override
    public int insertPointUser(PointUser user) {
        return sqlSession.getMapper(PointUserMapper.class).insertPointUser(user);
    }

    @Override
    public int updatePlusPointUser(String id) {
        return sqlSession.getMapper(PointUserMapper.class).updatePlusPointUser(id);
    }

    @Override
    public int updateMinusPointUser(String id) {
        return sqlSession.getMapper(PointUserMapper.class).updateMinusPointUser(id);
    }

    @Override
    public PointUser selectPointUser(String id) {
        return sqlSession.getMapper(PointUserMapper.class).selectPointUser(id);
    }
}
```
### PointUserService.java
```java
package xyz.itwill09.service;

import xyz.itwill09.dto.PointUser;

public interface PointUserService {
    PointUser addPointUser(PointUser user);
}
```
### PointUserServiceImpl.java
```java
package xyz.itwill09.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dao.PointUserDAO;
import xyz.itwill09.dto.PointUser;

@Service
@RequiredArgsConstructor
public class PointUserServiceImpl implements PointUserService {
    private final PointUserDAO pointUserDAO;
    
    // Receives user information as a parameter, inserts it into the POINT_USER table,
    // and returns the inserted user's information after retrieving it.
    public PointUser addPointUser(PointUser user) {
        // Manually throw an exception if the user ID already exists.
        if (pointUserDAO.selectPointUser(user.getId()) != null) {
            throw new RuntimeException("The ID is already in use.");
        }
        pointUserDAO.insertPointUser(user);
        return pointUserDAO.selectPointUser(user.getId());
    }
}
```

### Summary
- **PointUser**: A DTO class representing a user with fields for ID, name, and points.
- **PointUserDAO**: An interface defining CRUD operations for `PointUser`.
- **PointUserDAOImpl**: Implementation of the `PointUserDAO` interface using MyBatis.
- **PointUserService**: Interface for services related to `PointUser`.
- **PointUserServiceImpl**: Implementation of `PointUserService`, handling business logic and user management.
#### PointUserServiceTest.java Test
```java
package xyz.itwill.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.slf4j.Slf4j;
import xyz.itwill09.dto.PointUser;
import xyz.itwill09.service.PointUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@Slf4j
public class PointUserServiceTest {
    @Autowired
    private PointUserService pointUserService;
    
    @Test
    public void testAddPointUser() {
        PointUser user = PointUser.builder().id("abc123").name("Hong Gil-dong").build();
        // PointUser user = PointUser.builder().id("xyz789").name("Im Ggeok-jeong").build();
        
        PointUser addUser = pointUserService.addPointUser(user);
        
        log.info(addUser.toString());
    }
}
```

#### PointBoard.java DTO


```java
package xyz.itwill09.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointBoard {
    private int num;
    private String writer;
    private String content;
}
```
#### PointBoardDAO.java
```java
package xyz.itwill09.dao;

import java.util.List;
import xyz.itwill09.dto.PointBoard;

public interface PointBoardDAO {
    int insertPointBoard(PointBoard board);
    int deletePointBoard(int num);
    PointBoard selectPointBoard(int num);
    List<PointBoard> selectPointBoardList();
}
```

#### PointBoardDAOImpl.java
```java
package xyz.itwill09.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.PointBoard;
import xyz.itwill09.mapper.PointBoardMapper;

@Repository
@RequiredArgsConstructor
public class PointBoardDAOImpl implements PointBoardDAO {
    private final SqlSession sqlSession;

    @Override
    public int insertPointBoard(PointBoard board) {
        return sqlSession.getMapper(PointBoardMapper.class).insertPointBoard(board);
    }

    @Override
    public int deletePointBoard(int num) {
        return sqlSession.getMapper(PointBoardMapper.class).deletePointBoard(num);
    }

    @Override
    public PointBoard selectPointBoard(int num) {
        return sqlSession.getMapper(PointBoardMapper.class).selectPointBoard(num);
    }

    @Override
    public List<PointBoard> selectPointBoardList() {
        return sqlSession.getMapper(PointBoardMapper.class).selectPointBoardList();
    }
}
```

#### PointBoardService.java 
```java
package xyz.itwill09.service;

import java.util.List;
import xyz.itwill09.dto.PointBoard;
import xyz.itwill09.dto.PointUser;

public interface PointBoardService {
    PointUser addPointBoard(PointBoard board);
    PointUser removePointBoard(int num);
    List<PointBoard> getPointBoardList();
}
```
#### PointBoardServiceImpl.java
```java
package xyz.itwill09.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import xyz.itwill09.dao.PointBoardDAO;
import xyz.itwill09.dao.PointUserDAO;
import xyz.itwill09.dto.PointBoard;
import xyz.itwill09.dto.PointUser;

@Service
@RequiredArgsConstructor
public class PointBoardServiceImpl implements PointBoardService {
    private final PointUserDAO pointUserDAO;
    private final PointBoardDAO pointBoardDAO;
    
    // Method to add a point board entry
    // This method inserts the board entry into the POINT_BOARD table and then 
    // fetches the member info from the POINT_USER table based on the writer.
    // It also updates the POINT column value of the writer in the POINT_USER table.
    @Override
    public PointUser addPointBoard(PointBoard board) {
        pointBoardDAO.insertPointBoard(board);
        
        // Throw an exception if the writer's information is not found in the POINT_USER table
        if(pointUserDAO.selectPointUser(board.getWriter()) == null) {
            throw new RuntimeException("The writer does not exist.");
        }
        
        pointUserDAO.updatePlusPointUser(board.getWriter());
        
        return pointUserDAO.selectPointUser(board.getWriter());
    }

    // Method to remove a point board entry
    // This method deletes the board entry from the POINT_BOARD table and then 
    // fetches the member info from the POINT_USER table based on the writer.
    // It also updates the POINT column value of the writer in the POINT_USER table.
    @Override
    public PointUser removePointBoard(int num) {
        PointBoard board = pointBoardDAO.selectPointBoard(num);
        
        if(board == null) {
            throw new RuntimeException("The board entry does not exist.");
        }
        
        pointBoardDAO.deletePointBoard(num);
        
        if(pointUserDAO.selectPointUser(board.getWriter()) == null) {
            throw new RuntimeException("The writer does not exist.");
        }
        
        pointUserDAO.updateMinusPointUser(board.getWriter());
        
        return pointUserDAO.selectPointUser(board.getWriter());
    }

    // Method to get a list of all point board entries
    @Override
    public List<PointBoard> getPointBoardList() {
        return pointBoardDAO.selectPointBoardList();
    }
}
```

### Explanation:
- **`PointBoard` class**: A DTO (Data Transfer Object) representing a point board entry with fields for number, writer, and content.
- **`PointBoardDAO` interface**: Defines methods for interacting with the database to manage point board entries, such as inserting, deleting, selecting, and listing point board entries.
- **`PointBoardDAOImpl` class**: Implements the `PointBoardDAO` interface using MyBatis for data access, performing database operations on point board entries.
- **`PointBoardService` interface**: Defines service-level methods for managing point board entries, including adding, removing, and listing entries.
- **`PointBoardServiceImpl` class**: Implements the `PointBoardService` interface, providing the business logic for managing point board entries and interacting with `PointBoardDAO` and `PointUserDAO`. It handles adding and removing point board entries and updates the associated user's points accordingly.
#### PointBoardServiceTest.java Test


```java
package xyz.itwill.spring;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.slf4j.Slf4j;
import xyz.itwill09.dto.PointBoard;
import xyz.itwill09.dto.PointUser;
import xyz.itwill09.service.PointBoardService;

// How to manage transactions using TransactionManager in a web application with Spring MVC
// 1. Include the spring-tx library in the project build - Maven: pom.xml
// => If the spring-jdbc library is included in the project, it will be automatically included due to dependency management
// 2. Register TransactionManager-related classes as Spring Beans in the Spring Bean Configuration File (root-context.xml)
// 3. Configure Spring AOP for transaction management in the Spring Bean Configuration File (servlet-context.xml)

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class PointBoardServiceTest {
    @Autowired
    private PointBoardService pointBoardService;
    
    @Test
    public void test1() {
        //PointBoard board = PointBoard.builder().writer("abc123").content("Test-1").build();
        PointBoard board = PointBoard.builder().writer("xyz789").content("Test-2").build();
        
        // Call addPointBoard() method on the PointBoardService object to insert a row into the POINT_BOARD table
        // => This will increase the POINT column value of the writer's row in the POINT_USER table
        // => It will then fetch the member information for the writer from the POINT_USER table
        // => An exception will be thrown if the writer's information is not stored as a row in the POINT_USER table
        // Issue: Before the exception occurs, the SQL command for inserting the board is already executed, leading to an abnormal row insertion in the POINT_BOARD table
        // Solution: Ensure that all SQL commands (INSERT, UPDATE, DELETE) executed before an exception are rolled back
        // => Spring Framework provides consistent transaction management through TransactionManager
        PointUser user = pointBoardService.addPointBoard(board);
        log.info(user.toString());
        log.info(pointBoardService.getPointBoardList().toString());
    }
    
    /*
    @Test
    public void test2() {
        // Call removePointBoard() method on the PointBoardService object to delete a row from the POINT_BOARD table
        // => This will decrease the POINT column value of the writer's row in the POINT_USER table
        // => It will then fetch the member information for the writer from the POINT_USER table
        // => An exception will be thrown if the board does not exist or if the writer's information is not stored as a row in the POINT_USER table
        PointUser user = pointBoardService.removePointBoard(1);
        log.info(user.toString());
        log.info(pointBoardService.getPointBoardList().toString());
    }
    */
}
```

### Explanation:
- **Class Annotation**: The `@RunWith`, `@WebAppConfiguration`, `@ContextConfiguration`, and `@FixMethodOrder` annotations are used for configuring and running the JUnit test with Spring support and for specifying the order of test method execution.
- **Test Method `test1()`**: Tests the `addPointBoard()` method of the `PointBoardService` to insert a board entry and update the corresponding user's points. It logs the result and the current list of point boards. Issues and solutions regarding transaction management are discussed.
- **Test Method `test2()`**: (Commented out) Intended to test the `removePointBoard()` method for deleting a board entry and updating the corresponding user's points. It also logs the result and the updated list of point boards.
