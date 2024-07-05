---
layout: single
title: 2024/07/04 Servlet- 03-Cookies-Session
---
## Cookie create

#### Concept

1. **Cookies**:
   - Cookies are small pieces of data stored on the client side (in the browser) to provide continuity between server interactions.
   - They are used to maintain the state and remember user information across different web pages and sessions.

2. **Cookie Attributes**:
   - **Name**: Identifier for the cookie.
   - **Value**: Data stored in the cookie.
   - **Max Age**: Lifespan of the cookie in seconds. If not set, the cookie will last until the browser session ends.

#### Program Flow

1. **Initialization**:
   - The servlet is mapped to the URL pattern `/create.itwill`.

2. **Request Handling**:
   - The `service` method handles both GET and POST requests to create and send cookies to the client.

3. **Content Type**:
   - The response content type is set to `text/html` with UTF-8 encoding to properly display HTML content.

4. **Cookie Creation**:
   - Two `Cookie` objects are created:
     - `idCookie` with the name `id` and value `abc123`.
     - `countCookie` with the name `count` and value `0`.

5. **Setting Cookie Lifespan**:
   - The `countCookie`'s max age is set to `24 * 60 * 60` seconds (1 day) to persist the cookie even after the browser is closed.

6. **Adding Cookies to Response**:
   - The cookies (`idCookie` and `countCookie`) are added to the response using `response.addCookie()` method.
   - This ensures that the cookies are sent to the client and stored in the browser.

7. **Generating HTML Response**:
   - An HTML response is generated to inform the user that the cookies have been created.
   - The HTML includes:
     - A heading indicating cookie creation.
     - A message saying "There's a cookie inside you."
     - A link to `/servlet/use.itwill` for using the created cookies.

#### Program Flow 

1. **Client Request**: Client sends a request to `/create.itwill`.
2. **Servlet Handling**: The `CookieCreateServlet` handles the request.
3. **Set Response Type**: The response content type is set to `text/html` with UTF-8 encoding.
4. **Create Cookies**: Two cookies (`idCookie` and `countCookie`) are created.
5. **Set Cookie Max Age**: The `countCookie`'s lifespan is set to 1 day.
6. **Add Cookies to Response**: Cookies are added to the response.
7. **Generate HTML**: HTML content is generated and sent as a response to the client.
8. **Client Receives Response**: Client's browser stores the cookies and displays the HTML content.

## Full Code

```java

// Cookie: A value stored on the client to provide continuity between the server (web program) and the client (browser)
// => The client stores the cookie, distinguishing the server's information with an identifier.

// Servlet that creates cookies for the client and responds with an HTML document
// => The cookies received from the server are stored in the client's browser
@WebServlet("/create.itwill")
public class CookieCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		// Create Cookie objects using the Cookie class
		// => Cookie object: An object that stores information related to cookies
		// => Create Cookie objects using the Cookie(String name, String value) constructor
		// => name: Cookie name (identifier to distinguish the cookie value), value: Cookie value (string value to provide continuity)
		Cookie idCookie = new Cookie("id", "abc123");
		Cookie countCookie = new Cookie("count", "0");
		
		// Change the lifespan of the cookie stored in the client browser
		// Cookie.setMaxAge(int expiry): Method to change the lifespan of the cookie to the time (in seconds) passed as the parameter
		// => If the lifespan of the Cookie object is not changed, the default value [-1] is set
		countCookie.setMaxAge(24 * 60 * 60); // Set the browser cookie to be retained for one day
		
		// Send the Cookie objects to the client to store the cookies in the client browser
		// => Cookies with a lifespan of [-1] are stored in the client browser's memory 
		// and automatically deleted when the browser is closed
		// => Cookies with a lifespan other than [-1] are stored in a file on the client 
		// and automatically deleted after the lifespan expires
		// HttpServletResponse.addCookie(Cookie cookie): Method to save the Cookie object in the response message to be sent to the client for storage
		response.addCookie(idCookie);
		response.addCookie(countCookie);
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'>");
		out.println("<title>Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Create Cookie</h1>");
		out.println("<hr>");
		out.println("<p>There's a cookie inside you.</p>");
		out.println("<hr>");
		out.println("<p><a href='/servlet/use.itwill'>Use Cookie</a></p>");
		out.println("</body>");
		out.println("</html>");
	}
}
```

