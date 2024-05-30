---
layout: single
title: 2024/05/22/ IO-06 - Data In,OutputSteam /
---

# DataInputStream 

This Java program demonstrates how to read different types of data from a file using the `DataInputStream` class. The program reads an integer, a boolean, and a string from a file (`c:/data/data.txt`) and prints them to the console.

#### Concepts:
- **DataInputStream Class:** Used to create an input stream that can read data in various primitive types from an underlying input stream.
  - **Constructor:** `DataInputStream(InputStream in)` creates a `DataInputStream` object.
  - **Functionality:** Allows reading data of specific types (int, boolean, string, etc.) from the input stream.

- **FileInputStream Class:** Provides a stream to read data from a file.
  - **Constructor:** `FileInputStream(String name)` creates a `FileInputStream` object for the specified file.

---

#### Program Flow:
1. **Initialize Input Stream:**
   - Create a `DataInputStream` object to read different types of data from the file `c:/data/data.txt`.
   - Use `FileInputStream` to provide the raw byte data.

2. **Read Data in Order:**
   - Read an integer, a boolean, and a UTF-encoded string from the file in the same order they were written.

3. **Print Data:**
   - Print the read values to the console.

4. **Close Stream:**
   - Close the `DataInputStream` to release resources.

---

## Full Code:

```java

public class DataInputStreamApp {
    public static void main(String[] args) throws IOException {
        // Initialize DataInputStream to read various data types from the file
        DataInputStream in = new DataInputStream(new FileInputStream("c:/data/data.txt"));

        // Read data from the stream in the same order they were written
        // DataInputStream.readInt() reads an integer from the stream
        int value1 = in.readInt();

        // DataInputStream.readBoolean() reads a boolean from the stream
        boolean value2 = in.readBoolean();

        // DataInputStream.readUTF() reads a string (UTF-8 encoded) from the stream
        String value3 = in.readUTF();

        // Print the values to the console
        System.out.println("value1 = " + value1);
        System.out.println("value2 = " + value2);
        System.out.println("value3 = " + value3);

        // Close the DataInputStream
        in.close();
    }
}
```

---


- **Creating DataInputStream:**
  - `DataInputStream in = new DataInputStream(new FileInputStream("c:/data/data.txt"));`
    - Wraps a `FileInputStream` to read various data types from the specified file.

- **Reading Data:**
  - `int value1 = in.readInt();`
    - Reads an integer from the file.
  - `boolean value2 = in.readBoolean();`
    - Reads a boolean from the file.
  - `String value3 = in.readUTF();`
    - Reads a **UTF-encoded** string from the file.

- **Printing Data:**
  - `System.out.println("value1 = " + value1);`
    - Prints the integer value.
  - `System.out.println("value2 = " + value2);`
    - Prints the boolean value.
  - `System.out.println("value3 = " + value3);`
    - Prints the string value.

- **Closing Stream:**
  - `in.close();`
    - Closes the `DataInputStream` to free resources.

---

#### Key Points:
- Demonstrates how to read various types of data from a file using `DataInputStream`.
- Ensures data is read in the same order it was written to avoid errors.
- Properly closes the input stream to release system resources.

# DataOutputStream 


This Java program demonstrates how to write different types of data to a file using the `DataOutputStream` class. The program writes an integer, a boolean, and a string to a file (`c:/data/data.txt`).

#### Key Concepts:
- **DataOutputStream Class:** Used to create an output stream that can write data in various primitive types to an underlying output stream.
  - **Constructor:** `DataOutputStream(OutputStream out)` creates a `DataOutputStream` object.
  - **Functionality:** Allows writing data of specific types (int, boolean, string, etc.) to the output stream.

- **FileOutputStream Class:** Provides a stream to write data to a file.
  - **Constructor:** `FileOutputStream(String name)` creates a `FileOutputStream` object for the specified file.

---

#### Program Flow:
1. **Initialize Output Stream:**
   - Create a `DataOutputStream` object to write different types of data to the file `c:/data/data.txt`.
   - Use `FileOutputStream` to provide the raw byte data.

2. **Write Data:**
   - Write an integer, a boolean, and a UTF-encoded string to the file using the `DataOutputStream`.

3. **Close Stream:**
   - Close the `DataOutputStream` to release resources.

4. **Confirm Completion:**
   - Print a message indicating that the data has been successfully written to the file.

---

#### Code:

```java
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataOutputStreamApp {
    public static void main(String[] args) throws IOException {
        // Initialize DataOutputStream to write various data types to the file
        DataOutputStream out = new DataOutputStream(new FileOutputStream("c:/data/data.txt"));

        // Write an integer to the output stream
        out.writeInt(100);

        // Write a boolean to the output stream
        out.writeBoolean(true);

        // Write a UTF-encoded string to the output stream
        out.writeUTF("Hong Gil-dong");

        // Close the DataOutputStream
        out.close();

        // Confirm completion
        System.out.println("Check the c:/data/data.txt file.");
    }
}
```

---

#### Explanation:
- **Creating DataOutputStream:**
  - `DataOutputStream out = new DataOutputStream(new FileOutputStream("c:/data/data.txt"));`
    - Wraps a `FileOutputStream` to write various data types to the specified file.

- **Writing Data:**
  - `out.writeInt(100);`
    - Writes an integer value to the file.
  - `out.writeBoolean(true);`
    - Writes a boolean value to the file.
  - `out.writeUTF("Hong Gil-dong");`
    - Writes a UTF-encoded string to the file.

- **Closing Stream:**
  - `out.close();`
    - Closes the `DataOutputStream` to free resources.

- **Completion Message:**
  - `System.out.println("Check the c:/data/data.txt file.");`
    - Prints a message to the console indicating that the data has been written to the file.

---

#### Key Points:
- Demonstrates how to write various types of data to a file using `DataOutputStream`.
- Ensures data is written in a specific order to be read correctly later.
- Properly closes the output stream to release system resources.
