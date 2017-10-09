package lab;
import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;

import javax.swing.*;

import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
  
import javax.swing.JButton;  
import javax.swing.JDialog;  
import javax.swing.JFrame; 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.management.relation.RoleUnresolved;
import javax.print.attribute.ResolutionSyntax;


import javax.swing.event.*;
import java.util.*;
public class top {
	static Graph G = null;
	static String stringInput = new String();
	static String[] temInput;;
	static int index;
	static StringBuffer unreach = new StringBuffer("");

	static Graph createDirectedGraph(String filename) {
		readFile initialFile = new readFile(filename);
		Graph G = new Graph(initialFile.wordList, initialFile.wordNum);
		return G;
	}

	static void showDirectedGraph(Graph G) {
		G.graphShow();
	}

	static String queryBridgeWords(Graph G, String word1, String word2) {
		StringBuffer sb1 = new StringBuffer(word1);
		StringBuffer sb2 = new StringBuffer(word2);
		S container = new S();
		
		container = G.findBridge(sb1, sb2);
		switch (container.esp) {
		case -3:
			return "The word1 is invalid!";
		case -4:
			return "The word2 is invalid!";
		case -6:
			return "Two words both are invalid!";
		default:
			if (container.esp < 0) {
				return "No bridge found";
			}
			return container.getTop().word.toString();
		}
	}

	@SuppressWarnings("null")
	static String generateNewText(Graph G, String inputText) {
		String resultText;
		char[] letterList = null;
		letterList = new char[inputText.length()];
		System.out.print(inputText.length());
		int c;
		for (int i = 0; i < inputText.length(); i++) {
			c = inputText.charAt(i);
			if ((c >= 65) && (c <= 90))
				c = c + 32;
			if ((c < 97) || c > 122)
				c = 20; // 将非法字符处理为' '
			letterList[i] = (char) c;
		}
		int wordNum = 0;
		StringBuffer[] wordList = new StringBuffer[inputText.length()];
		StringBuffer combinedString = new StringBuffer();
		for (int i = 0; i < inputText.length(); i++) {
			if (letterList[i] != 20)
				combinedString.append(letterList[i]);
			else if (combinedString.length() != 0) {
				wordList[wordNum] = combinedString;
				System.out.println(wordList[wordNum].toString());
				wordNum++;
				combinedString = new StringBuffer();
			}
		}
		wordList[wordNum++] = combinedString;
		for(int i=0;i<wordNum;i++) System.out.print(wordList[i].toString());
		resultText = G.buildNewText(wordNum, wordList);
		return resultText;
	}

	static String calcShortestPath(Graph G, String word1, String word2) throws Exception {
		S_wordEdge[] stack = new S_wordEdge[G.realwordNum + 1];
		int count = 1;
		String resultPath = "";
		StringBuffer wordBegin = new StringBuffer(word1);
		if(G.checkPointExist(wordBegin, wordBegin.length()) == null) return null; 
		stack = G.shortestPath(wordBegin);
		int index;
		if(word2 != null){
			for (index = 0; index < G.pointList.length; index++)
				try{
				if (G.pointList[index].word.toString().equals(word2)){
					System.out.print(index);
					break;
				}
				}
			catch (Exception NullPointerException) {
				// TODO: handle exception
				return "输入单词有误，请重新输入！";
			}
			resultPath = resultPath + wordBegin.toString();
			while (count <= stack[index].esp) {
				resultPath = resultPath + "->";
				resultPath = resultPath + stack[index].S[count].followingPoint.word.toString();
				count++;
			}
			try {
				if (stack[index].esp != -1) {
					File fout = new File("/Users/xmh_mac/shortestlabGraph.dot");
					FileOutputStream out = new FileOutputStream(fout);
					out.write("digraph labGraph{\n\t".getBytes());
					out.write((word1+"[style=\"filled\", color=\"black\", fillcolor=\"#59A9FB\"];\n").getBytes());
					out.write(
							(G.pointList[index].word + "[style=\"filled\", color=\"black\", fillcolor=\"#59A9FB\"];\n")
									.getBytes());
					for (int i = 1; i <= stack[index].esp; i++) {
						out.write((stack[index].S[i].followingPoint.word
								+ "[style=\"filled\", color=\"black\", fillcolor=\"chartreuse\"];\n").getBytes());
					}
					for (int i = 0; i <= G.realwordNum; i++) {
						wordPoint startPoint = G.pointList[i];
						wordEdge currentPoint = startPoint.next;
						if (currentPoint == null)
							continue;
						for (int j = 0; j < startPoint.numChild; j++) {
							out.write((startPoint.word + "->" + currentPoint.followingPoint.word + "[label=\""
									+ currentPoint.num + "\"]" + ";\n").getBytes());
							currentPoint = currentPoint.bro;
						}
					}
					out.write("}".getBytes());
					out.close();
				}
				else{
					return "Unreachable";
				}
			}
			catch(FileNotFoundException e) { 
				System.out.println("FileStreamsTest: "+e); }
			catch(IOException e) { 
				System.err.println("FileStreamsTest: "+e); 
			}
		}
		else{
		for (index = 0; index < G.pointList.length; index++) // 获得开始点的角标
			if (G.pointList[index].word.toString().equals(word1))
				break;
		
		for(int i=0;i<G.realwordNum;i++){
			if(stack[i].esp == -1){
				if(G.pointList[i].word.toString().equals(word1)) continue;
				unreach.append(G.pointList[i].word.toString()+"  ");
			}
		}
		G.graphShow(stack, G.realwordNum, index);
		return "";
		}
		return resultPath;
	}

