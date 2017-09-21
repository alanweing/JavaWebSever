package http;

import com.sun.istack.internal.Nullable;

import java.io.*;
import java.util.ArrayList;

public class Renderer {

    public static ArrayList<String> render(final File file) {
        ArrayList<String> page = new ArrayList<>();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);
            String line;
            while ((line = bReader.readLine()) != null) {
                page.add(line);
            }
            reader.close();
            bReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    @Nullable
    public static byte[] renderImage(final File file) {
        byte[] toReturn = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            toReturn = new byte[(int) file.length()];
            fis.read(toReturn);
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

}
