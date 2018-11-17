package com.offer.offer7;

import java.util.Arrays;

/**
 * 根据前序序列和中序序列重建二叉树
 */
public class Main {
    public static void main(String args[]){
//        int[] list = {0,1,2,3,4,5,6};
//        int[] copylist = Arrays.copyOfRange(list,0,1);
//        for(int i = 0;i<copylist.length;i++){
//            System.out.println(copylist[i]);
//        }
//
        int[] pre = {1,2,4,7,3,5,6,8};
        int[] in = {4,7,2,1,5,3,8,6};
        reBuildBTree.BTree root;
        root = reBuildBTree.reConstructBinaryTree(pre,in);

    }

}
