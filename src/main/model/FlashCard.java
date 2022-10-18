package model;

public class FlashCard {

    private String front;
    private String back;
    private int daysBeforeReview;

    public FlashCard(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String getFront() {
        return this.front;
    }

    public String getBack() {
        return this.back;
    }

    // TESTS
    public void editFront(String newFront) {
        this.front = newFront;
    }

    // TESTS
    public void editBack(String newBack) {
        this.front = newBack;
    }

}
