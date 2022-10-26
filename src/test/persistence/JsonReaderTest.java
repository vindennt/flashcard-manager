// Source: JsonSerializationDemo

package persistence;

import model.FlashCard;
import model.Deck;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testGetSource() {
        String source = "./data/test_source.json";
        JsonReader reader = new JsonReader(source);
        assertEquals(source, reader.getSource());
    }

    @Test
    void testSetSource() {
        String oldSource = "./data/old_source.json";
        String newSource = "./data/new_source.json";
        JsonReader reader = new JsonReader(oldSource);
        assertEquals(oldSource, reader.getSource());
        reader.setSource(newSource);
        assertEquals(newSource, reader.getSource());
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            Deck d = reader.read();
            fail("IOException expected, no such file exists");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDeck() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDeck.json");
        try {
            Deck d = reader.read();
            assertEquals("Empty deck", d.getName());
            assertEquals("CPSC 210", d.getCourse());
            assertEquals(0, d.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDeck() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDeck.json");
        try {
            Deck d = reader.read();
            assertEquals("General deck", d.getName());
            List<FlashCard> cardsInDeck = d.getCardsInDeck();
            assertEquals(2, cardsInDeck.size());
            checkFlashCard(cardsInDeck.get(0), "1 + 2?", "3");
            checkFlashCard(cardsInDeck.get(1), "Worst food in the world?", "Mushrooms");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
