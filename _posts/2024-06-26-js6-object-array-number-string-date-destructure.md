---
layout: single
title: 2024/06/26 JavaScript 06- Object,Array,Number,String,Date,Math,Json,and Destructure
---
# Constructor Function

## Object 

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
</head>
<body>
	<h1>Object Constructor Function</h1>
	<hr>
	<p>The browser has predefined constructor functions to create objects.</p>
	<hr>
	<p>Object Constructor Function: A constructor function to create Object instances. All JavaScript objects inherit from the Object prototype.</p>
	<p>Object: An object with no properties initially, but contains the `toString` method.</p>
	<p>Object.toString(): A method that returns the name of the constructor function that created the object. It is automatically called when an object variable is printed.</p>
	<p>You can add properties and methods to the Object instance as needed.</p>
	 
	<script type="text/javascript">
	// Creating an Object instance using the Object constructor
	var student = new Object();
	
	// Adding properties to the Object instance
	student.num = 1000;
	student.name = "Paul";
	student.address = "Seoul GangNam";
	
	// Adding a method to the Object instance
	student.display = function() {
		alert("학번 = " + this.num + ", 이름 = " + this.name + ", 주소 = " + this.address);
	};	
	
	// Calling the method to display student details
	student.display();

	// Deleting a property from the Object instance
	delete student.address;

	// Trying to access the deleted property (should return undefined)
	alert("학번 = " + student.num + ", 이름 = " + student.name + ", 주소 = " + student.address);

	// Using JSON notation to create an Object instance with properties and methods
	var studentJSON = {
		"num": 1000,
		"name": "Paul",
		"address": "Seoul GangNam",
		display() {
			alert("학번 = " + this.num + ", 이름 = " + this.name + ", 주소 = " + this.address);
		}
	};
	
	// Calling the method to display student details
	studentJSON.display();
	</script> 
</body>
</html>
```


1. **Object Constructor**:
   - An instance of `Object` is created using the `new Object()` constructor.
   - Properties (`num`, `name`, and `address`) and a method (`display`) are added to this instance.
   - The `display` method is defined using a function expression and is called to show the student details.
   - The `address` property is deleted using the `delete` operator, and attempting to access it returns `undefined`.

2. **JSON Notation**:
   - An object is created using JSON notation, with properties and a method defined directly within the object literal.
   - The `display` method is defined using the shorthand method syntax, which omits the `function` keyword.
   - The method is called to display the student details.
---
## Array


```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaScript</title>
</head>
<body>
	<h1>Array Constructor Function</h1>
	<hr>
	<p>The Array constructor function is used to create array objects.</p>
	<p>Array objects store multiple values in elements, similar to arrays in other programming languages. Elements are accessed using indices and the [] operator.</p>
	
	<script type="text/javascript">
	// Creating an empty array
	var array = new Array();
	
	// Display the length of the empty array
	alert(array.length); // 0
	
	// Display the array (empty)
	alert(array); // ""

	// Creating an array with a specified size (all elements are initially undefined)
	var arrayWithSize = new Array(5);
	alert(arrayWithSize.length); // 5
	alert(arrayWithSize); // ",,,,"

	// Creating an array with multiple initial values
	var arrayWithValues = new Array(10, 20, 30);
	alert(arrayWithValues.length); // 3
	alert(arrayWithValues); // "10,20,30"
	
	// Accessing elements using indices
	alert("arrayWithValues[0] = " + arrayWithValues[0] + ", arrayWithValues[1] = " + arrayWithValues[1] + ", arrayWithValues[2] = " + arrayWithValues[2]);

	// Accessing an out-of-bound index (returns undefined)
	alert("arrayWithValues[3] = " + arrayWithValues[3]); // undefined

	// Creating an array using JSON notation
	var arrayJSON = [10, 20, 30];
	
	// Iterating over the array using a for loop
	for (var i = 0; i < arrayJSON.length; i++) {
		alert("arrayJSON[" + i + "] = " + arrayJSON[i]);
	}

	// Iterating over the array using a for-in loop (not recommended for arrays)
	for (var index in arrayJSON) {
		alert("arrayJSON[" + index + "] = " + arrayJSON[index]);
	}

	// Iterating over the array using a for-of loop 
	for (var value of arrayJSON) {
		alert(value);
	}

	// Using the forEach method to iterate over the array
	arrayJSON.forEach(function(element, index) {
		alert("arrayJSON[" + index + "] = " + element);
	});
	
	// Adding an element to the end of the array using push
	arrayJSON.push(40);
	alert(arrayJSON); // "10,20,30,40"
	
	// Removing the last element of the array using pop
	var poppedValue = arrayJSON.pop();
	alert(arrayJSON); // "10,20,30"
	alert(poppedValue); // 40
	
	// Adding an element to the beginning of the array using unshift
	arrayJSON.unshift(40);
	alert(arrayJSON); // "40,10,20,30"
	
	// Removing the first element of the array using shift
	var shiftedValue = arrayJSON.shift();
	alert(arrayJSON); // "10,20,30"
	alert(shiftedValue); // 40
	</script>
