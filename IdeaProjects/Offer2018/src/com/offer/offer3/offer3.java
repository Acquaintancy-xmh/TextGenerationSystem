package com.offer.offer3;

public class offer3 {
    public static boolean duplicate(int numbers[],int length,int [] duplication) {
        if(numbers == null || length<=0){
            return false;
        }
        for(int i=0;i<length;i++){
            if(numbers[i]<0 || numbers[i]>(length-1)){
                return false;
            }
        }
        for(int i=0;i<length;++i){
            while(numbers[i] != i){
                if(numbers[i] == numbers[numbers[i]]){
                    duplication[0] = numbers[i];
                    return true;
                }
                int temp = numbers[i];
                numbers[i] = numbers[numbers[i]];
                numbers[temp] = temp;
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//	    int numbers[] = {4,3,1,0,2,4,3};
//	    int duplication[] = {0};
//	    System.out.println(duplicate(numbers,numbers.length,duplication));
//	    System.out.println(duplication[0]);
//
//    }
}
