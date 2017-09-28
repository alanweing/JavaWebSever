package xml;

public class INPEXMLParser extends XMLParser {

    public enum CHILD {
        codigo,
        atualizacao,
        pressao,
        temperatura,
        tempo,
        tempo_desc,
        umidade,
        vento_dir,
        vento_int,
        visibilidade;
    }

    public INPEXMLParser(String toParse) {
        super(toParse);
        parse();
    }

    public String getValue(final CHILD child) {
        return getElementValue(child.name());
    }
}
