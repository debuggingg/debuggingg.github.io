---
layout: single
title: 2024/06/27 JavaScript 08-DOM- Element-add,remove,innerHTML
---

# DOM (Document Object Model)

## Element

- script- nomaly it put on the bottom on the body tag, expect using function on the top then you can use script on the head. 
	
```html
<script type="text/javascript">
/*
//window.onload : Register the event handler to the event listener property that is triggered when the window object is completed
window.onload=function() {
	alert("Write commands for implementing a dynamic page");
}
*/
</script>
</head>
<body>
	<h1>DOM(Document Object Model)</h1>
	<hr>
	<p>DOM: A JavaScript object provided based on the Document object - Node object</p>
	<p>The client requests a web program from the server using a browser, receives the result as an HTML document,
	uses the browser engine's DOM parser to read the HTML document and create a DOM tree, applies the CSS stylesheet, 
	and then outputs it in the browser</p> 
	<p>By using JavaScript, you can create dynamic pages by finding and manipulating tags (Node objects) in the DOM tree when an event occurs on the tag - DHTML (Dynamic HTML)</p>
	<p>Dynamic pages can only be implemented after the DOM tree is completed- onload </p>
	<p>Node object: Represents tags (TagNode) and tag content (TextNode) as JavaScript objects
	- Can be represented separately as Element objects and Text objects</p>
	<p>Element object: Represents only tags (TagNode) as a JavaScript object</p>
	<p>Text object: Represents only tag content (TextNode) as a JavaScript object</p>
	<hr>
	<h2 id="headline">Content output by the h2 tag</h2>
	<ul>
		<li>Content output by the li tag-1</li>
		<li>Content output by the li tag-2</li>
		<li>Content output by the li tag-3</li>
	</ul>
	<p class="text">Content output by the p tag</p>
	
	<!-- By writing the script tag as the last child tag of the body tag, you can write commands for implementing a dynamic page 
	even without registering an event handler in the onload event listener property -->
	<script type="text/javascript">
	//alert("Write commands for implementing a dynamic page");
	
	/*
	//document.getElementById(id) : A member function that searches for a tag by its id attribute value in the document object of the DOM tree and returns it as an Element object
	// => You can also search for and return child tags as Element objects using the Element object (tag) instead of the document object
	var h2E=document.getElementById("headline");
	//alert(h2E);//[object HTMLHeadingElement]
	
	//Element.nodeName : A member variable that stores the node name (tag name) of the Element object
	//alert(h2E.nodeName);//H2
	//Element.nodeName : A member variable that stores the node value (tag content) of the Element object
	//alert(h2E.nodeValue);//null
	
	//Element.firstChild : A member variable that stores the first child of the Element object
	// => If the first child is a tag, the Element object is stored, and if it is tag content, the Text object is stored
	//alert(h2E.firstChild);//[object Text]
	//alert(h2E.firstChild.nodeName);//#text
	//alert(h2E.firstChild.nodeValue);//Content output by the h2 tag
	
	h2E.firstChild.nodeValue="Content changed by JavaScript";
	*/
	
	/*
	//document.getElementsByTagName(tagName) : A member function that searches for multiple tags by their name in the document object of the DOM tree and returns them as an HTMLCollection object (NodeList object)
	//HTMLCollection object (NodeList object): A collection object that stores multiple tags (Element objects)
	var liList=document.getElementsByTagName("li");
	//alert(liList);//[object HTMLCollection]
	
	//HTMLCollection.length : A member variable that stores the number of elements stored in the HTMLCollection (NodeList object)
	//alert(liList.length);//3
	
	for(i=0;i<liList.length;i++) {
		//HTMLCollection.item(index) : A member function that returns the element (Element) at the specified index position in the HTMLCollection (NodeList object)
		var liE=liList.item(i); 
		//alert(liE);
		alert(liE.firstChild.nodeValue);		
	}
	*/
	
	/*
	//document.getElementsByClassName(class) : A member function that searches for multiple tags by their class attribute value in the document object of the DOM tree and returns them as an HTMLCollection object (NodeList object)
	var pList=document.getElementsByClassName("text");
	//alert(pList);//[object HTMLCollection]
	//alert(pList.length);
	alert(pList.item(0).firstChild.nodeValue);
	*/
	
	/*
	//document.querySelector(selectors) : A member function that searches for a single tag using a CSS selector in the document object of the DOM tree and returns it as an Element object
	var h2E=document.querySelector("#headline");
	alert(h2E.firstChild.nodeValue);
	*/
	
	//document.querySelectorAll(selectors) : A member function that searches for multiple tags using a CSS selector in the document object of the DOM tree and returns them as an HTMLCollection object (NodeList object)
	var pList=document.querySelectorAll(".text");
	alert(pList.item(0).firstChild.nodeValue);
	</script>
</body>
</html>
```
---
## Element -add-remove-inner

