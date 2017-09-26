package files;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

final class FileCache {
    private static final HashMap<String, SoftReference<String[]>> _fileCache = new HashMap<>();
    private static final HashMap<String, SoftReference<byte[]>> _imageCache = new HashMap<>();
    private static final Object _lock = new Object();
    private FileCache() {}

    @Nullable
    static String[] getFile(final String pathToFile) {
        final File file = FileManager.getFile(pathToFile);
        String[] fileString;
        if (file == null)
            return null;
        synchronized (_lock) {
            SoftReference<String[]> ref = _fileCache.get(pathToFile);
            // no one accessed the page before, so there is nothing in the cache
            if (ref == null) {
                ref = new SoftReference<>(FileManager.readFile(file));
                fileString = _fileCache.put(pathToFile, ref).get();
            }
            // someone already required the file
            else {
                fileString = _fileCache.get(pathToFile).get();
            }
        }
        return fileString;
    }

    static byte[] getImage(final String pathToImage) {
        final File file = FileManager.getFile(pathToImage);
        byte[] fileString;
        if (file == null)
            return null;
        synchronized (_lock) {
            SoftReference<byte[]> ref = _imageCache.get(pathToImage);
            // no one accessed the page before, so there is nothing in the cache
            if (ref == null) {
                ref = new SoftReference<>(FileManager.readFileBytes(file));
                fileString = _imageCache.put(pathToImage, ref).get();
            }
            // someone already required the file
            else {
                fileString = _imageCache.get(pathToImage).get();
            }
        }
        return fileString;
    }
}