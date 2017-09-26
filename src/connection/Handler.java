package connection;

import http.Context;
import http.Router;
import http.exceptions.MethodNotAllowedException;
import http.exceptions.RouteNotImplementedException;
import logger.IRegistrable;
import logger.Queue;

import java.io.IOException;
import java.net.Socket;

public class Handler implements IRegistrable, Runnable {

    private final Socket _socket;
    private Context _ctx;

    public Handler (final Socket socket) {
        _socket = socket;
    }

    @Override
    public void run() {
        try {
            _ctx = new Context(_socket);
            Queue.put(this);
            try {
                Router.handleRequest(_ctx);
            } catch (RouteNotImplementedException e) {
                // send 404
                e.printStackTrace();
            } catch (MethodNotAllowedException e) {
                // send message ?
                e.printStackTrace();
            }
            _ctx.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toLog() {
        return _ctx.getRequest().getHost() + "\t" +  _ctx.getRequest().getParser().getRequestURL() + "\r\n";
    }
}