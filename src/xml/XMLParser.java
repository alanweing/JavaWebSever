package xml;

import com.sun.istack.internal.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class XMLParser {
    private final Document _document;
    private HashMap<String, String> _childrenMap = new HashMap<>();
    private ArrayList<Node> _children = new ArrayList<>();

    public XMLParser(final String toParse) {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(toParse)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        _document = doc;
    }

    protected Document getDocument() {
        return _document;
    }

    protected ArrayList<Node> getChildren() {
        return _children;
    }

    @Nullable
    protected String getElementValue(final String element) {
        return _childrenMap.get(element);
    }

    protected void parse() {
        final NodeList children = _document.getDocumentElement().getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            final Node n = children.item(i);
            if (n instanceof Element) {
                _children.add(n);
                _childrenMap.put(((Element) n).getTagName(), n.getTextContent());
            }
        }
    }

    public static String clearXMLHeader(final String xmlString) {
        return xmlString.replace("<?xml version='1.0' encoding='ISO-8859-1'?>", "");
    }
}
