package part2;

import part1.MatchModel;
import part1.MaxSubArray;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/18 09:16
 * @Description:
 */
public class MaxSubSum {
    int maxSubSum(int[] a) {
        int maxSum = 0;
        int tempSum;
        for (int i = 0; i < a.length; i++) {
            tempSum = 0;
            for (int j = i; j < a.length; j++) {
                tempSum += a[j];
                if (tempSum > maxSum)
                    maxSum = tempSum;
            }
        }
        return maxSum;
    }

    int maxSubSumDG(int[] a) {
        int tempSum = 0;
        int maxSum = 0x80000000;

        for (int i = 0; i < a.length; i++) {
            tempSum = (tempSum + a[i] >= 0) ? tempSum + a[i] : a[i];
            maxSum = (tempSum > maxSum) ? tempSum : maxSum;
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[] a={-1,-1};
        System.out.println(new MaxSubSum().maxSubSumDG(a));
        System.out.println(new MaxSubArray().maxSubArray_dp(a));
    }
}
