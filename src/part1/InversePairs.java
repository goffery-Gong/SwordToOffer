package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/1 15:00
 * @Description:
 */
public class InversePairs {
    /**
     * 数组中的逆序对
     *
     * @param array
     * @return
     */
    public int inversePairs(int[] array) {
        if (array == null)
            return 0;

        int[] copy = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        return inversePairsCore(array, copy, 0, array.length - 1);

    }

    /**
     * @param array
     * @param copy  辅助数组
     * @param lo
     * @param hi
     * @return 测试用例输出结果比较大，对每次返回的count mod(1000000007)求余
     */
    private int inversePairsCore(int[] array, int[] copy, int lo, int hi) {
        if (lo == hi)
            return 0;

        int mid = (lo + hi) >>> 1;
        int leftCount = inversePairsCore(copy, array, lo, mid) % 1000000007;
        int rightCount = inversePairsCore(copy, array, mid + 1, hi) % 1000000007;

        int i = mid;
        int j = hi;
        int copyIndex = hi; //辅助数组的index
        int count = 0;
        while (i >= lo && j >= mid + 1) {
            if (array[i] > array[j]) {
                count += j - mid;
                copy[copyIndex--] = array[i--];
                //防止count太大而越界
                if (count >= 1000000007)
                    count %= 1000000007;
            } else
                copy[copyIndex--] = array[j--];
        }

        //上面循环是while(i >= low && j > mid) {} 当有一个指针不满足条件时剩余的数字直接进行拷贝到拷贝数组当中，
        // 因此用了两个for循环 如果满足条件就直接把剩余比它小的数直接进行拷贝
        for (; i >= lo; i--)
            copy[copyIndex--] = array[i];
        for (; j >= mid + 1; j--)
            copy[copyIndex--] = array[j];

        return (leftCount + rightCount + count) % 1000000007;
    }

    public static void main(String[] args) {
        int[] a = {7, 5, 6, 4};
        System.out.println(new InversePairs().inversePairs(a));
    }
}
