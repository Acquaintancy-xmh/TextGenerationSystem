package com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Main {

    public static void main(String[] args) {

//        getSynonymsByPython getSynonymsByPython = new getSynonymsByPython("爱国");
//        ArrayList<String> synonymsList = getSynonymsByPython.getSynonymsWords();
//        for (int i = 0; i < synonymsList.size(); i++) {
//            System.out.println(synonymsList.get(i));
//        }


//        getOpinionWords getOpinionWords = new getOpinionWords("/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/习近平：为实现民族伟大复兴 推进祖国和平统一而共同奋斗——在《告台湾同胞书》发表40周年纪念会上的讲话-新华网/keyWords.txt");
//        getOpinionWords.getRawKeyWords();




        /*
        //完成了关键词与语料库主题词之间的匹配

        ArrayList<File> fileList = (new DocumentIO()).getFileNames("/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments4/");
        ArrayList<String> subjectList = new ArrayList<>();

        for (File file : fileList) {
            String filename = file.getName();
            if (filename.contains(".") && !filename.contains("D"))
                subjectList.add(filename.split("\\.")[0]);
        }
        ArrayList<String> choosenOpinionWordList = chooseOpinionWords.getOpinionWords(subjectList,"/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/习近平：为实现民族伟大复兴 推进祖国和平统一而共同奋斗——在《告台湾同胞书》发表40周年纪念会上的讲话-新华网/keyWords.txt");
        System.out.println(choosenOpinionWordList);

        */







//        dealWithDocuments dealWithDocuments = new dealWithDocuments();
//        dealWithDocuments.deal("/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/Documents/");

//        ArrayList<Integer> test = new ArrayList<>();
//        test.add(1);
//        test.add(2);
//        test.add(3);
//
//        test.add(1, -1);
//        System.out.println(test);

        /*
        生成基于文章的自动摘要部分 返回summary字符串
         */


        StringBuffer finalString = new StringBuffer();

//        ArrayList<File> summaryfileList = (new DocumentIO).getFileNames("/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/");
//        for (File afile : summaryfileList) {
//            String fileName = afile.getName();
//            String name = fileName.substring(0,fileName.length()-4);
//            System.out.println("\n" + name);
//
//            getSummary getSummary = new getSummary();
//
//            String summary = getSummary.getDocument(name);
////            System.out.println(summary);
//        }

        String testString = "习近平：在全国政协新年茶话会上的讲话-新华网";

        getSummary getSummary = new getSummary();
        String summary = getSummary.getDocument(testString);
        if (summary.contains("第")) {
            ArrayList<String> actorList = getSummary.getActor(testString);
            finalString.append("  " + actorList.get(0) + "在文章中提出，");
            finalString.append(summary + "\n");
        } else {
            finalString.append("  在文章中，" + summary + "\n");
        }


        /*
        生成关键词的议论文部分
         */

        ArrayList<File> fileList = (new DocumentIO()).getFileNames("/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/HandledDocuments4/");
        ArrayList<String> subjectList = new ArrayList<>();

        for (File file : fileList) {
            String filename = file.getName();
            if (filename.contains(".") && !filename.contains("D"))
                subjectList.add(filename.split("\\.")[0]);
        }
        chooseOpinionWords chooseOpinionWords = new chooseOpinionWords();
        ArrayList<String> choosenOpinionWordList = chooseOpinionWords.getOpinionWords(subjectList,"/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/" + testString + "/keyWords.txt");
        ArrayList<ArrayList<String>> opinionWordsCouple = chooseOpinionWords.wordCoupleList;

        StringBuffer resultDocument = new StringBuffer();
        for (String opinionWord : choosenOpinionWordList) { //为每个主题词生成文档
            ArrayList<String> firstPart = new ArrayList<>();
            ArrayList<String> secontPart = new ArrayList<>();
            ArrayList<String> thirdPart = new ArrayList<>();

            String document = (new DocumentIO()).readFileByChars("/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/JSON/" + opinionWord.substring(1,opinionWord.length()-1) + ".json");
            JSONObject jsonObject = JSON.parseObject(document);

            if (jsonObject.getString("[引言]") != null) {
                Object[] tempString = jsonObject.getJSONArray("[引言]").toArray();
                for (Object temp : tempString) {
                    firstPart.add((String) temp);
                }
            } else if (jsonObject.getString("[名人名言]") != null) {
                Object[] tempString = jsonObject.getJSONArray("[名人名言]").toArray();
                for (Object temp : tempString) {
                    firstPart.add((String) temp);
                }
            }

            Object[] tempString = jsonObject.getJSONArray("[事实论据]").toArray();
            for (Object temp : tempString) {
                secontPart.add((String) temp);
            }

            if (jsonObject.getString("[理论论据]") != null) {
                Object[] tempString2 = jsonObject.getJSONArray("[理论论据]").toArray();
                for (Object temp : tempString2) {
                    String str = (String) temp;
                    if (str.contains("（")) {
                        str = str.replaceAll("（", "");
                        str = str.replaceAll("）", "");
                    }
                    if (str.contains("(")) {
                        str = str.replaceAll("\\(", "");
                        str = str.replaceAll("\\)", "");
                    }
                    if(str.contains("？"))
                        str = str.replaceAll("？", "。");
                    else if (str.contains("！"))
                        str = str.replaceAll("！", "。");
                    String[] strList = str.split("。");//将句子与人名分开
                    String name = strList[1];
                    String bookName = "";
                    Pattern patternString = Pattern.compile("《.*》"); //正则表达式 识别()中间的词
                    Matcher matcherString = patternString.matcher(name);
                    if(matcherString.find()) {
                        bookName = matcherString.group();
                    }
                    name = name.replaceAll(bookName, "");
                    thirdPart.add(name + "在" + bookName + "中说：" + strList[0] + "。");
                }
            }

            String keyword = opinionWordsCouple.get(choosenOpinionWordList.indexOf(opinionWord)).get(0);
            resultDocument.append("  文章中提到了" + keyword + "，由此我们想到了" + opinionWord + "。");

            if (firstPart.size()!=0)
                resultDocument.append(firstPart.get(0));
            if (secontPart.size()!=0)
                resultDocument.append(secontPart.get(0));
            if (thirdPart.size()!=0)
                resultDocument.append(thirdPart.get(0));
            resultDocument.append("\n");
        }
        System.out.println(resultDocument.toString());
        finalString.append(resultDocument.toString());


        (new DocumentIO()).writeFile("final.txt", finalString.toString());
        System.out.println("站起来了弟弟们");
    }
}
