package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlashCardTest {

    FlashCard testCard;
    String TEST_FRONT = "Front";
    String TEST_BACK = "Back";

    @Before
    public void setup() {
        testCard = new FlashCard(TEST_FRONT, TEST_BACK);
    }

    @Test
    public void testGetFront() {
        assertEquals(TEST_FRONT, testCard.getFront());
    }

    @Test
    public void testGetBack() {
        assertEquals(TEST_BACK, testCard.getBack());
    }


}