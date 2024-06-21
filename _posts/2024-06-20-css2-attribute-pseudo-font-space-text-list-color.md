---
layout: single
title: 20240620 CSS - 02-Attribute-Pseudo-Font-Space,Text,List,Color
---
## Attribute

-  Attribute Selector: Selects elements based on their attribute and value to apply styles 
	- Format: selector[attribute] - Selects elements with the specified attribute 
		- ex) div[title] { color: green; }

-  Format: selector[attribute='value'] - Selects elements with the specified attribute value 
		- ex) div[title='choice'] { color: blue; }

-  Format: selector[attribute*='value'] - Selects elements with an attribute value that contains the specified value 
		- ex) div[title*='woman'] { color: gold; } 

-  Format: selector[attribute~='value'] - Selects elements with an attribute value that is one of the space-separated values 
		- ex) div[title~='cosmetic'] { color: silver; } 

-  Format: selector[attribute^='value'] - Selects elements with an attribute value that starts with the specified value 
		- ex) div[title^='man'] { color: orange; } 

-  Format: selector[attribute$='value'] - Selects elements with an attribute value that ends with the specified value 
		- ex) div[title$='남성'] { color: maroon; }

## Full code

```html

    <style type="text/css">
    div {
        font-size: 20px;
        line-height: 35px;
        color: red;
    }

    div[title] { color: green; }

    div[title='choice'] { color: blue; }

    div[title*='woman'] { color: gold; } 

    div[title~='cosmetic'] { color: silver; }

    div[title^='man'] { color: orange; } 

    div[title$='man'] { color: maroon; } 
    </style>
</head>
<body>
    <h1>Selectors</h1>
    <hr>
    <p>Attribute selectors - Select elements based on their attributes and attribute values</p>
    <hr>
    <div>This is very important content that will be displayed in the browser.</div>		
    <div title="">This is very important content that will be displayed in the browser.</div>		
    <div title="choice">This is very important content that will be displayed in the browser.</div>
    <hr>
    <div title="woman">Cosmetics-1</div>		
    <div title="cosmetic">Cosmetics-2</div>		
    <div title="womancosmetic">Cosmetics-3</div>		
    <div title="woman cosmetic">Cosmetics-4</div>		
    <div title="mancosmetic">Cosmetics-5</div>		
    <div title="man cosmetic">Cosmetics-6</div>		
</body>
```
---

## Pseudo Class Selector

- Pseudo-class Selector: Applies styles based on the state of the selected tag
	-  Format: selector:state - Different states are available depending on the selected tag 

* link: Applies styles when the URL specified in the a tag has never been requested by the browser 
	* ex) a:link { color: lime; }

* visited: Applies styles when the URL specified in the a tag has been requested at least once by the browser 
	* ex) a:visited { color: red; }

* active: Applies styles when the mouse button is pressed on the tag 
	* ex) a:active { color: green; }

* hover: Applies styles when the mouse cursor is positioned over the tag 
	* ex) a:hover {
		color: blue;
		text-decoration: underline;}

* first-child: Applies styles to the first child tag among child tags 
	* ex) ul li:first-child { color: red; }

* last-child: Applies styles to the last child tag among child tags 
	* ex) ul li:last-child { color: green; }

* nth-child(n): Applies styles to the nth child tag among child tags 
	* ex) ul li:nth-child(3) { color: blue; }

* nth-child(2n): Applies styles to even-numbered child tags 
	* ex) ol li:nth-child(2n) { color: gold; }
	  ol li:nth-child(even) { color: gold; }

* nth-child(2n-1): Applies styles to odd-numbered child tags 
	* ex) ol li:nth-child(2n-1) { color: silver; }
		ol li:nth-child(odd) { color: silver; }
		input { border: 1px solid gray; }
		input[type='text'] { border: 1px solid maroon; }

* not(selector): Applies styles to all tags except the ones selected by the selector 
	* ex) input:not([type='text']) { border: 1px solid orange; }

* disabled: Applies styles to disabled tags 
* checked: Applies styles to selected tags in input elements (radio or checkbox) 
* selected: Applies styles to selected items (option tags) in select elements 
	* ex) input[type='text']:disabled { border: 1px solid red; }

* focus: Applies styles to tags that have focus 
	* ex) input:focus { border: 3px double green; }

