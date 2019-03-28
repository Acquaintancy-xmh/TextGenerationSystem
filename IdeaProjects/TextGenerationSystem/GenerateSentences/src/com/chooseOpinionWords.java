package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chooseOpinionWords {

    public ArrayList<ArrayList<String>> wordCoupleList; //最终选出来的关键词与主题词的配对

  /*
    通过依次比较关键字与主题词的相似度 选出相似度最高的词 返回ArrayList<ArrayList<String>>
    keywordString/subjectString 用","拼接起来的词字符串（"爱国,敬业"）
    size 返回词组的数量
     */
    public static ArrayList<ArrayList<String>> compareWords(String keyWordString,String subjectString,String size) {
        ArrayList<ArrayList<String>> returnList = new ArrayList<>();
        StringBuffer resultLine = new StringBuffer();
        try {
            String[] pyargs = new String[] { "python3", "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/chooseOpinionWords.py", keyWordString,subjectString,size};
            Process proc = Runtime.getRuntime().exec(pyargs);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                resultLine.append(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> resultList = new ArrayList<>();
        if(resultLine.length()!=0){ //对命令行返回的字符串的处理
            String resultString;
            Pattern patternString = Pattern.compile("\\(.*\\)"); //正则表达式 识别()中间的词
            Matcher matcherString = patternString.matcher(resultLine.toString());
            if(matcherString.find()) {
                resultString = matcherString.group();
                resultString = resultString.substring(2, resultString.length()-2);
                String[] tempList = resultString.split(",");
                for (String temp : tempList) {
                    resultList.add(temp);
                }
            }
        } //resultList =[[1.0, '爱岗', '爱岗', 1.0, '爱国', '爱国', 1.0, '敬业', '敬业'] ]
        ArrayList<String> tempList = new ArrayList<>();
        if (resultList.size() != 0) { //通过正则，提取出''中的汉字，生成tempList的词语数组
            String str;
            for (String temp : resultList) {
                Pattern patternString = Pattern.compile("'.*'");
                Matcher matcherString = patternString.matcher(temp);
                if(matcherString.find()) {
                    str = matcherString.group();
                    tempList.add(str);
                }
            }
        }
        for (int i = 0; i < tempList.size(); i += 2) { //将tempList数组内的词语两两配对，生成输出数组
            ArrayList<String> temp = new ArrayList<>();
            temp.add(tempList.get(i));
            temp.add(tempList.get(i + 1));
            returnList.add(temp);
        }
        return returnList;
    }


    private static ArrayList<String> removeDupString(ArrayList<String> choosenopinionWordList) {//去掉choosenopinionWordList中重复的项
        ArrayList<String> newList = new ArrayList<>();
        for (String temp : choosenopinionWordList) {
            if (!newList.contains(temp)) {
                newList.add(temp);
            }
        }
        return newList;
    }

    private static ArrayList<ArrayList<String>> removeDupList(ArrayList<ArrayList<String>> choosenList) {
        ArrayList<ArrayList<String>> newList = new ArrayList<>();
        for (ArrayList<String> temp : choosenList) {
            boolean flag = false;
            for (ArrayList<String> l : newList) {
                if (temp.get(1).equals(l.get(1))) {
                    flag = true;
                }
            }
            if (!flag) {
                newList.add(temp);
            }
        }
        return newList;
    }


    public  ArrayList<String> getOpinionWords(ArrayList<String> opinionWordList, String path) {
        ArrayList<String> choosenopinionWordList = new ArrayList<>(); //最终选出来的主题词

        StringBuffer subjectStringBuffer = new StringBuffer();
        for (String temp : opinionWordList) {
            subjectStringBuffer.append(temp + ",");
        }
        String subjectString = subjectStringBuffer.substring(0, subjectStringBuffer.length() - 1);
        String document = (new DocumentIO()).readFileByChars(path);
        String keyWordString = document.replaceAll("\n", ",").substring(0,document.length()-1);
        System.out.println(keyWordString);
        System.out.println(subjectString);


        int initialSize = 3; //初始的couple数目
        while (choosenopinionWordList.size() < 3) {//防止重复opinion 使得始终有3对主题词
            //wordCoupleList = [['同胞', '爱国'], ['台湾同胞', '爱国'], ['民族', '团结']]
            this.wordCoupleList = chooseOpinionWords.compareWords(keyWordString, subjectString, initialSize+"");
            System.out.println(this.wordCoupleList);

            //choosenopinionWordList = ['爱国'，'爱国'，'团结']
            for (ArrayList<String> temp : this.wordCoupleList) {
                choosenopinionWordList.add(temp.get(1));
            }
            this.wordCoupleList = removeDupList(this.wordCoupleList);
            choosenopinionWordList = removeDupString(choosenopinionWordList);
            initialSize++;
        }
        return choosenopinionWordList;
    }
}