</body>
</html>
```


-  **Creating Arrays**:
   - An empty array is created using `new Array()`.
   - An array with a specific size is created using `new Array(5)`, where all elements are initially `undefined`.
   - An array with initial values is created using `new Array(10, 20, 30)`.

-  **Accessing and Manipulating Elements**:
   - Elements are accessed using indices (e.g., `arrayWithValues[0]`).
   - An out-of-bounds index returns `undefined`.
   - Arrays can also be created using JSON notation for easier initialization.

-  **Iterating Over Arrays**:
   - Various loops are demonstrated to iterate over array elements:
     - A `for` loop using indices.
     - A `for-in` loop, which iterates over indices (not recommended for arrays but shown for completeness).
     - A `for-of` loop, which iterates over values.
     - The `forEach` method, which calls a function for each element in the array.

-  **Array Methods**:
   - `push`: Adds an element to the end of the array.
   - `pop`: Removes and returns the last element of the array.
   - `unshift`: Adds an element to the beginning of the array.
   - `shift`: Removes and returns the first element of the array.
---
## Number

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Number Constructor Function</h1>
	<hr>
	<p>Number Constructor Function: A constructor function used to create Number objects</p>
	<p>Number Object: An object used to store numeric values</p>
	
	<script type="text/javascript">
	/*
	var num=100;
	alert("Data type of num variable = "+typeof(num));//Data type of num variable = number
	*/

	/*
	//Create a Number object with the numeric value passed as a parameter and store it in a variable
	//var num=new Number(100);
	//Even if a string value is passed as a parameter, it is converted to a numeric value and stored in the Number object
	var num=new Number("100");
	//alert("Data type of num variable = "+typeof(num));//Data type of num variable = object
	
	//Number.toString(): A member function that converts the numeric value stored in a Number object to a string and returns it
	//alert("num.toString() = "+num.toString());	
	//alert("num = "+num);//When a variable storing a Number object is output, the toString() member function is automatically called	

	//Number.toString(radix): A member function that converts the numeric value stored in a Number object to a string in the specified radix (base) and returns it
	//alert(num+"(Decimal) = "+num.toString(16)+"(Hexadecimal)");	
	//alert(num+"(Decimal) = "+num.toString(8)+"(Octal)");	
	//alert(num+"(Decimal) = "+num.toString(2)+"(Binary)");	

	//Number.valueOf(): A member function that returns the numeric value stored in a Number object
	var su=num.valueOf();
	//alert("su = "+su);
	
	//In JavaScript, all values are internally represented and processed as objects
	// => Numeric values are internally processed as Number objects, allowing the use of member variables and member functions
	alert("su.toString(2) = "+su.toString(2));
	*/
	
	/*
	var num=new Number(100);
	//alert("Data type of num variable = "+typeof(num));//Data type of num variable = object
	//alert("num = "+num);
	
	//In JavaScript, it is possible to add, change, or delete elements (member variables or member functions) of an object
	num.display=function() {//Add a member function to the Number object
		alert("Display member function of the Number object called");
	}
	
	num.display();
	*/
	
	/*
	var num=100;
	//alert("Data type of num variable = "+typeof(num));//Data type of num variable = number
	//alert("num = "+num);
	
	//It is not possible to add member variables or member functions to a numeric value stored in a numeric variable
	num.display=function() {
		alert("Display member function of the Number object called");
	}
	
	num.display();//Unable to call member function - error occurs 
	*/
	
	/*
	var num=12.3456789;
	//alert("num = "+num);
	
	//Number.toFixed(digits): A member function that rounds the numeric value stored in a Number object to the specified number of decimal places (0-20) and returns it as a string
	//alert("num.toFixed(0) = "+num.toFixed(0));//num.toFixed(0) = 12
	//alert("num.toFixed(1) = "+num.toFixed(1));//num.toFixed(1) = 12.3
	alert("num.toFixed(2) = "+num.toFixed(2));//num.toFixed(2) = 12.35
	*/
	
	/*
	var num="100";
	//alert("Result of operation = "+(num+200));//Result of operation = 100200
	//Number(string): A function that converts the string value passed as a parameter to a numeric value and returns it
 	alert("Result of operation = "+(Number(num)+200));//Result of operation = 300
	*/
	
	alert("Maximum numeric value usable in JavaScript = "+Number.MAX_VALUE);
	alert("Minimum numeric value usable in JavaScript = "+Number.MIN_VALUE);
	</script>
</body>
</html>
```

