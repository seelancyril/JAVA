package blobxml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileProperty {

    private File filename = null;
    private String filepath;
    private String filetype;
    private String filesize;
    private String lastmodified;

    FileProperty(File filname) throws IOException {
        this.filename = filname;
        Path filepath = Paths.get(filename.getAbsolutePath());
        BasicFileAttributes attrs = Files.readAttributes(filepath, BasicFileAttributes.class);
        this.filetype = filename.toString().lastIndexOf('.') > 0 ? filename.toString().substring(filename.toString().lastIndexOf('.')+1) : "";
        this.filesize = (attrs.size() / 1024 + " KB");
        this.lastmodified = attrs.lastModifiedTime().toString();
        this.filepath = "../blobs" + filename.getAbsolutePath().substring(14).replace("\\","/");
    }

    public String getFileType(){
        return filetype;
    }

    public String getfilepath(){
        return filepath;
    }

    public String getfilesize(){
        return filesize;
    }

    public String getlastmodified(){
        return lastmodified;
    }

    public String getFolderCreation(){
        return lastmodified;
    }
}