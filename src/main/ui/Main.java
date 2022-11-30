package ui;

import java.io.FileNotFoundException;

// Runs the flashcard application
// throws FileNotFoundException when it cannot locate a file in the /data directory
public class Main {
    public static void main(String[] args) {
        try {
            new FlashCardAppUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run: file not found");
        }
    }
}
