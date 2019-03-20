package com.filatov;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();
        FileInputStream xml = new FileInputStream("resources/data.xml");

        parser.parse(xml, xmlHandler);

        System.out.println(xmlHandler.getPoints());
    }
}
