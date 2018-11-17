package com.offer.offer4;

public class Main {
    public static boolean Find(int[][] matrix,int rows,int columns,int number){
        boolean found = false;
        if(matrix != null && rows>0 && columns>0){
            int row=0;
            int column=columns-1;
            while(row<rows && column>=0){
                if(matrix[row][column] == number){
                    found = true;
                    break;
                }
                else if(matrix[row][column]>number) column--;
                else row++;
            }
        }
        return found;
    }

//    public static void main(String[] args){
//        int[][] numbers = {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
//        System.out.println(Find(numbers,4,4,7));
//    }
}
