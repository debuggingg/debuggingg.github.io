---
layout: single
title: 2024/07/10 JSP 09-EX-Student
---
### Using JSP, and Java DAO,DTO-DB Insert,update,select,delete 

The file that must be configured before creating the DAO and DTO
WEB-INF  -> lib->ojdbc11.jar
META-INF ->context.xml => setting can be different

### Student DTO 

\Columns      Null?       Type            
-------- -------- ------------- 
NO       NOT NULL NUMBER(4)     
NAME              VARCHAR2(50)  
PHONE             VARCHAR2(20)  
ADDRESS           VARCHAR2(100) 
BIRTHDAY          DATE     

Field Name - 
	private int no;
	private String name;
	private String phone;
	private String address;
	private String birthday;

## Student DAO - JavaClass

```java
// DAO class providing functions to insert, update, delete, and search rows in the STUDENT table
public class StudentDAO extends JdbcDAO {
    private static StudentDAO _dao;
    
    private StudentDAO() {
        // TODO Auto-generated constructor stub
    }

    static {
        _dao = new StudentDAO();
    }
    
    public static StudentDAO getDAO() {
        return _dao;
    }
    
    // Method that receives student information (StudentDTO object), inserts it as a row in the STUDENT table, and returns the number of inserted rows (int)
    public int insertStudent(StudentDTO student) {
        Connection con = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            con = getConnection();
            
            String sql = "insert into student values(?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, student.getNo());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getPhone());
            pstmt.setString(4, student.getAddress());
            pstmt.setString(5, student.getBirthday());
            
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Error] insertStudent method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt);
        }
        return rows;
    }
    
    // Method that receives student information (StudentDTO object), updates the row in the STUDENT table, and returns the number of updated rows (int)
    public int updateStudent(StudentDTO student) {
        Connection con = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            con = getConnection();
            
            String sql = "update student set name=?, phone=?, address=?, birthday=? where no=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getPhone());
            pstmt.setString(3, student.getAddress());
            pstmt.setString(4, student.getBirthday());
            pstmt.setInt(5, student.getNo());
            
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Error] updateStudent method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt);
        }
        return rows;
    }
    
    // Method that receives a student number (int), deletes the row in the STUDENT table, and returns the number of deleted rows (int)
    public int deleteStudent(int no) {
        Connection con = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            con = getConnection();
            
            String sql = "delete from student where no=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, no);
            
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Error] deleteStudent method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt);
        }
        return rows;
    }
    
    // Method that receives a student number (int), searches the STUDENT table, and returns the found student information (StudentDTO object)
    public StudentDTO selectStudent(int no) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StudentDTO student = null;
        try {
            con = getConnection();
            
            String sql = "select no, name, phone, address, birthday from student where no=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, no);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                student = new StudentDTO();
                student.setNo(rs.getInt("no"));
                student.setName(rs.getString("name"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                student.setBirthday(rs.getString("birthday"));
            }
        } catch (SQLException e) {
            System.out.println("[Error] selectStudent method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt, rs);
        }
        return student;
    }
    
    // Method that searches the STUDENT table for all rows and returns the found student information (List object)
    public List<StudentDTO> selectStudentList() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<StudentDTO> studentList = new ArrayList<StudentDTO>();
        try {
            con = getConnection();
            
            String sql = "select no, name, phone, address, birthday from student order by no";
            pstmt = con.prepareStatement(sql);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                StudentDTO student = new StudentDTO();
                student.setNo(rs.getInt("no"));
                student.setName(rs.getString("name"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                student.setBirthday(rs.getString("birthday"));
                
                studentList.add(student);
            }
        } catch (SQLException e) {
            System.out.println("[Error] selectStudentList method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt, rs);
        }
        return studentList;
    }
}
```

## StudentDisplay -jsp


