package com.offer.offer7;

import java.util.Arrays;

/**
 * 利用前序遍历与中序遍历重建二叉树
 */
public class reBuildBTree {
    public static class BTree{
        int value;
        BTree leftNode;
        BTree rightNode;

        public BTree(int value){
            this.value = value;
        }
    }

    public static BTree reConstructBinaryTree(int [] pre,int [] in){
        if(pre.length == 0 || in.length == 0){
            return null;
        }
        BTree root = new BTree(pre[0]);
        for(int i = 0;i<in.length;i++){
            if(pre[0] == in[i]){
                root.leftNode = reConstructBinaryTree(Arrays.copyOfRange(pre,1,i+1),Arrays.copyOfRange(in,0,i));
                root.rightNode = reConstructBinaryTree(Arrays.copyOfRange(pre,i+1,pre.length),Arrays.copyOfRange(in,i+1,in.length));
            }
        }
        return root;
    }
}
