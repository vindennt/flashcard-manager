// Reference: JsonSerializationDemo
// Git: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Author: Paul Carter
// Contribution: Templates for all tests

package persistence;

import exceptions.DuplicateFlashCardException;
import model.Event;
import model.EventLog;
import model.FlashCard;
import model.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    EventLog el = EventLog.getInstance();

    @Test
    void testGetDestination() {
        String destination = "./data/test_destination.json";
        JsonWriter writer = new JsonWriter(destination);
        assertEquals(destination, writer.getDestination());
    }

    @Test
    void testSetDestination() {
        String oldDestination = "./data/old_destination.json";
        String newDestination = "./data/new_destination.json";
        JsonWriter writer = new JsonWriter(oldDestination);
        assertEquals(oldDestination, writer.getDestination());
        writer.setDestination(newDestination);
        assertEquals(newDestination, writer.getDestination());
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Deck d = new Deck("Invalid Deck", "CPSC 210");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected. Invalid file.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDeck() {
        try {
            Deck d = new Deck("Empty deck", "CPSC 210");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDeck.json");
            writer.open();
            writer.write(d);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDeck.json");
            d = reader.read();
            assertEquals("Empty deck", d.getName());
            assertEquals("CPSC 210", d.getCourse());
            assertEquals(0, d.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDeck() {
        try {
            Deck d = new Deck("General deck", "CPSC 210");
            d.addFlashCard(new FlashCard("1 + 2?", "3"));
            d.addFlashCard(new FlashCard("Worst food in the world?", "Mushrooms"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDeck.json");
            writer.open();
            writer.write(d);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDeck.json");
            d = reader.read();
            assertEquals("General deck", d.getName());
            assertEquals("CPSC 210", d.getCourse());
            List<FlashCard> cardsInDeck = d.getCardsInDeck();
            assertEquals(2, cardsInDeck.size());
            checkFlashCard(cardsInDeck.get(0), "1 + 2?", "3");
            checkFlashCard(cardsInDeck.get(1), "Worst food in the world?", "Mushrooms");

        } catch (IOException | DuplicateFlashCardException e) {
            fail("Exception should not have been thrown");
        }
    }

    public List<Event> eventsToList() {
        List<Event> eventList = new ArrayList<>();
        for (Event nextEvent : el) {
            eventList.add(nextEvent);
        }
        return eventList;
    }

    @Test
    void testWriterGeneralDeckEventLogged() {
        Deck d = null;
        try {
            d = new Deck("General deck", "CPSC 210");
            el.clear();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDeck.json");
            writer.open();
            writer.write(d);
            writer.close();
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
        List<Event> eventList = eventsToList();
        assertEquals("Saved deck with " + d.getDescription() + " to location ./data/testWriterGeneralDeck.json",
                eventList.get(1).getDescription());
    }
}
