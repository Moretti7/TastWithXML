package com.filatov;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DOM {
    private DocumentBuilderFactory factory;
    private Document document;
    private DocumentBuilder documentBuilder;

    public DOM() throws ParserConfigurationException, IOException, SAXException {
        factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        documentBuilder = factory.newDocumentBuilder();
        document = documentBuilder.parse(new File("resources/data.xml"));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("resources/points.xsd"));

        Validator validator = schema.newValidator();
        Source source = new StreamSource(new FileInputStream("resources/data.xml"));
        validator.validate(source);
    }

    public void replace(Point oldPoint, Point newPoint) {
        NodeList xList = document.getDocumentElement().getElementsByTagName("x");
        NodeList yList = document.getDocumentElement().getElementsByTagName("y");
        NodeList points = document.getDocumentElement().getElementsByTagName("point");

        int length = xList.getLength();

        for (int i = 0; i < length; i++) {
            Node xItem = xList.item(i);
            Node yItem = yList.item(i);
            if (xItem instanceof Element) {
//                System.out.println(">>> xItem: " + xItem.getTextContent() + " yItem: " + yItem.getTextContent());
                double x = Double.valueOf(xItem.getTextContent());
                double y = Double.valueOf(yItem.getTextContent());
                Point point = new Point(x, y, points.item(i).getAttributes().item(0).getTextContent());

                if (oldPoint.equals(point)) {
                    xItem.setTextContent(Double.toString(newPoint.getX()));
                    yItem.setTextContent(Double.toString(newPoint.getY()));
                    points.item(i).getAttributes().item(0).setTextContent(newPoint.getDate());
//                    System.out.println(">>> new xItem: " + xItem.getTextContent() + " new yItem: " + yItem.getTextContent() + " new date: " + points.item(i).getAttributes().item(0).getTextContent());
                }
            }
        }
    }


    public void addNewPoint(Point point) {
        //document.creatElement
        //document.appendChild
        //document.creatAttribute
        //element.setAttributeNode

        NodeList pointsList = document.getElementsByTagName("points");

        Node points = pointsList.item(0);
//        Element newPoint = document.createElement("point");
//        Element x = document.createElement("x");
//        Element y = document.createElement("y");
//        Attr date = document.createAttribute("date");
//
//        date.setValue(point.getDate());
//        x.setTextContent(Double.toString(point.getX()));
//        y.setTextContent(Double.toString(point.getY()));
//
//        newPoint.appendChild(x);
//        newPoint.appendChild(y);
//        newPoint.setAttributeNode(date);

        Element newPoint = creatPoint(point, document);
        points.appendChild(newPoint);
    }

    public void insert(Point point, int position) {
        position--;
        NodeList pointsNodeList = document.getElementsByTagName("point");
        List<Node> points = new LinkedList<>();

        int length = pointsNodeList.getLength();
        for (int i = 0; i < length; i++) {
            points.add(pointsNodeList.item(i));
        }

        Element newPoint = creatPoint(point, document);

        Node pointsNode = document.getElementsByTagName("points").item(0);
        for (int i = 0; i < length; i++) {
            pointsNode.removeChild(points.get(i));
        }

        points.add(position, newPoint);

        for (Node element : points) {
            pointsNode.appendChild(element);
        }

    }

    public void delete(Point point) {
        Element pointForDelete = creatPoint(point, document);

        Point point1 = new Point();

        NodeList points = document.getElementsByTagName("point");
        NodeList xList = document.getElementsByTagName("x");
        NodeList yList = document.getElementsByTagName("y");


        int length = points.getLength();
        for (int i = 0; i < length; i++) {
            point1.setDate(points.item(i).getAttributes().item(0).getTextContent());
            point1.setX(Double.valueOf(xList.item(i).getTextContent()));
            point1.setY(Double.valueOf(yList.item(i).getTextContent()));

            if (point1.equals(point)) {
                document.getElementsByTagName("points").item(0).removeChild(points.item(i));
                break;
            }
        }


    }

    public String getData() {
        StringBuilder stringBuilder = new StringBuilder();

        NodeList xList = document.getElementsByTagName("x");
        NodeList yList = document.getElementsByTagName("y");
        NodeList points = document.getElementsByTagName("point");

        int length = xList.getLength();

        for (int i = 0; i < length; i++) {
            stringBuilder.append("[x=" + xList.item(i).getTextContent() +
                    "; y=" + yList.item(i).getTextContent() +
                    " " + points.item(i).getAttributes().item(0) + "]\n");
        }
        return stringBuilder.toString();
    }

    private Element creatPoint(Point point, Document document) {
        Element newPoint = document.createElement("point");
        Element x = document.createElement("x");
        Element y = document.createElement("y");
        Attr attr = document.createAttribute("date");

        attr.setValue(point.getDate());
        x.setTextContent(Double.toString(point.getX()));
        y.setTextContent(Double.toString(point.getY()));

        newPoint.setAttributeNode(attr);
        newPoint.appendChild(x);
        newPoint.appendChild(y);

        return newPoint;
    }

    public void toXML(String newXMLFileName) throws TransformerException, FileNotFoundException {
        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
        Transformer transformer = transformerFactory.newTransformer();
//        Properties properties = new Properties();
//        properties.put(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperties(properties);

        DOMSource source = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new FileOutputStream("resources/" + newXMLFileName));
        transformer.transform(source, streamResult);
    }
}
