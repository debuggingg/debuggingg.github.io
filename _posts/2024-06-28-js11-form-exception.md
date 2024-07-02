---
layout: single
title: 2024/06/28 JavaScript 11-Form- Submit-Exception
---
# Form Tag

## Submit Event

```html
<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript and the form tag</h1>
	<hr>
	<p>The form tag: A tag used to receive input values from users and to submit events (Submit Event) to request web programs for transmitting input values.</p>
	<p>For the form tag to function properly, child tags for inputting values from users and generating submit events must be included.</p>
	<p>Prior to requesting web programs with the form tag, it is recommended to use JavaScript to validate input values. Only execute the form tag and request the web program to transmit input values if they are valid.</p>
	<hr>
	<form action="46_form_action.html" method="post" name="loginForm">
	<table>
		<tr>
			<td>Username</td>
			<td><input type="text" name="id"></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="passwd"></td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit">Login</button></td>
		</tr>
	</table>	
	</form>

	<script type="text/javascript">
	// Using the name attribute of the form tag and input tags to reference Element objects
	// => document object can be omitted
	//alert(document.loginForm);//[object HTMLFormElement]
	//alert(loginForm.id);//[object HTMLInputElement]
	
	// HTMLInputElement.focus(): Member function that provides focus to an input tag
	loginForm.id.focus();
	
	// Saving an event handling function that will be automatically called when a submit event occurs in the form tag
	// => Validate input values received from input tags, and cancel the execution of the form tag if they are abnormal
	loginForm.onsubmit=function() {
		// HTMLInputElement.value: Member variable where input values entered into input tags are stored
		if(loginForm.id.value=="") {// If username is not entered
			alert("Please enter your username.");
			loginForm.id.focus();
			
			/*
			event.preventDefault();// Cancels the execution of the form tag by the submit event
			return;// Exit the event handling function
			*/
			
			// Using return false in the event handling function will return false, thereby ending the event handling function,
			// canceling the default command execution for the tag's event, and stopping event propagation
			return false;
		}
	
		// Writing /regular expression/g in JavaScript creates a RegExp object for use
		// RegExp object: Object where regular expressions (Regular Expression) are stored
		var idReg=/^[a-zA-Z]\w{5,19}$/g;			

		// RegExp.test(input): Member function that checks if the pattern stored in RegExp object matches the parameter input,
		// returning false if they do not match and true if they do
		if(!idReg.test(loginForm.id.value)) {// If username does not match the pattern of the regular expression
			alert("Username should consist of alphanumeric characters and some special characters, and be 6 to 20 characters in length.");
			loginForm.id.focus();
			return false;
		}
	
		if(loginForm.passwd.value=="") {// If password is not entered
			alert("Please enter your password.");
			loginForm.passwd.focus();
			return false; 
		}
		
		// If all input value validations succeed, execute the form tag normally to request the web program
		// return true; // Can be omitted
	}	
	</script>
</body>
</html>
```
---
## Button 

```html

<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript and the form tag</h1>
	<hr>
	<form name="loginForm">
	<table>
		<tr>
			<td>Username</td>
			<td><input type="text" name="id"></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="passwd"></td>
		</tr>
		<tr>
			<td colspan="2"><button type="button" id="loginBtn">Login</button></td>
		</tr>
	</table>	
	</form>
	
	<script type="text/javascript">
	loginForm.id.focus();

	// Saving an event handling function that will be called when a click event occurs on the Element object (button tag)
	// => Validate input values received from input tags, and if valid, trigger a submit event (Submit Event)
	// to execute the form tag and request the web program
	document.getElementById("loginBtn").onclick=function() {
		if(loginForm.id.value=="") {// If username is not entered
			alert("Please enter your username.");
			loginForm.id.focus();
			return;
		}
	
		var idReg=/^[a-zA-Z]\w{5,19}$/g;			

		if(!idReg.test(loginForm.id.value)) {// If username does not match the pattern of the regular expression
			alert("Username should consist of alphanumeric characters and some special characters, and be 6 to 20 characters in length.");
			loginForm.id.focus();
			return;
		}
	
		if(loginForm.passwd.value=="") {// If password is not entered
			alert("Please enter your password.");
			loginForm.passwd.focus();
			return; 
		}
		
		// Adding attributes to the Element object to change attribute values
		// => Used when different web programs need to be requested via the form tag based on variable values
		loginForm.action="47_form_action.html";
		loginForm.method="post";
		
		// If validation for all input values succeeds, trigger a submit event to execute the form tag
		// HTMLFormElement.submit(): Member function of Element object that triggers a submit event
		// => Executes the form tag and requests the web program
		loginForm.submit();
	}
	</script>
</body>
</html>
```
---
---
# Exception 

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript Exception Handling</h1>
	<hr>
	<p>Exception: A problem that occurs during the execution of a program's commands.</p>
	
	<script type="text/javascript">
	try {// Area where commands that may cause exceptions are written
		// var array = new Array(100);
		var array = new Array(1000000000000000); // Creating a large array to potentially cause an exception
		alert("Number of elements in the Array object = " + array.length);
	} catch (e) {// Area where exception handling commands are written, receiving exceptions that occur in the try area
		// Outputting the Error object containing the error message
		// alert(e);
		alert("A problem occurred during program execution.");
	} finally {// Area where commands that are executed regardless of whether an exception occurs or not are written
		alert("This command will always be executed.")
	}
	</script>  
