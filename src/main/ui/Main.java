package ui;

import java.io.FileNotFoundException;

// Run flashcard application in console mode
public class Main {
    public static void main(String[] args) {
        try {
            new FlashCardApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run: file not found");
        }
    }
}
