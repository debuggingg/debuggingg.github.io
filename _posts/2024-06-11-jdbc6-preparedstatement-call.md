---
layout: single
title: 2024/06/10/ JDBC -06-PreparedStatement-CallableStatement
---
## Prepared Statement 

This program demonstrates the use of a `PreparedStatement` object to execute SQL commands in a Java application. The use of `PreparedStatement` provides several advantages over `Statement`:

- **Advantages**:
    - Allows inclusion of Java variable values in SQL commands using placeholders (`?`), improving readability and maintainability.
    - The placeholder values are treated as strings, mitigating SQL injection risks.
- **Disadvantages**:
    - Each `PreparedStatement` object can only execute one SQL command at a time.

- **Inserting Data**: Uncomment the initial block to enable student data insertion.
- **Querying Data**: The provided code queries the database for student records by name using a `PreparedStatement`.
- **Security Note**: The use of `PreparedStatement` helps prevent SQL injection attacks by treating placeholder values as strings.
#### Program  Flow 

program has two parts:
1. **Inserting Student Information**: Reads student details from the keyboard and inserts them into the `STUDENT` table.
2. **Querying Student Information**: Reads a student's name from the keyboard and queries the `STUDENT` table to find and display matching records.


---
## Full Code
```java
public class PreparedStatementApp {
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
        Connection con = ConnectionFactory.getConnection();// ConnectionFactory class - is already created for connecting java to sql (userMade)
        String sql1 = "insert into student values(?,?,?,?,?)"; // Incomplete SQL command
        PreparedStatement pstmt = con.prepareStatement(sql1); // SQL command with placeholders
        
        // Assign values to the placeholders in the SQL command
        pstmt.setInt(1, no);
        pstmt.setString(2, name);
        pstmt.setString(3, phone);
        pstmt.setString(4, address);
        pstmt.setString(5, birthday);
        
        // Execute the SQL command and get the number of affected rows
        int rows = pstmt.executeUpdate();
        
        System.out.println(rows + " student's information inserted.");
        System.out.println("==============================================================");
        
        // Query and display all records from the STUDENT table
        String sql2 = "select no, name, phone, address, birthday from student order by no";
        pstmt = con.prepareStatement(sql2);
        ResultSet rs = pstmt.executeQuery();
        
        System.out.println("<< Student Information >>");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("no") + ", Name: " + rs.getString("name")
                + ", Phone: " + rs.getString("phone") + ", Address: " + rs.getString("address")
                + ", Birthday: " + rs.getString("birthday").substring(0, 10));
        }
        System.out.println("==============================================================");
        ConnectionFactory.close(con, pstmt, rs);
        */

        // Query student information by name
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("<< Query Student Information >>");
        System.out.print("Enter Name: ");
        String name = in.readLine();
        System.out.println("==============================================================");
        
        // Query and display records from the STUDENT table with the given name
        Connection con = ConnectionFactory.getConnection();
        String sql = "select no, name, phone, address, birthday from student where name=? order by no";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, name);

        ResultSet rs = pstmt.executeQuery();

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
        ConnectionFactory.close(con, pstmt, rs);
    }
}
```