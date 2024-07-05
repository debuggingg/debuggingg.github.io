---
layout: single
title: 2024/07/04 Servlet 04-DAO-DTO-JDBC
---
### DBCP

**DBCP (DataBase Connection Pool):**
- A DBCP object manages a pool of pre-created `Connection` objects.
- The `DataSource` interface is extended to create a DBCP object.

This servlet uses classes from the Apache `tomcat-dbcp` library to create a DBCP object (`DataSource` object). It then retrieves a `Connection` object from the pool and includes its information in an HTML response.

### Program Flow

1. **Initialization:**
   - The servlet is mapped to the URL pattern `/dbcp.itwill`.

2. **Request Handling:**
   - The servlet handles requests using the `service` method.

3. **DataSource Object Creation:**
   - A `BasicDataSource` object is created and configured:
     - Set the JDBC driver class name.
     - Set the database URL.
     - Set the database username.
     - Set the database password.
     - Set the initial number of connections.
     - Set the maximum number of idle connections.
     - Set the maximum number of total connections.

4. **HTML Response:**
   - The servlet generates an HTML response to display connection pool information.

5. **Connection Handling:**
   - Retrieve a `Connection` object from the `DataSource` using `dataSource.getConnection()`.
   - Display the `Connection` object information.
   - Display the number of idle and active connections.
   - Close the `Connection` object.
   - Display the number of idle and active connections after closing the `Connection`.

6. **DataSource Cleanup:**
   - Close the `DataSource` object using `dataSource.close()`.

## Full Code

```java
// DBCP (DataBase Connection Pool) Object: An object that manages a pool of pre-created Connection objects
// => Create a DataSource object using a subclass of the DataSource interface

// This servlet uses classes from the Apache tomcat-dbcp library to create a DBCP object (DataSource object)
// and includes the information of the Connection object in an HTML response
@WebServlet("/dbcp.itwill")
public class DataSourceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        // Create a BasicDataSource object (DataSource object) and store it
        BasicDataSource dataSource = new BasicDataSource();

        // Call methods to change the information (field values) of the BasicDataSource object
        // to create and store Connection objects in the BasicDataSource object
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        dataSource.setUsername("scott");
        dataSource.setPassword("tiger");
        dataSource.setInitialSize(10); // Change the number of initially created Connection objects
        dataSource.setMaxIdle(10); // Change the maximum number of idle Connection objects
        dataSource.setMaxTotal(15); // Change the maximum number of total Connection objects

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>DBCP (DataBase Connection Pool)</h1>");
        out.println("<hr>");
        try {
            // DataSource.getConnection(): Returns an idle Connection object from the DataSource object
            Connection con = dataSource.getConnection();
            out.println("<p>con = " + con + "</p>");
            out.println("<hr>");
            out.println("<h3>After Providing Connection Object</h3>");
            // DataSource.getNumIdle(): Returns the number of idle Connection objects from the DataSource object
            out.println("<p>Idle Connection Number = " + dataSource.getNumIdle() + "</p>");
            // DataSource.getNumActive(): Returns the number of active Connection objects from the DataSource object
            out.println("<p>Active Connection Number = " + dataSource.getNumActive() + "</p>");
            out.println("<hr>");
            con.close();
            out.println("<h3>After Removing Connection Object</h3>");
            out.println("<p>Idle Connection Number = " + dataSource.getNumIdle() + "</p>");
            out.println("<p>Active Connection Number = " + dataSource.getNumActive() + "</p>");

            // DataSource.close(): Deletes the DataSource object
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</body>");
        out.println("</html>");
    }
}
```

---
## Jndi - Create DTO

