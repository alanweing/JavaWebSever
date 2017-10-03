package files;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import util.Debug;

import java.io.*;
import java.util.ArrayList;

public final class FileManager {
    private FileManager() {}
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
    public static File getFile(@NotNull String file) {
        if (file.charAt(0) == '/')
            file = file.substring(1, file.length());
        final String s = ROOT.getAbsolutePath() + File.separator + file;
        for (final File f : _files) {
            if (f.getAbsolutePath().equals(s) || f.getName().equals(file)) {
                return f;
            }
        }
        return null;
    }

    @Nullable
    public static String[] readFile(@NotNull final File file) {
        final ArrayList<String> lines = new ArrayList<>();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);
            String line;
            while ((line = bReader.readLine()) != null) {
                lines.add(line);
            }
            bReader.close();
            reader.close();
        } catch (IOException e) {
            Debug.log(e);
        }
        return lines.size() > 0 ? lines.toArray(new String[lines.size()]) : null;
    }

    @Nullable
    public static byte[] readFileBytes(@NotNull final File file) {
        byte[] finalBytes = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            finalBytes = new byte[(int) file.length()];
            bufferedInputStream.read(finalBytes, 0, finalBytes.length);
            bufferedInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            Debug.log(e);
        }
        return finalBytes;
    }
}