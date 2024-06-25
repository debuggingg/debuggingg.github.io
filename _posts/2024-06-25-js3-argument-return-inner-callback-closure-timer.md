---
layout: single
title: 2024/06/25 JavaScript-Argument,Return,Inner,CallBack,Closure,Timer
---
## Argument

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Function Arguments</h1>
	<hr>
	<p>Argument: A variable used to store values passed during a function call for executing the function's commands - also known as a parameter.</p>
	
	<script type="text/javascript">
	/*
	// Parameters are declared without the var keyword and are treated as local variables.
	function sum(num1, num2) {
		alert("Sum = " + (num1 + num2));
	}
	
	// Call the function with the same number of values as parameters.
	//sum(10, 20); // Sum = 30
	
	// Call the function with fewer values than parameters.
	// Uninitialized parameters are treated as undefined, leading to unexpected results.
	//sum(10); // Sum = NaN
	
	// Call the function with more values than parameters.
	// Excess values are ignored.
	sum(10, 20, 30); // Sum = 30
	*/
	
	function total() {
		//alert("Executing total function");
		
		// Arguments object: A built-in object that stores all values passed during a function call as array-like elements.
		//alert(arguments); // [object Arguments]
		//alert(arguments.length); // Number of elements (arguments) passed
		
		var tot = 0;
		for (var i = 0; i < arguments.length; i++) {
			tot += arguments[i];
		}
		alert("Sum = " + tot);
	}
	
	// In JavaScript, functions can be called regardless of the number of arguments.
	//total();
	//total(10, 20, 30);
	total(10, 20, 30, 40, 50);
	</script>
</body>
</html>
```
---

## Return

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Return</h1>
	<hr>
	<p>return: A statement used to forcefully exit a function, providing the ability to return the result of the function's execution to the calling statement.</p>
	
	<script type="text/javascript">
	/*
	function display(name) {
		if(name == "Paul") {
			alert("Hello, Developer.");
			return; // Exit the function
		}
		alert(name + "welcome.");
	}
	
	//display("Paul");
	display("Sung");
	*/
	
	/*
	function sum(num1, num2) {
		return num1 + num2; // Exit the function and return the result
	}
	
	// Call the function and store the returned result in a variable
	var tot = sum(10, 20);
	alert("Sum = " + tot); // Sum = 30
	*/
	
	// Declare an arrow function and store it in a const variable
	// const sum = (num1, num2) => { return num1 + num2 };
	// If the arrow function has a single return statement, you can omit the {} and the return keyword
	const sum = (num1, num2) => num1 + num2;
	
	// Call the function and directly output the returned result
	alert("Sum = " + sum(10, 20)); // Sum = 30
	</script>
</body>
</html>
```
---
## Inner Function

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Inner Function</h1>
	<hr>
	<p>Inner Function: A function declared within another function, accessible only within the declaring function.</p>
	
	<script type="text/javascript">
	function outer() {
		alert("Executing outer function");
		
		// Declare inner function - it is automatically destroyed when the outer function completes
		function inner() {
			alert("Executing inner function");
		}
		
		// Inner function can only be called within the declaring outer function
		inner();
	}
	
	//inner(); // Error: inner is not defined
	outer();
	</script>
</body>
</html>
```

----
## CallBack

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Callback Function</h1>
	<hr>
	<p>Callback Function: A function passed as a parameter to another function, which is then executed within the outer function.</p>
	
	<script type="text/javascript">
	function display1() { // Declaration function
		alert("Executing display1 function");
	}

	// Function names are used as identifiers to distinguish functions but internally they are used to store functions as values
	//alert(display1); // Output function - displays the function code
	//display1();
	
	var display2 = function() { // Anonymous function - can be stored in a variable as a value
		alert("Executing display2 function");
	}
	
	//alert(display2); // Output variable value - displays the function code
	//display2(); // Call function stored in variable
	
	function display(callback) {
		//alert(callback);
		callback(); // Call the function stored in the parameter - callback function
	}
	
	//display(display1); // Call function and pass the function name - store declaration function in parameter
	//display(display2); // Call function and pass the variable value - store function stored in variable in parameter
	
	/*
	// Create an anonymous function and pass it as a parameter to be stored in the parameter when calling the function
	display(function() {
		alert("Executing callback function");
	});
	*/
	
	display(() => alert("Executing callback function"));
	</script> 
</body>
</html>
```
---

## Closure

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Closure Function</h1>
	<hr>
	<p>Closure Function: A function that returns an inner function using the return statement, allowing the inner function to be called from outside the outer function.</p>
	
	<script type="text/javascript">
	/*
	function outer() {
		function inner() {
			alert("Executing inner function");
		}
		
		//inner(); // Inner function can only be called within its declaring function
		
		return inner; // Return the inner function using its name
	}
	
	//inner(); // Error - inner function cannot be called from outside
	//outer();
	
	// Call the function to return the inner function and store it in a variable
	var closure = outer();
	closure(); // Call the function stored in the variable - closure function
	*/
	
	function display(name) {
		// Create and return an anonymous inner function
		return function() {
			alert("Hello, " + name + "!");
		}
	}
	
	/*
	var println = display("John Doe");
	println(); // Call the returned inner function
	*/
	
	// Call the returned inner function directly without storing it in a variable - can be called once
	display("John Doe")();
	display("Jane Doe")();
	</script>
</body>
</html>
```
---
## Built In Timer Function
```html
<title>JavaScript</title>
</head>
<body>
	<h1>Built-in Functions - Time-based Execution Functions</h1>
	<hr>
	<p>Built-in functions: Functions pre-declared and provided by the browser.</p>
	<hr>
	<p><code>setTimeout(callback, ms)</code>: Calls the callback function once after the specified time (ms) has elapsed. Returns a <code>timeoutId</code> (identifier for the setTimeout function).</p>
	<p><code>clearTimeout(timeoutId)</code>: Cancels the setTimeout function associated with the provided <code>timeoutId</code>.</p>
	<p><code>setInterval(callback, ms)</code>: Repeatedly calls the callback function at intervals specified by the time (ms). Returns an <code>intervalId</code> (identifier for the setInterval function).</p>
	<p><code>clearInterval(intervalId)</code>: Cancels the setInterval function associated with the provided <code>intervalId</code>.</p>
	
	<script type="text/javascript">
	// Create and pass an anonymous function as the first argument to the setInterval function
	// => The setInterval function will automatically call the anonymous function - Callback function
	/*
	var id = setInterval(function() {
		alert("Command repeatedly executed by setInterval function");
	}, 1000);
	*/
	var id = setInterval(() => alert("Command repeatedly executed by setInterval function"), 1000);	
	
	setTimeout(function() {
		// Commands to be executed after the specified time has elapsed
		clearInterval(id);
	}, 5000);
	</script> 
</body>
</html>
```