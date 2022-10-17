package model;

import model.DeckCollection;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class DeckCollectionTest {

    DeckCollection testCollection;

    Deck testDeck;
    String TEST_NAME = "test";
    String TEST_SUBJECT = "Science";

    @BeforeEach
    public void setup() {
        testCollection = new DeckCollection();
        testDeck = new Deck(TEST_NAME, TEST_SUBJECT);
        testCollection.deckList.add(testDeck);
    }

    @Test
    public void testContainsDeck() {
        assertTrue(testCollection.containsDeck());
    }
}
