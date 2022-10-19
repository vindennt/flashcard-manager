package ui;

import model.Deck;

import java.util.ArrayList;
import java.util.Scanner;

// Flashcard reviewer application
public class FlashCardApp {
    private Scanner input;
    private ArrayList<Deck> deckList;

    // EFFECTS: runs flashcard application
    public FlashCardApp() {
        runFlashCardApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input as a command
    private void runFlashCardApp() {
        boolean proceed = true;
        String command = null;
        init();

        while (proceed) {
            displayHomeMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("x")) {
                proceed = false;
            } else {
                processHomeCommand(command);
            }
        }
        System.out.println("Exiting...");
    }

    // MODIFIES: this
    // EFFECTS: initializes decklist
    private void init() {
        deckList = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays home screen menu options
    private void displayHomeMenu() {
        System.out.println("\nHOME: Select command:");
        System.out.println("\tn -> new deck");
        System.out.println("\tc -> choose deck");
        System.out.println("\tx -> exit");
    }

    // !!!
    // EFFECTS: displays edit deck menu options
    private void displayEditMenu() {
        System.out.println("\nDECK EDITOR: Select command:");
        System.out.println("\tr -> review deck");
        System.out.println("\ta -> add flashcard");
        System.out.println("\te -> edit flashcard");
        System.out.println("\td -> delete flashcard");
        System.out.println("\tb -> back to home screen");
    }

    // MODIFIES: this
    // EFFECTS: processes user input command on homescreen
    private void processHomeCommand(String command) {
        if (command.equals("n")) {
            doAddNewDeck();
        } else if (command.equals("c")) {
            doChooseDeck();
        } else {
            System.out.println("Invalid command");
        }
    }

    // TODO
    // MODIFIES: this
    // EFFECTS: processes user input command on edit screen
    private void processEditCommand(String command) {
        if (command.equals("r")) {
            doReviewDeck();
        } else if (command.equals("a")) {
            doAddNewCard();
        } else if (command.equals("e")) {
            doEditCard();
        } else if (command.equals("d")) {
            doDeleteCard();
        } else {
            System.out.println("Invalid command");
        }
    }

    // TODO
    private void doDeleteCard() {
    }

    // TODO
    private void doEditCard() {
        
    }

    // TODO
    private void doAddNewCard() {
    }


    // TODO
    private void doReviewDeck() {
    }

    // TODO
    private void doChooseDeck() {
        boolean proceed = true;
        String command = null;
        init();

        while (proceed) {
            displayDeckList();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                proceed = false;
            } else {
                processEditCommand(command);
            }
        }
        System.out.println("Back to home screen...");
    }

    // TODO
    private void displayDeckList() {
    }


    // TODO
    private void doAddNewDeck() {
        
    }


}