package blobxml;

import org.apache.commons.io.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;



public class TestCopyFiles {
    public static void main(String[] args) throws IOException {

        File sourceDirectory = new File("D:\\ETL\\BLOBxml\\EMEA\\Amanda Çormier");
        File targetDirectory = new File("D:\\ETL\\All_test_downloads\\all");

        //copy source to target using Files Class
//        Files.copy(sourceDirectory, targetDirectory);
        FileUtils.copyDirectory(sourceDirectory, targetDirectory);
    }
}
