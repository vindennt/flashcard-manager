package model;


import org.junit.Before;
import org.junit.Test;

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
        testCard = new FlashCard(TEST_FRONT, TEST_BACK);
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
    public void testSize() {
        assertEquals(0, testDeck.size());
    }

    @Test
    public void testContains() {
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
    }

    @Test
    public void testAddOneFlashCard() {
        assertEquals(0, testDeck.size());
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        assertEquals(1, testDeck.size());
    }

    @Test
    public void testAddTwoSameFlashCards() {
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        assertEquals(1, testDeck.size());
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        assertEquals(2, testDeck.size());
    }

    @Test
    public void testAddTwoDifferentFlashCards() {
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        assertEquals(1, testDeck.size());
        FlashCard differentCard = new FlashCard("second", "card");
        testDeck.addFlashCard(differentCard);
        assertTrue(testDeck.contains(differentCard));
        assertEquals(2, testDeck.size());
    }

    @Test
    public void testRemoveFlashCard() {
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        assertEquals(1, testDeck.size());
        testDeck.removeFlashCard(testCard);
        assertFalse(testDeck.contains(testCard));
        assertEquals(0, testDeck.size());
    }

    @Test
    public void testSetCard() {
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        FlashCard differentCard = new FlashCard("second", "card");
        testDeck.setFlashCard(0, differentCard);
        assertFalse(testDeck.contains(testCard));
        assertTrue(testDeck.contains(differentCard));

    }

    @Test
    public void testTripleSetCard() {
        testDeck.addFlashCard(testCard);
        testDeck.addFlashCard(testCard);
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        FlashCard differentCard = new FlashCard("second", "card");
        testDeck.setFlashCard(2, differentCard);
        assertTrue(testDeck.contains(differentCard));
        testDeck.setFlashCard(0, differentCard);
        testDeck.setFlashCard(1, differentCard);
        assertFalse(testDeck.contains(testCard));
        testDeck.setFlashCard(1, testCard);
        assertTrue(testDeck.contains(testCard));
    }

}
