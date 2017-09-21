package files;

import com.sun.istack.internal.Nullable;
import http.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileManager {
    private static final String _userDir = System.getProperty("user.dir");
    private static final Pattern _pattern = Pattern.compile("(.*)(\\/web-files\\/)(.*)(\\..*)");
    public static final File ROOT = new File(_userDir + File.separator + "web-files");

    private static ArrayList<File> _files = new ArrayList<>();

    public static void initialize() {
        searchInFile(ROOT);
    }

    private static void searchInFile(final File root) {
        File[] fs = root.listFiles();
        if (fs == null)
            return;
        for (final File file : fs) {
            if (file.isDirectory()) {
                searchInFile(file);
            } else {
                _files.add(file);
            }
        }
    }

    @Nullable
    public static File getFile(final String file) {
        System.out.println("Requested: " + file);
        for (final File f : _files) {
            if (f.getName().equals(file))
                return f;
            final Matcher m = _pattern.matcher(ROOT.getAbsolutePath() + File.separator + file);
            if (m.matches()) {
                System.out.println(m.toString());
                return f;
            }
        }
        return null;
    }

    @Nullable
    public static File getFolderByType(Request.FILE_TYPE type) {
        switch (type) {
            case HTML:
                return getFile("views");
            case CSS:
                return getFile("css");
            case IMG:
                return getFile("img");
            case JS:
                return getFile("js");
            default:
                return null;
        }
    }
}