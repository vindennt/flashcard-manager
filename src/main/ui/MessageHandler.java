package ui;

public interface MessageHandler {
    void showMessageDialog(String message, String title);

    void showErrorDialog(Exception e, String title);
}
