package logger;

import com.sun.istack.internal.Nullable;
import files.FileWriter;
import http.Server;

import java.io.File;
import java.util.ArrayList;

public final class Queue implements Runnable {

    private static final String ROOT_PATH = new File(System.getProperty("user.dir")).getAbsolutePath();
    private static final ArrayList<IRegistrable> _queue = new ArrayList<>();
    private static final Object _queueLock = new Object();

    @Override
    public void run() {
        while (Server.isOnline()) {
            synchronized (_queueLock) {
                if (_queue.isEmpty()) {
                    try {
                        _queueLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                final IRegistrable registrable = get();
                FileWriter.getInstance(ROOT_PATH + File.separator + registrable.getLogName()).write(registrable.toLog().getBytes());
            }
        }
    }

    public static void put(final IRegistrable registrable) {
        synchronized (_queueLock) {
            _queue.add(registrable);
            // notify all??
            _queueLock.notify();
        }
    }

    @Nullable
    private static IRegistrable get() {
        synchronized (_queueLock) {
            return _queue.remove(0);
        }
    }

}
