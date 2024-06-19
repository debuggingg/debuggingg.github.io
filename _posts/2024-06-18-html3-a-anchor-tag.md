---
layout: single
title: 2024/0618 HTML 03 -A tag-Anchor
---
## A Tag 

- **General:**
    
    - a tag: A tag to request a web document (web program) using the **GET** method via a **click event** 
    - => Provides hyperlink functionality 
    - => Connects pages to each other for displaying a new page - page navigation 
- **href Attribute:**
    
    - href attribute: Sets the URL of the web document (web program) to be requested when a click event occurs 
    - => When the content (text or image) of the a tag is clicked, the client browser's address area URL is changed to request the web document (web program) of the URL address and display the response 
- **target Attribute:**
    
    - target attribute: _self (current browser - default), _blank (new browser), _top (topmost browser), _parent (parent browser), _child (child browser), sets one of the name attribute values of the iframe tag (internal browser) as the attribute value 

## Full Code
### Main to kor, eng pages 
```html
- <body>
	<h1>a Tag</h1>
	<hr>
	<a href="https://www.daum.net">Daum</a>&nbsp;&nbsp;&nbsp;
	<a href="https://www.naver.com">Naver</a>&nbsp;&nbsp;&nbsp;
	<a href="https://www.google.com">Google</a>&nbsp;&nbsp;&nbsp;
	<hr>
	<a href="/web/html/07_kor.html"><img alt="Korean" src="/web/html/images/kor.png"></a>
	<a href="/web/html/07_kor.html" target="_blank"><img alt="Korean" src="/web/html/images/kor.png"></a>
	<a href="07_eng.html" target="_blank"><img alt="English" src="images/eng.png"></a>
</body>
```
### english to main 
```html
<body>

<h1>English Page</h1>

<hr>

<a href="/web/html/07_a.html">[home]</a>

</body>
```
### korean to main 
```html
<body>

<h1>한글 패이지</h1>

<a href="/web/html/07_a.html">home</a>

  

</body>
```
---
---
## Anchor

1. **General:**
    
    -  id attribute: Sets a unique identifier (value) to distinguish tags 
2. **a Tag Functionality:**
    
    -  Using the a tag, when a click event occurs, the web document (web program) at the URL address set in the href attribute is requested, and the response (HTML document) is displayed 
    -  => When setting the URL address in the href attribute of the a tag with a # symbol and the tag identifier (id attribute value), it moves to the tag of the specified identifier - Anchor functionality 
    -  Anchor: A feature to scroll to a specific tag in a page 
3. **Anchor Link Example:**
    
    -  Using # symbol in href attribute to set the tag identifier (id attribute value), it moves to the position of the tag specified by the identifier - Anchor functionality 
    -  If only the tag's output position is changed within a single page, related information of the requested web document (web program) can be omitted 
```html

 <body>
	<h1 id="top">a Tag</h1>
	<hr>
	<a href="/web/html/08_anchor.html#product">Product Introduction</a>&nbsp;&nbsp;&nbsp;
	<a href="08_anchor.html#review">Purchase Reviews</a>&nbsp;&nbsp;&nbsp;
	<a href="#message">Cautions</a>&nbsp;&nbsp;&nbsp;
	<hr>
	<h2 id="product">Product Introduction</h2>
<p> what ever introducing............
.
.
.
.
.
.
.
.
</p>
<a href="#top">[on top ]</a>

<hr>

<h2 id="review">review </h2>
<p> this is not that.......
.
.
.
.
.
.
</P>
<a href="#top">[on top]</a>

<hr>

<h2 id="message">warining </h2>
<p> please be careful that.....
.
.
.
.
.
</P>
```
---

