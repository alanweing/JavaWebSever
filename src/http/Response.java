package http;

import com.sun.istack.internal.Nullable;
import files.FileCache;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class Response {
    private final OutputStream _output;
    private final Context _ctx;
    private final Header _header;

    public Response(final Context context) throws IOException {
        _ctx = context;
        _output = context.getSocket().getOutputStream();
        _header = new Header();
    }

    public void send(@Nullable final String[] data) {
        if (data == null) return;
        final String[] header = _header.build();
        String[] finalString = Stream.concat(Arrays.stream(header), Arrays.stream(data)).toArray(String[]::new);
        for (final String line : finalString) {
            try {
                _output.write(line.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send404() {
        _header.setStatus(404);
        _header.setContentType(Header.ContentType.HTML);
        send(FileCache.getFile("views/404.html"));
    }

    public void send500() {
        _header.setStatus(500);
        _header.setContentType(Header.ContentType.HTML);
        send(FileCache.getFile("views/500.html"));
    }

    public void send(final byte[] data) {
        final String[] header = _header.build();
        for (final String line : header) {
            try {
                _output.write(line.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } for (final byte b : data) {
            try {
                _output.write(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void close() {
        try {
            _output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Header getHeader() {
        return _header;
    }
}