package com;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class dealWithDocuments {

    public void dealWithZhaiYao(String path,String fileName) {
        String document = (new DocumentIO()).readFileByChars(path);
        ArrayList<String> sentencesofenter = new ArrayList<>(Arrays.asList(document.split("\n")));
        if (sentencesofenter.get(0).equals("***TITLE")) {
            if (sentencesofenter.get(1).contains("摘要")) {
                sentencesofenter.remove(1);
            }
        }
        if (!sentencesofenter.get(1).contains("[")) {
            sentencesofenter.remove(1);
        }
        StringBuffer resultDocument = new StringBuffer();
        for (String sentence : sentencesofenter) {
            resultDocument.append(sentence);
            resultDocument.append("\n");
        }
        String newPath = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments3/"+fileName;
        (new DocumentIO()).writeFile(newPath,resultDocument.toString());
        System.out.println(fileName + "   摘要处理完毕");


    }

    private void dealWithTags(String path,String fileName){ //处理"引言""事实论据"等，加上[]以方便识别
        String document = (new DocumentIO()).readFileByChars(path);
        StringBuffer resultDocument = new StringBuffer();
        ArrayList<String> sentencesofenter = new ArrayList<>(Arrays.asList(document.split("\n")));
        String subject = fileName.split("\\.")[0];
        if (subject.equals(sentencesofenter.get(0)))
            sentencesofenter.remove(0);
        for (String sentence : sentencesofenter) {
            if (Pattern.matches(".*[1-9].*", sentence) && sentence.length()<10) {
                String newSentence = sentence.replaceAll("[^\\u4e00-\\u9fa5]", "");
                newSentence = "[" + newSentence + "]";
                Collections.replaceAll(sentencesofenter, sentence, newSentence);
            }
        }
        for (String sentence : sentencesofenter) {
            resultDocument.append(sentence);
            resultDocument.append("\n");
        }
        String newPath = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments1/"+fileName;
        (new DocumentIO()).writeFile(newPath,resultDocument.toString());
        System.out.println(subject + "   Tag处理完毕");
    }

    private void dealWithTitles(String path,String fileName) {
        String document = (new DocumentIO()).readFileByChars(path);
        StringBuffer resultDocument = new StringBuffer();
        ArrayList<String> sentencesofenter = new ArrayList<>(Arrays.asList(document.split("\n")));
        int startIndex = 0;
        if (sentencesofenter.indexOf("[事实论据]") != -1)
            startIndex = sentencesofenter.indexOf("[事实论据]");
        int endIndex = sentencesofenter.size();
        if (sentencesofenter.indexOf("理论论据") != -1) {
            endIndex = sentencesofenter.indexOf("[理论论据]");
        }
        for (int i = startIndex+1; i < endIndex; i++) {
            if (sentencesofenter.get(i).length() < 15) {
                sentencesofenter.add(i,"***");
                i++;
            }
        }
        sentencesofenter.add(0,"***TITLE"); //做一个标签，表示有***的title
        for (String sentence : sentencesofenter) {
            resultDocument.append(sentence);
            resultDocument.append("\n");
        }
        String newPath = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments2/"+fileName;
        (new DocumentIO()).writeFile(newPath,resultDocument.toString());
        System.out.println(fileName + "  title处理完毕");
    }

    private void dealWithSSLJ(String path,String fileName) { //处理事实论据
        String document = (new DocumentIO()).readFileByChars(path);
        StringBuffer resultDocument = new StringBuffer();
        ArrayList<String> resultList = new ArrayList<>();
        ArrayList<String> sentencesofenter = new ArrayList<>(Arrays.asList(document.split("\n")));
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < sentencesofenter.size(); i++) {
            if (sentencesofenter.get(i).equals("***")) {
                indexList.add(i);
            }
        }

        if (indexList.size() == 0) { //素材中没有事实论据
            for (String sentence : sentencesofenter) {
                resultDocument.append(sentence);
                resultDocument.append("\n");
            }
            String newPath = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments4/"+fileName;
            (new DocumentIO()).writeFile(newPath,resultDocument.toString());
            System.out.println(fileName + "  事实论据处理完毕");
            return;
        }

        ArrayList<String> tempList = new ArrayList<>();
        for (int i : indexList) {
            if (indexList.indexOf(i) == indexList.size() - 1) {
                break;
            }
            int index = i + 1;
            StringBuffer tempString = new StringBuffer();
            while (indexList.indexOf(index) == -1) {
                if (!sentencesofenter.get(index).contains("。")) {
                    tempString.append(sentencesofenter.get(index) + "。");
                }
                else
                    tempString.append(sentencesofenter.get(index));
                index++;
            }
            tempList.add(tempString.toString());
        }

        for (int i = 0; i < indexList.get(0); i++) {
            resultList.add(sentencesofenter.get(i));
        }
        for (String sentence : tempList) {
            resultList.add(sentence);
        }
        for (int i = indexList.get(indexList.size()-1)+1; i < sentencesofenter.size(); i++) {
            resultList.add(sentencesofenter.get(i));
        }

        for (String sentence : resultList) {
            resultDocument.append(sentence);
            resultDocument.append("\n");
        }
        String newPath = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments4/"+fileName;
        (new DocumentIO()).writeFile(newPath,resultDocument.toString());
        System.out.println(fileName + "  事实论据处理完毕");
    }

    public void deal(String path){
//        ArrayList<File> fileList = (new DocumentIO()).getFileNames(path);
//        for (File file : fileList) {
//            String filename = file.getName();
//            dealWithTags(path + filename,filename);
//        }
//
//        String path2 = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments1/";
//        ArrayList<File> fileList2 = (new DocumentIO()).getFileNames(path2);
//        for (File file : fileList2) {
//            String filename = file.getName();
//            dealWithTitles(path2 + filename, filename);
//        }
//
//        String path3 = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments2/";
//        ArrayList<File> fileList3 = (new DocumentIO()).getFileNames(path3);
//        for (File file : fileList3) {
//            String filename = file.getName();
//            dealWithZhaiYao(path3 + filename, filename);
//        }

        String path4 = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments3/";
        ArrayList<File> fileList4 = (new DocumentIO()).getFileNames(path4);
        for (File file : fileList4) {
            String filename = file.getName();
            dealWithSSLJ(path4 + filename, filename);
        }
    }


}
