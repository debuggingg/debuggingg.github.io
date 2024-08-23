---
layout: single
title: 2024-08-23-spring8-Annotation
---
#### 05-4_diAnnotation.xml


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- Component scanning configuration -->
    <!-- This will automatically detect and register Spring Beans in the specified package -->
    <context:component-scan base-package="xyz.itwill05.di"/>
</beans>
```


#### AnnotationStudentJdbcDAOImpl.java

```java
package xyz.itwill05.di;

import java.util.List;

import org.springframework.stereotype.Repository;

//@Component: An annotation used to register a class as a Spring Bean that can be managed by the Spring container.
// => The class name is automatically used as the identifier (beanName) with the first letter converted to lowercase.
// => The beanName can be changed using the value attribute of the @Component annotation.
// @Component
// @Primary: An annotation for dependency injection - used to specify which class to use when multiple classes are available for dependency injection.
// @Primary

//@Repository: An annotation used to register a DAO class (Repository class) as a Spring Bean that can be managed by the Spring container.
// => The class name is automatically used as the identifier (beanName), but it can be changed using the value attribute.
// => The class's methods can be rolled back in case of issues, managed by the transaction manager.
@Repository
public class AnnotationStudentJdbcDAOImpl implements StudentDAO {
    public AnnotationStudentJdbcDAOImpl() {
        System.out.println("### AnnotationStudentJdbcDAOImpl class's default constructor called ###");
    }
    
    @Override
    public int insertStudent(Student student) {
        System.out.println("*** AnnotationStudentJdbcDAOImpl class's insertStudent(Student student) method called ***");
        return 0;
    }

    @Override
    public int updateStudent(Student student) {
        System.out.println("*** AnnotationStudentJdbcDAOImpl class's updateStudent(Student student) method called ***");
        return 0;
    }

    @Override
    public int deleteStudent(int num) {
        System.out.println("*** AnnotationStudentJdbcDAOImpl class's deleteStudent(int num) method called ***");
        return 0;
    }

    @Override
    public Student selectStudent(int num) {
        System.out.println("*** AnnotationStudentJdbcDAOImpl class's selectStudent(int num) method called ***");
        return null;
    }

    @Override
    public List<Student> selectStudentList() {
        System.out.println("*** AnnotationStudentJdbcDAOImpl class's selectStudentList() method called ***");
        return null;
    }
}
```

#### AnnotationStudentMybatisDAOImpl.java


```java
package xyz.itwill05.di;

import java.util.List;

import org.springframework.stereotype.Repository;

//@Component("studentDAO")
//@Repository
public class AnnotationStudentMybatisDAOImpl implements StudentDAO {
    public AnnotationStudentMybatisDAOImpl() {
        System.out.println("### AnnotationStudentMybatisDAOImpl class's default constructor called ###");
    }
    
    @Override
    public int insertStudent(Student student) {
        System.out.println("*** AnnotationStudentMybatisDAOImpl class's insertStudent(Student student) method called ***");
        return 0;
    }

    @Override
    public int updateStudent(Student student) {
        System.out.println("*** AnnotationStudentMybatisDAOImpl class's updateStudent(Student student) method called ***");
        return 0;
    }

    @Override
    public int deleteStudent(int num) {
        System.out.println("*** AnnotationStudentMybatisDAOImpl class's deleteStudent(int num) method called ***");
        return 0;
    }

    @Override
    public Student selectStudent(int num) {
        System.out.println("*** AnnotationStudentMybatisDAOImpl class's selectStudent(int num) method called ***");
        return null;
    }

    @Override
    public List<Student> selectStudentList() {
        System.out.println("*** AnnotationStudentMybatisDAOImpl class's selectStudentList() method called ***");
        return null;
    }
}
```


#### AnnotationStudentServiceImpl.java

```java
package xyz.itwill05.di;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//@Component annotation with only the value attribute can set the property value directly
//@Component(value = "studentService")
//@Component("studentService")

// @Service: Annotation to register a service class as a Spring Bean that the Spring container can manage
// => The class name is automatically used as the identifier (beanName), but the value attribute can be used to change it
// => Allows handling exceptions using an ExceptionHandler
@Service
public class AnnotationStudentServiceImpl implements StudentService {
    
    // @Autowired: Annotation for automatic dependency injection of Spring Bean objects into fields
    // Dependency Injection at the field level using @Autowired annotation
    // => For multiple fields, @Autowired annotation is used on each field
    // => For autowiring byType, the same method is used if bean element's autowire attribute is set to [byType]
    // => Works like Setter Injection, but injection can occur even without setter methods
    
    // Instead of @Autowired, @Resource or @Inject annotations can also be used for dependency injection
    // => @Autowired is provided by Spring Framework, while @Resource or @Inject are from Java libraries
    // => @Resource or @Inject can be used with other frameworks besides Spring
    // @Resource: Works like autowiring byName if the bean element's autowire attribute is set to [byName]
    // @Inject: Works like autowiring byType if the bean element's autowire attribute is set to [byType]
    
    // Issue: When the field's data type is an interface and multiple implementations of the interface are registered as Spring Beans,
    // dependency injection fails and an exception occurs
    // Solution-1: Change the bean identifier (beanName) to match the field name
    // => Works like autowiring byName if multiple beans of the same type are present
    // Solution-2: Use @Primary annotation on the bean to indicate the primary bean to be injected
    // Solution-3: Use @Qualifier annotation on the field to specify the exact bean to be injected
    @Autowired
    //@Qualifier: Annotation to directly specify the object to be injected into the field
    // => Use the value attribute to set the object for dependency injection
    // => A dependent annotation of @Autowired
    // value attribute: Set the identifier (beanName) to differentiate beans
    // => Provides the object created with the identifier (beanName) to the field
    // => Can set only the property value if no other attributes are present
    @Qualifier("annotationStudentMybatisDAOImpl")
    private StudentDAO studentDAO;
    
    public AnnotationStudentServiceImpl() {
        System.out.println("### AnnotationStudentServiceImpl class's default constructor called ###");
    }
    
    @Override
    public void addStudent(Student student) {
        System.out.println("*** AnnotationStudentServiceImpl class's addStudent(Student student) method called ***");
        studentDAO.insertStudent(student);
    }

    @Override
    public void modifyStudent(Student student) {
        System.out.println("*** AnnotationStudentServiceImpl class's modifyStudent(Student student) method called ***");
        studentDAO.updateStudent(student);
    }

    @Override
    public void removeStudent(int num) {
        System.out.println("*** AnnotationStudentServiceImpl class's removeStudent(int num) method called ***");
        studentDAO.deleteStudent(num);
    }

    @Override
    public Student getStudent(int num) {
        System.out.println("*** AnnotationStudentServiceImpl class's getStudent(int num) method called ***");
        return studentDAO.selectStudent(num);
    }

    @Override
    public List<Student> getStudentList() {
        System.out.println("*** AnnotationStudentServiceImpl class's getStudentList() method called ***");
        return studentDAO.selectStudentList();
    }
}
```

#### AnnotationStudentApp.java

```java
package xyz.itwill05.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationStudentApp {
    public static void main(String[] args) {
        System.out.println("=============== Before Spring Container Initialization ===============");
        ApplicationContext context = new ClassPathXmlApplicationContext("05-4_diAnnotation.xml");
        System.out.println("=============== After Spring Container Initialization ===============");
        StudentService service = context.getBean("studentService", StudentService.class);
        service.addStudent(null);
        System.out.println("==========================================================");
        ((ClassPathXmlApplicationContext) context).close();
    }
}
```