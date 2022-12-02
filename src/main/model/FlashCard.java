// Reference: JsonSerializationDemo
// Git: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Author: Paul Carter
// Contribution: toJson method template

package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a FlashCard with a front side and back side
public class FlashCard implements Writable {
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
    

    // EFFECTS: returns the flashcard's front and back as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("front", this.front);
        json.put("back", this.back);
        return json;
    }

    // EFFECTS: returns this card's front and back description
    public String getDescription() {
        return "front: " + front + ", back: " + back;
    }
}
