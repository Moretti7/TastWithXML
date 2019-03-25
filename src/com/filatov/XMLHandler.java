package com.filatov;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

public class XMLHandler extends DefaultHandler implements ErrorHandler {
    private List<Point> points;
    private double tempX;
    private double tempY;
    private String tempDate;

    private boolean workingWithX;
    private boolean workingWithY;

    private double sumX, sumY, sumX2, sumXY, t;
    private double k, b;
    private int num;

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
        num /= 2;
        k = (sumXY - sumX * sumY / num) / (sumX2 - sumX * sumX / num);
        b = sumY / num - k * sumX / num;
        System.out.println("k: " + k + "\t" + "b: " + b);

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        System.out.println(">>> start element " + qName);
        if (qName.equals("point")) {
            tempDate = attributes.getValue(0);
//            System.out.println(String.format(">>> attribute: %s value: %s", attributes.getLocalName(0), attributes.getValue(0)));
        }
        if (qName.equals("x")) {
            workingWithX = true;
        }

        if (qName.equals("y")) {
            workingWithY = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        System.out.println(">>> end element " + localName);
        if (qName.equals("x")) {
            workingWithX = false;
            num++;
        }

        if (qName.equals("y")) {
            workingWithY = false;
            num++;
            t = 0;
        }

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
            sumX += tempX;
            sumX2 += tempX*tempX;
            t = tempX;
        }

        if (workingWithY) {
            tempY = Double.valueOf(value.trim());
            sumY += tempY;
            t = t * tempY;
            sumXY += t;
        }
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        throw e;
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        throw e;
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        throw e;
    }

    public List<Point> getPoints() {
        return points;
    }
}
