---
layout: single
title: 2024/07/03 Servlet 02-LifeCycleServlet-JoinServlet-FileUploadServlet
---
# Servlet
## LifeCycleServlet

### Concept 

#### Servlet Lifecycle
1. **Instantiation**:
   - The servlet container (WAS) creates an instance of the servlet class when the client first requests it.
   - This involves calling the constructor of the servlet class.

2. **Initialization**:
   - After instantiation, the container calls the `init()` method once.
   - This method is used for any initialization tasks and can access configuration parameters provided in the `web.xml` file through the `ServletConfig` object.

3. **Request Handling**:
   - Each client request results in a call to the `service()` method.
   - This method processes the request, generates the appropriate response, and sends it back to the client.

4. **Destruction**:
   - Before the servlet instance is destroyed, the container calls the `destroy()` method once.
   - This method is used for cleanup tasks.

#### Servlet Configuration
- **ServletConfig**: An object that provides servlet configuration information.
- **ServletContext**: An object that provides information about the web application environment.
- **web.xml**: A deployment descriptor file used to configure servlets and provide initialization parameters.

### Program Flow

1. **Client Request**:
   - A client makes a request to the servlet (e.g., `http://localhost:8000/servlet/life.itwill`).

2. **Servlet Instantiation**:
   - If this is the first request to the servlet, the servlet container creates an instance of `LifeCycleServlet`.

3. **Initialization (`init()`)**:
   - The container calls the `init()` method.
   - Configuration parameters are read from `web.xml` using `ServletConfig` and `ServletContext`.
   - Example: The `name` parameter is retrieved from `web.xml`.

4. **Request Handling (`service()`)**:
   - For each client request, the `service()` method is called.
   - This method generates an HTML response containing a greeting message.

5. **Response Generation**:
   - The `service()` method sends the generated HTML response back to the client.

6. **Subsequent Requests**:
   - For subsequent requests, only the `service()` method is called as the servlet instance is already created and initialized.

7. **Servlet Destruction (`destroy()`)**:
   - When the servlet container is shutting down, it calls the `destroy()` method to perform any necessary cleanup.

### Example Program Flow

1. **Client Requests `http://localhost:8000/servlet/life.itwill`**:
   - If this is the first request, `LifeCycleServlet` constructor and `init()` are called.
   - `service()` method is called to handle the request.

2. **`LifeCycleServlet` Constructor**:
   - Output: `### LifeCycleServlet class default constructor called ###`.

3. **`init(ServletConfig config)`**:
   - Output: `### LifeCycleServlet class init() method called ###`.
   - Initialization: `name` parameter is retrieved from `web.xml`.

4. **`service(HttpServletRequest request, HttpServletResponse response)`**:
   - Output: `### LifeCycleServlet class service() method called ###`.
   - Generates and sends an HTML response containing a greeting message.

5. **Client Receives Response**:
   - HTML response is displayed in the client's browser:
     ```html
     <!DOCTYPE html>
     <html>
     <head>
     <meta charset='utf-8'>
     <title>Servlet</title>
     </head>
     <body>
     <h1>Servlet Lifecycle</h1>
     <hr>
     <p>John Doe, hello.</p>
     </body>
     </html>
     ```

6. **Subsequent Requests**:
   - Only `service()` method is called to handle the requests.

7. **Container Shutdown**:
   - `destroy()` method is called for cleanup before the servlet instance is destroyed.

## Full Code
```java

// A servlet handles client requests by having the WAS (Web Application Server) program  // create an instance of the servlet class and call the request handling methods.
// If the servlet instance already exists, only the request handling method is called, 
// reusing the existing servlet instance.
// When the WAS program shuts down, all servlet instances created and used by the WAS program are destroyed.
// The WAS program provides a container function to manage servlet instances.

// Container: A program that manages the lifecycle of necessary objects during program execution.
// A servlet is an object managed by a container - it is a component: JavaBean (object).

@WebServlet("/life.itwill")
public class LifeCycleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String name;
    
    // This constructor is called by the WAS program when the client first requests the servlet.
    // Initialization commands to be executed while creating the object are written here - field initialization.
    public LifeCycleServlet() {
        System.out.println("### LifeCycleServlet class default constructor called ###");
        // name="John Doe";
    }

    // This method is called once after the WAS program creates an instance of the servlet class when the client first requests the servlet.
    // Initialization commands to be executed after the object creation are written here - field initialization.
    // The reason for using the init() method instead of the constructor is that the init() method can use the ServletConfig object provided.
    // ServletConfig object: Stores configuration information necessary for creating web resources.
    // The init() method can initialize fields by obtaining values provided in the [web.xml] file, increasing maintenance efficiency.
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("### LifeCycleServlet class init() method called ###");
        // name="John Doe";
        
        // ServletConfig.getServletContext(): Returns the ServletContext object (which stores the WAS program's information) 
        // that created the ServletConfig object.
        // ServletContext.getInitParameter(String name): Reads and returns the value provided by the context-param element in the [web.xml] file.
        // ServletConfig.getInitParameter(String name): Reads and returns the value provided by the init-param element in the [web.xml] file.
        name = config.getServletContext().getInitParameter("name");
    }

    // This method is called once before the WAS program destroys the servlet instance - finalization tasks.
    @Override
    public void destroy() {
        System.out.println("### LifeCycleServlet class destroy() method called ###");
    }
    
    // This method is called by the WAS program every time the client requests the servlet.
    // Commands to process the data by receiving the passed values and create a response file are written here - request handling method.
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("### LifeCycleServlet class service() method called ###");
        
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet Lifecycle</h1>");
        out.println("<hr>");
        out.println("<p>" + name + ", hello.</p>");
        out.println("</body>");
        out.println("</html>");        
    }
}
```

