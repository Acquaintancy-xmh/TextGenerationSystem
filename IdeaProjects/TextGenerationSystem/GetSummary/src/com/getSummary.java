package com;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.File;

public class getSummary {
    public String document;//文章
    public ArrayList<String> sentencesofenter = new ArrayList<>();//按\n分句
    public ArrayList<String> sentencesofcomma = new ArrayList<>();//按，分句
    public ArrayList<String> sentencesoffullstop = new ArrayList<>();//按。分句
    public ArrayList<String> leadingActors = new ArrayList<>();//文章标题中的人名列表
    public String Actor; // ！！！！！暂时无法解决的有多主人公的问题，替代上面的人名列表
    public ArrayList<String> ActorSentences = new ArrayList<>();//含有文章标题人名的"串联句"
    public ArrayList<String> choosenConnectionSentences = new ArrayList<>();//经过词性筛选之后的"串联句"
    public String title;


    String rootPath = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/";
    String dirRootPath = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/";

    String inputfile = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/1.txt";
    String firstSummaryFile = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/FirstSummary.txt";
    String secondSummaryFile = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/SecondSummary.txt";
    String fullSentencefilename = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/FullSentenceSummary.txt";
    String splitSentencefilename = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/SplitSentenceSummary.txt";
    String keyWordsFile;

    public String getDocument(String inputfile) {
        getActorSentencesFromDocument();
        this.inputfile = rootPath + inputfile + ".txt"; //获得源文件地址
        this.title = inputfile;

        String dirPath = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/FinalSummary/" + inputfile;
        File dirfile = new File(dirPath); //创建dirPath为地址的文件夹
        if (!dirfile.exists()) {
            dirfile.mkdir();
//            System.out.println("创建文件夹" + inputfile);
        }

        splitInputDocument(); //从input路径中获得文字，生成各种字符数组

        this.keyWordsFile = dirPath + "/keyWords.txt";
        getKeyWords(10); //获得文章的关键词，写入keyWordsFile中

        String seqSummary = "";
        String splitSummary = "";
        if (HasSequenceNumbers()) { //文章中有"第一第二""首先其次"等序列词
            this.firstSummaryFile = dirPath + "/seqFirstSummary.txt";
            StringBuffer tempString = new StringBuffer(); //含有序列词的字符串
            for (String sentence : sentencesoffullstop) {
                if(sentence.startsWith("第")){
                    tempString.append(sentence);
                    tempString.append("。");
                }
            }
            seqSummary = tempString.toString();
            (new DocumentIO()).writeFile(this.firstSummaryFile,seqSummary);
            getActorSentence(); //在文档中寻找带有主人公人名的串联句
//            System.out.println("***生成有序列词的摘要");

        } else {
//            getFullSentenceSummary();//由两步关键句提取，生成整句的关键句文档
            this.splitSentencefilename = dirPath + "/splitSentenceSummary";
            splitSummary = getSplitSentenceSummary(); //生成分句的关键句
//            System.out.println("###生成没有序列词的摘要");
        }
        if (!seqSummary.equals("")) {
            return seqSummary;
        }
        else
            return splitSummary;
    }

    public void getActorSentencesFromDocument(){ //从文件中（语料库中）取出准备好的关联词
        String sentences = (new DocumentIO()).readFileByChars("/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/ActorSentences.txt"); //文件流读文件
        choosenConnectionSentences = new ArrayList<>(Arrays.asList(sentences.split("\n")));//文章句子数组
    }

