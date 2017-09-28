package http;

import com.sun.istack.internal.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class GET {

    final String _URL;

    public GET(final String url) {
        _URL = url;
    }

    @Nullable
    public String request() {
        final StringBuilder stringBuilder = new StringBuilder("");
        try {
            final URL url = new URL(_URL);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String finalString = stringBuilder.toString();
        return finalString.equals("") ? null : finalString;
    }
}
