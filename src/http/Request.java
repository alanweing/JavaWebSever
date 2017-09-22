package http;

import com.sun.istack.internal.Nullable;
import files.FileManager;

import java.io.*;

public class Request {

    public enum FILE_TYPE {
        HTML,
        CSS,
        IMG,
        JS
    }

    private FILE_TYPE _fileType = FILE_TYPE.HTML;
    private HTTP.RequestType _type = HTTP.RequestType.UNKNOWN;
    private final InputStream _inputStream;
    private final Context _ctx;

    private String _host, _requestedFileString, _parsedFileName;
    private File _requestedFile;
    private boolean _validRequest = false;

    public Request(final Context context) throws IOException {
        _ctx = context;
        _inputStream = context.getSocket().getInputStream();
        parseRequest();
    }

    public void close() {
        try {
            _inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseRequest() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(_inputStream));
        String line;
        int lineCount = 0;
        try {
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                if (line.equals(""))
                    break;
                String[] requestParams = line.split(" ");
                if (lineCount == 0) {
                    _type = HTTP.RequestType.get(requestParams[0]);
                    _requestedFileString = requestParams[1];
                } else if (lineCount == 1) {
                    _host = requestParams[1];
                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseRequestFileType();
        parseFileRequest();
        generateFile();
    }

    private void parseRequestFileType() {
        if (_requestedFileString.contains("css/"))
            _fileType = FILE_TYPE.CSS;
        else if (_requestedFileString.contains("img/"))
            _fileType = FILE_TYPE.IMG;
        else if (_requestedFileString.contains("js/"))
            _fileType = FILE_TYPE.JS;
        else _fileType = FILE_TYPE.HTML;
    }

    private void parseFileRequest() {
        String requested = _requestedFileString;
        requested = requested.replaceFirst("/", "");
        if (requested.equals(""))
            requested = "index.html";
        if (!requested.contains("."))
            requested += ".html";
        _parsedFileName = requested;
    }

    private void generateFile() {
        if (_fileType == FILE_TYPE.HTML)
            _parsedFileName = "views" + File.separator + _parsedFileName;
        if (FileManager.getFile(_parsedFileName) != null) {
            _validRequest = true;
            _requestedFile = FileManager.getFile(_parsedFileName);
        } else {
            _validRequest = false;
        }
    }

    @Nullable
    public File getFile() {
        return _requestedFile;
    }

    public boolean requestIsValid() {
        return _validRequest;
    }

    public String getRequestedFile() {
        return _requestedFileString;
    }

    public String getHost() {
        return _host;
    }

    public FILE_TYPE getFileType() {
        return _fileType;
    }
}
