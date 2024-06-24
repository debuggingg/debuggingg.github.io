---
layout: single
title: 2024/06/19 HTML-04-Form-Label-TexArea-Select
---
## Form Tag
- **form Tag:**
    
    -  form tag: Used to request a web program via a **_submit event** and deliver user input values to the requested web program 
    -  Sub-tags must be written to receive input from the user and trigger the submit event to request the web program 
    -  Submit Event: Event to request a web program using the form tag 
    -  action attribute: Set the URL address of the web program to be requested using the form tag as the attribute value 
	    -  If the action attribute is omitted, the URL address of the current response-requested web program is used as the attribute value 
	-  method attribute: Set the request method (GET or POST) for requesting the web program as the attribute value 
	    -  If the method attribute is omitted, the web program is requested using the GET method 
    -  Depending on the request method, the way user input values are delivered is set differently 
    -  GET: User input values are delivered as a query string in the URL address 
	    -  Used to deliver a small amount of non-confidential values 
    -  POST: User input values are stored in the body of the request message and delivered 
	    -  Used to deliver confidential or large amounts of values 
    -  Request Message: Object that carries client information to the requested web resource when the client connects to the server and requests the web resource 
	    -  Consists of a header and a body; if the GET method is used, the body of the request message is not used 
    -  enctype attribute: Set the file type (MimeType) of user input values stored in the body of the request message as the attribute value 
	    -  application/x-www-form-urlencoded: Deliver encoded **_character data** of user input values 
	    -  multipart/form-data: Deliver raw data of user input values - used when receiving and delivering **_files** 
	    -  text/plain: Deliver unencoded character data of user input values 
    -  If the enctype attribute is omitted, the **attribute value defaults to application**/x-www-form-urlencoded 
- **input, select, textarea Tags:**
    
    -  Input tags: Tags to receive values from the user - input tag, select tag, textarea tag, etc. 
- **submit Tags:**
    
    -  Submit tags: Tags that trigger the submit event - input tag, button tag, img tag, etc. 
	    -  When the submit event occurs, the form tag executes, requesting the web program at the URL address set in the action attribute and delivering the values entered in the input tags to the requested web program 
	    -  The web program's execution result (web document) is received as a response and displayed - page transition 
	    -  The URL address in the client's browser address area changes to the URL address set in the action attribute 
    -  button Tag: Tag to display a button 
	    -  type attribute: Set one of button, submit, or reset as the attribute value 
	    -  If the type attribute is set to [submit], clicking the button triggers the submit event 
	    -  If the type attribute is omitted, the default value is submit 

#### form tag for log in and action 

```html
<body>
	<h1>form Tag</h1>
	<hr>
	<p>form tag is used to request a web program via a submit event and deliver user input values to the requested web program</p>
	
	<form action="13_action.html" method="post" enctype="application/x-www-form-urlencoded">
		<p>ID: <input type="text" name="id"></p>
		<p>Password: <input type="password" name="passwd"></p>
		<button type="submit">Login</button>
	</form>
</body>
```

#### Actiontag tag response back to main
**a Tag Using GET Method:**

-  Using the a tag, when a click event occurs, the web program is requested via GET method, and the response is received and processed for output 
	-  => Set the URL address of the requested web program in the href attribute 
	-  => When using the a tag to request a web program, values can be passed by directly writing a query string in the URL address 
-  URL addresses can only include alphanumeric characters and some special characters 
-  If characters other than alphanumeric characters, numbers, and some special characters are used in the query string, values cannot be passed normally 
-  To use characters other than alphanumeric characters, numbers, and some special characters, encode them before passing them; encoding can be done using Java or JavaScript 

```html
 <p><a href="/web/html/13_form.html?name=홍길동&phone=010-1234-5678">[Move to login page]</a> 
 <p><a href="/web/html/13_form.html?name=HongGilDong&phone=010-1234-5678">[Move to login page]</a>
```

---
---
## Input Tag
- input tag: A tag used to receive values (files) from users - input tag
	- type attribute: Specifies the type of input for receiving values (files)
	- name attribute: Specifies the name of the tag - an identifier for distinguishing tags: used for value validation (JavaScript) value attribute: Sets the initial value of the input tag
	- size attribute: Sets the size of the input tag
	- maxlength attribute: Sets the maximum number of characters that can be entered in the input tag
	- autofocus attribute: Provides focus to the tag - attribute value can be omitted
	- required attribute: Specifies that the input tag must have a value - attribute value can be omitted
	- placeholder attribute: Sets the description for the input tag value
	- readonly attribute: Prevents the input tag value from being changed - attribute value can be omitted
	- checked attribute: Specifies a default selection for input tags with type [radio] or [checkbox]
	- min attribute: Sets the minimum value that can be entered in the input tag
	- max attribute: Sets the maximum value that can be entered in the input tag

