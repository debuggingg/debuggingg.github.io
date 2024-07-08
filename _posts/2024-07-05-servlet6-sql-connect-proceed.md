---
layout: single
title: 2024/07/05 Servlet- 06-ConnectOracleSQL-proceed
---
## Ex) Guest Proceed 
## SelectProceed- Guest List Display 

```java
// Servlet to search all rows (posts) stored in the GUEST table and include them in an HTML document for response
// => If the [Write] button is clicked, request the post input page (/guest/writeForm.itwill) to navigate to the page
// => If the [Modify] button of a post is clicked, request the post input page (/guest/modifyForm.itwill) to navigate to the page - pass the post number to the requested page (servlet)
// => If the [Delete] button of a post is clicked, request the post deletion page (/guest/remove.itwill) to navigate to the page - pass the post number to the requested page (servlet)
@WebServlet("/guest/list.itwill")
public class GuestListDisplayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        // Call the method of the GuestDAO class to search all rows (posts) stored in the GUEST table and return them as a List object
        List<GuestDTO> guestList = GuestDAO.getDAO().selectGuestList();
        
        // Include the search results (List object) in the HTML document for response
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Post List</h1>");
        out.println("<table width='1000'>");
        out.println("<tr>");
        out.println("<td align='right'>");
        out.println("<button type='button' onclick='location.href=\"writeForm.itwill\";'>Write</button>");
        out.println("</td>");
        out.println("</tr>");
        if (guestList.isEmpty()) { // If the List object has no elements
            out.println("<tr>");
            out.println("<td align='right'>");
            out.println("<table border='1' cellspacing='0' width='100%'>");
            out.println("<tr>");
            out.println("<td align='center'>No posts found.</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
        } else { // If the List object has elements
            // Loop to sequentially provide all elements (GuestDTO objects) stored in the List object
            for (GuestDTO guest : guestList) {
                out.println("<tr>");
                out.println("<td align='right'>");
                out.println("<table border='1' cellspacing='0' width='100%'>");
                out.println("<tr>");
                out.println("<th width='150'>Writer</th>");
                out.println("<td width='200' align='center'>" + guest.getWriter() + "</td>");
                out.println("<th width='150'>Date</th>");
                out.println("<td width='200' align='center'>" + guest.getRegdate() + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th width='150'>Subject</th>");
                out.println("<td width='500' colspan='3'>" + guest.getSubject() + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th width='150'>Content</th>");
                out.println("<td width='500' colspan='3'>" + guest.getContent().replace("\n", "<br>") + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td colspan='4' align='right'>");
                out.println("<button type='button' onclick='location.href=\"modifyForm.itwill?num=" + guest.getNum() + "\";'>Modify</button>");
                out.println("<button type='button' onclick='location.href=\"remove.itwill?num=" + guest.getNum() + "\";'>Delete</button>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
            }
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
```
---
## Guest WriteForm, Write  - TABLE INSERT

#### Form 

```java
// Servlet to respond with an HTML document for user input of a post
// => If the [Save Post] button is clicked, request the post insertion page (/guest/write.itwill) to navigate to the page - passing input values
// => If the [Post List] button is clicked, request the post list page (/guest/list.itwill) to navigate to the page
@WebServlet("/guest/writeForm.itwill")
public class GuestWriteFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Guestbook Post</h1>");
        out.println("<hr>");
        out.println("<form action='write.itwill' method='post' name='insertForm'>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<td>Writer</td>");
        out.println("<td><input type='text' name='writer'></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>Subject</td>");
        out.println("<td><input type='text' name='subject'></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>Content</td>");
        out.println("<td><textarea rows='4' cols='60' name='content'></textarea></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td colspan='2'>");
        out.println("<button type='button' id='saveBtn'>Save Post</button>");
        out.println("<button type='reset'>Reset</button>");
        out.println("<button type='button' onclick='location.href=\"list.itwill\";'>Post List</button>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("<script type='text/javascript'>");
        out.println("insertForm.writer.focus();");
        out.println("document.getElementById('saveBtn').onclick=function() {");
        out.println("if(insertForm.writer.value=='') {");
        out.println("alert('Please enter the writer.');");
        out.println("insertForm.writer.focus();");
        out.println("return;");
        out.println("}");
        out.println("if(insertForm.subject.value=='') {");
        out.println("alert('Please enter the subject.');");
        out.println("insertForm.subject.focus();");
        out.println("return;");
        out.println("}");
        out.println("if(insertForm.content.value=='') {");
        out.println("alert('Please enter the content.');");
        out.println("insertForm.content.focus();");
        out.println("return;");
        out.println("}");
        out.println("insertForm.submit();");
        out.println("}");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
    }
}
```
####  Insert Proceed 

```java
// Servlet that receives a post and inserts it as a row in the GUEST table, then responds with a URL address
// that requests the post list page (/guest/list.itwill)
// => The client changes the browser's request URL to the received URL address and requests the servlet at that URL,
// receiving and displaying the execution result - Redirect
@WebServlet("/guest/write.itwill")
public class GuestWriteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Response for the case when the servlet is requested using the GET method - Response to an abnormal request
        if (request.getMethod().equals("GET")) {
            // Send a [405] error code to the client
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        // Change the character set to read the values passed in the POST request to the servlet
        request.setCharacterEncoding("utf-8");

        // Retrieve and store the passed values (post)
        String writer = request.getParameter("writer");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        // Create a GuestDTO object and set its fields with the passed values
        GuestDTO guest = new GuestDTO();
        guest.setWriter(writer);
        guest.setSubject(subject);
        guest.setContent(content);

        // Call the method of the GuestDAO class to insert the post (GuestDTO object) as a row in the Guest table
        // and return the number of inserted rows
        GuestDAO.getDAO().insertGuest(guest);

        // Respond to the client by sending the URL address
        response.sendRedirect("list.itwill"); // Redirect
    }
}
```
---

