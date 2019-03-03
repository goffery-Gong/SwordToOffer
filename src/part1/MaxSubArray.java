package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/20 16:37
 * @Description:
 */
public class MaxSubArray {
    /**
     * o(n^2)
     *
     * @param array
     * @return
     */
    int maxSubArray(int[] array) {
        if (array == null && array.length == 0)
            return 0;
        int tempSum;
        int maxSum = 0;

        for (int i = 0; i < array.length; i++) {
            tempSum = 0;
            for (int j = i; j < array.length; j++) {
                tempSum += array[j];
                if (tempSum > maxSum)
                    maxSum = tempSum;
            }
        }
        return maxSum;
    }

    public int maxSubArray_dp(int[] array) {
        if (array == null && array.length == 0)
            return 0;

        int tempSum = array[0];
        int maxSum = array[0]; //防止为负

        for (int i = 1; i < array.length; i++) {
            tempSum = (tempSum < 0) ? array[i] : tempSum + array[i];
            maxSum = (tempSum > maxSum) ? tempSum : maxSum;
        }
        /*int tempSum = 0;
        int maxSum = 0x80000000; //32位数最小值，负2的32次方

        for (int i = 0; i < array.length; i++) {
            tempSum = (tempSum < 0) ? array[i] : tempSum + array[i];
            maxSum = (tempSum > maxSum) ? tempSum : maxSum;
        }*/
        return maxSum;
    }
}