    public void getSummaryDocument(String inputfile) {
        getActorSentencesFromDocument();
        this.inputfile = rootPath + inputfile + ".txt"; // 获得源文件地址
        this.title = inputfile;

        String dirPath = this.dirRootPath + inputfile; //生成文件的文件夹地址

        File dirfile = new File(dirPath); //创建文件夹
        if (!dirfile.exists()) {
            dirfile.mkdir();
            System.out.println("创建文件夹：" + inputfile);
        }


        this.firstSummaryFile = dirPath + "/firstSummary.txt";
        this.secondSummaryFile = dirPath + "/secondSummary.txt";
        this.fullSentencefilename = dirPath + "/fullSentenceSummary.txt";
        this.splitSentencefilename = dirPath + "/splitSentenceSummary.txt";


        this.keyWordsFile = dirPath + "/keyWords.txt";

        splitInputDocument(); //拆分句子形成各种数组

        getKeyWords(10); //获得文章的关键词

        if (HasSequenceNumbers()) { //文章中有"第一第二""首先其次"等序列词
            this.firstSummaryFile = dirPath + "/seqFirstSummary.txt";
            StringBuffer tempString = new StringBuffer();
            for (String sentence : sentencesoffullstop) {
                if(sentence.startsWith("第")){
                    tempString.append(sentence);
                    tempString.append("\n");
                }
            }
            (new DocumentIO()).writeFile(this.firstSummaryFile,tempString.toString());
            getActorSentence();
//            System.out.println("***生成有序列词的摘要");

            //test
//            this.firstSummaryFile = "/Users/xmh_mac/IdeaProjects/TextGenerationSystem/GetSummary/Documents/SummaryS/" + "seq" + title + ".txt";
//            StringBuffer tempString = new StringBuffer();
//            for (String sentence : sentencesoffullstop) {
//                if(sentence.startsWith("第")){
//                    tempString.append(sentence);
//                    tempString.append("\n");
//                }
//            }
//            (new DocumentIO()).writeFile(this.firstSummaryFile,tempString.toString());
//            getActorSentence();
//            System.out.println("***生成有序列词的摘要");

        } else {
            getFullSentenceSummary(); //由两步关键句提取，生成整句的关键句文档
            getSplitSentenceSummary();
//            System.out.println("###生成没有序列词的摘要");
        }
    }



    /*
    根据全局变量inputfile作为地址 获得文件内字符
    将字符根据 \n ， 。 符号生成各种字符数组
     */

    public void splitInputDocument(){
        document = (new DocumentIO()).readFileByChars(inputfile); //文件流读文件
        sentencesofenter = new ArrayList<>(Arrays.asList(document.split("\n")));//文章句子数组
        for (String sentence : sentencesofenter) {
            String[] temps = sentence.split("[，。]");
            for (String temp : temps) {
                temp.replace((char)12288 + "","");
            }
            Collections.addAll(sentencesofcomma, temps);
        }
        for (String sentence : sentencesofenter) {
            String[] temps = sentence.split("。");
            ArrayList<String> newtemps = new ArrayList<>();
            for (int i = 0; i < temps.length; i++) {
                String temp = temps[i];
                for (int j = 0; j < temp.length(); j++) {
                    char c = temp.charAt(j);
                    if(c != (char)12288){
                        String newtemp = temp.substring(j);
                        temps[i] = newtemp;
                        break;
                    }
                }

            }
            Collections.addAll(sentencesoffullstop, temps);
        }

//        System.out.println("…………文章处理完成");
    }

    public void getKeyWords(int size){ //获得关键词 存入keyWordsFile文档
        List<String> keywordList = HanLP.extractKeyword(document, size);
        StringBuffer tempString = new StringBuffer();
        for (String str : keywordList) {
            tempString.append(str);
            tempString.append("\n");
        }
        (new DocumentIO()).writeFile(keyWordsFile,tempString.toString());
//        System.out.println("@@文章的关键词为" + keywordList.toString());
//        System.out.println("…………生成关键词文档");
    }

    public List<String> getFirstSummary(int size){ //从文档中获得size大小的关键句合集，并写入firstSummaryFile中
        List<String> sentenceList = HanLP.extractSummary(document, size);
        StringBuffer tempString = new StringBuffer();
        for(String s:sentenceList) {
            tempString.append(s);
            tempString.append("\n");
        }
        (new DocumentIO()).writeFile(firstSummaryFile,tempString.toString());
        return sentenceList;
    }