## Guest ModifyForm, update - TABLE UPDATE
### form
```java
// Servlet that receives a post number, retrieves the corresponding post from the GUEST table,
// and responds with an HTML document to display the post in input tags for the user to modify.
// => When the [Modify Post] button is clicked, the post modification page (/guest/modify.itwill) is requested
// with the input values (post) sent to it.
// => When the [Post List] button is clicked, the post list page (/guest/list.itwill) is requested.
@WebServlet("/guest/modifyForm.itwill")
public class GuestModifyFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        // Handle response for cases where no value is passed - Response to an abnormal request
        if (request.getParameter("num") == null) {
            // Send a [400] error code to the client
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Retrieve and store the passed value (post number) by converting it to an integer
        int num = Integer.parseInt(request.getParameter("num"));

        // Call the method of the GuestDAO class to retrieve the post from the GUEST table
        // and return it as a GuestDTO object
        GuestDTO guest = GuestDAO.getDAO().selectGuest(num);

        // Handle response for cases where no post is found - Response to an abnormal request
        if (guest == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Modify Guest Post</h1>");
        out.println("<hr>");
        out.println("<form action='modify.itwill' method='post' name='modifyForm'>");
        // Pass the post number as a [hidden] type to distinguish the post
        out.println("<input type='hidden' name='num' value='" + guest.getNum() + "'>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<td>Author</td>");
        out.println("<td><input type='text' name='writer' value='" + guest.getWriter() + "'></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>Title</td>");
        out.println("<td><input type='text' name='subject' value='" + guest.getSubject() + "'></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>Content</td>");
        out.println("<td><textarea rows='4' cols='60' name='content'>" + guest.getContent() + "</textarea></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td colspan='2'>");
        out.println("<button type='button' id='modifyBtn'>Modify Post</button>");
        out.println("<button type='reset'>Reset</button>");
        out.println("<button type='button' onclick='location.href=\"list.itwill\";'>Post List</button>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("<script type='text/javascript'>");
        out.println("modifyForm.writer.focus();");
        out.println("document.getElementById('modifyBtn').onclick = function() {");
        out.println("if (modifyForm.writer.value == '') {");
        out.println("alert('Please enter the author.');");
        out.println("modifyForm.writer.focus();");
        out.println("return;");
        out.println("}");
        out.println("if (modifyForm.subject.value == '') {");
        out.println("alert('Please enter the title.');");
        out.println("modifyForm.subject.focus();");
        out.println("return;");
        out.println("}");
        out.println("if (modifyForm.content.value == '') {");
        out.println("alert('Please enter the content.');");
        out.println("modifyForm.content.focus();");
        out.println("return;");
        out.println("}");
        out.println("modifyForm.submit();");
        out.println("}");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
    }
}
```

#### update proceed

```java
// Servlet that receives a post, updates the corresponding row in the GUEST table, and responds with a URL address
// for the post list page (/guest/list.itwill)
@WebServlet("/guest/modify.itwill")
public class GuestModifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle response for GET requests - Response to an abnormal request
        if (request.getMethod().equals("GET")) {
            // Send a [405] error code to the client
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        // Change character set to read the values passed in the POST request
        request.setCharacterEncoding("utf-8");

        // Retrieve and store the passed values (post)
        int num = Integer.parseInt(request.getParameter("num"));
        String writer = request.getParameter("writer");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        // Create a GuestDTO object and set its fields with the passed values
        GuestDTO guest = new GuestDTO();
        guest.setNum(num);
        guest.setWriter(writer);
        guest.setSubject(subject);
        guest.setContent(content);

        // Call the method of the GuestDAO class to update the corresponding row in the GUEST table
        // with the post (GuestDTO object) and return the number of updated rows
        GuestDAO.getDAO().updateGuest(guest);

        // Respond by sending the URL address to the client
        response.sendRedirect("list.itwill"); // Redirect
    }
}
```
---

## GuestRemove -TABLE DELETE 

```java
// Servlet that receives a post number, deletes the corresponding row in the GUEST table, and responds with a URL
// address for the post list page (/guest/list.itwill)
@WebServlet("/guest/remove.itwill")
public class GuestRemoveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle response for missing parameters - Response to an abnormal request
        if (request.getParameter("num") == null) {
            // Send a [400] error code to the client
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Retrieve and store the passed value (post number), converting it to an integer
        int num = Integer.parseInt(request.getParameter("num"));

        // Call the method of the GuestDAO class to delete the corresponding row in the GUEST table
        // and return the number of deleted rows
        int rows = GuestDAO.getDAO().deleteGuest(num);

        if (rows > 0) { // If there are deleted rows
            // Respond by sending the URL address to the client
            response.sendRedirect("list.itwill"); // Redirect
        } else { // If there are no deleted rows
            // Send a [400] error code to the client
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}
```

