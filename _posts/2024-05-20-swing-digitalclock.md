---
layout: single
title:  "2024/05/20/ Swing - DigitalClockApp"
---

# Digital Clock 

This Java program creates a digital clock using Swing, displaying the current date and time of the platform. The clock runs on a separate thread, updating every second. The user can pause and resume the clock using buttons.

---

#### Components:

1. **JFrame:**
   - The main window of the application that holds the components.

2. **JLabel:**
   - Displays the current date and time in a formatted manner.

3. **JButton:**
   - Two buttons, "Start" and "Stop," to control the clock's running state.

4. **Thread:**
   - Runs a separate thread to continuously update the time every second.

---

#### Implementation Steps:

1. **Create DigitalClockApp Class:**
   - Extends `JFrame` and initializes UI components including `JLabel` for displaying time and `JButton` for control.

2. **UI Components Initialization:**
   - `JLabel` is used to display the time, formatted with `SimpleDateFormat`.
   - `JButton` components are used to start and stop the clock.

3. **Thread Management:**
   - A new thread is created to update the time every second.
   - The thread's run method updates the `JLabel` with the current time, formatted as "yyyy년 MM월 dd일 HH시 mm분 ss초".

4. **Button Actions:**
   - Action listeners are added to buttons to handle start and stop events.
   - The `isRun` boolean field controls whether the clock updates.

5. **Frame Configuration:**
   - Sets default close operation, frame size, and visibility.

---

## Full Code 

```java


public class DigitalClockApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel clockLabel;
    private JButton startButton, stopButton;
    private boolean isRun;

    public DigitalClockApp(String title) {
        super(title);
        isRun = true;

        clockLabel = new JLabel("", JLabel.CENTER);
        clockLabel.setFont(new Font("굴림체", Font.BOLD, 30));

        startButton = new JButton("다시 실행");
        stopButton = new JButton("일시 중지");
        startButton.setFont(new Font("굴림체", Font.BOLD, 20));
        stopButton.setFont(new Font("굴림체", Font.BOLD, 20));

        startButton.setEnabled(false);

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(stopButton);

        new Thread(() -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
            while (true) {
                if (isRun) {
                    clockLabel.setText(dateFormat.format(new Date()));
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        getContentPane().add(clockLabel, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.SOUTH);

        startButton.addActionListener(new ClockButtonEventHandle());
        stopButton.addActionListener(new ClockButtonEventHandle());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(700, 200, 600, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DigitalClockApp("디지털 시계");
    }

    public class ClockButtonEventHandle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object eventSource = e.getSource();
            if (eventSource == startButton) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                isRun = true;
            } else if (eventSource == stopButton) {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                isRun = false;
            }
        }
    }
}
```

---

- **Clock Label:**
  - `JLabel` is used to display the current time, which updates every second.

- **Start and Stop Buttons:**
  - `JButton` components to control the clock. The `startButton` restarts the clock, and the `stopButton` pauses it.

- **Thread for Time Update:**
  - A thread runs in the background, updating the `JLabel` with the current time every second.

- **ActionListener Implementation:**
  - Handles button clicks to start and stop the clock by setting the `isRun` flag and enabling/disabling the buttons accordingly.

