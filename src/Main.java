import connection.INPERequest;
import controllers.IndexController;
import http.GET;
import http.Router;
import http.Server;
import http.exceptions.RouteAlreadyImplementedException;


public class Main {

    public static void main(final String... args) {
//        try {
//            Router.get("/", new IndexController());
//            Server.autoInitialize();
//        } catch (RouteAlreadyImplementedException e) {
//            e.printStackTrace();
//        }
        INPERequest.initializeRequests();
        }
}