## Full Code
```html

    <style type="text/css">
    a {
        color: orange;
        text-decoration: none;
    }

    a:link { color: lime; }

    a:visited { color: red; }

    a:active { color: green; }

    a:hover {
        color: blue;
        text-decoration: underline;
    }

    ul li:first-child { color: red; }

    ul li:last-child { color: green; }

    ul li:nth-child(3) { color: blue; }

    ol li:nth-child(2n) { color: gold; }
    ol li:nth-child(even) { color: gold; }

    ol li:nth-child(2n-1) { color: silver; }
    ol li:nth-child(odd) { color: silver; }

    input { border: 1px solid gray; }
    input[type='text'] { border: 1px solid maroon; }

    input:not([type='text']) { border: 1px solid orange; }

    input[type='text']:disabled { border: 1px solid red; }

    input:focus { border: 3px double green; }
    </style>
</head>
<body>
    <h1>Selectors</h1>
    <hr>
    <p>Pseudo-class selectors - Apply styles based on the state of tags</p>
    <hr>
    <a href="https://m.daum.net">Daum</a>&nbsp;&nbsp;&nbsp;
    <a href="https://www.naver.com">Naver</a>&nbsp;&nbsp;&nbsp;
    <a href="https://www.google.com">Google</a>&nbsp;&nbsp;&nbsp;
    <hr>
    <ul>
        <li>Hong Gil-dong</li>
        <li>Im Kkeok-jeong</li>
        <li>Jeon Woo-chi</li>
        <li>Iljimae</li>
        <li>Jang Gil-san</li>
    </ul>
    <hr>
    <ol>
        <li>Hong Gil-dong</li>
        <li>Im Kkeok-jeong</li>
        <li>Jeon Woo-chi</li>
        <li>Iljimae</li>
        <li>Jang Gil-san</li>
    </ol>
    <hr>
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
            <td>Name</td>
            <td><input type="text" name="name" value="Hong Gil-dong" disabled></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="email" name="email"></td>
        </tr>
    </table>
</body>
</html>
```
---

## Font 

  - Google Web Fonts can be used - refer to https:fonts.google.com 
	  - ex)```
	```html
	<link rel="preconnect" href="https:fonts.googleapis.com">
	<link rel="preconnect" href="https:fonts.gstatic.com">
	<link href="https:fonts.googleapis.comcss2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
	<title>CSS<title>
	<style type="textcss">
```

- Provide web fonts so that all clients use the same font 
-  Web Font: Function to provide font files stored on the web server to the client 
-  Font files: ttf files (platform font files), woff files, etc. 

- @font-face: A system property to provide font files to the client 
-  font-family: Sets the identifier of the font as the property value (font name) 
* src: Uses the url function to set the URL address of the font file as the property value 
	* ex)
```html
@font-face {
	font-family: "NanumGothic";
	src: url("webcssfontsNanumGothic.woff");}
```

-  font-family: Font-related style property - sets the font as the property value 
	- Fonts can be listed and applied sequentially using the [,] symbol 
-  If the font-family property is omitted or the fonts set as property values are not available, the platform's default font is used 
	- Different default fonts are used on each platform, resulting in different page displays 

-  font-size: Style property for font size - default size: 16px 
-  Property values (units): px, pt, em, %, keywords (small, medium, large, etc.) 
- font-style: Style property for font slant - normal (default), italic 
	-  Keywords related to font size increase or decrease by 1.2 times based on medium (16px) 
-  font-variant: Style property for English letters - normal (default), small-caps (capitalizes English letters and makes the remaining letters small except for the first letter) 
-  em: Sets the font size based on the width of the M character  used in responsive web design 
- font-weight: Style property for font weight 100~900 (default: 400), keywords (normal, bold, etc.) 
- font: A property to apply all style values related to fonts 
	- The font must be set to apply font-related style values 
-  letter-spacing: Style property to set the spacing between letters - units: px, em, %, etc. 
-  word-spacing: Style property to set the spacing between words - units: px, em, %, etc. 
-  line-height: Style property to set line spacing - units: px, em, %, etc. 
-  font: Style property that uses [font-sizeline-height] as property values 
## Full Code

```html

<head>
    <meta charset="UTF-8">
    <title>CSS</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <style type="text/css">
    @font-face {
        font-family: "NanumGothic";
        src: url("/web/css/fonts/NanumGothic.woff");
    }

    @font-face {
        font-family: "NanumBarunGothic";
        src: url("/web/css/fonts/NanumBarunGothic.woff");
    }

    body {
        font-family: "Noto Sans KR", sans-serif;
    }
    body {
        font-family: "sans-serif", "궁서체", "맑은 고딕";
    }
    .font1 {
        font-size: 14px;
    }

    .font2 {
        font-size: 20px;
        font-style: italic;
    }

    .font3 {
        font-size: xx-large;
        font-variant: small-caps;
    }

    .font4 {
        font-size: 1.5em;
        font-weight: bold;
    }

    .font5 {
        font: italic 700 200% "궁서체";
    }
    .gab {
        font-size: 30px;
    }

    .gab1 {
        letter-spacing: 0.1em;
    }

    .gab2 {
        word-spacing: 0.5em;
    }

    .gab3 {
        line-height: 50px;
    }

    .gab4 {
        font: 30px/60px "궁서체";
    }
    </style>
