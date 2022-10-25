// Source: JsonSerializationDemo

package persistence;

import model.FlashCard;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkFlashCard(FlashCard f, String front, String back) {
        assertEquals(front, f.getFront());
        assertEquals(back, f.getBack());
    }
}
