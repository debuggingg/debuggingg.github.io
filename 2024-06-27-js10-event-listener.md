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
