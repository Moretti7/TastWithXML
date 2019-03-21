package com.filatov;

public class MainDOM {
    public static void main(String[] args) throws Exception {
        DOM dom = new DOM();

        System.out.println(dom.getData());

//        dom.replace(new Point(7.7, 7.84, "25.03.2018"), new Point(4221, 4221, "4221.4221.4221"));
        dom.addNewPoint(new Point(4221, 4221, "4221.4221.4221"));
        System.out.println(dom.getData());
    }
}
