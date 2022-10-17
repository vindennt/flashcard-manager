package model;


import org.junit.Before;
import org.junit.Test;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    Deck testDeck;
    String TEST_NAME = "test";
    String TEST_SUBJECT = "Science";

    FlashCard testCard;
    String TEST_FRONT = "Front";
    String TEST_BACK = "Back";


    @Before
    public void setup() {
        testDeck = new Deck(TEST_NAME, TEST_SUBJECT);
        ArrayList<FlashCard> testCards = new ArrayList<FlashCard>();
        testCard = new FlashCard(TEST_FRONT, TEST_BACK);
        testCards.add(testCard);
    }

    @Test
    public void testGetName() {
        assertEquals(TEST_NAME, testDeck.getName());
    }

    @Test
    public void testGetSubject() {
        assertEquals(TEST_SUBJECT, testDeck.getSubject());
    }

    @Test
    public void testDeckContainsWord() {
        assertTrue(testDeck.contains("Front"));
        assertTrue(testDeck.contains("Back"));
        assertFalse(testDeck.contains("Middle"));
    }
}
