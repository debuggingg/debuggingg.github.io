---
layout: single
title: 2024/06/19 CSS -01 -Cascade-Format-DeriveSelector
---
## CSS 
- CSS is about designing HTML aesthetically.  CSS is represented as a style sheet (Style Sheet) that applies styles using code.  
	- style tag: Tag in HTML documents to apply a style sheet.  
	- type attribute: Attribute setting the type (MimeType) of the file for applying the style sheet.

#### select 
- Selector: Expression to select tags (box model) in HTML documents
	=> Represents selected tags as elements in the document
Selector[, Selector,...][:pseudo-class] {
	Property: Value [Value Value ...];
	Property: Value [Value Value ...];
	...
}
## Full code 

```html
<style type="text/css">
h1 {text-align: center;}	

p {
	font-size: 25px;
	color: green;
}
p:nth-child(2n) {color: blue;}
</style>
</head>
<body>
	<h1>CSS Style</h1>
	<hr>
	<p>Very important content to be displayed in the browser.</p>
	<p>Very important content to be displayed in the browser.</p>
	
</body>
```

---

## Cascade 

- Styles applied to parent tags are inherited by child tags 
- Styles are applied step by step based on the order of writing - overriding existing styles 
	- important: Style property value to apply the style with the highest priority, ignoring step-by-step application 
	- Exceptions to step-by-step application - Styles applied based on the hierarchical order of HTML tags 
<title>CSS</title>

```html
<style type="text/css">
body {color: red;}

p {color: green;}
li {color: blue !important;}
ul {
	font-size: 20px;
	color: lime;
}
li {color: aqua;}
</style>
</head>
<body>
	<h1>CSS Styles: Step-by-Step Application and Inheritance</h1>
	<hr>
	<p>This is very important content that will be displayed in the browser.</p>
	<ul>
		<li>Very Important Content-1</li>
		<li>Very Important Content-2</li>
		<li>Very Important Content-3</li>
	</ul>
</body>
```

---
## Format

<title>CSS</title>
 - link tag : Connects an external file to an HTML document for applying styles 
	=> To maintain consistent styles across all pages of a website, CSS files are linked using the link tag, enhancing productivity and maintenance efficiency
 - href attribute : Specifies the URL of the external file to be linked to the HTML document 
 - type attribute : Specifies the MIME type of the external file being linked 
- rel attribute : Defines the purpose of linking the external file to the document. To apply styles from a CSS file, use the value [stylesheet] 


## Full code

```html
<style type="text/css">
/* Instead of the link tag, CSS @import system property can be used to import CSS files into HTML documents and apply styles */
/* => Using @import system property with the url function to provide the path of the CSS file for applying styles */
@import url("/web/css/04_style.css");
h2 {color: skyblue;}

h3 {color: blue;}

h4 {color: aqua;}
</style>
</head>
<body>
	<h1>Applying Style Sheets to HTML Documents</h1>
	<hr>
	<h2>This is very important content that will be displayed in the browser.</h2>
	<h3>This is very important content that will be displayed in the browser.</h3>
	<!-- Applying style sheet using the style attribute on the tag - this style will override all previous styles -->
	<h4 style="color: maroon;">This is very important content that will be displayed in the browser.</h4>
</body>
```

### link(which is imported for above code)

```
@charset "UTF-8";

h1 {
color: red;
}
h2 {
color: pink;
}
```
---

## BasicSelector

- All selector: Applies styles to all tags as elements - not recommended 
	- ex)* {margin: 0;}
- Tag Selector: Selects elements by their tag name to apply styles 
	=> Applies the same style to multiple elements with the same tag name
- Class Selector: Selects elements by the value of their class attribute to apply styles
	=> Prefixed with [.] to differentiate from tag selectors 
	=> Applies the same style to multiple elements with the same class attribute value 
	
	 class attribute : Specifies the class name of the tag for applying styles 
		 => Applying the same style with identical class attribute values 
		 => Multiple attribute values can be separated by spaces 
			 ex) class="text1 text3"