---
## String 

```html

<title>JavaScript</title>
</head>
<body>
	<h1>String Constructor Function</h1>
	<hr>
	<p>String Constructor Function: A constructor function used to create String objects</p>
	<p>String Object: An object used to store string values</p>
	
	<script type="text/javascript">
	/*
	var str = "Hello, JavaScript!!!";
	alert("Data type of str variable = " + typeof(str)); // Data type of str variable = string
	*/
	
	/*
	// Create a String object with the string value passed as a parameter and store it in a variable
	var str = new String("Hello, JavaScript!!!");
	// alert("Data type of str variable = " + typeof(str)); // Data type of str variable = object
	
	// String.toString(): A member function that returns the string value stored in a String object
	// alert("str.toString() = " + str.toString());	
	// alert("str = " + str);	
	
	// String.length: A member variable that stores the number of characters in the string value stored in a String object
	// alert("str.length = " + str.length);
	
	// String.charAt(index): A member function that returns the character at the specified index in the string value stored in a String object
	// alert("str.charAt(7) = " + str.charAt(7)); // str.charAt(7) = J
	
	// String.indexOf(string): A member function that searches for the specified string value in the string value stored in a String object and returns the starting index. Returns -1 if the string is not found.
	// alert("str.indexOf('Script') = " + str.indexOf('Script')); // str.indexOf('Script') = 11
	// alert("str.indexOf('script') = " + str.indexOf('script')); // str.indexOf('script') = -1

	// String.replace(search, replace): A member function that searches for the first occurrence of the specified string in the string value stored in a String object and replaces it with another specified string
	// alert("str.replace('Hello', 'Hi') = " + str.replace('Hello', 'Hi')); // str.replace('Hello', 'Hi') = Hi, JavaScript!!!
	
	// String.toUpperCase(): A member function that converts the string value stored in a String object to uppercase and returns it
	// String.toLowerCase(): A member function that converts the string value stored in a String object to lowercase and returns it
	// alert("str.toUpperCase() = " + str.toUpperCase());	
	// alert("str.toLowerCase() = " + str.toLowerCase());	

	// String.substring(from, to): A member function that returns a substring from the specified start index (inclusive) to the end index (exclusive) in the string value stored in a String object
	// alert("str.substring(7, 11) = " + str.substring(7, 11)); // str.substring(7, 11) = Java
	
	// String.substr(from, length): A member function that returns a substring of the specified length starting from the specified start index in the string value stored in a String object
	// alert("str.substr(7, 4) = " + str.substr(7, 4)); // str.substr(7, 4) = Java

	// String.split(pattern): A member function that splits the string value stored in a String object by the specified delimiter and returns an array of substrings
	// Use destructuring assignment to store the elements of the returned array in variables
	var [str1, str2] = str.split(","); 
	// String.trim(): A member function that removes whitespace from both ends of the string value stored in a String object and returns it
	alert("str1 = " + str1 + ", str2 = " + str2.trim()); // str1 = Hello, str2 = JavaScript!!!
	*/
	
	var str = "ABCDEFG";
	// alert("Data type of str variable = " + typeof(str)); // Data type of str variable = string
	// String values are internally processed as String objects, allowing the use of member variables and member functions
	alert("str.length = " + str.length);
	</script>
</body>
</html>
```

---
## Date,Math,JSON

