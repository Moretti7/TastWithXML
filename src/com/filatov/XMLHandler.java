package com.filatov;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

public class XMLHandler extends DefaultHandler {
    private List<Point> points;
    private double tempX;
    private double tempY;
    private String tempDate;

    private boolean workingWithX;
    private boolean workingWithY;

    @Override
    public void startDocument() throws SAXException {
//        System.out.println(">>> start document");
        points = new LinkedList<>();
        workingWithX = false;
        workingWithY = false;
    }

    @Override
    public void endDocument() throws SAXException {
//        System.out.println(">>> end document");
//        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        System.out.println(">>> start element " + qName);
        if (qName.equals("point")) {
            tempDate = attributes.getValue(0);
//            System.out.println(String.format(">>> attribute: %s value: %s", attributes.getLocalName(0), attributes.getValue(0)));
        }
        if (qName.equals("x"))
            workingWithX = true;

        if (qName.equals("y"))
            workingWithY = true;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        System.out.println(">>> end element " + localName);
        if (qName.equals("x"))
            workingWithX = false;

        if (qName.equals("y"))
            workingWithY = false;

        if (qName.equals("point")) {
            points.add(new Point(tempX, tempY, tempDate));
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
//        System.out.println(">>> start characters -> " + new String(ch));

        String value = new String(ch, start, length);
        if (workingWithX) {
            tempX = Double.valueOf(value.trim());
        }

        if (workingWithY) {
            tempY = Double.valueOf(value.trim());
        }
    }


    public List<Point> getPoints() {
        return points;
    }
}
