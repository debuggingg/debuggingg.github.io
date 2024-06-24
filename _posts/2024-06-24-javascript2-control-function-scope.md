---
layout: single
title: 2024/06/24 JavaScript -02-Control-Function-Scope(local,global variable)
---
## Control



```html

<title>JavaScript Control Statements</title>
</head>
<body>
	<h1>JavaScript Control Statements</h1>
	<hr>
	<!-- Control statements: commands (statements) used to alter the flow of program execution -->
	<p>Selection statements - if, switch</p>
	<p>Loop statements - for, while</p>
	<p>Others - break, continue, return</p>

	<script type="text/javascript">
	/*
	// Write a program that calculates and outputs the square root of a number entered in the input box
	
	// prompt(message[, value]): A function that creates an input box to receive a value and returns it as a string
	var num = prompt("Please enter a number.");
	// alert("num variable value = " + num); // num variable value = 123
	// alert("num variable type = " + typeof(num)); // num variable type = string
	
	// isNaN(value): A function that returns [false] if the passed value can be converted to a number,
	// and [true] if it cannot be converted to a number
	if (isNaN(num)) {
		alert("The entered value is not a number.");
	} else {
		alert(num * num);
	}
	*/

	// Write a program that calculates and outputs the total score, average, and grade for student scores
	// => Grade: 100 ~ 90 : A, 89 ~ 80 : B, 79 ~ 70 : C, 69 ~ 60 : D, 59 ~ 0 : F
	var kor = 98, eng = 80, mat = 91;
	
	var tot = kor + eng + mat;
	var avg = tot / 3;
	
	var grade = "";
	// parseInt(value): A function that converts the passed value to an integer and returns it
	switch (parseInt(avg / 10)) {
	case 10:
	case 9: grade = "A"; break;
	case 8: grade = "B"; break;
	case 7: grade = "C"; break;
	case 6: grade = "D"; break;
	default: grade = "F"; break;
	}
	
	alert("Total = " + tot + ", Average = " + avg + ", Grade = " + grade);
	*/

	/*
	// Write a program that calculates and outputs the sum of integers from 1 to 100
	var tot = 0;
	
	for (i = 1; i <= 100; i++) {
		tot += i;
	}
	
	alert("Sum of integers from 1 to 100 = " + tot);
	*/

	/*
	// Write a program that calculates and outputs the number of times an A4 sheet needs to be folded
	// in half to create more than 500 rectangular shapes
	var cnt = 0, gae = 1;
	
	while (true) {
		cnt++;
		gae *= 2;
		if (gae >= 500) break;
	}
	
	alert(cnt + " folds create " + gae + " rectangular shapes.");
	*/

	// Write a program that outputs only odd numbers from 1 to 10
	var result = "";

	for (i = 1; i <= 10; i++) {
		if (i % 2 == 0) continue;
		result += i + " "; // Concatenate string values to the variable
	}

	alert("Odd numbers from 1 to 10 = " + result);
	</script>
</body>
</html>
```


## Function

```html

<title>JavaScript Functions</title>
</head>
<body>
	<h1>JavaScript Functions</h1>
	<hr>
	<p>Function: A collection of commands that process values provided as parameters and return a result</p>
	<p>In JavaScript, functions are declared and then called to execute the commands within the function, 
	and the result is used.</p>

	<script type="text/javascript">
	/*
	// Declared function - A function declared with a name (identifier to distinguish the function)
	// Format: function functionName(parameter, parameter, ...) { commands; commands; ... }
	// => Internally, the function name is used like a variable to store the declared function
	function display() {
		alert("Executing commands in the declared function");
	}
	
	// alert(display); // Output the function name - outputs the declared function stored as the function name
	// alert(typeof(display)); // function
	
	// Declared functions can be called multiple times using the function name
	// Format: functionName(value, value, ...)
	display(); // Call the function - executes commands in the function
	display();
	*/

	/*
	// Anonymous function - A function declared without a name
	// => Anonymous functions are called immediately after being declared - can only be called once
	// Format: (function(parameter, parameter, ...) { commands; commands; ... })(value, value, ...)
	(function() {
		alert("Executing commands in the anonymous function");
	})();
	*/

	/*
	// Declare an anonymous function and store it in a variable
	// => In JavaScript, functions are treated as values, so they can be stored in variables
	var display = function() {
		alert("Executing commands in the anonymous function");
	}
	
	// alert(display); // Output the variable value - outputs the anonymous function stored in the variable
	// alert(typeof(display)); // function
	
	// Functions stored in variables can be called using the variable
	// => Variables storing functions provide similar functionality to function names - can call the function multiple times using the variable
	// Format: functionVariable(value, value, ...)
	display();
	display();
	*/

	/*
	// Declared functions can be stored in variables, but the function cannot be called using the function name; it can be called using the variable - the function name is automatically discarded when the function is stored in a variable
	var display = function println() {
		alert("Executing commands in the declared function");
	}
	
	// println(); // Error occurs
	display();
	*/

	/*
	display(); // Can be called before the function declaration
	// Declared functions are created before JavaScript commands are executed, so they can be called before the function declaration
	function display() {
		alert("Executing commands in the declared function");
	}
	
	display(); // Can be called after the function declaration
	*/

	/*
	display(); // Cannot be called before the function declaration - error occurs
	// Anonymous functions are created and stored in variables during command execution, so they cannot be called before the function declaration
	var display = function() {
		alert("Executing commands in the anonymous function");
	}
	display(); // Can be called after the function declaration
	*/

	// ES6 introduced arrow functions using lambda expression
	// => Makes code more concise and increases readability
	// Format: (parameter, parameter, ...) => { commands; commands; ... }
	// => Arrow functions can be created and called once, or stored in variables to be called multiple times using the variable
	// => Variables storing arrow functions should be created using the const keyword
	// => If the function has only one command, the { } brackets can be omitted
	// (() => { alert("Executing commands in the arrow function"); })();
	// (() => alert("Executing commands in the arrow function"); )(); // { } brackets can be omitted

	const display = () => alert("Executing commands in the arrow function");
	display();
	</script>	
</body>
</html>
```

## Scope

Here is the translated version of the HTML and JavaScript code with the comments translated into English:

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
</head>
<body>
	<h1>Global Variables and Local Variables</h1>
	<hr>
	<p>Global Variable: A variable declared outside of a function - can be used in all functions</p>
	<p>Local Variable: A variable declared inside a function - can only be used within the declared function</p>
	
	<script type="text/javascript">
	var globalVar = 100; // Global variable
	
	function display1() {
		// alert("Value of globalVar used in display1 function = " + globalVar);
		globalVar = 200; // Change the value stored in the global variable within the function
		
		// Local variables are automatically destroyed when the function ends, so they cannot be used outside the function
		var localVar = 300; // Local variable
		// alert("Value of localVar used in display1 function = " + localVar);
		
		// In JavaScript, variables can be used by storing values even if they are not declared
		// => Variables used without declaration within a function are automatically treated as global variables
		variable = 400;
		// alert("Value of variable used in display1 function = " + variable);
	}
	
	function display2() {
		// alert("Value of globalVar used in display2 function = " + globalVar);
		// alert("Value of localVar used in display2 function = " + localVar); // Error occurs
		alert("Value of variable used in display2 function = " + variable);
	}
	
	display1();
	display2();
	</script>
</body>
</html>
```