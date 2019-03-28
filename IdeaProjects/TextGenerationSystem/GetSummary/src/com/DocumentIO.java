package com;

import java.io.*;
import java.util.ArrayList;

public class DocumentIO {
    public String readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        StringBuffer tempString = new StringBuffer();
        try {
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                tempString.append((char)tempchar);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempString.toString();
    }

    public void writeFile(String filename,String content) {
        try {
            File file = new File(filename);
            Writer writer = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<File> getFileNames(String documentpath){
        File file = new File(documentpath);
        ArrayList<File> fileList = new ArrayList<>();
        File[] array = file.listFiles();
        for (File afile : array) {
            if (afile.isFile()) {
                fileList.add(afile);
            }
        }
        return fileList;
    }
}
