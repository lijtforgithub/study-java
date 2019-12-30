package com.ljt.study.pp.xml;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * @author LiJingTang
 * @date 2019-11-28 13:18
 */
public class XMLTest {

    private static final InputStream INPUT = XMLTest.class.getResourceAsStream("/pp/test.xml");
    private static final String PEOPLE = "people";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";

    @Test
    public void testDom4j() throws DocumentException {
        Element root = new SAXReader().read(INPUT).getRootElement();
        Iterator it = root.elementIterator(PEOPLE);

        while (it.hasNext()) {
            Element element = (Element) it.next();
            System.out.printf("姓名：%s / 密码： %s %s",
                    element.elementText(NAME),
                    element.elementText(PASSWORD),
                    System.lineSeparator());
        }
    }

    @Test
    public void testJdom() throws JDOMException, IOException {
        List<org.jdom2.Element> nodes = new SAXBuilder().build(INPUT).getRootElement().getChildren();

        for (org.jdom2.Element node : nodes) {
            System.out.printf("姓名：%s / 密码： %s %s",
                    node.getChild(NAME).getText(),
                    node.getChild(PASSWORD).getText(),
                    System.lineSeparator());
        }
    }

    @Test
    public void testDom() throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(INPUT);
        NodeList nodes = doc.getElementsByTagName(PEOPLE);

        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.printf("姓名：%s / 密码： %s %s",
                    doc.getElementsByTagName(NAME).item(i).getFirstChild().getNodeValue(),
                    doc.getElementsByTagName(PASSWORD).item(i).getFirstChild().getNodeValue(),
                    System.lineSeparator());
        }
    }

    @Test
    public void testSax() throws Exception {
        SAXParser sp = SAXParserFactory.newInstance().newSAXParser();
        SaxReader reader = new SaxReader();
        sp.parse(INPUT, reader);
    }

    private static class SaxReader extends DefaultHandler {

        private Stack<String> tack = new Stack<>();

        @Override
        public void characters(char[] ch, int start, int length) {
            String tag = tack.peek();

            switch (tag) {
                case NAME:
                    System.out.println("姓名：" + new String(ch, start, length));
                    break;
                case PASSWORD:
                    System.out.println("密码：" + new String(ch, start, length));
                    break;
                default:
                    break;
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attrs) {
            tack.push(qName);
        }
    }

}
