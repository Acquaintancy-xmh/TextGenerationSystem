package com.offer.offer9;
import java.util.Stack;

public class Main {
    public static void main(String args[]){
        StacktoQueue queue1 = new StacktoQueue();
        queue1.appendTail(1);
        queue1.appendTail(2);
        queue1.appendTail(3);
        System.out.println(queue1.deleteHead());
        System.out.println(queue1.deleteHead());
        queue1.appendTail(9);
        System.out.println(queue1.deleteHead());
        System.out.println(queue1.deleteHead());
    }
}
