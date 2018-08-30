package blobxml;

/*
* This utility will xtract the files from the given path and create metadata XML for all the files.
* input parameters for this program are <files path> <table> <Output path>
* This is developed for the syncplicity application file extraction.
* @Author Seelan
* */

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.io.FileUtils;

public class UnstructuredFileExtractor {

    public static final String file_ext = ".xml";
    private static List all_files;
    private static int chunk = 0;
    private static int record_counter = 0;
    private static String outputpath = "D:\\ETL\\Syncplicity\\";
    private static int record_count_metadata = 0;

    static void replaceSpace(File filename, String givenpath) throws IOException {
        String fullpath = filename.getAbsolutePath();
        File filename1 = null;
        if (filename.getName().contains(" ")) {
            String name = filename.getName().replace(" ", "_");
            filename1 = new File(givenpath + "\\" + name);
            FileUtils.moveFile(filename, filename1);
            System.out.println("File renamed: " + filename1.getName());
        }
        filename1 = null;
    }

    public static void main(String args[]) throws Exception {
        if (args.length == 3) {
            String givenpath = args[0];
            String xmloption = args[1];
            outputpath = args[2] + "\\";
            File f = new File(givenpath);

            if (f.isDirectory()) {
                System.out.println("Gathering files from " + givenpath);
                String files[] = f.list();
                // will get one level of the directory files
                for (String file : files) {
                    System.out.println(file);
                    File newfile = new File(givenpath + "\\" + file);

                    if (newfile.isDirectory()) {
                        List<File> allFiles = getAllFiles(newfile);
                        createXML(allFiles, newfile.getName());

                    } else {

                    }

                }
                System.out.println("Total Records for Metadata: " + record_count_metadata);
                if (xmloption.equalsIgnoreCase("table")) {
                    return;
                } else if (xmloption.equalsIgnoreCase("sip")) {
                    return;
                }
            } else {
                System.out.println("Provide valid directory");
                System.exit(0);
            }
        } else {
            System.out.println("==========================Usage=========================");
            System.out.println("     <FilePath> <Table or SIP> <Output XML directory>    ");
            System.out.println("========================================================");
            System.exit(1);
        }
    }

    static void createXML(List<File> files_in_folder, String Parent) throws Exception {
        String xmlfilename = outputpath + "DBO-SYNCPLICITY_ATTACHMENTS-" + String.format("%04d", chunk) + file_ext;
        System.out.println(xmlfilename);
        Writer out = new OutputStreamWriter(new FileOutputStream(xmlfilename));
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(out);
        writer = factory.createXMLStreamWriter(new FileOutputStream(xmlfilename), "UTF-8");
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeCharacters("\n");
        writer.writeStartElement("DBO");
        writer.writeCharacters("\n\t");
        writer.writeStartElement("SYNCPLICITY_ATTACHMENTS");
        for (File f : files_in_folder) {
            if(!f.isDirectory()){
                FileProperty fp = new FileProperty(f);
                writer.writeCharacters("\n\t\t");
                writer.writeStartElement("ROW");
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("USERNAME");
                writer.writeCharacters(Parent);
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("ATTACHMENT");
                writer.writeAttribute("ref", fp.getfilepath());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("FILE_TYPE");
                writer.writeCharacters(fp.getFileType());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("FILE_MODIFIED_DATE");
                writer.writeCharacters(fp.getlastmodified());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("FILE_SIZE");
                writer.writeCharacters(fp.getfilesize());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t");
                writer.writeEndElement();
                fp =null;
                record_count_metadata++;
            }
        }
        writer.writeCharacters("\n\t");
        writer.writeEndElement();
        writer.writeCharacters("\n");
        writer.writeEndElement();

        if(out != null){
            out.flush();
            out.close();
        }
        chunk ++;
    }

    public static List<File> getAllFiles(File directory) {
        List<File> resultList = new ArrayList<File>();
        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isDirectory()) {
                resultList.addAll(getAllFiles(new File(file.getAbsolutePath())));
            }
        }
        return resultList;
    }
}