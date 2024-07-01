---
layout: single
title: 2024/06/28 JQuery 01-Selector-Filter-Each
---
# JQurey
## Jquery- install  and $
- In this HTML document, jQuery is included using a script tag sourced from a CDN (Content Delivery Network). The script demonstrates basic usage of jQuery to select `<div>` elements and change their text color to red. The script is placed at the end of the `<body>` tag to ensure that the document is fully loaded before executing jQuery code, allowing direct use of `$()` shorthand for jQuery operations.
```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<!-- To use jQuery functionalities, include the jQuery library file in your HTML document using a script tag -->
<!-- Refer to https://www.jquery.com for details -->

<!-- Download the jQuery library file and save it as a web resource (WebContext). Use a script tag to include it in the HTML document -->
<!-- Use version 1.X for IE9 and below, or 2.X or 3.X for IE10 and above -->
<!-- <script type="text/javascript" src="/web/jquery/jquery-3.7.1.min.js"></script> -->

<!-- Use a CDN (Content Delivery Network) server to retrieve the jQuery library file using a script tag and include it in the HTML document -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script type="text/javascript">
// To use jQuery library, pass a CSS selector to the jQuery function as a parameter to find DOM tree tags (Element objects)
// Then call member functions on the jQuery object to implement required functionality
// => All tags in the HTML document are stored as Element objects in the DOM tree

/*
// jQuery(document).ready(callback): Calls the callback function when the document object is ready (DOM tree is complete)
jQuery(document).ready(function() {
	alert("Document object is ready-1");
});

jQuery(document).ready(function() {
	alert("Document object is ready-2");
});
*/

/*
jQuery(document).ready(function() {
	jQuery("div").css("color","red");
});
*/
</script>
</head>
<body>
	<h1>Using jQuery</h1>
	<hr>
	<p>jQuery: A JavaScript library for event handling, tag manipulation, and AJAX functionality</p>
	<hr>
	<div>Hello, jQuery!!!</div>
	<div>Hello, jQuery!!!</div>
	<div>Hello, jQuery!!!</div>
	
	<!-- When the script tag is placed as the last child of the body tag, it is equivalent to the document object being ready,
	     so jQuery(document).ready() function calls are not necessary to use jQuery functionalities -->
	<script type="text/javascript">
	// jQuery("div").css("color","red");
	
	// You can use $ instead of jQuery as shorthand
	$("div").css("color","red");
	</script>
</body>
</html>
```
---
## Selector 

- **Tag Selector** (`$("h2")`): Selects all `<h2>` elements and changes their text color to red.
- **Class Selector** (`$(".item")`): Selects all elements with class "item" and changes their text color to orange.
- **ID Selector** (`$("#tag")`): Selects the element with id "tag" and changes its text color to green.
- **Method Chaining**: Demonstrates chaining of jQuery methods (`$("p").css("color", "blue").css("font-size","30px")`) to first change text color to blue and then set font size to 30 pixels for all `<p>` elements.
- **Object Argument**: Uses an object to pass multiple CSS properties (`$("p").css({"color":"blue", "font-size":"30px"})`) in a single call to the jQuery `css()` method.

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>Selector</h1>
	<hr>
	<h2 class="item">jQuery Selector-1</h2>
	<h2 id="tag">jQuery Selector-2</h2>
	<h3 class="item">jQuery Selector-3</h3>
	<p>jQuery Selector-4</p>
	
	<script type="text/javascript">
	// Tag Selector: Selects tags by tag name and applies CSS styles
	$("h2").css("color","red");
	
	// Class Selector: Selects tags by class attribute and applies CSS styles
	$(".item").css("color","orange");

	// ID Selector: Selects tags by id attribute and applies CSS styles
	$("#tag").css("color","green");
	
	// Method Chaining: Sequentially calls multiple jQuery methods on the selected elements
	$("p").css("color", "blue").css("font-size","30px");
	
	// Passing an object to jQuery method: Provides multiple CSS properties in a single call
	$("p").css({"color":"blue", "font-size":"30px"});
	</script>
</body>
</html>
```

---
## Descendant Selector
- **Child Selector** (`$("#subject > p")`): Selects `<p>` elements that are direct children of the element with id "subject" and applies CSS styles (`font-size` and `font-weight`).
  
- **Descendant Selector** (`$("#subject p")`): Selects all `<p>` elements that are descendants (children at any level) of the element with id "subject" and applies a CSS style (`color`).

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>Selector</h1>
	<hr>
	<div id="subject">
		<p>Java</p>
		<div>
			<p>Java basic</p>
			<p>Java OOP</p>
			<p>Java API</p>
		</div>
		
		<p>JSP </p>
		<div>
			<p>Script Element)</p>
			<p>(Directive)</p>
			<p>(Standard Action Tag)</p>
		</div>
	</div>
	
	<script type="text/javascript">
	// Child Selector: Selects direct children of the element with id "subject" and applies CSS styles
	$("#subject > p").css({"font-size":"25px", "font-weight":"bold"});

	// Descendant Selector: Selects all descendant elements of the element with id "subject" and applies CSS styles
	$("#subject p").css("color","green");
	</script>
</body>
</html>
```
---
## Filter Selector
- **`.filter()`** and **`.not()`** methods are used to selectively apply CSS styles based on the presence or absence of certain classes.
- **`.eq()`**, **`.first()`**, **`.last()`**, **`.next()`**, and **`.prev()`** methods are used to target specific elements within the DOM and apply CSS styles to them.

