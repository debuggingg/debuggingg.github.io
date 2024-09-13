---
layout: single
title: 2024-09-06-spring26-mybatis-filecontroller
---
#### file upload - pom.xml 
1. Apache Commons FileUpload
pom.xml
```xml
	<!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
	<dependency>
		    <groupId>commons-fileupload</groupId>
		    <artifactId>commons-fileupload</artifactId>
		    <version>1.5</version>
		</dependency>
```
2. servlet-context -add beans :bean

-
```xml
<!-- Register a class (CommonsMultipartResolver) that provides functionality to handle values or files transmitted in [multipart/form-data] format as a Spring Bean -->
<!-- => The identifier (beanName) for the Spring Bean must be set to [multipartResolver] -->
<beans:bean class="org.springframework.web.multipart.commons.CommonsMultipartResolver" id="multipartResolver">
    <!-- Inject the maximum upload size limit for files into the maxUploadSize field -->
    <beans:property name="maxUploadSize" value="20971520"/>    
    <!-- Inject the character encoding for transmitted values into the defaultEncoding field -->
    <beans:property name="defaultEncoding" value="utf-8"/>    
</beans:bean>
 
```
3.  received file or attribute using by MultiPartHttpServletRequest object 



#### FileController
```java
package xyz.itwill09.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

// How to handle file uploads from clients to the server directory
// 1. Add the commons-fileupload library to the project build - Maven: pom.xml
// 2. Register the class that provides file upload functionality as a Spring Bean in the Spring Bean Configuration File (servlet-context.xml)
// 3. Use the MultipartHttpServletRequest object to handle values or files transmitted in [multipart/form-data] format

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    // Dependency injection of the WebApplicationContext object (Spring container) into the field
    private final WebApplicationContext context;
    
    @RequestMapping(value = "/upload1", method = RequestMethod.GET)
    public String uploadOne() {
        return "file/form_one";
    }
    
    /*
    // If the request handling method's parameter is of type MultipartHttpServletRequest, the Front Controller will provide a MultipartHttpServletRequest object
    // => MultipartHttpServletRequest object: an object used to handle values or files transmitted in [multipart/form-data] format
    @RequestMapping(value = "/upload1", method = RequestMethod.POST)
    public String uploadOne(MultipartHttpServletRequest request) throws IOException {
        String uploaderName = request.getParameter("uploaderName");
        
        // MultipartHttpServletRequest.getFile(String name): Returns a MultipartFile object containing information about the uploaded file
        MultipartFile uploaderFile = request.getFile("uploaderFile");
        
        // Validation of the uploaded file
        // MultipartFile.isEmpty(): Returns [false] if the MultipartFile object contains file information, and [true] if it does not
        if (uploaderFile.isEmpty()) {
            return "file/upload_fail";
        }
        
        // MultipartFile.getContentType(): Returns the MIME type of the uploaded file
        System.out.println("File type (MimeType) = " + uploaderFile.getContentType());
        // MultipartFile.getBytes(): Returns the content of the uploaded file as a byte array
        System.out.println("File size (Bytes) = " + uploaderFile.getBytes().length);
        
        // Retrieve the system path of the server directory where the file will be stored
        // resources 에 경로 설정 하는 이유는 사진 파일은 view 가 응답해서 보여줄수없기때문에 frontcontroller 가 직접 응답 할수있도록 resource 안에 파일 경로를 설정 하여주어야 한다.
        String uploadDirectory = request.getServletContext().getRealPath("/resources/images/upload");
        System.out.println("uploadDirectory = " + uploadDirectory);
        
        // MultipartFile.getOriginalFilename(): Returns the original filename of the uploaded file
        String uploadFilename = uploaderFile.getOriginalFilename(); 

        // Create a File object with information about the file to be stored on the server directory
        File file = new File(uploadDirectory, uploadFilename);
        
        // MultipartFile.transferTo(File file): Uploads the file stored in the MultipartFile object to the File object
        // => If a file with the same name already exists in the server directory, the uploaded file will overwrite it
        uploaderFile.transferTo(file);
        
        request.setAttribute("uploaderName", uploaderName);
        request.setAttribute("uploadFilename", uploadFilename);
        
        return "file/upload_success_one";
    }
    */
    
    // Use parameters in the request handling method to receive values and files
    // => Parameters should have the same names as the names of the transmitted values and files
    // Issue: If a file with the same name exists in the server directory, it will be overwritten by the uploaded file
    // Solution: Save the uploaded file with a unique name instead of using the transmitted file's name
    @RequestMapping(value = "/upload1", method = RequestMethod.POST)
    public String uploadOne(@RequestParam String uploaderName
            , @RequestParam MultipartFile uploaderFile, Model model) throws IOException {
        if (uploaderFile.isEmpty()) {
            return "file/upload_fail";
        }
        
        String uploadDirectory = context.getServletContext().getRealPath("/resources/images/upload");
        // UUID.randomUUID(): Static method that returns a UUID object with a 36-byte identifier
        // UUID.toString(): Returns the 36-byte identifier of the UUID object as a string
        String uploadFilename = UUID.randomUUID().toString() + "_" + uploaderFile.getOriginalFilename(); 
        
        File file = new File(uploadDirectory, uploadFilename);
        uploaderFile.transferTo(file);
        
        model.addAttribute("uploaderName", uploaderName);
        model.addAttribute("uploadFilename", uploadFilename);
        
        return "file/upload_success_one";
    }
    
    @RequestMapping(value = "/upload2", method = RequestMethod.GET)
    public String uploadTwo() {
        return "file/form_two";
    }
    
    @RequestMapping(value = "/upload2", method = RequestMethod.POST)
    public String uploadTwo(@RequestParam String uploaderName
            , @RequestParam List<MultipartFile> uploaderFileList, Model model) throws IOException {
        String uploadDirectory = context.getServletContext().getRealPath("/resources/images/upload");

        List<String> filenameList = new ArrayList<>();
        
        // Iterate through the elements (MultipartFile objects) stored in the List
        for (MultipartFile multipartFile : uploaderFileList) {
            if (multipartFile.isEmpty()) {
                return "file/upload_fail";
            }
            
            String uploadFilename = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename(); 
            File file = new File(uploadDirectory, uploadFilename);
            multipartFile.transferTo(file);
            
            // Add the uploaded file to the list of filenames
            filenameList.add(uploadFilename);
        }
        
        model.addAttribute("uploaderName", uploaderName);
        model.addAttribute("filenameList", filenameList);
        
        return "file/upload_success_two";
    }
}
```

