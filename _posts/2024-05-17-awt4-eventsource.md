---
layout: single
title: 2024/5/17/ Awt-04 - EventSource
---
#  Event Source

## Concepts:
- **Event Handling:** Dealing with various events that occur in components (containers) of a GUI program.
- **ActionListener Interface:** Used to handle action events, such as button clicks, in Java.
- **Event Source:** The component that triggers an event.

## Program Flow:
1. **Create UI Class with Buttons and Canvas:**
   - The UI class extends Frame and adds buttons for different colors and a canvas to display color changes.
2. **Register Event Listeners:**
   - Each color button is associated with the same event handling class.
   - When a color button is clicked, the actionPerformed method of the event handling class is executed, changing the canvas background color accordingly.
3. **Handle Event Source:**
   - In the event handling class, the getSource method is used to determine the event source (which button was clicked).
   - Based on the event source, the background color of the canvas is changed.

## Full Code:

```java
import java.awt.*;
import java.awt.event.*;

public class EventSourceGetApp extends Frame {
    private static final long serialVersionUID = 1L;

    private Canvas canvas;
    private Button red, green, blue, white;

    public EventSourceGetApp(String title) {
        super(title);

        red = new Button("RED");
        green = new Button("GREEN");
        blue = new Button("BLUE");
        white = new Button("WHITE");

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1, 4));
        panel.add(red);
        panel.add(green);
        panel.add(blue);
        panel.add(white);
        add(panel, BorderLayout.NORTH);

        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);

        red.addActionListener(new ColorButtonEventHandle());
        green.addActionListener(new ColorButtonEventHandle());
        blue.addActionListener(new ColorButtonEventHandle());
        white.addActionListener(new ColorButtonEventHandle());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setBounds(800, 200, 400, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EventSourceGetApp("Event Handling");
    }

    public class ColorButtonEventHandle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object eventSource = e.getSource();
            if (eventSource == red) {
                canvas.setBackground(Color.RED);
            } else if (eventSource == green) {
                canvas.setBackground(Color.GREEN);
            } else if (eventSource == blue) {
                canvas.setBackground(Color.BLUE);
            } else if (eventSource == white) {
                canvas.setBackground(Color.WHITE);
            }
        }
    }
}
```
## Key Points:

- **Event Source Detection:** The getSource method of the ActionEvent class is used to identify which button triggered the event.
- **Single Event Handling Class:** All color buttons are associated with the same event handling class, simplifying the code.
- **Conditional Handling:** Based on the event source, the appropriate action is taken to change the background color of the canvas.
