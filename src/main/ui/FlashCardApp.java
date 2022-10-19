package ui;

import model.Deck;

import java.util.ArrayList;
import java.util.Scanner;

public class FlashCardApp {
    private Scanner input;
    private ArrayList<Deck> deckList;

    public FlashCardApp() {
        runFlashCardApp();
    }

    private void runFlashCardApp() {
        boolean proceed = true;
        String command = null;
        init();

        while (proceed) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("x")) {
                proceed = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Exiting...");
    }

    private void init() {
        deckList = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void displayMainMenu() {
        System.out.println("\nSelect command:");
        System.out.println("\tn -> new deck");
        System.out.println("\te -> edit deck");
        System.out.println("\tr -> review deck");
        System.out.println("\tx -> exit");
    }

    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddNewDeck();
        } else if (command.equals("e")) {
            doEditDeck();
        } else if (command.equals("r")) {
            doReviewDeck();
        } else {
            System.out.println("Invalid command");
        }
    }

    private void doReviewDeck() {
    }

    private void doEditDeck() {
        
    }

    private void doAddNewDeck() {
        
    }


}