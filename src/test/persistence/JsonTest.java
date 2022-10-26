// Reference: JsonSerializationDemo
// Git: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Author: Paul Carter
// Contribution: Templates for all tests

package persistence;

import model.FlashCard;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkFlashCard(FlashCard f, String front, String back) {
        assertEquals(front, f.getFront());
        assertEquals(back, f.getBack());
    }
}
