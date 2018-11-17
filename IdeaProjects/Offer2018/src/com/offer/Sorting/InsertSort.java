package com.offer.Sorting;

/**
 * 时间复杂度：O(n^2)
 */
public class InsertSort {
    public int[] sortList;

    InsertSort(int[] inputlist){
        this.sortList = inputlist;
    }

    public void sortInsertList(){
        for(int i=1;i<sortList.length;i++) {
            int j = i;
            while (sortList[j] < sortList[j - 1]) {
                Swap(j, j - 1);
                j--;
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