    public List<String> getSecondSummary(){ //从第一次生成的关键词文档中读取关键句，并进一步生成关键句，写入secondSummaryFile中
        String secondStepDocument = (new DocumentIO()).readFileByChars(firstSummaryFile);
        List<String> sentenceList = HanLP.extractSummary(secondStepDocument, 5);
        StringBuffer tempString = new StringBuffer();
        for(String s:sentenceList) {
            tempString.append(s);
            tempString.append("\n");
        }
        (new DocumentIO()).writeFile(secondSummaryFile,tempString.toString());

        StringBuffer tempFullSentence = new StringBuffer(); //生成整句摘要
        for (String s : sentenceList) {
            for (String se : sentencesoffullstop) {
                if (se.contains(s)) {
                    tempFullSentence.append(se);
                    tempFullSentence.append("\n");
                }
            }
        }
        (new DocumentIO()).writeFile(fullSentencefilename,tempFullSentence.toString());
//        System.out.println("…………生成整句摘要");

        return sentenceList;
    }

    public void getActorSentence(){
        Segment segment = HanLP.newSegment().enableNameRecognize(true);//利用词性标注进行人名的识别
        List<Term> termList = segment.seg(title);
        for (Term term : termList) {
            if (term.nature == Nature.nr) {
                leadingActors.add(term.word);
            }
        }
        if(!leadingActors.isEmpty())
            this.Actor = leadingActors.get(0);

        if(Actor!=null) {
            for (String sentence : sentencesofcomma) {
                if (sentence.contains(Actor)) {
                    ActorSentences.add(sentence);
                }
            }
        }
    }

    public ArrayList<String> getActor(String title) {
        ArrayList<String> resultList = new ArrayList<>();
        Segment segment = HanLP.newSegment().enableNameRecognize(true);//利用词性标注进行人名的识别
        List<Term> termList = segment.seg(title);
        for (Term term : termList) {
            if (term.nature == Nature.nr) {
                resultList.add(term.word);
            }
        }
        return resultList;
    }

    public void getFullSentenceSummary() {
        getFirstSummary(10); //根据文档，获得size大小的关键句文件
        getSecondSummary(); //根据getFirstSummay生成的文档进一步生成关键句
    }

    public String getSplitSentenceSummary() { //生成split关键句，并用连接词连接起来，写入splitSentencefilename中
        getActorSentence();
        chooseConnectionSentences();
        List<String> firstSummarySentences = getFirstSummary(16); //生成16句split关键句
        StringBuffer summary = new StringBuffer();

        for (int i = 0; i < firstSummarySentences.size(); i = i+2) {
            double d = Math.random();
            int k= (int)(d*2);
            summary.append(choosenConnectionSentences.get(k) + "，");

            summary.append(firstSummarySentences.get(i) + "，");
            summary.append(firstSummarySentences.get(i+1) + "。");
        }

        (new DocumentIO()).writeFile(splitSentencefilename,summary.toString());
//        System.out.println("…………生成分句摘要");

        return summary.toString();
    }

    public void chooseConnectionSentences() { //通过词性筛选选择合适的串联句 使串联句中只包括"人名""动词""副词"
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        for (String sentence : ActorSentences) {
            boolean flag = true;//记录是否包含其他词性词(true:是连接句)
            List<Term> termList = segment.seg(sentence);
            for (Term term : termList) {
                if (term.nature != Nature.w && term.nature != Nature.nr && term.nature != Nature.l && term.nature!= Nature.v) {
                    flag = false;
                }
            }
            if (flag) {
                choosenConnectionSentences.add(sentence);
//                System.out.println("------连接句："+sentence);
            }
        }
    }


    public boolean HasSequenceNumbers() {
        boolean flag1 = false;
        boolean flag2 = false;
        for (String sentence : sentencesoffullstop) {
            if (sentence.startsWith("第一"))
                flag1 = true;
            if (sentence.startsWith("第二"))
                flag2 = true;
        }
        return flag1 && flag2;
    }
}