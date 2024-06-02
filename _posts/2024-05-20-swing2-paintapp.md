---
layout: single
title: 2024/05/20/ Swing-02 - PaintApp
---
# PaintApp
## Mouse Click Coordinates Display Program

This Java program demonstrates how to capture mouse click events on a JFrame and display the coordinates of the click as a string on the frame.

---

#### Concepts:
- **MouseEvent Class:** Used to represent mouse events such as clicks, presses, and releases.
- **MouseAdapter Class:** A convenience class for receiving mouse events. This class provides empty implementations for all methods in the MouseListener and MouseMotionListener interfaces, allowing you to override only the methods you need.

---

#### Program Flow:
1. **Initialize JFrame:**
   - Create a JFrame and set its properties.
2. **Register Mouse Listener:**
   - Add a MouseListener to capture mouse click events.
3. **Handle Mouse Click Events:**
   - Override the `mouseClicked` method to capture the click coordinates.
   - Store the coordinates in fields.
   - Call `repaint` to refresh the frame.
4. **Override `paint` Method:**
   - Override the `paint` method to draw the click coordinates on the frame.

---

#### Full Code:

```java

public class PaintApp extends JFrame {
    private static final long serialVersionUID = 1L;

    // Fields to store the coordinates of the mouse click
    private int x, y;
    
    public PaintApp(String title) {
        super(title);
        
        // Register a MouseAdapter to handle mouse click events
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the X and Y coordinates of the mouse click
                x = e.getX();
                y = e.getY();
                
                // Call repaint to refresh the frame and trigger the paint method
                repaint();
            }
        });
        
        // Set default close operation and frame properties
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(800, 200, 300, 400);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new PaintApp("Paint Method");
    }
    
    // Override the paint method to draw the coordinates on the frame
    @Override
    public void paint(Graphics g) {
        // Call the superclass's paint method to ensure proper painting
        super.paint(g);
        
        // Draw the coordinates as a string at the click location
        g.drawString("[" + x + ", " + y + "]", x, y);
    }
}
```

---

#### Key Points:
- **Mouse Listener Registration:**
  - `addMouseListener(new MouseAdapter() {...})`: Registers a MouseAdapter to handle mouse events.
- **Handling Mouse Click Events:**
  - `mouseClicked(MouseEvent e)`: Captures the coordinates of the mouse click and stores them in fields.
  - `repaint()`: Calls the `paint` method to update the frame.
- **Drawing on the Frame:**
  - `paint(Graphics g)`: Overrides the paint method to draw the coordinates on the frame.
  - `super.paint(g)`: Ensures that the superclass's paint method is called to properly paint the frame.

---
