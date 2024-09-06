---
layout: single
title: 2024-09-04-spring22-mybatis-logFactory
---


### MyBatis Setup

   - **Spring → MyBatis Setup**
   - Build framework libraries 
   - `pom.xml`

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<!-- => Library for using the MyBatis framework -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.16</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
<!-- => Library for using the MyBatis framework in a Spring MVC web application -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>3.0.4</version>
</dependency>
```

**root-context.xml**
- **root-context.xml**: A Spring Bean Configuration File to register classes as Spring Beans so that they can be provided to all Front Controllers. This includes DataSource, SqlSessionFactory, SqlSession, and TransactionManager.

- **DataSource Configuration**:
```xml
<!-- Registering DataSource related class (DriverManagerDataSource class) as a Spring Bean -->
<!-- => The class's fields are injected with values needed to create a Connection object - Setter Injection -->
<bean class="org.springframework.jdbc.datasource.DriverManagerDataSource" id="dataSource">
    <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"/>
    <property name="url" value="jdbc:oracle:thin:@localhost:1521:xe"/>
    <property name="username" value="scott"/>
    <property name="password" value="tiger"/>
</bean>
```

- **SqlSessionFactory Configuration**:
```xml
<!-- Registering SqlSessionFactory related class (SqlSessionFactoryBean class) as a Spring Bean -->
<!-- => Fields are injected with values or objects needed to create an SqlSessionFactory object - Setter Injection -->
<bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactoryBean">
    <!-- configLocation field is injected with the path to the MyBatis framework configuration file -->
    <!-- If the configuration file is in [src/main/java] or [src/main/resources], use classpath prefix -->
    <!-- <property name="configLocation" value="classpath:xyz/itwill/config/mybatis-config.xml"/> -->
    <!-- If the configuration file is in [src/main/webapp], use the web resource path -->
    <property name="configLocation" value="/WEB-INF/spring/mybatis-config.xml"/> 
    
    <!-- The dataSource field is injected with the Spring Bean identifier of the DataSource class -->
    <!-- => Provides similar functionality to the environment element in MyBatis configuration -->
    <property name="dataSource" ref="dataSource"/>
    
    <!-- The typeAliasesPackage field is injected with the package where DTO classes are written -->
    <!-- => Provides aliases for Java types used in XML-based mapper files -->
    <!-- => Similar functionality to the typeAlias element in MyBatis configuration -->
    <property name="typeAliasesPackage" value="xyz.itwill09.dto"/>
    
    <!-- The mapperLocations field is injected with a List object containing the paths to XML-based mapper files -->
    <!-- => Registers the XML-based mapper files -->
    <!-- => Similar functionality to the mapper element in MyBatis configuration -->
    <property name="mapperLocations">
        <list>
            <!-- Register all XML-based mapper files written in [src/main/java] -->
            <!-- => Use classpath prefix to provide file paths for XML-based mapper files -->
            <value>classpath:xyz/itwill09/mapper/*.xml</value>
        </list>
    </property>
</bean>    

<!-- Registering SqlSession related class (SqlSessionTemplate class) as a Spring Bean -->
<!-- => The SqlSessionFactory object that provides SqlSession objects is injected into the field - Constructor Injection -->
<!-- => Uses destroy-method attribute to automatically call the clearCache method before the SqlSession object is destroyed - Skips calling close() method after using SqlSession object -->
<bean class="org.mybatis.spring.SqlSessionTemplate" id="sqlSession" destroy-method="clearCache">
    <constructor-arg ref="sqlSessionFactoryBean"/>
</bean>
```

**mybatis-config.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="callSettersOnNulls" value="true"/>
        <setting name="jdbcTypeForNull" value="NULL"/>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
```
---
---

### LogFactory

To record log events with the Spring framework's logging implementation using LogFactory:

1. **Add `log4jdbc-log4j2-jdbc4` Dependency**:
   - Find `log4jdbc-log4j2-jdbc4` in MVN and add the library to `pom.xml`
```xml
<!-- https://mvnrepository.com/artifact/org.bgee.log4jdbc-log4j2/log4jdbc-log4j2-jdbc4 -->
<dependency>
    <groupId>org.bgee.log4jdbc-log4j2</groupId>
    <artifactId>log4jdbc-log4j2-jdbc4</artifactId>
    <version>1.16</version>
</dependency>
```

2. **Update DataSource Configuration in `root-context.xml`**:
   - Change the `driverClassName` and `url` values to use those provided by `log4jdbc-log4j2-jdbc4`
```xml
<!-- Update the driverClassName and url values in the DataSource to use values provided by the log4jdbc-log4j2-jdbc4 library -->
 <!-- Before changed <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"/>
    <property name="url" value="jdbc:oracle:thin:@localhost:1521:xe"/> --> 
<bean class="org.springframework.jdbc.datasource.DriverManagerDataSource" id="dataSource">
    <property name="driverClassName" value="net.sf.log4jdbc.sql.jdbcapi.DriverSpy"/>
    <property name="url" value="jdbc:log4jdbc:oracle:thin:@localhost:1521:xe"/>
    <property name="username" value="scott"/>
    <property name="password" value="tiger"/>
</bean>
```

3. **Create `log4jdbc.log4j2.properties` in [src/main/resources]**:
   - `log4jdbc.spylogdelegator.name = net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator`

4. **Modify `log4j.xml` Configuration**:
   - Add `logger` elements to record different types of logs
```xml
<!-- Logger elements to record log events generated by the SpyLogDelegator object -->
<!-- jdbc.sqlonly: Record completed SQL commands -->
<logger name="jdbc.sqlonly">
    <level value="info"/>
</logger>

<!-- jdbc.sqltiming: Record SQL command execution time (ms) -->
<logger name="jdbc.sqltiming">
    <level value="info"/>
</logger>

<!-- jdbc.audit: Record all JDBC-related information except ResultSet object details -->
<logger name="jdbc.audit">
    <level value="info"/>
</logger>

<!-- jdbc.resultset: Record ResultSet object-related information -->
<!-- 
<logger name="jdbc.resultset">
    <level value="info"/>
</logger>
-->

<!-- jdbc.resultsettable: Record ResultSet object-related information in tabular format -->
<logger name="jdbc.resultsettable">
    <level value="info"/>
</logger>

<!-- jdbc.connection: Record Connection object-related information -->
<logger name="jdbc.connection">
    <level value="info"/>
</logger>
```