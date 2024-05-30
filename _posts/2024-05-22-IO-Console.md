---
layout: single
title: 2024/05/22/ IO - ConsoleApp-Buffered
---

### Console I/O Application

**Purpose:**
This Java program demonstrates how to read a user's name and birth year from the keyboard, calculate their age, and display the results on the monitor.
---

#### Concepts:
- **Streams:** Represent a one-way flow of data. Used to transfer values sequentially.
- **Character Stream:** Handles text data, converting raw bytes into character values.
- **EOF (End Of File):** Indicates the end of the data stream.
- **BufferedReader:** Reads text from an input stream, buffering characters for efficient reading.
- **InputStreamReader:** Converts byte streams to character streams.
- **Calendar:** Provides methods for manipulating calendar fields and converting between time and calendar fields.

#### Key Points:

  - BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  - This line initializes a `BufferedReader` to read character data from the standard input stream (`System.in`).
    - The `BufferedReader.readLine()` method reads input from the keyboard as a string.
    
---

#### Program Flow:
1. **Initialization:**
   - Extend the `System.in` input stream to read character data using `InputStreamReader`.
   - Further extend it to read large amounts of character data using `BufferedReader`.

2. **Read and Process Input:**
   - Prompt the user to enter their name and birth year.
   - Read the entered values from the keyboard.

3. **Calculate Age:**
   - Calculate the user's age based on the current year and the entered birth year.

4. **Output Result:**
   - Display the user's name and calculated age on the monitor.

---

### Full Code:

```java

public class ConsoleIOApp {
    public static void main(String[] args) throws IOException {
        // Extend System.in to read character data
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        /*
        // Uncomment this section if you need to extend System.out for character data output
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        out.write("Enter your name >> ");
        out.flush();
        */

        // Prompt user to enter their name
        System.out.print("Enter your name >> ");
        String name = in.readLine();

        // Prompt user to enter their birth year
        System.out.print("Enter your birth year >> ");
        int birthYear = Integer.parseInt(in.readLine());

        // Calculate age
        int age = Calendar.getInstance().get(Calendar.YEAR) - birthYear;

        // Display result
        System.out.println("[Result] " + name + ", your age is " + age + " years.");
    }
}
```

---
