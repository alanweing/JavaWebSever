package http.exceptions;

public class FileNotFoundException extends Exception {
    private static final String DESCRIPTION = "The requested file could not be found!";
    public FileNotFoundException() {
        super(DESCRIPTION);
    }
}
