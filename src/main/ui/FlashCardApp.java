package ui;

import model.Deck;
import model.FlashCard;

import java.util.ArrayList;
import java.util.Scanner;

// Flashcard storage and review application
public class FlashCardApp {
    private Scanner input;            // monitors input
    private ArrayList<Deck> deckList; // list of all decks
    private int lastChosenCardIndex;  // tracks last chosen flashcard index

    // EFFECTS: runs flashcard application
    public FlashCardApp() {
        runFlashCardApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input as a command starting at homescreen
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
    // EFFECTS: initializes decklist, input tracker, and last chosen card
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

    // EFFECTS: displays deck menu options
    private void displayDeckMenu() {
        System.out.println("\nDECK: Select command:");
        System.out.println("\tr -> review deck");
        System.out.println("\ta -> add flashcard");
        System.out.println("\te -> edit flashcard");
        System.out.println("\td -> delete flashcard");
        System.out.println("\tb -> back to home screen");
    }

    // MODIFIES: this
    // EFFECTS: processes user input command on edit screen
    private void processDeckMenuCommand(String command, Deck chosenDeck) {
        if (command.equals("a")) {
            doAddNewCard(chosenDeck);
        } else if (((command.equals("r")) || (command.equals("e")) || (command.equals("d")))
                && (chosenDeck.size() == 0)) {
            System.out.println("Invalid command, chosen deck is empty");
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

    // EFFECTS: prompts user to create a flashcard and returns it
    private FlashCard doUserCreateCard() {
        String front;
        String back;
        FlashCard newCard;

        System.out.print("Card front: ");
        front = input.next();
        System.out.print("Card back: ");
        back = input.next();
        newCard = new FlashCard(front, back);
        return newCard;
    }

    // MODIFIES: this
    // EFFECTS: adds flashcard with an inputted front and back to the chosen deck, regardless of duplicates
    private void doAddNewCard(Deck chosenDeck) {
        FlashCard cardToAdd = doUserCreateCard();
        chosenDeck.addFlashCard(cardToAdd);
        String front = cardToAdd.getFront();
        String back = cardToAdd.getBack();
        System.out.println("Added flashcard with front: " + front + ", back: " + back + " to deck.");
    }

    // EFFECTS: displays all flashcards in deck and returns the flashcard at index chosen by user
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
        System.out.print("Choose flashcard ID: ");
        int command = input.nextInt();
        // TODO throw exception or request a new input if invalid
        if ((command < 0) || (command >= chosenDeck.size())) {
            System.out.println("Invalid ID");
        }
        lastChosenCardIndex = command;
        return chosenDeck.get(command);
    }

    // REQUIRES: chosenDeck contains at least 1 flashcard
    // MODIFIES: this
    // EFFECTS: replaces a user-chosen flashcard with a user-created flashcard
    private void doEditCard(Deck chosenDeck) {
        selectFlashCard(chosenDeck);
        int cardIndexToReplace = lastChosenCardIndex;
        FlashCard editedCard = doUserCreateCard();
        chosenDeck.setFlashCard(cardIndexToReplace, editedCard);
        System.out.println("Edit complete");
    }

    // REQUIRES: chosenDeck contains at least 1 flashcard
    // MODIFIES: this
    // EFFECTS; deletes user-chosen flashcard from the chosenDeck
    private void doDeleteCard(Deck chosenDeck) {
        selectFlashCard(chosenDeck);
        int cardIndexToDelete = lastChosenCardIndex;
        FlashCard cardToDelete = chosenDeck.get(cardIndexToDelete);
        chosenDeck.removeFlashCard(cardToDelete);
        System.out.println("Card deleted");
    }

    // REQUIRES: deckToReview is not empty
    // EFFECTS: prints out fronts of flashcards from deckToReview for user to type matching back
    private void doReviewDeck(Deck deckToReview) {
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
            System.out.println("\tQuestion " + questionNumber + ": " + c.getFront());
            System.out.print("Matching answer: ");
            command = input.next();
            String answerText = c.getBack();
            if (command.equals(answerText)) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. Correct match: " + answerText);
                cardsIncorrect.add(c);
            }
        }
        displayReviewEndMessage(cardsToDisplay.size(), cardsIncorrect.size());
        displayFlashCardsFromList(cardsIncorrect);
    }


    // REQUIRES: numberCardsReview != 0
    // EFFECTS: displays correctness score and end message for review session
    private void displayReviewEndMessage(double numberCardsReviewed, double numberCardsIncorrect) {
        double cardsCorrect = numberCardsReviewed - numberCardsIncorrect;
        double percentScore = cardsCorrect / numberCardsReviewed;
        double maxPercent = 100.00;
        percentScore *= maxPercent;
        System.out.println("Review session ended. Score: " + percentScore + "%");
        if (numberCardsIncorrect != 0) {
            System.out.println("Displaying mismatched cards...");
        }
    }

    // REQUIRES: cardsToDisplay.size() != 0
    // EFFECTS: displays fronts and back of all flashcards from cardsToDisplay
    private void displayFlashCardsFromList(ArrayList<FlashCard> cardsToDisplay) {
        if (cardsToDisplay.size() == 0) {
            // ignore method if no cards to display
            // TODO: throw exception?
        } else {
            for (FlashCard c : cardsToDisplay) {
                String front = c.getFront();
                String back = c.getBack();
                System.out.println("\tFront: " + front + ", Back: " + back);
            }
        }
    }

    // EFFECTS: displays and processes commands to enact upon a chosen deck
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
    // EFFECTS: display all decks with an index and returns user-chosen deck
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

        System.out.print("Choose deck ID: ");
        int command = input.nextInt();
        // TODO throw exception or request a new input if invalid
        if ((command < 0) || (command >= deckList.size())) {
            System.out.println("Invalid ID");
        }
        return deckList.get(command);
    }


    // MODIFIES: this
    // EFFECTS: prompts user to choose a new deck's name and course and adds it to decklist
    private void doAddNewDeck() {
        String name;
        String course;
        Deck deckToAdd;

        System.out.print("Deck name: ");
        name = input.next();
        System.out.print("Deck course: ");
        course = input.next();
        deckToAdd = new Deck(name, course);
        deckList.add(deckToAdd);
        System.out.println("Added deck " + name + " for course " + course + " to deck list.");
        System.out.println("Back to home screen...");
    }
}