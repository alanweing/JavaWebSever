package http;

import com.sun.istack.internal.Nullable;

import java.io.*;

public class Request {

    public enum FILE_TYPE {
        HTML,
        CSS,
        IMG,
        JS
    }

    private FILE_TYPE _requestType = FILE_TYPE.HTML;
    private final InputStream _inputStream;
    private final Context _ctx;
    private HttpParser _parser;

    private boolean _validRequest = false;

    public Request(final Context context) throws IOException {
        _ctx = context;
        _inputStream = context.getSocket().getInputStream();
        _parser = new HttpParser(_inputStream);
        parseRequest();
    }

    public void close() {
        try {
            _inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpParser getParser() {
        return _parser;
    }

    private void parseRequest() {
        HttpParser parser = new HttpParser(_inputStream);
        try {
            parser.parseRequest();
            _requestType = parseRequestType(parser.getHeader("accept"));
            if (_requestType == null) {
                _ctx.setRequestValidity(false);
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
            _ctx.setRequestValidity(false);
        }
    }


    @Nullable
    private FILE_TYPE parseRequestType(final String accept) {
        if (accept == null || accept.equals(""))
            return null;
        String[] acceptedParams = accept.split(",");
        if (acceptedParams.length == 0 || acceptedParams[0].equals("") || !acceptedParams[0].contains("/"))
            return null;
        acceptedParams = acceptedParams[0].split("/");
        final String accepted = acceptedParams[1];
        switch (accepted) {
            case "html":
                return FILE_TYPE.HTML;
            case "css":
                return FILE_TYPE.CSS;
            case "*":
                return FILE_TYPE.IMG;
            case "javascript":
                return FILE_TYPE.JS;
            default:
                return null;
        }
    }

    public boolean requestIsValid() {
        return _validRequest;
    }

    public String getHost() {
        return _parser.getHeader("host");
    }

    public FILE_TYPE getFileType() {
        return _requestType;
    }
}
