package util;

import logger.IRegistrable;
import logger.Queue;

public abstract class Debug {
    private Debug() {}

    private static final boolean
            PRINT_TO_CONSOLE = true,
            SAVE_TO_LOG = true;

    public static void log(final Object toLog) {
        if (PRINT_TO_CONSOLE)
            System.out.println(toLog.toString());
        if (SAVE_TO_LOG)
            Queue.put(new Message(toLog.toString()));
    }

    public static void log(final Exception e) {
        if (PRINT_TO_CONSOLE)
            e.printStackTrace();
        if (SAVE_TO_LOG) {
            final String s = e.getMessage();
            Queue.put(new Message(s));
        }
    }

    public static void register(final Object toLog) {
        Queue.put(new Message(toLog.toString()));
    }

    public static void dd(final Object toLog) {
        log(toLog);
        System.exit(1);
    }

    public static class Message implements IRegistrable {
        private static final String LOG_FILE = "debug.txt";
        private final String _toLog;
        Message(final String toLog) {
            _toLog = toLog;
        }

        @Override
        public String toLog() {
            return _toLog.concat("\n\n");
        }

        @Override
        public String getLogName() {
            return LOG_FILE;
        }
    }
}
