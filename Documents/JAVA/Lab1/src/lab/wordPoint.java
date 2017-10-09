package lab;

public class wordPoint{
	StringBuffer word;
	wordEdge next;
	int numChild;
	
	public wordPoint(StringBuffer wordList){
		word = wordList;
		numChild = 0;
		next = null;
	}
}