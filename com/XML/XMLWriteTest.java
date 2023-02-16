package com_second.XML;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class XMLWriteTest {
    public static void main(String[] args) throws Exception {
        org.w3c.dom.Document doc = newDrawing(600, 400);
        writeDocument(doc,"drawing1.svg");
        writeNewDrawing(600,400,"drawing2.svg");

    }

    private static Random generator = new Random();

    public static org.w3c.dom.Document newDrawing(int drawingWidth, int drawingHeight) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        var namespace = "http://www.w3.org/2000/svg";
        org.w3c.dom.Document doc = builder.newDocument();
        Element svgElement = doc.createElementNS(namespace, "svg");
        doc.appendChild(svgElement);
        svgElement.setAttribute("width","" + drawingWidth);
        svgElement.setAttribute("height","" + drawingHeight);
        int n = 10 + generator.nextInt();
        for (int i = 1;i<= n;i++){
            int x = generator.nextInt(drawingWidth);
            int y = generator.nextInt(drawingHeight);
            int width = generator.nextInt(drawingWidth - x);
            int height = generator.nextInt(drawingHeight - y);
            int r = generator.nextInt(256);
            int g = generator.nextInt(256);
            int b = generator.nextInt(256);
            Element rectElement = doc.createElementNS(namespace, "rect");
            rectElement.setAttribute("x",""+ x);
            rectElement.setAttribute("y",""+ y);
            rectElement.setAttribute("width",""+ width);
            rectElement.setAttribute("height",""+ height);
            rectElement.setAttribute("fill",String.format("#%02x%02x%02x",r,g,b));
            svgElement.appendChild(rectElement);
        }
        return  doc;
    }

    public static void writeDocument(org.w3c.dom.Document doc,String filename) throws TransformerException, IOException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
                "http://www.w3.org/2000/CR-SVG-20000802/DTD/svg-20000802.dtd");
        t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
                "-//W3C//DTD SVG 20000802//EN");
        t.setOutputProperty(OutputKeys.INDENT,"yes");
        t.setOutputProperty(OutputKeys.METHOD,"xml");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
        t.transform(new DOMSource( doc),new StreamResult(Files.newOutputStream(Paths.get(filename))));

    }

    public static void writeNewDrawing(int drawingWidth,int drawingHeight,String filename) throws IOException, XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(Files.newOutputStream(Paths.get(filename)));
        writer.writeStartDocument();
        writer.writeDTD("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20000802//EN\" " + "\"http://www.w3.org/TR/2000/CR-SVG-20000802/DTD/svg-20000802.dtd\">");
        writer.writeStartElement("svg");
        writer.writeDefaultNamespace("http://www.w3.org/2000/svg");
        writer.writeAttribute("width","" + drawingWidth);
        writer.writeAttribute("height","" + drawingHeight);
        int n = 10 + generator.nextInt(20);
        for (int i = 1;i<= n;i++){
            int x = generator.nextInt(drawingWidth);
            int y = generator.nextInt(drawingHeight);
            int width = generator.nextInt(drawingWidth - x);
            int height = generator.nextInt(drawingHeight - y);
            int r = generator.nextInt(256);
            int g = generator.nextInt(256);
            int b = generator.nextInt(256);
            writer.writeEmptyElement("rect");
            writer.writeAttribute("x",""+ x);
            writer.writeAttribute("y",""+ y);
            writer.writeAttribute("width",""+ width);
            writer.writeAttribute("height",""+ height);
            writer.writeAttribute("fill",String.format("#%02x%02x%02x",r,g,b));
        }
        writer.writeEndDocument();

    }
}
