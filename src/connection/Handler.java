package connection;

import http.Request;
import http.Response;
import logger.IRegistrable;
import logger.Queue;

import java.io.IOException;
import java.net.Socket;

public class Handler implements IRegistrable, Runnable{

    private final Socket _socket;
    private Request _request;
    private Response _response;

    public Handler (final Socket socket) {
        _socket = socket;
    }

    @Override
    public void run() {
        try {
            _request = new Request(_socket.getInputStream());
            Queue.put(this);
            _response = new Response(_socket.getOutputStream(), _request);
            _response.send();
            _response.close();
            _request.close();
            _socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toLog() {
        return _request.getHost() + "\t" +  _request.getRequestedFile() + "\r\n";
    }
}
