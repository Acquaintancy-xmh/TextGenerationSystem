package com.offer.offer10;

public class Fibonacci {
    /**
     * 使用递归的方法求Fibonacci数列的第n项
     * @param n
     * @return
     */
    public int fibonacci1(int n){
        if(n <= 0) return 0;
        if(n == 1) return 1;
        return fibonacci1(n-1) + fibonacci1(n-2);
    }

    /**
     * 用循环的方法求fibonacci数列的第n项
     * @param n
     * @return
     */
    public int fibonacci2(int n){
        if(n <= 0) return 0;
        if(n == 1) return 1;
        int fibNMinusOne = 1;
        int fibNMinusTwo = 0;
        int fibN = 0;
        for(int i = 2;i<=n;i++){
            fibN = fibNMinusOne + fibNMinusTwo;
            fibNMinusTwo = fibNMinusOne;
            fibNMinusOne = fibN;
        }
        return fibN;
    }

}
