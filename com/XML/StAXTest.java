package com_second.XML;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class StAXTest {
    public static void main(String[] args) throws Exception {
        String urlString;
        if (args.length == 0){
            urlString = "http://www.w3c.org";
            System.out.println("Using " + urlString);
        }else
            urlString = args[0];

        var url = new URL(urlString);
        InputStream in = url.openStream();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parse = factory.createXMLStreamReader(in);
        while (parse.hasNext()){
            int event = parse.next();
            if (event == XMLStreamConstants.START_ELEMENT){
                if (parse.getLocalName().equals("a")){
                    String href = parse.getAttributeValue(null, "href");
                    if (href != null)
                        System.out.println(href);
                }
            }
        }


    }
}
