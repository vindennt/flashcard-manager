package ui;

import model.Deck;
import model.FlashCard;

import java.util.ArrayList;
import java.util.Scanner;

// Flashcard reviewer application
public class FlashCardApp {
    private Scanner input;
    private ArrayList<Deck> deckList;
    private int lastChosenCardIndex;

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
        lastChosenCardIndex = 0;
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
    private void displayDeckMenu() {
        System.out.println("\nDECK: Select command:");
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
            if (deckList.size() == 0) {
                System.out.println("No decks available");
            } else {
                deckMenu();
            }
        } else {
            System.out.println("Invalid command");
        }
    }


    // MODIFIES: this  r a e d
    // EFFECTS: processes user input command on edit screen
    private void processDeckMenuCommand(String command, Deck chosenDeck) {
        if (command.equals("a")) {
            doAddNewCard(chosenDeck);
        } else if (chosenDeck.size() == 0) {
            System.out.println("Deck is empty");
        } else if (command.equals("r")) {
            doReviewDeck(chosenDeck);
        } else if (command.equals("e")) {
            doEditCard(chosenDeck);
        } else if (command.equals("d")) {
            doDeleteCard(chosenDeck);
        } else {
            System.out.println("Invalid command");
        }
    }

    // EFFECTS: returns a flashcard that the user creates
    private FlashCard doUserCreateCard() {
        String front;
        String back;
        FlashCard newCard;

        System.out.println("Card front?");
        front = input.next();
        System.out.println("Deck back?");
        back = input.next();
        newCard = new FlashCard(front, back);
        return newCard;
    }

    // MODIFIES: this
    // EFFECTS: adds flashcard with an inputted front and back to the chosen deck
    private void doAddNewCard(Deck chosenDeck) {
        FlashCard cardToAdd = doUserCreateCard();
        chosenDeck.addFlashCard(cardToAdd);
        String front = cardToAdd.getFront();
        String back = cardToAdd.getBack();
        System.out.println("Added flashcard with front: " + front + ", back: " + back + " to deck.");
    }

    // EFFECTS: returns flashcard at index chosen by user
    private FlashCard selectFlashCard(Deck chosenDeck) {
        int index;
        String front;
        String back;
        System.out.println("Displaying flashcards in deck...");

        for (int i = 0; i < chosenDeck.size(); i++) {
            FlashCard currentFlashCard = chosenDeck.get(i);
            index = i;
            front = currentFlashCard.getFront();
            back = currentFlashCard.getBack();
            System.out.println("\tID: " + index + ", Front: " + front + ", Back: " + back);
        }
        System.out.println("Choose flashcard ID:");
        int command = input.nextInt();
        while (!(command >= 0) && !(command < chosenDeck.size())) {
            System.out.println("Invalid ID");
            command = input.nextInt();
        }
        lastChosenCardIndex = command;
        return chosenDeck.get(command);
    }

    // REQUIRES: chosenDeck contains at least 1 flashcard
    // MODIFIES: this
    // EFFECTS: changes selected card to new user input parameters
    private void doEditCard(Deck chosenDeck) {
        selectFlashCard(chosenDeck);
        int cardIndexToReplace = lastChosenCardIndex;
        FlashCard editedCard = doUserCreateCard();
        chosenDeck.setFlashCard(cardIndexToReplace, editedCard);
        System.out.println("Edit complete.");
    }

    // REQUIRES: chosenDeck contains at least 1 flashcard
    // MODIFIES: this
    // EFFECTS; deletes user selected flashcard from the chosenDeck
    private void doDeleteCard(Deck chosenDeck) {
        selectFlashCard(chosenDeck);
        int cardIndexToDelete = lastChosenCardIndex;
        FlashCard cardToDelete = chosenDeck.get(cardIndexToDelete);
        chosenDeck.removeFlashCard(cardToDelete);
        System.out.println("Card deleted.");
    }

    // REQUIRES: deckToReview is not empty
    // EFFECTS: prints out fronts of flashcards from deckToReview for user to type matching back
    private void doReviewDeck(Deck deckToReview) {
        System.out.println("Rules: Case sensitive, one attempt per card");
        String command = null;
        int questionNumber = 0;
        ArrayList<FlashCard> cardsToDisplay = new ArrayList<>();
        ArrayList<FlashCard> cardsIncorrect = new ArrayList<>();
        for (int i = 0; i < deckToReview.size(); i++) {
            FlashCard currentFlashCard = deckToReview.get(i);
            cardsToDisplay.add(currentFlashCard);
        }
        for (FlashCard c : cardsToDisplay) {
            questionNumber += 1;
            System.out.println("Question " + questionNumber + ": " + c.getFront());
            System.out.println("Type matching back:");
            command = input.next();
            if (command == c.getBack()) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect");
                cardsIncorrect.add(c);
            }
        }
        System.out.println("Got " + cardsIncorrect.size() + "incorrect. Displaying flashcards gotten incorrect:");
        displayFlashCardsFromList(cardsIncorrect);
    }

    private void displayFlashCardsFromList(ArrayList<FlashCard> cardsIncorrect) {
        for (FlashCard c : cardsIncorrect) {
            String name = c.getFront();
            String course = c.getBack();
            System.out.println("\tICard: " + name + ", Course: " + course);
        }
    }

    private void deckMenu() {
        boolean proceed = true;
        String command = null;
        Deck chosenDeck = selectDeck();
        System.out.println("Chose deck named " + chosenDeck.getName() + " for course " + chosenDeck.getCourse());

        while (proceed) {
            displayDeckMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                proceed = false;
            } else {
                processDeckMenuCommand(command, chosenDeck);
            }
        }
        System.out.println("Back to home screen...");
    }

    // REQUIRES: deckList is not empty
    // EFFECTS: display all decks with an index and returns chosen deck
    private Deck selectDeck() {
        int index;
        String name;
        String course;
        System.out.println("Displaying deck list...");

        for (int i = 0; i < deckList.size(); i++) {
            Deck currentDeck = deckList.get(i);
            index = i;
            name = currentDeck.getName();
            course = currentDeck.getCourse();
            System.out.println("\tID: " + index + ", Name: " + name + ", Course: " + course);
        }

        System.out.println("Choose deck ID:");
        int command = input.nextInt();
        while (!(command >= 0) && !(command < deckList.size())) {
            System.out.println("Invalid ID");
            command = input.nextInt();
        }
        return deckList.get(command);
    }


    // MODIFIES: this
    // EFFECTS: adds an empty deck with name and course, regardless of duplicates.
    private void doAddNewDeck() {
        String name;
        String course;
        Deck deckToAdd;

        System.out.println("Deck name?");
        name = input.next();
        System.out.println("Deck course?");
        course = input.next();
        deckToAdd = new Deck(name, course);
        deckList.add(deckToAdd);
        System.out.println("Added deck " + name + " for course " + course + " to deck list.");
        System.out.println("Back to home screen...");
    }
}