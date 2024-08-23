---
layout: single
title: 2024/08/22 Spring6-Collection
---
05-2_collection.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Register the child classes that inherit the Controller interface as Spring Beans -->
    <bean class="xyz.itwill05.di.LoginController" id="loginController"/>
    <bean class="xyz.itwill05.di.LogoutController" id="logoutController"/>
    <bean class="xyz.itwill05.di.ListController" id="listController"/>

    <!-- Inject dependencies by calling Setter methods on the CollectionBean class fields to store Collection objects -->
    <bean class="xyz.itwill05.di.CollectionBean" id="collectionBean">
        <property name="nameSet">
            <!-- set: Element to create a Set object and store it in the field -->
            <set>
                <!-- value: Element to add values as elements in the Collection object -->
                <value>Mr,Hong</value>
                <value>Mr,Lim</value>
                <value>Mr,Jeon</value>
                <value>Mr,Hong</value>
            </set>
        </property>
        
        <property name="nameList">
            <!-- list: Element to create a List object and store it in the field -->
            <list>
                <value>Mr,Hong</value>
                <value>Mr,Lim</value>
                <value>Mr,Jeon</value>
                <value>Mr,Hong</value>
            </list>
        </property>
        
        <property name="controllerSet">
            <set>
                <!-- ref: Element to add objects from Spring Beans as elements in the Set -->
                <!-- bean attribute: Set the identifier (beanName) to distinguish Spring Beans -->
                <ref bean="loginController"/>
                <ref bean="logoutController"/>
                <ref bean="listController"/>
            </set>
        </property>
        
        <property name="controllerList">
            <list>
                <ref bean="loginController"/>
                <ref bean="logoutController"/>
                <ref bean="listController"/>
            </list>
        </property>
        
        <property name="controllerMap">
            <!-- map: Element to create a Map object and store it in the field -->
            <map>
                <!-- entry: Element to add entries to the Map object -->
                <entry>
                    <!-- key: Element to set the map key for the entry -->
                    <key>
                        <value>login</value>
                    </key>
                    <!-- ref: Element to set the map value (object) for the entry -->
                    <ref bean="loginController"/>
                </entry>
                <entry>
                    <key>
                        <value>logout</value>
                    </key>
                    <ref bean="logoutController"/>
                </entry>
                <entry>
                    <key>
                        <value>list</value>
                    </key>
                    <ref bean="listController"/>
                </entry>
            </map>
        </property>
        
        <property name="controllerProperties">
            <!-- props: Element to create a Properties object and store it in the field -->
            <props>
                <!-- prop: Element to add entries to the Properties object -->
                <!-- key attribute: Set the map key for the entry -->
                <!-- The content of the prop element is used as the map value for the entry -->
                <prop key="login">xyz.itwill05.di.LoginController</prop>
                <prop key="logout">xyz.itwill05.di.LogoutController</prop>
                <prop key="list">xyz.itwill05.di.ListController</prop>
            </props>
        </property>        
    </bean>
</beans>
```


#### CollectionBean.java


```java
package xyz.itwill05.di;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CollectionBean {
    private Set<String> nameSet;
    private List<String> nameList;
    // If the generic of the Collection class is set to an interface, the Collection object can store 
    // objects of classes that inherit the interface as elements
    private Set<Controller> controllerSet;
    private List<Controller> controllerList;
    private Map<String, Controller> controllerMap;
    private Properties controllerProperties;
    
    public CollectionBean() {
        System.out.println("### CollectionBean class default constructor called ###");
    }

    public Set<String> getNameSet() {
        return nameSet;
    }

    public void setNameSet(Set<String> nameSet) {
        this.nameSet = nameSet;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public Set<Controller> getControllerSet() {
        return controllerSet;
    }

    public void setControllerSet(Set<Controller> controllerSet) {
        this.controllerSet = controllerSet;
    }

    public List<Controller> getControllerList() {
        return controllerList;
    }

    public void setControllerList(List<Controller> controllerList) {
        this.controllerList = controllerList;
    }

    public Map<String, Controller> getControllerMap() {
        return controllerMap;
    }

    public void setControllerMap(Map<String, Controller> controllerMap) {
        this.controllerMap = controllerMap;
    }

    public Properties getControllerProperties() {
        return controllerProperties;
    }

    public void setControllerProperties(Properties controllerProperties) {
        this.controllerProperties = controllerProperties;
    }
}
```


#### CollectionBeanApp.java

```java
package xyz.itwill05.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CollectionBeanApp {
    public static void main(String[] args) {
        System.out.println("=============== Before Spring Container Initialization ===============");
        ApplicationContext context = new ClassPathXmlApplicationContext("05-2_collection.xml");
        System.out.println("=============== After Spring Container Initialization ===============");
        CollectionBean bean = context.getBean("collectionBean", CollectionBean.class);
        
        // Retrieve and print the field values (Collection objects) of the CollectionBean object
        // => When printing a Collection object, the toString() method of the Collection class is called
        // to return a string representation of all the elements stored in the Collection object
        System.out.println("nameSet = " + bean.getNameSet());
        System.out.println("nameList = " + bean.getNameList());
        System.out.println("controllerSet = " + bean.getControllerSet());
        System.out.println("controllerList = " + bean.getControllerList());
        System.out.println("controllerMap = " + bean.getControllerMap());
        System.out.println("controllerProperties = " + bean.getControllerProperties());
        System.out.println("==========================================================");
        ((ClassPathXmlApplicationContext)context).close();
    }
}
```


#### Controller-interface ,LoginController-class, LoginController-class, LogoutController-class
```java 
package xyz.itwill05.di;

public interface Controller {
	void handleRequest();
	
}

package xyz.itwill05.di;

public class ListController implements Controller {

	@Override
	public void handleRequest() {
		// TODO Auto-generated method stub
		
	}

}

package xyz.itwill05.di;

public class LoginController implements Controller{

	@Override
	public void handleRequest() {
		// TODO Auto-generated method stub
		
	}

}

package xyz.itwill05.di;

public class LogoutController implements Controller {

	@Override
	public void handleRequest() {
		// TODO Auto-generated method stub
		
	}

}
```
