package com.filatov;

public class TestFromSAXtoXML {
    public static void main(String[] args)throws Exception {
        FromSAXtoDOM fromSAXtoDOM = new FromSAXtoDOM("resources/data.xml");
        fromSAXtoDOM.toXMLFile("resources/newXMLobtainedFromSAX.xml");
    }
}
