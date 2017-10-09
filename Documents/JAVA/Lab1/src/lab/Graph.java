package lab;
import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;

import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.management.relation.RoleUnresolved;
import javax.print.attribute.ResolutionSyntax;


import javax.swing.event.*;
import java.util.*;

public class Graph{
	General start;
	wordPoint[] pointList;
	int realwordNum;
	
	//9_11 xmh
	int[] pointMatrix = {0};
	
	
	
	public wordPoint checkPointExist(StringBuffer currentWord, int length){
		General currentStage = start;
		int count = 0;
		while(count < length){
			int index = currentWord.codePointAt(count);
			if(index < 97 || index > 122) return null;
			currentStage = currentStage.next[index-97];
			if(currentStage == null) break; //走偏
			count++;
			
		}
		if(count == length && currentStage != null)
			if(currentStage.real != null)
				return currentStage.real; //树的尾字母节点
		return null;
	}
	public wordPoint createPoint(StringBuffer wordList, int length){
		General currentStage = start;
		for(int i=0;i<length;i++){
			int edi = wordList.codePointAt(i)-97;
			if(currentStage.next[edi] == null){ 			//不存在此字母的通用节点
				currentStage.next[edi] = new General();
				currentStage = currentStage.next[edi];
			}
			else{ 											//存在此字母的通用节点
				currentStage = currentStage.next[edi];
			}
		} //到此时currentStage为单词尾字母节点
		wordPoint thePoint = new wordPoint(wordList);
		currentStage.real = thePoint;
		return currentStage.real;
		
	}
	public wordEdge checkEdgeExist(wordPoint previous, wordPoint following){
		wordEdge current = previous.next;
		while(current != null){
			if(current.followingPoint.word == following.word) return current;
			current = current.bro;
		}
		return null;
	}
	public void createEdge(wordPoint previous, wordPoint following){
		wordEdge current = previous.next;
		if(current != null){
			while(current.bro != null){
				if(current.followingPoint.word == following.word) break;
				current = current.bro;
			}
		
			current.bro = new wordEdge(following);
		}
		else
			previous.next = new wordEdge(following);
		previous.numChild++;
	}
	public Graph(StringBuffer[] wordList,int wordNum){
		//创建第一个节点
		start = new General();
		wordPoint previousWord = createPoint(wordList[0],wordList[0].length());
		pointList = new wordPoint[wordNum];
		realwordNum = 0;
		pointList[realwordNum] = previousWord;
		for(int i=1;i<wordNum;i++){
			StringBuffer currentWord = wordList[i];
			int length = currentWord.length();
			wordPoint resultPoint = checkPointExist(currentWord, length); //检查当前词是否存在
			if(resultPoint != null){ //已存在词
				wordEdge resultEdge = checkEdgeExist(previousWord,resultPoint); //检查当前边是否存在
				if(resultEdge != null) resultEdge.num++; //已存在边，次数加一
				else //不存在边，创建边，并且加一
					createEdge(previousWord, resultPoint);
			}
			else{ //不存在点，先创建点
				resultPoint = createPoint(currentWord,length);
				createEdge(previousWord, resultPoint);
				realwordNum++;
				pointList[realwordNum] = resultPoint;
			}
			
			previousWord = resultPoint;
		}
	}
	
	
	public void graphShow(){
		
		try{
			File fout = new File("/Users/xmh_mac/labGraph.dot");
			FileOutputStream out = new FileOutputStream(fout);
			out.write("digraph labGraph{\n\t".getBytes());
			for(int i = 0;i<=realwordNum;i++){
				wordPoint startPoint = pointList[i];
				wordEdge currentPoint = startPoint.next;
				if(currentPoint == null) continue;
				for(int j = 0;j<startPoint.numChild;j++){
					out.write((startPoint.word+"->"+currentPoint.followingPoint.word+"[label=\""+currentPoint.num+"\"]"+";\n").getBytes());
					currentPoint = currentPoint.bro;
				}
			}
			out.write("}".getBytes());
			out.close();
		}
		catch(FileNotFoundException e) { 
			System.out.println("FileStreamsTest: "+e); }
		catch(IOException e) { 
			System.err.println("FileStreamsTest: "+e); 
		}
		/*
		        try {  
		            String[] shpath={"/bin/sh","-c","/Users/xmh_mac/Desktop/try.sh"};  
		            Process ps = Runtime.getRuntime().exec(shpath);  
		            ps.waitFor();  
		  
		            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));  
		            StringBuffer sb = new StringBuffer();  
		            String line;  
		            while ((line = br.readLine()) != null) {  
		                sb.append(line).append("\n");  
		            }  
		            String result = sb.toString();  
		            System.out.println(result);  
		            }   
		        catch (Exception e) {  
		            e.printStackTrace();  
		            }  
		    
		    */
	}
	
