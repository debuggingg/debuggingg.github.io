---
layout: single
title: 2024/07/01 jQuery 03-Event
---
# JQuery

## Mouse Event

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
div {
	font-size: 1.2em;
	margin: 10px;
	width: 200px;
	text-align: center;
}

.mystyle {
	border: 2px solid red;
	background: yellow;
}
</style>
</head>
<body>
	<h1>Mouse Events</h1>
	<hr>
	<p>click: Occurs when the mouse button is pressed on the element.</p>
	<p>dblclick: Occurs when the mouse button is double-clicked on the element.</p>
	<p>mouseover: Occurs when the mouse cursor enters the element - including the bubbling phase.</p>
	<p>mouseout: Occurs when the mouse cursor leaves the element - including the bubbling phase.</p>
	<p>mouseenter: Occurs when the mouse cursor enters the element - excluding the bubbling phase.</p>
	<p>mouseleave: Occurs when the mouse cursor leaves the element - excluding the bubbling phase.</p>
	<p>mousedown: Occurs when the mouse button is pressed on the element.</p>
	<p>mouseup: Occurs when the pressed mouse button is released on the element.</p>
	<p>mousewheel: Occurs when the mouse wheel is moved while over the browser.</p>
	<p>mousemove: Occurs when the mouse pointer is moved over the browser.</p>
	<p>contextmenu: Occurs when the right mouse button is clicked on the browser.</p>
	<hr>
	<!-- <div class="mystyle">Mouse Event</div>  -->
	<div>Mouse Event-1</div>
	<div>Mouse Event-2</div>
	<div>Mouse Event-3</div>
	<div>Mouse Event-4</div>
	<div>Mouse Event-5</div>
	
	<script type="text/javascript">
	/*
	$("div").click(function() {
		if($(this).attr("class") != "mystyle") {
			//$(selector).addClass(className): Adds the specified class(es) to the selected element(s).
			$(this).addClass("mystyle");
		} else {
			//$(selector).removeClass(className): Removes the specified class from the selected element(s).
			$(this).removeClass("mystyle");
		}
	});
	*/
	
	/*
	$("div").click(function() {
		//$(selector).toggleClass(className): Toggles between adding/removing the specified class from the selected element(s).
		$(this).toggleClass("mystyle");
	});
	*/
	
	/*
	$("div").dblclick(function() {
		$(this).toggleClass("mystyle");
	});
	*/
	
	/*
	$("div").mouseover(function() {
		$(this).addClass("mystyle");
	});
	
	$("div").mouseout(function() {
		$(this).removeClass("mystyle");
	})
	*/
	
	/*
	//$(selector).hover(callback1, callback2): Attaches two event handlers to the selected element(s), to be executed when the mouse enters and leaves the element.
	$("div").hover(function() {
		$(this).addClass("mystyle");
	}, function(event) {
		$(this).removeClass("mystyle");
	});
	*/
	
	$(document).contextmenu(function() {
		alert("Right-clicking is prohibited.");
		return false;
	});
	</script>
</body>
</html>
```

This HTML file demonstrates various mouse events (`click`, `dblclick`, `mouseover`, `mouseout`, `mouseenter`, `mouseleave`, etc.) and how jQuery handles them. Each `<div>` element in the document responds to these events with corresponding CSS changes (`mystyle` class toggle). Additionally, a context menu event (`contextmenu`) is captured at the document level to prevent the default browser behavior (alerting that right-clicking is prohibited).

---
## Key Event

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>Key Event</h1>
	<hr>
	<p>keydown: Event triggered when a key is pressed down on an element - works for all keys.</p>
	<p>keypress: Event triggered when a key is pressed down on an element - works specifically for character keys like [Enter], [Space], [Esc], providing KeyCode to distinguish.</p>
	<p>keyup: Event triggered when a key is released on an element - provides KeyCode to distinguish keys.</p>
	<hr>
	<form method="post" name="loginForm" id="loginForm">
	<table>
		<tr>
			<td>Username</td>
			<td><input type="text" name="id" id="id"></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="passwd" id="passwd"></td>
		</tr>
		<tr>
			<td colspan="2"><button type="button" id="loginBtn">Login</button></td>			
		</tr>
	</table>
	</form>
	<hr>
	<div>Current character count: <span id="count">0</span></div>
	<textarea rows="7" cols="80" id="content"></textarea>
	
	<script type="text/javascript">
	// Using jQuery to set focus on the input element with id="id"
	$("#id").focus();
	
	$("#loginBtn").click(function() {
		// Checking if the input fields are empty before submission
		if($("#id").val()=="") {
			alert("Please enter your username.");
			$("#id").focus();
			return;
		}
		
		if($("#passwd").val()=="") {
			alert("Please enter your password.");
			$("#passwd").focus();
			return;
		}

		// Setting the action attribute of the form to "18_key_event.html"
		$("#loginForm").attr("action", "18_key_event.html");
		
		// Submitting the form using jQuery submit method
		$("#loginForm").submit();
	});
	
	$("#id").keyup(function(event) {
		// Detecting if the Enter key (keyCode 13) is pressed and focusing on the password field
		if(event.keyCode == 13) {
			$("#passwd").focus();
		}
	});
	
	$("#passwd").keyup(function(event) {
		// Detecting if the Enter key (keyCode 13) is pressed and submitting the form
		if(event.keyCode == 13) {
			if($("#id").val()=="") {
				alert("Please enter your username.");
				$("#id").focus();
				return;
			}
			
			if($("#passwd").val()=="") {
				alert("Please enter your password.");
				$("#passwd").focus();
				return;
			}

			$("#loginForm").attr("action", "18_key_event.html");
			
			$("#loginForm").submit();
		}
	});
	
	$("#content").keypress(function() {
		// Counting the number of characters in the textarea and updating the count span
		var count = $(this).val().length;
		$("#count").text(count);
	});
	</script>
</body>
</html>
```

