// Reference: JsonSerializationDemo
// Git: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Author: Paul Carter
// Contribution: Some parts of doLoadDeck and doSaveDeck methods

package ui;

import exceptions.DuplicateFlashCardException;
import model.Deck;
import model.FlashCard;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Flashcard storage and review application
public class FlashCardApp {
    private Scanner input;            // monitors input
    private ArrayList<Deck> deckList; // list of all decks
    private int lastChosenCardIndex;  // tracks last chosen flashcard index
    private JsonWriter jsonWriter;    // deck saver
    private JsonReader jsonReader;    // deck loader
    private String jsonFileLocation;  // tracks target file location


    // EFFECTS: runs flashcard application
    public FlashCardApp() throws FileNotFoundException {
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
            // Remind to save before exiting
            if (command.equals("x")) {
                System.out.println("All unsaved data will be lost. Proceed?");
                System.out.println("\tno = return to homescreen");
                System.out.println("\tyes = exits program");
                command = input.next();
                if (command.equals("yes")) {
                    proceed = false;
                } else if (command.equals("no")) {
                    // do nothing
                } else {
                    System.out.println("Invalid command");
                }
                // end of exiting process
            } else {
                processHomeCommand(command);
            }
        }
        System.out.println("Exiting...");
    }

    // MODIFIES: this
    // EFFECTS: initializes variables and default save name and location
    private void init() {
        deckList = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        lastChosenCardIndex = 0;
        jsonWriter = new JsonWriter("");
        jsonReader = new JsonReader("");
        updateTargetFileLocation("Default"); // sets a default target file location
    }

    // EFFECTS: displays home screen menu options
    private void displayHomeMenu() {
        System.out.println("\nHOME: Select command:");
        System.out.println("\tn -> new deck");
        System.out.println("\tc -> choose deck");
        System.out.println("\tl -> load deck");
        System.out.println("\tx -> exit");
    }

    // MODIFIES: this
    // EFFECTS: processes user input command on homescreen
    private void processHomeCommand(String command) {
        if (command.equals("n")) {
            doAddNewDeck();
        } else if (command.equals("l")) {
            doLoadDeck();
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
        System.out.println("\tr -> review session");
        System.out.println("\ta -> add flashcard");
        System.out.println("\te -> edit flashcard");
        System.out.println("\td -> delete flashcard");
        System.out.println("_______________________");
        System.out.println("\tc -> change name and course");
        System.out.println("\ts -> save deck");
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
        } else if (command.equals("s")) {
            doSaveDeck(chosenDeck);
        } else if (command.equals("c")) {
            doChangeDeckNameCourse(chosenDeck);
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
        System.out.print("Set card back: ");
        back = input.next();
        newCard = new FlashCard(front, back);
        return newCard;
    }

    // MODIFIES: this
    // EFFECTS: adds flashcard with an inputted front and back to the chosen deck, regardless of duplicates
    private void doAddNewCard(Deck chosenDeck) {
        FlashCard cardToAdd = doUserCreateCard();
        try {
            chosenDeck.addFlashCard(cardToAdd);
        } catch (DuplicateFlashCardException e) {
            System.out.println(e.getMessage());
        }
        String front = cardToAdd.getFront();
        String back = cardToAdd.getBack();
        System.out.println("Added flashcard with front: " + front + ", back: " + back + " to deck.");
    }

    // EFFECTS: displays all flashcards in deck and returns the flashcard at index chosen by user
    private FlashCard selectFlashCard(Deck chosenDeck) {
        System.out.println("Displaying flashcards in deck...");
        boolean proceed = false;
        displayFlashCardsFromList(chosenDeck.getCardsInDeck(), true);

        int command = 0;
        // Waits until user inputs a valid FlashCard ID
        while (!proceed) {
            System.out.print("Choose flashcard ID: ");
            command = input.nextInt();
            if ((command < 0) || (command >= chosenDeck.size())) {
                System.out.println("Invalid ID");
            } else {
                proceed = true;
                lastChosenCardIndex = command;
            }
        }
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
        displayFlashCardsFromList(cardsIncorrect, false);
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
    // EFFECTS: displays fronts and back of all flashcards from cardsToDisplay, and index if showIndex is true.
    private void displayFlashCardsFromList(List<FlashCard> cardsToDisplay, boolean showIndex) {
        int index;
        String front;
        String back;

        if (cardsToDisplay.size() == 0) {
            // ignore method if no cards to display
        } else if (showIndex) {
            for (int i = 0; i < cardsToDisplay.size(); i++) {
                FlashCard currentFlashCard = cardsToDisplay.get(i);
                index = i;
                front = currentFlashCard.getFront();
                back = currentFlashCard.getBack();
                System.out.println("\tID: " + index + ", Front: " + front + ", Back: " + back);
            }
        } else {
            for (FlashCard c : cardsToDisplay) {
                front = c.getFront();
                back = c.getBack();
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
    // EFFECTS: displays all decks and returns user-chosen deck
    private Deck selectDeck() {
        System.out.println("Displaying deck list...");
        displayDeckList();
        boolean proceed = false;
        int command = 0;

        while (!proceed) {
            System.out.print("Choose deck ID: ");
            command = input.nextInt();
            if ((command < 0) || (command >= deckList.size())) {
                System.out.println("Invalid ID");
            } else {
                proceed = true;
            }
        }
        return deckList.get(command);
    }

    // REQUIRES: deckList is not empty
    // EFFECTS: display all decks with an index
    private void displayDeckList() {
        int index;
        String name;
        String course;

        for (int i = 0; i < deckList.size(); i++) {
            Deck currentDeck = deckList.get(i);
            index = i;
            name = currentDeck.getName();
            course = currentDeck.getCourse();
            System.out.println("\tID: " + index + ", Name: " + name + ", Course: " + course);
        }
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

    // MODIFIES: this
    // EFFECTS: gives new name and course to chosen deck
    private void doChangeDeckNameCourse(Deck chosenDeck) {
        System.out.print("New deck name: ");
        String newName = input.next();
        System.out.print("New deck course: ");
        String newCourse = input.next();
        chosenDeck.setName(newName);
        chosenDeck.setCourse(newCourse);
    }

    // EFFECTS: sets where the saver/loader should look for a file
    private void updateTargetFileLocation(String filename) {
        jsonFileLocation = "./data/" + filename + ".json";
        jsonWriter.setDestination(jsonFileLocation);
        jsonReader.setSource(jsonFileLocation);
    }

    // REQUIRES: filePath is not empty
    // EFFECTS: returns true if file with name filePath exists, else false.
    private boolean isOccupiedFilepath(String filePath) {
        File file = new File(filePath);
        return (file.exists() && !file.isDirectory());
    }

    // EFFECTS: initiates saving the deck to a file and warns if file already exists
    private void doSaveDeck(Deck deck) {
        try {
            String fileName = deck.getName() + deck.getCourse();
            updateTargetFileLocation(fileName);

            if (isOccupiedFilepath(jsonFileLocation)) {
                String command;
                System.out.println("File named " + fileName + ".json already exists. Overwrite?");
                System.out.println("\tno = do not overwrite existing file");
                System.out.println("\tyes = overwrite existing file");
                command = input.next();

                if (command.equals("yes")) {
                    saveDeck(deck);
                } else if (command.equals("no")) {
                    // do nothing
                } else {
                    System.out.println("Invalid command");
                }
            } else {
                saveDeck(deck);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file at: " + jsonFileLocation);
        }
    }

    // EFFECTS: saves deck to a file
    private void saveDeck(Deck deck) throws FileNotFoundException {
        jsonWriter.open();
        jsonWriter.write(deck);
        jsonWriter.close();
        System.out.println("Saved deck " + deck.getName() + deck.getCourse() + " to " + jsonFileLocation);
    }

    // MODIFIES: this
    // EFFECTS: loads deck from a file to decklist
    private void doLoadDeck() {
        System.out.println("Load which file?");
        String fileToLoad = input.next();
        updateTargetFileLocation(fileToLoad);
        try {
            Deck deckToLoad = jsonReader.read();
            deckList.add(deckToLoad);
            System.out.println("Loaded " + deckToLoad.getName() + " from " + jsonFileLocation);
        } catch (IOException e) {
            System.out.println("Unable to read from file at: " + jsonFileLocation);
        }
    }
}