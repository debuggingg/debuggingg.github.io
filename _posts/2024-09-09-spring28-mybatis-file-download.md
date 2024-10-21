---
layout: single
title: 2024-09-09-spring28-mybatis-file-delete-download
---
#### FileController

#### Delete 
```java

public class FileController {
@RequestMapping("/delete")
public String fileBoardDelete(@RequestParam int num) {
    // Retrieve the file board to be deleted and save it - used to obtain the uploaded filename
    FileBoard fileBoard = fileBoardService.getFileBoard(num);
    
    // Save the system path of the server directory where uploaded files are stored
    String uploadDirectory = context.getServletContext().getRealPath("/WEB-INF/upload");
    
    // Delete the uploaded file stored in the server directory
    new File(uploadDirectory, fileBoard.getFilename()).delete();
    
    // Remove the file board entry from the service
    fileBoardService.removeFileBoard(num);
    
    return "redirect:/file/list";
}

}
```

### Explanation:

- **File Deletion (`/delete` endpoint)**:
  - Retrieves the file board entry based on the `num` parameter.
  - Gets the real path of the server directory where uploaded files are stored.
  - Deletes the file from the server directory.
  - Removes the file board entry from the service.
  - Redirects to the file list page.
#### DownLoad


```java 

public class FileController {
// Download: Functionality to deliver a file stored in the server directory to the client for saving
// => The request handling method returns a string (ViewName) to invoke a method of a download
// functionality object that handles the response by delivering the file to the client for saving
// => The BeanNameViewResolver object is used to resolve the ViewName returned by the request handling method
// into a response handling class object and invoke the method of that object for response processing
// => Register the BeanNameViewResolver class as a Spring Bean in the Spring Bean Configuration File (servlet-context.xml)
// and set its priority to the highest among the ViewResolvers
@RequestMapping("/download")
public String fileBoardDownload(@RequestParam int num, Model model) {
    FileBoard fileBoard = fileBoardService.getFileBoard(num);

    // Use the Model object to store request scope attributes that will be used by the response handling class's method
    model.addAttribute("uploadDirectory", context.getServletContext().getRealPath("/WEB-INF/upload"));
    model.addAttribute("uploadFilename", fileBoard.getFilename());
    
    // Return the bean name of the response handling class to be resolved by the BeanNameViewResolver
    // => The response handling class must be registered as a Spring Bean in the Spring Bean Configuration File (servlet-context.xml)
    // using the bean element or the @Component annotation
    return "fileDownload";
}
```
- **File Download (`/download` endpoint)**:
  - Retrieves the file board entry based on the `num` parameter.
  - Adds attributes to the model, including the upload directory path and filename.
  - Returns a view name (`fileDownload`) to resolve the appropriate Spring Bean responsible for handling the download functionality.

#### servlet-context.xml

```xml
<!-- Register the response handling class that provides file download functionality as a Spring Bean -->
<!-- => Set the Spring Bean identifier (beanName) to match the return value (ViewName) of the request handling method -->
<beans:bean class="xyz.itwill09.util.FileDownload" id="fileDownload"/>
```
1. servlet-context.xml -BeanNameViewResolver

#### FileDownload.java 


```java
package xyz.itwill09.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

// Response handling class for providing file download functionality
// => Methods of the class are invoked by the BeanNameViewResolver object for response handling
// => Registered as a Spring Bean in the Spring Bean Configuration File (servlet-context.xml)
// The response handling class executed by the BeanNameViewResolver object must extend the AbstractView class (abstract class)
// => Implement the abstract method renderMergedOutputModel() to handle the response processing commands
public class FileDownload extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // Retrieve Request Scope attributes saved by the request handling method
        // => Use the Map object stored in the model parameter of the renderMergedOutputModel() method
        // to access the Request Scope attributes
        String uploadDirectory = (String) model.get("uploadDirectory");
        String uploadFilename = (String) model.get("uploadFilename");
        
        // Create a File object for the uploaded file stored in the server directory
        File file = new File(uploadDirectory, uploadFilename);
        
        // Set the response content type for the file to be downloaded
        response.setContentType("application/download; utf-8");
        
        // Set the response content length to the size of the file
        response.setContentLengthLong(file.length());
        
        // Set the filename to be saved on the client side in the response header
        // => Encode the filename if it contains non-ASCII characters
        String originalFilename = URLEncoder.encode(uploadFilename.substring(37), "utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + originalFilename + "\";");
        
        // Get an output stream to write the file to the response body
        OutputStream out = response.getOutputStream();
        
        // Create an input stream to read the file from the server directory
        InputStream in = new FileInputStream(file);
        
        // Use FileCopyUtils.copy(InputStream in, OutputStream out) to copy the file content from input stream to output stream
        FileCopyUtils.copy(in, out);
        
        in.close();
    }
}
```

### Explanation:

- **FileDownload Class**:
  - **Purpose**: Handles file download responses.
  - **Inherits from**: `AbstractView` which is a Spring framework class used for custom view handling.
  - **Method**: `renderMergedOutputModel` is overridden to process the file download.
  
- **Method Details**:
  - Retrieves `uploadDirectory` and `uploadFilename` from the `model` parameter.
  - Creates a `File` object to represent the file to be downloaded.
  - Sets the MIME type and content length for the response.
  - Sets the `Content-Disposition` header to prompt the browser to download the file, with proper encoding for filenames.
  - Reads the file from the server and writes it to the response output stream.
  - Uses `FileCopyUtils.copy()` to efficiently copy data from the file input stream to the response output stream.

