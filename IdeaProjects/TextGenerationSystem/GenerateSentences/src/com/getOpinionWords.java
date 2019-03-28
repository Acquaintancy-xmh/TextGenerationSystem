package com;

import java.util.ArrayList;
import java.util.Arrays;

public class getOpinionWords {
    private ArrayList<String> rawKeyWords;
    private ArrayList<String> keyWordsString;
    private ArrayList<ArrayList<String>> synonymsWordsList = new ArrayList<>();
    private ArrayList<ArrayList<String>> synonymsWordsListString = new ArrayList<>();
    private String path;

    public getOpinionWords(String path) {
        this.path = path;
    }

    public void getRawKeyWords(){
        String document = (new DocumentIO()).readFileByChars(path); //文件流读文件
        rawKeyWords = new ArrayList<>(Arrays.asList(document.split("\n")));
        for (String seedWord : rawKeyWords) {
            getSynonymsByPython getSynonymsByPython = new getSynonymsByPython(seedWord,"爱国,爱党");
            System.out.println(getSynonymsByPython.getSynonymsWords_newMethod());
            //synonymsWordsList.add(getSynonymsByPython.getSynonymsWords_newMethod());
            System.out.println(seedWord + "………python脚本执行完成 同义词生成完成");
        }
        //dealWithRawKeyWords();
    }

    public void dealWithRawKeyWords() {
        for (ArrayList<String> synonymsWords : synonymsWordsList) {
            ArrayList<String> tempList = new ArrayList<>();
            int numbersOfWords = synonymsWords.size()>>1;
            for (int i = 0; i < numbersOfWords; i++) {
                tempList.add(synonymsWords.get(i));
            }
            synonymsWordsListString.add(tempList);
        }
        System.out.println("同义词有：" + synonymsWordsListString);
    }
}

