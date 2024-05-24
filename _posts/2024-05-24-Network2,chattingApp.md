---
layout: single
title:  "2024/05/24/ Network-(Chating App)"
---

# Chatting 
## Server


This application forwards messages received from connected clients to all other clients. It uses multithreading to handle each client's socket independently for input and output operations.

#### Field for Client Sockets
- A list to store `SocketThread` objects representing currently connected clients.

```java
private List<SocketThread> clientList;
```

#### Constructor
- Initializes the server and continuously waits for client connections.

```java
public ChattingServerApp() {
    ServerSocket chatingServer = null;
    clientList = new Vector<SocketThread>();

    try {
        chatingServer = new ServerSocket(5000);
        System.out.println("[Message] Chatting server is running...");
        /* What the Main Thread does:
			Create a socket using the accept method,
			Create a SocketThread object by passing the Socket containing information about the client's connection to the constructor parameter
			Add the SocketThread object containing the socket to the Vector object (List object) as an element
			Create a new thread using the SocketThread object (Thread object) to execute the instructions of the run() method
			*/
			// Create a socket and create a SocketThread object using the socket object

        while (true) {
            Socket socket = chatingServer.accept();
          // Create a SocketThread object by passing the Socket containing information about the client's connection to the constructor parameter
            SocketThread socketThread = new SocketThread(socket);
            clientList.add(socketThread);
            socketThread.start();
            System.out.println("[Connection Log] " + socket.getInetAddress().getHostAddress() + " has connected to the chat server.");
        }
    } catch (IOException e) {
        System.out.println("[Error Log] The server is not running properly.");
    }
}

#### sendMessage Method
- Broadcasts a message to all connected clients.

```java
public void sendMessage(String message) {
    for (SocketThread socketThread : clientList) {
      // Use the output stream stored in the out field of the SocketThread object to send the string (message)
			// => The method of the outer class can access the fields or methods of the object created in the inner class regardless of the access modifier
        socketThread.out.println(message);
    }
}
```

#### SocketThread Class
- Handles communication with individual clients using a separate thread.

```java
public class SocketThread extends Thread {
    private Socket socket;
  // Field to store the input stream for receiving messages sent from the client
    private BufferedReader in;
// Field to store the output stream for sending messages to the client
    private PrintWriter out;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // Variable to store the client's nickname
        String aliasName = "";
        try {
            // Expand the socket's input stream to an input stream that can receive a large amount of text data (string) using the socket's input stream
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Expand the socket's output stream to an output stream that can convert all data types to strings using the socket's output stream
            out = new PrintWriter(socket.getOutputStream());
            // Receive the nickname sent from the client and store it using the input stream
            aliasName = in.readLine();
            // Send the join message to all currently connected clients using the output stream
				    // => The method of the inner class can access the fields or methods of the outer class regardless of the access modifier
            sendMessage("[" + aliasName + "] has joined.");
            // Receive messages sent from the client and forward them to all clients currently connected to the server
				    // => Process repeatedly until the client terminates the server connection
				    // => If the client terminates the server connection, IOException occurs - the loop ends
            while (true) {
                sendMessage("[" + aliasName + "] " + in.readLine());
            }
        } catch (IOException e) {
            // Remove the SocketThread object representing the disconnected client from the clientList
            clientList.remove(this);
            // Notify all connected clients about the departing client using the sendMessage method
            sendMessage("[" + aliasName + "] has left.");
            System.out.println("[Disconnection Log] " + socket.getInetAddress().getHostAddress() + " has disconnected from the chat server.");
        }
    }
}
```

### Full Code

```java


public class ChattingServerApp {
    private List<SocketThread> clientList;

    public ChattingServerApp() {
        ServerSocket chatingServer = null;
        clientList = new Vector<SocketThread>();

        try {
            chatingServer = new ServerSocket(5000);
            System.out.println("[Message] Chatting server is running...");

            while (true) {
                Socket socket = chatingServer.accept();
                SocketThread socketThread = new SocketThread(socket);
                clientList.add(socketThread);
                socketThread.start();
                System.out.println("[Connection Log] " + socket.getInetAddress().getHostAddress() + " has connected to the chat server.");
            }
        } catch (IOException e) {
            System.out.println("[Error Log] The server is not running properly.");
        }
    }

    public static void main(String[] args) {
        new ChattingServerApp();
    }

    public void sendMessage(String message) {
        for (SocketThread socketThread : clientList) {
            socketThread.out.println(message);
        }
    }

    public class SocketThread extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public SocketThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String aliasName = "";
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
                aliasName = in.readLine();
                sendMessage("[" + aliasName + "] has joined.");

                while (true) {
                    sendMessage("[" + aliasName + "] " + in.readLine());
                }
            } catch (IOException e) {
                clientList.remove(this);
                sendMessage("[" + aliasName + "] has left.");
                System.out.println("[Disconnection Log] " + socket.getInetAddress().getHostAddress() + " has disconnected from the chat server.");
            }
        }
    }
}
```

