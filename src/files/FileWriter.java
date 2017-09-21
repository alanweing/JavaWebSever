package files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public final class FileWriter {

    private static HashMap<String, FileWriter> _paths = new HashMap<>();

    private final Path _path;
    private final Object _lock = new Object();

    private FileWriter(final String path) {
        _path = Paths.get(path);
    }

    public void write(final byte[] toWrite) {
        synchronized (_lock) {
            try {
                Files.write(_path, toWrite, StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FileWriter getInstance(final String path) {
        if (_paths.containsKey(path)) {
            return _paths.get(path);
        } else {
            File f = new File(path);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            _paths.put(path, new FileWriter(path));
            return  _paths.get(path);
        }
    }
}
