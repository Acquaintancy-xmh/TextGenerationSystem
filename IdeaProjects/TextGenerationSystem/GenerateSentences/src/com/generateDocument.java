package com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class generateDocument {
    public static String generate(ArrayList<String> opinionWordList) {
        StringBuffer resultString = new StringBuffer();
        String rootPath = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GenerateSentences/JSON/";
        for (String word : opinionWordList) {
            String document = (new DocumentIO()).readFileByChars(rootPath + word + ".json");
            JSONObject jsonObject = JSON.parseObject(document);

        }
        return resultString.toString();
    }
}
