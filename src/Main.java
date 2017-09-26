import controllers.IndexController;
import http.Router;
import http.Server;
import http.exceptions.RouteAlreadyImplementedException;


public class Main {
    private static final String INPE_URL = "http://servicos.cptec.inpe.br/XML/estacao/SBSM/condicoesAtuais.xml";

    public static void main(final String... args) {
        try {
            Router.get("/", new IndexController());
            Server.autoInitialize();
        } catch (RouteAlreadyImplementedException e) {
            e.printStackTrace();
        }
    }
}