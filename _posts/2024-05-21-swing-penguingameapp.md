---
layout: single
title: 2024/05/21/ Swing - PenguinGameApp
---
### PenguinGameApp - Penguin Movement and Obstacle Avoidance Game

This Java program creates a simple game where a penguin moves left and right to avoid falling stones. The game uses threads to handle penguin movement and stone generation, making it more interactive and game-like.

---

#### Concepts:

1. **JFrame Class:** The main container for the game, where all components are added and displayed.
2. **Image Class:** Used to handle the background and penguin images.
3. **Thread Class:** Used to create concurrent tasks for animation and game logic.
4. **KeyListener Interface:** Handles keyboard inputs to control the penguin's movement.
5. **Set Interface:** Used to store and manage the stones as they are generated and move.

---

####  Components and Flow:

1. **Initialization:**
   - Load background and penguin images.
   - Initialize game variables such as penguin position, direction, and game state.

2. **Key Event Handling:**
   - Use `KeyAdapter` to listen for key presses.
   - Left and right arrow keys control the penguin's movement.
   - 'P' key toggles the game pause state.
   - 'F5' key restarts the game if the penguin is dead.

3. **Game Logic and Threads:**
   - `PenguinAnimationThread`: Moves the penguin left or right based on the current direction.
   - `StoneThread`: Represents individual stones falling from the top of the frame.
   - `CreateStoneThread`: Generates new stones at regular intervals.

4. **Collision Detection:**
   - Stones check for collision with the penguin to determine if the game should end.

---

#### Full Code:

```java
public class PenguinGameApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int JFRAME_WIDTH = 646;
    private static final int JFRAME_HEIGHT = 461;
    private static final int PENGUIN_SIZE = 50;

    private Image backImage;
    private Image[] penguins;
    private int penguinNo;
    private int penguinX, penguinY;

    // To store penguin movement direction
    // 1: left, 2: right
    private int direction;

    // To store game run state
    // false: stopped, true: running
    private boolean isRun;

    // To store penguin alive state
    // false: dead, true: alive
    private boolean isPenguinAlive;

    // To store stone image size
    private static final int STONE_SIZE = 30;

    private Image stoneImage;
    private Set<StoneThread> stoneSet;

    public PenguinGameApp(String title) {
        super(title);

        backImage = new ImageIcon(getClass().getResource("/images/back.jpg")).getImage();
        penguins = new Image[3];
        for (int i = 0; i < penguins.length; i++) {
            penguins[i] = new ImageIcon(getClass().getResource("/images/penguin" + (i + 1) + ".gif")).getImage();
        }
        stoneImage = new ImageIcon(getClass().getResource("/images/stone.gif")).getImage();

        stoneSet = new HashSet<StoneThread>();

        init();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        direction = 1;
                        break;
                    case KeyEvent.VK_RIGHT:
                        direction = 2;
                        break;
                    case KeyEvent.VK_P:
                        // Toggle game run state
                        isRun = !isRun;
                        if (!isRun) repaint();
                        break;
                    case KeyEvent.VK_F5:
                        // Reinitialize game if penguin is dead
                        if (!isPenguinAlive) init();
                        break;
                }
            }
        });

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(700, 200, JFRAME_WIDTH, JFRAME_HEIGHT);
        setVisible(true);
    }

    // Method to initialize game state
    private void init() {
        penguinNo = 0;
        penguinX = JFRAME_WIDTH / 2 - PENGUIN_SIZE / 2;
        penguinY = JFRAME_HEIGHT - PENGUIN_SIZE;
        direction = 0;
        isRun = true;
        isPenguinAlive = true;

        new PenguinAnimationThread().start();
        new CreateStoneThread().start();
    }

    public static void main(String[] args) {
        new PenguinGameApp("Penguin Game");
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(backImage, 0, 0, JFRAME_WIDTH, JFRAME_HEIGHT, this);

        if (isPenguinAlive) {
            g.drawImage(penguins[penguinNo], penguinX, penguinY, PENGUIN_SIZE, PENGUIN_SIZE, this);

            if (!isRun) {
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.setColor(Color.RED);
                g.drawString("Paused", 200, 200);
            }
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 150, 200);
            g.drawString("Retry (F5)", 200, 300);
        }

        // Draw stones
        synchronized (stoneSet) {
            for (StoneThread stone : stoneSet) {
                g.drawImage(stoneImage, stone.stoneX, stone.stoneY, STONE_SIZE, STONE_SIZE, this);
            }
        }
    }

    // Thread to animate penguin movement
    public class PenguinAnimationThread extends Thread {
        @Override
        public void run() {
            while (isPenguinAlive) {
                if (isRun) {
                    switch (direction) {
                        case 1:
                            penguinX -= 5;
                            if (penguinX <= 0) penguinX = 0;
                            break;
                        case 2:
                            penguinX += 5;
                            if (penguinX >= JFRAME_WIDTH - PENGUIN_SIZE)
                                penguinX = JFRAME_WIDTH - PENGUIN_SIZE;
                            break;
                    }

                    penguinNo++;
                    penguinNo %= 3;
                    repaint();
                }

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Thread to manage individual stones
    public class StoneThread extends Thread {
        private int stoneX, stoneY;
        private boolean isStoneAlive;
        private int stoneSpeed;

        public StoneThread() {
            stoneX = new Random().nextInt(JFRAME_WIDTH - STONE_SIZE);
            stoneY = 0;
            isStoneAlive = true;
            stoneSpeed = new Random().nextInt(10) + 30;

            start();
        }

        @Override
        public void run() {
            while (isPenguinAlive && isStoneAlive) {
                if (isRun) {
                    stoneY += 5;
                    if (stoneY >= JFRAME_HEIGHT - STONE_SIZE) {
                        isStoneAlive = false;
                        synchronized (stoneSet) {
                            stoneSet.remove(this);
                        }
                    }

                    // Check for collision with penguin
                    if (stoneY + 20 >= penguinY) {
                        if (stoneX + 10 >= penguinX && stoneX + 10 <= penguinX + PENGUIN_SIZE
                                && stoneX + 20 >= penguinX && stoneX + 20 <= penguinX + PENGUIN_SIZE) {
                            isPenguinAlive = false;
                            synchronized (stoneSet) {
                                stoneSet.clear();
                            }
                            repaint();
                        }
                    }
                }

                try {
                    Thread.sleep(stoneSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Thread to generate new stones
    public class CreateStoneThread extends Thread {
        @Override
        public void run() {
            while (isPenguinAlive) {
                if (isRun) {
                    synchronized (stoneSet) {
                        stoneSet.add(new StoneThread());
                    }

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
```

---

#### Explanation of Main Components:

- **Penguin Movement:**
  - The `PenguinAnimationThread` class updates the penguin's position based on the current direction and triggers repainting.
  
- **Stone Generation and Movement:**
  - The `CreateStoneThread` class generates new stones at regular intervals.
  - The `StoneThread` class handles the movement of individual stones, checking for collisions with the penguin and removing stones that reach the bottom of the frame.

- **Game Control:**
  - The game can be paused or restarted using keyboard inputs.
  - The direction of the penguin is controlled by the left and right arrow keys.

