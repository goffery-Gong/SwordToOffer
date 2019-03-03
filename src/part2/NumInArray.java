package part2;

import part1.Utils;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/19 09:37
 * @Description:
 */
public class NumInArray {
    /**
     * 通过对比下标值与数值
     * @param a
     * @return
     */
    static int numInArray(int[] a) {
        if (Utils.isNullorZero(a))
            return -1;

        //判断两个值与下标是否相同，相同比较下一个，不同则交换
        for (int i = 0; i < a.length; i++) {
            while (a[i] != i) {
                if (a[i] != a[a[i]])
                    exchange(a, a[i], a[a[i]]);
                else
                    return a[i];
            }
        }
        return -1;
    }

    private static void exchange(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }



    /**
     * 不改变数组，类似二分法，通过统计区间的数字个数。
     * 1~ m 中如果1-m数值个数大于m，则有重复数字, (m+1)-n
     * @param a
     * @return
     */
    static int likebinary(int[] a) {
        if (Utils.isNullorZero(a))
            return -1;

        int lo = 1;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            int count = CountTimes(a, lo, mid);
            if (lo == hi) {
                if (count > 1)
                    return lo;
                else
                    break;
            }
            if (count > (mid - lo + 1))
                hi = mid;
            else
                lo = mid+1;//注意这里
        }
        return -1;
    }

    private static int CountTimes(int[] a, int lo, int hi) {
        int count = 0;
        for (int i : a) {
            if (i >= lo && i <= hi)
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int[] a = {2, 3, 1, 0, 2, 5, 3};
        int[] b = new int[1];
        System.out.println(likebinary(a));
        System.out.println(numInArray(a));
    }
}