```jsp
<%@page import="xyz.itwill.dto.StudentDTO"%>
<%@page import="java.util.List"%>
<%@page import="xyz.itwill.dao.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that searches all rows (student information) stored in the STUDENT table and responds by including them in HTML tags --%>
<%-- => When the [Add Student] tag is clicked, request the [insertFormStudent.jsp] document to navigate the page --%>    
<%-- => When the [Delete] tag of the student information is clicked, request the [deleteStudent.jsp] document to navigate the page - pass the student number --%>    
<%-- => When the [Update] tag of the student information is clicked, request the [updateFormStudent.jsp] document to navigate the page - pass the student number --%>
<%
    // Call the method of the StudentDAO class that searches all rows stored in the STUDENT table and returns them as a List object
    List<StudentDTO> studentList = StudentDAO.getDAO().selectStudentList();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<style type="text/css">
h1 {
    margin: 0 auto;
    width: 850px; 
    text-align: center; 
}

div {
    margin: 10px auto;
    width: 850px;
    text-align: right;
}

table {
    margin: 0 auto;
    border: 1px solid black;
    border-collapse: collapse;     
}

th {
    background-color: black;
    color: white; 
}

th, td {
    border: 1px solid black;
    text-align: center;
    padding: 5px;    
}

.no { width: 100px; }
.name { width: 100px; }
.phone { width: 150px; }
.address { width: 250px; }
.birthday { width: 150px; }
.remove { width: 50px; }
.modify { width: 50px; }
</style>
</head>
<body>
    <h1>Student List</h1>
    <div>
        <button type="button" id="addBtn">Add Student</button>
    </div>
    <table>
        <tr>
            <th class="no">Student Number</th>
            <th class="name">Name</th>
            <th class="phone">Phone Number</th>
            <th class="address">Address</th>
            <th class="birthday">Birthday</th>
            <th class="remove">Delete</th>
            <th class="modify">Update</th>
        </tr>
        <%-- Loop to sequentially receive the elements (StudentDTO objects) stored in the List object and store them in a variable --%>
        <%-- => Get the field values of the StudentDTO object and output them by including them in HTML tags --%> 
        <% for(StudentDTO student : studentList) { %>
        <tr align="center">
            <td><%= student.getNo() %></td>                
            <td><%= student.getName() %></td>                
            <td><%= student.getPhone() %></td>                
            <td><%= student.getAddress() %></td>                
            <td><%= student.getBirthday().substring(0, 10) %></td>                
            <td><button type="button" onclick="removeStudent(<%= student.getNo() %>);">Delete</button></td>        
            <td><button type="button" onclick="modifyStudent(<%= student.getNo() %>);">Update</button></td>        
        </tr>    
        <% } %>
    </table>
    
    <script type="text/javascript">
    document.getElementById("addBtn").onclick = function() {
        location.href = "<%= request.getContextPath() %>/student/insertFormStudent.jsp";    
    }
    
    function modifyStudent(no) {
        //alert(no);
        location.href = "<%= request.getContextPath() %>/student/updateFormStudent.jsp?no=" + no;    
    }
    
    function removeStudent(no) {
        if(confirm("Are you sure you want to delete the student information?")) {
            location.href = "<%= request.getContextPath() %>/student/deleteStudent.jsp?no=" + no;
        }
    }
    </script>
</body>
</html>
```

## StudentInsetForm -jsp

Here is the translation of the provided JSP document into English:

