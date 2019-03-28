package com;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String args[]){

        DocumentIO documentIO = new DocumentIO();
        ArrayList<File> fileList = documentIO.getFileNames("/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/");
        for (File afile : fileList) {
            String fileName = afile.getName();
            String name = fileName.substring(0,fileName.length()-4);
            System.out.println("\n" + name);

            getSummary getSummary = new getSummary();

            String summary = getSummary.getDocument(name);
            System.out.println(summary);
        }



//        DocumentIO documentIO = new DocumentIO();
//        getSummary getSummary = new getSummary();
//
//        getSummary.getSummaryDocument("TestDocument");


//        DocumentIO documentIO = new DocumentIO();
//        ArrayList<File> fileList = documentIO.getFileNames("/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/");
//        for (File afile : fileList) {
//            String fileName = afile.getName();
//
//            if(afile.isFile()){
//
//            }
//            else{
//                afile.delete();
//            }
//
//        }
    }
}
