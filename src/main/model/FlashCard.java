package model;

public class FlashCard {

    private String front;
    private String back;

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

}
