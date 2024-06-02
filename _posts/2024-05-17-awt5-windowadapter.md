---
layout: single
title: 2024/5/17/ Awt-03 -WindowAdapter
---
# WindowAdapter

## Concepts:
- **Event Handling:** Dealing with various events that occur in components (containers) of a GUI program.
- **WindowAdapter Class:** A convenience class for receiving window events. This class provides empty implementations for all methods in the WindowListener interface, allowing you to override only the methods you need.

## Program Flow:
1. **Initialize Frame:**
   - Create a Frame and set its properties.
2. **Register Window Listener:**
   - Add a WindowListener to capture window events.
3. **Handle Window Closing Event:**
   - Override the `windowClosing` method of the WindowAdapter class to handle the window closing event.
   - Call `System.exit(0)` to exit the program when the window is closed.

## Full Code:

```java
import java.awt.*;
import java.awt.event.*;

public class WindowAdapterApp extends Frame {
    private static final long serialVersionUID = 1L;

    public WindowAdapterApp(String title) {
        super(title);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setBounds(800, 200, 300, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new WindowAdapterApp("WindowEvent");
    }
}
```

---
# WindowListener

## Concepts:
- **Event Handling:** Dealing with various events that occur in components (containers) of a GUI program.
- **WindowListener Interface:** Used to handle events related to the window, such as opening, closing, iconifying, etc.

## Program Flow:
1. **Initialize Frame:**
   - Create a Frame and set its properties.
2. **Register Window Listener:**
   - Add a WindowListener to capture window events.
3. **Handle Window Closing Event:**
   - Override the `windowClosing` method of the WindowListener interface to handle the window closing event.
   - Call `System.exit(0)` to exit the program when the window is closed.

## Full Code:

```java
import java.awt.*;
import java.awt.event.*;

public class WindowListenerApp extends Frame {
    private static final long serialVersionUID = 1L;

    public WindowListenerApp(String title) {
        super(title);

        addWindowListener(new WindowEventHandle());

        setBounds(800, 200, 300, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new WindowListenerApp("WindowEvent");
    }

    public class WindowEventHandle implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }

        @Override
        public void windowClosed(WindowEvent e) {}

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowDeactivated(WindowEvent e) {}
    }
}
```

## Key Points:

- **Window Closing Event Handling:** The `windowClosing` method of the WindowListener interface is overridden to handle the window closing event.
- **System Exit:** `System.exit(0)` is called to exit the program when the window is closed.