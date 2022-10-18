package model;

import java.util.ArrayList;

public class Deck {
    private String name;
    private String subject;
    ArrayList<FlashCard> cardsInDeck;

    public Deck(String name, String subject) {
        this.name = name;
        this.subject = subject;
        cardsInDeck = new ArrayList<FlashCard>();
    }

    public String getName() {
        return this.name;
    }

    public String getSubject() {
        return this.subject;
    }

    // TESTS
    public void addFlashCard(String front, String back) {
        FlashCard newFlashCard = new FlashCard(front, back);
        cardsInDeck.add(newFlashCard);
    }

    // TESTS
    // !!!!
    public void removeFlashCard(FlashCard toRemove) {
        cardsInDeck.remove(toRemove);
    }

}
