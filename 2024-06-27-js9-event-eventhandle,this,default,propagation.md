---
layout: single
title: 2024/06/27 JavaScript 08-Event-EventHandle,This,Default,Propagation
---
# Event
---
## EventHandle

```html

<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript Event Handling</h1>
	<hr>
	<p>Event: Various incidents that can occur on a tag (Element object)</p>
	<p>Event Handling: Writing commands in JavaScript to be executed when an event occurs on a tag</p>
	<hr>
	<p>Event Handling Method 1: Write event handling commands as attribute values in the event listener property (onXXX) of the tag where the event will occur - it is recommended to call the event handler function as the event handling command</p>
	<p>Event Handling Method 2: Find the Element object in the DOM tree and store the event handler function in the event listener property of the Element object - register the event handler function in the tag</p>
	<p>Event Handling Method 3: Find the Element object in the DOM tree and call the addEventListener() member function on the Element object to register the event handler function for the event</p>
	<hr>
	<!-- <button type="button" onclick="alert('Execute event handling command 1');">Button 1</button> -->
	<button type="button" onclick="eventHandler();">Button 1</button>
	<button type="button" id="eventBtn2">Button 2</button>
	<button type="button" id="eventBtn3">Button 3</button>
	
	<script type="text/javascript">
	//Event handler function - the function to be called when an event occurs on a tag
	function eventHandler() {
		//Write event handling commands
		alert('Execute event handling command 1');
	}
	
	//When an event handler function is registered in the tag (Element object), the event handler function is automatically called to execute event handling commands when an event occurs on the tag
	document.getElementById("eventBtn2").onclick = function() {
		alert('Execute event handling command 2');
	}
	
	//Element.addEventListener(eventName, callback[, useCapture]): A member function that calls the callback function (event handler function) when the event passed as a parameter occurs on the Element object
	document.getElementById("eventBtn3").addEventListener("click", function() {
		alert('Execute event handling command 3');
	});
	</script>
</body>
</html>
```
---
## Event This


```html

<title>JavaScript</title>
<style type="text/css">
#displayDiv {
	width: 600px;
	font-size: 2em;
	font-weight: bold;
	text-align: center;
	border: 1px solid red;
}

ul li {
	width: 400px;
	margin: 10px;
	text-align: center;
	font-size: 1.5em;
	font-weight: bold;
	border: 1px solid blue;
	list-style-type: none;
}
</style>
</head>
<body>
	<h1>JavaScript Event Handling</h1>
	<hr>
	<p>Use the this keyword to refer to the tag (Element object) where the event occurred in the event handler function</p>
	<hr>
	<div id="displayDiv">Event Handling</div>
	<hr>
	<ul>
		<li>Hong Gil-dong</li>
		<li>Im Kkeok-jeong</li>
		<li>Jeon Woo-chi</li>
	</ul>
	
	<script type="text/javascript">
	document.getElementById("displayDiv").onclick = function() {
		//alert("A click event occurred on the tag.");
		
		//Add a tag attribute to the Element object to change the tag attribute value
		//document.getElementById("displayDiv").style = "color: green;";
		
		this.style = "color: green;";
	}
	
	var liList = document.getElementsByTagName("li");
	
	/*
	liList.item(0).onclick = function() {
		//liList.item(0).style = "color: orange";
		this.style = "color: orange";
	}
	
	liList.item(1).onclick = function() {
		//liList.item(1).style = "color: orange";
		this.style = "color: orange";
	}
	
	liList.item(2).onclick = function() {
		//liList.item(2).style = "color: orange";
		this.style = "color: orange";
	}
	*/
	
	for (i = 0; i < liList.length; i++) { // When variable i is 3, the loop ends
		//Register the event handler function in the tag (Element object)
		liList.item(i).onclick = function() {
			//Commands executed when an event occurs on the tag after the loop ends - variable i has 3 stored
			// => liList.item(3) - null >> [null] cannot be used to use member variables or member functions
			//liList.item(i).style = "color: orange"; // Error occurs
			this.style = "color: orange";
		}
	}
	</script>
</body>
</html>
```
---
## Event default- (preventDefault)

```html
<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript Event Handling</h1>
	<hr>
	<p>Some HTML tags provide event handling functionality for specific events
	- such as the a tag, input tag, form tag, etc.</p>
	<button type="button" id="btn">Go to Daum Site</button>
	<hr>
	<a href="https://www.naver.com" id="naver"><button type="button">Go to Naver Site</button></a>
	
	<script type="text/javascript">
	document.getElementById("btn").onclick = function() {
		location.href = "https://www.daum.net";
	}
	
	document.getElementById("naver").onclick = function(event) {
		//event object: an object that contains information related to the event that occurred on the tag
		// => It is automatically created when the event handler function is called and destroyed when the function ends
		
		//event.preventDefault(): a member function that cancels the default event command set on the tag
		event.preventDefault();
	}
	</script>
</body>
</html>
```
## Event Propagation

 **Styles:**
   - `div` elements are styled with a solid border, padding, and center-aligned text.
   - Specific colors are applied to `#red`, `#green`, and `#blue` divs.

2. **Event Handling:**
   - Event propagation is explained, covering capturing and bubbling phases.
   - The `onclick` events for each `div` stop the propagation of the event to ensure that only the intended navigation occurs.

3. **Functionality:**
   - Clicking on the `#red` div navigates to Daum.
   - Clicking on the `#green` div navigates to Naver.
   - Clicking on the `#blue` div navigates to Google.

The code uses `event.stopPropagation()` to prevent the event from propagating to parent elements, ensuring that only the `div` clicked handles the navigation.


```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
<style type="text/css">
div {
	border: 1px solid black;
	padding: 30px;
	text-align: center;
}

#red { background-color: red; }
#green { background-color: green; }
#blue { background-color: blue; }
</style>
</head>
<body>
	<h1>JavaScript Event Handling</h1>
	<hr>
	<p>Event Propagation: Events triggered in the browser first reach the window object,
	then propagate to the Element object (tag) in the DOM tree where the event occurred,
	before reversing direction and returning to the window object where the event is destroyed.</p>
	<p>The process by which events travel is divided into the capturing phase and the bubbling phase.</p>
	<p>Capturing: The process where the event travels from the window object to the tag where the event occurred
	- during this process, the event handler functions registered on the tags are called.</p>
	<p>Bubbling: The process where the event travels from the tag where the event occurred back to the window object
	- during this process, the event handler functions registered on the tags are called (default).</p>
	<hr>
	<div id="red">
		<div id="green">
			<div id="blue"></div>			
		</div>
	</div>  
	
	<script type="text/javascript">
	document.getElementById("red").onclick = function(event) {
		location.href = "https://www.daum.net";
		//event.stopPropagation() : A method to stop event propagation.
		event.stopPropagation();
	}
	
	document.getElementById("green").onclick = function(event) {
		location.href = "https://www.naver.com";
		event.stopPropagation();
	}
	
	document.getElementById("blue").onclick = function(event) {
		location.href = "https://www.google.com";
		event.stopPropagation();
	}
	</script>	
</body>
</html>
```
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
