package model;

public class FlashCard {

    private static int INITIAL_DAYS = 0;

    private String front;
    private String back;
    private int daysBeforeReview;

    public FlashCard(String front, String back) {
        this.front = front;
        this.back = back;
        this.daysBeforeReview = INITIAL_DAYS;
    }

    public String getFront() {
        return this.front;
    }

    public String getBack() {
        return this.back;
    }

    public int getDaysBeforeReview() {
        return this.daysBeforeReview;
    }

    // TESTS
    public void editFront(String newFront) {
        this.front = newFront;
    }

    // TESTS
    public void editBack(String newBack) {
        this.front = newBack;
    }

    public int getInitialDays() {
        return INITIAL_DAYS;
    }
}