## Ex) Student Table DB Connect . 
```java

//JNDI (Java Naming and Directory Interface): A service provided by the WAS (Web Application Server) to manage objects by creating them upon server startup and providing them when requested by name.
// => Configuration of objects managed by the WAS is provided using elements (tags) in the [src/main/webapp/META-INF/context.xml] file.

// A servlet that retrieves a DataSource object managed by the WAS, obtains a Connection object from it,
// and includes the Connection object's information in the HTML response.
@WebServlet("/jndi.itwill")
public class JndiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		
		try {
			//InitialContext object: An object provided for JNDI services.
			// => Provides methods to retrieve managed objects from the WAS.
			//InitialContext.lookup(String name): Returns the object managed by the WAS with the given name.
			// => Returns an Object, so explicit casting is needed.
			// => Throws NamingException if no object with the given name exists.
			DataSource dataSource=(DataSource)new InitialContext().lookup("java:comp/env/jdbc/oracle");
			
			Connection con=dataSource.getConnection();
			
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset='utf-8'>");
			out.println("<title>Servlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>JNDI</h1>");
			out.println("<hr>");
			out.println("<p>Connection = "+con+"</p>");
			out.println("</body>");
			out.println("</html>");
			
			con.close();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- context.xml: Configuration file storing information about objects to be created when the WAS program starts -->
<!-- Context: Top-level element in context.xml - Contains Resource elements as child elements -->
<Context>
	<!-- Resource: Element providing information to create an object -->
	<!-- => Configure object-related information using attributes and attribute values of Resource elements -->
	<!-- name attribute: Set to differentiate objects -->
	<!-- auth attribute: Set to manage user name for the object -->
	<!-- factory attribute: Set to specify the Factory class for object creation -->
	<!-- => Alternatively, use the class attribute to specify the class for creating objects -->
	<!-- type attribute: Set to specify the Java data type of the object provided as a Resource element -->
	<!-- Use attributes (field names) and attribute values (field values) to configure object creation - Modify field values -->
	<Resource name="jdbc/oracle" auth="Container"
		factory="org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory"
		type="javax.sql.DataSource"
		driverClassName="oracle.jdbc.driver.OracleDriver"
		url="jdbc:oracle:thin:@localhost:1521:xe" username="scott" password="tiger"
		initialSize="10" maxIdle="10" maxTotal="15"/>
</Context>
```
---


---
## JDBC DAO

```java
// Parent class designed for DAO classes that inherit JDBC functionality.
// => It is recommended to implement this as an abstract class (a class designed for inheritance).
// => Retrieves and stores a DataSource object managed by the WAS program for use in fields.

public abstract class JdbcDAO {
	private static DataSource dataSource;

	static {
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	// Retrieves a connection from the DataSource object managed by the WAS program.
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	// Closes the provided Connection object.
	public void close(Connection con) {
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Closes the provided PreparedStatement and Connection objects.
	public void close(Connection con, PreparedStatement pstmt) {
		try {
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Closes the provided ResultSet, PreparedStatement, and Connection objects.
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
```
---
## Student DAO

```java
// DAO(Data Access Object) class: Provides functionality to insert, update, delete, and search rows in a table
// by executing SQL commands and mapping the results to Java objects.
// => It is recommended to implement this as a singleton class (a class with only one instance).

// Provides functionality to insert, update, delete, and search rows (student information) in the STUDENT table
// => Executes a single SQL command on the DBMS server and returns the result as a Java object.
public class StudentDAO extends JdbcDAO {
	private static StudentDAO _dao;
	
	private StudentDAO() {
	}
	
	/*
	static {
		_dao=new StudentDAO(); 
	}
	*/
	
	public static StudentDAO getDAO() {
		if(_dao == null) {
			_dao=new StudentDAO();
		}
		return _dao;
	}
	
	// Retrieves all rows (student information) stored in the STUDENT table and returns them as a List object.
	public List<StudentDTO> selectStudentList() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<StudentDTO> studentList=new ArrayList<StudentDTO>();
		try {
			con=getConnection();
			
			String sql="select no, name, phone, address, birthday from student order by no";
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				StudentDTO student=new StudentDTO();
				student.setNo(rs.getInt("no"));
				student.setName(rs.getString("name"));
				student.setPhone(rs.getString("phone"));
				student.setAddress(rs.getString("address"));
				student.setBirthday(rs.getString("birthday"));
				
				// Adds the element (StudentDTO object) to the List object.
				studentList.add(student);
			}
		} catch (SQLException e) {
			System.out.println("[Error] SQL error in selectStudentList() method: "+e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return studentList;
	}
}
```
---

## Student program 
```java

// Servlet that retrieves all rows (student information) stored in the STUDENT table
// and includes them in an HTML document response using methods from the StudentDAO class.
@WebServlet("/new.itwill")
public class StudentDisplayNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        // Calling the method of the StudentDAO class to retrieve all rows (student information)
        // stored in the STUDENT table and return them as a List object.
        List<StudentDTO> studentList = StudentDAO.getDAO().selectStudentList();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>학생목록</h1>");
        out.println("<hr>");
        out.println("<table border='1' cellspacing='0'>");
        out.println("<tr>");
        out.println("<th width='100'>학번</th>");
        out.println("<th width='150'>이름</th>");
        out.println("<th width='200'>전화번호</th>");
        out.println("<th width='300'>주소</th>");
        out.println("<th width='250'>생년월일</th>");
        out.println("</tr>");

        // Loop through each element (StudentDTO object) stored in the List object.
        for (StudentDTO student : studentList) {
            out.println("<tr>");
            out.println("<td align='center'>" + student.getNo() + "</td>");
            out.println("<td align='center'>" + student.getName() + "</td>");
            out.println("<td align='center'>" + student.getPhone() + "</td>");
            out.println("<td align='center'>" + student.getAddress() + "</td>");
            out.println("<td align='center'>" + student.getBirthday().substring(0, 10) + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
```



