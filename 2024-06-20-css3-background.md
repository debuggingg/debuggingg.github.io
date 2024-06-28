---
layout: single
title: 2024/06/20 CSS -03-Background-Color-Image-Position
---
## BackGround-color-image-position

-  background-color: Style property for the background color of the tag - uses color-related property values 
-  background-color: Style property for the background color of the tag - uses color-related property values 
-  margin: Centering the element horizontally 
-  width: Setting the width of the element 
-  height: Setting the height of the element 
-  border: Setting the border color and style 
-  font-size: Setting the font size of the text inside the element 
- color: Setting the text color */
- background-color: Setting the background color of the element */
	- background-color(nth-child(2n-1): Setting the background color for odd-numbered rows */
	- background-color (nth-child(2n): Setting the background color for even-numbered rows */
- background-image: Style property for the background image of the tag */
	-  Property value: Use the url function to set the URL of the image file 
	- If the background image is smaller than the width or height of the tag, the background image is repeated horizontally (X) or vertically (Y) */
	- If the background image is larger than the width or height of the tag, the image is cut off 
	- The background image is displayed based on the top-left corner of the tag 
- background-repeat: Style property for background image repetition */
	- Property values: repeat (default), repeat-x, repeat-y, no-repeat */
    
        
- background-position: Style property for the position of the background image */
    - Property values: px, %, keywords (left, right, top, bottom, center) - default value: left top. The background image is moved horizontally (X) and vertically (Y) based on the top-left corner of the tag 
	- background-position: Setting the position of the background image to the center 



```html
<title>Background-related Style Properties</title>
<style>
    body {    background-color: silver;}

div {    background-color: yellow;
    margin: 0 auto;
    width: 50%;
    height: 150px;
    border: 1px solid rgb(126, 252, 249);
    font-size: 30px;}

table {
    margin: 0 auto;
    width: 50%;}
th {    color: #fff;
    background-color: #0c5775;}
tr:nth-child(2n-1) {   
    background-color: lime;}
tr:nth-child(2n) {
    background-color: aqua;}
body {
    background-image: url("/web/css/images/bg-img1.png");
    background-repeat: no-repeat;}
div {    margin: 0 auto;
    width: 80%;
    height: 400px;
    border: 1px solid red;
    background-image: url("/web/css/images/bg-img2.png");}
div {  
    margin: 0 auto;
    width: 50%;
    height: 300px;
    border: 1px solid red;	
    background-image: url("/web/css/images/bg.png");
    background-repeat: no-repeat;
    /* background-position: 200px 100px; */
    /* background-position: 30% 60%; */
    /* background-position: center; */
    background-position: right bottom;}
</style>
</head>
<body>

<h1>Background-related Style Properties</h1>
<hr>
<div>Set background styles here.</div>
<hr>
<table>
    <tr>
        <th>Number</th><th>Name</th><th>Address</th>
    </tr>
    <tr>
        <td>1000</td><td>Hong Gil-dong</td><td>Seoul, Gangnam-gu</td>
    </tr>
    <tr>
        <td>2000</td><td>Im Kkeok-jeong</td><td>Incheon, Wolmido-gu</td>
    </tr>
    <tr>
        <td>3000</td><td>Jeon Woo-chi</td><td>Suwon, Paldal-gu</td>
    </tr>
    <tr>
        <td>4000</td><td>Iljimae</td><td>Bucheon, Wonmi-gu</td>
    </tr>
    <tr>
        <td>5000</td><td>Jang Gil-san</td><td>Busan, Saha-gu</td>
    </tr>
</table>
<h1>Background-related Style Properties</h1>
<hr>
<div class="today">
    Today is June 20, 2024. It's a very hot day.
</div>
</body>
</html>
```
---
## Background - Attachment

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSS</title>
<style type="text/css">
body {
	background-image: url("/web/css/images/bg.png");
	background-repeat: no-repeat;
	background-position: right bottom;
	background-attachment: fixed; /* Fixed background attachment */

	/* Additional background shorthand */
	/* background: url("/web/css/images/bg.png") no-repeat right bottom fixed; */
}
</style>
</head>
<body>
	<h1>Background-related Style Properties</h1>
	<hr>
	<p>This is very important content that will be displayed in the browser.</p>	
	<p>This is very important content that will be displayed in the browser.</p>	
.
.
.

	<p>This is very important content that will be displayed in the browser.</p>	
	<p>This is very important content that will be displayed in the browser.</p>	
</body>
</html>
```

## Background - Size

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSS</title>
<style type="text/css">
div {
	width: 500px;
	height: 350px;
	border: 1px solid red;
	margin: 20px;
	background: url("/web/css/images/station.jpg") no-repeat;
}

/* Background size properties */
.bgsize1 { background-size: auto; }
.bgsize2 { background-size: 300px; }
.bgsize3 { background-size: 500px 350px; }
.bgsize4 { background-size: 90%; }
.bgsize5 { background-size: 100% 100%; }
.bgsize6 { background-size: contain; }
.bgsize7 { background-size: cover; }
</style>
</head>
<body>
<h1>Background-related Style Properties</h1>
<hr>
<div class="bgsize1"></div>
<div class="bgsize2"></div>
<div class="bgsize3"></div>
<div class="bgsize4"></div>
<div class="bgsize5"></div>
<div class="bgsize6"></div>
<div class="bgsize7"></div>
</body>
</html>
```

## Grandient

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSS</title>
<style type="text/css">
div {
	width: 500px;
	height: 300px;
	border: 1px solid black;
	margin: 20px;
}

.grade1 {
	/* Using linear-gradient function in the background style property */
	/* linear-gradient function provides linear gradient effect to the tag */
	/* Format) linear-gradient(direction, start-color, end-color) */
	/* => direction: angle(deg), keyword (to left, to right, to bottom, to top) */
	background: linear-gradient(45deg, red, white);
	background: linear-gradient(135deg, blue, white);
	background: linear-gradient(to bottom, green, white);
	/* Middle color can be specified after the start color using percentage */
	background: linear-gradient(255deg, olive, white 30%, olive);
	/* If percentage is omitted in the middle color, it defaults to 50% */
	background: linear-gradient(to right, yellow, orange, red);
}

.grade2 {
	/* Using radial-gradient function in the background style property */
	/* radial-gradient function provides radial gradient effect to the tag */
	/* Format) radial-gradient(shape, start-color, end-color) */
	/* => shape: circle, ellipse */
	/* => Provides radial gradient effect based on the center of the tag */
	/* => Can change the center position for providing radial gradient effect - works only in specific browsers */
	background: radial-gradient(circle, white, blue);
	/* Specific style property values only available in certain browsers, not defined in CSS standard */
	/* -webkit- : WebKit-based browsers - Chrome, Safari, Edge, etc. */
	/* -moz- : Gecko-based browsers - Firefox, Mozilla, etc. */
	/* -o- : Opera-based browsers - Opera, etc. */
	/* -ms- : Trident-based browsers - IE, etc. */
	background: -webkit-radial-gradient(70% 70%, circle, white, blue);
}
</style>
</head>
<body>
<h1>Background-related Style Properties</h1>
<hr>
<div class="grade1"></div>
<div class="grade2"></div>
</body>
</html>
```