This HTML file demonstrates various keyboard events (`keydown`, `keypress`, `keyup`) and how jQuery handles them in different scenarios: focusing on input fields, submitting a form on Enter key press, and updating character count in a textarea. It also includes basic form validation alerts for empty input fields before form submission.

---
## Scroll Event

Certainly! Here's the translated HTML content in English:

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>Scroll Event</h1>
	<hr>
	<div id="displayDiv"></div>
	
	<script type="text/javascript">
	function displayImage() {
		for(i=0;i<10;i++) {
			$("#displayDiv").append("<div><img src='/web/jquery/images/"+i+".png' width='100'></div>");
		}		
	}
	
	displayImage();
	
	//$(selector).scroll(callback) : Member function that calls a callback function (event handler) when a scroll event occurs in the selected tag (window object - browser).
	$(window).scroll(function() { 
		//alert("Scroll event occurred");
		
		//$(selector).height() : Member function that returns the height of the selected tag.
		var docH = $(document).height();
		//alert(docH);
		//alert($(window).height());
		
		//$(selector).scrollTop() : Method that returns the scroll top position of the selected tag.
		// => $(window).scrollTop() : Height of the document object that is not visible in the browser.
		var scrollH = $(window).height() + $(window).scrollTop();
		
		if(scrollH >= docH) { // When the scroll reaches the end of the document object.
			displayImage();
		}	
	});
	</script>
</body>
</html>
```

This HTML file demonstrates a scroll event handler in jQuery. When the user scrolls to the bottom of the page (`$(window).scroll`), it dynamically loads more images (`<img>`) into the `#displayDiv`. The `displayImage()` function appends new image elements to the div each time the scroll event reaches the bottom of the document.

---
## Submit Event

Certainly! Here is the translated HTML content in English:

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
fieldset {
	width: 800px;
	margin: 0 auto;
}

legend {
	font-size: 1.2em;
}

#joinForm ul li {
	list-style-type: none;
	margin: 15px 0;
}

#joinForm label {
	width: 100px;
	text-align: right;
	float: left;
	margin-right: 10px;
}

#btnFs {
	text-align: center;
}

.error {
	color: red;
	margin-left: 10px;
}
</style>
</head>
<body>
	<h1>Form Event</h1>
	<hr>
	<form action="20_submit_event.html" method="post" id="joinForm">
	<fieldset>
		<legend>Membership Information</legend>
		<ul>
			<li>
				<label for="id">Username</label>
				<input type="text" name="id" id="id">
				<span id="idMsg" class="error"></span>
			</li>
			<li>
				<label for="passwd">Password</label>
				<input type="password" name="passwd" id="passwd">
				<span id="passwdMsg" class="error"></span>
			</li>
			<li>
				<label for="name">Name</label>
				<input type="text" name="name" id="name">
				<span id="nameMsg" class="error"></span>
			</li>
			<li>
				<label for="email">Email</label>
				<input type="text" name="email" id="email">
				<span id="emailMsg" class="error"></span>
			</li>
		</ul>
	</fieldset>
	<fieldset id="btnFs">
		<button type="submit" id="submitBtn">Join</button>
		<button type="reset" id="resetBtn">Reset</button>
	</fieldset>
	</form>
	
	<script type="text/javascript">
	$("#id").focus();
	
	//$(selector).submit(callback) : Member function that calls a callback function (event handler) when a submit event occurs in the selected tag (form tag).
	// => The event handler validates input values and cancels form submission if validation fails.
	$("#joinForm").submit(function() {
		// Clear all error messages within the form
		$(".error").text("");
		
		// Variable to store validation result
		// => false: validation failed, true: validation succeeded
		var result = true;
		
		var idReg = /[a-zA-Z]\w{5,19}$/g;
		if ($("#id").val() == "") {
			$("#idMsg").text("Please enter your username.");
			result = false;
		} else if (!idReg.test($("#id").val())) {
			$("#idMsg").text("Username must be 6 to 20 characters long and can contain letters, numbers, and underscore (_).");
			result = false;
		}
		
		if ($("#passwd").val() == "") {
			$("#passwdMsg").text("Please enter your password.");
			result = false;
		} 
		
		if ($("#name").val() == "") {
			$("#nameMsg").text("Please enter your name.");
			result = false;
		}
		
		if ($("#email").val() == "") {
			$("#emailMsg").text("Please enter your email address.");
			result = false;
		}
		
		// Cancel form submission if result is false (validation failed)
		return result;
	});
	
	//$(selector).blur(callback) : Member function that calls a callback function (event handler) when an input tag loses focus to another tag.
	// => $(selector).focusout(callback) is equivalent.
	$("#id").blur(function() {
		var idReg = /[a-zA-Z]\w{5,19}$/g;
		if ($("#id").val() == "") {
			$("#idMsg").text("Please enter your username.");
			return;
		} else if (!idReg.test($("#id").val())) {
			$("#idMsg").text("Username must be 6 to 20 characters long and can contain letters, numbers, and underscore (_).");
			return;
		}
		$("#idMsg").text("");
	});
	
	// Event handler for the [Reset] button click event
	// => Resets input values automatically triggered by the Reset Event, and manually resets error messages.
	$("#resetBtn").click(function() {
		$(".error").text("");
		$("#id").focus();
	});
	</script>
