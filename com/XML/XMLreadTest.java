
package com_second.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class XMLreadTest {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String filename;
        if (args.length == 0){
            try (var in = new Scanner(System.in)){
                System.out.print("Input file: ");
                filename = in.nextLine();
            }
        }
        else
            filename = args[0];

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);

        if (filename.contains("-schema")){
            factory.setNamespaceAware(true);
            final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
            final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
            factory.setAttribute(JAXP_SCHEMA_LANGUAGE,W3C_XML_SCHEMA);
        }
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.err.println("Warning: " + exception.getMessage());
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.err.println("Error: " + exception.getMessage());
                System.exit(0);
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.err.println("fatalError: " + exception.getMessage());
                System.exit(0);
            }
        });

        Document doc = builder.parse(filename);
        Map<String, Object> config = parseConfig(doc.getDocumentElement());
        System.out.println(config);
    }

    private static Map<String,Object> parseConfig(Element e) {
        
    }
}
