package com.filatov;

public class MainDOM {
    public static void main(String[] args) throws Exception {
        DOM dom = new DOM();

        System.out.println(dom.getData());

        dom.replace(new Point(4.4, 4.28, "22.03.2018"), new Point(4221, 4221, "4221.4221.4221"));
        dom.addNewPoint(new Point(4221, 4221, "4221.4221.4221"));
        dom.insert(new Point(4221, 4221, "4221.4221.4221"), 6);
        dom.delete(new Point(1.1, 1.01, "19.03.2018"));
        System.out.println(dom.getData());

        dom.toXML("newXMLFile.xml");
    }
}
