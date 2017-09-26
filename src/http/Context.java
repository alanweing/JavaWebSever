package http;

import java.io.IOException;
import java.net.Socket;

public final class Context {
    private final Request _request;
    private final Response _response;
    private final Socket _socket;
    private boolean _validRequest = true;

    public Context(final Socket socket) throws IOException {
        _socket = socket;
        _request = new Request(this);
        _response = new Response(this);
    }

    public void close() {
        _response.close();
        _request.close();
        try {
            _socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRequestValidity(final boolean isValid) {
        _validRequest = isValid;
    }

    public boolean isRequestValid() {
        return _validRequest;
    }

    public Response getResponse() {
        return _response;
    }

    public Request getRequest() {
        return _request;
    }

    public Socket getSocket() {
        return _socket;
    }
}