---
## JoinServlet

## **There are  HTML files too**
#### signup html, errorMessage html
### Concept 

#### Servlet Request Handling
1. **Request Type Handling**:
   - The servlet checks if the request is made via GET or POST.
   - If the request is via GET, it sends an error response or redirects the client to an error page.

2. **Character Encoding**:
   - For POST requests, the servlet sets the character encoding to UTF-8 to correctly read the request body.

3. **Parameter Retrieval**:
   - The servlet retrieves input values from the request using `getParameter()` or `getParameterValues()`.

4. **Response Generation**:
   - The servlet generates an HTML response that includes the input values and sends it back to the client.

### Program Flow

1. **Client Request**:
   - A client makes a POST request to the servlet (e.g., `http://localhost:8000/servlet/join.itwill`) with input values from a form.

2. **Request Type Handling**:
   - The servlet checks if the request method is GET or POST.
   - If GET, it responds with an error or redirects the client to an appropriate page.
   - If POST, it continues to process the request.

3. **Character Encoding**:
   - The servlet sets the request character encoding to UTF-8 to ensure correct reading of input values.

4. **Parameter Retrieval**:
   - The servlet retrieves input values from the request using `getParameter()` for single values and `getParameterValues()` for multiple values.

5. **Response Generation**:
   - The servlet generates an HTML response that includes the retrieved input values formatted appropriately.
   - The response is sent back to the client and displayed in the client's browser.

## Full Code
```java
// Servlet that receives input values (member information) from the input page (form.html)
// and responds with an HTML document that includes these values
// => Servlet executed by a POST request from the form tag on the input page
@WebServlet("/join.itwill")
public class JoinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        // If the servlet is requested via GET, it is an abnormal request,
        // so send an error code (4XX or 5XX) to the client or respond with a URL
        // to an error message page
        // HttpServletRequest.getMethod(): Returns the request method (GET or POST) used by the client
        // System.out.println("Request Method = " + request.getMethod());
        if (request.getMethod().equals("GET")) { // If the servlet is requested via GET
            /*
            // HttpServletResponse.sendError(int sc): Sends an error code to the client
            // The error code should preferably be one of the constants defined in HttpServletResponse
            // The error-page element in [web.xml] can be used to display an error message
            // based on the error code, without changing the browser's URL
            // response.sendError(400);
            // response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            // response.sendError(405);
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
            */

            /*
            // HttpServletResponse.sendRedirect(String url): Sends a URL to the client
            // The client will change the browser's URL to the given URL and request the web program
            // at that URL, receiving and displaying the result - redirect
            response.sendRedirect("/servlet/error.html");
            return;
            */

            // Respond with an HTML document that includes JavaScript to display an alert
            // message and redirect to the desired page
            out.println("<script>");
            out.println("alert('The page was requested in an abnormal way.');");
            out.println("location.href='/servlet/form.html';");
            out.println("</script>");
            return;
        }

        // If the servlet is requested via POST, the input values are sent in the body of the request message
        // Since the default character encoding for reading the body of the request message is ISO-8859-1,
        // the values need to be re-encoded to be used correctly
        // HttpServletRequest.setCharacterEncoding(String encoding): Changes the character encoding
        // used to read the body of the request message
        request.setCharacterEncoding("utf-8");

        // Get the input values from the request and store them
        // HttpServletRequest.getParameter(String name): Returns the value of the specified parameter
        // If the parameter does not exist, returns null
        String id = request.getParameter("id");

        /*
        // Validate the input values
        if (id == null || id.equals("")) { // If the value is missing - abnormal request
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!Pattern.matches("^[a-zA-Z]\\w{5,19}$", id)) { // If the value does not match the regex pattern - abnormal request
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        */

        String passwd = request.getParameter("passwd");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        // If multiple values are passed for a single name, use getParameterValues() to get all values as an array
        // String hobby = request.getParameter("hobby");
        String[] hobby = request.getParameterValues("hobby");
        String job = request.getParameter("job");
        String profile = request.getParameter("profile");

        // Generate an HTML document that includes the input values and send it to the client
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Membership Confirmation</h1>");
        out.println("<hr>");
        out.println("<p>ID = " + id + "</p>");
        out.println("<p>Password = " + passwd + "</p>");
        out.println("<p>Name = " + name + "</p>");
        out.println("<p>Email = " + email + "</p>");
        out.println("<p>Gender = " + gender + "</p>");
        // out.println("<p>Hobby = " + hobby + "</p>");
        out.println("<p>Hobbies = ");
        for (int i = 0; i < hobby.length; i++) {
            out.println(hobby[i]);
            if (i < hobby.length - 1) {
                out.println(", ");
            }
        }
        out.println("</p>");
        out.println("<p>Job = " + job + "</p>");
        // Convert line breaks in the input value to <br> tags for proper display
        out.println("<p>Profile = <br>" + profile.replace("\n", "<br>") + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
```
---
## FileUploadServlet

