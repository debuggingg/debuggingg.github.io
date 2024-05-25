---
layout: single
title:  "2024/05/22/ IO / Byte, Char"
---

# Java Byte Stream Application: Keyboard Input and Output

This program demonstrates how to read input from the keyboard using a byte stream and display it on the monitor until the End Of File (EOF) signal (Ctrl+Z) is received.

### Package
The program utilizes the `java.io` package, which provides classes for input and output operations.

### Concepts

- **Stream**: A stream represents a one-way flow of data. It is used to transfer values sequentially.
- **Byte Stream**: A byte stream handles raw, unprocessed data, transferring values one byte at a time.
- **EOF (End Of File)**: The EOF signal indicates the end of the data stream.

### Classes

- `InputStream`: An abstract class representing an input stream.
- `OutputStream`: An abstract class representing an output stream.
- `System.in`: A predefined input stream connected to the keyboard.
- `System.out`: A predefined output stream connected to the console.

## Program Flow

1. **Initialize**:
   - Display a message instructing the user to enter text.
   - Declare an `int` variable `readByte` to store the read byte value.

2. **Read-Write Loop**:
   - Enter the loop.
   - Use `System.in.read()` to read a byte from the keyboard and store it in `readByte`.
   - If `readByte` is -1 (EOF signal), break out of the loop.
   - Use `System.out.write(readByte)` to write the read byte to the monitor.

3. **Termination**:
   - Display a message indicating the program's termination.

## Key Points
- The program continuously reads and displays bytes until the EOF signal is received.
- The `System.in.read()` method blocks the thread until a byte is available or the EOF signal is received.
- The `System.out.write()` method writes the byte to the output stream as a raw byte value (1 byte).

## Additional Notes
- This program demonstrates basic byte stream operations. For more advanced input/output scenarios, consider using character streams or higher-level file operations.
- Handle potential `IOException` exceptions that may occur during input/output operations.

## Corresponding Code

~~~

public class ByteStreamApp {

    public static void main(String[] args) throws IOException {
        System.out.println("[Message] Please enter text using the keyboard.");

        int readByte;

        while (true) {
            readByte = System.in.read();

            if (readByte == -1) {
                break;
            }

            System.out.write(readByte);
        }

        System.out.println("[Message] Program terminating.");
    }
}
~~~


# Java Character Stream Application: Keyboard Input and Output

This program demonstrates how to read input from the keyboard using a character stream and display it on the monitor until the End Of File (EOF) signal (Ctrl+Z) is received. Character streams handle text data, converting raw bytes into character values.


### Concepts

- **Stream**: A stream represents a one-way flow of data. It is used to transfer values sequentially.
- **Character Stream**: A character stream handles text data, encoding raw bytes into character values (2 bytes per character).
- **EOF (End Of File)**: The EOF signal indicates the end of the data stream.

### Classes

- `Reader`: An abstract class representing a character input stream.
- `Writer`: An abstract class representing a character output stream.
- `System.in`: A predefined input stream connected to the keyboard.
- `System.out`: A predefined output stream connected to the console.

## Program Flow

1. **Initialize**:
   - Display a message instructing the user to enter text.
   - Create an `InputStreamReader` object to convert keyboard input to character data.
   - Create a `PrintWriter` object to write character data to the monitor.

2. **Read-Write Loop**:
   - Enter the loop.
   - Use `in.read()` to read a character from the keyboard and store it in `readByte`.
   - If `readByte` is -1 (EOF signal), break out of the loop.
   - Use `out.write(readByte)` to write the character to the monitor.
   - Use `out.flush()` to flush the character from the buffer to the monitor.

3. **Termination**:
   - Display a message indicating the program's termination.
   - Close the `InputStreamReader` and `PrintWriter` objects.

## Key Points
- The program continuously reads and displays characters until the EOF signal is received.
- The `in.read()` method blocks the thread until a character is available or the EOF signal is received.
- The `out.write()` method writes the character to the output stream as a character value (2 bytes).
- The `out.flush()` method ensures that the character is sent to the monitor immediately.

## Additional Notes
- This program demonstrates basic character stream operations. For more advanced text processing, consider using higher-level classes like `Scanner` and `FileReader`/`FileWriter`.
- Handle potential `IOException` exceptions that may occur during input/output operations.

## Corresponding Code

~~~

public class CharacterStreamApp {

    public static void main(String[] args) throws IOException {
        System.out.println("[Message] Please enter text using the keyboard.");

        InputStreamReader in = new InputStreamReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int readByte;

        while (true) {
            readByte = in.read();

            if (readByte == -1) {
                break;
            }

            out.write(readByte);
            out.flush();
        }

        System.out.println("[Message] Program terminating.");
        in.close();
        out.close();
    }
}