```jsp
<%@page import="xyz.itwill.dto.StudentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document to receive student information from the user --%>
<%-- => When the [Add Student] tag is clicked, request the [insertStudent.jsp] document and move the page - input value (student information) is sent --%>
<%-- => When the [Student List] tag is clicked, request the [displayStudent.jsp] document and move the page --%>
<%
    String message = (String) session.getAttribute("message");
    if(message == null) {
        message = "";
    } else {
        session.removeAttribute("message");
    }
    
    StudentDTO student = (StudentDTO) session.getAttribute("student");
    if(student != null) {
        session.removeAttribute("student");
    }
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<style type="text/css">
h1 {
    margin: 0 auto;
    width: 300px; 
    text-align: center; 
}

table {
    margin: 0 auto;
    border: 1px solid black;
    border-collapse: collapse;     
}

th, td {
    border: 1px solid black;
    text-align: center;
    padding: 5px;    
}

.title { width: 100px; }
.input { width: 200px; }
</style>
</head>
<body>
    <h1>Enter Student Information</h1>
    <hr>
    <form name="studentForm" action="<%=request.getContextPath()%>/student/insertStudent.jsp" method="post">
    <table>
        <tr>
            <th class="title">Student Number</th>
            <td class="input">
                <input type="text" name="no" <% if(student != null) { %>value="<%=student.getNo()%>"<% } %>>
            </td>
        </tr>
        <tr>
            <th class="title">Name</th>
            <td class="input">
                <input type="text" name="name" <% if(student != null) { %>value="<%=student.getName()%>"<% } %>>
            </td>
        </tr>
        <tr>
            <th class="title">Phone Number</th>
            <td class="input">
                <input type="text" name="phone" <% if(student != null) { %>value="<%=student.getPhone()%>"<% } %>>
            </td>
        </tr>
        <tr>
            <th class="title">Address</th>
            <td class="input">
                <input type="text" name="address" <% if(student != null) { %>value="<%=student.getAddress()%>"<% } %>>
            </td>
        </tr>
        <tr>
            <th class="title">Date of Birth</th>
            <td class="input">
                <input type="text" name="birthday" <% if(student != null) { %>value="<%=student.getBirthday()%>"<% } %>>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit">Add Student</button> 
                <button type="reset">Reset</button> 
                <button type="button" id="listBtn">Student List</button> 
            </td>
        </tr>
    </table>
    </form>
    <p align="center" style="color: red;"><%=message %></p>
    
    <script type="text/javascript">
    studentForm.no.focus();

    studentForm.onsubmit = function() {
        if(studentForm.no.value == "") {
            alert("Please enter the student number.");
            studentForm.no.focus();
            return false;
        }
        
        var noReg = /\d{4}/g;
        if(!noReg.test(studentForm.no.value)) {
            alert("Please enter a 4-digit integer for the student number.");
            studentForm.no.focus();
            return false;
        }
        
        if(studentForm.name.value == "") {
            alert("Please enter the name.");
            studentForm.name.focus();
            return false;
        }

        if(studentForm.phone.value == "") {
            alert("Please enter the phone number.");
            studentForm.phone.focus();
            return false;
        }

        var phoneReg = /(01[016789])-\d{3,4}-\d{4}/g;
        if(!phoneReg.test(studentForm.phone.value)) {
            alert("Please enter the phone number in the correct format.");
            studentForm.phone.focus();
            return false;
        }
        
        if(studentForm.address.value == "") {
            alert("Please enter the address.");
            studentForm.address.focus();
            return false;
        }

        if(studentForm.birthday.value == "") {
            alert("Please enter the date of birth.");
            studentForm.birthday.focus();
            return false;
        }
        
        var birthdayReg = /(18|19|20)\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])/g;
        if(!birthdayReg.test(studentForm.birthday.value)) {
            alert("Please enter the date of birth in the correct format.");
            studentForm.birthday.focus();
            return false;
        }
    } 
    
    document.getElementById("listBtn").onclick = function() {
        location.href = "<%=request.getContextPath()%>/student/displayStudent.jsp";    
    }
    </script>

</body>
</html>
```


## StudentInsert -jsp