```html
<title>JavaScript</title>
</head>

<body>

<h1>Date Constructor Function</h1>
<hr>
<p>Date Constructor Function: A constructor function used to create Date objects</p>
<p>Date Object: An object used to store date and time</p>

<script type="text/javascript">
// Create a Date object with the current date and time of the client's platform (OS) and store it in a variable
var now = new Date();

// Date.toString(): A member function that converts the date and time stored in a Date object to a string and returns it
// alert("now.toString() = " + now.toString()); // now.toString() = Wed Jun 26 2024 16:01:19 GMT+0900 (Korean Standard Time)
// alert("now = " + now); // now = Wed Jun 26 2024 16:01:19 GMT+0900 (Korean Standard Time)

// Date.toLocaleString(): A member function that converts the date and time stored in a Date object to a string in the client's platform (OS) local date and time pattern and returns it
// alert("now.toLocaleString() = " + now.toLocaleString()); // now.toLocaleString() = 2024. 6. 26. 오후 4:04:05

/*
// Date.getXXX(): A member function that returns a specific value from the date and time stored in a Date object
var day = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
var today = now.getFullYear() + " year " + (now.getMonth() + 1) + " month "
    + now.getDate() + " day " + day[now.getDay()] + " day";
alert("today = " + today);
*/

// You can call the Date(year, month, date) constructor function to create a Date object with the desired date and time
var birthday = new Date(2000, 0, 1); // January 1, 2000, 00:00:00

// Date.getTime(): A member function that converts the date and time stored in a Date object to a numeric value in milliseconds (TimeStamp) and returns it
var intervalDay = (now.getTime() - birthday.getTime()) / (1000 * 60 * 60 * 24);
// alert("You have lived for " + intervalDay + " days until today.");
alert("You have lived for " + Math.ceil(intervalDay) + " days until today.");
</script>

<h1>Math Constructor Function</h1>
<hr>
<p>Math Constructor Function: A constructor function that provides member variables and member functions</p>

<script type="text/javascript">
// Math.PI: A member variable that stores the value of pi
// alert("Math.PI = " + Math.PI); // Math.PI = 3.141592653589793

// Math.ceil(number): A member function that rounds up the numeric value passed as a parameter to the nearest integer and returns it
// alert("Math.ceil(12.1) = " + Math.ceil(12.1)); // Math.ceil(12.1) = 13

// Math.floor(number): A member function that rounds down the numeric value passed as a parameter to the nearest integer and returns it
// alert("Math.floor(12.9) = " + Math.floor(12.9)); // Math.floor(12.9) = 12

// Math.round(number): A member function that rounds the numeric value passed as a parameter to the nearest integer and returns it
// alert("Math.round(12.4) = " + Math.round(12.4)); // Math.round(12.4) = 12
// alert("Math.round(12.5) = " + Math.round(12.5)); // Math.round(12.5) = 13

// Math.pow(number, number): A member function that calculates and returns the power of the numeric value passed as a parameter
// alert("Math.pow(3, 5) = " + Math.pow(3, 5)); // Math.pow(3, 5) = 243

// Math.random(): A member function that returns a random number between 0.0 (inclusive) and 1.0 (exclusive)
// alert("Random value (float) = " + Math.random());
alert("Random value (integer) = " + parseInt(Math.random() * 100)); // Returns an integer random value in the range of 0 to 99
</script>

<h1>JSON Constructor Function</h1>
<hr>
<p>JSON Constructor Function: A constructor function that provides member variables and member functions</p>

<script type="text/javascript">
// Create an Object object using JSON functionality and store it in a variable
var student = {num: 1000, name: "Hong Gil-dong"};
// alert("Data type of student variable = " + typeof(student)); // Data type of student variable = object
// alert("student = " + student); // student = [object Object]
// alert("Student number = " + student.num + ", Name = " + student.name); // Student number = 1000, Name = Hong Gil-dong

// JSON.stringify(object): A member function that converts a JavaScript object passed as a parameter to a JSON string and returns it
// => Used to convert a JavaScript object to a JSON string and send it to a REST web program
var str = JSON.stringify(student);
// alert("Data type of str variable = " + typeof(str)); // Data type of str variable = string
// alert("str = " + str); // str = {"num":1000,"name":"Hong Gil-dong"}

// JSON.parse(string): A member function that converts a JSON string passed as a parameter to a JavaScript object and returns it - eval function can be used
// => Used to convert a JSON string received as a response from a REST web program to a JavaScript object
// var stu = JSON.parse(str);
var stu = eval("(" + str + ")");
// alert("Data type of stu variable = " + typeof(stu)); // Data type of stu variable = object
// alert("stu = " + stu); // stu = [object Object]
alert("Student number = " + stu.num + ", Name = " + stu.name); // Student number = 1000, Name = Hong Gil-dong
</script>
</body>
</html>
```

---
## Destructuring

