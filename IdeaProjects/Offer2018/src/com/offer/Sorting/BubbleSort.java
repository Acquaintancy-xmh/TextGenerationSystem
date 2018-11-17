package com.offer.Sorting;

public class BubbleSort {
    public int[] sortList;

    BubbleSort(int[] inputlist){
        this.sortList = inputlist;
    }

    public void sortBubbleList(){
        int length = sortList.length;
        for(int i=0;i<=length-1;i++){
            for(int j=length-1;j>=i+1;j--){
                if(sortList[j]<sortList[j-1]) Swap(j,j-1);
            }
        }
    }

    private void Swap(int i,int j){
        int temp;
        temp = sortList[i];
        sortList[i] = sortList[j];
        sortList[j] = temp;
    }
}


