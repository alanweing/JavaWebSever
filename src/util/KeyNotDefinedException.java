package util;

public class KeyNotDefinedException extends Exception {
    private static final String DESCRIPTION = "Key '%s' not defined on file '%s'";
    KeyNotDefinedException(final String key, final String file) {
        super(String.format(DESCRIPTION, key, file));
    }
}