This `FileController` class handles file uploads in a Spring MVC application. It demonstrates how to process single and multiple file uploads, manage file names to prevent overwriting, and update the model with information about the uploaded files.

#### form_two.jsp


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
</head>
<body>
    <h1>File Upload</h1>
    <hr>
    <form action="<c:url value="/file/upload2"/>" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td>Uploader Name</td>
            <td><input type="text" name="uploaderName"></td>
        </tr>
        <tr>
            <td>Uploader Files</td>
            <%-- multiple attribute: provides functionality to input and transmit multiple files --%>
            <%-- => Attribute value is optional --%>
            <td>
                <input type="file" name="uploaderFileList" multiple="multiple">
                <span style="color: red;">* You can input multiple files.</span>
            </td>
        </tr>
        <tr>
            <td colspan="2"><button type="submit">Submit</button></td>
        </tr>
    </table>    
    </form>
</body>
</html>
```

#### upload_success_two.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
</head>
<body>
    <h1>File Upload Success</h1>
    <hr>
    <h2>Uploader Name = ${uploaderName}</h2>
    <c:forEach var="filename" items="${filenameList}" varStatus="status">
        <h2>Uploaded Filename-${status.count}: ${filename}</h2>
    </c:forEach>
    <hr>
    <c:forEach var="filename" items="${filenameList}">
        <img src="<c:url value="/images/upload/${filename}"/>" width="200">
    </c:forEach>
</body>
</html>
```
