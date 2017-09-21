package http;

import java.util.HashMap;
import java.util.Map;

public class HTTP {
    public enum StatusCode {
        OK(200),
        NOT_FOUND(404);

        private final int _code;

        StatusCode(final int code) {
            _code = code;
        }

        public int getCode() {return _code;}

        private static final Map<Integer, StatusCode> _lookup = new HashMap<>();

        static {
            for (StatusCode code : StatusCode.values()) {
                _lookup.put(code.getCode(), code);
            }
        }

        public static StatusCode get(final int code) {
            return _lookup.get(code);
        }
    }

    public enum RequestType {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE"),
        UPDATE("UPDATE"),
        UNKNOWN("");

        private final String _type;

        RequestType(final String type) {
            _type = type;
        }

        public String getType() {
            return _type;
        }

        private static final Map<String, RequestType> _lookup = new HashMap<>();

        static {
            for (RequestType type : RequestType.values()) {
                _lookup.put(type.getType(), type);
            }
        }

        public static RequestType get(final String type) {
            return _lookup.get(type);
        }
    }
}
