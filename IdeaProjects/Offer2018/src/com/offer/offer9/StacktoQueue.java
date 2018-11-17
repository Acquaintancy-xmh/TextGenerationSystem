package com.offer.offer9;
import java.util.Stack;

public class StacktoQueue {
    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    public void appendTail(int value){
        stack1.push(value);
    }

    public int deleteHead(){
        if(!stack2.isEmpty()){
            return stack2.pop();
        }
        else{
            while(!stack1.isEmpty()) stack2.push(stack1.pop());
            return stack2.pop();
        }
    }
}