```html
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>Selector</h1>
	<hr>
	<h2>jQuery practice-1</h2>
	<h2 class="normal">jQuery practice-2</h2>
	<h2 class="normal">jQuery practice-3</h2>
	<h2>jQuery practice-4</h2>
	<h2>jQuery practice-5</h2>
	<h3 class="normal">jQuery practice-6</h3>
	<h3>jQuery practice-7</h3>
	<hr>
	<ul>
		<li>(Selector)-1</li>
		<li>(Selector)-2</li>
		<li>(Selector)-3</li>
		<li>(Selector)-4</li>
		<li>(Selector)-5</li>
	</ul>
		<h1>Selector</h1>
	<hr>
	<table>
		<tr>
			<td>ID</td>
			<td><input type="text" name="id"></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="passwd"></td>
		</tr>
		<tr>
			<td>Name</td>
			<td><input type="text" name="name"></td>
		</tr>
		<tr>
			<td>Email</td>
			<td><input type="text" name="email"></td>
		</tr>
	</table>

	<script type="text/javascript">
	// Applying CSS background yellow to all <h2> elements
	$("h2").css("background", "yellow");
	
	// Filtering and applying CSS background green to <h2> elements with class "normal"
	$(".normal").filter("h2").css("background", "green");

	// Applying CSS background silver to <h3> elements with class "normal"
	$("h3").filter(".normal").css("background", "silver");

	// Applying CSS background gold to <h3> elements NOT having class "normal"
	$("h3").not(".normal").css("background", "gold");
	
	// Applying CSS font-size 25px to all <li> elements inside <ul>
	$("ul").find("li").css("font-size","25px");
	Here is the translated content:

//Pseudo-class Selector: A selector to provide tags based on the state or position of the selected tag.
//:nth-child(N): A pseudo-class selector to provide the tag located at the Nth position.
//You can use pseudo-class selectors by passing them as arguments to the filter member function.


//:eq(index): A pseudo-class selector to provide the tag at the specified index position.


//$(selector).eq(index): A member function that returns the tag at the specified index among the multiple tags found by the selector as a jQuery object.
	// Applying CSS color red to the third <li> element inside <ul>
	$("ul li").eq(2).css("color","red");

	// Applying CSS color green to the first <li> element inside <ul>
	$("ul li").first().css("color","green");

	// Applying CSS color blue to the last <li> element inside <ul>
	$("ul li").last().css("color","blue");
	
	// Applying CSS color aqua to the next <li> element after the first one inside <ul>
	$("ul li").first().next().css("color", "aqua");	

	// Applying CSS color lime to the previous <li> element before the last one inside <ul>
	$("ul li").last().prev().css("color", "lime");	

	// Attribute Selector: Selecting elements based on their attribute and attribute values
	$("input[type]").css("border","2px solid green");  // Select all inputs regardless of type and add a green border
	$("input[type='password']").css("color","red");    // Select input with type 'password' and set text color to red
	$("input[name='email']").css("color","blue");       // Select input with name 'email' and set text color to blue
	</script>
</body>
</html>
```

---
## Each 
```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>each Method</h1>
	<hr>
	<h3>each Method Call-1</h3>
	<h3>each Method Call-2</h3>
	<h3>each Method Call-3</h3>
	<h3>each Method Call-4</h3>
	<h3>each Method Call-5</h3>
	
	<script type="text/javascript">
	// When multiple tags are selected by a selector, a jQuery object is returned for each tag, allowing member functions to be called repeatedly.
	// Implicit iteration: Calls the same member function for all selected tags, implementing the same functionality for all tags.
	//$("h3").css("color", "red");
	
	/*
	// Different functionalities can be implemented on the selected tags by distinguishing them using the jQuery object.
	$("h3").eq(0).css("color", "red");
	$("h3").eq(1).css("color", "green");
	$("h3").eq(2).css("color", "blue");
	$("h3").eq(3).css("color", "orange");
	$("h3").eq(4).css("color", "lime");
	*/
	
	var color=["red","green","blue","orange","lime"];

	/*
	//$("selector").size(): Returns the number of tags selected by the selector.
	// In jQuery 3.0, use length member variable instead of the size() method to provide the number of tags.
	for(i=0;i<$("h3").length;i++) {
		$("h3").eq(i).css("color",color[i]);
	}
	*/
	
	// $(selector).each(callback([index],[element])): A member function that iterates over the number of tags selected by the selector, calling a callback function repeatedly.
	// Explicit iteration
	// The callback function automatically stores the index of the selected tag and the Element object of the tag, which can be used in the callback function.
	$("h3").each(function(index, element) {
		//$("h3").eq(index).css("color",color[index]);
		
		// Passing a JavaScript object (Element object) instead of a CSS selector to the jQuery function to receive a jQuery object and call member functions.
		// $(element).css("color",color[index]);
		
		// this: Keyword used in the callback function to represent the Element object of the tag being processed.
		// this: JavaScript object - Element object, $(this): jQuery object
		$(this).css("color",color[index]);
	});
	
	</script>
</body>
</html>
```