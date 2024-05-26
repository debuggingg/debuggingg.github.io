---
layout: single
title:  "2024/05/24/ Network-(Chating App)"
---

# Chatting 
## Server

This application forwards messages received from connected clients to all other clients. It uses multithreading to handle each client's socket independently for input and output operations.

#### Field for Client Sockets
- A list to store `SocketThread` objects representing currently connected clients.

```
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

## Full Code

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
## Client

This section outlines the development of a chatting client application.

 ### NO  explanation: how to make JFrame 

### Sending Messages Entered via the JTextField Component to the Server - Event Handling (EventQueue Thread)

- **Description**: This part handles the event where messages entered in the JTextField component are sent to the server. It utilizes an event handling mechanism (EventQueue thread) to accomplish this task.
    -The statement textField.addActionListener(new ActionListener() {...}) in the context of the EventThread creates a thread only when an event occurs, and sends the message through the out object
```java
textField.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Retrieve the message entered in the JTextField component
        String message = textField.getText();

        // Send the message to the server using the output stream
        out.println(message);

        // Clear the JTextField component after sending the message
        textField.setText("");
    }
});
```

---

### Receiving Messages Sent from the Server and Displaying Them in the JTextArea Component - Continuous Loop (Main Thread)

- **Description**: This segment is responsible for receiving messages sent from the server and displaying them in the JTextArea component. It operates within an infinite loop (main thread) to continuously listen for incoming messages.

```java
// Continuously receive messages from the server and display them in the JTextArea component
while (true) {
    try {
        // Read the incoming message from the server
        String receivedMessage = in.readLine();

        // Append the received message to the JTextArea component
        textArea.append(receivedMessage + "\n");

        // Scroll the JTextArea component to display the latest messages
        textArea.setCaretPosition(textArea.getText().length());
    } catch (IOException e) {
        // Handle network-related issues if the server connection is terminated
        JOptionPane.showMessageDialog(this, "The connection to the server has been terminated.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
}
```

---

### Sending Messages Entered via the JTextField Component to the Server - Event Handling (EventQueue Thread)

- **Description**: This section repeats the process of sending messages entered via the JTextField component to the server. It utilizes an event handling mechanism (EventQueue thread) similar to the previous part.

```java
textField.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Retrieve the message entered in the JTextField component
        String message = textField.getText();

        // Send the message to the server using the output stream
        out.println(message);

        // Clear the JTextField component after sending the message
        textField.setText("");
    }
});
```
---
## Full Code

```java

public class ChattingClientApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;
    private JTextField textField;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String aliasName;

    public ChattingClientApp(String title) {
        super(title);
        textArea = new JTextArea();
        textField = new JTextField();
        textArea.setFont(new Font("굴림체", Font.BOLD, 20));
        textField.setFont(new Font("굴림체", Font.BOLD, 20));
        textArea.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(textField, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(700, 200, 400, 500);
        setVisible(true);
        // Add an ActionListener object to handle ActionEvents that occur in the JTextField component
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                if (!message.equals("")) {
                    out.println(message);
                    textField.setText("");
                }
            }
        });
        // Create a Socket object and store it in the field - Connect to the server
        try {
            socket = new Socket("192.168.13.31", 5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to connect to the server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        while (true) {
             // Use an input dialog to enter the nickname and store it
            aliasName = JOptionPane.showInputDialog(this, "Enter your nickname.", "Nickname Entry", JOptionPane.QUESTION_MESSAGE);
           // Regular expression to ensure the nickname is between 2 and 6 Korean characters
            String regEx = "^[가-힣]{2,6}$";
            if (aliasName != null && Pattern.matches(regEx, aliasName)) break;
            JOptionPane.showMessageDialog(this, "Please enter a nickname between 2 to 6 characters in length using Korean.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
         // Send the entered nickname to the server using the output stream
        out.println(aliasName);
         // Receive messages sent from the server using the input stream and display them in the JTextArea component
        // => Process repeatedly until the client program exits
        while (true) {
            try {
                 // Use the input stream to receive messages sent from the server and append them to the JTextArea component
                textArea.append(in.readLine() + "\n");
                textArea.setCaretPosition(textArea.getText().length());
            // Handle IOExceptions that occur due to network issues or server disconnection
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "The connection to the server has been terminated.", "Connection Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        new ChattingClientApp("Java Chat");
    }
}
```