```jsp
<%@page import="xyz.itwill.dto.StudentDTO"%>
<%@page import="xyz.itwill.dao.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that receives student information, inserts it into a row in the STUDENT table, and responds with a URL address to request the [displayStudent.jsp] document --%>    
<%
    // Response processing for abnormal requests
    if(request.getMethod().equals("GET")) {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return;
    }

    // Change the character encoding to read the values sent via POST method
    request.setCharacterEncoding("utf-8");
    
    // Retrieve and store the sent values
    int no = Integer.parseInt(request.getParameter("no"));
    String name = request.getParameter("name");
    String phone = request.getParameter("phone");
    String address = request.getParameter("address");
    String birthday = request.getParameter("birthday");
    
    // Create a StudentDTO object and set its field values with the sent values
    StudentDTO student = new StudentDTO();
    student.setNo(no);
    student.setName(name);
    student.setPhone(phone);
    student.setAddress(address);
    student.setBirthday(birthday);
    
    // Issue: If the provided student number is already present in the STUDENT table, 
    // it will violate the PRIMARY KEY constraint, making insertion impossible - SQLException occurs.
    // Solution: If the provided student number is already present in the STUDENT table,
    // respond with a URL address to request the [insertFormStudent.jsp] document.
    
    // Call the method of the StudentDAO class that receives a student number,
    // searches for a row in the STUDENT table, and returns the found student information (StudentDTO object).
    // => Returns null: Student information not found - student number not duplicated, 
    //    Returns StudentDTO object: Student information found - student number duplicated.
    if(StudentDAO.getDAO().selectStudent(no) != null) { // If the student number is duplicated
        session.setAttribute("message", "The entered student number is already in use. Please enter again.");
        session.setAttribute("student", student);
        response.sendRedirect(request.getContextPath() + "/student/insertFormStudent.jsp");
        return;
    }
    
    // Call the method of the StudentDAO class that receives student information (StudentDTO object),
    // inserts it into a row in the STUDENT table, and returns the number of inserted rows.
    StudentDAO.getDAO().insertStudent(student);
    
    // Send a URL address to the client and respond
    response.sendRedirect(request.getContextPath() + "/student/displayStudent.jsp");
%>
```
### StudentUpdateForm - jsp
Here is the translation of the provided JSP document into English:

```jsp
<%@page import="xyz.itwill.dao.StudentDAO"%>
<%@page import="xyz.itwill.dto.StudentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document for receiving the updated student information from the user --%>    
<%-- => Receives the student number, queries the STUDENT table for the corresponding row, and populates the input tags with the retrieved student information --%>
<%-- => When the [Update Student] tag is clicked, request the [updateStudent.jsp] document and move the page - input values (student information) are sent --%>    
<%-- => When the [Student List] tag is clicked, request the [displayStudent.jsp] document and move the page --%>
<%
    // Handling abnormal requests
    if(request.getParameter("no") == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }

    // Retrieve and store the parameter value
    int no = Integer.parseInt(request.getParameter("no"));
    
    // Calls the method of the StudentDAO class that takes the student number, 
    // queries the STUDENT table for a corresponding row, and returns the student information (StudentDTO object)
    StudentDTO student = StudentDAO.getDAO().selectStudent(no);
    
    // Handling abnormal requests
    if(student == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<style type="text/css">
h1 {
    margin: 0 auto;
    width: 300px; 
    text-align: center; 
}

table {
    margin: 0 auto;
    border: 1px solid black;
    border-collapse: collapse;     
}

th, td {
    border: 1px solid black;
    text-align: center;
    padding: 5px;    
}

.title { width: 100px; }
.input { width: 200px; }
</style>
</head>
<body>
    <h1>Update Student Information</h1>
    <hr>
    <form name="studentForm" action="<%=request.getContextPath()%>/student/updateStudent.jsp" method="post">
    <table>
        <tr>
            <th class="title">Student Number</th>
            <td class="input">
                <input type="text" name="no" value="<%=student.getNo() %>" readonly="readonly">
            </td>
        </tr>
        <tr>
            <th class="title">Name</th>
            <td class="input">
                <input type="text" name="name" value="<%=student.getName() %>">
            </td>
        </tr>
        <tr>
            <th class="title">Phone Number</th>
            <td class="input">
                <input type="text" name="phone" value="<%=student.getPhone() %>">
            </td>
        </tr>
        <tr>
            <th class="title">Address</th>
            <td class="input">
                <input type="text" name="address" value="<%=student.getAddress() %>">
            </td>
        </tr>
        <tr>
            <th class="title">Date of Birth</th>
            <td class="input">
                <input type="text" name="birthday" value="<%=student.getBirthday().substring(0, 10) %>">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit">Update Student</button> 
                <button type="reset">Reset</button> 
                <button type="button" id="listBtn">Student List</button> 
            </td>
        </tr>
    </table>
    </form>
    <p align="center" style="color: red;"></p>
    
    <script type="text/javascript">
    studentForm.no.focus();

    studentForm.onsubmit = function() {
        if(studentForm.no.value == "") {
            alert("Please enter the student number.");
            studentForm.no.focus();
            return false;
        }
        
        var noReg = /\d{4}/g;
        if(!noReg.test(studentForm.no.value)) {
            alert("Please enter a 4-digit integer for the student number.");
            studentForm.no.focus();
            return false;
        }
        
        if(studentForm.name.value == "") {
            alert("Please enter the name.");
            studentForm.name.focus();
            return false;
        }

        if(studentForm.phone.value == "") {
            alert("Please enter the phone number.");
            studentForm.phone.focus();
            return false;
        }

        var phoneReg = /(01[016789])-\d{3,4}-\d{4}/g;
        if(!phoneReg.test(studentForm.phone.value)) {
            alert("Please enter the phone number in the correct format.");
            studentForm.phone.focus();
            return false;
        }
        
        if(studentForm.address.value == "") {
            alert("Please enter the address.");
            studentForm.address.focus();
            return false;
        }

        if(studentForm.birthday.value == "") {
            alert("Please enter the date of birth.");
            studentForm.birthday.focus();
            return false;
        }
        
        var birthdayReg = /(18|19|20)\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])/g;
        if(!birthdayReg.test(studentForm.birthday.value)) {
            alert("Please enter the date of birth in the correct format.");
            studentForm.birthday.focus();
            return false;
        }
    } 
    
    document.getElementById("listBtn").onclick = function() {
        location.href = "<%=request.getContextPath()%>/student/displayStudent.jsp";    
    }
    </script>
</body>
</html>
```
### StudentUpdate - jsp

