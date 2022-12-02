package ui;

// Represents behaviour for printing messages to JFrame
public interface MessageHandler {

    // EFFECTS: creates a message box with the message and window title
    void showMessageDialog(String message, String title);

    // EFFECTS: creates an error message box with the message and window title
    void showErrorDialog(Exception e, String title);
}
