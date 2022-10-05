package model;

public class FlashCard {

    private String frontSide;
    private String backSide;
    private int daysBeforeReview;

    public FlashCard(String front, String back) {
        this.frontSide = front;
        this.backSide = back;
        this.daysBeforeReview = 0;
    }

    public String getFrontSide() {
        return this.frontSide;
    }

    public String getBackSide() {
        return this.backSide;
    }

    public int getDaysBeforeReview() {
        return this.daysBeforeReview;
    }
}
