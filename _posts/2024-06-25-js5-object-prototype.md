---
layout: single
title: 2024/06/25 JavaScript-Object-prototype,
---
# Object

## Prototype

```html
<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript Objects</h1>
	<hr>
	<p>JavaScript is an object-oriented programming language that uses objects to structure programs. Every value in JavaScript can be represented as an object.</p>
	<p>An object is a value representing a specific entity with properties (attributes) and methods (functions).</p>
	<p>Instead of using class data types, JavaScript uses constructor functions (prototypes) to create objects. The <code>new</code> operator is used to call a constructor function and create an object.</p>
	<p>Using constructor functions allows the creation of multiple objects with the same properties and methods, improving productivity and maintainability.</p>	
	
	<script type="text/javascript">
	// Constructor function (Prototype) definition - a special function to create objects
	// Constructor functions are named with a capital letter to distinguish them from regular functions
	// Parameters are used to initialize properties
	function Student(num, name, address) {
		// 'this' keyword refers to the object instance created by the constructor function
		
		// Defining object properties using 'this' keyword
		this.num = num;
		this.name = name;
		this.address = address;
	}
	
	// Adding methods to the constructor function using the prototype property
	// This way, methods are shared across all instances, saving memory
	Student.prototype = {
		display: function() {
			alert("Student ID = " + this.num + ", Name = " + this.name + ", Address = " + this.address);
		},
		setValue: function(num, name, address) {
			this.num = num;
			this.name = name;
			this.address = address;
		}
	};
	
	// Creating objects using the constructor function
	// Using the 'new' operator to call the constructor function and create an object
	var student1 = new Student(1000, "Paul", "Seoul Gangnam");
	var student2 = new Student(2000, "Sung", "Seoul Sonpa");
	
	// Displaying properties and methods of the created objects
	student1.display();
	student2.display();
	</script>
</body>
</html>
```


- **Constructor Function**:
   - The `Student` constructor function initializes new objects with the given `num`, `name`, and `address` properties.

-  **Prototype**:
   - Methods (`display` and `setValue`) are added to the `Student` prototype, ensuring that all instances of `Student` share these methods, optimizing memory usage.

-  **Object Creation**:
   - Objects `student1` and `student2` are created using the `new` operator, invoking the `Student` constructor function.

-  **Displaying Properties**:
   - The `display` method is called on both `student1` and `student2` to show their properties.
---

## Class

-  **Class Definition**:
   - The `Human` class is defined with a constructor to initialize the `num` property.
   - The `Student` class extends the `Human` class and adds `name` and `address` properties. It also includes methods `display` and `setValue`.

-  **Object Creation**:
   - An instance of the `Student` class is created using the `new` operator, and its properties are initialized through the constructor.

-  **Method Invocation**:
   - The `display` method is called to show student details.
   - The `setValue` method is called to update student details, and `display` is called again to show the updated details.

-  **Property Access**:
   - Object properties and methods are accessed using both dot notation (`student.display()`) and bracket notation (`student["display"]()`).

-  **Type Checking**:
   - The `instanceof` operator checks whether `student` is an instance of `Student` and `Human`.

-  **Property Existence Checking**:
   - The `in` operator checks if certain properties exist within the `student` object.

-  **Iteration over Properties**:
   - A `for...in` loop iterates over the properties of the `student` object, displaying each property and its value.

-  **Convenient Property Access**:
   - The `with` statement is used to access multiple properties of the `student` object more conveniently. 


```html
<title>JavaScript</title>
</head>
<body>
	<h1>JavaScript Objects</h1>
	<hr>
	<p>In ES6, the class keyword allows you to define a class and create objects using the new operator to call the class constructor. Internally, this is still handled using constructor functions (prototypes).</p>
	
	<script type="text/javascript">
	// Defining a Human class with a constructor
	class Human {
		constructor(num) {
			// Initializing object properties
			this.num = num;	
		}
	}
	
	// Defining a Student class that extends the Human class
	class Student extends Human {
		constructor(num, name, address) {
			// Calling the parent class constructor with the super keyword
			super(num);
			this.name = name;
			this.address = address;	
		}
		
		// Defining a method to display student details
		display() {
			alert("StudentID = " + this.num + ", Name = " + this.name + ", Address = " + this.address);
		}
		
		// Defining a method to set student details
		setValue(num, name, address) {
			this.num = num;
			this.name = name;
			this.address = address;
		}
	}
	
	// Creating a Student object using the Student class
	let student = new Student(1000, "Paul", "Seoul Gangnam");

	// Displaying student details using the display method
	student.display();

	// Setting new student details using the setValue method
	student.setValue(2000, "Sung", "Seoul Sonpa");
	student.display();

	// Accessing object properties and methods using the dot notation
	alert("StudentID = " + student.num + ", Name = " + student.name + ", Address = " + student.address);
	student.display();

	// Accessing object properties and methods using the bracket notation
	alert("StudentID = " + student["num"] + ", Name = " + student["name"] + ", Address = " + student["address"]);
	student["display"]();

	// Using instanceof operator to check the type of the object
	alert(student instanceof Student); // true
	alert(student instanceof Human); // true

	// Using in operator to check if a property exists in the object
	alert("num" in student); // true
	alert("display" in student); // true
	alert("phone" in student); // false

	// Using for...in loop to iterate over object properties
	for (let variable in student) {
		alert(variable + ": " + student[variable]);
	}

	// Using with statement to access object properties more conveniently
	with (student) {
		alert("StudentID = " + num + ", Name = " + name + ", Address = " + address);
	}
	</script>
</body>
</html>
```
---
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
	student.name = "홍길동";
	student.address = "서울시 강남구";
	
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
		"name": "홍길동",
		"address": "서울시 강남구",
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
