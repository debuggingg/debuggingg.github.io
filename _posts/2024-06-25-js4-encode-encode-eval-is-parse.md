---
layout: single
title: 2024/06/25 JavaScript-Encode,Eval,Is,Parse
---
# Encode-Decode

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Built-in Functions - Encoding and Decoding</h1>
	<hr>
	<!-- URL (URI) addresses can only be composed of letters, numbers, and certain special characters -->
	<!-- URI (Uniform Resource Identifier) = URL (Uniform Resource Locator) + QueryString (parameters) -->
	<!-- URI addresses can include QueryStrings to pass necessary values to the requested web program -->
	<!-- Problem: If a QueryString includes characters that are not allowed in a URI address,
	the values may be transmitted incorrectly, resulting in errors -->
	<!-- Solution: Encode characters that are not allowed in a URI address -->
	<!-- Encoding: Converting characters that are not allowed in a URI address into Unicode -->
	<p><code>encodeURI(string)</code> or <code>encodeURIComponent(string)</code>: Converts the given string into an encoded URI component.</p>
	<!-- Decoding: Converting an encoded URI component (Unicode) back into a regular string -->
	<p><code>decodeURI(string)</code> or <code>decodeURIComponent(string)</code>: Converts the given encoded URI component back into a regular string.</p>
	
	<script type="text/javascript">
	var name = "Paul";
	// alert("name = " + name);
	
	var encodeName = encodeURIComponent(name);
	// alert("encodeName = " + encodeName);
	
	var decodeName = decodeURIComponent(encodeName);
	alert("decodeName = " + decodeName);
	</script> 
</body>
</html>
```
---

## Eval Function

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Built-in Functions - eval Function</h1>
	<hr>
	<p><code>eval(string)</code>: Executes the JavaScript code represented by the string passed as a parameter.</p>
	
	<script type="text/javascript">
	/*
	var text = "20 + 10";
	// alert(text); // "20 + 10"
	// Use eval to execute the string as a JavaScript command
	alert(eval(text)); // 30
	*/
	
	/*
	var operation = prompt("Please enter an expression:");
	alert("Result = " + eval(operation));
	*/
	
	var display = "function() { alert('Function executed'); }";
	// alert("Type of display variable = " + typeof(display)); // string
	// display(); // Error - cannot call a string as a function
	
	// Convert the string into a function using eval, wrapping the string in parentheses
	// alert("Type of display variable = " + typeof(eval("(" + display + ")"))); // function
	// Use eval to convert the string to a function and call it
	eval("(" + display + ")")();
	</script>
</body>
</html>
```
---
## Is - IsFinite-IsNaN

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Built-in Functions</h1>
	<hr>

	<h2>eval Function</h2>
	<p><code>eval(string)</code>: Executes the JavaScript code represented by the string passed as a parameter.</p>
	
	<script type="text/javascript">
	/*
	var text = "20 + 10";
	// alert(text); // "20 + 10"
	// Use eval to execute the string as a JavaScript command
	alert(eval(text)); // 30
	*/
	
	/*
	var operation = prompt("Please enter an expression:");
	alert("Result = " + eval(operation));
	*/
	
	var display = "function() { alert('Function executed'); }";
	// alert("Type of display variable = " + typeof(display)); // string
	// display(); // Error - cannot call a string as a function
	
	// Convert the string into a function using eval, wrapping the string in parentheses
	// alert("Type of display variable = " + typeof(eval("(" + display + ")"))); // function
	// Use eval to convert the string to a function and call it
	eval("(" + display + ")")();
	</script>

	<hr>
	<h2>isNaN Function</h2>
	<p><code>isNaN(value)</code>: Returns <code>true</code> if the value is not a number, otherwise returns <code>false</code>.</p>
	
	<script type="text/javascript">
	//var value = "123";
	var value = "12a3";
	
	if(isNaN(value)) {
		alert("The value is not composed only of numbers.");
	} else {
		alert("The value is composed only of numbers.");
	}
	</script>
</body>
</html>
```

1. **Eval Function Section:**
   - Demonstrates how to use the `eval` function to execute a string as JavaScript code.
   - Includes examples of evaluating arithmetic expressions and converting a string to a function.

2. **isNaN Function Section:**
   - Demonstrates how to use the `isNaN` function to check if a value is a number.
   - Includes an example with a string that is not composed only of numbers to trigger the alert.
---
## Parse


```html
<title>JavaScript</title>
</head>
<body>
	<h1>Built-in Functions - Number Conversion Functions</h1>
	<hr>
	<p><code>parseInt(value)</code>: Converts the given value to an integer.</p>
	<p><code>parseFloat(value)</code>: Converts the given value to a floating-point number.</p>
	
	<script type="text/javascript">
	/*
	// Example: Converting a division result to an integer
	var result = 50 / 3;
	// alert("Result = " + result); // Result = 16.666666666666668
	alert("Result = " + parseInt(result)); // Result = 16
	*/
	
	/*
	// Example: Converting hexadecimal and octal strings to integers
	// parseInt(string[, radix]) - Converts the given string to an integer of the specified radix (base)
	// alert("Hexadecimal 123 = Decimal " + parseInt("123", 16)); // Hexadecimal 123 = Decimal 291
	// alert("Hexadecimal abc = Decimal " + parseInt("abc", 16)); // Hexadecimal abc = Decimal 2748
	alert("Octal 123 = Decimal " + parseInt("123", 8)); // Octal 123 = Decimal 83
	*/
	
	/*
	// Example: Converting a string to a number using parseInt and Number
	var num = "100";
	// alert(num + 200); // 100200 - String concatenation
	// alert(Number(num) + 200); // 300 - Number conversion and addition
	alert(parseInt(num) + 200); // 300 - parseInt conversion and addition
	*/
	
	/*
	// Example: Handling non-numeric characters in strings with Number and parseInt
	var num = "100abc";
	// Number returns NaN if the string contains non-numeric characters
	// alert(Number(num) + 200); // NaN
	// parseInt ignores non-numeric characters after the numeric part
	alert(parseInt(num) + 200); // 300
	*/
	
	// Example: Converting strings with decimal points
	var num = "10.5";
	// alert(num + 20); // 10.520 - String concatenation
	// alert(parseInt(num) + 20); // 30 - parseInt conversion (ignores decimal part)
	// alert(parseFloat(num) + 20); // 30.5 - parseFloat conversion
	alert(Number(num) + 20); // 30.5 - Number conversion
	</script>
</body>
</html>
```


