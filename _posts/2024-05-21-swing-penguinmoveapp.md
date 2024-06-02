---
layout: single
title: 2024/05/21/ Swing -  PenguinMoveApp
---
### Penguin Movement Program

This Java program demonstrates how to move a penguin image within a JFrame using keyboard arrow keys. The program captures keyboard events to update the position of the penguin image and redraws it accordingly.

---

#### Concepts:
- **JFrame Class:** Used to create the main window for the GUI.
- **Image Class:** Represents images in various formats (JPEG, PNG, GIF, etc.).
- **KeyEvent Class:** Used to capture keyboard events.
- **KeyAdapter Class:** Provides default implementations for the methods in the KeyListener interface, allowing you to override only the methods you need.

---

#### Program Flow:
1. **Initialize JFrame:**
   - Set up the main window properties such as size, position, and close operation.
2. **Load Images:**
   - Load background and penguin images using `ImageIcon`.
3. **Set Initial Position:**
   - Set the initial position of the penguin image at the bottom center of the JFrame.
4. **Register Key Listener:**
   - Add a KeyListener to capture keyboard events and update the penguin's position.
5. **Handle Keyboard Events:**
   - Override the `keyPressed` method to move the penguin left or right based on the arrow key pressed.
   - Ensure the penguin does not move outside the JFrame boundaries.
6. **Override `paint` Method:**
   - Override the `paint` method to draw the background and penguin images on the JFrame.

---

#### Full Code:

```java


public class PenguinMoveApp extends JFrame {
    private static final long serialVersionUID = 1L;

    // Constants for JFrame dimensions
    private static final int JFRAME_WIDTH = 646;
    private static final int JFRAME_HEIGHT = 461;

    // Constant for penguin image size
    private static final int PENGUIN_SIZE = 50;

    // Field to store background image
    private Image backImage;

    // Array to store penguin images
    private Image[] penguins;

    // Field to select the current penguin image
    private int penguinNo;

    // Fields to store the coordinates where the penguin image will be drawn
    private int penguinX, penguinY;

    public PenguinMoveApp(String title) {
        super(title);

        // Load background image
        backImage = new ImageIcon(getClass().getResource("/images/back.jpg")).getImage();

        // Load penguin images into the array
        penguins = new Image[3];
        for (int i = 0; i < penguins.length; i++) {
            penguins[i] = new ImageIcon(getClass().getResource("/images/penguin" + (i + 1) + ".gif")).getImage();
        }

        // Set initial penguin image index
        penguinNo = 0;

        // Set initial penguin position to the bottom center of the JFrame
        penguinX = JFRAME_WIDTH / 2 - PENGUIN_SIZE / 2;
        penguinY = JFRAME_HEIGHT - PENGUIN_SIZE;

        // Register a KeyAdapter to handle key events
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Move the penguin based on the key pressed
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        penguinX -= 10;
                        if (penguinX <= 0) {
                            penguinX = 0;
                        }
                        penguinNo = (penguinNo + 1) % 3;
                        repaint();
                        break;

                    case KeyEvent.VK_RIGHT:
                        penguinX += 10;
                        if (penguinX >= JFRAME_WIDTH - PENGUIN_SIZE) {
                            penguinX = JFRAME_WIDTH - PENGUIN_SIZE;
                        }
                        penguinNo = (penguinNo + 1) % 3;
                        repaint();
                        break;
                }
            }
        });

        // Set JFrame properties
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(700, 200, JFRAME_WIDTH, JFRAME_HEIGHT);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PenguinMoveApp("Penguin Movement");
    }

    @Override
    public void paint(Graphics g) {
        // Draw background image
        g.drawImage(backImage, 0, 0, JFRAME_WIDTH, JFRAME_HEIGHT, this);

        // Draw penguin image at the specified coordinates
        g.drawImage(penguins[penguinNo], penguinX, penguinY, PENGUIN_SIZE, PENGUIN_SIZE, this);
    }
}
```

---

#### Key Points:
- **Loading Images:**
  - `new ImageIcon(getClass().getResource("/images/back.jpg")).getImage()`: Loads an image from a resource file.
- **Setting Initial Positions:**
  - Centers the penguin image at the bottom of the JFrame.
- **Handling Keyboard Events:**
  - `keyPressed(KeyEvent e)`: Captures the key press event and updates the penguin's position.
  - `repaint()`: Calls the `paint` method to refresh the JFrame.
- **Drawing on the JFrame:**
  - `paint(Graphics g)`: Overrides the paint method to draw the background and penguin images.

---
This PenguinApp, as it is currently designed, only allows the penguin to move back and forth. Therefore, in the next step, we will create an app similar to a game where the penguin can avoid  like rocks and continue moving in a direction with a single keyboard input.