- input tag types:
	- hidden type: Hides the input tag, allowing values to be passed without user input
	- text type: Allows text input from the keyboard
	- password type: Allows password input from the keyboard - input characters are displayed as a specific character
	- radio type: Allows selection of one input tag among those with the same name attribute to pass a value
	- checkbox type: Allows selection of zero or more input tags among those with the same name attribute to pass values
	- file type: Allows selection and transfer of files - form tag's enctype attribute should be set to [multipart/form-data]
	- email type: Allows input and validation of email addresses
	- number type: Allows input and validation of numeric values - increment and decrement buttons are provided
	- tel type: Allows input and validation of phone numbers - changes input pad for **smart devices**
	- url type: Allows input and validation of URL addresses - changes input pad for **smart devices**
	- search type: Allows input and transfer of search terms - utilizes auto-complete feature on **smart devices**
	- color type: Allows selection of a color and passes its color value (#RRGGBB)
	- range type: Allows selection of a value within a specified range defined by min and max attributes
	- date type: Allows input and transfer of date values (yyyy-MM-dd) - provides a date selection box
	- time type: Allows input and transfer of time values ({AM|PM} HH:mm) - provides a time selection box
	- datetime-local type: Allows input and transfer of date and time values - provides a date-time selection box

- Submit 
	Element that triggers the submission event - input tag, button tag, img tag, etc.
	When the type attribute of an input tag is set to [submit], it appears as a button, and clicking it triggers the submission event
	Sets the label for the button using the value attribute

- Reset:
	Element that resets all input values within the form tag
	Triggers the reset event - input tag, button tag, img tag, etc.
	When the type attribute of an input tag is set to [reset], it appears as a button, and clicking it triggers the reset event
	Sets the label for the button using the value attribute
## Full code

```html
<body>
	<h1>input tag</h1>
	<hr>
	
	<form action="#" method="post">
		<input type="hidden" name="num" value="1000">
		<p> id : <input type="text" name="id" size="10" maxlength="20" autofocus required><p>
		<p> pw : <input type="password" name="passwd" placeholder="pw"><p>
		<p>name : <input type="text" name="name" value="paul" readonly></p>
		<p>gender : <input type="radio" name="gender" value="M">Male
			<input type="radio" name="gender" value="W" checked>Female</p>
		<p>hobby : <input type="checkbox" name="hobby" value="reading">Reading
			<input type="checkbox" name="hobby" value="hiking">Hiking
			<input type="checkbox" name="hobby" value="fishing">Fishing
			<input type="checkbox" name="hobby" value="game">Gaming</p>	
		<p>picture : <input type="file" name="photo"></p>
		<p>email : <input type="email" name="email"></p>
		<p>age : <input type="number" name="age" min="0" max="200"></p>
		<p>phone : <input type="tel" name="phone"></p>
		<p>SNS : <input type="url" name="sns"></p>
		<p>favorite keyword : <input type="search" name="keyword"></p>
		<p>favorite color : <input type="color" name="color"></p>
		<p>Java skill : Low<input type="range" name="grade" min="1" max="5">High</p>
		<p>birth : <input type="date" name="birthday"></p> 
		<p>wake time : <input type="time" name="uptime"></p> 
		<p>alarm: <input type="datetime-local" name="alarm"></p> 

		
		<button type="submit">sign up</button>

	
		<button type="reset">reset</button>
	</form>
</body>
```

---
## Label Tag

- label tag: A tag used to provide focus to input tags
	=> Without using the label tag, you must click the input tag to provide focus for attribute: Sets the attribute value to the identifier (id attribute value) of the input tag
	=> Clicking the label tag provides focus to the input tag specified by the for attribute value

## Full code

```html
<body>
	<h1>label tag</h1>
	<hr>
	<h2>label no used  tag</h2>
	<p>name : <input type="text" name="name"></p>
	<p>hobby : <input type="checkbox" name="hobby" value="Reading">Reading
			<input type="checkbox" name="hobby" value="Hiking">Hiking
			<input type="checkbox" name="hobby" value="Fishing">Fishing
			<input type="checkbox" name="hobby" value="Gaming">Gaming</p>	
	<hr>		
	<h2>label with tag</h2>
	<p><label>name : <input type="text" name="name"></label></p>
	<p>hobby : <input type="checkbox" name="hobby" value="Reading" id="hobby1"><label for="hobby1">Reading</label>
			<input type="checkbox" name="hobby" value="Hiking" id="hobby2"><label for="hobby2">Hiking</label>
			<input type="checkbox" name="hobby" value="Fishing" id="hobby3"><label for="hobby3">Fishing</label>
			<input type="checkbox" name="hobby" value="Gaming" id="hobby4"><label for="hobby4">Gaming</label></p>		
</body>
```

---
## Textarea
-  textarea tag: A tag used for receiving multi-line input from the keyboard 
	=> If content is written within the textarea tag, it will be used as the initial value of the input field 
-  rows attribute: Sets the number of rows for the input field
 - cols attribute: Sets the number of columns for the input field 
## Full Code


```html
<body>
	<h1>textarea 태그</h1>
	<hr>
	<form method="post">
		<table>
			<tr>
				<td>good</td>
				<td><textarea rows="5" cols="80" name="good">a lot good thing.</textarea></td>
			</tr>
			<tr>
				<td>bad</td>
				<td><textarea rows="5" cols="80" name="bad"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit">submit</button></td>
			</tr>
		</table>
	</form>
</body>
```

---
## Select tag

 - select tag: A tag used to choose one item from a list of multiple items for input 
	 => Sub-tags: option tag, optgroup tag 
	 => Using the multiple attribute allows for multiple items to be selected and submitted 
-  optgroup tag: A tag used to group items defined by option tags 
 - label attribute: Sets the name of the group as its value 
 - option tag: A tag used to provide items in the select tag 
	 => When an item is selected, the value set in the option tag's value attribute is submitted 
 - selected attribute: Provides a priority selection feature - the attribute value can be omitted 

## Full code

```html
<body>
	<h1>select tag</h1>
	<hr>
	<form action="#" method="post">
		<label for="class">Department</label>
		<select id="class" name="class">
			<optgroup label="Engineering">
				<option value="arch">Architecture</option>
				<option value="machinic">Mechanical Engineering</option>
				<option value="indust">Industrial Engineering</option>
				<option value="computer" selected>Computer Engineering</option>
				<option value="chemical">Chemical Engineering</option>
			</optgroup>
			<optgroup label="Humanities">
				<option value="history">History</option>
				<option value="lang">Linguistics</option>
				<option value="philo">Philosophy</option>
			</optgroup>
		</select>
		<button type="submit">Submit</button>
	</form>
</body>
```


---

## Datalist and Filedset

 - datalist tag: A tag used to provide possible input values for an input tag 
	 => Sub-tag: option tag 
 - list attribute: Sets the datalist tag's identifier (id attribute value) as its value 
	 => Allows selecting one of the values provided by the datalist tag for input 
 - fieldset tag: A tag used to group related input elements 
	 => Sub-tags: legend tag, input tag, textarea tag, select, etc. 
 - legend tag: A tag used to provide a title for the fieldset 

## Full Code

```html
<body>
	<h1>datalist tag</h1>
	<hr>
	<form action="#" method="post">
		<label for="subject">Favorite Subject:</label>
		<input type="text" name="subject" id="subject" list="subjectlist">
		<datalist id="subjectlist">
			<option>JAVA</option>		
			<option>JSP</option>		
			<option>SPRING</option>		
		</datalist>
		<button type="submit">Submit</button>
	</form>
	
	<h1>fieldset tag</h1>
	<hr>
	<form action="#" method="post">
		<fieldset>
			<legend>Authentication Information</legend>
			<ul>
				<li>ID: <input type="text" name="id"></li>
				<li>Password: <input type="password" name="passwd"></li>
			</ul>		
		</fieldset>
		<fieldset>
			<legend>Personal Information</legend>
			<ul>
				<li>Name: <input type="text" name="name"></li>
				<li>Email: <input type="text" name="email"></li>
				<li>Phone Number: <input type="text" name="phone"></li>
			</ul>
		</fieldset>
		<div>
			<button type="submit">Sign Up</button>
			<button type="reset">Reset</button>
		</div>
	</form>
</body>
```