</head>
<body>
    <h1>Font-related Style Properties</h1>
    <hr>
    <p>Very important content to be displayed in the browser.</p>
    <p>Very important content to be displayed in the browser.</p>
    <p>Very important content to be displayed in the browser.</p>
    <p>Very important content to be displayed in the browser.</p>
    <p>Very important content to be displayed in the browser.</p>

    <h1>Text-related Style Properties</h1>
    <hr>
    <p class="font1">Text-related Style Properties-1 (Font Style Attribute)</p>
    <p class="font2">Text-related Style Properties-2 (Font Style Attribute)</p>
    <p class="font3">Text-related Style Properties-3 (Font Style Attribute)</p>
    <p class="font4">Text-related Style Properties-4 (Font Style Attribute)</p>
    <p class="font5">Text-related Style Properties-5 (Font Style Attribute)</p>
    <h1>Text Spacing-related Style Properties</h1>
    <hr>
    <p class="gab">Client Script Language</p>
    <p class="gab gab1">Client Script Language</p>
    <p class="gab gab2">Client Script Language</p>
    <hr>
    <h2>HTML5<br>CSS3</h2>
    <h2 class="gab3">HTML5<br>CSS3</h2>
    <h2 class="gab4">HTML5<br>CSS3</h2>
</body>
```

---
##  Space,Text,List,Color

-  Automatically wraps content if it exceeds the width of the tag that is why we need space control 
- white-space: 
	- Style property for handling spaces
		- Provides functionality for handling content that exceeds the tag width 
		- Property values: normal (default - pre-line), pre, nowrap, pre-wrap 
		- pre-line: Converts multiple spaces into one space and automatically wraps content if it exceeds the tag width
		- pre: Keeps multiple spaces as is and displays content that exceeds the tag width outside the tag 
			- The overflow style property can handle content that exceeds the tag 
		- nowrap: Converts multiple spaces into one space and displays content that exceeds the tag width outside the tag 
		- pre-wrap: Keeps multiple spaces as is and automatically wraps content if it exceeds the tag width 
- direction: 
	- Style property for writing direction 
		- Property values: ltr (left to right - default), rtl (right to left) 
-  text-align: 
	- Style property for text alignment 
		- Property values: left (default), right, center, justify (both sides) 
- text-shadow: 
	- Style property to provide shadow effect on text 
		- Property values: horizontal vertical blur color 
    
- overflow:
	- Style property related to handling content that exceeds the tag 
		- Property values: visible (default) - display, hidden - hide, scroll - provide scroll 
	- text-overflow: Style property related to handling hidden text 
		- Property values: clip (default) - cut off, ellipsis - use [...] symbol 
    -  text-decoration: Style property for line-related text 
	    - Property values: none (default), underline, line-through, overline 
	    
    - text-indent: Style property for text indentation - provides margin at the beginning of the text 
    - text-transform: Style property for text transformation (for English letters) 
	    - Property values: none (default), uppercase, lowercase, capitalize 

-  list-style-type: 
	- Style property for bullet or order values for list items (li tags) 
		- Property values: none - no bullets or order values 
        - Property values (ul tags) - disc (default), circle, square, etc. 
        - Property values (ol tags) - decimal (default), lower-alpha, upper-alpha, lower-roman, upper-roman, etc. 
- list-style-image: 
	- Style property to use an image file as a bullet using the url function 
-  list-style-position: 
	- Style property related to the output position of the list 
		- Property values: inside (indented), outside (outdented) 
- **list-style: Style property to use all style values related to lists** 

- color: 
	- Style property for text color 
	    - Property values: color-related keywords - red, green, blue, etc. 
	    - Property values: hexadecimal notation for color - #RGB or #RRGGBB 
	    - Property values: rgb (RED: 0~255, GREEN: 0~255, BLUE: 0~255) function 
	    - Property values: hsl (hue: 0~360, saturation: 0~100%, lightness: 0~100%) function 
	    - Property values: rgba (RED: 0~255, GREEN: 0~255, BLUE: 0~255, transparency: 0.0 (transparent) ~ 1.0 (opaque)) function 
	    - Property values: hsla (hue: 0~360, saturation: 0~100%, lightness: 0~100%, transparency: 0.0 (transparent) ~ 1.0 (opaque)) function 
	    
```html
<style type="text/css">
	    div {margin: 5px;
	     width: 200px; 
	     border: 1px solid red; }
	    .space1 { white-space: pre-line; }
	    .space2 { white-space: pre; }
	    .space3 { white-space: nowrap; }
	    .space4 { white-space: pre-wrap; }
	    div {
	        margin: 15px;
	        width: 500px;
	        border: 1px solid red;}
	    .dir1 { direction: ltr; }
	    .dir2 { direction: rtl; }
	    
	    .align { width: 150px; }
	    .align1 { text-align: left; }
	    .align2 { text-align: right; }
	    .align3 { text-align: center; }
	    .align4 { text-align: justify; }
	    
	    .shadow {
	        font-size: 30px;
	        font-weight: bold;
	        color: gray;
	        text-shadow: 3px 3px 3px #000;}
	    .overflow {
	        width: 200px;
	        white-space: nowrap;
	    }
	    .overflow1 { overflow: hidden; }
	    .overflow2 { overflow: scroll; }
	    .overflow3 {
	        overflow: hidden;
	        text-overflow: ellipsis;
	    }
	    .deco1 { text-decoration: underline; }
	    .deco2 { text-decoration: line-through; }
	    .deco3 { text-decoration: overline; }
	
	    .in1 { text-indent: 20px; }
	    .in2 { text-indent: 30px; }
	    .in3 { text-indent: 40px; }
	    .trans1 { text-transform: uppercase; }
	    .trans2 { text-transform: lowercase; }
	    .trans3 { text-transform: capitalize; }
	
	    ul li {
	        list-style-type: square;
	        list-style-image: url("/web/css/images/bullet.png");
	        list-style-position: inside; }
	
	    ol li { list-style: upper-roman inside;  }
	    div {
	        margin: 20px;
	        font-size: 24px;
	        font-weight: bold; }
	
	    .color1 { color: red; }
	    .color2 { color: #00FF00; }
	    .color3 { color: rgb(0, 0, 255); }
	    .color4 { color: hsl(130, 100%, 50%); }
	    .color5 { color: rgba(0, 0, 255, 0.5); }
	    .color6 { color: hsl(200, 100%, 50%, .5); }
	    </style>
</head>
<body>
    <h1>Space-related Style Properties</h1>
    <hr>
    <!-- Multiple spaces are converted to one space -->
    <div class="space1">Creating a website using web standards (HTML5 + CSS3 + JavaScript)</div>
    <div class="space2">Creating a website using web standards (HTML5 + CSS3 + JavaScript)</div>
    <div class="space3">Creating a website using web standards (HTML5 + CSS3 + JavaScript)</div>
    <div class="space4">Creating a website using web standards (HTML5 + CSS3 + JavaScript)</div>

    <h1>Text-related Style Properties</h1>
    <hr>
    <div class="dir1">This is very important content to be displayed in the browser.</div>
    <div class="dir2">This is very important content to be displayed in the browser.</div>
    <hr>
    <div class="align align1">This is very important content to be displayed in the browser.</div>
    <div class="align align2">This is very important content to be displayed in the browser.</div>
    <div class="align align3">This is very important content to be displayed in the browser.</div>
    <div class="align align4">This is very important content to be displayed in the browser.</div>
    <hr>
    <div class="shadow">Learning HTML5 and CSS3.</div>
    <hr>
    <div class="overflow overflow1">Learning HTML5 and CSS3.</div>
    <div class="overflow overflow2">Learning HTML5 and CSS3.</div>
    <div class="overflow overflow3">Learning HTML5 and CSS3.</div>
    <hr>
    <div class="deco1">Web programs are created using servlet or jsp.</div>
    <div class="deco2">Web programs are created using servlet or jsp.</div>
    <div class="deco3">Web programs are created using servlet or jsp.</div>
    <hr>
    <div class="in1">I plan to study JavaScript after learning CSS3.</div>
    <div class="in2">I plan to study JavaScript after learning CSS3.</div>
    <div class="in3">I plan to study JavaScript after learning CSS3.</div>
    <hr>
    <div class="trans1">After learning JavaScript, I plan to learn jQuery.</div>
    <div class="trans2">After learning JavaScript, I plan to learn jQuery.</div>
    <div class="trans3">After learning JavaScript, I plan to learn jQuery.</div>

    <h1>Text Color-related Style Properties</h1>
    <hr>
    <div class="color1">Learning text color-related style properties.</div>
    <div class="color2">Learning text color-related style properties.</div>
    <div class="color3">Learning text color-related style properties.</div>
    <div class="color4">Learning text color-related style properties.</div>
    <div class="color5">Learning text color-related style properties.</div>
    <div class="color6">Learning text color-related style properties.</div>
	<h1>List-related Style Properties</h1> 
	<hr>
	<!-- ul tag provides bullets for list items (li tags) -->
	<ul>
		<li>Unordered list - 1</li> 
		<li>Unordered list - 2</li> 
		<li>Unordered list - 3</li>
	</ul>
	<!-- ol tag provides order values for list items (li tags) -->
	<ol>
		<li>Ordered list - 1</li>
		<li>Ordered list - 2</li>
		<li>Ordered list - 3</li>
	</ol>
	</body> 
```