package ui;

public class FlashCardApp {
    public FlashCardApp() {
        runFlashCardApp();
    }

    private void runFlashCardApp() {
        boolean proceed = true;
        String command = null;

        init();

        while (proceed) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                proceed = false;
            } else {
                processCommand(command);
            }
        }
    }
}