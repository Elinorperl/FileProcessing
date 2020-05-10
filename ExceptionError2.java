package filesprocessing;

/**
 * Exception file  - deals with type two warnings, which include
 * mainly bad format.
 */

public class ExceptionError2 extends Exception {
    private static final long serialVersionUID = 1L;

    public ExceptionError2(String message) {
        super(message);
    }
}
