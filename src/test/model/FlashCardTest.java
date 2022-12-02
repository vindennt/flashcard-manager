package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlashCardTest {

    FlashCard testCard;
    String TEST_FRONT = "Front";
    String TEST_BACK = "Back";

    @BeforeEach
    public void setup() {
        testCard = new FlashCard(TEST_FRONT, TEST_BACK);
    }

    @Test
    public void testGetFront() {
        String testFront = testCard.getFront();
        assertEquals(TEST_FRONT, testFront);
    }

    @Test
    public void testGetBack() {
        String testBack = testCard.getBack();
        assertEquals(TEST_BACK, testBack);
    }

    @Test
    public void testGetDescription() {
        String expected = "front: " + testCard.getFront() + ", back: "
                + testCard.getBack();
        assertEquals(expected, testCard.getDescription());
    }

    @Test
    public void testToJson() {
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("front", TEST_FRONT);
        expectedJson.put("back", TEST_BACK);
        String expectedFront = expectedJson.getString("front");
        String expectedBack = expectedJson.getString("back");
        assertEquals(expectedFront, TEST_FRONT);
        assertEquals(expectedBack, TEST_BACK);
    }
}

