package model;

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


}