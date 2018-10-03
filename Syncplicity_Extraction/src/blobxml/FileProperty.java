package blobxml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.time.LocalDateTime;

public class FileProperty {

    private File filename = null;
    private String filepath;
    private String filetype;
    private String filesize;
    private String lastmodified;
    private String creationDate;
    private String lastaccessed;

    FileProperty(File filname) throws IOException {
        this.filename = filname;
        Path filepath = Paths.get(filename.getAbsolutePath());
        BasicFileAttributes attrs = Files.readAttributes(filepath, BasicFileAttributes.class);
        this.filetype = filename.toString().lastIndexOf('.') > 0 ? filename.toString().substring(filename.toString().lastIndexOf('.') + 1) : "";
        this.filesize = (attrs.size() / 1024 + " KB");
        this.lastmodified = attrs.lastModifiedTime().toString();
        this.creationDate = attrs.creationTime().toString();
        this.lastaccessed = attrs.lastAccessTime().toString();
        this.filepath = "../blobs/" + UnstructuredFileExtractor.region + "/" + UnstructuredFileExtractor.getFileFormatted(UnstructuredFileExtractor.user) +filename.getAbsolutePath().substring(UnstructuredFileExtractor.givenpath.length()).replace("\\","/");
//        this.filepath = filename.getAbsolutePath().contains("\\") || filename.getAbsolutePath().contains("/") ? "../blobs" + TestUnstructured.getFolderFormatted(filename.getAbsolutePath().replace("\\", "/").substring(TestUnstructured.givenpath.length(), filename.getAbsolutePath().replace("\\","/").lastIndexOf('/') + 1))
//                 + TestUnstructured.getFileFormatted(filename.getName()) : "../blobs" + filename.getAbsolutePath().substring(TestUnstructured.givenpath.length())
//                .replace("\\", "/") + TestUnstructured.getFileFormatted(filename.getName());
    }

    public String getFile() {
        return filename.getName();
//        return TestUnstructured.getFileFormatted(filename.getName());
    }

    public String getFileType() {
        return filetype;
    }

    public String getfilepath() {
        return filepath;
    }

    public String getfilesize() {
        return filesize;
    }

    public String getlastmodified() {
        return lastmodified;
    }

//    public String getFolderCreation() {
//        return lastmodified;
//    }

    public String getFilename() {
        return UnstructuredFileExtractor.region + UnstructuredFileExtractor.user + filename.toString().substring(UnstructuredFileExtractor.givenpath.length()).replace("\\", "/");
//        return filename.toString().substring(TestUnstructured.givenpath.length() + 1).replace("\\", "/");
    }

    public String getFileCreation() {
        return creationDate;
    }

    public String getCurrentDate() {
        return LocalDateTime.now().toString();
    }

    public String getSourcePath(){
        return filename.getAbsolutePath();
    }

    public String getLastAccessDate(){
        return lastaccessed;
    }
}