	public void graphShow(S_wordEdge stack[],int wordNum,int num){
			
			try{
				for(int index=0;index<=wordNum;index++){
					if(stack[index].esp!=-1){
						File fout = new File("/Users/xmh_mac/lab1_shortestPath/labGraph_"+pointList[index].word+".dot");
						FileOutputStream out = new FileOutputStream(fout);
						out.write("digraph labGraph{\n\t".getBytes());
						out.write((pointList[num].word+"[style=\"filled\", color=\"black\", fillcolor=\"#59A9FB\"];\n").getBytes());
						for(int i=1;i<=stack[index].esp;i++){
							out.write((stack[index].S[i].followingPoint.word+"[style=\"filled\", color=\"black\", fillcolor=\"chartreuse\"];\n").getBytes());
						}
						for(int i = 0;i<=realwordNum;i++){
							wordPoint startPoint = pointList[i];
							wordEdge currentPoint = startPoint.next;
							if(currentPoint == null) continue;
							for(int j = 0;j<startPoint.numChild;j++){
								out.write((startPoint.word+"->"+currentPoint.followingPoint.word+"[label=\""+currentPoint.num+"\"]"+";\n").getBytes());
								currentPoint = currentPoint.bro;
							}
						}
						out.write("}".getBytes());
						out.close();
					}
				}
				
			}
			catch(FileNotFoundException e) { 
				System.out.println("FileStreamsTest: "+e); }
			catch(IOException e) { 
				System.err.println("FileStreamsTest: "+e); 
			}
	}
	
	
	public S findBridge(StringBuffer word1,StringBuffer word2){
		wordPoint point1,point2;
		S container = new S();
		byte flag = 0;
		point1 = checkPointExist(word1,word1.length());
		point2 = checkPointExist(word2,word2.length());
		if(point1==null){
			//System.out.println("The word is invalid");
			container.esp += -2;  //word1不存在
		}
		if(point2 == null){
			container.esp += -3;  //word2不存在
		}
		if(container.esp<=-3) return container;
		S stack = new S();
		wordEdge Edge1x = point1.next; 
		if(Edge1x == null) {  //p1-px 路径不存在
			//System.out.println("no bridge found!");
			container.esp = -7;
			return container;
		}
		for(int i=0;i<point1.numChild;i++){ //遍历p1的边
			stack.Push(Edge1x.followingPoint);
			wordPoint currentPoint = stack.getTop();
			wordEdge Edgex2;
			Edgex2 = currentPoint.next; //px-p2的边
			if((Edgex2 == null)||(Edgex2.followingPoint==null)) {
				continue;
			}
			wordPoint testWord=null;
			for(int j=0;j<currentPoint.numChild;j++){
				testWord = Edgex2.followingPoint;
				if(testWord.word.toString().equals( word2.toString())){
					//System.out.println(word1+ stack.getTop().word.toString() + word2);
					flag = 1;
					container.Push(stack.getTop());
					stack.Pull();
					break;
				}
				Edgex2 = Edgex2.bro;
				
			}
			Edge1x = Edge1x.bro;
		}
		return container;

	}
	
	public String buildNewText(int wordNum,StringBuffer[] wordList){
		S container;
		String resultString = "";
		for(int i=0;i<wordNum-1;i++){
			StringBuffer currentWord = wordList[i];
			StringBuffer followingWord = wordList[i+1];
			container = findBridge(currentWord,followingWord);
			resultString = resultString + currentWord.toString()+" ";
			if(container.esp>=0){
				java.util.Random r=new java.util.Random(); 
				int result = r.nextInt()%(container.esp+1);
				resultString = resultString + container.S[result<0?(-result):result].word.toString()+" ";
				}
		}
		if(wordNum==0) return "";
		resultString = resultString + wordList[wordNum-1].toString();
		return resultString;
	}
	
