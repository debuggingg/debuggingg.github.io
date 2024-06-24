---
layout: single
title: 2024/06/21 CSS -05-Transform- Transition-Animation- de
---
## Transform 
#### Translate
- transform: Style property related to box model changes
	- Property values: transformation-related functions - translate, scale, rotate, skew, etc.
- translate(x, y): Function to move the box model in the horizontal (X) and vertical (Y) directions - unit: px
- translatex(x): Function to move the box model in the horizontal (X) direction - unit: px
- translatey(y): Function to move the box model in the vertical (Y) direction - unit: px
- translatez(z): Function to move the box model in the perspective (Z) direction - unit: px
- translate3d(x, y, z): Function to move the box model in 3D space - unit: px
- perspective: Style property for the Z-axis area
	- Property values: Set the depth of the Z-axis in px units
- transform-style: Style property related to the dimension of box model changes
- Property values: flat (2D - default), preserve-3d (3D)
#### Scale
- scale(x, y): Function to scale the box model in the horizontal (X) and vertical (Y) directions - unit: float value (scale factor)
- scalex(x): Function to scale the box model in the horizontal (X) direction - unit: float value (scale factor)
- scaley(y): Function to scale the box model in the vertical (Y) direction - unit: float value (scale factor)
- scalez(z): Function to scale the box model in the perspective (Z) direction - unit: float value (scale factor)
- scale3d(x, y, z): Function to scale the box model in 3D space - unit: float value (scale factor)
#### Rotate
- rotate(angle): Function to rotate the box model in the horizontal (X) and vertical (Y) directions - unit: deg
- rotatex(angle): Function to rotate the box model in the horizontal (X) direction - unit: deg
- rotatey(angle): Function to rotate the box model in the vertical (Y) direction - unit: deg
- rotatez(angle): Function to rotate the box model in the perspective (Z) direction - unit: deg
- rotate3d(x, y, z, angle): Function to rotate the box model in 3D space - unit: deg
#### Skew
- skew(angle, angle): Function to skew the box model in the horizontal (X) and vertical (Y) directions - unit: deg
- skewx(angle): Function to skew the box model in the horizontal (X) direction - unit: deg
- skewy(angle): Function to skew the box model in the vertical (Y) direction - unit: deg

### Full Code 
```html
<style type="text/css">
#original {
	margin: 20px;
}

img {
	border: 1px solid black;
}

.image {
	float: left;
	margin: 20px;
	background: rgba(0, 0, 255, 0.3);
	perspective: 200px;
	transform-style: preserve-3d;
}
#translate {transform: translate(20px, 20px);}
#translatex {transform: translatex(20px);}
#translatey {transform: translatey(-20px);}
#translatez {transform: translatez(-20px);}
#translate3d {transform: translate3d(-20px, -20px, -20px);}

#scale {transform: scale(1.2, 1.2);}
#scalex {transform: scalex(0.8);}
#scaley {transform: scaley(1.2);}
#scalez {transform: scalez(0.8);}
#scale3d {transform: scale3d(1.2, 1.2, 0.8);}

#rotate {transform: rotate(45deg);}
#rotatex {transform: rotatex(45deg);}
#rotatey {transform: rotatey(45deg);}
#rotatez {transform: rotatez(45deg);}
#rotate3d {transform: rotate3d(-1, 1, 2, 45deg);}

#skew {transform: skew(30deg, 30deg);}
#skewx {transform: skewx(30deg);}
#skewy {transform: skewy(30deg);}
</style>
</head>
<body>
	<h1>Box Model Transformation Related Style Properties</h1>
	<hr>
	<div id="original">
		<h3>Original Image</h3>
		<img alt="Pension Image" src="/web/css/images/1.jpg">
	</div>
	<hr>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="translate"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="translatex"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="translatey"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="translatez"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="translate3d"></div>
	<hr>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="scale"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="scalex"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="scaley"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="scalez"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="scale3d"></div>
	<hr>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="rotate"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="rotatex"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="rotatey"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="rotatez"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="rotate3d"></div>
	<hr>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="skew"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="skewx"></div>
	<div class="image">
		<img alt="Pension Image" src="/web/css/images/1.jpg" id="skewy"></div>
</body>
</html>
```

## Transition

- Transition Effect:
   - Transition effect: Allows the style of a box model to gradually change over time.

- transition-property : 
   - transition-property: Sets the style properties for which the transition effect is provided.

- transition-duration : 
   - transition-duration: Sets the duration of the transition effect - unit: s (seconds).

- transition-timing-function 
   - transition-timing-function: Sets the speed of the transition effect.
	   - Property : linear(default), ease-in, ease-out, ease-in-out, cubic-bezier
	   - Property values: linear (default), ease-in, ease-out, ease-in-out, cubic-bezier function, etc.
	     -  Refer to https://easings.net.

- transition-delay 
   - transition-delay: Sets the delay time for the transition effect - unit: s (seconds).

- opacity 
   - opacity: Style property related to the transparency of a box model.
     - Property values: 0.0 (transparent) ~ 1.0 (opaque).

