---
layout: single
title: 2024/08/02 SP3-M2-controllsevlet
---


### `ControllerServlet.java` Overview

**Initialization and Setup**:
1. **Initialization (`init` Method)**:
   - When the servlet is first instantiated, the `init` method is called.
   - The `actionMap` field is initialized as a `HashMap`, which will store the mapping between request commands and their corresponding `Action` classes.
   
2. **Loading Properties**:
   - A `Properties` object is created to handle the configuration file.
   - The path to the properties file (`model.properties`) is obtained from the servlet context using the initialization parameter `configFilePath`.
   - The properties file is loaded into the `Properties` object via a `FileInputStream`.

3. **Mapping Commands to Action Classes**:
   - For each entry in the properties file, the key (request command) and value (action class) are extracted.
   - The `Action` class specified by the value is instantiated using reflection. This involves:
     - Loading the class using `Class.forName()`.
     - Creating an instance using the default constructor.
   - The command is then mapped to the corresponding `Action` object in the `actionMap`.

### Properties File (`model.properties`)

**Purpose**:
- This file maps URL patterns (commands) to their corresponding model classes. 

**Format**:
- Each line contains a command and a model class separated by an equals sign (`=`). For example:
  - `/loginform.do = xyz.itwill.mvc.LoginFormModel`
  - `/list.do = xyz.itwill.mvc.ListModel`

**Usage**:
- The `ControllerServlet` reads this file during initialization to build the `actionMap`. This map helps the servlet to route requests to the appropriate model classes based on the URL pattern.

### XML Configuration File (`web.xml`)

**Purpose**:
- This file configures the servlet and its mappings for the web application.

**Key Elements**:
1. **Servlet Definition**:
   - The `<servlet>` element defines the `ControllerServlet` class and its initialization parameters.
     - `servlet-class` specifies the fully qualified class name of the servlet.
     - `init-param` provides initialization parameters for the servlet, such as `configFilePath`, which points to the properties file.
     - `<load-on-startup>` indicates that the servlet should be loaded and initialized when the web application starts, before any client requests.

2. **Servlet Mapping**:
   - The `<servlet-mapping>` element maps URL patterns to the servlet.
     - `url-pattern` specifies that the servlet will handle requests with patterns ending in `.do`.

3. **Welcome Files**:
   - The `<welcome-file-list>` element specifies default files to be served when a directory is accessed. It includes `index.html`, `index.jsp`, etc.

**Flow**:
1. **Startup**:
   - When the web application starts, the servlet container initializes the `ControllerServlet` as specified by the `<load-on-startup>` element.
   - The `ControllerServlet` loads its configuration from `model.properties`, mapping URL commands to `Action` classes.

2. **Request Handling**:
   - When a client makes a request matching a URL pattern like `/login.do`, the servlet container routes the request to the `ControllerServlet`.
   - The `ControllerServlet` uses the `actionMap` to find the appropriate `Action` class for the request command and invokes it to handle the request.

#### ControllerSevlet.java 
Sure, here is the translated code in English:

