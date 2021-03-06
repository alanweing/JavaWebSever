package http;

import connection.Handler;
import connection.INPERequest;
import logger.Queue;
import files.FileManager;
import util.Debug;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final short PORT = 3010;
    public static final boolean USE_CACHE = false;

    private static ServerSocket _serverSocket;
    private static volatile boolean _online = false;

    public static void shutdown() {
        _online = false;
    }

    public static void initialize(final short port) {
        Runtime.getRuntime().addShutdownHook(new Thread(Server::shutdown));
        try {
            _serverSocket = new ServerSocket(port);
            _online = true;
        } catch (IOException e) {
            _serverSocket = null;
            Debug.log(e);
        }
    }

    public static void initialize() {
        initialize(PORT);
    }

    public static void autoInitialize() {
        INPERequest.initializeRequests();
        FileManager.initialize();
            initialize();
        new Thread(new Queue()).start();
        listen();
    }

    public static void listen() {
        if (_serverSocket == null) {
            return;
        }
        new Thread(() -> {
            System.out.println("Server is listening on PORT: ".concat(Integer.toString(PORT)));
            while (_online) {
                try {
                    Socket s = _serverSocket.accept();
                    new Thread(new Handler(s)).start();
                } catch (IOException e) {
                    Debug.log(e);
                }
            }
        }).start();
    }

    public static boolean isOnline() {
        return _online;
    }
}