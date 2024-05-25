---
layout: single
title:  "2024/05/22/ IO-03/File In,OutputStream / "
---

### File OutputStream 


This Java program reads raw data from the keyboard and writes it to a file until the End Of File (EOF) signal (Ctrl+Z) is received.



#### Concepts:
- **FileOutputStream Class:** Used to create an output stream to write raw data to a file.
  - **Constructor:** `FileOutputStream(String name)` creates a `FileOutputStream` object with the specified file path.
  - **Appending Constructor:** `FileOutputStream(String name, boolean append)` creates a `FileOutputStream` object with the specified file path and determines whether to append to the existing file.

---

#### Program Flow:
1. **Initialize Output Stream:**
   - Create a `FileOutputStream` object for the file path "c:/data/byte.txt".
   - Set to append mode to add data to the file if it already exists.

2. **Read-Write Loop:**
   - Continuously read bytes from the keyboard.
   - Write each byte to the file until the EOF signal is received.

3. **Close Output Stream:**
   - Close the `FileOutputStream` to ensure the data is saved and the stream is properly closed.

4. **Completion Message:**
   - Display a message indicating the file operation is complete and prompt the user to check the file.

---

#### Full Code:

~~~
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamApp {
    public static void main(String[] args) throws IOException {
        System.out.println("[Message] Please enter values using the keyboard. [End program: Ctrl+Z]");

        // Create a FileOutputStream object for "c:/data/byte.txt" in append mode
        FileOutputStream out = new FileOutputStream("c:/data/byte.txt", true);

        int readByte;
        while (true) {
            // Read raw data from the keyboard input stream
            readByte = System.in.read();

            if (readByte == -1) break;

            // Write the raw data to the file output stream
            out.write(readByte);
        }

        // Close the file output stream
        out.close();

        System.out.println("[Result] Check the c:/data/byte.txt file.");
    }
}
~~~

---


- **Creating `FileOutputStream` Object:**
  - `FileOutputStream out = new FileOutputStream("c:/data/byte.txt", true);`
    - Creates a `FileOutputStream` object to write to "c:/data/byte.txt".
    - The `true` parameter enables append mode, so new data is added to the end of the file if it exists.

- **Read-Write Loop:**
  - `readByte = System.in.read();`
    - Reads raw data from the keyboard input stream.
  - `out.write(readByte);`
    - Writes the raw data to the file output stream.

- **Closing the Output Stream:**
  - `out.close();`
    - Closes the `FileOutputStream` to finalize the write operation and release resources.

---

#### Key Points:
- Demonstrates basic file write operations using `FileOutputStream`.
- Shows how to append data to an existing file.
- Reads and writes raw data in a loop until the EOF signal is received.
- Ensures proper closure of the file output stream.

---


### File InputStream


This Java program reads raw data from a file and prints it to the console.

---

#### Concepts:
- **FileInputStream Class:** Used to create an input stream to read raw data from a file.
  - **Constructor:** `FileInputStream(String name)` creates a `FileInputStream` object with the specified file path.
  - **Exception Handling:** If the specified file does not exist, a `FileNotFoundException` is thrown and must be handled.

---

#### Program Flow:
1. **Initialize Input Stream:**
   - Create a `FileInputStream` object for the file path "c:/data/byte.txt".
   - Handle `FileNotFoundException` to notify the user if the file does not exist.

2. **Read-Write Loop:**
   - Continuously read bytes from the file.
   - Write each byte to the console until the End Of File (EOF) signal is reached.

3. **Close Input Stream:**
   - Close the `FileInputStream` to release resources.

---

#### Full Code:

~~~
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInputStreamApp {
    public static void main(String[] args) throws IOException {
        // Create a FileInputStream object to read raw data from "c:/data/byte.txt"
        FileInputStream in = null;
        try {
            in = new FileInputStream("c:/data/byte.txt");
        } catch (FileNotFoundException e) {
            System.out.println("[Error] Cannot find the file c:/data/byte.txt.");
            System.exit(0);
        }

        System.out.println("[Message] Contents of the file c:/data/byte.txt:");

        int readByte;

        while (true) {
            // Read raw data from the file input stream and store it in the variable
            readByte = in.read();

            // Break the loop if EOF (End Of File) is reached
            if (readByte == -1) break;

            // Write the raw data to the console output stream
            System.out.write(readByte);
        }

        // Close the file input stream
        in.close();
    }
}
~~~

---


- **Creating `FileInputStream` Object:**
  - `FileInputStream in = new FileInputStream("c:/data/byte.txt");`
    - Attempts to create a `FileInputStream` object to read from "c:/data/byte.txt".
    - If the file does not exist, a `FileNotFoundException` is caught, and an error message is printed.

- **Read-Write Loop:**
  - `readByte = in.read();`
    - Reads raw data from the file input stream.
  - `System.out.write(readByte);`
    - Writes the raw data to the console output stream.

- **Closing the Input Stream:**
  - `in.close();`
    - Closes the `FileInputStream` to release resources.

---

#### Key Points:
- Demonstrates basic file read operations using `FileInputStream`.
- Reads and prints raw data from a specified file to the console.
- Properly handles `FileNotFoundException` to manage cases where the file does not exist.
- Ensures the input stream is closed after the operation is complete.

---
