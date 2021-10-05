package com.mitsurin.tools.creating_best_party.model.compatibility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CompatibilityXMLReader {
    private Document document;
    private Element root;

    public CompatibilityXMLReader(String request) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch(ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            this.document = builder.parse("http://localhost"+request);
        } catch(IOException | SAXException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        
        this.root = this.document.getDocumentElement();
    }

    private Compatibility convertCompatibility(Node compatibilityNode) {
        Element type = (Element)(((Element)compatibilityNode).getElementsByTagName("type").item(0));
        NodeList weakpointNodeList = ((Element)compatibilityNode).getElementsByTagName("weakpoint");

        Type[] t1t2 = new Type[2];
        t1t2[0] = Type.getTypeByTypeName(type.getAttribute("t1"));
        t1t2[1] = Type.getTypeByTypeName(type.getAttribute("t2"));

        Map<Type, Integer> weakpointMap = new HashMap<>();
        for(int i = 0; i < weakpointNodeList.getLength(); i++) {
            Element weakpoint = (Element)(weakpointNodeList.item(i));

            Type weakType = Type.getTypeByTypeName(weakpoint.getAttribute("type"));
            int level = Integer.parseInt(weakpoint.getAttribute("level"));

            weakpointMap.put(weakType, level);
        }

        return new Compatibility(t1t2, weakpointMap);
    }

    public Compatibility[] getCompatibilities() {
        NodeList compatibilityNodeList = this.root.getElementsByTagName("compatibility");
        Compatibility[] compatibilities = new Compatibility[compatibilityNodeList.getLength()];

        for(int i = 0; i < compatibilityNodeList.getLength(); i++) {
            Node compatibilityNode = compatibilityNodeList.item(i);
            
            compatibilities[i] = convertCompatibility(compatibilityNode);
        }

        return compatibilities;
    }
}