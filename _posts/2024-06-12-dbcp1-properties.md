---
layout: single
title: 2024/06/12 DBCP -01 -Properties
---
# DBCP
---

**DBCP (Database Connection Pool):**  
A DBCP object pre-creates multiple `Connection` objects and stores them for providing to JDBC programs.

- **Benefits:**
    - Increases execution speed of JDBC programs by providing pre-created `Connection` objects.
    - Easier to change the information needed to create `Connection` objects, enhancing productivity and maintainability.
    - Limits the number of `Connection` objects, minimizing resource waste.

**DBCP Classes:** DBCP classes are created by implementing the `DataSource` interface, ensuring uniform method structures and reducing class coupling, which increases maintainability.

**Using Oracle's UCP (Universal Connection Pool):** To use Oracle's UCP library for DBCP, download the `ucp11.jar` file from the Oracle website and add it to your project.

## Full Code
---

```java
public class DataSourceApp {
    public static void main(String[] args) throws SQLException {
        // Retrieve and store a PoolDataSource (DBCP) object
        PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();

        // Configure the PoolDataSource to pre-create Connection objects
        // Set the JDBC driver class
        pds.setConnectionFactoryClassName("oracle.jdbc.driver.OracleDriver");
        // Set the DBMS server URL
        pds.setURL("jdbc:oracle:thin:@localhost:1521:xe");
        // Set the DBMS server user account name
        pds.setUser("scott");
        // Set the DBMS server password
        pds.setPassword("tiger");

        // Optionally limit the number of Connection objects in the PoolDataSource
        // Set the initial number of Connection objects
        pds.setInitialPoolSize(2);
        // Set the maximum number of Connection objects
        pds.setMaxPoolSize(3);

        // Retrieve and print Connection objects from the PoolDataSource
        Connection con1 = pds.getConnection();
        System.out.println("con1 = " + con1);

        Connection con2 = pds.getConnection();
        System.out.println("con2 = " + con2);

        Connection con3 = pds.getConnection();
        System.out.println("con3 = " + con3);

        Connection con4 = pds.getConnection();
        System.out.println("con4 = " + con4);
        con4.close(); // Close and release the Connection object back to the pool
    }
}
```

## Properties
---
A properties file is a text file used to provide values required for program execution.

- **Details:**
    - The file is saved with a `.properties` extension, containing lines of `name = value` strings.  
    - Values in the properties file are distinguished by their names, so names must be unique.
    - Non-English characters, numbers, and some special characters in the properties file are automatically converted to Unicode.
    - Using properties files for frequently changing values enhances the maintainability of the program.

**Example:**  
Eclipse : New(samePackage) -> general - > filename-> name(should be something.properties)-> ok.
Creating a program that reads values from a `user.properties` file and prints them.
```
#comment-\uC124\uBA85\uBB38 <- Unicode.
#properties File>> Configuration file
# Name=value
id=abc123
name=\uD64D\uAE38\uB3D9\<-Unicode.
email=abc@itwill.xyz
```

--- 
#### program( class) explain 
 1. Object.getClass() : A method that returns the Class object of the class in which the command is executed.
	  Class object: An object that contains information about the class loaded into memory.
	2. Class.getClassLoader() : A method that returns the ClassLoader object that created the Class object.
	  ClassLoader object: An object that reads the class file (XXX.class) and creates the Class object in memory.
		 3. ClassLoader.getResourceAsStream(String name) : A method that takes the path of a file existing in the project as a parameter, and returns an input stream to read that file.
```java
 InputStream in=getClass().getClassLoader().getResourceAsStream("xyz/itwill/dbcp/user.properties");
```
- Creating an object using the Properties class 
	=> The Properties class is a collection class that inherits from the **Map** interface 
	=> The Properties object can store multiple entries

