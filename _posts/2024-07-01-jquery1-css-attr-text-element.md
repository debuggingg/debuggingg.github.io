---
layout: single
title: 2024/07/01 jQuery 02-CSS-Attr-Text-Element
---
# JQuery

## CSS

```html
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
#displayDiv {
	margin: 20px;
	padding: 10px;
	color: red;
	border: 1px solid blue;
	font-size: 1.5em;
	text-align: center; 
}
</style>
</head>
<body>
	<h1>css Member Function</h1>
	<hr>
	<div id="displayDiv">Very important content to be displayed in the browser</div>
	
	<script type="text/javascript">
	//$(selector).css(name) : A member function that returns the CSS style property value of the given name for the tags found by the selector.
	//alert("CSS Style Property Value = " + $("#displayDiv").css("margin")); //CSS Style Property Value = 20px
	//alert("CSS Style Property Value = " + $("#displayDiv").css("padding")); //CSS Style Property Value = 10px
	//alert("CSS Style Property Value = " + $("#displayDiv").css("color")); //CSS Style Property Value = rgb(255, 0, 0)
	//alert("CSS Style Property Value = " + $("#displayDiv").css("border")); //CSS Style Property Value = 1px solid rgb(0, 0, 255)
	//alert("CSS Style Property Value = " + $("#displayDiv").css("font-size")); //CSS Style Property Value = 24px
	//For CSS style property names that contain a hyphen [-], you can express them using JavaScript camelCase notation.
	//alert("CSS Style Property Value = " + $("#displayDiv").css("fontSize")); //CSS Style Property Value = 24px
	//alert("CSS Style Property Value = " + $("#displayDiv").css("text-align")); //CSS Style Property Value = center
	//alert("CSS Style Property Value = " + $("#displayDiv").css("textAlign")); //CSS Style Property Value = center
	
	//$(selector).css(name, value) : A member function that changes the CSS style property value of the given name to the specified value for the tags found by the selector.
	/*
	$("#displayDiv").css("width", "50%");
	$("#displayDiv").css("position", "absolute");
	$("#displayDiv").css("top", "200px");
	$("#displayDiv").css("left", "100px");
	*/
	
	//You can call the $(selector).css(object) method to change the CSS style of the tags found by the selector using the elements (style property names) and element values (style property values) of the JSON-formatted Object passed as an argument.
	$("#displayDiv").css({"width":"50%", "position":"absolute", "top":"200px", "left":"100px"});
	</script>
</body>
</html>
```
---
## Attr

```html
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>attr Member Function</h1>
	<hr>
	<div id="imgDiv" data-value="KoalaImage">
		<img alt="Koala" src="/web/jquery/images/Koala.jpg" width="200" id="img">
	</div>
	
	<script type="text/javascript">
	//$(selector).attr(name): A member function that returns the attribute value of the given name for the tags found by the selector.
	//alert("Attribute Value = " + $("#imgDiv").attr("data-value")); //Attribute Value = KoalaImage
	//alert("Attribute Value = " + $("#img").attr("src")); //Attribute Value = /web/jquery/images/Koala.jpg
	
	/*
	//$(selector).attr(name, value): A member function that changes (or adds) the attribute value of the given name to the specified value for the tags found by the selector.
	$("#img").attr("src", "/web/jquery/images/Penguins.jpg");
	$("#img").attr("width", "300");
	*/
	
	//You can call the $(selector).attr(object) method to change the attributes of the tags found by the selector using the elements (attribute names) and element values (attribute values) of the JSON-formatted Object passed as an argument.
	$("#img").attr({"src":"/web/jquery/images/Penguins.jpg", "width":"300"});
	</script>
</body>
</html>
```
---
## Text


```html
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>text Member Function and html Member Function</h1>
	<hr>
	<div id="displayDiv"><p>Using text and html member functions</p></div>
	<hr>
	<h3>Practice with jQuery object member functions</h3>
	<h3>Practice with jQuery object member functions</h3>
	<h3>Practice with jQuery object member functions</h3>
	<h3>Practice with jQuery object member functions</h3>
	<h3>Practice with jQuery object member functions</h3>
	
	<script type="text/javascript">
	/*
	//$(selector).text() : A member function that returns the tag content of the tags found by the selector
	// => Returns the tag content excluding the HTML tags
	alert("Tag Content = " + $("#displayDiv").text());
	
	//$(selector).html() : A member function that returns the tag content of the tags found by the selector
	// => Returns the tag content including the HTML tags
	alert("Tag Content = " + $("#displayDiv").html());
	*/
	
	/*
	//$(selector).text(value) : A member function that changes the tag content of the tags found by the selector to the value passed as a parameter
	// => If the parameter value contains HTML tags, they are treated as plain text
	//$("#displayDiv").text("<p style='color:red;'>Tag content changed by text() member function</p>");

	//$(selector).html(value) : A member function that changes the tag content of the tags found by the selector to the value passed as a parameter
	// => If the parameter value contains HTML tags, they are treated as tags
	$("#displayDiv").html("<p style='color:red;'>Tag content changed by html() member function</p>");
	*/
	
	//Implicit repetition changes the tag content of all found tags to the same content
	//$("h3").text("Practice with jQuery object member functions - changed");
	
	/*
	//By calling the each() member function, you can use explicit iteration to get all found tags one by one and change their tag content to different values
	$("h3").each(function(i) {
		$(this).text("Practice with jQuery object member functions - " + (i+1));
	});
	*/
	
	//By calling the $(selector).text(callback([index][, content])) function, you can process explicit iteration
	// => You can call the text() or html() member function by passing a callback function as a parameter
	// => The callback function called with the parameters must return a string value - the returned string value is used to change the tag content
	// => The callback function parameters provide the index and tag content, which can be used in the callback function
	$("h3").text(function(index, content) {
		return content + " - " + (index+1);
	});
	</script>
</body>
</html>
```
---
## element Add-Remove


