package com.offer.offer3_2;

public class Main {

    /**
     * 利用二分法获得数组中重复的数字
     * @param numbers
     * @param length
     * @return duplication
     */
    public static int duplicate(int numbers[], int length){
        if(numbers == null || length <= 0){
            return -1;
        }
        int start = 1;
        int end = length - 1;
        while(end >= start){
            int middle = ((end - start) >> 1 ) + start;
            int count = countRange(numbers,length,start,middle);
            if(end == start){
                if(count>1) return start;
                else break;
            }
            if(count>(middle - start + 1)) end = middle;
            else start = middle + 1;
        }
        return  -1;
    }

    public static int countRange(int numbers[],int length,int start,int end){
        if(numbers == null){
            return 0;
        }
        int count = 0;
        for(int i=0;i<length;i++){
            if(numbers[i]>=start && numbers[i]<=end) ++count;
        }
        return count;
    }

    /**
     * 利用boolean型的辅助数组找出重复的数字
     * @param numbers
     * @param length
     * @return duplication
     */

    public static int ano_duplicate(int numbers[], int length){
        if(numbers == null || length == 0){
            return -1;
        }
        boolean[] flags = new boolean[length];
        for(int i=0;i<length;i++){
            flags[i] = false;
        }
        for(int i = 0;i < length;i++){
            if(flags[numbers[i]] == false) flags[numbers[i]] = true;
            else return numbers[i];
        }
        return -1;
    }



//    public static void main(String[] args) {
//        int numbers[] = {2,3,5,4,3,2,6,7};
//        int duplication = duplicate(numbers,numbers.length);
//        System.out.println(duplication);
//        System.out.println(ano_duplicate(numbers,numbers.length));
//    }
}
