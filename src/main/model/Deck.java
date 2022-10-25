package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a deck with a name, course, and a list of FlashCards
public class Deck implements Writable {
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
            System.out.println("Invalid index");
        } else {
            cardsInDeck.set(index, newCard);
        }
    }

    // TODO: add tests?
    // EFFECTS: returns current deck data
    // Source: JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("course", this.course);
        json.put("cardsInDeck", cardsInDeckToJson());
        return json;
    }

    // TODO: add tests?
    // EFFECTS: returns flaschards in this deck as a JSON array
    // Source: JsonSerializationDemo
    private JSONArray cardsInDeckToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FlashCard f : cardsInDeck) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns an unmodifiable list of flashcards from this deck
    // Source: JsonSerializationDemo
    // TODO: add tests?
    public List<FlashCard> getCardsInDeck() {
        return Collections.unmodifiableList(cardsInDeck);
    }
}
