---
layout: single
title: 2024/06/24 JavaScript -01 -Variable-Operator-TypeCast
---
# JavaScript

- HTML 
   - The sequence of interpreting and executing an HTML document in a web browser.
     - Read the tags of the HTML document and store them in memory (DOM Parser) - DOM Tree >> Apply styles written in CSS to the tags of the DOM Tree >> Execute JavaScript commands >> Sequentially render the DOM Tree tags in the browser.
-  **script
   - By using the `src` attribute in the `script` tag, you can include a JavaScript file in the HTML document.
     - Used to provide the same functions (functions) to all web documents that make up the website.

- script tag : 
   - The `script` tag is used to write programs using a scripting language.
    - You can write multiple `script` tags in an HTML document, and they are executed sequentially.   
     - `type` attribute: Set the file type (MimeType) of the scripting language as the attribute value.

   - JavaScript comments(// or /*  * /) are the same as in Java. 
   - JavaScript uses an interpreter method to execute commands one by one, so use the `;` symbol at the end of a command.
     - JavaScript recognizes and processes code written in one line as a single command, so the `;` symbol can be omitted.
   - JavaScript is an object-oriented programming language that uses objects to write programs.
   - Property and Function
     - Write and execute commands using the properties and functions of objects.

- console :
   - The `console` object is provided to use the browser's console.
     - `console.log(message)`: A function that prints the message received as a parameter to the browser's console.
   - The `window` object is provided to use the browser.
     - `window.alert(message)`: A function that creates a message window (alert) and displays the message received as a parameter.
     - The function can be called without the `window` object.

   - JavaScript function declaration.
    - Using the `src` attribute in the `script` tag to include and use a JavaScript file in an HTML document.
    - The `onXXX` attribute of a tag is an event listener attribute that sets event handling commands as attribute values.
      - Write event handling commands in JavaScript.
      - `onclick` event listener attribute: The event listener attribute to be used when a click event occurs on the tag.
- eventHandler
    - It is recommended to write commands that call JavaScript functions (event handler functions) as event listener attribute values of tags.

## Full Code 

```html
<title>JavaScript</title>
<script type="text/javascript" src="/web/js/01_hello.js"></script>
<script type="text/javascript">
console.log("Hello, JavaScript!!!");
alert("head tag for javascript prompt");

function eventHandler() {
	alert('evnt action');
}
</script>
</head>
<body>
	<h1>write javascript</h1>
	<hr>
	<p>(JavaScript): A programming language for event processing on web pages - it runs in the client browser. </p>
	<p>ES15 and ES6 refer to the same standard, officially known as ECMAScript 2015. To avoid confusion, it's better to consistently refer to it as ECMAScript 2015 or ES6. </p>
	<hr>
	<button type="button" onclick="eventHandler();">press button</button>
	<script type="text/javascript">
		alert("body tag for javascript prompt");
	</script>
</body>
</html>
```

## Variable\


   - Variable declaration - If the variable is not initialized with a value, it is treated as having the [undefined] data type.
     - **=> undefined**: Data type of a variable that has no value stored.

   - Variable declaration and initialization.
     - Store a number value in the variable - data type of the variable: number.
     - Concatenate string values using the `+` operator.
     - Re-declaring a variable with the same name does not cause an error - the existing variable is used.

   - In JavaScript, string values are represented using ' ' or " " symbols.
     - Store a string value in the variable - data type of the variable: string.

   - `typeof(variableName)`: An operator that provides the data type of a variable.

   - Store a boolean value (false or true) in the variable.
     - Data type of the variable = boolean.


   - Store a function in a JavaScript variable - JavaScript internally treats functions as a single value.
     - Execute the commands written in the function.

   - Create an object and store it in a variable.
   - **[object Object]**
     - The default string representation of an object in JavaScript.
     - Data type of the variable = object.

   - Even if a variable is not declared with the `var` keyword, it can be used if a value is assigned to it.

### Full Code 


```html
<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript Variables and Data Types</h1>
	<hr>
	<p>Variable: A named storage location in memory for storing a literal (value).</p>
	<p>In JavaScript, variables can be used without being declared, but it is recommended to declare them.</p>
	<p>Format: var variableName = initialValue</p>
	<p>JavaScript variables automatically change their data type based on the stored value.</p>
	
	<script type="text/javascript">
	/* Variable declaration - If the variable is not initialized with a value, it is treated as having the [undefined] data type
	 var num;
	 alert(num);
	
	 Variable declaration and initialization
	 var num = 10; // Store a number value in the variable - data type of the variable: number
	 alert("num = " + num); // Concatenate string values using the + operator

	 Re-declaring a variable with the same name does not cause an error - the existing variable is used
	 var num = 20;
	 alert("num = " + num);

	 In JavaScript, string values are represented using ' ' or " " symbols
	 var num = '10'; // Store a string value in the variable - data type of the variable: string
	 alert("num = " + num);

	 typeof(variableName): An operator that provides the data type of a variable
	 var num = 10;
	 alert("num variable's data type = " + typeof(num)); // num variable's data type = number

	 num = "10";
	 alert("num variable's data type = " + typeof(num)); // num variable's data type = string

	 Store a boolean value (false or true) in the variable
	 var result = true;
	 alert("result = " + result);
	 alert("result variable's data type = " + typeof(result)); // result variable's data type = boolean

	 Store a function in a JavaScript variable - JavaScript internally treats functions as a single value
	 var fun = function() {
		alert("Execute commands written in the function");
	 }
	 alert("fun = " + fun);
	 alert("fun variable's data type = " + typeof(fun)); // fun variable's data type = function

	 Create an object and store it in a variable
	 var obj = new Object();
	 alert("obj = " + obj); // [object Object]
	 alert("obj variable's data type = " + typeof(obj)); // obj variable's data type = object

	 Even if a variable is not declared with the var keyword, it can be used if a value is assigned to it*/
	num = 10;
	alert("num = " + num);
	</script>
</body>
</html>
```

## Variable 2 

   - When declaring a variable with the `var` keyword, it is possible to declare a variable with the same name and change its initial value.


   - When declaring a variable with the `let` keyword, it is not possible to declare a variable with the same name, but it is possible to change its initial value.

   - When declaring a variable with the `const` keyword, it is not possible to declare a variable with the same name, and it is not possible to change its initial value.
     - Constant variable: the initial value stored in the variable cannot be changed - constant.
   - Variables declared with `var` inside a control statement can be used outside the control statement, but variables declared with `let` and `const` cannot be used outside the control statement.
     - Variables declared with `let` and `const` automatically disappear when the control statement ends.

   - All variables declared inside a function automatically disappear when the function ends.


   - In ES6, string values can be represented using backticks (` `).
     - In string values using backticks, variable values can be provided in the format `${variableName}` and used as string values.

### Full Code 

```html
<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript Variables and Data Types</h1>
	<hr>
	<p>In ES6 (ECMAScript6), variables can be declared using the let keyword or the const keyword.</p>

	<script type="text/javascript">
	// When declaring a variable with the var keyword, it is possible to declare a variable with the same name and change its initial value
	// var num = 100;
	// alert("num = " + num)
	
	// var num = 200; // The existing variable is used
	// alert("num = " + num)

	// When declaring a variable with the let keyword, it is not possible to declare a variable with the same name, but it is possible to change its initial value
	// let num = 100;
	// alert("num = " + num)
	// let num = 200; // Error occurs
	// num = 200;
	// alert("num = " + num)

	// When declaring a variable with the const keyword, it is not possible to declare a variable with the same name, and it is not possible to change its initial value
	// const num = 100; // Constant variable: the initial value stored in the variable cannot be changed - constant
	// alert("num = " + num)
	
	// const num = 100; // Error occurs
	// num = 200; // Error occurs

	// Variables declared with var inside a control statement can be used outside the control statement, but variables declared with let and const cannot be used outside the control statement
	// if (true) {
	//	var num1 = 100; // Automatically disappears when the program ends
	//	let num2 = 200; // Automatically disappears when the control statement ends
	//	const num3 = 300; // Automatically disappears when the control statement ends
		
	//	alert("num1 = " + num1 + ", num2 = " + num2 + ", num3 = " + num3);
	// }
	// Variables declared with let and const automatically disappear when the control statement ends, so an error occurs when using the variables
	// alert("num1 = " + num1 + ", num2 = " + num2 + ", num3 = " + num3);

	// All variables declared inside a function automatically disappear when the function ends
	// function display() {
	//	var num1 = 100;
	//	let num2 = 200;
	//	const num3 = 300;
		
	//	alert("num1 = " + num1 + ", num2 = " + num2 + ", num3 = " + num3);
	// }
	
	// display(); // Function call - Executes the commands written inside the function
	// alert("num1 = " + num1 + ", num2 = " + num2 + ", num3 = " + num3); // Error occurs

	let num = 100;
	// alert("num = " + num);
	// In ES6, string values can be represented using backticks (` `)
	// alert(`num = ` + num);

	// In string values using backticks, variable values can be provided in the format ${variableName} and used as string values
	alert(`num = ${num}`);
	</script>
</body>
</html>
```

## Operator\


- **var num = 123;**
   - Declare a variable `num` and assign it the value 123.

-  **alert("num variable value = " + num); // num variable value = 123**
   - Alert the value of the `num` variable; expected output is "num variable value = 123".

-  **alert("num variable type = " + typeof(num)); // num variable type = number**
   - Alert the data type of the `num` variable; expected output is "num variable type = number".

-  **num = "123";**
   - Assign the string "123" to the `num` variable.

-  **alert("num variable value = " + num); // num variable value = 123**
   - Alert the value of the `num` variable after assigning a string; expected output is "num variable value = 123".

-  **alert("num variable type = " + typeof(num)); // num variable type = string**
   - Alert the data type of the `num` variable after assigning a string; expected output is "num variable type = string".

-  **== operator or != operator compares only values regardless of variable type and provides a boolean result**
   - The `==` or `!=` operators compare values regardless of their data type and provide a boolean result.

-  **alert("Comparison result = " + (num == 123)); // Comparison result = true**
   - Alert the result of comparing `num` to 123 using `==`; expected output is "Comparison result = true".

-  **alert("Comparison result = " + (num == "123")); // Comparison result = true**
   - Alert the result of comparing `num` to "123" using `==`; expected output is "Comparison result = true".

-  **=== operator or !== operator compares both the variable type and value and provides a boolean result**
    - The `===` or `!==` operators compare both the value and the data type and provide a boolean result.

-  **alert("Comparison result = " + (num === 123)); // Comparison result = false**
    - Alert the result of comparing `num` to 123 using `===`; expected output is "Comparison result = false".

-  **alert("Comparison result = " + (num === "123")); // Comparison result = true**
    - Alert the result of comparing `num` to "123" using `===`; expected output is "Comparison result = true".

### Full Code 

```html
<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript Operators</h1>
	<hr>
	<p>Highest priority operators: (), [], {}, .</p>
	<p>Unary operators: +, -, ++, --, !, new, typeof</p>
	<p>Arithmetic operators: *, /, %, +, -</p>
	<p>Relational (comparison) operators: &gt;, &gt;=, &lt;, &lt;=, ==, !=, ===, !==</p>
	<p>Logical operators: &amp;&amp;, ||</p>
	<p>Conditional (ternary) operator: condition ? true : false</p>
	<p>Assignment operators: =, *=, /=, %=, +=, -=</p>

	<script type="text/javascript">
	var num = 123;
	// alert("num variable value = " + num); // num variable value = 123
	// alert("num variable type = " + typeof(num)); // num variable type = number

	num = "123";
	// alert("num variable value = " + num); // num variable value = 123
	// alert("num variable type = " + typeof(num)); // num variable type = string

	// == operator or != operator compares only values regardless of variable type and provides a boolean result
	alert("Comparison result = " + (num == 123)); // Comparison result = true
	alert("Comparison result = " + (num == "123")); // Comparison result = true

	// === operator or !== operator compares both the variable type and value and provides a boolean result
	alert("Comparison result = " + (num === 123)); // Comparison result = false
	alert("Comparison result = " + (num === "123")); // Comparison result = true

	</script> 
</body>
</html>
```

## TypeCast

Here is the translated version of the HTML and JavaScript code with the comments translated into English:

### Translated Comments

- **alert("Operation result = " + (98/10)); // Operation result = 9.8**
   - Alert the result of dividing 98 by 10; expected output is "Operation result = 9.8".

-  **+ operator performs arithmetic operations if both operands are of numeric type**
   - The `+` operator performs arithmetic addition if both operands are numbers.

- **alert(20+10); // 30**
   - Alert the result of adding 20 and 10; expected output is "30".

-  **+ operator concatenates values if at least one operand is a string**
   - The `+` operator concatenates values if at least one operand is a string.

-  **alert("20" + 10); // 2010**
   - Alert the result of concatenating the string "20" with the number 10; expected output is "2010".

-  **alert(20 + "10"); // 2010**
   - Alert the result of concatenating the number 20 with the string "10"; expected output is "2010".

-  **alert("20" + "10"); // 2010**
   - Alert the result of concatenating the strings "20" and "10"; expected output is "2010".

8. **Other arithmetic operators convert all operands to numbers before performing operations**
   - Arithmetic operators (other than `+`) convert all operands to numbers before performing operations.

-  **alert(20 - 10); // 10**
   - Alert the result of subtracting 10 from 20; expected output is "10".

-  **alert("20" - 10); // 10**
    - Alert the result of subtracting 10 from the string "20" (which is converted to the number 20); expected output is "10".

-  **alert(20 - "10"); // 10**
    - Alert the result of subtracting the string "10" (which is converted to the number 10) from 20; expected output is "10".

-  **alert("20" - "10"); // 10**
    - Alert the result of subtracting the string "10" (which is converted to the number 10) from the string "20" (which is converted to the number 20); expected output is "10".

-  **NaN (Not a Number): provided when a value cannot be converted to a number**
    - `NaN` is returned when an operand cannot be converted to a number.

-  **alert(20 - "abc"); // NaN**
    - Alert the result of subtracting the string "abc" (which cannot be converted to a number) from 20; expected output is "NaN".

-  **Boolean values are converted to numbers for arithmetic operations: false becomes 0 and true becomes 1**
    - Boolean values are converted to numbers for arithmetic operations: `false` becomes 0 and `true` becomes 1.

- **alert(20 - true); // 19**
    - Alert the result of subtracting `true` (which is converted to 1) from 20; expected output is "19".

- **Using ! operator on a number converts 0 to false and any non-zero number to true**
    - Using the `!` operator on a number converts 0 to `false` and any non-zero number to `true`.

- **alert(!20 - true); // -1**
    - Alert the result of converting 20 to `true` (which becomes `false` with `!`), subtracting `true` (1) from `false` (0); expected output is "-1".

- **alert("20" + 10); // 2010**
    - Alert the result of concatenating the string "20" with the number 10; expected output is "2010".

- **Number(value): converts the passed value to a number**
    - `Number(value)` converts the passed value to a number.

- **String(value): converts the passed value to a string**
    - `String(value)` converts the passed value to a string.

- **Boolean(value): converts the passed value to a boolean**
    - `Boolean(value)` converts the passed value to a boolean.

- **alert(Number("20") + 10); // 30**
    - Alert the result of converting the string "20" to a number and adding 10; expected output is "30".

## Full Code 

```html
<title>JavaScript Type Conversion</title>
</head>
<body>
	<h1>JavaScript Type Conversion</h1>
	<hr>
	<p>Automatic Type Conversion - Values are automatically converted by operators</p>
	<p>Explicit Type Conversion - Values are explicitly converted using type conversion functions</p>

	<script type="text/javascript">
	// alert("Operation result = " + (98/10)); // Operation result = 9.8

	// + operator performs arithmetic operations if both operands are of numeric type
	// alert(20 + 10); // 30

	// + operator concatenates values if at least one operand is a string
	// alert("20" + 10); // 2010
	// alert(20 + "10"); // 2010
	// alert("20" + "10"); // 2010

	// Other arithmetic operators convert all operands to numbers before performing operations
	// alert(20 - 10); // 10
	// alert("20" - 10); // 10
	// alert(20 - "10"); // 10
	// alert("20" - "10"); // 10

	// NaN (Not a Number): provided when a value cannot be converted to a number
	// alert(20 - "abc"); // NaN

	// Boolean values are converted to numbers for arithmetic operations: false becomes 0 and true becomes 1
	// alert(20 - true); // 19

	// Using ! operator on a number converts 0 to false and any non-zero number to true
	// alert(!20 - true); // -1

	// alert("20" + 10); // 2010
	// Number(value): converts the passed value to a number
	// String(value): converts the passed value to a string
	// Boolean(value): converts the passed value to a boolean
	alert(Number("20") + 10); // 30
	</script>
</body>
</html>
```