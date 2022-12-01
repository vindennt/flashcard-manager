package exceptions;

// Represents an exception when a duplicate deck is trying to be created or imported
public class DuplicateDeckException extends Exception {
    public DuplicateDeckException(String e) {
        super(e);
    }
}
