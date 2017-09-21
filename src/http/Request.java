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

    private String _host, _file, _parsedFileName;
    private File _requestedFile;
    private boolean _validRequest = false;

    public Request(final InputStream inputStream) {
        _inputStream = inputStream;
        parseRequest();
    }

    public void close() {
        try {
            _inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRequestedFile() {
        return _file;
    }

    public String getHost() {
        return _host;
    }

    private void parseFileType() {
        if (_file.contains("css"))
            _fileType = FILE_TYPE.CSS;
        else if (_file.contains("img"))
            _fileType = FILE_TYPE.IMG;
        else if (_file.contains("js"))
            _fileType = FILE_TYPE.JS;
    }

    public FILE_TYPE getFileType() {
        return _fileType;
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
                    _file = requestParams[1];
                } else if (lineCount == 1) {
                    _host = requestParams[1];
                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseFileType();
        parseFileRequest();
        generateFile();
    }

    private void parseFileRequest() {
        String requested = _file;
        requested = requested.replaceFirst("/", "");
        if (requested.equals(""))
            requested = "index.html";
        if (!requested.contains("."))
            requested += ".html";
//        if (requested.contains("css") || requested.contains("img")){
//            requested = requested.substring(requested.indexOf("/")+1);
//        }
        _parsedFileName = requested;
    }

    private void generateFile() {
        File folder = FileManager.getFolderByType(getFileType());
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
}