```html
<title>JavaScript</title>
</head>
<body>
	<h1>DOM(Document Object Model)</h1>
	<hr>
	<div id="imageDiv"></div>
	
	<hr>
	<h2>Element-1</h2>
	<h2 id="remove">Element-2</h2>
	
	<hr>
	<h2>Element-3</h2>
	<hr>
		<ul>
		<li>Menu-1</li>
		<li>Menu-2</li>
		<li>Menu-3</li>
	</ul>
	<ul>
		<li>Menu-4</li>
		<li>Menu-5</li>
		<li>Menu-6</li>
	</ul>
	<hr>
	<div id="text"></div>
	
	<script type="text/javascript">
	//document.createElement(tagName): A member function that creates a tag with the name received as a parameter and returns it as an Element object
	var h2E = document.createElement("h2"); //<h2></h2>
	//alert(h2E); //[object HTMLHeadingElement]
	
	//document.createTextNode(content): A member function that creates and returns a Text object with the tag content received as a parameter
	var h2T = document.createTextNode("Content output by the h2 tag");
	//alert(h2T); //[object Text]
	
	//Element.appendChild(newElement): A member function that adds the Element object (Text object) received as a parameter as the last child object of the Element object
	h2E.appendChild(h2T); //<h2>Content output by the h2 tag</h2>
	
	//document.body: Represents the body tag of the HTML document as a JavaScript object
	//alert(document.body); //[object HTMLBodyElement]
	//Add to the end of the child tags of the body tag in the HTML document - add the tag to the DOM tree for output
	document.body.appendChild(h2E);
	
	var imgE = document.createElement("img"); //<img>
	
	//Element.attributeName: Represents the attribute of the tag (Element object) to add or change the attribute value of the tag
	imgE.src = "/web/js/images/Koala.jpg"; //<img src="/web/js/images/Koala.jpg">
	
	//Element.setAttribute(attributeName, attributeValue): A member function that adds or changes an attribute of the tag (Element object)
	//Element.getAttribute(attributeName): A member function that returns the attribute value of the tag (Element object)
	//Element.removeAttribute(attributeName): A member function that deletes an attribute of the tag (Element object)
	imgE.setAttribute("width", "300"); //<img src="/web/js/images/Koala.jpg" width="300">
	
	//Search the DOM tree for a tag and add it as the last child tag of the found tag for output
	//<div id="imageDiv"><img src="/web/js/images/Koala.jpg" width="300"></div>
	document.getElementById("imageDiv").appendChild(imgE);
	
	var hrE = document.createElement("hr");
	//Element.insertBefore(newElement, before): A member function that searches the DOM tree for a tag (before) and inserts a new tag (newElement) before the found tag
	document.body.insertBefore(hrE, h2E);



// Remove 
	var h2E = document.getElementById("remove");
	
	/*
	//Element.removeChild(oldElement): A member function that deletes a child Element object of the Element object
	// => Search the DOM tree for a tag and delete it so that it is not output
	document.body.removeChild(h2E);
	*/
	
	//Element.parentElement: A member variable that stores the parent Element object of the Element object
	h2E.parentElement.removeChild(h2E);
	
	
// Inner 
	/*
	// Write a program that finds the li tag with the tag content [Menu-2] among the li tags and changes the tag content to [New Menu]
	var ulList = document.getElementsByTagName("ul");
	var liList = ulList.item(0).getElementsByTagName("li");
	var liE = liList.item(1); //<li>Menu-2</li>
	
	// Change the tag content of the first child (Text object) of the Element object
	//liE.firstChild.nodeValue = "New Menu";
	
	var newText = document.createTextNode("New Menu");
	// Element.replaceChild(newElement, oldElement) : A member function that changes the child Element object (Text object) of the Element object to a new Element object (Text object)
	liE.replaceChild(newText, liE.firstChild);
	*/
	
	/*
	var divE = document.getElementById("text"); //<div id="text"></div>
	var pE = document.createElement("p"); //<p></p>
	var pT = document.createTextNode("Content output by the p tag");
	pE.appendChild(pT); //<p>Content output by the p tag</p>
	divE.appendChild(pE); //<div id="text"><p>Content output by the p tag</p></div>
	*/
	
	var divE = document.getElementById("text"); //<div id="text"></div>
	// Element.innerHTML : A member variable that stores child tags (tag content) in the Element object
	// => Changing the value stored in the innerHTML member variable allows the tag or tag content to be changed
	divE.innerHTML = "<p>Content output by the p tag</p>";
	</script>
</body>
</html>
```
---
## Clock 

Here is the English translation of your HTML document:

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
<style type="text/css">
#displayDiv {
	width: 600px;
	margin: 0 auto;
	padding: 30px 0;
	font-size: 2em;
	font-weight: bold;
	text-align: center;
	border: 2px solid black; 
}[p]
</style>
</head>
<body>
	<h1>DOM(Document Object Model)</h1>
	<hr>
	<div id="displayDiv">June 27, 2024 14:41:22</div>
	
	<script type="text/javascript">
	//Search for the tag in the document object of the DOM tree and save it as an Element object
	var displayDiv = document.getElementById("displayDiv");
	
	//Declare an anonymous function and save it to a variable
	// => Write a command to receive the current date and time of the client platform and change the tag content
	var displayClock = function() {
		//Create a Date object that stores the current date and time of the client platform and save it in a variable
		var now = new Date();
		
		//Receive the date and time stored in the Date object, generate a string to be printed as the tag content, and save it in a variable
		var printNow = now.getFullYear() + "/" + (now.getMonth() + 1) + "/" + now.getDate() + "/" 
			+ now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
		
		//Change the tag content to the string value with the date and time
		displayDiv.innerHTML = printNow;
	}
	
	displayClock();
	
	setInterval(displayClock, 1000);
	</script>
</body>
</html>
```
---