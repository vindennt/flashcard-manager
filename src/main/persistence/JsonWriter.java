// Reference: JsonSerializationDemo
// Git: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Author: Paul Carter
// Contribution: Method templates for all in this class

package persistence;

import model.Deck;
import model.Event;
import model.EventLog;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return this.destination;
    }

    // REQUIRES: newDestination is not empty
    // MODIFIES: this
    // EFFECTS: sets destination
    public void setDestination(String newDestination) {
        this.destination = newDestination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of deck to file
    public void write(Deck d) {
        JSONObject json = d.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event(
                "Saved deck with " + d.getDescription() + " to location "
                        + this.destination));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
