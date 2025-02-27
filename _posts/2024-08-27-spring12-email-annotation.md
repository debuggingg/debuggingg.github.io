---
layout: single
title: 2024-08-27-spring12-email-annotation
---
### ExecutionTimeAdvice.java



```java
package xyz.itwill06.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class ExecutionTimeAdvice {

    /*
    // Method to measure and record the execution time of the target method - Around Advice Method
    public Object timeWatchLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // Command to be executed before the core concern code of the target method
        long startTime = System.currentTimeMillis();

        // Execute the core concern code of the target method and store the result
        Object object = joinPoint.proceed();
        
        // Command to be executed after the core concern code of the target method
        long endTime = System.currentTimeMillis();
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println(className + " class's " + methodName + "() method execution time = "
                + (endTime - startTime) + "ms");
        
        return object;
    }
    */

    public Object timeWatchLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // StopWatch class: Provides functionality for time measurement
        StopWatch stopWatch = new StopWatch();
        
        // StopWatch.start(): Method to start time measurement
        stopWatch.start();
        
        Object object = joinPoint.proceed();
        
        // StopWatch.stop(): Method to stop time measurement
        stopWatch.stop();
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        // StopWatch.getTotalTimeMillis(): Method to return the measured time in milliseconds
        System.out.println(className + " class's " + methodName + "() method execution time = "
                + stopWatch.getTotalTimeMillis() + "ms");
        
        return object;
    }
}
```
#### ExecutionTimeBean.java


```java
package xyz.itwill06.aop;

public class ExecutionTimeBean {
    public void one() {
        // System.currentTimeMillis(): A static method that returns a timestamp representing the current date and time from the platform (OS).
        // Timestamp: A value that converts date and time into an integer for date and time calculations.
        // long startTime = System.currentTimeMillis();
        
        long count = 0;
        for (long i = 1; i <= 10000000000L; i++) {
            count++;
        }
        System.out.println("count = " + count);

        // long endTime = System.currentTimeMillis();
        // System.out.println("ExecutionTimeBean class's one() method execution time = "
        //         + (endTime - startTime) + "ms");
    }
    
    public void two() {
        // long startTime = System.currentTimeMillis();
        
        long count = 0;
        for (long i = 1; i <= 100000000000L; i++) {
            count++;
        }
        System.out.println("count = " + count);  
        
        // long endTime = System.currentTimeMillis();
        // System.out.println("ExecutionTimeBean class's two() method execution time = "
        //         + (endTime - startTime) + "ms");
    }
}
```

#### ExecutionTimeApp.java


```java
package xyz.itwill06.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExecutionTimeApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("06-3_timer.xml");
        ExecutionTimeBean bean = context.getBean("executionTimeBean", ExecutionTimeBean.class);
        System.out.println("=============================================================");
        bean.one();
        System.out.println("=============================================================");
        bean.two();
        System.out.println("=============================================================");
        ((ClassPathXmlApplicationContext) context).close();    
    }
}
```

#### 06-3_timer.**xml**


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- Register the core concern module class as a Spring Bean -->
    <bean class="xyz.itwill06.aop.ExecutionTimeBean" id="executionTimeBean"/>
    
    <!-- Register the cross-cutting concern module class as a Spring Bean -->
    <bean class="xyz.itwill06.aop.ExecutionTimeAdvice" id="executionTimeAdvice"/>
    
    <aop:config>
        <aop:aspect ref="executionTimeAdvice">
            <!-- Apply the around advice to all methods -->
            <aop:around method="timeWatchLog" pointcut="execution(* *(..))"/>
        </aop:aspect>
    </aop:config>
</beans>
```

---
---

#### EmailSendAdvice.java

```java
package xyz.itwill06.aop;

import org.aspectj.lang.JoinPoint;

