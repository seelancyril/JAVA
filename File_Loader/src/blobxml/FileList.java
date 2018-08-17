package blobxml;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileList {

    static void replaceSpace(File filename, String givenpath) throws IOException {
        String fullpath = filename.getAbsolutePath();
        File filename1 = null;
        if(filename.getName().contains(" ")){
            String name = filename.getName().replace(" ", "_");
            filename1 = new File(givenpath+"\\"+name);
            FileUtils.moveFile(filename, filename1);
            System.out.println("File renamed: "+filename1.getName());
        }
    }

    public static void main(String args[]) throws IOException {
        if(args.length == 2){
            String givenpath = args[0];
            String xmloption = args[1];
            File f = new File(givenpath);
            if(f.isDirectory()){
                System.out.println("Gathering files in " + givenpath);
                String files[] = f.list();
                for(String file:files) {
                    File newfile = new File(givenpath + "\\" + file);
                    replaceSpace(newfile, givenpath);
                }
                if(xmloption.equalsIgnoreCase("sip")){

                }
            }
            else{
                System.out.println("Provide valid directory");
                System.exit(0);
            }
        }
        else{
            System.out.println("==============Usage===================");
            System.out.println("     <FilePath> <Table or SIP>     ");
            System.out.println("======================================");
            System.exit(1);
        }
    }
}
