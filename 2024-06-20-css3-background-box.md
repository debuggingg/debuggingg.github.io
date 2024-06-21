---
layout: single
title: 2024/06/20 CSS -03-Background-Box
---
## BackGround

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
