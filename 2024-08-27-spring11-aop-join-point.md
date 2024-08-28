---
layout: single
title: 2024-08-27-spring11-aop-joinPoint
---
## AOP JoinPoint



### `JoinPointBean.java`:
```java
package xyz.itwill06.aop;

// Core Concern Module
public class JoinPointBean {
    public void add() {
        System.out.println("*** Called add() method of JoinPointBean class ***");
    }
    
    public void modify(int num, String name) {
        System.out.println("*** Called modify(int num, String name) method of JoinPointBean class ***");
    }
    
    public void remove(int num) {
        System.out.println("*** Called remove(int num) method of JoinPointBean class ***");
    }
    
    public String getName() {
        System.out.println("*** Called getName() method of JoinPointBean class ***");
        return "Hong Gil-dong";
    }
    
    public void calc(int num1, int num2) {
        System.out.println("*** Called calc(int num1, int num2) method of JoinPointBean class ***");
        System.out.println("Quotient = " + (num1 / num2));
    }
}
```

### `JoinPointAdvice.java`:
```java
package xyz.itwill06.aop;

import org.aspectj.lang.JoinPoint;

// Cross-Cutting Concern Module
public class JoinPointAdvice {
    // Except for Around Advice Method, other Advice Methods are generally written with a [void] return type
    // and may have no parameters or use JoinPoint interface as the parameter type
    // => If Advice methods in the Advice class are written incorrectly, IllegalArgumentException will be thrown
    // JoinPoint Object: Contains information about the target method
    // => When the Advice class's method is called by the AspectJ compiler, the JoinPoint object containing information about the target method is passed to the method
    // => If the Advice class's method requires information about the target method, it can use the JoinPoint object by including it as a parameter and call its methods
    public void beforeDisplay(JoinPoint joinPoint) { // Before Advice Method
        // System.out.println("### [before] Cross-cutting concern code to be inserted and executed before core concern code");
        
        // joinPoint.getTarget(): Method to return the object (core concern module - Spring Bean) that called the target method as an Object
        // Object.getClass(): Method to return the Class object containing information about the class that created the object
        // Class.getName(): Method to return the name of the Class object (including package) as a String
        // System.out.println(joinPoint.getTarget().getClass().getName());
        
        // Class.getSimpleName(): Method to return the name of the Class object (excluding package) as a String
        // System.out.println(joinPoint.getTarget().getClass().getSimpleName());
        
        // joinPoint.getSignature(): Method to return a Signature object containing information about the target method
        // Signature.getName(): Method to return the name of the target method
        // System.out.println(joinPoint.getSignature().getName());
        
        // joinPoint.getArgs(): Method to return all parameter values (objects) of the target method as an Object array
        // System.out.println(joinPoint.getArgs());
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] params = joinPoint.getArgs();
        
        System.out.print("### [before] Method " + methodName + " of class " + className + "(");
        for (int i = 0; i < params.length; i++) {
            System.out.print(params[i]);
            if (i < params.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(") called ###");
    }
    
    public void displayMessage(JoinPoint joinPoint) { // After Advice Method
        // System.out.println("### [after] Cross-cutting concern code to be inserted and executed unconditionally after core concern code");
        
        Object[] params = joinPoint.getArgs();
        System.out.println("[after] Deleted employee information with employee number " + params[0] + ".");
    }
}
```

### `JoinPointApp.java`:
```java
package xyz.itwill06.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JoinPointApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("06-2_param.xml");
        JoinPointBean bean = context.getBean("joinPointBean", JoinPointBean.class);
        System.out.println("=============================================================");
        bean.add();
        System.out.println("=============================================================");
        bean.modify(1000, "Hong Gil-dong");        
        System.out.println("=============================================================");
        bean.remove(2000);
        System.out.println("=============================================================");
        bean.getName();
        System.out.println("=============================================================");
        bean.calc(20, 10);
        System.out.println("=============================================================");
        ((ClassPathXmlApplicationContext)context).close();    
    }
}
```

### Summary:

1. **`JoinPointBean.java`**:
   - Defines core concern methods for different operations, such as `add`, `modify`, `remove`, `getName`, and `calc`.

2. **`JoinPointAdvice.java`**:
   - Implements cross-cutting concerns using AspectJ's `JoinPoint` to access information about the target methods.
   - Contains `beforeDisplay` method (before advice) and `displayMessage` method (after advice) to handle cross-cutting concerns before and after method execution.

3. **`JoinPointApp.java`**:
   - Configures the Spring application context and invokes methods on `JoinPointBean` to demonstrate AOP functionality.
#### XML(Bean)


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- Register the core concern module class as a Spring Bean -->
    <bean class="xyz.itwill06.aop.JoinPointBean" id="joinPointBean"/>

    <!-- Register the cross-cutting concern module class as a Spring Bean -->
    <bean class="xyz.itwill06.aop.JoinPointAdvice" id="joinPointAdvice"/>
    
    <aop:config>
        <aop:aspect ref="joinPointAdvice">
            <!-- Before advice: Executes before any method of any class -->
            <aop:before method="beforeDisplay" pointcut="execution(* *(..))"/>
            
            <!-- After advice: Executes after the remove(int) method of any class -->
            <aop:after method="displayMessage" pointcut="execution(* remove(int))"/>
        </aop:aspect>    
    </aop:config>
</beans>
```

### Summary of the Configuration:

1. **Bean Definitions**:
   - Registers `JoinPointBean` and `JoinPointAdvice` classes as Spring Beans with IDs `joinPointBean` and `joinPointAdvice`, respectively.

2. **AOP Configuration**:
   - Configures Aspect-Oriented Programming (AOP) settings using the `aop:config` element.
   - Defines an aspect using the `joinPointAdvice` bean.
     - **Before Advice**: `beforeDisplay` method is executed before any method (`execution(* *(..))`) of any class.
     - **After Advice**: `displayMessage` method is executed after the `remove(int)` method of any class.