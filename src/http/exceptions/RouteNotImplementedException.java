package http.exceptions;

public class RouteNotImplementedException extends Exception {
    private static final String DESCRIPTION = "There is no implemented route for this request!";
    public RouteNotImplementedException(final String route) {
        super(DESCRIPTION.concat(" [").concat(route).concat("]."));
    }
}
