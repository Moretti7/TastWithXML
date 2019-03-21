package com.filatov;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DOM {
    private DocumentBuilderFactory factory;
    private Document document;
    private DocumentBuilder documentBuilder;

    public DOM() throws ParserConfigurationException, IOException, SAXException {
        factory = DocumentBuilderFactory.newInstance();
        documentBuilder = factory.newDocumentBuilder();
        document = documentBuilder.parse(new File("G:\\GAMES\\CPP\\Code\\XML\\resources\\data.xml"));
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
//                System.out.println(">>> xItem: " + xItem.getTextContent() + " yItem: " + yItem.getTextContent());
                double x = Double.valueOf(xItem.getTextContent());
                double y = Double.valueOf(yItem.getTextContent());
                Point point = new Point(x, y, points.item(i).getAttributes().item(0).getTextContent());

                if (oldPoint.equals(point)){
                    xItem.setTextContent(Double.toString(newPoint.getX()));
                    yItem.setTextContent(Double.toString(newPoint.getY()));
                    points.item(i).getAttributes().item(0).setTextContent(newPoint.getDate());
//                    System.out.println(">>> new xItem: " + xItem.getTextContent() + " new yItem: " + yItem.getTextContent() + " new date: " + points.item(i).getAttributes().item(0).getTextContent());
                }
            }
        }
    }


    public void addNewPoint(Point point){
        //document.creatElement
        //document.appendChild
        //document.creatAttribute
        //element.setAttributeNode

        NodeList pointsList = document.getElementsByTagName("points");

        Node points = pointsList.item(0);
        Element newPoint = document.createElement("point");
        Element x = document.createElement("x");
        Element y = document.createElement("y");
        Attr date = document.createAttribute("date");

        date.setValue(point.getDate());
        x.setTextContent(Double.toString(point.getX()));
        y.setTextContent(Double.toString(point.getY()));

        newPoint.appendChild(x);
        newPoint.appendChild(y);
        newPoint.setAttributeNode(date);
        points.appendChild(newPoint);
    }



    public String getData(){
        StringBuilder stringBuilder = new StringBuilder();

        NodeList xList = document.getElementsByTagName("x");
        NodeList yList = document.getElementsByTagName("y");
        NodeList points = document.getElementsByTagName("point");

        int length = xList.getLength();

        for (int i = 0; i < length; i++){
            stringBuilder.append("[x=" + xList.item(i).getTextContent() +
                    "; y=" + yList.item(i).getTextContent() +
                    " " + points.item(i).getAttributes().item(0) +"]\n");
        }
        return stringBuilder.toString();
    }
}
