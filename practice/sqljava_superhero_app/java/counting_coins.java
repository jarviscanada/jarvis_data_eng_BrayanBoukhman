package ca.jrvs.practice.codingChallenge.temp;

import java.util.Arrays;

public class counting_coins {

    public static int changeFast(int target) {
        int[] changeTotal = new int[target+1];
        Arrays.fill(changeTotal, Integer.MAX_VALUE);
        int[] coins = new int[]{ 1, 7, 22};


        changeTotal[1] = 1;
        if(target > 7) {
            changeTotal[7] = 1;
        }
        if(target > 22) {
            changeTotal[22] = 1;
        }

        for(int i = 1; i < target; i++) {
            for(int coin: coins) {
                if(i+coin < target) {
                    changeTotal[i+coin] = Math.min(changeTotal[i+coin], changeTotal[i] + 1);
                }
            }
        }

        return changeTotal[target+1];
    }
}
