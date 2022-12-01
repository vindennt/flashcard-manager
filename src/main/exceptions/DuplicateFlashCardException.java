package exceptions;

// Represents an exception when a duplicate FlashCard is trying to be added
public class DuplicateFlashCardException extends Exception {
    public DuplicateFlashCardException(String s) {
        super(s);
    }
}
