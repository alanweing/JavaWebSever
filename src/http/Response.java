package http;

import java.io.*;
import java.util.ArrayList;

public class Response {
    private final OutputStream _output;
    private final Context _ctx;
    private final Header _header;

    private ArrayList<String> _page = new ArrayList<>();

    public Response(final Context context) throws IOException {
        _ctx = context;
        _output = context.getSocket().getOutputStream();
        _header = new Header();
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

    private void buildContent() {
        if (_ctx.getRequest().requestIsValid()) {
            _page.addAll(Renderer.render(_ctx.getRequest().getFile()));
        } else {
            send404();
        }
    }

    private void sendImage(final byte[] image) {
        buildHeader();
        try {
            for (final String line : _page) {
                _output.write(line.getBytes());
            }
            _output.write(image, 0, image.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}