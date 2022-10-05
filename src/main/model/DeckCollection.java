package model;

import java.util.ArrayList;

public class DeckCollection {
    ArrayList<Deck> deckList;

    public DeckCollection() {
        deckList = new ArrayList<Deck>();
    }

    public ArrayList<Deck> getDeckList() {
        return this.deckList;
    }
}
