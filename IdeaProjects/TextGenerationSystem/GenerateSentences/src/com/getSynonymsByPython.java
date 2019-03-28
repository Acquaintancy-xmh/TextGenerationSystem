package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getSynonymsByPython {
    private String seedWord = null;
    private String wordString = null;

    public getSynonymsByPython(String seedWord,String wordString) {
        this.seedWord = seedWord;
        this.wordString = wordString;
    }

    /*
    传入wordString的备选词列表
    输出seedWord与wordString中各个词的相似度数组
     */
    public ArrayList<String> getSynonymsWords_newMethod() {
        ArrayList<String> resultList = new ArrayList<>();
        StringBuffer resultLine = new StringBuffer();
        try {
            String[] pyargs = new String[] { "python3", "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/getSynonymsWords.py", seedWord, wordString};
            Process proc = Runtime.getRuntime().exec(pyargs);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;resultLine = new StringBuffer();
            while ((line = in.readLine()) != null) {
                resultLine.append(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

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
        }


        return resultList;
    }


    /*
    通过python脚本获得同义词
    根绝seedWord获得同义词
     */
    public ArrayList<String> getSynonymsWords_oldMethod(){
        ArrayList<String> resultList = new ArrayList<>();
        StringBuffer resultLine = new StringBuffer();
        try {
            String[] pyargs = new String[] { "python3", "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/synonymstest.py", seedWord };
            Process proc = Runtime.getRuntime().exec(pyargs);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;resultLine = new StringBuffer();
            while ((line = in.readLine()) != null) {
                resultLine.append(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if(resultLine.length()!=0){ //对命令行返回的字符串的处理
            String resultString;
            Pattern patternString = Pattern.compile("\\(.*\\)"); //正则表达式 识别''中间的词
            Matcher matcherString = patternString.matcher(resultLine.toString());
            if(matcherString.find()) {
                resultString = matcherString.group();
                String[] tempList = resultString.split(",");
                for (String temp : tempList) {
                    resultList.add(temp.substring(temp.indexOf("'")+1,temp.length()-1));
                }
            }
        }

        int num = resultList.size() >> 1;
        resultList.remove(resultList.size() - 1); //去掉最不匹配的词与相似值
        resultList.remove(num - 1);

        ArrayList<String> resultStringNumbers = new ArrayList<>();

        for (String temp : resultList) {
            if(temp.equals(" [1."))
                resultStringNumbers.add(" 1.0");
            resultStringNumbers.add(temp);
        }


        return resultStringNumbers;
    }
}