	static String randomWalk(Graph G) throws IOException {
		System.out.println("please input the start string");
		java.util.Random r=new java.util.Random(); 
		int result = r.nextInt()%(G.realwordNum);
		result = result<0 ? -result : result;
		StringBuffer beginWord = new StringBuffer(G.pointList[result].word);
		S path = G.RandomGo(beginWord);
		String resultPath = "";
		resultPath = resultPath + beginWord.toString();
		int index = 1;
		while (index <= path.esp) {
			resultPath = resultPath + "->" + path.S[index++].word.toString();
		}
		return resultPath;
	}

	public static void main(String[] args) throws Exception {

		// String initialPath = "/Users/xmh_mac/Downloads/file1.txt";

		// Graph G = createDirectedGraph(initialPath); //读入初始文件 创建有向图
		//
		// showDirectedGraph(G); //展示有向图，生成.dot文件

		/*
		 * 查询桥接词 String findBridgeWord1 = "rises",findBridgeWord2 =
		 * "was",findBridgeResult; findBridgeResult = queryBridgeWords(G,
		 * findBridgeWord1,findBridgeWord2); if(findBridgeResult !=null )
		 * System.out.println(findBridgeResult);
		 */
		 
		/*
		 * 根据桥接词生成新文本 String inputText =
		 * "when the leaves fall, @everything up to go sleep. The poor elephant still sweet."
		 * ; String conbinedText = generateNewText(G, inputText);
		 * System.out.println(conbinedText);
		 */

		// /*计算最短路径
		// String resultPath = calcShortestPath(G, "rises", "up");
		// System.out.println(resultPath);
		// */

		/*
		 * 随机游走 String resultPath = randomWalk(G);
		 * System.out.println(resultPath);
		 */
		JFrame frame = new JFrame("finish my assignment v1.0");

		JPanel entreePanel = new JPanel();
		final ButtonGroup entreeGroup = new ButtonGroup();
		JRadioButton radioButton;
		entreePanel.add(radioButton = new JRadioButton("读入文本", true));
		radioButton.setActionCommand("1");
		entreeGroup.add(radioButton);
		entreePanel.add(radioButton = new JRadioButton("展示有向图"));
		radioButton.setActionCommand("2");
		entreeGroup.add(radioButton);
		entreePanel.add(radioButton = new JRadioButton("查询桥接词"));
		radioButton.setActionCommand("3");
		entreeGroup.add(radioButton);
		entreePanel.add(radioButton = new JRadioButton("生成新文本"));
		radioButton.setActionCommand("4");
		entreeGroup.add(radioButton);
		entreePanel.add(radioButton = new JRadioButton("计算最短路径"));
		radioButton.setActionCommand("5");
		entreeGroup.add(radioButton);
		entreePanel.add(radioButton = new JRadioButton("随机游走"));
		radioButton.setActionCommand("6");
		entreeGroup.add(radioButton);

		JPanel orderPanel = new JPanel();
		JButton orderButton = new JButton("执行");
		orderPanel.add(orderButton);

		final JTextArea area = new JTextArea();
		area.setFont(new Font("Serif", Font.BOLD, 18));
		area.setText("Howdy!\n");
		final JTextField field = new JTextField();
		
		JScrollPane scroll = new JScrollPane(area);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
//		Icon icon = new ImageIcon("/Users/xmh_mac/out_labGraph.gif");
//		JLabel label4 = new JLabel(icon);

		Container content = frame.getContentPane();
		content.setLayout(new GridLayout(3, 1));
		content.add(entreePanel);
		content.add(orderPanel);
		content.add(field);
		content.add(scroll);
//		content.add(label4);

//		field.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				area.append(field.getText() + '\n');
//				field.setText("");
//			}
//		});
		
		

//		model.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent e) {
//				System.out.println(((SpinnerNumberModel) e.getSource()).getValue());
//			}
//		});

		field.addActionListener(new ActionListener() {  //输入框事件
			public void actionPerformed(ActionEvent ae) {
				stringInput = field.getText();
				area.append(field.getText() + '\n');
				field.setText("");
			}
		});
		
		orderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				String entree = entreeGroup.getSelection().getActionCommand();
				switch (entree) {
				case "1": {
					JFileChooser chooser = new JFileChooser(); // 设置选择器
					chooser.setMultiSelectionEnabled(true); // 设为多选
					int returnVal = chooser.showOpenDialog(null); // 是否打开文件选择框
					String filepath = chooser.getSelectedFile().getAbsolutePath();
					System.out.print(filepath);
					if (!filepath.endsWith(".txt")) {
						JOptionPane.showConfirmDialog(null, "文件错误，请重新选择文件！", "Overwrite file",
								JOptionPane.CLOSED_OPTION);
						break;
					}
					G = createDirectedGraph(filepath);
					if (G != null)
						JOptionPane.showConfirmDialog(null, "有向图生成成功！", "Overwrite file", JOptionPane.CLOSED_OPTION);
					else
						JOptionPane.showConfirmDialog(null, "文件错误，请重新选择文件！", "Overwrite file",
								JOptionPane.CLOSED_OPTION);
				}

					break;
				case "2": {
					if (G == null)
						JOptionPane.showConfirmDialog(null, "请先读入文件", "Overwrite file", JOptionPane.CLOSED_OPTION);
					else {
						String graphPath = "/Users/xmh_mac/labGraph.dot";
						G.graphShow();
						JOptionPane.showConfirmDialog(null, "有向图位置为" + graphPath, "Overwrite file",
								JOptionPane.CLOSED_OPTION);
						GraphViz gg = new GraphViz();
						File fin = new File("/Users/xmh_mac/labGraph.dot");
						File fout = new File("/Users/xmh_mac/out_labGraph.gif");
						
					
						
						try {  
				            String[] shpath={"/bin/sh","-c","/Users/xmh_mac/Desktop/command2.sh"};  
				            Process ps = Runtime.getRuntime().exec(shpath);  
				            //ps.waitFor();  
				  
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
						
//					   	 Runtime run = Runtime.getRuntime();
//				         try {
//				             Process process = run.exec("");
//				             InputStream in = process.getInputStream();
//				             while (in.read() != -1) {
//				                 System.out.println(in.read());
//				             }
//				             in.close();
//				             process.waitFor();
//				         } catch (Exception e) {
//				             e.printStackTrace();
//				         }
					}
				}
					break;
				case "3": {
					if (G == null)
						JOptionPane.showConfirmDialog(null, "请先读入文件", "Overwrite file", JOptionPane.CLOSED_OPTION);
					else {
						String findBridgeResult;
						int i;
						i = stringInput.indexOf(' ');
						if(i<1){
							JOptionPane.showConfirmDialog(null, "请重新输入单词", "Overwrite file", JOptionPane.CLOSED_OPTION);
							field.setText("");
						} else {
							String string1 = stringInput.substring(0, i);
							String string2 = stringInput.substring(i + 1);
							findBridgeResult = queryBridgeWords(G, string1, string2);
							area.setText("");
							area.append(findBridgeResult + '\n');
						}
					}
				}
					break;
				case "4": {
					if (G == null)
						JOptionPane.showConfirmDialog(null, "请先读入文件", "Overwrite file", JOptionPane.CLOSED_OPTION);
					else {
						String resultString = generateNewText(G, stringInput);
						if(resultString=="") JOptionPane.showConfirmDialog(null, "请重新输入文本", "Overwrite file", JOptionPane.CLOSED_OPTION);
						area.setText("");
						area.append(resultString + '\n');
					}
				}
					break;

				case "5": {
					if (G == null)
						JOptionPane.showConfirmDialog(null, "请先读入文件", "Overwrite file", JOptionPane.CLOSED_OPTION);
					else {
						int i;
						i = stringInput.indexOf(' ');
						try {  
				            String[] shpath={"/bin/sh","-c","/Users/xmh_mac/Desktop/command5-2"
				            		+ ".sh"};  
				            Process ps = Runtime.getRuntime().exec(shpath);  
				            //ps.waitFor();  
				  
				            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));  
				            StringBuffer sb = new StringBuffer();  
				            String line;  
				            while ((line = br.readLine()) != null) {  
				                sb.append(line).append("\n");  
				            }  
				            String rresult = sb.toString();  
				            System.out.println(rresult);  
				            }   
				        catch (Exception e) {  
				            e.printStackTrace();  
				            } 
						if (i == -1) {
							String re = null;
							String string1 = stringInput;
							try {
								re = calcShortestPath(G, string1, null);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(re == null) {
								JOptionPane.showConfirmDialog(null,
										"输入有误，请重新输入！",
										"Overwrite file", JOptionPane.CLOSED_OPTION);
								break;
							}
							JOptionPane.showConfirmDialog(null,
									"只输入一个单词，输出图中该单词到所有点的最短路径\n文件地址为/Users/xmh_mac/lab1_shortestPath/\n不可达的单词有："+unreach.toString(),
									"Overwrite file", JOptionPane.CLOSED_OPTION);
							unreach = new StringBuffer();
							try {  
					            String[] shpath={"/bin/sh","-c","/Users/xmh_mac/Desktop/command5-1"
					            		+ ".sh"};  
					            Process ps = Runtime.getRuntime().exec(shpath);  
					            //ps.waitFor();  
					  
					            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));  
					            StringBuffer sb = new StringBuffer();  
					            String line;  
					            while ((line = br.readLine()) != null) {  
					                sb.append(line).append("\n");  
					            }  
					            String rresult = sb.toString();  
					            System.out.println(rresult);  
					            }   
					        catch (Exception e) {  
					            e.printStackTrace();  
					            } 
							field.setText("");
						} else {
							String string1 = stringInput.substring(0, i);
							String string2 = stringInput.substring(i + 1);
							try {
								String result = calcShortestPath(G, string1, string2);
								if (result == "输入单词有误，请重新输入！" || result == null)
									JOptionPane.showConfirmDialog(null, "输入的单词有误，请重新输入！", "Overwrite file",
											JOptionPane.CLOSED_OPTION);
								else if(result == "Unreachable"){
									JOptionPane.showConfirmDialog(null, "目标不可达！", "Overwrite file",
											JOptionPane.CLOSED_OPTION);
								}
								else {

									//area.setText(unreach.toString());
									JOptionPane.showConfirmDialog(null,
											"输入两个单词，输出图中该两单词之间的最短路径\n文件地址为/Users/xmh_mac/shortestlabGraph.dot",
											"Overwrite file", JOptionPane.CLOSED_OPTION);
									
									try {  
							            String[] shpath={"/bin/sh","-c","/Users/xmh_mac/Desktop/command5-0.sh"};  
							            Process ps = Runtime.getRuntime().exec(shpath);  
							            //ps.waitFor();  
							  
							            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));  
							            StringBuffer sb = new StringBuffer();  
							            String line;  
							            while ((line = br.readLine()) != null) {  
							                sb.append(line).append("\n");  
							            }  
							            String rresult = sb.toString();  
							            System.out.println(rresult);  
							            }   
							        catch (Exception e) {  
							            e.printStackTrace();  
							            } 
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}
					break;
					
				case "6": {
					if (G == null)
						JOptionPane.showConfirmDialog(null, "请先读入文件", "Overwrite file", JOptionPane.CLOSED_OPTION);
					else {
						try {
							area.append(randomWalk(G)+'\n');
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
				default:
					break;
				}

				// int rc = JOptionPane.showConfirmDialog(null,
				// "File already exist. Do you want to overwrite?",
				// "Overwrite file",
				// JOptionPane.YES_NO_OPTION);
				// System.out.print(rc);
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 300);
		frame.setVisible(true);

	}
}