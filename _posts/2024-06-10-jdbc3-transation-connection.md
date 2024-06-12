---
layout: single
title: 2024/06/10/ JDBC -03 -TransactionControl-ConnectionFactory
---
---
## **TransactionControl**

The provided JDBC program demonstrates how to handle database transactions manually.  commit or rollback depending on the success or failure of the operation.

- Key Point = Autocommit false - manually commit for sql 
```
 con.setAutoCommit(false);
 .
 .
 .
 con.commit(); 
```

## Program Flow
**Disabling Auto-Commit**:

- The auto-commit feature of the connection is disabled with `con.setAutoCommit(false)`. This means that changes will not be automatically committed to the database and need to be explicitly committed.
- **Commit or Rollback**:

- If no exceptions occur, `con.commit()` is called to commit the transaction.
- If an exception occurs, the transaction is rolled back using `con.rollback()` in the catch block.
## Full Code
public class TransactionControlApp {
 ```java
   public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "scott";
            String password = "tiger";
            con = DriverManager.getConnection(url, username, password);

            // Disable auto-commit
            con.setAutoCommit(false);

            stmt = con.createStatement();

            String sql = "update student set name='임꺽정' where no=2000";
            int rows = stmt.executeUpdate(sql);

            // if(con!=null) throw new Exception(); // Uncomment to simulate an exception

            if (rows > 0) {
                System.out.println("[메세지]" + rows + "명의 학생정보를 변경 하였습니다.");
            } else {
                System.out.println("[메세지]해당 학번의 학생정보를 찾을 수 없습니다.");
            }

            // Commit the transaction
            con.commit();
        } catch (ClassNotFoundException e) {
            System.out.println("[에러]OracleDriver 클래스를 찾을 수 없습니다.");
        } catch (SQLException e) {
            System.out.println("[에러]DBMS 관련 오류 = " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[에러]프로그램 실행에 예기치 못한 오류가 발생 되었습니다.");
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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

---
## ConnectionFactory -Class for shortcut 

- The `ConnectionFactory` class provides utility methods to create and close JDBC-related objects, aiming to **reduce code duplication and enhance productivity and maintainability** in JDBC programs.
	- The `ConnectionFactory` class is designed to create and return `Connection` objects and to close JDBC-related objects passed as parameters. This class is intended to **encapsulate** commonly executed commands in JDBC programs into methods, minimizing code duplication and enhancing productivity and maintainability.
## Program Flow 
- **Create Connection**:
    
    - `getConnection()`: A static method that creates and returns a `Connection` object.
- **Close Resources**:
    
    - `close(Connection con)`: Closes the provided `Connection` object.
    - `close(Connection con, Statement stmt)`: Closes the provided `Statement` and `Connection` objects.
    - `close(Connection con, Statement stmt, ResultSet rs)`: Closes the provided `ResultSet`, `Statement`, and `Connection` objects.
## Full Code
```java
public class ConnectionFactory {
    // Static method to create and return a Connection object
    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "scott";
            String password = "tiger";
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) { // Catching all exceptions to handle errors
            System.out.println("[Error] Cannot create Connection object.");
        }

        return con;
    }

    // Static method to close a Connection object passed as a parameter - method overloading
    public static void close(Connection con) {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection con, Statement stmt) {
        try {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection con, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

