---
layout: single
title: 2024-08-29-spring15-mvc-withoutannotation-bean
---
#### src/main/webapp/WEB-INF/spring/mvc/mvc-context1.xml

### web.xml

```xml
<servlet>
    <servlet-name>spring</servlet-name>
    <!-- Using the DispatcherServlet class from the spring-webmvc library as the Front Controller -->
    <!-- => Creates a Spring container that provides objects only available to the Front Controller role class -->
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- init-param: Element used to provide values to the servlet class -->
    <!-- => Used to provide the path to the Spring Bean Configuration Files to the Spring container (WebApplication object) -->
    <!-- => The Spring container will only provide objects from Spring Beans registered in the Spring Bean Configuration Files to the Front Controller role class -->
    <!-- => Multiple Spring Bean Configuration Files can be provided using Enter (Enter), [,], or [;] symbols -->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>
            /WEB-INF/spring/mvc/mvc-context1.xml
            /WEB-INF/spring/mvc/mvc-context2.xml
        </param-value>
    </init-param>
    <!-- load-on-startup: Element to create the servlet class object when the WAS program starts -->
    <!-- => Creates the object when the WAS program starts, not by client requests -->
    <!-- => Servlets with smaller element values are created first -->
    <load-on-startup>0</load-on-startup>
</servlet>
```


#### mvc-context1.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Registering the child classes (request handling classes) that inherit from the Controller interface as Spring Beans -->
    <bean class="xyz.itwill08.spring.ListController" id="listController"/>
    <bean class="xyz.itwill08.spring.ViewController" id="viewController"/>
    
    <!-- Registering the SimpleUrlHandlerMapping class provided by the spring-webmvc library as a Spring Bean -->
    <!-- SimpleUrlHandlerMapping object: An object that provides the functionality of receiving request information (Command) from the controller (DispatcherServlet object)
    and returning the object of the request handling class (Controller class - Model) -->
    <!-- => In the SimpleUrlHandlerMapping class, a Map object is stored in the mappings field,
    and the Map object contains entries where the request information is set as the map key and the identifier of the request handling class (beanName) is set as the map value -->
    <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <!-- Using the map element to create a Map object - the props element can be used instead of the map element -->
            <props>
                <prop key="/list.do">listController</prop>
                <prop key="/view.do">viewController</prop>
            </props>
        </property>
    </bean>
<!-- Registering the InternalResourceViewResolver class provided by the spring-webmvc library as a Spring Bean --> <!-- InternalResourceViewResolver object: An object that receives a view name and generates and provides the path to the JSP document for forwarding --> <!-- => The prefix field and suffix field in the InternalResourceViewResolver class are injected with values used to generate the path to the JSP document --> 
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"> 
<!-- prefix field: Stores the string to be added to the beginning of the view name - folder --> 
<property name="prefix" value="/WEB-INF/mvc/"/> 
<!-- suffix field: Stores the string to be added to the end of the view name - extension -->
<property name="suffix" value=".jsp"/> 
</bean>

</beans>
```

#### Product.java
```java 
package xyz.itwill08.spring;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
	private int num;
	private String name;
}
```

#### ListController.java

```java
package xyz.itwill08.spring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// Creating a request handling class by extending the Controller interface provided by the spring-webmvc library - Controller class

// When a client requests the URL address [/list.do], the controller's request handling method is called
// to handle the client's request
public class ListController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ModelAndView object: An object used to store the processing result and view name
        ModelAndView modelAndView = new ModelAndView();
        
        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product(1000, "Computer"));
        productList.add(new Product(2000, "Laptop"));
        productList.add(new Product(3000, "Tablet"));
        
        // ModelAndView.addObject(String attributeName, Object attributeValue): Method to store
        // the result of the request handling method as an attribute value in the ModelAndView object - Request Scope
        // => The JSP document can use the attributes stored in the ModelAndView object for response processing
        modelAndView.addObject("productList", productList);
        
        // ModelAndView.setViewName(String viewName): Method to change the view name of the ModelAndView object
        modelAndView.setViewName("product_list");
        
        return modelAndView;
    }

}
```

#### ViewController.java

```java
package xyz.itwill08.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// When a client requests the URL address [/view.do], the controller's request handling method is called
// to handle the client's request
public class ViewController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product(4000, "Printer");
        modelAndView.addObject("product", product);
        modelAndView.setViewName("product_view");
        return modelAndView;
    }

}
```