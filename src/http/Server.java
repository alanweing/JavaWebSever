package http;

import connection.Handler;
import logger.Queue;
import files.FileManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final short PORT = 3001;
    private static ServerSocket _serverSocket;
    private static volatile boolean _online = false;

    public static void shutdown() {
        _online = false;
    }

    public static void initialize(final short port) {
        try {
            _serverSocket = new ServerSocket(port);
            _online = true;
        } catch (IOException e) {
            _serverSocket = null;
            e.printStackTrace();
        }
    }

    public static void initialize() {
        initialize(PORT);
    }

    public static void autoInitialize() {
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
            System.out.println("Server is listening");
            while (_online) {
                try {
                    Socket s = _serverSocket.accept();
                    new Thread(new Handler(s)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static boolean isOnline() {
        return _online;
    }
}