package files;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

final class FileCache {
    private static final HashMap<String, SoftReference<File>> _cache = new HashMap<>();
    private static final Object _lock = new Object();
    private FileCache() {}

    @Nullable
    private static SoftReference<File> loadFromFile(final String pathToFile) {
        final File file = FileManager.getFile(pathToFile);
        return file != null ? _cache.put(pathToFile, new SoftReference<>(file)) : null;
    }

    @Nullable
    static File get(final String pathToFile) {
        synchronized (_lock) {
            if (!_cache.containsKey(pathToFile))
                return null;
            SoftReference<File> reference = _cache.get(pathToFile);
            // someone already accesses the page before
            if (reference != null) {
                File f = reference.get();
                if (f != null)
                    return f;
                    // the GC collected the reference
                else {
                    reference = loadFromFile(pathToFile);
                    return reference != null ? reference.get() : null;
                }
            }
            // no one accessed the page
            else {
                reference = loadFromFile(pathToFile);
                return reference != null ? reference.get() : null;
            }
        }
    }

}
