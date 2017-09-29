package connection;

import http.Context;
import http.Router;
import http.exceptions.FileNotFoundException;
import http.exceptions.MethodNotAllowedException;
import http.exceptions.RouteNotImplementedException;
import logger.IRegistrable;
import logger.Queue;
import util.Debug;

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
            try {
                Router.handleRequest(_ctx);
            } catch (RouteNotImplementedException | FileNotFoundException e) {
                // send 404
                _ctx.getResponse().send404();
                Debug.log(e.getMessage());
            } catch (MethodNotAllowedException e) {
                // send message ?
                _ctx.getResponse().send500();
                Debug.log(e.getMessage());
            }
            Queue.put(this);
            _ctx.close();
        } catch (IOException e) {
            Debug.log(e.getMessage());
        }
    }

    @Override
    public String toLog() {
        return _ctx.getRequest().getHost() + "\t" +
                _ctx.getRequest().getParser().getRequestURL() + "\t" +
                _ctx.getRequest().getParser().getMethod() + "\t" +
                _ctx.getRequest().getFileType().name() +
                "\r\n";
    }

    @Override
    public String getLogName() {
        return "log.txt";
    }
}