### Concept 

#### File Upload Servlet Handling
1. **Request Type Handling**:
   - The servlet checks if the request is made via GET or POST.
   - If the request is via GET, it redirects the client to the file upload form.

2. **File Upload Directory**:
   - The servlet determines the file system path of the server directory where uploaded files will be stored.

3. **MultipartRequest Creation**:
   - The servlet creates a `MultipartRequest` object to handle the files and form data transmitted via [multipart/form-data].
   - This automatically saves the uploaded files to the specified server directory.

4. **Parameter Retrieval**:
   - The servlet retrieves form data (text values) and file names using `getParameter()` and `getOriginalFileName()` methods of `MultipartRequest`.

5. **Response Generation**:
   - The servlet generates an HTML response that includes the retrieved form data and file names, and displays the uploaded images.

### Program Flow

1. **Client Request**:
   - A client makes a POST request to the servlet (e.g., `http://localhost:8000/servlet/upload.itwill`) with files and form data from the file upload form.

2. **Request Type Handling**:
   - The servlet checks if the request method is GET or POST.
   - If GET, it redirects the client to the file upload form page.
   - If POST, it continues to process the request.

3. **File Upload Directory**:
   - The servlet determines the server directory path where the uploaded files will be saved.

4. **MultipartRequest Creation**:
   - The servlet creates a `MultipartRequest` object, specifying the request, save directory, maximum file size, encoding, and file rename policy.
   - This handles the file upload process, saving the uploaded files to the server directory.

5. **Parameter Retrieval**:
   - The servlet retrieves the uploader's name and the original and saved file names from the `MultipartRequest` object.

6. **Response Generation**:
   - The servlet generates an HTML response that includes the uploader's name, original file names, saved file names, and displays the uploaded images.
   - The response is sent back to the client and displayed in the client's browser.

### Steps to Download and Build the COS Library File Distributed by the Oreilly Group into the Project

