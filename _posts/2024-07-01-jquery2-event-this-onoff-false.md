---
layout: single
title: 2024/07/01 jQuery 03-Event-This-on/off-False
---
# JQuery
## Event

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>jQuery Event Handling</h1>
	<hr>
	<button type="button" id="btn">Click the button</button>
	<div id="displayDiv"></div>
	
	<script type="text/javascript">
	//$(selector).click(callback): A member function that calls the callback function (event handler) when a click event occurs on the tags found by the selector
	$("#btn").click(function() {
		$("#displayDiv").text("Event handler executed");
	});
	</script>
</body>
</html>
```

---
## Event-This

Here is the translated content:

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
ul li {
	font-size: 1.2em;
	margin: 10px;
}

div {
	font-size: 1.5em;
	color: green;
}

#menu {
	font-weight: bold;
	color: red;
}
</style>
</head>
<body>
	<h1>jQuery Event Handling</h1>
	<hr>
	<h2>Please select a lunch menu.</h2>
	<ul>
		<li>Home-cooked Meal</li>
		<li>Jjajangmyeon</li>
		<li>Jjamppong</li>
		<li>Seolleongtang</li>
		<li>Soondae Soup</li>
		<li>Samgyetang</li>
		<li>Pho</li>
		<li>Tonkatsu</li>
	</ul>
	<div>The selected lunch menu is <span id="menu">???</span>.</div>
	
	<script type="text/javascript">
	$("ul li").click(function() {
		// The 'this' keyword in the event handler function refers to the tag where the event occurred
		var choice = $(this).text(); // Store the text content of the tag where the event occurred
		
		// Change the text content of the selected tag
		$("#menu").text(choice);
	});
	</script>
</body>
</html>
```

---
## Event on-off

```html
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
div {
	width: 350px;
	margin: 0 auto;
	padding: 50px;
	font-size: 1.2em;
	text-align: center;
	border: 1px solid black;
}
</style>
</head>
<body>
	<h1>jQuery Event Handling</h1>
	<hr>
	<div>Try clicking or moving the mouse over this area.</div>
	
	<script type="text/javascript">
	/*
	// Short form for handling click events on the selected tag
	$("div").click(function() {
		$(this).css("color", "red");
	});
	
	//$(selector).mouseover(callback): Calls the callback function (event handler) when the mouse cursor enters the selected tag
	$("div").mouseover(function() {
		$(this).css("background", "yellow");
	});
	
	//$(selector).mouseout(callback): Calls the callback function (event handler) when the mouse cursor leaves the selected tag
	$("div").mouseout(function() {
		$(this).css({"color":"black", "background":"white"});
	});
	*/
	
	/*
	//$(selector).on(event, callback): Registers an event handler for events that occur on the selected tag - Standard form
	$("div").on("click", function() {
		$(this).css("color", "red");
	});
	*/
	
	//$(selector).on(object): Registers event handlers for multiple events that can occur on the selected tag by passing an Object containing the events
	// => The keys in the Object are event names, and the values are event handler functions for those events
	$("div").on({
		"click": function() {
			$(this).css("color", "red");
		},
		"mouseover": function() {
			$(this).css("background", "yellow");
		},
		"mouseout": function() {
			$(this).css({"color": "black", "background": "white"});
		}
	});
	</script>
<h1>jQuery Event Handling</h1>
	<hr>
	<button type="button" id="eventOn">Register Event Handler Button</button>
	<button type="button" id="eventOff">Remove Event Handler Button</button>
	<hr>
	<button type="button" id="btn">Click the Button</button>
	<div id="displayDiv"></div>
	
	<script type="text/javascript">
	/*
	$("#btn").click(function() {
		$("#displayDiv").text("Event handler command executed");
	});
	*/
	
	$("#eventOn").click(function() {
		$("#displayDiv").text("Event handler registered successfully");
		
		// Register event handler by searching for the tag within the event handler
		$("#btn").click(function() {
			$("#displayDiv").text("Event handler command executed");
		});
	});
	
	$("#eventOff").click(function() {
		$("#displayDiv").text("Event handler removed successfully");

		// $(selector).off(event) : Removes the event handler for the specified event from the selected tag
		$("#btn").off("click");
	});
	</script>
</body>
</html>
```
---
## Event False


```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
div {
	width: 300px;
	margin: 0 auto;
	padding: 20px;
	text-align: center;
	border: 3px solid blue;
}

a, a:visited {
	text-decoration: none;
	padding: 10px;
	border: 3px solid red;
}
</style>
</head>
<body>
	<h1>jQuery Event Handling</h1>
	<hr>
	<div>
		<!-- When the a tag is clicked, the browser's URL changes to the href attribute value,
		requesting a new web program and displaying the result - page transition  -->
		<a href="https://www.daum.net">Go to Daum</a>
	</div>
	
	<script type="text/javascript">
	$("div").click(function() {
		$(this).css("background", "aqua");
		$(this).find("a").css("background", "white");
	});
	
	$("div").find("a").click(function() {
		$(this).css("background", "yellow");

		/*
		event.preventDefault(); // Cancel the default event of the tag
		event.stopPropagation(); // Stop event propagation (bubbling)
		*/
		
		// If [false] is returned from the event handler, it cancels the default event of the tag,
		// stops event propagation, and then ends the event handler
		return false;
	});
	</script>
</body>
</html>
```

In this example, the `<a>` tag within the `<div>` element is prevented from performing its default action (navigating to the URL) when clicked. The `<div>`'s background changes to aqua, and the `<a>` tag's background changes to white when the `<div>` is clicked. When the `<a>` tag is clicked, its background changes to yellow, and the default navigation behavior is prevented.

---


