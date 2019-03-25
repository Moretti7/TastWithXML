package com.filatov;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class FromSAXtoDOM {
    private Document document;
    private List<Point> points;

    public FromSAXtoDOM(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser parser = factory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();
        FileInputStream xml = new FileInputStream(xmlFile);

        parser.parse(xml, xmlHandler);

        List<Point> pointsList = xmlHandler.getPoints();
        this.points = pointsList;

        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element points = document.createElement("points");
        for (Point point : pointsList){
            Element pointTag = document.createElement("point");
            Element x = document.createElement("x");
            Element y = document.createElement("y");
            Attr date = document.createAttribute("date");

            x.setTextContent(Double.toString(point.getX()));
            y.setTextContent(Double.toString(point.getY()));
            date.setValue(point.getDate());

            pointTag.appendChild(x);
            pointTag.appendChild(y);
            pointTag.setAttributeNode(date);

            points.appendChild(pointTag);
        }

        document.appendChild(points);
    }


    public Document getDocument(){
        return document;
    }

    public void toXMLFile(String xmlFileName) throws TransformerException, FileNotFoundException {
        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Properties properties = new Properties();
        properties.put(OutputKeys.INDENT, "yes");
        transformer.setOutputProperties(properties);
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(xmlFileName)));
    }
}
