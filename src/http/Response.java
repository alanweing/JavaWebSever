package http;

import files.FileManager;

import java.io.*;
import java.util.ArrayList;

public class Response {
    private final OutputStream _output;
    private final Context _ctx;

    private HTTP.StatusCode _statusCode = HTTP.StatusCode.OK;
    private ArrayList<String> _page = new ArrayList<>();

    public Response(final Context context) throws IOException {
        _ctx = context;
        _output = context.getSocket().getOutputStream();
    }

    public void setStatusCode(HTTP.StatusCode code) {
        _statusCode = code;
    }

    public void send() {
        if (_ctx.getRequest().getFileType() == Request.FILE_TYPE.IMG) {
            if (_ctx.getRequest().requestIsValid()) {
                sendImage(Renderer.renderImage(_ctx.getRequest().getFile()));
                return;
            }
        }

        buildContent();
        buildHeader();
        for (final String line : _page) {
            try {
                _output.write(line.getBytes());
                _output.flush();
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

    private void buildHeader() {
        buildHeader(0);
    }

    private void buildHeader(final int contentLength) {
        String contentType = "text/";
        if (_ctx.getRequest().requestIsValid()) {
            switch (_ctx.getRequest().getFileType()) {
                case HTML:
                    contentType += "html";
                    break;
                case CSS:
                    contentType += "css";
                    break;
                case IMG:
                    String s = _ctx.getRequest().getRequestedFile();
                    contentType = "image/" + s.substring(s.indexOf(".")+1);
                    break;
                case JS:
                    contentType += "javascript";
                    break;
            }
        } else {
            contentType += "html";
        }


        _page.add(0, "\r\n\r\n");
        if (contentLength != 0)
            _page.add(0, "Content-Length: " + contentLength + "\r\n");
        _page.add(0, "Content-Type: " + contentType + "\r\n");
//        if (_ctx.getRequest().getFileType() == Request.FILE_TYPE.IMG && _ctx.getRequest().requestIsValid())
//            _page.add(0, "Content-Encoding: base64\r\n");
        _page.add(0, "HTTP/1.1 " + _statusCode.getCode() + " OK\r\n");
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

    private void send404() {
        setStatusCode(HTTP.StatusCode.NOT_FOUND);
        _page.addAll(Renderer.render(FileManager.getFile("404.html")));
//        if (_ctx.getRequest().getFileType() == Request.FILE_TYPE.HTML)
    }
}