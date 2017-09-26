import http.Server;


public class Main {
    private static final String INPE_URL = "http://servicos.cptec.inpe.br/XML/estacao/SBSM/condicoesAtuais.xml";

    public static void main(final String... args) {
        Server.autoInitialize();
    }

}
