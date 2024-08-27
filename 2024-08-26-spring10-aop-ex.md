---
layout: single
title: 2024-08-26-Spring10-aop-EX
---
## AOP with Spring -bean(xml)


```java
package xyz.itwill06.aop;

import lombok.Data;

@Data
public class Hewon {
    private int num;
    private String name;
}
===================================================================================================
package xyz.itwill06.aop;

import java.util.List;

public interface HewonDAO {
    int insertHewon(Hewon hewon);
    Hewon selectHewon(int num);
    List<Hewon> selectHewonList();
}
===================================================================================================
package xyz.itwill06.aop;

import java.util.List;

// Core Concern Module: Class where methods with core concerns are defined
public class HewonDAOImpl implements HewonDAO {

    @Override
    public int insertHewon(Hewon hewon) {
        System.out.println("*** HewonDAOImpl class's insertHewon(Hewon hewon) method called ***");
        return 0;
    }

    @Override
    public Hewon selectHewon(int num) {
        System.out.println("*** HewonDAOImpl class's selectHewon(int num) method called ***");
        return null;
    }

    @Override
    public List<Hewon> selectHewonList() {
        System.out.println("*** HewonDAOImpl class's selectHewonList() method called ***");
        return null;
    }

}
===================================================================================================
package xyz.itwill06.aop;

import java.util.List;

public interface HewonService {
    void addHewon(Hewon hewon);
    Hewon getHewon(int num);
    List<Hewon> getHewonList();
}

package xyz.itwill06.aop;

import java.util.List;

import lombok.Setter;

public class HewonServiceImpl implements HewonService {
    @Setter
    private HewonDAO hewonDAO;

    @Override
    public void addHewon(Hewon hewon) {
        System.out.println("*** HewonServiceImpl addHewon(Hewon hewon) method ***");
        hewonDAO.insertHewon(hewon);
    }

    @Override
    public Hewon getHewon(int num) {
        System.out.println("*** HewonServiceImpl getHewon(int num) method ***");
        return hewonDAO.selectHewon(num);
    }

    @Override
    public List<Hewon> getHewonList() {
        System.out.println("*** HewonServiceImpl getHewonList() method ***");
        return hewonDAO.selectHewonList();
    }

}
===================================================================================================
package xyz.itwill06.aop;

import org.aspectj.lang.ProceedingJoinPoint;

// Cross-Cutting Concern Module: Class where methods for cross-cutting concerns are defined - Advice Class
// => Cross-cutting concerns: Commands providing auxiliary functions excluding data processing commands
// => Examples include logging, security (authorization), transaction handling, exception handling
public class HewonAdvice {
    // Method to be executed before or after the target method of the core concern module
    // => PointCut Language: Language used to specify target methods in the core concern module
    
    // Method to be executed before the core concern code of the target method is run
    // => Before Advice Method
    // => JoinPoint: Location where cross-cutting concern code is inserted based on the core concern code
    public void beforeLog() {
        System.out.println("### [before] Cross-cutting concern code to be executed before core concern code ###");
    }
    
    // Method to be executed after the core concern code of the target method has executed successfully
    // => After Returning Advice Method
    public void afterReturningLog() {
        System.out.println("### [after-returning] Cross-cutting concern code to be executed after core concern code successfully executes ###");
    }

    // Method to be executed after an exception occurs during the execution of the core concern code of the target method
    // => After Throwing Advice Method
    public void afterThrowingLog() {
        System.out.println("### [after-throwing] Cross-cutting concern code to be executed after an exception occurs during core concern code execution ###");
    }

    // Method to be executed unconditionally after the core concern code of the target method has executed
    // => After Advice Method
    public void afterLog() {
        System.out.println("### [after] Cross-cutting concern code to be executed unconditionally after core concern code execution ###");
    }
    
    // Method to be executed before and after the core concern code of the target method
    // => Around Advice Method
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("### [around] Cross-cutting concern code to be executed before the core concern code ###");
        Object object = joinPoint.proceed();
        System.out.println("### [around] Cross-cutting concern code to be executed after the core concern code ###");
        return object;
    }
}
===================================================================================================
package xyz.itwill06.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HewonApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("06-1_aop.xml");
        HewonService service = context.getBean("hewonService", HewonService.class);
        System.out.println("=============================================================");
        service.addHewon(null);
        System.out.println("=============================================================");
        service.getHewon(0);        
        System.out.println("=============================================================");
        service.getHewonList();
        System.out.println("=============================================================");
        ((ClassPathXmlApplicationContext)context).close();    
    }
}
```

### Summary of Translated Components:

