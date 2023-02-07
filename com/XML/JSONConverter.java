package com_second.XML;

import com_second.Stream.CreatingStreams;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * This program displays an XML document as a tree in JSON format
 */
public class JSONConverter {
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
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(filename);
        Element root = doc.getDocumentElement();
        System.out.println(convert(root,0));

    }


    public static StringBuilder convert(Node node, int level){
        if (node instanceof Element)
            return elementObject((Element) node,level);
        else if (node instanceof CharacterData) {
            return characterString((CharacterData) node,level);
        }
        else{
            return pad(new StringBuilder(),level).append(jsonEscape(node.getClass().getName()));
        }
    }

    private static StringBuilder elementObject(Element elem,int level){
        StringBuilder result = new StringBuilder();
        pad(result,level).append("{\n");
        pad(result,level + 1).append("\"name\": ");
        result.append(jsonEscape(elem.getTagName()));
        NamedNodeMap attrs = elem.getAttributes();
        if (attrs.getLength() > 0) {
            pad(result.append(",\n"), level + 1).append("\"attributes\": ");
            result.append(attributeObject(attrs));
        }
        NodeList children = elem.getChildNodes();
        if (children.getLength() > 0){
            pad(result.append(",\n"),level + 1).append("\"children\": [\n");
            for (int i =0;i < children.getLength();i ++){
                if (i > 0) result.append(",\n");
                result.append(convert(children.item(i),level + 2));
            }
            result.append("\n");
            pad(result, level + 1).append("]\n");
        }
        pad(result, level).append("}");
        return result;
    }

    private static StringBuilder jsonEscape(String str){
        StringBuilder result = new StringBuilder("\"");
        for (int i = 0;i < str.length();i++){
            char ch = str.charAt(i);
            String s = replacements.get(ch);
            if (s == null) result.append(ch);
            else result.append(s);
        }
        result.append("\"");
        return result;
    }

    private static Map<Character, String> replacements = Map.of('\b',"\\b",'\f',
            "\\f",'\n',"\\n",'\r',"\\r",'\t',"\\t",
            '"',"\\\"",'\\',"\\\\");
    private static StringBuilder pad(StringBuilder builder, int level){
        for (int i =0 ;i < level; i++) builder.append(" ");
        return builder;
    }

    private static StringBuilder attributeObject(NamedNodeMap attrs){
        StringBuilder stringBuilder = new StringBuilder("{");
        for (int i = 0; i < attrs.getLength();i++){
            if (i > 0) stringBuilder.append(", ");
            stringBuilder.append(jsonEscape(attrs.item(i).getNodeName()));
            stringBuilder.append(": ");
            stringBuilder.append(jsonEscape(attrs.item(i).getNodeValue()));
        }
        stringBuilder.append("}");
        return stringBuilder;
    }

    private static  StringBuilder characterString(CharacterData node,int level){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder data = jsonEscape(node.getData());
        if (node instanceof Comment) data.insert(1, "Comment: ");
        pad(stringBuilder,level).append(data);
        return stringBuilder;
    }
}
