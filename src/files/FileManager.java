package files;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.util.ArrayList;

public class FileManager {
    private static final String _userDir = System.getProperty("user.dir");
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
        final String s = ROOT.getAbsolutePath() + File.separator + file;
        for (final File f : _files) {
            if (f.getAbsolutePath().equals(s) || f.getName().equals(file)) {
                return f;
            }
        }
        return null;
    }
}