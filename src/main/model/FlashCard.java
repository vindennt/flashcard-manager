package model;

public class FlashCard {

    private String front;
    private String back;

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
