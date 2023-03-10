// Reference: JsonSerializationDemo
// Git: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Author: Paul Carter
// Contribution: Method templates for all in this class

package persistence;

import exceptions.DuplicateFlashCardException;
import model.Deck;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Event;
import model.EventLog;
import model.FlashCard;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    public String getSource() {
        return this.source;
    }

    // REQUIRES: newSource is not empty
    // MODIFIES: this
    // EFFECTS: sets source
    public void setSource(String newSource) {
        this.source = newSource;
    }

    // EFFECTS: reads deck from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Deck read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDeck(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        EventLog.getInstance().logEvent(new Event(
                "Loaded deck from location " + source));
        return contentBuilder.toString();
    }

    // EFFECTS: parses deck from JSON object and returns it
    private Deck parseDeck(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String course = jsonObject.getString("course");
        Deck d = new Deck(name, course);
        try {
            addDeck(d, jsonObject);
        } catch (DuplicateFlashCardException e) {
            System.out.println(e.getMessage());
        }
        return d;

    }

    // MODIFIES: d
    // EFFECTS: parses flashcards from JSON object and adds them to deck
    private void addDeck(Deck d, JSONObject jsonObject) throws DuplicateFlashCardException {
        JSONArray jsonArray = jsonObject.getJSONArray("cardsInDeck");
        for (Object json : jsonArray) {
            JSONObject nextFlashCard = (JSONObject) json;
            addFlashCard(d, nextFlashCard);
        }
    }

    // MODIFIES: d
    // EFFECTS: parses flashcard from JSON object and adds it to deck
    private void addFlashCard(Deck d, JSONObject jsonObject) throws DuplicateFlashCardException {
        String front = jsonObject.getString("front");
        String back = jsonObject.getString("back");
        FlashCard flashCard = new FlashCard(front, back);
        d.addFlashCard(flashCard);
    }

}
