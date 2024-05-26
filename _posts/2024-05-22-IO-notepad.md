---
layout: single
title:  "2024/05/22/ IO / NotepadApp"
---

# Notepad Application

This Java program demonstrates a simple notepad application using Swing components. It provides basic functionalities such as creating a new document, opening an existing document, saving the current document, and exiting the application.

---

#### Components:

1. **JFrame:**
   - The main window of the application, which houses the text area and the menu bar.

2. **JTextArea:**
   - A component for displaying and editing multi-line text.

3. **JMenuBar and JMenu:**
   - The menu bar contains the file menu with items for various file operations.

4. **JMenuItem:**
   - Individual menu items for actions such as new, open, save, and exit.

5. **JFileChooser:**
   - A dialog for selecting files to open or save.

---

#### Implementation Steps:

1. **Create NotepadApp Class:**
   - Extends `JFrame` and initializes the UI components.
   - Sets up the menu bar with the file menu and its items.
   - Configures the text area and adds it to a scroll pane.

2. **Handle Menu Actions:**
   - Implements `ActionListener` to handle actions for menu items.
   - Provides functionality for creating a new document, opening a file, saving a file, and exiting the application.

3. **File Operations:**
   - Uses `JFileChooser` to open and save files.
   - Reads from and writes to files using `BufferedReader` and `BufferedWriter`.

---

## Full Code 

**NotepadApp:**

```java

public class NotepadApp extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextArea textArea;
    private JMenuItem init, open, save, exit;
    private File file;
    private JFileChooser fileChooser;

    public NotepadApp(String title) {
        super(title);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("파일(F)");
        fileMenu.setMnemonic('F');

        init = new JMenuItem("새로 만들기(N)", 'N');
        open = new JMenuItem("열기(O)", 'O');
        save = new JMenuItem("저장(S)", 'S');
        exit = new JMenuItem("끝내기(X)", 'X');

        init.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));

        fileMenu.add(init);
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        textArea = new JTextArea();
        textArea.setFont(new Font("굴림체", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(textArea);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("c:/"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("textFile(*.txt)", "txt"));

        init.addActionListener(new NotepadActionEventHandle());
        open.addActionListener(new NotepadActionEventHandle());
        save.addActionListener(new NotepadActionEventHandle());
        exit.addActionListener(new NotepadActionEventHandle());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(450, 150, 1000, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new NotepadApp("No Title - Java Notepad");
    }

    public class NotepadActionEventHandle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object eventSource = e.getSource();

            if (eventSource == init) {
                textArea.setText("");
                setTitle("No Title - Java Notepad");
                file = null;
            } else if (eventSource == open) {
                int option = fileChooser.showOpenDialog(NotepadApp.this);

                if (option == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    setTitle(file.toString() + " - Java Notepad");

                    try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                        textArea.setText("");
                        String text;
                        while ((text = in.readLine()) != null) {
                            textArea.append(text + "\n");
                        }
                    } catch (FileNotFoundException exception) {
                        JOptionPane.showMessageDialog(NotepadApp.this, "No files.");
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(NotepadApp.this, "error: program error");
                    }
                }
            } else if (eventSource == save) {
                if (file == null) {
                    int option = fileChooser.showSaveDialog(NotepadApp.this);

                    if (option == JFileChooser.APPROVE_OPTION) {
                        file = fileChooser.getSelectedFile();
                        if (!file.toString().endsWith(".txt")) {
                            file = new File(file.toString() + ".txt");
                        }
                        setTitle(file.toString() + " - Java Notepad");

                        try (BufferedWriter out = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
                            String text = textArea.getText();
                            out.write(text);
                        } catch (IOException exception) {
                            JOptionPane.showMessageDialog(NotepadApp.this, "error: program error.");
                        }
                    }
                } else {
                    try (BufferedWriter out = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
                        String text = textArea.getText();
                        out.write(text);
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(NotepadApp.this, "error: program error");
                    }
                }
            } else if (eventSource == exit) {
                System.exit(0);
            }
        }
    }
}
```

---

#### Explanation:

- **Menu Bar and Items:**
  - Creates a menu bar and file menu with items for new, open, save, and exit operations.
  - Sets mnemonics and accelerators for keyboard shortcuts.

- **Text Area:**
  - Configures a `JTextArea` for text input and adds it to a scroll pane for better usability.

- **File Chooser:**
  - Configures `JFileChooser` for file operations, setting a default directory and adding a filter for text files.

- **ActionListener Implementation:**
  - Implements `ActionListener` to handle menu item actions.
  - Provides functionality for creating a new document, opening an existing file, saving the current document, and exiting the application.

- **Exception Handling:**
  - Handles exceptions related to file operations, such as `FileNotFoundException` and `IOException`, displaying appropriate error messages.

This program provides a basic notepad application with essential file operations, showcasing how to use Swing components and handle file I/O in Java.