</body>
</html>
```

This HTML file demonstrates a membership registration form with jQuery event handling for validation and submission. It validates username, password, name, and email fields, displaying error messages dynamically. The form submission is prevented if any validation fails.

---
## Form Event


```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
fieldset {
	width: 800px;
	margin: 0 auto;
}

legend {
	font-size: 1.2em;
}

#joinForm ul li {
	list-style-type: none;
	margin: 15px 0;
}

#joinForm label {
	width: 100px;
	text-align: right;
	float: left;
	margin-right: 10px;
}

#btnFs {
	text-align: center;
}

.error {
	color: red;
	margin-left: 10px;
}
</style>
</head>
<body>
	<h1>Form Event</h1>
	<hr>
	<form action="21_form_event.html" method="post" id="joinForm">
	<fieldset>
		<legend>Membership Information</legend>
		<ul>
			<li>
				<label for="id">Username</label>
				<input type="text" name="id" id="id">
				<span id="idMsg" class="error"></span>
			</li>
			<li>
				<label for="passwd">Password</label>
				<input type="password" name="passwd" id="passwd">
				<span id="passwdMsg" class="error"></span>
			</li>
			<li>
				<label for="name">Name</label>
				<input type="text" name="name" id="name">
				<span id="nameMsg" class="error"></span>
			</li>
			<li>
				<label for="email">Email</label>
				<input type="text" name="email" id="email">
				<span id="emailMsg" class="error"></span>
			</li>
		</ul>
	</fieldset>
	<fieldset id="btnFs">
		<button type="button" id="submitBtn">Join</button>
		<button type="button" id="resetBtn">Reset</button>
	</fieldset>
	</form>
	
	<script type="text/javascript">
	$("#id").focus();
	
	// Register event handling function for [Join] button click event
	// => Validates input values in the event handler and submits the form if validation succeeds (Submit Event).
	$("#submitBtn").click(function() {
		$(".error").text("");
		
		var result = true;
		
		var idReg = /[a-zA-Z]\w{5,19}$/g;
		if ($("#id").val() == "") {
			$("#idMsg").text("Please enter your username.");
			result = false;
		} else if (!idReg.test($("#id").val())) {
			$("#idMsg").text("Username must be 6 to 20 characters long and can contain letters, numbers, and underscore (_).");
			result = false;
		}
		
		if ($("#passwd").val() == "") {
			$("#passwdMsg").text("Please enter your password.");
			result = false;
		} 
		
		if ($("#name").val() == "") {
			$("#nameMsg").text("Please enter your name.");
			result = false;
		}
		
		if ($("#email").val() == "") {
			$("#emailMsg").text("Please enter your email address.");
			result = false;
		}
		
		// Exit event handling function if result is false (validation failed)
		if (!result) return;
		
		// Trigger the submit event to execute the form if result is true
		$("#joinForm").submit();
		
	});
	
	// Register event handling function for [Reset] button click event
	// => Resets input values of the form and clears error messages.
	$("#resetBtn").click(function() {
		//$(selector).reset() : Member function that resets input values of the selected tag (input tags within the form).
		// => Since there may be multiple forms returned as jQuery objects, use an index to access the desired form and reset its input tags.
		$("#joinForm")[0].reset();
		$(".error").text("");
		$("#id").focus();
	});
	</script>
</body>
</html>
```

This HTML file demonstrates a membership registration form with jQuery event handling for validation and form submission. It validates username, password, name, and email fields, displaying error messages dynamically. The form submission is triggered if all validations pass. The reset button clears input fields and error messages.