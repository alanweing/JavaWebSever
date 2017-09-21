package logger;

import com.sun.istack.internal.Nullable;
import files.FileWriter;
import http.Server;

import java.io.File;
import java.util.ArrayList;

public final class Queue implements Runnable {

    private static final String LOG_PATH = new File(System.getProperty("user.dir"), "log.txt").getAbsolutePath();
    private static final ArrayList<IRegistrable> _queue = new ArrayList<>();
    private static final Object
            _queueLock = new Object(),
            _emptyLock = new Object();

    @Override
    public void run() {
        while (Server.isOnline()) {
            synchronized (_emptyLock) {
                if (_queue.isEmpty()) {
                    try {
                        _emptyLock.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                FileWriter.getInstance(LOG_PATH).write(get().toLog().getBytes());
            }
        }
    }

    public static void put(final IRegistrable registrable) {
        synchronized (_queueLock) {
            _queue.add(registrable);
            synchronized (_emptyLock) {
                _emptyLock.notifyAll();
            }
        }
    }

    @Nullable
    private static IRegistrable get() {
        synchronized (_queueLock) {
            return _queue.remove(0);
        }
    }

}
