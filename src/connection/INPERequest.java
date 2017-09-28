package connection;

import com.sun.istack.internal.Nullable;
import http.GET;
import xml.INPEXMLParser;

public final class INPERequest implements Runnable {
    private static final String INPE_URL = "http://servicos.cptec.inpe.br/XML/estacao/SBSM/condicoesAtuais.xml";
    private static final byte REQUEST_INTERVAL = 30;

    private static INPERequest _instance;
    private final Object _lastRequestLock = new Object(),
            _parserLock = new Object();
    private final GET _get;

    private INPEXMLParser _inpeParser;

    private String _lastRequest = null;

    public static void initializeRequests() {
        getInstance();
    }

    public static INPERequest getInstance() {
        if (_instance == null)
            _instance = new INPERequest();
        return _instance;
    }

    private INPERequest() {
        _get = new GET(INPE_URL);
        new Thread(this).start();
    }

    @Nullable
    public String getLastRequest() {
        synchronized (_lastRequestLock) {
            return _lastRequest;
        }
    }

    @Nullable
    public INPEXMLParser getParser() {
        synchronized (_parserLock) {
            return _inpeParser;
        }
    }

    private void doRequest() {
        synchronized (_lastRequestLock) {
            _lastRequest = _get.request();
        } synchronized (_parserLock) {
            _inpeParser = new INPEXMLParser(_lastRequest);
        }
    }



    @Override
    public void run() {
        doRequest();
        while (true) {
            try {
                Thread.sleep(REQUEST_INTERVAL * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            doRequest();
        }
    }
}
