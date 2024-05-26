---
layout: single
title:  "2024/05/22/ IO-07/ Object In,OutputSteam / "
---
serialiaztion -> explanation at bottom line-> 
# ObjectInputStream

This Java program demonstrates how to read objects from a file using the `ObjectInputStream` class. The program reads a string, a date, and a list of strings from a file (`c:/data/object.txt`) and prints them to the console.

---

#### Concepts:
- **ObjectInputStream Class:** Used to create an input stream that can read objects from an underlying input stream.
  - **Constructor:** `ObjectInputStream(InputStream in)` creates an `ObjectInputStream` object.
  - **Functionality:** Allows reading serialized objects from a file.

- **FileInputStream Class:** Provides a stream to read raw byte data from a file.
  - **Constructor:** `FileInputStream(String name)` creates a `FileInputStream` object for the specified file.

- **Deserialization(역직렬화):** The process of reading objects from a byte stream, which were previously serialized (written) to the stream.

---

#### Program Flow:
1. **Initialize Input Stream:**
   - Create an `ObjectInputStream` object to read objects from the file `c:/data/object.txt`.
   - Use `FileInputStream` to provide the raw byte data.

2. **Read Objects:**
   - Read objects from the input stream using `ObjectInputStream.readObject()`.
   - Cast the read objects to their appropriate types.

3. **Close Stream:**
   - Close the `ObjectInputStream` to release resources.

4. **Print Objects:**
   - Print the read objects to the console.

---

## Full Code:

~~~

public class ObjectInputStreamApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Initialize ObjectInputStream to read objects from the file
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("c:/data/object.txt"));

        // Read objects from the input stream and cast them to their appropriate types
        String object1 = (String) in.readObject();
        Date object2 = (Date) in.readObject();
        @SuppressWarnings("unchecked")
        List<String> object3 = (List<String>) in.readObject();

        // Print the read objects to the console
        System.out.println("object1 = " + object1);
        System.out.println("object2 = " + object2);
        System.out.println("object3 = " + object3);

        // Close the ObjectInputStream
        in.close();
    }
}
~~~

---

#### Explanation:
- **Creating ObjectInputStream:**
  - `ObjectInputStream in = new ObjectInputStream(new FileInputStream("c:/data/object.txt"));`
    - Wraps a `FileInputStream` to read serialized objects from the specified file.

- **Reading Objects:**
  - `String object1 = (String) in.readObject();`
    - Reads a string object from the file.
  - `Date object2 = (Date) in.readObject();`
    - Reads a date object from the file.
  - `@SuppressWarnings("unchecked") List<String> object3 = (List<String>) in.readObject();`
    - Reads a list of strings from the file.

- **Closing Stream:**
  - `in.close();`
    - Closes the `ObjectInputStream` to free resources.

- **Printing Objects:**
  - `System.out.println("object1 = " + object1);`
    - Prints the string object.
  - `System.out.println("object2 = " + object2);`
    - Prints the date object.
  - `System.out.println("object3 = " + object3);`
    - Prints the list of strings.

---

#### Key Points:
- Demonstrates how to read serialized objects from a file using `ObjectInputStream`.
- Ensures objects are read in the same order they were written to the file.
- Properly handles type casting and closes the input stream to release system resources.

- # ObjectOutputStream

**Purpose:**
This Java program demonstrates how to write objects to a file using the `ObjectOutputStream` class. The program serializes a string, a date, and a list of strings, and writes them to a file (`c:/data/object.txt`).

---

#### Concepts:
- **ObjectOutputStream Class:** Used to create an output stream that can write objects to an underlying output stream.
  - **Constructor:** `ObjectOutputStream(OutputStream out)` creates an `ObjectOutputStream` object.
  - **Functionality:** Allows writing serialized objects to a file.

- **FileOutputStream Class:** Provides a stream to write raw byte data to a file.
  - **Constructor:** `FileOutputStream(String name)` creates a `FileOutputStream` object for the specified file.

- **Serialization:** The process of converting an object into a byte stream, which can then be written to a file.

---

#### Program Flow:
1. **Initialize Output Stream:**
   - Create an `ObjectOutputStream` object to write objects to the file `c:/data/object.txt`.
   - Use `FileOutputStream` to handle the raw byte data.

2. **Write Objects:**
   - Write objects to the output stream using `ObjectOutputStream.writeObject(Object obj)`.

3. **Close Stream:**
   - Close the `ObjectOutputStream` to release resources.

4. **Print Confirmation:**
   - Print a confirmation message indicating the file path.

---

## Full Code:

```java


public class ObjectOutputStreamApp {
    public static void main(String[] args) throws IOException {
        // Initialize ObjectOutputStream to write objects to the file
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("c:/data/object.txt"));

        // Write objects to the output stream
        out.writeObject("홍길동"); // Write a String object
        out.writeObject(new Date()); // Write a Date object
        out.writeObject(List.of("임꺽정", "전우치", "일지매")); // Write a List object

        // Close the ObjectOutputStream
        out.close();

        // Print confirmation message
        System.out.println("Check the c:\\data\\object.txt file.");
    }
}
```

---
- **Creating ObjectOutputStream:**
  - `ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("c:/data/object.txt"));`
    - Wraps a `FileOutputStream` to write serialized objects to the specified file.

- **Writing Objects:**
  - `out.writeObject("홍길동");`
    - Writes a string object to the file.
  - `out.writeObject(new Date());`
    - Writes a date object to the file.
  - `out.writeObject(List.of("임꺽정", "전우치", "일지매"));`
    - Writes a list of strings to the file.

- **Closing Stream:**
  - `out.close();`
    - Closes the `ObjectOutputStream` to free resources.

- **Confirmation Message:**
  - `System.out.println("Check the c:\\data\\object.txt file.");`
    - Prints a message to indicate that the objects have been successfully written to the file.

---

#### Key Points:
- Demonstrates how to serialize objects and write them to a file using `ObjectOutputStream`.
- Ensures objects are written in the intended order for proper deserialization later.
- Properly handles closing the output stream to release system resources.

## serialiaztion
- What is serialiaztion: 직렬화(Serialization)이란 객체를 일련의 바이트 데이터로 변환하는 과정입니다. 마치 물건을 옮길 때 상자에 넣듯이 객체를 바이트 스트림이라는 가상의 상자에 넣는다고 생각하면 됩니다.
  - **Serialization** involves converting an object into a stream of bytes, allowing it to be stored in a file or transmitted over a network. This process involves encoding the object's state and structure into a format that can be later interpreted and reconstructed.
    - 직렬화 예:게임을 할 때 게임 진행 상황을 저장하는 기능.  이러한 저장 기능은 게임 데이터를 객체로 만들고 직렬화하여 파일에 저장하는 방식으로 구현됨. 나중에 게임을 다시 시작할 때는 저장된 파일을 읽어 직렬화된 객체를 역직렬화하여 게임 데이터를 복원하는 과정을 거침.