```html
<title>JavaScript</title>
</head>
<body>
	<h1>Destructuring Assignment</h1>
	<hr>
	<p>A feature that allows you to unpack values from objects or arrays into individual variables</p>
	
	<script type="text/javascript">
	/*
	var array=["Paul", "Sung", "Tim"];
	//alert(array);

	//var one=array[0];
	//var two=array[1];
	//var three=array[2];
	
	//Using destructuring assignment to store array elements in variables
	//Syntax: var [variable1, variable2, ...] = Array
	var [one, two, three]=array;
	
	alert("one = "+one+", two = "+two+", three = "+three);
	*/

	/*
	//var [one, two, three]=["Paul", "Sung", "Tim"];
	//When destructuring an array, you can set default values for variables
	//var [one, two, three="Eddie"]=["Paul", "Sung", "Tim"];
	var [one, two, three="Eddie"]=["Paul", "Sung"];

	alert("one = "+one+", two = "+two+", three = "+three);
	*/
	
	/*
	//You can set default values for function parameters
	function sum(num1=10, num2=20) {
		return num1+num2;
	}
	
	//alert("Sum = "+sum(100, 200));
	//alert("Sum = "+sum(100));
	alert("Sum = "+sum());
	*/
	
	/*
	var [num1, num2]=[10, 20];
	//alert("num1 = "+num1+", num2 = "+num2);//num1 = 10, num2 = 20
	
	//Using destructuring assignment to swap the values of two variables
	var [num1, num2]=[num2, num1];
	alert("num1 = "+num1+", num2 = "+num2);//num1 = 20, num2 = 10
	*/
	
	//Function that returns an array
	function returnArray() {
		return [10, 20, 30];
	}
	
	/*
	//Calling a function and storing the returned array in a variable
	var array=returnArray();
	alert("array[0] = "+array[0]+", array[1] = "+array[1]+", array[2] = "+array[2]);
	*/
	
	/*
	//Calling a function and storing the returned array elements in variables
	//var [num1, num2, num3]=returnArray();
	//alert("num1 = "+num1+", num2 = "+num2+", num3 = "+num3);

	//You can choose to only store the needed elements from the returned array
	//var [num1, , num2]=returnArray();
	//alert("num1 = "+num1+", num2 = "+num2);
	
	//Using the spread operator to store the remaining elements of the array
	var [num1, ... num2]=returnArray();
	//alert("num1 = "+num1);
	alert("num2 = "+num2);
	*/
	
	/*
	//Creating an object and storing it in a variable
	var student={"num":1000, "name":"Paul"};
	//alert("Student ID ="+student.num+", Name = "+student.name);
	//alert("Student ID ="+student["num"]+", Name = "+student["name"]);
	
	//Using destructuring assignment to store object properties in variables
	// => The variable names must match the property names to store the values
	//Syntax: var {property1, property2, ...} = Object
	//var {num, name}=student;
	//alert("Student ID ="+num+", Name = "+name);
	
	//If the variable names do not match the property names, the values will not be stored and the variables will be undefined
	//alert("Student ID ="+bunho+", Name = "+irum);
	
	//You can use different variable names while still storing the property values
	//Syntax: var {property1:variable1, property2:variable2, ...} = Object
	var {num:bunho, name:irum}=student;
	alert("Student ID ="+bunho+", Name = "+irum);
	*/
	
	/*
	var {num:bunho, name:irum}={num:1000, name:"Paul"};
	alert("Student ID ="+bunho+", Name = "+irum);
	*/
	
	var people=[{name:"Paul", subject:"JAVA", grade:"Beginner"}
		, {name:"Sung", subject:"JSP", grade:"Intermediate"}
		,{name:"Tim", subject:"SPRING", grade:"Advanced"}];
	
	/*
	for(i=0;i<people.length;i++) {
		//alert(people[i]);//[object Object]
		
		alert("Name = "+people[i].name+", Subject = "+people[i].subject+", Grade = "+people[i].grade);
	}
	*/
	
	/*
	for(obj of people) {
		alert("Name = "+obj.name+", Subject = "+obj.subject+", Grade = "+obj.grade);
	}
	*/
	
	/*
	for({name, subject, grade} of people) {
		alert("Name = "+name+", Subject = "+subject+", Grade = "+grade);
	}
	*/
	
	//Function that takes an object as a parameter and uses destructuring to store the values in variables
	function display({num,name}) {
		alert("Student ID = "+num+", Name = "+name);
	}
	
	display({num:1000, name:"Paul"});
	
	</script>
</body>
</html>
```