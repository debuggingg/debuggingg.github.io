---
layout: single
title: 2024/07/17 JSP 17-EX-Shop-Review-write
---
## Review Write
#### review_write.jsp (must have form tag with <enctype="multipart/form-data"> becuse DATA move )
- received data info from reveiw_list.jsp( new text) and review_detail.jsp(reply text)
- You should create a "review_images" folder to save images uploaded by writers. (The reason for creating this folder is to ensure that Apache Tomcat automatically creates a folder with the same name. When images are uploaded, they will go into the "review_image" folder within Apache Tomcat. You need to copy these images into your actual server's "review_images" folder so that they can be accessed and viewed by calling their URLs.)
- copy and paste "cos.jar"file to  \<webapp->WEB-INF->lib>
	- cos.jar:  In Java, this is the way to define a package, and this package is distributed as a JAR file named cos.jar.

#### DAO class update 

add this three methods;
selectReviewNextNum()
updateReviewRestep(ref, restep);
insertReview(review);

#### review_write_action.jsp 


#### Utility.Java after update Utility ->go to review_write_action.jsp 
- You need to write code using the Pattern in a Utility file under xyz.itwill.util to prevent XSS (Cross-Site Scripting) attacks. Otherwise, when writing content in HTML format on the web, it will be directly displayed and executed as-is on the web page.
	

```java
public static String stripTag(String source) {
    // Pattern.compile(String regex): A static method that creates and returns a Pattern object
    // storing the regular expression provided as a parameter.
    // => Pattern object: Object used to store regular expressions.
    // => Pattern.CASE_INSENSITIVE: Constant field used for case-insensitive comparison.
    Pattern htmlTag = Pattern.compile("\\<.*?\\>");
    // Pattern htmlTag = Pattern.compile("\\<.*?\\>", Pattern.CASE_INSENSITIVE);

    // Pattern.matcher(CharSequence input): Creates and returns a Matcher object using the
    // provided string (input) and the regular expression stored in the Pattern object.
    // => Matcher object: Object used to compare the regular expression with the input string
    // and provide search and modification (deletion) capabilities.
    Matcher matcher = htmlTag.matcher(source);

    /*
    // Matcher.find(): Searches for and returns true if the Matcher object finds any substring
    // matching the regular expression stored in the object.
    // => If there are multiple substrings matching the regular expression, a loop is used
    // to sequentially search and process each one.
    while (matcher.find()) {
        // Matcher.group(): Returns the substring that matches the regular expression stored
        // in the Matcher object.
        String group = matcher.group();
        System.out.println(group);
    }
    */

    // Matcher.replaceAll(String replacement): Uses the information stored in the Matcher
    // object (regular expression and string) to find all substrings matching the regular
    // expression pattern in the string and replaces them with the replacement string provided
    // as a parameter.
    return matcher.replaceAll("");
    
}
```
- The other way  - this is more common becuase it will be able to use <> mark 
```java
// A static method that receives a string and returns it after replacing tag-related characters (< or >) with escape characters.
public static String escapeTag(String source) {
    return source.replace("<", "&lt;").replace(">", "&gt;");
}
```
#### Add->go to review_write_action.jsp 
original : String reviewSubject = multipartReques.getParameter("reviewSubject");
->String reviewSubject = **Utility.stripTag**(multipartReques.getParameter("reviewSubject"));
```java
// If HTML tags are present in values received from user input, it can cause issues during web application execution.
// => XSS (Cross Site Scripting) Attack: When a user inputs malicious scripts to disrupt the page's normal behavior,
//    prevent other users from accessing services, or compromise personal information.
// String reviewSubject = multipartReques.getParameter("reviewSubject");
String reviewSubject = Utility.stripTag(multipartReques.getParameter("reviewSubject"));
```
- The other way  - this is more common becuase it will be able to use <> mark 
original : String reviewSubject = Utility.stripTag(multipartReques.getParameter("reviewSubject"));
-> String reviewSubject=**Utility.escapeTag**(multipartReques.getParameter("reviewSubject"));
original : String reviewContent=multipartReques.getParameter("reviewContent");
->String reviewContent=Utility.escapeTag(multipartReques.getParameter("reviewContent"));

#### Review_Write