</body>
</html>
```
---
---
# Cookie

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
</head>
<body>
	<h1>Cookies in JavaScript</h1>
	<hr>
	<p>Cookie: A value stored on the client to provide persistence between server and client connections.</p>
	<p>When a client connects to a server, multiple cookies stored on the client under the server's name are sent to the server when requesting the server's web program.</p>
	<hr>
	<form name="cookieForm">
		Username: <input type="text" name="id">&nbsp;&nbsp;
		<button type="button" id="saveBtn">Save Username</button>&nbsp;&nbsp;
		<button type="button" id="useBtn">Use Saved Username</button>&nbsp;&nbsp;	
		<button type="button" id="removeBtn">Remove Saved Username</button>&nbsp;&nbsp;	
	</form>
	
	<script type="text/javascript">
	// Function to set (or update) a cookie on the client browser
	// Parameters include cookie name, value, path, and expiration time
	function setCookie(name, value, path, expires) {
		var cookieString = ""; // Variable to store the cookie information
		
		// Concatenating cookie name and value using '=' and separating different attributes using ';'
		cookieString += name + "=" + encodeURIComponent(value) + ";";
		
		// Adding the path attribute of the cookie
		cookieString += "path=" + path + ";";
		
		// Creating a Date object to set the expiration time of the cookie
		var date = new Date();
		date.setDate(date.getDate() + expires);
		
		// Adding the expiration time attribute of the cookie
		cookieString += "expires=" + date.toUTCString() + ";";
		
		// Storing the cookie in the client browser using document.cookie
		document.cookie = cookieString;
	}
	
	// Function to retrieve the value of a cookie stored in the client browser
	// Parameter is the name of the cookie whose value needs to be retrieved
	function getCookie(name) {
		var cookies = document.cookie.split(";"); // Splitting the cookie string by ';' to get an array of cookies
		
		// Iterating through the array of cookies to find the one with the specified name
		for (var i = 0; i < cookies.length; i++) {
			var cookie = cookies[i].trim(); // Trimming any leading or trailing whitespace
			
			// Splitting the cookie into name and value parts and checking if the name matches
			if (cookie.split("=")[0] == name) {
				// Returning the value of the cookie
				return decodeURIComponent(cookie.split("=")[1]);
			}
		}
		
		return null; // Returning null if no cookie with the specified name is found
	}
	
	// Event handler for the "Save Username" button click
	// Retrieves the username input, checks if it's empty, and then sets a cookie with a 1-day expiration
	document.getElementById("saveBtn").onclick = function() {
		var username = cookieForm.id.value; // Getting the input value
		
		if (username == "") {
			alert("Please enter a username.");
			return;
		}
		
		// Setting a cookie to store the username with a 1-day expiration
		setCookie("id", username, "/", 1);
		
		// Clearing the input field after saving the username
		cookieForm.id.value = "";
 	}
	
	// Event handler for the "Use Saved Username" button click
	// Retrieves the saved username from the cookie and sets it as the value of the username input field
	document.getElementById("useBtn").onclick = function() {
		var username = getCookie("id"); // Retrieving the saved username from the cookie
		
		if (username == null) {
			alert("No saved username found in the cookie.");
			return;
		}
		
		// Setting the input field value to the saved username
		cookieForm.id.value = username;
	}

	// Event handler for the "Remove Saved Username" button click
	// Deletes the cookie storing the saved username by setting its expiration time to the past
	document.getElementById("removeBtn").onclick = function() {
		// Deleting the cookie by setting its expiration time to a past date
		setCookie("id", "", "/", -1);
	}
	</script>
</body>
</html>
```