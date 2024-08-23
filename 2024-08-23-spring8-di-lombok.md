---
layout: single
title: 2024-08-23-spring8-di-Lombok
---
#### 05-5_lombok.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- Component scanning configuration -->
    <!-- This will automatically detect and register Spring Beans in the specified package -->
    <context:component-scan base-package="xyz.itwill05.lombok"/>
</beans>
```

#### Member.java

```java
package xyz.itwill05.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// How to use the Lombok library that automatically generates constructors or methods for classes:
// 1. Include the Lombok library in the project's build process - Maven: pom.xml
// 2. Close Eclipse (STS3) and run the console (cmd) as an administrator. Navigate to the local repository folder 
// (user folder's .m2 folder) where the Lombok library file is stored.
// 3. Run the JAR program in the console to configure Lombok for use in Eclipse (STS3):
// => To run the JAR program: java -jar lombok-1.18.34.jar
// => Alternatively, double-click the Lombok library file in the local repository folder to execute it.
// 4. Run the JAR program and use the provided installer to select Eclipse (STS3) for Lombok installation.
// => Move to the Eclipse (STS3) installation folder, edit the eclipse.ini (STS.ini) file to save, and run Eclipse (STS3).
// => Change the [-javaagent:lombok.jar] entry in the eclipse.ini (STS.ini) file to use the relative path of the lombok.jar file.
// 5. Write classes in Eclipse (STS3) using the annotations provided by Lombok:
// => @NoArgsConstructor, @AllArgsConstructor, @RequiredArgsConstructor, @Setter, @Getter, @ToString, etc.

// @NoArgsConstructor: Provides a no-argument default constructor.
// @AllArgsConstructor: Provides a constructor with arguments for initializing all fields.
// @RequiredArgsConstructor: Provides a constructor with arguments only for fields marked as final.
// => If final fields are used, using @NoArgsConstructor will cause an error.
// @Setter: Provides setter methods for all fields in the class.
// => Applying @Setter to a field provides a setter method for that field.
// @Getter: Provides getter methods for all fields in the class.
// => Applying @Getter to a field provides a getter method for that field.
// @ToString: Provides an overridden toString() method for the class.
// => Combines and returns all field values as a string - useful for checking field values.
// @Data: Provides setter methods, getter methods, toString() method, equals() method, and hashCode() method for the class.
// => Used when declaring VO classes (classes designed to handle value comparisons).
// @Builder: Provides a Builder class and a builder() method for the class.
// => Builder class: Provides methods to set required values for fields when creating an object.
// => Using the Builder class's methods allows setting values for only the necessary fields, making initialization 
// easier and more flexible compared to constructors, and fields can be initialized in any order.
// => Useful for writing test programs.
// @Slf4j: Provides a log field for storing a Logger object capable of generating log events.

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Member {
    private String id;
    private String name;
    private String email;
}
```

#### MemberDAO interface

```java
package xyz.itwill05.lombok;

import java.util.List;

public interface MemberDAO {
    int insertMember(Member member);
    int updateMember(Member member);
    int deleteMember(String id);
    Member selectMember(String id);
    List<Member> selectMemberList();
}
```

#### MemberDAOImpl.java

```java
package xyz.itwill05.lombok;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MemberDAOImpl implements MemberDAO {
    public MemberDAOImpl() {
        log.info("### MemberDAOImpl class default constructor called ###");
    }
    
    @Override
    public int insertMember(Member member) {
        log.info("*** MemberDAOImpl class's insertMember(Member member) method called ***");
        return 0;
    }

    @Override
    public int updateMember(Member member) {
        log.info("*** MemberDAOImpl class's updateMember(Member member) method called ***");
        return 0;
    }

    @Override
    public int deleteMember(String id) {
        log.info("*** MemberDAOImpl class's deleteMember(String id) method called ***");
        return 0;
    }

    @Override
    public Member selectMember(String id) {
        log.info("*** MemberDAOImpl class's selectMember(String id) method called ***");
        return null;
    }

    @Override
    public List<Member> selectMemberList() {
        log.info("*** MemberDAOImpl class's selectMemberList() method called ***");
        return null;
    }
}
```

#### MemberService interface

```java
package xyz.itwill05.lombok;

import java.util.List;

