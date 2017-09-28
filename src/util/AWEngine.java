package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AWEngine {

    private static final String TEMPLATE_PATTERN = "\\{{2}(.*?)}{2}";
    private static final Pattern PATTERN = Pattern.compile(TEMPLATE_PATTERN);

    public static String[] parseFile(final String[] file, final HashMap<String, String> map) throws KeyNotDefinedException {
        final ArrayList<String> rendered = new ArrayList<>();
        Matcher matcher;
        for (final String line : file) {
            boolean match = false;
            matcher = PATTERN.matcher(line);
            final StringBuilder sb = new StringBuilder();
            int i = 0;
            while (matcher.find()) {
                match = true;
                final String key = matcher.group(1).trim();
                final String newValue = map.get(key);
                sb.append(line.substring(i, matcher.start()));
                if (newValue == null)
                    throw new KeyNotDefinedException(key, "");
                sb.append(newValue);
                i = matcher.end();
            }
            rendered.add(match ? sb.toString() : line);
        }
        return rendered.toArray(new String[rendered.size()]);
    }

}
