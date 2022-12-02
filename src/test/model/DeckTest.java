package model;


import exceptions.DuplicateFlashCardException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    Deck testDeck;
    Deck emptyDeck;
    String TEST_NAME = "test";
    String TEST_COURSE = "CPSC210";

    FlashCard testCard;
    String TEST_FRONT = "Front";
    String TEST_BACK = "Back";
    FlashCard differentCard;
    EventLog el = EventLog.getInstance();


    @BeforeEach
    public void setup() {
        emptyDeck = new Deck("emptyName", "emptyCourse");
        testDeck = new Deck(TEST_NAME, TEST_COURSE);
        testCard = new FlashCard(TEST_FRONT, TEST_BACK);
        differentCard = new FlashCard("second", "card");

        try {
            testDeck.addFlashCard(testCard);
        } catch (DuplicateFlashCardException e) {
            fail("Unexpected DuplicateFlashCardException");
        }
        el.clear();
    }

    @Test
    public void testToJson() {
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("front", TEST_FRONT);
        expectedJson.put("back", TEST_BACK);
    }

    @Test
    public void testGetName() {
        assertEquals(TEST_NAME, testDeck.getName());
    }

    @Test
    public void testGetDescription() {
        String expected = "name: " + testDeck.getName() + ", course: "
                + testDeck.getCourse();
        assertEquals(expected, testDeck.getDescription());
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
        assertEquals(1, testDeck.size());
    }

    @Test
    public void testContains() {
        assertTrue(testDeck.contains(testCard));
    }

    @Test
    public void testAddOneFlashCard() {

        try {
            emptyDeck.addFlashCard(testCard);
        } catch (DuplicateFlashCardException e) {
            fail("unexpected DuplicateFlashCardException");
        }
        assertEquals(1, emptyDeck.size());
        assertTrue(emptyDeck.contains(testCard));
    }

    @Test
    public void testAddTwoSameFlashCards() {
        try {
            testDeck.addFlashCard(testCard);
            fail("should throw duplicateCardException");
        } catch (DuplicateFlashCardException e) {
            // pass, expected
        }
        assertEquals(1, testDeck.size());
    }

    @Test
    public void testAddTwoDifferentFlashCards() {
        FlashCard differentCard = new FlashCard("second", "card");
        try {
            testDeck.addFlashCard(differentCard);
        } catch (DuplicateFlashCardException e) {
            fail("unexpected DuplicateFlashCardException");
        }
        assertTrue(testDeck.contains(differentCard));
        assertEquals(2, testDeck.size());
    }

    @Test
    public void testRemoveFlashCard() {
        testDeck.removeFlashCard(testCard);
        assertFalse(testDeck.contains(testCard));
        assertEquals(0, testDeck.size());
    }

    @Test
    public void testSetCard() {

        testDeck.setFlashCard(0, differentCard);
        assertFalse(testDeck.contains(testCard));
        assertTrue(testDeck.contains(differentCard));

    }

    @Test
    public void testSetCardUnderZero() {
        testDeck.setFlashCard(-1, differentCard);
        assertTrue(testDeck.contains(testCard));
        assertFalse(testDeck.contains(differentCard));
    }

    @Test
    public void testSetCardSameDeckSize() {
        testDeck.setFlashCard(1, differentCard);
        assertTrue(testDeck.contains(testCard));
        assertFalse(testDeck.contains(differentCard));
    }

    @Test
    public void testSetCardOverDeckSize() {
        testDeck.setFlashCard(2, differentCard);
        assertTrue(testDeck.contains(testCard));
        assertFalse(testDeck.contains(differentCard));
    }

    @Test
    public void testGetOnce() {
        assertEquals(testCard, testDeck.get(0));
    }

    @Test
    public void testGetTwice() {
        assertEquals(testCard, testDeck.get(0));
        FlashCard testcard_2 = new FlashCard("front_2", "back_2");
        try {
            testDeck.addFlashCard(testcard_2);
        } catch (DuplicateFlashCardException e) {
            fail("unexpected DuplicateFlashCardException");
        }
        assertEquals(testcard_2, testDeck.get(1));
    }

    @Test
    public void testGetCardsInEmptyDeck() {
        List<FlashCard> empty = new ArrayList<>();
        assertEquals(empty, emptyDeck.getCardsInDeck());
    }

    @Test
    public void testGetCardsInDeck() {
        List<FlashCard> testList = new ArrayList<>();
        testList.add(testCard);
        assertEquals(testList, testDeck.getCardsInDeck());
    }

    @Test
    public void testEqualDeckNames() {
        Deck equalDeck = new Deck("test", "CPSC310");
        assertTrue(testDeck.equals(equalDeck));
    }

    @Test
    public void testNotEqualDecks() {
        assertFalse(testDeck.equals(emptyDeck));
    }

    @Test
    public void testEqualDeckListNotName() {
        try {
            emptyDeck.addFlashCard(testCard);
        } catch (DuplicateFlashCardException e) {
            fail("unexpected DuplicateFlashCardException");
        }
        assertFalse(testDeck.equals(emptyDeck));
    }

    @Test
    public void testGetMultipleCardsInDeck() {
        List<FlashCard> testList = new ArrayList<>();
        testList.add(testCard);
        testList.add(differentCard);
        try {
            testDeck.addFlashCard(differentCard);
        } catch (DuplicateFlashCardException e) {
            fail("unexpected DuplicateFlashCardException");
        }
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

    // EFFECTS: puts events from event log into iterable list
    public List<Event> eventsToList() {
        List<Event> eventList = new ArrayList<>();
        for (Event nextEvent : el) {
            eventList.add(nextEvent);
        }
        return eventList;
    }

    @Test
    public void testConstructorEventLogged() {
        Deck newDeck = new Deck("event", "test");
        List<Event> eventList = eventsToList();

        assertEquals(eventList.get(1).getDescription(), "Created deck with name: event, course: test");
    }

    @Test
    public void testSetNameEventLogged() {
        testDeck.setName("newName");
        List<Event> eventList = eventsToList();

        assertEquals(eventList.get(1).getDescription(), "Set name of deck with name: test, course:" +
                " CPSC210 to newName");
    }

    @Test
    public void testSetCourseEventLogged() {
        testDeck.setCourse("newCourse");
        List<Event> eventList = eventsToList();

        assertEquals(eventList.get(1).getDescription(), "Set course of deck with name: test, course:" +
                " CPSC210 to newCourse");
    }

    @Test
    public void testAddFlashCardEventLogged() {
        FlashCard newCard = new FlashCard("event", "flash=card");

        try {
            testDeck.addFlashCard(newCard);
        } catch (DuplicateFlashCardException e) {
            fail("unexpected DuplicateFlashCardException");
        }
        List<Event> eventList = eventsToList();

        assertEquals(eventList.get(1).getDescription(),
                "Added flashcard with " + newCard.getDescription() + " to deck " + testDeck.getDescription());
    }

    @Test
    public void testRemoveFlashCardEventLogged() {
        testDeck.removeFlashCard(testCard);
        List<Event> eventList = eventsToList();

        assertEquals(eventList.get(1).getDescription(),
                "Removed flashcard with " + testCard.getDescription()
                        + " from deck " + testDeck.getDescription());
    }

    @Test
    public void testSetFlashCardEventLogged() {
        FlashCard newCard = new FlashCard("event", "flash=card");
        testDeck.setFlashCard(0, newCard);
        List<Event> eventList = eventsToList();

        assertEquals(eventList.get(1).getDescription(),
                "Replaced flashcard with " + testCard.getDescription() + " from deck"
                        + testDeck.getDescription() + " with flashcard with " + newCard.getDescription());
    }


}