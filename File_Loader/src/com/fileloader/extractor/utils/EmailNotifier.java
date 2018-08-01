package com.fileloader.extractor.utils;
/*
* Email Notifier will trigger a email after every process completion
* Wriiten by Seelan
 */
public class EmailNotifier {
    private String subject;
    private String emailTo;
    private String emailFrom;
    private int triggerid;

    public EmailNotifier(String emailTo, int triggerid){
        this.emailTo = emailTo;
        this.triggerid = triggerid;
    }

    public void emailExtractor(){

    }
}
