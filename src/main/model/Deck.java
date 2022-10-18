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

    public boolean contains(FlashCard flashCard) {
        return cardsInDeck.contains(flashCard);
    }

    public int size() {
        return cardsInDeck.size();
    }

    public void addFlashCard(FlashCard newCard) {
        cardsInDeck.add(newCard);
    }

    public void removeFlashCard(FlashCard toRemove) {
        cardsInDeck.remove(toRemove);
    }

    public void setFlashCard(int index, FlashCard newCard) {
        cardsInDeck.set(index, newCard);
    }

}