Here is the translation of the provided JSP document into English:

```jsp
<%@page import="xyz.itwill.dao.StudentDAO"%>
<%@page import="xyz.itwill.dto.StudentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that receives student information, updates the corresponding row in the STUDENT table, and responds with a URL to request the [displayStudent.jsp] document --%>      
<%
    // Handling abnormal requests
    if(request.getMethod().equals("GET")) {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return;
    }

    // Change the character encoding to read the values sent via POST method
    request.setCharacterEncoding("utf-8");
    
    // Retrieve and store the parameter values
    int no = Integer.parseInt(request.getParameter("no"));
    String name = request.getParameter("name");
    String phone = request.getParameter("phone");
    String address = request.getParameter("address");
    String birthday = request.getParameter("birthday");
    
    // Create a StudentDTO object and set its fields with the received values
    StudentDTO student = new StudentDTO();
    student.setNo(no);
    student.setName(name);
    student.setPhone(phone);
    student.setAddress(address);
    student.setBirthday(birthday);
    
    // Call the method of the StudentDAO class that takes a StudentDTO object, 
    // updates the corresponding row in the STUDENT table, and returns the number of updated rows
    StudentDAO.getDAO().updateStudent(student);
    
    // Respond to the client with a URL to request the [displayStudent.jsp] document
    response.sendRedirect(request.getContextPath() + "/student/displayStudent.jsp");
%>
```
### StudentDelete
Here is the translation of the provided JSP document into English:

```jsp
<%@page import="xyz.itwill.dao.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSP document that receives a student number, deletes the corresponding row in the STUDENT table, and responds with a URL to request the [displayStudent.jsp] document --%>      
<%
    // Handling abnormal requests
    if(request.getParameter("no") == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }

    // Retrieve and store the parameter value
    int no = Integer.parseInt(request.getParameter("no"));
    
    // Call the method of the StudentDAO class that takes the student number, 
    // deletes the corresponding row in the STUDENT table, and returns the number of deleted rows
    int rows = StudentDAO.getDAO().deleteStudent(no);
    
    if(rows > 0) { // If there are deleted rows
        response.sendRedirect(request.getContextPath() + "/student/displayStudent.jsp");
    } else { // If there are no deleted rows - Handling abnormal requests
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
%>
```