- transition 
   - transition: Style property that can use all transition-related style property values.

### Full Code 

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Transition Effects</title>
<style type="text/css">
#shape div {
	width: 100px;
	height: 100px;
	background: red;
	border-radius: 0;
	transition-property: background, border-radius;
	transition-duration: 3s;
	transition-timing-function: ease-in-out;
	transition-delay: 0.5s; 
}

#shape div:hover {
	background: blue;
	border-radius: 50%;
}
#images img {
	margin: 20px;
	opacity: 0.5;
	transition: all 1s;
}

#images img:hover {
	transform: scale(1.2, 1.2);
	opacity: 1.0;
}

#shape div {
	width: 100px;
	height: 100px;
	background: url("/web/css/images/f1.png");
	position: relative;
	left: 0;
	transition: all 3s;
}

#shape:hover div {
	background: url("/web/css/images/f2.png");
	left: 600px;
}
</style>
</head>
<body>
	<h1>Transition Related Style Properties</h1>
	<hr>
	<p>Hover over the shape with your mouse cursor.</p>
	<div id="shape">
		<div></div>
	</div>
	<hr>
	<h1>Transition Related Style Properties</h1>
	<hr>
	<div id="images">
		<img alt="Pension Image 1" src="/web/css/images/1.jpg">
		<img alt="Pension Image 2" src="/web/css/images/2.jpg">
		<img alt="Pension Image 3" src="/web/css/images/3.jpg">
	</div>
	<hr>
	<div id="shape">
		<div></div>
	</div>
</body>
</html>
```
## Animation


- animation-name :
   - animation-name: Sets the name of the animation to be applied to the box model.
   
- animation-duration :
   - animation-duration: Sets the duration of the animation to be applied to the box model.
   
- animation-iteration-count : 
   - animation-iteration-count: Sets the number of times the animation will repeat on the box model.
     - Property values: Integer value or infinite (repeats indefinitely).

- animation-direction : 
   - animation-direction: Sets the direction in which the animation will progress on the box model.
       - Property values: normal (forward - default), reverse (backward), alternate (forward > backward), alternate-reverse (backward > forward).

- @keyframes : 
   - @keyframes: System property to declare the changes in style properties according to the animation name and state.
     - Provides animation effects by writing CSS style properties according to states (percentage or keywords - from, to).
     - Write two or more states, including the initial state (from) and the final state (to).

- Animation effect: A feature that automatically changes the style of the box model over time.

### Full Code

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Animation Effects</title>
<style type="text/css">
#ani {
	width: 100px;
	height: 100px;
	position: relative;
	animation-name: myani;
	animation-duration: 5s;
	animation-iteration-count: infinite;
	animation-direction: alternate;
}

@keyframes myani {
	0% {
		top: 0;
		left: 10px;
		background: url("/web/css/images/f1.png");
	}

	25% {
		top: 0;
		left: 600px;
		background: url("/web/css/images/f2.png");
	}

	50% {
		top: 300px;
		left: 600px;
		background: url("/web/css/images/f1.png");
	}

	75% {
		top: 300px;
		left: 10px;
		background: url("/web/css/images/f2.png");
	}

	100% {
		top: 0;
		left: 10px;
		background: url("/web/css/images/f1.png");
	}
}
</style>
</head>
<body>
	<h1>Animation Related Style Properties</h1>
	<hr>
	<div id="ani"></div>
</body>
</html>
```

## Design



- meta 
   - Set the `name` attribute value of the meta tag to `viewport`.
   - Due to different device widths or browser resolutions, it may provide abnormal output results.
   - Set the `content` attribute value to output the page to be displayed according to the device width - allowing for zoom in or out.
   - Attribute to set the tag content to be displayed according to the device resolution.
   - Limit the width of the page to be displayed for responsive web design - container.
   - Set the box model to a flexible structure for responsive web design.
     - Use % or em for size units instead of px.

- @media : 
   - @media: System property to apply different style sheets to tags according to device or resolution - Media Query.
     - Format) @media device [and condition] [and condition] ... { selector { property: value ..; } ...}
     - Devices: all, screen, print, tv, aural, braille, etc.

### Full Code 

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width">
<title>CSS</title>
<style type="text/css">
body {
	background: lime;
}

#container {
	width: 80%;
	margin: 0 auto;
}

#logo img {
	width: 100%;
	margin-top: 150px;
}

@media screen and (max-width: 425px) {
	body {
		background: yellow;	
	}
}

@media screen and (min-width: 426px) and (max-width: 768px) {
	body {
		background: orange;	
	}
}

@media screen and (min-width: 769px) and (max-width: 1024px) {
	body {
		background: olive;	
	}
}
</style>
</head>
<body>
	<div id="container">
		<h1>Responsive Web Design</h1>
		<hr>
		<p>A web design implementation method to apply different style sheets to tags according to device or resolution.</p>
		<hr>
		<div id="logo">
			<img alt="Logo" src="/web/css/images/logo.png">
		</div>
	</div>
</body>
</html>
```