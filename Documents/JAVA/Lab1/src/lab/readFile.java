package lab;
import java.io.*;
import java.lang.String;

public class readFile{
	StringBuffer[] wordList;
	int wordNum;
	@SuppressWarnings("null")
	public readFile(String path){
		int c = 0; //数据流中字符
		long fileLength = 0; //输入文件大小
		char[] letterList = null;  //字符流数组
		try{
		File inFile=new File(path);
		FileInputStream fin=new FileInputStream(inFile);
		fileLength = inFile.length();
		letterList = new char [(int)fileLength];
		for(int i=0;i<fileLength;i++){  //文件读取
			c = fin.read();
			if((c>=65)&&(c<=90)) c = c+32;
			if((c<97)||c>122) c = 20; //将非法字符处理为' '
			letterList[i] = (char)c;
		}
		fin.close();
		}
		catch(FileNotFoundException e) { 
			System.out.println("FileStreamsTest: "+e); }
		catch(IOException e) { 
			System.err.println("FileStreamsTest: "+e); 
		}
		
		//将letterList中字符转化为字符串
		wordNum = 0;
		wordList = new StringBuffer[(int) fileLength];
		StringBuffer combinedString = new StringBuffer();
		for(int i=0;i<fileLength;i++){
			if(letterList[i]!=20){
				combinedString.append(letterList[i]);
			}
			else{
				if(combinedString.length()!=0){
					wordList[wordNum] = combinedString;
					wordNum++;
					combinedString = new StringBuffer();
				}
			}
		}
	}
}