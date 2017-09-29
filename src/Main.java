import connection.INPERequest;
import controllers.IndexController;
import controllers.WeatherController;
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
            Server.autoInitialize();
        } catch (RouteAlreadyImplementedException e) {
            Debug.log(e.getMessage());;
        }
//        HashMap<String, String> map = new HashMap<>();
//        map.put("aloha", "trocaAloha");
//        map.put("teste", "trocou o test");
//        map.put("sera", "sera que vai trocar");
//        try {
//            String[] s = AWEngine.parseFile(new String[] {"dasnasdjkdas {{ aloha }} dnsakjasdn sdjnnjkasd asdjnjkads {{tesste}} askjdjkds {{ sera }}", "{{sera}} {{sera}} {{teste}}"}, map);
//            for (final String str : s) {
//                System.out.println(str);
//            }
//        } catch (KeyNotDefinedException e) {
//            e.printStackTrace();
//        }
    }
}