public class EmailSendAdvice {
	// Method executed before sending an email - Before Advice Method
	// => Records the recipient's email address and the email subject provided as parameters to the target method
	public void accessLog(JoinPoint joinPoint) {
		// Retrieve and store the recipient's email address from the target method's parameters
		String email = (String) joinPoint.getArgs()[0];
		// Retrieve and store the email subject from the target method's parameters
		String subject = (String) joinPoint.getArgs()[1];
		System.out.println("Sending an email with the subject <" + subject + "> to " + email + ".");
	}
	
	// Method executed after successfully sending an email - After Returning Advice Method
	// => Records the recipient's email address provided as a return value from the target method
	public void successLog(String email) {
		System.out.println("Successfully sent the email to " + email + ".");
	}
	
	// Method executed after failing to send an email - After Throwing Advice Method
	// => Records the exception information from the target method indicating the failure of sending an email
	public void errorLog(Exception exception) {
		System.out.println("Failed to send the email = " + exception.getMessage());
	}
}
```


#### EmailSendBean.java


```java
package xyz.itwill06.aop;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.mail.javamail.JavaMailSender;

import lombok.Setter;

// To implement JavaMail functionality, ensure that the spring-context-support and javax.mail libraries 
// are included in the project build - using Maven: pom.xml

// Class for providing email sending functionality using JavaMail
// => Mail Server: A computer that provides services for sending and receiving emails
// => The mail server uses SMTP (Simple Mail Transfer Protocol - 25) for sending emails
// and POP3 (Post Office Protocol 3 - 110) or IMAP (Internet Message Access Protocol - 143)
// for receiving emails and delivering them to the user
public class EmailSendBean {
	// Field for storing the JavaMailSender object
	// => JavaMailSender object: Stores information about the SMTP server
	// => The JavaMailSender object is provided and stored in the field via DI (Dependency Injection) 
	@Setter
	private JavaMailSender javaMailSender;
	
