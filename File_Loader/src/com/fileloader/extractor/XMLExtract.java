package com.fileloader.extractor;

import com.fileloader.extractor.utils.JobProperties;
import com.fileloader.extractor.Extractor;


public class XMLExtract {

    public static void main(String args[]){
        JobProperties jp = new JobProperties("D://File_Loader//src//job.properties");
        String schema = jp.getValue("db.schema.name", "DBO");
        Extractor newjob = new Extractor(schema);
        newjob.countTableRows();
        newjob.runExtractor();
    }

}
