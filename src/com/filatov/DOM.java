package com.filatov;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DOM {
    private DocumentBuilderFactory factory;
    private Document document;

    public DOM() throws ParserConfigurationException, IOException, SAXException {
        factory = DocumentBuilderFactory.newInstance();
        document = factory.newDocumentBuilder().parse(new File("resources/data.xml"));
        factory.setIgnoringElementContentWhitespace(true);
    }

    public void replace(Point oldPoint, Point newPoint){
        NodeList xList = document.getDocumentElement().getElementsByTagName("x");
        NodeList yList = document.getDocumentElement().getElementsByTagName("y");
        NodeList points = document.getDocumentElement().getElementsByTagName("point");

        int length = xList.getLength();

        for (int i = 0; i < length; i++){
            Node xItem = xList.item(i);
            Node yItem = yList.item(i);
            if (xItem instanceof Element){
                double x = Double.valueOf(xItem.getNodeValue());
                double y = Double.valueOf(yItem.getNodeValue());
                Point point = new Point(x, y, points.item(i).getAttributes().item(0).getNodeValue());

                if (oldPoint.equals(point)){
                    xItem.setNodeValue(newPoint.getX());
                }
            }
        }
    }
}
