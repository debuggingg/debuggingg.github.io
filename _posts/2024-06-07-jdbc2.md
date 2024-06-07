---
layout: categories
title: 2024/06/07/ JDBC -02 - Select
permalink: /categories/jdbc/
---

---

# JDBC Select Example
---


This Java program demonstrates how to retrieve and display all student records from the `STUDENT` table in a database. The records are fetched and displayed in ascending order based on student IDs .

#### Concepts:
- **JDBC (Java Database Connectivity):** A Java API that allows Java programs to interact with databases.
  - **Connection Class:** Establishes a connection to the database.
  - **Statement Class:** Used to execute SQL queries.
  - **ResultSet Class:** Represents the result set of a query.

- **OracleDriver Class:** Connects to an Oracle database.
  - **Class.forName:** Loads the Oracle JDBC driver.
  
- **DBMS (Database Management System):** Manages data in a structured way and allows CRUD operations.

---

#### Program Flow:
1. **Initialize Database Connection:**
   - Load the Oracle JDBC driver.
   - Create a connection to the database using `DriverManager`.

2. **Create Statement Object:**
   - Create a `Statement` object to execute SQL queries.

3. **Execute SQL Query:**
   - Execute a SELECT query to fetch all student records ordered by student ID.

4. **Process ResultSet:**
   - Iterate through the `ResultSet` to retrieve and print student information.

5. **Close Resources:**
   - Close the `ResultSet`, `Statement`, and `Connection` objects to release resources.

---

## Full Code:

```java


public class SelectStudentApp {
    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Connect to the database
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "scott";
            String password = "tiger";
            con = DriverManager.getConnection(url, username, password);

            // Create a Statement object
            stmt = con.createStatement();

            // Execute a SELECT query to fetch all student records ordered by student ID
            String sql = "select no, name, phone, address, birthday from student order by no";
            rs = stmt.executeQuery(sql);

            // Process the ResultSet
            if (rs.next()) {
                do {
                    // Retrieve column values from the current row
                    int no = rs.getInt("no");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String birthday = rs.getString("birthday");

                    // Print the student information
                    System.out.println("id = " + no);
                    System.out.println("name = " + name);
                    System.out.println("phone = " + phone);
                    System.out.println("address = " + address);
                    // Print the birthday (substring to show only date part)
                    System.out.println("birthday = " + birthday.substring(0, 10));
                    System.out.println("==================================================");
                } while (rs.next());
            } else {
                System.out.println("[result]No student information selected");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("[error]OracleDriver no founded ");
        } catch (SQLException e) {
            System.out.println("[error]DBMS error = " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

---

- **Creating Database Connection:**
  - `Class.forName("oracle.jdbc.driver.OracleDriver");`
    - Loads the Oracle JDBC driver.
  - `con = DriverManager.getConnection(url, username, password);`
    - Establishes a connection to the database.

- **Creating and Executing Statement:**
  - `stmt = con.createStatement();`
    - Creates a `Statement` object.
  - `rs = stmt.executeQuery(sql);`
    - Executes the SELECT query and returns the result set.

- **Processing ResultSet:**
  - `if (rs.next()) { ... }`
    - Checks if there are any records.
  - `do { ... } while (rs.next());`
    - Iterates through the result set and retrieves column values using `getInt`, `getString`, etc.
  - `System.out.println(...)`
    - Prints the retrieved values to the console.

- **Closing Resources:**
  - `if (rs != null) rs.close();`
    - Closes the `ResultSet`.
  - `if (stmt != null) stmt.close();`
    - Closes the `Statement`.
  - `if (con != null) con.close();`
    - Closes the `Connection`.

---

#### Key Points:
- Demonstrates how to retrieve and display records from a database table using JDBC.
- Ensures resources are properly closed to prevent resource leaks.
- Handles exceptions to manage errors related to database connectivity and operations.