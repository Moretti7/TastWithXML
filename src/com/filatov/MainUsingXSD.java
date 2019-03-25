package com.filatov;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;

public class MainUsingXSD {
    public static void main(String[] args) throws Exception {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("resources/points.xsd"));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new FileInputStream("resources/data.xml")));

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();
        FileInputStream xml = new FileInputStream("resources/data.xml");

        parser.parse(xml, xmlHandler);

        System.out.println(xmlHandler.getPoints());
    }
}
