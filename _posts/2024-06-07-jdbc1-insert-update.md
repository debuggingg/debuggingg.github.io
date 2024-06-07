---
layout: single
title: 2024/06/07/ JDBC-01- StaticBlock-Insert-update
---
---
# JDBC - StaticBlock
---

## Class object

- **Class.forName(String className)**: This method takes the fully qualified name of a class as a string and loads it into memory using the ClassLoader. It returns a `Class` object containing information about the specified class. If the class is not found, a `ClassNotFoundException` is thrown, so proper exception handling is necessary.
- **Object.getClass()**: This method returns the `Class` object representing the class of the object that calls the method.
- **ClassName.class**: This is the most common method. It directly reads the class file and creates a `Class` object.

#### Ex)

- Class.forName(String className): A static method that takes the fully qualified name of a class as a parameter and uses the ClassLoader to load the class into memory.
	 => Returns a Class object containing information about the class.
	 => If the class specified by the parameter does not exist, a ClassNotFoundException is thrown, so it must be handled.
```
// Manually load the class into memory
	Class.forName("xyz.itwill.jdbc.StaticBlock"); 
     StaticBlock sb = new StaticBlock();
     sb.display();
```

---
## StaticBlock
---
**Create StaticBlock** 
- **Static Block**:
    
    - The static block contains commands that are automatically executed once when the class file is read and stored in memory as a Class object.
    - These commands are executed only once during the program's execution.
    - In this static block, it prints a message, creates an instance of `StaticBlock`, and calls the `display()` method on that instance.
- **Constructor**:
    
    - The constructor `StaticBlock()` prints a message when an object of `StaticBlock` is created.
    - This constructor is called when the `new` operator is used to create an instance of the class.
- **`display()` Method**:
    
    - The `display()` method prints a message when it is called.
    - This method is called in both the static block and can be called on any `StaticBlock` instance.

```java
public class StaticBlock {
    static {
        System.out.println("### Executing commands written in the static block of the StaticBlock class ###");
        StaticBlock sb = new StaticBlock();
        sb.display();
    }
    public StaticBlock() {
        System.out.println("### Calling the default constructor of the StaticBlock class - Object creation ###");
    }    
    public void display() {
        System.out.println("### Calling the display() method of the StaticBlock class ###");
    }
}
```


**Main**

- **JVM and ClassLoader**:
    - The JVM uses the ClassLoader to read the class file (e.g., `XXX.class`) and create a Class object (`Clazz`) in the Method area of memory. This creation and storage happen only once.
- **Object Creation with `new` Operator**:
    
    - The `new` operator is used to call the constructor of the class to create an object. This object is created in the Heap area of memory.
- **Reference Variable**:
    
    - A reference variable is created to store the memory address of the created object. This reference variable is stored in the Static area of memory.
- **Method Call**:
    
    - The method is called by referencing the object using the memory address stored in the reference variable.
```java
public class StaticBlockApp {
    public static void main(String[] args) throws ClassNotFoundException {
        StaticBlock sb = new StaticBlock();
        sb.display();
    }
}
```

---
---
## JDBC download (ojdbc11)

**JDBC (Java DataBase Connectivity)**:

- JDBC is a Java API that provides the functionality to connect to a DBMS server, send SQL commands, and execute them.

**java.sql Package**:

- java.sql: A package that contains Java data types for writing JDBC programs.
	=> The java.sql package provides interfaces for JDBC functionality.
	=> Because there are various types of DBMS, JDK library (jrt-fs.jar) cannot include JDBC functionality classes.
	=> The groups developing and managing DBMS create and distribute JDBC Driver classes.
	Thus, you need to download the JDBC Driver library file and build it into your project.

**Oracle JDBC Driver**:
    
- To use Oracle DBMS in a JDBC program, download the Oracle JDBC Driver library file and build it into your project.
- Download the `ojdbc11.jar` file from the Oracle website, referring to the appropriate JDK version.
	- https://www.oracle.com/database/technologies/appdev/jdbc-drivers-archive.html
- Copy the `ojdbc11.jar` file into your project folder.
    

**Building the Library into the Project**:

- Link the library file stored in your project folder to your project.
- This setup allows the interfaces or classes from the library file to be used in your program.
- Steps:
    1. Right-click on your project.
    2. Select "Properties".
    3. In the Properties window, navigate to "Java Build Path".
    4. Go to the "Libraries" tab.
    5. Select "Classpath" and then "Add JARs".
    6. Choose the `ojdbc11.jar` file from your project folder.
    7. Click "Apply and Close".
---
---
## Connection to JDBC
---
### Insert

## Program Flow

 **Declare JDBC References**:
   - Declare `Connection` and `Statement` references to store JDBC-related objects. These references can be used to call methods throughout the program, including the try block.

1. **Load OracleDriver Class**:
   - Use `Class.forName("oracle.jdbc.driver.OracleDriver")` to load the OracleDriver class. This method creates a `Class` object and stores it in memory.
   - The static block in the OracleDriver class registers the driver with `DriverManager`.