	// Method for sending emails using SMTP service (JavaMailSender object) - Core Concern Code
	// => Returns the recipient's email address
	// => Receives the recipient's email address, email subject, and email content as parameters and uses them
	public String sendEmail(String email, String subject, String content) throws Exception {
		// JavaMailSender.createMimeMessage(): Creates and returns a MimeMessage object using the JavaMailSender object
		// MimeMessage object: Stores information related to email sending
		MimeMessage message = javaMailSender.createMimeMessage();
		
		// MimeMessage.setSubject(String subject): Sets the email subject in the MimeMessage object
		message.setSubject(subject);
		
		// MimeMessage.setText(String content): Sets the email content in the MimeMessage object
		// => Stores and delivers the email content as a plain text file document
		// message.setText(content);
		
		// MimeMessage.setContent(Object o, String type): Sets the email content in the MimeMessage object
		// => Passes the document type (MimeType) for the content in the 'type' parameter
		message.setContent(content, "text/html; charset=utf-8"); // Sends email content with HTML tags
	
		// MimeMessage.setRecipient(RecipientType type, Address address): Sets the recipient's email address in the MimeMessage object
		// => type: Constant field (RecipientType) for distinguishing the email recipient
		// => address: Address object with the email address
		// MimeMessage.setRecipients(RecipientType type, Address[] address): Method for sending emails to multiple users
		message.setRecipient(RecipientType.TO, new InternetAddress(email));
		
		// JavaMailSender.send(MimeMessage message): Sends the email using the JavaMailSender object (SMTP) with the provided MimeMessage object
		javaMailSender.send(message);
		
		return email;
	}
}
```

#### EmailSendApp.java


```java
package xyz.itwill06.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmailSendApp {
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("06-4_email.xml");
		EmailSendBean bean = context.getBean("emailSendBean", EmailSendBean.class);
		System.out.println("=============================================================");
		bean.sendEmail("beom.kb@gmail.com", "mailSendTest", "<h1>Java Mail program </h1>");
		System.out.println("=============================================================");
		
		System.out.println("=============================================================");
		((ClassPathXmlApplicationContext)context).close();	
	}
}
```

#### 06-4_email.xml


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

	<!-- Register the JavaMailSenderImpl class, which implements the JavaMailSender interface, as a Spring Bean -->
	<!-- The JavaMailSenderImpl class's fields are injected with the information of the mail server providing SMTP services -->
	<bean class="org.springframework.mail.javamail.JavaMailSenderImpl" id="javaMailSender">
		<!-- host field: Stores the name (HostName) of the mail server providing SMTP services -->
		<property name="host" value="smtp.gmail.com"/>
		<!-- port field: Stores the port number (PortNumber) of the mail server providing SMTP services -->
		<property name="port" value="587"/>
		<!-- username field: Stores the username (ID) for accessing the mail server providing SMTP services -->
		<property name="username" value="beom.kb"/>
		<!-- password field: Stores the password for accessing the mail server providing SMTP services -->
		<!-- Instead of the user password, an app password is provided -->
		<!-- How to obtain a Google app password -->
		<!-- => Visit the Google site and log in >> Google Account Management >> Security >> 2-Step Verification
		>> App Passwords >> Enter app name and create >> Generate password -->
		<property name="password" value="wvyt iooo dwrv sxam"/>
		
		<!-- javaMailProperties field: Stores additional mail sending information related to the SMTP service
		in a Properties object -->
		<property name="javaMailProperties">
			<props>
				<prop key="mail.smtp.ssl.trust">smtp.gmail.com</prop>
				<prop key="mail.smtp.starttls.enable">true</prop>
				<prop key="mail.smtp.auth">true</prop>
			</props>
		</property>
	</bean>
	
	<!-- Register the core concern module class (EmailSendBean) as a Spring Bean -->
	<!-- The emailSendBean class's javaMailSender field is injected with the Spring Bean identifier (beanName) of JavaMailSenderImpl -->
	<bean class="xyz.itwill06.aop.EmailSendBean" id="emailSendBean">
		<property name="javaMailSender" ref="javaMailSender"/>
	</bean>

	<!-- Register the cross-cutting concern module class (EmailSendAdvice) as a Spring Bean -->
	<bean class="xyz.itwill06.aop.EmailSendAdvice" id="emailSendAdvice"/>
	
	<aop:config>
		<aop:pointcut expression="execution(* sendEmail(..))" id="sendEmailPointcut"/>
		<aop:aspect ref="emailSendAdvice">
			<aop:before method="accessLog" pointcut-ref="sendEmailPointcut"/>
			<aop:after-returning method="successLog" returning="email" pointcut-ref="sendEmailPointcut"/>
			<aop:after-throwing method="errorLog" throwing="exception" pointcut-ref="sendEmailPointcut"/>
		</aop:aspect>
	</aop:config>
</beans>
```

### Summary of the XML Configuration:

- **JavaMailSender Configuration**:
  - Registers `JavaMailSenderImpl` as a Spring Bean with SMTP server details.
  - Configures the host, port, username, and password for SMTP access.
  - Sets additional mail sending properties such as SSL trust, STARTTLS, and authentication.

- **Core Concern Module (EmailSendBean)**:
  - Registers `EmailSendBean` as a Spring Bean and injects the `javaMailSender` bean.

- **Cross-Cutting Concern Module (EmailSendAdvice)**:
  - Registers `EmailSendAdvice` as a Spring Bean.
  - Configures Aspect-Oriented Programming (AOP) to include advice methods before, after returning, and after throwing for the `sendEmail` method.
---
---


### AopAnnotationAdvice.java


