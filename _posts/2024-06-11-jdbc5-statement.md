---
layout: single
title: 2024/06/10/ JDBC -05 -BadThing Statement ex
---
## Limit of Statement in Java

#### Concept 
This program demonstrates how to use a `Statement` object to execute SQL commands in a Java application. The program has two parts:
**Inserting Student Information**:
**Querying Student Information**:
- good n bad
	- **Advantages**: A single `Statement` object can execute multiple SQL commands.
	- **Disadvantages**: If SQL commands include Java variables, string concatenation is required, reducing readability and maintainability. Moreover, this approach is vulnerable to SQL injection attacks (e.g., injecting SQL fragments instead of expected values).

**Security Note**: This example uses simple string concatenation for SQL commands, making it vulnerable to SQL injection. In production code, consider using `PreparedStatement` to mitigate such risks.

## Full Code
```java
public class StatementApp {
    public static void main(String[] args) throws NumberFormatException, IOException, SQLException {
        /*
        // Uncomment the below section for inserting student information
        // Create an input stream to read student information from the keyboard
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        // Read student information from the keyboard
        System.out.println("<< Enter Student Information >>");
        System.out.print("Enter Student ID: ");
        int no = Integer.parseInt(in.readLine());
        System.out.print("Enter Name: ");
        String name = in.readLine();
        System.out.print("Enter Phone Number: ");
        String phone = in.readLine();
        System.out.print("Enter Address: ");
        String address = in.readLine();
        System.out.print("Enter Birthday (yyyy-MM-dd): ");
        String birthday = in.readLine();
        System.out.println("==============================================================");
        
        // Insert the student information into the STUDENT table
        Connection con = ConnectionFactory.getConnection();
        Statement stmt = con.createStatement();
        String sql1 = "insert into student values(" + no + ",'" + name + "','" + phone + "','" + address + "','" + birthday + "')";
        int rows = stmt.executeUpdate(sql1);
        
        System.out.println(rows + " student's information inserted.");
        System.out.println("==============================================================");
        
        // Query and display all records from the STUDENT table
        String sql2 = "select no, name, phone, address, birthday from student order by no";
        ResultSet rs = stmt.executeQuery(sql2);
        
        System.out.println("<< Student Information >>");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("no") + ", Name: " + rs.getString("name")
                + ", Phone: " + rs.getString("phone") + ", Address: " + rs.getString("address")
                + ", Birthday: " + rs.getString("birthday").substring(0, 10));
        }
        System.out.println("==============================================================");
        ConnectionFactory.close(con, stmt, rs);
        */

        // Query student information by name
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("<< Query Student Information >>");
        System.out.print("Enter Name: ");
        String name = in.readLine();
        System.out.println("==============================================================");
        
        // Query and display records from the STUDENT table with the given name
        Connection con = ConnectionFactory.getConnection();
        Statement stmt = con.createStatement();
        
        // Vulnerable to SQL injection if malicious input is provided
        String sql = "select no, name, phone, address, birthday from student where name='" + name + "' order by no";
        
        ResultSet rs = stmt.executeQuery(sql);
        
        System.out.println("<< Query Results >>");
        
        if (rs.next()) {
            do {
                System.out.println("ID: " + rs.getInt("no") + ", Name: " + rs.getString("name")
                    + ", Phone: " + rs.getString("phone") + ", Address: " + rs.getString("address")
                    + ", Birthday: " + rs.getString("birthday").substring(0, 10));
            } while (rs.next());
        } else {
            System.out.println("No student information found for the entered name.");
        }
        System.out.println("==============================================================");
        ConnectionFactory.close(con, stmt, rs);
    }
}
```