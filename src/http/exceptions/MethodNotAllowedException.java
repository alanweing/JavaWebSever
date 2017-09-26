package http.exceptions;

import http.Router;

public class MethodNotAllowedException extends Exception {
    private static final String DESCRIPTION = "The method is not allowed in this route! ";
    public MethodNotAllowedException(final String route, final Router.Method method) {
        super(DESCRIPTION.concat(" [Method: ").concat(method.name()).concat(", Route: ").concat(route).concat("]."));
    }
}
