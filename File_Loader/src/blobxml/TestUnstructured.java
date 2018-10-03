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

public class TestUnstructured {

    public static final String file_ext = ".xml";
    public static String givenpath;
    public static String blobpath = null;
    public static String tablepath = null;
    private static List all_files;
    private static int chunk = 0;
    private static int chunkvalue = 20;
    private static int record_counter = 0;
    public static String outputpath = "D:/ETL/Syncplicity/";
    private static int record_count_metadata = 0;

//    TestUnstructured(String givenpath, String outputpath){
//        this.givenpath = givenpath;
//        this.outputpath = outputpath;
//    }
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
        if (args.length <= 2) {
//            TestUnstructured t = new TestUnstructured(args[0], args[1]);
            givenpath = args[0];
            outputpath = args[1] + "/";
            File source = new File(givenpath);
            blobpath = outputpath + "blobs/";
            tablepath = outputpath + "tables/";
            File blobs = new File(blobpath);
            File tables = new File(tablepath);
            System.out.println("Copying file from " + args[0] + " to " + args[1]);
            if (!blobs.exists()) {
                blobs.mkdirs();
            }
            if (!tables.exists()) {
                tables.mkdirs();
            }
//            recursiveCopy(source, blobs);
            FileUtils.copyDirectory(source, blobs);

            //creating xmls TODO: need to check whether the xmls are getting generated if the junk values are present in the column value
            if (source.isDirectory()) {
                System.out.println("Gathering file information from source: " + args[0]);
                String files[] = source.list();

                //Check for the first level of directories (country/region)
                for (String region : files) {
                    File first_dir = new File(givenpath + "/" + region);

                    // second level of directory (Users)
                    if (first_dir.isDirectory()) {
//                        System.out.println("File renamed: " + first_dir.getName());
                        String[] users = first_dir.list();
                        List<File> others = new ArrayList<File>();
                        for (String file : users) {
                            System.out.println("Parent: " + file);
                            File newfile = new File(givenpath + "/" + region + "/" + file);
//                            <if needed we can add this>
//                            File dest = new File(getFileFormatted(newfile.getName()));
//                            newfile.renameTo(new File(newfile.getParent(), dest.getName()));
                            if (newfile.isDirectory()) {
//                                removeNonAlphaNumericChars(newfile);
                                List<File> allFiles = getAllFiles(newfile);
                                createXML(allFiles, newfile.getName(), region);
                            } else {
                                others.add(newfile);
                            }
                        }
                        if(others.size() > 0){
                            createXML(others, first_dir.getName() , "");
                        }
                    }
                }
                System.out.println("------------Extraction completed---------------");
                System.out.println("Total Records for Metadata: " + record_count_metadata);
            }

            //reformatting files in the destination TODO: check all the files recursively
            if (blobs.isDirectory()) {
                System.out.println("File name reformatting... " + args[0]);
                String files[] = blobs.list();

                //Check for the first level of directories (country/region)
                for (String region : files) {
                    File first_dir = new File(blobpath + "/" + region);
                    if (first_dir.isDirectory()) {
                        removeNonAlphaNumericChars(first_dir);
                    }
                    // second level of directory (Users)
                    if (first_dir.isDirectory()) {
//                        System.out.println("File renamed: " + first_dir.getName());
                        String[] users = first_dir.list();
                        List<File> others = new ArrayList<File>();
                        for (String file : users) {
                            File newfile = new File(blobpath + "/" + region + "/" + file);
//                            removing non english from User directories
                            File dest = new File(getFileFormatted(newfile.getName()));
                            newfile.renameTo(new File(newfile.getParent(), dest.getName()));
                            if (newfile.isDirectory()) {
                                removeNonAlphaNumericChars(newfile);
                            }
                        }
                    }
                }
                System.out.println("-------------Process completed-----------------");
            }
            else {
                System.out.println("Provide valid directory");
                System.exit(0);
            }
        } else {
            System.out.println("================================Usage===========================");
            System.out.println("     <Source file path> <destination path(Application Path)>    ");
            System.out.println("================================================================");
            System.exit(1);
        }
    }

    static void createXML(List<File> files_in_folder, String Parent, String root) throws Exception {

        String xmlfilename = tablepath + "DBO-FILE_ATTACHMENTS-" + String.format("%04d", chunk) + file_ext;
        System.out.println(xmlfilename);
        Writer out = new OutputStreamWriter(new FileOutputStream(xmlfilename));
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(out);
        writer = factory.createXMLStreamWriter(new FileOutputStream(xmlfilename), "UTF-8");
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeCharacters("\n");
        writer.writeStartElement("DBO");
        writer.writeCharacters("\n\t");
        writer.writeStartElement("FILE_ATTACHMENTS");
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
                if(!dest.exists()){
                    file.renameTo(new File(file.getParent(), getFolderFormatted(file.getName())));
                }
                removeNonAlphaNumericChars(new File(dest.getAbsolutePath()));
            } else {
                File dest = new File(getFileFormatted(file.getName()));
                if(!dest.exists()){
                    file.renameTo(new File(file.getParent(), getFolderFormatted(file.getName())));
                }
                else{
                    System.out.println("duplicate found");
                }
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

    public static void recursiveCopy(File fSource, File fDest) {
        try {
            if (fSource.isDirectory()) {
                // A simple validation, if the destination is not exist then create it
                if (!fDest.exists()) {
                    fDest.mkdirs();
                }

                // Create list of files and directories on the current source
                // Note: with the recursion 'fSource' changed accordingly
                String[] fList = fSource.list();

                for (int index = 0; index < fList.length; index++) {
                    File dest = new File(fDest, fList[index]);
                    File source = new File(fSource, fList[index]);

                    // Recursion call take place here
                    recursiveCopy(source, dest);
                }
            } else {
                // Found a file. Copy it into the destination, which is already created in 'if' condition above

                // Open a file for read and write (copy)
                FileInputStream fInStream = new FileInputStream(fSource);
                FileOutputStream fOutStream = new FileOutputStream(fDest);

                // Read 2K at a time from the file
                byte[] buffer = new byte[2048];
                int iBytesReads;

                // In each successful read, write back to the source
                while ((iBytesReads = fInStream.read(buffer)) >= 0) {
                    fOutStream.write(buffer, 0, iBytesReads);
                }

                // Safe exit
                if (fInStream != null) {
                    fInStream.close();
                }

                if (fOutStream != null) {
                    fOutStream.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
