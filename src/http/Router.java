package http;

import controllers.IController;
import http.exceptions.MethodNotAllowedException;
import http.exceptions.RouteAlreadyImplementedException;
import http.exceptions.RouteNotImplementedException;

import java.util.HashMap;

public abstract class Router {

    public enum Method {
        GET, POST
    }

    private static final HashMap<String, HashMap<Method, IController>> _routes = new HashMap<>();

    private static void put(final String route, final Method method, final IController controller) throws RouteAlreadyImplementedException {
        if (_routes.containsKey(route)) {
            if (_routes.get(route).containsKey(method))
                throw new RouteAlreadyImplementedException();
            _routes.get(route).put(method, controller);
        } else {
            HashMap<Method, IController> hm = new HashMap<>();
            hm.put(method, controller);
            _routes.put(route, hm);
        }
    }

    private static Method getMethod(final String method) {
        switch (method) {
            case "GET":
            case "get":
            default:
                return Method.GET;
            case "POST":
                return Method.POST;
        }
    }

    public static void get(final String route, final IController controller) throws RouteAlreadyImplementedException {
        put(route, Method.GET, controller);
    }

    public static void post(final String route, final IController controller) throws RouteAlreadyImplementedException {
        put(route, Method.POST, controller);
    }

    public static void handleRequest(final Context ctx) throws RouteNotImplementedException, MethodNotAllowedException {
        final String route = ctx.getRequest().getParser().getRequestURL();
        final Method method = getMethod(ctx.getRequest().getParser().getMethod());

        if (!_routes.containsKey(route))
            throw new RouteNotImplementedException(route);
        if (!_routes.get(route).containsKey(method))
            throw new MethodNotAllowedException(route, method);
        _routes.get(route).get(method).handleConnection(ctx);
    }

}
