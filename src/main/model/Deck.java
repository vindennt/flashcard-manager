package model;

import java.util.ArrayList;

public class Deck {
    private String name;
    private String subject;
    private ArrayList<FlashCard> cardsInDeck;

    public Deck(String name, String subject) {
        this.name = name;
        this.subject = subject;
        this.cardsInDeck = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getSubject() {
        return this.subject;
    }

    // EFFECTS: returns TRUE if deck contains flashcard, else false
    public boolean contains(FlashCard flashCard) {
        return cardsInDeck.contains(flashCard);
    }

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
