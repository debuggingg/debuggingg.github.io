---
layout: single
title: 2024/06/10 JDBC-SQL App
---
## SQL Minus APP
Java JDBC program, which simulates a SQL command-line tool like `sqlplus`, hence named `sqlminus`.

### Explanation:

1. **Program Initialization**:
    
    - Creates a `BufferedReader` to read user input from the keyboard.
    - Establishes a database connection using a `ConnectionFactory`.
    - Initializes a `Statement` object for executing SQL commands.
2. **Main Loop**:
    
    - Prompts the user for SQL commands.
    - Reads the input, trims whitespace, and checks for the `exit` command to terminate the loop.
    - Differentiates between `SELECT` and other SQL commands using `stmt.execute(sql)`:
        - For `SELECT` commands, processes the `ResultSet` to display the results.
        - For `INSERT`, `UPDATE`, and `DELETE` commands, displays the number of affected rows.
3. **Error Handling**:
    
    - Catches and displays SQL errors for invalid commands.
4. **Cleanup**:
    
    - Closes the database connection and related resources.


## Full Code

```java
public class SqlMinusApp {
    public static void main(String[] args) throws SQLException, IOException {
        // Create and store input stream to read SQL commands from keyboard
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        Connection con = ConnectionFactory.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = null;

        System.out.println("[Message] SQLMinus program is starting. (Exit: exit)");

        // Loop to read SQL commands from keyboard, send them to DBMS server, execute, and print results
        while (true) {
            System.out.print("SQL> ");
            // Read SQL command from keyboard and store after trimming leading and trailing spaces
            String sql = in.readLine().trim();

            // If no input, restart loop
            if (sql == null || sql.equals("")) continue;

            // If input is "exit", break loop and end program
            if (sql.equalsIgnoreCase("exit")) break;

            try {
                // Use execute(String sql) method of Statement object to send SQL command to DBMS server
                // and differentiate between SELECT and other commands using the returned boolean
                if (stmt.execute(sql)) { // If a SELECT command
                    rs = stmt.getResultSet();

                    if (rs.next()) { // If there are rows in the result set
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnCount = rsmd.getColumnCount();

                        System.out.println("==================================================================");
                        // Loop to print column labels
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(rsmd.getColumnLabel(i) + "\t");
                        }
                        System.out.println();
                        System.out.println("==================================================================");
                        // Loop to print column values for each row
                        do {
                            for (int i = 1; i <= columnCount; i++) {
                                String columnValue = rs.getString(i);

                                // If column type is DATE, format it as [yyyy-MM-dd]
                                if (rsmd.getColumnTypeName(i).equals("DATE")) {
                                    columnValue = columnValue.substring(0, 10);
                                }

                                if (columnValue == null) { // If column value is null
                                    columnValue = "";
                                }

                                System.out.print(columnValue + "\t");
                            }
                            System.out.println();
                        } while (rs.next());

                    } else { // If no rows in the result set
                        System.out.println("No rows found.");
                    }
                } else { // If not a SELECT command
                    int rows = stmt.getUpdateCount();
                    System.out.println(rows + " row(s) " + sql.substring(0, 6).toUpperCase() + "ed.");
                }
            } catch (SQLException e) {
                // Handle SQL exception for incorrect SQL commands
                System.out.println("SQL error = " + e.getMessage());
            }
        }

        ConnectionFactory.close(con, stmt, rs);
        System.out.println("[Message] SQLMinus program has terminated.");
    }
}
```