---
## Cookie Use

#### Concept

1. **Cookies**:
   - Cookies are small pieces of data stored on the client-side (in the browser) to maintain the state and continuity between server interactions.
   - They contain key-value pairs and have attributes like name, value, and lifespan (max age).

2. **Cookie Retrieval**:
   - When a client makes a request to the server, all cookies stored by the client's browser for that server are sent along with the request.
   - The servlet can retrieve these cookies, process them, and send back a response, potentially updating the cookies.

#### Program Flow

1. **Initialization**:
   - The servlet is mapped to the URL pattern `/use.itwill`.

2. **Request Handling**:
   - The `service` method handles both GET and POST requests to read and process cookies received from the client.

3. **Content Type**:
   - The response content type is set to `text/html` with UTF-8 encoding to properly display HTML content.

4. **Retrieve Cookies**:
   - All cookies received from the client are retrieved using `request.getCookies()`, which returns an array of `Cookie` objects.

5. **Generate HTML Response**:
   - An HTML response is generated to display the cookie values or indicate that no cookies were received.
   - The HTML includes:
     - A heading indicating the usage of cookies.
     - A message saying "There are no cookies inside you" if no cookies were received.
     - Display of the `id` cookie value if present.
     - Incrementing and displaying the `count` cookie value if present.
     - Links to create and delete cookies.

6. **Process Cookies**:
   - If cookies are received, the servlet iterates through the array of cookies:
     - Checks for the `id` cookie and retrieves its value.
     - Checks for the `count` cookie, retrieves its value, increments it, and displays the updated count.
     - Updates the `count` cookie with the new count value and sends it back to the client with a lifespan of 1 day.

7. **Response to Client**:
   - The client receives the HTML response with the cookie values displayed.
   - The updated `count` cookie is stored in the client's browser.

#### Program Flow 

1. **Client Request**: Client sends a request to `/use.itwill`.
2. **Servlet Handling**: The `CookieUseServlet` handles the request.
3. **Set Response Type**: The response content type is set to `text/html` with UTF-8 encoding.
4. **Retrieve Cookies**: All cookies are retrieved from the client using `request.getCookies()`.
5. **Generate HTML**:
   - If no cookies are received, display a message indicating no cookies.
   - If cookies are received, display the `id` cookie value and increment/display the `count` cookie value.
6. **Process `count` Cookie**:
   - Increment the `count` cookie value by 1.
   - Create a new `count` cookie with the updated value and set its lifespan to 1 day.
   - Add the updated `count` cookie to the response.
7. **Response to Client**: Client receives the HTML response with displayed cookie values and stores the updated `count` cookie.
## Full Code

