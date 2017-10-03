import connection.INPERequest;
import controllers.IndexController;
import controllers.WeatherController;
import controllers.WeatherRESTController;
import http.GET;
import http.Router;
import http.Server;
import http.exceptions.RouteAlreadyImplementedException;
import util.AWEngine;
import util.Debug;
import util.KeyNotDefinedException;

import java.util.HashMap;


public class Main {

    public static void main(final String... args) {
        try {
            Router.get("/", new IndexController());
            Router.get("/weather", new WeatherController());
            Router.get("/data/weather", new WeatherRESTController());
            Server.autoInitialize();
        } catch (RouteAlreadyImplementedException e) {
            Debug.log(e);
        }
    }
}