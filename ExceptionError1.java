package filesprocessing;

/**
 * Exception file  - deals with type one warnings, which include
 * bad parameters, illegal values, and points out the line in which the
 * error occurred.
 */
public class ExceptionError1 extends Exception {
    private static final long serialVersionUID = 1L;

    public ExceptionError1(int line) {
        super();
        System.err.println("Warning in line " + line);
    }
}
