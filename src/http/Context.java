package http;

import java.io.IOException;
import java.net.Socket;

public final class Context {
    private final Request _request;
    private final Response _response;
    private final Socket _socket;

    public Context(final Socket socket, final Request request, final Response response) {
        _request = request;
        _response = response;
        _socket = socket;
    }

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

    public void sendResponse() {
        _response.send();
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
