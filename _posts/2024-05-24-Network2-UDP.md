---
layout: single
title:  "2024/05/24/ Network-02 , UDP, "
---

# UDP Network
Definition: A connectionless protocol for faster, but less reliable, data transfer.
Characteristics: Establishes a 1:N connection for high-speed communication.

## server/Client UDP Example - MessageSendApp, MessageReceiveApp

## Server
This program listens for incoming messages from another computer using UDP. 

#### DatagramSocket Class
- Creates an object for receiving packets on a specified port.
- The constructor with a port number is used to bind the socket to the port for listening.

```java
DatagramSocket datagramSocket = new DatagramSocket(4000);
```

#### Byte Array for Data Storage
- A byte array is created to store the data received in the packet.

```java
byte[] data = new byte[1024];
```

#### DatagramPacket Class
- Creates a packet object to store incoming data.
- The constructor takes a byte array and its length.

```java
DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
```

#### Receiving the Packet
- Uses the `receive` method of `DatagramSocket` to receive the packet and store it in the `DatagramPacket` object.
- If no packet is received, the thread is blocked until a packet arrives.

```java
datagramSocket.receive(datagramPacket);
```

#### Converting Byte Array to String
- Converts the received byte array to a string to retrieve the message.

```java
String message = new String(data);
```

#### Closing the DatagramSocket
- Closes the `DatagramSocket` object to release resources.

```java
datagramSocket.close();
```

### Full Code

```java
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MessageReceiveApp {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(4000);
        byte[] data = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
        
        datagramSocket.receive(datagramPacket);
        
        String message = new String(data);
        System.out.println("[Result] Received message: " + message);
        
        datagramSocket.close();
    }
}
```

### Key Point
It binds a `DatagramSocket` to port 4000, receives a packet, extracts the message from the byte array, prints the message, and then closes the `DatagramSocket` to release resources.


## Client
- This program sends a user-input message from one computer to another over a network using UDP.
### BufferedReader for Input
- Used to read input from the keyboard.
- Expands `System.in` to handle a large amount of text data.

```java
BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter message to send >> ");
String message = in.readLine();
```

#### DatagramSocket Class
- Creates an object for sending or receiving packets.
- No-argument constructor is used for sending packets.

```java
DatagramSocket datagramSocket = new DatagramSocket();
```

#### InetAddress Class
- Retrieves an `InetAddress` object representing the IP address of the destination computer.

```java
InetAddress inetAddress = InetAddress.getByName("192.168.13.31");
```

#### Converting Message to Byte Array
- Converts the input message to a byte array, which is necessary for packet transmission.
- Can't just send byte Array
```java
byte[] data = message.getBytes();
```

#### DatagramPacket Class
- Creates a packet object that contains the message, its length, the destination address, and the port number.
- Used to send or receive data packets.

```java
DatagramPacket datagramPacket = new DatagramPacket(data, data.length, inetAddress, 4000);
```

#### Sending the Packet
- Uses the `send` method of `DatagramSocket` to send the packet.

```java
datagramSocket.send(datagramPacket);
```

#### Closing the DatagramSocket
- Closes the `DatagramSocket` object to release resources.

```java
datagramSocket.close();
```

### Full Code

```java

public class MessageSendApp {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter message to send >> ");
        String message = in.readLine();

        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("192.168.13.31");
        byte[] data = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, inetAddress, 4000);

        datagramSocket.send(datagramPacket);
        datagramSocket.close();

        System.out.println("[Message] Sent message to the connected computer.");
    }
}
```
### Key Point
The message is read from the keyboard, converted to a byte array, and sent as a packet using `DatagramSocket` and `DatagramPacket`. After sending the message, the `DatagramSocket` is closed to release resources.


 
