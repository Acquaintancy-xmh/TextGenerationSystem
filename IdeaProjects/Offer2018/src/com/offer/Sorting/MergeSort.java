package com.offer.Sorting;

/**
 * 时间复杂度：O(nlog2n)
 */

public class MergeSort {
    public int[] sortList;

    MergeSort(int[] inputlist){
        this.sortList = inputlist;
    }

    private void merge(int start,int mid,int end){
        int length = end - start + 1;
        int[] newlist = new int[length];
        int i = start,j = mid+1,k = 0;
        while(i<=mid && j<=end)
        if(sortList[i]<sortList[j]) newlist[k++] = sortList[i++];
        else newlist[k++] = sortList[j++];
        while(i<=mid) newlist[k++] = sortList[i++];
        while(j<=end) newlist[k++] = sortList[j++];

        for(int index=0;index<length;index++){
            sortList[index+start] = newlist[index];
        }
    }

    public void mergeSort(int start,int end){
        int mid = (start+end)/2;
        if(start<end){
            mergeSort(start,mid);
            mergeSort(mid+1,end);
            merge(start,mid,end);
        }
    }
}