```java

// Servlet that includes cookie values received from the client in an HTML document response
// => When the client connects to the server and requests the servlet, all cookies stored on the server 
// are sent in the request message and passed to the servlet
@WebServlet("/use.itwill")
public class CookieUseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        // Retrieve and store all cookies received from the client
        // HttpServletRequest.getCookies: Method that returns all cookies received from the client as an array of Cookie objects
        Cookie[] cookies = request.getCookies();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Use Cookies</h1>");
        out.println("<hr>");
        if (cookies == null) { // If no cookies are received from the client
            out.println("<p>There are no cookies inside you.</p>");
        } else {
            String id = "";
            String count = "";

            // Loop to iterate over each element (Cookie object) in the Cookie array
            for (Cookie cookie : cookies) {
                // Cookie.getName(): Method that returns the name of the cookie stored in the Cookie object
                if (cookie.getName().equals("id")) {
                    // Cookie.getValue(): Method that returns the value of the cookie stored in the Cookie object
                    id = cookie.getValue();
                } else if (cookie.getName().equals("count")) {
                    count = cookie.getValue();
                }
            }

            if (!id.equals("")) { // If there is a cookie value
                // Include the cookie value in the HTML document and output it
                out.println("<p>ID = " + id + "</p>");
            }

            if (!count.equals("")) {
                int cnt = Integer.parseInt(count) + 1;
                out.println("<p>Number of Servlet requests = " + cnt + "</p>");

                // Create a Cookie object and send it to the client for storage
                // => If the cookie name of the sent cookie matches the cookie name stored on the client,
                // the existing cookie is deleted and replaced with the sent cookie - cookie update
                Cookie cookie = new Cookie("count", cnt + "");
                cookie.setMaxAge(24 * 60 * 60);
                response.addCookie(cookie);
            }
        }
        out.println("<hr>");
        out.println("<p><a href='/servlet/create.itwill'>Create Cookie</a></p>");
        out.println("<p><a href='/servlet/remove.itwill'>Delete Cookie</a></p>");
        out.println("</body>");
        out.println("</html>");
    }
}
```
---
## Cookie remove


```java
package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Servlet that deletes all cookies stored on the client and responds with an HTML document showing the result
@WebServlet("/remove.itwill")
public class CookieRemoveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        // Retrieve and store all cookies received from the client
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) { // If there are cookies received from the client
            for (Cookie cookie : cookies) {
                // Change the lifespan of the received cookies to [0] and send them back to the client
                // => Cookies with expired lifespans are automatically deleted from the client - cookie deletion
                cookie.setMaxAge(0);
                // Send the cookies back to the client with the updated lifespan to overwrite the existing cookies
                response.addCookie(cookie);
            }
        }
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Delete Cookies</h1>");
        out.println("<hr>");
        out.println("<p>There are no cookies inside you.</p>");
        out.println("<hr>");
        out.println("<p><a href='/servlet/use.itwill'>Use Cookies</a></p>");
        out.println("</body>");
        out.println("</html>");
    }
}
```
---
## Session 

### Concept

**Session:**
- A session stores values on the server to provide connection persistence between the server (web application) and the client (browser).
- Sessions are used to store security-related information, such as permissions.
- Sessions are distinguished by an identifier (SessionId) to differentiate between client sessions.
- Session binding is the process of associating a session with a servlet to use the values stored in the session.

**Session Binding:**
- If the client does not send a cookie named `JSESSIONID`, a new session is created on the server, bound to the servlet, and the session identifier (SessionId) is sent to the client as a cookie named `JSESSIONID`.
- If the client sends a cookie named `JSESSIONID`, the server tracks the session and binds it. If tracking fails, a new session is created and bound.
- Session tracking involves comparing the `JSESSIONID` cookie value received from the client with the server's session identifier (SessionId).

### Program Flow

1. **Initialization:**
   - The servlet is mapped to the URL pattern `/session.itwill`.

2. **Request Handling:**
   - The servlet handles requests using the `service` method.

3. **Session Retrieval:**
   - The servlet retrieves the session using `request.getSession()`.
   - If a new session is created, it is bound to the servlet. If an existing session is tracked, it is also bound to the servlet.

4. **HTML Response:**
   - The servlet generates an HTML response to display session information.
   - It checks whether the session is new or tracked using `session.isNew()`.
   - It retrieves and displays the session identifier using `session.getId()`.
   - It retrieves and displays the session's maximum inactive interval using `session.getMaxInactiveInterval()`.
   - It retrieves and displays the session creation time using `session.getCreationTime()`.

5. **Session Attribute Management:**
   - The servlet stores a new attribute in the session using `session.setAttribute("now", new Date())`.
   - It retrieves and displays the stored attribute using `session.getAttribute("now")`.
   - It removes the stored attribute using `session.removeAttribute("now")`.

