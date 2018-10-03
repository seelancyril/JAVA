package blobxml;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class RenameFiles {

    public static void renameFile(File f, String givenpath) throws IOException {
        if(f.getName().contains("_") && f.getName().contains(".")){
            String rename = f.getName().substring(0,f.getName().indexOf('_'))
                    + f.getName().substring(f.getName().lastIndexOf('.'));
            FileUtils.moveFile(f, new File(givenpath + "/" + rename));
        }
    }

    public static void main(String args[]) throws IOException{
        String givenpath = args[0];
        File file = new File(givenpath);
        for (File f : file.listFiles()) {
            renameFile(f, givenpath);
        }
        System.out.println("Process completed");
    }

}