2. **Connect to DBMS Server**:
   - Use `DriverManager.getConnection(String url, String username, String password)` to connect to the DBMS server and return a `Connection` object.
   - If the connection is successful, a `Connection` object is returned. Otherwise, a `SQLException` is thrown.
   - Define the URL, username, and password for the DBMS server connection.

3. **Create Statement Object**:
   - Use `con.createStatement()` to create a `Statement` object. This object is used to send SQL commands to the connected DBMS server.

4. **Execute SQL Command**:
   - Use `stmt.executeUpdate(String sql)` to execute a DML (INSERT) command and return the number of affected rows as an int.
   - Print the result of the SQL command execution.

5. **Exception Handling**:
   - Handle `ClassNotFoundException` and `SQLException` to manage errors related to the JDBC operations.

6. **Cleanup JDBC Objects**:
   - In the `finally` block, remove all JDBC-related objects in reverse order of their creation to close the connection to the DBMS server.
   - Check if `Statement` and `Connection` objects are not null before calling their `close()` methods to prevent `NullPointerException`.

## Full Code
---
```java
public class InsertStudentApp {

    public static void main(String[] args) throws ClassNotFoundException {

    // Declare references to store JDBC-related objects
    // => These references can be used to call methods in all areas, including the try block
        Connection con = null;
        Statement stmt = null;

        try {
        // We will use the OracleDriver from the setup ojdbc11.
        // 1. Call the Class.forName(String className) method to load the OracleDriver class,
        // create a Class object, and store it in memory.
        // => The static block in the OracleDriver class creates an instance of OracleDriver
        // => The static block in the OracleDriver class registers the driver with DriverManager
        Class.forName("oracle.jdbc.driver.OracleDriver");

        // 2. Connect to the DBMS server and get a Connection object
        // DriverManager.getConnection(String url, String username, String password)
		// => This static method connects to the DBMS server and returns a Connection object.
		// => The method uses the registered JDBC driver to connect to the DBMS server using the given parameters.
		// => If the connection is successful, a Connection object is returned, otherwise a SQLException is thrown.
		// Connection object: Stores the connection information to the DBMS server.
		// SQLException: An exception thrown when a JDBC method encounters an error.
		// => The SQLException object contains error messages related to DBMS issues.
		// URL (Uniform Resource Locator): An address used to represent the location of a resource on the internet.
		// Format: Protocol:ServerName:Port:ResourcePath
		// e.g., https://www.itwill.xyz:80/test/index.html
		// URL to access the global database (XE) on the Oracle DBMS server
		// Format: jdbc:oracle:thin:@ServerName:Port:SID
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "scott";
            String password = "tiger";

            con = DriverManager.getConnection(url, username, password);

		// 3. Call a method on the Connection object to get a Statement object
		// Connection.createStatement(): A method that returns a Statement object for sending SQL commands to the Connection object.
		// Statement object: Provides functionality to send SQL commands to the connected DBMS server.
            stmt = con.createStatement();

		// 4. Call a method on the Statement object to send an SQL command to the DBMS server and execute it, storing the result.
		// Statement.executeUpdate(String sql): Executes a DML (INSERT, UPDATE, DELETE) command and returns the number of affected rows as an int.
		// Statement.executeQuery(String sql): Executes a DQL (SELECT) command and returns a ResultSet object containing the result set.
		// If the SQL command is incorrect, a SQLException is thrown.
		// String sql = "insert into student values(1000,'홍길동','010-1234-5678','서울시 강남구','01/01/01')";
		// String sql = "insert into student values(2000,'임꺽정','010-2378-2341','수원시 월정구','02/05/09')";
            String sql = "insert into student values(3000,'전우치','010-7214-3911','인천시 상당구','1998-12-11')";
            int rows = stmt.executeUpdate(sql);

		// 5. Use the returned integer or ResultSet object to print the result.
            System.out.println("[Message] " + rows + " student(s) information has been inserted and saved.");

        } catch (ClassNotFoundException e) {
            System.out.println("[Error] Could not find OracleDriver class.");
        } catch (SQLException e) {
            System.out.println("[Error] DBMS related error = " + e.getMessage());
        } finally {
            try {
			// 6. Remove all JDBC-related objects - in reverse order of their creation.
			// => Remove JDBC-related objects to close the connection to the DBMS server.
			// Statement.close(): Method to remove the Statement object.
			// => NullPointerException may occur - use an if statement to prevent the exception.
			// NullPointerException: Exception that occurs when a method is called on a reference variable with a NULL value.
                if (stmt != null) stmt.close();
			// Connection.close(): Method to remove the Connection object - closes the DBMS server connection.
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

---

### Update

## Full Code
 A JDBC program to update the name and address of the student with ID [2000] in the STUDENT table.
```java
public class UpdateStudentApp {
    public static void main(String[] args) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "scott";
            String password = "tiger";
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String sql = "update student set name='임걱정', address='부천시 원미구' where no=2000";
            int rows = stmt.executeUpdate(sql);
            System.out.println("Message: " + rows + " student(s) information has been updated.");
        } catch (ClassNotFoundException e) {
            System.out.println("OracleDriver not found");
        } catch (SQLException e) {
            System.out.println("DBMS Error: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

