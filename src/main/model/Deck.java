package model;

import java.util.ArrayList;

public class Deck {
    private String name;
    private String course;
    private ArrayList<FlashCard> cardsInDeck;

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

    // EFFECTS: returns TRUE if deck contains flashcard, else false
    public boolean contains(FlashCard flashCard) {
        return cardsInDeck.contains(flashCard);
    }

    // EFFECTS: returns number of cards in deck
    public int size() {
        return cardsInDeck.size();
    }

    // MODIFIES: this
    // EFFECTS: adds flashcard to deck
    // Note: make empty character exception
    public void addFlashCard(FlashCard newCard) {
        cardsInDeck.add(newCard);
    }

    // MODIFIES: this
    // EFFECTS: removes flashcard from deck
    public void removeFlashCard(FlashCard toRemove) {
        cardsInDeck.remove(toRemove);
    }

    // MODIFIES: this
    // EFFECTS: sets flashcard at index in the deck to input card
    public void setFlashCard(int index, FlashCard newCard) {
        cardsInDeck.set(index, newCard);
    }

}
