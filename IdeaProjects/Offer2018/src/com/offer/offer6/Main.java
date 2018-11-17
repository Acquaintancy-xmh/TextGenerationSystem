package com.offer.offer6;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    public class listNode{
        int value;
        listNode next = null;

        public listNode(int data){
            this.value = data;
        }
    }

    public static listNode head = null;

    /**
     * 添加节点
     * @param value
     */
    public void addNode(int value){
        listNode newNode = new listNode(value);
        if(head == null){
            head = newNode;
            return;
        }
        listNode temp = head;
        while(temp.next != null) temp = temp.next;
        temp.next = newNode;
    }

    /**
     * 顺序打印节点
     */
    public void printNode(){
        if(head == null){
            System.out.println("链表无内容");
            return;
        }
        listNode temp = head;
        while(temp != null){
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    /**
     * 根据节点内容删除特定节点
     * @param value
     */
    public void deleteNode(int value){
        if(head == null){
            System.out.println("链表无内容");
            return;
        }
        if(head.value == value){
            if(head.next != null){
                head = head.next;
                return;
            }
            else head = null;
        }
        listNode temp = head;
        listNode ahead_temp;
        while(temp.next!=null){
            if(temp.next.value == value){
                ahead_temp = temp;
                temp = temp.next;
                ahead_temp.next = temp.next;
                return;
            }
            temp = temp.next;
        }
        return;
    }

    /**
     * 用递归的方法实现倒序打印链表
     * @param listNode
     * @return
     */

    private static ArrayList<Integer> resultList = new ArrayList<Integer>();

    public ArrayList<Integer> printListFromTailToHead(listNode listNode) {
        if(listNode != null){
            this.printListFromTailToHead(listNode.next);
            resultList.add(listNode.value);
        }
        return resultList;
    }

    /**
     * 用堆栈的方法实现倒序打印链表
     * @param listNode
     * @return
     */
    public ArrayList<Integer> Stack_printListFromTailToHead(listNode listNode){
        Stack<Integer> resultStack = new Stack<Integer>();
        while(listNode!=null){
            resultStack.push(listNode.value);
            listNode = listNode.next;
        }
        ArrayList<Integer> resultList = new ArrayList<>();
        while(!resultStack.isEmpty()) resultList.add(resultStack.pop());
        return resultList;
    }

    public static void main(String args[]) throws IOException {
        Main linkList = new Main();
        Scanner sc = new Scanner(System.in);
        while(true){
            int value = sc.nextInt();
            if(value == -1){
                break;
            }
            linkList.addNode(value);
        }
        linkList.printNode();
        linkList.deleteNode(sc.nextInt());
        linkList.printNode();

        resultList = linkList.printListFromTailToHead(linkList.head);
        System.out.println(resultList);
        resultList = linkList.Stack_printListFromTailToHead(linkList.head);
        System.out.println(resultList);
    }
}
