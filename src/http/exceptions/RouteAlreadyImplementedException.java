package http.exceptions;

public class RouteAlreadyImplementedException extends Exception {
    private static final String DESCRIPTION = "This route is already registered!";
    public RouteAlreadyImplementedException() {
        super(DESCRIPTION);
    }
}
