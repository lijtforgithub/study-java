package com.ljt.study.pp.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * @author LiJingTang
 * @date 2019-11-28 14:16
 */
public class ValidateXML {

    public static void main(String[] args) {
        String path = ValidateXML.class.getResource("").getPath();
        System.out.println(validateTaskXML(path, "NewFile.xml"));
    }

    private static boolean validateTaskXML(String xmlPath, String xmlName) {
        File file = new File(xmlPath + xmlName);
        String xsdPath = ValidateXML.class.getResource("catchTaskXML.xsd").toString().substring(5);

        return validateXML(file, xsdPath);
    }

    private static boolean validateCatchTaskXML(String xmlPath, String xmlName) {
        File file = new File(xmlPath + xmlName);
        String xsdPath = ValidateXML.class.getResource("catchTaskXML.xsd").toString().substring(5);
        String regex = "catchTasks";
        String replacement = "catchTasks xmlns=\"http://www.example.org/catchTaskXML\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"http://www.example.org/catchTaskXML catchTaskXML.xsd\"";
        File xmlFile = handleXML(file, regex, replacement, xmlPath);

        if (xmlFile == null) {
            return false;
        }

        return validateXML(xmlFile, xsdPath);
    }

    private static boolean validateFlowXML(String xmlPath, String xmlName) {
        File file = new File(xmlPath + xmlName);
        String xsdPath = ValidateXML.class.getResource("flowXML.xsd").toString().substring(5);
        String regex = "dataMap";
        String replacement = "dataMap xmlns=\"http://www.example.org/flowXML\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"http://www.example.org/flowXML flowXML.xsd\"";
        File xmlFile = handleXML(file, regex, replacement, xmlPath);

        if (xmlFile == null) {
            return false;
        }

        return validateXML(xmlFile, xsdPath);
    }

    private static boolean validateDataModelXML(String xmlPath, String xmlName) {
        File file = new File(xmlPath + xmlName);
        String xsdPath = ValidateXML.class.getResource("dataModelXML.xsd").toString().substring(5);
        String regex = "dataModel";
        String replacement = "dataModel xmlns=\"http://www.example.org/dataModelXML\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"http://www.example.org/dataModelXML dataModelXML.xsd\"";
        File xmlFile = handleXML(file, regex, replacement, xmlPath);

        if (xmlFile == null) {
            return false;
        }

        return validateXML(xmlFile, xsdPath);
    }

    private static boolean validateXML(File tempFile, String xsdPath) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.XML_NS_URI);
        File schemaFile = new File(xsdPath);
        Schema schema = null;

        try {
            schema = schemaFactory.newSchema(schemaFile);
        } catch (SAXException e) {
            e.printStackTrace();
        }

        Validator validator = schema.newValidator();
        Source source = new StreamSource(tempFile);

        try {
            validator.validate(source);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static File handleXML(File file, String regex, String replacement, String tempPath) {
        File replaceFile = new File(tempPath + "XML_XSD.xml");

        try {
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(file);
            XMLWriter xmlWriter = null;
            StringWriter strWriter = new StringWriter();
            xmlWriter = new XMLWriter(strWriter);
            xmlWriter.write(doc);
            String xmlStr = strWriter.toString();
            if (!xmlStr.contains(regex)) {
                return null;
            }
            xmlStr = xmlStr.replaceFirst(regex, replacement);
            doc = DocumentHelper.parseText(xmlStr);
            OutputStream out = new FileOutputStream(replaceFile);
            xmlWriter = new XMLWriter(out);
            xmlWriter.write(doc);
            out.close();
            strWriter.close();
            xmlWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return replaceFile;
    }

}
