package util;

public class Debug {

    private static final boolean
            PRINT_TO_CONSOLE = true,
            SAVE_TO_LOG = false;

    public static void log(final Object toLog) {
        if (PRINT_TO_CONSOLE)
            System.out.println(toLog.toString());
    }

    public static void dd(final Object toLog) {
        log(toLog);
        System.exit(1);
    }
}
