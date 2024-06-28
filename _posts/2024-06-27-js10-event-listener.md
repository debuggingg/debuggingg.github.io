---
layout: single
title: 2024/06
---
## Event listener 

1. **Event Handler Functions:**
   - `eventHandlerOne()` and `eventHandlerTwo()` are functions that show alert messages.
   - `Event Button-1` calls both functions when clicked.

2. **Event Handling on Button-2 and Button-3:**
   - `btn2` has two event handlers assigned using `onclick`, but only the last assigned handler will be executed.
   - `btn3` uses `addEventListener` to attach multiple event handlers, allowing both to be executed.

3. **Event Propagation:**
   - The `red` and `green` `div`s have event listeners for both capturing and bubbling phases.
   - The `green` `div` stops event propagation using `event.stopPropagation()` during the bubbling phase.
   - Events on the `red` `div` during both capturing and bubbling phases trigger alerts with details about the event.

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
<style type="text/css">
div {
	border: 1px solid black; /* Corrected typo in 'solid' */
	padding: 30px;
	text-align: center;
}

#red { background-color: red; }
#green { background-color: green; }
</style>
</head>
<body>
	<h1>JavaScript Event Handling</h1>
	<hr>
	<!-- Multiple event handler functions can be called as attribute values in the tag's event listener attribute -->
	<button type="button" onclick="eventHandlerOne(); eventHandlerTwo();">Event Button-1</button>
	<button type="button" id="btn2">Event Button-2</button>
	<button type="button" id="btn3">Event Button-3</button>
	<hr>
	<div id="red">
		<div id="green"></div>
	</div>  
	
	<script type="text/javascript">
	function eventHandlerOne() {
		alert("Executing commands of eventHandlerOne function");
	}
	
	function eventHandlerTwo() {
		alert("Executing commands of eventHandlerTwo function");
	}
	
	// Only one event handler function can be stored in the event listener attribute of the Element object
	document.getElementById("btn2").onclick = eventHandlerOne;
	document.getElementById("btn2").onclick = eventHandlerTwo; // This will overwrite the previous handler

	// Multiple event handler functions can be registered for the desired event by calling the addEventListener member function on the Element object
	document.getElementById("btn3").addEventListener("click", eventHandlerOne);
	document.getElementById("btn3").addEventListener("click", eventHandlerTwo);
	
	// Element.addEventListener(eventType, callback[, useCapture]) registers event handler functions
	// => Depending on the boolean value passed to the useCapture parameter, the callback function is called during the capturing phase or the bubbling phase
	// => Passing [false] to the useCapture parameter calls the callback function during the bubbling phase to execute the event handler command
	// => Passing [true] to the useCapture parameter calls the callback function during the capturing phase to execute the event handler command
	// => If no boolean value is passed to the useCapture parameter, [false] is used by default
	document.getElementById("red").addEventListener("click", function(event) {
		// event.currentTarget: The member variable storing the Element object of the tag where the event occurred
		var tag = event.currentTarget.tagName;
		var id = event.currentTarget.getAttribute("id");
		// event.type: The member variable storing the type of event that occurred in the tag
		alert("Capturing phase = " + tag + " tag (" + id + ") where " + event.type + " event occurred");
	}, true);
	
	document.getElementById("green").addEventListener("click", function(event) {
		var tag = event.currentTarget.tagName;
		var id = event.currentTarget.getAttribute("id");
		alert("Bubbling phase = " + tag + " tag (" + id + ") where " + event.type + " event occurred");
		event.stopPropagation();
	}, false);
	
	document.getElementById("red").addEventListener("click", function(event) {
		var tag = event.currentTarget.tagName;
		var id = event.currentTarget.getAttribute("id");
		alert("Bubbling phase = " + tag + " tag (" + id + ") where " + event.type + " event occurred");
	}, false);
	</script>
</body>
</html>
```

---
## Dom

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
<style type="text/css">
#itemListDiv div {
	margin: 5px;
}
</style>
</head>
<body>
	<h1>JavaScript Event Handling</h1>
	<hr>
	<p>Using JavaScript to dynamically modify tags in the DOM tree when events occur, providing dynamic pages - DHTML (Dynamic HTML).</p>
	<hr>
	<button type="button" id="addBtn">Add Item</button>
	<div id="itemListDiv"></div>
	
	<script type="text/javascript">
	// Variable to store values for identifying tags
	var count=0;
	
	document.getElementById("addBtn").onclick=function() {
		count++;
		
		var newItem=document.createElement("div");// <div></div>
		newItem.setAttribute("id", "item_"+count);// <div id="item_XXX"></div>
		
		var html="Item["+count+"]&nbsp;&nbsp;<button type='button' onclick='removeItem("+count+");'>Delete</button>";
		newItem.innerHTML=html;// <div id="item_XXX">Item[XXX]</div>
		
		document.getElementById("itemListDiv").appendChild(newItem);
	}
	
	function removeItem(cnt) {
		//alert(cnt);
		var removeE=document.getElementById("item_"+cnt);
		removeE.parentElement.removeChild(removeE);
	}
	</script>
</body>
</html>
```






```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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