- ID Selector: Selects elements by the value of their id attribute to apply styles
	=> Prefixed with [#] to differentiate from tag selectors
	=> Applies styles to a single element identified by a unique id attribute value 
		 [,] symbol allows multiple selectors to share the same style declaration
		 => Applies the same style to elements selected by multiple id attributes
			
	id attribute : Specifies a unique identifier for distinguishing tags
	Cannot assign the same id attribute value to multiple tags 
## Full code 

```html
<title>CSS</title>
<style type="text/css">
h2 { color: green; }
p { color: blue; }
.text1 { color: maroon; }
.text2 { color: pink; }
.text3 { text-align: center; }
#text4 { color: olive; }
#text5, #text6 { color: silver; }
</style>
</head>
<body>
	<h1>Selectors</h1>
	<hr>
	<p>Basic selectors - Tag selector, Class selector, ID selector</p>
	<hr>
	<h2>Tag Selector</h2>
	<p>This is very important content that will be displayed in the browser.</p>
	<p>This is very important content that will be displayed in the browser.</p>
	<p>This is very important content that will be displayed in the browser.</p>
	<hr>
	<h2>Class Selector</h2>
	<p class="text1">This is very important content that will be displayed in the browser.</p>
	<p class="text1 text3">This is very important content that will be displayed in the browser.</p>
	<p class="text2">This is very important content that will be displayed in the browser.</p>
	<hr>
	<p id="text4">This is very important content that will be displayed in the browser.</p>
	<p id="text5">This is very important content that will be displayed in the browser.</p>
</body>
```
---

## DeriveSelector

- Descendant Selector: Selects all child elements deeply nested inside a parent element and applies styles 
	_Format: parentSelector descendantSelector 
- Child Selector: Selects first-level child elements of a parent element and applies styles 
	_Format: parentSelector > childSelector 

 - Attribute Selector: Selects specific tags among elements selected by a selector and applies styles 
    => Always use class selectors([.]) to select specific tags as elements 
    _Format: selector.attributeSelector

 - General Sibling Selector: Selects sibling elements at the same level following an element selected by a selector and applies styles 
    _Format: selector ~ siblingSelector 

- Adjacent Sibling Selector: Selects the first sibling element at the same level following an element selected by a selector and applies styles 
    _Format: selector + siblingSelector 
    
    ```html
    <title>CSS</title>
    <style type="text/css">

        #super1 div { color: purple; }
        #super2 > div { color: gold; }
        #super3 div { color: orange; }
        #super3 div.choice { color: lime; }
        #standard ~ p { color: fuchsia; }
        #standard + p { color: red; }
    </style> 
    </head>
    <body>
    <h1>Selectors</h1>
    <hr>
    <p>Derived Selectors - Descendant Selector, Child Selector, Attribute Selector, Sibling Selectors</p>
    <hr>
    <h2>Descendant Selector</h2>
    <div id="super1">
        <div>Employee Names</div>
        <ul>
            <li><div>Hong Gil-dong</div></li>
            <li><div>Im Gyeong-jeong</div></li>
            <li><div>Jeon Woo-chi</div></li>
        </ul>
    </div>
    <hr>
    <h2>Child Selector</h2>
    <div id="super2">
        <div>Employee Names</div>
        <ul>
            <li><div>Hong Gil-dong</div></li>
            <li><div>Im Gyeong-jeong</div></li>
            <li><div>Jeon Woo-chi</div></li>
        </ul>
    </div>
    <h2>Attribute Selector</h2>
    <div id="super3">
        <div>Employee Names</div>
        <ul>
            <li><div>Hong Gil-dong</div></li>
            <li><div class="choice">Im Gyeong-jeong</div></li>
            <li><div>Jeon Woo-chi</div></li>
        </ul>
    </div>
    <hr>
    <h2>Sibling Selectors</h2>
    <p id="standard">This is very important content that will be displayed in the browser.</p>
    <p>More important content that will be displayed in the browser.</p>
    <p>Even more important content that will be displayed in the browser.</p>
    </body>
    ```