```java
package xyz.itwill06.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// Implementing Spring AOP features using AOP-related annotations in the Advice class and methods

@Component
// @Aspect: Annotation that provides functionality to insert cross-cutting concerns into the core concerns
// => Provides functionality similar to the aspect element in the Spring Bean Configuration File
// => Spring AOP features can only be implemented if the aspectj-autoproxy element is set in the Spring Bean Configuration File
@Aspect
public class AopAnnotationAdvice {

    // @Pointcut: Annotation to specify target methods
    // => Provides functionality similar to the pointcut element in the Spring Bean Configuration File
    // => When the method is called, it can use the Pointcut expression provided
    // value attribute: Sets the Pointcut expression to specify target methods
    // => If there are no other attributes besides value, only the attribute value can be set
    @Pointcut("within(xyz.itwill06.aop.AopAnnotationBean)")
    public void aopPointcut() {}

    // @Before: Annotation to provide cross-cutting code that will be inserted and executed before the core concern code of the target method
    // => Provides functionality similar to the before element in the Spring Bean Configuration File
    // value attribute: Sets the Pointcut expression to specify target methods
    // => If there are no other attributes besides value, only the attribute value can be set
    // => Call the method annotated with @Pointcut to provide the Pointcut expression to specify the target method
    @Before("aopPointcut()") 
    public void beforeLog() {
        System.out.println("### [before] Cross-cutting concern code executed before the core concern code ###");
    }

    // @After: Annotation to provide cross-cutting code that will be inserted and executed unconditionally after the core concern code of the target method
    // => Provides functionality similar to the after element in the Spring Bean Configuration File
    @After("aopPointcut()")
    public void afterLog() {
        System.out.println("### [after] Cross-cutting concern code executed unconditionally after the core concern code ###");
    }

    // @AfterReturning: Annotation to provide cross-cutting code that will be inserted and executed after the core concern code of the target method executes successfully
    // => Provides functionality similar to the after-returning element in the Spring Bean Configuration File
    @AfterReturning("aopPointcut()")
    public void afterReturningLog() {
        System.out.println("### [after-returning] Cross-cutting concern code executed after the core concern code executes successfully ###");
    }

    // @AfterThrowing: Annotation to provide cross-cutting code that will be inserted and executed if an exception occurs during the execution of the core concern code of the target method
    // => Provides functionality similar to the after-throwing element in the Spring Bean Configuration File
    @AfterThrowing("aopPointcut()")
    public void afterThrowingLog() {
        System.out.println("### [after-throwing] Cross-cutting concern code executed if an exception occurs during the execution of the core concern code ###");
    }

    // @Around: Annotation to provide cross-cutting code that will be executed both before and after the core concern code of the target method
    // => Provides functionality similar to the around element in the Spring Bean Configuration File
    @Around("aopPointcut()")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("### [around] Cross-cutting concern code executed before the core concern code ###");
        Object object = joinPoint.proceed();
        System.out.println("### [around] Cross-cutting concern code executed after the core concern code ###");
        return object;
    }
}
```

#### AopAnnotationApp.java


```java
package xyz.itwill06.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopAnnotationApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("06-5_aopAnnotation.xml");
        AopAnnotationBean bean = context.getBean("aopAnnotationBean", AopAnnotationBean.class);
        System.out.println("=============================================================");
        bean.display1();
        System.out.println("=============================================================");
        bean.display2();
        System.out.println("=============================================================");
        bean.display3();
        System.out.println("=============================================================");
        ((ClassPathXmlApplicationContext) context).close();            
    }
}
```


#### AopAnnotationBean.java

```java
package xyz.itwill06.aop;

import org.springframework.stereotype.Component;

@Component
public class AopAnnotationBean {
    public void display1() {
        System.out.println("*** Calling display1() method of AopAnnotationBean class ***");
    }
    
    public void display2() {
        System.out.println("*** Calling display2() method of AopAnnotationBean class ***");
    }
    
    public void display3() {
        System.out.println("*** Calling display3() method of AopAnnotationBean class ***");
    }
}
```


#### 06-5_aopAnnotation.xml


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="xyz.itwill06.aop"/>
    
    <!-- aspectj-autoproxy: Provides the functionality to create and provide Aspect (Proxy classes)
    using AOP-related annotations -->
    <aop:aspectj-autoproxy/>
</beans>
```