```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>Adding and Removing Tags</h1>
	<hr>
	<div id="imagesList1"></div>
	<hr>
	<div id="imagesList2"></div>
	<hr>
	<div id="imagesList3">
		<img alt="Number Image" src="/web/jquery/images/1.png" width="200" id="img1">
		<img alt="Number Image" src="/web/jquery/images/2.png" width="200" id="img2">
		<img alt="Number Image" src="/web/jquery/images/3.png" width="200" id="img3">
	</div>
	
	<script type="text/javascript">
	//$(selector).append(newItem): A member function that creates and adds the tag passed as a parameter as the last child tag of the tags found by the selector - adds the tag to the DOM tree
	$("#imagesList1").append("<img src='/web/jquery/images/1.png' width='200'>");
	$("#imagesList1").append("<img src='/web/jquery/images/2.png' width='200'>");
	
	//$(selector).prepend(newItem): A member function that creates and adds the tag passed as a parameter as the first child tag of the tags found by the selector - adds the tag to the DOM tree
	$("#imagesList1").prepend("<img src='/web/jquery/images/3.png' width='200'>");
	$("#imagesList1").prepend("<img src='/web/jquery/images/4.png' width='200'>");

	//$(selector).before(newItem): A member function that creates and adds the tag passed as a parameter before the tags found by the selector - adds the tag to the DOM tree
	$("#imagesList1").find("img[src='/web/jquery/images/1.png']").before("<img src='/web/jquery/images/5.png' width='200'>");

	//$(selector).after(newItem): A member function that creates and adds the tag passed as a parameter after the tags found by the selector - adds the tag to the DOM tree
	$("#imagesList1").find("img[src='/web/jquery/images/1.png']").after("<img src='/web/jquery/images/6.png' width='200'>");
	
	//$(selector).appendTo(targetSelector): A member function that moves the tags found by the selector as the last child tag of the tags found by the target selector
	//$("#img1").appendTo("#imagesList2");
	//If you write an HTML tag as a parameter to a jQuery function instead of a selector, the Element object is created and returned as a jQuery object, allowing you to call member functions
	// => Adds the tag to the DOM tree and displays it - provides similar functionality to append() member function
	$("<img src='/web/jquery/images/1.png' width='200'>").appendTo("#imagesList2");
	$("<img src='/web/jquery/images/2.png' width='200'>").appendTo("#imagesList2");
	
	//$(selector).prependTo(targetSelector): A member function that moves the tags found by the selector as the first child tag of the tags found by the target selector
	$("<img src='/web/jquery/images/3.png' width='200'>").prependTo("#imagesList2");
	$("<img src='/web/jquery/images/4.png' width='200'>").prependTo("#imagesList2");

	//$(selector).insertBefore(targetSelector): A member function that moves the tags found by the selector before the tags found by the target selector
	$("<img src='/web/jquery/images/5.png' width='200'>").insertBefore("#imagesList2 img[src='/web/jquery/images/1.png']");

	//$(selector).insertAfter(targetSelector): A member function that moves the tags found by the selector after the tags found by the target selector
	$("<img src='/web/jquery/images/6.png' width='200'>").insertAfter("#imagesList2 img[src='/web/jquery/images/1.png']");

	//$(selector).remove([selector]): A member function that removes the tags found by the selector
	// => Searches the DOM tree for the tags and removes them so they are not displayed
	//$("#imagesList3").remove(); //Deleting the parent tag also deletes the child tags
	
	//$("#imagesList3").children().eq(1).remove();
	//$("#imagesList3").children(":eq(1)").remove();
	//$("#imagesList3").children().remove(":eq(1)");
	$("#img2").remove();
	</script>
</body>
</html>
```
---
## Element Move


```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>Moving Tags</h1>
	<hr>
	<div id="imageList"></div>
	
	<script type="text/javascript">
	for (i = 0; i < 10; i++) {
		// Create new tags and add them as the last child tag of the tags found by the selector, then display
		$("#imageList").append("<img src='/web/jquery/images/" + i + ".png' width='150'>");
	}
	
	setInterval(function() {
		// Move the tags found by the selector to the last child tag of the tags found by the target selector, then display
		$("#imageList").children(":eq(0)").appendTo("#imageList");		
	}, 1000);
	</script>
</body>
</html>
```

In this example, images are appended to the `#imageList` div initially. Then, every second, the first image in the list is moved to the end, creating a rotating effect.