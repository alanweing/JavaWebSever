package http;

import java.util.HashMap;

public class Header {

    public enum ContentType {
        HTML("text/html"),
        JAVASCRIPT("text/javascript"),
        CSS("text/css"),
        IMG("image/png");
        private final String _type;
        ContentType(final String type) {
            _type = type;
        }
        public String getType() {
            return _type;
        }
    }

    private static final String[][] PARAMS = new String[][] {
            {"HTTP/1.1", "200 OK"},
            {"Content-Type", "text/html"}
    };
    private final HashMap<String, String> _header = new HashMap<>();

    public Header() {
        for (String[] line : PARAMS) {
            _header.put(line[0], line[1]);
        }
    }

    public Header setContentType(final String contentType) {
        _header.put("Content-Type", contentType);
        return this;
    }

    public Header setContentType(final ContentType contentType) {
        return setContentType(contentType.getType());
    }

    public Header setStatus(final short status) {
        _header.put("HTTP/1.1", Short.toString(status).concat(" ").concat(HttpParser.getHttpReply(status)));
        return this;
    }

    public String[] build() {
        final String[] header = new String[_header.size()+1];
        int i = 0;
        for (String k : _header.keySet()) {
            header[i] = k;
            if (!k.equals("HTTP/1.1"))
                header[i] += ":";
            header[i] += " ".concat(_header.get(k)).concat("\r\n");
            i++;
        }
        header[i] = "\r\n";
        return header;
    }

}
