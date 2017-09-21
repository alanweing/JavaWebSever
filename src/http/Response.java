package http;

import files.FileManager;

import java.io.*;
import java.util.ArrayList;

public class Response {
    private final OutputStream _output;
    private final PrintWriter _writer;
    private final Request _request;

    private HTTP.StatusCode _statusCode = HTTP.StatusCode.OK;
    private ArrayList<String> _page = new ArrayList<>();

    public Response(final OutputStream outputStream, final Request request) {
        _output = outputStream;
        _request = request;
        _writer = new PrintWriter(outputStream, true);
    }

    public void setStatusCode(HTTP.StatusCode code) {
        _statusCode = code;
    }

    public void send() {
        if (_request.getFileType() == Request.FILE_TYPE.IMG) {
            sendImage(Renderer.renderImage(_request.getFile()));
            return;
        }
        buildContent();
        buildHeader();
        for (final String line : _page) {
            _writer.write(line);
        }
        _writer.flush();
    }

    public void close() {
        try {
            _output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        _writer.close();
    }

    private void buildHeader() {
        buildHeader(0);
    }

    private void buildHeader(final int contentLength) {
        String contentType = "text/";
        switch (_request.getFileType()) {
            case HTML:
                contentType += "html";
                break;
            case CSS:
                contentType += "css";
                break;
            case IMG:
                String s = _request.getRequestedFile();
                contentType = "image/" + s.substring(s.indexOf(".")+1);
                break;
            case JS:
                contentType += "javascript";
                break;
        }

        _page.add(0, "\r\n\r\n");
        if (contentLength != 0)
            _page.add(0, "Content length: " + contentLength + "\r\n");
        _page.add(0, "Content-Type: " + contentType + "\r\n");
//        if (_request.getFileType() == Request.FILE_TYPE.IMG)
//            _page.add(0, "Content-Encoding: base64\r\n");
        _page.add(0, "HTTP/1.1 " + _statusCode.getCode() + " OK\r\n");
    }

    private void buildContent() {
        if (_request.requestIsValid()) {
            _page.addAll(Renderer.render(_request.getFile()));
        } else {
            setStatusCode(HTTP.StatusCode.NOT_FOUND);
            if (_request.getFileType() == Request.FILE_TYPE.HTML)
                _page.addAll(Renderer.render(FileManager.getFile("404_s.html")));
        }
    }

    private void sendImage(final byte[] image) {
        buildHeader(image.length);
        DataOutputStream dos = new DataOutputStream(_output);
        try {
            for (final String line : _page) {
                dos.writeBytes(line);
            }
            dos.write(image);
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}