```java
package xyz.itwill.mvc;


// Controller (Servlet): Receives all client requests and processes them by calling methods on model (class) objects
// to handle client requests and then provides the results to the view (JSP) to respond, thereby controlling the flow of the web program.

// 1. Implement a single entry point by setting the URL pattern for the servlet to handle all client requests
// => Front Controller Pattern
// @WebServlet("url"): Registers the class as a servlet and maps it to a URL address where it can be requested
// => URL patterns can use wildcard characters (*: all, ?: a single character) for mapping
// => @WebServlet("*.do"): The servlet will be executed when the client requests a URL of the form [XXX.do]
// => Instead of using @WebServlet annotation, you can use elements in the [web.xml] file to register the class as a servlet
// and match URL patterns

//@WebServlet("*.do")
public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Field for storing the HashMap object (Map object)
    private Map<String, Action> actionMap;
    
    // Method called only once right after the servlet class is instantiated - Initialization
    @Override
    public void init(ServletConfig config) throws ServletException {
        //System.out.println("ControllerServlet class's init() method called");
        // Create a HashMap object (Map object) and store it in the field
        actionMap = new HashMap<String, Action>();

        /*
        // Create entries with client request information (Command) as the map key and model objects as the map values
        // and store them in the HashMap object
        actionMap.put("/loginform.do", new LoginFormModel());
        actionMap.put("/login.do", new LoginModel());
        actionMap.put("/logout.do", new LogoutModel());
        actionMap.put("/writeform.do", new WriteFormModel());
        actionMap.put("/write.do", new WriteModel());
        actionMap.put("/list.do", new ListModel());
        actionMap.put("/view.do", new ViewModel());
        actionMap.put("/modifyform.do", new ModifyFormModel());
        actionMap.put("/modify.do", new ModifyModel());
        actionMap.put("/remove.do", new RemoveModel());
        actionMap.put("/error.do", new ErrorModel());
        */
        
        // Read request information and model classes from the Properties file and add them as entries to the Map object
        // => Allows management of model objects according to request information by only changing the Properties file
        // without modifying the servlet that acts as the controller - increases maintenance efficiency
        
        // Create and store a Properties object (Map object)
        // => Properties object: A collection object for storing entries with the Properties file names (Keys) and values (Values)
        Properties properties = new Properties();
        
        // Get the system path of the Properties file
        //String configFilePath = config.getServletContext().getRealPath("/WEB-INF/model.properties");
        //ServletConfig.getInitParameter(String name): Method to read and return values provided by the init-param element in [web.xml]
        String configFilePath = config.getServletContext().getRealPath(config.getInitParameter("configFilePath"));
        //System.out.println("configFilePath = "+configFilePath);
        
        try {
            // Create a FileInputStream with the system path of the Properties file
            FileInputStream in = new FileInputStream(configFilePath);
                        
            // Use the FileInputStream to load request information and model classes from the Properties file into the Properties object
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Iterate through all entries in the Properties object
        for (Object key : properties.keySet()) {
            // Get the key (request information) as a string
            String command = (String) key;
            
            // Get the value (model class) as a string
            String actionClass = (String) properties.get(key);
            
            try {
                // Create an instance of the model class from the string
                // => Reflection: A feature that allows creating objects using Class objects and accessing fields or methods regardless of access modifiers
                // Class.forName(String className): Static method that loads a class with the given string and returns a Class object containing class information
                // Class.getDeclaredConstructor(): Method that returns a Constructor object containing information about the default constructor from the Class object
                // Constructor.newInstance(): Method that creates and returns an Object using the Constructor object
                Action actionObject = (Action) Class.forName(actionClass)
                        .getDeclaredConstructor().newInstance();
                
                // Create an entry with the client's request information (Command) as the map key and the Model object as the map value
                // and store it in the HashMap object
                actionMap.put(command, actionObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

#### PropertiesFile
```
#Command(Key) = ModelClass(Value)

/loginform.do = xyz.itwill.mvc.LoginFormModel

/login.do = xyz.itwill.mvc.LoginModel

/logout.do = xyz.itwill.mvc.LogoutModel

/writeform.do = xyz.itwill.mvc.WriteFormModel

/write.do = xyz.itwill.mvc.WriteModel

/list.do = xyz.itwill.mvc.ListModel

/view.do = xyz.itwill.mvc.ViewModel

/modifyform.do = xyz.itwill.mvc.ModifyFormModel

/modify.do = xyz.itwill.mvc.ModifyModel

/remove.do = xyz.itwill.mvc.RemoveModel

/error.do = xyz.itwill.mvc.ErrorModel
```
### web.xml

Here is the translated `web.xml` configuration file in English:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd" id="WebApp_ID" version="4.0">
  <display-name>mvc</display-name>
  
  <!-- servlet: Element for registering a class as a servlet -->
  <servlet>
    <servlet-name>controller</servlet-name>
    <servlet-class>xyz.itwill.mvc.ControllerServlet</servlet-class>
    <!-- init-param: Element for providing values to the servlet class -->
    <init-param>
      <param-name>configFilePath</param-name>
      <param-value>/WEB-INF/model.properties</param-value>
    </init-param>
    <!-- load-on-startup: Element for pre-creating the servlet class object when the application server starts -->
    <!-- => Initializes the servlet class object in advance even if the client does not request the servlet -->
    <!-- => Set an integer value for the element to specify which classes to create objects for first -->
    <load-on-startup>1</load-on-startup>
  </servlet>
  
  <!-- servlet-mapping: Element for registering URL patterns to request and execute the servlet -->
  <servlet-mapping>
    <servlet-name>controller</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>
  
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.jsp</welcome-file>
    <welcome-file>default.htm</welcome-file>
  </welcome-file-list>
</web-app>
```