## Full Code
---
```java
public class PropertiesApp {
    public PropertiesApp() throws IOException {
        // Create an input stream to read the contents of the properties file
        // Using the class loader to avoid issues with file paths during program distribution
        InputStream in = getClass().getClassLoader().getResourceAsStream("xyz/itwill/dbcp/user.properties");

        // Create a Properties object
        Properties properties = new Properties();

        // Load the properties from the input stream
        properties.load(in);

        // Retrieve values from the Properties object
        String id = (String) properties.get("id");
        String name = (String) properties.get("name");
        String email = (String) properties.get("email");

        // Print the retrieved values
        System.out.println("ID = " + id);
        System.out.println("Name = " + name);
        System.out.println("Email = " + email);
    }

    public static void main(String[] args) throws IOException {
        new PropertiesApp();
    }
}
```

---
## DBCP with properties 

 `DataSourceApp` class using a properties file.
 
 **Properties File**:

- Stores configuration values needed to create the Connection objects.
- The properties file (`dbcp.properties`) contains key-value pairs such as driver, URL, user, password, initial connection count, and maximum connection count.

```java
// DBCP (DataBase Connection Pool): An object that pre-creates and stores multiple Connection objects 
// to provide them for JDBC programs.
// => Increases the execution speed of JDBC programs by pre-creating and providing Connection objects.
// => Easier to change the information needed to create Connection objects - increases productivity and maintenance efficiency.
// => Limits the number of Connection objects - minimizes resource wastage.

// The class to create a DBCP object is written by inheriting the DataSource interface.
// => Provides method writing rules so that all DBCP classes are written with the same methods.
// => Reduces the coupling of classes, increasing maintenance efficiency.

// Using the DBCP class of the UCP (Universal Connection Pool) library distributed by the Oracle group 
// to write a JDBC program.
// => Download the UCP library file (ucp11.jar) from the https://www.oracle.com site 
// and build it into the project.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class DataSourceApp {
    public DataSourceApp() throws IOException, SQLException {
        // Creating an input stream to read the contents of the properties file
        InputStream in = getClass().getClassLoader().getResourceAsStream("xyz/itwill/dbcp/dbcp.properties");

        // Creating a Properties object
        Properties properties = new Properties();
        properties.load(in);

        // Getting properties values
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String initCons = properties.getProperty("initCons");
        String maxCons = properties.getProperty("maxCons");

        // Getting a DataSource object (PoolDataSource object - DBCP object)
        // PoolDataSourceFactory.getPoolDataSource(): A static method that returns a PoolDataSource object
        // PoolDataSource pds = new PoolDataSourceImpl();
        PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();

        // Calling a method to pre-create Connection objects to be stored in the PoolDataSource object - mandatory
        // PoolDataSource.setConnectionFactoryClassName(String driver): A method to change the JDBC Driver class
        pds.setConnectionFactoryClassName(driver);
        // PoolDataSource.setURL(String url): A method to change the URL address to connect to the DBMS server
        pds.setURL(url);
        // PoolDataSource.setUser(String user): A method to change the account name to connect to the DBMS server
        pds.setUser(user);
        // PoolDataSource.setPassword(String password): A method to change the password to connect to the DBMS server
        pds.setPassword(password);

        // Limiting the number of Connection objects to be stored in the PoolDataSource object - optional: default values are used if omitted
        // PoolDataSource.setInitialPoolSize(int size): A method to change the number of Connection objects initially created in the PoolDataSource object
        pds.setInitialPoolSize(Integer.parseInt(initCons));
        // PoolDataSource.setMaxPoolSize(int size): A method to change the maximum number of Connection objects that can be created and stored in the PoolDataSource object
        pds.setMaxPoolSize(Integer.parseInt(maxCons));

        // PoolDataSource.getConnection(): A method to return one of the Connection objects stored in the PoolDataSource object - the returned Connection object in the PoolDataSource object is deactivated
        Connection con1 = pds.getConnection();
        System.out.println("con1 = " + con1);
        // When calling the close() method on the Connection object, the Connection object stored in the PoolDataSource object is activated
        // con1.close();

        Connection con2 = pds.getConnection();
        System.out.println("con2 = " + con2);
        // con2.close();

        Connection con3 = pds.getConnection();
        System.out.println("con3 = " + con3);
        // con3.close();

        Connection con4 = pds.getConnection();
        System.out.println("con4 = " + con4);
        con4.close();
    }

    public static void main(String[] args) throws SQLException, IOException {
        new DataSourceApp();
    }
}
```