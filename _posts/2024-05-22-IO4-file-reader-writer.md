---
layout: single
title:  "2024/05/22/ IO-04/ File Reader,Writer/ "
---

## File Writing 

This Java program reads **character** data from the keyboard and writes it to a file until the End Of File (EOF) signal (Ctrl+Z) is received.

---

#### Concepts:
- **InputStreamReader Class:** Used to create an input stream that reads character data from the keyboard.
  - **Constructor:** `InputStreamReader(InputStream in)` creates an `InputStreamReader` object using `System.in`.

- **FileWriter Class:** Used to create an output stream that writes **character** data to a file.
  - **Constructor:** `FileWriter(String name)` creates a `FileWriter` object with the specified file path.
  - **Constructor:** `FileWriter(String name, boolean append)` creates a `FileWriter` object with the specified file path and the option to append to the file if it exists.
  - **Automatic File Creation:** If the file does not exist, it will be created automatically without throwing a `FileNotFoundException`.

---

#### Program Flow:
1. **Initialize Input Stream:**
   - Create an `InputStreamReader` object using `System.in` to read **character** data from the keyboard.

2. **Initialize Output Stream:**
   - Create a `FileWriter` object for the file path "c:/data/char.txt".
   - Use the constructor with the `append` parameter set to `true` to append new data to the existing file content.

3. **Read-Write Loop:**
   - Continuously read characters from the keyboard.
   - Write each character to the file until the EOF signal (Ctrl+Z) is received.

4. **Close Output Stream:**
   - Close the `FileWriter` to release resources and ensure all data is written to the file.

---

## Full Code:

~~~
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileWriterApp {
    public static void main(String[] args) throws IOException {
        System.out.println("[Message] Please enter text using the keyboard. [End program: Ctrl+Z]");

        // Create an InputStreamReader to read character data from the keyboard
        InputStreamReader in = new InputStreamReader(System.in);

        // Create a FileWriter to write character data to "c:/data/char.txt" and append new data
        FileWriter out = new FileWriter("c:/data/char.txt", true);

        int readByte;
        while (true) {
            // Read character data from the keyboard input stream
            readByte = in.read();
            if (readByte == -1) break;
            // Write character data to the file output stream
            out.write(readByte);
        }

        // Close the FileWriter to release resources
        out.close();

        System.out.println("[Result] Check the file c:\\data\\char.txt.");
    }
}
~~~

---

- **Creating `InputStreamReader` Object:**
  - `InputStreamReader in = new InputStreamReader(System.in);`
    - Creates an `InputStreamReader` object to read from the keyboard.

- **Creating `FileWriter` Object:**
  - `FileWriter out = new FileWriter("c:/data/char.txt", true);`
    - Creates a `FileWriter` object to write to "c:/data/char.txt" and appends data if the file exists.

- **Read-Write Loop:**
  - `readByte = in.read();`
    - Reads character data from the keyboard input stream.
  - `out.write(readByte);`
    - Writes the character data to the file output stream.

- **Closing the Output Stream:**
  - `out.close();`
    - Closes the `FileWriter` to release resources and ensure all data is written to the file.

---

#### Key Points:
- Demonstrates basic file write operations using `FileWriter`.
- Reads and writes character data from the keyboard to a specified file.
- Handles file creation and appending to existing files.
- Ensures the output stream is closed after the operation is complete.

---

#### Notes:
- This program handles character data input from the keyboard. For more advanced text processing or error handling, consider using higher-level classes like `BufferedWriter` or `PrintWriter`.
- Proper error handling should be implemented for robust applications, including handling `IOException` and ensuring resources are released in a `finally` block.

---

## File Reader 

This Java program reads **character** data from a file and prints it to the console.

---

#### Concepts:
- **FileReader Class:** Used to create an input stream to read **character** data from a file.
  - **Constructor:** `FileReader(String name)` creates a `FileReader` object with the specified file path.
  - **Exception Handling:** If the specified file does not exist, a `FileNotFoundException` is thrown and must be handled.

- **OutputStreamWriter Class:** Used to create an output stream to write **character** data to the console.
  - **Constructor:** `OutputStreamWriter(OutputStream out)` creates an `OutputStreamWriter` object with the specified output stream.

---

#### Program Flow:
1. **Initialize Input Stream:**
   - Create a `FileReader` object for the file path "c:/data/char.txt".
   - Handle `FileNotFoundException` to notify the user if the file does not exist.

2. **Initialize Output Stream:**
   - Create an `OutputStreamWriter` object using `System.out` to write **character** data to the console.

3. **Read-Write Loop:**
   - Continuously read characters from the file.
   - Write each character to the console until the End Of File (EOF) signal is reached.

4. **Close Input Stream:**
   - Close the `FileReader` to release resources.

---

## Full Code:

~~~
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileReaderApp {
    public static void main(String[] args) throws IOException {
        // Create a FileReader object to read character data from "c:/data/char.txt"
        FileReader in = null;
        try {
            in = new FileReader("c:/data/char.txt");
        } catch (FileNotFoundException e) {
            System.out.println("[Error] Cannot find the file c:/data/char.txt.");
            System.exit(0);
        }

        // Create an OutputStreamWriter object to write character data to the console
        OutputStreamWriter out = new OutputStreamWriter(System.out);

        System.out.println("[Message] Contents of the file c:/data/char.txt:");

        int readByte;

        while (true) {
            // Read character data from the file input stream and store it in the variable
            readByte = in.read();

            // Break the loop if EOF (End Of File) is reached
            if (readByte == -1) break;

            // Write the character data to the console output stream
            out.write(readByte);
            out.flush();
        }

        // Close the FileReader to release resources
        in.close();
    }
}
~~~

---

- **Creating `FileReader` Object:**
  - `FileReader in = new FileReader("c:/data/char.txt");`
    - Attempts to create a `FileReader` object to read from "c:/data/char.txt".
    - If the file does not exist, a `FileNotFoundException` is caught, and an error message is printed.

- **Creating `OutputStreamWriter` Object:**
  - `OutputStreamWriter out = new OutputStreamWriter(System.out);`
    - Creates an `OutputStreamWriter` object to write character data to the console.

- **Read-Write Loop:**
  - `readByte = in.read();`
    - Reads character data from the file input stream.
  - `out.write(readByte);`
    - Writes the character data to the console output stream.
  - `out.flush();`
    - Flushes the character data to ensure it is immediately written to the console.

- **Closing the Input Stream:**
  - `in.close();`
    - Closes the `FileReader` to release resources.

---

#### Key Points:
- Demonstrates basic file read operations using `FileReader`.
- Reads and prints character data from a specified file to the console.
- Properly handles `FileNotFoundException` to manage cases where the file does not exist.
- Ensures the input stream is closed after the operation is complete.

---

### Notes:
- For more advanced text processing, consider using higher-level classes like `BufferedReader` or `Scanner`.
  
