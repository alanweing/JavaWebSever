package files;

import com.sun.istack.internal.Nullable;
import http.Server;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

public final class FileCache {
    private static final HashMap<String, SoftReference<String[]>> _fileCache = new HashMap<>();
    private static final HashMap<String, SoftReference<byte[]>> _imageCache = new HashMap<>();
    private static final Object _lock = new Object();
    private FileCache() {}

    @Nullable
    public static String[] getFile(final String pathToFile) {
        if (!Server.USE_CACHE)
            return FileManager.readFile(FileManager.getFile(pathToFile));
        final File file = FileManager.getFile(pathToFile);
        String[] fileString;
        if (file == null)
            return null;
        synchronized (_lock) {
            SoftReference<String[]> ref = _fileCache.get(pathToFile);
            // no one accessed the page before, so there is nothing in the cache
            if (ref == null) {
                ref = new SoftReference<>(FileManager.readFile(file));
                _fileCache.put(pathToFile, ref);
                fileString = ref.get();
            }
            // someone already requested the file
            else {
                fileString = _fileCache.get(pathToFile).get();
            }
        }
        return fileString;
    }

    public static byte[] getImage(final String pathToImage) {
        if (Server.USE_CACHE)
            return FileManager.readFileBytes(FileManager.getFile(pathToImage));
        final File file = FileManager.getFile(pathToImage);
        byte[] fileString;
        if (file == null)
            return null;
        synchronized (_lock) {
            SoftReference<byte[]> ref = _imageCache.get(pathToImage);
            // no one accessed the page before, so there is nothing in the cache
            if (ref == null) {
                ref = new SoftReference<>(FileManager.readFileBytes(file));
                _imageCache.put(pathToImage, ref);
                fileString = ref.get();
            }
            // someone already required the file
            else {
                fileString = _imageCache.get(pathToImage).get();
            }
        }
        return fileString;
    }
}
