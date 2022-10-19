package model;

// Represents a FlashCard with a front side and back side
public class FlashCard {
    private String front;
    private String back;

    // REQUIRES: front and back are not empty
    // MODIFIES: this
    // EFFECTS: creates FlashCard with inputted front and back
    public FlashCard(String front, String back) {
        this.front = front;
        this.back = back;
    }

    // EFFECTS: returns front side of this flashcard
    public String getFront() {
        return this.front;
    }

    // EFFECTS: returns back side of this flashcard
    public String getBack() {
        return this.back;
    }

}