1. **`Hewon` Class**:
   - A data class representing an entity with `num` and `name` properties.

2. **`HewonDAO` Interface**:
   - Defines methods for basic CRUD operations related to `Hewon`.

3. **`HewonDAOImpl` Class**:
   - Implements the `HewonDAO` interface with core concern methods.

4. **`HewonService` Interface**:
   - Defines service layer methods for interacting with `Hewon` objects.

5. **`HewonServiceImpl` Class**:
   - Implements the `HewonService` interface and uses `HewonDAO` to perform operations.

6. **`HewonAdvice` Class**:
   - Contains methods for cross-cutting concerns (advice), including before, after, and around advice.

7. **`HewonApp` Class**:
   - The main application class that initializes the Spring context and demonstrates the usage of the service methods.

#### XML (Bean)


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- Register core concern modules (DAO and Service classes) as Spring Beans - Dependency Injection -->
    <bean class="xyz.itwill06.aop.HewonDAOImpl" id="hewonDAO"/>
    <bean class="xyz.itwill06.aop.HewonServiceImpl" id="hewonService">
        <property name="hewonDAO" ref="hewonDAO"/>
    </bean>
    
    <!-- Register cross-cutting concern modules (Advice classes) as Spring Beans -->
    <bean class="xyz.itwill06.aop.HewonAdvice" id="hewonAdvice"/>
    
    <!-- Spring AOP (Aspect Oriented Programming) : Provides functionality for weaving core concerns and cross-cutting concerns together at runtime using AspectJ compiler -->
    <!-- => To use Spring AOP functionality, aspectjrt and aspectjweaver libraries must be included in the project build - Typically specified in pom.xml -->
    <!-- => To implement Spring AOP functionality, use XML elements in the Spring Bean Configuration File or AOP-related annotations for configuration -->
    
    <!-- To implement Spring AOP functionality using XML configuration, add the aop namespace and provide the spring-aop.xsd file -->
    <!-- config: Element to provide information for implementing Spring AOP functionality -->
    <!-- => Sub-elements: advisor element, aspect element, pointcut element -->
    <aop:config>
        <!-- aspect: Element for configuring where to insert cross-cutting concerns into core concerns -->
        <!-- => Sub-elements: before element, after-returning element, after-throwing element, after element, around element -->
        <!-- ref attribute: Specifies the identifier (beanName) of the cross-cutting concern module (Advice class) -->
        <!-- => Provides the object for invoking cross-cutting concern methods -->
        <aop:aspect ref="hewonAdvice">
            <!-- before: Element to configure cross-cutting concerns to be inserted before the execution of core concern code -->
            <!-- method attribute: Specifies the method name in the cross-cutting concern module (Advice class) -->
            <!-- pointcut attribute: Specifies the target methods in the core concern module's Spring Beans where cross-cutting concerns are inserted -->
            <!-- Pointcut Expression: Method of specifying target methods using Pointcut Language -->
            <!-- => Pointcut Expression: Use pattern characters for specifying target methods among classes - Operators can be used for target method specification -->
            <!-- Pointcut Expression Pattern Characters: .. (zero or more), * (one or more), ? (zero or one) -->
            <!-- Pointcut Language Operators: !(Not), && (And), || (Or) -->

            <!-- Using execution modifier to specify target methods -->
            <!-- => Use the method signature to specify target methods -->
            <!-- Format: execution([access modifier] return type [package.ClassName.]methodName(parameter type, parameter type, ...)) -->
            <!-- => Omitting [package.ClassName] specifies methods in classes registered as Spring Beans -->
            <!-- => Using interfaces instead of classes specifies methods in all child classes implementing the interface -->
            <!-- => If return type or parameter type is a class (interface), express with package included -->			
            <!-- <aop:before method="beforeLog" pointcut="execution(void addHewon(xyz.itwill06.aop.Hewon))"/> -->
            <!-- Target all methods in classes registered as Spring Beans -->
            <!-- <aop:before method="beforeLog" pointcut="execution(* *(..))"/> -->
            <!-- Target methods with at least one parameter in classes registered as Spring Beans -->
            <!-- <aop:before method="beforeLog" pointcut="execution(* *(*))"/> -->
            <!-- Target methods in classes within a specific package (excluding sub-packages) registered as Spring Beans -->
            <!-- <aop:before method="beforeLog" pointcut="execution(* xyz.itwill06.aop.*(..))"/> -->
            <!-- Target methods in classes within a specific package (including sub-packages) registered as Spring Beans -->
            <!-- <aop:before method="beforeLog" pointcut="execution(* xyz.itwill06.aop..*(..))"/> -->
            <!-- Target methods in a specific class registered as a Spring Bean -->
            <!-- <aop:before method="beforeLog" pointcut="execution(* xyz.itwill06.aop.HewonDAOImpl.*(..))"/> -->
            <!-- Target methods in classes implementing a specific interface registered as a Spring Bean -->
            <!-- <aop:before method="beforeLog" pointcut="execution(* xyz.itwill06.aop.HewonDAO.*(..))"/> -->
            <!-- <aop:before method="beforeLog" pointcut="execution(* xyz.itwill06.aop.Hewon*.*(..))"/>  -->
            <!-- <aop:before method="beforeLog" pointcut="execution(* get*(..))"/> -->
            <!-- <aop:before method="beforeLog" pointcut="!execution(* get*(..))"/> -->
            <!-- <aop:before method="beforeLog" pointcut="execution(* *(int)) or execution(int *(..))"/> -->
            
            <!-- Using within modifier to specify target methods -->
            <!-- => Target all methods in classes registered as Spring Beans -->
            <!-- Format: within(packageName.ClassName) -->
            <!-- => Can use pattern characters with within modifier but cannot use interfaces -->
            <!-- <aop:before method="beforeLog" pointcut="within(xyz.itwill06.aop.HewonDAOImpl)"/> --> 
            <!-- <aop:before method="beforeLog" pointcut="within(xyz.itwill06.aop.*)"/>  -->
            
            <!-- Using bean modifier to specify target methods -->
            <!-- => Target all methods in classes registered as Spring Beans -->
            <!-- => bean modifier uses the bean identifier (beanName) to specify target methods -->
            <!-- Format: bean(beanName) -->
            <!-- <aop:before method="beforeLog" pointcut="bean(hewonDAO)"/> --> 
            <!-- <aop:before method="beforeLog" pointcut="bean(hewonService*)"/> -->
            
            <!-- pointcut: Element to specify information about target methods for inserting cross-cutting concerns -->
            <!-- => Used to store commonly used Pointcut Expressions and provide them to sub-elements of the aspect element -->
            <!-- => Can be defined locally within aspect element or globally outside -->
            <!-- expression attribute: Specifies modifiers for target method selection -->
            <!-- id attribute: Identifies the pointcut element -->
            <aop:pointcut expression="execution(* xyz.itwill06.aop.HewonDAO.*(..))" id="HewonDAOPointcut"/>			
            <aop:pointcut expression="execution(* xyz.itwill06.aop.HewonService.*(..))" id="HewonServicePointcut"/>
            
            <!-- pointcut-ref attribute: Specifies the identifier (id attribute value) of the pointcut element -->
            <!-- => Uses the information provided by pointcut element to specify target methods -->
            <!-- <aop:before method="beforeLog" pointcut-ref="HewonDAOPointcut"/> -->
            <aop:before method="beforeLog" pointcut-ref="HewonServicePointcut"/>
                
            <!-- after-returning: Element to configure cross-cutting concerns to be inserted after the successful execution of target method (without exceptions) -->
            <aop:after-returning method="afterReturningLog" pointcut-ref="HewonServicePointcut"/>
            
            <!-- after-throwing: Element to configure cross-cutting concerns to be inserted after an exception occurs during the execution of the target method -->
            <aop:after-throwing method="afterThrowingLog" pointcut-ref="HewonServicePointcut"/>
    
            <!-- after: Element to configure cross-cutting concerns to be inserted unconditionally after the execution of the target method -->
            <aop:after method="afterLog" pointcut-ref="HewonServicePointcut"/>			
			
            <!-- around: Element to configure cross-cutting concerns to be inserted before and after the execution of the target method -->
            <aop:around method="aroundLog" pointcut-ref="HewonServicePointcut"/>			
        </aop:aspect>
    </aop:config>
</beans>
```

### Summary of Translated Components:

1. **Bean Registration**:
   - Register `HewonDAOImpl` and `HewonServiceImpl` classes as Spring Beans with dependency injection.
   - Register `HewonAdvice` class as a Spring Bean for cross-cutting concerns.

2. **Spring AOP Configuration**:
   - Define the `aop` namespace and use `spring-aop.xsd` for AOP configuration.
   - Configure aspects to define where cross-cutting concerns (advice) should be applied:
     - **Before Advice**: Executed before the target method.
     - **After Returning Advice**: Executed after the target method executes successfully.
     - **After Throwing Advice**: Executed after an exception occurs during the execution of the target method.
     - **After Advice**: Executed unconditionally after the target method.
     - **Around Advice**: Executed before and after the target method execution.
   - Define pointcuts to specify which methods should have the