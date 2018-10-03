package blobxml;

/*
 * This utility will xtract the files from the given path and create metadata XML for all the files.
 * input parameters for this program are <files path> <table> <Output path>
 * This is developed for the syncplicity application file extraction.
 * @Author Seelan
 * */

import java.io.*;
import java.util.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.apache.commons.io.FileUtils;

public class UnstructuredFileExtractor {

    public static final String file_ext = ".xml";
    public static String givenpath = null;
    public static String region = null;
    public static String user = null;
    private static int chunk = 0;
    public static String outputpath = null;
    private static int record_count_metadata = 0;

    public static void main(String args[]) throws Exception {
        if (args.length == 3) {
            givenpath = args[0].replace("\\", "/");
            outputpath = args[1].replace("\\", "/") + "/";
            user = givenpath.substring(givenpath.lastIndexOf("/"));
            region = args[2];
            File source = new File(givenpath);
            System.out.println("Given Region: " + region);

            if (source.isDirectory()) {
                System.out.println("Reformatting files (removing junk characters from files)");
                removeNonAlphaNumericChars(source);
                System.out.println("Generating xml for the user: " + source.getName());
                List<File> allFiles = getAllFiles(source);
                createXML(allFiles, source.getName(), region);
                formatUserDirectory(source);
                System.out.println("Total Records for Metadata: " + record_count_metadata);
                System.out.println("---------------------process completed----------------");
            } else {
                System.out.println("Provide valid directory");
                System.exit(0);
            }
        } else {
            System.out.println("================================Usage===========================");
            System.out.println("     <Source file path> <xml generation path> <region>    ");
            System.out.println("================================================================");
            System.exit(1);
        }
    }

    static void createXML(List<File> files_in_folder, String Parent, String root) throws Exception {
        String tablename = formatTableName(Parent.toUpperCase());
        String xmlfilename = outputpath + "DBO-" + tablename + "-" + String.format("%04d", chunk) + file_ext;
        Writer out = new OutputStreamWriter(new FileOutputStream(xmlfilename));
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(out);
        writer = factory.createXMLStreamWriter(new FileOutputStream(xmlfilename), "UTF-8");
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeCharacters("\n");
        writer.writeStartElement("DBO");
        writer.writeCharacters("\n\t");
        writer.writeStartElement(tablename);
        for (File f : files_in_folder) {
            if (!f.isDirectory()) {
                FileProperty fp = new FileProperty(f);
                writer.writeCharacters("\n\t\t");
                writer.writeStartElement("ROW");
                writer.writeAttribute("id", UUID.randomUUID().toString());
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("USERNAME");
                writer.writeCharacters(Parent);
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("REGION");
                writer.writeCharacters(root);
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("ATTACHMENT");
                writer.writeAttribute("ref", fp.getfilepath());
                writer.writeCharacters(fp.getFile());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("FILE_PATH");
                writer.writeCharacters(fp.getFilename());
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
                writer.writeStartElement("FILE_CREATED_DATE");
                writer.writeCharacters(fp.getFileCreation());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("FILE_LAST_ACCESS_DATE");
                writer.writeCharacters(fp.getLastAccessDate());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("FILE_SIZE");
                writer.writeCharacters(fp.getfilesize());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("RECORD_ADDED_DATE");
                writer.writeCharacters(fp.getCurrentDate());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t\t");
                writer.writeStartElement("SRC_PATH");
                writer.writeCharacters(fp.getSourcePath());
                writer.writeEndElement();
                writer.writeCharacters("\n\t\t");
                writer.writeEndElement();
                fp = null;
                record_count_metadata++;
            }
        }
        writer.writeCharacters("\n\t");
        writer.writeEndElement();
        writer.writeCharacters("\n");
        writer.writeEndElement();

        if (out != null) {
            out.flush();
            out.close();
        }
        chunk++;
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

    public static void removeNonAlphaNumericChars(File directory) {
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isDirectory()) {
                File dest = new File(file.getParent(), getFolderFormatted(file.getName()));
                file.renameTo(new File(file.getParent(), getFolderFormatted(file.getName())));
                removeNonAlphaNumericChars(new File(dest.getAbsolutePath()));
            } else {
                File dest = new File(getFileFormatted(file.getName()));
                file.renameTo(new File(file.getParent(), dest.getName()));
            }
        }
    }

    public static String getFolderFormatted(String string) {
        string = string.replaceAll("[^\\p{Alnum}.\\s_\\/]", "").trim().replace("$", "").replace("(", "").replace(")", "")
                .replace("[", "").replace("]", "").replace("{", "").replace("}", "")
                .replace("\"", "").replace("'", "").replace("`", "").replace("&", "").replace("@", "").replace("#", "")
                .replace("%", "").replace("!", "").replace("^", "").replace("*", "").replace("|", "");
        return string;
    }

    public static String getFileFormatted(String string) {
        return string.replaceAll("[^\\p{Alnum}.\\s_]", "").trim().replace("$", "").replace("(", "").replace(")", "")
                .replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "")
                .replace("'", "").replace("`", "").replace("&", "").replace("@", "").replace("#", "")
                .replace("%", "").replace("!", "").replace("^", "").replace("*", "").replace("|", "");
    }

    public static String formatTableName(String table){
        table = UnstructuredFileExtractor.getFileFormatted(table.toUpperCase().replace(" ", "_"));
        return table;
    }

    public static void formatUserDirectory(File file){
        File dest = new File(givenpath.substring(0,givenpath.lastIndexOf("/"))
                + "/" + getFileFormatted(file.getName()));
        file.renameTo(dest);
    }
}
