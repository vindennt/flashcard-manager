package model;

import java.util.ArrayList;

// Represents a deck with a name, course, and a list of FlashCards
public class Deck {
    private String name;
    private String course;
    private ArrayList<FlashCard> cardsInDeck;

    // REQUIRES: name and course are not empty
    // MODIFIES: this
    // EFFECTS: constructs a deck with name and course, and empty card list
    public Deck(String name, String course) {
        this.name = name;
        this.course = course;
        this.cardsInDeck = new ArrayList<>();
    }

    // EFFECTS: returns deck name
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns deck subject
    public String getCourse() {
        return this.course;
    }

    // EFFECTS: returns TRUE if deck contains specified flashcard, else false
    public boolean contains(FlashCard flashCard) {
        return cardsInDeck.contains(flashCard);
    }

    // EFFECTS: returns number of cards in deck
    public int size() {
        return cardsInDeck.size();
    }

    // REQUIRES: index >= 0 and index < size(cardsInDeck)
    // EFFECTS: returns flashcard at index i from cardsInDeck
    public FlashCard get(int index) {
        return this.cardsInDeck.get(index);
    }

    // MODIFIES: this
    // EFFECTS: adds flashcard to deck
    // Note: make empty character exception
    public void addFlashCard(FlashCard newCard) {
        cardsInDeck.add(newCard);
    }

    // MODIFIES: this
    // EFFECTS: removes flashcard from deck
    public void removeFlashCard(FlashCard cardToRemove) {
        cardsInDeck.remove(cardToRemove);
    }

    // REQUIRES: index >= 0
    // MODIFIES: this
    // EFFECTS: sets flashcard at index in the deck to input card
    public void setFlashCard(int index, FlashCard newCard) {
        if ((index < 0) || (index >= cardsInDeck.size())) {
            // TODO: exception?
            System.out.println("Invalid index");
        } else {
            cardsInDeck.set(index, newCard);
        }
    }

}
