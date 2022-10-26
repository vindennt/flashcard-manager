package model;


import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    Deck testDeck;
    String TEST_NAME = "test";
    String TEST_COURSE = "CPSC210";

    FlashCard testCard;
    String TEST_FRONT = "Front";
    String TEST_BACK = "Back";


    @BeforeEach
    public void setup() {
        testDeck = new Deck(TEST_NAME, TEST_COURSE);
        testCard = new FlashCard(TEST_FRONT, TEST_BACK);
    }

    @Test
    public void testGetName() {
        assertEquals(TEST_NAME, testDeck.getName());
    }

    @Test
    public void testGetCourse() {
        assertEquals(TEST_COURSE, testDeck.getCourse());
    }

    @Test
    public void testSetName() {
        String newName = "RESET_NAME";
        testDeck.setName(newName);
        assertEquals(newName, testDeck.getName());
    }

    @Test
    public void testSetCourse() {
        String newCourse = "RESET_COURSE";
        testDeck.setCourse(newCourse);
        assertEquals(newCourse, testDeck.getCourse());
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

    @Test
    public void testSetCardUnderZero() {
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        FlashCard differentCard = new FlashCard("second", "card");
        testDeck.setFlashCard(-1, differentCard);
        assertTrue(testDeck.contains(testCard));
        assertFalse(testDeck.contains(differentCard));
    }

    @Test
    public void testSetCardSameDeckSize() {
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        FlashCard differentCard = new FlashCard("second", "card");
        testDeck.setFlashCard(2, differentCard);
        assertTrue(testDeck.contains(testCard));
        assertFalse(testDeck.contains(differentCard));
    }

    @Test
    public void testSetCardOverDeckSize() {
        testDeck.addFlashCard(testCard);
        assertTrue(testDeck.contains(testCard));
        FlashCard differentCard = new FlashCard("second", "card");
        testDeck.setFlashCard(3, differentCard);
        assertTrue(testDeck.contains(testCard));
        assertFalse(testDeck.contains(differentCard));
    }

    @Test
    public void testGetOnce() {
        testDeck.addFlashCard(testCard);
        assertEquals(testCard, testDeck.get(0));
    }

    @Test
    public void testGetTwice() {
        testDeck.addFlashCard(testCard);
        assertEquals(testCard, testDeck.get(0));
        FlashCard testcard_2 = new FlashCard("front_2", "back_2");
        testDeck.addFlashCard(testcard_2);
        assertEquals(testcard_2, testDeck.get(1));
    }

    @Test
    public void testGetCardsInEmptyDeck() {
        List<FlashCard> empty = new ArrayList<>();
        assertEquals(empty, testDeck.getCardsInDeck());
    }

    @Test
    public void testGetCardsInDeck() {
        List<FlashCard> testList = new ArrayList<>();
        testList.add(testCard);
        testDeck.addFlashCard(testCard);
        assertEquals(testList, testDeck.getCardsInDeck());
    }

    @Test
    public void testGetMultipleCardsInDeck() {
        List<FlashCard> testList = new ArrayList<>();
        testList.add(testCard);
        testList.add(testCard);
        testDeck.addFlashCard(testCard);
        testDeck.addFlashCard(testCard);
        assertEquals(testList, testDeck.getCardsInDeck());
    }

    @Test
    public void testCardsInDeckToJson() {
        JSONArray expectedCardListJson = new JSONArray();
        expectedCardListJson.put(testCard.toJson());
        assertFalse(expectedCardListJson.isEmpty());
        JSONObject expectedCard = (JSONObject) expectedCardListJson.get(0);
        String expectedFront = expectedCard.getString("front");
        String expectedBack = expectedCard.getString("back");
        assertEquals(expectedFront, TEST_FRONT);
        assertEquals(expectedBack, TEST_BACK);
    }

    @Test
    public void testToJsonEmptyDeck() {
        JSONObject expectedDeckJson = new JSONObject();
        JSONArray expectedCardListJson = new JSONArray();
        expectedDeckJson.put("name", TEST_NAME);
        expectedDeckJson.put("course", TEST_COURSE);
        expectedDeckJson.put("cardsInDeck", expectedCardListJson);

        String expectedName = expectedDeckJson.getString("name");
        String expectedCourse = expectedDeckJson.getString("course");
        JSONArray expectedCardList = expectedDeckJson.getJSONArray("cardsInDeck");
        assertEquals(expectedName, TEST_NAME);
        assertEquals(expectedCourse, TEST_COURSE);
        assertTrue(expectedCardList.isEmpty());
    }

    @Test
    public void testToJsonOneCardDeck() {
        JSONObject expectedDeckJson = new JSONObject();
        JSONArray expectedCardListJson = new JSONArray();
        expectedCardListJson.put(testCard.toJson());
        expectedDeckJson.put("name", TEST_NAME);
        expectedDeckJson.put("course", TEST_COURSE);
        expectedDeckJson.put("cardsInDeck", expectedCardListJson);

        String expectedName = expectedDeckJson.getString("name");
        String expectedCourse = expectedDeckJson.getString("course");
        JSONArray expectedCardList = expectedDeckJson.getJSONArray("cardsInDeck");
        assertEquals(expectedName, TEST_NAME);
        assertEquals(expectedCourse, TEST_COURSE);
        assertFalse(expectedCardList.isEmpty());

        JSONObject expectedCard = (JSONObject) expectedCardList.get(0);
        String expectedFront = expectedCard.getString("front");
        String expectedBack = expectedCard.getString("back");
        assertEquals(expectedFront, TEST_FRONT);
        assertEquals(expectedBack, TEST_BACK);
    }



}