public interface MemberService {
    void addMember(Member member);
    void modifyMember(Member member);
    void removeMember(String id);
    Member getMember(String id);
    List<Member> getMemberList();
}
```

#### MemberServiceImpl.java

```java
package xyz.itwill05.lombok;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@AllArgsConstructor : Annotation that provides a constructor with parameters to initialize all fields
//@RequiredArgsConstructor : Annotation that provides a constructor with parameters to initialize fields with the final modifier
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	// Field-level dependency injection using @Autowired to have the Spring container provide and inject the bean object
	// => Dependency injection without the need for a setter method
	// => Common method but can lead to StackOverflow if circular references occur
	//@Autowired
	//private MemberDAO memberDAO;
	
	//@RequiredArgsConstructor annotation initializes fields using a constructor with parameters
	// => Use final modifier for fields to be initialized
	// => Alternatively, use @NonNull annotation instead of final
	//private final MemberDAO memberDAO;
	
	//@NonNull : Annotation used to indicate fields for initialization with @RequiredArgsConstructor
	@NonNull
	private MemberDAO memberDAO;
	
	/*
	public MemberServiceImpl() {
		log.info("### Default constructor of MemberServiceImpl class called ###");
	}
	*/

	/*
	// Constructor-level dependency injection using @Autowired
	// => Prevents circular references by using constructor injection
	// => If only one constructor exists, @Autowired annotation can be omitted
	//@Autowired
	public MemberServiceImpl(MemberDAO memberDAO) {
		log.info("### Constructor with parameters of MemberServiceImpl class called ###");
		this.memberDAO = memberDAO;		
	}
	*/
	
	/*
	// Setter-level dependency injection using @Autowired on setter methods
	// => Allows changing dependencies if setter method is public
	@Autowired
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	*/

	@Override
	public void addMember(Member member) {
		log.info("*** addMember(Member member) method of MemberServiceImpl class called ***");
		memberDAO.insertMember(member);
	}

	@Override
	public void modifyMember(Member member) {
		log.info("*** modifyMember(Member member) method of MemberServiceImpl class called ***");
		memberDAO.updateMember(member);
	}

	@Override
	public void removeMember(String id) {
		log.info("*** removeMember(String id) method of MemberServiceImpl class called ***");
		memberDAO.deleteMember(id);
	}

	@Override
	public Member getMember(String id) {
		log.info("*** getMember(String id) method of MemberServiceImpl class called ***");
		return memberDAO.selectMember(id);
	}

	@Override
	public List<Member> getMemberList() {
		log.info("*** getMemberList() method of MemberServiceImpl class called ***");
		return memberDAO.selectMemberList();
	}

}
```


#### MemberServiceApp.java
```java
package xyz.itwill05.lombok;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MemberServiceApp {
	public static void main(String[] args) {
		System.out.println("=============== Before Spring Container Initialization ===============");
		ApplicationContext context = new ClassPathXmlApplicationContext("05-5_lombok.xml");
		System.out.println("=============== After Spring Container Initialization ===============");
		MemberService service = context.getBean("memberServiceImpl", MemberServiceImpl.class);
		service.addMember(null);
		service.modifyMember(null);
		service.removeMember(null);
		service.getMember(null);
		service.getMemberList();
		System.out.println("==========================================================");
		((ClassPathXmlApplicationContext)context).close();	
	}
}
```

#### MemberBuilderApp.java

```java
package xyz.itwill05.lombok;

public class MemberBuilderApp {
	public static void main(String[] args) {
		Member member1 = new Member("abc123", "Hong Gil-dong", "abc@itwill.xyz");
		
		System.out.println("ID = " + member1.getId());
		System.out.println("Name = " + member1.getName());
		System.out.println("Email = " + member1.getEmail());
		System.out.println("=============================================================");
		// When printing the reference variable of the Member object, the Member class's toString()
		// method is automatically called, returning a string representation
		// => The toString() method generated by the Lombok library combines all the field values
		// of the object into a string and returns it
		System.out.println(member1);
		System.out.println("=============================================================");
		// Class.builder() : A static method that creates and returns a Builder object using the 
		// Builder class (inner static class) declared in the class
		// => You can call methods on the Builder object to set values for the class's fields
		// Builder.build() : A method that creates and returns an object of the class (outer class)
		// using the values stored in the Builder object
		// => Initializes the object's fields using the values stored in the Builder object and returns it
		Member member2 = Member.builder()
				.id("xyz789")
				.name("Im Kkeok-jeong")
				.email("xyz@itwill.xyz")
				.build();
		
		System.out.println(member2);
		System.out.println("=============================================================");
	}
}
```