package http;

public final class Context {
    private final Request _request;
    private final Response _response;
    public Context(final Request request, final Response response) {
        _request = request;
        _response = response;
    }
}
