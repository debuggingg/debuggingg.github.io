---
layout: single
title:  "2024/05/22/ IO-05/ File Copy / "
---

# File Copy 

 This Java program copies the contents of a source file (`c:\data\bandizip.exe`) to a target file (`c:\data\bandizip_byte.exe`) using raw byte data. This allows copying any file format without modification.


#### Concepts:
- **BufferedInputStream Class:** Used to create an input stream that can read large amounts of raw byte data from a source file.
  - **Constructor:** `BufferedInputStream(InputStream in)` creates a `BufferedInputStream` object, expanding the input stream to handle large amounts of data.
  
- **BufferedOutputStream Class:** Used to create an output stream that can write large amounts of raw byte data to a target file.
  - **Constructor:** `BufferedOutputStream(OutputStream out)` creates a `BufferedOutputStream` object, expanding the output stream to handle large amounts of data.

- **NoReader,Writer** When copying a file using BufferedReader and Writer, the original binary data (like images, videos, or compressed files) is converted to character data. This conversion can lead to loss of information and a corrupted or unusable copy of the target file.

- **File I/O Streams:**
  - **FileInputStream:** Reads raw byte data from a file.
  - **FileOutputStream:** Writes raw byte data to a file.

---

#### Program Flow:
1. **Initialize Input Stream:**
   - Create a `BufferedInputStream` object to read raw byte data from the source file `c:/data/bandizip.exe`.
   - Use `FileInputStream` to provide the raw byte data.

2. **Initialize Output Stream:**
   - Create a `BufferedOutputStream` object to write raw byte data to the target file `c:/data/bandizip_byte.exe`.
   - Use `FileOutputStream` to handle the output.

3. **Read-Write Loop:**
   - Continuously read bytes from the source file.
   - Write each byte to the target file until the end of the file is reached (EOF).

4. **Close Streams:**
   - Close both the input and output streams to release resources.

---

## Full Code:

```java

public class FileCopyByteApp {

    public static void main(String[] args) throws IOException {
        // Initialize BufferedInputStream to read raw byte data from the source file
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream("c:/data/bandizip.exe"));
        } catch (FileNotFoundException e) {
            System.out.println("[Error] Source file not found.");
            System.exit(0);
        }

        // Initialize BufferedOutputStream to write raw byte data to the target file
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("c:/data/bandizip_byte.exe"));

        int readByte;
        while (true) {
            // Read bytes from the source file
            readByte = in.read();
            if (readByte == -1) break;
            // Write bytes to the target file
            out.write(readByte);
        }

        // Close input and output streams
        in.close();
        out.close();

        System.out.println("[Message] File successfully copied.");
    }
}
```


- **Creating BufferedInputStream:**
  - `BufferedInputStream in = new BufferedInputStream(new FileInputStream("c:/data/bandizip.exe"));`
    - Wraps a `FileInputStream` to read bytes from the source file.
  - Catches `FileNotFoundException` to handle cases where the source file does not exist.

- **Creating BufferedOutputStream:**
  - `BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("c:/data/bandizip_byte.exe"));`
    - Wraps a `FileOutputStream` to write bytes to the target file.

- **Read-Write Loop:**
  - `readByte = in.read();`
    - Reads a byte from the source file.
  - `out.write(readByte);`
    - Writes the byte to the target file.

- **Closing Streams:**
  - `in.close();`
    - Closes the input stream.
  - `out.close();`
    - Closes the output stream.

---

#### Key Points:
- Demonstrates file copy operations using `BufferedInputStream` and `BufferedOutputStream`.
- Copies raw byte data, making it suitable for any file format.
- Ensures proper resource management by closing streams after use.

---

#### Notes:
- This program handles basic file copy operations using raw byte data. For more complex scenarios, consider using higher-level APIs or libraries that provide additional error handling and performance optimizations.
- Proper error handling should be implemented for robust applications, including handling `IOException` and ensuring resources are released in a `finally` block.