	public int getIndex(wordPoint[] list,wordPoint word){
		for(int i=0;i<list.length;i++){
			if(list[i].equals(word)) return i;
		}
		return -1;
	}
	
	
	public S_wordEdge[] shortestPath(StringBuffer wordBegin) throws Exception{
		S_wordEdge[] stackList = new S_wordEdge[realwordNum+1];
		for(int i=0;i<=realwordNum;i++)
			stackList[i] = new S_wordEdge();
		S_wordEdge stack = new S_wordEdge();
		S pointStack = new S();
		wordPoint pointBegin = checkPointExist(wordBegin, wordBegin.length());
		if(pointBegin==null){
			System.out.print("The word not exist!");
			return null;
		}
		wordEdge currentEdge = pointBegin.next;
		wordPoint currentPrePoint = pointBegin;
		pointStack.Push(currentPrePoint);
		stack.Push(currentEdge);
		currentPrePoint = currentEdge.followingPoint;
		do{
			while(currentEdge != null){
				currentPrePoint = currentEdge.followingPoint;
				if(pointStack.isRepeat(currentPrePoint)){
					currentEdge = currentEdge.bro;
					continue;
				}
				stack.Push(currentEdge);
				pointStack.Push(currentPrePoint);
				int index = getIndex(pointList, currentPrePoint);
				if(stackList[index].esp == -1 || stackList[index].esp>stack.esp)
					stackList[index] = new S_wordEdge(stack);
				currentEdge = currentPrePoint.next;
				if(currentEdge == null) break;
				currentPrePoint = currentEdge.followingPoint;
			}
			currentEdge = stack.Pull();
			pointStack.Pull();
			currentEdge = currentEdge.bro;

			
		}while(pointStack.isEmpty()==0);
		return stackList;
	}
	public S RandomGo(StringBuffer start, int steps) throws IOException{
		S path = new S();
		wordPoint currentPoint = checkPointExist(start,start.length());
		path.Push(currentPoint);
		while(steps != 0){
			 currentPoint = stepOne(currentPoint);
			 if(currentPoint == null){
				 System.out.println("no words to explore, stop walking");
				 break;
			 }
			 if(path.isRepeat(currentPoint)){
				 System.out.println("get repeated point, stop walking");
				 break;
			 }
			 path.Push(currentPoint);
			 steps--;
		}
		if(steps == 0) System.out.println("length limit, stop walking");
		return path;
	}
	public S RandomGo(StringBuffer start) throws IOException{
		S path = new S();
		wordPoint currentPoint = checkPointExist(start,start.length());
		path.Push(currentPoint);
		boolean action = true;
		StringBuffer stringPath = new StringBuffer(currentPoint.word.toString()+"->");
		currentPoint = stepOne(currentPoint);
		while(action){
			
			while(true){
				int choice;
				choice = JOptionPane.showConfirmDialog(null, "当前路径为"+stringPath+"\n是否继续？", "Overwrite file", JOptionPane.YES_NO_OPTION);
				path.Push(currentPoint);
				 stringPath.append(currentPoint.word.toString()+"->");
				action = (choice == 0 ? true : false);
				break;
			}
			 currentPoint = stepOne(currentPoint);
			 if(currentPoint == null){
				 System.out.println("no words to explore, stop walking");
				 break;
			 }
			 if(path.isRepeat(currentPoint)){
				 System.out.println("get repeated point, stop walking");
				 break;
			 }
			 
		}
		if(!action) System.out.println("stop due to user input");
		path.Pull();
		return path;
	}
	public wordPoint stepOne(wordPoint currentPoint){
		if(currentPoint.numChild == 0) return null;
		java.util.Random r=new java.util.Random(); 
		int result = r.nextInt()%(currentPoint.numChild);
		result = result<0 ? -result : result;
		wordEdge currentEdge = currentPoint.next;
		for(int i=0;i<result;i++){
			currentEdge = currentEdge.bro;
		}
		return currentEdge.followingPoint;
	}
}
