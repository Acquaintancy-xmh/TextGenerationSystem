package com.offer.offer5;

public class Main {
    public static String replaceSpace(StringBuffer str) {
        if(str == null) return "Empty String";
        int spacenum = 0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i) == ' ') spacenum++;
        }
        int oldindex = str.length()-1;
        int newlength = str.length() + spacenum*2;
        int newindex = newlength-1;
        str.setLength(newlength);
        for(;oldindex>=0;oldindex--){
            if(str.charAt(oldindex) == ' '){
                str.setCharAt(newindex--,'0');
                str.setCharAt(newindex--,'2');
                str.setCharAt(newindex--,'%');
            }
            else{
                str.setCharAt(newindex,str.charAt(oldindex));
                newindex--;
            }
        }
        return str.toString();
    }

    public static String anEasyWay(StringBuffer str){
        String strString = str.toString();
        return strString.replaceAll("\\s","%20");
    }

    public static void main(String arg[]){
        StringBuffer oldString = new StringBuffer("Bona is soso beal");
        String newstring = anEasyWay(oldString);
        System.out.println(newstring);
    }
}
