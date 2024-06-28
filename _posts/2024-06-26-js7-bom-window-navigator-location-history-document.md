---
layout: single
title: 2024/06/26 JavaScript 07-BOM- Window,Navigator,Location,History,Document
---
# BOM (Browser Object Model)

## Window
```html
<title>JavaScript</title>
</head>
<body>
	<h1>BOM (Browser Object Model) - window Object</h1>
	<hr>
	<p>BOM: JavaScript objects provided based on the browser</p>
	<p>window Object: An object that represents the browser and provides member variables and member functions</p>
	
	<script type="text/javascript">
	// alert(window); // [object Window]
	
	/*
	// window.alert(message): A member function that creates an alert box and displays the message passed as a parameter
	// => The window object can be omitted, and the member function can be called as a general function
	// window.alert("Display message using an alert box");
	alert("Display message using an alert box");
	*/

	/*
	// window.prompt(message[, value]): A member function that creates an input box and receives input from the user,
	// and returns the input value as a string
	var input = prompt("Please enter a number.", 100);
	alert("input = " + input);
	*/
	
	/*
	// window.confirm(message): A member function that creates a confirmation box and returns the value selected by the user
	// => If [Cancel] is selected in the confirmation box, it returns [false], and if [OK] is selected, it returns [true]
	if(confirm("Do you want to delete the post?")) {
		alert("The post has been deleted.");
	} else {
		alert("The post deletion has been canceled.");
	}
	*/
	
	/*
	// window.open(url[, name][, option]): A member function that creates a child browser (popup window),
	// requests a web program to the child browser, and displays the execution result in the child browser
	// => option: width, height, top, left, menubar, toolbar, resizable, etc.
	// window.open("https://www.daum.net", "popup", "width=1000, height=640, top=100, left=200");
	*/

	if(confirm("Do you want to close the browser?")) {
		// window.close(): A member function that closes the current browser
		window.close();
	}
	</script>
</body>
</html>
```

## Navigator

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
</head>
<body>
	<h1>BOM (Browser Object Model) - Navigator Object</h1>
	<hr>
	<p> Navigator Object: An object that represents the browser engine and provides member variables and member functions</p>
	<p>Browser Engine: A program to interpret and execute web documents</p>
	
	<script type="text/javascript">
	// alert(navigator); // [object Navigator]

	// navigator.appName: A member variable that stores the name (identifier) of the browser engine
	// alert("Browser Engine Name = " + navigator.appName); // Browser Engine Name = Netscape
	
	// navigator.appVersion: A member variable that stores the version of the browser engine
	// alert("Browser Engine Version = " + navigator.appVersion); // Browser Engine Version = 5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36
	
	// navigator.userAgent: A member variable that stores the browser engine information
	alert("Browser Engine = " + navigator.userAgent); // Browser Engine = Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36
	</script>
</body>
</html>
```

## Location

- The code demonstrates the usage of the `location` object in the Browser Object Model (BOM) in JavaScript. It includes a commented-out example of changing the page URL after a delay, and an active example of reloading the page every second.

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
</head>
<body>
	<h1>BOM (Browser Object Model) - location Object</h1>
	<hr>
	<p>location Object: An object representing the URL address area in the browser, providing member variables and member functions</p>

	<script type="text/javascript">
	// Location.toString() : A member function that returns the URL address entered in the location object as a string
	// alert(location); // http://localhost:8000/web/js/32_location.html
	
	/*
	// location.href : A member variable that stores the URL address of the web program to be requested using the hyperlink function of the location object
	// => Changes the request URL address of the browser to request a web program and outputs the execution result - Page transition
	setTimeout(function() {
		location.href = "https://www.daum.net";
	}, 3000);
	*/
	// setTimeout(() => location.href = "https://www.daum.net", 3000);
	
	// location.reload() : A member function that requests the currently displayed page again and displays it - Page refresh
	setInterval(() => location.reload(), 1000);
	</script>
</body>
</html>
```

## History


- The  code provides an example of how to use the `history` object in the Browser Object Model (BOM) in JavaScript. It includes functions to navigate back and forward in the browser history, as well as reload the current page after a delay of 3 seconds.

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
</head>
<body>
	<h1>BOM (Browser Object Model) - history Object</h1>
	<hr>
	<p>history Object: An object representing the browser's history feature, providing member variables and member functions</p>
	<p>Browser History Feature: A feature that stores the response results (webpages) of web programs requested by the browser</p>

	<script type="text/javascript">
	// alert(history); // [object History]
	
	setTimeout(function() {
		// history.back() : A member function that navigates to the previous page
		// history.back();
		
		// history.forward() : A member function that navigates to the next page
		// history.forward();
		
		// history.go(number) : A member function that navigates to a specific page
		// history.go(-1); // Equivalent to history.back()
		// history.go(1); // Equivalent to history.forward()
		history.go(0); // Equivalent to location.reload()
	}, 3000);
	</script>	
</body>
</html>
```

## Document

- The code demonstrates how to use the `document` object in the Browser Object Model (BOM) in JavaScript. It includes a call to the `document.write` function, which outputs a paragraph to the browser. The `document` object represents the web page that is currently being displayed and provides various methods and properties to manipulate the content and structure of the web page.
```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
</head>
<body>
	<h1>BOM (Browser Object Model) - document Object</h1>
	<hr>
	<p>document Object: An object representing the display area of the browser response results, providing member variables and member functions</p>

	<script type="text/javascript">
	// alert(document); // [object HTMLDocument]
	
	// document.write(html) : A member function that outputs the provided string (HTML tags) to the browser
	document.write("<p>Content outputted using the document.write member function.</p>");
	</script>
</body>
</html>
```