6. **Session Invalidation:**
   - The servlet invalidates the session using `session.invalidate()`.
   - After invalidation, any attempt to store attributes in the session will result in an `IllegalStateException`.

## Full Code
```java

// Session: Values stored on the server to provide connection persistence between the server (web application) and client (browser)
// => Sessions are used to store security-related information (permissions)
// => Sessions are distinguished by an identifier (SessionId) to differentiate between client sessions in the servlet - session binding
// Session Binding: The process of associating a session containing values for connection persistence with a servlet for use - managed by the WAS program

// If a client does not send a cookie named [JSESSIONID], a new session is created on the server, bound to the servlet, and the session identifier (SessionId) 
// is sent to the client as a cookie named [JSESSIONID] for storage
// => Binding for the case when the client makes an initial request to the servlet
// => The cookie named [JSESSIONID] will be deleted when the client browser is closed

// If the client sends a cookie named [JSESSIONID], session tracking is performed and the session is bound
// => If session tracking fails, a new session is created and bound
// Session Tracking: The process of comparing the cookie value named [JSESSIONID] received from the client with the server's session identifier (SessionId)

// Servlet that binds a session and responds with an HTML document containing the bound session information
@WebServlet("/session.itwill")
public class SessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        // HttpServletRequest.getSession(): Method that returns the bound session (HttpSession object)
        // => Creates a new session and binds it or tracks an existing session and binds it
        // => HttpSession object: An object that stores values (objects) for connection persistence - session
        HttpSession session = request.getSession();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Session</h1>");
        out.println("<hr>");
        // HttpSession.isNew(): Method that returns [false] if the session was tracked and bound, [true] if a new session was created and bound
        if (session.isNew()) {
            out.println("<p>A new session has been created and bound.</p>");
        } else {
            out.println("<p>The session has been tracked and bound.</p>");
        }
        
        // HttpSession.getId(): Method that returns the session identifier (SessionId)
        out.println("<p>Session Identifier (SessionId) = " + session.getId() + "</p>");
        
        // HttpSession.getMaxInactiveInterval(): Method that returns the session's lifespan (in seconds)
        // => Session lifespan: The time after which the session is automatically deleted if not used
        // => The default session lifespan is 30 minutes if not changed
        out.println("<p>Session Lifespan = " + session.getMaxInactiveInterval() + "</p>");

        // HttpSession.getCreationTime(): Method that returns the session creation time (TimeStamp)
        out.println("<p>Session Creation Time = " + session.getCreationTime() + "</p>");
        
        // HttpSession.setAttribute(String attributeName, Object attributeValue): Method that stores the attribute name (string) and attribute value (object) in the session (HttpSession object)
        // => Used to store values (objects) for connection persistence in the session
        // => If an attribute with the same name already exists in the session, the attribute value is updated
        // => The client can use the same session in multiple servlets, so all servlets can retrieve and use the attribute values stored in the session - object sharing
        session.setAttribute("now", new Date());
        
        // HttpSession.getAttribute(String attributeName): Method that returns the attribute value stored in the session as an object for the given attribute name
        // => All attribute values stored in the session are returned as Object, so explicit type casting is used
        // => If there is no attribute value for the given attribute name, it returns [null]
        Date now = (Date) session.getAttribute("now");
        out.println("<p>Attribute Value (Object) Stored in Session = " + now + "</p>");
        
        // HttpSession.removeAttribute(String attributeName): Method that deletes the attribute value (object) stored in the session for the given attribute name
        session.removeAttribute("now");
        
        // HttpSession.invalidate(): Method that unbinds and deletes the session
        session.invalidate();
        
        // After unbinding the session, if an attribute value is stored in the session, an IllegalStateException is thrown
        // session.setAttribute("now", new Date());
        
        out.println("</body>");
        out.println("</html>");
    }
}
```

