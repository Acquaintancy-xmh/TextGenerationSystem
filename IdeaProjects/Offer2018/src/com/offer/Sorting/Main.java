package com.offer.Sorting;

import java.util.Arrays;

public class Main {
    public static void main(String args[]){
        int[] list = {72,6,34,88,50,10,4};

        BubbleSort sortbubble = new BubbleSort(list);
        sortbubble.sortBubbleList();
        System.out.println(Arrays.toString(sortbubble.sortList));

        QuickSort sortquick = new QuickSort(list);
        sortquick.sortQuickList(0,list.length-1);
        System.out.println(Arrays.toString(sortquick.sortList));

        InsertSort sortinsert = new InsertSort(list);
        sortinsert.sortInsertList();
        System.out.println(Arrays.toString(sortinsert.sortList));

        MergeSort sortmerge = new MergeSort(list);
        sortmerge.mergeSort(0,list.length-1);
        System.out.println(Arrays.toString(sortmerge.sortList));
    }
}
