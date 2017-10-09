package lab;


public class S{
	final static int N=100;
	wordPoint[] S;
	int esp;
	public S(){
		S = new wordPoint[N];
		esp = -1;
	}
	public void Push(wordPoint point){
		if(esp>=N) System.out.println("Srack is full!");
		esp++;
		S[esp] = point;
	}
	public wordPoint Pull(){
		if(esp == -1) System.out.println("Stack is empty!");
		return S[esp--];
	}
	public wordPoint getTop(){
		if(esp == -1) System.out.println("Stack is empty!");
		return S[esp];
	}
	public int isEmpty(){
		if(esp<0) return 1;
		else return 0;
	}
	public boolean isRepeat(wordPoint w){
		for(int i=0;i<=esp;i++){
			if(S[i].word.equals(w.word)) return true;
		}
		return false;
	}
}