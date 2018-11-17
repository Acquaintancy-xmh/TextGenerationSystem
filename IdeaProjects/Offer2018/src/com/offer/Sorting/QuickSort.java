package com.offer.Sorting;

/**
 * 时间复杂度：O(nlog2n)
 * 空间复杂度：O(log2n)
 */

public class QuickSort{
    public int[] sortList;

    QuickSort(int[] inputlist){
        this.sortList = inputlist;
    }

    private int Partition(int start,int end){
        int index = sortList[start];
        while(start<end){
            while(start<end && sortList[end]>index) end--;
            if(start<end){
                sortList[start] = sortList[end];
                start++;
            }
            while(start<end && sortList[start]<index) start++;
            if(start<end){
                sortList[end] = sortList[start];
                end--;
            }
        }
        sortList[start] = index;
        return start;
    }

    public void sortQuickList(int start,int end){
        if(start<end){
            int index = Partition(start,end);
            sortQuickList(start,index-1);
            sortQuickList(index+1,end);
        }
    }
}
