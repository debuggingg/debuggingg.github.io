---
layout: single
title: 2024-09-02-spring18-tiles
---
#### Program Flow 
- pom.xml ->Build the TilesView-related libraries (tiles-extras library) into the project 
- template page(jsp) for responding 
- Create a configuration file-> /WEB-INF/spring/appServlet/tiles.xml
- Configure the Spring Bean Configuration File ->servlet-context.xml
---

### pom.xml
```xml
<!-- https://mvnrepository.com/artifact/org.apache.tiles/tiles-extras -->
<!-- => Library for providing ViewResolver functionality -->
<dependency>
    <groupId>org.apache.tiles</groupId>
    <artifactId>tiles-extras</artifactId>
    <version>3.0.8</version>
</dependency>
```

#### tiles.xml 
**/WEB-INF/spring/appServlet/tiles.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE tiles-definitions PUBLIC "//Apache Software Foundation//DTD Tiles Configuration 3.0/EN"
    "http://tiles.apache.org/dtds/tiles-config_3_0.dtd">
<tiles-definitions>
    <!-- definition: Element for providing the JSP documents that serve as templates 
    for response processing based on the return value (ViewName) of the request handling method -->
    <!-- name attribute: Sets an identifier value to distinguish the definition element -->
    <!-- => Response processing is done using the definition element with the same name attribute value as the return value of the request handling method -->
    <!-- template attribute: Sets the path of the JSP document for the template functionality of the definition element as an attribute value -->    
    <definition name="main" template="/WEB-INF/views/layout/template.jsp">
        <!-- put-attribute: Element for providing the JSP documents that make up the template page -->
        <!-- name attribute: Identifies the put-attribute element -->
        <!-- value attribute: Sets the path of the JSP document to be provided to the template page as an attribute value -->
        <put-attribute name="header" value="/WEB-INF/views/layout/header.jsp"/>
        <put-attribute name="content" value="/WEB-INF/views/layout/main.jsp"/>
        <put-attribute name="footer" value="/WEB-INF/views/layout/footer.jsp"/>
    </definition>
    
    <definition name="admin" extends="main">
        <put-attribute name="header" value="/WEB-INF/views/layout/header_admin.jsp"/>
        <put-attribute name="content" value="/WEB-INF/views/layout/main_admin.jsp"/>
        <put-attribute name="footer" value="/WEB-INF/views/layout/footer_admin.jsp"/>
    </definition>
    
    <definition name="admin/*" extends="admin">
        <put-attribute name="content" value="/WEB-INF/views/admin/{1}.jsp"/>
    </definition>
    
    <definition name="admin/*/*" extends="admin">
        <put-attribute name="content" value="/WEB-INF/views/admin/{1}/{2}.jsp"/>
    </definition>
    
    <!-- If the name attribute of the definition element is set to [*], it can handle responses 
    for all view names returned in the format ["value"] -->
    <!-- extends attribute: Sets the identifier of the definition element as the attribute value -->
    <!-- => Used to inherit information from the definition element -->
    <!-- => Can inherit put-attribute elements but can also override them -->
    <definition name="*" extends="main">
        <!-- <put-attribute name="header" value="/WEB-INF/views/layout/header.jsp"/> -->
        <!-- If the name attribute of the definition element is set to [*], values in the value attribute of put-attribute 
        can be expressed in the {integer value} format to use values -->
        <!-- => The integer value used instead of the view name is incremented from 1 -->
        <!-- => Creates folder and file names based on the return value of the request handling method to provide to the template page -->
        <put-attribute name="content" value="/WEB-INF/views/{1}.jsp"/>
        <!-- <put-attribute name="footer" value="/WEB-INF/views/layout/footer.jsp"/> -->
    </definition>

    <!-- If the name attribute of the definition element is set to [*/*], it can handle responses 
    for all view names returned in the format ["value/value"] -->
    <definition name="*/*" extends="main">
        <put-attribute name="content" value="/WEB-INF/views/{1}/{2}.jsp"/>
    </definition>
    
    <!-- If the name attribute of the definition element is set to [*/*/*], it can handle responses 
    for all view names returned in the format ["value/value/value"] -->
    <definition name="*/*/*" extends="main">
        <put-attribute name="content" value="/WEB-INF/views/{1}/{2}/{3}.jsp"/>
    </definition>
</tiles-definitions>
```

#### template.jsp 
- header.jsp 
- main.jsp
- footer.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that provides the structure of the page - Template page --%>
<!-- Adding the tags-tiles tag library to the JSP document to use TilesView functionality -->    
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
<style type="text/css">
#header {
    border: 2px solid red;
    height: 150px;
    margin: 10px;
    padding: 10px;
    text-align: center;
}

#content {
    border: 2px solid green;
    min-height: 550px;
    margin: 10px;
    padding: 10px;
}

#footer {
    border: 2px solid blue;
    height: 150px;
    margin: 10px;
    padding: 10px;
    text-align: center;
}
</style>
</head>
<body>
    <div id="header">
        <%-- insertAttribute tag: Tag provided by TilesView functionality to insert the result of the JSP document 
        provided by the put-attribute element in the configuration file --%>
        <tiles:insertAttribute name="header"/>
    </div>
    
    <div id="content">
        <tiles:insertAttribute name="content"/>
    </div>
    
    <div id="footer">
        <tiles:insertAttribute name="footer"/>
    </div>
</body>
</html>
```

#### sevlet-context.xml
```xml
<!-- Register the UrlBasedViewResolver class as a Spring Bean -->
<!-- => UrlBasedViewResolver object: Provides functionality to handle responses using a ViewResolver class 
    rather than the ViewResolver object provided by the Spring framework -->
<!-- => Used to customize a ViewResolver object for handling views based on view names -->
<beans:bean class="org.springframework.web.servlet.view.UrlBasedViewResolver">
    <!-- Inject value into the viewClass field to specify the class providing ViewResolver functionality -->
    <beans:property name="viewClass" value="org.springframework.web.servlet.view.tiles3.TilesView"/>
    <!-- Inject value into the order field to set the priority of the ViewResolver class -->
    <!-- => Must be set if multiple ViewResolver classes are registered -->
    <!-- => The smaller the integer value, the higher the priority, meaning it will be executed first -->
    <beans:property name="order" value="2"/>
</beans:bean>

<!-- Register the TilesConfigurer class as a Spring Bean -->
<!-- => TilesConfigurer object: Provides the configuration file containing information to handle responses 
    based on the return value (ViewName) of request handling methods -->
<beans:bean class="org.springframework.web.servlet.view.tiles3.TilesConfigurer">
    <!-- Inject dependency to create and store a List object in the definitions field -->
    <!-- => The List object contains paths to the configuration files -->
    <beans:property name="definitions">
        <beans:list>
            <beans:value>/WEB-INF/spring/appServlet/tiles.xml</beans:value>
        </beans:list>
    </beans:property>
</beans:bean>
```
#### TilesController.java

```java
package xyz.itwill09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Using the TilesView class to respond with views created by combining multiple JSP pages
// => TilesView class: A ViewResolver class that provides functionality to respond with views composed of multiple JSP documents
// 1. Build the TilesView-related libraries (tiles-extras library) into the project - Maven usage: pom.xml
// 2. Set the JSP document (template page) to respond with by providing the view name returned from the request handling method
// => Create a configuration file for the environment that TilesView will use - /WEB-INF/spring/appServlet/tiles.xml
// 3. Configure the Spring Bean Configuration File (servlet-context.xml) to allow the Front Controller (DispatcherServlet object) 
// to respond with the TilesView class as the ViewResolver object by providing the view name from the request handling method
// => Set priority so that the TilesView object is executed before the InternalResourceViewResolver object

@Controller
public class TilesController {
	@RequestMapping("/")
	public String tiles() {
		return "main";
	}
	
	@RequestMapping("/tiles")
	public String tiles1() {
		return "layout/tiles";
	}
	
	@RequestMapping("/admin/")
	public String admin() {
		return "admin";
	}
}
```