1. **Download COS Library**
   - Visit [Servlets.com](http://www.servlets.com).
   - Click on the **COS File Upload Library** menu.
   - Download the `cos-22.05.zip` file.

2. **Extract COS Library**
   - Unzip the downloaded `cos-22.05.zip` file.
   - Navigate to the extracted `cos-22.05` folder.
   - Go to the `lib` directory inside the `cos-22.05` folder.

3. **Copy COS Jar File**
   - Locate the `cos.jar` file in the `lib` directory.
   - Copy the `cos.jar` file.

4. **Build COS Library into the Project**
   - Open your project directory.
   - Navigate to `src/main/webapp/WEB-INF/lib`.
   - Paste the `cos.jar` file into the `lib` folder.

### Summary
By pasting the `cos.jar` file into the `/WEB-INF/lib` directory, the library file is automatically built into your project. This allows you to utilize the classes and methods provided by the COS library for file upload handling in your servlet application.


## Full Code
```java
package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

// When receiving user input (file) using the form tag on the input page with [multipart/form-data],
// read the binary file in the request message body with the HttpServletRequest object by obtaining
// an input stream (ServletInputStream object) and process the values and files separately.
// => It is recommended to use classes to handle text and files transmitted via [multipart/form-data].

// Libraries that include classes for handling text and files transmitted via [multipart/form-data]:
// 1. Use classes from the commons-fileupload library distributed by the Apache group - optional file upload
// 2. Use classes from the cos library distributed by the Oreilly group - mandatory file upload

// Steps to download and build the cos library file distributed by the Oreilly group into the project:
// 1. Go to http://www.servlets.com >> Click COS File Upload Library menu >> Download cos-22.05.zip
// 2. Unzip cos-22.05.zip >> Go to cos-22.05 folder >> Go to lib folder >> Copy cos.jar
// 3. Project >> src/main/webapp >> WEB-INF >> lib >> Paste cos.jar
// => By pasting the library file (jar file) into the /WEB-INF/lib folder, it is automatically built into the project.

// Servlet to respond with an HTML document that includes the input values and file names received from the input page (file_upload.html).
// => Files uploaded by the user are saved in a server directory - File Upload
@WebServlet("/upload.itwill")
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        if (request.getMethod().equals("GET")) {
            response.sendRedirect("/servlet/file_upload.html"); // Redirect to input page
            return;
        }

        /*
        // Input values transmitted via [multipart/form-data] cannot be retrieved using getParameter() method of HttpServletRequest object
        request.setCharacterEncoding("utf-8");
        String uploader = request.getParameter("uploader");
        */

        // Get the file system path of the server directory where the transmitted files will be saved
        // => Returns the file system path of the web directory used by the WAS program, not the Eclipse workspace directory
        // => Files are uploaded and saved in the upload directory created in Eclipse web directory
        // The WAS program converts the files in the Eclipse workspace directory to resources (Context) in the web directory during execution (Start) - Synchronization process
        // => Since there are no uploaded files in the workspace directory, synchronization process deletes the files uploaded in the web directory
        String saveDirectory = request.getServletContext().getRealPath("/upload");
        // System.out.println("saveDirectory = " + saveDirectory);

        // Create an object of MultipartRequest class from cos library
        // => MultipartRequest object: An object to handle text and files transmitted via [multipart/form-data]
        // Creating a MultipartRequest object automatically saves all transmitted files to the server directory - mandatory file upload
        // MultipartRequest(HttpServletRequest request, String saveDirectory[, int maxPostSize]
        // [, String encoding][, FileRenamePolicy policy]) constructor to create an object
        // => request: Pass the HttpServletRequest object containing the request information
        // => saveDirectory: Pass the file system path of the server directory where the transmitted files will be saved - upload directory
        // => maxPostSize: Pass the size (in bytes) to limit the size of the transmitted files - if omitted, the default is unlimited
        // => encoding: Pass the character set to read the transmitted values - if omitted, the default is Western European
        // => policy: Pass an object created from a class that inherits the FileRenamePolicy interface - if omitted,
        // the transmitted file will overwrite the file stored in the server directory
        // FileRenamePolicy object: An object that stores the rules for renaming files
        // => If there is a file with the same name in the server directory, the transmitted file is saved with a different name using this object
        MultipartRequest mr = new MultipartRequest(request, saveDirectory,
                30 * 1024 * 1024, "utf-8", new DefaultFileRenamePolicy());

        // MultipartRequest.getParameter(String name): Method to read and return the text value transmitted via [multipart/form-data]
        String uploader = mr.getParameter("uploader");

        // MultipartRequest.getOriginalFileName(String name): Method to get and return the name of the input file transmitted via [multipart/form-data]
        String fileone = mr.getOriginalFileName("fileone");
        String filetwo = mr.getOriginalFileName("filetwo");

        // MultipartRequest.getFilesystemName(String name): Method to get and return the name of the uploaded file saved in the server directory
        String uploadone = mr.getFilesystemName("fileone");
        String uploadtwo = mr.getFilesystemName("filetwo");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>File Upload</h1>");
        out.println("<hr>");
        out.println("<p>Uploader = " + uploader + "</p>");
        out.println("<p>File-1 (Original Filename) = " + fileone + "</p>");
        out.println("<p>File-1 (Uploaded Filename) = " + uploadone + "</p>");
        out.println("<p>File-2 (Original Filename) = " + filetwo + "</p>");
        out.println("<p>File-2 (Uploaded Filename) = " + uploadtwo + "</p>");
        out.println("<hr>");
        out.println("<img src='/servlet/upload/" + uploadone + "' width='200'>");
        out.println("<img src='/servlet/upload/" + uploadtwo + "' width='200'>");
        out.println("</body>");
        out.println("</html>");
    }
}
```



