package com.rowcountfromxml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.apache.commons.io.FileUtils.iterateFiles;


public class RowCounter {
    public RowCounter(File xmldir) throws Exception{
        String allfiles[] = xmldir.list();

        for (String file:allfiles){
            parseXML(xmldir.getAbsolutePath() + "\\" + file);
        }
    }

    static void parseXML(String filename) throws FileNotFoundException, XMLStreamException {
        System.out.println(filename);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        // Initializing the handler to access the tags in the XML file
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(filename));
        while(eventReader.hasNext()){

        }
    }

}
