package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/4 09:41
 * @Description:
 */
public class LostNUm {
    public int lostNum(int[] a) {
        if (Utils.isNullorZero(a))
            return -1;
        if (a[0] != 0)
            return 0;

        for (int i = 0; i < a.length; i++)
            if (i + 1 < a.length && a[i] + 1 != a[i + 1])
                return i + 1;
            else if (i + 1 == a.length)
                return a.length;
        return -1;
    }

    /**
     * 类二分法
     *
     * @param a
     * @return
     */
    public int binary(int[] a) {
        if (Utils.isNullorZero(a))
            return -1;
        int mid;
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            //如果mid元素的值与下标相等，则查找右半边；
            //如果mid元素值与下标不等，且前面一个元素值与index相等，则mid元素就是返回值
            //如果mid元素值与下标不等，且前面一个元素值与index不相等，查找左半边
            mid = (lo + hi) >> 1;
            if (a[mid] == mid)
                lo = mid + 1;
            else if (a[mid] != mid && mid - 1 >= 0 && a[mid - 1] != (mid - 1))
                hi = mid - 1;
            else
                return mid;
        }
        if (lo == a.length)
            return lo;
        return -1;
    }

    /**
     * 二分法求区间数字个数,借鉴面试题3
     *
     * @param
     */
    public int binaryNum(int[] a) {
        int mid;
        int lo = 0;
        int hi = a.length - 1;
        int len = a.length;
        while (lo <= hi) {
            mid = (lo + hi) >> 1;
            int count = countRange(a, lo, mid);
            //分两段，lo-mid和mid+1~hi；count比lo-mid长度小，则hi=mid-1;
            if (lo == hi) {
                if (count < 1)
                    return lo;
                else if(lo==len-1)
                    return lo+1;
                else
                    break;
            }
            if (count < (mid - lo + 1))
                hi = mid;
            else
                lo = mid + 1;
        }
        if(lo==a.length)
            return lo;
        return -1;
    }

    private int countRange(int[] a, int lo, int hi) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= lo && a[i] <= hi)
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 3, 4};
        System.out.println(new LostNUm().binaryNum(a));
    }
}
