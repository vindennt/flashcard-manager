// Source: JsonSerializationDemo

package persistence;

import model.FlashCard;
import model.Deck;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

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

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}